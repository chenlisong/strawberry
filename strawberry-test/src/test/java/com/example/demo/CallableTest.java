package com.example.demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class CallableTest {

	private Logger logger = LoggerFactory.getLogger(CallableTest.class);

	private static Random random = new Random();

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	@Test
	public void callableTest() throws Exception{

		for(int i=0; i<10; i++) {
			methodWithTimeout();
		}

		System.out.println("----------------------");

		for(int i=0; i<10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run(){
					try{
						methodWithTimeout();
					}catch (Exception e) {
						e.printStackTrace();
					}
 				}
			}).start();
		}
		while(true){
			Thread.sleep(3000);
		}
	}

	private static void methodWithTimeout() throws Exception{

		FutureTask<String> futureTask = new FutureTask<String>(() -> {
			Thread.sleep(random.nextInt(3000));
			return "ok";
		});

		executorService.submit(futureTask);
		String result = null;
		try {
			result = futureTask.get(1, TimeUnit.SECONDS);
		}catch (TimeoutException e) {
			result = "timeout";
		}
		System.out.println(String.format("the result is: %s", result));
	}

}

