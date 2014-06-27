package com.taobao.tae.alipush.demo.task;

/**
 * Created by xinyuan on 14/6/17.
 */

import android.os.AsyncTask;
import com.alibaba.cloudpush.client.AlipushOpenApiClient;
import com.alibaba.cloudpush.client.http.AlipushBaseResult;
import com.alibaba.cpush.android.CloudPush;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * 给设备打标签
 */
public class RemoveTagOnDeviceTask extends AsyncTask<String, AlipushOpenApiClient, String> {
    AlipushOpenApiClient client = null;
    AlipushBaseResult result = null;
    String tagName;

    public RemoveTagOnDeviceTask(AlipushOpenApiClient client) {
        this.client = client;
    }

    @Override
    protected String doInBackground(String... params) {
        String message = "";
        try {
            tagName = params[0];
            result = client.removeTagOnDevice(tagName, CloudPush.getInstance().getDeviceID());
            result = client.delAppTag(tagName);
            if (StringUtils.isNotBlank(result.getErrorMsg())) {
                message = result.getErrorMsg();
            } else {
                message = "移除标签成功";
            }
        } catch (IOException e) {
            message = "移除标签失败";
            e.printStackTrace();
        } catch (Exception e) {
            message = "移除标签失败";
            e.printStackTrace();
        }
        return message;
    }
}

