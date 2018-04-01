package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.dictionary.VadEntry;
import me.espere.feelings.spec.dictionary.VadValue;

import java.util.Collection;

public interface VadAggregator {
    VadValue aggregate(String sentence, Collection<VadEntry> vadEntries);
}
