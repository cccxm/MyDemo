package github.cccxm.mydemo.android.data.net

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.color
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout

/**
 * Created by cxm
 * on 2017/3/27.
 */
interface ServiceDownloadContract {
    interface UI {
        fun initAppBar(activity: AppCompatActivity)
        fun setProgress(progress: Int)
    }

    interface Presenter {
        fun start(model: ServiceDownloadContract.Model)
        fun downloadClick()
    }

    interface Model {
        fun onProgress(onNext: (Int) -> Unit)
        fun download(url: String)
        fun removeListener()
    }
}

class ServiceDownloadActivity : AppCompatActivity(), ServiceDownloadContract.Presenter {
    private val ui = ServiceDownloadUI()
    private lateinit var model: ServiceDownloadContract.Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        startService<BackgroundService>()
    }

    @Subscribe
    override fun start(model: ServiceDownloadContract.Model) {
        this.model = model
        setContentView(ui.setContentView(this))
        ui.initAppBar(this)
        model.onProgress(ui::setProgress)
    }

    override fun onDestroy() {
        super.onDestroy()
        model.removeListener()
        EventBus.getDefault().unregister(this)
    }

    override fun downloadClick() {
        model.download("www.google.com")
    }
}

private class ServiceDownloadUI : AnkoComponent<ServiceDownloadActivity>, ServiceDownloadContract.UI {
    private lateinit var mToolBar: Toolbar
    private lateinit var mSeekBar: SeekBar

    override fun createView(ui: AnkoContext<ServiceDownloadActivity>): View = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            lparams(matchParent, matchParent)
            appBarLayout(theme = R.style.AppTheme_AppBarOverlay) {
                toolbar {
                    mToolBar = this
                    backgroundColor = color("#ff0088")
                    title = "后台下载"
                }.lparams(matchParent, dimen(R.dimen.tool_bar_height))
            }.lparams(matchParent, dimen(R.dimen.tool_bar_height))
            linearLayout {
                orientation = LinearLayout.VERTICAL
                button("下载").onClick { ui.owner.downloadClick() }
                seekBar {
                    mSeekBar = this
                    max = 100
                }.lparams(matchParent, wrapContent) { padding = dip(15) }
            }.lparams(matchParent, matchParent)
        }
    }

    override fun setProgress(progress: Int) {
        if (progress <= 100)
            mSeekBar.progress = progress
    }

    override fun initAppBar(activity: AppCompatActivity) {
        with(activity) {
            setSupportActionBar(mToolBar)
            val actionBar = supportActionBar ?: return
            actionBar.setDisplayHomeAsUpEnabled(true)
            mToolBar.setNavigationOnClickListener { onBackPressed() }
        }
    }
}