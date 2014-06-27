package com.taobao.tae.alipush.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.cloudpush.client.AlipushOpenApiClient;
import com.taobao.tae.alipush.demo.R;
import com.taobao.tae.alipush.demo.task.RemoveTagOnDeviceTask;
import com.taobao.tae.alipush.demo.utility.Constant;

import java.util.List;

/**
 * Created by xinyuan on 14/6/17.
 */
public class TagDeletableAdapter extends BaseAdapter {
    private Context context;
    public List<String> datas;

    public TagDeletableAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index = position;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tag_item_list, null);
        }
        final TextView textView = (TextView) view.findViewById(R.id.tag_item_name);
        textView.setText(datas.get(position));
        final ImageView imageView = (ImageView) view.findViewById(R.id.tag_item_delete_img_btn);
        imageView.setTag(position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlipushOpenApiClient client = new AlipushOpenApiClient(Constant.OPENAPI_DOMAIN, Constant.appId, Constant.appKey, Constant.VERSION);
                RemoveTagOnDeviceTask removeTagOnDeviceTask = new RemoveTagOnDeviceTask(client);
                removeTagOnDeviceTask.execute(textView.getText().toString());
                datas.remove(index);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
