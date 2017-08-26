package github.cccxm.mydemo.android.layout.im

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil
import cn.dreamtobe.kpswitch.util.KeyboardUtil
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout
import github.cccxm.mydemo.R

/**
 * Created by Jacksgong on 15/7/1.
 *
 *
 * For FragmentActivity/Activity. (Just for test(because has different view hierarchy, but has
 * already handled internal), all calls are identical to the [ChattingResolvedActivity]).
 *
 *
 * For resolving the conflict by delay the visible or gone of panel.
 *
 *
 * In case of Normal(not fullscreen) Theme.
 * In case of Translucent Status Theme with the `getFitSystemWindow()` is true in root view.
 */
class ChattingResolvedFragmentActivity : FragmentActivity() {
    private var mContentRyv: RecyclerView? = null
    private var mSendEdt: EditText? = null
    private var mPanelRoot: KPSwitchPanelLinearLayout? = null
    private var mSendImgTv: TextView? = null
    private var mPlusIv: ImageView? = null

    private fun assignViews() {
        mContentRyv = findViewById<RecyclerView>(R.id.content_ryv)
        mSendEdt = findViewById<EditText>(R.id.send_edt)
        mPanelRoot = findViewById<KPSwitchPanelLinearLayout>(R.id.panel_root)
        mSendImgTv = findViewById<TextView>(R.id.send_img_tv)
        mPlusIv = findViewById<ImageView>(R.id.plus_iv)
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun adaptTheme(isTranslucentStatusFitSystemWindowTrue: Boolean) {
        if (isTranslucentStatusFitSystemWindowTrue) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }

    private fun adaptTitle(isTranslucentStatusFitSystemWindowTrue: Boolean) {
        if (isTranslucentStatusFitSystemWindowTrue) {
            setTitle(R.string.activity_chatting_translucent_status_true_resolved_title)
        } else {
            setTitle(R.string.activity_chatting_resolved_title)
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private fun adaptFitsSystemWindows(isTranslucentStatusFitSystemWindowTrue: Boolean) {
        if (isTranslucentStatusFitSystemWindowTrue && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            findViewById<View>(R.id.rootView).fitsSystemWindows = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ********* Below code Just for Demo Test, do not need to adapt in your code. ************
        val isTranslucentStatusFitSystemWindowTrue = intent.getBooleanExtra(WeChatActivity.KEY_TRANSLUCENT_STATUS_FIT_SYSTEM_WINDOW_TRUE, false)
        adaptTheme(isTranslucentStatusFitSystemWindowTrue)

        setContentView(R.layout.activity_chatting_resolved)

        adaptFitsSystemWindows(isTranslucentStatusFitSystemWindowTrue)

        adaptTitle(isTranslucentStatusFitSystemWindowTrue)

        assignViews()

        if (intent.getBooleanExtra(WeChatActivity.KEY_IGNORE_RECOMMEND_PANEL_HEIGHT, false)) {
            mPanelRoot!!.setIgnoreRecommendHeight(true)
        }
        // ********* Above code Just for Demo Test, do not need to adapt in your code. ************

        KeyboardUtil.attach(this, mPanelRoot
                // Add keyboard showing state callback, do like this when you want to listen in the
                // keyboard's show/hide change.
        ) { isShowing -> Log.d(TAG, String.format("Keyboard is %s", if (isShowing) "showing" else "hiding")) }

        KPSwitchConflictUtil.attach(mPanelRoot!!, mPlusIv, mSendEdt
        ) { switchToPanel ->
            if (switchToPanel) {
                mSendEdt!!.clearFocus()
            } else {
                mSendEdt!!.requestFocus()
            }
        }

        mSendImgTv!!.setOnClickListener {
            // mock start the translucent full screen activity.
            startActivity(Intent(this@ChattingResolvedFragmentActivity, TranslucentActivity::class.java))
        }

        mContentRyv!!.layoutManager = LinearLayoutManager(this)

        mContentRyv!!.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot!!)
            }

            false
        }
    }


    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPanelRoot!!.visibility == View.VISIBLE) {
                KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot!!)
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }

    companion object {

        private val TAG = "ResolvedActivity"
    }

}