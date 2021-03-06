package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.VadValue;
import me.espere.feelings.spec.analyzer.WordAnalysis;
import me.espere.feelings.spec.dictionary.Dictionary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static me.espere.feelings.spec.commons.Conditions.equalTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MaxRangeAggregatorTest {
    private Aggregator aggregator;

    @Mock
    private Dictionary dictionary;

    @Before
    public void setUp() {
        aggregator = new MaxRangeAggregator(dictionary);

        when(dictionary.getMeanVadValue())
                .thenReturn(new VadValue(
                        BigDecimal.valueOf(5.06),
                        BigDecimal.valueOf(4.21),
                        BigDecimal.valueOf(5.18)
                ));
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void shouldAggregateEmptyText() {
        Collection<WordAnalysis> wordAnalyses = emptyList();

        VadValue aggregate = aggregator.aggregate("", wordAnalyses);

        assertThat(aggregate.getValence()).is(equalTo(0.00));
        assertThat(aggregate.getArousal()).is(equalTo(0.00));
        assertThat(aggregate.getDominance()).is(equalTo(0.00));
    }

    @Test
    public void shouldAggregateTheMaximumRange() {
        Collection<WordAnalysis> wordAnalyses = asList(
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(2.28),
                        BigDecimal.valueOf(6.24),
                        BigDecimal.valueOf(5.82)
                )),
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(5.56),
                        BigDecimal.valueOf(1.92),
                        BigDecimal.valueOf(6.36)
                )),
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(7.06),
                        BigDecimal.valueOf(4.95),
                        BigDecimal.valueOf(3.44)
                ))
        );

        VadValue aggregate = aggregator.aggregate("", wordAnalyses);

        assertThat(aggregate.getValence()).is(equalTo(4.78));
        assertThat(aggregate.getArousal()).is(equalTo(4.32));
        assertThat(aggregate.getDominance()).is(equalTo(2.92));
    }

    @Test
    public void shouldUseDictionaryMeanValueWhenWordsMinimumIsAboveAverage() {
        Collection<WordAnalysis> wordAnalyses = asList(
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(5.15),
                        BigDecimal.valueOf(6.24),
                        BigDecimal.valueOf(5.82)
                )),
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(5.56),
                        BigDecimal.valueOf(4.78),
                        BigDecimal.valueOf(6.36)
                )),
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(7.06),
                        BigDecimal.valueOf(4.95),
                        BigDecimal.valueOf(5.54)
                ))
        );

        VadValue aggregate = aggregator.aggregate("", wordAnalyses);

        assertThat(aggregate.getValence()).is(equalTo(2.00));
        assertThat(aggregate.getArousal()).is(equalTo(2.03));
        assertThat(aggregate.getDominance()).is(equalTo(1.18));
    }

    @Test
    public void shouldUseDictionaryMeanValueWhenWordsMaximumIsBelowAverage() {
        Collection<WordAnalysis> wordAnalyses = asList(
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(2.28),
                        BigDecimal.valueOf(4.12),
                        BigDecimal.valueOf(4.82)
                )),
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(4.56),
                        BigDecimal.valueOf(1.92),
                        BigDecimal.valueOf(5.14)
                )),
                new WordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(5.02),
                        BigDecimal.valueOf(3.95),
                        BigDecimal.valueOf(3.44)
                ))
        );

        VadValue aggregate = aggregator.aggregate("", wordAnalyses);

        assertThat(aggregate.getValence()).is(equalTo(2.78));
        assertThat(aggregate.getArousal()).is(equalTo(2.29));
        assertThat(aggregate.getDominance()).is(equalTo(1.74));
    }
}