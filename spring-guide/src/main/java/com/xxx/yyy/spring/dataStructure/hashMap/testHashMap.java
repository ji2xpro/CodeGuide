package com.xxx.yyy.spring.dataStructure.hashMap;

import java.util.*;
import java.util.Map.Entry;

/**
 * 定义一个Student类，属性：name 姓名，classNumber 班级，score 成绩
 * 现在将若干student对象放入List，请统计出每个班级的总分和平均分，分别打印出来
 * 
 * @author jimmy
 *
 */
public class testHashMap {
	public static void main(String[] args) {
		List<Student> list = new ArrayList<Student>();
		exam(list);
		// 统计
		Map<String, ClassRoom> rooms = new HashMap<String, ClassRoom>();
		count(rooms, list);
		// 打印
		printScore(rooms);
	}

	/**
	 * 打印总分与平均分
	 * 
	 * @param rooms
	 */
	public static void printScore(Map<String, ClassRoom> rooms) {
		Set<Entry<String, ClassRoom>> entrySet = rooms.entrySet();
		Iterator<Entry<String, ClassRoom>> iterable = entrySet.iterator();
		while (iterable.hasNext()) {
			Entry<String, ClassRoom> entry = iterable.next();
			ClassRoom room = entry.getValue();
			double total = room.getTotal();
			double avg = total / room.getStus().size();
			System.out.println("班号为：" + room.getNo() + "，总分：" + total + "，平均分：" + avg);
		}
	}

	/**
	 * 统计分数
	 * 
	 * @param rooms
	 * @param list
	 */
	public static void count(Map<String, ClassRoom> rooms, List<Student> list) {
		for (Student student : list) {
			String no = student.getNo();
			double score = student.getScore();
			// 根据班级编号查看Map是否存在该班级，分拣思路
			ClassRoom room = rooms.get(no);
			if (null == room) {
				room = new ClassRoom(no);
				rooms.put(no, room);
			}
			// 存储
			room.setTotal(room.getTotal() + score);
			room.getStus().add(student); // 加入学生
		}
	}

	/**
	 * 添加学生信息到list
	 * 
	 * @param list
	 */
	public static void exam(List<Student> list) {
		list.add(new Student("测试", "1001", 80));
		list.add(new Student("测试1", "1001", 70));
		list.add(new Student("测试1", "1002", 50));
		list.add(new Student("测试2", "1002", 80));
		list.add(new Student("测试", "1003", 80));
	}
}
