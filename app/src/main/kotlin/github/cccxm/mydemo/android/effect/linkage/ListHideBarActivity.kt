package github.cccxm.mydemo.android.effect.linkage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.simpleCardAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

class ListHideBarActivity : AppCompatActivity() {
    private val ui = MyUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        ui.initToolbar(this)
        ui.setData(LinearLayoutManager(this))
    }
}

private class MyUI : AnkoComponent<ListHideBarActivity> {
    private lateinit var mToolBar: Toolbar
    private lateinit var mRecycleView: RecyclerView
    override fun createView(ui: AnkoContext<ListHideBarActivity>): View = with(ui) {
        coordinatorLayout {
            lparams(matchParent, matchParent) {
                fitsSystemWindows = true
            }
            appBarLayout(R.style.AppTheme_AppBarOverlay) {
                lparams(matchParent, dimen(R.dimen.app_bar_height)) {
                    backgroundResource = android.R.color.holo_blue_dark
                    fitsSystemWindows = true
                }
                mToolBar = toolbar(R.style.AppTheme_PopupOverlay) {
                    lparams(matchParent, matchParent)
                }
            }
            nestedScrollView {
                lparams(matchParent, matchParent)
                mRecycleView = recyclerView {
                    lparams(matchParent, matchParent)
                }
            }
        }
    }

    fun setData(manager: LinearLayoutManager) {
        with(mRecycleView) {
            layoutManager = manager
            simpleCardAdapter {
                for (i in 0..300)
                    item("item : $i")
            }
        }
    }

    fun initToolbar(activity: AppCompatActivity) {
        with(activity) {
            setSupportActionBar(mToolBar)
            val actionBar = supportActionBar ?: return
            actionBar.setDisplayHomeAsUpEnabled(true)
            mToolBar.setNavigationOnClickListener { onBackPressed() }
        }
    }
}