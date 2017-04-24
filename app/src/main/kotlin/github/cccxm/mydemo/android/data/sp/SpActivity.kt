package github.cccxm.mydemo.android.data.sp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import github.cccxm.mydemo.utils.SpSerializable
import github.cccxm.mydemo.utils.SpString
import github.cccxm.mydemo.utils.SpUtil
import github.cccxm.mydemo.utils.logger
import org.jetbrains.anko.*
import java.util.*

/**
 * Created by cxm
 * on 2017/4/16.
 */
class SpActivity : AppCompatActivity() {

    private val ui = SpUI()
    private var mUserModel: SpUserBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val user = SpUtil.register(this, SpUserBean())
        mUserModel = user
        ui.setName(user.userName)
        logger(user.time)

        val appBar = supportActionBar ?: return
        appBar.title = "SP文件"
        appBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        val user = mUserModel
        if (user != null) {
            user.time = Date()
            SpUtil.unRegister(user)
            mUserModel = null
        }
    }

    fun saveName(name: CharSequence) {
        val user = mUserModel
        if (user != null) {
            user.userName = name.toString()
        }
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

private class SpUserBean {
    var userName: String by SpString
    var time: Date by SpSerializable(Date())
}

private class SpUI : AnkoComponent<SpActivity> {
    private lateinit var mNameEdit: EditText
    override fun createView(ui: AnkoContext<SpActivity>): View = with(ui) {
        linearLayout {
            relativeLayout {
                editText {
                    mNameEdit = this
                    hint = "姓名"
                    background = null
                }.lparams(dip(200), dip(50)) { centerVertically() }
                button("保存") {
                    onClick {
                        ui.owner.saveName(mNameEdit.text)
                    }
                }.lparams(wrapContent, wrapContent) {
                    alignParentRight()
                    centerVertically()
                }
            }.lparams(matchParent, wrapContent) { padding = dip(10) }
        }
    }

    fun setName(name: String) {
        mNameEdit.setText(name)
    }
}