package com.ccq.bangdream.map;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.ccq.bangdream.R;
import jp.wasabeef.glide.transformations.CropTransformation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MapGame extends AppCompatActivity {
    private ArrayList<CharSequence> titles = new ArrayList<>();
    private String value;
    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.map);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_map_game);
        final ImageView mapBar = findViewById(R.id.map_bar);
        final Button button = findViewById(R.id.guess);
        loadMap();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                value = data.getString("value");
                String str = data.getString("str");
                TextView titleView = findViewById(R.id.map_title);
                titleView.setText(value);
                Spinner song = findViewById(R.id.song);
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(MapGame.this, android.R.layout.simple_spinner_item, titles);
                song.setAdapter(adapter);
                song.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            return;
                        }
                        if (titles.get(i).equals(value)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MapGame.this);
                            dialog.setTitle("结果");
                            dialog.setMessage("\n回答正确");
                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    button.performClick();
                                }
                            });
                            dialog.show();
                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MapGame.this);
                            dialog.setTitle("结果");
                            dialog.setMessage("\n正确答案为: " + value);
                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    button.performClick();
                                }
                            });
                            dialog.show();
                        }
                    }


                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
//                Glide.with(MapGame.this).load("http://www.sdvx.in/bandri/bg/" + str + "bg.png").apply(bitmapTransform(new CropTransformation(3018 / 21, 984, CropTransformation.CropType.CENTER))).into(mapBar);
                Glide.with(MapGame.this).load("http://www.sdvx.in/bandri/obj/data" + str + "ex.png").apply(bitmapTransform(new CropTransformation(144, 984, CropTransformation.CropType.CENTER))).into(mapBar);
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMap();
            }
        });
    }

    private void loadMap() {
        final Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                try {
                    Document document = Jsoup.connect("http://www.sdvx.in/bandri/sort/def.htm")
                            .userAgent("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Mobile Safari/537.36")
                            .get();
                    int songSize = document.select("table[class=c]").eq(2).select("script").size();

                    Random random = new Random();
                    int i = random.nextInt(songSize);
                    String td = document.select("table[class=c]").eq(2).select("td").toString().replace("<td> <script>", "").replace("--> </td>", "");
                    @SuppressLint("DefaultLocale") String str = String.format("%03d", i);
                    String[] songs = td.split("--> <script>");
                    if (titles.size() == 0) {
                        titles.add(" ✢✣✤✥❋✦✧※❇❈✢✣✤✥❋✦✧※❇❈ ");
                        for (String song : songs) {
                            String title = song.split("<!--")[1];
                            titles.add(title);
                        }
                    }
                    for (String song : songs) {
                        String sort = song.split(";")[0];
                        String title = song.split("<!--")[1];
                        if (sort.equals("SORT" + str + "()")) {
                            data.putString("value", title);
                            data.putString("str", str);
                        }
                    }
                    msg.setData(data);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
