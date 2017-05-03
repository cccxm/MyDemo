package github.cccxm.mydemo.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
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
     * 一个弹幕在屏幕上停留的时间，单位ms
     */
    var mDuration = 300L
    var speed = 10000L
        set(value) {
            if (value in 1000 until 64000) {
                field = value
                mDuration = field / 5
            }
        }
    var maxLine = 10
        set(value) {
            if (value in 1..256) field = value
        }

    private val mItems = LinkedList<BarrageItem>()
    private var isStart = false
    private var mSubscribe: Disposable? = null

    fun prepare(items: List<BarrageItem>) {
        mItems.clear()
        mItems.addAll(items)
    }

    fun addItems(items: List<BarrageItem>) {
        onStop()
        mItems.addAll(0, items)
        onStart()
    }

    fun onStart() {
        onStop()
        isStart = true
        mSubscribe = Observable.create<BarrageItem> { subscribe ->
            val items = LinkedList<BarrageItem>()
            items.addAll(mItems)
            var loop = true
            while (loop) {
                items.forEach {
                    if (!isStart) {
                        loop = false
                        return@forEach
                    }

                    subscribe.onNext(it)
                    try {
                        Thread.sleep(mDuration)
                    } catch (e: InterruptedException) {
                        loop = false
                        return@forEach
                    }
                }
            }
            subscribe.onComplete()
        }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (isStart)
                        showBarrageItem(generateBarrageView(it))
                }, { }, { })
    }

    fun onStop() {
        isStart = false
        val sub = mSubscribe ?: return
        if (!sub.isDisposed) sub.dispose()
    }

    private fun showBarrageItem(view: View) {
        val start = right - left - paddingLeft
        val params = LayoutParams(wrapContent, wrapContent)
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        params.topMargin = (Math.random() * height - 20).toInt()
        addView(view, params)
        val anim = generateTranslateAnim(start.toFloat(), width.toFloat())
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                view.clearAnimation()
                removeView(view)
            }

            override fun onAnimationStart(animation: Animation?) {}
        })
        view.startAnimation(anim)
    }

    private fun generateBarrageView(item: BarrageItem): View = with(context) {
        linearLayout {
            if (item.getBackgroundResource() != -1)
                backgroundResource = item.getBackgroundResource()
            else backgroundColor = item.getBackgroundColor()
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            onClick { item.onBarrageClick() }

            if (item.avatarEnable()) {
                imageView {
                    item.inflateAvatar(this)
                    onClick { item.onAvatarClick() }
                }.lparams(wrapContent, matchParent) {
                    padding = dip(3)
                    rightMargin = dip(3)
                }
            }

            textView(item.getText()) {
                textColor = item.getTextColor()
                textSize = item.getTextSize()
                onClick { item.onTextClick() }
            }.lparams(matchParent, wrapContent) {
                rightMargin = dip(5)
            }
        }
    }

    private fun generateTranslateAnim(start: Float, barrageWidth: Float): TranslateAnimation {
        val anim = TranslateAnimation(start, -barrageWidth, 0f, 0f)
        anim.duration = speed
//        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.fillAfter = true
        return anim
    }

//    fun getTextSize(tv: TextView, text: String): Rect {
//        val bounds = Rect()
//        val paint = tv.paint
//        paint.getTextBounds(text, 0, text.length, bounds)
//        return bounds
//    }
}

abstract class BarrageItem {
    open fun getTextColor() = Color.WHITE
    open fun getTextSize() = 16f
    open fun getBackgroundColor() = Color.TRANSPARENT
    open fun getBackgroundResource(): Int = -1
    open fun avatarEnable() = false
    open fun inflateAvatar(avatar: ImageView) {}
    open fun onBarrageClick() {}
    open fun onAvatarClick() {}
    open fun onTextClick() {}
    abstract fun getText(): String
}