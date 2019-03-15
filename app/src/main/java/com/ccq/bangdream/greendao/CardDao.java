package com.ccq.bangdream.greendao;

import java.util.List;
import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.ccq.bangdream.pojo.Panel;

import com.ccq.bangdream.pojo.Card;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "CARD".
 */
public class CardDao extends AbstractDao<Card, Integer> {

    public static final String TABLENAME = "CARD";

    /**
     * Properties of entity Card.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "ID");
        public final static Property Number = new Property(1, Integer.class, "number", false, "NUMBER");
        public final static Property Member = new Property(2, String.class, "member", false, "MEMBER");
        public final static Property Title = new Property(3, String.class, "title", false, "TITLE");
        public final static Property Rarity = new Property(4, Byte.class, "rarity", false, "RARITY");
        public final static Property Attribute = new Property(5, String.class, "attribute", false, "ATTRIBUTE");
        public final static Property Skill = new Property(6, String.class, "skill", false, "SKILL");
        public final static Property Icon = new Property(7, String.class, "icon", false, "ICON");
        public final static Property Icon_trained = new Property(8, String.class, "icon_trained", false, "ICON_TRAINED");
    }

    private DaoSession daoSession;


    public CardDao(DaoConfig config) {
        super(config);
    }

    public CardDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"CARD\" (" + //
                "\"ID\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NUMBER\" INTEGER," + // 1: number
                "\"MEMBER\" TEXT," + // 2: member
                "\"TITLE\" TEXT," + // 3: title
                "\"RARITY\" INTEGER," + // 4: rarity
                "\"ATTRIBUTE\" TEXT," + // 5: attribute
                "\"SKILL\" TEXT," + // 6: skill
                "\"ICON\" TEXT," + // 7: icon
                "\"ICON_TRAINED\" TEXT);"); // 8: icon_trained
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CARD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Card entity) {
        stmt.clearBindings();

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer number = entity.getNumber();
        if (number != null) {
            stmt.bindLong(2, number);
        }

        String member = entity.getMember();
        if (member != null) {
            stmt.bindString(3, member);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }

        Byte rarity = entity.getRarity();
        if (rarity != null) {
            stmt.bindLong(5, rarity);
        }

        String attribute = entity.getAttribute();
        if (attribute != null) {
            stmt.bindString(6, attribute);
        }

        String skill = entity.getSkill();
        if (skill != null) {
            stmt.bindString(7, skill);
        }

        String icon = entity.getIcon();
        if (icon != null) {
            stmt.bindString(8, icon);
        }

        String icon_trained = entity.getIcon_trained();
        if (icon_trained != null) {
            stmt.bindString(9, icon_trained);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Card entity) {
        stmt.clearBindings();

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer number = entity.getNumber();
        if (number != null) {
            stmt.bindLong(2, number);
        }

        String member = entity.getMember();
        if (member != null) {
            stmt.bindString(3, member);
        }

        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }

        Byte rarity = entity.getRarity();
        if (rarity != null) {
            stmt.bindLong(5, rarity);
        }

        String attribute = entity.getAttribute();
        if (attribute != null) {
            stmt.bindString(6, attribute);
        }

        String skill = entity.getSkill();
        if (skill != null) {
            stmt.bindString(7, skill);
        }

        String icon = entity.getIcon();
        if (icon != null) {
            stmt.bindString(8, icon);
        }

        String icon_trained = entity.getIcon_trained();
        if (icon_trained != null) {
            stmt.bindString(9, icon_trained);
        }
    }

    @Override
    protected final void attachEntity(Card entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

    @Override
    public Card readEntity(Cursor cursor, int offset) {
        Card entity = new Card( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // number
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // member
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // title
                cursor.isNull(offset + 4) ? null : (byte) cursor.getShort(offset + 4), // rarity
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // attribute
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // skill
                cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // icon
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // icon_trained
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, Card entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setNumber(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setMember(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRarity(cursor.isNull(offset + 4) ? null : (byte) cursor.getShort(offset + 4));
        entity.setAttribute(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSkill(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setIcon(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setIcon_trained(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
    }

    @Override
    protected final Integer updateKeyAfterInsert(Card entity, long rowId) {
        return entity.getId();
    }

    @Override
    public Integer getKey(Card entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Card entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getPanelDao().getAllColumns());
            builder.append(" FROM CARD T");
            builder.append(" LEFT JOIN PANEL T0 ON T.\"NUMBER\"=T0.\"NUMBER\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }

    protected Card loadCurrentDeep(Cursor cursor, boolean lock) {
        Card entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Panel panel = loadCurrentOther(daoSession.getPanelDao(), cursor, offset);
        entity.setPanel(panel);

        return entity;
    }

    public Card loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();

        String[] keyArray = new String[]{key.toString()};
        Cursor cursor = db.rawQuery(sql, keyArray);

        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }

    /**
     * Reads all available rows from the given cursor and returns a list of new ImageTO objects.
     */
    public List<Card> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Card> list = new ArrayList<Card>(count);

        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }

    protected List<Card> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }


    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     */
    public List<Card> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }

}