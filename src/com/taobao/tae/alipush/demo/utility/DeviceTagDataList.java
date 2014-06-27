package com.taobao.tae.alipush.demo.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xinyuan on 14/6/17.
 */
public class DeviceTagDataList {

    public static DeviceTagDataList instance;

    private List<String> tagsOnDevice;


    public DeviceTagDataList() {
    }

    public static DeviceTagDataList getInstance() {
        if (instance == null) {
            instance = new DeviceTagDataList();
        }
        return instance;
    }

    public List<String> getTagsOnDevice() {
        if (tagsOnDevice == null) {
            tagsOnDevice = new ArrayList<String>();
        }
        return tagsOnDevice;
    }

    public void setTagsOnDevice(List<String> tagsOnDevice) {
        this.tagsOnDevice = tagsOnDevice;
    }
}
