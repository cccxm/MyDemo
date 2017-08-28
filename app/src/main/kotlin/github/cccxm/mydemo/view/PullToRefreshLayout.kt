package github.cccxm.mydemo.view

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet

/**
 * Created by cxm
 * on 2017/4/28.
 */
class PullToRefreshLayout : SwipeRefreshLayout {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
//        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.PullToRefreshLayout)
//        val layoutManagerAttr = typedArray?.getString(R.styleable.PullToRefreshLayout_LayoutManager) ?: "LinearLayoutManager"
//        val orientationAttr = typedArray?.getInt(R.styleable.PullToRefreshLayout_orientation, 1) ?: 1
//        mRecyclerView = recyclerView {
//            lparams(this@PullToRefreshLayout.layoutParams)
//            layoutManager = when (layoutManagerAttr) {
//                "LinearLayoutManager" -> {
//                    LinearLayoutManager(context, orientationAttr, false)
//                }
//                else -> {
//                    LinearLayoutManager(context)
//                }
//            }
//        }
//        typedArray?.recycle()
    }

//    private val mRecyclerView: RecyclerView

//    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
//        val wrapperAdapter = HeaderFooterWrapper(adapter)
//        wrapperAdapter.addFootView()
//    }
//
//    private class FooterBehaviour :CoordinatorLayout.Behavior<>
//
//    private class FooterView
}