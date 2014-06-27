package com.taobao.tae.alipush.demo;

import android.app.Application;
import com.alibaba.cpush.android.CloudPush;
import com.taobao.tae.alipush.demo.utility.Constant;

/**
 * Created by xinyuan on 14/6/11.
 */
public class AlipushDemoApplication extends Application {

    /**
     * 设备注册
     */
    @Override
    public void onCreate() {
        super.onCreate();
        CloudPush.getInstance().register(this, Constant.LOGIN_ACCOUNT);
    }

}
