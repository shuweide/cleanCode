package idv.code;

import org.junit.Test;

public class PrimeGeneratorTest {
	@Test
	public void testPrimGenerator(){
		int[] primes = PrimeGenerator.generatePrimes(100);
		for(int prime : primes)
			System.out.print(prime + ",");
	}
}
