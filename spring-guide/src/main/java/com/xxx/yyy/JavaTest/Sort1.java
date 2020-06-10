package com.xxx.yyy.JavaTest;

public class Sort1 {

	public static int[] intersection(int[] array1, int[] array2) {
//		int length = array1.length > array2.length ? array2.length : array1.length;
		int[] temp = new int[10];
		int i = 0, j = 0, index =0;
		while (i < array1.length && j < array2.length) {
			// 数组1第一个数和数组2第一个数比较，若小于，再用第二个数和数组2的第一个比较...
			while (array1[i] < array2[j])
				i++;
			while (array1[i] > array2[j])
				j++;
			while (array1[i] == array2[j]) {
				temp[index] = array1[i]; 
				index ++;
				// 两个数组最后一个数字相同时 a[M-1] == a[N-1] == -858993460（vs2013环境下）
				if (i == array1.length - 1 && j == array2.length - 1) {
					return temp;
				} else {
					i++;
					j++;
				}
			}
		}
		return temp;
	}
	
	public static void main(String[] args) {
		int array1[] = { 1, 3, 4, 7, 11 };
	    int array2[] = { 2, 3, 5, 6, 7, 8, 11 };
	    int[] temp = intersection(array1, array2);
	    for (int i : temp) {
			System.out.println(i);
		}
	}

}
