package com.ccq.bangdream.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ccq.bangdream.pojo.Panel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "PANEL".
 */
public class PanelDao extends AbstractDao<Panel, Integer> {

    public static final String TABLENAME = "PANEL";

    /**
     * Properties of entity Panel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Number = new Property(0, Integer.class, "number", true, "NUMBER");
        public final static Property Performance = new Property(1, int.class, "performance", false, "PERFORMANCE");
        public final static Property Technique = new Property(2, int.class, "technique", false, "TECHNIQUE");
        public final static Property Visual = new Property(3, int.class, "visual", false, "VISUAL");
        public final static Property Overall = new Property(4, int.class, "overall", false, "OVERALL");
    }


    public PanelDao(DaoConfig config) {
        super(config);
    }

    public PanelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"PANEL\" (" + //
                "\"NUMBER\" INTEGER PRIMARY KEY ," + // 0: number
                "\"PERFORMANCE\" INTEGER NOT NULL ," + // 1: performance
                "\"TECHNIQUE\" INTEGER NOT NULL ," + // 2: technique
                "\"VISUAL\" INTEGER NOT NULL ," + // 3: visual
                "\"OVERALL\" INTEGER NOT NULL );"); // 4: overall
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PANEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Panel entity) {
        stmt.clearBindings();

        Integer number = entity.getNumber();
        if (number != null) {
            stmt.bindLong(1, number);
        }
        stmt.bindLong(2, entity.getPerformance());
        stmt.bindLong(3, entity.getTechnique());
        stmt.bindLong(4, entity.getVisual());
        stmt.bindLong(5, entity.getOverall());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Panel entity) {
        stmt.clearBindings();

        Integer number = entity.getNumber();
        if (number != null) {
            stmt.bindLong(1, number);
        }
        stmt.bindLong(2, entity.getPerformance());
        stmt.bindLong(3, entity.getTechnique());
        stmt.bindLong(4, entity.getVisual());
        stmt.bindLong(5, entity.getOverall());
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

    @Override
    public Panel readEntity(Cursor cursor, int offset) {
        Panel entity = new Panel( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // number
                cursor.getInt(offset + 1), // performance
                cursor.getInt(offset + 2), // technique
                cursor.getInt(offset + 3), // visual
                cursor.getInt(offset + 4) // overall
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, Panel entity, int offset) {
        entity.setNumber(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setPerformance(cursor.getInt(offset + 1));
        entity.setTechnique(cursor.getInt(offset + 2));
        entity.setVisual(cursor.getInt(offset + 3));
        entity.setOverall(cursor.getInt(offset + 4));
    }

    @Override
    protected final Integer updateKeyAfterInsert(Panel entity, long rowId) {
        return entity.getNumber();
    }

    @Override
    public Integer getKey(Panel entity) {
        if (entity != null) {
            return entity.getNumber();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Panel entity) {
        return entity.getNumber() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

}
