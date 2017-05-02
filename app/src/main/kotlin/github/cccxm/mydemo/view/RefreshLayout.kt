package github.cccxm.mydemo.view

import android.content.Context
import android.os.Bundle
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import github.cccxm.mydemo.utils.logger
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.wrapContent

/**
 * Created by cxm
 * on 2017/4/28.
 */
class RefreshLayout : LinearLayout, NestedScrollingParent {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        orientation = VERTICAL
    }

    var refreshView: RefreshView? = null
        set(value) {
            if (field == null) {
                value ?: return
                val initLayout = LinearLayout.LayoutParams(matchParent, wrapContent)
                addView(value.freshView, 0, initLayout)
                addView(value.loadingView, childCount, initLayout)
                field = value
            }
        }
    private val mMaxHeight = dip(120)
    private var mCurrentHeight = 0

    override fun onNestedScrollAccepted(child: View?,
                                        target: View?,
                                        axes: Int) {
        logger("onNestedScrollAccepted----child:$child -- target:$target -- axes:$axes")
        super.onNestedScrollAccepted(child, target, axes)
    }

    override fun onNestedPrePerformAccessibilityAction(target: View?,
                                                       action: Int,
                                                       args: Bundle?): Boolean {
        logger("onNestedPrePerformAccessibilityAction----target:$target -- action:$action -- args:$args")
        return super.onNestedPrePerformAccessibilityAction(target, action, args)
    }

    /**
     * 1，是否处理滑动事件
     */
    override fun onStartNestedScroll(child: View,
                                     target: View,
                                     nestedScrollAxes: Int)
            : Boolean {
        return refreshView != null && (nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    /**
     * 2，滑动前的准备工作
     */
    override fun onNestedPreScroll(target: View,
                                   dx: Int,
                                   dy: Int,
                                   consumed: IntArray) {
        logger("onNestedPreScroll----target:$target -- dx:$dx -- dy:$dy ")
//        if (mCurrentHeight != 0) consumed[1] = dy
    }

    /**
     * 3，开始滑动
     */
    override fun onNestedScroll(target: View,
                                dxConsumed: Int,
                                dyConsumed: Int,
                                dxUnconsumed: Int,
                                dyUnconsumed: Int) {
        logger("onNestedScroll---- dxConsumed:$dxConsumed --- dyConsumed:$dyConsumed --- dxUnconsumed:$dxUnconsumed --- dyUnconsumed:$dyUnconsumed")
//        val refresh = refreshView ?: return
//        mCurrentHeight += dyUnconsumed
//        if (mCurrentHeight > 0 && dyConsumed < 0) mCurrentHeight += dyConsumed
//        if (mCurrentHeight < 0 && dyConsumed > 0) mCurrentHeight += dyConsumed
//        val layoutParams = LinearLayout.LayoutParams(matchParent, Math.abs(mCurrentHeight))
//        refresh.freshView.layoutParams = layoutParams
//        if (dyUnconsumed > 0) { //上拉加载
//
//        } else { //下拉刷新
//
//        }
    }

    override fun onNestedPreFling(target: View?,
                                  velocityX: Float,
                                  velocityY: Float)
            : Boolean {
        logger("onNestedPreFling----target:$target -- velocityX:$velocityX -- velocityY:$velocityY")
        return super.onNestedPreFling(target, velocityX, velocityY)
    }

    override fun onNestedFling(target: View?,
                               velocityX: Float,
                               velocityY: Float,
                               consumed: Boolean)
            : Boolean {
        logger("onNestedFling----target:$target -- velocityX:$velocityX -- velocityY:$velocityY  -- consumed:$consumed")
        return super.onNestedFling(target, velocityX, velocityY, consumed)
    }

    override fun onStopNestedScroll(target: View?) {
        logger("onStopNestedScroll----target:$target")
        super.onStopNestedScroll(target)
    }
}

interface RefreshView {
    val freshView: View
    val loadingView: View

    /**
     * 正在下拉
     */
    fun onDrop(view: View, progress: Int)

    /**
     * 没有数据
     */
    fun onEmpty(view: View)

    /**
     * 数据加载错误
     */
    fun onError(view: View, error: Throwable)

    /**
     * 开始动画
     */
    fun onStartAnimation(view: View)

    /**
     * 结束动画
     */
    fun onStopAnimation(view: View)

    /**
     * 下拉刷新
     */
    fun onRefresh()

    /**
     * 上滑加载
     */
    fun onLoad()

}