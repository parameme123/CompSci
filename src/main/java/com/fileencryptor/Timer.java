package com.fileencryptor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Timer {

	static long lastaccessed = System.currentTimeMillis();;

	@Scheduled(fixedRate = 1000)
	public void reverter() {
		long current = (System.currentTimeMillis());
		System.out.println("current time "+current);
		long lastTime = lastaccessed;
		System.out.println("lastTime  "+lastTime);
		double diff = ((current - lastTime)  / 1000);
		System.out.println("diff : "+diff);
		if (diff >= 3.0) {
			System.exit(0);

		}
	}



}
