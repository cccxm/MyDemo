package github.cccxm.mydemo.android.ndk.bitmap

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.setContentView

/**
 * Created by cxm
 * on 2017/4/12.
 */
class BitmapBlueMoveActivity : AppCompatActivity() {
    private val ui = BitmapBlueMoveUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
    }

    /**
     * 设置需要操作的图片
     */
    external fun initBitmap(activity: Activity, bitmap: Bitmap)
}

private class BitmapBlueMoveUI : AnkoComponent<BitmapBlueMoveActivity> {
    override fun createView(ui: AnkoContext<BitmapBlueMoveActivity>): View = with(ui) {
        linearLayout {

        }
    }
}