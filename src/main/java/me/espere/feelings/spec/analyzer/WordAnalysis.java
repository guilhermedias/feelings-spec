package me.espere.feelings.spec.analyzer;

import lombok.Value;
import me.espere.feelings.spec.VadValue;

@Value
public class WordAnalysis {
    private String word;
    private String lemma;
    private VadValue vadValue;
}
