package github.cccxm.mydemo.android.layout.barrage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/5/2.
 */
class BarrageListActivity : AppCompatActivity() {
    private val ui = BarrageListUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.title = "弹幕"
        appBar.setDisplayHomeAsUpEnabled(true)
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

private class BarrageListUI : AnkoComponent<BarrageListActivity> {
    override fun createView(ui: AnkoContext<BarrageListActivity>): View = with(ui) {
        linearLayout {

        }
    }
}