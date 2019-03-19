package com.ccq.bangdream.gacha;

import android.annotation.SuppressLint;
import android.graphics.Color;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.*;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

class MyLineChart {
    void initLineChart(LineChart lineChart, List<Integer> list, List<Integer> list2, List<Integer> list3) {

        lineChart.setDrawBorders(false);

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new Entry(i, list.get(i)));
        }
        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < list2.size(); i++) {
            entries2.add(new Entry(i, list2.get(i)));
        }
        List<Entry> entries3 = new ArrayList<>();
        for (int i = 0; i < list3.size(); i++) {
            entries3.add(new Entry(i, list3.get(i)));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "二星");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "三星");
        LineDataSet lineDataSet3 = new LineDataSet(entries3, "四星");

        //线颜色
        lineDataSet.setColor(Color.BLUE);
        lineDataSet2.setColor(Color.RED);
        lineDataSet3.setColor(Color.GREEN);
        //线宽度
        lineDataSet.setLineWidth(1.6f);
        lineDataSet2.setLineWidth(1.6f);
        lineDataSet3.setLineWidth(1.6f);
        //不显示圆点
        lineDataSet.setDrawCircles(true);
        lineDataSet2.setDrawCircles(true);
        lineDataSet3.setDrawCircles(true);
        //线条平滑
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet3.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(list.size(), false);
        xAxis.setAxisMinimum(1f);
        xAxis.setAxisMaximum(list.size());
        xAxis.setLabelRotationAngle(10);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                @SuppressLint("DefaultLocale") CharSequence format = String.format("%d", (int) value);
                return format.toString();
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        YAxis yAxis = lineChart.getAxisLeft();
        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false);
        yAxis.setDrawGridLines(false);
        yAxis.setGranularity(0.1f);
        yAxis.setAxisMinimum(0);
        yAxis.setAxisMaximum(10);
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int IValue = (int) value;
                return String.valueOf(IValue);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        lineChart.setNoDataText("暂无数据");
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet3);
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
