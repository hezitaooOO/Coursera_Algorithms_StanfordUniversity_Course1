/*
* This is Assignment 3 of Course: Divide and Conquer, Sorting and Searching, and Randomized Algorithms
* by Standford University.
* The task of this assignment is to implement a quick sort using recursive method.
* @author: Zitao He
 * */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class QuickSorter {

    private int comparisonCounts = 0;
    public void quickSort(int[] array, int left, int right){
        if (left >= right){
            return;
        }
        comparisonCounts += (right - left);

        //use right element as pivot element by swapping the right value with left value before partition
//        int rightValue = array[right];
//        array[right] = array[left];
//        array[left] = rightValue;

        int rightValue = array[right];
        int leftValue = array[left];
        int middleValue = array[(right + left)/2];

        //when middle value is the median value of three
        if ((leftValue < middleValue && middleValue < rightValue) ||
                (rightValue < middleValue && middleValue < leftValue)) {
            //swap the median element with the first element
            array[(right + left) / 2] = array[left];
            array[left] = middleValue;
        }
        //when right value is the median value of three
        if ((middleValue < rightValue && rightValue < leftValue) ||
                (leftValue < rightValue && rightValue < middleValue)){
            //swap the median element with the first element
            array[right] = array[left];
            array[left] = rightValue;
        }

        int pivotIdx = partition(array, left, right);
        quickSort(array, left, pivotIdx - 1);
        quickSort(array, pivotIdx + 1, right);
    }

    //left and right are left index and right index of array
    private static int partition(int[] array, int left, int right){

        //int[] partitioned = new int[array.length];
        int pivot = array[left];

        //i is the interface of elements smaller than pivot and elements larger than pivots
        int i = left + 1;

        for (int j = left + 1; j <= right; j++){
            if (array[j] < pivot){
                //swap two elements in array
                int iValue = array[i];
                array[i] = array[j];
                array[j] = iValue;
                i ++;
            }
        }

        int leftValue = array[left];
        array[left] = array[i - 1];
        array[i - 1] = leftValue;
        return i - 1;
    }

    public int getComparisonCounts() {
        return comparisonCounts;
    }

    public static void main(String[] args){
        //int[] test = {5, 6, 4, 1, 2, 7, 3};
        //int[] test = {5, 6, 4, 1, 2, 7};
        int[] test = new int [10000];
        try {
            Scanner scanner = new Scanner(new File("data/QuickSort.txt"));
            int i = 0;
            while(scanner.hasNextInt())
            {
                test[i++] = scanner.nextInt();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        QuickSorter sorter = new QuickSorter();
        sorter.quickSort(test, 0, test.length - 1);
        System.out.println(Arrays.toString(test));
        System.out.println("Total number of comparisons: " + sorter.getComparisonCounts());
    }
}
