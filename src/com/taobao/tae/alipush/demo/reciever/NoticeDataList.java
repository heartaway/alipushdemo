package com.taobao.tae.alipush.demo.reciever;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xinyuan on 14/6/11.
 */
public class NoticeDataList {

    public static final String TITLE = "notice_title";
    public static final String SUMMERY = "notice_summery";
    public static final String RECIVETIME = "notice_reciveTime";

    private static List<Map<String,String>> notifications = new ArrayList<Map<String, String>>();

    /**
     * 添加 通知
     * @param title
     * @param summery
     * @param reciveTime
     */
    public static void addNotice(String title, String summery, String reciveTime){
        Map message = new HashMap();
        message.put(TITLE,title);
        message.put(SUMMERY,summery);
        Date date = new Date(Long.valueOf(reciveTime));
        SimpleDateFormat format =new SimpleDateFormat("hh:mm:ss");
        message.put(RECIVETIME,format.format(date));
        notifications.add(message);
    }

    /**
     * 获取 通知 列表
     * @return
     */
    public static List<Map<String,String>> getNotice(){
        return notifications;
    }



}
