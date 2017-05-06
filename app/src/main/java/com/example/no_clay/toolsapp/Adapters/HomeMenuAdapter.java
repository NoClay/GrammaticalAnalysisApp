package com.example.no_clay.toolsapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.no_clay.toolsapp.R;
import com.example.no_clay.toolsapp.Utils.MyConstants;

/**
 * Created by no_clay on 2017/3/2.
 */

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.ViewHolder>{
    Context mContext;
    int resource;
    OnItemClickListener mOnItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public HomeMenuAdapter(Context context, int resource) {
        mContext = context;
        this.resource = resource;
    }

    public HomeMenuAdapter(Context context) {
        this(context, R.layout.menu_item);
    }

    @Override
    public HomeMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.icon.setImageResource(MyConstants.MENU_ICON[position]);
        holder.title.setText(MyConstants.MENU_TITLE[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return MyConstants.MENU_TITLE.length;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.menuIcon);
            title = (TextView) itemView.findViewById(R.id.menuTitle);
        }
    }
}
