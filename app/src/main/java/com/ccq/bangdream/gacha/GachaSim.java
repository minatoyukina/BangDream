package com.ccq.bangdream.gacha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ccq.bangdream.R;
import com.ccq.bangdream.greendao.CardDao;
import com.ccq.bangdream.greendao.DaoMaster;
import com.ccq.bangdream.greendao.DaoSession;
import com.ccq.bangdream.pojo.Card;
import com.ccq.bangdream.util.JsoupUtil;
import com.ccq.bangdream.util.ListSumUtil;
import org.greenrobot.greendao.query.QueryBuilder;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class GachaSim extends AppCompatActivity {
    private static final String DB_PATH = "my.db";

    private static float TWO_STAR;
    private static float THREE_STAR;
    private static float FOUR_STAR;

    private ArrayList<Integer> list2 = new ArrayList<>();
    private ArrayList<Integer> list3 = new ArrayList<>();
    private ArrayList<Integer> list4 = new ArrayList<>();

    private static int STAR_COUNT;

    private GridView listView;
    private Button goOn;
    private Button chart;
    private TextView starCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.gacha);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_gacha);
        listView = findViewById(R.id.card_list);
        goOn = findViewById(R.id.go_on);
        chart = findViewById(R.id.charts);
        starCount = findViewById(R.id.star_count);
        SQLiteService service = new SQLiteService(GachaSim.this, DB_PATH, null, 1);

        SQLiteDatabase readableDatabase = service.getReadableDatabase();
        final DaoSession daoSession = new DaoMaster(readableDatabase).newSession();
        final CardDao cardDao = daoSession.getCardDao();
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        final Button button = findViewById(R.id.gacha);
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
                Document document = JsoupUtil.getDocument("https://bandori.party/gachas/");
                String url = document.select("div[class=text-center top-item]").select("img").attr("src");
                url = "http://" + url;
                data.putString("value", url);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        };
        new Thread(networkTask).start();

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                ArrayList<String> picList = new ArrayList<>();
                Card card5 = cardDao.queryBuilder().where(CardDao.Properties.Id.eq(1)).unique();
                Log.d("card", card5.toString());
                Random random = new Random();
                for (int i = 0; i < 9; i++) {
                    int nextInt = random.nextInt(200);
                    if (nextInt < 6) {
                        List<Card> list = cardDao.queryBuilder().where(CardDao.Properties.Rarity.eq(4), CardDao.Properties.Access.eq(1)).list();
                        int index = (int) (Math.random() * list.size());
                        Card card = list.get(index);
                        picList.add("http://" + card.getIcon());
                        FOUR_STAR++;
                    } else if (nextInt < 24) {
                        List<Card> list = cardDao.queryBuilder().where(CardDao.Properties.Rarity.eq(3), CardDao.Properties.Access.eq(1)).list();
                        int index = (int) (Math.random() * list.size());
                        Card card = list.get(index);
                        picList.add("http://" + card.getIcon());
                        THREE_STAR++;
                    } else {
                        List<Card> list = cardDao.queryBuilder().where(CardDao.Properties.Rarity.eq(2), CardDao.Properties.Access.eq(1)).list();
                        int index = (int) (Math.random() * list.size());
                        Card card = list.get(index);
                        picList.add("http://" + card.getIcon());
                        TWO_STAR++;
                    }
                }
                List<Card> list = cardDao.queryBuilder().where(CardDao.Properties.Rarity.eq(3), CardDao.Properties.Access.eq(1)).list();
                int index = (int) (Math.random() * list.size());
                Card card = list.get(index);
                picList.add("http://" + card.getIcon());
                THREE_STAR++;

                for (String s : picList) {
                    Log.d("list", s);
                }

                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new GachaListAdapter(GachaSim.this, picList));
                if (list2.size() == 0) {
                    list2.add((int) TWO_STAR);
                } else {
                    list2.add((int) TWO_STAR - ListSumUtil.sum(list2));
                }
                if (list3.size() == 0) {
                    list3.add((int) THREE_STAR);
                } else {
                    list3.add((int) THREE_STAR - ListSumUtil.sum(list3));
                }
                if (list4.size() == 0) {
                    list4.add((int) FOUR_STAR);
                } else {
                    list4.add((int) FOUR_STAR - ListSumUtil.sum(list4));
                }

                goOn.setVisibility(View.VISIBLE);
                chart.setVisibility(View.VISIBLE);

                TextView starCount = findViewById(R.id.star_count);
                starCount.setText(getString(R.string.using_star) + (++STAR_COUNT) * 2500);
            }
        });

        Button goOn = findViewById(R.id.go_on);
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.performClick();
            }
        });


        Button chart = findViewById(R.id.charts);
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float[] floats = new float[3];
                floats[0] = TWO_STAR;
                floats[1] = THREE_STAR;
                floats[2] = FOUR_STAR;
                Intent intent = new Intent(GachaSim.this, ChartResult.class);
                intent.putExtra("pie", floats);
                intent.putIntegerArrayListExtra("line2", list2);
                intent.putIntegerArrayListExtra("line3", list3);
                intent.putIntegerArrayListExtra("line4", list4);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        TWO_STAR = 0;
        THREE_STAR = 0;
        FOUR_STAR = 0;
        STAR_COUNT = 0;
        list2.clear();
        list3.clear();
        list4.clear();
        listView.setVisibility(View.INVISIBLE);
        goOn.setVisibility(View.INVISIBLE);
        chart.setVisibility(View.INVISIBLE);
        starCount.setText("");
    }
}
