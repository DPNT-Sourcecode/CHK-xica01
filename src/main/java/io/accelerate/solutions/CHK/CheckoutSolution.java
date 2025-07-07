package io.accelerate.solutions.CHK;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {
    public Integer checkout(String skus) {
        if (skus == null) {
            return -1;
        }
        
// listing individual prices of items        
        Map<Character, Integer> skuPrices = new HashMap<>();
        skuPrices.put('A', 50);
        skuPrices.put('B', 30);
        skuPrices.put('C', 20);
        skuPrices.put('D', 15);

//listing the offers
        Map<Character, int[]> skuOffers = new HashMap<>();
        skuOffers.put('A', new int[]{3, 130}); // 3 for 130
        skuOffers.put('B', new int[]{2, 45}); // 2 for 45

//counting all item
        Map<Character, Integer> skuCounts = new HashMap<>();

        for (char sku : skus.toCharArray()) {
            if (!skuPrices.containsKey(sku)) {
                return -1;
            }
            skuCounts.put(sku, skuCounts.getOrDefault(sku, 0) + 1);
        }

        int total = 0;

        for (Map.Entry<Character, Integer> entry : skuCounts.entrySet()) {
            char item = entry.getKey();
            int quantity = entry.getValue();
            
            if (skuOffers.containsKey(item)) {
                int offerQuantity = skuOffers.get(item)[0];
                int offerPrice = skuOffers.get(item)[1];
                int offerBundles = quantity / offerQuantity;
                int remainingItems = quantity % offerQuantity;
                total += offerBundles * offerPrice + remainingItems * skuPrices.get(item);
            }
            else {
                total += quantity * skuPrices.get(item);
            }
        }
        return total;
    }
}
