package subsetsum;

import cs1c.SongEntry;
import java.util.ConcurrentModificationException;
import java.util.*;

public class SubsetSum {
    public static Set<Set<Integer>> powerSet(Set<Integer> integerSet) {
        Set<Set<Integer>> pset = new HashSet<Set<Integer>>();

        if (integerSet.isEmpty()) {
            pset.add(new HashSet<Integer>());
            return pset;
        }
        List<Integer> integerList = new ArrayList<Integer>(integerSet);
        Integer top = integerList.get(0);
        Set<Integer> restSets = new HashSet<Integer>(integerList.subList(1, integerList.size()));
        for (Set<Integer> set : powerSet(restSets)) {
            Set<Integer> newSet = new HashSet<Integer>();
            newSet.add(top);
            newSet.addAll(set);
            pset.add(newSet);
            pset.add(set);
        }
        return pset;
    }

    public static <T extends Iterable<E>, E> void printSet(T s) {
        System.out.print('[');
        for (Object m : s) {
            System.out.print(m + ", ");
        }
        System.out.print("]\n");
    }
    public static void printPowerSet(Set<Set<Integer>> ps) {
        for (Set<Integer> newSet : ps) {
            printSet(newSet);
        }
    }
    /*
    implement finding subset of groceries that is closest to meeting the user's budget.
    In this part, you only need to keep track of the price of each item,
    and not the name of the item you are buying.
    */
    public static ArrayList<Double> findSubset(ArrayList<Double> price, double budget) {
        ArrayList<Double> a = new ArrayList<Double>();
        HashSet<Integer> indices = new HashSet<Integer>();
        //calculate price length
        int size = price.size();
        // early return when sum of all the items are less than budget
        Double priceSum = 0.0;
        for (Double newPrice : price) {
            priceSum = priceSum + newPrice;
        }
        if (budget>priceSum){
            return price;
        }

        // Initialize the hash set with indices of price list, we only need to
        // create the power set of indices and retrieve the elements from there
        // when needed.
        for (int i = 0; i < size; i++) {
            indices.add(i);
        }
        // Create all possible subsets of price list.
        Set<Set<Integer>> ps = powerSet(indices);
        //printPowerSet(ps);
        //System.out.println("\nTotal Elements in powerset: " + ps.size());
        Set<Set<Integer>> validSets = new HashSet<Set<Integer>>();
        // Loop over all sets of power set.
        for (Set<Integer> newSet : ps) {
            Double sum = 0.0;
            //added all the elements in a set
            for (Integer m : newSet) {
                sum = sum + price.get(m);
            }

            // keep the subsets which have sum < budget
            if (sum < budget) {
                validSets.add(newSet);
            }
            else if (sum == budget){
                validSets.clear();
                validSets.add(newSet);
                break;
            }
        }
        //printPowerSet(validSets);
        //System.out.println("\nValid Elements in powerset: " + validSets.size());

        // Loop over all the valid subsets and find the one with maximum sum
        Set<Integer> maxSet = null;
        Double max = 0.0;
        for (Set<Integer> newSet : validSets) {
            Double sum = 0.0;
            //added all the elements in a set
            for (Integer m : newSet) {
                sum = sum + price.get(m);
            }
            // Discard all the subsets which have sum > budget
            if (sum > max) {
                max = sum;
                maxSet = newSet;
            }
        }
        ArrayList<Double> maxSumSet = new ArrayList<>();
        for (Integer s : maxSet) {
            maxSumSet.add(price.get(s));
        }
        //System.out.println("MaxSum: " + max);
        //printSet(maxSet);
        //printSet(maxSumSet);
        //created array

        //convert array to arrayList
        return maxSumSet;
    }


    public static ArrayList<SongEntry> findSubsetOfSongs(ArrayList<SongEntry> songList, double duration) {
        ArrayList<SongEntry> h = new ArrayList<SongEntry>();
        return h;
    }

}