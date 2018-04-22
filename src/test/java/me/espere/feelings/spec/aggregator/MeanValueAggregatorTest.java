package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.analyzer.WordAnalysis;
import me.espere.feelings.spec.VadValue;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static me.espere.feelings.spec.commons.Conditions.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

public class MeanValueAggregatorTest {
    private MeanValueAggregator aggregator;

    @Before
    public void setUp() {
        aggregator = new MeanValueAggregator();
    }

    @Test
    public void shouldAggregateEmptyText() {
        Collection<WordAnalysis> wordAnalyses = emptyList();

        VadValue aggregate = aggregator.aggregate("", wordAnalyses);

        assertThat(aggregate.getValence()).is(equalTo(0.00));
        assertThat(aggregate.getArousal()).is(equalTo(0.00));
        assertThat(aggregate.getDominance()).is(equalTo(0.00));
    }

    @Test
    public void shouldAggregateTheMeanValuesUsingNaturalRounding() {
        Collection<WordAnalysis> wordAnalyses = asList(
                new WordAnalysis(
                        "",
                        "",
                        new VadValue(
                                BigDecimal.valueOf(4.28),
                                BigDecimal.valueOf(3.75),
                                BigDecimal.valueOf(6.04)
                        )),
                new WordAnalysis(
                        "",
                        "",
                        new VadValue(
                                BigDecimal.valueOf(2.95),
                                BigDecimal.valueOf(1.26),
                                BigDecimal.valueOf(3.52)
                        )));

        VadValue aggregate = aggregator.aggregate("", wordAnalyses);

        assertThat(aggregate.getValence()).is(equalTo(3.62));
        assertThat(aggregate.getArousal()).is(equalTo(2.51));
        assertThat(aggregate.getDominance()).is(equalTo(4.78));
    }
}
