package github.cccxm.mydemo.android.effect.drag

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.support.v4.widget.ViewDragHelper
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import github.cccxm.mydemo.utils.color
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/6.
 */
class DragReleaseBackActivity : AppCompatActivity() {
    private val ui = DragReleaseBackUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.title = "回弹"
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

private class DragReleaseBackUI : AnkoComponent<DragReleaseBackActivity> {
    override fun createView(ui: AnkoContext<DragReleaseBackActivity>): View = with(ui) {
        with(DragReleaseBackLayout(ui.ctx)) {
            addView(with(View(ui.ctx)) {
                id = 0
                backgroundColor = color("#3388ff")
                layoutParams = LinearLayout.LayoutParams(dip(100), dip(100))
                this
            })
            this
        }
    }
}

private class DragReleaseBackLayout(context: Context) : RelativeLayout(context) {
    private val mOriginPoints = Point()

    private lateinit var mDragger: ViewDragHelper

    init {
        mDragger = ViewDragHelper.create(this, 1f, object : ViewDragHelper.Callback() {

            override fun tryCaptureView(child: View, pointerId: Int) = child.id == 0

            override fun clampViewPositionHorizontal(child: View?, left: Int, dx: Int) = left

            override fun clampViewPositionVertical(child: View?, top: Int, dy: Int) = top

            override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
                mDragger.settleCapturedViewAt(mOriginPoints.x, mOriginPoints.y)
                invalidate()
            }
        })
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return mDragger.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDragger.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (mDragger.continueSettling(true))
            invalidate()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)

        val view = findViewById<View>(0)
        mOriginPoints.x = view.left
        mOriginPoints.y = view.top
    }
}