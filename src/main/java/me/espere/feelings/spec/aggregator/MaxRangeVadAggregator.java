package me.espere.feelings.spec.aggregator;

import me.espere.feelings.spec.analyzer.VadSentenceWordAnalysis;
import me.espere.feelings.spec.dictionary.VadValue;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public class MaxRangeVadAggregator implements VadAggregator {
    private static final VadValue MEAN_VAD_VALUE = new VadValue(
            BigDecimal.valueOf(5.06),
            BigDecimal.valueOf(4.21),
            BigDecimal.valueOf(5.18)
    );

    @Override
    public VadValue aggregate(String sentence, Collection<VadSentenceWordAnalysis> words) {
        if (words.isEmpty()) {
            return new VadValue(
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO
            );
        }

        BigDecimal valenceMaxRange = calculateFieldMaxRange(words, VadValue::getValence);
        BigDecimal arousalMaxRange = calculateFieldMaxRange(words, VadValue::getArousal);
        BigDecimal dominanceMaxRange = calculateFieldMaxRange(words, VadValue::getDominance);

        return new VadValue(
                valenceMaxRange,
                arousalMaxRange,
                dominanceMaxRange
        );
    }

    private BigDecimal calculateFieldMaxRange(Collection<VadSentenceWordAnalysis> words,
                                              Function<VadValue, BigDecimal> field) {
        BigDecimal fieldMinValue = calculateFieldMinValue(words, field);
        BigDecimal fieldMaxValue = calculateFieldMaxValue(words, field);

        return fieldMaxValue.subtract(fieldMinValue);
    }

    private BigDecimal calculateFieldMinValue(Collection<VadSentenceWordAnalysis> words,
                                              Function<VadValue, BigDecimal> field) {
        BigDecimal wordsFieldMinimum = words
                .stream()
                .map(VadSentenceWordAnalysis::getVadValue)
                .min(comparing(field))
                .map(field)
                .orElse(BigDecimal.ZERO);

        return wordsFieldMinimum.min(field.apply(MEAN_VAD_VALUE));
    }

    private BigDecimal calculateFieldMaxValue(Collection<VadSentenceWordAnalysis> words,
                                              Function<VadValue, BigDecimal> field) {
        BigDecimal wordsFieldMaximum = words
                .stream()
                .map(VadSentenceWordAnalysis::getVadValue)
                .max(comparing(field))
                .map(field)
                .orElse(BigDecimal.ZERO);

        return wordsFieldMaximum.max(field.apply(MEAN_VAD_VALUE));
    }
}
