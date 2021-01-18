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

public class Haplo_completeFireConservative {
	
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
	public int pat = 0;
	public String countryHap = null;
	public boolean longer = false;
		
	private Haplo_completeFireConservative() {}
	
	public void call_Haplo_completeFireConservative(String canarian) {
		try {
			// From HaplotypeMonsterEater
			// File with 2 lines: tab separated int IDS for 1001 plus macaronesian plants, 
			// and in the second line, same order, country code for the same plant
			
			String whereWe = "/home/CIBIV/andreaf/canaries/rawData/analyses/haplotypeCallsCan/";
			int whichCan = Integer.parseInt(canarian);
			
			File idsCountryFile = new File(whereWe + "idsCountry_AllFine.txt");
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
	    	File matrix = new File(whereWe + "1001uCVI2.tsv");
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
			// System.out.println("Cvi-0: " + cviZindex + " " + splitIDs[cviZindex]);
		    // System.out.println(Arrays.toString(iberianIndexes));
			// for (int check=0; check<canarianIndexes.length; check++) {
		    	// System.out.println(Integer.parseInt(splitIDs[canarianIndexes[check]]));
	    		// for (int co=0; co<ids.length; co++) {
	    			// if (ids[co] == Integer.parseInt(splitIDs[iberianIndexes[check]])) {
	    				// System.out.println(country[co]);
	    			// }
	    		// }
		    // }
		    
			// Linked to country. Now we have int Cvi-0, Col-0 index, and int[] for Iberian indexes.
			// From HaplotypeMonsterEater END
			
			
			
			
			
			
			
			
			
			
			
			// Start the new game
			/*
	    	scannerMatrix = new Scanner(matrix);
	    	scannerMatrix.nextLine();
	    	int rows=0;
	    	
	    	// Just count SNPs... And hope you will do it only once...
	    	while (scannerMatrix.hasNextLine()) {
				scannerMatrix.nextLine();
		    	rows = rows + 1;
	    	}
			System.out.println("rows: " + rows);
			*/
	    	// Counted! Just for speed, take the number here: How many?
			int rows = 11530428;
			
			
			
			// Play hard, in the fire
			
	    	for (int can=whichCan; can<whichCan + 1; can++) {
	    		
		    	System.out.println("Focus on can: " + splitIDs[canarianIndexes[can]]);
		    	
				Writer writer = new FileWriter(whereWe + "haploCallsCan_NoNCut_" + can + ".txt");
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
	        		if (baseCan != 'N') {
		    			
	        			// Filter out whatever appears both in Iberia (not only low frequency) and Cvi-0, non informative 'matches'
		    			// Look in Cvi-0
		    			inCVI = false;
			    		if ( splitSnp[cviZindex].charAt(0) == baseCan) {
			    			inCVI = true;
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
			    		// System.out.println("howMany: " + howMany);
			    		// System.out.println("howMany: " + (double)howMany/(double)tot);
	        			
	        			
				    	// Remove uninformative SNPs
	        			if (!(inIbp && inCVI)) {
	        				
					    	// Do the haplotype game
					    	
					    	Boolean unique = true;
					    	// Boolean isThere = false;
							
					    	for (int b=0; b<allButCanCviIndexes.length; b++) {
					        		char baseBB = splitSnp[allButCanCviIndexes[b]].charAt(0);					        		
					        		if (baseBB != 'N') {
					        			// isThere = true;
						        		if (baseBB == baseCan) {
						        			unique = false;
						        			if (memHap[b] == 0) {
						        				startHap[b] = Integer.parseInt(splitSnp[1]);
						        				varWithin[b] = 0;
						        			}
					        				memHap[b] = memHap[b] + 1;
			        						maskEndtwo.clear(b);
			        						secondLast.clear(b);
					        				endHap[b] = Integer.parseInt(splitSnp[1]);
					        			} else {
					        				// if (!unique) {
					        					if ( maskEndtwo.get(b) ) {
					        						if ((memHap[b] >= 2)) {
					        					    	
					        							// System.out.println("Heretime?");
					        							
					        							// Find country of this haplotype (donor plant)
					        				    		for (int co=0; co<ids.length; co++) {
					        				    			if (ids[co] == Integer.parseInt(splitIDs[allButCanCviIndexes[b]])) {
					        				    				countryHap = country[co];
					        				    			}
					        				    		}
					        				    		if (secondLast.get(b)) {
					        				    			varWithin[b] = varWithin[b] - 1;
					        				    		}
					        				    		out.print(chr + "\t" + startHap[b] + "\t" + endHap[b] + "\t" + Integer.parseInt(splitIDs[allButCanCviIndexes[b]]) + "\t" + countryHap + "\t" +  memHap[b] + "\t" +  (endHap[b] + 1 - startHap[b]) + "\t" + varWithin[b] + "\n");
					        				    		
					        				    		// End of this haplotype
					        						}
					        						memHap[b] = 0;
							        				secondLast.clear(b);
							        				maskEndtwo.clear(b);
							        				varWithin[b] = 0;
					        					} else {
					        						maskEndtwo.set(b);
					        						if (baseBB != 'N') {
					        							varWithin[b] = varWithin[b] + 1;
					        							secondLast.set(b);
					        						}
					        					}
					        				// }
					        			}
					        		}
						    	}
				        		if (unique) {
				        			if (memHap[memHap.length-1] == 0) {
				        				startHap[memHap.length-1] = Integer.parseInt(splitSnp[1]);
				        				varWithin[varWithin.length-1] = 0;
				        			}
				        			memHap[memHap.length-1] = memHap[memHap.length-1] + 1;
	        						maskEndtwo.clear(1000000);
			        				endHap[memHap.length-1] = Integer.parseInt(splitSnp[1]);
				        		} else {
	        						if (memHap[memHap.length-1] > 0) {
	        							if (!maskEndtwo.get(1000000)) {
	        						 		maskEndtwo.set(1000000);
	        						 	} else {
	        						 		if (memHap[memHap.length-1] > 1) { 
        					    				out.print(chr + "\t" + startHap[memHap.length-1] + "\t" + endHap[memHap.length-1] + "\t" + 0 + "\t" + "CANISL" + "\t" +  memHap[memHap.length-1] + "\t" +  (endHap[memHap.length-1] + 1 - startHap[memHap.length-1]) + "\t" + (varWithin[varWithin.length-1]) + "\n");
	        					    		}
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
		    		}
			    	rows = rows + 1;
			    	// In each row (SNP in 1001)

		    	}
		    	/*
		    	if ( (rows == 10000) || (rows == 200000)|| (rows == 1000000)|| (rows == 10000000)|| (rows == 20000000) ) {
			    	System.out.println("Checkpoint!");
		    	}
		    	*/
		    	
		    	out.close();
	    	}
	    	
	    	
	    	
	    	
	    	
	    	/*
	    	System.out.println("Writing!");
	    	
	    	for (int can=10; can<11; can++) { // canarianIndexes.length
				String fileOutName = "/Volumes/MicroTera/congmao_matrix/canarian_fire_" + can + ".txt";
				Writer writerO = new FileWriter(fileOutName); 
				PrintWriter outO = new PrintWriter(writerO);
				
				outO.print("Parliamo di: " + splitIDs[canarianIndexes[can]] + "\n");
				outO.print("chromosome" + "\t" + "startPos" + "\t" + "endPos" + "\t" + "pattern" + "\t" +  "howManySnps" + "\t" +  "hapLength" + "\n");
				
				for (int chr=0; chr<5; chr++) {
					int[] start = new int[6];
					int[] end = new int[6];
					int[] maxLength = new int[6];
					boolean[] inTheHap = new boolean[6];
					for (int co=0; co<6; co++) {
						start[co] = 0;
						end[co] = 0;
						inTheHap[co] = false;
						maxLength[co] = haps[can][co][chr].length();
					}
					Arrays.sort(maxLength);
					int maxL = maxLength[maxLength.length-1];
					
					for (int p=0; p<maxL; p++) {
						for (int co=0; co<haps[can].length; co++) {
							if (haps[can][co][chr].get(p)) {
								if (!inTheHap[co]) {
									start[co] = p;
									end[co] = p;
									inTheHap[co] = true;
								} else {
									end[co] = p;
									inTheHap[co] = true;
								}
							} else {
								if (inTheHap[co]) {
									// This is the end of a haplotype shared between can and region "co"
									
									int[] check = new int[haps[can].length];
									for (int backP = end[co]; backP > start[co]; backP--) {
										for (int countr=0; countr<haps[can].length; countr++) {
											if (haps[can][countr][chr].get(backP)) {
												check[countr] = check[countr] + 1;
											}
										}
									}
						    		String pattern = "";
									for (int countr=0; countr<haps[can].length; countr++) {
										longer = false;
										if (check[countr] == (start[co] - end[co]) ) {
											if ( (!haps[can][countr][chr].get(start[co]-1)) && (!haps[can][countr][chr].get(end[co]+1)) ) {
								    			pattern =  "1" + pattern;
											} else {
												longer = true;
											}
										} else {
							    			pattern =  "0" + pattern;
										}
									}
									if (!longer) {
										// print this haplotype out
	        							outO.print( (chr+1) + "\t" + start[co] + "\t" + end[co] + "\t" + pattern + "\t" + memHap[co] + "\t" + (end[co] - start[co]) + "\n");
									}
									inTheHap[co] = false;
									start[co] = 0;
									end[co] = 0;
								}
							}
						}
					}
				}
		    	outO.close();
		    }
	    	*/
	    	
	    	
	    	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Haplo_completeFireConservative haplo_completeFireConservative = new Haplo_completeFireConservative();
		haplo_completeFireConservative.call_Haplo_completeFireConservative(args[0]);
	}
}

