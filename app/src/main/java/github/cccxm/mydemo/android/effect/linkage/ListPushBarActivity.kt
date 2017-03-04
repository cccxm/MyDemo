package github.cccxm.mydemo.android.effect.linkage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.simpleCardAdapter
import kotlinx.android.synthetic.main.activity_list_push.*

/**
 * 该Demo展示的是ListView上滑时推动ActionBar回收，该示例的详细说明在XML文件中
 */
class ListPushBarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_push)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        with(recycle_effect_linkage_push_bar) {
            layoutManager = LinearLayoutManager(applicationContext)
            simpleCardAdapter {
                for (i in 0..50)
                    item("Item $i")
            }
        }
    }
}