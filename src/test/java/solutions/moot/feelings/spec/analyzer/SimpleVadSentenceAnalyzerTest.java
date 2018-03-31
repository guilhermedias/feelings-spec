package solutions.moot.feelings.spec.analyzer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import solutions.moot.feelings.spec.aggregator.VadAggregator;
import solutions.moot.feelings.spec.dictionary.VadDictionary;
import solutions.moot.feelings.spec.dictionary.VadEntry;
import solutions.moot.feelings.spec.dictionary.VadValue;
import solutions.moot.feelings.spec.lemmatizer.Lemmatizer;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static solutions.moot.feelings.spec.commons.Conditions.equalTo;

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

        VadValue sentenceVadValue = analyzer.analyzeSentence("");

        assertThat(sentenceVadValue.getValence()).is(equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(equalTo(0.0));
    }

    @Test
    public void shouldAnalyzeSingleWordSentence() {
        when(lemmatizer.lemmas("abnormal"))
                .thenReturn(asList("abnormal"));

        VadEntry wordVadEntry = new VadEntry("abnormal", new VadValue(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        ));

        when(dictionary.getEntry("abnormal")).thenReturn(Optional.of(wordVadEntry));

        when(aggregator.aggregate("abnormal", asList(wordVadEntry))).thenReturn(
                new VadValue(
                        BigDecimal.ZERO,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                ));

        VadValue sentenceVadValue = analyzer.analyzeSentence("abnormal");

        assertThat(sentenceVadValue.getValence()).is(equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(equalTo(0.0));
    }

    @Test
    public void shouldAnalyzeMultipleWordsSentence() {
        when(lemmatizer.lemmas("much word"))
                .thenReturn(asList("much", "word"));

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

        VadValue sentenceVadValue = analyzer.analyzeSentence("much word");

        assertThat(sentenceVadValue.getValence()).is(equalTo(0.0));
        assertThat(sentenceVadValue.getArousal()).is(equalTo(0.0));
        assertThat(sentenceVadValue.getDominance()).is(equalTo(0.0));
    }
}
