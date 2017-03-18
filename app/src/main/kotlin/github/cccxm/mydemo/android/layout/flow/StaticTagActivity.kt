package github.cccxm.mydemo.android.layout.flow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.simpleStringAdapter
import github.cccxm.mydemo.utils.tagFlowLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout

class StaticTagActivity : AppCompatActivity() {
    private val ui = StaticTagUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        ui.initToolbar(this)
    }
}

private class StaticTagUI : AnkoComponent<StaticTagActivity> {
    private lateinit var mToolbar: Toolbar
    override fun createView(ui: AnkoContext<StaticTagActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent) {
                orientation = LinearLayout.VERTICAL
            }
            appBarLayout(R.style.AppTheme_AppBarOverlay) {
                lparams(matchParent, dimen(R.dimen.tool_bar_height))
                mToolbar = toolbar(R.style.AppTheme_PopupOverlay) {
                    lparams(matchParent, matchParent)
                }
            }
            tagFlowLayout {
                lparams(matchParent, matchParent)
                simpleStringAdapter {
                    for (i in 0..30)
                        tag("TAG$i")
                }
            }
        }
    }

    fun initToolbar(activity: AppCompatActivity) {
        with(activity) {
            setSupportActionBar(mToolbar)
            val actionBar = supportActionBar ?: return
            actionBar.setDisplayHomeAsUpEnabled(true)
            mToolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }
}
