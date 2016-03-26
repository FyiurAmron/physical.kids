package vax.util;

/**

 @author toor
 @param <T>
 */
@FunctionalInterface
public interface Action<T> {
    void exec ( T target );
}
