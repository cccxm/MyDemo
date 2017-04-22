package github.cccxm.mydemo.android.effect.drag.tantan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import github.cccxm.mydemo.R
import github.cccxm.mydemo.utils.CommonItemAdapter
import org.jetbrains.anko.*

/**
 * Created by cxm
 * on 2017/4/14.
 */
class TanTanDragActivity : AppCompatActivity() {
    private val ui = TanTanDragUI()
    private val resArr = listOf(
            R.drawable.demo_hy_1,
            R.drawable.demo_hy_2,
            R.drawable.demo_hy_3,
            R.drawable.demo_hy_4,
            R.drawable.demo_hy_5,
            R.drawable.demo_hy_6)
    private val mAdapter = TanTanAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        ui.mAlbumView.setAdapter(mAdapter)
        mAdapter.addItems(resArr)

        val appBar = supportActionBar ?: return
        appBar.title = "仿探探的拖拽"
        appBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

private class TanTanAdapter : CommonItemAdapter<Int, TanTanHolder>() {
    override fun onBindView(holder: TanTanHolder, item: Int) {
        (holder.itemView as ImageView).setImageResource(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TanTanHolder {
        val imageView = ImageView(parent.context).apply { scaleType = ImageView.ScaleType.CENTER_CROP }
        return TanTanHolder(imageView)
    }
}

private class TanTanHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

private class TanTanDragUI : AnkoComponent<TanTanDragActivity> {
    lateinit var mAlbumView: AlbumView
    lateinit var mGridLayout: GridLayout
    override fun createView(ui: AnkoContext<TanTanDragActivity>): View = with(ui) {
        relativeLayout {
            gridLayout { mGridLayout = this }.lparams(matchParent, matchParent)
            addView(with(AlbumView(ui.ctx)) {
                mAlbumView = this
                setRootView(mGridLayout)
                layoutParams = RelativeLayout.LayoutParams(matchParent, wrapContent)
                mAlbumView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        mAlbumView.viewTreeObserver.removeOnPreDrawListener(this)
                        layoutParams = RelativeLayout.LayoutParams(matchParent, height)
                        return true
                    }
                })
                this
            })
        }
    }
}