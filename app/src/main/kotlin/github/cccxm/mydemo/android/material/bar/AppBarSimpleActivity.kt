package github.cccxm.mydemo.android.material.bar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import github.cccxm.mydemo.utils.menuView
import org.jetbrains.anko.*

class AppBarSimpleActivity : AppCompatActivity() {
    private val ui = AppBarSimpleUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        val actionBar = supportActionBar ?: return
        actionBar.title = "Hello World"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuView(menu) {
            item("item1")
            item("item2")
            group("group") {
                item("item3")
                item("item4")
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}

private class AppBarSimpleUI : AnkoComponent<AppBarSimpleActivity> {
    override fun createView(ui: AnkoContext<AppBarSimpleActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)

        }
    }
}


