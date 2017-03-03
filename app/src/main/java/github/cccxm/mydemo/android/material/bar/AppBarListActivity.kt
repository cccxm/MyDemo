package github.cccxm.mydemo.android.material.bar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

class AppBarListActivity : AppCompatActivity() {
    private val ui = AppBarListUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
    }
}

private class AppBarListUI : AnkoComponent<AppBarListActivity> {
    override fun createView(ui: AnkoContext<AppBarListActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            listView {
                lparams(matchParent, matchParent)
                simpleStringItemAdapter {
                    item("Simple") { startActivity<AppBarSimpleActivity>() }
                }
            }
        }
    }
}
