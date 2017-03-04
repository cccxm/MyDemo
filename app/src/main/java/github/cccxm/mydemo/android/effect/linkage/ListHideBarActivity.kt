package github.cccxm.mydemo.android.effect.linkage

import android.content.res.Resources
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.View
import github.cccxm.mydemo.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout

private interface Contract {
    interface View {

    }

    interface Presenter {

    }
}

class ListHideBarActivity : AppCompatActivity() {

    private val ui = ListHideBarUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
    }
}

private class ListHideBarUI : AnkoComponent<ListHideBarActivity>, Contract.View {
    private lateinit var mCoordinatorLayout: CoordinatorLayout
    private lateinit var mAppBarLayout: AppBarLayout

    override fun createView(ui: AnkoContext<ListHideBarActivity>): View = with(ui) {
        mCoordinatorLayout = coordinatorLayout {
            fitsSystemWindows = true
            mAppBarLayout = appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
                lparams(mCoordinatorLayout.layoutParams) {
                    width = matchParent
                    height = dimen(R.dimen.app_bar_height)
                    fitsSystemWindows = true
                    backgroundColor = Color.BLUE
                }
                collapsingToolbarLayout {
                    lparams(mAppBarLayout.layoutParams as AppBarLayout.LayoutParams) {
                        width = matchParent
                        height = matchParent
                        fitsSystemWindows = true
                        setContentScrimColor(Color.GREEN)
                    }
                }
            }
        }
        mCoordinatorLayout
    }
}
