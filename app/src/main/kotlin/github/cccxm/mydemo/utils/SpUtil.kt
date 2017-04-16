package github.cccxm.mydemo.utils

import android.content.Context
import android.content.SharedPreferences
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * Created by cxm
 * on 2017/4/15.
 */

@Aspect
class SpUtil {
    companion object {
        @JvmStatic var context: Context? = null
        @JvmStatic private val mSpCache = HashMap<String, SharedPreferences>()
    }

    /**
     * 获取带有注解的类
     */
    @Pointcut("within(@github.cccxm.mydemo.utils.SharedPreferenceInject *)")
    fun annotatedMethod() {
    }

    /**
     * 获得所有带有注解的setInt方法
     */
//    @Pointcut("annotatedMethod() && (execution(* set*(int)) || execution(* set*(java.lang.Integer)))")
    @Pointcut("execution(* set*(int))")
    fun setIntMethod() {
    }

    @Around("setIntMethod()")
    fun setIntMethod(joinPoint: ProceedingJoinPoint): Any? {
        logger("setIntMethod") //TODO
        val args = joinPoint.args ?: return joinPoint.proceed(joinPoint.args)
        val any = args[0] ?: return joinPoint.proceed(joinPoint.args)
        if (any is Int) {
            val sp = getSp(joinPoint)
            sp.edit().putInt(joinPoint.signature.name, any).apply()
        }
        return joinPoint.proceed(joinPoint.args)
    }

    /**
     * 获得所有带有注解的setBoolean方法
     */
    @Pointcut("annotatedMethod() && (execution(* set*(boolean)) || execution(* set*(java.lang.Boolean)))")
    fun setBooleanMethod() {
    }

    @Around("setBooleanMethod()")
    fun setBoolean(joinPoint: ProceedingJoinPoint): Any? {
        val args = joinPoint.args ?: return joinPoint.proceed(joinPoint.args)
        val any = args[0] ?: return joinPoint.proceed(joinPoint.args)
        if (any is Boolean) {
            val sp = getSp(joinPoint)
            sp.edit().putBoolean(joinPoint.signature.name, any).apply()
        }
        return joinPoint.proceed(joinPoint.args)
    }

    /**
     * 获得所有带有注解的setFloat方法
     */
    @Pointcut("annotatedMethod() && (execution(* set*(float)) || execution(* set*(java.lang.Float)))")
    fun setFloatMethod() {
    }

    @Around("setFloatMethod()")
    fun setFloat(joinPoint: ProceedingJoinPoint): Any? {
        val args = joinPoint.args ?: return joinPoint.proceed(joinPoint.args)
        val any = args[0] ?: return joinPoint.proceed(joinPoint.args)
        if (any is Float) {
            val sp = getSp(joinPoint)
            sp.edit().putFloat(joinPoint.signature.name, any).apply()
        }
        return joinPoint.proceed(joinPoint.args)
    }

    /**
     * 获得所有带有注解的setLong方法
     */
    @Pointcut("annotatedMethod() && (execution(* set*(long)) || execution(* set*(java.lang.Long)))")
    fun setLongMethod() {
    }

    @Around("setLongMethod()")
    fun setLongMethod(joinPoint: ProceedingJoinPoint): Any? {
        val args = joinPoint.args ?: return joinPoint.proceed(joinPoint.args)
        val any = args[0] ?: return joinPoint.proceed(joinPoint.args)
        if (any is Long) {
            val sp = getSp(joinPoint)
            sp.edit().putLong(joinPoint.signature.name, any).apply()
        }
        return joinPoint.proceed(joinPoint.args)
    }

    /**
     * 获得所有带有注解的setString方法
     */
    @Pointcut("annotatedMethod() && execution(* set*(java.lang.String))")
    fun setStringMethod() {
    }

    @Around("setStringMethod()")
    fun setStringMethod(joinPoint: ProceedingJoinPoint): Any? {
        val args = joinPoint.args ?: return joinPoint.proceed(joinPoint.args)
        val any = args[0] ?: return joinPoint.proceed(joinPoint.args)
        if (any is String) {
            val sp = getSp(joinPoint)
            sp.edit().putString(joinPoint.signature.name, any).apply()
        }
        return joinPoint.proceed(joinPoint.args)
    }

    /**
     * 获得所有带有注解的getBoolean方法
     */
    @Pointcut("annotatedMethod() && (execution(boolean is*()) || execution(java.lang.Boolean is*()))")
    fun getBoolean() {
    }

    @Around("getBoolean()")
    fun getBoolean(joinPoint: ProceedingJoinPoint): Any? {
        val sp = getSp(joinPoint)
        return sp.getBoolean(joinPoint.signature.name, false)
    }

    /**
     * 获得所有带有注解的getInt方法
     */
    @Pointcut("annotatedMethod() && (execution(int get*()) || execution(java.lang.Integer get*()))")
    fun getInt() {
    }

    @Around("getInt()")
    fun getInt(joinPoint: ProceedingJoinPoint): Any? {
        val sp = getSp(joinPoint)
        return sp.getInt(joinPoint.signature.name, 0)
    }

    /**
     * 获得所有带有注解的getFloat方法
     */
    @Pointcut("annotatedMethod() && (execution(float get*()) || execution(java.lang.Float get*()))")
    fun getFloat() {
    }

    @Around("getFloat()")
    fun getFloat(joinPoint: ProceedingJoinPoint): Any? {
        val sp = getSp(joinPoint)
        return sp.getFloat(joinPoint.signature.name, 0f)
    }

    /**
     * 获得所有带有注解的getLong方法
     */
    @Pointcut("annotatedMethod() && (execution(long get*()) || execution(java.lang.Long get*()))")
    fun getLong() {
    }

    @Around("getLong()")
    fun getLong(joinPoint: ProceedingJoinPoint): Any? {
        val sp = getSp(joinPoint)
        return sp.getLong(joinPoint.signature.name, 0)
    }

    /**
     * 获得所有带有注解的getString方法
     */
    @Pointcut("annotatedMethod() && execution(java.lang.String get*())")
    fun getString() {
    }

    @Around("getString()")
    fun getString(joinPoint: ProceedingJoinPoint): Any? {
        val sp = getSp(joinPoint)
        return sp.getString(joinPoint.signature.name, "")
    }

    private fun getSp(joinPoint: ProceedingJoinPoint): SharedPreferences {
        val ctx = context ?: throw NullPointerException("context must initialize")
        val name = joinPoint.target.javaClass.name
        val sharedPreferences = mSpCache[name] ?: ctx.getSharedPreferences(name, Context.MODE_PRIVATE)
        mSpCache[name] = sharedPreferences
        return sharedPreferences
    }
}
