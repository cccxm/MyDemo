package github.cccxm.condition.api

import github.cccxm.condition.annotation.Condition
import github.cccxm.condition.annotation.Conditions
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*

/**
 *  Copyright (C) <2017>  <陈小默>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  Created by 陈小默 on 5/07.
 */
@Suppress("UNCHECKED_CAST")
class ConditionUtil {
    companion object {
        @JvmStatic fun <I> create(clazz: Class<I>, target: () -> Unit): I {
            if (clazz.isInterface) {
                val handler = ConditionHandler(clazz, target)
                val proxyObject = Proxy.newProxyInstance(clazz.classLoader, arrayOf(clazz), handler)
                return proxyObject as I
            } else throw ClassCastException("'ConditionUtil.create' need a interface class")
        }
    }
}

private class ConditionHandler(clazz: Class<*>, private val target: () -> Unit) : InvocationHandler {
    private val mConditionMap = HashMap<Method, ConditionEntry>()
    private var mOverFlag = false

    init {
        clazz.declaredMethods.forEach {
            val annotation = it.getAnnotation(Condition::class.java)
            if (annotation != null)
                mConditionMap[it] = ConditionEntry(annotation.cond)
        }
    }

    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {
        if (!mOverFlag) {
            var res = true
            if (args != null && args.size == 1) {
                val any = args[0]
                res = if (any is Boolean) any else true
            }
            mConditionMap[method]?.value = res
            if (getCondition(mConditionMap.values)) {
                mOverFlag = true
                target()
            }
        }
        return null
    }
}

private class ConditionEntry(val conditions: Conditions) {
    var value = false
}

private fun getCondition(list: Collection<ConditionEntry>): Boolean {
    val groupBy = list.groupBy { it.conditions }
    val a = computeAnd(groupBy[Conditions.AND])
    val o = computeOr(groupBy[Conditions.OR])
    val n = computeNot(groupBy[Conditions.NOT])
    return (!n) and a and o
}

private fun computeAnd(list: List<ConditionEntry>?): Boolean {
    return list?.map { it.value }?.scan { x, y -> (x ?: true) and (y ?: true) } ?: true
}

private fun computeOr(list: List<ConditionEntry>?): Boolean {
    return list?.map { it.value }?.scan { x, y -> (x ?: false) or (y ?: false) } ?: true
}

private fun computeNot(list: List<ConditionEntry>?): Boolean {
    return list?.map { it.value }?.scan { x, y -> (x ?: false) or (y ?: false) } ?: false
}

private fun <T> List<T>.scan(f: (T?, T?) -> T?): T? {
    var res: T? = null
    forEach { res = f(res, it) }
    return res
}
