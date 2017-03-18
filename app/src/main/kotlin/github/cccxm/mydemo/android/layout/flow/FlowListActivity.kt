package github.cccxm.mydemo.android.layout.flow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

class FlowListActivity : AppCompatActivity() {
    private val ui = FlowListUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
    }
}

private class FlowListUI : AnkoComponent<FlowListActivity> {
    override fun createView(ui: AnkoContext<FlowListActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            listView {
                lparams(matchParent, matchParent)
                simpleStringItemAdapter {
                    item("静态标签展示") { startActivity<StaticTagActivity>() }
                }
            }
        }
    }
}