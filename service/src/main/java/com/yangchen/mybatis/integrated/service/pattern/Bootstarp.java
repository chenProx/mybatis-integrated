package com.yangchen.mybatis.integrated.service.pattern;

public class Bootstarp {
    public static void main(String[] args) {
        Payment payment = PaymentFactory.get();
        payment.pay(new PayRequest());
    }
}
