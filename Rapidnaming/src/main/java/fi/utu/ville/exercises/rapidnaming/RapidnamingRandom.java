package fi.utu.ville.exercises.rapidnaming;

import java.util.Random;

public class RapidnamingRandom extends Random {

	private final String[] pairs = new String[] { "gabrielle", "patel", "brian", "robinson", "eduardo", "haugen", "hoen", "johansen", "alejandro", "macdonald", "angel", "karlsson", "yahir", "gustavsson", "haiden", "svensson", "emily", "stewart", "corinne", "davis", "ryann", "davis", "yurem", "jackson", "kelly", "gustavsson", "eileen", "walker", "katelyn", "martin", "israel", "carlsson", "quinn", "hansson", "makena", "smith", "danielle", "watson", "leland", "harris", "gunner", "karlsen", "jamar", "olsson", "lara", "martin", "ann", "andersson", "remington", "andersson", "rene", "carlsson", "elvis", "olsen", "solomon", "jaydan", "jackson", "bernard", "nilsen" };
	private int numero = pairs.length;

	public String[] getSequence() {
		for (int i = 0; i < numero; i++) {
			int random = i + (int) (Math.random() * (numero - i));
			String randomElement = pairs[random];
			pairs[random] = pairs[i];
			pairs[i] = randomElement;
		}
		return pairs;
	}

	public int[] getRandom() {
		int[] nums = new int[numero];
		for (int i = 0; i < nums.length; i++) {
			int random = i + (int) (Math.random() * (numero - i));
			int randomElement = nums[random];
			nums[random] = nums[i];
			nums[i] = randomElement;
		}
		return nums;
	}

	public int getRandom(int n) {
		int[] nums = new int[numero];
		for (int i = 0; i < nums.length; i++) {
			int random = i + (int) (Math.random() * (numero - i));
			int randomElement = nums[random];
			nums[random] = nums[i];
			nums[i] = randomElement;
		}
		return nums[n];
	}

	public int getRandomNum() {
		return super.nextInt(numero);
	}

}
