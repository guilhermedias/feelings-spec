package solutions.moot.feelings.spec.commons;

import org.assertj.core.api.Condition;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class Conditions {
    public static Condition<BigDecimal> equalTo(Double otherValue) {
        Predicate<BigDecimal> hasValueOf = bigDecimal -> {
            BigDecimal otherBigDecimal = BigDecimal.valueOf(otherValue);
            BigDecimal difference = bigDecimal.subtract(otherBigDecimal);

            return difference.doubleValue() == 0.0;
        };

        return new Condition<>(hasValueOf, "equals to %s", otherValue);
    }
}
