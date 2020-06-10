package com.xxx.yyy.JavaTest;

import java.util.Arrays;

public class Sort {
	/**
	 * 交换
	 * @param a
	 * @param i
	 * @param j
	 */
//	public static void swap(int a[],int i,int j) {
//		if (i == j) {
//			return;
//		} else {
//			int temp = a[i];
//			a[i] = a[j];
//			a[j] = temp;
//		}
//	}
	
	
//	public static int partition(int a[],int low,int high) {
//		int pos = low;
//		int pivot = a[pos];
//		for (int i = low+1; i < high+1; i++) {
//			if (a[i] < pivot) {
//				pos++;
//				swap(a, pos, i);
//			}
//		}
//		swap(a, low, pos);
//		return pos;
//	}
	
	private static int partition(int[] arr, int low, int high) { // 选定枢轴为low所对应的值  
		int pivot = arr[low]; // 序列的第一条记录作为枢轴元素
		while (low < high) { // 从低位往高位遍历
			while (low < high && pivot <= arr[high]) { // 在高位找到比枢轴大的元素，符合要求，继续寻找
				high--;
			}
			arr[low] = arr[high]; // 将比枢轴元素小的记录交换到低位
			while (low < high && pivot >= arr[low]) { // 在低位找到比枢轴小的元素，符合要求，继续寻找
				low++;
			}
			arr[high] = arr[low]; // 将比枢轴记录大的元素交换到高位
		}
		arr[low] = pivot; // 将枢轴放在正确的排序位置
		return low; // 返回枢轴元素所在的位置
	}
	
	
	public static void quickSort(int a[],int low,int high) {
		if (low<high) {
			int pivot = partition(a, low, high);
			quickSort(a, low, pivot-1);
			quickSort(a, pivot+1, high);
		}
	}
	
	public static void main(String[] args) {
		
		int a[] = { 49, 13, 65, 97, 76, 13, 27, 49 };
		quickSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));
	}

}
