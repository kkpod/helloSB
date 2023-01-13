package com.pod.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pod.property.Car;

@RestController
public class HelloWorldController {
	public static int count;

	public static boolean degradePerformance;
	
	@RequestMapping("/status")
	public String status() {
		return "Applicaiton deployed on AWS beanstalk";
	}

	@RequestMapping("/perf")
	public Boolean performance(@RequestParam boolean performance) {
		degradePerformance = !performance;
		return degradePerformance;
	}
	
	@RequestMapping("/")
	public List<Car> hello() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<Car> cars = new ArrayList<Car>();

		if (degradePerformance) {
			String s = "TestTestTest";
			for (long j=0; j<=30000l; j++) {
				s = s + j;
			}
		}
		
		for (int i = 1; i <= 10; i++) {
			Car car = new Car();
			count = count + 1;
			car.setId(count);
			car.setName("NameOfCar" + count);
			cars.add(car);
		}

		stopWatch.stop();
		System.out.println("Elapsed Time in minutes: " + stopWatch.getLastTaskTimeMillis());

		return cars;
	}
}
