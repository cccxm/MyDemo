package github.cccxm.mydemo.android.ndk.bitmap

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/12.
 */
class NDKBitmapActivity : AppCompatActivity() {
    private val ui = NDKBitmapUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.title = "本地图片处理"
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

private class NDKBitmapUI : AnkoComponent<NDKBitmapActivity> {
    override fun createView(ui: AnkoContext<NDKBitmapActivity>): View = with(ui) {
        linearLayout {
            listView {
                simpleStringItemAdapter {
                    item("图片蓝移") { startActivity<BitmapBlueMoveActivity>() }
                }
            }.lparams(matchParent, matchParent)
        }
    }
}