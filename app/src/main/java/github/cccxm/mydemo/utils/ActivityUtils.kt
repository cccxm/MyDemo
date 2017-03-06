package github.cccxm.mydemo.utils

import android.app.Activity

/**
 * Created by cxm
 * on 2017/3/6.
 */

fun Activity.string(id: Int): String {
    return this.getString(id)
}