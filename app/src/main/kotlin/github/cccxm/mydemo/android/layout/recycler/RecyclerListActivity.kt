package github.cccxm.mydemo.android.layout.recycler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout

/**
 * Created by cxm
 * on 2017/3/30.
 */
class RecyclerListActivity : AppCompatActivity() {
    private val ui = RecyclerListUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        setSupportActionBar(ui.mToolBar)
        val appBar = supportActionBar ?: return
        appBar.setDisplayHomeAsUpEnabled(true)
        appBar.title = "RecyclerView"
        ui.mToolBar.setNavigationOnClickListener { onBackPressed() }
    }
}

private class RecyclerListUI : AnkoComponent<RecyclerListActivity> {
    lateinit var mToolBar: Toolbar

    override fun createView(ui: AnkoContext<RecyclerListActivity>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            lparams(matchParent, matchParent)
            appBarLayout(R.style.AppTheme_AppBarOverlay) {
                toolbar(R.style.AppTheme_PopupOverlay) { mToolBar = this }.lparams(matchParent, matchParent)
            }.lparams(matchParent, dimen(R.dimen.tool_bar_height))
            listView {
                simpleStringItemAdapter {
                    item("实现数据分组") { startActivity<RecyclerGroupActivity>() }
                }
            }.lparams(matchParent, matchParent)
        }
    }
}