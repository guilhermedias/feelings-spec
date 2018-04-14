package me.espere.feelings.spec.analyzer;

import me.espere.feelings.spec.aggregator.VadAggregator;
import me.espere.feelings.spec.commons.Conditions;
import me.espere.feelings.spec.dictionary.VadDictionary;
import me.espere.feelings.spec.dictionary.VadEntry;
import me.espere.feelings.spec.dictionary.VadValue;
import me.espere.feelings.spec.lemmatizer.Lemma;
import me.espere.feelings.spec.lemmatizer.Lemmatizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleVadSentenceAnalyzerTest {
    private SimpleVadSentenceAnalyzer analyzer;

    @Mock
    private Lemmatizer lemmatizer;

    @Mock
    private VadDictionary dictionary;

    @Mock
    private VadAggregator aggregator;

    @Before
    public void setUp() {
        analyzer = new SimpleVadSentenceAnalyzer(lemmatizer, dictionary, aggregator);
    }

    @Test
    public void shouldAnalyzeEmptySentence() {
        when(lemmatizer.lemmas(""))
                .thenReturn(emptyList());

        when(aggregator.aggregate("", emptyList())).thenReturn(
                new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));

        VadSentenceAnalysis sentenceAnalysis = analyzer.analyzeSentence("");

        VadValue sentenceVadValue = sentenceAnalysis.getVadValue();
        assertThat(sentenceVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(sentenceAnalysis.getEntries()).isEmpty();
    }

    @Test
    public void shouldAnalyzeSingleWordSentence() {
        Lemma lemma = new Lemma("abnormal", "abnormal");

        when(lemmatizer.lemmas("abnormal"))
                .thenReturn(singletonList(lemma));

        VadEntry entry = new VadEntry("abnormal", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        when(dictionary.getEntry("abnormal")).thenReturn(Optional.of(entry));

        when(aggregator.aggregate("abnormal", singletonList(entry))).thenReturn(
                new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));

        VadSentenceAnalysis sentenceAnalysis = analyzer.analyzeSentence("abnormal");

        VadValue sentenceVadValue = sentenceAnalysis.getVadValue();
        assertThat(sentenceVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(sentenceAnalysis.getEntries()).containsExactly(entry);
    }

    @Test
    public void shouldAnalyzeMultipleWordsSentence() {
        Lemma lemma1 = new Lemma("much", "much");
        Lemma lemma2 = new Lemma("word", "word");

        when(lemmatizer.lemmas("much word"))
                .thenReturn(asList(lemma1, lemma2));

        VadEntry entry1 = new VadEntry("much", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        VadEntry entry2 = new VadEntry("word", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        when(dictionary.getEntry("much")).thenReturn(Optional.of(entry1));
        when(dictionary.getEntry("word")).thenReturn(Optional.of(entry2));

        when(aggregator.aggregate("much word", asList(entry1, entry2))).thenReturn(
                new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));

        VadSentenceAnalysis sentenceAnalysis = analyzer.analyzeSentence("much word");

        VadValue sentenceVadValue = sentenceAnalysis.getVadValue();
        assertThat(sentenceVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(sentenceAnalysis.getEntries()).containsExactly(entry1, entry2);
    }
}
