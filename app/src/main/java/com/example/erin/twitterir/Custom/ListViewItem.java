package com.example.erin.twitterir.Custom;

import android.graphics.drawable.Drawable;

/**
 * Created by Erin on 2015-12-02.
 */
public class ListViewItem {

    // 아이콘
    public Drawable mIcon;
    public String mText;
    public String mNick;
    public String mId;
    public String mDate;

    public ListViewItem(String text, String Nick, String Id, String date, Drawable image)
    {
        mText = text;
        mNick = Nick;
        mId = Id;
        mDate = date.replace("GMT+09:00 ", "");
        mIcon = image;
    }

}
