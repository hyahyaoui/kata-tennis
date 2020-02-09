package com.onepoint.kata.tennisgame.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Player {
    private String name;
}
