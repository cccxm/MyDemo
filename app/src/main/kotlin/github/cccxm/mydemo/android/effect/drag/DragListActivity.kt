package github.cccxm.mydemo.android.effect.drag

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.android.effect.drag.tantan.TanTanDragActivity
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/6.
 */
class DragListActivity : AppCompatActivity() {
    private val ui = DragListUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.title = "拖动效果列表"
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

private class DragListUI : AnkoComponent<DragListActivity> {
    override fun createView(ui: AnkoContext<DragListActivity>): View = with(ui) {
        linearLayout {
            listView {
                simpleStringItemAdapter {
                    item("拖动View") { startActivity<DragViewActivity>() }
                    item("拖动回弹") { startActivity<DragReleaseBackActivity>() }
                    item("侧边拖动")  //TODO
                    item("弹出的可拖动布局") { startActivity<PopupDragLayoutActivity>() }
                    item("仿探探的个人页面拖动控件") { startActivity<TanTanDragActivity>() }
                }
            }.lparams(matchParent, matchParent)
        }
    }
}