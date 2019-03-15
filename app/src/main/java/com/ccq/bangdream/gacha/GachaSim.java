package com.ccq.bangdream.gacha;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import com.bumptech.glide.Glide;
import com.ccq.bangdream.R;
import com.ccq.bangdream.greendao.CardDao;
import com.ccq.bangdream.greendao.DaoMaster;
import com.ccq.bangdream.greendao.DaoSession;
import com.ccq.bangdream.pojo.Card;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import org.greenrobot.greendao.query.QueryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GachaSim extends AppCompatActivity {
    private static final String DB_PATH = "my.db";

    private static float TWO_STAR;
    private static float THREE_STAR;
    private static float FOUR_STAR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.gacha);
        setContentView(R.layout.activity_gacha);
        Button button = findViewById(R.id.gacha);
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String value = data.getString("value");
                ImageView gacha = findViewById(R.id.current_gacha);
                Glide.with(GachaSim.this).load(value).into(gacha);

            }
        };
        final Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                try {
                    Document document = Jsoup.connect("https://bandori.party/events/")
                            .userAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Mobile Safari/537.36")
                            .get();
                    String url = document.select("div[class=row items]").select("img").attr("src");
                    url = "http://" + url;
                    data.putString("value", url);
                    msg.setData(data);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).start();

        SQLiteService service = new SQLiteService(GachaSim.this, DB_PATH, null, 1);

        SQLiteDatabase readableDatabase = service.getReadableDatabase();
        final DaoSession daoSession = new DaoMaster(readableDatabase).newSession();
        final CardDao cardDao = daoSession.getCardDao();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> picList = new ArrayList<>();
                Random random = new Random();
                for (int i = 0; i < 9; i++) {
                    int nextInt = random.nextInt(200);
                    if (nextInt < 6) {
                        List<Card> list = cardDao.queryBuilder().where(CardDao.Properties.Rarity.eq(4)).list();
                        int index = (int) (Math.random() * list.size());
                        Card card = list.get(index);
                        picList.add("http://" + card.getIcon());
                        FOUR_STAR++;
                    } else if (nextInt < 24) {
                        List<Card> list = cardDao.queryBuilder().where(CardDao.Properties.Rarity.eq(3)).list();
                        int index = (int) (Math.random() * list.size());
                        Card card = list.get(index);
                        picList.add("http://" + card.getIcon());
                        THREE_STAR++;
                    } else {
                        List<Card> list = cardDao.queryBuilder().where(CardDao.Properties.Rarity.eq(2)).list();
                        int index = (int) (Math.random() * list.size());
                        Card card = list.get(index);
                        picList.add("http://" + card.getIcon());
                        TWO_STAR++;
                    }
                }
                List<Card> list = cardDao.queryBuilder().where(CardDao.Properties.Rarity.eq(3)).list();
                int index = (int) (Math.random() * list.size());
                Card card = list.get(index);
                picList.add("http://" + card.getIcon());

                for (String s : picList) {
                    Log.d("list", s);
                }

                ListView listView = findViewById(R.id.card_list);
                listView.setAdapter(new GachaListAdapter(GachaSim.this, picList));
            }
        });

        Button chart = findViewById(R.id.charts);
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<PieEntry> pieEntries = new ArrayList<>();
                pieEntries.add(new PieEntry(TWO_STAR / (TWO_STAR + THREE_STAR + FOUR_STAR), "二星"));
                pieEntries.add(new PieEntry(THREE_STAR / (TWO_STAR + THREE_STAR + FOUR_STAR), "三星"));
                pieEntries.add(new PieEntry(FOUR_STAR / (TWO_STAR + THREE_STAR + FOUR_STAR), "四星"));

                PieDataSet dataSet = new PieDataSet(pieEntries, "");
                List<Integer> colors = new ArrayList<>();
                colors.add(ContextCompat.getColor(GachaSim.this, R.color.colorPrimary));
                colors.add(ContextCompat.getColor(GachaSim.this, R.color.colorAccent));
                colors.add(ContextCompat.getColor(GachaSim.this, R.color.colorGreen));
                dataSet.setColors(colors);

                PieData pieData = new PieData(dataSet);
//                pieData.setDrawValues(true);
                pieData.setValueTextSize(12f);
                Description description = new Description();
                description.setText("抽选结果");
                PieChart picChart = findViewById(R.id.pic_chart);
                picChart.setDescription(description);
                picChart.setHoleRadius(0f);
                picChart.setTransparentCircleRadius(0f);
//                picChart.setUsePercentValues(true);
                picChart.setData(pieData);
//                picChart.invalidate();
            }
        });
    }


}
