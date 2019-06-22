package com.example.lenovo.myapplication.mvp.main.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.manager.BusinessManage;
import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.business.activity.BuinessActivity;
import com.example.lenovo.myapplication.mvp.user.activity.UserLoginActivity;
import com.example.lenovo.myapplication.utils.StreamTools;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.lenovo.myapplication.mvp.main.view.activity.Data.business;
import static com.example.lenovo.myapplication.mvp.main.view.activity.Data.user;


/**
 * @User basi
 * @Date 2019/4/1
 * @Time 3:10 PM
 * @Description ${欢迎页面}
 */
public class WelcomePageActivity extends AppCompatActivity {

    private static final int GO_HOME_USER = 0;//user主页
    private static final int GO_HOME_BUSSINES = 1;//bussiness主页

    private static final int GO_LOGIN = 2;//登录页

    Timer timer = new Timer();
    private int recLen = 4;
    private TextView tvSkip;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        tvSkip = findViewById(R.id.tvSkip);

        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                if (UserManage.getInstance().hasUserInfo(WelcomePageActivity.this)){
                    mHandler.sendEmptyMessage(GO_HOME_USER);
                } else if (BusinessManage.getInstance().hasBussinessInfo(WelcomePageActivity.this)){
                    mHandler.sendEmptyMessage(GO_HOME_BUSSINES);
                } else {
                    mHandler.sendEmptyMessage(GO_LOGIN);
                }
            }
        },4000);

        timer.schedule(timerTask,0,1000);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserManage.getInstance().hasUserInfo(WelcomePageActivity.this)){
                    mHandler.sendEmptyMessage(GO_HOME_USER);
                } else if (BusinessManage.getInstance().hasBussinessInfo(WelcomePageActivity.this)){
                    mHandler.sendEmptyMessage(GO_HOME_BUSSINES);
                } else {
                    mHandler.sendEmptyMessage(GO_LOGIN);
                }
                if (runnable !=null){
                    handler.removeCallbacks(runnable);
                }
            }
        });
    }
    int id;
    String name;
    String password;
    String phone;
    String baidu_zuobiao;
    String image;
    int followers_num;
    int qiandao;
    Date qiandao_date;
    String shop_name;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_HOME_USER:
                    user.setId(Integer.parseInt(UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getId()));
                    user.setName(UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getUserName());
                    user.setBusiness_id(UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getBusiness_id());
                    user.setChakan_goods(UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getChakan_goods());
                    user.setCollection_id( UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getCollection_id());
                    user.setImage(UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getImage());
                    user.setQiandao(Integer.parseInt(UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getQiandao()));
                    user.setQianming( UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getQianming());
                    user.setSex(UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getSex());
                    user.setQiandao_date(StreamTools.StrToDate(UserManage.getInstance().getUserInfo(WelcomePageActivity.this).getQiandao_date()));

                    Intent intent = new Intent(WelcomePageActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_HOME_BUSSINES:
                    business.setId(Integer.parseInt(BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getId()));
                    business.setName( BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getName());
                    business.setPassword( BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getPassword());
                    business.setPhone( BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getPhone());
                    business.setBaidu_zuobiao( BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getBaidu_zuobiao());
                    business.setImage(BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getImage());
                    business.setFollowers_num(Integer.parseInt(BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getFollowers_num()));
                    business.setQiandao(Integer.parseInt(BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getQiandao()));
                    business.setQiandao_date(StreamTools.StrToDate( BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getQiandao_date()));
                    business.setShop_name( BusinessManage.getInstance().getBusinessInfo(WelcomePageActivity.this).getShop_name());

                    BuinessActivity.readGo(WelcomePageActivity.this);
                    finish();
                    break;
                case GO_LOGIN:
                   UserLoginActivity.readGo(WelcomePageActivity.this);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };


    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recLen--;
                    tvSkip.setText(recLen+"s "+"跳过");
                    if (recLen < 0){
                        timer.cancel();
                        tvSkip.setVisibility(View.GONE);
                    }
                }
            });
        }
    };
}
