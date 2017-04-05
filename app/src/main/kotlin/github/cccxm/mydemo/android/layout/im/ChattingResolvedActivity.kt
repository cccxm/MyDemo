package github.cccxm.mydemo.android.layout.im

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
 * For AppCompatActivity. (Just for test(because has different view hierarchy, but has already
 * handled internal), all calls are identical to the [ChattingResolvedFragmentActivity]).
 *
 *
 * For resolving the conflict by delay the visible or gone of panel.
 *
 *
 * In case of Normal(not fullscreen) Theme.
 * In case of Translucent Status Theme with the `getFitSystemWindow()` is true in root view.
 */
class ChattingResolvedActivity : AppCompatActivity() {
    private var mContentRyv: RecyclerView? = null
    private var mSendEdt: EditText? = null
    private var mPanelRoot: KPSwitchPanelLinearLayout? = null
    private var mSendImgTv: TextView? = null
    private var mPlusIv: ImageView? = null

    private fun assignViews() {
        mContentRyv = findViewById(R.id.content_ryv) as RecyclerView
        mSendEdt = findViewById(R.id.send_edt) as EditText
        mPanelRoot = findViewById(R.id.panel_root) as KPSwitchPanelLinearLayout
        mSendImgTv = findViewById(R.id.send_img_tv) as TextView
        mPlusIv = findViewById(R.id.plus_iv) as ImageView
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

    private var mSubPanel1: View? = null
    private var mSubPanel2: View? = null
    private var mPlusIv1: ImageView? = null
    private var mPlusIv2: ImageView? = null

    private fun adaptMultiSubPanel(isMultiSubPanel: Boolean) {
        if (isMultiSubPanel) {
            setContentView(R.layout.activity_multiple_sub_panel_chatting_resolved)
        } else {
            setContentView(R.layout.activity_chatting_resolved)
        }

        assignViews()

        if (isMultiSubPanel) {
            mSubPanel1 = mPanelRoot!!.findViewById(R.id.sub_panel_1)
            mSubPanel2 = mPanelRoot!!.findViewById(R.id.sub_panel_2)

            mPlusIv1 = findViewById(R.id.voice_text_switch_iv) as ImageView
            mPlusIv2 = mPlusIv

            mPlusIv1!!.setImageResource(R.drawable.chatting_plus_btn_icon)

            mSendImgTv!!.setText(R.string.mark_panel1_to_img)
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private fun adaptFitsSystemWindows(isTranslucentStatusFitSystemWindowTrue: Boolean) {
        if (isTranslucentStatusFitSystemWindowTrue && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            findViewById(R.id.rootView).fitsSystemWindows = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ********* Below code Just for Demo Test, do not need to adapt in your code. ************
        val isTranslucentStatusFitSystemWindowTrue = intent.getBooleanExtra(WeChatActivity.KEY_TRANSLUCENT_STATUS_FIT_SYSTEM_WINDOW_TRUE, false)
        adaptTheme(isTranslucentStatusFitSystemWindowTrue)

        val isMultiSubPanel = intent.getBooleanExtra(WeChatActivity.KEY_MULTI_SUB_PANEL, false)

        adaptMultiSubPanel(isMultiSubPanel)

        adaptFitsSystemWindows(isTranslucentStatusFitSystemWindowTrue)

        adaptTitle(isTranslucentStatusFitSystemWindowTrue)


        if (intent.getBooleanExtra(WeChatActivity.KEY_IGNORE_RECOMMEND_PANEL_HEIGHT, false)) {
            mPanelRoot!!.setIgnoreRecommendHeight(true)
        }
        // ********* Above code Just for Demo Test, do not need to adapt in your code. ************


        KeyboardUtil.attach(this, mPanelRoot
                // Add keyboard showing state callback, do like this when you want to listen in the
                // keyboard's show/hide change.
        ) { isShowing -> Log.d(TAG, String.format("Keyboard is %s", if (isShowing) "showing" else "hiding")) }

        if (isMultiSubPanel) {
            // If there are several sub-panels in this activity ( e.p. function-panel, emoji-panel).
            KPSwitchConflictUtil.attach(mPanelRoot, mSendEdt,
                    KPSwitchConflictUtil.SwitchClickListener { switchToPanel ->
                        if (switchToPanel) {
                            mSendEdt!!.clearFocus()
                        } else {
                            mSendEdt!!.requestFocus()
                        }
                    },
                    KPSwitchConflictUtil.SubPanelAndTrigger(mSubPanel1, mPlusIv1),
                    KPSwitchConflictUtil.SubPanelAndTrigger(mSubPanel2, mPlusIv2))
        } else {
            // In the normal case.
            KPSwitchConflictUtil.attach(mPanelRoot!!, mPlusIv, mSendEdt
            ) { switchToPanel ->
                if (switchToPanel) {
                    mSendEdt!!.clearFocus()
                } else {
                    mSendEdt!!.requestFocus()
                }
            }
        }


        mSendImgTv!!.setOnClickListener {
            // mock start the translucent full screen activity.
            startActivity(Intent(this@ChattingResolvedActivity, TranslucentActivity::class.java))
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