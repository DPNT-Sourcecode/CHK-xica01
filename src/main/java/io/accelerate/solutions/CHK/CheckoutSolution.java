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
        skuPrices.put('E', 40);

//counting all item
        Map<Character, Integer> skuCounts = new HashMap<>();

        for (char ch : skus.toCharArray()) {
            if (!skuPrices.containsKey(ch)) {
                return -1; // Invalid SKU
            }
            skuCounts.put(ch, skuCounts.getOrDefault(ch, 0) + 1);
        }

        int total = 0;

//apply e offer
        if (skuCounts.containsKey('E')) {
            int freeBs = skuCounts.get('E') / 2;
            int currentB = skuCounts.getOrDefault('B', 0);
            skuCounts.put('B', Math.max(0, currentB - freeBs));
        }

//apply a offers
        int countA = skuCounts.getOrDefault('A', 0);
        total += (countA / 5) * 200;
        countA %= 5;
        total += (countA / 3) * 130;
        countA %= 3;
        total += countA * skuPrices.get('A');

//apply b offers
        int countB = skuCounts.getOrDefault('B', 0);
        total += (countB / 2) * 45;
        total += (countB % 2) * skuPrices.get('B');
        
//regular prices for c, d, e
        total += skuCounts.getOrDefault('C', 0) * skuPrices.get('C');
        total += skuCounts.getOrDefault('D', 0) * skuPrices.get('D');
        total += skuCounts.getOrDefault('E', 0) * skuPrices.get('E');

        return total;

    }
}


