package com.taobao.tae.alipush.demo.reciever;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xinyuan on 14/6/11.
 */
public class MessageDataList {

    public static final String TITLE = "message_title";
    public static final String CONTEXT = "message_context";
    public static final String RECIVETIME = "message_reciveTime";

    private static List<Map<String,String>> messageCommand = new ArrayList<Map<String, String>>();

    /**
     * 添加 消息命令
     * @param title
     * @param context
     * @param reciveTime
     */
    public static void addMessageCommand(String title,String context,String reciveTime){
        Map message = new HashMap();
        message.put(TITLE,title);
        message.put(CONTEXT,context);
        Date date = new Date(Long.valueOf(reciveTime));
        SimpleDateFormat format =new SimpleDateFormat("hh:mm:ss");
        message.put(RECIVETIME,format.format(date));
        messageCommand.add(message);
    }

    /**
     * 获取消息命令列表
     * @return
     */
    public static List<Map<String,String>> getMessageCommands(){
        return messageCommand;
    }



}
