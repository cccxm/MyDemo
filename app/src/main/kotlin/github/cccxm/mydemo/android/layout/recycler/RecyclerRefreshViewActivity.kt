package github.cccxm.mydemo.android.layout.recycler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import github.cccxm.mydemo.R

/**
 * Created by cxm
 * on 2017/4/28.
 *
 * RecyclerView上拉刷新下拉加载
 */
class RecyclerRefreshViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recycler_refresh_view)

        supportActionBar?.apply {
            title = "自定义刷新加载"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}