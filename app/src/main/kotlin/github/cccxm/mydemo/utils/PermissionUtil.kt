package github.cccxm.mydemo.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.locationManager
import java.util.*

/**
 * Created by
 * cxm on 2017/3/7.
 */
private var permissionCode = 0

data class _Permission(val granted: (Boolean) -> Unit)

object PermissionManager {
    @SuppressLint("UseSparseArrays")
    val permissionList = HashMap<Int, _Permission>()

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissionList.containsKey(requestCode)) {
            val permission = permissionList.remove(requestCode) ?: return
            permission.granted(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }
}

/**
 * 申请定位权限
 */
fun Activity.locationPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.LOCATION) == PackageManager.PERMISSION_GRANTED) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            granted(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        } else {
            granted(true)
        }
    } else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
    }
}

/**
 * 申请读取内存卡的权限
 */
fun Activity.storagePermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
    }
}

/**
 * 申请读取联系人的权限
 */
fun Activity.contactsPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.CONTACTS) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_CONTACTS), requestCode)
    }
}

/**
 * 申请获得设备信息的权限
 */
fun Activity.phonePermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.PHONE) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_CALL_LOG,
                Manifest.permission.USE_SIP,
                Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.ADD_VOICEMAIL), requestCode)
    }
}

/**
 * 申请读取日历的权限
 */
fun Activity.calendarPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.CALENDAR) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR), requestCode)
    }
}

/**
 * 申请使用相机的权限
 */
fun Activity.cameraPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.CAMERA) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.CAMERA), requestCode)
    }
}

/**
 * 申请使用传感器的权限
 */
fun Activity.sensorsPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.SENSORS) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.BODY_SENSORS), requestCode)
    }
}

/**
 * 申请使用麦克风的权限
 */
fun Activity.microphonePermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.MICROPHONE) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.RECORD_AUDIO), requestCode)
    }
}

/**
 * 申请操作短信的权限
 */
fun Activity.smsPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.SMS) == PackageManager.PERMISSION_GRANTED)
        granted(true)
    else {
        val requestCode = ++permissionCode
        PermissionManager.permissionList[requestCode] = _Permission(granted)
        ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_WAP_PUSH,
                Manifest.permission.RECEIVE_MMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.SEND_SMS), requestCode)
    }
}