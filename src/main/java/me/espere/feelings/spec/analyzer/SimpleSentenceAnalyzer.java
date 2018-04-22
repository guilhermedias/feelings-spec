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

public class SimpleSentenceAnalyzer implements SentenceAnalyzer {
    private Lemmatizer lemmatizer;
    private Dictionary dictionary;
    private Aggregator aggregator;

    public SimpleSentenceAnalyzer(Lemmatizer lemmatizer, Dictionary dictionary, Aggregator aggregator) {
        this.lemmatizer = lemmatizer;
        this.dictionary = dictionary;
        this.aggregator = aggregator;
    }

    @Override
    public SentenceAnalysis analyzeSentence(String sentence) {
        Collection<Lemma> lemmas = lemmatizer.lemmas(sentence);

        Collection<SentenceWordAnalysis> wordAnalyses = new ArrayList<>();

        lemmas.forEach(lemma -> {
            Optional<Entry> entryOptional = dictionary.getEntry(lemma.getValue());

            entryOptional.ifPresent(entry ->
                    wordAnalyses.add(new SentenceWordAnalysis(
                            lemma.getWord(),
                            lemma.getValue(),
                            entry.getVadValue()
                    ))
            );
        });

        VadValue vadValue = aggregator.aggregate(sentence, wordAnalyses);

        return new SentenceAnalysis(vadValue, wordAnalyses);
    }
}
