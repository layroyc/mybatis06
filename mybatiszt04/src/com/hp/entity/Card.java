package com.hp.entity;

import java.io.Serializable;

/**
 * card
 * @author 
 */
public class Card implements Serializable {
    private Integer cardid;

    private String cardname;

    private Integer pid;

    private static final long serialVersionUID = 1L;

    public Integer getCardid() {
        return cardid;
    }

    public void setCardid(Integer cardid) {
        this.cardid = cardid;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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
        Card other = (Card) that;
        return (this.getCardid() == null ? other.getCardid() == null : this.getCardid().equals(other.getCardid()))
            && (this.getCardname() == null ? other.getCardname() == null : this.getCardname().equals(other.getCardname()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCardid() == null) ? 0 : getCardid().hashCode());
        result = prime * result + ((getCardname() == null) ? 0 : getCardname().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cardid=").append(cardid);
        sb.append(", cardname=").append(cardname);
        sb.append(", pid=").append(pid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}