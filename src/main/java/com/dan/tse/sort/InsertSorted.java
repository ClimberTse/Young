package com.dan.tse.sort;

/**
 * 插入排序
 * 平均复杂度：O(n2), 稳定
 */
public class InsertSorted {
    public static void main(String[] args) {
        int a[] = new int[]{2,5,1,8,4,6,9,3,7};
        insertSorted(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
    public static void insertSorted(int[] a){
        for (int i=1;i<a.length;i++){
            int get=a[i];
            int j=i-1;
            while(j>=0 && a[j]>get){
                a[j+1]=a[j];
                j--;
            }
            a[j+1]=get;
        }
    }
}
