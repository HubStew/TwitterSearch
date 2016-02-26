package com.example.erin.twitterir;

import android.util.Log;

import com.example.erin.twitterir.Custom.RankListViewItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Erin on 2015-11-30.
 */
public class SetNaverRankWord {

    Document doc = null;
    private ArrayList<RankListViewItem> rankList = new ArrayList<RankListViewItem>();

    public void SetRankList() {

        rankList.clear();

        try {
            doc = Jsoup.connect("http://www.naver.com/?mobile").get();
        } catch (IOException e1) {

        }

        Elements elements = doc.select("ol#realrank li a");
        // realrank는 ol의 id
        Log.i("paser", "class into");

        for(int i = 0; i < elements.size()-1; i++)
        {
            String article = elements.get(i).attr("title");
            //Log.i("paser", article);
            rankList.add(new RankListViewItem(i+1, article));
            //rankList.add( " " + (i+1) + " |   " + article);
        }

        /*for (Element e : elements) {

            String article = e.attr("title");
            // 실시간 검색어 텍스트
            String articleHref = e.attr("abs:href");
            // 실시간 검색어 링크
            Log.i("paser", article);
        }*/
    }

    public void RankListClear() { rankList.clear(); }

    public ArrayList GetRankList()
    {
        return rankList;
    }
}




