package com.ccq.bangdream.setting;

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
        clearCache.setSummary("当前缓存大小: " + (float) totalSpace / (1024 * 1024) + "M");
        clearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                delete(file);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示").setMessage("\n清理完成").setPositiveButton("确定", null).show();
                clearCache.setSummary("当前缓存大小: 0M");
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
                return false;
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
                dialog.show();
                return true;
            }
        });

        Preference donate = findPreference("donate");
        donate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });
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
