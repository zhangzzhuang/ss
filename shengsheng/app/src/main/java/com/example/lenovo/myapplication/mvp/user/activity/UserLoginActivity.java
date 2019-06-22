package com.example.lenovo.myapplication.mvp.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.myapplication.mvp.business.activity.BusinessLoginActivity;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.main.view.activity.MainActivity;
import com.example.lenovo.myapplication.mvp.user.model.User;
import com.example.lenovo.myapplication.utils.MD5Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.lenovo.myapplication.mvp.main.view.activity.Data.user;

/**
 * @User basi
 * @Date 2019/4/1
 * @Time 3:55 PM
 * @Description ${用户登录页}
 */
public class UserLoginActivity extends Activity {
    private String userString;


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.subBtn1)
    Button subBtn1;



    @OnClick(R.id.subBtn1)
    public void userLogin(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url + "userLogin?name=" + username.getText().toString() + "&password=" + password.getText().toString())
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            userString = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type type=new TypeToken<User>(){}.getType();
        user = gson.fromJson(userString,type);

        if(user!=null && user.getName().equals(username.getText().toString())){

            UserManage.getInstance().saveUserInfo(UserLoginActivity.this,user.getName(),user.getPassword(), String.valueOf(user.getId())
            ,user.getPhone(),user.getCollection_id(),user.getBusiness_id(), String.valueOf(user.getQiandao()),user.getQiandao_date(),user.getImage(),user.getChakan_goods(),user.getSex(),user.getQianming());

            Intent intent=new Intent(UserLoginActivity.this,MainActivity.class);
            startActivity(intent);
            UserLoginActivity.this.finish();
        }else {
            username.setText("");
            password.setText("");

            Toast.makeText(UserLoginActivity.this,"please try again",Toast.LENGTH_LONG).show();
        }

    }


    @OnClick(R.id.subBtn2)
    public void userReg(){
        Intent intent=new Intent(UserLoginActivity.this,UserRegisterActivty.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }


    public static void readGo(Context context){
        Intent intent = new Intent(context,UserLoginActivity.class);
        context.startActivity(intent);
    }

    @OnTextChanged(value = R.id.password,callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onPwdEtChanged(CharSequence c, int i, int i1, int i2) {
        String name = username.getText().toString();
        if (!TextUtils.isEmpty(name)){
            if (c.length()>0){
                subBtn1.setBackgroundResource(R.drawable.background_button_div);
                subBtn1.setClickable(true);
            }else {
                subBtn1.setBackgroundResource(R.drawable.login_btn_gray);
                subBtn1.setClickable(false);
            }
        }else {
            subBtn1.setClickable(false);
            subBtn1.setBackgroundResource(R.drawable.login_btn_gray);
        }
    }


    @OnClick(R.id.enter_business_login)
    public void busLogin(){
        Intent intent=new Intent(UserLoginActivity.this,BusinessLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
