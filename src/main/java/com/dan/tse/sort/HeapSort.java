package com.dan.tse.sort;

import java.util.Arrays;

/**
 * 堆排序
 * @author Tse
 */
public class HeapSort {
	/**
	 * 下沉调整
	 * @param array
	 * @param parentIndex
	 * @param length
	 */
	public static void downAdjust(int[] array,int parentIndex,int length) {
		//存储父节点的值
		int temp=array[parentIndex];
		int childIndex=2*parentIndex+1;
		while(childIndex<length) {
			//如果有右孩子，且大于左孩子节点的值，则定位到右孩子
			if(childIndex+1<length&&array[childIndex+1]>array[childIndex]) {
				childIndex++;
			}
			if(temp>=array[childIndex]) {
				break;
			}
			//无须真正交换，单项赋值即可
			array[parentIndex]=array[childIndex];
			parentIndex=childIndex;
			childIndex=parentIndex*2+1;
		}
		array[parentIndex]=temp;
	}
	
	public static void heapSort(int[] array) {
		//1.把无须数组构建成二叉堆
		for(int i=(array.length-2)/2;i>=0;i--) {
			downAdjust(array, i, array.length);
		}
		//2.循环删除堆顶元素，移到集合尾部，调节堆产生新的堆顶
		for(int i=array.length-1;i>0;i--) {
			int temp=array[i];
			array[i]=array[0];
			array[0]=temp;
			//下沉调整最大堆
			downAdjust(array, 0, i);
		}
	}
	
	public static void main(String[] args) {
		int arr[]=new int[] {1,3,2,6,5,7,8,9,10};
		heapSort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
