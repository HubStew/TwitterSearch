package com.example.erin.twitterir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.erin.twitterir.Custom.CustomDialog;
import com.example.erin.twitterir.Custom.ListViewAdapter;
import com.example.erin.twitterir.Custom.ListViewItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.shineware.util.common.model.Pair;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;


public class FragmentWordSearch extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    Button searchButton;
    Button graphButton;
    EditText searchEditText;

    private View v;
    TwitterSetting twit;

    private Context mContext;
    public WordSearchTask wordSearchTask;

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    private String SearchStr;
    private boolean clickFlag = true;

    HashMap<String, Integer> map = new HashMap<String, Integer>();

    ArrayList<ListViewItem> listItem= new ArrayList<ListViewItem>();

    /*private static FragmentWordSearch uniqueInstance;

    public static FragmentWordSearch getInstance() {

        synchronized (FragmentWordSearch.class) {
            if (uniqueInstance == null) {
                uniqueInstance = new FragmentWordSearch();
            }
        }
        return uniqueInstance;
    }
    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_word_search, container, false);

        searchButton = (Button)v.findViewById(R.id.word_search_button);
        searchEditText = (EditText)v.findViewById(R.id.word_search_edit_text);
        mListView = (ListView)v.findViewById(R.id.word_result_list_view);
        graphButton =  (Button)v.findViewById(R.id.graph_button);

        searchButton.setOnClickListener(mButtonClickListener);
        graphButton.setOnClickListener(mGraphButtonClickListener);

        mContext = getContext();
        twit = new TwitterSetting();


        mAdapter = new ListViewAdapter(inflater, listItem);
        mListView.setOnItemClickListener(listViewListener);


        return v;
    }

    Button.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.i("WordSearch", "클릭");
            map.clear();
            SearchStr = searchEditText.getText().toString();

            if (SearchStr.equals("") == true) {
                Toast.makeText(mContext, "검색 단어를 입력해 주세요.", Toast.LENGTH_SHORT).show();

            } else {
                wordSearchTask = new WordSearchTask();
                wordSearchTask.execute();

                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
            }


        }
    };

    Button.OnClickListener mGraphButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.i("WordSearch", "클릭");

            Intent i = new Intent(mContext, ActivityChart.class);

            //getMapLog();
            //getMapStatic();

            i.putExtra("hashMap", map);
            i.putExtra("title", searchEditText.getText().toString());
            startActivity(i);


        }
    };

    public void search(String str)
    {

        Log.i("search", str);
        SearchStr = str;
        searchEditText.setText(str);
        //wordSearchTask = new WordSearchTask();
        //wordSearchTask.execute();

    }

    private class WordSearchTask extends AsyncTask<Void , Void, Void > {

        private Exception exception;
        ProgressDialog asyncDialog = new ProgressDialog(mContext);


        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("정보를 가져오는 중입니다..");
            asyncDialog.setCanceledOnTouchOutside(false);
            asyncDialog.setCancelable(false);
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            QuerySearch(SearchStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //getMapLog();
            asyncDialog.dismiss();
            mListView.setAdapter(mAdapter);

        }
    }

    public QueryResult QuerySearch(String str)
    {
        Query query = new Query(str + "+exclude:retweets" + "+exclude:links");
        query.resultType(Query.ResultType.recent);

        query.setCount(100);
        //query.setSince("2015-11-29");
        //query.setUntil("2015-12-01");


        QueryResult result = null;

        listItem.clear();

        int searchResultCount;
        long lowestTweetId = Long.MAX_VALUE;

        int count = 0;

       // do {

            try {
                result = twit.twitter.search(query);
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            searchResultCount = result.getTweets().size();
            Log.i("Twit", result.getTweets().size() + "");


            for (Status tweet : result.getTweets()) {

                count++;
                // do whatever with the tweet
                //Log.i("twit", "검색 " + count + " " + tweet.getUser().getScreenName() + ":" +
                //        tweet.getText() + " " + tweet.getCreatedAt());

                Drawable d = null;
                try {
                    d = TwitterSetting.drawableFromUrl(tweet.getUser().getBiggerProfileImageURLHttps());
                    //Log.i("twit", d.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listItem.add(new ListViewItem(tweet.getText(), tweet.getUser().getName(), tweet.getUser().getScreenName(),
                        tweet.getCreatedAt().toString(), d));

                analyze(tweet.getText());

                if (tweet.getId() < lowestTweetId) {
                    lowestTweetId = tweet.getId();
                    query.setMaxId(lowestTweetId);
                }
            }

        //} while (searchResultCount != 0 && searchResultCount % 100 == 0);
        clickFlag = true;
        if (count == 0) {
            listItem.add(new ListViewItem("", "검색 결과가 없습니다.", "", "", null));
            clickFlag = false;
        }

        return result;
    }

    private void analyze(String str)
    {
        List<List<Pair<String,String>>> result = MainActivity.analyzer.analyze(str);

        //Log.i("stem", "--------------------------------------");
        for (List<Pair<String, String>> eojeolResult : result) {

            for (Pair<String, String> wordMorph : eojeolResult) {
                if (wordMorph.getSecond().toString().equals("NNG") && checkSpell(wordMorph.getFirst()) == true
                        && !searchEditText.getText().toString().contains(wordMorph.getFirst())) {

                    if (map.get(wordMorph.getFirst()) == null)
                    {
                        map.put(wordMorph.getFirst(), 1);
                    }
                    else
                    {
                        map.put(wordMorph.getFirst(), map.get(wordMorph.getFirst()) + 1);
                    }
                }

            }
        }
    }

    private boolean checkSpell(String s)
    {
        if (s.equals("ㅂ") || s.equals("ㅈ") || s.equals("ㄷ") || s.equals("ㄱ") || s.equals("ㅅ") || s.equals("ㅛ") ||
                s.equals("ㅕ") || s.equals("ㅑ") || s.equals("ㅐ") || s.equals("ㅔ") || s.equals("ㅁ") || s.equals("ㄴ") ||
                s.equals("ㅇ") || s.equals("ㄹ") || s.equals("ㅎ") || s.equals("ㅗ") || s.equals("ㅓ") || s.equals("ㅏ") ||
                s.equals("ㅣ") || s.equals("ㅋ") || s.equals("ㅌ") || s.equals("ㅊ") || s.equals("ㅍ") || s.equals("ㅠ") ||
                s.equals("ㅜ") || s.equals("ㅡ") || s.equals("ㅃ") || s.equals("ㅉ") || s.equals("ㄸ") || s.equals("ㄲ") ||
                s.equals("ㅆ") || s.equals("ㅖ") || s.equals("ㅒ"))
        {
            return false;
        }

        return true;
    }




    AdapterView.OnItemClickListener listViewListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (clickFlag == true) {
                CustomDialog dialog = new CustomDialog(mContext, listItem.get(position).mText, listItem.get(position).mId
                        , listItem.get(position).mNick, listItem.get(position).mDate, listItem.get(position).mIcon);
                dialog.show();
            }

            //Toast.makeText(mContext, listItem.get(position).mText, Toast.LENGTH_SHORT).show();
        }
    };


}
