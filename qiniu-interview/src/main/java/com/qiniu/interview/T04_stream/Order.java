package com.qiniu.interview.T04_stream;

import lombok.Data;

@Data
public class Order {
    private long orderId;
    private long userId;
    private String status; // active, canceled
    private double orderMoney;
}