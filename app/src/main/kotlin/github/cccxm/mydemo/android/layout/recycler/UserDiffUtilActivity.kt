package github.cccxm.mydemo.android.layout.recycler

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.adapter.CommonDiffAdapter
import kotlinx.android.synthetic.main.activity_user_diff_util.*
import kotlinx.android.synthetic.main.item_use_diff.view.*
import org.jetbrains.anko.layoutInflater
import java.util.*

class UserDiffUtilActivity : AppCompatActivity() {

    private val messageArray = arrayOf("Hello World", "Fuck Java", "Java ++ 8", "Kotlin is Niubility")

    private val adapter by lazy { MyAdapter(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_diff_util)
        val initData = (0..100).mapTo(LinkedList()) { MyBean(it, messageArray[it % messageArray.size]) }
        mRecyclerView.adapter = adapter
        adapter.resetItems(initData)
    }

    private data class MyBean(val id: Int, var message: String) : CommonDiffAdapter.DiffBean<MyBean> {

        override fun isSameItem(newItem: MyBean?): Boolean = id == newItem?.id

        override fun isSameContent(newItem: MyBean?): Boolean = message == newItem?.message
    }

    private class MyHolder(view: View) : RecyclerView.ViewHolder(view)
    private class MyAdapter(private val context: Context) : CommonDiffAdapter<MyBean, MyHolder>() {
        override fun onBindViewHolder(holder: MyHolder, item: MyBean) {
            holder.itemView.mMessageText.text = item.message
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder =
                MyHolder(context.layoutInflater.inflate(R.layout.item_use_diff, null))
    }
}
