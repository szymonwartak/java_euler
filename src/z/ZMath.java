package z;

import java.math.BigInteger;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

/**
 * Utils for solving computational/math problems 
 * @author szymon
 *
 */
public class ZMath {
	public static Vector<Long> primes;

	/**
	 * Method for generating primes using modulo and a list of previously discovered primes. 
	 * Checks if current (odd) number is divisible by all previous primes up to sqrt of the current number. 
	 * Has better space performance than sieve but poorer runtime (for smaller max?).
	 * @param max
	 * @return The list of primes.
	 */
	public static Vector<Long> listPrimes(double max) {
		Vector<Long> pfs = new Vector<Long>();
		pfs.add(new Long(2));
		double previousOut = 0, nextOut=0, outIncr = 0.01;
		System.out.format("Generating primes < %1.0f . . . ",max);
		for(long i1=3;i1<max;i1=i1+2) {
			if(previousOut<i1/max && i1/max>nextOut) {
				nextOut = nextOut+outIncr;
				System.out.format("\n%4.0f%% ",i1/max/outIncr);
				System.out.print(new Date());
			}
			boolean isPF = true;
			for(int i2=0;i2<pfs.size();i2++) {
				if(Math.sqrt(i1)<pfs.get(i2)) break; // no need to check for primes > sqrt(i1)
				if(i1%pfs.get(i2)==0)
					isPF = false;
			}
			if(isPF) pfs.add(i1);
		}
		System.out.println("done.");
		return pfs;
	}
	/**
	 * Method for generating primes using a sieve. Better runtime than list method but has larger space order.
	 * @param max
	 * @return The list of primes.
	 */
	public static Vector<Long> sievePrimes(double max, double outIncr) {
		boolean[] allPrimes = new boolean[new Double(max).intValue()];
		double previousOut = 0, nextOut=0;//, outIncr = 0.1;
		if(outIncr>0) System.out.format("Generating primes < %1.0f . . . ",max);
		for(int i1=0;i1<allPrimes.length;i1++) allPrimes[i1] = true; // initialize
		for(int i1=3;i1<max;i1=i1+2) {
			if(outIncr>0 && previousOut<i1/max && i1/max>nextOut) {
				nextOut = nextOut+outIncr;
				System.out.format("%4.0f%% ",100*i1/max);
			}
			for(int i2=i1*2;i2<max;i2=i2+i1) {
				allPrimes[i2] = false;
			}
		}
		Vector<Long> thePrimes = new Vector<Long>(new Double(max).intValue());
		thePrimes.add(2l);
		for(int i1=3;i1<max;i1=i1+2) {
			if(allPrimes[i1]) {
				thePrimes.add(new Integer(i1).longValue());
			}
		}
		if(outIncr>0) System.out.println("done.");
		return thePrimes;
	}
	public static Vector<Long> primeFactorization(long num) {
		Vector<Long> pfs = new Vector<Long>();
		long div = 2; int currentPrime = 0;
		while(num>1 && Math.sqrt(num+1)>div) { // don't continue if the remainder (num) is prime
			while(num%div==0) { // get prime factors
				pfs.add(div);
				num = num/div;
			}
			if(primes!=null && ++currentPrime<primes.size())
				div = primes.get(currentPrime); // get next prime
			else if(primes==null || primes.lastElement()<=Math.sqrt(num))
				primes = ZMath.sievePrimes(Math.max(100,num/2),0.1); // generate more for potential future needs
			else
				break;
		}
		if(num>1) {
			pfs.add(num);
		}
		return pfs;
	}
	public static Vector<Long> properDivisors(long num) {
		Vector<Long> pfs = ZMath.primeFactorization(num);
		Vector<Long> divisors = new Vector<Long>();
		for(int i1=0;i1<Math.pow(2, pfs.size());i1++) {
			String combo = Integer.toBinaryString(i1);
			while(combo.length()<pfs.size()) combo = "0"+combo;
			long prod = 1;
			for(int i2=0;i2<combo.length();i2++)
				prod = prod*(combo.substring(i2, i2+1).equals("1") ? pfs.get(i2) : 1l);
			if(!divisors.contains(prod) && prod!=num)
				divisors.add(prod);
		}
		return divisors;
	}
	/**
	 * @param num
	 * @return the number of proper divisors of num
	 */
	public static long numProperDivisors(long num) { // divisors < num (including 1)
		long properDivisors = 1l;
		Vector<Long> pfs = ZMath.primeFactorization(num);
		HashMap<Long,Integer> counts = new HashMap<Long,Integer>();
		for(Long pf : pfs) {
			if(counts.containsKey(pf))
				counts.put(pf, counts.get(pf)+1);
			else
				counts.put(pf, 1);
		}
		properDivisors = 1;
		for(Integer count : counts.values()) {
			properDivisors = properDivisors*(count+1);
		}
		return (properDivisors-1l);
	}
	/**
	 * @param num
	 * @return num factorial
	 */
	public static BigInteger fact(int num) {
		BigInteger a = new BigInteger(new Integer(num).toString());
		if(num==0) return new BigInteger("1");
		for(int i1=num-1;i1>1;i1--)
			a=a.multiply(new BigInteger(new Integer(i1).toString()));
		return a;		
	}
	// a choose b
	public static BigInteger combination(int a, int b) {
		BigInteger choose = new BigInteger("1");
		BigInteger afact = fact(a);
		BigInteger bfact = fact(b);
		BigInteger cfact = fact(a-b);
		afact=afact.divide(bfact);afact=afact.divide(cfact);
		return afact;
	}
	public static BigInteger permutation(int a, int b) {
		BigInteger choose = new BigInteger("1");
		BigInteger afact = fact(a);
		BigInteger bfact = fact(b);
		afact=afact.divide(bfact);
		return afact;
	}
	public static void main(String[] args) {
		/*Vector<Long> primes = sievePrimes(2000000,0.01);//generatePrimesLessThan(1000000l);
		long sum = 0l;
		for(Long p: primes) {
			sum = sum+p;
			//System.out.print("\n"+p+" ");
		}*/
		Vector<Long> divs = listPrimes(100000);
		/*for(Long div:divs)
			System.out.println(div);*/
	}
}
