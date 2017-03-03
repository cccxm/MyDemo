package github.cccxm.mydemo.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.px2sp
import org.jetbrains.anko.wrapContent
import java.util.*

/**
 * Created by cxm
 * c on 2017/3/2.
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