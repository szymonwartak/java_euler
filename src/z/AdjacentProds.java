package z;

import java.util.Arrays;
import java.util.Vector;

public class AdjacentProds {

	public static void main(String[] args) {
		int[][] grid = new int[][]{{8,02,22,97,38,15,00,40,00,75,04,05,07,78,52,12,50,77,91,8},
			{49,49,99,40,17,81,18,57,60,87,17,40,98,43,69,48,04,56,62,00},
			{81,49,31,73,55,79,14,29,93,71,40,67,53,88,30,03,49,13,36,65},
			{52,70,95,23,04,60,11,42,69,24,68,56,01,32,56,71,37,02,36,91},
			{22,31,16,71,51,67,63,89,41,92,36,54,22,40,40,28,66,33,13,80},
			{24,47,32,60,99,03,45,02,44,75,33,53,78,36,84,20,35,17,12,50},
			{32,98,81,28,64,23,67,10,26,38,40,67,59,54,70,66,18,38,64,70},
			{67,26,20,68,02,62,12,20,95,63,94,39,63,8,40,91,66,49,94,21},
			{24,55,58,05,66,73,99,26,97,17,78,78,96,83,14,88,34,89,63,72},
			{21,36,23,9,75,00,76,44,20,45,35,14,00,61,33,97,34,31,33,95},
			{78,17,53,28,22,75,31,67,15,94,03,80,04,62,16,14,9,53,56,92},
			{16,39,05,42,96,35,31,47,55,58,88,24,00,17,54,24,36,29,85,57},
			{86,56,00,48,35,71,89,07,05,44,44,37,44,60,21,58,51,54,17,58},
			{19,80,81,68,05,94,47,69,28,73,92,13,86,52,17,77,04,89,55,40},
			{04,52,8,83,97,35,99,16,07,97,57,32,16,26,26,79,33,27,98,66},
			{88,36,68,87,57,62,20,72,03,46,33,67,46,55,12,32,63,93,53,69},
			{04,42,16,73,38,25,39,11,24,94,72,18,8,46,29,32,40,62,76,36},
			{20,69,36,41,72,30,23,88,34,62,99,69,82,67,59,85,74,04,36,16},
			{20,73,35,29,78,31,90,01,74,31,49,71,48,86,81,16,23,57,05,54},
			{01,70,54,71,83,51,54,69,16,92,33,48,61,43,52,01,89,19,67,48}};
		// first number
		Vector<AdjacentProds> one = new Vector<AdjacentProds>();
		for(int i1=0;i1<grid.length;i1++) {
			for(int i2=0;i2<grid[0].length;i2++) {
				if(grid[i1][i2]>0)
					one.add(new AdjacentProds(grid[i1][i2], i1, i2));
			}
		}
		// second number
		Vector<AdjacentProds> two = AdjacentProds.iterate(one,grid,100);
		// third number
		Vector<AdjacentProds> three = AdjacentProds.iterate(two,grid,100);
		// fourth number
		Vector<AdjacentProds> four = AdjacentProds.iterate(three,grid,100);
		
		// 80338698
		outputProds(four);
		int max = 0; int[] numbers = new int[four.size()];
		for(int i1=0;i1<four.size();i1++) {
			if(four.elementAt(i1).getProd()>max) max = four.elementAt(i1).getProd();
			numbers[i1] = four.elementAt(i1).getProd();
		}
		Arrays.sort(numbers);
		for(int i1=0;i1<numbers.length;i1++)
			System.out.print(numbers[i1]+" ");
	}
	public static Vector<AdjacentProds> iterate(Vector<AdjacentProds> currentSet, int[][] grid, int maxsize) {
		Vector<AdjacentProds> nextSet = new Vector<AdjacentProds>();
		for(int i1=0;i1<currentSet.size();i1++) { // loop through current set of Prods
			AdjacentProds currentProd = currentSet.elementAt(i1);
			int row = currentProd.getLastRow(), col = currentProd.getLastCol();
			for(int i2=-1;i2<=1;i2++) { // row offset
				for(int i3=-1;i3<=1;i3++) { // col offset
					if(i2==0 && i3==0) continue;
					if(row+i2<0 || row+i2>=grid.length || col+i3<0 || col+i3>=grid[0].length) continue;
					AdjacentProds newProd = currentProd.addPoint(grid[row+i2][col+i3], row+i2, col+i3, i2, i3);
					if(newProd!=null) { // new point is already in the set
						// keep track only of N largest products to trim the tree in a greedy manner
						if(nextSet.size()<maxsize) nextSet.add(newProd);
						else {
							for(int i4=0;i4<nextSet.size();i4++) {
								if(newProd.getProd()>nextSet.elementAt(i4).getProd()) {
									nextSet.set(i4, newProd);
									break;
								}
							}
						}
					}
				}
			}			
		}
		return nextSet;
	}
	public static void outputProds(Vector<AdjacentProds> prods) {
		for(int i1=0;i1<prods.size();i1++) {
			AdjacentProds currentProd = prods.elementAt(i1);
			System.out.println(currentProd.getRows()[0]+","+currentProd.getCols()[0]+" "
			        +currentProd.getRows()[1]+","+currentProd.getCols()[1]+" "
			        +currentProd.getRows()[2]+","+currentProd.getCols()[2]+" "
			        +currentProd.getRows()[3]+","+currentProd.getCols()[3]+" "
					+currentProd.getProd());
		}
	}
	
	int[] rows, cols;
	private int prod, vals, deltarow, deltacol;
	public AdjacentProds(int prod, int vals, int deltarow, int deltacol, int[] rows, int[] cols) { // internal constructor used when cloning Prods 
		this.rows = new int[4]; this.cols = new int[4];
		for(int i1=0;i1<vals;i1++) {
			this.rows[i1] = rows[i1];
			this.cols[i1] = cols[i1];
		}
		this.prod = prod; this.vals = vals; this.deltarow = deltarow; this.deltacol = deltacol;
	}
	public AdjacentProds(int firstVal, int row, int col) { // initial constructor (used externally)
		rows = new int[]{row,0,0,0}; cols = new int[]{col,0,0,0}; prod = firstVal; vals = 1;
	}
	public AdjacentProds addPoint(int value, int row, int col, int deltarow, int deltacol) {
		if(!(this.deltarow==0 && this.deltacol==0)&&(deltarow!=this.deltarow || deltacol!=this.deltacol)) return null;
		for(int i1=0;i1<vals;i1++)
			if(rows[i1]==row && cols[i1]==col) return null;
		return this.duplicate(deltarow, deltacol).add(value, row, col);
	}
	public AdjacentProds add(int value, int row, int col) {
		rows[vals] = row; cols[vals++] = col; prod = prod*value; return this;
	}
	private AdjacentProds duplicate(int deltarow, int deltacol) {
		return new AdjacentProds(prod, vals, deltarow, deltacol, rows, cols);
	}
	public int getLastRow() { return rows[vals-1]; }
	public int getLastCol() { return cols[vals-1]; }
	public int[] getRows() { return rows; }
	public int[] getCols() { return cols; }
	public int getProd() { return prod; }
}
