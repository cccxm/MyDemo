package github.cccxm.mydemo.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.io.*
import java.util.concurrent.Executors
import kotlin.reflect.KProperty

private val `SpUtil$$mCache` = HashMap<Any, SharedPreferences>()

/**
 * 用于映射SharedPreferences文件的对象
 */
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
     * 使用完成之后应该在合适的位置取消注册，以释放内存，防止内存泄漏。
     * 注意：取消注册之后应该立即将该对象释放，以免不小心调用导致异常。
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
 * Int 类型的映射委托
 */
class SpDefaultInt(private val default: Int) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Int {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getInt(property.name, default)
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
 * Boolean 类型的映射委托
 */
class SpDefaultBoolean(private val default: Boolean) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getBoolean(property.name, default)
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
 * Float 类型的映射委托
 */
class SpDefaultFloat(private val default: Float) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Float {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getFloat(property.name, default)
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
 * Long 类型的映射委托
 */
class SpDefaultLong(private val default: Long) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Long {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getLong(property.name, default)
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
 * String 类型的映射委托
 */
class SpDefaultString(private val default: String) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): String {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getString(property.name, default)
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
 * StringSet 类型的映射委托
 */
class SpDefaultStringSet(private val default: Set<String>) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): Set<String> {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        return preferences.getStringSet(property.name, default)
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: Set<String>) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        preferences.edit().putStringSet(property.name, value).apply()
    }
}

/**
 * Serializable 类型的映射委托
 */
@Suppress("UNCHECKED_CAST")
class SpSerializable<T : Serializable> {
    operator fun getValue(thisRef: Any, property: KProperty<*>): T? {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        val string = preferences.getString(property.name, null) ?: return null
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
        return null
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        if (value == null) {
            preferences.edit().putString(property.name, null).apply()
            return
        }
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

/**
 * Serializable 类型的映射委托
 *
 * 使用该委托对象时，需要传入一个默认值
 */
@Suppress("UNCHECKED_CAST")
class SpDefaultSerializable<T : Serializable>(private val default: T) {
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

/**
 * Serializable 异步类型的映射委托
 *
 * 其类型必须为可空，建议对于非基本数据类型或[String]和[Set]以外的对象使用此委托，能够提高系统运行效率
 */
@Suppress("UNCHECKED_CAST")
class SpAsyncSerializable<T : Serializable> {
    private var field: T? = null
    private var isFirst = true
    private val service = Executors.newSingleThreadExecutor()

    operator fun getValue(thisRef: Any, property: KProperty<*>): T? {
        if (isFirst && field == null) {
            val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
            val string = preferences.getString(property.name, null)
            if (string != null) {
                var objectBuffer: ObjectInputStream? = null
                try {
                    val bytes = Base64.decode(string, Base64.DEFAULT)
                    val buffer = ByteArrayInputStream(bytes)
                    objectBuffer = ObjectInputStream(buffer)
                    val readObject = objectBuffer.readObject()
                    field = readObject as T
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    objectBuffer.safeClose()
                }
            }
        }
        isFirst = false
        return field
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        val preferences = `SpUtil$$mCache`[thisRef] ?: throw RuntimeException("this object do not register")
        field = value
        if (value == null) {
            preferences.edit().putString(property.name, null).apply()
        } else
            service.execute {
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
                    objectBuffer.safeClose()
                }
            }
    }
}