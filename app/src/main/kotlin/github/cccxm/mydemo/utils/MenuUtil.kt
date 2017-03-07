package github.cccxm.mydemo.utils

import android.util.SparseArray
import android.view.Menu
import android.view.MenuItem
import java.util.*

/**
 * Created by cxm
 * on 2017/3/3.
 */
private var menuId = 0

open class _Menu(val groupId: Int, val menu: Menu) {
    val click = HashMap<Int, (() -> Unit)?>()
    fun group(title: String, init: _Menu.() -> Unit) {
        val itemId = ++menuId
        val newMenu = menu.addSubMenu(groupId, itemId, Menu.NONE, title)
        val _menu = _Menu(++menuId, newMenu)
        _menu.init()
        for ((key, value) in _menu.click) {
            click[key] = value
        }
    }

    fun item(title: String, listener: (() -> Unit)? = null) {
        val itemId = ++menuId
        click[itemId] = listener
        menu.add(groupId, itemId, Menu.NONE, title)
    }
}

open class MenuManager constructor(private val listenerArray: HashMap<Int, (() -> Unit)?>) {
    fun onclick(item: MenuItem) {
        listenerArray[item.itemId]?.invoke()
    }
}

fun menuView(menu: Menu, init: _Menu.() -> Unit): MenuManager {
    val m = _Menu(++menuId, menu)
    m.init()
    val click = HashMap<Int, (() -> Unit)?>()
    for ((key, value) in m.click) {
        click[key] = value
    }
    return MenuManager(click)
}