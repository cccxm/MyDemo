package github.cccxm.mydemo.android.data.net

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/3/27.
 */
class NetRequestActivity : AppCompatActivity() {
    private val ui = NetRequestUI()

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
                finish()
            }
        }
        return true
    }
}

private class NetRequestUI : AnkoComponent<NetRequestActivity> {
    override fun createView(ui: AnkoContext<NetRequestActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            listView {
                simpleStringItemAdapter {
                    item("后台下载") { startActivity<ServiceDownloadActivity>() }
                }
            }.lparams(matchParent, matchParent)
        }
    }
}