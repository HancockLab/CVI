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

public class Haplo_completeFireConservative_Moroccancestor_noNCut {
	
	public int cviZindex = 0;
	public int colIndex = 0;
	public int totalBp = 0;
	public int totalBpIBP = 0;
	public int mismCviZ = 0;
	public int mismIBP = 0;
	public char[] bases = null;
	public int[] spectrum = null;
	public int snpsGoodForDifferenciatingIbpCvi =0 ;
	public int snpsBadForDifferenciatingIbpCvi =0 ;
	public int snpsGoodIBP =0 ;
	public int snpsBadIBP =0 ;
	public boolean difference = false;
	public Boolean inCVI = false;
	public Boolean inIbp = false;
	public Boolean inMor = false;
	public Boolean inCol = false;
	public int pat = 0;
	public String countryHap = null;
	public boolean longer = false;
		
	private Haplo_completeFireConservative_Moroccancestor_noNCut() {}
	
	public void call_Haplo_completeFireConservative(String canarian) {
		try {
			// From HaplotypeMonsterEater
			// File with 2 lines: tab separated int IDS for 1001 plus macaronesian plants, 
			// and in the second line, same order, country code for the same plant
			
			String whereWe = "/home/lv70590/Andrea/analyses/haplotypeCallsCan/";
			int whichCan = Integer.parseInt(canarian);
			
			File idsCountryFile = new File(whereWe + "idsCountry_01-2016.txt");
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
			
			for (int i=2; i<splitIDs.length; i++) {
	    		// ids1001[i-2] = Integer.parseInt(splitIDs[i]);
	    		// Count Iberians
	    		for (int co=0; co<ids.length; co++) {
	    			if (ids[co] == Integer.parseInt(splitIDs[i])) {
	    				if ( ids[co] == 6911 ) {
	    					cviZindex = i;
	    					numAllButCanaryCvi = numAllButCanaryCvi + 1;
	    				}
		    			if ( ids[co] == 6909 ) {
		    				colIndex = i;
		    				numAllButCanaryCvi = numAllButCanaryCvi + 1;
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
	    				/*if ( country[co].equals("CPV") ) {
	    					System.out.print("Cvi: " + ids[co]);  
	    				}*/
	    				if ( ids[co] == 6911 ) {
	    					// System.out.print("Cvi: " + country[co]);  
	    					allButCanCviIndexes[numAllButCanaryCvi] = i;
	    					numAllButCanaryCvi = numAllButCanaryCvi + 1;
	    				}
		    			if ( ids[co] == 6909 ) {
	    					allButCanCviIndexes[numAllButCanaryCvi] = i;
	    					numAllButCanaryCvi = numAllButCanaryCvi + 1;
	    				}
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
			
			
			
			
			
			
			
			
			
			
			
		// Start the new game
		
	    	scannerMatrix = new Scanner(matrix);
	    	scannerMatrix.nextLine();
	    	int rows=0;
	    	
	    	// Just count SNPs... And hope you will do it only once...
	    	while (scannerMatrix.hasNextLine()) {
			scannerMatrix.nextLine();
		    	rows = rows + 1;
	    	}
		System.out.println("rows: " + rows);
		
	    	// Counted! Just for speed, take the number here: How many?
		// int rows = 11530428;
			
			
			
		// Play hard, in the fire
			
	    	for (int can=whichCan; can<whichCan + 1; can++) {
	    		
		    	System.out.println("Focus on can: " + splitIDs[canarianIndexes[can]]);
		    	
		    	// If missing data DO NOT cut haplotypes:
			Writer writer = new FileWriter(whereWe + "haploCallsCan_MorAnc_NoNCut_" + can + ".txt");
				
			// If missing data DO cut haplotypes:
			// Writer writer = new FileWriter(whereWe + "haploCallsCan_MorAnc_YeaCutN_noMatches" + can + ".txt");
				
				
			PrintWriter out = new PrintWriter(writer);
			out.print("Focus on can: " + splitIDs[canarianIndexes[can]] + "\n");
			out.print("chromosome" + "\t" + "startPos" + "\t" + "endPos" + "\t" + "ID" + "\t" + "country" + "\t" +  "howManySnps" + "\t" +  "hapLength" + "\t" +  "variationWithinHap" + "\n");
			
			int[] memHap = new int[allButCanCviIndexes.length+1];
			int[] startHap = new int[allButCanCviIndexes.length+1];
			int[] endHap = new int[allButCanCviIndexes.length+1];
			int[] varWithin = new int[allButCanCviIndexes.length+1];
			
	    		for (int i=0; i<allButCanCviIndexes.length+1; i++) {
	    			memHap[i] = 0;
	    			startHap[i] = 0;
	    			endHap[i] = 0;
	    			varWithin[i] = 0;
	    		}
			BitSet maskEndtwo = new BitSet();
			BitSet secondLast = new BitSet();
			BitSet[][] haps = new BitSet[6][5];
	    		for (int c=0; c<6; c++) {
				for (int chr=0; chr<5; chr++) {
					haps[c][chr] = new BitSet();
				}
			}
				
			scannerMatrix = new Scanner(matrix);
		    	scannerMatrix.nextLine();
		    	rows = 0;
		    	System.out.println("Start the game!");
		    	
		    	int chrMemory = 0;
		    	while (scannerMatrix.hasNextLine()) {
			    	// System.out.println(rows);
		    	
				String snp = scannerMatrix.nextLine();
			    	String[] splitSnp = snp.split("\t");
			    	
				// If you are on a new chromosome, re-initialize everything
		    		int chr = Integer.parseInt(splitSnp[0]);
		    		if (chr > chrMemory) {
		    			for (int i=0; i<allButCanCviIndexes.length+1; i++) {
	    					memHap[i] = 0;
	    					startHap[i] = 0;
	    					endHap[i] = 0;
	    					varWithin[i] = 0;
	    				}
					maskEndtwo = new BitSet();
					secondLast = new BitSet();
		    		}
		    		chrMemory = chr;
				
		    		// System.out.println(can);
		    		
	        		char baseCan = splitSnp[canarianIndexes[can]].charAt(0);
	        		
	        		
	        		
	        		
	        		if ( (baseCan != 'N') ) {
		    			
	        			// Filter out whatever appears both in Iberia (not only low frequency) and Morocco, non informative 'matches'
		    			// Look in Morocco
					inMor = false;
					double howManyMor = 0.0;
					int totM =0;
				    	for (int mor=0; mor<moroccanIndexes.length; mor++) {
				    		if ( (splitSnp[moroccanIndexes[mor]].charAt(0) != 'N') ) {
				    			totM = totM + 1;
				    			if (splitSnp[moroccanIndexes[mor]].charAt(0) == baseCan) {
				    				howManyMor = howManyMor + 1;
				    			}
				    		}
				    	}
				    	if (howManyMor/(double)(totM) > 0.95) {			// 1
						inMor = true;
				    	}
				    				    		
				    	// Look at Iberian variation
					inIbp = false;
					double howMany = 0.0;
					int tot =0;
				    	for (int ibp=0; ibp<iberianIndexes.length; ibp++) {
				    		if ( (splitSnp[iberianIndexes[ibp]].charAt(0) != 'N') ) {
				    			tot = tot + 1;
				    			if (splitSnp[iberianIndexes[ibp]].charAt(0) == baseCan) {
				    				howMany = howMany + 1;
				    			}
				    		}
				    	}
				    	if (howMany/(double)(tot) > 0.75) {			// 1
						inIbp = true;
				    	}
	        			
	        			// Check the reference
	        		//	inCol = false; 
	        		//	if (splitSnp[colIndex].charAt(0) == baseCan) {
	        		//		inCol = true;
	  		      	//	}
	        				
	        			
				    	// Remove uninformative SNPs
	        			if (!(inIbp && inMor) ) { // && (!inCol)) {
	        				
					    	// DO THE HAPLOTYPE GAME
					    	
						// Is this base at this position unique to Canaries?
					    	Boolean unique = true;
						
						// Cut or elongate haplotype calls for every pair of samples
					    	for (int b=0; b<allButCanCviIndexes.length; b++) {
					        	char baseBB = splitSnp[allButCanCviIndexes[b]].charAt(0);					        		
							
							// If N in the non-canarian, jump
							if (baseBB != 'N') {							

								// Enter here if it is an informative match	
								if ( (baseBB == baseCan) && (baseCan != 'N') ) {
									unique = false;
							        	// Initialize hap call
							        	if (memHap[b] == 0) {
						        			startHap[b] = Integer.parseInt(splitSnp[1]);
						        			varWithin[b] = 0;
						        		}
									// Elongate hap
						        		memHap[b] = memHap[b] + 1;
									endHap[b] = Integer.parseInt(splitSnp[1]);
									// Clear the signals for hap cut
			        					maskEndtwo.clear(b);
			        					secondLast.clear(b);
					        		} else {
									// You are here if it is an informative mismatch
									// maskEndtwo records the second mismatch, if true, cut the hap call
									if ( maskEndtwo.get(b) ) {
					        				// If the hap call was long enough [>2], output it
										if ((memHap[b] >= 2)) {
					        					// Find country of this haplotype (donor plant)
					        					for (int co=0; co<ids.length; co++) {
					     		   					if (ids[co] == Integer.parseInt(splitIDs[allButCanCviIndexes[b]])) {
					        							countryHap = country[co];
					        						}
					        					}
											// SecondLast records if the second last SNP was a mismatch or a 'N'. In the 1st case, we are counting one new mutation too much in the varWithin
					        					if (secondLast.get(b)) {
					        						varWithin[b] = varWithin[b] - 1;
						        				}
											// Put out
						        				out.print(chr + "\t" + startHap[b] + "\t" + endHap[b] + "\t" + Integer.parseInt(splitIDs[allButCanCviIndexes[b]]) + "\t" + countryHap + "\t" +  memHap[b] + "\t" +  (endHap[b] + 1 - startHap[b]) + "\t" + varWithin[b] + "\n");
						        			}
										// End of this haplotype, clear all for the next
						        			memHap[b] = 0;
										secondLast.clear(b);
							        		maskEndtwo.clear(b);
								        	varWithin[b] = 0;
					        			} else {
					        			// It is the first mismatch, so wait one more to cut, and count as new mutation
										maskEndtwo.set(b);
						        			if ( (baseBB != 'N') && (baseCan != 'N') ) {
						        				varWithin[b] = varWithin[b] + 1;
						        				secondLast.set(b);
						        			}
						        		}
						        	}
							}
						}
						
						// If it is private variation in canaries, output as well. Store informations at index ###.length-1
				        	if (unique && (baseCan != 'N') ) {
							// Initialize hap call
				        		if (memHap[memHap.length-1] == 0) {
				        			startHap[memHap.length-1] = Integer.parseInt(splitSnp[1]);
				        			varWithin[varWithin.length-1] = 0;
				        		}
							// Elongate hap
				        		memHap[memHap.length-1] = memHap[memHap.length-1] + 1;
	        					endHap[memHap.length-1] = Integer.parseInt(splitSnp[1]);
							// Clear the signals for hap cut
							maskEndtwo.clear(1000000);
				        	} else {
						// End of private hap
							// Out put also private single bases 
	        					if (memHap[memHap.length-1] > 0) {
								// Just avoid entering here any time a mutation is not private and there is no private haplotype
	        						if (!maskEndtwo.get(1000000)) {
	        					 		maskEndtwo.set(1000000);
	        					 	} else {
	        					 		// Allow for single non-private bases within a private haplotype... Maybe recurrent mutations
	        					 		if (memHap[memHap.length-1] > 0) { 
        									out.print(chr + "\t" + startHap[memHap.length-1] + "\t" + endHap[memHap.length-1] + "\t" + 0 + "\t" + "CANISL" + "\t" +  memHap[memHap.length-1] + "\t" +  (endHap[memHap.length-1] + 1 - startHap[memHap.length-1]) + "\t" + (varWithin[varWithin.length-1]) + "\n");
	        					    		}
        					    			// End of this haplotype, clear all for the next
        					    			memHap[memHap.length-1] = 0;
				    	    				maskEndtwo.clear(1000000);
				        				startHap[memHap.length-1] = 0;
				        				endHap[memHap.length-1] = 0;
			        					varWithin[varWithin.length-1] = 0;
	        					 	}
			        			}
        					}
        					// Funny??	
					}
					// inside here, if you jump "N"s in The canarian guy
		    		}
			    	rows = rows + 1;
			    	// In each row (SNP in 1001)

		    	}
		    	out.close();
	    	}
	    	
	    	
	    	
	    	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Haplo_completeFireConservative_Moroccancestor_noNCut haplo_completeFireConservative_Moroccancestor_noNCut = new Haplo_completeFireConservative_Moroccancestor_noNCut();
		haplo_completeFireConservative_Moroccancestor_noNCut.call_Haplo_completeFireConservative(args[0]);
	}
}

