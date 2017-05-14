package github.cccxm.mydemo.android.layout.barrage

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.inflateCircle
import github.cccxm.mydemo.view.AbsBarrageItem
import kotlinx.android.synthetic.main.activity_barrage_list.*
import org.jetbrains.anko.onClick

/**
 * Created by cxm
 * on 2017/5/2.
 */
class BarrageListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barrage_list)
        mBarrageLayout.onCreate() //在OnCreate中（或其他合适位置）启动弹幕
        val initBarrageItems = mMessageList.map {
            object : AbsBarrageItem() {
                override fun getText(): String = it
                override fun getBackgroundResource(): Int = R.drawable.shape_radius_alpha_black
                override fun avatarEnable(): Boolean = true
                override fun inflateAvatar(avatar: ImageView) = avatar.inflateCircle(R.drawable.avatar_pain_1)
            }
        }.toList()
        mBarrageLayout.addItems(initBarrageItems) //在弹幕启动前后都可以设置数据
        mSendBtn.onClick(this::onSendClick) //注册发送按钮点击事件
        val appBar = supportActionBar ?: return
        appBar.title = "弹幕"
        appBar.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Activity销毁时清空弹幕数据
     */
    override fun onDestroy() {
        super.onDestroy()
        mBarrageLayout.onDestroy()
    }

    /**
     * 发送按钮被点击
     */
    private fun onSendClick(view: View?) {
        val message = mBarrageEdit.text.toString()
        if (message.isNotBlank()) {
            mBarrageEdit.setText("")
            val item = object : AbsBarrageItem() {
                override fun getText(): String = message
                override fun getBackgroundResource(): Int = R.drawable.shape_radius_alpha_black
                override fun getTextColor(): Int = mTextColor[(Math.random() * mTextColor.size).toInt()]
                override fun avatarEnable(): Boolean = true
                override fun inflateAvatar(avatar: ImageView) = avatar.inflateCircle(mAvatarList[(Math.random() * mAvatarList.size).toInt()])
            }
            mBarrageLayout.addItem(item)
        }
    }

    private val mTextColor = intArrayOf(
            Color.parseColor("#3366ff"),
            Color.WHITE,
            Color.parseColor("#66ff99"),
            Color.parseColor("#ff9966"))

    private val mAvatarList = intArrayOf(
            R.drawable.avatar_kakashi_1,
            R.drawable.avatar_konan_1,
            R.drawable.avatar_madara_1,
            R.drawable.avatar_minato_1,
            R.drawable.avatar_naruto_1,
            R.drawable.avatar_sakura_1,
            R.drawable.avatar_sasuke_1
    )

    private val mMessageList = arrayOf(
            "他们的痛苦使我成长",
            "我已经在无限存在的痛苦之中",
            "这就是神的使命",
            "要让世界成长就要让他知道什么叫痛苦",
            "感受痛苦吧",
            "体验痛苦吧",
            "接受痛苦吧",
            "了解痛苦吧",
            "你为了你的正义，我为了我的正义",
            "人类是无论如何都不会互相理解的愚蠢生物",
            "如果和平真的存在，那么就让我来实现它",
            "是神的命令!要杀了你!",
            "因为这就是我的“正义”")

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