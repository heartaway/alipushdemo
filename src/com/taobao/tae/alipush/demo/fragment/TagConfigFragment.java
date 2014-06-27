package com.taobao.tae.alipush.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.cloudpush.client.AlipushOpenApiClient;
import com.taobao.tae.alipush.demo.R;
import com.taobao.tae.alipush.demo.adapter.TagDeletableAdapter;
import com.taobao.tae.alipush.demo.menu.SetFragment;
import com.taobao.tae.alipush.demo.task.PutTagOnDeviceTask;
import com.taobao.tae.alipush.demo.utility.Constant;
import com.taobao.tae.alipush.demo.utility.DeviceTagDataList;

public class TagConfigFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentManager fragmentManager = getFragmentManager();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tag_config, null);


        ListView tagListView = (ListView) view.findViewById(R.id.tag_datas);
        final TagDeletableAdapter tagDeletableAdapter = new TagDeletableAdapter(getActivity(), DeviceTagDataList.getInstance().getTagsOnDevice());
        tagListView.setAdapter(tagDeletableAdapter);

        //返回
        TextView cancelAddTagTextView = (TextView) view.findViewById(R.id.config_tag_add_cancel_txt);
        cancelAddTagTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.config_tag_add_id, new SetFragment());
                fragmentTransaction.commit();
            }
        });

        //添加标签
        ImageView saveAddTagImageView = (ImageView) view.findViewById(R.id.config_tag_add_save_img_btn);
        saveAddTagImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = (EditText) getActivity().findViewById(R.id.config_tag_add_content_txt);
                if (editText != null && !"".equals(editText.getText().toString())) {
                    AlipushOpenApiClient client = new AlipushOpenApiClient(Constant.OPENAPI_DOMAIN, Constant.appId, Constant.appKey, Constant.VERSION);
                    PutTagOnDeviceTask senDeviceId = new PutTagOnDeviceTask(client);
                    senDeviceId.execute(editText.getText().toString());
                    if (tagDeletableAdapter.datas.contains(editText.getText().toString())) {
                        showMessage("标签已存在");
                    } else {
                        tagDeletableAdapter.datas.add(editText.getText().toString());
                        tagDeletableAdapter.notifyDataSetChanged();
                    }
                } else {
                    showMessage("标签名称不能为空");
                }
            }
        });
        return view;
    }

    private void showMessage(String message) {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
