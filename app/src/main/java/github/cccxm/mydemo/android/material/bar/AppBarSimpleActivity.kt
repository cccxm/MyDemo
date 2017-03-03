package github.cccxm.mydemo.android.material.bar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import org.jetbrains.anko.*

class AppBarSimpleActivity : AppCompatActivity() {
    private val ui = AppBarSimpleUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        supportActionBar?.title = "Hello World"
    }
}

private class AppBarSimpleUI : AnkoComponent<AppBarSimpleActivity> {
    override fun createView(ui: AnkoContext<AppBarSimpleActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)

        }
    }

}


