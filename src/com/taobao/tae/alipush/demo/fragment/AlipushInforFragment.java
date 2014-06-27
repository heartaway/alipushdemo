package com.taobao.tae.alipush.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.taobao.tae.alipush.demo.R;
import com.taobao.tae.alipush.demo.menu.NoticeFragment;

public class AlipushInforFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentManager fragmentManager = getFragmentManager();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alipush_info, null);
        TextView cancelAddTagTextView = (TextView) view.findViewById(R.id.config_tag_add_cancel_txt);
        cancelAddTagTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.alipush_info_id, new NoticeFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
