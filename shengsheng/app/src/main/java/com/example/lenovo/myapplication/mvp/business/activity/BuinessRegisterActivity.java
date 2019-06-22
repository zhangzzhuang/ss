package com.example.lenovo.myapplication.mvp.business.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.mvp.business.model.Business;

import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.activity.UserLoginActivity;
import com.example.lenovo.myapplication.mvp.user.activity.UserRegisterActivty;
import com.example.lenovo.myapplication.utils.DialogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/23.12
 */

public class BuinessRegisterActivity extends Activity {
    private String businessStr;
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int REQUEST_PERMISSION = 2;
    private static final String TAG = "appbusiness";
    private OkHttpClient okHttpClient;
    private TextView business_register_back;
    private ImageView business_image;
    private EditText business_name;
    private EditText business_name_1;
    private EditText business_password;
    private EditText business_comfirm_password;
    private EditText business_phone;
    private EditText business_address;
    private Button business_register;
    private Button business_map_xy;
    private Business business = new Business();
    private static final int RESSUCCESS = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buiness_registered_layout);
        okHttpClient=new OkHttpClient();
        business_register_back=findViewById(R.id.Buiness_register_back);
        business_image=findViewById(R.id.topImage);
        business_name=findViewById(R.id.Buiness_Register_username);
        business_name_1=findViewById(R.id.Buiness_name_1);
        business_password=findViewById(R.id.Buiness_Register_password);
        business_comfirm_password=findViewById(R.id.Buiness_confirm_password);
        business_phone=findViewById(R.id.Buiness_phone);
        business_address=findViewById(R.id.Buiness_address);
        business_register=findViewById(R.id.Buiness_register);
        business_map_xy=findViewById(R.id.Business_map_xy);
        business_map_xy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BuinessRegisterActivity.this,BusinessMap.class);
                startActivity(intent);
            }
        });
        business_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String str=business_name.getText().toString();

            }
        });
        business_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String string=business_password.getText().toString();
                if (!hasFocus){
                    if (string.length()>=6&&string.length()<=9){
                        return;
                    }else {
                        Toast.makeText(BuinessRegisterActivity.this,"密码长度应该在6-9位之间",Toast.LENGTH_SHORT).show();
                        business_password.setText("");
                    }
                }

            }
        });
        business_comfirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (business_comfirm_password.getText().toString()
                            .equals(business_password.getText().toString())) {
                        Toast.makeText(BuinessRegisterActivity.this,"密码一致",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BuinessRegisterActivity.this,"密码不相同或未输入，请重新输入！",Toast.LENGTH_SHORT).show();
                        business_comfirm_password.setText("");
                    }
                }
            }
        });
        //返回登录页
        business_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuinessRegisterActivity.this.finish();
            }
        });
        //上传头像
        business_image.setFocusable(true);
        business_image.setFocusableInTouchMode(true);
        business_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                business_name.setFocusableInTouchMode(false);

                //打开手机相册，动态申请权限；
                ActivityCompat.requestPermissions(BuinessRegisterActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},REQUEST_PERMISSION);


            }
        });
        business_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=business_name.getText().toString();
                String password=business_password.getText().toString();
                Request request=new Request.Builder()
                        .url(Data.url+"businessRegister?name=" +business_name.getText().toString()
                        +"&password="+password
                                + "&image="+business_name.getText().toString()+".jpg"
                                + "&shopname=" +business_name_1.getText().toString()
                                + "&phone=" +business_phone.getText().toString()
                                + "&address="+Data.businessLocation+
                                "&add_name="+business_address.getText().toString())
                        .build();
                Call call =okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                            businessStr = response.body().string();
                            Gson gson=new Gson();
                            Type type= new TypeToken<Business>(){}.getType();
                            business=gson.fromJson(businessStr,type);
                            if (business.getName().equals(username)){
                                Message message = new Message();
                                message.what = RESSUCCESS;
                                mhandler.sendMessage(message);

                            }
                    }
                });

            }
        });
    }


    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case RESSUCCESS:
                    final DialogUtils dialogUtils = new DialogUtils(BuinessRegisterActivity.this,R.string.success_prompt,true);
                    dialogUtils.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogUtils.dismiss();
                            BuinessRegisterActivity.this.startActivity(new Intent(BuinessRegisterActivity.this,BusinessLoginActivity.class));
                            BuinessRegisterActivity.this.finish();
                        }
                    },3000);

                    break;
                default:
                    break;
            }
        }
    };
    /*
    上传方法
     */
    private void doUploadFile(File file)  {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String imgname= null;
        EditText business_name=findViewById(R.id.Buiness_Register_username);
        try {
            imgname = URLDecoder.decode(business_name.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/*; charset=utf-8"),file);
        Request request = new Request.Builder().url(Data.url+"UploadBusinessFile?name="+imgname)
                .post(requestBody).build();
        Call call=mOkHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG,response.body().string());
                Log.e("aaaaa","上传成功");
            }
        });


    }
    //相册界面返回之后回调方法

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取照片
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            //获取照片
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            cursor.moveToFirst();
            String column = MediaStore.Images.Media.DATA;
            int columIndex = cursor.getColumnIndex(column);
            String path = cursor.getString(columIndex);
            File file = new File(path);
            Glide.with(this).load(file).into(business_image);
            doUploadFile(file);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //打开手机相册
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        Log.e("text","businessregister");
        startActivityForResult(intent,REQUEST_CODE);
    }
}
