package com.lc.misc.utils;

import javax.annotation.Generated;
import java.util.HashMap;

/**
 * <pre>
 * 模仿 Java 10 的 Map.of() 方法。用法：<code>
 *
 *   Map&lt;String, Object&gt; map = Maps.of(
 *       "id", 1,
 *       "name", "alice",
 *       "age", 100
 *   );
 * </code></pre>
 */
public class Maps {

    private static final int MAX_ARITY = 20;

    /**
     * code generator
     *
     * @param args useless
     */
    public static void main(String... args) {
        for (int i = 0; i < MAX_ARITY; i++) {
            System.out.println("@Generated(\"Maps\")");
            System.out.print("public static <K, V> HashMap<K, V> of(K k0, V v0");
            for (int j = 0; j < i; j++) {
                System.out.print(", K k" + h(j + 1) + ", V v" + h(j + 1));
            }
            System.out.println(") {");
            System.out.println("    HashMap<K, V> m = new HashMap<>();");
            for (int j = 0; j <= i; j++) {
                System.out.println("    m.put(k" + h(j) + ", v" + h(j) + ");");
            }
            System.out.println("    return m;");
            System.out.println("}");
            System.out.println();
        }
    }

    private static char h(int i) {
        return Integer.toString(i, 36).charAt(0);
    }

    public static <K, V> HashMap<K, V> empty() {
        return new HashMap<>();
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb, K kc, V vc) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        m.put(kc, vc);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb, K kc, V vc, K kd, V vd) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        m.put(kc, vc);
        m.put(kd, vd);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb, K kc, V vc, K kd, V vd, K ke, V ve) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        m.put(kc, vc);
        m.put(kd, vd);
        m.put(ke, ve);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb, K kc, V vc, K kd, V vd, K ke, V ve, K kf, V vf) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        m.put(kc, vc);
        m.put(kd, vd);
        m.put(ke, ve);
        m.put(kf, vf);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb, K kc, V vc, K kd, V vd, K ke, V ve, K kf, V vf, K kg, V vg) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        m.put(kc, vc);
        m.put(kd, vd);
        m.put(ke, ve);
        m.put(kf, vf);
        m.put(kg, vg);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb, K kc, V vc, K kd, V vd, K ke, V ve, K kf, V vf, K kg, V vg, K kh, V vh) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        m.put(kc, vc);
        m.put(kd, vd);
        m.put(ke, ve);
        m.put(kf, vf);
        m.put(kg, vg);
        m.put(kh, vh);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb, K kc, V vc, K kd, V vd, K ke, V ve, K kf, V vf, K kg, V vg, K kh, V vh, K ki, V vi) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        m.put(kc, vc);
        m.put(kd, vd);
        m.put(ke, ve);
        m.put(kf, vf);
        m.put(kg, vg);
        m.put(kh, vh);
        m.put(ki, vi);
        return m;
    }

    @Generated("Maps")
    public static <K, V> HashMap<K, V> of(K k0, V v0, K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K ka, V va, K kb, V vb, K kc, V vc, K kd, V vd, K ke, V ve, K kf, V vf, K kg, V vg, K kh, V vh, K ki, V vi, K kj, V vj) {
        HashMap<K, V> m = new HashMap<>();
        m.put(k0, v0);
        m.put(k1, v1);
        m.put(k2, v2);
        m.put(k3, v3);
        m.put(k4, v4);
        m.put(k5, v5);
        m.put(k6, v6);
        m.put(k7, v7);
        m.put(k8, v8);
        m.put(k9, v9);
        m.put(ka, va);
        m.put(kb, vb);
        m.put(kc, vc);
        m.put(kd, vd);
        m.put(ke, ve);
        m.put(kf, vf);
        m.put(kg, vg);
        m.put(kh, vh);
        m.put(ki, vi);
        m.put(kj, vj);
        return m;
    }

}
