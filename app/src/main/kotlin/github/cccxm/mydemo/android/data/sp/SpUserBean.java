package github.cccxm.mydemo.android.data.sp;

import android.util.Log;

import github.cccxm.mydemo.utils.SharedPreferenceInject;
import hugo.weaving.DebugLog;

/**
 * Created by cxm
 * on 2017/4/16.
 */
@SharedPreferenceInject
public class SpUserBean {
    private String userName;
    private int age;
    private String job;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    @DebugLog
    public void setAge(int age) {
        Log.e("setAge", "----" + age);//TODO
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
