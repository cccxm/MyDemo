package github.cccxm.mydemo.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.alert

/**
 * Created by
 * cxm on 2017/3/7.
 */
private var permissionCode = 0

data class _Permission(val message: String?, val granted: (Boolean) -> Unit)

object PermissionManager {
    val permissionList = HashMap<Int, _Permission>()
    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissionList.containsKey(requestCode)) {
            val granted = permissionList.remove(requestCode) ?: return
            val isTip = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])
            val isDenied = grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED
            if (isDenied) {
                if (isTip) {
                    val code = ++permissionCode
                    PermissionManager.permissionList[requestCode] = granted
                    ActivityCompat.requestPermissions(activity, permissions, code)
                } else {
                    granted.granted.invoke(false)
                    if (granted.message != null) activity.alert(message = granted.message, title = "权限提醒").show()
                }
            } else
                granted.granted.invoke(true)
        }
    }
}

fun Activity.locationPermission(message: String? = null, granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.LOCATION) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(message, granted)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
    }
}
