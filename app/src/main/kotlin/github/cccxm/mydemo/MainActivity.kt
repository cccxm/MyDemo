package github.cccxm.mydemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import github.cccxm.mydemo.android.adapter.screen.ScreenAdapterActivity
import github.cccxm.mydemo.android.data.net.NetRequestActivity
import github.cccxm.mydemo.android.effect.animation.AnimationListActivity
import github.cccxm.mydemo.android.effect.drag.DragListActivity
import github.cccxm.mydemo.android.effect.linkage.LinkageListActivity
import github.cccxm.mydemo.android.extension.mapbox.MapBoxActivity
import github.cccxm.mydemo.android.layout.flow.FlowListActivity
import github.cccxm.mydemo.android.layout.im.IMLayoutActivity
import github.cccxm.mydemo.android.layout.recycler.RecyclerListActivity
import github.cccxm.mydemo.android.material.bar.AppBarListActivity
import github.cccxm.mydemo.android.ndk.bitmap.NDKBitmapActivity
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
                        item("拖动") { startActivity<DragListActivity>() }
                    }
                    group("View") {
                        item("圆形控件") { startActivity<CircleListActivity>() }
                        item("对话框")
                    }
                    group("Layout") {
                        item("FlowLayout") { startActivity<FlowListActivity>() }
                        item("RecyclerView") { startActivity<RecyclerListActivity>() }
                        item("IMLayout") { startActivity<IMLayoutActivity>() }
                    }
                    group("数据操作") {
                        item("网络请求") { startActivity<NetRequestActivity>() }
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
                        item("图片处理") { startActivity<NDKBitmapActivity>() }
                    }
                    group("系统组件") {
                        item("打电话")
                        item("发短信")
                        item("打开浏览器")
                        item("系统分享")
                        item("发送邮件")
                    }
                    group("适配") {
                        item("屏幕适配") { startActivity<ScreenAdapterActivity>() }
                        item("系统适配") {}
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