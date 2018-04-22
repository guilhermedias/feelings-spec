package me.espere.feelings.spec;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class VadValue {
    private BigDecimal valence;
    private BigDecimal arousal;
    private BigDecimal dominance;
}
