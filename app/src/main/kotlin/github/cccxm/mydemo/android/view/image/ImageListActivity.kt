package github.cccxm.mydemo.android.view.image

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/18.
 */

class ImageListActivity : AppCompatActivity() {
    private val ui = ImageListUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.setDisplayHomeAsUpEnabled(true)
        appBar.title = "图片显示"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

private class ImageListUI : AnkoComponent<ImageListActivity> {
    override fun createView(ui: AnkoContext<ImageListActivity>): View = with(ui) {
        relativeLayout {
            listView {
                simpleStringItemAdapter {
                    item("图片按比例排列") { startActivity<RateImageActivity>() }
                }
            }.lparams(matchParent, matchParent)
        }
    }
}
