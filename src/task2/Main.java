package task2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        double[] t = {2,1,4,2,3};
        System.out.println(Arrays.toString(task(t)));
    }

    /**
     * @param a массив чисел, длина массива может быть больше 10млн.
     * @return массив чисел, в которых нeт дубликатов. Порядок чисел в оригинальном массиве должен быть сохранён.
     * Из дубликатов нужно оставлять последний элемент, например, для {2,1,4,2,3} правильное решение - {1,4,2,3}, а не {2,1,4,3}

    В случае, если во входном массиве есть элемент меньше 0, то нужно выдавать ошибку.
    Например, для {2,3,-1,5} обработка должна закончиться ошибкой.
     */
    public static double[] task(double[] a) {
        Map<Double, Integer> indexesMap = new LinkedHashMap<>();
        Set<Double> res = new LinkedHashSet<>(a.length);
        for(int i = 0; i < a.length; i++) {
            if (a[i] < 0) {
                throw new RuntimeException("Массив не должен содержать отрицательных значений");
            }
            indexesMap.put(a[i], i);
        }
        for(int i = 0; i < a.length; i++) {
            if (indexesMap.get(a[i]) == i) {
                res.add(a[i]);
            }
        }
        return res.stream().mapToDouble(Double::doubleValue).toArray();
    }

}

