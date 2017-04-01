package github.cccxm.mydemo.android.layout.recycler

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import github.cccxm.mydemo.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import java.util.*

/**
 * Created by cxm
 * on 2017/3/31.
 */
class RecyclerGroupActivity : AppCompatActivity() {
    private val ui = RecyclerGroupUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        setSupportActionBar(ui.mToolBar)
        val appBar = supportActionBar ?: return
        appBar.setDisplayHomeAsUpEnabled(true)
        appBar.title = "数据分组"
        ui.mToolBar.setNavigationOnClickListener { onBackPressed() }

        ui.mRecyclerView.layoutManager = LinearLayoutManager(this)
        val javaClass = RecyclerGroupDataBean().javaClass
        val data = Gson().fromJson(RecyclerGroupJsonData, javaClass)
        val adapter = RecyclerGroupAdapter(applicationContext)
        ui.mRecyclerView.adapter = adapter
        adapter.list = data.data!!
        adapter.notifyDataSetChanged()
    }
}

private class RecyclerGroupUI : AnkoComponent<RecyclerGroupActivity> {
    lateinit var mToolBar: Toolbar
    lateinit var mRecyclerView: RecyclerView

    override fun createView(ui: AnkoContext<RecyclerGroupActivity>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            appBarLayout(R.style.AppTheme_AppBarOverlay) {
                toolbar(R.style.AppTheme_PopupOverlay) { mToolBar = this }.lparams(matchParent, matchParent)
            }.lparams(matchParent, dimen(R.dimen.tool_bar_height))
            recyclerView { mRecyclerView = this }.lparams(matchParent, matchParent)
        }
    }
}

private class RecyclerGroupAdapter(private val context: Context) : AbsRecyclerGroupAdapter<RecyclerGroupChildBean, RecyclerGroupParentBean, RecyclerGroupChildHolder, RecyclerGroupParentHolder>() {
    var list: MutableList<RecyclerGroupParentBean> = LinkedList()

//    private var mItemClickListener: ()? = null

    override fun getParentCount() = list.size

    override fun getChildCount(parentPosition: Int) = list[parentPosition].values!!.size

    override fun getParent(parentPosition: Int) = list[parentPosition]

    override fun getChild(parentPosition: Int, childPosition: Int) = getParent(parentPosition).values!![childPosition]

    override fun onCreateParentHolder(): RecyclerGroupParentHolder {
        var type: TextView? = null
        val view = with(context) {
            linearLayout {
                lparams(matchParent, wrapContent)
                textView {
                    type = this
                    textColor = Color.BLACK
                    textSize = 32f
                    gravity = Gravity.CENTER
                    padding = dip(10)
                }.lparams(wrapContent, wrapContent)
            }
        }
        return RecyclerGroupParentHolder(type!!, view)
    }

    override fun onCreateChildHolder(): RecyclerGroupChildHolder {
        var name: TextView? = null
        var value: TextView? = null
        val view = with(context) {
            relativeLayout {
                lparams(matchParent, wrapContent)
                textView {
                    name = this
                    textColor = Color.GRAY
                    textSize = 22f
                    padding = dip(10)
                }.lparams(wrapContent, wrapContent) {
                    alignParentLeft()
                }
                textView {
                    value = this
                    textColor = Color.GRAY
                    textSize = 22f
                    padding = dip(10)
                }.lparams(wrapContent, wrapContent) {
                    alignParentRight()
                }
            }
        }
        return RecyclerGroupChildHolder(name!!, value!!, view)
    }

    override fun onBindParentHolder(holder: RecyclerGroupParentHolder, item: RecyclerGroupParentBean) {
        holder.type.text = item.type
    }

    override fun onBindChildHolder(holder: RecyclerGroupChildHolder, item: RecyclerGroupChildBean) {
        holder.name.text = item.name
        holder.value.text = item.value
    }
}

private class RecyclerGroupParentHolder(val type: TextView, view: View) : RecyclerView.ViewHolder(view)

private class RecyclerGroupChildHolder(val name: TextView, val value: TextView, view: View) : RecyclerView.ViewHolder(view)

private abstract class AbsRecyclerGroupAdapter<CT, PT, CH : RecyclerView.ViewHolder, PH : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private val TYPE_PARENT = 0
        private val TYPE_CHILDREN = 1
    }

    private val parentPositions = LinkedList<Int>()

    override fun getItemCount(): Int {
        var sumCount = 0
        parentPositions.clear()
        (0 until getParentCount()).forEach {
            parentPositions.add(sumCount)
            sumCount += (getChildCount(it) + 1)
        }
        return sumCount
    }

    override fun getItemViewType(position: Int): Int {
        return if (parentPositions.contains(position)) TYPE_PARENT else TYPE_CHILDREN
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PARENT -> onCreateParentHolder()
            TYPE_CHILDREN -> onCreateChildHolder()
            else -> TODO()
        }
    }

    protected var parentPosition = 0
        private set
    protected var childPosition = 0
        private set

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_PARENT -> {
                childPosition = 0
                var sumCount = 0
                (0 until getParentCount()).forEach {
                    if (sumCount == position) {
                        parentPosition = it
                        onBindParentHolder(holder as PH, getParent(parentPosition))
                        return
                    }
                    sumCount += (getChildCount(it) + 1)
                }
            }
            TYPE_CHILDREN -> {
                var sumCount = 0
                (0 until getParentCount()).forEach {
                    parentPosition = it
                    val sum = sumCount + (getChildCount(it) + 1)
                    if (sum > position) {
                        childPosition = position - sumCount - 1
                        onBindChildHolder(holder as CH, getChild(parentPosition, childPosition))
                        return
                    }
                    sumCount = sum
                }
            }
            else -> TODO()
        }
    }

    abstract fun getParentCount(): Int
    abstract fun getChildCount(parentPosition: Int): Int
    abstract fun getParent(parentPosition: Int): PT
    abstract fun getChild(parentPosition: Int, childPosition: Int): CT
    abstract fun onCreateParentHolder(): PH
    abstract fun onCreateChildHolder(): CH
    abstract fun onBindParentHolder(holder: PH, item: PT)
    abstract fun onBindChildHolder(holder: CH, item: CT)
}

class RecyclerGroupDataBean(var data: MutableList<RecyclerGroupParentBean>? = null)

class RecyclerGroupParentBean(var type: String? = null, var values: List<RecyclerGroupChildBean>? = null)

class RecyclerGroupChildBean(var id: String? = null, var name: String? = null, var value: String? = null)

private val RecyclerGroupJsonData = """
 {"data":[{"type":"\u57fa\u672c\u4fe1\u606f","values":[{"id":"60","name":"ABS","value":"\u6807\u914d"},
{"id":"61","name":"\u79cd\u7c7b","value":"\u8e0f\u677f"}]},{"type":"\u53d1\u52a8\u673a","values":[
{"id":"41","name":"\u53d1\u52a8\u673a\u5f62\u5f0f","value":"\u53cc\u7f38"},{"id":"65","name":"\u6392\u91cf","value":"647cc"}
,{"id":"66","name":"\u6700\u5927\u529f\u7387","value":"44 Kw \/ 7500rpm"},{"id":"70","name":"\u5185\u5f84\u00d7\u884c\u7a0b",
"value":"79mm x66mm"},{"id":"42","name":"\u51b2\u7a0b","value":"\u56db\u51b2\u7a0b"},{"id":"43","name":
"\u6700\u5927\u626d\u77e9","value":"66 N\u00b7m \/ 6000rpm"},{"id":"44","name":"\u51b7\u5374\u7cfb\u7edf",
"value":"\u6c34\u51b7"},{"id":"45","name":"\u542f\u52a8\u65b9\u5f0f","value":"\u7535\/\u811a\u8d77\u52a8"}
,{"id":"46","name":"\u71c3\u6599\u4f9b\u7ed9\u5f62\u5f0f","value":"\u7535\u55b7"},{"id":"47",
"name":"\u4f20\u52a8\u65b9\u5f0f","value":"\u94fe\u6761"},{"id":"48","name":"\u538b\u7f29\u6bd4","value":"11.6:1"}]},
{"type":"\u8f66\u4f53","values":[{"id":"59","name":"\u6cb9\u7bb1\u5bb9\u79ef","value":"16l"},
{"id":"67","name":"\u524d\u8f6e\u8f6e\u80ce\u89c4\u683c","value":"120\/70 R15"},{"id":"68","name":
"\u540e\u8f6e\u8f6e\u80ce\u89c4\u683c","value":"160\/60 R15"},{"id":"71","name":"\u524d\u8f6e\u5236\u52a8\u65b9\u5f0f",
"value":"\u789f\u5f0f"},{"id":"72","name":"\u540e\u8f6e\u5236\u52a8\u65b9\u5f0f","value":"\u789f\u5f0f"}]},
{"type":"\u5c3a\u5bf8\u91cd\u91cf","values":[{"id":"69","name":"\u8f74\u8ddd","value":"1591mm"},
{"id":"57","name":"\u6574\u8f66\u6574\u5907\u8d28\u91cf","value":"249kg"},{"id":"55","name":"\u5ea7\u9ad8","value":"800mm"},
{"id":"53","name":"\u8f66\u9ad8","value":"1378mm"},{"id":"51","name":"\u8f66\u957f","value":"2155mm"},
{"id":"52","name":"\u8f66\u5bbd","value":"877mm"}]},{"type":"\u4f20\u52a8\u7cfb\u7edf","values":[{"id":"50","name":"\u53d8\u901f\u7cfb\u7edf","value":"\u65e0\u7ea7\u53d8\u901f"}]}]}
"""