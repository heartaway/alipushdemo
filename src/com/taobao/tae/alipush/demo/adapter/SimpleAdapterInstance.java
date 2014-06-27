package com.taobao.tae.alipush.demo.adapter;

import android.widget.SimpleAdapter;
import com.taobao.tae.alipush.demo.R;
import com.taobao.tae.alipush.demo.reciever.MessageDataList;
import com.taobao.tae.alipush.demo.reciever.NoticeDataList;

import java.util.List;
import java.util.Map;

/**
 * Created by xinyuan on 14/6/18.
 */
public class SimpleAdapterInstance {

    public static SimpleAdapter messageSimpleAdapter;
    public static SimpleAdapter noticeSimpleAdapter;

    public static SimpleAdapter getMessageSimpleAdapter(android.content.Context context) {
        if (messageSimpleAdapter == null) {
            List<Map<String, String>> messages = MessageDataList.getMessageCommands();
            messageSimpleAdapter = new SimpleAdapter(context, messages, R.layout.message_item_list,
                    new String[]{"message_title", "message_context", "message_reciveTime"}, new int[]{R.id.message_title, R.id.message_context, R.id.message_reciveTime});
        }
        return messageSimpleAdapter;
    }

    public static SimpleAdapter getNoticeSimpleAdapter(android.content.Context context) {
        if (noticeSimpleAdapter == null) {
            List<Map<String, String>> notices = NoticeDataList.getNotice();
            noticeSimpleAdapter = new SimpleAdapter(context, notices, R.layout.notice_item_list,
                    new String[]{"notice_title", "notice_summery", "notice_reciveTime"}, new int[]{R.id.notice_title, R.id.notice_summery, R.id.notice_reciveTime});

        }
        return noticeSimpleAdapter;
    }

}
