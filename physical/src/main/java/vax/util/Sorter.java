package vax.util;

import java.util.List;

/**

 @author toor
 @param <T>
 @param <I>
 */
@FunctionalInterface
public interface Sorter<T, I extends Iterable<T>> {
    default List<T> sort ( I iterable ) {
        return sort( iterable, true );
    }

    List<T> sort ( I input, boolean ascending );
}
