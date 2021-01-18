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

public class Fst {
	
	public Fst() {}
	
	public void setMatrixFile(String matrixName, String results, String sampleSanto, String sampleFogo){
		
		try {
			int size = 70;

			Writer writerLog = new FileWriter(results + "log.txt");
			PrintWriter outLog = new PrintWriter(writerLog);
			
			outLog.println("src/c/e/data_processing/fst.java");            
						
			// Open IDs in the big matrix
			
			File matrix = new File(matrixName);
			Scanner scannerMatrix = new Scanner(matrix);
			String idsUnsplit = scannerMatrix.nextLine();
			String[] IDs = idsUnsplit.split("\t");
			
				

			// Get Fogo
			
			File fileNamesToGet2 = new File(sampleFogo);
			
			Scanner scanNamesTG2 = new Scanner(fileNamesToGet2);

			int samplesTG2 = 0;
			while (scanNamesTG2.hasNextLine()) {
				String name = scanNamesTG2.nextLine();
				samplesTG2 = samplesTG2 + 1;
			}
			String[] samplesToGet2 = new String[samplesTG2];
			
			samplesTG2 = 0;
			scanNamesTG2 = new Scanner(fileNamesToGet2);
			while (scanNamesTG2.hasNextLine()) {
				String name = scanNamesTG2.nextLine();
				String[] splitName = name.split("\t");
				samplesToGet2[samplesTG2] = splitName[0];
				samplesTG2 = samplesTG2 + 1;
			}
			outLog.println(Arrays.toString(samplesToGet2));
			outLog.println("We have: " + samplesToGet2.length + " samples");


			////
			// 	Sorteggia Fogos without replacement
	       		// 	Fisher-Yates algorithm
	       		////
			
	       		String[] subFogo = new String[size];
	       		Random randomGenerator = new Random();
			for (int boot=0; boot<subFogo.length; boot++) {
				int r = randomGenerator.nextInt(samplesToGet2.length-boot);
				String l = samplesToGet2[r];
				subFogo[subFogo.length-1-boot] = l;
				samplesToGet2[r] = samplesToGet2[samplesToGet2.length-1-boot];
				samplesToGet2[samplesToGet2.length-1-boot] = l;
			}
			outLog.println(Arrays.toString(subFogo));
			outLog.println("We have: " + subFogo.length + " fogos");
			

			// Get indexes
			
			int[] index2 = new int[subFogo.length];
			for (int s=0; s<subFogo.length; s++) {
				Boolean thereIs = false;
				for (int i=0; i<IDs.length; i++) {
					if (subFogo[s].equals(IDs[i])) {
						thereIs = true;
						index2[s] = i;
					}
				}
				if (!thereIs) {
					outLog.println("There is no " + subFogo[s]);
				}
			}
			for (int p=0; p<index2.length; p++) {
				outLog.print(IDs[index2[p]] + "\t");
			}
			outLog.print("\n");
			
		




			
			// Get Santo Antao
			
			File fileNamesToGet = new File(sampleSanto);
			Scanner scanNamesTG = new Scanner(fileNamesToGet);
			
			int samplesTG = 0;
			while (scanNamesTG.hasNextLine()) {
				String name = scanNamesTG.nextLine();
				samplesTG = samplesTG + 1;
			}
			String[] samplesToGet = new String[samplesTG];
			
			samplesTG = 0;
			scanNamesTG = new Scanner(fileNamesToGet);
			while (scanNamesTG.hasNextLine()) {
				String name = scanNamesTG.nextLine();
				String[] splitName = name.split("\t");
				samplesToGet[samplesTG] = splitName[0];
				samplesTG = samplesTG + 1;
			}
			outLog.println(Arrays.toString(samplesToGet));
			outLog.println("We have: " + samplesToGet.length + " samples");
			System.out.println("We have: " + samplesToGet.length + " samples");
			
			////
			// 	Sorteggia Santos without replacement
	       		// 	Fisher-Yates algorithm
	       		////
			
	       		String[] subSanto = new String[size];
	       		randomGenerator = new Random();
			for (int boot=0; boot<subSanto.length; boot++) {
				int r = randomGenerator.nextInt(samplesToGet.length-boot);
				String l = samplesToGet[r];
				subSanto[subSanto.length-1-boot] = l;
				samplesToGet[r] = samplesToGet[samplesToGet.length-1-boot];
				samplesToGet[samplesToGet.length-1-boot] = l;
			}
			outLog.println(Arrays.toString(subSanto));
			outLog.println("We have: " + subSanto.length + " samples");
			
			
			// Get indexes
			
			int[] index1 = new int[subSanto.length];
			for (int s=0; s<subSanto.length; s++) {
				Boolean thereIs = false;
				for (int i=0; i<IDs.length; i++) {
					if (subSanto[s].equals(IDs[i])) {
						thereIs = true;
						index1[s] = i;
					}
				}
				if (!thereIs) {
					outLog.println("There is no " + subSanto[s]);
				}
			}
			for (int p=0; p<index1.length; p++) {
				outLog.print(IDs[index1[p]] + "\t");
			}			
			outLog.print("\n");
			// Got indexes
			
			
			
			
			
			
			int[] indexTot = new int[index1.length + index2.length];
			int t=0;
			for (int p=0; p<index1.length; p++) {
				indexTot[t] = index1[p];
				t = t + 1;
			}
			for (int p=0; p<index2.length; p++) {
				indexTot[t] = index2[p];
				t = t + 1;
			}
			outLog.println(Arrays.toString(indexTot));
			
			// Got indexes
			
			
	
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
		
			
			
			
			
			int[] sfsSanto = new int[index1.length+1];
			int[] sfsFogo = new int[index2.length+1];
			int[][] jsfs = new int[index1.length+1][index2.length+1];
			for (int s=0; s<index1.length; s++) {
				sfsSanto[s] = 0;
				for (int s2=0; s2<index2.length; s2++) {
					jsfs[s][s2] = 0;
				}
			}
			for (int s=0; s<index2.length; s++) {
				sfsFogo[s] = 0;
			}
			
			
			//
			// Now the real game
			// 
			
			int[] chrL = {30427671, 19698289, 23459830, 18585056, 26975502};
			int chr = 0;
			
			int[] chrCand = {1, 1, 1, 4, 4, 5};
			int[] posCand={1186604, 5981216, 8065458, 269719, 10707874, 3179333};
			String[] namesCand= {"CRY2", "LOX3", "GI", "FRI", "IRT1", "FLC"};
			
			int[] chrSantoDnDsPeak1 = {1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5};
			int[] posSantoDnDsPeak1 = {15968195, 22018761, 22203675, 7704212, 8903025, 2625905, 4067436, 872069, 12553604, 12950226, 13980453, 1625880, 7385002, 7774607, 8158607, 22981436, 23777535};

			int[] chrSantoDnDsPeak2 = {1, 3, 3, 3, 5};
			int[] posSantoDnDsPeak2 = {25934692, 1667168, 3456286, 17518560, 4661745};
			
			// Are the candidates in the CpG/centromeres mask?
			for (int ca =0; ca<chrCand.length;ca++) {
				if (maskRepeat[chrCand[ca] - 1].get(posCand[ca])) {
					System.out.print(namesCand[ca] + "\t" + "CpGCentrom" + "\n");
				} else {
					System.out.print(namesCand[ca] + "\t" + "no CpGC" + "\n");
				}
			}

			System.out.println("End CpGMask");



		    matrix = new File(matrixName);
		    scannerMatrix = new Scanner(matrix);
		    scannerMatrix.nextLine();
		    
		    Writer writer = new FileWriter(results + "fst_perSnp.txt");
			PrintWriter out = new PrintWriter(writer);
			
			
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Just print where we are
    			if (chr != Integer.parseInt(splitSnp[0])) {
    				System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
    			}
    			chr = Integer.parseInt(splitSnp[0]);
    			int pos = Integer.parseInt(splitSnp[1]);
       		 	//
        		
			Boolean cand = false;
			for (int ca=0; ca<chrCand.length;ca++) {
				if (chr == chrCand[ca] && pos == posCand[ca]) {	
					cand = true;
					outLog.print(namesCand[ca] + "\t" + chr + "\t" + pos);
				}
			}
			
			// Look for the 2 peaks of Dn/Ds vs Fst in Santo
			//
			for (int p=0; p<chrSantoDnDsPeak1.length; p++) {
				if (chr == chrSantoDnDsPeak1[p] && pos == posSantoDnDsPeak1[p]) {
					outLog.print("Dn/Ds Peak 1\t" + chr + "\t" + pos + "\t");
					for (int s=6; s<splitSnp.length; s++) {
						if (splitSnp[s].charAt(0) != splitSnp[2].charAt(0)) {
							outLog.print(IDs[s] + "|");
						}
					}
					outLog.print("\n");
				}
			}
			// Peak 2
			for (int p=0; p<chrSantoDnDsPeak2.length; p++) {
				if (chr == chrSantoDnDsPeak2[p] && pos == posSantoDnDsPeak2[p]) {
					outLog.print("Dn/Ds Peak 2\t" + chr + "\t" + pos + "\t");
					for (int s=6; s<splitSnp.length; s++) {
						if (splitSnp[s].charAt(0) != splitSnp[2].charAt(0)) {
							outLog.print(IDs[s] + "|");
						}
					}
					outLog.print("\n");
				}
			}
			// End of Fst / DnDs peaks
			
			
    			
    			// Segrega?
    			boolean segrega = false;
    			boolean n = false;
			//
    			for (int s1 = 0; s1 < indexTot.length; s1++) {
    				char base1 = splitSnp[indexTot[s1]].charAt(0);
    				if (base1 != 'N') {
    					for (int s2 = 0; s2 < indexTot.length; s2++) {
    						//
    	    					char base2 = splitSnp[indexTot[s2]].charAt(0);
            					if (base2 != 'N') {
            						if (base1 != base2) {
            							segrega = true;
                					}
						}
            				}
            			} else {
					n = true;
				}
    			}
    			
    			if (cand) {
				outLog.print("\tsegrega: " + segrega + "\tn: " + n);
			}
    			if (segrega && !n) {					 // && !maskRepeat[chr-1].get(pos)) {
    				//
    				// Pi Santo
    				//
    				int pairS = 0;
    				int diffS = 0;
    				for (int s = 0; s < index1.length - 1; s++) {
        				char base1 = splitSnp[index1[s]].charAt(0);
        				if (base1 != 'N') {
        					for (int s2 = s+1; s2 < index1.length; s2++) {
    	    					//
        						char base2 = splitSnp[index1[s2]].charAt(0);
        	    				if (base2 != 'N') {
        	    					pairS = pairS + 1;
                					if (base1 != base2) {
                						diffS = diffS + 1;
                					}
                				}
    	    				}
        				}
        			}
    				double piS = (double)diffS/pairS;
    				// 
				// Check the sfs
				int derS = 0;
				for (int s = 0; s < index1.length; s++) {
					char base1 = splitSnp[index1[s]].charAt(0);
					if (base1 != 'N' && base1 != splitSnp[2].charAt(0)) {
						derS = derS + 1;
					}
				}
				sfsSanto[derS] = sfsSanto[derS] + 1;

    				//
    				// Pi Fogo
    				//
    				int pairF = 0;
    				int diffF = 0;
    				for (int s = 0; s < index2.length - 1; s++) {
        				char base1 = splitSnp[index2[s]].charAt(0);
        				if (base1 != 'N') {
        					for (int s2 = s+1; s2 < index2.length; s2++) {
    	    					//
        						char base2 = splitSnp[index2[s2]].charAt(0);
        	    				if (base2 != 'N') {
        	    					pairF = pairF + 1;
                					if (base1 != base2) {
                						diffF = diffF + 1;
                					}
                				}
    	    				}
        				}
        			}
    				double piF = (double)diffF/pairF;
    				// 
				// Check the sfs
				int derF = 0;
				for (int s = 0; s < index2.length; s++) {
					char base1 = splitSnp[index2[s]].charAt(0);
					if (base1 != 'N' && base1 != splitSnp[2].charAt(0)) {
						derF = derF + 1;
					}
				}
				//if (derF == 113 || derS == 139) {
				//	System.out.println("fix: " + derF + "\t" + derS);
				//}
				sfsFogo[derF] = sfsFogo[derF] + 1;
				// Now jsfs
				jsfs[derS][derF] = jsfs[derS][derF] + 1;

    				//
    				// Pi between
    				//
    				int pairB = 0;
    				int diffB = 0;
    				for (int s = 0; s < indexTot.length - 1; s++) {
        				char base1 = splitSnp[indexTot[s]].charAt(0);
        				if (base1 != 'N') {
        					for (int s2 = s+1; s2 < indexTot.length; s2++) {
    	    					//
        						char base2 = splitSnp[indexTot[s2]].charAt(0);
        	    				if (base2 != 'N') {
        	    					pairB = pairB + 1;
                					if (base1 != base2) {
                						diffB = diffB + 1;
                					}
                				}
    	    				}
        				}
        			}
    				double piB = (double)diffB/pairB;
    				
    				//		Fst
    				//
    				double averageWt = (double)(piF + piS)/2;
				String isl = ".";
				if ( derS > 0 ) {
					if (derF > 0) {
						isl = "s,f";
					} else {
						isl = "s";
					}
				} else {
					isl = "f";
				}
    				double fst = (double)(piB - averageWt)/(double)piB;
    				// out
				//
    				out.print(chr + "\t" + pos + "\t" + fst + "\t" + isl + "\n");
				
				if (cand) {
					// 0.08681408681408681 0.0 0.0393574297188755  -0.10289324575038862
					outLog.print("\t" + derS + "\t" + derF + "\t" + piF + "\t" + piS + "\t" + piB + "\t" + fst + "\t");
					for (int s=6; s<splitSnp.length; s++) {
						if (splitSnp[s].charAt(0) != splitSnp[2].charAt(0)) {
							outLog.print(IDs[s] + "|");
						}
					}
					outLog.print("\n");
				}
    			}
	        }
		out.close();

		outLog.println("SFS fogo: " + Arrays.toString(sfsFogo));
		outLog.println("SFS santo: " + Arrays.toString(sfsSanto));
		
		outLog.println("jsfs:");
		for (int s=0; s<jsfs.length; s++) {
			for (int s2=0; s2<jsfs[s].length; s2++) {
				outLog.print(jsfs[s][s2] + "\t");
			}
			outLog.print("\n");
		}
		outLog.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Fst fst = new Fst();
		fst.setMatrixFile(args[0], args[1], args[2], args[3]);
	}
}
