package github.cccxm.mydemo.android.view.circle

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class CardViewCircleActivity : AppCompatActivity() {

    private val ui = CardViewCircleUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
    }
}

private class CardViewCircleUI : AnkoComponent<CardViewCircleActivity> {
    override fun createView(ui: AnkoContext<CardViewCircleActivity>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            gravity = Gravity.CENTER
            cardView {
                lparams(dip(100), dip(100))
                cardElevation = 14f
                setCardBackgroundColor(Color.BLUE)
                radius = 100f
            }
        }
    }
}
