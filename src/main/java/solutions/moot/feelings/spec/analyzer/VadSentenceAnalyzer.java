package solutions.moot.feelings.spec.analyzer;

import solutions.moot.feelings.spec.dictionary.VadValue;

public interface VadSentenceAnalyzer {
    VadValue analyzeSentence(String sentence);
}
