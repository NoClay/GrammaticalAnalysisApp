package com.example.no_clay.toolsapp.GrammticalAnalysis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.no_clay.toolsapp.R;

import java.util.ArrayList;

/**
 * Created by noclay on 2017/5/11.
 */

public class TreeViewAdapter extends BaseAdapter {
    public static final int []COLOR = {
            R.color.color0,
            R.color.color1,
            R.color.color2,
            R.color.color3,
            R.color.color4,
            R.color.color5,
            R.color.color6,
            R.color.color7,
            R.color.color8,
            R.color.color9,
    };
    /** 元素数据源 */
    private ArrayList<Element> elementsData;
    /** 树中元素 */
    private ArrayList<Element> elements;
    /** LayoutInflater */
    private LayoutInflater inflater;
    /** item的行首缩进基数 */
    private int indentionBase;
    private Context mContext;

    public TreeViewAdapter(ArrayList<Element> elements, ArrayList<Element> elementsData, LayoutInflater inflater, Context context) {
        this.elements = elements;
        this.elementsData = elementsData;
        this.inflater = inflater;
        mContext = context;
        indentionBase = 50;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public ArrayList<Element> getElementsData() {
        return elementsData;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.treeview_item, null);
            holder.disclosureImg = (ImageView) convertView.findViewById(R.id.disclosureImg);
            holder.contentText = (TextView) convertView.findViewById(R.id.contentText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Element element = elements.get(position);
        int level = element.getLevel();
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.disclosureImg.getLayoutParams();
        layoutParams.setMargins(indentionBase * (level + 1),
                layoutParams.topMargin,
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        holder.disclosureImg.setLayoutParams(layoutParams);
//        holder.disclosureImg.setPadding(
//                indentionBase * (level + 1),
//                holder.disclosureImg.getPaddingTop(),
//                holder.disclosureImg.getPaddingRight(),
//                holder.disclosureImg.getPaddingBottom());
        holder.contentText.setText(element.getContentText());
        if (element.isHasChildren() && !element.isExpanded()) {
            holder.disclosureImg.setImageResource(R.drawable.open);
            //这里要主动设置一下icon可见，因为convertView有可能是重用了"设置了不可见"的view，下同。
            holder.disclosureImg.setVisibility(View.VISIBLE);
        } else if (element.isHasChildren() && element.isExpanded()) {
            holder.disclosureImg.setImageResource(R.drawable.close);
            holder.disclosureImg.setVisibility(View.VISIBLE);
        } else if (!element.isHasChildren()) {
            holder.disclosureImg.setImageResource(R.drawable.open);
            holder.disclosureImg.setVisibility(View.INVISIBLE);
        }
        holder.disclosureImg.setColorFilter(mContext.getResources().getColor(COLOR[element.getLevel() % COLOR.length]));
        holder.contentText.setTextColor(mContext.getResources().getColor(COLOR[element.getLevel() % COLOR.length]));
        return convertView;
    }

    /**
     * 优化Holder
     * @author carrey
     *
     */
    static class ViewHolder{
        ImageView disclosureImg;
        TextView contentText;
    }
}
