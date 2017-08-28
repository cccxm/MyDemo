@file:Suppress("unused")

package github.cccxm.mydemo.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.io.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by cxm
 * on 2017/8/28.
 */
abstract class BaseSharedPreferences(context: Context, spName: String? = null) {

    private val sharedPreference = context.applicationContext.getSharedPreferences(spName ?: javaClass.name, Context.MODE_PRIVATE)

    /**
     * [Int] 类型的映射委托
     */
    fun spInt(default: Int = 0): ReadWriteProperty<Any, Int> =
            SpDefaultInt(default, sharedPreference)

    /**
     * [Boolean] 类型的映射委托
     */
    fun spBoolean(default: Boolean = false): ReadWriteProperty<Any, Boolean> =
            SpDefaultBoolean(default, sharedPreference)

    /**
     * [Float] 类型的映射委托
     */
    fun spFloat(default: Float = 0f): ReadWriteProperty<Any, Float> =
            SpDefaultFloat(default, sharedPreference)

    /**
     * [Long] 类型的映射委托
     */
    fun spLong(default: Long = 0): ReadWriteProperty<Any, Long> =
            SpDefaultLong(default, sharedPreference)

    /**
     * [String] 类型的映射委托
     */
    fun spString(): ReadWriteProperty<Any, String?> = SpDefaultString(sharedPreference)

    /**
     * [String] 类型的映射委托
     */
    fun spString(default: String): ReadWriteProperty<Any, String> =
            SpDefaultStringNotNull(default, sharedPreference)

    /**
     * [Set] 类型的映射委托
     */
    fun spStringSet(): ReadWriteProperty<Any, Set<String>?> = SpDefaultStringSet(sharedPreference)

    /**
     * [Set] 类型的映射委托
     */
    fun spStringSet(default: Set<String>): ReadWriteProperty<Any, Set<String>> =
            SpDefaultStringSetNotNull(default, sharedPreference)

    /**
     * [Serializable] 类型的映射委托
     */
    fun <T : Serializable> spSerializable(): ReadWriteProperty<Any, T?> =
            SpDefaultSerializable(sharedPreference)

    /**
     * [Serializable] 类型的映射委托
     */
    fun <T : Serializable> spSerializable(default: T): ReadWriteProperty<Any, T> =
            SpDefaultSerializableNotNull(default, sharedPreference)

    private class SpDefaultInt(val default: Int, val preferences: SharedPreferences) : ReadWriteProperty<Any, Int> {
        private var value: Int? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): Int =
                value ?: preferences.getInt(property.name, default).apply { value = this }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
            preferences.edit().putInt(property.name, value.apply { this@SpDefaultInt.value = this }).apply()
        }
    }

    private class SpDefaultBoolean(val default: Boolean, val preferences: SharedPreferences) : ReadWriteProperty<Any, Boolean> {
        private var value: Boolean? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean =
                value ?: preferences.getBoolean(property.name, default).apply { value = this }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            preferences.edit().putBoolean(property.name, value.apply { this@SpDefaultBoolean.value = value }).apply()
        }
    }

    private class SpDefaultFloat(val default: Float, val preferences: SharedPreferences) : ReadWriteProperty<Any, Float> {
        private var value: Float? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): Float =
                value ?: preferences.getFloat(property.name, default).apply { value = this }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
            preferences.edit().putFloat(property.name, value.apply { this@SpDefaultFloat.value = value }).apply()
        }
    }

    private class SpDefaultLong(val default: Long, val preferences: SharedPreferences) : ReadWriteProperty<Any, Long> {
        private var value: Long? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): Long =
                value ?: preferences.getLong(property.name, default).apply { value = this }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
            preferences.edit().putLong(property.name, value.apply { this@SpDefaultLong.value = value }).apply()
        }
    }

    private class SpDefaultString(val preferences: SharedPreferences) : ReadWriteProperty<Any, String?> {
        private var value: String? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): String? =
                value ?: preferences.getString(property.name, null).apply { value = this }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
            preferences.edit().putString(property.name, value.apply { this@SpDefaultString.value = value }).apply()
        }
    }

    private class SpDefaultStringNotNull(val default: String, val preferences: SharedPreferences) : ReadWriteProperty<Any, String> {
        private var value: String? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): String =
                value ?: preferences.getString(property.name, default).apply { value = this }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            preferences.edit().putString(property.name, value.apply { this@SpDefaultStringNotNull.value = value }).apply()
        }
    }

    private class SpDefaultStringSet(val preferences: SharedPreferences) : ReadWriteProperty<Any, Set<String>?> {
        private var value: Set<String>? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): Set<String> =
                value ?: preferences.getStringSet(property.name, null).apply { value = this }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Set<String>?) {
            preferences.edit().putStringSet(property.name, value.apply { this@SpDefaultStringSet.value = value }).apply()
        }
    }

    private class SpDefaultStringSetNotNull(val default: Set<String>, val preferences: SharedPreferences) : ReadWriteProperty<Any, Set<String>> {
        private var value: Set<String>? = null
        override fun getValue(thisRef: Any, property: KProperty<*>): Set<String> =
                value ?: preferences.getStringSet(property.name, default).apply { value = this }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Set<String>) {
            preferences.edit().putStringSet(property.name, value.apply { this@SpDefaultStringSetNotNull.value = value }).apply()
        }
    }

    private class SpDefaultSerializable<T : Serializable>(val preferences: SharedPreferences) : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            val string = preferences.getString(property.name, null) ?: return null
            var objectBuffer: ObjectInputStream? = null
            try {
                val bytes = Base64.decode(string, Base64.DEFAULT)
                val buffer = ByteArrayInputStream(bytes)
                objectBuffer = ObjectInputStream(buffer)
                val readObject = objectBuffer.readObject()
                @Suppress("UNCHECKED_CAST")
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

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
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

    private class SpDefaultSerializableNotNull<T : Serializable>(val default: T, val preferences: SharedPreferences) : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            val string = preferences.getString(property.name, null) ?: return default
            var objectBuffer: ObjectInputStream? = null
            try {
                val bytes = Base64.decode(string, Base64.DEFAULT)
                val buffer = ByteArrayInputStream(bytes)
                objectBuffer = ObjectInputStream(buffer)
                val readObject = objectBuffer.readObject()
                @Suppress("UNCHECKED_CAST")
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

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
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
}