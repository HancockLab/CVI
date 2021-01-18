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

public class Matrix_getJSFS_morPol {
	
	public Matrix_getJSFS_morPol() {}
	
	public void setFileToSubSet(String matrixName, String maskName, String results, String santoFile, String fogoFile){
		
		try {
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
			//		Get Canaries, just to check
			//// 

			sampleFile = new File("/srv/biodata/irg/grp_hancock/VCF/canarians_clean_plusCan0.txt");
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

			// Check FLC
			if (mask[4].get(3179333)) {
				System.out.println("FLC in the 1st mask");
			} else {
				System.out.println("FLC not in the 1st mask");
			}

			int cardin = 0;
			for (int c=0; c<5; c++) {
				mask[c].and(maskRepeat[c]);
				cardin = cardin + mask[c].cardinality();
			}
			// Check FLC
			if (mask[4].get(3179333)) {
				System.out.println("FLC in the 2st mask");
			} else {
				System.out.println("FLC not in the 2st mask");
			}
			
			// Check variants
			if (mask[2].get(21567088)) {
				System.out.println("var1 in the mask");
			}
			if (maskRepeat[2].get(21567088)) {
				System.out.println("var1 in the maskRepeat");
			}


			if (mask[2].get(21569086)) {
				System.out.println("var2 in the mask");
			}
			if (maskRepeat[2].get(21569086)) {
				System.out.println("var2 in the maskRepeat");
			}



			//
















				
			// String results = maskName + "_" + repl;
			
			Writer writerPca = new FileWriter(results + "_midFreqPca.txt");
			PrintWriter outPca = new PrintWriter(writerPca);
			
			Writer writerFfSs = new FileWriter(results + "_FogoFixedSantoSegreg.txt");
			PrintWriter outFfSs = new PrintWriter(writerFfSs);

			Writer writerSnp = new FileWriter(results + "_sfsSnpsOneByOne.txt");
			PrintWriter outSnp = new PrintWriter(writerSnp);
			outSnp.print("chr\tpos\tderSanto\tderFogo\tSantoFreq\tFogoFreq\n");

			Writer writerFfix = new FileWriter(results + "_fogoFixedSantoOut.txt");
			PrintWriter outFfix = new PrintWriter(writerFfix);
			outFfix.print("chr\tpos\tderSanto\tderFogo\tSantoFreq\tFogoFreq\n");
		
			Writer writerSfix = new FileWriter(results + "_santoFixedSantoOut.txt");
			PrintWriter outSfix = new PrintWriter(writerSfix);
			outSfix.print("chr\tpos\tderSanto\tderFogo\tSantoFreq\tFogoFreq\n");


			Writer writerShared = new FileWriter(results + "_sharedHighLowFreq.txt");
			PrintWriter outShare = new PrintWriter(writerShared);
			outShare.print("chr\tpos\tderSanto\tderFogo\tSantoFreq\tFogoFreq\n");



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
			// int maxNS = (int) Math.round(pop1.length);
			// int maxNF = (int) Math.round(pop2.length);

			//		For sub40: 40 samples in each
			int maxNS = 40;
			int maxNF = 40;

			int[] sfsSMl = new int[maxNS + 1];
			int[] sfsSX = new int[maxNS + 1];
			int[] sfsSMach = new int[maxNS + 1];
			
			int[] sfsFMl = new int[maxNF + 1];
			int[] sfsFX = new int[maxNF + 1];
			int[] sfsFMach = new int[maxNF + 1];
		
		
			int[][] jsfsML = new int[maxNS + 1][maxNF + 1];
			int[][] jsfsX = new int[maxNS + 1][maxNF + 1];
			int[][] jsfsMach = new int[maxNS + 1][maxNF + 1];
			
			int[] canAncestor = {0, 0};
			int[] noCanAnc = {0, 0};

			int[] morAncestor = {0, 0};
			int[] noMorAnc = {0, 0};
			
			int[] canMorAncestor = {0, 0};
			int[] noCanMorAnc = {0, 0};

			////
			//	Open the big matrix
			////
			
			int[] disagree = new int[3];

	    		matrix = new File(matrixName);
	    		scannerMatrix = new Scanner(matrix);
			
	       		scannerMatrix.nextLine();
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       	
		       		char refBase = splitSnp[2].charAt(0);
		       		
				char anc1 = splitSnp[3].charAt(0);
				char anc2 = splitSnp[4].charAt(0);
				char anc3 = splitSnp[5].charAt(0);
				
				// Trick for new Cvi-0 ref
				
				// char anc1 = splitSnp[2].charAt(0);
				// char anc2 = splitSnp[2].charAt(0);
				// char anc3 = splitSnp[2].charAt(0);

				// Check ancestral states
				//
				// if ( (anc2 != 'A' && anc2 != 'C' && anc2 != 'G' && anc2 != 'T') || (anc3 != 'A' && anc3 != 'C' && anc3 != 'G' && anc3 != 'T') ) {
				//	System.out.print("Ancestral problem at: " + splitSnp[0] + "\t" + splitSnp[1] + "\t" + anc1 + "\t" + anc2 + "\t" + anc3 + "\n");
				// }

				if (anc1 != anc2) {
					disagree[0] = disagree[0] + 1;
				}
				if (anc1 != anc3) {
					disagree[1] = disagree[1] + 1;
				}
				if (anc2 != anc3) {
					disagree[2] = disagree[2] + 1;
				}
				
		       		////
				//	 Count non Ns in Santos
				////
				Boolean mismatch = false;
				char cviBase = '.';
		       		int nonNS = 0;
				int derS = 0;
				for (int f=0; f<p1ind.length; f++) {
		       			if (splitSnp[p1ind[f]].charAt(0) != 'N') {
		       				nonNS = nonNS + 1;
						if (splitSnp[p1ind[f]].charAt(0) != refBase) {
							cviBase = splitSnp[p1ind[f]].charAt(0);
							derS = derS + 1;
							mismatch = true;
						}
		       			}
		       		}
				// Just to check the bump at intermediate frequencies in the spectrum
				// 
				if (nonNS > 19 && derS >= 8 && derS <=12 ) {
					for (int f=0; f<p1ind.length; f++) {
						char b1 = splitSnp[p1ind[f]].charAt(0);
						// For Pca
						if (b1 == refBase) {
							outPca.print("0");
						} else {
							outPca.print("1");
						}
						if (f < p1ind.length-1) {
							outPca.print("\t");
						}
							
						// For nj
						for (int f2=0; f2<p1ind.length; f2++) {
							char b2 = splitSnp[p1ind[f2]].charAt(0);
							if (b1 != 'N' && b2 != 'N') {
								len[f][f2] = len[f][f2] + 1;
								if (b1 != b2) {
									diff[f][f2] = diff[f][f2] + 1;
								}
							}
						}
					}
					outPca.print("\n");
				}

	       			
				////
				//	 Count non Ns in Fogoes
				////

		       		int nonNF = 0;
				for (int f=0; f<p2ind.length; f++) {
		       			if (splitSnp[p2ind[f]].charAt(0) != 'N') {
		       				nonNF = nonNF + 1;
						if (splitSnp[p2ind[f]].charAt(0) != refBase) {
							cviBase = splitSnp[p2ind[f]].charAt(0);
							mismatch = true;
						}

		       			}
		       		}
	       			
		       		int chr = Integer.parseInt(splitSnp[0]);
		       		int pos = Integer.parseInt(splitSnp[1]);
				
	       			
				////
				//	 If it is not too many Ns, use this SNP
				////
				
				// Out FLC
				//
				if (chr == 5 && pos == 3179333) {
					System.out.println("Here comes FLC " + mask[chr-1].get(pos));
				}


		       		if (nonNS >= ( (int) Math.round(95*pop1.length/100) ) && nonNF >= ((int) Math.round(95*pop2.length/100)) && mask[chr-1].get(pos)) {			// nonNS >= maxNS && nonNF >= maxNF && mask[chr-1].get(pos)) {
		       			
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

					int derS1 = 0;
					int derS2 = 0;
		       			int derS3 = 0;
					for (int f=0; f<focIndRandS.length; f++) {
		       				if (splitSnp[focIndRandS[f]].charAt(0) != anc1) {
		       					derS1 = derS1 + 1;
			       			}
		       				if (splitSnp[focIndRandS[f]].charAt(0) != anc2) {
		       					derS2 = derS2 + 1;
			       			}
						if (splitSnp[focIndRandS[f]].charAt(0) != anc3) {
		       					derS3 = derS3 + 1;
			       			}
			       		}
		       			sfsSMl[derS1] = sfsSMl[derS1] + 1;
		       			sfsSX[derS2] = sfsSX[derS2] + 1;
		       			sfsSMach[derS3] = sfsSMach[derS3] + 1;

	
					//////////
					/////
					//		For Fogoes!
					/////
					//////////
					
	
		       			////
					//	Build indexes of nonN accessions
					////

			       		int[] focIndNf = new int[nonNF];
			       		nonNF = 0;
			       		for (int f=0; f<p2ind.length; f++) {
			       			if (splitSnp[p2ind[f]].charAt(0) != 'N') {
			       				focIndNf[nonNF] = p2ind[f];
			       				nonNF = nonNF + 1;
			       			}
			       		}
			       		
					////
					// 	Sorteggia i Fogoes non-N without replacement
			       		// 	Fisher-Yates algorithm
			       		////
					
			       		int[] focIndRandF = new int[maxNF];
			       		randomGenerator = new Random();
					for (int boot=0; boot<focIndRandF.length; boot++) {
						int r = randomGenerator.nextInt(focIndNf.length-boot);
						int l = focIndNf[r];
						focIndRandF[focIndRandF.length-1-boot] = l;
						focIndNf[r] = focIndNf[focIndNf.length-1-boot];
						focIndNf[focIndNf.length-1-boot] = l;
					}
			       		
					////
					//	Get entries for the spectra
					////

					int derF1 = 0;
					int derF2 = 0;
		       			int derF3 = 0;
					for (int f=0; f<focIndRandF.length; f++) {
		       				if (splitSnp[focIndRandF[f]].charAt(0) != anc1) {
		       					derF1 = derF1 + 1;
			       			}
		       				if (splitSnp[focIndRandF[f]].charAt(0) != anc2) {
		       					derF2 = derF2 + 1;
			       			}
						if (splitSnp[focIndRandF[f]].charAt(0) != anc3) {
		       					derF3 = derF3 + 1;
			       			}
			       		}
		       			sfsFMl[derF1] = sfsFMl[derF1] + 1;
		       			sfsFX[derF2] = sfsFX[derF2] + 1;
		       			sfsFMach[derF3] = sfsFMach[derF3] + 1;




					////
					//	Now fill the joint spectra
					////

					jsfsML[derS1][derF1] = jsfsML[derS1][derF1] + 1;
					jsfsX[derS2][derF2] = jsfsX[derS2][derF2] + 1;
					jsfsMach[derS3][derF3] = jsfsMach[derS3][derF3] + 1;
					
					outSnp.print(chr + "\t" + pos + "\t" + derS2 + "\t" + derF2 + "\t" + (double)(derS2)/(double)focIndRandS.length + "\t" + (double)(derF2)/(double)(focIndRandF.length) + "\n");
					
					////
					//	Write here if shared at low or high freq in both pops
					////
					if ( derS2 > 0 && derF2 > 0 && derS2 < maxNS && derF2 < maxNF && ( (derS2 < (int) Math.round(20*maxNS/100) && derF2 < (int) Math.round(20*maxNF/100) ) || ( derS2 > (int) Math.round(80*maxNS/100) && derF2 > (int) Math.round(80*maxNF/100) ) ) ) {
						outShare.print(chr + "\t" + pos + "\t" + derS2 + "\t" + derF2 + "\t" + (double)(derS2)/(double)focIndRandS.length + "\t" + (double)(derF2)/(double)(focIndRandF.length) + "\t");
						// Santo that share:
						// 
						for (int f=0; f<focIndRandS.length; f++) {
		       					if (splitSnp[focIndRandS[f]].charAt(0) != anc2) {
		       						outShare.print(splitIDs[focIndRandS[f]] + ",");
			       				}
						}
						outShare.print("-");
						// Now Fogos that share
						//
						for (int f=0; f<focIndRandF.length; f++) {
		       					if (splitSnp[focIndRandF[f]].charAt(0) != anc2) {
		       						outShare.print(splitIDs[focIndRandF[f]] + ",");
			       				}
						}
						outShare.print("\n");
			       		} 
					// maxNS = (int) Math.round(9*pop1.length/10);

					///
					// Write here if fixed derived in Santo, absent in Fogo
					///

					if (derS2 == focIndRandS.length && derF2 == 0) {
						outSfix.print(chr + "\t" + pos + "\t" + derS2 + "\t" + derF2 + "\t" + (double)(derS2)/(double)focIndRandS.length + "\t" + (double)(derF2)/(double)(focIndRandF.length) + "\n");
					}
					/// And vice versa
					//
					if (derF2 == focIndRandF.length && derS2 == 0) {
						outFfix.print(chr + "\t" + pos + "\t" + derS2 + "\t" + derF2 + "\t" + (double)(derS2)/(double)focIndRandS.length + "\t" + (double)(derF2)/(double)(focIndRandF.length) + "\n");
					}
					
					if (chr == 3 && pos == 21569086) {
						System.out.println("Here comes var2 " + chr + "\t" + pos + "\t" + derS2 + "\t" + derF2 + "\t" + (double)(derS2)/(double)(focIndRandS.length) + "\t" + (double)(derF2)/(double)(focIndRandF.length));
					}

					// Out FLC
					if (chr == 5 && pos == 3179333) {
						System.out.println("Here comes FLC " + chr + "\t" + pos + "\t" + derS2 + "\t" + derF2 + "\t" + (double)(derS2)/(double)(focIndRandS.length) + "\t" + (double)(derF2)/(double)(focIndRandF.length));
					}


									////
					//	Pull out fixed in Fogo, segregating in Santo:
					//	Pre-adaptation at the early stages in Santo?
					////

					if (derF2 == focIndRandF.length && (derS2 != 0 && derS2 != focIndRandS.length) ) {
						outFfSs.print(chr + "\t" + pos + "\t" + derS2 + "\t" + derF2 + "\t" + (double)(derS2)/(double)(focIndRandS.length) + "\t" + (double)(derF2)/(double)(focIndRandF.length) + "\n");
					}

					////
					// 	Look at canaries
					////
					
					//if (derS2 == (jsfsX.length - 1) && derF2 == (jsfsX[0].length - 1) ) {
					if (mismatch && ( (derS2 == (jsfsX.length - 1) && derF2 == (jsfsX[0].length - 1) ) || (derS2 == 0 && derF2 == 0) ) ) {
						// char cviBase = splitSnp[focIndRandF[0]].charAt(0);
						// Check can
						Boolean inCan = false;
						for (int can=0; can<p3ind.length; can++) {
							if (splitSnp[p3ind[can]].charAt(0) == cviBase) {
								inCan = true;
							}
						}
						if (inCan) {
							if (derS2 == 0 && derF2 == 0) {
								canAncestor[0] = canAncestor[0] + 1;
							} else {
								canAncestor[1] = canAncestor[1] + 1;
							}
						} else {
							if (derS2 == 0 && derF2 == 0) {
								noCanAnc[0] = noCanAnc[0] + 1;
							} else {
								noCanAnc[1] = noCanAnc[1] + 1;
							}
						}

						// Check mor
						Boolean inMor = false;
						for (int mor=0; mor<p4ind.length; mor++) {
							if (splitSnp[p4ind[mor]].charAt(0) == cviBase) {
								inMor = true;
							}
						}
						if (inMor) {
							if (derS2 == 0 && derF2 == 0) {
								morAncestor[0] = morAncestor[0] + 1;
							} else {
								morAncestor[1] = morAncestor[1] + 1;
							}
						} else {
							if (derS2 == 0 && derF2 == 0) {
								noMorAnc[0] = noMorAnc[0] + 1;
							} else {
								noMorAnc[1] = noMorAnc[1] + 1;
							}
						}
						
						// Check both
						
						if (inMor || inCan) {
							if (derS2 == 0 && derF2 == 0) {
								canMorAncestor[0] = canMorAncestor[0] + 1;
							} else {
								canMorAncestor[1] = canMorAncestor[1] + 1;
							}
						} else {
							if (derS2 == 0 && derF2 == 0) {
								noCanMorAnc[0] = noCanMorAnc[0] + 1;
							} else {
								noCanMorAnc[1] = noCanMorAnc[1] + 1;
							}
						}
					}
				// }
		       		}
			}
			outPca.close();
			outSnp.close();
			outFfSs.close();
			outFfix.close();
			outSfix.close();
			outShare.close();







			// String results = maskName;
			
			Writer writerCan = new FileWriter(results + "_canAncestors.txt");
			PrintWriter outCan = new PrintWriter(writerCan);
			outCan.println("Cvi derived bases that appear in can: " + "\t" + canAncestor[0] + "\t" + canAncestor[1]);
			outCan.println("Cvi derived bases that NotApp in can: " + "\t" + noCanAnc[0] + "\t" + noCanAnc[1]);
			outCan.println("");
			outCan.println("Cvi derived bases that appear in Mor: " + "\t" + morAncestor[0] + "\t" + morAncestor[1]);
			outCan.println("Cvi derived bases that NotApp in Mor: " + "\t" + noMorAnc[0] + "\t" + noMorAnc[1]);
			outCan.println("");
			outCan.println("Cvi derived bases that appear in can or mor: " + "\t" + canMorAncestor[0] + "\t" + canMorAncestor[1]);
			outCan.println("Cvi derived bases that NotApp in can or mor: " + "\t" + noCanMorAnc[0] + "\t" + noCanMorAnc[1]);
			outCan.close();


			

			Writer writerSnpNum = new FileWriter(results + "_snps_nonN_inTheMask.txt");
			PrintWriter outSnpNum = new PrintWriter(writerSnpNum);
			outSnpNum.println(actualSnps);
			outSnpNum.close();

			Writer writer = new FileWriter(results + "_sfsSanto_withAncestor.txt");
			PrintWriter out = new PrintWriter(writer);
			
			for (int s=0; s<sfsSMl.length; s++) {
				out.print(sfsSMl[s] + "\t");
	       		}
			out.print("\n");
			for (int s=0; s<sfsSX.length; s++) {
				out.print(sfsSX[s] + "\t");
	       		}
			out.print("\n");
       			for (int s=0; s<sfsSMach.length; s++) {
				out.print(sfsSMach[s] + "\t");
	       		}
			out.print("\n");
			out.close();
		

			Writer writerF = new FileWriter(results + "_sfsFogo_withAncestor.txt");
			PrintWriter outF = new PrintWriter(writerF);
			
			for (int s=0; s<sfsFMl.length; s++) {
				outF.print(sfsFMl[s] + "\t");
	       		}
			outF.print("\n");
			for (int s=0; s<sfsFX.length; s++) {
				outF.print(sfsFX[s] + "\t");
	       		}
			outF.print("\n");
       			for (int s=0; s<sfsFMach.length; s++) {
				outF.print(sfsFMach[s] + "\t");
	       		}
			outF.print("\n");
			outF.close();

			
			// Jsfs

			Writer writerJ = new FileWriter(results + "_jsfsML.txt");
			PrintWriter outJ = new PrintWriter(writerJ);
			
			for (int r=0; r<jsfsML.length; r++) {
				for (int c=0; c<jsfsML[r].length; c++) {
					outJ.print(jsfsML[r][c] + "\t");
				}
				outJ.print("\n");
			}
			outJ.close();


			Writer writerJ2 = new FileWriter(results + "_jsfsX.txt");
			PrintWriter outJ2 = new PrintWriter(writerJ2);
			
			for (int r=0; r<jsfsX.length; r++) {
				for (int c=0; c<jsfsX[r].length; c++) {
					outJ2.print(jsfsX[r][c] + "\t");
				}
				outJ2.print("\n");
			}
			outJ2.close();


			Writer writerJ3 = new FileWriter(results + "_jsfsMach.txt");
			PrintWriter outJ3 = new PrintWriter(writerJ3);
			
			for (int r=0; r<jsfsMach.length; r++) {
				for (int c=0; c<jsfsMach[r].length; c++) {
					outJ3.print(jsfsMach[r][c] + "\t");
				}
				outJ3.print("\n");
			}
			outJ3.close();





			Writer writerD1 = new FileWriter(results + "_dadiJsfsML.txt");
			PrintWriter outD1 = new PrintWriter(writerD1);
			
			outD1.println((maxNS + 1) + " " + (maxNF + 1) + " unfolded");
			
			for (int p1 =0; p1<jsfsML.length; p1++) {
				for (int p2 =0; p2<jsfsML[p1].length; p2++) {
					outD1.print(jsfsML[p1][p2] + " ");
				}
			}
			outD1.print("\n");
			
			for (int p1 =0; p1<jsfsML.length; p1++) {
				for (int p2 =0; p2<jsfsML[p1].length; p2++) {
					outD1.print("0 ");
				}
			}
			outD1.print("\n");
			outD1.close();
	
			
			
			
			
			Writer writerD2 = new FileWriter(results + "_dadiJsfsX.txt");
			PrintWriter outD2 = new PrintWriter(writerD2);
			
			outD2.println((maxNS + 1) + " " + (maxNF + 1) + " unfolded");
			
			for (int p1 =0; p1<jsfsX.length; p1++) {
				for (int p2 =0; p2<jsfsX[p1].length; p2++) {
					outD2.print(jsfsX[p1][p2] + " ");
				}
			}
			outD2.print("\n");
			
			for (int p1 =0; p1<jsfsX.length; p1++) {
				for (int p2 =0; p2<jsfsX[p1].length; p2++) {
					outD2.print("0 ");
				}
			}
			outD2.print("\n");
			outD2.close();
	



			Writer writerD3 = new FileWriter(results + "_dadiJsfsMach.txt");
			PrintWriter outD3 = new PrintWriter(writerD3);
			
			outD3.println((maxNS + 1) + " " + (maxNF + 1) + " unfolded");
			
			for (int p1 =0; p1<jsfsMach.length; p1++) {
				for (int p2 =0; p2<jsfsMach[p1].length; p2++) {
					outD3.print(jsfsMach[p1][p2] + " ");
				}
			}
			outD3.print("\n");
			
			for (int p1 =0; p1<jsfsMach.length; p1++) {
				for (int p2 =0; p2<jsfsMach[p1].length; p2++) {
					outD3.print("0 ");
				}
			}
			outD3.print("\n");
			outD3.close();
	
			System.out.println("disagree: " + disagree);





			Writer writerP = new FileWriter(results + "_medFreqCheck.txt");
			PrintWriter outP = new PrintWriter(writerP);
			
			for (int f=0; f<p1ind.length; f++) {
				for (int f2=0; f2<p1ind.length; f2++) {
					pairw[f][f2] = (double)(diff[f][f2])/(double)(len[f][f2]);
					outP.print(pairw[f][f2]);
					if (f2 < p1ind.length-1) {
						outP.print("\t");
					}
				}
				outP.print("\n");
			}
			outP.close();
		




		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix_getJSFS_morPol matrix_getJSFS_morPol = new Matrix_getJSFS_morPol();
		matrix_getJSFS_morPol.setFileToSubSet(args[0], args[1], args[2], args[3], args[4]);
	}
}
