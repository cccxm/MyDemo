package github.cccxm.mydemo.android.effect.linkage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

class LinkageListActivity : AppCompatActivity() {

    private val ui = LinkageListUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        val actionBar = supportActionBar ?: return
        actionBar.title = "linkage"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }
}

private class LinkageListUI : AnkoComponent<LinkageListActivity> {
    override fun createView(ui: AnkoContext<LinkageListActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            listView {
                lparams(matchParent, matchParent)
                simpleStringItemAdapter {
                    item("ScrollingActivity") { startActivity<ScrollingActivity>() }
                    item("RecyclerView联动ActionBar") { startActivity<ListPushBarActivity>() }
                    item("RecyclerView顶开ActionBar") { startActivity<ListHideBarActivity>() }
                    item("弹出的可拖拽窗口") { startActivity<PopupViewActivity>() }
                    item("一个View影响另一个View") { startActivity<CoorDemoActivity>() }
                }
            }
        }
    }
}
