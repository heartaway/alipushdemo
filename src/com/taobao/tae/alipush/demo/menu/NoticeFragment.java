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
import com.taobao.tae.alipush.demo.reciever.NoticeDataList;

import java.util.List;
import java.util.Map;

public class NoticeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.notice, null);
        ListView noticeListView = (ListView) view.findViewById(R.id.notice_datas);
        displayNoticeData(noticeListView);
        return view;
    }

    /**
     * 使用简单适配器显示条目列表
     */
    private void displayNoticeData(ListView listView) {
//        List<Map<String, String>> notices = NoticeDataList.getNotice();
//        SimpleAdapter adapter = new SimpleAdapter(getActivity(), notices, R.layout.notice_item_list,
//                new String[]{"notice_title", "notice_summery", "notice_reciveTime"}, new int[]{R.id.notice_title, R.id.notice_summery, R.id.notice_reciveTime});
        listView.setAdapter(SimpleAdapterInstance.getNoticeSimpleAdapter(getActivity()));
    }
}
