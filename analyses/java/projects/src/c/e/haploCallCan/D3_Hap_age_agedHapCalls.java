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

public class D3_Hap_age_agedHapCalls {
	
	private int whichCountry = 0;
	private D3_Hap_age_agedHapCalls() {}
	
	public void fromHapCallsToHapFS(String canarian) {
		try {
			
			int accession = Integer.parseInt(canarian);

			String hapPartialCall = "/home/lv70590/Andrea/analyses/haplotypeCallsCan/";
			// From HaplotypeMonsterEater
			// File with 2 lines: tab separated int IDS for 1001 plus macaronesian plants, 
			// and in the second line, same order, country code for the same plant
			File idsCountryFile = new File("/home/lv70590/Andrea/analyses/haplotypeCallsCan/idsCountry_01-2016.txt");
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
	    	File matrix = new File("/home/lv70590/Andrea/analyses/haplotypeCallsCan/newBies02-16_matrix.txt_clean2.txt");
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
			
			// Linked to country. Now we have int Cvi-0, Col-0 index, and int[] for Iberian indexes.
			// From HaplotypeMonsterEater END
						
						
			
			
			
			
			
			
			
			
			
			
			
			

			
			// From B_HapFS
			
			// Focus on ANCESTRAL haplotypes:
			boolean anc=true;
			
			// Or Migrant?
			anc=false;
			
			if (anc) {
				System.out.println("Focus on ancestral haps");
			} else {
				System.out.println("Focus on migrant haps");
			}
			
			// Count samples
			int nCan = 47;
			int chromosomes = 5;
			System.out.println("There are: " + nCan + " canarians");
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	    	
	    	
	    	
	    	
			// MY PIPELIENE:
				    	
	    	// From A_pullAncestralMigrantPositions.java
	    	
			// Get all info on one class of haplotypes in bitset hapOfInterest

		    // 3 charge the right haps
			
			// From Haplo_plotAgedHapCalls
			
			// Open aged hap calls

			BitSet[][] ancestralAndNotMigrant = new BitSet[nCan][chromosomes];
			BitSet[][] migrantAndNotancestral = new BitSet[nCan][chromosomes];
			for (int can=0; can< nCan; can++) {
				for (int c=0; c<chromosomes; c++) {
					ancestralAndNotMigrant[can][c] = new BitSet();
					migrantAndNotancestral[can][c] = new BitSet();
				}
			}
			String[] names = new String[nCan];
		    
			for (int can=accession; can<accession + 1; can++) {
				
				File partialCallFile = new File(hapPartialCall + "haploCallsCan_MorAnc_YeaCutN_noMatches_4aged" + can + ".txt");
				Scanner scannerpartialCall = new Scanner(partialCallFile);
				String nameLine = scannerpartialCall.nextLine();
				String name = nameLine.split(" ")[nameLine.split(" ").length - 1];
				scannerpartialCall.nextLine();
				System.out.println(name);
				names[can] = name;
				
				
		    	while (scannerpartialCall.hasNextLine()) {
					String hap = scannerpartialCall.nextLine();
			    	String[] splithap = hap.split("\t");
			    	
		    		int chr = Integer.parseInt(splithap[0]);
		    		int startHap = Integer.parseInt(splithap[1]);
		    		int endHap = Integer.parseInt(splithap[2]);
		    		String ancestry = splithap[3];
		    		
		    		if (ancestry.equals("ANC")) {
		    			for (int p = startHap; p<=endHap; p++ ) {
		    				ancestralAndNotMigrant[can][chr].set(p);
		    			}
		    		}
		    		if (ancestry.equals("MIG")) {
		    			for (int p = startHap; p<=endHap; p++ ) {
		    				migrantAndNotancestral[can][chr].set(p);
		    			}
		    		}
		    	}
		    	for (int c=0; c<5; c++) {
		    		System.out.println("Can: " + can + " chr: " + c + "ancestor Length: " + ancestralAndNotMigrant[can][c].length());
		    		System.out.println("Can: " + can + " chr: " + c + "migrant Length: " + migrantAndNotancestral[can][c].length());
		    	}
			}
			
			// From Haplo_plotAgedHapCalls End
			
			
			
			
			
			
			
			
	    	BitSet[][] hapOfInterest = new BitSet[nCan][chromosomes];
			
			for (int can=0; can< nCan; can++) {
				for (int c=0; c<chromosomes; c++) {
					if (anc) {
						hapOfInterest[can][c] = new BitSet();
						hapOfInterest[can][c].or(ancestralAndNotMigrant[can][c]);
						hapOfInterest[can][c].andNot(migrantAndNotancestral[can][c]);
					} else {
						hapOfInterest[can][c] = new BitSet();
						hapOfInterest[can][c].or(migrantAndNotancestral[can][c]);
						hapOfInterest[can][c].andNot(ancestralAndNotMigrant[can][c]);
					}
					ancestralAndNotMigrant[can][c] = new BitSet();
					migrantAndNotancestral[can][c] = new BitSet();
				}
			}
						
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			// Define haplotypes - from B_HapFS_agedCalls
			
			// Define haplotypes - rrrrrreally ancestral - from D2_Hap_age_chromoP
			
			int[] memPos = new int[nCan];
			int[] numHaps = new int[nCan];
	    	System.out.println("Counting haps...");
	    	
			// Let's count haplotypes per can
			for (int can=0; can<nCan; can++) {
				memPos[can] = 0;
				numHaps[can] = 0;
				for (int c=0; c<5; c++) {
					memPos[can] = 0;
					for (int p=0; p<hapOfInterest[can][c].length(); p++) {
						if (hapOfInterest[can][c].get(p)) {
							
							// First position behaves different
							if (memPos[can] != 0) {
								// Did we jump a gap?
						    	if (memPos[can] != p-1) {
						    		numHaps[can] = numHaps[can] + 1;
								}
						    	memPos[can] = p;
							} else {
								// starting from the second p, activate memPos
						    	memPos[can] = p;								
							}
						}
					}
					numHaps[can] = numHaps[can] + 1;
				}
				System.out.println("Can: " + can + " have " + numHaps[can] + "haps");
			}
			
			
			// Let's find breakpoints and chromosomes
			
			int[][] hapChr = new int[nCan][];
			int[][] hapBegin = new int[nCan][];
			int[][] hapEnd = new int[nCan][];
			
			for (int can=0; can<nCan; can++) {
				hapChr[can] = new int[numHaps[can]];
				hapBegin[can] = new int[numHaps[can]];
				hapEnd[can] = new int[numHaps[can]];
				numHaps[can] = 0;
				
				
				for (int c=0; c<5; c++) {
					memPos[can] = 0;
					for (int p=0; p<hapOfInterest[can][c].length(); p++) {
						if (hapOfInterest[can][c].get(p)) {
							
							// First position behaves different
							if (memPos[can] != 0) {
								// Did we jump a gap?
						    	if (memPos[can] != p-1) {
						    		hapEnd[can][numHaps[can]] = memPos[can];
						    		numHaps[can] = numHaps[can] + 1;
									hapBegin[can][numHaps[can]] = p;
									hapChr[can][numHaps[can]] = c;
								}
						    	memPos[can] = p;
							} else {
								hapBegin[can][numHaps[can]] = p;
								hapChr[can][numHaps[can]] = c;
								// starting from the second p, activate memPos
						    	memPos[can] = p;								
							}
						}
					}
					hapEnd[can][numHaps[can]] = hapOfInterest[can][c].length() - 1;
					numHaps[can] = numHaps[can] + 1;
				}
			}
			System.out.println("Finitooooo... Ma solo di caricare le maschere...");
			
			// END from B_HapFS_agedCalls
	    	
	    	
	    			    
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

	    	
	    	
			//
			// Now the real game
			// 
			// From D2_Hap_age_chromoP

        	// Begin to calculate pairwise differences
        	
		    
		    int[] comparisonIndexes = null;
		    
			if (anc) {
				comparisonIndexes = moroccanIndexes;
			} else {
				comparisonIndexes = iberianIndexes;
			}
			
		    
			int[][] differences = new int[nCan][];
			int[][] length = new int[nCan][];
			double[][] pi = new double[nCan][];
			int[][] closestGuys = new int[nCan][];
			for (int can=0;can< nCan; can++) {
				differences[can] = new int[hapChr[can].length];
				length[can] = new int[hapChr[can].length];
				pi[can] = new double[hapChr[can].length];
				closestGuys[can] = new int[hapChr[can].length];
    	    	
				for (int hap=0; hap<hapChr[can].length; hap++) {
    	    		pi[can][hap] = 2.0;
    	    		differences[can][hap] = 0;
    	    		length[can][hap] = 0;
    	    	}
			}
			int chr =0;
			int[] hapMem = new int[nCan];
			int[][] bestD = new int[nCan][comparisonIndexes.length];
			int[][] bestL = new int[nCan][comparisonIndexes.length];
			
	    	scannerMatrix = new Scanner(matrix);
	    	scannerMatrix.nextLine();
	    	
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Just print where we are
        		if (chr != Integer.parseInt(splitSnp[0]) -1) {
        			System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
        		}
        		chr = Integer.parseInt(splitSnp[0]) - 1;
        		int pos = Integer.parseInt(splitSnp[1]);
        		
        		
        		
    			for (int can=accession ;can<accession + 1; can++) {
    				
    				// Is the position called a hap in this canarian?
    				if (hapOfInterest[can][chr].get(pos)) {
    					
    					// which hap?
    	    	    	for (int hap=0; hap<hapChr[can].length; hap++) {
    	    	    		if ( (hapChr[can][hap] == chr) && (hapBegin[can][hap] <= pos) && (hapEnd[can][hap] >= pos) ) {
    	    	    			
    	    	    			// Output the last hap, if we are in the next
    	    	    			if (hap > hapMem[can]) {
    	    	    				// Find the best, and put it in pi, length, distances
   	        	    				for (int acc2=0; acc2<comparisonIndexes.length; acc2++) {
   	        	    					// Check that it is the closest sample, but also that it is not the closest because it misses a lot of data
   	        	    					if ( ( (double)(bestD[can][acc2])/(double)(bestL[can][acc2]) < pi[can][hapMem[can]]) && (bestL[can][acc2] >= 0.8*(hapEnd[can][hapMem[can]] - hapBegin[can][hapMem[can]]) ) ) {
   	        	    						pi[can][hapMem[can]] = (double)(bestD[can][acc2])/(double)(bestL[can][acc2]);
   	        	    						differences[can][hapMem[can]] = bestD[can][acc2];
   	        	    						length[can][hapMem[can]] = bestL[can][acc2];
   	        	    						closestGuys[can][hapMem[can]] = Integer.parseInt(splitIDs[comparisonIndexes[acc2]]);
   	        	    					}
   	        	    					bestD[can][acc2] = 0;
   	        	    					bestL[can][acc2] = 0;
   	        	    				}
   	        	    				hapMem[can] = hap;
    	    	    			} else {
    	    	    				// If we are in the same hap, just keep counting
        	    	        		char base1 = splitSnp[canarianIndexes[can]].charAt(0);
  	        	    	        		
        	    		        	for (int acc2=0; acc2<comparisonIndexes.length; acc2++) {
        	    		        		char base2 = splitSnp[comparisonIndexes[acc2]].charAt(0);
   	        	    		        	
        	    		        		if ( (base1 != 'N') && (base2 != 'N') ) {
        	    		        			bestL[can][acc2] = bestL[can][acc2] + 1;
   	        	    	        			if (base1 != base2) {
   	        	    	        				bestD[can][acc2] = bestD[can][acc2] + 1;
   	        	    		        		}
    	        	    		        }
    	        	    			}
    	    	    			}	
    	    	    		}
    	    	    	}
    				}
    			}
    		}
	    	
			
			
			// Also, pull out the set of closest Iberians to iberian haps, 
			// and closest Moroccans to moroccan haps
			
			Writer writerClosest = null;
			if (anc) {
				writerClosest = new FileWriter(hapPartialCall + "ancestral_closestSamples_myAgedHapCalls_" + accession + ".txt"); 
			} else {
				writerClosest = new FileWriter(hapPartialCall + "migrant_closestSamples_myAgedHapCalls_" + accession + ".txt"); 
			}
			PrintWriter outClosest = new PrintWriter(writerClosest);			
			
			for (int can=accession; can<accession + 1; can++) {
				outClosest.print(names[can] + "\n");
				for (int hap=0; hap<closestGuys[can].length; hap++) {
					outClosest.print(closestGuys[can][hap] + "\t");
				}
				outClosest.print("\n");
			}
			outClosest.close();
			
			
			
			
			
			
	    	
			// Output all positions at which in at least one sample there is an ancestral call
			Writer writer = null;
			if (anc) {
				writer = new FileWriter(hapPartialCall + "ancestral_age_myAgedHapCalls_" + accession + ".txt"); 
			} else {
				writer = new FileWriter(hapPartialCall + "migrant_age_myAgedHapCalls_" + accession + ".txt"); 
			}
			PrintWriter out = new PrintWriter(writer);
			
			for (int can=accession; can<accession + 1; can++) {
				out.print(names[can] + "\t");
			}
			out.print("\n");				
			
			out.print("Pi: \n");
			for (int can=accession; can<accession + 1; can++) {
    	    	for (int hap=0; hap<pi[can].length; hap++) {
    	    		if (pi[can][hap] <= 1.0) {
    					out.print(pi[can][hap] + "\t");
    	    		}
				}
				out.print("\n");				
			}

			int lengthTot[] = new int[nCan];
			out.print("Length: \n");
			for (int can=accession; can<accession+1; can++) {
    	    	for (int hap=0; hap<pi[can].length; hap++) {
    	    		if (pi[can][hap] <= 1.0) {
    					out.print(length[can][hap] + "\t");
    					lengthTot[can] = lengthTot[can] + length[can][hap];
    	    		}
				}
				out.print("\n");				
			}
			
			int diffTot[] = new int[nCan];
			out.print("Differences: \n");
			for (int can=accession; can<accession + 1; can++) {
    	    	for (int hap=0; hap<pi[can].length; hap++) {
    	    		if (pi[can][hap] <= 1.0) {
    					out.print(differences[can][hap] + "\t");
    					diffTot[can] = diffTot[can] + differences[can][hap];
    	    		}
				}
				out.print("\n");				
			}
			
		out.print("\n");
                        out.close();






			Writer writerM = null;
                        if (anc) {
                                writerM = new FileWriter(hapPartialCall + "ancestral_ageMean_myAgedHapCalls_" + accession + ".txt");
                        } else {
                                writerM = new FileWriter(hapPartialCall + "migrant_ageMean_myAgedHapCalls_" + accession + ".txt");
                        }
                        PrintWriter outM = new PrintWriter(writerM);

                        for (int can=accession; can<accession + 1; can++) {
	
			// Put out a mean
			
				outM.print((double)(diffTot[can])/(double)(lengthTot[can]) + "\t");
				System.out.print((double)(diffTot[can])/(double)(lengthTot[can]) + "\t");
			}
			outM.close();
			// END
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	    	
	    	
	    	
	    	
	    	
	    	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		D3_Hap_age_agedHapCalls d3_Hap_age_agedHapCalls = new D3_Hap_age_agedHapCalls();
		d3_Hap_age_agedHapCalls.fromHapCallsToHapFS(args[0]);
	}
}
