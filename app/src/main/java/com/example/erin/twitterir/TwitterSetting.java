package com.example.erin.twitterir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Erin on 2015-11-25.
 */
public class TwitterSetting {

    Twitter twitter;

    User user = null;

    final String CONSUMER_KEY = "";
    final String CONSUMER_SECRET = "";

    final String ACCESS_KEY = "";
    final String ACCESS_SECRET = "";

    ConfigurationBuilder cb = new ConfigurationBuilder();

    TwitterSetting()
    {

        cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_KEY)
                .setOAuthAccessTokenSecret(ACCESS_SECRET);


        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();


/*
        try {
            user = twitter.verifyCredentials();
        } catch (TwitterException e) {
            e.printStackTrace();
        }


        Paging page = new Paging();
        page.setCount(100);

        List<Status> statuses = new ArrayList<Status>();
        try {
            statuses = twitter.getUserTimeline("TwitterKR", page);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        for(Status status : statuses) {
            Log.i("twit", "?ëÏÑ±??: " + status.getUser().getScreenName() + " " + status.getText());
        }

        */
/*

        List<Status> list = null;

        try {
           // list = twitter.getUserTimeline();
            list = twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }


        // ScreenName : id  Name : nickname

        Log.i("twit", "?Ä?ÑÎùº??Í≥ÑÏ†ï:" + user.getScreenName() + " " + user.getName());
        for(Status status : list) {
            Log.i("twit", "?ëÏÑ±??" + status.getUser().getName() + " " + status.getText());
        }
*/
    }

    public Twitter getTwitter()
    {
        return twitter;
    }


    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }



}
