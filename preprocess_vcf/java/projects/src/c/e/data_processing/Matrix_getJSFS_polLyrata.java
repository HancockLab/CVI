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

public class Matrix_getJSFS_polLyrata {
	
	public Matrix_getJSFS_polLyrata() {}
	
	public void setFileToSubSet(String matrixName, String maskName, String results, String morFile, String lyrataFile){
		
		try {
			// get clean samples from a file
			////
			//		Moroccans to specterize and polarize
			////

			File sampleFile = new File(morFile);
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
			//		Get Lyrata IDs
			////
			sampleFile = new File(lyrataFile); 
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
			//		Get Canaries, just to check
			//// 

			sampleFile = new File("/srv/biodata/dep_coupland/grp_hancock/VCF/canarians_clean_plusCan0.txt");
			scannerS = new Scanner(sampleFile);
	    		fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			System.out.println("files: " + fil);
			String[] pop3 = new String[fil];
			
			fil= 0;
			scannerS = new Scanner(sampleFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				pop3[fil] = splitSnp[0];
				fil = fil + 1;
			}
			System.out.println(Arrays.toString(pop3));


			////
			//		Get also moroccans
			////	
			sampleFile = new File("/home/fulgione/moroccan_paper_clean_IDs_plink.txt");
			scannerS = new Scanner(sampleFile);
	    		fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			System.out.println("files: " + fil);
			String[] pop4 = new String[fil];
			
			fil= 0;
			scannerS = new Scanner(sampleFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				pop4[fil] = splitSnp[0];
				fil = fil + 1;
			}
			System.out.println(Arrays.toString(pop4));




			////
			//	Get indexes for both pops
			////

			
			int[] p1ind = new int[pop1.length];
			int[] p2ind = new int[pop2.length];
			int[] p3ind = new int[pop3.length];
			int[] p4ind = new int[pop4.length];


			
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

	    		// Indexes for pop2
			for (int f =0; f<p2ind.length; f++) {
        	        	for (int s=2; s<splitIDs.length; s++) {
                	    		if (splitIDs[s].equals(pop2[f])) {
					    p2ind[f] = s;
			    		}
                		}
			}
			System.out.println(Arrays.toString(p2ind));
		
			// Indexes for pop3
			for (int f =0; f<p3ind.length; f++) {
        	        	for (int s=2; s<splitIDs.length; s++) {
                	    		if (splitIDs[s].equals(pop3[f])) {
					    p3ind[f] = s;
			    		}
                		}
			}
			System.out.println(Arrays.toString(p3ind));
			
			// Indexes for pop4
			for (int f =0; f<p4ind.length; f++) {
        	        	for (int s=2; s<splitIDs.length; s++) {
                	    		if (splitIDs[s].equals(pop4[f])) {
					    p4ind[f] = s;
			    		}
                		}
			}
			System.out.println(Arrays.toString(p4ind));
			
			//






			////
			// 	Charge the mask for CpG and Centromeres
			////
			
			BitSet[] maskRepeat = new BitSet[5];
			for (int c=0; c<5; c++) {
				maskRepeat[c]= new BitSet();
			}
			File fileMask = new File("/srv/biodata/dep_coupland/grp_hancock/andrea/masks_cvi_func/repeatCentromCpg_out_mask.txt");
			Scanner scannerMask = new Scanner(fileMask);
		        int chrCVI=0;
		        String lineMask=null;
		        for (int m=0; m<10; m++) {
			       	lineMask = scannerMask.nextLine();
			       	if (m%2 == 1) {
			        	for (int i=0; i<lineMask.length(); i++) {
			        		if (lineMask.charAt(i) == ('1')) {
			        			maskRepeat[chrCVI].set(i);
			        		}
			        	}
			        	chrCVI=chrCVI+1;
			       	}
			}
			
			////
			//	Load the specific mask
			////

			BitSet[] mask = new BitSet[5];
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			fileMask = new File(maskName);
			scannerMask = new Scanner(fileMask);
		        chrCVI=0;
		        lineMask=null;
		        for (int m=0; m<10; m++) {
			       	lineMask = scannerMask.nextLine();
			       	if (m%2 == 1) {
			        	for (int i=0; i<lineMask.length(); i++) {
			        		if (lineMask.charAt(i) == ('1')) {
			        			mask[chrCVI].set(i);
			        		}
			        	}
			        	chrCVI=chrCVI+1;
			       	}
			}
			int cardin = 0;
			for (int c=0; c<5; c++) {
				// mask[c].and(maskRepeat[c]);
				cardin = cardin + mask[c].cardinality();
			}

			//
















				
			// String results = maskName + "_" + repl;
			


			////////
			////
			//	Go with contents
			//
			////
			////////

			// Here, to check pairw diff in Santo: 
			// Intermediate freq peak check
			//
			int[][] len = new int[p1ind.length][p1ind.length];
			int[][] diff = new int[p1ind.length][p1ind.length];			
			double[][] pairw = new double[p1ind.length][p1ind.length];
			for (int f=0; f<p1ind.length; f++) {
				for (int f2=0; f2<p1ind.length; f2++) {
					len[f][f2] = 0;
					diff[f][f2] = 0;
					pairw[f][f2] = 0.0;
				}
			}

			int actualSnps = 0;
			
			//// 
			//		For fogo/santo
			////

			// For whole islands
			// int maxNS = (int) Math.round(2*pop1.length/3);
			// int maxNF = (int) Math.round(2*pop2.length/3);
			
			// 		For stands
			// int maxNS = (int) Math.round(9*pop1.length/10);
			// int maxNF = (int) Math.round(9*pop2.length/10);
			////

			//		For S1-S8
			// int maxNS = (int) Math.round(2*pop1.length/3);
			// int maxNF = (int) Math.round(7*pop2.length/7);
			////
			
			//		For Slim: no missing data
			int maxNS = (int) Math.round(pop1.length);
			// int maxNF = (int) Math.round(pop2.length);

			int[] sfs = new int[maxNS + 1];
			
			int[] canAncestor = {0, 0};
			int[] noCanAnc = {0, 0};

			int[] morAncestor = {0, 0};
			int[] noMorAnc = {0, 0};
			
			int[] canMorAncestor = {0, 0};
			int[] noCanMorAnc = {0, 0};

			////
			//	Open the big matrix
			////
			
	    		matrix = new File(matrixName);
	    		scannerMatrix = new Scanner(matrix);
			
	       		scannerMatrix.nextLine();
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       	
		       		char refBase = splitSnp[2].charAt(0);
		       		
				////
				//		Find a good ancestral base from 24 lyrata samples
				////
				char anc = '.';
				boolean lyrN = true;
				char[] lyrBases = null;
				for (int l=0; l<p2ind.length; l++) {
					if (splitSnp[p2ind[l]].charAt(0) != 'N') {
						if (lyrN) {
							// First non-N base foundin lyrata: 
							// record it
							lyrBases = new char[1];
							lyrBases[0] = splitSnp[p2ind[l]].charAt(0);
						} else {
							// More than the first: 
							// check if it is a segregating site in lyrata
							if (lyrBases[0] != splitSnp[p2ind[l]].charAt(0)) {
								char[] lyrMem = lyrBases;
								lyrBases = new char[lyrMem.length + 1];
								for (int m=0; m<lyrMem.length; m++) {
									lyrBases[m] = lyrMem[m];
								}
								lyrBases[lyrBases.length-1] = splitSnp[p2ind[l]].charAt(0);
							}
						}
						lyrN = false;
					}
				}
				// Use it if it is not missing in all lyrata,
				// and be suspicious if it is segregating in lyrata
				//
				// just tocheck
      			
				if (!lyrN) {
					for (int l=0; l<lyrBases.length; l++) {
						if (lyrBases[l] != 'A' && lyrBases[l] != 'T' && lyrBases[l] != 'C' && lyrBases[l] != 'G') {
							System.out.println("Lyrata problem at base: " + Integer.parseInt(splitSnp[0]) + " " + Integer.parseInt(splitSnp[1]) + " " + lyrBases[l]);
						}
					}
				}

				if (!lyrN && lyrBases.length == 1) {
					// Now do the spectrum
					//
					anc = lyrBases[0];
					// Just to check
				


		       			////
					//	 Count non Ns in Santos
					////
					Boolean mismatch = false;
					char cviBase = '.';
		       			int nonNS = 0;
					for (int f=0; f<p1ind.length; f++) {
		       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       					nonNS = nonNS + 1;
							if (splitSnp[p1ind[f]].charAt(0) != refBase) {
								cviBase = splitSnp[p1ind[f]].charAt(0);
								mismatch = true;
							}
		       				}
		       			}

	       			
		       			int chr = Integer.parseInt(splitSnp[0]);
		       			int pos = Integer.parseInt(splitSnp[1]);
				
	       			
				////
				//	 If it is not too many Ns, use this SNP
				////

		       		if (nonNS >= maxNS && mask[chr-1].get(pos)) {
		       			
					actualSnps = actualSnps + 1;
				// if (mismatch) {

					//////////
					/////
					//		For Santos!
					/////
					//////////
					
		       			
		       			////
					//	Build indexes of nonN accessions
					////

			       		int[] focIndNs = new int[nonNS];
			       		nonNS = 0;
			       		for (int f=0; f<p1ind.length; f++) {
			       			if (splitSnp[p1ind[f]].charAt(0) != 'N') {
			       				focIndNs[nonNS] = p1ind[f];
			       				nonNS = nonNS + 1;
			       			}
			       		}
			       		
					////
					// 	Sorteggia i Santos non-N without replacement
			       		// 	Fisher-Yates algorithm
			       		////
					
			       		int[] focIndRandS = new int[maxNS];
			       		Random randomGenerator = new Random();
					for (int boot=0; boot<focIndRandS.length; boot++) {
						int r = randomGenerator.nextInt(focIndNs.length-boot);
						int l = focIndNs[r];
						focIndRandS[focIndRandS.length-1-boot] = l;
						focIndNs[r] = focIndNs[focIndNs.length-1-boot];
						focIndNs[focIndNs.length-1-boot] = l;
					}
			       		
					////
					//	Get entries for the spectra
					////

					int derS = 0;
					for (int f=0; f<focIndRandS.length; f++) {
		       				if (splitSnp[focIndRandS[f]].charAt(0) != anc) {
		       					derS = derS + 1;
			       			}
			       		}
		       			sfs[derS] = sfs[derS] + 1;
		       		}
				}
			}

			Writer writerSnpNum = new FileWriter(results + "_snps_nonN_inTheMask.txt");
			PrintWriter outSnpNum = new PrintWriter(writerSnpNum);
			outSnpNum.println(actualSnps);
			outSnpNum.close();

			Writer writer = new FileWriter(results + "_sfs_toLyr.txt");
			PrintWriter out = new PrintWriter(writer);
			
			for (int s=0; s<sfs.length; s++) {
				out.print(sfs[s] + "\t");
	       		}
			out.print("\n");
			out.close();
		
	



		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix_getJSFS_polLyrata matrix_getJSFS_polLyrata = new Matrix_getJSFS_polLyrata();
		matrix_getJSFS_polLyrata.setFileToSubSet(args[0], args[1], args[2], args[3], args[4]);
	}
}
