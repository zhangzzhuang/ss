package com.example.lenovo.myapplication.mvp.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.myapplication.mvp.main.view.activity.Data;
import com.example.lenovo.myapplication.R;
import com.example.lenovo.myapplication.mvp.business.model.Business;

import java.util.List;

/**
 * @User basi
 * @Date 2019/4/8
 * @Time 8:51 PM
 * @Description ${DESCRIPTION}
 */
public class DiscoveryFragmentAdapter extends RecyclerView.Adapter<DiscoveryFragmentAdapter.FollowFragmentViewHolder>{

    private Context mContext;
    private List<Business> dataSource;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        /**
         * onItemClick
         * @param position
         */
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DiscoveryFragmentAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setDataSource(List<Business> dataSource) {
        this.dataSource = dataSource;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FollowFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_recycerview_item,viewGroup,false);
        return new FollowFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowFragmentViewHolder followFragmentViewHolder, int i) {
        Business business = dataSource.get(i);
        Glide.with(mContext)
                .load(Data.urlImageBusiness + business.getImage())
                .into(followFragmentViewHolder.imageView);
        followFragmentViewHolder.textView.setText(business.getShop_name());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class FollowFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        public FollowFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
            textView = itemView.findViewById(R.id.name_item);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
