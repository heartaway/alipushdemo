package com.taobao.tae.alipush.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.taobao.tae.alipush.demo.R;
import com.taobao.tae.alipush.demo.menu.MessageFragment;
import com.taobao.tae.alipush.demo.menu.NoticeFragment;
import com.taobao.tae.alipush.demo.menu.SetFragment;
import com.taobao.tae.alipush.demo.menu.UserFragment;


/**
 * Created with IntelliJ IDEA.
 * User: xinyuan.ymm
 * Date: 13-1-11
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    // 标题中的设置按钮
    protected FrameLayout noticeLayout, messageLayout, userLayout, setLayout;
    protected ImageView noticeImg, messageImg, userImg, setImg;
    protected ImageView plusImage, plusBackImage;
    private NoticeFragment noticeFragment;
    private MessageFragment messageFragment;
    private UserFragment userFragment;
    private SetFragment setFragment;
    View plusView = null;
    PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initMenu();
        Intent intent = getIntent();
        String destination = intent.getStringExtra("to");
        if ("NoticationList".equals(destination)) {
            setNoticeMenuBackground();
        } else if ("MessageList".equals(destination)) {
            setMessageMenuBackground();
        } else {
            setNoticeMenuBackground();
        }
    }

    /**
     * 初始化 menu
     */
    private void initMenu() {
        plusBackImage = (ImageView) this.findViewById(R.id.menu_plus_back_img);
        plusImage = (ImageView) this.findViewById(R.id.menu_plus_img);

        noticeImg = (ImageView) this.findViewById(R.id.menu_notice_list_img);
        messageImg = (ImageView) this.findViewById(R.id.menu_message_list_img);
        userImg = (ImageView) this.findViewById(R.id.menu_user_img);
        setImg = (ImageView) this.findViewById(R.id.menu_set_img);

        noticeLayout = (FrameLayout) this.findViewById(R.id.bottom_notice_list_layout);
        messageLayout = (FrameLayout) this.findViewById(R.id.bottom_message_list_layout);
        userLayout = (FrameLayout) this.findViewById(R.id.bottom_user_layout);
        setLayout = (FrameLayout) this.findViewById(R.id.bottom_set_layout);

        plusBackImage.setOnClickListener(this);

        noticeLayout.setOnClickListener(this);
        messageLayout.setOnClickListener(this);
        userLayout.setOnClickListener(this);
        setLayout.setOnClickListener(this);
    }

    protected void setNoticeMenuBackground() {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content, new NoticeFragment());
        fragmentTransaction.commit();
        noticeLayout.setSelected(true);
        messageLayout.setSelected(false);
        userLayout.setSelected(false);
        setLayout.setSelected(false);

        noticeImg.setSelected(true);
        messageImg.setSelected(false);
        userImg.setSelected(false);
        setImg.setSelected(false);
    }

    protected void setMessageMenuBackground() {
        messageFragment = new MessageFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content, messageFragment);
        fragmentTransaction.commit();
        noticeLayout.setSelected(false);
        messageLayout.setSelected(true);
        userLayout.setSelected(false);
        setLayout.setSelected(false);

        noticeImg.setSelected(false);
        messageImg.setSelected(true);
        userImg.setSelected(false);
        setImg.setSelected(false);
    }


    protected void setUserMenuBackground() {
        userFragment = new UserFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content, userFragment);
        fragmentTransaction.commit();
        noticeLayout.setSelected(false);
        messageLayout.setSelected(false);
        userLayout.setSelected(true);
        setLayout.setSelected(false);

        noticeImg.setSelected(false);
        messageImg.setSelected(false);
        userImg.setSelected(true);
        setImg.setSelected(false);
    }


    protected void setSetMenuBackground() {
        setFragment = new SetFragment();
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content, setFragment);
        fragmentTransaction.commit();
        noticeLayout.setSelected(false);
        messageLayout.setSelected(false);
        userLayout.setSelected(false);
        setLayout.setSelected(true);

        noticeImg.setSelected(false);
        messageImg.setSelected(false);
        userImg.setSelected(false);
        setImg.setSelected(true);
    }


    private void clickPlusBtn() {
        showPlusMenuView(plusBackImage);
        plusImage.setImageResource(R.drawable.tae_logo_reverse);
    }


    private void restorePlusBtn() {
        plusImage.setImageResource(R.drawable.tae_logo);
    }

    private void showPlusMenuView(View parent) {
        if (popup == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            plusView = layoutInflater.inflate(R.layout.menu_tae, null);
            popup = new PopupWindow(plusView, LinearLayout.LayoutParams.MATCH_PARENT, 320);
        }
        popup.setFocusable(true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(parent, Gravity.CLIP_VERTICAL, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                restorePlusBtn();
            }
        });
    }

    /**
     * 当执行 菜单 中的点击操作
     *
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_plus_back_img:
                clickPlusBtn();
                break;
            case R.id.bottom_notice_list_layout:
                setNoticeMenuBackground();
                break;
            case R.id.bottom_message_list_layout:
                setMessageMenuBackground();
                break;
            case R.id.bottom_user_layout:
                setUserMenuBackground();
                break;
            case R.id.bottom_set_layout:
                setSetMenuBackground();
                break;
            default:
                break;
        }
    }

}
