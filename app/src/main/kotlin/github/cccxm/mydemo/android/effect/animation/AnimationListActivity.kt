package github.cccxm.mydemo.android.effect.animation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import github.cccxm.mydemo.utils.item
import github.cccxm.mydemo.utils.simpleStringItemAdapter
import org.jetbrains.anko.*

class AnimationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

private class AnimationListUI : AnkoComponent<AnimationListActivity> {
    override fun createView(ui: AnkoContext<AnimationListActivity>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            listView {
                lparams(matchParent, matchParent)
                simpleStringItemAdapter {
                    item("属性动画") {}
                }
            }
        }
    }
}
