package com.yangchen.mybatis.integrated.service.pattern;

public class WxPay extends AbstractBasePayment {
    @Override
    protected void postPay() {

    }

    @Override
    protected void prePay() {

    }

    @Override
    public PayResponse pay(PayRequest request) {
        return null;
    }
}
