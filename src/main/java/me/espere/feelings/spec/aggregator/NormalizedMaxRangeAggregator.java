package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.VadValue;
import me.espere.feelings.spec.analyzer.WordAnalysis;
import me.espere.feelings.spec.dictionary.Dictionary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.function.Function;

public class NormalizedMaxRangeAggregator extends MaxRangeAggregator {
    public NormalizedMaxRangeAggregator(Dictionary dictionary) {
        super(dictionary);
    }

    @Override
    public VadValue aggregate(String text, Collection<WordAnalysis> wordAnalyses) {
        VadValue aggregatedVadValue = super.aggregate(text, wordAnalyses);
        return normalize(aggregatedVadValue);
    }

    private VadValue normalize(VadValue aggregatedVadValue) {
        return new VadValue(
                normalizeField(aggregatedVadValue, VadValue::getValence),
                normalizeField(aggregatedVadValue, VadValue::getArousal),
                normalizeField(aggregatedVadValue, VadValue::getDominance)
        );
    }

    private BigDecimal normalizeField(VadValue aggregatedVadValue, Function<VadValue, BigDecimal> field) {
        BigDecimal value = field.apply(aggregatedVadValue);
        BigDecimal minimum = field.apply(dictionary.getMinVadValue());
        BigDecimal maximum = field.apply(dictionary.getMaxVadValue());

        BigDecimal numerator = value.subtract(minimum);
        BigDecimal denominator = maximum.subtract(minimum);

        return numerator.divide(denominator, RoundingMode.HALF_UP);
    }
}
