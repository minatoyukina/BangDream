package com.ccq.bangdream.gacha;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.ccq.bangdream.util.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQLiteService extends SQLiteOpenHelper {
    private Context context = MyApplication.getContext();

    SQLiteService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    private String readTextFromAsserts(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder builder = new StringBuilder();
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            builder.append(str);
        }
        return builder.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            InputStream inputStream = context.getAssets().open("card.sql");
            String query = "";
            try {
                query = readTextFromAsserts(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] strings = query.split(";");
            for (String string : strings) {
                if (!TextUtils.isEmpty(string)) {
                    sqLiteDatabase.execSQL(string);
                }
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            InputStream inputStream = context.getAssets().open("panel.sql");
            String query = "";
            try {
                query = readTextFromAsserts(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] strings = query.split(";");
            for (String string : strings) {
                if (!TextUtils.isEmpty(string)) {
                    sqLiteDatabase.execSQL(string);
                }
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table card");
        sqLiteDatabase.execSQL("drop table panel");
    }
}
