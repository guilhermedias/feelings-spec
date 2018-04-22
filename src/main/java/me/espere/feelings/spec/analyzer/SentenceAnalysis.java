package me.espere.feelings.spec.analyzer;

import lombok.Value;
import me.espere.feelings.spec.VadValue;

import java.util.Collection;

@Value
public class SentenceAnalysis {
    private VadValue vadValue;
    private Collection<SentenceWordAnalysis> wordAnalyses;
}
