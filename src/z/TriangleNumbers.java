package z;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.util.Map.Entry;

public class TriangleNumbers {

	// http://projecteuler.net/index.php?section=problems&id=45
	public static void main(String[] args) {
		long numFactors = 1l, tri = 1l, next = 2l;
		while(numFactors<500) {
			tri = tri+next++;
			Vector<Long> pfs = ZMath.primeFactorization(tri);
			HashMap<Long,Integer> counts = new HashMap<Long,Integer>();
			for(Long pf : pfs) {
				//System.out.println(pf+" ");
				if(counts.containsKey(pf))
					counts.put(pf, counts.get(pf)+1);
				else
					counts.put(pf, 1);
			}
			numFactors = 1;
			for(Integer count : counts.values()) {
				//System.out.println(count);
				numFactors = numFactors*(count+1);
			}
			//System.out.println(tri+" factors:"+numFactors);
		}
		System.out.println(tri+" factors:"+numFactors);
	}

}
