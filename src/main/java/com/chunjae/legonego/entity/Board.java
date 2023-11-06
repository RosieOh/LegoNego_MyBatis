package com.chunjae.legonego.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private int bno;
    private String title;
    private String content;
    private String regdate;
    private String visit;
}
