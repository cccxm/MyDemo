package github.cccxm.mydemo.android.layout.recycler

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.simpleStringAdapter
import github.cccxm.mydemo.view.RefreshView
import kotlinx.android.synthetic.main.activity_recycler_refresh_view.*
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/28.
 */
class RecyclerRefreshViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_recycler_refresh_view)

        supportActionBar?.apply {
            title = "自定义刷新加载"
            setDisplayHomeAsUpEnabled(true)
        }
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mRecyclerView.simpleStringAdapter {
            for (i in 0..30) item("Item $i")
        }

        mRefreshLayout.refreshView = RecyclerRefreshView(this)
    }

//    private fun onRefresh() { //下拉刷新
//        mRefreshLayout.isRefreshing = true
//        Observable.timer((Math.random() * 1500 + 500).toLong(), TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    mRefreshLayout.isRefreshing = false
//                    mRecyclerView.simpleStringAdapter {
//                        for (i in 0..30) item("Item $i")
//                    }
//                }
//    }

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

private class RecyclerRefreshView(context: Context) : RefreshView {
    override val freshView: View = with(context) {
        relativeLayout {
            textView("下拉刷新").lparams(wrapContent, wrapContent) {
                centerInParent()
            }
        }
    }
    override val loadingView: View = with(context) {
        relativeLayout {
            textView("上拉加载").lparams(wrapContent, wrapContent) {
                centerInParent()
            }
        }
    }

    override fun onDrop(view: View, progress: Int) {
        view.backgroundColor = Color.WHITE + progress * 255
    }

    override fun onStartAnimation(view: View) {
    }

    override fun onStopAnimation(view: View) {
    }

    override fun onEmpty(view: View) {
    }

    override fun onError(view: View, error: Throwable) {
    }

    override fun onRefresh() {
    }

    override fun onLoad() {
    }
}