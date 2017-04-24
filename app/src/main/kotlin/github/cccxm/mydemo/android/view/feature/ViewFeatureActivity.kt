package github.cccxm.mydemo.android.view.feature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/22.
 */
class ViewFeatureActivity : AppCompatActivity() {
    private val ui = ViewFeatureUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.setDisplayHomeAsUpEnabled(true)
        appBar.title = "View的特性"
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

private class ViewFeatureUI : AnkoComponent<ViewFeatureActivity> {
    override fun createView(ui: AnkoContext<ViewFeatureActivity>): View = with(ui) {
        linearLayout {
            listView {
                simpleStringItemAdapter {
                    item("检测View是否滑动到了底部")
                }
            }
        }
    }
}