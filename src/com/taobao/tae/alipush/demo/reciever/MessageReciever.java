package com.taobao.tae.alipush.demo.reciever;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.alibaba.cpush.android.constants.CPushContants;
import com.alibaba.cpush.android.receiver.*;
import com.taobao.tae.alipush.demo.adapter.SimpleAdapterInstance;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by xinyuan on 14/6/11.
 */
public class MessageReciever extends CPushBaseBroadcastReciever {

    /**
     * 接收到消息命令的处理
     *
     * @param context
     * @param message
     */
    @Override
    protected void onMessage(Context context, CPushMessage message) {
        try {
            String commandContext = new String(message.getContext(), "UTF-8");
            MessageDataList.addMessageCommand(message.getTitle(), commandContext, String.valueOf(System.currentTimeMillis()));
            SimpleAdapterInstance.getMessageSimpleAdapter(context).notifyDataSetChanged();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户打开通知后的结果
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    protected void onNotificationOpenResult(Context context, String title, String summary, String extraMap) {

    }


    /**
     * 由于OnNotification没有暴露接口，所以覆写onReceive ，接收到 通知 或消息时 对 通知进行处理，添加到列表中
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onReceive(Context context, Intent intent) {
        CPushNotication cpushNotication = new CPushNotication();
        super.onReceive(context, intent);
        if (intent != null) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            /** 通收消息*/
            if (CPushContants.NOTIFICATION_RECEIVER.equals(intent.getAction())) {
                byte[] msg = intent.getByteArrayExtra(CPushContants.MESSAGE_DATA);
                PushMessageCodec.PushMessage pushMessage = PushMessageCodec.decodeMessage(msg);
                if (pushMessage.getMsgType() == PushMessageCodec.NOTIFCATION) {
                    try {
                        JSONObject jsonObject = null;
                        if (PushMessageCodec.CHAR_SET_UTF8 == pushMessage.getCharSet()) {
                            jsonObject = new JSONObject(new String(pushMessage.getContextBody(), PushMessageCodec.UTF8));
                        } else if (PushMessageCodec.CHAR_SET_GBK == pushMessage.getCharSet()) {
                            jsonObject = new JSONObject(new String(pushMessage.getContextBody(), PushMessageCodec.GBK));
                        }
                        cpushNotication = convert(jsonObject);
                    } catch (UnsupportedEncodingException e) {
                        Log.e("AliPush", "encode", e);
                    } catch (JSONException e) {
                        Log.e("AliPush", "json", e);
                    }
                }

            }

        }


        NoticeDataList.addNotice(cpushNotication.getTitle(), cpushNotication.getSummary(), String.valueOf(System.currentTimeMillis()));
        SimpleAdapterInstance.getNoticeSimpleAdapter(context).notifyDataSetChanged();
    }


    private CPushNotication convert(JSONObject jsonObject) throws JSONException {
        CPushNotication cPushNotication = new CPushNotication();

        cPushNotication.setNotifyType(getInt(jsonObject, "notifyType", CPushNotication.NOTIFY_TYPE_SOUND));
        cPushNotication.setOpenType(getInt(jsonObject, "openType", CPushNotication.OPEN_APP));
        cPushNotication.setOpenUrl(getString(jsonObject, "openUrl", null));
        cPushNotication.setSound(getString(jsonObject, "sound", null));
        cPushNotication.setTitle(getString(jsonObject, "title", null));
        cPushNotication.setSummary(getString(jsonObject, "summary", null));
        cPushNotication.setClear(getBoolean(jsonObject, "clear", CPushNotication.CLEAR));
        cPushNotication.setContentText(getString(jsonObject, "contentText", null));

        JSONObject map = getJSONObject(jsonObject, "extraMap", null);
        if (map != null && map.length() > 0) {
            cPushNotication.setExtraMap(toMap(map));
        }

        return cPushNotication;
    }

    private class CPushNotication {
        public static final boolean NO_CLEAR = true; //在通知栏中清除通知后; 为true时此通知不清除
        public static final boolean CLEAR = false;

        public static final int OPEN_APP = 1; // 打开app
        public static final int OPEN_ACTIVITY = 2;// 打开ap指定位置
        public static final int OPEN_WEB = 3;// 打开网页

        public static final int NOTIFY_TYPE_VIBRATE = 1;// 提醒方式，振动
        public static final int NOTIFY_TYPE_SOUND = 2;// 提醒方式，声音提醒,还可以自定义声音提醒
        public static final int NOTIFY_TYPE_VIBRATE_SOUND = 3;// 提醒方式，声音加振动

        private long messageId;// 冗余，统计数据时使用
        private int appId;// 冗余，统计数据时使用

        private String title;// 标题
        private String summary;// 摘要
        private String contentText;// 通知内容

        private int notifyType; // 提醒方式
        private String sound;//自定义声音或者默认音乐

        private String openUrl;// 打开app，网页或者指定app指定位置
        private int openType;// 打开类型

        private boolean clear;// 是否允许被清除，在通知栏中清除通知后，为true时此通知不清除

        private Map<String, String> extraMap;// 自定义的kv结构，开发者扩展用

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public int getOpenType() {
            return openType;
        }

        public void setOpenType(int openType) {
            this.openType = openType;
        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getNotifyType() {
            return notifyType;
        }

        public void setNotifyType(int notifyType) {
            this.notifyType = notifyType;
        }

        public String getOpenUrl() {
            return openUrl;
        }

        public void setOpenUrl(String openUrl) {
            this.openUrl = openUrl;
        }

        public Map<String, String> getExtraMap() {
            return extraMap;
        }

        public void setExtraMap(Map<String, String> extraMap) {
            this.extraMap = extraMap;
        }


        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public boolean isClear() {
            return clear;
        }

        public void setClear(boolean clear) {
            this.clear = clear;
        }


        public long getMessageId() {
            return messageId;
        }

        public void setMessageId(long messageId) {
            this.messageId = messageId;
        }

        public int getAppId() {
            return appId;
        }

        public void setAppId(int appId) {
            this.appId = appId;
        }

    }


    private static String getString(JSONObject jsonObject, String key, String defaultValue) {
        try {
            return jsonObject.getString(key);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    private static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {
        try {
            return jsonObject.getJSONObject(key);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    private static boolean getBoolean(JSONObject jsonObject, String key, boolean defaultValue) {
        try {
            return jsonObject.getBoolean(key);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    private static int getInt(JSONObject jsonObject, String key, int defaultValue) {
        try {
            return jsonObject.getInt(key);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

}
