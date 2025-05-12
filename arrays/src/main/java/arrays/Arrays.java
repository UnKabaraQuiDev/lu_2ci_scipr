package arrays;

import java.util.ArrayList;
import java.util.List;

import lu.pcy113.pclib.PCUtils;

public class Arrays {

	public static void main(String[] args) {
		final int size = 10_000_000;
		List<Integer> list = new ArrayList<>(size);
		int[] numbers = new int[size];
		
		long nanoTime = PCUtils.nanoTime(() -> {
			for (int i = 0; i < size; i++) {
				numbers[i] = (int) (Math.random() * 5000);
			}
		});
		System.out.println("Time taken to fill array: " + nanoTime + " ns = " + ((double) nanoTime / 1_000_000) + " ms");
		
		nanoTime = PCUtils.nanoTime(() -> {
			for (int i = 0; i < size; i++) {
				list.add((int) (Math.random() * 5000));
			}
		});
		System.out.println("Time taken to fill list: " + nanoTime + " ns = " + ((double) nanoTime / 1_000_000) + " ms");
	}
	
}
