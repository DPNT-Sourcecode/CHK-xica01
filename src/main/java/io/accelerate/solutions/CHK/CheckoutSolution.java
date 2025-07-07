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
        skuPrices.put('F', 10);
        skuPrices.put('G', 20);
        skuPrices.put('H', 10);
        skuPrices.put('I', 35);
        skuPrices.put('J', 60);
        skuPrices.put('K', 80);
        skuPrices.put('L', 90);
        skuPrices.put('M', 15);
        skuPrices.put('N', 40);
        skuPrices.put('O', 10);
        skuPrices.put('P', 50);
        skuPrices.put('Q', 30);
        skuPrices.put('R', 50);
        skuPrices.put('S', 30);
        skuPrices.put('T', 20);
        skuPrices.put('U', 40);
        skuPrices.put('V', 50);
        skuPrices.put('W', 20);
        skuPrices.put('X', 90);
        skuPrices.put('Y', 10);
        skuPrices.put('Z', 50);

//counting all item
        Map<Character, Integer> skuCounts = new HashMap<>();

        for (char ch : skus.toCharArray()) {
            if (!skuPrices.containsKey(ch)) {
                return -1; // Invalid SKU
            }
            skuCounts.put(ch, skuCounts.getOrDefault(ch, 0) + 1);
        }

        int total = 0;

        applyFreeItem(skuCounts, 'E', 2, 'B');
        applyFreeItem(skuCounts, 'N', 3, 'M');
        applyFreeItem(skuCounts, 'R', 3, 'Q');


        total += computeSameItemFree(skuCounts, 'F', 2, skuPrices.get('F'));
        total += computeSameItemFree(skuCounts, 'U', 3, skuPrices.get('U'));

        total += applyMultiPrice(skuCounts, 'A', new int[][] {{5, 200}, {3, 130}}, skuPrices);

        total += applyMultiPrice(skuCounts, 'B', new int[][] {{2, 45}}, skuPrices);

        total += applyMultiPrice(skuCounts, 'H', new int[][] {{10, 80}, {5, 45}}, skuPrices);

        total += applyMultiPrice(skuCounts, 'K', new int[][] {{2, 120}}, skuPrices);

        total += applyMultiPrice(skuCounts, 'P', new int[][] {{5, 200}}, skuPrices);

        total += applyMultiPrice(skuCounts, 'Q', new int[][] {{3, 80}}, skuPrices);

        total += applyMultiPrice(skuCounts, 'V', new int[][] {{3, 130}, {2, 90}}, skuPrices);

        for (char ch : skuPrices.keySet()) {
            if (!skuCounts.containsKey(ch)) {
                continue;
            }
            total += skuCounts.get(ch) * skuPrices.get(ch);
        }


        return total;

    }

    private int applyMultiPrice(Map<Character, Integer> skuCounts, char item, int [][] offers, Map<Character, Integer> skuPrices) {
        if (!skuCounts.containsKey(item)) {
            return 0;
        }
        int quantity = skuCounts.get(item);
        int total = 0;

        for (int[] offer : offers) {
            int numberOfDeals = quantity / offer[0];
            total += numberOfDeals * offer[1];
            quantity %= offer[0];
        }

        
        total += quantity * skuPrices.get(item);
        skuCounts.remove(item);
        return total;
        
    }

    private void applyFreeItem(Map<Character, Integer> skuCounts, char trigger, int required, char freeItem) {
        if (!skuCounts.containsKey(trigger)){
            return;
        }
        int triggerQuantity = skuCounts.get(trigger);
        int freeCount = triggerQuantity / required;
        int currentFree = skuCounts.getOrDefault(freeItem, 0);
        skuCounts.put(freeItem, Math.max(0, currentFree - freeCount));
    }

    private int computeSameItemFree(Map<Character, Integer> skuCounts, char item, int required, int unitPrice) {
        if (!skuCounts.containsKey(item)) {
            return 0;
        }
        int quantity = skuCounts.get(item);
        int groups = quantity / (required + 1);
        int charged = quantity - groups;
        skuCounts.remove(item);
        return charged * unitPrice;
    }

}




