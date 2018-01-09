package com.cpigeon.app.pigeonnews.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpigeon.app.R;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class ReplyAdapter extends BaseAdapter {

    List<String> data;
    LayoutInflater inflater;

    public ReplyAdapter(Context context, List<String> data){
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_news_reply_layout,parent,false);
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.reply);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(data.get(position));

        return convertView;
    }
}


class ViewHolder{
    TextView textView;
}
