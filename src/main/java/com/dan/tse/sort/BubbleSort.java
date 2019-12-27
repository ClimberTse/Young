package com.dan.tse.sort;

/**
 * 冒泡排序
 *   平均复杂度：O(n2), 稳定
 */
public class BubbleSort {
    public static void main(String[] args) {
        int a[] = new int[]{2,5,1,8,4,6,9,3,7};
        bubbleSort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void bubbleSort(int[] a){
        for (int i = 0; i <a.length-1; i++) {
            //是否有序
            boolean isSorted=true;
            for(int j=0;j <a.length-1-i;j++){
                if(a[j]>a[j+1]){
                    swap(a,j,j+1);
                    //有元素交换，所以不是有序
                    isSorted=false;
                }
            }
            if(isSorted)
                break;
        }
    }

    public static void swap(int a[],int i,int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
