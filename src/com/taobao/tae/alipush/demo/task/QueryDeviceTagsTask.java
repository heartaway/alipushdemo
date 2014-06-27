package com.taobao.tae.alipush.demo.task;

/**
 * Created by xinyuan on 14/6/17.
 */

import android.os.AsyncTask;
import com.alibaba.cloudpush.client.AlipushOpenApiClient;
import com.alibaba.cloudpush.client.http.AlipushDataResult;
import com.alibaba.cpush.android.CloudPush;
import com.taobao.tae.alipush.demo.utility.DeviceTagDataList;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * 查询设备标签
 */
public class QueryDeviceTagsTask extends AsyncTask<String, AlipushOpenApiClient, List<String>> {
    AlipushOpenApiClient client = null;
    List<String> result = null;

    public QueryDeviceTagsTask(AlipushOpenApiClient client) {
        this.client = client;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        try {
            AlipushDataResult<List<String>> tagsOnDeviceResult = client.queryAppTagsOnDevice(CloudPush.getInstance().getDeviceID());
            if (StringUtils.isNotBlank(tagsOnDeviceResult.getErrorMsg())) {
                return null;
            }
            result = tagsOnDeviceResult.getResponseParams();
            DeviceTagDataList.getInstance().setTagsOnDevice(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}