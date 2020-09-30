package com.lc.misc.usualTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

public class AverageTest {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        System.out.println(calcMaxMinAverage(list));
    }

    private static List<Double> calcMaxMinAverage(List<Object> data) {
        Long start = System.currentTimeMillis();
        Double max = new Double(0);
        Double min = new Double(0);
        Double average = new Double(0);
        try {
            Supplier<DoubleStream> streamSupplier = () -> data.stream().filter(Objects::nonNull).map(Object::toString).mapToDouble(Double::valueOf);
            max = streamSupplier.get().max().getAsDouble();
            min = streamSupplier.get().min().getAsDouble();
            average = streamSupplier.get().average().getAsDouble();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - start));

        return Arrays.asList(max, min, average);
    }
}
