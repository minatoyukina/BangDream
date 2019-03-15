package com.ccq.bangdream.pojo;

import com.ccq.bangdream.greendao.CardDao;
import com.ccq.bangdream.greendao.DaoSession;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.*;

@Entity
public class Card {

    @Id
    private Integer id;

    private Integer number;
    private String member;
    private String title;
    private Byte rarity;

    @ToOne(joinProperty = "number")
    private Panel panel;
    private String attribute;
    private String skill;
    private String icon;
    private String icon_trained;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 599084715)
    private transient CardDao myDao;

    @Generated(hash = 23904880)
    public Card(Integer id, Integer number, String member, String title,
                Byte rarity, String attribute, String skill, String icon,
                String icon_trained) {
        this.id = id;
        this.number = number;
        this.member = member;
        this.title = title;
        this.rarity = rarity;
        this.attribute = attribute;
        this.skill = skill;
        this.icon = icon;
        this.icon_trained = icon_trained;
    }

    @Generated(hash = 52700939)
    public Card() {
    }

    @Generated(hash = 120668517)
    private transient Integer panel__resolvedKey;

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @Keep
    public Panel getPanel() {
        return panel;
    }

    @Keep
    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte getRarity() {
        return rarity;
    }

    public void setRarity(Byte rarity) {
        this.rarity = rarity;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_trained() {
        return icon_trained;
    }

    public void setIcon_trained(String icon_trained) {
        this.icon_trained = icon_trained;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number=" + number +
                ", member='" + member + '\'' +
                ", title='" + title + '\'' +
                ", rarity=" + rarity +
                ", panel=" + panel +
                ", attribute='" + attribute + '\'' +
                ", skill='" + skill + '\'' +
                ", icon='" + icon + '\'' +
                ", icon_trained='" + icon_trained + '\'' +
                '}';
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1693529984)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCardDao() : null;
    }
}
