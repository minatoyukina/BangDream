package com.ccq.bangdream;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.ccq.bangdream.card.LoadCards;
import com.ccq.bangdream.event.LoadEvents;
import com.ccq.bangdream.gacha.GachaSim;
import com.ccq.bangdream.map.MapGame;
import com.ccq.bangdream.setting.ActivityWithPreferenceFragment;
import com.ccq.bangdream.util.CheckUpdateUtil;
import com.ccq.bangdream.util.MyApplication;
import com.ccq.bangdream.util.UAUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Intent intent;
    private WebView webView;

    private long mPressedTime = 0;
    private boolean flag = true;

    @SuppressLint({"SetJavaScriptEnabled", "InflateParams"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("当前活动");
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
        builder.setCancelable(false);
        builder.setView(R.layout.layout_dialog);
        final AlertDialog dialog = builder.create();
        dialog.show();

        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Bundle data = msg.getData();
                final String value = data.getString("value");
                webView = findViewById(R.id.index);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(value);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        view.loadUrl(request.getUrl().toString());
                        return true;
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        loadCss();
                        dialog.dismiss();
                        if (flag) {
                            try {
                                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                boolean update = CheckUpdateUtil.checkUpdate();
                                if (update) {
                                    dialog.setMessage("\n有新版本");
                                    dialog.setTitle("检查更新").setPositiveButton("去更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/minatoyukina/BangDream/releases"));
                                            startActivity(intent);
                                        }
                                    });
                                    dialog.setNegativeButton("忽略", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            flag = false;
                                        }
                                    });
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }

        };
        final Runnable networkTask = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle data = new Bundle();
                try {
                    Document document = Jsoup.connect("https://bandori.party/events/")
                            .userAgent(UAUtil.UserAgent[new Random().nextInt(UAUtil.UserAgent.length)]).get();
                    String url = document.select("div[class=row items]").select("a").attr("href");
                    url = "http://bandori.party" + url;
                    data.putString("value", url);
                    msg.setData(data);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkTask).start();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long mNowTime = System.currentTimeMillis();//获取第一次按键时间
            if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mPressedTime = mNowTime;
            } else {//退出程序
                this.finish();
                System.exit(0);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.isChecked()) {
            item.setChecked(false);
        }
        int id = item.getItemId();

        if (id == R.id.nav_card) {
            intent = new Intent(MainActivity.this, LoadCards.class);
        } else if (id == R.id.nav_event) {
            intent = new Intent(MainActivity.this, LoadEvents.class);
        } else if (id == R.id.nav_gacha) {
            intent = new Intent(MainActivity.this, GachaSim.class);
        }
//        else if (id == R.id.nav_score) {
//            intent = new Intent(MainActivity.this, ScoreSum.class);
//        }
        else if (id == R.id.nav_map) {
            intent = new Intent(MainActivity.this, MapGame.class);
        } else if (id == R.id.nav_share) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/minatoyukina");
        } else if (id == R.id.nav_setting) {
            intent = new Intent(MainActivity.this, ActivityWithPreferenceFragment.class);
        }
        startActivity(intent);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void loadCss() {
        InputStream is = null;
        try {
            is = MyApplication.getContext().getAssets().open("display.css");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] buffer = new byte[Objects.requireNonNull(is).available()];
            int read = is.read(buffer);
            Log.d("whatever", String.valueOf(read));
            is.close();
            String cssCode = Base64.encodeToString(buffer, Base64.NO_WRAP);
            String jsCode = "javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    "style.innerHTML = window.atob('" + cssCode + "');" +
                    "parent.appendChild(style);" +
                    "})();";
            webView.loadUrl(jsCode);
        } catch (IOException e) {
            Log.d("test", e.getMessage());
        }
    }
}
