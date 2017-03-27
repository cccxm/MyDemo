package github.cccxm.mydemo.android.data.net

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import github.cccxm.mydemo.utils.logger
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by cxm
 * on 2017/3/27.
 */
class BackgroundService : Service(), ServiceDownloadContract.Model {
    private var listener: ((Int) -> Unit)? = null
    private var curProgress = 0

    override fun onCreate() {
        logger("onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger("onStartCommand")
        EventBus.getDefault().post(this)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onRebind(intent: Intent?) {
        logger("onRebind")
        super.onRebind(intent)
    }

    override fun onBind(intent: Intent?): IBinder {
        logger("onBind")
        return Binder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logger("onUnbind")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        logger("onDestroy")
    }

    override fun setProgressListener(listener: (Int) -> Unit) {
        this.listener = listener
        listener(curProgress)
    }

    override fun download(url: String) {
        doAsync {
            for (i in 0..100) {
                curProgress = i
                uiThread {
                    listener?.invoke(i)
                }
                Thread.sleep(100)
            }
        }
    }
}
