package github.cccxm.mydemo.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import org.jetbrains.anko.*

private interface Contract {
    interface View {
        fun showWeb(detail: String)
    }
}

class LearnDetailActivity : AppCompatActivity() {
    private val ui = LearnDetailUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ui.setContentView(this))
        val markdown = intent.extras.getString("markdown", "")
        ui.showWeb(markdown)
    }
}

private class LearnDetailUI : AnkoComponent<LearnDetailActivity>, Contract.View {
    private lateinit var mWebView: WebView

    override fun createView(ui: AnkoContext<LearnDetailActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            mWebView = webView {
                lparams(matchParent, matchParent)
            }
        }
    }

    override fun showWeb(detail: String) {
        mWebView.loadData(detail, "http/html", "utf-8")
    }
}