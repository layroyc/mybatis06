package com.hp.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * clazz
 * @author 
 */
public class Clazz implements Serializable {
    private Integer cid;

    private Integer scid;

    private String cname;

    private String goodis;
    private ArrayList<Student> students;

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    private static final long serialVersionUID = 1L;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getScid() {
        return scid;
    }

    public void setScid(Integer scid) {
        this.scid = scid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getGoodis() {
        return goodis;
    }

    public void setGoodis(String goodis) {
        this.goodis = goodis;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Clazz other = (Clazz) that;
        return (this.getCid() == null ? other.getCid() == null : this.getCid().equals(other.getCid()))
            && (this.getScid() == null ? other.getScid() == null : this.getScid().equals(other.getScid()))
            && (this.getCname() == null ? other.getCname() == null : this.getCname().equals(other.getCname()))
            && (this.getGoodis() == null ? other.getGoodis() == null : this.getGoodis().equals(other.getGoodis()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCid() == null) ? 0 : getCid().hashCode());
        result = prime * result + ((getScid() == null) ? 0 : getScid().hashCode());
        result = prime * result + ((getCname() == null) ? 0 : getCname().hashCode());
        result = prime * result + ((getGoodis() == null) ? 0 : getGoodis().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "cid=" + cid +
                ", scid=" + scid +
                ", cname='" + cname + '\'' +
                ", goodis='" + goodis + '\'' +
                ", students=" + students +
                '}';
    }
}