package github.cccxm.mydemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.cccxm.mydemo.android.effect.animation.AnimationListActivity
import github.cccxm.mydemo.android.effect.linkage.LinkageListActivity
import github.cccxm.mydemo.android.extension.mapbox.MapBoxActivity
import github.cccxm.mydemo.android.layout.flow.FlowListActivity
import github.cccxm.mydemo.android.material.bar.AppBarListActivity
import github.cccxm.mydemo.android.view.circle.CircleListActivity
import github.cccxm.mydemo.utils.group
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringGroupAdapter
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {

    private val ui = MainUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        title = "Android"
    }
}

private class MainUI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            expandableListView {
                lparams(matchParent, matchParent)
                simpleStringGroupAdapter {
                    group("Material Design") {
                        item("App Bar") { startActivity<AppBarListActivity>() }
                        item("Settings")
                    }
                    group("特效") {
                        item("动画") { startActivity<AnimationListActivity>() }
                        item("联动") { startActivity<LinkageListActivity>() }
                    }
                    group("View") {
                        item("圆形控件") { startActivity<CircleListActivity>() }
                        item("对话框")
                    }
                    group("Layout") {
                        item("FlowLayout") { startActivity<FlowListActivity>() }
                    }
                    group("数据操作") {
                        item("网络请求")
                        item("数据库")
                        item("联系人")
                    }
                    group("Android通信机制") {
                        item("进程内通信")
                        item("AIDL通信")
                        item("Remote View")
                    }
                    group("NDK") {
                        item("OpenGL ES")
                    }
                    group("系统组件") {
                        item("打电话")
                        item("发短信")
                        item("打开浏览器")
                        item("系统分享")
                        item("发送邮件")
                    }
                    group("我的扩展") {
                        item("MapBox") { startActivity<MapBoxActivity>() }
                        item("CrazyPlatte")
                    }
                }
            }
        }
    }
}