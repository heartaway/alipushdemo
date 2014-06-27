package com.taobao.tae.alipush.demo.menu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.cloudpush.client.AlipushOpenApiClient;
import com.alibaba.cpush.android.CloudPush;
import com.taobao.tae.alipush.demo.R;
import com.taobao.tae.alipush.demo.fragment.TagConfigFragment;
import com.taobao.tae.alipush.demo.task.QueryDeviceTagsTask;
import com.taobao.tae.alipush.demo.task.SendDeviceIdThrowAlipushNotificationTask;
import com.taobao.tae.alipush.demo.utility.Constant;
import com.taobao.tae.alipush.demo.utility.SendDeviceStatus;

public class SetFragment extends Fragment {
    View view;
    String deviceId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentManager fragmentManager = getFragmentManager();
        view = LayoutInflater.from(getActivity()).inflate(R.layout.system_config, null);
        AlipushOpenApiClient client = new AlipushOpenApiClient(Constant.OPENAPI_DOMAIN, Constant.appId, Constant.appKey, Constant.VERSION);
        getDeviceIdAndStatus(view);
        SendDeviceIdThrowAlipushNotificationTask senDeviceId = new SendDeviceIdThrowAlipushNotificationTask(client);
        senDeviceId.execute();
        QueryDeviceTagsTask queryDeviceTagsTask = new QueryDeviceTagsTask(client);
        queryDeviceTagsTask.execute();
        showSendDeviceStatus(view);
        RelativeLayout addTagRelativeLayout = (RelativeLayout) view.findViewById(R.id.config_add_tag_btn);
        addTagRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.config_layout_id, new TagConfigFragment());
                fragmentTransaction.commit();
            }
        });
        return view;
    }


    /**
     * 获取设备ID
     */
    public void getDeviceIdAndStatus(View view) {
        try {
            deviceId = CloudPush.getInstance().getDeviceID();
            TextView deviceIdView = (TextView) view.findViewById(R.id.config_deivce_id_txt);
            TextView deviceStatusView = (TextView) view.findViewById(R.id.config_deivce_status_txt);
            deviceIdView.setText(deviceId);
            if (isNetworkConnected()) {
                deviceStatusView.setText("上线");
            } else {
                deviceStatusView.setText("离线");
            }
        } catch (Exception e) {
            Log.e("get device id failure.", "");
        }
    }

    private void showSendDeviceStatus(View view) {
        if (SendDeviceStatus.IS_SEND_SUCCESS) {
            TextView accountTextView = (TextView) view.findViewById(R.id.config_send_device_id_status_text);
            accountTextView.setText(Constant.SEND_DEVICE_ID_SUCCESS);
            ImageView sendDeviceStatusImageView = (ImageView) view.findViewById(R.id.config_send_device_id_status_img);
            sendDeviceStatusImageView.setBackgroundResource(R.drawable.confirm_check_32);
        } else if (SendDeviceStatus.SENDED && !SendDeviceStatus.IS_SEND_SUCCESS) {
            TextView accountTextView = (TextView) view.findViewById(R.id.config_send_device_id_status_text);
            accountTextView.setText(Constant.SEND_DEVICE_ID_FAILURE);
            ImageView sendDeviceStatusImageView = (ImageView) view.findViewById(R.id.config_send_device_id_status_img);
            sendDeviceStatusImageView.setBackgroundResource(R.drawable.close_check_32);
        }
    }


    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

}
