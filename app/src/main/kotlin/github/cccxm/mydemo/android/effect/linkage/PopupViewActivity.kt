package github.cccxm.mydemo.android.effect.linkage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.logger
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
            }.lparams(matchParent, matchParent) {
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

//    /**
//     * 该方法用来确定事件源和消费者之间的关系，如果返回true，则该事件源发出的事件将传递给此消费者
//     *
//     * @param parent     CoordinatorLayout
//     * @param child      该对象是被绑定对象，也是事件消费者对象
//     * @param dependency 该对象是被依赖对象，也是事件生产者对象
//     * @return true 绑定
//     */
//    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
//        val b = dependency is NestedScrollView
//        if (b) {
//            val height = parent.height
//            val dependencyParams = dependency.layoutParams as CoordinatorLayout.LayoutParams
//            dependencyParams.topMargin = height / 2
//            dependency.layoutParams = dependencyParams
//        }
//        return b
//    }

    /**
     * 此方法用来监听布局中所有可滑动控件的滑动状态，与[layoutDependsOn]方法的绑定结果无关。
     * 该方法可以用来当作是与滑动相关的事件绑定方法。
     * 如果此方法返回false则表示不接受此系列滑动事件。
     *
     * @param directTargetChild 此次滑动事件发生的控件对应的[CoordinatorLayout]布局中的子控件，在这里代表的就是当前事件源[NestedScrollView]
     * @param target 真正发生滑动事件的控件
     * @param nestedScrollAxes 当前滑动状态
     */
    override fun onStartNestedScroll(parent: CoordinatorLayout,
                                     child: View,
                                     directTargetChild: View,
                                     target: View,
                                     nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    /**
     * 该函数表示当前滑动事件绑定成功，该方法在一组滑动序列中只会被调用一次。
     */
    override fun onNestedScrollAccepted(coordinatorLayout: CoordinatorLayout,
                                        child: View,
                                        directTargetChild: View,
                                        target: View,
                                        nestedScrollAxes: Int) {
    }

    /**
     * 该函数是在一次真实滑动之前回调的预处理函数，该函数会传入即将发生的滑动的移动位置，在这里我们可以按照我们
     * 自己的程序对滑动结果进行修改.
     *
     * @param dx 将要在x轴方向上进行的滑动
     * @param dy 将要在y轴方向上进行的滑动
     * @param consumed 这个数组的长度为2 其中的内容为x,y的真实移动距离
     */
    override fun onNestedPreScroll(parent: CoordinatorLayout,
                                   child: View,
                                   target: View,
                                   dx: Int,
                                   dy: Int,
                                   consumed: IntArray) {
        if (dy > 0 && dy + target.height > parent.height)
            consumed[1] = parent.height - target.height
    }

    /**
     * 该函数在[onNestedPreScroll]之后被调用，用来处理滑动事件
     *
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    override fun onNestedScroll(parent: CoordinatorLayout,
                                child: View,
                                target: View,
                                dxConsumed: Int,
                                dyConsumed: Int,
                                dxUnconsumed: Int,
                                dyUnconsumed: Int) {
        logger("dyConsumed:$dyConsumed -- dyUnconsumed:$dyUnconsumed")
        val dy = if (dyConsumed > 0) dyConsumed else if (dyUnconsumed < 0) dyUnconsumed else return
        val targetParams = target.layoutParams as CoordinatorLayout.LayoutParams
        targetParams.topMargin -= dy
        target.layoutParams = targetParams
    }
}