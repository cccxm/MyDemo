package github.cccxm.mydemo.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.io.*
import kotlin.reflect.KProperty

private val `SpUtil$$mCache` = HashMap<Any, SharedPreferences>()

object SpUtil {

    /**
     * 注册SharedPreference映射对象
     */
    fun <T : Any> register(context: Context, any: T): T {
        val preferences = `SpUtil$$mCache`[any]
        if (preferences != null) {
            throw RuntimeException("this object already register , don't register repeat")
        }
        val name = any.javaClass.name
        val sp = context.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)
        `SpUtil$$mCache`[any] = sp
        return any
    }

    /**
     * 使用完成之后应该在合适的位置取消注册，以释放内存，防止内存泄漏
     */
    fun unRegister(any: Any) {
        `SpUtil$$mCache`.remove(any)
    }
}

/**
 * Int 类型的映射委托
 */
object SpInt {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Int {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getInt(property.name, 0)
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        preferences.edit().putInt(property.name, value).apply()
    }
}

/**
 * Boolean 类型的映射委托
 */
object SpBoolean {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getBoolean(property.name, false)
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        preferences.edit().putBoolean(property.name, value).apply()
    }
}

/**
 * Float 类型的映射委托
 */
object SpFloat {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Float {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getFloat(property.name, 0f)
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        preferences.edit().putFloat(property.name, value).apply()
    }
}

/**
 * Long 类型的映射委托
 */
object SpLong {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Long {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getLong(property.name, 0L)
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        preferences.edit().putLong(property.name, value).apply()
    }
}

/**
 * String 类型的映射委托
 */
object SpString {
    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getString(property.name, "")
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        preferences.edit().putString(property.name, value).apply()
    }
}

/**
 * StringSet 类型的映射委托
 */
object SpStringSet {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Set<String> {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getStringSet(property.name, HashSet<String>())
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Set<String>) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        preferences.edit().putStringSet(property.name, value).apply()
    }
}

/**
 * Serializable 类型的映射委托
 *
 * 使用该委托对象时，需要传入一个默认值
 */
@Suppress("UNCHECKED_CAST")
class SpSerializable<T : Serializable>(private val default: T) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        val string = preferences.getString(property.name, null) ?: return default
        var objectBuffer: ObjectInputStream? = null
        try {
            val bytes = Base64.decode(string, Base64.DEFAULT)
            val buffer = ByteArrayInputStream(bytes)
            objectBuffer = ObjectInputStream(buffer)
            val readObject = objectBuffer.readObject()
            return readObject as T
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (objectBuffer != null)
                try {
                    objectBuffer.close()
                } catch (e: Exception) {
                }
        }
        return default
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        var objectBuffer: ObjectOutputStream? = null
        try {
            val buffer = ByteArrayOutputStream()
            objectBuffer = ObjectOutputStream(buffer)
            objectBuffer.writeObject(value)
            val bytes = buffer.toByteArray()
            val code = Base64.encode(bytes, Base64.DEFAULT)
            preferences.edit().putString(property.name, String(code)).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (objectBuffer != null)
                try {
                    objectBuffer.close()
                } catch (e: Exception) {
                }
        }
    }
}