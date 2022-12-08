package com.jd.saas.pdagoodsquery.goods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jd.saas.pdagoodsquery.R;

import java.util.List;

/**
 * dialog适配器
 * @author: ext.anxinlong
 * @date: 2021/6/3
 */
public class GoodsQueryDialogAdapter extends BaseAdapter {
    private LayoutInflater mInflater;


    List<String> mDatas;

    public GoodsQueryDialogAdapter(Context context, List<String> list) {
        this.mInflater = LayoutInflater.from(context);
        mDatas = list;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        // Item View的复用
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.goods_query_dialog_item, null);
            // 获取title
            holder.textView = (TextView) convertView.findViewById(R.id.dialog_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mDatas.get(position));

        return convertView;

    }

    private class ViewHolder {
        private TextView textView;
    }

}
