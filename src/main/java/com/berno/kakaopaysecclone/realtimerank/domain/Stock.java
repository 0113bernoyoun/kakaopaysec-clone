package com.berno.kakaopaysecclone.realtimerank.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "stock", uniqueConstraints = {
        @UniqueConstraint(name = "stockUnique", columnNames = {"ts", "code"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "ts")
    private Integer ts;

    @Column(name = "code")
    private String code;

    public Stock(String name, BigDecimal price, String code) {
        this.name = name;
        this.price = price;
        this.code = code;
        this.ts = getCurrentTimeByUnixTimestamp();
    }


    private Integer getCurrentTimeByUnixTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }

}
