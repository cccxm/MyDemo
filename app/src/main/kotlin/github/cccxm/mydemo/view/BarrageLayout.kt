package github.cccxm.mydemo.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.*
import java.util.*


/**
 * Created by cxm
 * on 2017/5/2.
 */
class BarrageLayout : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 发射间隔
     */
    var rate = 1000L
    /**
     * 一个弹幕在屏幕上停留的时间，单位ms
     */
    var speed = 8000L
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
    private var mSubscribe: Disposable? = null
    private var mOrignChildCount = 0

    /**
     * 获取空闲的行数，没有时候返回-1
     */
    private fun getIndex(): Int {
        for (i in mVPosition.indices) mVPosition[i]--
        return mVPosition.indexOfFirst { it <= 0 }
    }

    fun addItem(item: BarrageItem) {
        mTempItems.add(item)
        mItems.add(item)
    }

    fun addItems(items: List<BarrageItem>) {
        mItems.addAll(0, items)
    }

    fun onCreate() {
        mOrignChildCount = childCount
        onDestroy()
        isStart = true
        mSubscribe = Observable.create<BarrageIndex> { subscribe ->
            var loop = true
            while (loop) {
                val items = LinkedList<BarrageItem>()
                items.addAll(mItems)
                items.forEach {
                    if (!isStart) {
                        loop = false
                        return@forEach
                    }
                    var pos = getIndex()
                    while (pos == -1) {
                        try {
                            Thread.sleep(buildTime())
                            pos = getIndex()
                        } catch (e: InterruptedException) {
                            loop = false
                            return@forEach
                        }
                    }
                    if (mTempItems.isNotEmpty()) {
                        while (mTempItems.isNotEmpty()) {
                            val remove = mTempItems.remove()
                            mVPosition[pos] = (remove.getText().length / 3) + 3
                            subscribe.onNext(BarrageIndex(remove, pos))
                            pos = getIndex()
                            while (pos == -1) {
                                try {
                                    Thread.sleep(buildTime())
                                    pos = getIndex()
                                } catch (e: InterruptedException) {
                                    loop = false
                                    return@forEach
                                }
                            }
                        }
                    }
                    mVPosition[pos] = (it.getText().length / 3) + 3
                    subscribe.onNext(BarrageIndex(it, pos))
                    try {
                        Thread.sleep(buildTime())
                    } catch (e: InterruptedException) {
                        loop = false
                        return@forEach
                    }
                }
                try {
                    Thread.sleep(speed / 2)
                    for (i in mVPosition.indices) mVPosition[i] = 0
                } catch (e: InterruptedException) {
                    loop = false
                }
            }
            subscribe.onComplete()
        }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ if (isStart) showBarrageItem(generateBarrageView(it.item), it.pos) }, { }, { })
    }

    private fun buildTime(): Long {
        val r = speed / (maxLine * 5)
        return if (rate > 0) r else (Math.random() * r).toLong() + r
    }

    fun onDestroy() {
        isStart = false
        val sub = mSubscribe ?: return
        if (!sub.isDisposed) sub.dispose()
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
        if (childCount - mOrignChildCount > 30) {
            (mOrignChildCount until childCount).map(this::getChildAt)
                    .forEach {
                        it.clearAnimation()
                        removeView(it)
                    }
        }
    }

    @SuppressLint("InflateParams") private fun generateBarrageView(item: BarrageItem): View {
        return with(context) {
            linearLayout {
                linearLayout {
                    gravity = Gravity.CENTER_VERTICAL
                    padding = dip(5)
                    if (item.getBackgroundResource() != -1)
                        backgroundResource = item.getBackgroundResource()
                    else backgroundColor = item.getBackgroundColor()
                    onClick { item.onClick() }
                    if (item.avatarEnable()) {
                        imageView {
                            item.inflateAvatar(this)
                        }.lparams(dip(20), dip(20))
                    }

                    textView(item.getText()) {
                        maxLines = 1
                        textColor = item.getTextColor()
                    }.lparams(wrapContent, wrapContent) {
                        leftMargin = dip(5)
                        rightMargin = dip(3)
                    }
                }.lparams(wrapContent, wrapContent)
            }
        }
    }

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

interface BarrageItem {
    fun getTextColor(): Int
    fun getBackgroundColor(): Int
    fun getBackgroundResource(): Int
    fun avatarEnable(): Boolean
    fun inflateAvatar(avatar: ImageView)
    fun onClick()
    fun getText(): String
}

abstract class AbsBarrageItem : BarrageItem {
    override fun getTextColor() = Color.WHITE
    override fun getBackgroundColor() = Color.TRANSPARENT
    override fun getBackgroundResource(): Int = -1
    override fun avatarEnable() = false
    override fun inflateAvatar(avatar: ImageView) {}
    override fun onClick() {}
}