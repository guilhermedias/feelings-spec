package me.espere.feelings.spec.analyzer;

import me.espere.feelings.spec.aggregator.VadAggregator;
import me.espere.feelings.spec.dictionary.VadDictionary;
import me.espere.feelings.spec.dictionary.VadEntry;
import me.espere.feelings.spec.dictionary.VadValue;
import me.espere.feelings.spec.lemmatizer.Lemma;
import me.espere.feelings.spec.lemmatizer.Lemmatizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class SimpleVadSentenceAnalyzer implements VadSentenceAnalyzer {
    private Lemmatizer lemmatizer;
    private VadDictionary dictionary;
    private VadAggregator aggregator;

    public SimpleVadSentenceAnalyzer(Lemmatizer lemmatizer, VadDictionary dictionary, VadAggregator aggregator) {
        this.lemmatizer = lemmatizer;
        this.dictionary = dictionary;
        this.aggregator = aggregator;
    }

    @Override
    public VadSentenceAnalysis analyzeSentence(String sentence) {
        Collection<Lemma> lemmas = lemmatizer.lemmas(sentence);

        Collection<VadSentenceWordAnalysis> wordAnalyses = new ArrayList<>();

        lemmas.forEach(lemma -> {
            Optional<VadEntry> entryOptional = dictionary.getEntry(lemma.getValue());

            entryOptional.ifPresent(entry ->
                    wordAnalyses.add(new VadSentenceWordAnalysis(
                            lemma.getWord(),
                            lemma.getValue(),
                            entry.getVadValue()
                    ))
            );
        });

        VadValue vadValue = aggregator.aggregate(sentence, wordAnalyses);

        return new VadSentenceAnalysis(vadValue, wordAnalyses);
    }
}
