package me.espere.feelings.spec.analyzer;

import me.espere.feelings.spec.VadValue;
import me.espere.feelings.spec.aggregator.Aggregator;
import me.espere.feelings.spec.commons.Conditions;
import me.espere.feelings.spec.dictionary.Dictionary;
import me.espere.feelings.spec.dictionary.Entry;
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
public class SimpleTextAnalyzerTest {
    private SimpleTextAnalyzer analyzer;

    @Mock
    private Lemmatizer lemmatizer;

    @Mock
    private Dictionary dictionary;

    @Mock
    private Aggregator aggregator;

    @Before
    public void setUp() {
        analyzer = new SimpleTextAnalyzer(lemmatizer, dictionary, aggregator);
    }

    @Test
    public void shouldAnalyzeEmptyText() {
        when(lemmatizer.lemmas(""))
                .thenReturn(emptyList());

        when(aggregator.aggregate("", emptyList()))
                .thenReturn(new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));

        TextAnalysis textAnalysis = analyzer.analyzeText("");

        VadValue textVadValue = textAnalysis.getVadValue();
        assertThat(textVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(textVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(textVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(textAnalysis.getWordAnalyses()).isEmpty();
    }

    @Test
    public void shouldAnalyzeSingleWordText() {
        when(lemmatizer.lemmas("abnormal"))
                .thenReturn(singletonList(new Lemma("abnormal", "abnormal")));


        Entry entry = new Entry("abnormal", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        when(dictionary.getEntry("abnormal"))
                .thenReturn(Optional.of(entry));

        WordAnalysis wordAnalysis = new WordAnalysis("abnormal", "abnormal", new VadValue(
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

        TextAnalysis textAnalysis = analyzer.analyzeText("abnormal");

        VadValue textVadValue = textAnalysis.getVadValue();
        assertThat(textVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(textVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(textVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(textAnalysis.getWordAnalyses())
                .containsExactly(new WordAnalysis("abnormal", "abnormal", new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                )));
    }

    @Test
    public void shouldAnalyzeMultipleWordsText() {
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

        WordAnalysis wordAnalysis1 = new WordAnalysis("much", "much", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        WordAnalysis wordAnalysis2 = new WordAnalysis("word", "word", new VadValue(
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

        TextAnalysis textAnalysis = analyzer.analyzeText("much word");

        VadValue textVadValue = textAnalysis.getVadValue();
        assertThat(textVadValue.getValence()).is(Conditions.equalTo(0.0));
        assertThat(textVadValue.getArousal()).is(Conditions.equalTo(0.0));
        assertThat(textVadValue.getDominance()).is(Conditions.equalTo(0.0));

        assertThat(textAnalysis.getWordAnalyses())
                .containsExactly(
                        new WordAnalysis("much", "much", new VadValue(
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO
                        )),
                        new WordAnalysis("word", "word", new VadValue(
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                BigDecimal.ZERO
                        ))
                );
    }
}
