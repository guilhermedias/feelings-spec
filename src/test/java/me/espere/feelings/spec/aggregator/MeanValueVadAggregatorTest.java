package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.analyzer.VadSentenceWordAnalysis;
import me.espere.feelings.spec.dictionary.VadValue;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static java.util.Arrays.asList;
import static me.espere.feelings.spec.commons.Conditions.equalTo;
import static org.assertj.core.api.Assertions.assertThat;

public class MeanValueVadAggregatorTest {
    private MeanValueVadAggregator aggregator;

    @Before
    public void setUp() {
        aggregator = new MeanValueVadAggregator();
    }

    @Test
    public void shouldAggregateTheMeanValuesUsingNaturalRounding() {
        Collection<VadSentenceWordAnalysis> wordAnalyses = asList(
                new VadSentenceWordAnalysis(
                        "",
                        "",
                        new VadValue(
                                BigDecimal.valueOf(4.28),
                                BigDecimal.valueOf(3.75),
                                BigDecimal.valueOf(6.04)
                        )),
                new VadSentenceWordAnalysis(
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
