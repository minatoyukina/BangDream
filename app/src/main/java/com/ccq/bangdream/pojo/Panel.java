package com.ccq.bangdream.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Panel {
    @Id
    private Integer number;
    private int performance;
    private int technique;
    private int visual;
    private int overall;

    @Generated(hash = 1573903157)
    public Panel(Integer number, int performance, int technique, int visual,
                 int overall) {
        this.number = number;
        this.performance = performance;
        this.technique = technique;
        this.visual = visual;
        this.overall = overall;
    }

    @Generated(hash = 1231309556)
    public Panel() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getTechnique() {
        return technique;
    }

    public void setTechnique(int technique) {
        this.technique = technique;
    }

    public int getVisual() {
        return visual;
    }

    public void setVisual(int visual) {
        this.visual = visual;
    }

    public int getOverall() {
        return overall;
    }

    public void setOverall(int overall) {
        this.overall = overall;
    }

    @Override
    public String toString() {
        return "Panel{" +
                "number=" + number +
                ", performance=" + performance +
                ", technique=" + technique +
                ", visual=" + visual +
                ", overall=" + overall +
                '}';
    }
}
