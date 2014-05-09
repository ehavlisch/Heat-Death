package main;

import java.util.Random;

public class RNG {

	public static int random(int max) {
		Random generator = new Random();
		if(max <= 0) max = 1;
		return Math.abs(generator.nextInt(max));
	}
	
	public static int random(int low, int max) {
		if(low > max) max = low;
		if(low == max) return low;
		int test = low;
		do {
			test = random(max);
		} while(test < low);
		return test;
	}
}
