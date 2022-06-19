package com.xxx.yyy.spring.javaTest;

import java.util.Arrays;

public class Sort {
    /**
     * 交换
     *
     * @param arr
     * @param i
     * @param j
     */
//    public static void swap(int[] arr, int i, int j) {
//        if (i == j) {
//            return;
//        } else {
//            int temp = arr[i];
//            arr[i] = arr[j];
//            arr[j] = temp;
//        }
//    }
//
//    /**
//     * 返回调整后的基准数的位置
//     *
//     * @param arr
//     * @param low
//     * @param high
//     * @return
//     */
//    public static int partition(int[] arr, int low, int high) {
//        int pos = low;
//        int pivot = arr[pos];
//        for (int i = low + 1; i < high + 1; i++) {
//            if (arr[i] < pivot) {
//                pos++;
//                swap(arr, pos, i);
//            }
//        }
//        swap(arr, low, pos);
//        return pos;
//    }

    /**
     * 选定枢轴为low所对应的位置
     *
     * @param arr
     * @param low
     * @param high
     * @return
     */
    private static int partition(int[] arr, int low, int high) {
        // 将序列的第一条记录作为枢轴元素
        int pivot = arr[low];
        // 从低位往高位遍历
        while (low < high) {
            // 在高位找到大于等于枢轴的元素, 符合要求, 向前挪动high指针, 继续寻找
            while (low < high && pivot <= arr[high]) {
                high--;
            }
            // 如果高位比枢轴元素小时, 需交换到低位
            arr[low] = arr[high];
            // 在低位找到小于等于枢轴的元素, 符合要求, 向前挪动low指针, 继续寻找
            while (low < high && pivot >= arr[low]) {
                low++;
            }
            // 如果低位比枢轴记录大时, 需交换到高位
            arr[high] = arr[low];
        }
        // 跳出循环时low和high相等,此时的low或high就是tmp的正确索引位置
        // 将枢轴放在正确的排序位置
        arr[low] = pivot;
        // 返回枢轴元素所在的位置
        return low;
    }

    /**
     * 快速排序
     * @param arr
     * @param low
     * @param high
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 找寻基准数据的正确索引
            int pivot = partition(arr, low, high);
            // 进行迭代对index之前和之后的数组进行相同的操作使整个数组变成有序
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args) {
        int[] arr = {49, 13, 65, 97, 76, 13, 27, 49};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
