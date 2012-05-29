package z;

/**
 * Addition and multiplication of big numbers (without using the BigInteger class)
 * @author szymon
 *
 */
public class BigAddMultiply {

	public static final int maxAdd = 18; // maximum length of string to multiply (sqrt max length long)
	public static final int maxMult = 9; // maximum length of string to multiply (sqrt max length long)
	//public static final int maxMult = 18; // maximum length of string to multiply by 2 (max length long/2)
	
	public static void main(String[] args) {		
		String num="1";
		for(int i1=2;i1<=100;i1++) {
			num = multiply(num,new Integer(i1).toString(),0);
			System.out.println(num);
		}

		int sum=0;
		for(int i1=0;i1<num.length();i1++)
			sum = sum+new Integer(num.substring(i1, i1+1)).intValue();
		System.out.println(sum);
		
	}
	public static String multiply(String first, String second, int totalShift) {
		if(first.equals("")||second.equals("")) return "0";
		String end1 = first.substring(Math.max(0,first.length()-maxMult));
		String end2 = second.substring(Math.max(0,second.length()-maxMult));
		String start1 = first.substring(0, Math.max(0,first.length()-maxMult));
		String start2 = second.substring(0, Math.max(0,second.length()-maxMult));
		//System.out.println(start1+" "+end1+"*"+start2+" "+end2+" <<"+totalShift);
		String sum = new Long(new Long(end1).longValue()*new Long(end2).longValue()).toString()+padding(totalShift);
		String p1 = multiply(start1,end2,++totalShift);
		String p2 = multiply(end1,start2,totalShift);
		String p3 = multiply(start1,start2,++totalShift);
		return add(sum,
				add(p1,
						add(p2,
								p3)));
	}
	public static String padding(int totalShift) {
		String padding = "";
		for(int i1=0;i1<totalShift;i1++) 
			for(int i2=0;i2<maxMult;i2++) 
				padding = padding+"0";
		return padding;
	}
	public static String add(String first, String second) {
		if(first.equals("")&&second.equals("")) return "0";
		if(first.equals("")) return second;
		if(second.equals("")) return first;
		String sum = "";
		long carry = 0;
		while(!first.equals("") && !second.equals("")) {
			String end1 = first.substring(Math.max(0,first.length()-maxAdd));
			String end2 = second.substring(Math.max(0,second.length()-maxAdd));
			first = first.substring(0, Math.max(0,first.length()-maxAdd));
			second = second.substring(0, Math.max(0,second.length()-maxAdd));

			long subsum = new Long(end1).longValue()+new Long(end2).longValue()+carry;
			if(subsum>=1000000000000000000l) { carry = 1l; subsum = subsum-1000000000000000000l; }
			else carry = 0l;
			if(!first.equals("") || !second.equals("")) sum = String.format("%0"+maxAdd+"d", subsum)+sum;
			else sum = new Long(subsum).toString()+sum;
		}
		String front = carry==1l ? add(first+second,new Long(carry).toString()) : first+second;
		sum = (front.equals("0")?"":front)+sum; // concatenate on the last bit	
		return sum;
	}
}
