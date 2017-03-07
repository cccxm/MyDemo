package github.cccxm.mydemo.android.effect.linkage

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
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
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

private interface Contract {
    interface View {
        fun initToolbar(activity: AppCompatActivity)
        fun loadData()
    }
}

class ListHideBarActivity : AppCompatActivity() {

    private val ui = ListHideBarUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        ui.initToolbar(this)
        ui.loadData()
    }
}

private class ListHideBarUI : AnkoComponent<ListHideBarActivity>, Contract.View {
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private val ACTION_BAR_ID = 0x1234

    override fun createView(ui: AnkoContext<ListHideBarActivity>): View = with(ui) {
        coordinatorLayout {
            lparams(matchParent, matchParent) {
                fitsSystemWindows = true
            }
            appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
                lparams(width = matchParent, height = dimen(R.dimen.app_bar_height)) {
                    id = ACTION_BAR_ID
                    fitsSystemWindows = true
                    backgroundColor = Color.parseColor("#ffff8800")
                }
//                collapsingToolbarLayout {
//                    with(AppBarLayout.LayoutParams(matchParent, matchParent)) {
//                        fitsSystemWindows = true
//                        setContentScrimColor((Color.parseColor("#ffff4444")))
//                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL and AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
//                        layoutParams = this
//                    }
//                    mToolbar = toolbar(theme = R.style.AppTheme_PopupOverlay) {
//                        with(CollapsingToolbarLayout.LayoutParams(matchParent, dip(50))) {
//                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
//                            layoutParams = this
//                        }
//                    }
//                }
            }
//            include<NestedScrollView>(R.layout.content_scrolling) {
//                removeAllViews()
//                mRecyclerView = this.recyclerView {
//                    layoutManager = LinearLayoutManager(ui.ctx)
//                    lparams(matchParent, matchParent) {
//                        overScrollMode = View.OVER_SCROLL_ALWAYS
//                    }
//                }
//            }
//            ui.ctx.layoutInflater.inflate(R.layout.content_scrolling, this)
//            with(_nestedScrollView) {
//                lparams(matchParent, matchParent)
//                removeAllViews()
//                mRecyclerView = this.recyclerView {
//                    layoutManager = LinearLayoutManager(ui.ctx)
//                    lparams(matchParent, matchParent) {
//                        overScrollMode = View.OVER_SCROLL_ALWAYS
//                    }
//                }
//            }
            nestedScrollView {
                with(CoordinatorLayout.LayoutParams(matchParent, matchParent)) {
                    behavior = ListHideBarBehavior(ACTION_BAR_ID)
                    layoutParams = this
                }
                mRecyclerView = recyclerView {
                    layoutManager = LinearLayoutManager(ui.ctx)
                    lparams(matchParent, matchParent) {
                        overScrollMode = View.OVER_SCROLL_ALWAYS
                    }
                }
            }
        }
    }

    override fun initToolbar(activity: AppCompatActivity) {
        with(activity) {
            setSupportActionBar(mToolbar)
            val actionBar = supportActionBar ?: return
            mToolbar.title = "MyDemo"
            actionBar.setDisplayHomeAsUpEnabled(true)
            mToolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun loadData() {
        with(mRecyclerView) {
            simpleCardAdapter {
                for (i in 0..50)
                    item("Item $i")
            }
        }
    }
}

private class ListHideBarBehavior(val id: Int) : CoordinatorLayout.Behavior<View>() {
    /**
     * 确定子视图与同级视图的依赖关系
     *
     * 重要
     */
    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        //return super.layoutDependsOn(parent, child, dependency)
        return dependency?.id ?: 0 == id
    }

    /**
     * 依赖布局变化时回调
     *
     * 重要
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
//        return super.onDependentViewChanged(parent, child, dependency)
        child?.translationY = -(dependency?.top ?: 0).toFloat()
        return true
    }

    /**
     * 指定Behavior关注的滑动方向
     */
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes)
    }

    /**
     * 用来监听滚动状态，在事件被消费前回调
     */
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)
    }

    /**
     * 快速滑动时回调
     */
    override fun onNestedFling(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

}