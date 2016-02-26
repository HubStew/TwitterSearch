package com.example.erin.twitterir.Custom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erin.twitterir.R;

import java.util.ArrayList;

/**
 * Created by Erin on 2015-12-02.
 */
public class ListViewAdapter extends BaseAdapter {


    ArrayList<ListViewItem> datas;

    LayoutInflater inflater;

    public ListViewAdapter(LayoutInflater inflater, ArrayList<ListViewItem> datas) {

        // TODO Auto-generated constructor stub
        this.datas= datas;
        this.inflater= inflater;
    }

    @Override
    public int getCount() {
        return datas.size(); //datas의 개수를 리턴
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);//datas의 특정 인덱스 위치 객체 리턴.
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        if( convertView==null) {
            convertView= inflater.inflate(R.layout.listview_item, null);

        }


        TextView text_content = (TextView)convertView.findViewById(R.id.list_text);
        TextView text_date = (TextView)convertView.findViewById(R.id.list_date);
        TextView text_nick = (TextView)convertView.findViewById(R.id.list_nick);
        //TextView text_id = (TextView)convertView.findViewById(R.id.list_date);


        ImageView img_flag= (ImageView)convertView.findViewById(R.id.list_image);


        text_content.setText(datas.get(position).mText);
        text_nick.setText(datas.get(position).mNick);
        text_date.setText(datas.get(position).mDate);
        img_flag.setImageDrawable(datas.get(position).mIcon);

        return convertView;
    }


}
