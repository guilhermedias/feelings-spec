package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.analyzer.SentenceWordAnalysis;
import me.espere.feelings.spec.VadValue;

import java.util.Collection;

public interface Aggregator {
    VadValue aggregate(String sentence, Collection<SentenceWordAnalysis> words);
}
