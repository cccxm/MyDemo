package github.cccxm.mydemo.android.effect.drag

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.Scroller
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.logger
import github.cccxm.mydemo.utils.simpleStringAdapter
import kotlinx.android.synthetic.main.activity_effect_drag_popup.*
import kotlinx.android.synthetic.main.layout_effect_drag_popup.*
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/6.
 */
class PopupDragLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_effect_drag_popup)

        mShowBtn.onClick {
            if (mDrawView.isShow)
                mDrawView.dismiss()
            else mDrawView.show()
        }

        mDrawCloseBtn.onClick {
            mDrawView.dismiss()
        }

        val appBar = supportActionBar ?: return
        appBar.title = "可拖动"
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

class PopupDragLayoutView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private enum class PositionType {DISMISS, MID, TOP }

    private var mType = PositionType.DISMISS

    private val mScroller = Scroller(context)

    private val mScreenHeight = context.displayMetrics.heightPixels //屏幕高度
    private val mMidHeight = mScreenHeight * 8 / 10 //初次显示高度，高于此高就度完全显示
    private val mTopHeight = mScreenHeight - mMidHeight
    private val mMoveCloseRange = -mScreenHeight until -(mScreenHeight * 5 / 10) //低于5/10就隐藏
    private val mMoveMidRange = -(mScreenHeight * 5 / 10) until -(mScreenHeight * 2 / 10) // 5/10-8/10之间就移动到中间
    private val mMoveTopRange = -(mScreenHeight * 2 / 10) until 0 //7/10以上全部显示

    private var downY = 0 //点击位置的高度
    private var moveY = 0 //拖动时位置的高度
    private var distanceY = 0 // 垂直方向拖动的距离
    private var upY = 0 //松手时位置的高度

    var isMoving = false //是否正在移动
        private set
    val isShow: Boolean get() = mType != PositionType.DISMISS //当前的显示状态

    private val mDuration = 500 //动画时长

    init {
        descendantFocusability = FOCUS_AFTER_DESCENDANTS
        isFocusable = true
        backgroundColor = Color.TRANSPARENT
        val view = context.layoutInflater.inflate(R.layout.layout_effect_drag_popup, null)
        with(view.findViewById<RecyclerView>(R.id.mRecyclerView)) {
            layoutManager = LinearLayoutManager(context)
            simpleStringAdapter {
                for (i in 0..50) item("item $i")
            }
        }
        val params = LayoutParams(matchParent, mScreenHeight)
        addView(view, params)
        scrollTo(0, mScreenHeight)
    }

    /**
     * 拖动动画
     * @param startY
     * @param dy  移动到某点的Y坐标距离
     */
    fun startMoveAnim(startY: Int, dy: Int) {
        isMoving = true
        mScroller.startScroll(0, startY, 0, dy, mDuration)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downY = y
                if (isShow) return true //正在显示时拦截事件
            }
            MotionEvent.ACTION_MOVE -> {
                moveY = y
                distanceY = moveY - downY
                if (distanceY > 0) {    //向下拖动
                    when (mType) {
                        PositionType.MID -> {
                            scrollTo(0, -(mTopHeight + distanceY))
                        }
                        PositionType.TOP -> {
                            scrollTo(0, -distanceY)
                        }
                        PositionType.DISMISS -> {
                        }
                    }
                } else {    //向上拖动
                    when (mType) {
                        PositionType.MID -> {
                            scrollTo(0, -(mTopHeight + distanceY))
                        }
                        PositionType.TOP, PositionType.DISMISS -> {
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                upY = y
                when (scrollY) {
                    in mMoveCloseRange -> {
                        startMoveAnim(scrollY, -(height - scrollY))
                        mType = PositionType.DISMISS
                    }
                    in mMoveMidRange -> {
                        startMoveAnim(scrollY, mMidHeight - (mScreenHeight + scrollY))
                        mType = PositionType.MID
                    }
                    in mMoveTopRange -> {
                        startMoveAnim(scrollY, -scrollY)
                        mType = PositionType.TOP
                    }
                }
            }
            MotionEvent.ACTION_OUTSIDE -> {
                logger("ACTION_OUTSIDE")
            }
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            postInvalidate()
            isMoving = true
        } else {
            isMoving = false
        }
        super.computeScroll()
    }

    fun show() {
        if (!isShow && !isMoving) {
            logger("show start:-$mScreenHeight to $mMidHeight")
            startMoveAnim(-mScreenHeight, mMidHeight)
            mType = PositionType.MID
        }
    }

    fun dismiss() {
        if (isShow && !isMoving) {
            logger("dismiss")
            when (mType) {
                PositionType.MID -> startMoveAnim(0, -mMidHeight)
                PositionType.TOP -> startMoveAnim(0, -mScreenHeight)
                PositionType.DISMISS -> {
                }
            }
            mType = PositionType.DISMISS
        }
    }
}