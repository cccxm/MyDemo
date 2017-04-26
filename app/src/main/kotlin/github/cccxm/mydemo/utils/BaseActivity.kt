package github.cccxm.mydemo.utils

import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import java.io.Serializable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by cxm
 * on 2017/4/26.
 */
open class BaseActivity : AppCompatActivity() {
    val mSavedInstanceState = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null)
            mSavedInstanceState.putAll(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putAll(mSavedInstanceState)
    }
}

fun BaseActivity.save(default: Int)
        : ReadWriteProperty<BaseActivity, Int> = `BaseActivity$$getPrimaryProperty`(default)

fun BaseActivity.save(default: Byte)
        : ReadWriteProperty<BaseActivity, Byte> = `BaseActivity$$getPrimaryProperty`(default)

fun BaseActivity.save(default: Char)
        : ReadWriteProperty<BaseActivity, Char> = `BaseActivity$$getPrimaryProperty`(default)

fun BaseActivity.save(default: Boolean)
        : ReadWriteProperty<BaseActivity, Boolean> = `BaseActivity$$getPrimaryProperty`(default)

fun BaseActivity.save(default: Float)
        : ReadWriteProperty<BaseActivity, Float> = `BaseActivity$$getPrimaryProperty`(default)

fun BaseActivity.save(default: Short)
        : ReadWriteProperty<BaseActivity, Short> = `BaseActivity$$getPrimaryProperty`(default)

fun BaseActivity.save(default: Double)
        : ReadWriteProperty<BaseActivity, Double> = `BaseActivity$$getPrimaryProperty`(default)

fun BaseActivity.saveString(default: String? = null)
        : NullableReadWriteProperty<BaseActivity, String> = `BaseActivity$$getStringProperty`(default)

fun BaseActivity.saveIntArray()
        : NullableReadWriteProperty<BaseActivity, IntArray> = `BaseActivity$$getIntArrayProperty`()

fun BaseActivity.saveIntArrayList()
        : NullableReadWriteProperty<BaseActivity, ArrayList<Int>> = `BaseActivity$$getIntArrayListProperty`()

fun BaseActivity.saveByteArray()
        : NullableReadWriteProperty<BaseActivity, ByteArray> = `BaseActivity$$getByteArrayProperty`()

fun BaseActivity.saveShortArray()
        : NullableReadWriteProperty<BaseActivity, ShortArray> = `BaseActivity$$getShortArrayProperty`()

fun BaseActivity.saveCharArray()
        : NullableReadWriteProperty<BaseActivity, CharArray> = `BaseActivity$$getCharArrayProperty`()

fun BaseActivity.saveFloatArray()
        : NullableReadWriteProperty<BaseActivity, FloatArray> = `BaseActivity$$getFloatArrayProperty`()

fun BaseActivity.saveLongArray()
        : NullableReadWriteProperty<BaseActivity, LongArray> = `BaseActivity$$getLongArrayProperty`()

fun BaseActivity.saveDoubleArray()
        : NullableReadWriteProperty<BaseActivity, DoubleArray> = `BaseActivity$$getDoubleArrayProperty`()

fun BaseActivity.saveStringArray()
        : NullableReadWriteProperty<BaseActivity, Array<String>> = `BaseActivity$$getStringArrayProperty`()

fun BaseActivity.saveStringArrayList()
        : NullableReadWriteProperty<BaseActivity, ArrayList<String>> = `BaseActivity$$getStringArrayListProperty`()

fun <V : Serializable> BaseActivity.saveSerializable()
        : NullableReadWriteProperty<BaseActivity, V> = `BaseActivity$$getSerializableProperty`()

fun <V : Parcelable> BaseActivity.saveParcelable()
        : NullableReadWriteProperty<BaseActivity, V> = `BaseActivity$$getParcelableProperty`()

fun <V : Parcelable> BaseActivity.saveParcelableArray()
        : NullableReadWriteProperty<BaseActivity, Array<V>> = `BaseActivity$$getParcelableArrayProperty`()

fun <V : Parcelable> BaseActivity.saveParcelableArrayList()
        : NullableReadWriteProperty<BaseActivity, ArrayList<V>> = `BaseActivity$$getParcelableArrayListProperty`()

private fun <T : BaseActivity> `BaseActivity$$getPrimaryProperty`(default: Int)
        = `BaseActivity$$SavePrimaryProperty`(
        { t: T, name -> t.mSavedInstanceState.getInt(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putInt(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getPrimaryProperty`(default: Byte)
        = `BaseActivity$$SavePrimaryProperty`(
        { t: T, name -> t.mSavedInstanceState.getByte(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putByte(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getPrimaryProperty`(default: Char)
        = `BaseActivity$$SavePrimaryProperty`(
        { t: T, name -> t.mSavedInstanceState.getChar(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putChar(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getPrimaryProperty`(default: Boolean)
        = `BaseActivity$$SavePrimaryProperty`(
        { t: T, name -> t.mSavedInstanceState.getBoolean(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putBoolean(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getPrimaryProperty`(default: Float)
        = `BaseActivity$$SavePrimaryProperty`(
        { t: T, name -> t.mSavedInstanceState.getFloat(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putFloat(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getPrimaryProperty`(default: Short)
        = `BaseActivity$$SavePrimaryProperty`(
        { t: T, name -> t.mSavedInstanceState.getShort(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putShort(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getPrimaryProperty`(default: Double)
        = `BaseActivity$$SavePrimaryProperty`(
        { t: T, name -> t.mSavedInstanceState.getDouble(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putDouble(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getPrimaryProperty`(default: Long)
        = `BaseActivity$$SavePrimaryProperty`(
        { t: T, name -> t.mSavedInstanceState.getLong(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putLong(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getStringProperty`(default: String?)
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getString(name, default) },
        { t: T, v, name -> t.mSavedInstanceState.putString(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getIntArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getIntArray(name) },
        { t: T, v, name -> t.mSavedInstanceState.putIntArray(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getIntArrayListProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getIntegerArrayList(name) },
        { t: T, v, name -> t.mSavedInstanceState.putIntegerArrayList(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getByteArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getByteArray(name) },
        { t: T, v, name -> t.mSavedInstanceState.putByteArray(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getShortArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getShortArray(name) },
        { t: T, v, name -> t.mSavedInstanceState.putShortArray(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getCharArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getCharArray(name) },
        { t: T, v, name -> t.mSavedInstanceState.putCharArray(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getFloatArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getFloatArray(name) },
        { t: T, v, name -> t.mSavedInstanceState.putFloatArray(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getLongArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getLongArray(name) },
        { t: T, v, name -> t.mSavedInstanceState.putLongArray(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getDoubleArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getDoubleArray(name) },
        { t: T, v, name -> t.mSavedInstanceState.putDoubleArray(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getStringArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getStringArray(name) },
        { t: T, v, name -> t.mSavedInstanceState.putStringArray(name, v) })

private fun <T : BaseActivity> `BaseActivity$$getStringArrayListProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getStringArrayList(name) },
        { t: T, v, name -> t.mSavedInstanceState.putStringArrayList(name, v) })

@Suppress("UNCHECKED_CAST")
private fun <T : BaseActivity, V : Serializable> `BaseActivity$$getSerializableProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name ->
            val serializable = t.mSavedInstanceState.getSerializable(name)
            if (serializable != null)
                serializable as V
            else null
        },
        { t: T, v, name -> t.mSavedInstanceState.putSerializable(name, v) })

private fun <T : BaseActivity, V : Parcelable> `BaseActivity$$getParcelableProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getParcelable<V>(name) },
        { t: T, v, name -> t.mSavedInstanceState.putParcelable(name, v) })

@Suppress("UNCHECKED_CAST")
private fun <T : BaseActivity, V : Parcelable> `BaseActivity$$getParcelableArrayProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name ->
            val parcelableArray = t.mSavedInstanceState.getParcelableArray(name)
            if (parcelableArray != null)
                parcelableArray as Array<V>
            else null
        },
        { t: T, v, name -> t.mSavedInstanceState.putParcelableArray(name, v) })

private fun <T : BaseActivity, V : Parcelable> `BaseActivity$$getParcelableArrayListProperty`()
        = `BaseActivity$$SaveProperty`(
        { t: T, name -> t.mSavedInstanceState.getParcelableArrayList<V>(name) },
        { t: T, v, name -> t.mSavedInstanceState.putParcelableArrayList(name, v) })

private class `BaseActivity$$SavePrimaryProperty`<in T, V>(private val getter: (T, name: String) -> V,
                                                           private val setter: (T, V, name: String) -> Unit)
    : ReadWriteProperty<T, V> {

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        return getter.invoke(thisRef, property.name)
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
        return setter.invoke(thisRef, value, property.name)
    }
}

interface NullableReadWriteProperty<in R, T> {
    operator fun getValue(thisRef: R, property: KProperty<*>): T?

    operator fun setValue(thisRef: R, property: KProperty<*>, value: T?)
}

private class `BaseActivity$$SaveProperty`<in T, V>(private val getter: (T, name: String) -> V?,
                                                    private val setter: (T, V?, name: String) -> Unit)
    : NullableReadWriteProperty<T, V> {

    private var field: V? = null

    override fun getValue(thisRef: T, property: KProperty<*>): V? {
        if (field == null)
            field = getter.invoke(thisRef, property.name)
        return field
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: V?) {
        field = value
        return setter.invoke(thisRef, value, property.name)
    }
}