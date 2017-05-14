package github.cccxm.mydemo.android.data.net

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import github.cccxm.mydemo.utils.logger
import org.greenrobot.eventbus.EventBus
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by cxm
 * on 2017/3/27.
 */
class BackgroundService : Service(), ServiceDownloadContract.Model {
    private var onNext: ((Int) -> Unit)? = null
    /*
    记录当前位置防止再次打开页面时显示0进度
     */
    private var curProgress = 0
    /*
    是否正在下载，用来当作是否关闭Service的参考
     */
    private var isDownloading = false

    override fun onCreate() {
        logger("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger("onStartCommand")
        EventBus.getDefault().post(this)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        logger("onBind")
        return Binder()
    }

    override fun onDestroy() {
        logger("onDestroy")
    }

    override fun onProgress(onNext: (Int) -> Unit) {
        this.onNext = onNext
    }

    override fun removeListener() {
        onNext = null
        close()
    }

    override fun download(url: String) {
        isDownloading = true
        Observable.interval(200, TimeUnit.MILLISECONDS)
                .take(100)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    curProgress = it.toInt()
                    onNext?.invoke(curProgress)
                }, {}, {
                    isDownloading = false
                    close()
                })
    }

    private fun close() {
        if (!isDownloading && onNext == null)
            stopSelf()
    }
}
