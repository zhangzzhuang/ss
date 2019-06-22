package com.example.lenovo.myapplication.mvp.business.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.manager.BusinessManage;
import com.example.lenovo.myapplication.mvp.business.model.Business;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.user.activity.UserLoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by lenovo on 2018/5/23.
 */

public class BusinessLoginActivity extends Activity {
    private EditText buiness_name;
    private EditText buiness_password;
    private Button buiness_login;
    private Button buiness_register;
    private String userString;
    private TextView UserLogin;
    private Business business = new Business();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangjiadenglu);
        ButterKnife.bind(this);
        UserLogin=findViewById(R.id.back_user_login);
        UserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLoginActivity.readGo(BusinessLoginActivity.this);
                finish();
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        buiness_name=findViewById(R.id.Buiness_name);
        buiness_password=findViewById(R.id.Buiness_password);
        buiness_login=findViewById(R.id.Buiness_login);
        buiness_register=findViewById(R.id.Buiness_registered);

        buiness_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OkHttpClient okHttpClient=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(Data.url+"businessLogin?name="
                        +buiness_name.getText().toString()
                        +"&password=" +buiness_password.getText().toString())
                        .build();
                Call call=okHttpClient.newCall(request);
                try {
                    Response response=call.execute();
                    userString=response.body().string();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson=new Gson();
                Type type =new TypeToken<Business>(){}.getType();
                business=gson.fromJson(userString,type);
                if (business!=null&&business.getName().equals(buiness_name.getText().toString())){
                    BusinessManage.getInstance().saveBussinessInfo(BusinessLoginActivity.this,String.valueOf(business.getId()),business.getName(),business.getPassword(),
                            business.getPhone(),business.getBaidu_zuobiao(),business.getImage(),String.valueOf(business.getFollowers_num()),String.valueOf(business.getQiandao()),business.getQiandao_date(),business.getShop_name());
                    BuinessActivity.readGo(BusinessLoginActivity.this);
                    BusinessLoginActivity.this.finish();
                }else {
                    buiness_name.setText("");
                    buiness_password.setText("");
                    Toast.makeText(BusinessLoginActivity.this,"用户名或密码错误，请重新输入！！",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        buiness_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BusinessLoginActivity.this,BuinessRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @OnTextChanged(value = R.id.Buiness_password,callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onPwdEtChanged(CharSequence c, int i, int i1, int i2) {
        String name = buiness_name.getText().toString();
        if (!TextUtils.isEmpty(name)){
            if (c.length()>0){
                buiness_login.setBackgroundResource(R.drawable.shape);
                buiness_login.setClickable(true);
            }else {
                buiness_login.setBackgroundResource(R.drawable.login_btn_gray);
                buiness_login.setClickable(false);
            }
        }else {
            buiness_login.setClickable(false);
            buiness_login.setBackgroundResource(R.drawable.login_btn_gray);
        }
    }
}
