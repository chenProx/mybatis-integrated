package com.yangchen.mybatis.integrated.service.pattern;

public interface Payment {
    /**
     * 支付
     *
     */
    PayResponse pay(PayRequest request);
}
