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
 * Created by Erin on 2015-12-16.
 */
public class RankListViewAdapter extends BaseAdapter {

    ArrayList<RankListViewItem> datas;
    LayoutInflater inflater;

    public RankListViewAdapter(LayoutInflater inflater, ArrayList<RankListViewItem> datas) {

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
            convertView= inflater.inflate(R.layout.listvist_item_rank, null);

        }


        TextView text_rank = (TextView)convertView.findViewById(R.id.rank_num_text);
        TextView text_data = (TextView)convertView.findViewById(R.id.rank_text);


        text_rank.setText(datas.get(position).mRank + "");
        text_data.setText(datas.get(position).mText);

        return convertView;

    }
}
