package github.cccxm.mydemo.utils

import android.view.Menu

/**
 * Created by cxm
 * on 2017/3/3.
 */

open class _Menu(val menu: Menu) {
    fun group(title: String, init: _Menu.() -> Unit) {
        val newMenu = menu.addSubMenu(title)
        _Menu(newMenu).init()
    }

    fun item(title: String, onClick: (() -> Unit)? = null) {
        menu.add(title)
    }
}

fun menuView(menu: Menu, init: _Menu.() -> Unit) {
    val m = _Menu(menu)
    m.init()
}