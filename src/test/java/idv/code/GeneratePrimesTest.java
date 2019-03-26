package idv.code;

import org.junit.Test;

public class GeneratePrimesTest {

	@Test
	public void testGeneratePrimes(){
		int[] primes = GeneratePrimes.generatePrimes(1000);
		for(int prime : primes)
			System.out.print(prime + ",");
	}
}
