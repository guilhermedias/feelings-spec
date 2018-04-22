package me.espere.feelings.spec.analyzer;

import lombok.Value;
import me.espere.feelings.spec.VadValue;

import java.util.Collection;

@Value
public class TextAnalysis {
    private VadValue vadValue;
    private Collection<WordAnalysis> wordAnalyses;
}
