package c.e.haploCallCan;

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

public class Mor_private_haps_IbpAsiaMor {
	
	private int whichCountry = 0;
	private Mor_private_haps_IbpAsiaMor() {}
	
	public void runTheMatrix(String matrixName, String groupStr, String iterateStr) {
		try {
			
			int group = Integer.parseInt(groupStr);
			int iterate = Integer.parseInt(iterateStr);

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
			
			for (int i=3; i<splitIDs.length; i++) {
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
			
			for (int i=3; i<splitIDs.length; i++) {
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
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			// Get IDs of Iberians - Central asians - Moroccans
			
			File refugiaFile = new File("/home/lv70590/Andrea/data/refugia_comparison.txt");
			Scanner scannerRef = new Scanner(refugiaFile);
			
			int[] ref = new int[3];
			while (scannerRef.hasNextLine()) {
				String refugia = scannerRef.nextLine();	
				String[] refSplit = refugia.split("\t");

				// Check if they are really in the data set
				Boolean isThere = false;
				for (int a=0; a<allIndexes.length; a++) {
					if (Integer.parseInt(splitIDs[allIndexes[a]]) == Integer.parseInt(refSplit[0])) {
						isThere = true;
					}
				}
				if (isThere) {
					if (refSplit[2].equals("Central_Asia")) {
	                                        ref[0] = ref[0] + 1;
       		                        } else {
                	                        if (refSplit[2].equals("Morocco")) {
                        	                        ref[1] = ref[1] + 1;
                                	        } else {
                                 	               if (refSplit[2].equals("Iberia")) {
                                               		         ref[2] = ref[2] + 1;
                                      			} else {
                                                	        System.out.println("Got a problem: " + refugia);
                                        	        }
                                	        }
                        	        }
				}
			}
			System.out.println(Arrays.toString(ref));
	
			int[][] idsOfInterest = new int[3][];
			for (int i=0; i<idsOfInterest.length; i++) {
				idsOfInterest[i] = new int[ref[i]];
			}
			
			scannerRef = new Scanner(refugiaFile);
			ref = new int[3];
			while (scannerRef.hasNextLine()) {
				String refugia = scannerRef.nextLine();	
				String[] refSplit = refugia.split("\t");
				
				// Check that they are really in the data set
				Boolean isThere = false;
				for (int a=0; a<allIndexes.length; a++) {
					if (Integer.parseInt(splitIDs[allIndexes[a]]) == Integer.parseInt(refSplit[0])) {
						isThere = true;
					}
				}
				if (isThere) {
				
					if (refSplit[2].equals("Central_Asia")) {
						
						// Get indexIDs
						for (int i=0; i<allIndexes.length; i++) {
							if (Integer.parseInt(splitIDs[allIndexes[i]]) == Integer.parseInt(refSplit[0])) {
								idsOfInterest[0][ref[0]] = i;
							}
						}
						ref[0] = ref[0] + 1;
					} else {
						if (refSplit[2].equals("Morocco")) {
							
							// Get indexIDs
							for (int i=0; i<allIndexes.length; i++) {
								if (Integer.parseInt(splitIDs[allIndexes[i]]) == Integer.parseInt(refSplit[0])) {
									idsOfInterest[1][ref[1]] = i;
								}
							}
							ref[1] = ref[1] + 1;
						} else {
							if (refSplit[2].equals("Iberia")) {
								
								// Get indexIDs
								for (int i=0; i<allIndexes.length; i++) {
									if (Integer.parseInt(splitIDs[allIndexes[i]]) == Integer.parseInt(refSplit[0])) {
										idsOfInterest[2][ref[2]] = i;
									}
								}
								ref[2] = ref[2] + 1;
							} else {
								System.out.println("Got a problem: " + refugia);
							}
						}
					}
				}
			}
			for (int i=0; i<idsOfInterest.length; i++) {
				System.out.println("idsOfInterest" + i);
				System.out.println(Arrays.toString(idsOfInterest[i]));
			}
			
			
			// Got ids of Iberians, Moroccans, central asians
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			// Subsample 50 each
			
		//	int[][] idsSubset = new int[3][];
		//	for (int i=0; i<idsSubset.length; i++) {
		//		idsSubset[i] = new int[50];
		//	}
			
			// Get a random set of 50 numbers
		//	int r = 0;
		//	for (int run=0; run<3; run++) {
		//		Random randomGenerator = new Random();
		//		for (int boot=0; boot<50; boot++) {
					// Generate random number
		//			r = randomGenerator.nextInt(idsSubset[run].length);
		//			idsSubset[run][boot] = idsOfInterest[run][r];
		//		}
		//		System.out.print("\n");
		//	}
		//	for (int i=0; i<idsSubset.length; i++) {
		//		System.out.println("idsSubset" + i);
		//		System.out.println(Arrays.toString(idsSubset[i]));
		//	}	
		





                        // Get a random accession
                        // Random randomGenerator = new Random();
			// int accession = randomGenerator.nextInt(idsOfInterest[group].length);
			
			int accession = Integer.parseInt(iterateStr);

	
			// Subsamples are subsampled
	    	
			
			
			
		










			// Get index[] to compare to all samples that do not come from the same region
	                String[] centralAsian = {"ARM", "GEO", "LBN", "IRN", "AZE", "TJK", "AFG", "IND", "UZB", "KGZ", "KAZ", "CHN", "RUS", "UKR"};
			
			String[] thisCountry = null;
	        	if (group == 0) {
				// We are in Central Asia!
	                        thisCountry = new String[centralAsian.length];
                                thisCountry = centralAsian;
       			}
			if (group == 1) {
				// We are in Morocco
				thisCountry = new String[2];
                                thisCountry[0] = "MOR";
                                thisCountry[1] = "MAR";
              		}
			if (group == 2) {
				// We are in Iberia
				thisCountry = new String[2];
                                thisCountry[0] = "ESP";
                                thisCountry[1] = "POR";
                        }

			int[] strangers = {0, 0, 0};
			
			// Count the strangers!
			//
                        for (int s2=0; s2<allIndexes.length; s2++) {
				// Get country of this one
				String thisCountry2 = "";
                                for (int co=0; co<ids.length; co++) {
	                                if (ids[co] == Integer.parseInt(splitIDs[allIndexes[s2]])) {
	                                        thisCountry2 = country[co];
					}
				}
				Boolean foreign = true;
                                for (int st=0; st<thisCountry.length; st++) {
	                                if (thisCountry2.equals(thisCountry[st])) {
        	                                foreign = false;
                                        }
                		}
                                if (foreign && !thisCountry2.equals("CVI") && !thisCountry2.equals("CANISL") && !thisCountry2.equals("ARE") && !thisCountry2.equals("CPV")) {
					strangers[group] = strangers[group] + 1;
				}
			}
			
			int[][] strangerIndex = new int[strangers.length][];	
			for (int z=0; z<strangers.length; z++) {
				strangerIndex[z] = new int[strangers[z]];
				strangers[z] = 0;
			}
			
                        // Now get the stranger's ids:
                        //
                        for (int s2=0; s2<allIndexes.length; s2++) {
				// Get country of this one
				String thisCountry2 = "";
                                for (int co=0; co<ids.length; co++) {
                                        if (ids[co] == Integer.parseInt(splitIDs[allIndexes[s2]])) {
                                                thisCountry2 = country[co];
                                        }
                                }
                                Boolean foreign = true;
                                for (int st=0; st<thisCountry.length; st++) {
                                        if (thisCountry2.equals(thisCountry[st])) {
                                                foreign = false;
                                        }
                                }
                                if (foreign && !thisCountry2.equals("CVI") && !thisCountry2.equals("CANISL") && !thisCountry2.equals("ARE") && !thisCountry2.equals("CPV")) {
					strangerIndex[group][strangers[group]] = allIndexes[s2];
					strangers[group] = strangers[group] + 1;
				}
                        }
			for (int p=0; p<strangerIndex.length; p++) {
				System.out.println("strangerIndex: " + Arrays.toString(strangerIndex[p]));
			}


		// Start the game!!
						
	    	scannerMatrix = new Scanner(matrix);
	    	scannerMatrix.nextLine();
	    	
	    	int chrMemory = 0;
			
		int memHap = 0;
		int startHap = 0;
		int endHap = 0;
		int varWithin = 0;
		int notSinglePrivate = 0;
		
		BitSet maskEndtwo = new BitSet();
		
		int privateHap = 0;

		String breakIt = splitIDs[idsOfInterest[group][accession]];
	
		Writer writer = new FileWriter("/global/lv70590/Andrea/privateHaps/" + group + "/" + iterate + ".txt");
                PrintWriter out = new PrintWriter(writer);
		out.print("Focus on: " + splitIDs[idsOfInterest[group][accession]] + "\n");
		out.print("chromosome" + "\t" + "startPos" + "\t" + "endPos" + "\t" +  "#SNP" + "\t" + "#NotSingletons" + "\t" + "sharedSnpsWithinHap" + "\n");
	
		while (scannerMatrix.hasNextLine()) {
		    	String snp = scannerMatrix.nextLine();
		    	String[] splitSnp = snp.split("\t");
		    	
		    	// Print where we are at chromosome jumps, and re initialize everything
    			int chr = Integer.parseInt(splitSnp[0]);
    			if (chr > chrMemory) {
    				memHap = 0;
    				startHap = 0;
    				endHap = 0;
    				varWithin = 0;
				notSinglePrivate = 0;
        			maskEndtwo = new BitSet();
    		    		
				System.out.println("Chr: " + chr);
    			}
    			chrMemory = chr;
    			

			// DO THE HAPLOTYPE GAME
   			// Get the base of this sample
        		char base = splitSnp[idsOfInterest[group][accession]].charAt(0);
     			if (base != 'N') {
     				Boolean unique = true;
        	    		Boolean allN = true;
	         				
       				// Check all samples for uniqueness
    				for (int s2=0; s2<strangerIndex[group].length; s2++) {
					
					// Check uniqueness
					char base2 = splitSnp[strangerIndex[group][s2]].charAt(0);
                                        if (base2 != 'N') {
                                        	allN = false;
                                               	if (base == base2) {
                                                      	unique = false;
                                                }
                                       	}
				}
               			
				// Flag if it is a singleton
				Boolean singleton = true;
				for (int s=0; s<idsOfInterest[group].length; s++) {
					if ( (s != accession) && (splitSnp[idsOfInterest[group][s]].charAt(0) == base) ) {
						singleton = false;
					}
				}
               			// NOW the proper calls!
       	    			// Now you got what you want - unique alleles
       	    			if (!allN) {
      	    				if (unique) {
        	    				
      	    					// Initialize hap call - if it is the first private snp
            					if (memHap == 0) {
            		        			startHap = Integer.parseInt(splitSnp[1]);
            		        			varWithin = 0;
            		        		}
            	    				
            	    				// Elongate hap - in any case
            		        		memHap = memHap + 1;
            					endHap = Integer.parseInt(splitSnp[1]);
            					// Count singletons separate
            					if (!singleton) {
							notSinglePrivate = notSinglePrivate + 1;
						}
						
            					// Clear the signals for hap cut
            					maskEndtwo.clear(0);
            				} else {
           	 				// You are here if the allele is not unique. Could be uninteresting, or the end of a unique hap
            					if (memHap != 0) {
            					
                        		               // maskEndtwo is set true if the previous snp was non-private - then we cut the hap call
                      			               if ( maskEndtwo.get(0) ) {
		
 				                                // If the hap call was long enough [>2], count it
                                      				//if ((memHap >= 2)) {
                                   				        out.print(chr + "\t" + startHap + "\t" + endHap + "\t" + memHap + "\t" + notSinglePrivate + "\t" + (varWithin-1) + "\n");
								//}
                                           				
                                        			// End of this haplotype, clear all for the next
                                   				memHap = 0;
                                        			maskEndtwo.clear(0);
                                      				varWithin = 0;
								notSinglePrivate = 0;
                                   			} else {
                                 			       	// If you got here, this is the first non-private, so wait one more to cut, and count as new mutation
                                        			maskEndtwo.set(0);
                                      				varWithin = varWithin + 1;
                                        		}
            					}
            				}
            		    		// NOt all N
        		    	}
        			// Base is not 'N'
           		}
			// Per line
		}
                out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Mor_private_haps_IbpAsiaMor mor_private_haps_IbpAsiaMor = new Mor_private_haps_IbpAsiaMor();
		mor_private_haps_IbpAsiaMor.runTheMatrix(args[0], args[1], args[2]);
	}
}
