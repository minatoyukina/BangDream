package com.ccq.bangdream.map;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ccq.bangdream.R;
import com.ccq.bangdream.util.JsoupUtil;
import org.jsoup.nodes.Document;

import java.util.*;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MapGame extends AppCompatActivity {
    private ArrayList<CharSequence> titles = new ArrayList<>();
    private String value;
    private Handler handler;

    private float width;
    private Bitmap bitmap;


    private int i;

    private static int LEVEL_COUNT;
    private static int CORRECT_COUNT;

    private static Set<String> MAP_CHANGE_COUNT = new HashSet<>();

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
//        loadMap();

        final Button pre = findViewById(R.id.pre);
        final Button next = findViewById(R.id.next);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                value = data.getString("value");
                final String str = data.getString("str");
//                final TextView titleView = findViewById(R.id.map_title);
//                titleView.setText(value);
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
                            CORRECT_COUNT++;
                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (LEVEL_COUNT < 10) {
                                        button.performClick();
                                    } else {
                                        resultDialog();
                                    }
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
                                    if (LEVEL_COUNT < 10) {
                                        button.performClick();
                                    } else {
                                        resultDialog();
                                    }
                                }
                            });
                            dialog.show();
                        }
                    }

                    private void resultDialog() {
                        AlertDialog.Builder result = new AlertDialog.Builder(MapGame.this);
                        Log.d("MAP_CHANGE_COUNT", MAP_CHANGE_COUNT.toString());
                        result.setTitle("结果").setMessage("答对谱面: " + CORRECT_COUNT + "/10" + "\n提示次数: " + (MAP_CHANGE_COUNT.size() - 10)).setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onResume();
                            }
                        });
                        result.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onBackPressed();
                            }
                        }).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                Glide.with(MapGame.this).asBitmap().load("http://www.sdvx.in/bandri/bg/" + str + "bg.png").into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        width = resource.getWidth();
                        bitmap = resource;
                        Random random = new Random();
                        final int pieces = random.nextInt(Math.round(width / 144) - 4);
                        i = pieces + 2;
                        MAP_CHANGE_COUNT.add(LEVEL_COUNT + "-" + i);
                        Log.d("pieces", String.valueOf(i));
                        Glide.with(MapGame.this).load("http://www.sdvx.in/bandri/obj/data" + str + "ex.png").apply(bitmapTransform(new MyCropTransformation(144, 984, i, bitmap))).into(mapBar);

                        pre.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (i > 1) {
                                    Glide.with(MapGame.this).load("http://www.sdvx.in/bandri/obj/data" + str + "ex.png").apply(bitmapTransform(new MyCropTransformation(144, 984, --i, bitmap))).into(mapBar);
                                    MAP_CHANGE_COUNT.add(LEVEL_COUNT + "-" + i);
                                    Log.d("<-", String.valueOf(i));
                                } else {
                                    Toast.makeText(MapGame.this, "到头了", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (i < width / 144) {
                                    Glide.with(MapGame.this).load("http://www.sdvx.in/bandri/obj/data" + str + "ex.png").apply(bitmapTransform(new MyCropTransformation(144, 984, ++i, bitmap))).into(mapBar);
                                    MAP_CHANGE_COUNT.add(LEVEL_COUNT + "-" + i);
                                    Log.d("->", String.valueOf(i));
                                } else {
                                    Toast.makeText(MapGame.this, "到尾了", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });


            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMap();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadMap() {
        final TextView mapCount = findViewById(R.id.map_count);
        mapCount.setText("Map: " + (++LEVEL_COUNT));
        final Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                Document document = JsoupUtil.getDocument("http://www.sdvx.in/bandri/sort/def.htm");
                int songSize = document.select("table[class=c]").eq(2).select("script").size();

                Random random = new Random();
                int i = random.nextInt(songSize);
                String td = document.select("table[class=c]").eq(2).select("td").toString().replace("<td> <script>", "").replace("--> </td>", "").replaceAll("\n<td height=\"10\"></td>\n", "--> <script>");
                @SuppressLint("DefaultLocale") String str = String.format("%03d", i);
                String[] songs = td.split("--> <script>");
                if (titles.size() == 0) {
                    titles.add("Song List : ");
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

            }
        };
        new Thread(networkTask).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LEVEL_COUNT = 0;
        MAP_CHANGE_COUNT.clear();
        CORRECT_COUNT = 0;
        loadMap();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
