package com.hp.entity;

import java.io.Serializable;

/**
 * banwei
 * @author 
 */
public class Banwei implements Serializable {
    private Integer banid;

    private String banname;

    private Integer sid;

    private static final long serialVersionUID = 1L;

    public Integer getBanid() {
        return banid;
    }

    public void setBanid(Integer banid) {
        this.banid = banid;
    }

    public String getBanname() {
        return banname;
    }

    public void setBanname(String banname) {
        this.banname = banname;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
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
        Banwei other = (Banwei) that;
        return (this.getBanid() == null ? other.getBanid() == null : this.getBanid().equals(other.getBanid()))
            && (this.getBanname() == null ? other.getBanname() == null : this.getBanname().equals(other.getBanname()))
            && (this.getSid() == null ? other.getSid() == null : this.getSid().equals(other.getSid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBanid() == null) ? 0 : getBanid().hashCode());
        result = prime * result + ((getBanname() == null) ? 0 : getBanname().hashCode());
        result = prime * result + ((getSid() == null) ? 0 : getSid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", banid=").append(banid);
        sb.append(", banname=").append(banname);
        sb.append(", sid=").append(sid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}