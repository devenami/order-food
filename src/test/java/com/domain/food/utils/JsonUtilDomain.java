package com.domain.food.utils;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * json测试对象
 *
 * @author feb13th
 * @since 2019/5/19 15:07
 */
@Getter
@Setter
public class JsonUtilDomain {


    private String userCode;

    private int sex;

    private long timestamp;

    private BigDecimal bigDecimal;

    private Date date;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

}
