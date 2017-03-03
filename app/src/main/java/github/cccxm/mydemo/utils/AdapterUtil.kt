package github.cccxm.mydemo.utils

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.px2sp
import org.jetbrains.anko.textColor
import org.jetbrains.anko.wrapContent
import java.util.*

/**
 * Created by cxm
 * on 2017/3/3.
 */

abstract class SimpleViewHolder<T>(val view: View) : RecyclerView.ViewHolder(view) {
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
        object : SimpleViewHolder<String>(getTextView(context)) {
            override fun parse(item: String) {
                (view as TextView).text = item
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