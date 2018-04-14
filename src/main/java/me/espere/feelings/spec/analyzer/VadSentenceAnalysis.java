package me.espere.feelings.spec.analyzer;

import lombok.Value;
import me.espere.feelings.spec.dictionary.VadEntry;
import me.espere.feelings.spec.dictionary.VadValue;

import java.util.Collection;

@Value
public class VadSentenceAnalysis {
    private VadValue vadValue;
    private Collection<VadEntry> entries;
}
