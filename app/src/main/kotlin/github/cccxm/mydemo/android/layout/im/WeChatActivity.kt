package github.cccxm.mydemo.android.layout.im

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.cccxm.mydemo.R
import kotlinx.android.synthetic.main.activity_im_wechat_main.*


/**
 * Created by
 * Jacksgong on 15/7/1.
 */
class WeChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_im_wechat_main)
//        setTitle(R.string.activity_main_title)

        theme_rg.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.full_screen_rb) {
                // full screen theme.
                handle_by_placeholder_resolved_btn.setText(R.string.activity_chatting_fullscreen_resolved_title)
            } else {
                // translucent status with fitSystemWindows=false theme.
                handle_by_placeholder_resolved_btn.setText(R.string.activity_chatting_translucent_status_false_resolved_title)
            }
        }

        translucent_status_true_cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // translucent status with fitSystemWindows=true theme.
                handle_by_delay_resolved_btn.setText(R.string.activity_chatting_translucent_status_true_resolved_title)
            } else {
                // normal theme.
                handle_by_delay_resolved_btn.setText(R.string.activity_chatting_resolved_title)
            }
        }
    }

    fun onClickResolved(view: View) {
        val i = Intent()
        i.putExtra(KEY_TRANSLUCENT_STATUS_FIT_SYSTEM_WINDOW_TRUE, translucent_status_true_cb.isChecked)
        i.putExtra(KEY_IGNORE_RECOMMEND_PANEL_HEIGHT, ignore_recommend_panel_height_cb.isChecked)
        i.putExtra(KEY_MULTI_SUB_PANEL, multiple_sub_panel_cb.isChecked)

        val componentName: ComponentName
        if (app_compat_activity_rb.isChecked) {
            componentName = ComponentName(this, ChattingResolvedActivity::class.java)
        } else {
            componentName = ComponentName(this, ChattingResolvedFragmentActivity::class.java)
        }
        i.component = componentName

        startActivity(i)
    }

    /**
     * Resolved for Full Screen Theme or Translucent Status Theme.
     */
    fun onClickExtraThemeResolved(view: View) {
        val fullScreenTheme = full_screen_rb.isChecked

        val i = Intent(this, ChattingResolvedHandleByPlaceholderActivity::class.java)
        i.putExtra(KEY_FULL_SCREEN_THEME, fullScreenTheme)
        startActivity(i)
    }


    fun onClickUnResolved(view: View) {
        startActivity(Intent(this, ChattingUnresolvedActivity::class.java))
    }

    companion object {
        val KEY_FULL_SCREEN_THEME = "key.theme.fullscreen"
        val KEY_TRANSLUCENT_STATUS_FIT_SYSTEM_WINDOW_TRUE = "key.theme.translucent.status.fitSystemWindow.true"
        val KEY_IGNORE_RECOMMEND_PANEL_HEIGHT = "key.ignore.recommend.panel.height"
        val KEY_MULTI_SUB_PANEL = "key.multi.sub.panel"
    }
}
