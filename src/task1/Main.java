package task1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<OrderService.OrderData> orderDataList = new ArrayList<>();
        orderDataList.add(new OrderService.OrderData(OrderService.Type.DELIVERY, "EUR", 2000L));
        orderDataList.add(new OrderService.OrderData(OrderService.Type.DELIVERY, "USD", 15L));
        orderDataList.add(new OrderService.OrderData(OrderService.Type.DELIVERY, "RUB", 200L));
        orderDataList.add(new OrderService.OrderData(OrderService.Type.DELIVERY, "USD", 350L));
        orderDataList.add(new OrderService.OrderData(OrderService.Type.DELIVERY, "RUB", 100L));
        orderDataList.add(new OrderService.OrderData(OrderService.Type.PICKUP, "RUB", 1250L));
        orderDataList.add(new OrderService.OrderData(OrderService.Type.PICKUP, "USD", 55L));
        System.out.println(getMaxMinusMinDeliveryMapByCurrency(orderDataList));
    }

    /**
     *
     * @return map вида [валюта (в порядке возрастания разницы) – разница между самым большим и маленьким заказом типа DELIVERY для валюты].
    Если по какой-то валюте только один заказ, то он является и самым большим и самым маленьким и разница равна 0.
     * Пример входных данных:
     * [
     * Order(DELIVERY, "EUR", 2000),
     * Order (DELIVERY, "USD", 15),
     * Order (DELIVERY, "RUB", 200),
     * Order (PICKUP, "RUB", 1250),
     * Order (DELIVERY, "USD", 35),
     * Order (PICKUP, "USD", 55),
     * Order (DELIVERY, "RUB", 100)
     * ]
     * Ожидаемый результат:
     * ["EUR" -> 0.0, "USD" -> 20.0, "RUB" -> 100.0]
     */
    public static Map<String, Double> getMaxMinusMinDeliveryMapByCurrency(List<OrderService.OrderData> orderDataList) {
        Map<String, DoubleSummaryStatistics> tmp = orderDataList
                .stream()
                .filter(orderData -> orderData.getType().equals(OrderService.Type.DELIVERY))
                .collect(Collectors.groupingBy(
                        OrderService.OrderData::getCurrency,
                        Collectors.summarizingDouble(OrderService.OrderData::getAmount)));
        Map<String, Double> result = new HashMap<>();
        for(Map.Entry<String, DoubleSummaryStatistics> item : tmp.entrySet()){
            result.put(item.getKey(), item.getValue().getMax() - item.getValue().getMin());
        }

        return sortByValue(result);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
        Map<K,V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K,V>> st = map.entrySet().stream();
        st.sorted(Map.Entry.comparingByValue())
                .forEach(e ->result.put(e.getKey(),e.getValue()));
        return result;
    }
}
