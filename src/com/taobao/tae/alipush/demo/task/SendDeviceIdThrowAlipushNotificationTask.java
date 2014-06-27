package com.taobao.tae.alipush.demo.task;

import android.os.AsyncTask;
import android.os.Build;
import com.alibaba.cloudpush.client.AlipushOpenApiClient;
import com.alibaba.cloudpush.client.http.AlipushBaseResult;
import com.alibaba.cloudpush.openapi.model.AliNotification;
import com.alibaba.cloudpush.openapi.model.BaseMessage;
import com.alibaba.cpush.android.CloudPush;
import com.taobao.tae.alipush.demo.utility.Constant;
import com.taobao.tae.alipush.demo.utility.SendDeviceStatus;

import java.io.IOException;

/**
 * 发送设备ID到服务端通知列表
 */
public class SendDeviceIdThrowAlipushNotificationTask extends AsyncTask<String, AlipushOpenApiClient, AlipushBaseResult> {
    AlipushOpenApiClient client = null;
    AlipushBaseResult result = null;

    public SendDeviceIdThrowAlipushNotificationTask(AlipushOpenApiClient client) {
        this.client = client;
    }

    @Override
    protected AlipushBaseResult doInBackground(String... params) {
        // 未发送 或者 发送失败时，进行 发送
        if (!SendDeviceStatus.SENDED || !SendDeviceStatus.IS_SEND_SUCCESS) {
            AliNotification notification = new AliNotification();
            try {
                notification.setAppId(Constant.appId);
                notification.setDeviceId(CloudPush.getInstance().getDeviceID());
                notification.setTimeout(70);
                notification.setSendType(BaseMessage.SendTypeEnum.DEVICE.getValue());
                notification.setTitle("云推送Demo");
                notification.setSummary("发送设备ID到服务端。");
                notification.setContent("您的 ".concat(Build.MODEL).concat(" 手机的设备ID为：").concat(CloudPush.getInstance().getDeviceID()));
                notification.setAndroidNotifyType(1);
                notification.setDeviceType(2);
                notification.setStatus(1);
                notification.setAndroidOpenType(1);
                notification.setIosFooter(1);
                result = client.pushNotification(notification);
            } catch (IOException e) {
                e.printStackTrace();
                result = new AlipushBaseResult(null, -400, e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                result = new AlipushBaseResult(null, -400, e.getMessage());
            }
            if (result.getErrorCode() == null) {
                SendDeviceStatus.IS_SEND_SUCCESS = true;
            }
            SendDeviceStatus.SENDED = true;
        }
        return result;
    }
}
