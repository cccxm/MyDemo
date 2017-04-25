package github.cccxm.mydemo.android.effect.linkage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.simpleCardAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

/**
 * Created by cxm
 * on 2017/4/8.
 */
class PopupViewActivity
    : AppCompatActivity() {

    private val ui = PopupViewUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.title = "可拖拽布局"
        appBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

private class PopupViewUI
    : AnkoComponent<PopupViewActivity> {

    override fun createView(ui: AnkoContext<PopupViewActivity>): View = with(ui) {
        coordinatorLayout {
            view {
                backgroundColor = Color.TRANSPARENT
            }.lparams(matchParent, wrapContent) {
                behavior = PopupViewBehavior(context)
            }

            nestedScrollView {
                recyclerView {
                    layoutManager = LinearLayoutManager(context)
                    simpleCardAdapter {
                        for (i in 0..50)
                            item("Item $i")
                    }
                }
            }.lparams(matchParent, matchParent)
        }
    }
}

private class PopupViewBehavior(context: Context?, attrs: AttributeSet? = null)
    : CoordinatorLayout.Behavior<View>(context, attrs) {

    /**
     * 该方法用来确定事件源和消费者之间的关系，如果返回true，则该事件源发出的事件将传递给此消费者
     *
     * @param parent     CoordinatorLayout
     * @param child      该对象是被绑定对象，也是事件消费者对象
     * @param dependency 该对象是被依赖对象，也是事件生产者对象
     * @return true 绑定
     */
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        val b = dependency is NestedScrollView
        if (b) {
            val height = parent.height
            val childParams = child.layoutParams as CoordinatorLayout.LayoutParams
            childParams.height = height / 2
            child.layoutParams = childParams

            val dependencyParams = dependency.layoutParams as CoordinatorLayout.LayoutParams
            dependencyParams.topMargin = height / 2
            dependency.layoutParams = dependencyParams
        }
        return b
    }

}