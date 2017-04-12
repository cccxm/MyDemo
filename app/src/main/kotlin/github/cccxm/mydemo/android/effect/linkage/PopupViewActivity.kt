package github.cccxm.mydemo.android.effect.linkage

import android.content.Context
import android.os.Bundle
import android.support.v4.view.NestedScrollingParent
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.Scroller

/**
 * Created by cxm
 * on 2017/4/8.
 */
class PopupViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

class PopupLayout : LinearLayout, NestedScrollingParent {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mScroller = Scroller(context)//控制View滑动

    /**
     * 该方法决定是否接受子控件的滑动参数
     */
    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return (nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0 //接受所有垂直滑动的子控件状态
    }

    /**
     * 如果[onStartNestedScroll]方法返回true，则此方法会在子控件滑动前接受子控件的滑动参数
     *
     * @param target 被检测滑动状态的子控件
     * @param dx 预计该控件在x轴方向的滑动距离
     * @param dy 预计该控件在y轴方向的滑动距离
     * @param consumed 实际消费距离的接收数组，在其中指定需要[target]滑动的实际距离 {x,y}
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        if (dy > 0) { //向上滑动
            consumed[1] = dy //消费滑动
        } else {
            if (ViewCompat.canScrollVertically(target, -1)) {//当前子View内部是否还可以向下滑动，如果不可以，则Parent消费此事件
                consumed[1] = dy
            }
        }
    }

    /**
     * @param velocityX 水平方向滑动速度（每秒移动的像素数）
     * @param velocityY 水平方向滑动速度（每秒移动的像素数）
     * @param consumed 子控件是否消费了此次事件
     * @return 父控件是否消费此次事件，如果返回true则子控件将不再接收到事件
     */
    override fun onNestedFling(target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        if (scrollY >= 0) return false
        mScroller.fling(0, scrollY, 0, velocityY.toInt(), 0, 0, 0, 0)
        invalidate()
        return true
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.currY)
            invalidate()
        }
    }
}