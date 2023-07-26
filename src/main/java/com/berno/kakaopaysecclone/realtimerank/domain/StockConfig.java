package com.berno.kakaopaysecclone.realtimerank.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_config")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockConfig {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "init_yn")
    private Boolean initYn;

    public void initComplete() {
        this.initYn = true;
    }
}
