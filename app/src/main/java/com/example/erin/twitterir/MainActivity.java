package com.example.erin.twitterir;

import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import kr.co.shineware.nlp.komoran.core.MorphologyAnalyzer;
import kr.co.shineware.util.common.model.Pair;


public class MainActivity extends AppCompatActivity {

    public static final String APP_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TwitterIR/";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Fragment fragment = null;
    private Bundle args = null;

    public static MorphologyAnalyzer analyzer = null;

    public ViewPager mViewPager = null;
    private static MainActivity uniqueInstance;

    public static MainActivity getInstance() {

        synchronized (MainActivity.class) {
            if (uniqueInstance == null) {
                uniqueInstance = new MainActivity();
            }
        }
        return uniqueInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        uniqueInstance = this;
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        InitClass();
        //test();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void InitClass()
    {
        makeAssets("irrDic.txt");
        makeAssets("morphTable.txt");
        makeAssets("observation.obj");
        makeAssets("transition.obj");
        makeAssets("userDic.txt");
    }

    void makeAssets(String fileName)
    {
        InputStream is = null;
        FileOutputStream fos = null;

        String dirStr = Environment.getExternalStorageDirectory().getAbsolutePath();

        File outDir = new File(dirStr + "/TwitterIR");

        if (!outDir.exists())
            outDir.mkdirs();

        File f = new File(outDir + "/" + fileName);
        //Log.i("stem222", f.exists() + " " + outDir);
        if (!f.exists()) {
            try {
                is = getAssets().open("komoran_models/" + fileName);
                int size = is.available();
                byte[] buffer = new byte[size];
                File outfile = new File(outDir + "/" + fileName);
                fos = new FileOutputStream(outfile);
                for (int c = is.read(buffer); c != -1; c = is.read(buffer)) {
                    fos.write(buffer, 0, c);
                }
                is.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {
                case 0:
                    fragment = new FragmentRank();
                    Log.i("ler", "position " + 1);
                    args = new Bundle();

                    args.putInt("1", position + 1);
                    fragment.setArguments(args);

                    return fragment;
                case 1:
                    fragment = new FragmentWordSearch();
                    Log.i("ler", "position " + 2);
                    args = new Bundle();

                    args.putInt("2", position + 1);
                    fragment.setArguments(args);

                    return fragment;

                case 2:
                    fragment = new FragmentPeopleSearch();
                    Log.i("ler", "position " + 3);
                    args = new Bundle();

                    args.putInt("3", position + 1);
                    fragment.setArguments(args);

                    return fragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "실시간 검색어";
                case 1:
                    return "단어 검색";
                case 2:
                    return "ID 검색";
            }
            return null;
        }


    }

}
