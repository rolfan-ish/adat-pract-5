package io.gitlab.rolfan.util;

import java.util.List;
import java.util.function.BiConsumer;

public interface ListUtil {
    static <T> void forEachIndexed(List<T> l, BiConsumer<Integer, T> consumer) {
        int i = 0;
        for (var e : l) {
            consumer.accept(i, e);
            i++;
        }
    }
}
