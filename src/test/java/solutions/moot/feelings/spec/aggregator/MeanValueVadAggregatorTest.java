package solutions.moot.feelings.spec.aggregator;

import org.junit.Before;
import org.junit.Test;
import solutions.moot.feelings.spec.dictionary.VadEntry;
import solutions.moot.feelings.spec.dictionary.VadValue;

import java.math.BigDecimal;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static solutions.moot.feelings.spec.commons.Conditions.equalTo;

public class MeanValueVadAggregatorTest {
    private MeanValueVadAggregator aggregator;

    @Before
    public void setUp() {
        aggregator = new MeanValueVadAggregator();
    }

    @Test
    public void shouldAggregateTheMeanValuesUsingNaturalRounding() {
        Collection<VadEntry> vadEntries = asList(
                new VadEntry("", new VadValue(
                        BigDecimal.valueOf(4.28),
                        BigDecimal.valueOf(3.75),
                        BigDecimal.valueOf(6.04)
                )),
                new VadEntry("", new VadValue(
                        BigDecimal.valueOf(2.95),
                        BigDecimal.valueOf(1.26),
                        BigDecimal.valueOf(3.52)
                )));

        VadValue aggregate = aggregator.aggregate("", vadEntries);

        assertThat(aggregate.getValence()).is(equalTo(3.62));
        assertThat(aggregate.getArousal()).is(equalTo(2.51));
        assertThat(aggregate.getDominance()).is(equalTo(4.78));
    }
}
