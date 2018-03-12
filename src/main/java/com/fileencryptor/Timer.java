package com.fileencryptor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
/**
 * the timer for the program, once the pinging stops. 
 * It waits 3000 miliseconds before exiting the program and closing down the server
 * @author paradox
 *
 */
public class Timer {

	static long lastaccessed = System.currentTimeMillis();;

	@Scheduled(fixedRate = 1000)
	public void reverter() {
		long current = (System.currentTimeMillis());
		long lastTime = lastaccessed;
		double diff = ((current - lastTime)  / 1000);
		if (diff >= 3.0) {
			System.exit(0);

		}
	}



}
