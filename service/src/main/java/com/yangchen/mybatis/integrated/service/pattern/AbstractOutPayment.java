package com.yangchen.mybatis.integrated.service.pattern;
/**
 * 支付标准动作
 *
 */
public abstract class AbstractOutPayment implements Payment {
    public void payment() {
//        log.info("国外");
        prePay();

        pay(new PayRequest());

        postPay();
    }

    protected abstract void postPay();

    protected abstract void prePay();
}
