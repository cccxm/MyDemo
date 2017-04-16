package github.cccxm.mydemo.android.data.sp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import github.cccxm.mydemo.utils.logger
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/16.
 */
class SpActivity : AppCompatActivity() {
    private val ui = SpUI()
    private var userBean = SpUserBean()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.title = "SharedPreference"
        appBar.setDisplayHomeAsUpEnabled(true)

        val name = userBean.userName ?: ""
        val age = userBean.age
        val job = userBean.job ?: ""

        ui.mNameEdit.setText(name)
        ui.mAgeEdit.setText("$age")
        ui.mJobEdit.setText(job)
    }

    fun onSaveNameClick() {
        val name = ui.mNameEdit.text.toString()
        userBean.userName = name
    }

    fun onSaveAgeClick() {
        logger("onSaveAgeClick") //TODO
        val age = ui.mAgeEdit.text.toString().toIntOrNull() ?: 0
        userBean.age = age
    }

    fun onSaveJobClick() {
        val job = ui.mJobEdit.text.toString()
        userBean.job = job
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

private class SpUI : AnkoComponent<SpActivity> {
    lateinit var mNameEdit: EditText
    lateinit var mAgeEdit: EditText
    lateinit var mJobEdit: EditText

    override fun createView(ui: AnkoContext<SpActivity>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            relativeLayout {
                editText {
                    hint = "姓名"
                    mNameEdit = this
                }.lparams(dip(250), dip(50)) { alignParentLeft() }
                button("保存") { onClick { ui.owner.onSaveNameClick() } }.lparams(wrapContent, wrapContent) { alignParentRight() }
            }.lparams(matchParent, wrapContent) {
                padding = dip(10)
                gravity = Gravity.CENTER_VERTICAL
            }

            relativeLayout {
                editText {
                    mAgeEdit = this
                    hint = "年龄"
                    inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                }.lparams(dip(250), dip(50)) { alignParentLeft() }
                button("保存") { onClick { ui.owner.onSaveAgeClick() } }.lparams(wrapContent, wrapContent) { alignParentRight() }
            }.lparams(matchParent, wrapContent) {
                padding = dip(10)
                gravity = Gravity.CENTER_VERTICAL
            }

            relativeLayout {
                editText {
                    mJobEdit = this
                    hint = "职业"
                }.lparams(dip(250), dip(50)) { alignParentLeft() }
                button("保存") { onClick { ui.owner.onSaveJobClick() } }.lparams(wrapContent, wrapContent) { alignParentRight() }
            }.lparams(matchParent, wrapContent) {
                padding = dip(10)
                gravity = Gravity.CENTER_VERTICAL
            }
        }
    }
}