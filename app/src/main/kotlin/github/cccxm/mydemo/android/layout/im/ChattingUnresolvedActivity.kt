package github.cccxm.mydemo.android.layout.im

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout

import github.cccxm.mydemo.R


/**
 * Created by Jacksgong on 15/7/1.
 *
 *
 * Desc: 未适配 Panel<->Keyboard 切换冲突
 */
class ChattingUnresolvedActivity : AppCompatActivity() {

    private var mContentRyv: RecyclerView? = null
    private var mSendEdt: EditText? = null
    private var mPanelRoot: LinearLayout? = null
    private var mPlusIv: ImageView? = null

    private fun assignViews() {
        mContentRyv = findViewById(R.id.content_ryv) as RecyclerView
        mSendEdt = findViewById(R.id.send_edt) as EditText
        mPanelRoot = findViewById(R.id.panel_root) as LinearLayout
        mPlusIv = findViewById(R.id.plus_iv) as ImageView
    }


    fun onClickPlusIv(view: View) {
        if (mPanelRoot!!.visibility == View.VISIBLE) {
            showKeyboard()
        } else {
            hideKeyboard()
            mPanelRoot!!.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_unresolved)

        assignViews()

        mPlusIv!!.setOnClickListener { v -> onClickPlusIv(v) }

        mContentRyv!!.layoutManager = LinearLayoutManager(this)

        mSendEdt!!.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                mPanelRoot!!.visibility = View.GONE
            }
        }

        mContentRyv!!.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                hideKeyboard()
                mPanelRoot!!.visibility = View.GONE
            }

            false
        }
    }

    private fun showKeyboard() {
        mSendEdt!!.requestFocus()
        val inputManager = mSendEdt!!.context.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(mSendEdt, 0)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mSendEdt!!.clearFocus()
        imm.hideSoftInputFromWindow(mSendEdt!!.windowToken, 0)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_UP && event.keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPanelRoot!!.visibility == View.VISIBLE) {
                mPanelRoot!!.visibility = View.GONE
                return true
            }
        }
        return super.dispatchKeyEvent(event)
    }
}
