package github.cccxm.mydemo.utils

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import java.util.*

/**
 * Created by cxm
 * on 2017/3/3.
 */

class Item<T>(val value: T) {
    var onClickListener: ((View) -> Unit)? = null
}

class Group<out R, T>(val value: R) {
    val items = LinkedList<Item<T>>()
}

fun <R, T> Group<R, T>.item(value: T, listener: ((View) -> Unit)? = null): T {
    items.add(Item(value).apply { onClickListener = listener })
    return value
}

fun <R, T> GroupAdapter<R, T>.group(value: R, init: Group<R, T>.() -> Unit): Group<R, T> {
    val mGroup = Group<R, T>(value)
    mGroup.init()
    datas.add(mGroup)
    return mGroup
}

class SimpleStringItem(val item: String, val listener: ((View) -> Unit)?)

fun ExpandableListView.simpleStringGroupAdapter(init: SimpleStringGroupAdapter.() -> Unit): SimpleStringGroupAdapter {
    val adapter = SimpleStringGroupAdapter(context)
    setAdapter(adapter)
    adapter.init()
    adapter.notifyDataSetChanged()
    return adapter
}

abstract class GroupAdapter<R, T> : BaseExpandableListAdapter() {
    val datas = LinkedList<Group<R, T>>()

    override fun getGroup(groupPosition: Int): Group<R, T> {
        return datas[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        return groupView(datas[groupPosition], isExpanded, convertView)
    }

    abstract fun groupView(group: Group<R, T>, isExpanded: Boolean, convertView: View?): View

    override fun getChildrenCount(groupPosition: Int): Int {
        return datas[groupPosition].items.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Item<T> {
        return datas[groupPosition].items[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        return childView(getChild(groupPosition, childPosition), isLastChild, convertView)
    }

    abstract fun childView(child: Item<T>, isLastChild: Boolean, convertView: View?): View

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return datas.size
    }
}

class SimpleStringGroupAdapter(private val context: Context) : GroupAdapter<String, String>() {
    override fun groupView(group: Group<String, String>, isExpanded: Boolean, convertView: View?): View {
        val cv = getView(context, convertView)
        cv.text = group.value
        cv.setBackgroundColor(Color.LTGRAY)
        return cv
    }

    override fun childView(child: Item<String>, isLastChild: Boolean, convertView: View?): View {
        val cv = getView(context, convertView)
        cv.text = child.value
        cv.setOnClickListener(child.onClickListener)
        return cv
    }

}

fun <T> ItemAdapter<T>.item(value: T, listener: ((View) -> Unit)? = null) {
    val it = Item(value)
    it.onClickListener = listener
    datas.add(it)
}

fun ListView.simpleStringItemAdapter(init: SimpleStringItemAdapter.() -> Unit): SimpleStringItemAdapter {
    val adapter = SimpleStringItemAdapter(context)
    setAdapter(adapter)
    adapter.init()
    adapter.notifyDataSetChanged()
    return adapter
}

abstract class ItemAdapter<T> : BaseAdapter() {
    val datas = LinkedList<Item<T>>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(datas[position], convertView)
    }

    abstract fun getView(item: Item<T>, convertView: View?): View

    override fun getItem(position: Int): Item<T> {
        return datas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return datas.size
    }
}

class SimpleStringItemAdapter(private val context: Context) : ItemAdapter<String>() {
    override fun getView(item: Item<String>, convertView: View?): View {
        val cv = getView(context, convertView)
        cv.text = item.value
        cv.setOnClickListener(item.onClickListener)
        return cv
    }
}

private fun getView(context: Context, convertView: View?): TextView {
    return (convertView ?: with(TextView(context)) {
        layoutParams = AbsListView.LayoutParams(matchParent, wrapContent)
        setPadding(70, 30, 0, 30)
        textSize = px2sp(35)
        this
    }) as TextView
}

abstract class SimpleViewHolder<in T>(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun parse(item: T)
}

open class _SimpleAdapter<T> : RecyclerView.Adapter<SimpleViewHolder<T>>() {
    private val mData = LinkedList<T>()
    private var mParse: (() -> SimpleViewHolder<T>)? = null
    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SimpleViewHolder<T> {
        return mParse!!.invoke()
    }

    override fun onBindViewHolder(holder: SimpleViewHolder<T>?, position: Int) {
        holder?.parse(mData[position])
    }

    fun item(item: T) {
        mData.add(item)
    }

    fun inflate(parse: () -> SimpleViewHolder<T>) {
        mParse = parse
    }
}

fun <T> RecyclerView.simpleAdapter(init: _SimpleAdapter<T>.() -> Unit): _SimpleAdapter<T> {
    val apt = _SimpleAdapter<T>()
    adapter = apt
    apt.init()
    apt.notifyDataSetChanged()
    return apt
}

fun RecyclerView.simpleCardAdapter(init: _SimpleAdapter<String>.() -> Unit): _SimpleAdapter<String> {
    val apt = _SimpleAdapter<String>()
    adapter = apt
    apt.init()
    apt.inflate {
        object : SimpleViewHolder<String>(with(context) {
            linearLayout {
                lparams {
                    width = matchParent
                    height = dip(70)
                    topMargin = dip(10)
                    leftMargin = dip(20)
                    rightMargin = dip(20)
                    gravity = Gravity.CENTER
                }
                cardView {
                    lparams(width = matchParent, height = dip(70)) {
                        margin = dip(20)
                    }
                    cardElevation = px2dip(14)
                    setCardBackgroundColor(Color.WHITE)
                    radius = px2dip(10)
                    gravity = Gravity.CENTER
                    textView {
                        lparams(matchParent, matchParent)
                        gravity = Gravity.CENTER
                        id = 0x1234
                        textSize = px2sp(40)
                        textColor = Color.BLACK
                    }
                }
            }
        }) {
            override fun parse(item: String) {
                (view.findViewById(0x1234) as TextView).text = item
            }
        }
    }
    apt.notifyDataSetChanged()
    return apt
}

fun RecyclerView.simpleStringAdapter(init: _SimpleAdapter<String>.() -> Unit): _SimpleAdapter<String> {
    val apt = _SimpleAdapter<String>()
    adapter = apt
    apt.init()
    apt.inflate {
        object : SimpleViewHolder<String>(getTextView(context)) {
            override fun parse(item: String) {
                (view as TextView).text = item
            }
        }
    }
    apt.notifyDataSetChanged()
    return apt
}

fun getTextView(context: Context): TextView {
    return with(TextView(context)) {
        layoutParams = AbsListView.LayoutParams(matchParent, wrapContent)
        setPadding(70, 30, 0, 30)
        textSize = px2sp(35)
        textColor = Color.BLACK
        this
    }
}

abstract class CommonAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    private val items: MutableList<T> = LinkedList()

    fun setItems(items: List<T>) {
        if (this.items != items) {
            this.items.clear()
            this.items.addAll(items)
        }
        notifyDataSetChanged()
    }

    fun getItems(): MutableList<T> = items

    fun addItems(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}