package com.db.trade;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ch.qos.logback.core.net.SyslogOutputStream;

public class Usaa {
	
	public static void main(String[] args) {
	
	String[] s = {"Delhi", "Bengaluru", "Chennai", "Bangalore", "Noida"};
	
	//count the occurance of cities starting with letter c
	
	
	//Display city staring with c
	
	
	
	//Map<String, Long> map =Arrays.stream(s).collect(Collectors.groupingBy(i ->i.stratsWith('A'), Collectors.counting()));
	
	//Map<Object, List<String>> map1 =Arrays.stream(s).collect(Collectors.groupingBy(i ->i, Collectors.toList()));
	
	
	//map1.forEach(id, city)->{System.out.println(id + city);};
	
	
	Map<String, Long> map = Arrays.stream(s).collect(Collectors.groupingBy(i ->i, Collectors.counting()));
	
	Long cities = Arrays.stream(s).filter(i -> i.startsWith("C")).count();
	
	List<String> city = Arrays.stream(s).filter(i -> i.startsWith("C")).collect(Collectors.toList());
	
     System.out.println(city);
     
     System.out.println(map);
      
	
	
	}
	

}
