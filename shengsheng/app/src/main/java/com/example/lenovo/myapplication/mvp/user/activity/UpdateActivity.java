package com.example.lenovo.myapplication.mvp.user.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.manager.UserManage;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateActivity extends Activity {
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_OK = -1;
    private static final int REQUEST_PERMISSION = 2;
    private OkHttpClient okHttpClient;
    ImageView update_image;
    EditText newQianming;
    TextView name;
    private String qianming;
    RadioButton radioButton;
    String sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update);
        okHttpClient=new OkHttpClient();
        name = findViewById(R.id.new_name);
        newQianming = findViewById(R.id.new_qianming);
        update_image = findViewById(R.id.update_touxiang);
        //修改头像
        update_image.setFocusable(true);
        update_image.setFocusableInTouchMode(true);
        update_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                update_image.setFocusableInTouchMode(false);
                //打开手机相册，动态申请权限；
                ActivityCompat.requestPermissions(UpdateActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},REQUEST_PERMISSION);

            }
        });
        Glide.with(UpdateActivity.this)
                .load(Data.urlImage+UserManage.getInstance().getUserInfo(this).getImage())
                .into(update_image);
        Log.e("update_image",Data.urlImage+UserManage.getInstance().getUserInfo(this).getImage());

        //修改性别
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton)findViewById(checkedId);
                sex = (String) radioButton.getText();
                Log.e("radioButton", (String) radioButton.getText());
                OkHttpClient okHttpClientsex = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Data.url+"GengGaiSex?user_id="+Data.user.getId()+"&sex="+sex)
                        .build();
                Call call = okHttpClientsex.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.e("onFailure","修改Failure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("sex","修改sex");
                    }
                });
            }
        });
        //修改签名，无修改用户名
        name.setHint(Data.user.getName());
        newQianming.setText(Data.user.getQianming());
        Button baocun = findViewById(R.id.baocun);
        baocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qianming = newQianming.getText().toString();

                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(Data.url+"GengGaiQianMing?user_id="+Data.user.getId()+"&qianming="+qianming)
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Log.e("onFailure","修改Failure");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("onResponse","修改成功");

                    }
                });


                Data.user.setQianming(qianming);
                Toast toast =Toast.makeText(UpdateActivity.this,"修改成功",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.show();
                finish();
            }
        });
        ImageView update_back= findViewById(R.id.update_back);

        update_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateActivity.this.finish();
            }
        });




    }
    /*
    上传方法
     */
    private void doUploadFile(File file)  {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String str=UserManage.getInstance().getUserInfo(this).getUserName();
        String imgname = null;
        try {
            imgname = URLDecoder.decode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/*; charset=utf-8"),file);
        Request request = new Request.Builder().url(Data.url+"UploadUserFile?name="+imgname)
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
                Log.e("updatesuccesslog",response.body().string());
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
            Glide.with(this).load(file).into(update_image);
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
        Log.e("text","update");
        startActivityForResult(intent,REQUEST_CODE);
    }
}
