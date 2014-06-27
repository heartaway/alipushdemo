package com.taobao.tae.alipush.demo.task;

/**
 * Created by xinyuan on 14/6/17.
 */

import android.os.AsyncTask;
import com.alibaba.cloudpush.client.AlipushOpenApiClient;
import com.alibaba.cloudpush.client.http.AlipushBaseResult;
import com.alibaba.cloudpush.client.http.AlipushDataResult;
import com.alibaba.cpush.android.CloudPush;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * 给设备打标签
 */
public class PutTagOnDeviceTask extends AsyncTask<String, AlipushOpenApiClient, String> {
    AlipushOpenApiClient client = null;
    AlipushBaseResult result = null;
    String tagName;

    public PutTagOnDeviceTask(AlipushOpenApiClient client) {
        this.client = client;
    }

    @Override
    protected String doInBackground(String... params) {
        String message = "";
        try {
            tagName = params[0];
            AlipushDataResult<List<String>> tagsResult = client.queryAppTags();
            // 服务端 不存在标签此标签时，创建服务端标签，服务端标签数量超过20个时，不允许在创建新标签
            if (StringUtils.isBlank(tagsResult.getErrorMsg()) && !tagsResult.getResponseParams().contains(tagName)) {
                if (tagsResult.getResponseParams().size() >= 20) {
                    return "标签数量已达到上线";
                } else {
                    result = client.createAppTag(tagName);
                }
            }
            AlipushDataResult<List<String>> tagsOnDeviceResult = client.queryAppTagsOnDevice(CloudPush.getInstance().getDeviceID());
            if (StringUtils.isBlank(tagsOnDeviceResult.getErrorMsg()) && (tagsOnDeviceResult.getResponseParams() == null || !tagsOnDeviceResult.getResponseParams().contains(tagName))) {
                if (tagsOnDeviceResult.getResponseParams() != null && tagsOnDeviceResult.getResponseParams().size() >= 20) {
                    return "标签数量已达到上线";
                } else {
                    result = client.putTagOnDevice(tagName, CloudPush.getInstance().getDeviceID());
                }
            } else if (StringUtils.isBlank(tagsOnDeviceResult.getErrorMsg()) && tagsOnDeviceResult.getResponseParams().contains(tagName)) {
                return "标签已存在";
            }

            if (StringUtils.isNotBlank(result.getErrorMsg())) {
                message = result.getErrorMsg();
            } else {
                message = "添加标签成功";
            }
        } catch (IOException e) {
            message = "添加标签失败";
            e.printStackTrace();
        } catch (Exception e) {
            message = "添加标签失败";
            e.printStackTrace();
        }
        return message;
    }
}

