package github.cccxm.mydemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.cccxm.mydemo.android.effect.animation.AnimationListActivity
import github.cccxm.mydemo.android.effect.linkage.LinkageListActivity
import github.cccxm.mydemo.android.map.mapbox.MapBoxActivity
import github.cccxm.mydemo.android.material.bar.AppBarListActivity
import github.cccxm.mydemo.android.view.circle.CircleListActivity
import github.cccxm.mydemo.utils.group
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringGroupAdapter
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private val ui = MainUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        title = "Android"
    }
}

private class MainUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            expandableListView {
                lparams(matchParent, matchParent)
                simpleStringGroupAdapter {
                    group("Material Design") {
                        item("App Bar") { startActivity<AppBarListActivity>() }
                    }
                    group("特效") {
                        item("动画") { startActivity<AnimationListActivity>() }
                        item("联动") { startActivity<LinkageListActivity>() }
                    }
                    group("View") {
                        item("圆形控件") { startActivity<CircleListActivity>() }
                    }
                    group("地图") {
                        item("MapBox") { startActivity<MapBoxActivity>() }
                    }
                }
            }
        }
    }
}