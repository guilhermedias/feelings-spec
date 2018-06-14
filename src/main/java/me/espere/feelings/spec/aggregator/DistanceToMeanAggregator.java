package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.VadValue;
import me.espere.feelings.spec.analyzer.WordAnalysis;
import me.espere.feelings.spec.dictionary.Dictionary;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public class DistanceToMeanAggregator implements Aggregator {
    private Dictionary dictionary;

    public DistanceToMeanAggregator(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public VadValue aggregate(String text, Collection<WordAnalysis> wordAnalyses) {
        return new VadValue(
                calculateMaximumFieldDistanceToMean(wordAnalyses, VadValue::getValence),
                calculateMaximumFieldDistanceToMean(wordAnalyses, VadValue::getArousal),
                calculateMaximumFieldDistanceToMean(wordAnalyses, VadValue::getDominance)
        );
    }

    private BigDecimal calculateMaximumFieldDistanceToMean(Collection<WordAnalysis> wordAnalyses,
                                                           Function<VadValue, BigDecimal> field) {
        BigDecimal fieldMeanValue = field.apply(dictionary.getMeanVadValue());

        return wordAnalyses
                .stream()
                .map(WordAnalysis::getVadValue)
                .map(field)
                .map(distanceTo(fieldMeanValue))
                .max(comparing(BigDecimal::abs))
                .orElse(BigDecimal.ZERO);
    }

    private Function<BigDecimal, BigDecimal> distanceTo(BigDecimal fieldMeanValue) {
        return fieldValue -> fieldValue.subtract(fieldMeanValue);
    }
}
