package github.cccxm.mydemo.android.data.app

import android.os.Bundle
import github.cccxm.mydemo.utils.*
import java.util.*

/**
 * Created by cxm
 * on 2017/4/26.
 */
class SaveInstanceActivity : BaseActivity() {
    var saveName by saveString()
    var name: String? = null

    var saveDate by saveSerializable<Date>()
    var date: Date? = null

    var saveAge by save(0)
    var age = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var start = System.currentTimeMillis()
        for (i in 0..10000)
            saveName = "$i"
        logger("save String time:${System.currentTimeMillis() - start}")

        start = System.currentTimeMillis()
        for (i in 0..10000)
            name = "$i"
        logger("normal String time:${System.currentTimeMillis() - start}")

        start = System.currentTimeMillis()
        for (i in 0..10000)
            saveDate = Date()
        logger("save date time:${System.currentTimeMillis() - start}")

        start = System.currentTimeMillis()
        for (i in 0..10000)
            date = Date()
        logger("normal date time:${System.currentTimeMillis() - start}")

        start = System.currentTimeMillis()
        for (i in 0..10000)
            saveAge = i
        logger("save int time:${System.currentTimeMillis() - start}")

        start = System.currentTimeMillis()
        for (i in 0..10000)
            age = i
        logger("normal int time:${System.currentTimeMillis() - start}")
    }
    /*
    save String time:93
    normal String time:19
    save date time:98
    normal date time:24
    save int time:92
    normal int time:1
     */
}