package github.cccxm.mydemo.android.adapter.screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import com.mapbox.mapboxsdk.MapboxAccountManager
import com.mapbox.mapboxsdk.maps.MapView
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.color
import github.cccxm.mydemo.utils.string
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout

/**
 * Created by cxm
 * on 2017/3/31.
 */
@SuppressLint("NewApi")
class ViewPartFullScreenActivity : AppCompatActivity() {
    private val ui = UI()
    private lateinit var mFragment: ViewPartFullScreenFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapboxAccountManager.start(this, string(R.string.mapbox_token))
        setContentView(ui.setContentView(this))

        mFragment = (supportFragmentManager.findFragmentById(UI.ID_FRAME_LAYOUT) ?: ViewPartFullScreenFragment()) as ViewPartFullScreenFragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(UI.ID_FRAME_LAYOUT, mFragment)
        transaction.commit()

        val mToolBar = ui.mToolBar
        setSupportActionBar(mToolBar)
        val appBar = supportActionBar ?: return
        appBar.setDisplayHomeAsUpEnabled(true)
        mToolBar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onStart() {
        super.onStart()
        mFragment.mRelativeLayout.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                val screenHeight = displayMetrics.heightPixels
                val locations = IntArray(2)
                mFragment.mRelativeLayout.getLocationOnScreen(locations)
                val height = screenHeight - locations[1]
                val params = LinearLayout.LayoutParams(matchParent, height)
                mFragment.mRelativeLayout.layoutParams = params
                mFragment.mRelativeLayout.viewTreeObserver.removeOnPreDrawListener(this) //如果不移除此监听，在滑动过程中将会持续回调
                return true
            }
        })
    }

    class UI : AnkoComponent<ViewPartFullScreenActivity> {
        companion object {
            @JvmStatic val ID_FRAME_LAYOUT = 0x001
        }

        lateinit var mToolBar: Toolbar

        override fun createView(ui: AnkoContext<ViewPartFullScreenActivity>): View = with(ui) {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                appBarLayout(R.style.AppTheme_AppBarOverlay) {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
                        elevation = px2dip(5)
                    toolbar(R.style.AppTheme_PopupOverlay) {
                        mToolBar = this
                        backgroundColor = color("#75CFF1")
                    }.lparams(matchParent, dimen(R.dimen.tool_bar_height))
                }.lparams(matchParent, wrapContent)
                frameLayout { id = ID_FRAME_LAYOUT }.lparams(matchParent, matchParent)
            }
        }
    }
}

class ViewPartFullScreenFragment : Fragment() {
    private lateinit var mMapView: MapView
    lateinit var mRelativeLayout: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return with(context) {
            scrollView {
                backgroundColor = Color.BLACK
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        mRelativeLayout = this
                        addView(with(InterceptLayout(context, this@scrollView)) {
                            addView(with(MapView(context)) {
                                //地图
                                mMapView = this
                                this
                            }.lparams(matchParent, matchParent))
                            this
                        }.lparams(matchParent, 0) { weight = 1f })
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            backgroundResource = R.drawable.shape_gradient_blue_to_black
                            textView("显示在屏幕上的文字") {
                                //显示的文字
                                gravity = Gravity.CENTER
                                textColor = Color.WHITE
                                textSize = px2sp(32)
                                backgroundResource = R.drawable.shape_radius_black_gray
                            }.lparams(matchParent, matchParent) {
                                margin = dip(8)
                            }
                        }.lparams(matchParent, dip(200))
                    }.lparams(matchParent, wrapContent)
                    textView("被隐藏在下方的文字") {
                        //隐藏的文字
                        gravity = Gravity.CENTER
                        textColor = Color.WHITE
                        textSize = px2sp(32)
                        backgroundResource = R.drawable.shape_radius_black_gray
                    }.lparams(matchParent, dip(400)) {
                        margin = dip(8)
                    }
                }.lparams(matchParent, wrapContent)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mMapView.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }
}

@SuppressLint("ViewConstructor")
private class InterceptLayout(context: Context, private val scrollView: ScrollView) : RelativeLayout(context) {
    override fun onTouchEvent(event: MotionEvent) = true
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        scrollView.requestDisallowInterceptTouchEvent(ev.action != MotionEvent.ACTION_UP)
        return false
    }
}