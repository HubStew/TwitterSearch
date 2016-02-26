package com.example.erin.twitterir;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.erin.twitterir.Custom.RankListViewAdapter;
import com.example.erin.twitterir.Custom.RankListViewItem;
import com.example.erin.twitterir.R;

import java.util.ArrayList;

import kr.co.shineware.nlp.komoran.core.MorphologyAnalyzer;


public class FragmentRank extends Fragment {

    SetNaverRankWord setNaverRankWord = new SetNaverRankWord();
    private ArrayList<RankListViewItem> rankList;
    private RankTask task;
    private StemmerTask stemmerTask;

    LayoutInflater inflater;

    private RankListViewAdapter adapter;
    private View v;

    private ListView rankListView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_rank, container, false);
        this.inflater = inflater;

        rankListView = (ListView)v.findViewById(R.id.rank_list_view);
        rankListView.setOnItemClickListener(listViewListener);

        mContext = getContext();

        Log.i("log", "FragmentRank");
        task = new RankTask();
        task.execute();


        return v;
    }

    private class RankTask extends AsyncTask<Void , Void, Void > {

        private Exception exception;
        ProgressDialog asyncDialog = new ProgressDialog(mContext);



        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");
            asyncDialog.setCanceledOnTouchOutside(false);
            asyncDialog.setCancelable(false);
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            setNaverRankWord.SetRankList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            asyncDialog.dismiss();
            rankList = setNaverRankWord.GetRankList();

            adapter = new RankListViewAdapter(inflater, rankList);
            rankListView.setAdapter(adapter);

            if (MainActivity.analyzer == null)
            {
                stemmerTask = new StemmerTask();
                stemmerTask.execute();
            }

        }
    }

    AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //MainActivity.getInstance().mViewPager.setCurrentItem(1);

            //Toast.makeText(mContext, rankList.get(position), Toast.LENGTH_SHORT).show();
        }
    };

    private class StemmerTask extends AsyncTask<Void , Void, Void > {

        private Exception exception;
        ProgressDialog asyncDialog = new ProgressDialog(mContext);


        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("초기 설정 중 입니다...");
            asyncDialog.setCanceledOnTouchOutside(false);
            asyncDialog.setCancelable(false);
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String state = MainActivity.APP_FILE_PATH;
            MainActivity.analyzer = new MorphologyAnalyzer(state);
            //MainActivity.analyzer.setUserDic("userDic.txt");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            asyncDialog.dismiss();
        }
    }



}
