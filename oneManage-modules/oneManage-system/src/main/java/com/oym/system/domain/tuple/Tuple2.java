package com.oym.system.domain.tuple;

/**
 * @author oneyuanma
 * @date 2021/08/18
 */
public class Tuple2<T0, T1> {

    /**
     * Field 0 of the tuple.
     */
    public T0 t0;
    /**
     * Field 1 of the tuple.
     */
    public T1 t1;

    public Tuple2() {
    }

    public Tuple2(T0 t0, T1 t1) {
        this.t0 = t0;
        this.t1 = t1;
    }

    /**
     * Sets new values to all fields of the tuple.
     *
     * @param t0 The value for field 0
     * @param t1 The value for field 1
     */
    public void setFields(T0 t0, T1 t1) {
        this.t0 = t0;
        this.t1 = t1;
    }

    public T0 getT0() {
        return t0;
    }

    public T1 getT1() {
        return t1;
    }

    /**
     * Creates a new tuple and assigns the given values to the tuple's fields.
     * This is more convenient than using the constructor, because the compiler can
     * infer the generic type arguments implicitly. For example:
     * {@code Tuple3.of(n, x, s)}
     * instead of
     * {@code new Tuple3<Integer, Double, String>(n, x, s)}
     */
    public static <T0, T1> Tuple2<T0, T1> of(T0 value0, T1 value1) {
        return new Tuple2<>(value0,
                value1);
    }
}
