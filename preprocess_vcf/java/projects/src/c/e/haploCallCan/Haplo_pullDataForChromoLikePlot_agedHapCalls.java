package c.e.haploCallCan;

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

public class Haplo_pullDataForChromoLikePlot_agedHapCalls {
	
	private int whichCountry = 0;
	private Haplo_pullDataForChromoLikePlot_agedHapCalls() {}
	
	public void fromHapCallsToHapFS(String matrixName, String canarian) {
		try {
			
			// 1 get indices right
			
			// 2 run on one canarian:
				
				// 3.1 get mask of potential haps from the 2 sources for this canarian
				
				// 3.2 define borders and numbers of potential admixture tracts
				
				// 3.3 open the big matrix - for every potential admixture tract, 
					// check that the density of differences to the potential source is smaller than that to the other source
					// require 2 snps of difference at least
					// Check also second best sample
					
				// 3.4 Somehow output these positions - to plot, and to charge in faster next time
			
			// The eeeeeend
			
			
       			String whereWe = "/home/lv70590/Andrea/analyses/haplotypeCallsCan/";
       			int whichCan = Integer.parseInt(canarian);
			
			
			// 1 get indices right
			
			// Modified to exclude 80 genomes
			
			File eightyGenomes = new File("/home/lv70590/Andrea/data/80genomes.txt");
			Scanner scannerEighty = new Scanner(eightyGenomes);
			scannerEighty.nextLine();
			int eightyS = 0;
			while (scannerEighty.hasNextLine()) {
				scannerEighty.nextLine();			   	
			   	eightyS = eightyS + 1;
			}
			
			int[] eightyIds = new int[eightyS];
			eightyS = 0;
			scannerEighty = new Scanner(eightyGenomes);
			scannerEighty.nextLine();
			while (scannerEighty.hasNextLine()) {
				String eightyString = scannerEighty.nextLine();
			   	String[] splitEighty = eightyString.split("\t");
			   	
			   	eightyIds[eightyS] = Integer.parseInt(splitEighty[0]);
			   	eightyS = eightyS + 1;
			}
			
			
			
			
			// From HaplotypeMonsterEater
			// File with 2 lines: tab separated int IDS for 1001 plus macaronesian plants, 
			// and in the second line, same order, country code for the same plant
			File idsCountryFile = new File(whereWe + "idsCountry_05-2016.txt");
			Scanner scannerIdsCountry = new Scanner(idsCountryFile);
			
			// How many plants in our sample?
			int plants = scannerIdsCountry.nextLine().split("\t").length;	
			
			int[] ids = new int[plants];
			String[] country = new String[plants];
			
			scannerIdsCountry = new Scanner(idsCountryFile);
			String idsPlants = scannerIdsCountry.nextLine();
		   	String[] splitIds = idsPlants.split("\t");
		   	for (int p=0; p< splitIds.length; p++) {
		   		ids[p] = Integer.parseInt(splitIds[p]);
		   	}
		   	String Count = scannerIdsCountry.nextLine();
		   	String[] splitCount = Count.split("\t");
		   	for (int p=0; p< splitIds.length; p++) {
		   		country[p] = splitCount[p];
		   	}
			// System.out.println(Arrays.toString(ids));
			// System.out.println(Arrays.toString(country));
			
		   	// Got ids and country
			// From HaplotypeMonsterEater
			
			
			
	    	
		    // Begin with the big matrix
			// From HaplotypeMonsterEater BEGIN
		    File matrix = new File(matrixName);
		    Scanner scannerMatrix = new Scanner(matrix);
		    	
		    // Just get ID order in the matrix from first line
		    // And count iberians [Spanish + Portuguese]
			String idsUnsplit = scannerMatrix.nextLine();
		    String[] splitIDs = idsUnsplit.split("\t");
			
		    int numIberianPlants =0;
			int numCanPlants = 0;
			int numCviPlants = 0;
			int numMorPlants = 0;
			int numAllWorld = 0;
			int numMadeiraPlants = 0;
			int numAllButCanaryCvi = 0;
			
			int cviZindex = 0;
			int colIndex = 0;
			
			for (int i=2; i<splitIDs.length; i++) {
				// Check if it is a 80genome
				Boolean isInEighty = false;
				for (int eightyId=0; eightyId<eightyIds.length; eightyId++) {
					if (eightyIds[eightyId] == Integer.parseInt(splitIDs[i])) {
						isInEighty = true;
					}
				}
				if (!isInEighty) {
					// ids1001[i-2] = Integer.parseInt(splitIDs[i]);
					// Count Iberians
					for (int co=0; co<ids.length; co++) {
						if (ids[co] == Integer.parseInt(splitIDs[i])) {
							if ( ids[co] == 6911 ) {
								cviZindex = i;
							}
							if ( ids[co] == 6909 ) {
								colIndex = i;
							}
							if ( country[co].equals("ESP") || country[co].equals("POR") ) {
								numIberianPlants = numIberianPlants + 1;
								numAllButCanaryCvi = numAllButCanaryCvi + 1;
							} else {
								if ( country[co].equals("CANISL") ) {
									numCanPlants = numCanPlants + 1;
								} else {
									if ( country[co].equals("CVI") ) {
										numCviPlants = numCviPlants + 1;
									} else {
										if ( country[co].equals("ARE") ) {
											numMadeiraPlants = numMadeiraPlants + 1;
											numAllButCanaryCvi = numAllButCanaryCvi + 1;
										} else {
											if ( country[co].equals("MOR") ) {
												numMorPlants = numMorPlants + 1;
												numAllButCanaryCvi = numAllButCanaryCvi + 1;
											} else {
												numAllWorld = numAllWorld + 1;
												numAllButCanaryCvi = numAllButCanaryCvi + 1;
											}
										}
									}
								}
							}
						}
					}
				}
			}
			System.out.println("After eliminating the 80 genomes...");
			
			System.out.println("We have " + numCviPlants + " CapeVerdeans");
			System.out.println("We have " + numCanPlants + " Canarians");
			System.out.println("We have " + numIberianPlants + " Iberians");
			System.out.println("We have " + numMorPlants + " Moroccans");
			System.out.println("We have " + numMadeiraPlants + " Madeirans");
			System.out.println("We have " + numAllButCanaryCvi + " But canaries but cvi-non0");
			System.out.println("We have " + numAllWorld + " in the world");
			
			// Got IDs order, got Macaronesian, Moroccan and Iberian numbers, got Cvi-0 ID and col-0
		    
		    	
			// Link ids to country in a index int[] for IBP, Cvi-0 we have already
			// int[] sfsIBP = new int[numIberianPlants+1];
			int[] iberianIndexes = new int[numIberianPlants];
			int[] canarianIndexes = new int[numCanPlants];
			int[] capeVerdeanIndexes = new int[numCviPlants];
			int[] moroccanIndexes = new int[numMorPlants];
			int[] madeiranIndexes = new int[numMadeiraPlants];
			int[] restOfWorldIndexes = new int[numAllWorld];
			int[] allButCanCviIndexes = new int[numAllButCanaryCvi];
			
			numIberianPlants =0;
			numCanPlants =0;
			numCviPlants = 0;
			numMorPlants = 0;
			numAllWorld = 0;
			numMadeiraPlants = 0;
			numAllButCanaryCvi = 0;
			
			for (int i=2; i<splitIDs.length; i++) {
				// Check if it is a 80genome
				Boolean isInEighty = false;
				for (int eightyId=0; eightyId<eightyIds.length; eightyId++) {
					if (eightyIds[eightyId] == Integer.parseInt(splitIDs[i])) {
						isInEighty = true;
					}
				}
				if (!isInEighty) {				
					for (int co=0; co<ids.length; co++) {
		    			if (ids[co] == Integer.parseInt(splitIDs[i])) {
		    				if ( country[co].equals("ESP") || country[co].equals("POR") ) {
		    					iberianIndexes[numIberianPlants] = i;
		    					numIberianPlants = numIberianPlants + 1;
		    					allButCanCviIndexes[numAllButCanaryCvi] = i;
		    					numAllButCanaryCvi = numAllButCanaryCvi + 1;
		    				} else {
			    				if ( country[co].equals("CANISL") ) {
			    					canarianIndexes[numCanPlants] = i;
			    					numCanPlants = numCanPlants + 1;
			    				} else {
			    					if ( country[co].equals("CVI") ) {
				    					capeVerdeanIndexes[numCviPlants] = i;
			    						numCviPlants = numCviPlants + 1;
			    					} else {
			    						if ( country[co].equals("ARE") ) {
					    					madeiranIndexes[numMadeiraPlants] = i;
					    					numMadeiraPlants = numMadeiraPlants + 1;
					    					allButCanCviIndexes[numAllButCanaryCvi] = i;
					    					numAllButCanaryCvi = numAllButCanaryCvi + 1;
					    				} else {
							    			if ( country[co].equals("MOR") ) {
						    					moroccanIndexes[numMorPlants] = i;
						    					numMorPlants = numMorPlants + 1;
						    					allButCanCviIndexes[numAllButCanaryCvi] = i;
						    					numAllButCanaryCvi = numAllButCanaryCvi + 1;
						    				} else {
						    					restOfWorldIndexes[numAllWorld] = i;
							    				numAllWorld = numAllWorld + 1;
						    					allButCanCviIndexes[numAllButCanaryCvi] = i;
						    					numAllButCanaryCvi = numAllButCanaryCvi + 1;
						    				}
					    				}
			    					}
			    				}
		    				}
		    			}
		    		}
				}
			}
			
			// Linked to country. Now we have int Cvi-0, Col-0 index, and int[] for Iberian indexes.
			// From HaplotypeMonsterEater END
			
			
			
			
			// 2 order canarians
			
			
			// Count samples
			int nCan = canarianIndexes.length;
			int chromosomes = 5;
			System.out.println("There are: " + nCan + " canarians");
			
			
			
			
			
			
			
			
			// 2 run on one canarian:
			
			for (int can=whichCan; can<whichCan + 1; can++) {
				
				// 3.1 get mask of potential haps from the 2 sources for this canarian
				
				BitSet[] potentiallyAncestral = new BitSet[chromosomes];
				BitSet[] potentiallyNonAncestral = new BitSet[chromosomes];
				for (int c=0; c<chromosomes; c++) {
					potentiallyAncestral[c] = new BitSet();
					potentiallyNonAncestral[c] = new BitSet();
				}
				
				File partialCallFile = new File(whereWe + "hapCalls_YeaCutN_" + can + ".txt");
				Scanner scannerpartialCall = new Scanner(partialCallFile);
				String name = scannerpartialCall.nextLine();
				scannerpartialCall.nextLine();
				System.out.println(name);
				
				while (scannerpartialCall.hasNextLine()) {
					String hap = scannerpartialCall.nextLine();
					String[] splithap = hap.split("\t");
					
					// Disregard if it is a hap match to 80 genomes
					int idHap = Integer.parseInt(splithap[3]);
					Boolean isInEighty = false;
					
					for (int eightyId=0; eightyId<eightyIds.length; eightyId++) {
						if (eightyIds[eightyId] == idHap) {
							isInEighty = true;
						}
					}
					
					if (!isInEighty) {
						int chr = Integer.parseInt(splithap[0]);
						int startHap = Integer.parseInt(splithap[1]);
						int endHap = Integer.parseInt(splithap[2]);
						String country2 = splithap[4];
					
						if (country2.equals("CANISL")) {
							whichCountry = 0;
						} else {
							if ( (country2.equals("CPV")) || country2.equals("CVI") ) {
								whichCountry = 1;
							} else {
								if (country2.equals("ARE")) {
									whichCountry = 2;
								} else {
									if (country2.equals("ESP")) {
										whichCountry = 3;
									} else {
										if (country2.equals("POR")) {
											whichCountry = 4;
										} else {
											if (country2.equals("MOR")) {
												whichCountry = 5;
											} else {
												whichCountry = 6;
											}
										}
									}
								}
							}
						}
					
						// Add this hap
						for (int p = startHap; p < endHap; p++) {
							if ( ((whichCountry >= 0) && (whichCountry <= 2)) || (whichCountry == 5) ) {
								potentiallyAncestral[chr-1].set(p);
							}
							if ( (whichCountry == 3) || (whichCountry == 4) ) { // || (whichCountry == 6)
								potentiallyNonAncestral[chr-1].set(p);
							}
						}
					}
				}
				int cardAnc = 0;
				int cardMig = 0;
				for (int c=0; c<5; c++) {
					cardAnc = cardAnc + potentiallyAncestral[c].cardinality();
					cardMig = cardMig + potentiallyNonAncestral[c].cardinality();
				}
				System.out.println("Potentially ancestral: " + cardAnc);
				System.out.println("Potentially Migrant: " + cardMig);
				
				// Got potential haps
			
			
			
			
			
			
			
			
			
			// 3.2 define borders and numbers of potential admixture tracts
			
			// Potentially ancestral - from D2_Hap_age_chromoP
			int memPos = 0;
			int numAncHaps = 0;
			System.out.println("Counting potential ancestral haps...");
			
			// Let's count haplotypes
			for (int c=0; c<5; c++) {
				memPos = 0;
				for (int p=0; p<potentiallyAncestral[c].length(); p++) {
					if (potentiallyAncestral[c].get(p)) {
						
						// First position behaves different
						if (memPos != 0) {
							// Did we jump a gap?
								if (memPos != p-1) {
									numAncHaps = numAncHaps + 1;
							}
								memPos = p;
						} else {
							// starting from the second p, activate memPos
								memPos = p;								
						}
					}
				}
				numAncHaps = numAncHaps + 1;
			}
			
			// Let's find breakpoints and chromosomes
			int[] hapChrAnc = new int[numAncHaps];
			int[] hapBeginAnc = new int[numAncHaps];
			int[] hapEndAnc = new int[numAncHaps];
			numAncHaps = 0;
			
			for (int c=0; c<5; c++) {
				memPos = 0;
				for (int p=0; p<potentiallyAncestral[c].length(); p++) {
					if (potentiallyAncestral[c].get(p)) {
						
						// First position behaves different
						if (memPos != 0) {
							// Did we jump a gap?
							if (memPos != p-1) {
								hapEndAnc[numAncHaps] = memPos;
								numAncHaps = numAncHaps + 1;
								hapBeginAnc[numAncHaps] = p;
								hapChrAnc[numAncHaps] = c;
							}
							memPos = p;
						} else {
							hapBeginAnc[numAncHaps] = p;
							hapChrAnc[numAncHaps] = c;
							// starting from the second p, activate memPos
							memPos = p;
						}
					}
				}
				hapEndAnc[numAncHaps] = potentiallyAncestral[c].length() - 1;
				numAncHaps = numAncHaps + 1;
			}
			System.out.println("Working on: " + can + " there are: " + hapChrAnc.length + " potential ancestral haplotypes");
			
			// END from B_HapFS
				
				
				
				
				
			// Potentially non-ancestral - from D2_Hap_age_chromoP
			
			memPos = 0;
			int numMigHaps = 0;
			System.out.println("Counting haps...");
			
			// Let's count haplotypes
			for (int c=0; c<5; c++) {
				memPos = 0;
				for (int p=0; p<potentiallyNonAncestral[c].length(); p++) {
					if (potentiallyNonAncestral[c].get(p)) {
						
						// First position behaves different
						if (memPos != 0) {
							// Did we jump a gap?
							if (memPos != p-1) {
								numMigHaps = numMigHaps + 1;
							}
							memPos = p;
						} else {
							// starting from the second p, activate memPos
							memPos = p;								
						}
					}
				}
				numMigHaps = numMigHaps + 1;
			}
			
			// Let's find breakpoints and chromosomes
			int[] hapChrMig = new int[numMigHaps];
			int[] hapBeginMig = new int[numMigHaps];
			int[] hapEndMig = new int[numMigHaps];
			numMigHaps = 0;
			
			for (int c=0; c<5; c++) {
				memPos = 0;
				for (int p=0; p<potentiallyNonAncestral[c].length(); p++) {
					if (potentiallyNonAncestral[c].get(p)) {

						// First position behaves different
						if (memPos != 0) {
							// Did we jump a gap?
							if (memPos != p-1) {
								hapEndMig[numMigHaps] = memPos;
								numMigHaps = numMigHaps + 1;
								hapBeginMig[numMigHaps] = p;
								hapChrMig[numMigHaps] = c;
							}
							memPos = p;
						} else {
							hapBeginMig[numMigHaps] = p;
							hapChrMig[numMigHaps] = c;
							// starting from the second p, activate memPos
							memPos = p;								
						}
					}
				}
				hapEndMig[numMigHaps] = potentiallyNonAncestral[c].length() - 1;
				numMigHaps = numMigHaps + 1;
			}
			System.out.println("Working on: " + can + " there are: " + hapChrMig.length + " potential migrant haplotypes");
			
			// END from B_HapFS
				
			
			// GOT potential haplotypes of the 2 kinds
			
		    	
		    		
				
				
				
				
				
				
				
				
				
				
				//
				// 3.3 open the big matrix - for every potential admixture tract, 
				// check that the density of differences to the potential source is smaller than that to the other source
				// Require min 2 SNPs of difference
				// Check the second closest sample
				
				// Run through potentially from here or there, 
				// Look at the big matrix and date the region. 
				// Is it closer to morocco, or iberia?
				
				
				
				BitSet[] ancestralAndNotMigrant = new BitSet[chromosomes];
				BitSet[] migrantAndNotancestral = new BitSet[chromosomes];
                BitSet[] outBecauseLessThan2DiffAnc = new BitSet[chromosomes];
                BitSet[] outBecauseLessThan2DiffMig = new BitSet[chromosomes];

				for (int c=0; c<chromosomes; c++) {
					ancestralAndNotMigrant[c] = new BitSet();
					migrantAndNotancestral[c] = new BitSet();
                    outBecauseLessThan2DiffAnc[c] = new BitSet();
		            outBecauseLessThan2DiffMig[c] = new BitSet();
				}
				
				int out2DiffAncHap = 0;
				int out2DiffMigHap = 0;
				
				// How many haps have the closest relative in the right population, 
				// but the second closest in the wrong?
				int secondWrongAnc = 0;
				int secondWrongAnc2 = 0;
				int secondWrongMig = 0;
				int secondWrongMig2 = 0;
				
				int chr =0;
				int hapMemAnc = 0;
				int hapMemMig = 0;
				
				int[] bestDAnc = new int[moroccanIndexes.length + iberianIndexes.length];
				int[] bestLAnc = new int[moroccanIndexes.length + iberianIndexes.length];
				
				int[] bestDMig = new int[moroccanIndexes.length + iberianIndexes.length];
				int[] bestLMig = new int[moroccanIndexes.length + iberianIndexes.length];
				
				scannerMatrix = new Scanner(matrix);
			    scannerMatrix.nextLine();
				System.out.println("Chr: 1");
				
				while ( scannerMatrix.hasNextLine() ) {
					String snp = scannerMatrix.nextLine();
					String[] splitSnp = snp.split("\t");
					
					// Just print where we are
	        		if (chr != Integer.parseInt(splitSnp[0]) - 1) {
	       				System.out.println("length ancestral: " + ancestralAndNotMigrant[chr].length());
                        System.out.println("length migrant: " + migrantAndNotancestral[chr].length());
						System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
					}
	        		chr = Integer.parseInt(splitSnp[0]) - 1;
	        		int pos = Integer.parseInt(splitSnp[1]);
	        			
	        			
	        			
	        			
	   				// Is the position in a potential ancestral hap in this canarian?
    				if (potentiallyAncestral[chr].get(pos)) {
    				
    					// which hap?
    	   		    	for (int hapAnc=0; hapAnc<hapChrAnc.length; hapAnc++) {
    			    		if ( (hapChrAnc[hapAnc] == chr) && (hapBeginAnc[hapAnc] <= pos) && (hapEndAnc[hapAnc] >= pos) ) {
    			    			
    	   				    	// Output the last hap, if we are in the next
    	    		    		if (hapAnc > hapMemAnc) {
    	    				    	// Check the density of differences to the right and wrong source
    	    	    				
    	    	    				// Find the closest moroccan, but not because it misses a lot of data    	    	    				
    				    	    	double minAncMor = 2.0;
   	        	    				int nDiffAncBestMor = 0;
                                    int nDiffAncBestIbp = 0;
                                    
									for (int mor=0; mor<moroccanIndexes.length; mor++) {
   	        	    					if ( ( (double)(bestDAnc[mor])/(double)(bestLAnc[mor]) < minAncMor) && (bestLAnc[mor] >= 0.8*(hapEndAnc[hapMemAnc] - hapBeginAnc[hapMemAnc]) ) ) {
   	        	    						minAncMor = (double)(bestDAnc[mor])/(double)(bestLAnc[mor]);
											nDiffAncBestMor = bestDAnc[mor];
   	  			      	    			}
   	        	    				}	 
   	      		  	    					
									// Actually check also the second closest...
                                    double minAncMor2 = 2.0;
									for (int mor=0; mor<moroccanIndexes.length; mor++) {
                                    	if ( ( (double)(bestDAnc[mor])/(double)(bestLAnc[mor]) < minAncMor2) && ((double)(bestDAnc[mor])/(double)(bestLAnc[mor]) > minAncMor) && (bestLAnc[mor] >= 0.8*(hapEndAnc[hapMemAnc] - hapBeginAnc[hapMemAnc]) ) ) {
                                        	minAncMor2 = (double)(bestDAnc[mor])/(double)(bestLAnc[mor]);
                                        }
                                        bestDAnc[mor] = 0;
                                        bestLAnc[mor] = 0;
                                    }

   	        			    		// Find the closest iberian, but not because it misses a lot of data    	    	    				
    	    	    				double minAncIbp = 2.0;
   	        	    				for (int ibp=moroccanIndexes.length; ibp<moroccanIndexes.length + iberianIndexes.length; ibp++) {
   	        	    					if ( ( (double)(bestDAnc[ibp])/(double)(bestLAnc[ibp]) < minAncIbp) && (bestLAnc[ibp] >= 0.8*(hapEndAnc[hapMemAnc] - hapBeginAnc[hapMemAnc]) ) ) {
   	    			    	    			minAncIbp = (double)(bestDAnc[ibp])/(double)(bestLAnc[ibp]);
											nDiffAncBestIbp = bestDAnc[ibp];
   	        	    					}
   	        			    		}
   	        	    						
									// Actually check also the second closest...
									double minAncIbp2 = 2.0;
									for (int ibp=moroccanIndexes.length; ibp<moroccanIndexes.length + iberianIndexes.length; ibp++) {
										if ( ( (double)(bestDAnc[ibp])/(double)(bestLAnc[ibp]) < minAncIbp) && ((double)(bestDAnc[ibp])/(double)(bestLAnc[ibp]) > minAncIbp)&& (bestLAnc[ibp] >= 0.8*(hapEndAnc[hapMemAnc] - hapBeginAnc[hapMemAnc]) ) ) {
											minAncIbp2 = (double)(bestDAnc[ibp])/(double)(bestLAnc[ibp]);
										}
										bestDAnc[ibp] = 0;
                                        bestLAnc[ibp] = 0;
									}

   	        	    				// If the right pop is the closest by at least 2 SNPs, call a hap
									if (minAncMor < minAncIbp) {
										if (nDiffAncBestMor <= nDiffAncBestIbp - 2) {
   	        			    				for (int p=hapBeginAnc[hapMemAnc]; p<=hapEndAnc[hapMemAnc]; p++) {
   	        	    							ancestralAndNotMigrant[hapChrAnc[hapMemAnc]].set(p);
   	        	    						}
											// If the second closest is not from the right population... Keep it in, but count them.
											if (minAncMor2 > minAncIbp) {
												secondWrongAnc = secondWrongAnc + 1;
											}
											if (minAncMor2 > minAncIbp2) {
                            					secondWrongAnc2 = secondWrongAnc2 + 1;
                                            }
   	        	    					} else {
											// If there is less than 2 differences, but the hap is longer than the counterpart from the alternative source
											// Then recover it
											Boolean recovered = false;
											
											// Get length of this haplotype
											int lengthRight = hapEndAnc[hapMemAnc] - hapBeginAnc[hapMemAnc];
											
											// Now look at haplotypes of the other origin: overlap? Shorter or longer?
											for (int hapMig=0; hapMig<hapChrMig.length; hapMig++) {
												Boolean overlap2diff = false;
												for (int p=hapBeginAnc[hapMemAnc]; p<=hapEndAnc[hapMemAnc]; p++) {
													if ( (hapChrMig[hapMig] == chr) && (hapBeginMig[hapMig] <= p) && (hapEndMig[hapMig] >= p) ) {
														overlap2diff = true;
													}
												}    	    	
												if (overlap2diff) {
													int lengthAlternative = hapEndMig[hapMig] - hapBeginMig[hapMig];
													
													// check which is longer now
													if (lengthRight >= 10 + lengthAlternative) {
														// then recover it
														for (int p=hapBeginAnc[hapMemAnc]; p<=hapEndAnc[hapMemAnc]; p++) {
															ancestralAndNotMigrant[hapChrAnc[hapMemAnc]].set(p);
														}
														recovered = true;
													}
												}    						
											}
											if (!recovered) {
												for (int p=hapBeginAnc[hapMemAnc]; p<=hapEndAnc[hapMemAnc]; p++) {
													out2DiffAncHap = out2DiffAncHap + 1;
													outBecauseLessThan2DiffAnc[hapChrAnc[hapMemAnc]].set(p);
												}
											}
										}
									}
   	        	    				hapMemAnc = hapAnc;
  			  	    	    	} else {
    	    	    				// If we are in the same hap, just keep counting
        	    	       			char base1 = splitSnp[canarianIndexes[can]].charAt(0);
  	        	    	        	
  	        	    	        	// Moroccan side...
        	  			  		    for (int mor=0; mor<moroccanIndexes.length; mor++) {
        	    		        		char base2 = splitSnp[moroccanIndexes[mor]].charAt(0);
   	        	    		        	
   	        	    		        	if ( (base1 != 'N') && (base2 != 'N') ) {
        	    							bestLAnc[mor] = bestLAnc[mor] + 1;
   	        	    				    	if (base1 != base2) {
   	        	    							bestDAnc[mor] = bestDAnc[mor] + 1;
   	        	    	 					}
    	        	    	   			}
   			 	        	    	}
        	    					// ...and Iberian side
   	        	    				for (int ibp=0; ibp<iberianIndexes.length; ibp++) {
        	    		        		char base2 = splitSnp[iberianIndexes[ibp]].charAt(0);
   	        	    		        
        	    		        		if ( (base1 != 'N') && (base2 != 'N') ) {
        	    		        			bestLAnc[moroccanIndexes.length + ibp] = bestLAnc[moroccanIndexes.length + ibp] + 1;
   	        	    	        			if (base1 != base2) {
   	        	    	        				bestDAnc[moroccanIndexes.length + ibp] = bestDAnc[moroccanIndexes.length + ibp] + 1;
   	        	    		       			}
    	        	    		       	}
    	        	    			}
    	    	    			}	
    	    	    		}
    	    	  		}
    				}
    				
    				// Is the position in a potential migrant hap in this canarian?
    				
   					if (potentiallyNonAncestral[chr].get(pos)) {
    					
    					// which hap?
			    	    for (int hapMig=0; hapMig<hapChrMig.length; hapMig++) {
    	    	    		if ( (hapChrMig[hapMig] == chr) && (hapBeginMig[hapMig] <= pos) && (hapEndMig[hapMig] >= pos) ) {
    	    	    		
    	    	    			// Output the last hap, if we are in the next
    	    	    			if (hapMig > hapMemMig) {
    	    						// Check the density of differences to the right and wrong source
    	    	    				
    	    	    				// Find the closest moroccan, but not because it misses a lot of data    	    	    				
			    	    	  		double minMigMor = 2.0;
                	                int nDiffMigBestMor = 0;
                                    int nDiffMigBestIbp = 0;
                                    
									for (int mor=0; mor<moroccanIndexes.length; mor++) {
   	        	    					if ( ( (double)(bestDMig[mor])/(double)(bestLMig[mor]) < minMigMor) && (bestLMig[mor] >= 0.8*(hapEndMig[hapMemMig] - hapBeginMig[hapMemMig]) ) ) {
   	        	    						minMigMor = (double)(bestDMig[mor])/(double)(bestLMig[mor]);
   	        	    				        nDiffMigBestMor = bestDMig[mor];	
										}
   	        	    				}
									
									// Actually check also second closest...	
	   	        	 	   			double minMigMor2 = 2.0;
                                    for (int mor=0; mor<moroccanIndexes.length; mor++) {
                                       	if ( ( (double)(bestDMig[mor])/(double)(bestLMig[mor]) < minMigMor2) && ((double)(bestDMig[mor])/(double)(bestLMig[mor]) > minMigMor) && (bestLMig[mor] >= 0.8*(hapEndMig[hapMemMig] - hapBeginMig[hapMemMig]) ) ) {
                                           	minMigMor2 = (double)(bestDMig[mor])/(double)(bestLMig[mor]);
                                        }
                                        bestDMig[mor] = 0;
                                        bestLMig[mor] = 0;
                                    }
                                    
                                    // Find the closest iberian, but not because it misses a lot of data    	    	    				
    	    	    				double minMigIbp = 2.0;
   	        	    				for (int ibp=moroccanIndexes.length; ibp<moroccanIndexes.length + iberianIndexes.length; ibp++) {
   	        	    					if ( ( (double)(bestDMig[ibp])/(double)(bestLMig[ibp]) < minMigIbp) && (bestLMig[ibp] >= 0.8*(hapEndMig[hapMemMig] - hapBeginMig[hapMemMig]) ) ) {
   	        	    						minMigIbp = (double)(bestDMig[ibp])/(double)(bestLMig[ibp]);
   	        	    						nDiffMigBestIbp = bestDMig[ibp];
										}
   	        	  					}
   	        	  						
									// Actually check also second closest... 
									double minMigIbp2 = 2.0;
									for (int ibp=moroccanIndexes.length; ibp<moroccanIndexes.length + iberianIndexes.length; ibp++) {
										if ( ( (double)(bestDMig[ibp])/(double)(bestLMig[ibp]) < minMigIbp2) && ( (double)(bestDMig[ibp])/(double)(bestLMig[ibp]) > minMigIbp) && (bestLMig[ibp] >= 0.8*(hapEndMig[hapMemMig] - hapBeginMig[hapMemMig]) ) ) {
											minMigIbp2 = (double)(bestDMig[ibp])/(double)(bestLMig[ibp]);
										}
										bestDMig[ibp] = 0;
                                        bestLMig[ibp] = 0;
									}
   	        	    				
									// If the right pop is the closest by at least 2 SNPs, call a hap
									if (minMigIbp < minMigMor) {
										if (nDiffMigBestIbp <= nDiffMigBestMor - 2) {
											for (int p=hapBeginMig[hapMemMig]; p<=hapEndMig[hapMemMig]; p++) {
                                       	 		migrantAndNotancestral[hapChrMig[hapMemMig]].set(p);
                                           	}
											// If the second closest is not from the right population... Keep it in, but count them.
											if (minMigIbp2 > minMigMor) {
                                    	        secondWrongMig = secondWrongMig + 1;
                                            }
                                            if (minMigIbp2 > minMigMor2) {
                                        	    secondWrongMig2 = secondWrongMig2 + 1;
                                            }
										} else {
											// If there is less than 2 differences, but the hap is longer than the counterpart from the alternative source
											// Then recover it
											Boolean recovered = false;
											
											// Get length of this haplotype
											int lengthRight = hapEndMig[hapMemMig] - hapBeginMig[hapMemMig];
											
											// Now look at haplotypes of the other origin: overlap? Shorter or longer?
											for (int hapAnc=0; hapAnc<hapChrAnc.length; hapAnc++) {
												Boolean overlap2diff = false;
												for (int p=hapBeginMig[hapMemMig]; p<=hapEndMig[hapMemMig]; p++) {
													if ( (hapChrAnc[hapAnc] == chr) && (hapBeginAnc[hapAnc] <= p) && (hapEndAnc[hapAnc] >= p) ) {
														overlap2diff = true;
													}
												}
												if (overlap2diff) {
													int lengthAlternative = hapEndAnc[hapAnc] - hapBeginAnc[hapAnc];
													
													// check which is longer now
													if (lengthRight >= 10 + lengthAlternative) {
														// then recover it
														for (int p=hapBeginMig[hapMemMig]; p<=hapEndMig[hapMemMig]; p++) {
															migrantAndNotancestral[hapChrMig[hapMemMig]].set(p);
														}
														recovered = true;
													}
												}
											}
											if (!recovered) {
												for (int p=hapBeginMig[hapMemMig]; p<=hapEndMig[hapMemMig]; p++) {
													outBecauseLessThan2DiffMig[hapChrMig[hapMemMig]].set(p);
													out2DiffMigHap = out2DiffMigHap + 1;
												}
											}
										}
									}
   	        	   		 			hapMemMig = hapMig;
    	    	    			} else {
    	    	    				// If we are in the same hap, just keep counting
        	    	       			char base1 = splitSnp[canarianIndexes[can]].charAt(0);
  	        	    	        	
        	    	        		// Moroccan side...
     			   	    		    for (int mor=0; mor<moroccanIndexes.length; mor++) {
        	    				    	char base2 = splitSnp[moroccanIndexes[mor]].charAt(0);
   	        	    				 	
        	    		       			if ( (base1 != 'N') && (base2 != 'N') ) {
        	    		    				bestLMig[mor] = bestLMig[mor] + 1;
   	        	    	        			if (base1 != base2) {
   	        	    	        				bestDMig[mor] = bestDMig[mor] + 1;
   	        	    		        		}
    	        	    		       	}
    	        	    			}
        	    				    // ...and Iberian side
   	        	    				for (int ibp=0; ibp<iberianIndexes.length; ibp++) {
        	    		        		char base2 = splitSnp[iberianIndexes[ibp]].charAt(0);
   	        	    		        	
        	    		        		if ( (base1 != 'N') && (base2 != 'N') ) {
        	    		        			bestLMig[moroccanIndexes.length + ibp] = bestLMig[moroccanIndexes.length + ibp] + 1;
   	        	    	        			if (base1 != base2) {
   	        	    	        				bestDMig[moroccanIndexes.length + ibp] = bestDMig[moroccanIndexes.length + ibp] + 1;
   	        	    				        }
    	        	    		     	}
    	        	    			}
    	    	    			}	
    	    	    		}
    			    	}
    				}
    			}
		    	
				
				
				
				
				
				// See how much of the genome is from either source 
				cardAnc = 0;
		    	cardMig = 0;
				for (int c=0; c<5; c++) {
		    		cardAnc = cardAnc + ancestralAndNotMigrant[c].cardinality();
		    		cardMig = cardMig + migrantAndNotancestral[c].cardinality();
		    		
					System.out.println("lAnc, chr " + c + " " + ancestralAndNotMigrant[c].length());
					System.out.println("lMig, chr " + c + " " + migrantAndNotancestral[c].length());
				}
		    	System.out.println("Ancestral and not migrant - with overlap: " + cardAnc);
		    	System.out.println("Migrant and not ancestral - with overlap: " + cardMig);
		    	
		    	
		    	
		    	
		    	
				// Check that overlap is not great
				BitSet[] overlap = new BitSet[5];
		    	for (int c=0; c<5; c++) {
		    		overlap[c] = new BitSet();
		    		overlap[c].or(migrantAndNotancestral[c]);
		    		overlap[c].and(ancestralAndNotMigrant[c]);
		    	}
		    	
		    	// See how much of the genome is overlap 
				int cardOverlap = 0;
		    	for (int c=0; c<5; c++) {
		    		cardOverlap = cardOverlap + overlap[c].cardinality();
				}
		    	System.out.println("Overlap between ancestral and migrant: " + cardOverlap);		    	
		    	
		    	
		    	
		    	
		    	// What nature has this overlap?
		    	
		    	
		    	if (cardOverlap != 0) {
					
					// HapOverlap[0,1]: 
					// Small haps from Morocco (ibp) nested in big hap of the other
					// Ibp				-------------
					// Mor					---			
					// HapOverlap[2]: 
					// Overlap at an end of both haps?
					// Ibp				-------------
					// Mor		-------------		
				
				
					int[] hapOverlap = new int[3];
					for (int i=0; i<hapOverlap.length; i++) {
						hapOverlap[i] = 0; 
					}
				
					for (int c=0; c<5; c++) {
						Boolean inAhap = false;
					
						// HapEntrance: 
						// 0 if it was ancestral before the overlap 
						// 1 if it was migrant 
						// 2 if it was neither - should not happen that they were overlapping already
						int hapEntrance = 3;
						int hapExit = 3;
					
						for (int p=0; p<overlap[c].length(); p++) {
							if (overlap[c].get(p)) {
							
								if (!inAhap) {
									// Entering an overlap region, check what happened before
									if (ancestralAndNotMigrant[c].get(p - 1)) {
										hapEntrance = 0;
									}
									if (migrantAndNotancestral[c].get(p - 1)) {	
										hapEntrance = 1;
									}
									if (!ancestralAndNotMigrant[c].get(p - 1) && !migrantAndNotancestral[c].get(p - 1)) {	
										hapEntrance = 2;
									}
								}
								inAhap = true;
							} else {
							
								if (inAhap) {
									
									// Exiting an overlap region, check what happens after
									if (ancestralAndNotMigrant[c].get(p + 1)) {
										hapExit = 0;
									}
									if (migrantAndNotancestral[c].get(p + 1)) {	
										hapExit = 1;
									}
									if (!ancestralAndNotMigrant[c].get(p + 1) && !migrantAndNotancestral[c].get(p + 1)) {	
										hapExit = 2;
									}
								
									// This overlapHap is over - record the pattern
									if ( ( ( (hapEntrance == 0) || (hapEntrance == 2)) && (hapExit == 0) ) || ( (hapEntrance == 0) && ( (hapExit == 0) || (hapExit == 2) ) ) ) {
										hapOverlap[0] = hapOverlap[0] + 1;
									}
									if ( ( ( (hapEntrance == 1) || (hapEntrance == 2)) && (hapExit == 1) ) || ( (hapEntrance == 1) && ( (hapExit == 1) || (hapExit == 2) ) ) ) {
										hapOverlap[1] = hapOverlap[1] + 1;
									}
									if ( ( (hapEntrance == 0) && (hapExit == 1) ) || ( (hapEntrance == 1) && (hapExit == 0) )  ) {
										hapOverlap[2] = hapOverlap[2] + 1;
									}
								}
								hapEntrance = 3;
								hapExit = 3;
								inAhap = false;
							}
						}
					}
				
					System.out.println("patterns of overlaps: ");
					System.out.println("Moroccan hap nested in Iberian hap: " + "\t" + hapOverlap[0]);
					System.out.println("Iberian hap nested in Moroccan hap: " + "\t" + hapOverlap[1]);
					System.out.println("no nesting, just ends overlapping: " + "\t" + hapOverlap[2]);
				
		    	} else {
		    		System.out.println("Null overlap!! YEEEE no problems!!!");
		    	}
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	// Do the real private haps
		    	
		    	for (int c=0; c<5; c++) {
		    		ancestralAndNotMigrant[c].andNot(migrantAndNotancestral[c]);
		    		migrantAndNotancestral[c].andNot(ancestralAndNotMigrant[c]);
		    	}
		    	
		    	cardAnc = 0;
		    	cardMig = 0;
		    	for (int c=0; c<5; c++) {
		    		cardAnc = cardAnc + ancestralAndNotMigrant[c].cardinality();
		    		cardMig = cardMig + migrantAndNotancestral[c].cardinality();
		    	}
		    	System.out.println("Ancestral and not migrant - overlap eliminated: " + cardAnc);
		    	System.out.println("Migrant and not ancestral - overlap eliminated: " + cardMig);
		    				
				
				
				// Check how big the
				//  outBecauseLessThan2DiffMig[c] = new BitSet();
				//
				int out2DiffAnc = 0;
                int out2DiffMig = 0;
				for (int c=0; c<5; c++) {
                	out2DiffAnc = out2DiffAnc + outBecauseLessThan2DiffAnc[c].cardinality();
                    out2DiffMig = out2DiffMig + outBecauseLessThan2DiffMig[c].cardinality();
                    
                    // System.out.println("out2Anc, chr " + c + " " + outBecauseLessThan2DiffAnc[c].length());
                    // System.out.println("out2Mig, chr " + c + " " + outBecauseLessThan2DiffMig[c].length());
                }
                System.out.println("#bases eliminated because less than 2 differences - Ancestral: " + out2DiffAnc);
                System.out.println("#bases eliminated because less than 2 differences - Migrant: " + out2DiffMig);
                
		System.out.println("#haplotypes eliminated because less than 2 differences - Ancestral: " + out2DiffAncHap);
                System.out.println("#haplotypes eliminated because less than 2 differences - Migrant: " + out2DiffMigHap);
				
		System.out.println("Ancestral haps: #cases in which the second best sample from Morocco is worse than the best iberian: " + secondWrongAnc);
                System.out.println("Ancestral haps: #cases in which the second best sample from Morocco is worse than the second best iberian: " + secondWrongAnc2);
                System.out.println("Migrant haps: #cases in which the second best sample from Iberia is worse than the best moroccan: " + secondWrongMig);
                System.out.println("Migrant haps: #cases in which the second best sample from Iberia is worse than the second best moroccan: " + secondWrongMig2);
		    	
		    	
		    	
		    	
		    	// Put it out!
		    	
				Writer writer = new FileWriter(whereWe + "hapCalls_YeaCutN_aged" + can + ".txt"); 
				PrintWriter out = new PrintWriter(writer);
			
				out.print(name + "\n");
				out.print("hapChr" + "\t" + "hapBegin" + "\t" + "hapEnd" + "\t" + "origin" + "\n");
			
				// 4 define borders and numbers of real admixture tracts
				
				// Define haplotypes - rrrrrreally ancestral - from D2_Hap_age_chromoP
				
				memPos = 0;
				numAncHaps = 0;
				System.out.println("Counting haps...");
				
				// Let's count haplotypes per can
				for (int c=0; c<5; c++) {
					memPos = 0;
					for (int p=0; p<ancestralAndNotMigrant[c].length(); p++) {
						if (ancestralAndNotMigrant[c].get(p)) {
						
							// First position behaves different
							if (memPos != 0) {
								// Did we jump a gap?
								if (memPos != p-1) {
									numAncHaps = numAncHaps + 1;
								}
								memPos = p;
							} else {
								// starting from the second p, activate memPos
								memPos = p;								
							}
						}
					}
					numAncHaps = numAncHaps + 1;
				}
				System.out.println("We have " + numAncHaps + " real ancestral haps");
			
				// Let's find breakpoints and chromosomes
				hapChrAnc = new int[numAncHaps];
				hapBeginAnc = new int[numAncHaps];
				hapEndAnc = new int[numAncHaps];
				numAncHaps = 0;
			
				for (int c=0; c<5; c++) {
					memPos = 0;
					for (int p=0; p<ancestralAndNotMigrant[c].length(); p++) {
						if (ancestralAndNotMigrant[c].get(p)) {
						
							// First position behaves different
							if (memPos != 0) {
								// Did we jump a gap?
								if (memPos != p-1) {
									hapEndAnc[numAncHaps] = memPos;
									numAncHaps = numAncHaps + 1;
									hapBeginAnc[numAncHaps] = p;
									hapChrAnc[numAncHaps] = c;
								}
								memPos = p;
							} else {
								hapBeginAnc[numAncHaps] = p;
								hapChrAnc[numAncHaps] = c;
								// starting from the second p, activate memPos
								memPos = p;								
							}
						}
					}
					hapEndAnc[numAncHaps] = ancestralAndNotMigrant[c].length() - 1;
					numAncHaps = numAncHaps + 1;
				}
				System.out.println("Working on: " + can + " there are: " + hapChrAnc.length + " ancestral haplotypes");
			
				// END from B_HapFS
			
			
			
				System.out.println("Printing...");
				for (int hap=0; hap<hapChrAnc.length; hap++) {
					out.print(hapChrAnc[hap] + "\t" + hapBeginAnc[hap] + "\t" + hapEndAnc[hap] + "\t" + "ANC" + "\n");
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				

			// Define haplotypes - rrrrrreally migrant - from D2_Hap_age_chromoP
			
			memPos = 0;
			numMigHaps = 0;
		    System.out.println("Counting haps...");
		    	
			// Let's count haplotypes per can
			for (int c=0; c<5; c++) {
				memPos = 0;
				for (int p=0; p<migrantAndNotancestral[c].length(); p++) {
					if (migrantAndNotancestral[c].get(p)) {
						
						// First position behaves different
						if (memPos != 0) {
							// Did we jump a gap?
					    	if (memPos != p-1) {
					    		numMigHaps = numMigHaps + 1;
							}
					    	memPos = p;
						} else {
							// starting from the second p, activate memPos
					    	memPos = p;								
						}
					}
				}
				numMigHaps = numMigHaps + 1;
			}
			System.out.println("We have " + numMigHaps + " real migrant haps");
			
			// Let's find breakpoints and chromosomes
			hapChrMig = new int[numMigHaps];
			hapBeginMig = new int[numMigHaps];
			hapEndMig = new int[numMigHaps];
			numMigHaps = 0;
			
			for (int c=0; c<5; c++) {
				memPos = 0;
				for (int p=0; p<migrantAndNotancestral[c].length(); p++) {
					if (migrantAndNotancestral[c].get(p)) {
						
						// First position behaves different
						if (memPos != 0) {
							// Did we jump a gap?
					    	if (memPos != p-1) {
					    		hapEndMig[numMigHaps] = memPos;
					    		numMigHaps = numMigHaps + 1;
								hapBeginMig[numMigHaps] = p;
								hapChrMig[numMigHaps] = c;
							}
					    	memPos = p;
						} else {
							hapBeginMig[numMigHaps] = p;
							hapChrMig[numMigHaps] = c;
							// starting from the second p, activate memPos
					    	memPos = p;								
						}
					}
				}
				hapEndMig[numMigHaps] = migrantAndNotancestral[c].length() - 1;
				numMigHaps = numMigHaps + 1;
			}
			System.out.println("Working on: " + can + " there are: " + hapChrMig.length + " migrant haplotypes");
		    
			// END from B_HapFS
		   	
			
			
			System.out.println("Printing...");
			for (int hap=0; hap<hapChrMig.length; hap++) {
				out.print(hapChrMig[hap] + "\t" + hapBeginMig[hap] + "\t" + hapEndMig[hap] + "\t" + "MIG" + "\n");
			}
			out.close();
			
	    	
		    System.out.println("Finished this one!" + "\n");
		    // Per can
		}
			
			
			
			
			
			/*
			
			// 6 Somehow output these positions - to plot, and to charge in faster next time
			
			// Call the chromo-like matrix

			// All genome in a row
			
			Writer writerAllGenomeP = new FileWriter(hapPartialCall + "positionsChromoLike_mycalls_agedHapCalls.txt"); 
			PrintWriter outwriterAllGenomeP = new PrintWriter(writerAllGenomeP);
			
			Writer writerwriterAllGenomeM = new FileWriter(hapPartialCall + "matrixChromoLike_mycalls_agedHapCalls.txt"); 
			PrintWriter outwriterAllGenomeM = new PrintWriter(writerwriterAllGenomeM);
			
			int[] centromeres = {15084050, 3616850, 13590100, 3953300, 11705550};
			int[] centPos = {0, 0, 0, 0, 0};
			boolean[] centIn = {false, false, false, false, false};
			int[] chrTresholds = {0, 0, 0, 0, 0, 0};


			for (int c=0; c<5; c++) {
				
				// WITH MOROCCO AND MACARONESIAN AS ANCESTORS
				
				int fixHaps = 0;
				
				Writer writerPC = new FileWriter(hapPartialCall + "positionsChromoLike_mycalls_agedHapCalls_" + c + ".txt"); 
				PrintWriter outPC = new PrintWriter(writerPC);
				
				Writer writerM = new FileWriter(hapPartialCall + "matrixChromoLike_mycalls_agedHapCalls_" + c + ".txt"); 
				PrintWriter outM = new PrintWriter(writerM);
				
				// Which is longer? Macaronesian or non?
				
				int endChr = 0;
				for (int can=0; can<nCan; can++) {
					
					// Find the end of chromosome
					if (ancestralAndNotMigrant[can][c].length() > endChr) {
						endChr = ancestralAndNotMigrant[can][c].length();
					}
					if (migrantAndNotancestral[can][c].length() > endChr) {
						endChr = migrantAndNotancestral[can][c].length();
					}
				}
				endChr = endChr + 1;
				
				
				// Now write that: 0: uncertain 1: strictly Macaronesian 2: Strictly nonMac
				
				int[] pattern = new int[nCan];
				int[] patternMemory = new int[nCan];
				for (int pat=0; pat<pattern.length; pat++) {
					pattern[pat] = 0;
					patternMemory[pat] = 0;
				}
				
				// Run through all positions in this chromosome
				int pMemory = 0;
				for (int p=0; p<endChr; p++) {
					
					// Build up pattern, for all canarians
					for (int can=0; can<nCan; can++) {
						if (!ancestralAndNotMigrant[can][c].get(p) && !migrantAndNotancestral[can][c].get(p)) {
							pattern[can] = 0;
						}
						if (ancestralAndNotMigrant[can][c].get(p) && !migrantAndNotancestral[can][c].get(p)) {
							pattern[can] = 1;
						}
						if (!ancestralAndNotMigrant[can][c].get(p) && migrantAndNotancestral[can][c].get(p)) {
							pattern[can] = 2;
						}
					}
					// Check if pattern differs from previous positions 
					boolean same = true;
					boolean fixed = true;
					for (int can=0; can<nCan; can++) {
						if (pattern[can] != patternMemory[can]) {
							same = false;
						}
						if (pattern[can] != 1) {
							fixed = false;
						}
					}
					// If it changed, print out position and pattern
					if (!same) {
						if (fixed) {
							fixHaps = fixHaps + 1;
						}
						// Just for the first position
						if (p == 1) {
							
							// Print position
							outPC.print( (p + chrTresholds[c]) + " ");
							outwriterAllGenomeP.print((p + chrTresholds[c]) + " ");
							pMemory = p;
							
							// Print Pattern
							for (int can=0; can<nCan; can++) {
								outM.print(pattern[canInOrder[can]] + " ");
								outwriterAllGenomeM.print(pattern[canInOrder[can]] + " ");
							}
							outM.print("\n");
							outwriterAllGenomeM.print("\n");
						}
						
						// For the whole genome in the middle
						if ( (p > 0) && (p < (endChr-1) ) ) {
							
							// If we jump a region, print the last position out
							if (pMemory != (p-1) ) {
								
								// Print position
								outPC.print( ( (p + chrTresholds[c]) - 1) + " ");
								outwriterAllGenomeP.print( ( (p + chrTresholds[c]) - 1) + " ");

								// Print Pattern
								for (int can=0; can<nCan; can++) {
									outM.print(patternMemory[canInOrder[can]] + " ");
									outwriterAllGenomeM.print(patternMemory[canInOrder[can]] + " ");
								}
								outM.print("\n");
								outwriterAllGenomeM.print("\n");
							}
							
							// In case there was no jump, print the position of the change.
							// In case there was the jump, print the new start position
							// Either case, print it :)
							
							// Print position
							outPC.print( (p + chrTresholds[c]) + " ");
							outwriterAllGenomeP.print(  (p + chrTresholds[c]) + " ");
							pMemory = p;
							
							// Print Pattern
							for (int can=0; can<nCan; can++) {
								outM.print(pattern[canInOrder[can]] + " ");
								outwriterAllGenomeM.print(pattern[canInOrder[can]] + " ");
							}
							outM.print("\n");
							outwriterAllGenomeM.print("\n");
							
							// Check out centromeres
							if ( (p >= centromeres[c]) && (!centIn[c]) ) {
								centPos[c] = (p + chrTresholds[c]);
								centIn[c] = true;
							}
							
						}
					}
					// Last position
					if (p == endChr-1) {
						
						// Print position
						outPC.print(  (p + chrTresholds[c]) + " ");
						outwriterAllGenomeP.print(  (p + chrTresholds[c]) + " ");
						
						// Print Pattern
						for (int can=0; can<nCan; can++) {
							outM.print(patternMemory[canInOrder[can]] + " ");
							outwriterAllGenomeM.print(patternMemory[canInOrder[can]] + " ");
						}
						outM.print("\n");
						outwriterAllGenomeM.print("\n");
					}
					
					for (int pat=0; pat<pattern.length; pat++) {
						patternMemory[pat] = pattern[pat];
					}
				}
				System.out.println("Fixed: " + fixHaps);
				
				
				// Get chromosomal threshold
				for (int chrom=c; chrom<5; chrom++) {
					chrTresholds[chrom+1] = chrTresholds[chrom+1] + endChr + 1;
				}
				
				outPC.print("\n");
				outPC.close();
				outM.close();

				// END
			
			}
			outwriterAllGenomeP.print("\n");
			
			// Print chromosomal threshold
			outwriterAllGenomeP.print("chrTresholds: " + "\n");
			for (int c=0; c<chrTresholds.length; c++) {
				outwriterAllGenomeP.print(chrTresholds[c] + " ");
			}
			outwriterAllGenomeP.print("\n");
			
			// Print centromeres
			outwriterAllGenomeP.print("centromeres: " + "\n");
			for (int c=0; c<centPos.length; c++) {
				outwriterAllGenomeP.print(centPos[c] + " ");
			}
			outwriterAllGenomeP.print("\n");
			
			outwriterAllGenomeP.close();
			outwriterAllGenomeM.close();
			
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
        public static void main(String[] args) {
		Haplo_pullDataForChromoLikePlot_agedHapCalls haplo_pullDataForChromoLikePlot_agedHapCalls = new Haplo_pullDataForChromoLikePlot_agedHapCalls();
		haplo_pullDataForChromoLikePlot_agedHapCalls.fromHapCallsToHapFS(args[0], args[1]);
	}
}
