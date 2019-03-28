package com.ccq.bangdream.util;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class CheckUpdateUtil {
    private static boolean update;

    public static boolean checkUpdate() {

        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                update = data.getBoolean("update");
            }
        };

        Runnable runnable = new Runnable() {
            Message message = new Message();
            Bundle bundle = new Bundle();

            @Override
            public void run() {
                try {
                    String url = "https://github.com/minatoyukina/BangDream/tags";
                    Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36").get();
                    String versionName = document.select("div[class=box]").select("div[class=Box-row position-relative d-flex]").select("a").first().text();
                    Log.d("version", versionName);
                    String[] split = versionName.split("\\.");
                    PackageManager manager = MyApplication.getContext().getPackageManager();
                    String originalVersionName = manager.getPackageInfo(MyApplication.getContext().getPackageName(), 0).versionName;
                    Log.d("originalVersionName", originalVersionName);
                    String[] originalSplit = originalVersionName.split("\\.");
                    if (Integer.parseInt(split[0]) > Integer.parseInt(originalSplit[0])) {
                        bundle.putBoolean("update", true);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } else if (Integer.parseInt(split[0]) == Integer.parseInt(originalSplit[0]) && Integer.parseInt(split[1]) > Integer.parseInt(originalSplit[1])) {
                        bundle.putBoolean("update", true);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } else if (Integer.parseInt(split[0]) == Integer.parseInt(originalSplit[0]) && Integer.parseInt(split[1]) == Integer.parseInt(originalSplit[1]) && Integer.parseInt(split[2]) > Integer.parseInt(originalSplit[2])) {
                        bundle.putBoolean("update", true);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    } else {
                        bundle.putBoolean("update", false);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
        Log.d("1234", String.valueOf(update));
        return update;

    }
}
