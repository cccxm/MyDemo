package github.cccxm.mydemo.android.effect.linkage

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.color
import github.cccxm.mydemo.utils.simpleStringAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ListHideBarActivity : AppCompatActivity() {
    private val ui = MyView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
    }
}

private class MyView : AnkoComponent<ListHideBarActivity> {
    override fun createView(ui: AnkoContext<ListHideBarActivity>): View = with(ui) {
        coordinatorLayout {
            lparams(matchParent, matchParent) {
                fitsSystemWindows = true
            }
            appBarLayout(R.style.AppTheme_AppBarOverlay) {
                lparams(matchParent, dimen(R.dimen.app_bar_height)) {
                    backgroundColor = color("#0088ff")
                    fitsSystemWindows = true
                }
                view {
                    lparams(matchParent, matchParent)
                }
            }
            recyclerView {
                lparams(matchParent, matchParent) {
                    //TODO behavior
                }
                simpleStringAdapter {
                    for (i in 0..30)
                        item("index $i")
                }
            }
            view {
                lparams(matchParent, dip(80)) {
                    backgroundColor = color("#ff8800")
                }
            }
        }
    }
}

private class MyBehavior<V : View> : CoordinatorLayout.Behavior<V>() {

}