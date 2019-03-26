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
import com.github.mikephil.charting.formatter.PercentFormatter;

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
        pieEntries.add(new PieEntry(pies[0] / sum * 100, "二星"));
        pieEntries.add(new PieEntry(pies[1] / sum * 100, "三星"));
        pieEntries.add(new PieEntry(pies[2] / sum * 100, "四星"));

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        List<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(this, R.color.colorPrimary));
        colors.add(ContextCompat.getColor(this, R.color.colorRed));
        colors.add(ContextCompat.getColor(this, R.color.colorGreen));
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueTextSize(12f);
        pieData.setValueFormatter(new PercentFormatter());
        Description description = new Description();
        description.setText("抽选结果");
        PieChart pieChart = findViewById(R.id.pie_chart);

        pieChart.setDescription(description);
        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();


        LineChart lineChart = findViewById(R.id.line_Chart);
        MyLineChart myLineChart = new MyLineChart();
        ArrayList<Integer> line2 = intent.getIntegerArrayListExtra("line2");
        ArrayList<Integer> line3 = intent.getIntegerArrayListExtra("line3");
        ArrayList<Integer> line4 = intent.getIntegerArrayListExtra("line4");
        myLineChart.initLineChart(lineChart, line2, line3, line4);

    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, GachaSim.class);
        startActivity(intent);
    }
}
