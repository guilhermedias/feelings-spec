package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.analyzer.WordAnalysis;
import me.espere.feelings.spec.VadValue;

import java.util.Collection;

public interface Aggregator {
    VadValue aggregate(String text, Collection<WordAnalysis> wordAnalyses);
}
