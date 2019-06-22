package com.example.lenovo.myapplication.mvp.user.fragment;

/**
 * Created by lenovo on 2018/5/24.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.mvp.user.adapter.DiscoveryFragmentAdapter;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.user.activity.SearchActivity;
import com.example.lenovo.myapplication.mvp.user.activity.ShopdetialActivity;
import com.example.lenovo.myapplication.mvp.business.model.Business;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dingin on 2018/3/19.
 */

public class DiscoveryFragment extends Fragment implements DiscoveryFragmentAdapter.OnItemClickListener {


    private Gson gson;
    private String businessesListStr;
    private Type type;
    private List<Business> businessList;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private DiscoveryFragmentAdapter discoveryFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.activity_discovery,container,false);

        refreshLayout = view.findViewById(R.id.refreshDiscovery);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                upPullRefresh(refreshLayout);
            }
        });

        recyclerView = view.findViewById(R.id.frag_follow_recycler);
        StaggeredGridLayoutManager sgLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(sgLayoutManager);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageView ivsousuo = view.findViewById(R.id.ivsousuo);
        ivsousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),SearchActivity.class);
                startActivityForResult(intent,0);
            }
        });

        //获取商家
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url+"Faxian_index")
                .build();
        final Call call = okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            businessesListStr = response.body().string();
            gson = new Gson();
            type = new TypeToken<List<Business>>(){}.getType();
            businessList = gson.fromJson(businessesListStr,type);

        } catch (IOException e) {
            e.printStackTrace();
        }

        discoveryFragmentAdapter = new DiscoveryFragmentAdapter(view.getContext());
        discoveryFragmentAdapter.setDataSource(businessList);
        recyclerView.setAdapter(discoveryFragmentAdapter);
        discoveryFragmentAdapter.setOnItemClickListener(this);

        return view;

       }


    @Override
    public void onItemClick(int position) {
        Intent intent =new Intent(getActivity(),ShopdetialActivity.class);
        intent.putExtra("business",businessList.get(position));
        getContext().startActivity(intent);
    }



    private void upPullRefresh(RefreshLayout refreshLayout) {
        //获取商家
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Data.url+"Faxian_index")
                .build();
        final Call call = okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            businessesListStr = response.body().string();
            gson = new Gson();
            type = new TypeToken<List<Business>>(){}.getType();
            businessList = gson.fromJson(businessesListStr,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        discoveryFragmentAdapter.setDataSource(businessList);
        refreshLayout.finishLoadMore(2000);
    }

}

