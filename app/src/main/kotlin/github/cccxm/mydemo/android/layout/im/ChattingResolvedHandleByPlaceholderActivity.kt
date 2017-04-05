package github.cccxm.mydemo.android.layout.im

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView

import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil
import cn.dreamtobe.kpswitch.util.KeyboardUtil
import cn.dreamtobe.kpswitch.widget.KPSwitchFSPanelLinearLayout
import github.cccxm.mydemo.R

/**
 * Created by Jacksgong on 3/26/16.
 *
 *
 * For resolving the conflict by showing the panel placeholder.
 *
 *
 * In case of FullScreen Theme.
 * In case of Translucent Status Theme with the `getFitSystemWindow()` is false in root view.
 */
class ChattingResolvedHandleByPlaceholderActivity : AppCompatActivity() {

    private fun adaptTitle(isFullScreenTheme: Boolean) {
        if (isFullScreenTheme) {
            setTitle(R.string.activity_chatting_fullscreen_resolved_title)
        } else {
            setTitle(R.string.activity_chatting_translucent_status_false_resolved_title)
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun adaptTheme(isFullScreenTheme: Boolean) {
        if (isFullScreenTheme) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ********* Below code Just for Demo Test, do not need to adapt in your code. ************
        val isFullScreenTheme = intent.getBooleanExtra(WeChatActivity.KEY_FULL_SCREEN_THEME, false)

        adaptTheme(isFullScreenTheme)

        setContentView(R.layout.activity_chatting_fullscreen_resolved)
        assignViews()

        adaptTitle(isFullScreenTheme)

        if (!isFullScreenTheme) {
            // For present the theme: Translucent Status and FitSystemWindow is True.
            contentRyv!!.setBackgroundColor(resources.getColor(R.color.abc_search_url_text_normal))
        }
        // ********* Above code Just for Demo Test, do not need to adapt in your code. ************

        KeyboardUtil.attach(this, panelRoot)
        KPSwitchConflictUtil.attach(panelRoot!!, plusIv, sendEdt
        ) { switchToPanel ->
            if (switchToPanel) {
                sendEdt!!.clearFocus()
            } else {
                sendEdt!!.requestFocus()
            }
        }

        contentRyv!!.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                KPSwitchConflictUtil.hidePanelAndKeyboard(panelRoot!!)
            }
            false
        }

    }

    override fun onPause() {
        super.onPause()
        panelRoot!!.recordKeyboardStatus(window)
    }

    private var panelRoot: KPSwitchFSPanelLinearLayout? = null
    private var sendEdt: EditText? = null
    private var plusIv: ImageView? = null
    private var contentRyv: RecyclerView? = null


    private fun assignViews() {
        contentRyv = findViewById(R.id.content_ryv) as RecyclerView
        panelRoot = findViewById(R.id.panel_root) as KPSwitchFSPanelLinearLayout
        sendEdt = findViewById(R.id.send_edt) as EditText
        plusIv = findViewById(R.id.plus_iv) as ImageView
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_BACK) {
            if (panelRoot!!.visibility != View.GONE) {
                KPSwitchConflictUtil.hidePanelAndKeyboard(panelRoot!!)
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }
}
