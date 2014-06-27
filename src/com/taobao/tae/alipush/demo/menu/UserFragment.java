package com.taobao.tae.alipush.demo.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.cpush.android.CloudPush;
import com.taobao.tae.alipush.demo.R;
import com.taobao.tae.alipush.demo.utility.Constant;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FragmentManager fragmentManager = getFragmentManager();
        if (Constant.LOGIN_ACCOUNT == null || Constant.LOGIN_ACCOUNT == "") {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.login, null);
            Button loginBtn = (Button) view.findViewById(R.id.login_btn);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = (EditText) getActivity().findViewById(R.id.login_account);
                    Constant.LOGIN_ACCOUNT = editText.getText().toString();
                    switchAppAccount(Constant.LOGIN_ACCOUNT);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.login_fragment, new UserFragment());
                    fragmentTransaction.commit();
                }
            });
            return view;
        } else {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.user, null);
            TextView accountTextView = (TextView) view.findViewById(R.id.user_login_account);
            accountTextView.setText(Constant.LOGIN_ACCOUNT);
            ImageView logoutImg = (ImageView) view.findViewById(R.id.logout_btn);
            logoutImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constant.LOGIN_ACCOUNT = "";
                    switchAppAccount(Constant.LOGIN_ACCOUNT);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.user_fragment, new UserFragment());
                    fragmentTransaction.commit();
                }
            });
            return view;
        }
    }

    /**
     * 切换 App 账号
     */
    public void switchAppAccount(String account) {
        CloudPush.getInstance().bindAccount(account);
    }

}
