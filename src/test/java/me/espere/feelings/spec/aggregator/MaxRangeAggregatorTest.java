package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.analyzer.SentenceWordAnalysis;
import me.espere.feelings.spec.VadValue;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static me.espere.feelings.spec.commons.Conditions.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

public class MaxRangeAggregatorTest {
    private Aggregator aggregator;

    @Before
    public void setUp() {
        aggregator = new MaxRangeAggregator();
    }

    @Test
    public void shouldAggregateEmptySentences() {
        Collection<SentenceWordAnalysis> wordAnalyses = emptyList();

        VadValue aggregate = aggregator.aggregate("", wordAnalyses);

        assertThat(aggregate.getValence()).is(equalTo(0.00));
        assertThat(aggregate.getArousal()).is(equalTo(0.00));
        assertThat(aggregate.getDominance()).is(equalTo(0.00));
    }

    @Test
    public void shouldAggregateTheMaximumRange() {
        Collection<SentenceWordAnalysis> wordAnalyses = asList(
                new SentenceWordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(2.28),
                        BigDecimal.valueOf(6.24),
                        BigDecimal.valueOf(5.82)
                )),
                new SentenceWordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(5.56),
                        BigDecimal.valueOf(1.92),
                        BigDecimal.valueOf(6.36)
                )),
                new SentenceWordAnalysis("", "", new VadValue(
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
        Collection<SentenceWordAnalysis> wordAnalyses = asList(
                new SentenceWordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(5.15),
                        BigDecimal.valueOf(6.24),
                        BigDecimal.valueOf(5.82)
                )),
                new SentenceWordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(5.56),
                        BigDecimal.valueOf(4.78),
                        BigDecimal.valueOf(6.36)
                )),
                new SentenceWordAnalysis("", "", new VadValue(
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
        Collection<SentenceWordAnalysis> wordAnalyses = asList(
                new SentenceWordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(2.28),
                        BigDecimal.valueOf(4.12),
                        BigDecimal.valueOf(4.82)
                )),
                new SentenceWordAnalysis("", "", new VadValue(
                        BigDecimal.valueOf(4.56),
                        BigDecimal.valueOf(1.92),
                        BigDecimal.valueOf(5.14)
                )),
                new SentenceWordAnalysis("", "", new VadValue(
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