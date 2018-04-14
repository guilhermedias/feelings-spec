package me.espere.feelings.spec.analyzer;

import me.espere.feelings.spec.aggregator.VadAggregator;
import me.espere.feelings.spec.dictionary.VadDictionary;
import me.espere.feelings.spec.dictionary.VadEntry;
import me.espere.feelings.spec.dictionary.VadValue;
import me.espere.feelings.spec.lemmatizer.Lemmatizer;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
        Collection<String> lemmas = lemmatizer.lemmas(sentence);

        Collection<VadEntry> entries = lemmas
                .stream()
                .map(dictionary::getEntry)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        VadValue vadValue = aggregator.aggregate(sentence, entries);

        return new VadSentenceAnalysis(vadValue, entries);
    }
}