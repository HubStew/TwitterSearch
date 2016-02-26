package com.example.erin.twitterir;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ActivityChart extends AppCompatActivity {

    private PieChart mChart;

    public ArrayList<String> xData = new ArrayList();
    public ArrayList<Float> yData = new ArrayList();

    private final int LIMIT_SIZE = 10;

    HashMap<String, Integer> map;
    public static int MapCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Intent intent = getIntent();
        map = (HashMap<String, Integer>) intent.getSerializableExtra("hashMap");
        String title = (String) intent.getSerializableExtra("title");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title + " 분석 그래프");
        setSupportActionBar(toolbar);



        mChart = (PieChart) findViewById(R.id.chart);

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Graph by MPAndroidChart - GitHub");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        //mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setValueTextColor(Color.BLACK);

        getMapLog();
        getMapStatic();

        // add data
        addData();
        mChart.animateXY(1500, 1500);

        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.size(); i++) {

            int num = yData.get(i).intValue();
            float result = num * 100;
            float visible = result / MapCount;

            if(visible == 0)
            {

            }
            else {
                yVals1.add(new Entry(visible, i));
            }
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.size(); i++)
            xVals.add(xData.get(i));

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        //data.setValueFormatter(new PercentFormatter());
        //data.setValueTextSize(11f);
        //data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }


    public void getMapLog()
    {
        MapCount = 0;
        Iterator iterator = sortByValue(map).iterator();
        while(iterator.hasNext()){
            String temp = (String) iterator.next();
            Log.i("chart", temp + " = " + map.get(temp));
            MapCount++;
        }
    }

    public void getMapStatic()
    {
        int count = 0;
        Iterator iterator = sortByValue(map).iterator();
        while(iterator.hasNext()){

            Log.i("chart", count + "");
            if (count == LIMIT_SIZE)
                break;

            String temp = (String) iterator.next();

            xData.add(temp);
            yData.add(new Float(map.get(temp)));


            count++;
        }
    }

    public List sortByValue(final Map map){
        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);

                return ((Comparable) v1).compareTo(v2);
            }

        });
        Collections.reverse(list); // 주석시 오름차순
        return list;
    }


}
