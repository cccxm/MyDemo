package github.cccxm.mydemo.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import github.cccxm.mydemo.R
import kotlinx.android.synthetic.main.item_barrage.view.*
import org.jetbrains.anko.*
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Created by cxm
 * on 2017/5/2.
 */
class BarrageLayout : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * setRate()设置发射间隔，取值范围100-8000，单位毫秒
     */
    var rate = 500L
        set(value) {
            if (value in 100 until 8000) {
                field = value
            }
        }
    /**
     * setSpeed()设置弹幕在屏幕上停留的时间，取值范围1000-6400，单位ms
     */
    var speed = 10000L
        set(value) {
            if (value in 1000 until 64000) {
                field = value
            }
        }
    private val maxLine by lazy { height / dip(40) }
    private val mPositions by lazy { IntArray(maxLine) { it * (height - 50) / maxLine + 50 } }
    private val mVPosition by lazy { IntArray(maxLine) { 0 } }
    private val mItems = LinkedList<BarrageItem>()
    private val mTempItems = LinkedList<BarrageItem>()
    private var isStart = false
    private var mSubscribe: Subscription? = null
    private var mOrigChildCount = 0

    /*
     * 获取空闲的行数，没有时候返回-1
     */
    private fun getIndex(): Int {
        for (i in mVPosition.indices) mVPosition[i]--
        return mVPosition.indexOfFirst { it <= 0 }
    }

    /**
     * 向其中添加一条弹幕，该弹幕将会立即显示在屏幕上（如果有位置的话）
     */
    fun addItem(item: BarrageItem) {
        mTempItems.add(item)
        mItems.add(item)
    }

    /**
     * 向其中添加一系列的弹幕，这些弹幕将会排在末尾，并在下次循环时显示
     */
    fun addItems(items: List<BarrageItem>) {
        mItems.addAll(0, items)
    }

    /**
     * 调用该方法开始滚动显示弹幕，一旦调用此方法，务必在合适的位置调用[onDestroy]方法来停止弹幕播放，以免造成内存泄漏
     */
    fun onCreate() {
        mOrigChildCount = childCount
        onDestroy()
        isStart = true
        val items = LinkedList<BarrageItem>()
        mSubscribe = Observable.interval(rate, TimeUnit.MILLISECONDS)
                .map {
                    val pos = getIndex()
                    var barrage: BarrageIndex? = null
                    if (pos != -1) {
                        var item: BarrageItem? = null
                        if (mTempItems.isNotEmpty()) { //有刚刚发送的消息
                            item = mTempItems.remove()
                        } else if (items.isNotEmpty()) {
                            item = items.remove()
                        } else { //一轮数据发射完毕
                            val max = mVPosition.max() ?: 0 + 10//两轮数据之中间隔10个中断
                            for (i in mVPosition.indices) mVPosition[i] = max
                            items.addAll(mItems)
                        }
                        if (item != null) {
                            mVPosition[pos] = Int.MAX_VALUE
                            barrage = BarrageIndex(item, pos)
                        }
                    }
                    barrage
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    if (isStart && res != null) {
                        val barrageView = generateBarrageView(res.item)
                        val lot = computeLot(barrageView.mItemText, res.item.getText())
                        mVPosition[res.pos] = lot
                        showBarrageItem(barrageView, res.pos)
                    }
                }, { }, { })
    }

    private fun computeLot(textView: TextView, text: String): Int {
        val length = Math.min(textView.paint.measureText(text) + dip(40), width.toFloat())
        return ((length * speed) / (2 * width * rate)).toInt() + 1
    }

    /**
     * 调用该方法销毁此View，如果没有调用此方法则可能会造成内存泄漏
     */
    fun onDestroy() {
        isStart = false
        val sub = mSubscribe ?: return
        if (!sub.isUnsubscribed) sub.unsubscribe()
    }

    private fun showBarrageItem(view: View, pos: Int) {
        checkChildCount()
        val params = LayoutParams(wrapContent, wrapContent)
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        params.topMargin = mPositions[pos]
        addView(view, params)
        startAnimation(view, width.toFloat(), width.toFloat())
    }

    private fun checkChildCount() {
        if (childCount - mOrigChildCount > 30) {
            (mOrigChildCount until childCount).map(this::getChildAt)
                    .forEach {
                        it.clearAnimation()
                        removeView(it)
                    }
        }
    }

    @SuppressLint("InflateParams") private fun generateBarrageView(item: BarrageItem): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_barrage, null)
        if (item.getBackgroundResource() != -1)
            view.backgroundResource = item.getBackgroundResource()
        else view.backgroundColor = item.getBackgroundColor()
        view.onClick { item.onClick() }
        if (item.avatarEnable()) {
            view.mItemImage.visibility = View.VISIBLE
            item.inflateAvatar(view.mItemImage)
        } else view.mItemImage.visibility = View.GONE
        view.mItemText.text = item.getText()
        view.mItemText.textColor = item.getTextColor()
        return view
    }

    /**
     * @param start 动画的启动位置
     * @param barrageWidth 弹幕布局的宽度，用来当作弹幕结束的位置
     */
    private fun startAnimation(view: View, start: Float, barrageWidth: Float) {
        val animator = ObjectAnimator.ofFloat(view, "translationX", start, -barrageWidth).setDuration(speed)
        animator.setInterpolator { it }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                removeView(view)
            }
        })
        animator.start()
    }

    private data class BarrageIndex(val item: BarrageItem, val pos: Int)
}

/**
 * 弹幕Item接口
 */
interface BarrageItem {
    fun getTextColor(): Int
    fun getBackgroundColor(): Int
    fun getBackgroundResource(): Int
    fun avatarEnable(): Boolean
    fun inflateAvatar(avatar: ImageView)
    fun onClick()
    fun getText(): String
}

/**
 * 弹幕Item的基本实现
 */
abstract class AbsBarrageItem : BarrageItem {
    override fun getTextColor() = Color.WHITE
    override fun getBackgroundColor() = Color.TRANSPARENT
    override fun getBackgroundResource(): Int = -1
    override fun avatarEnable() = false
    override fun inflateAvatar(avatar: ImageView) {}
    override fun onClick() {}
}