package c.e.data_processing;

import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
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

public class Check_2samplesForIdentity {
	
	private Check_2samplesForIdentity() {}
	
	public void runTheMatrix(String fileOneName, String fileTwoName) {
		try {
			
 			File shoreFile1 = new File(fileOneName);
			
			// Get numID
			int numID1= Integer.parseInt(fileOneName.split("\\.")[1]);
			System.out.println("numID1: " + numID1);
			
			Scanner shoreScan1 = new Scanner(shoreFile1);
			
			BitSet[] mask1 = new BitSet[5];
			BitSet[] maskSNP1 = new BitSet[5];
			for (int c=0; c<5; c++) {
				mask1[c] = new BitSet();
				maskSNP1[c] = new BitSet();
			}
			// Build a mask of called sites
	    	while (shoreScan1.hasNextLine()) {
				String snp = shoreScan1.nextLine();
		    	String[] splitSnp = snp.split("\t");
		    	
		    	// Jump the header
		    	if (snp.charAt(0) != '#') {
		    		// Just see chromosomes
		    		if ( (splitSnp[0].charAt(0) == 'C') && (splitSnp[0].charAt(1) == 'h') && (splitSnp[0].charAt(2) == 'r') ) {
		    			int chr = Character.getNumericValue(splitSnp[0].charAt(3) - 1);
		    			int pos = Integer.parseInt(splitSnp[1]);
		    			
		    			// See if there is any reasonable call - from VcfCombined_to_SnpMatrix.java
		    			if ( (splitSnp[9].split(":")[0].charAt(0) != '.') && (splitSnp[9].split(":")[1].charAt(0) != '.') && (splitSnp[9].split(":")[2].charAt(0) != '.') ){
		    				
		    				// Check coverage and quality
		    				int cov=Integer.parseInt(splitSnp[9].split(":")[2]);
		    				int qual=Integer.parseInt(splitSnp[9].split(":")[1]);
		    				if ( (cov >=3) && (qual >= 25) ) {
		    					mask1[chr].set(pos);
		    					if (Integer.parseInt(splitSnp[9].split(":")[0]) != 0) {
		    						maskSNP1[chr].set(pos);
		    						
		    						// Just to check...
			    					if (Integer.parseInt(splitSnp[9].split(":")[0]) != 1) {
			    						System.out.println("Strange stuff at: " + snp);
			    					}
		    					}
		    				}
		    			}
		    		}
		    	}
	    	}
			int shoreCard1 = 0;
			int shoreMism1 = 0;
			for (int c=0; c<5; c++) {
				shoreCard1 = shoreCard1 + mask1[c].cardinality();
				shoreMism1 = shoreMism1 + maskSNP1[c].cardinality();
			}
			System.out.println("Shore calls - 1: " + shoreCard1 + " bases and " + shoreMism1 + " mismatches! :)");
			
			
		
			
			
			
			
			
			
			
			// If 2 vcf
		/*	
			File shoreFile2 = new File(fileTwoName);
			
			// Get numID
			int numID2= Integer.parseInt(fileTwoName.split("\\.")[1]);
			// System.out.println("numID1: " + numID1);
			
			Scanner shoreScan2 = new Scanner(shoreFile2);
			
			BitSet[] mask2 = new BitSet[5];
			BitSet[] maskSNP2 = new BitSet[5];
			for (int c=0; c<5; c++) {
				mask2[c] = new BitSet();
				maskSNP2[c] = new BitSet();
			}
			// Build a mask of called sites
	    	while (shoreScan2.hasNextLine()) {
				String snp = shoreScan2.nextLine();
		    	String[] splitSnp = snp.split("\t");
		    	
		    	// Jump the header
		    	if (snp.charAt(0) != '#') {
		    		// Just see chromosomes
		    		if ( (splitSnp[0].charAt(0) == 'C') && (splitSnp[0].charAt(1) == 'h') && (splitSnp[0].charAt(2) == 'r') ) {
		    			int chr = Character.getNumericValue(splitSnp[0].charAt(3) - 1);
		    			int pos = Integer.parseInt(splitSnp[1]);
		    			
		    			// See if there is any reasonable call - from VcfCombined_to_SnpMatrix.java
		    			if ( (splitSnp[9].split(":")[0].charAt(0) != '.') && (splitSnp[9].split(":")[1].charAt(0) != '.') && (splitSnp[9].split(":")[2].charAt(0) != '.') ){
		    				
		    				// Check coverage and quality
		    				int cov=Integer.parseInt(splitSnp[9].split(":")[2]);
		    				int qual=Integer.parseInt(splitSnp[9].split(":")[1]);
		    				if ( (cov >=3) && (qual >= 25) ) {
		    					mask2[chr].set(pos);
		    					if (Integer.parseInt(splitSnp[9].split(":")[0]) != 0) {
		    						maskSNP2[chr].set(pos);
		    						
		    						// Just to check...
			    					if (Integer.parseInt(splitSnp[9].split(":")[0]) != 1) {
			    						System.out.println("Strange stuff at: " + snp);
			    					}
		    					}
		    				}
		    			}
		    		}
		    	}
	    	}
			int shoreCard2 = 0;
			int shoreMism2 = 0;
			for (int c=0; c<5; c++) {
				shoreCard2 = shoreCard2 + mask2[c].cardinality();
				shoreMism2 = shoreMism2 + maskSNP2[c].cardinality();
			}
			System.out.println("Shore calls - 2: " + shoreCard2 + " bases and " + shoreMism2 + " mismatches! :)");
			
			
		*/	
			
			
			
			
			
			
			
			
			// Now get same sample in superVcf
			
			File superVcf = new File(fileTwoName);
			Scanner superVcfScan = new Scanner(superVcf);
			
			BitSet[] mask2 = new BitSet[5];
			BitSet[] maskSNP2 = new BitSet[5];
			for (int c=0; c<5; c++) {
				mask2[c] = new BitSet();
				maskSNP2[c] = new BitSet();
			}
			int numID2 = 0;
			
			
			// Build a mask of called sites
	    		
			int indexSample = 0;
			while (superVcfScan.hasNextLine()) {
				String snp = superVcfScan.nextLine();
		    	String[] splitSnp = snp.split("\t");
		    	
		    	// In the header
		    	// find the same numID1
		    	if (snp.charAt(0) == '#' && snp.charAt(1) != '#') {
		    		for (int run=9; run<splitSnp.length; run++) {
		    			if (Integer.parseInt(splitSnp[run]) == numID1) {
		    				numID2 = Integer.parseInt(splitSnp[run]);
		    				indexSample = run;
		    			}
		    		}
			System.out.println(numID1 + " = " + splitSnp[indexSample] + " index: " + indexSample);
		    	
			}
		    	
		    	// Jump the header
		    	if (snp.charAt(0) != '#') {
		    		// Just see chromosomes
		    		if ( (splitSnp[0].charAt(0) == 'C') && (splitSnp[0].charAt(1) == 'h') && (splitSnp[0].charAt(2) == 'r') ) {
		    			int chr = Character.getNumericValue(splitSnp[0].charAt(3) - 1);
		    			int pos = Integer.parseInt(splitSnp[1]);
		    			
		    			// See if there is any reasonable call - from VcfCombined_to_SnpMatrix.java
					if ( (splitSnp[indexSample].split(":")[0].charAt(0) != '.') && (splitSnp[indexSample].split(":")[1].charAt(0) != '.') && (splitSnp[indexSample].split(":")[2].charAt(0) != '.') ){
		    				
		    				// Check coverage and quality
		    				int cov=Integer.parseInt(splitSnp[indexSample].split(":")[2]);
		    				int qual=Integer.parseInt(splitSnp[indexSample].split(":")[1]);
		    				if ( (cov >=3) && (qual >= 25) ) {
		    					mask2[chr].set(pos);
		    					if (Integer.parseInt(splitSnp[indexSample].split(":")[0]) != 0) {
		    						maskSNP2[chr].set(pos);
		    						
		    						// Just to check...
			    					// if (Integer.parseInt(splitSnp[indexSample].split(":")[0]) != 1) {
			    					//		System.out.println("Strange stuff at: " + snp);
			    					//}
		    					}
		    				}
		    			}
		    		}
		    	}
	    	}
			int shoreCard2 = 0;
			int shoreMism2 = 0;
			for (int c=0; c<5; c++) {
				shoreCard2 = shoreCard2 + mask2[c].cardinality();
				shoreMism2 = shoreMism2 + maskSNP2[c].cardinality();
			}
			System.out.println("Shore calls - 2: " + shoreCard2 + " bases and " + shoreMism2 + " mismatches! :)");

			
			
			
			
			
			
			
			

			
			// Now compare them

			int intersectMaskCard = 0;
			int mismAndNotCard = 0;
			
			BitSet[] intersectMaskAnd = new BitSet[5];
			BitSet[] intersectMaskOr = new BitSet[5];
			BitSet[] mismOr = new BitSet[5];
			BitSet[] mismAnd = new BitSet[5];
			
			for (int c=0; c<5; c++) {

				// Cardinality
				intersectMaskOr[c] = new BitSet();
				intersectMaskOr[c].or(mask1[c]);
				intersectMaskOr[c].or(mask2[c]);
				
				intersectMaskAnd[c] = new BitSet();
				intersectMaskAnd[c].or(mask1[c]);
				intersectMaskAnd[c].and(mask2[c]);
				
				intersectMaskOr[c].andNot(intersectMaskAnd[c]);
				intersectMaskCard = intersectMaskCard + intersectMaskOr[c].cardinality();
				
				// Mismatches
				mismOr[c] = new BitSet();
				mismOr[c].or(maskSNP1[c]);
				mismOr[c].or(maskSNP2[c]);
				
				mismAnd[c] = new BitSet();
				mismAnd[c].or(maskSNP1[c]);
				mismAnd[c].and(maskSNP2[c]);
				
				mismOr[c].andNot(mismAnd[c]);
				
				mismAndNotCard = mismAndNotCard + mismOr[c].cardinality();
			}			
			
			System.out.println(numID1 + "\t" + numID2 + "\t" + 
					shoreCard1 + "\t" + shoreCard2 + "\t" + 
					shoreMism1 + "\t" + shoreMism2 + "\t" + 
					intersectMaskCard + "\t" + mismAndNotCard + "\n");
			
			// Write it out
			String results = "/global/lv70590/Andrea/ourDataProcessed/check/";

                        Writer writer = new FileWriter(results + numID1 + ".txt");
                        PrintWriter out = new PrintWriter(writer);
			out.print(numID1 + "\t" + numID2 + "\t" + shoreCard1 + "\t" + shoreCard2 + "\t" + shoreMism1 + "\t" + shoreMism2 + "\t" + intersectMaskCard + "\t" + mismAndNotCard + "\n");
			out.close();




			Writer writer2 = new FileWriter(results + numID1 + "_problems.txt");
                        PrintWriter out2 = new PrintWriter(writer2);
			for (int c=0; c<5; c++) {
				out2.print(c + "\t" + intersectMaskOr[c] + "\n");
			}
			for (int c=0; c<5; c++) {
                                out2.print(c + "\t" + mismOr[c] + "\n");
                        }
			out2.close();




			
			// Write out an easier mask for this sample - called bases
			/*
			Writer writer = new FileWriter(shoreFile1 + ".mask.txt");
			PrintWriter out = new PrintWriter(writer);
			
			for (int c=0; c<5; c++) {
				out.print(">Chr" + (c+1));
				out.print("\n");
				for (int bit =0; bit< shoreMask1[c].length(); bit++) {
					if (shoreMask1[c].get(bit)) {
						out.print("1");
					}
					if (!shoreMask1[c].get(bit)) {
						out.print("0");
					}
				}
				out.print("\n");
			}
			out.close();
			
			// Write out an easier mask for this sample - mismatches
			
			Writer writerM = new FileWriter(shoreFile1 + ".all.snp");
			PrintWriter outM = new PrintWriter(writerM);
			
			for (int c=0; c<5; c++) {
				outM.print(">Chr" + (c+1));
				outM.print("\n");
				for (int bit =0; bit< shoreMMism1[c].length(); bit++) {
					if (shoreMMism1[c].get(bit)) {
						outM.print("1");
					}
					if (!shoreMMism1[c].get(bit)) {
						outM.print("0");
					}
				}
				outM.print("\n");
			}
			outM.close();
			// Out easier mask
			*/
			// Got first sample
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Check_2samplesForIdentity check_2samplesForIdentity = new Check_2samplesForIdentity();
		check_2samplesForIdentity.runTheMatrix(args[0], args[1]);
	}
}
