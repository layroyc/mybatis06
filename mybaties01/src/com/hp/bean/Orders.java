package com.hp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * orders
 * @author 
 */
public class Orders implements Serializable {
    private Integer ordersId;

    private Integer personId;

    private Double totalPrice;

    private String addr;
    private List<OrderDetail> orderDetails;
    //多对1 是要写一方的对象
    private Person person;

    public Orders() {
        super();
    }

    @Override
    public String toString() {
        return "Orders{" +
                "ordersId=" + ordersId +
                ", personId=" + personId +
                ", totalPrice=" + totalPrice +
                ", addr='" + addr + '\'' +
                ", orderDetails=" + orderDetails +
                ", person=" + person +
                '}';
    }

    public Orders(Integer ordersId, Integer personId, Double totalPrice, String addr, List<OrderDetail> orderDetails, Person person) {
        this.ordersId = ordersId;
        this.personId = personId;
        this.totalPrice = totalPrice;
        this.addr = addr;
        this.orderDetails = orderDetails;
        this.person = person;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}