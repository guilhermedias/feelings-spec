package solutions.moot.feelings.spec.aggregator;

import solutions.moot.feelings.spec.dictionary.VadEntry;
import solutions.moot.feelings.spec.dictionary.VadValue;

import java.util.Collection;

public interface VadAggregator {
    VadValue aggregate(String sentence, Collection<VadEntry> vadEntries);
}
