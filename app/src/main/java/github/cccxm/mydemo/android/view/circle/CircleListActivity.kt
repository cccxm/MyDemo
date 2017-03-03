package github.cccxm.mydemo.android.view.circle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

class CircleListActivity : AppCompatActivity() {

    private val ui = CircleListUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        title = "圆形控件的实现"
    }
}

private class CircleListUI : AnkoComponent<CircleListActivity> {
    override fun createView(ui: AnkoContext<CircleListActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            listView {
                lparams(matchParent, matchParent)
                simpleStringItemAdapter {
                    item("CardView实现") { startActivity<CardViewCircleActivity>() }
                }
            }
        }
    }
}
