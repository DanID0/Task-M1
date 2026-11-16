package org.example.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class CollectionUtils {
    public static <T> List<T> filter(
            List<T> items,
            Predicate<T> condition
    ) {
        List<T> filteredItems = new ArrayList<>();
        for (T item : items) {
            if (condition.test(item)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    public static <T> Optional<T> findFirst(
            List<T> items,
            Predicate<T> condition
    ) {
        for (T item : items) {
            if (condition.test(item)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    public static <T> double average(
            List<T> items,
            ToIntFunction<T> valueExtractor
    ) {
        if (items.isEmpty()) {
            return 0.0;
        }
        int sum = 0;

        for (T item : items) {
            sum += valueExtractor.applyAsInt(item);
        }

        return (double) sum / items.size();
    }
}
