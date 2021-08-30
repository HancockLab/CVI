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

public class Matrix_getJSFS_polLyrata_3pops {
	
	public Matrix_getJSFS_polLyrata_3pops() {}
	
	public void setFileToSubSet(String matrixName, String maskName, String results, String pop1File, String pop2File, String pop3File, String outgroupFile){
		
		try {
			// get clean samples from a file
			////
			//		pop1 to specterize and polarize
			////

			File sampleFile = new File(pop1File);
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


			////
			//		pop2 to specterize and polarize
			////

			sampleFile = new File(pop2File); 
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
			//		pop3 to specterize and polarize
			////

			sampleFile = new File(pop3File); 
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
			//		Get outgroup IDs
			////
			
			sampleFile = new File(outgroupFile); 
			scannerS = new Scanner(sampleFile);
	    		fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			System.out.println("files: " + fil);
			String[] popOut = new String[fil];
			
			fil= 0;
			scannerS = new Scanner(sampleFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				popOut[fil] = splitSnp[0];
				fil = fil + 1;
			}
			System.out.println(Arrays.toString(popOut));


			




			////
			//	Get indexes for both pops
			////

			
			int[] p1ind = new int[pop1.length];
			int[] p2ind = new int[pop2.length];
			int[] p3ind = new int[pop3.length];
			int[] pOutInd = new int[popOut.length];

			
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
			System.out.println("p1ind\t" + Arrays.toString(p1ind));

	    		// Indexes for pop2
			for (int f =0; f<p2ind.length; f++) {
        	        	for (int s=2; s<splitIDs.length; s++) {
                	    		if (splitIDs[s].equals(pop2[f])) {
					    p2ind[f] = s;
			    		}
                		}
			}
			System.out.println("p2ind\t" + Arrays.toString(p2ind));
		
			// Indexes for pop3
			for (int f =0; f<p3ind.length; f++) {
        	        	for (int s=2; s<splitIDs.length; s++) {
                	    		if (splitIDs[s].equals(pop3[f])) {
					    p3ind[f] = s;
			    		}
                		}
			}
			System.out.println("p3ind\t" + Arrays.toString(p3ind));
			
			// Indexes for pop Outgroup
			for (int f =0; f<pOutInd.length; f++) {
        	        	for (int s=2; s<splitIDs.length; s++) {
                	    		if (splitIDs[s].equals(popOut[f])) {
					    pOutInd[f] = s;
			    		}
                		}
			}
			System.out.println("pOutInd\t" + Arrays.toString(pOutInd));
			
			//






			////
			// 	Charge the mask for CpG and Centromeres
			////
			
			BitSet[] maskRepeat = new BitSet[5];
			for (int c=0; c<5; c++) {
				maskRepeat[c]= new BitSet();
			}
			File fileMask = new File("/srv/biodata/irg/grp_hancock/andrea/masks_cvi_func/repeatCentromCpg_out_mask.txt");
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
				mask[c].and(maskRepeat[c]);
				cardin = cardin + mask[c].cardinality();
			}

			//








			int actualSnps = 0;







				
			// String results = maskName + "_" + repl;
			


			////////
			////
			//	Go with contents
			//
			////
			////////

			
			//// 
			//		For fogo/santo
			////

			// For Santo and Morocco
			int maxN1 = (int) Math.round(9*pop1.length/10);
			int maxN2 = (int) Math.round(9*pop2.length/10);
			int maxN3 = (int) Math.round(9*pop3.length/10);


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
			// int maxNS = (int) Math.round(pop1.length);
			// int maxNF = (int) Math.round(pop2.length);

			int[] sfs1 = new int[maxN1 + 1];
			int[] sfs2 = new int[41];
			int[] sfs3 = new int[41];
			int[][][] jsfs = new int[maxN1 + 1][41][41];
			
			
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
				//		Find a good ancestral base from outgroup samples
				////
				char anc = '.';
				boolean lyrN = true;
				char[] lyrBases = null;
				for (int l=0; l<pOutInd.length; l++) {
					if (splitSnp[pOutInd[l]].charAt(0) != 'N') {
						if (lyrN) {
							// First non-N base foundin lyrata: 
							// record it
							lyrBases = new char[1];
							lyrBases[0] = splitSnp[pOutInd[l]].charAt(0);
						} else {
							// More than the first: 
							// check if it is a segregating site in lyrata
							if (lyrBases[0] != splitSnp[pOutInd[l]].charAt(0)) {
								char[] lyrMem = lyrBases;
								lyrBases = new char[lyrMem.length + 1];
								for (int m=0; m<lyrMem.length; m++) {
									lyrBases[m] = lyrMem[m];
								}
								lyrBases[lyrBases.length-1] = splitSnp[pOutInd[l]].charAt(0);
							}
						}
						lyrN = false;
					}
				}
				// Use it if it is not missing in all outgroups,
				// and be suspicious if it is segregating in the outgroup
				//
				// just tocheck
      			
				if (!lyrN) {
					for (int l=0; l<lyrBases.length; l++) {
						if (lyrBases[l] != 'A' && lyrBases[l] != 'T' && lyrBases[l] != 'C' && lyrBases[l] != 'G') {
							System.out.println("Outgroup problem at base: " + Integer.parseInt(splitSnp[0]) + " " + Integer.parseInt(splitSnp[1]) + " " + lyrBases[l]);
						}
					}
				}

				if (!lyrN && lyrBases.length == 1) {
					// Now do the spectrum
					//
					anc = lyrBases[0];
					// Just to check
				

		       			////
					//	 Count non Ns in pop1
					////
					Boolean mismatch = false;
					char cviBase = '.';
		       			int nonN1 = 0;
					for (int f=0; f<p1ind.length; f++) {
		       				if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       					nonN1 = nonN1 + 1;
							if (splitSnp[p1ind[f]].charAt(0) != refBase) {
								cviBase = splitSnp[p1ind[f]].charAt(0);
								mismatch = true;
							}
		       				}
		       			}



		       			////
					//	 Count non Ns in pop2
					////
					Boolean mismatch2 = false;
					char cviBase2 = '.';
		       			int nonN2 = 0;
					for (int f=0; f<p2ind.length; f++) {
		       				// System.out.println(splitSnp[p2ind[f]].charAt(0));
						if (splitSnp[p2ind[f]].charAt(0) != 'N') {
		       					nonN2 = nonN2 + 1;
							if (splitSnp[p2ind[f]].charAt(0) != refBase) {
								cviBase2 = splitSnp[p2ind[f]].charAt(0);
								mismatch2 = true;
							}
		       				}
		       			}

		       			////
					//	 Count non Ns in pop3
					////
					Boolean mismatch3 = false;
					char cviBase3 = '.';
		       			int nonN3 = 0;
					for (int f=0; f<p3ind.length; f++) {
		       				if (splitSnp[p3ind[f]].charAt(0) != 'N') {
		       					nonN3 = nonN3 + 1;
							if (splitSnp[p3ind[f]].charAt(0) != refBase) {
								cviBase = splitSnp[p3ind[f]].charAt(0);
								mismatch = true;
							}
		       				}
		       			}



	       			
		       			String chr = splitSnp[0];
		       			int pos = Integer.parseInt(splitSnp[1]);
				
	       			
				////
				//	 If it is not too many Ns, use this SNP
				////
				// System.out.println(nonNS+ "\t" + nonN2);
		       		if (nonN1 >= maxN1 && nonN2 >= maxN2 && nonN3 >= maxN3 && mask[Integer.parseInt(chr)-1].get(pos)) {
		       			
					actualSnps = actualSnps + 1;
					
					//////////
					/////
					//		For pop1!
					/////
					//////////
					
		       			
		       			////
					//	Build indexes of nonN accessions
					////

			       		int[] focIndNs = new int[nonN1];
			       		nonN1 = 0;
			       		for (int f=0; f<p1ind.length; f++) {
			       			if (splitSnp[p1ind[f]].charAt(0) != 'N') {
			       				focIndNs[nonN1] = p1ind[f];
			       				nonN1 = nonN1 + 1;
			       			}
			       		}
			       		
					////
					// 	Sorteggia from pop1 non-N without replacement
			       		// 	Fisher-Yates algorithm
			       		////
					
			       		int[] focIndRandS = new int[maxN1];
			       		Random randomGenerator = new Random();
					// System.out.println(focIndNs.length);

					for (int boot=0; boot<focIndRandS.length; boot++) {
						int r = randomGenerator.nextInt(focIndNs.length-boot);
						int l = focIndNs[r];
						focIndRandS[focIndRandS.length-1-boot] = l;
						focIndNs[r] = focIndNs[focIndNs.length-1-boot];
						focIndNs[focIndNs.length-1-boot] = l;
					}
			       	









					//////////
					/////
					//		For pop2!
					/////
					//////////
					
		       			
		       			////
					//	Build indexes of nonN accessions
					////

			       		int[] focIndNf = new int[nonN2];
			       		nonN2 = 0;
			       		for (int f=0; f<p2ind.length; f++) {
			       			if (splitSnp[p2ind[f]].charAt(0) != 'N') {
			       				focIndNf[nonN2] = p2ind[f];
			       				nonN2 = nonN2 + 1;
			       			}
			       		}
			       		
					////
					// 	Sorteggia i pop2 non-N without replacement
			       		// 	Fisher-Yates algorithm
			       		////
					
			       		int[] focIndRandF = new int[40];
			       		Random randomGenerator2 = new Random();
					for (int boot=0; boot<focIndRandF.length; boot++) {
						int r = randomGenerator2.nextInt(focIndNf.length-boot);
						int l = focIndNf[r];
						focIndRandF[focIndRandF.length-1-boot] = l;
						focIndNf[r] = focIndNf[focIndNf.length-1-boot];
						focIndNf[focIndNf.length-1-boot] = l;
					}
			       		



					//////////
					/////
					//		For pop3!
					/////
					//////////
					
		       			
		       			////
					//	Build indexes of nonN accessions
					////

			       		int[] focIndN3 = new int[nonN3];
			       		nonN3 = 0;
			       		for (int f=0; f<p3ind.length; f++) {
			       			if (splitSnp[p3ind[f]].charAt(0) != 'N') {
			       				focIndN3[nonN3] = p3ind[f];
			       				nonN3 = nonN3 + 1;
			       			}
			       		}
			       		
					////
					// 	Sorteggia i Santos non-N without replacement
			       		// 	Fisher-Yates algorithm
			       		////
					
			       		int[] focIndRand3 = new int[40];
			       		Random randomGenerator3 = new Random();
					for (int boot=0; boot<focIndRand3.length; boot++) {
						int r = randomGenerator3.nextInt(focIndN3.length-boot);
						int l = focIndN3[r];
						focIndRand3[focIndRand3.length-1-boot] = l;
						focIndN3[r] = focIndN3[focIndN3.length-1-boot];
						focIndN3[focIndN3.length-1-boot] = l;
					}
			       		







					////
					//	Get entries for the spectra
					////

					int der1 = 0;
					for (int f=0; f<focIndRandS.length; f++) {
		       				if (splitSnp[focIndRandS[f]].charAt(0) != anc) {
		       					der1 = der1 + 1;
			       			}
			       		}
					int der2 = 0;
					for (int f=0; f<focIndRandF.length; f++) {
		       				if (splitSnp[focIndRandF[f]].charAt(0) != anc) {
		       					der2 = der2 + 1;
			       			}
			       		}
					int der3 = 0;
					for (int f=0; f<focIndRand3.length; f++) {
		       				if (splitSnp[focIndRand3[f]].charAt(0) != anc) {
		       					der3 = der3 + 1;
			       			}
			       		}
					
					sfs1[der1] = sfs1[der1] = + 1;
					sfs2[der2] = sfs2[der2] = + 1;
		       			sfs3[der3] = sfs3[der3] = + 1;
					jsfs[der1][der2][der3] = jsfs[der1][der2][der3] + 1;
		       		}
				}
			}

			Writer writerSnpNum = new FileWriter(results + "_snps_nonN_inTheMask.txt");
			PrintWriter outSnpNum = new PrintWriter(writerSnpNum);
			outSnpNum.println(actualSnps);
			outSnpNum.close();

			Writer writer = new FileWriter(results + "_sfs1.txt");
			PrintWriter out = new PrintWriter(writer);
			
			for (int s=0; s<sfs1.length; s++) {
				out.print(sfs1[s] + "\t");
	       		}
			out.print("\n");
			out.close();


			writer = new FileWriter(results + "_sfs2.txt");
			out = new PrintWriter(writer);
			
			for (int s=0; s<sfs2.length; s++) {
				out.print(sfs2[s] + "\t");
	       		}
			out.print("\n");
			out.close();
		


			writer = new FileWriter(results + "_sfs3.txt");
			out = new PrintWriter(writer);
			
			for (int s=0; s<sfs3.length; s++) {
				out.print(sfs3[s] + "\t");
	       		}
			out.print("\n");
			out.close();
		

			


			Writer writerD1 = new FileWriter(results + "_dadiJsfs.txt");
			PrintWriter outD1 = new PrintWriter(writerD1);
			
			outD1.println((maxN1 + 1) + " " + (41) +  " " + (41) + " unfolded");
			
			for (int p1 =0; p1<(maxN1 + 1); p1++) {
				for (int p2 =0; p2<(41); p2++) {
					for (int p3 =0; p3<(41); p3++) {
						outD1.print(jsfs[p1][p2][p3] + " ");
					}
				}
			}
			outD1.print("\n");
			
			for (int p1 =0; p1<(maxN1 + 1); p1++) {
				for (int p2 =0; p2<(41); p2++) {
					for (int p3 =0; p3<(41); p3++) {
						outD1.print("0 ");
					}
				}
			}
			outD1.print("\n");
			outD1.close();
	
			
	


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix_getJSFS_polLyrata_3pops matrix_getJSFS_polLyrata_3pops = new Matrix_getJSFS_polLyrata_3pops();
		matrix_getJSFS_polLyrata_3pops.setFileToSubSet(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
	}
}
