package github.cccxm.mydemo.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;


/**
 * 用于生成SharedPreference映射的组件注解
 *
 * 目前暂不支持{Set{String}}类型
 *
 * Created by cxm
 * on 2017/4/16.
 */
@Target({TYPE})
@Retention(CLASS)
public @interface SharedPreferenceInject {
}
