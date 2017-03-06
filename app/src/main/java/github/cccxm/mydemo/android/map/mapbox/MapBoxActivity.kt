package github.cccxm.mydemo.android.map.mapbox

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import com.mapbox.mapboxsdk.MapboxAccountManager
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.string
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout

private interface Contract {
    interface View {

        fun initActionBar(activity: AppCompatActivity)
        /**
         * 准备加载地图信息，加载完毕后会回调 P:mapPrepared
         */
        fun prepare(savedInstanceState: Bundle?)

        fun onResume()
        fun onPause()
        fun onSaveInstanceState(state: Bundle)
        fun onLowMemory()
        fun onDestroy()
    }

    interface Presenter {
        /**
         * 回调：当地图准备完毕时
         */
        fun mapPrepared(map: MapboxMap)
    }

    interface Model
}

class MapBoxActivity : AppCompatActivity(), Contract.Presenter {

    private val ui = MapBoxUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapboxAccountManager.start(this, string(R.string.mapbox_token))
        setContentView(ui.setContentView(this))
        ui.initActionBar(this)
        ui.prepare(savedInstanceState)
    }

    override fun mapPrepared(map: MapboxMap) {
        //TODO
    }

    override fun onResume() {
        super.onResume()
        ui.onResume()
    }

    override fun onPause() {
        super.onPause()
        ui.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        ui.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        ui.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        ui.onDestroy()
    }
}

private class MapBoxUI : AnkoComponent<MapBoxActivity>, Contract.View {
    private lateinit var mMapView: MapView
    private lateinit var mToolbar: Toolbar
    private lateinit var mPresenter: Contract.Presenter

    override fun createView(ui: AnkoContext<MapBoxActivity>): View = with(ui) {
        mPresenter = ui.owner
        linearLayout root@ {
            lparams(matchParent, matchParent) {
                fitsSystemWindows = true
                orientation = LinearLayout.VERTICAL
            }
            appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
                lparams(width = matchParent, height = dimen(R.dimen.tool_bar_height)) {
                    fitsSystemWindows = true
                    backgroundColor = Color.parseColor("#ffff8800")
                }
                mToolbar = toolbar(theme = R.style.AppTheme_PopupOverlay) {
                    lparams(matchParent, matchParent)
                }
            }
            mMapView = with(MapView(ui.ctx)) {
                this@root.addView(this)
                lparams(matchParent, matchParent)
                this
            }
        }
    }

    override fun initActionBar(activity: AppCompatActivity) {
        with(activity) {
            mToolbar.title = "MapBox"
            setSupportActionBar(mToolbar)
            val actionBar = supportActionBar ?: return
            actionBar.setDisplayHomeAsUpEnabled(true)
            mToolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    override fun prepare(savedInstanceState: Bundle?) {
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync { mPresenter.mapPrepared(it) }
    }

    override fun onResume() {
        mMapView.onResume()
    }

    override fun onPause() {
        mMapView.onPause()
    }

    override fun onSaveInstanceState(state: Bundle) {
        mMapView.onSaveInstanceState(state)
    }

    override fun onLowMemory() {
        mMapView.onLowMemory()
    }

    override fun onDestroy() {
        mMapView.onDestroy()
    }
}