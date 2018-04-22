package me.espere.feelings.spec.analyzer;

import me.espere.feelings.spec.aggregator.Aggregator;
import me.espere.feelings.spec.commons.Conditions;
import me.espere.feelings.spec.dictionary.Dictionary;
import me.espere.feelings.spec.dictionary.Entry;
import me.espere.feelings.spec.VadValue;
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
public class SimpleSentenceAnalyzerTest {
    private SimpleSentenceAnalyzer analyzer;

    @Mock
    private Lemmatizer lemmatizer;

    @Mock
    private Dictionary dictionary;

    @Mock
    private Aggregator aggregator;

    @Before
    public void setUp() {
        analyzer = new SimpleSentenceAnalyzer(lemmatizer, dictionary, aggregator);
    }

    @Test
    public void shouldAnalyzeEmptySentence() {
        when(lemmatizer.lemmas(""))
                .thenReturn(emptyList());

        when(aggregator.aggregate("", emptyList()))
                .thenReturn(new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));

        SentenceAnalysis sentenceAnalysis = analyzer.analyzeSentence("");

        VadValue sentenceVadValue = sentenceAnalysis.getVadValue();
        assertThat(sentenceVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(sentenceAnalysis.getWordAnalyses()).isEmpty();
    }

    @Test
    public void shouldAnalyzeSingleWordSentence() {
        when(lemmatizer.lemmas("abnormal"))
                .thenReturn(singletonList(new Lemma("abnormal", "abnormal")));


        Entry entry = new Entry("abnormal", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        when(dictionary.getEntry("abnormal"))
                .thenReturn(Optional.of(entry));

        SentenceWordAnalysis wordAnalysis = new SentenceWordAnalysis("abnormal", "abnormal", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        when(aggregator.aggregate("abnormal", singletonList(wordAnalysis)))
                .thenReturn(new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));

        SentenceAnalysis sentenceAnalysis = analyzer.analyzeSentence("abnormal");

        VadValue sentenceVadValue = sentenceAnalysis.getVadValue();
        assertThat(sentenceVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(sentenceAnalysis.getWordAnalyses())
                .containsExactly(new SentenceWordAnalysis("abnormal", "abnormal", new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                )));
    }

    @Test
    public void shouldAnalyzeMultipleWordsSentence() {
        when(lemmatizer.lemmas("much word"))
                .thenReturn(asList(new Lemma("much", "much"), new Lemma("word", "word")));

        Entry entry1 = new Entry("much", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        Entry entry2 = new Entry("word", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        when(dictionary.getEntry("much")).thenReturn(Optional.of(entry1));
        when(dictionary.getEntry("word")).thenReturn(Optional.of(entry2));

        SentenceWordAnalysis wordAnalysis1 = new SentenceWordAnalysis("much", "much", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        SentenceWordAnalysis wordAnalysis2 = new SentenceWordAnalysis("word", "word", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        when(aggregator.aggregate("much word", asList(wordAnalysis1, wordAnalysis2)))
                .thenReturn(new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));

        SentenceAnalysis sentenceAnalysis = analyzer.analyzeSentence("much word");

        VadValue sentenceVadValue = sentenceAnalysis.getVadValue();
        assertThat(sentenceVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(sentenceAnalysis.getWordAnalyses())
                .containsExactly(
                        new SentenceWordAnalysis("much", "much", new VadValue(
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO
                        )),
                        new SentenceWordAnalysis("word", "word", new VadValue(
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO
                        ))
                );
    }
}
