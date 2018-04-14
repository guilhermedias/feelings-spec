package me.espere.feelings.spec.analyzer;

import lombok.Value;
import me.espere.feelings.spec.dictionary.VadValue;

@Value
public class VadSentenceWordAnalysis {
    private String word;
    private String lemma;
    private VadValue vadValue;
}
