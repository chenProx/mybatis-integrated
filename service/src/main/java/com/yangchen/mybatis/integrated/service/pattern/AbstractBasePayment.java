package com.yangchen.mybatis.integrated.service.pattern;
/**
 * 支付标准动作
 *
 */
public abstract class AbstractBasePayment implements Payment {
    public void payment() {
//        log.info("国内");
        prePay();

        pay(new PayRequest());

        postPay();
    }

    protected abstract void postPay();

    protected abstract void prePay();
}
