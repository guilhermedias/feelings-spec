package me.espere.feelings.spec.analyzer;

import me.espere.feelings.spec.dictionary.VadValue;

public interface VadSentenceAnalyzer {
    VadValue analyzeSentence(String sentence);
}
