package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.analyzer.WordAnalysis;
import me.espere.feelings.spec.VadValue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public class MaxRangeAggregator implements Aggregator {
    private static final VadValue MEAN_VAD_VALUE = new VadValue(
            BigDecimal.valueOf(5.06),
            BigDecimal.valueOf(4.21),
            BigDecimal.valueOf(5.18)
    );

    @Override
    public VadValue aggregate(String text, Collection<WordAnalysis> wordAnalyses) {
        if (wordAnalyses.isEmpty()) {
            return new VadValue(
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }

        BigDecimal valenceMaxRange = calculateFieldMaxRange(wordAnalyses, VadValue::getValence);
        BigDecimal arousalMaxRange = calculateFieldMaxRange(wordAnalyses, VadValue::getArousal);
        BigDecimal dominanceMaxRange = calculateFieldMaxRange(wordAnalyses, VadValue::getDominance);

        return new VadValue(
                valenceMaxRange,
                arousalMaxRange,
                dominanceMaxRange
        );
    }

    private BigDecimal calculateFieldMaxRange(Collection<WordAnalysis> words,
                                              Function<VadValue, BigDecimal> field) {
        BigDecimal fieldMinValue = calculateFieldMinValue(words, field);
        BigDecimal fieldMaxValue = calculateFieldMaxValue(words, field);

        return fieldMaxValue.subtract(fieldMinValue);
    }

    private BigDecimal calculateFieldMinValue(Collection<WordAnalysis> words,
                                              Function<VadValue, BigDecimal> field) {
        BigDecimal wordsFieldMinimum = words
                .stream()
                .map(WordAnalysis::getVadValue)
                .min(comparing(field))
                .map(field)
                .orElse(BigDecimal.ZERO);

        return wordsFieldMinimum.min(field.apply(MEAN_VAD_VALUE));
    }

    private BigDecimal calculateFieldMaxValue(Collection<WordAnalysis> words,
                                              Function<VadValue, BigDecimal> field) {
        BigDecimal wordsFieldMaximum = words
                .stream()
                .map(WordAnalysis::getVadValue)
                .max(comparing(field))
                .map(field)
                .orElse(BigDecimal.ZERO);

        return wordsFieldMaximum.max(field.apply(MEAN_VAD_VALUE));
    }
}
