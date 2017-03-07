package github.cccxm.mydemo.utils

import android.util.Log

/**
 * Created by cxm
 * on 2017/3/2.
 */

fun Any.logger(msg: Any?) {
    Log.e(javaClass.simpleName, "$msg")
}