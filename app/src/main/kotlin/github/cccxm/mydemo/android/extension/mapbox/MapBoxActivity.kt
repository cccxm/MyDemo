package github.cccxm.mydemo.android.extension.mapbox

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.mapbox.mapboxsdk.MapboxAccountManager
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.constants.MyLocationTracking
import com.mapbox.mapboxsdk.constants.Style
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationListener
import com.mapbox.mapboxsdk.location.LocationServices
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.services.geocoding.v5.GeocodingCriteria
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.*
import kotlinx.android.synthetic.main.activity_map_box.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.onClick


class MapBoxActivity : AppCompatActivity() {
    private lateinit var mMapBoxMap: MapboxMap
    private lateinit var mMenuManager: MenuManager
    private lateinit var mLocationService: LocationServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapboxAccountManager.start(this, string(R.string.mapbox_token))
        setContentView(R.layout.activity_map_box)
        initActionBar()
        prepare(savedInstanceState)
        registerListener()
    }

    /**
     * 给控件注册监听
     */
    private fun registerListener() {
        fab_mapview_location.onClick {
            if (!mMapBoxMap.isMyLocationEnabled) {
                locationPermission { enableLocation(it) }
            } else {
                enableLocation(false)
            }
        }
        map_box_search.setAccessToken(MapboxAccountManager.getInstance().accessToken)
        map_box_search.setType(GeocodingCriteria.TYPE_POI)
        map_box_search.setOnFeatureListener {
            val position = it.asPosition()
            inputMethodManager.hideSoftInputFromWindow(map_box_search.applicationWindowToken, 0)
            updateMap(it.text, position.latitude, position.longitude)
        }
    }

    /**
     * 更新地图显示的位置
     */
    private fun updateMap(tag: String, latitude: Double, longitude: Double) {
        mMapBoxMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title(tag))
        val cameraPosition = CameraPosition.Builder()
                .target(LatLng(latitude, longitude))
                .zoom(15.0)
                .build()
        mMapBoxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000, null)
    }

    /**
     * 初始化ActionBar
     */
    private fun initActionBar() {
        toolbar_map_box.title = "MapBox"
        setSupportActionBar(toolbar_map_box)
        val actionBar = supportActionBar ?: return
        actionBar.setDisplayHomeAsUpEnabled(true)
        toolbar_map_box.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * 初始化MapView，初始化完毕时回调 onMapPrepared
     */
    private fun prepare(savedInstanceState: Bundle?) {
        mapbox_mapview.onCreate(savedInstanceState)
        mapbox_mapview.getMapAsync { onMapPrepared(it) }
    }

    /**
     * 地图初始化完毕时回调，并进行定位
     */
    private fun onMapPrepared(map: MapboxMap) {
        mMapBoxMap = map
        mLocationService = LocationServices.getLocationServices(this)
        initMyLocationStyle()
        locationPermission { enableLocation(it) }
    }

    private fun enableLocation(enable: Boolean) {
        if (enable) {
            val lastLocation = mLocationService.lastLocation
            if (lastLocation != null) {
                mMapBoxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lastLocation), 16f))
            }
            mLocationService.addLocationListener(object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                        mMapBoxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lastLocation), 16f))
                        mLocationService.removeLocationListener(this)
                    }
                }
            })
            mMapBoxMap.isMyLocationEnabled = true
            fab_mapview_location.imageResource = R.drawable.ic_location_disabled_24dp
        } else {
            mMapBoxMap.isMyLocationEnabled = false
            fab_mapview_location.imageResource = R.drawable.ic_my_location_24dp
        }
    }

    /**
     * 地图上显示我的位置时的样式
     */
    private fun initMyLocationStyle() {
        with(mMapBoxMap) {
            trackingSettings.myLocationTrackingMode = MyLocationTracking.TRACKING_FOLLOW
            trackingSettings.setDismissAllTrackingOnGesture(true) //是否允许手势移动坐标点
            myLocationViewSettings.setPadding(0, 0, 0, 0)
            myLocationViewSettings.foregroundTintColor = Color.parseColor("#56B881")
            myLocationViewSettings.accuracyTintColor = Color.parseColor("#FBB03B")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        mMenuManager = menuView(menu) {
            group("地图风格") {
                item("默认") { mMapBoxMap.styleUrl = Style.MAPBOX_STREETS }
                item("白色") { mMapBoxMap.styleUrl = Style.LIGHT }
                item("黑色") { mMapBoxMap.styleUrl = Style.DARK }
                item("卫星视图") { mMapBoxMap.styleUrl = Style.SATELLITE }
                item("卫星视图街道") { mMapBoxMap.styleUrl = Style.SATELLITE_STREETS }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mMenuManager.onclick(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        mapbox_mapview.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapbox_mapview.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapbox_mapview.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapbox_mapview.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapbox_mapview.onDestroy()
    }
}