package github.cccxm.mydemo.android.effect.drag

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.ViewDragHelper
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import github.cccxm.mydemo.utils.color
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/6.
 */
class DragViewActivity : AppCompatActivity() {
    private val ui = DragViewUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.title = "简单View拖拽"
        appBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

private class DragViewUI : AnkoComponent<DragViewActivity> {
    override fun createView(ui: AnkoContext<DragViewActivity>): View = with(ui) {
        with(DragViewLayout(ui.ctx)) {
            orientation = LinearLayout.VERTICAL
            addView(with(TextView(ui.ctx)) {
                id = 0
                text = "能够拖动的View"
                textColor = Color.WHITE
                gravity = Gravity.CENTER
                backgroundColor = color("#3388ff")
                layoutParams = LinearLayout.LayoutParams(dip(200), dip(200))
                this
            })
            addView(with(TextView(ui.ctx)) {
                id = 1
                text = "不能能够拖动的View"
                textColor = Color.WHITE
                gravity = Gravity.CENTER
                backgroundColor = color("#33ff66")
                layoutParams = LinearLayout.LayoutParams(dip(200), dip(200))
                this
            })
            this
        }
    }
}

private class DragViewLayout(context: Context) : LinearLayout(context) {

    // 第一个参数用来分发事件的ViewGroup，第二个参数用来指定灵敏度 值越大刷新间隔越小
    private val mDragger = ViewDragHelper.create(this, 1f, object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            //该方法用来确定是否捕获该View
            return child.id == 0
        }

        /**
         * 控制水平移动边界
         *
         * @param left 即将移动到的边界（View的左侧位置）
         * @return 经过处理后希望移动到的边界
         */
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val leftBound = this@DragViewLayout.paddingLeft
            val rightBound = this@DragViewLayout.width - child.width - leftBound

            val newLeft = minOf(maxOf(left, leftBound), rightBound)
            return newLeft
        }

        /**
         * 不控制垂直移动边界
         *
         * @param top 即将移动到的边界（View的顶部位置）
         * @return 经过处理后希望移动到的边界
         */
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }
    })

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mDragger.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDragger.processTouchEvent(event)
        return true
    }
}