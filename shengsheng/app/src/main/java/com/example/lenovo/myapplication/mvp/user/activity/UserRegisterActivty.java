package com.example.lenovo.myapplication.mvp.user.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.utils.DialogUtils;
import com.example.lenovo.myapplication.utils.MD5Util;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/8.
 */

public class UserRegisterActivty extends Activity{
	private static final int REQUEST_CODE = 1;
	private static final int RESULT_OK = -1;
	private static final int REQUEST_PERMISSION = 2;
	private static final String TAG = "app";
	private EditText rusername;
	private EditText rpassword;
	private String Rpassword;
	private String Rusername;
	private ImageView ReturnLogin;
	private Button button;
	private ImageView postImage;
	private static final int RESSUCCESS = 1;
	final OkHttpClient client = new OkHttpClient();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register_layout);
		ReturnLogin=findViewById(R.id.imgBack);
		rusername = findViewById(R.id.Rusername);
		rpassword = findViewById(R.id.Rpassword);
        button=findViewById(R.id.register);
        ReturnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		postImage=findViewById(R.id.postImage);
        postImage.setFocusable(true);
        postImage.setFocusable(true);

		postImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//打开手机相册，动态申请权限
              ActivityCompat.requestPermissions(UserRegisterActivty.this,
                     new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},REQUEST_PERMISSION);
			}

		});

		rpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus){
					if (rpassword.length()>=6&&rpassword.length()<=9){
						return;
					}else {
						Toast.makeText(UserRegisterActivty.this,"密码长度应该在6-9位之间",Toast.LENGTH_SHORT).show();
						rpassword.setText("");
					}
				}
			}
		});
        button.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            if (rusername.getText().toString().isEmpty()| rpassword.getText().toString().isEmpty()){
                    Toast.makeText(UserRegisterActivty.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                Rusername=rusername.getText().toString();
                Rpassword=rpassword.getText().toString();

		        postRequest(Rusername,Rpassword,Rusername+".jpg");

	        }
        });
	}


	private Handler mhandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case RESSUCCESS:
					final DialogUtils dialogUtils = new DialogUtils(UserRegisterActivty.this,R.string.success_prompt,true);
					dialogUtils.show();
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							dialogUtils.dismiss();
							UserRegisterActivty.this.startActivity(new Intent(UserRegisterActivty.this,UserLoginActivity.class));
							UserRegisterActivty.this.finish();
						}
					},3000);

					break;
					default:
						break;
			}
		}
	};
	private void  postRequest(String name1,String pwd,String Userimg){
		RequestBody formBody=new FormBody.Builder()
				.add("name",name1)
				.add("password",pwd)
				.add("image",Userimg)
				.build();
		final Request request = new Request.Builder()
				.url(Data.url+"userRegister")
				.post(formBody)
				.build();

		//新建一个线程，用于得到服务器响应的参数
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				Response response = null;
				try {
					//回调
					response = client.newCall(request).execute();
					if (response.isSuccessful()) {
							Message message = new Message();
							message.what = RESSUCCESS;
							mhandler.sendMessage(message);

					} else {
						throw new IOException("Unexpected code:" + response);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	/**
	 * 上传文件
	 */
	private void doUploadFile(File file){
		OkHttpClient mOkHttpClient = new OkHttpClient();
		//构建请求体
		final EditText username = findViewById(R.id.Rusername);
		String imgname = null;
		try {
			imgname = URLDecoder.decode(username.getText().toString(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		RequestBody requestBody = RequestBody.create(MediaType.parse("images/*; charset=utf-8"),file);
		Request request = new Request.Builder()
				.url(Data.url+"UploadUserFile?name="+imgname)
				.post(requestBody)
				.build();
		//创建call对象，并执行请求
		final Call call = mOkHttpClient.newCall(request);
		//执行异步请求
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				Log.e(TAG,response.body().string());
			}
		});
	}
	//相册界面返回之后的回调方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
			//获取照片
			Uri uri = data.getData();
			Cursor cursor = getContentResolver().query(uri,null,null,null,null);
			cursor.moveToFirst();
			String column = MediaStore.Images.Media.DATA;
			int columIndex = cursor.getColumnIndex(column);
			String path = cursor.getString(columIndex);
			File file = new File(path);
			Glide.with(this).load(file).into(postImage);
			doUploadFile(file);
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		Log.e("text","text2");

		//打开手机相册
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		Log.e("text","text4");
		startActivityForResult(intent,REQUEST_CODE);

		//}

	}



}