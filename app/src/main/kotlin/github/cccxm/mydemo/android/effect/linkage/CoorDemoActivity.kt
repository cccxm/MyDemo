package github.cccxm.mydemo.android.effect.linkage

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.Gravity
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import github.cccxm.mydemo.utils.color
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout

/**
 * Created by cxm
 * on 2017/4/25.
 */
class CoorDemoActivity : AppCompatActivity() {
    private val ui = CoorDemoUI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))

        val appBar = supportActionBar ?: return
        appBar.title = "相互影响的View"
        appBar.setDisplayHomeAsUpEnabled(true)
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

private class CoorDemoUI : AnkoComponent<CoorDemoActivity> {

    override fun createView(ui: AnkoContext<CoorDemoActivity>): View = with(ui) {
        coordinatorLayout {
            addView(CoorDemoMoveView(context).apply {
                gravity = Gravity.CENTER
                text = "MOVE"
                layoutParams = CoordinatorLayout.LayoutParams(dip(100), dip(100)).apply {
                    backgroundColor = color("#3388ff")
                    textColor = Color.WHITE
                }
            })

            textView("事件消费者View") {
                textColor = Color.WHITE
                backgroundColor = color("#ff8833")
                gravity = Gravity.CENTER
            }.lparams(dip(100), dip(100)) {
                behavior = CoorDemoBehavior(context)
            }
        }
    }
}

private class CoorDemoBehavior(context: Context, attrs: AttributeSet? = null) : CoordinatorLayout.Behavior<TextView>(context, attrs) {
    private val width: Int
    private val height: Int

    init {
        val displayMetrics = context.resources.displayMetrics
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels
    }

    /**
     * 该方法用来确定事件源和消费者之间的关系，如果返回true，则该事件源发出的事件将传递给此消费者
     *
     * @param parent     CoordinatorLayout
     * @param child      该对象是被绑定对象，也是消费者对象
     * @param dependency 该对象是被依赖对象，也是生产者对象
     * @return true 绑定
     */
    override fun layoutDependsOn(parent: CoordinatorLayout, child: TextView, dependency: View) = dependency is CoorDemoMoveView

    /**
     * 当事件源发出布局改变时的回调
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: TextView, dependency: View): Boolean {
        val top = dependency.top
        val left = dependency.left
        val x = width - left - child.width
        val y = height - top - child.height
        val layoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.leftMargin = x
        layoutParams.topMargin = y
        child.layoutParams = layoutParams
        return true
    }
}

private class CoorDemoMoveView : TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var lastX = 0f
    private var lastY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.rawX
        val y = event.rawY
        if (event.action == MotionEvent.ACTION_MOVE) {
            val layoutParams = layoutParams as CoordinatorLayout.LayoutParams
            //计算当前View的左上角坐标
            val left = layoutParams.leftMargin + x - lastX
            val top = layoutParams.topMargin + y - lastY
            //设置坐标
            layoutParams.leftMargin = left.toInt()
            layoutParams.topMargin = top.toInt()
            this.layoutParams = layoutParams
        }
        lastX = x
        lastY = y
        return true
    }
}