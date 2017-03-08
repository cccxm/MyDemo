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
            val result = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            granted.granted.invoke(result)
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                if (granted.message != null) activity.alert(title = "权限说明", message = granted.message).show()
            }
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
