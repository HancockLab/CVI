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

public class Mor_private_haps_perChr {
	
	private int whichCountry = 0;
	private Mor_private_haps_perChr() {}
	
	public void runTheMatrix(String matrixName, String chromString) {
		try {
			
			int chromosome = Integer.parseInt(chromString);
			System.out.println("Doing chr: " + chromosome);

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
			File idsCountryFile = new File("/home/lv70590/Andrea/analyses/haplotypeCallsCan/idsCountry_05-2016.txt");
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
			
			int allPlants = 0;
			
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
		    		for (int co=0; co<ids.length; co++) {
		    			if (ids[co] == Integer.parseInt(splitIDs[i])) {
		    				allPlants = allPlants + 1;
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
		    System.out.println("We have a total of: " + allPlants + " plants");
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
			
			int[] allIndexes = new int[allPlants];
			
			numIberianPlants =0;
			numCanPlants =0;
			numCviPlants = 0;
			numMorPlants = 0;
			numAllWorld = 0;
			numMadeiraPlants = 0;
			numAllButCanaryCvi = 0;
			
			allPlants = 0;
			
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
		    				allIndexes[allPlants] = i;
	    					allPlants = allPlants + 1;
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
			
			// Got ids and country
			// From HaplotypeMonsterEater
			
			
	    	
			
			
			
			
			
			
			
			
	    	
	    	// Find unique list of countries
			
	    	String[] countryUnique = new String[1];
	    	
			for (int pl=0; pl<allIndexes.length; pl++) {
	    		for (int co=0; co<ids.length; co++) {
	    			if (ids[co] == Integer.parseInt(splitIDs[allIndexes[pl]])) {
	    				if (pl == 0) {
	    					countryUnique[0] = country[co];
	    				}
	    	    		boolean thereIs = false;
	    		    	for (int cU=0; cU< countryUnique.length; cU++) {
	    		    		if (countryUnique[cU].equals(country[co])) {
	    		    			thereIs = true;
	    		    		}
	    		    	}
	    		    	if (!thereIs) {
	    	    	    	String[] countryMem = countryUnique;
	    	    	    	countryUnique = new String[countryUnique.length + 1];
	    			    	for (int cM=0; cM< countryMem.length; cM++) {
	    			    		countryUnique[cM] = countryMem[cM];
	    			    	}
	    			    	countryUnique[countryMem.length] = country[co];
	    	    		}
	    			}
	    		}
			}
	    	System.out.println("countryUnique:");
	    	System.out.println(Arrays.toString(countryUnique));
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	// Exclude islands
	    	
	    	String[] countryUniqueSub = new String[countryUnique.length - 4];
	    	
	    	int in = 0;
	    	for (int c = 0; c<countryUnique.length; c++) {
	    		if (!countryUnique[c].equals("CVI") && !countryUnique[c].equals("CPV") && !countryUnique[c].equals("CANISL") && !countryUnique[c].equals("ARE")) {
	    			countryUniqueSub[in] = countryUnique[c];
	    			in = in + 1;
	    		}
	    	}
	    	System.out.println("countryUniqueSub:");
	    	System.out.println(Arrays.toString(countryUniqueSub));
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	// Get vectors of id positions for every country
	    	// Just count samples in each country
	    	
	    	int[] numSamplesInCountry = new int[countryUniqueSub.length];
	    	for (int unique = 0; unique<countryUniqueSub.length; unique++) {
		    	for (int c=0; c< country.length; c++) {
	    			if (country[c].equals(countryUniqueSub[unique])) {
	    				//Check that we really have it processed
	    				for (int i=2; i<splitIDs.length; i++) {
	    					if (Integer.parseInt(splitIDs[i]) == ids[c]) {
	    						numSamplesInCountry[unique] = numSamplesInCountry[unique] + 1;
	    					}
	    				}
	    			}
	    		}
	    	}
	    	System.out.println("numSamplesInCountry: ");
	    	System.out.println(Arrays.toString(numSamplesInCountry));
	    	
	    	int[][] countryIndexes = new int[countryUniqueSub.length][];
	    	for (int unique = 0; unique<countryUniqueSub.length; unique++) {
	    		countryIndexes[unique] = new int[numSamplesInCountry[unique]];
	    		// Re- initialize counting
	    		numSamplesInCountry[unique] = 0;
	    		
	    		// Assign samples to country
		    	for (int c=0; c< country.length; c++) {
	    			if (country[c].equals(countryUniqueSub[unique])) {
	    				//Check that we really have it processed
	    				for (int i=2; i<splitIDs.length; i++) {
	    					if (Integer.parseInt(splitIDs[i]) == ids[c]) {
	    						countryIndexes[unique][numSamplesInCountry[unique]] = i;
	    	    				numSamplesInCountry[unique] = numSamplesInCountry[unique] + 1;
	    					}
	    				}
	    			}
	    		}	
	    	}
	    	System.out.println("countryUniqueSub[unique]");
	    	System.out.println("Arrays.toString(countryIndexes[unique])");
	    	
	    	for (int unique = 0; unique<countryUniqueSub.length; unique++) {
		    	System.out.println(countryUniqueSub[unique]);
		    	System.out.println(Arrays.toString(countryIndexes[unique]));
	    	}
	    	
	    	
	    	
	    	
	    	
			
			
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
			// Start the game!!
						
	    	scannerMatrix = new Scanner(matrix);
	    	scannerMatrix.nextLine();
	    	
	    	int chrMemory = 0;
		String thisCountry = null;
		String thisCountry2 = null;
			
		int[] memHap = new int[allIndexes.length];
		int[] startHap = new int[allIndexes.length];
		int[] endHap = new int[allIndexes.length];
		int[] varWithin = new int[allIndexes.length];
			
    		for (int i=0; i<allIndexes.length; i++) {
	    		memHap[i] = 0;
	    		startHap[i] = 0;
    			endHap[i] = 0;
    			varWithin[i] = 0;
    		}
		BitSet maskEndtwo = new BitSet();
	
		Writer writer = new FileWriter(matrixName + "_privateHapsPerCountry_chr" + chromosome + ".txt");
		PrintWriter out = new PrintWriter(writer);
		out.print("chromosome" + "\t" + "startPos" + "\t" + "endPos" + "\t" + "ID" + "\t" + "country" + "\t" +  "sharedSnpsWithinHap" + "\n");
		
		while (scannerMatrix.hasNextLine()) {
		    	String snp = scannerMatrix.nextLine();
		    	String[] splitSnp = snp.split("\t");
	    	
    			int chr = Integer.parseInt(splitSnp[0]);
   			if (chr > chrMemory) {
    				for (int i=0; i<allIndexes.length; i++) {
					memHap[i] = 0;
					startHap[i] = 0;
					endHap[i] = 0;
					varWithin[i] = 0;
				}
    				maskEndtwo = new BitSet();
    	    			System.out.println("Chr: " + chr);
    			}
    			chrMemory = chr;
    			
		if (chr == chromosome) {
			
			// DO THE HAPLOTYPE GAME
		    	
    			for (int b=0; b<allIndexes.length; b++) {
    				char base = splitSnp[allIndexes[b]].charAt(0);
    				
    				if (base != 'N') {
    					// Get country of this sample
    			    		for (int co=0; co<ids.length; co++) {
    			    			if (ids[co] == Integer.parseInt(splitIDs[allIndexes[b]])) {
    		    					thisCountry = country[co];
    		    				}
    		    			}
    		  	  		// Check all samples from any other country 
    		    			Boolean unique = true;
    		    			Boolean allN = true;
    	    				for (int b2=0; b2<allIndexes.length; b2++) {
        					// Get country of this sample
        		    			for (int co=0; co<ids.length; co++) {
        			    			if (ids[co] == Integer.parseInt(splitIDs[allIndexes[b2]])) {
        			    				thisCountry2 = country[co];
        		    				}
        		    			}
        		 	   		// If they are  not from the same place...
    	    					if (!thisCountry2.equals(thisCountry)) {
    	    						
  							// If they have the same allele, it is not unique to the country
    		    					if (base == splitSnp[allIndexes[b2]].charAt(0)) {
    	    							unique = false;
    	    						}
    	    						// Just check the rare case of a base called only in Morocco, and N everywhere else
    	    						if (splitSnp[allIndexes[b2]].charAt(0) != 'N') {
    	    							allN = false;
    	    						}
    	 	   				}
    	    				}
 	   	    			if (allN) {
    		    				System.out.println("AllN!!!");
    	    				}
    	    			
    	 	   			// NOW the proper calls!
    	    				// Now you got what you want - unique alleles
    	    				
    	    				if (unique) {
    	    					
    	    					// Initialize hap call - if it is the first private snp
    	    					if (memHap[b] == 0) {
    			        			startHap[b] = Integer.parseInt(splitSnp[1]);
    			        			varWithin[b] = 0;
    			        		}
    	    					
    	    					// Elongate hap - in any case
    		       		 		memHap[b] = memHap[b] + 1;
    						endHap[b] = Integer.parseInt(splitSnp[1]);
    						
    						// Clear the signals for hap cut
    						maskEndtwo.clear(b);
    					} else {
    	    					// You are here if the allele is not unique. Could be uninteresting, or the end of a unique hap
    	 	   				
               					// maskEndtwo is set true if the previous snp was non-private - then we cut the hap call
          			                if ( maskEndtwo.get(b) ) {

                                			// If the hap call was long enough [>2], output it
                          				if ((memHap[b] >= 2)) {
                                	
                       				             // OutPut this haplotype match
							out.print(chr + "\t" + startHap[b] + "\t" + endHap[b] + "\t" + Integer.parseInt(splitIDs[allIndexes[b]]) + "\t" + thisCountry + "\t" + (varWithin[b]-1) + "\n");
			                                }
                        	        
                        			        // End of this haplotype, clear all for the next
                    			            memHap[b] = 0;
                			            maskEndtwo.clear(b);
							varWithin[b] = 0;
		                            } else {
		                            	// If you got here, this is the first non-private, so wait one more to cut, and count as new mutation
                		            	maskEndtwo.set(b);
                                		varWithin[b] = varWithin[b] + 1;
                            		}
                      		  }
    	    			// If not missing
            		}
    	    		// for every sample
    			}
}
    		}
			out.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Mor_private_haps_perChr mor_private_haps_perChr = new Mor_private_haps_perChr();
		mor_private_haps_perChr.runTheMatrix(args[0], args[1]);
	}
}
