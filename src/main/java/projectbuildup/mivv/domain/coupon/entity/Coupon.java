package projectbuildup.mivv.domain.coupon.entity;

import projectbuildup.mivv.global.common.BaseTimeEntity;

import java.time.LocalDate;

public class Coupon extends BaseTimeEntity {

    int id;
    int valueId;
    String title;
    String content;
    String imageUrl;
    int pin;
    Boolean create;
    Boolean use;
    LocalDate limitDate;
    int originalPrice;
    int salePrice;



}
