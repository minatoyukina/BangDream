package com.ccq.bangdream.setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import com.ccq.bangdream.R;
import com.ccq.bangdream.util.CheckUpdateUtil;
import com.ccq.bangdream.util.MyApplication;

import java.io.File;
import java.util.Objects;


public class SettingPreference extends PreferenceFragment {
    private File file;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager();
        addPreferencesFromResource(R.xml.setting_preference);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Preference clearCache = findPreference("clear_cache");
        file = Objects.requireNonNull(MyApplication.getContext().getCacheDir());
        long totalSpace = getFolderSize(file);
        Log.d("file", file.toString());
        clearCache.setSummary("已使用缓存大小: " + (float) totalSpace / (1024 * 1024) + "M");
        clearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                delete(file);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示").setMessage("\n清理完成").setPositiveButton("确定", null).show();
                clearCache.setSummary("已使用缓存大小: 0M");
                return true;
            }


            private void delete(File parent) {
                if (parent.isDirectory()) {
                    File[] files = parent.listFiles();
                    for (File child : files) {
                        delete(child);
                    }
                } else {
                    boolean delete = parent.delete();
                    Log.d("delete", String.valueOf(delete));
                }
            }
        });

        Preference update = findPreference("update");
        PackageManager manager = MyApplication.getContext().getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(MyApplication.getContext().getPackageName(), 0);
            String versionName = packageInfo.versionName;
            update.setSummary("当前版本: " + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        update.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("检查更新");
                boolean flag = CheckUpdateUtil.checkUpdate();
                if (flag) {
                    dialog.setMessage("\n有新版本");
                    dialog.setPositiveButton("去更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/minatoyukina/BangDream/releases"));
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("忽略", null);
                    dialog.show();
                } else {
                    dialog.setPositiveButton("确定", null);
                    dialog.setMessage("\n已是最新版本").show();
                }
                return true;
            }
        });

        Preference aboutUs = findPreference("contract_us");
        aboutUs.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:uzi9mm7@gmail.com"));
                startActivity(intent);
                return true;
            }
        });

        Preference licence = findPreference("licence");
        licence.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String[] items = {"Jsoup", "GreenDao", "Glide", "glide-transformations", "sqlite", "MPAndroidChart"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("开源库");
                dialog.setItems(items, null);
                dialog.setPositiveButton("确定", null);
                dialog.show();
                return true;
            }
        });

        Preference specialLinks = findPreference("special_links");
        specialLinks.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                String[] items = {
                        "bandori.party",
                        "谱面保管所",
                        "官方twitter",
                        "日服下载更新"};
                dialog.setTitle("special links").setItems(items, new DialogInterface.OnClickListener() {
                    Intent intent;

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {
                            case 0:
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bandori.party"));
                                break;
                            case 1:
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sdvx.in/bandri/sort/def.htm"));
                                break;
                            case 2:
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://twitter.com/bang_dream_gbp"));
                                break;
                            case 3:
                                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://apps.qoo-app.com/app/4847"));
                                break;
                            default:
                                break;
                        }
                        startActivity(intent);
                    }

                }).setPositiveButton("确定", null).show();
                return true;
            }
        });

//        Preference donate = findPreference("donate");
//        donate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                return false;
//            }
//        });
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    private static long getFolderSize(File file) {

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);

                } else {
                    size = size + aFileList.length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
