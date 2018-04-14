package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.analyzer.VadSentenceWordAnalysis;
import me.espere.feelings.spec.dictionary.VadValue;

import java.util.Collection;

public interface VadAggregator {
    VadValue aggregate(String sentence, Collection<VadSentenceWordAnalysis> words);
}
