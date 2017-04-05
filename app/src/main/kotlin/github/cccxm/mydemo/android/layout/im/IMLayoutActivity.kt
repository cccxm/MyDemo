package github.cccxm.mydemo.android.layout.im

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/5.
 */
class IMLayoutActivity : AppCompatActivity() {
    private val ui = IMLayoutUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val actionBar = supportActionBar ?: return
        actionBar.title = "聊天界面大全"
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

private class IMLayoutUI : AnkoComponent<IMLayoutActivity> {
    override fun createView(ui: AnkoContext<IMLayoutActivity>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            listView {
                simpleStringItemAdapter {
                    item("微信") { startActivity<WeChatActivity>() }
                }
            }.lparams(matchParent, matchParent)
        }
    }
}