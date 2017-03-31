package github.cccxm.mydemo.android.adapter.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.color
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout

/**
 * Created by cxm
 * on 2017/3/30.
 */
class ScreenAdapterActivity : AppCompatActivity() {
    private val ui = ScreenAdapterUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        ui.initAppBar(this)
    }
}

private class ScreenAdapterUI : AnkoComponent<ScreenAdapterActivity> {
    private lateinit var mToolBar: Toolbar

    override fun createView(ui: AnkoContext<ScreenAdapterActivity>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            appBarLayout(R.style.AppTheme_AppBarOverlay) {
                toolbar(R.style.AppTheme_PopupOverlay) {
                    mToolBar = this
                    title = "屏幕适配"
                }.lparams(matchParent, dimen(R.dimen.tool_bar_height))
            }.lparams(matchParent, wrapContent)
            listView {
                simpleStringItemAdapter {
                    item("ScrollView指定部分占满屏幕") { startActivity<ViewPartFullScreenActivity>() }
                }
            }.lparams(matchParent, matchParent)
        }
    }

    fun initAppBar(activity: AppCompatActivity) {
        with(activity) {
            activity.setSupportActionBar(mToolBar)
            val actionBar = supportActionBar ?: return
            actionBar.setDisplayHomeAsUpEnabled(true)
            mToolBar.setNavigationOnClickListener { onBackPressed() }
        }
    }
}