package github.cccxm.mydemo.utils

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import github.cccxm.mydemo.R
import org.jetbrains.anko.*
import java.util.*

/**
 * Created by cxm
 * on 2017/3/18.
 */

open class _TagFlowLayout(context: Context?, attrs: AttributeSet?, defStyle: Int) : TagFlowLayout(context, attrs, defStyle) {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null, 0)

    var maxSelect: Int = 0
        set(value) {
            setMaxSelectCount(value)
            field = value
        }

    fun lparams(width: Int, height: Int, init: (ViewGroup.MarginLayoutParams.() -> Unit)? = null): ViewGroup.MarginLayoutParams {
        val params = ViewGroup.MarginLayoutParams(width, height)
        init?.invoke(params)
        layoutParams = params
        return params
    }
}

class _TagSimpleStringAdapter {
    private val tags = LinkedList<String>()

    fun tags(vararg ts: String) {
        tags.addAll(ts)
    }

    fun tag(t: String) {
        tags.add(t)
    }

    fun getAdapter(): TagAdapter<String> = object : TagAdapter<String>(tags) {
        override fun getView(parent: FlowLayout, position: Int, t: String): View = with(parent.context) {
            linearLayout {
                lparams(wrapContent, wrapContent) {
                    padding = dip(5)
                    margin = dip(3)
                    gravity = Gravity.CENTER
                    backgroundResource = R.drawable.radius_hollow_blue
                }
                textView(t)
            }
        }
    }
}

fun _TagFlowLayout.simpleStringAdapter(init: _TagSimpleStringAdapter.() -> Unit): TagAdapter<String> {
    val tssa = _TagSimpleStringAdapter()
    tssa.init()
    val mAdapter = tssa.getAdapter()
    adapter = mAdapter
    return mAdapter
}

fun <T : AppCompatActivity> AnkoContext<T>.tagFlowLayout(init: _TagFlowLayout.() -> Unit): TagFlowLayout {
    val layout = _TagFlowLayout(ctx)
    layout.init()
    return layout
}

fun ViewGroup.tagFlowLayout(init: _TagFlowLayout.() -> Unit): TagFlowLayout {
    return context.tagFlowLayout(init)
}

fun Context.tagFlowLayout(init: _TagFlowLayout.() -> Unit): TagFlowLayout {
    val layout = _TagFlowLayout(this)
    layout.init()
    return layout
}