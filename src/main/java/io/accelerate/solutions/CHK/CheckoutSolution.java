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

//apply e offer
        if (skuCounts.containsKey('E')) {
            int freeBs = skuCounts.get('E') / 2;
            int currentB = skuCounts.getOrDefault('B', 0);
            skuCounts.put('B', Math.max(0, currentB - freeBs));
        }

//new f offer
        if (skuCounts.containsKey('F')) {
            int totalF = skuCounts.get('F');
            int chargeableF = totalF - (totalF / 3);
            total += chargeableF * skuPrices.get('F');
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

    private void applyFreeItem(Map<Character, Integer> skuCounts, char trigger, int required, char freeItem) {
        if (!skuCounts.containsKey(trigger)){
            return; // Trigger item not present
        }
        int triggerQuantity = skuCounts.get(trigger);
        int freeCount = triggerQuantity / required;
        int currentFree = skuCounts.getOrDefault(freeItem, 0);
        skuCounts.put(freeItem, Math.max(0, currentFree - freeCount));
    }

    private int computeSameItemFree(Map<Character, Integer> skuCounts, char item, int required, int unitPrice) {
        if (!skuCounts.containsKey(item)) {
            return 0; // Item not present
        }
        int quantity = skuCounts.get(item);
        int groups = quantity / (required + 1);
        int charged = quantity - groups;
        skuCounts.remove(item);
        return charged * unitPrice;
    }

}


