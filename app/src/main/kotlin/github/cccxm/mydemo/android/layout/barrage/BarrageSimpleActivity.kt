package github.cccxm.mydemo.android.layout.barrage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import github.cccxm.mydemo.R

/**
 * Created by cxm
 * on 2017/5/2.
 */
class BarrageSimpleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barrage_simple)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}