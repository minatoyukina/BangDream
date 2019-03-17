package com.ccq.bangdream.gacha;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.ccq.bangdream.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class ChartResult extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("结果报表");
        setContentView(R.layout.chart_result);
        List<PieEntry> pieEntries = new ArrayList<>();
        Intent intent = getIntent();
        float[] pies = intent.getFloatArrayExtra("pie");
        float sum = pies[0] + pies[1] + pies[2];
        pieEntries.add(new PieEntry(pies[0] / sum, "二星"));
        pieEntries.add(new PieEntry(pies[1] / sum, "三星"));
        pieEntries.add(new PieEntry(pies[2] / sum, "四星"));

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        List<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(this, R.color.colorPrimary));
        colors.add(ContextCompat.getColor(this, R.color.colorAccent));
        colors.add(ContextCompat.getColor(this, R.color.colorGreen));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueTextSize(12f);
        Description description = new Description();
        description.setText("抽选结果");
        PieChart picChart = findViewById(R.id.pic_chart);
        picChart.setDescription(description);
        picChart.setHoleRadius(0f);
        picChart.setTransparentCircleRadius(0f);
        picChart.setUsePercentValues(true);
        picChart.setData(pieData);
        picChart.invalidate();

        LineChart lineChart = findViewById(R.id.line_Chart);
        MyLineChart myLineChart = new MyLineChart();
        ArrayList<Integer> line = intent.getIntegerArrayListExtra("line");
        myLineChart.initLineChart(lineChart, line);

    }
}
