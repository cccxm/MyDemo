package github.cccxm.mydemo.utils.transformation

import android.graphics.*
import com.squareup.picasso.Transformation

/**
 * Created by cxm
 * on 2017/5/14.
 */
object CircleImageTransformation : Transformation {

    override fun key(): String = "circle"

    override fun transform(source: Bitmap): Bitmap {
        val size = minOf(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val square = Bitmap.createBitmap(source, x, y, size, size)
        if (square != source) source.recycle()
        val bitmap = Bitmap.createBitmap(size, size, source.config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(square, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true
        val r = size.toFloat() / 2
        canvas.drawCircle(r, r, r, paint)
        square.recycle()
        return bitmap
    }
}