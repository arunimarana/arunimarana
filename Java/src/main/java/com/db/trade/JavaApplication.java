package com.db.trade;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaApplication.class, args);

		// TODO Auto-generated method stub

		System.out.println("work");

		String s = "adaaddarsc";

		Map<String, Long> map = Arrays.stream(s.split(""))
				.collect(Collectors.groupingBy(i -> i, Collectors.counting()));

		System.out.println(map);

		Integer[] arr = { 2, 4, 6, 4, 99, 5 };

		Map<Integer, Long> map1 = Arrays.stream(arr).collect(Collectors.groupingBy(j -> j, Collectors.counting()));
		System.out.println(map1);

		String cities = "Delhi,Mumbai,Patna,Delhi";

		// count the number of cities
		long count = Arrays.stream(cities.split(",")).distinct().count();
		System.out.println("Cities:: " + count);
		
		//count the distict cities
		
		Map<String, Long> countD = Arrays.stream(cities.split(",")).collect(Collectors.groupingBy(k ->k, Collectors.counting()));
		System.out.println(countD);

	}

}
