/*
* @author Zitao He
* This is assignment 2 of course Divide and Conquer, Sorting and Searching, and Randomized Algorithms
* This class finds the inversion in a integer array through fast algorithm.
* The implementation of algorithm uses divide and conquer approach algorithm design.
* A example integer array text file can be found in /data folder
*
* */

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class InversionsFinder {

    //low is the first valid index of array, high is the last valid index of array
    public BigInteger countInversions(int[] array, int low, int high){
        int[] temp = new int[array.length];
        return countInversions(array, temp, low, high);
    }

    public BigInteger countInversions(int[] array, int[] temp, int low, int high){

        BigInteger count = new BigInteger("0");
        int mid = (high + low)/2;
        //base case: if there is only one element left in array, return 0 as there is no inversion exists
        if (high <= low){return new BigInteger("0");}

        //call recursions to split array
        count = count.add(countInversions(array, temp, low, mid));
        count = count.add(countInversions(array, temp, mid + 1, high));

        //count number of inversions using merge
        count = count.add(mergeCount(array, temp, low, mid, high));

        return count;
    }

    //merge  sub-arrays
    public BigInteger mergeCount (int[] array, int[] temp, int low, int mid, int high){
        BigInteger count = new BigInteger("0");

        int leftIdx = low;
        int rightIdx = mid + 1;
        int arrInx = low;

        while (leftIdx <= mid && rightIdx <= high){

            if (array[leftIdx] < array[rightIdx]){
                temp[arrInx++] = array[leftIdx++];
            }
            else {
                temp[arrInx++] = array[rightIdx++];
                count = count.add(new BigInteger(String.valueOf((mid + 1) - leftIdx)));
            }
        }

        //add remaining elements of temp array
        while (leftIdx <= mid){
            temp[arrInx++] = array[leftIdx++];
        }
        while (rightIdx <= high){
            temp[arrInx++] = array[rightIdx++];
        }

        //replace input array with sorted temp array
        for (int idx = low; idx <= high; idx ++){
            array[idx] = temp[idx];
        }
        return count;
    }
    public static void main(String[] args) {
        int[] intArray = new int [100000];
        try {
            Scanner scanner = new Scanner(new File("data/IntegerArray.txt"));
            int i = 0;
            while(scanner.hasNextInt())
            {
                intArray[i++] = scanner.nextInt();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //int[] intArray = {3, 6, 5, 4, 1, 2};
        int[] intArray_short = Arrays.copyOfRange(intArray, 0,intArray.length);
        InversionsFinder tester = new InversionsFinder();
        System.out.println("Number of inversions found:");
        System.out.println(tester.countInversions(intArray_short, 0, intArray_short.length-1).toString());
    }
}
