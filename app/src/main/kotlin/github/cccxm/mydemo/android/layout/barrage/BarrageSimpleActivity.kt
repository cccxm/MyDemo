package github.cccxm.mydemo.android.layout.barrage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import github.cccxm.mydemo.R
import github.cccxm.mydemo.view.BarrageItem
import kotlinx.android.synthetic.main.activity_barrage_simple.*
import java.util.*

/**
 * Created by cxm
 * on 2017/5/2.
 */
class BarrageSimpleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barrage_simple)

        supportActionBar?.apply {
            title = "弹幕"
            setDisplayHomeAsUpEnabled(true)
        }
        val items = LinkedList<BarrageItem>()
        for (i in 0..30) items.add(BarrageSimpleItem())
        mBarrageLayout.prepare(items)
    }

    override fun onStart() {
        super.onStart()
        mBarrageLayout.onStart()
    }

    override fun onStop() {
        super.onStop()
        mBarrageLayout.onStop()
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

private class BarrageSimpleItem : BarrageItem() {
    override fun getText(): String = "吾王剑之所指，吾民心之所向"
}