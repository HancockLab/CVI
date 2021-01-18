package c.e.data_processing;

import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.io.*;
import java.lang.reflect.Constructor;

public class Matrix_singleIsland {
	
	public Matrix_singleIsland() {}
	
	public void setFileToSubSet(String matrixName, String samples){
		
		try {
			System.out.println("src/c/e/data_processing/Matrix_singleIsland.java");
			
			
			File sampleFile = new File(samples);
			Scanner scannerS = new Scanner(sampleFile);
	    		int fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			System.out.println("files: " + fil);
			String[] pop1 = new String[fil];

			fil= 0;
			scannerS = new Scanner(sampleFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				pop1[fil] = splitSnp[0];
				fil = fil + 1;
			}
			System.out.println(Arrays.toString(pop1));



			int[] p1ind = new int[pop1.length];
			
			File matrix = new File(matrixName);
	    		Scanner scannerMatrix = new Scanner(matrix);
	    	
			String idsUnsplit = scannerMatrix.nextLine();
		    	String[] splitIDs = idsUnsplit.split("\t");
	    	
	    		// Indexes for pop1
			for (int f =0; f<p1ind.length; f++) {
            			for (int s=2; s<splitIDs.length; s++) {
                			if (splitIDs[s].equals(pop1[f])) {
                    				p1ind[f] = s;
                    			}
                		}
			}
			System.out.println(Arrays.toString(p1ind));

	

			///////
		    	////	 Begin with the big matrix
			///////

		    	matrix = new File(matrixName);
	    		scannerMatrix = new Scanner(matrix);
			
			// Open also the writing file
			
			String name = samples.split("/")[6].split("_")[0];
			System.out.println("Island: " + name);

			Writer writer = new FileWriter(matrixName + "_" + name + "_snps.txt");
			PrintWriter out = new PrintWriter(writer);
			
			// Print header
			//if (chromosome.charAt(0) == '1') {
				String head = scannerMatrix.nextLine();
				String[] splitHead = head.split("\t");
		       		for (int h=0; h<5; h++) {
					out.print(splitHead[h] + "\t");
				}
				for (int f =0; f<p1ind.length; f++) {
					out.print(splitHead[p1ind[f]]);
					if (f<p1ind.length-1) {
						out.print("\t");
					}
				}
				out.print("\n");
					
			//}
			
			// Go with contents
		       	int snps = 0;
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
			       	String[] splitSnp = snp.split("\t");
		       		
				Boolean n = false;

		 	      	// Get one base 
				char refBase = '.';
		  		for (int f =0; f<p1ind.length; f++) {
					if (splitSnp[p1ind[f]].charAt(0) != 'N') {
						refBase = splitSnp[p1ind[f]].charAt(0);
					} else {
						n = true;
					}
				}
				// if (!n) {
			       		// Select segregating sites
			       		boolean segrega = false;
			       		for (int f =0; f<p1ind.length; f++) {
			       			if ( (splitSnp[p1ind[f]].charAt(0) != refBase) && (splitSnp[p1ind[f]].charAt(0) != 'N') ) {
			       				segrega = true;
			       			}
			       		}
					
				      	// If it is segregating, output
			       		if (segrega) {
				 		for (int h=0; h<5; h++) {
							out.print(splitSnp[h] + "\t");
						}
						for (int f =0; f<p1ind.length; f++) {
							out.print(splitSnp[p1ind[f]]);
							if (f<p1ind.length-1) {
								out.print("\t");
							}
						}
						out.print("\n");
					     	snps = snps + 1;
	       				}	
				// }
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix_singleIsland matrix_singleIsland = new Matrix_singleIsland();
	        matrix_singleIsland.setFileToSubSet(args[0], args[1]);
	}
}
