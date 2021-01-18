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

public class Mor_private_haps_admClusters {
	
	private int whichCountry = 0;
	private Mor_private_haps_admClusters() {}
	
        public void runTheMatrix(String matrixName, String groupStr, String sampleStr) {
               try {
			
                        int group = Integer.parseInt(groupStr);
			int sample= Integer.parseInt(sampleStr);
			
			Boolean morTogether = false;
			// morTogether = true;
			
			System.out.println(matrixName + "\t" + groupStr + "\t" + sampleStr + "\n");
			System.out.println("Mor_private_haps_admClusters - morTogether: " + morTogether);
			
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
			
			
			
			
			
			
			
			
			






			// Load Ids separated by admixture cluster
			
			// Get in the list of 1135
			
			File genom1001 = new File("/home/lv70590/Andrea/data/1001genomes-accessions.txt");
                        Scanner scanner1001 = new Scanner(genom1001);
                        scanner1001.nextLine();
			
			// In case relicts go out...
			// int[] relicts = {9832, 9837, 9947, 9533, 9871, 9905, 9542, 9869, 9600, 9543, 9598, 9555, 9545, 9550, 9887, 9549, 9554, 9944, 9574, 9583, 9879, 9762, 9764, 7063, 6911};
			int[] relicts={7063, 6911, 9606, 9939, 9940};
			
			// Count samples
			int the1001 = 0;
                        int the1001_admixed = 0;
			while (scanner1001.hasNextLine()) {
                                String snp = scanner1001.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Relicts out
				Boolean isArel = false;
				for (int r=0; r<relicts.length; r++) {
					if (Integer.parseInt(splitSnp[0]) == relicts[r]) {
						isArel = true;
					}
				}
				if (!splitSnp[12].equals("forbidden") && !isArel) {			// && !isArel
                                	the1001 = the1001 + 1;
                        	} else {
					the1001_admixed = the1001_admixed + 1;
				}
			}
                        System.out.println("We Have: " + the1001 + " the1001");
	                System.out.println("We Have: " + the1001_admixed + " the1001_admixed");
	
                        scanner1001 = new Scanner(genom1001);
                        scanner1001.nextLine();
			
                        int[] thousIds = new int[the1001];
                        String[] thousAdmixtures = new String[the1001];
			
                        // Count samples
                        the1001 = 0;
                        while (scanner1001.hasNextLine()) {
                                String snp = scanner1001.nextLine();
                                String[] splitSnp = snp.split("\t");
				
				// Relicts out
				Boolean isArel = false;
                                for (int r=0; r<relicts.length; r++) {
                                       if (Integer.parseInt(splitSnp[0]) == relicts[r]) {
                                                isArel = true;
                                       }
                                }
                                if (!splitSnp[12].equals("forbidden") && !isArel) {					// && !isArel
	                                thousIds[the1001] = Integer.parseInt(splitSnp[0]);
                                	thousAdmixtures[the1001] = splitSnp[12];
                                	the1001 = the1001 + 1;
                        	}
			}
                        //System.out.println("1135 ids: " + Arrays.toString(thousIds));
                        //System.out.println("1135 ids: " + Arrays.toString(thousAdmixtures));
			
			
			
			// Now add moroccans
			
			File morClustFile = new File("/home/lv70590/Andrea/data/moroccan_samples-Sheet1.tsv");
                        Scanner scannerMor = new Scanner(morClustFile);
			
			// Count samples
			int moroccans = 0;
                        while (scannerMor.hasNextLine()) {
                                scannerMor.nextLine();
                                moroccans = moroccans + 1;
                        }
                        System.out.println("Moroccans: " + moroccans);
			
                        int[] idsThousAndMor = new int[thousIds.length + moroccans];
                        String[] groupsThousAndMor = new String[thousAdmixtures.length + moroccans];
			
                        for (int thou=0; thou<thousIds.length; thou++) {
                                idsThousAndMor[thou] = thousIds[thou];
                                groupsThousAndMor[thou] = thousAdmixtures[thou];
                        }
			
			
			
                        scannerMor = new Scanner(morClustFile);
			
                        moroccans = 0;
                        while (scannerMor.hasNextLine()) {
                                String mo = scannerMor.nextLine();
                                String[] splitMo = mo.split("\t");
				
                                idsThousAndMor[thousIds.length + moroccans] = Integer.parseInt(splitMo[1]);
                                
				if (morTogether) {
					groupsThousAndMor[thousIds.length + moroccans] = "Morocco";
        			} else {
					groupsThousAndMor[thousIds.length + moroccans] = splitMo[2];
				}
	                        moroccans = moroccans + 1;
                        }
                        // System.out.println("ids: " + Arrays.toString(idsThousAndMor));
                        // System.out.println("groups: " + Arrays.toString(groupsThousAndMor));
                        
			
			
			
			
			// Find unique list of admixture clusters
			String[] groupUnique = {groupsThousAndMor[0]};
			
                	for (int c=0; c< groupsThousAndMor.length; c++) {
                	        if (!groupsThousAndMor[c].equals("forbidden")) {
					
	                                boolean thereIs = false;
					for (int cU=0; cU< groupUnique.length; cU++) {
       	                                if (groupUnique[cU].equals(groupsThousAndMor[c])) {
                                        	thereIs = true;
                                        }
                                }
                                if (!thereIs) {
                                        String[] groupMem = groupUnique;
                                        groupUnique = new String[groupUnique.length + 1];
                                        for (int cM=0; cM< groupMem.length; cM++) {
                                                groupUnique[cM] = groupMem[cM];
                                        }
                                        groupUnique[groupMem.length] = groupsThousAndMor[c];
                                }

                        }
                }
                System.out.println("We have: " + groupUnique.length + " unique ADMIXTURE groups");
                System.out.println("Unique groups: " + Arrays.toString(groupUnique));



                // Get ids separated by admixture cluster
                int[] samplesAdmSorted = new int[groupUnique.length];

                for (int c=0; c< groupsThousAndMor.length; c++) {
                        for (int gU=0; gU<groupUnique.length; gU++) {
                                if (groupsThousAndMor[c].equals(groupUnique[gU])) {
                                        Boolean seen = false;

                                        for (int i=3; i<splitIDs.length; i++) {
                                                if (Integer.parseInt(splitIDs[i]) == idsThousAndMor[c]) {
                                                        if (seen) {
                                                                System.out.println("Whats that??");
                                                                System.out.println(idsThousAndMor[c]);
                                                        }
                                                        samplesAdmSorted[gU] = samplesAdmSorted[gU] + 1;
                                                        seen = true;
                                                }
                                        }
                                }
                        }
                }
                System.out.println("How many samples per cluster: " + Arrays.toString(samplesAdmSorted));




		int[][] idsAdmixtureSorted = new int[groupUnique.length][];
                for (int i=0; i<groupUnique.length; i++) {
                	idsAdmixtureSorted[i] = new int[samplesAdmSorted[i]];
                }
                samplesAdmSorted = new int[groupUnique.length];
                for (int i=0; i< samplesAdmSorted.length; i++) {
                        samplesAdmSorted[i] = 0;
                }
                for (int c=0; c< groupsThousAndMor.length; c++) {
                        for (int gU=0; gU<groupUnique.length; gU++) {
                                if (groupsThousAndMor[c].equals(groupUnique[gU])) {
					
                                        // Find the index
                                        for (int i=3; i<splitIDs.length; i++) {
                                                if (Integer.parseInt(splitIDs[i]) == idsThousAndMor[c]) {
                                	                idsAdmixtureSorted[gU][samplesAdmSorted[gU]] = i;
                        	                        samplesAdmSorted[gU] = samplesAdmSorted[gU] + 1;
                                                }
                                        }
                                }
                        }
                }
		for (int p=0; p<idsAdmixtureSorted.length; p++) {
			System.out.println(Arrays.toString(idsAdmixtureSorted[p]));
		}
		// Got ids sorted by admixture cluster













		System.out.println("Focus on cluster: " + groupUnique[group] + " sample: " + idsAdmixtureSorted[group][sample]);
		
		
		
		
		// Build a int[] of foreign indexes
		// And one of homeIds

		String thisGroup = groupUnique[group];
		
		int strangers = 0;
		int homey = 0;
		// System.out.println("ids: " + Arrays.toString(idsThousAndMor));
		// System.out.println("groups: " + Arrays.toString(groupsThousAndMor));
		
		for (int s=0; s<idsThousAndMor.length; s++) {
			if (!groupsThousAndMor[s].equals("forbidden")) {
				if (!groupsThousAndMor[s].equals(thisGroup)) {
					for (int i=3; i<splitIDs.length; i++) {
       		                         	if (idsThousAndMor[s] == Integer.parseInt(splitIDs[i])) {
       		                                 	strangers = strangers + 1;
        	                        	}
 					}
				} else {
					for (int i=3; i<splitIDs.length; i++) {
                                	        if (idsThousAndMor[s] == Integer.parseInt(splitIDs[i])) {
                        	                        homey = homey + 1;
                	                        }
        	                        }	
				}
			}
		}
		System.out.println("There are: " + strangers + " strangers");
                System.out.println("There are: " + homey + " homey");
	
		int[] strangerIndex = new int[strangers];
		int[] homeyIndex = new int[homey];
		strangers = 0;
		homey = 0;
		
		// Get strangers ids
		for (int s=0; s<idsThousAndMor.length; s++) {
                        if (!groupsThousAndMor[s].equals("forbidden")) {
	                        if (!groupsThousAndMor[s].equals(thisGroup)) {
	       	                         for (int i=3; i<splitIDs.length; i++) {
						if (idsThousAndMor[s] == Integer.parseInt(splitIDs[i])) {
							strangerIndex[strangers] = i;
               		         		        strangers = strangers + 1;
						}
					}
	                        } else {
       		                         for (int i=3; i<splitIDs.length; i++) {
               		                         if (idsThousAndMor[s] == Integer.parseInt(splitIDs[i])) {
 							homeyIndex[homey] = i;
							homey = homey + 1;
                               		         }
                          	      	}
                        	}
			}
                }
				







		// Get the index of the sample of interest
		
		int idOfInterest = idsAdmixtureSorted[group][sample];
		System.out.println("idOfInterest: " + idOfInterest);










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

		Writer writer = null;
		if (morTogether) {
                        // writer = new FileWriter("/global/lv70590/Andrea/privateHaps_morTogether/" + group + "/" + sample + ".txt");
                        writer = new FileWriter("/global/lv70590/Andrea/privateHaps_morTogether_2morCanSwitch/" + group + "/" + sample + ".txt");
                } else {
                	writer = new FileWriter("/global/lv70590/Andrea/privateHaps_admClusters_2morCanSwitch/" + group + "/" + sample + ".txt");
		}
		PrintWriter out = new PrintWriter(writer);
		out.print("#" + "\t" + groupUnique[group] + "\t" + splitIDs[idOfInterest] + "\n");
		out.print("#chromosome" + "\t" + "startPos" + "\t" + "endPos" + "\t" +  "#SNP" + "\t" + "#NotSingletons" + "\t" + "sharedSnpsWithinHap" + "\n");
	
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
        		char base = splitSnp[idOfInterest].charAt(0);
     			if (base != 'N') {
     				Boolean unique = true;
        	    		Boolean allN = true;
	         				
       				// Check all samples for uniqueness
    				for (int s2=0; s2<strangerIndex.length; s2++) {
					
					// Check uniqueness
					char base2 = splitSnp[strangerIndex[s2]].charAt(0);
                                        if (base2 != 'N') {
                                        	allN = false;
                                               	if (base == base2) {
                                                      	unique = false;
                                                }
                                       	}
				}
               			
				// Flag if it is a singleton
				Boolean singleton = true;
				for (int s=0; s<homeyIndex.length; s++) {
					if ( (Integer.parseInt(splitIDs[idOfInterest]) != Integer.parseInt(splitIDs[homeyIndex[s]])) && (splitSnp[homeyIndex[s]].charAt(0) == base) ) {
						singleton = false;
					}
				}
               			// NOW the proper calls!
       	    			// Now you got what you want - unique alleles
       	    			if (!allN) {
      	    				if (unique) {
        	    				// Output also snps only
        	    				int sing=0;
						if (!singleton) {
							sing = 1;
						}
        	    				out.print(chr + "\t" + Integer.parseInt(splitSnp[1]) + "\t" + Integer.parseInt(splitSnp[1]) + "\t" + "1" + "\t" + sing + "\t" + "0" + "\n");
						
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
                                      				if ((memHap >= 2)) {
                                   				        out.print(chr + "\t" + startHap + "\t" + endHap + "\t" + memHap + "\t" + notSinglePrivate + "\t" + (varWithin-1) + "\n");
								}
                                           				
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
		Mor_private_haps_admClusters mor_private_haps_admClusters = new Mor_private_haps_admClusters();
		mor_private_haps_admClusters.runTheMatrix(args[0], args[1], args[2]);
	}
}
