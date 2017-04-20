package github.cccxm.mydemo.android.view.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import github.cccxm.mydemo.R
import org.jetbrains.anko.*
import java.util.*

/**
 * Created by cxm
 * on 2017/4/20.
 */
class RateImageActivity : AppCompatActivity() {
    private val ui = RateImageUI()

    private val resArr = intArrayOf(
            R.drawable.demo_hy_1,
            R.drawable.demo_hy_2,
            R.drawable.demo_hy_3,
            R.drawable.demo_hy_4,
            R.drawable.demo_hy_5,
            R.drawable.demo_hy_6,
            R.drawable.demo_hy_7,
            R.drawable.demo_hy_8,
            R.drawable.demo_hy_9)

    private lateinit var images: List<MultipartImageView.Image>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        images = resArr.map { BitmapFactory.decodeResource(resources, it) }
                .mapIndexed(::RateImage)
        ui.setImages(images)
    }
}

private class RateImage(val index: Int, val bitmap: Bitmap) : MultipartImageView.Image {
    override fun setImage(image: ImageView) {
        image.setImageBitmap(bitmap)
    }

    override val height: Int
        get() = bitmap.height
    override val width: Int
        get() = bitmap.width
}

private class RateImageUI : AnkoComponent<RateImageActivity> {
    private lateinit var multiPartImageView: MultipartImageView

    override fun createView(ui: AnkoContext<RateImageActivity>): View = with(ui) {
        scrollView {
            linearLayout {
                addView(MultipartImageView(ui.ctx).apply {
                    layoutParams = LinearLayout.LayoutParams(matchParent, matchParent)
                    multiPartImageView = this
                    setOnImageItemClickListener {
                        if (it is RateImage)
                            toast("onClick ${it.index}")
                    }
                })
            }.lparams(matchParent, wrapContent)
        }
    }

    fun setImages(images: List<MultipartImageView.Image>) {
        multiPartImageView.setImages(images)
    }
}

class MultipartImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = LinearLayout.VERTICAL
    }

    private var mListener: ((Image) -> Unit)? = null
    private val mImages = LinkedList<Image>()

    fun setOnImageItemClickListener(listener: (Image) -> Unit) {
        this.mListener = listener
    }

    fun recycle() {
        mListener = null
        removeAllViews()
        mImages.clear()
    }

    private var hasData = false
    private var hasWidth = false

    fun setImages(images: List<Image>) {
        mImages.addAll(images)
        hasData = true
        showImages()
    }

    private fun showImages() {
        if (hasData && hasWidth) {
            val width = viewWidth * 1
            var line = getLine(width)
            while (line.isNotEmpty()) {
                buildLine(line)
                line = getLine(width)
            }
            invalidate()
        }
    }

    private var viewWidth: Int = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w != oldw && w != 0) {
            viewWidth = w
            hasWidth = true
            showImages()
        }
    }

    private fun buildLine(images: List<Image>) {
        var maxHeight = 0
        images.forEach { maxHeight = Math.max(maxHeight, it.height) }
        //计算高度相等时的总宽度
        var maxWidth = 0f
        images.forEach {
            val rate = maxHeight.toFloat() / it.height
            maxWidth += rate * it.width
        }
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.HORIZONTAL
        val rate = viewWidth.toFloat() / maxWidth
        images.forEach { image ->
            val a = maxHeight.toFloat() / image.height
            val realWidth = (a * rate * image.width.toFloat()).toInt()
            val imageView = ImageView(context)
            val imageParams = LinearLayout.LayoutParams(realWidth, (maxHeight * rate).toInt())
            imageParams.margin = dip(1)
            layout.addView(imageView, imageParams)
            image.setImage(imageView)
            imageView.onClick {
                val listener = mListener ?: return@onClick
                listener.invoke(image)
            }
        }
        addView(layout, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    private fun getLine(width: Int): List<Image> {
        val images = LinkedList<Image>()
        val iterator = mImages.listIterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (one(images, next, width)) {
                images.add(next)
                iterator.remove()
            } else
                break
        }
        return images
    }

    /**
     * 是否可以被添加到该行
     */
    private fun one(images: LinkedList<Image>, image: Image, width: Int): Boolean {
        if (images.isEmpty()) return true
        var mWidth = 0
        images.forEach { mWidth += it.width }
        mWidth += image.width
        return mWidth in 0..width
    }

    interface Image {
        fun setImage(image: ImageView)

        val height: Int

        val width: Int
    }
}
