package github.cccxm.mydemo.utils

import java.io.Closeable
import java.io.IOException

/**
 * Created by cxm
 * on 2017/4/24.
 */

fun Closeable?.safeClose() {
    if (this != null)
        try {
            close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
}
