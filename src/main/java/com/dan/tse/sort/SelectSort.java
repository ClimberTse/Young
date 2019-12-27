package com.dan.tse.sort;

/**
 * 选择排序
 * 平均复杂度：O(n2), 不稳定
 */
public class SelectSort {
    public static void main(String[] args) {
        int a[] = new int[]{2,5,1,8,4,6,9,3,7};
        selectSort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void selectSort(int[] a){
        for (int i = 0; i < a.length-1; i++){
            int min = i;
            for (int j = i+1; j < a.length; j++) {
                if(a[j] < a[min]){
                    min = j;
                }
            }
            if(min!=i){
                swap(a,i,min);
            }
        }
    }

    public static void swap(int a[],int i,int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
