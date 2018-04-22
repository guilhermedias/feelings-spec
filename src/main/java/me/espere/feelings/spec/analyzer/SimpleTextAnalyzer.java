package me.espere.feelings.spec.analyzer;

import me.espere.feelings.spec.aggregator.Aggregator;
import me.espere.feelings.spec.dictionary.Dictionary;
import me.espere.feelings.spec.dictionary.Entry;
import me.espere.feelings.spec.VadValue;
import me.espere.feelings.spec.lemmatizer.Lemma;
import me.espere.feelings.spec.lemmatizer.Lemmatizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class SimpleTextAnalyzer implements TextAnalyzer {
    private Lemmatizer lemmatizer;
    private Dictionary dictionary;
    private Aggregator aggregator;

    public SimpleTextAnalyzer(Lemmatizer lemmatizer, Dictionary dictionary, Aggregator aggregator) {
        this.lemmatizer = lemmatizer;
        this.dictionary = dictionary;
        this.aggregator = aggregator;
    }

    @Override
    public TextAnalysis analyzeText(String text) {
        Collection<Lemma> lemmas = lemmatizer.lemmas(text);

        Collection<WordAnalysis> wordAnalyses = new ArrayList<>();

        lemmas.forEach(lemma -> {
            Optional<Entry> entryOptional = dictionary.getEntry(lemma.getValue());

            entryOptional.ifPresent(entry ->
                    wordAnalyses.add(new WordAnalysis(
                            lemma.getWord(),
                            lemma.getValue(),
                            entry.getVadValue()
                    ))
            );
        });

        VadValue vadValue = aggregator.aggregate(text, wordAnalyses);

        return new TextAnalysis(vadValue, wordAnalyses);
    }
}
