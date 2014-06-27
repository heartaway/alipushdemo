package com.taobao.tae.alipush.demo.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.taobao.tae.alipush.demo.R;
import com.taobao.tae.alipush.demo.adapter.SimpleAdapterInstance;
import com.taobao.tae.alipush.demo.reciever.MessageDataList;

import java.util.List;
import java.util.Map;

public class MessageFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.message, null);
        ListView messageListView = (ListView)view.findViewById(R.id.message_datas);
        displayMessageData(messageListView);
		return view;
	}

    /**
     * 使用简单适配器显示条目列表
     */
    private void displayMessageData(ListView listView) {
//        List<Map<String, String>> messages = MessageDataList.getMessageCommands();
//        SimpleAdapter adapter = new SimpleAdapter(getActivity(), messages, R.layout.message_item_list,
//                new String[]{"message_title", "message_context", "message_reciveTime"}, new int[]{R.id.message_title, R.id.message_context, R.id.message_reciveTime});
        listView.setAdapter(SimpleAdapterInstance.getMessageSimpleAdapter(getActivity()));

    }

}
