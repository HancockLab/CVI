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

public class XpEhh_input {
	
	public XpEhh_input() {}
	
	public void setFileToSubSet(String matrixName, String c, String santoFile, String fogoFile){
		
		try {
			int chromosome = Integer.parseInt(c);

			// get clean samples from a file
			////
			//		SANTO
			////

			File sampleFile = new File(santoFile);
				//File sampleFile = new File("/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_19-4-18_noS1-8.txt");
	    			// "/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_onePerStand.txt");
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





			// get clean samples from a file
			////
			//		FOGO
			////
			sampleFile = new File(fogoFile); 
				// "/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_onePerStand.txt");			
				// sampleFile = new File("/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_11-7-18_noWtSwitches.txt");
			scannerS = new Scanner(sampleFile);
	    		fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			System.out.println("files: " + fil);
			String[] pop2 = new String[fil];
			
			fil= 0;
			scannerS = new Scanner(sampleFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				pop2[fil] = splitSnp[0];
				fil = fil + 1;
			}
			System.out.println(Arrays.toString(pop2));


			




			////
			//	Get indexes for both pops
			////

			
			int[] p1ind = new int[pop1.length];
			int[] p2ind = new int[pop2.length];
			int[] pAllind = new int[pop1.length + pop2.length];


			
			File matrix = new File(matrixName);
	    		Scanner scannerMatrix = new Scanner(matrix);
	    	
			String idsUnsplit = scannerMatrix.nextLine();
		    	String[] splitIDs = idsUnsplit.split("\t");
	    	
	    		// Indexes for pop1 & popAll
			int runAll = 0;
			for (int f =0; f<p1ind.length; f++) {
            			for (int s=2; s<splitIDs.length; s++) {
                			if (splitIDs[s].equals(pop1[f])) {
                    				p1ind[f] = s;
						pAllind[runAll] = s;
						runAll = runAll + 1;
                    			}
                		}
			}
			System.out.println(Arrays.toString(p1ind));

	    		// Indexes for pop2
			for (int f =0; f<p2ind.length; f++) {
        	        	for (int s=2; s<splitIDs.length; s++) {
                	    		if (splitIDs[s].equals(pop2[f])) {
					    p2ind[f] = s;
					    pAllind[runAll] = s;
					    runAll = runAll + 1;
			    		}
                		}
			}
			System.out.println(Arrays.toString(p2ind));
		











				
			String results = matrixName + "_xpEhh_chr" + chromosome;
			
			Writer writerHap1 = new FileWriter(results + "_hapSanto.txt");
			PrintWriter outHap1 = new PrintWriter(writerHap1);

			Writer writerHap2 = new FileWriter(results + "_hapFogo.txt");
			PrintWriter outHap2 = new PrintWriter(writerHap2);


			Writer writerPos = new FileWriter(results + "_pos.txt");
			PrintWriter outPos = new PrintWriter(writerPos);
			

	    		matrix = new File(matrixName);
	    		scannerMatrix = new Scanner(matrix);
			
	       		scannerMatrix.nextLine();
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				int chr = Integer.parseInt(splitSnp[0]);

				if (chr == chromosome) {

					int pos = Integer.parseInt(splitSnp[1]);
			       		char refBase = splitSnp[2].charAt(0);
					char anc2 = splitSnp[4].charAt(0);
					
			       		////
					//		Out missing, and non-segregating	 
					////
					Boolean segrega = false;
					Boolean n = false;
					char cviBase = splitSnp[pAllind[0]].charAt(0);
					
					// Check Ns and polymorphisms
					// 
					for (int f=0; f<pAllind.length; f++) {
			       			if (splitSnp[pAllind[f]].charAt(0) != 'N') {
							if (splitSnp[pAllind[f]].charAt(0) != cviBase) {
								segrega = true;
							}
			       			} else {
							n = true;
						}
		       			}
					if (!n && segrega) {
						// do something
						//
						// Get Alternative base
						char alt = '.';
						for (int f=0; f<pAllind.length; f++) {
							if (splitSnp[pAllind[f]].charAt(0) != anc2) {
								alt = splitSnp[pAllind[f]].charAt(0);
							}
						}
						// Positions
						outPos.print(chr + "\t" + chr + "_" + pos + "\t0\t" + pos + "\t" + anc2 + "\t" + alt + "\n");
						
						// Santo
						// 
						for (int f=0; f<p1ind.length; f++) {
							if (splitSnp[p1ind[f]].charAt(0) == anc2) {
								outHap1.print("0");
							} else {
								outHap1.print("1");
							}
							if (f < p1ind.length-1) {
								outHap1.print("\t");
							}
						}
						outHap1.print("\n");
						
						// Fogo
						// 
						for (int f=0; f<p2ind.length; f++) {
							if (splitSnp[p2ind[f]].charAt(0) == anc2) {
								outHap2.print("0");
							} else {
								outHap2.print("1");
							}
							if (f < p2ind.length-1) {
								outHap2.print("\t");
							}
						}
						outHap2.print("\n");
					}
				}
			}
			outHap1.close();
			outHap2.close();
			outPos.close();




		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	 	XpEhh_input xpEhh_input = new XpEhh_input();
		xpEhh_input.setFileToSubSet(args[0], args[1], args[2], args[3]);
	}
}
