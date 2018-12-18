package org.zixing.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTypeCon {
	public  List<Integer> listConInt(List<String> list){
		List<Integer> i= new LinkedList<Integer>();
		for (String str : list) {
			i.add(Integer.parseInt(str));
		}
		return i;
	}
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
//		int s =listConInt(list).get(0);
//		System.out.println(s);
	}
}
