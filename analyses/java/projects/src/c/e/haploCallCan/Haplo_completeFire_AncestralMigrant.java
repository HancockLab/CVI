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

public class Haplo_completeFire_AncestralMigrant {
	
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
	public Boolean inMorMac = false;
	public int pat = 0;
	public String countryHap = null;
	public boolean longer = false;
		
	private Haplo_completeFire_AncestralMigrant() {}
	
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
		    	
		    	// If missing data DO NOT cut haplotypes:
				// Writer writer = new FileWriter(whereWe + "haploCallsCan_MorAnc_NoNCut_" + can + ".txt");
				
				// If missing data DO cut haplotypes:
				Writer writer = new FileWriter(whereWe + "haploCalls_simplified_" + can + ".txt");
				
				
				PrintWriter out = new PrintWriter(writer);
				out.print("Focus on can: " + splitIDs[canarianIndexes[can]] + "\n");
				out.print("chromosome" + "\t" + "startPos" + "\t" + "endPos" + "\t" + "ID" + "\t" + "country" + "\t" +  "howManySnps" + "\t" +  "hapLength" + "\t" +  "variationWithinHap" + "\n");
				
				int[] memHap = new int[2];
				int[] startHap = new int[2];
				int[] endHap = new int[2];
				int[] varWithin = new int[2];
				
		    		for (int i=0; i<2; i++) {
		    			memHap[i] = 0;
	    				startHap[i] = 0;
	    				endHap[i] = 0;
	    				varWithin[i] = 0;
	    			}
				BitSet maskEndtwo = new BitSet();
				BitSet secondLast = new BitSet();
				BitSet[][] haps = new BitSet[2][5];
	    			for (int c=0; c<2; c++) {
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
		    			for (int i=0; i<2; i++) {
	    					memHap[i] = 0;
	    					startHap[i] = 0;
	    					endHap[i] = 0;
	    					varWithin[i] = 0;
	    				}
					maskEndtwo = new BitSet();
					secondLast = new BitSet();
		    		}
		    		chrMemory = chr;
	        		
					char baseCan = splitSnp[canarianIndexes[can]].charAt(0);
		    			// Look in Morocco
					inMorMac = false;
				    	for (int mor=0; mor<moroccanIndexes.length; mor++) {
				    		if ( (splitSnp[moroccanIndexes[mor]].charAt(0) != 'N') && (splitSnp[moroccanIndexes[mor]].charAt(0) == baseCan) ) {
								inMorMac = true;
				    		}
				    	}
				    	for (int mad=0; mad<madeiranIndexes.length; mad++) {
				    		if ( (splitSnp[madeiranIndexes[mad]].charAt(0) != 'N') && (splitSnp[madeiranIndexes[mad]].charAt(0) == baseCan) ) {
							inMorMac = true;
				    		}
				    	}
			    		if ( (splitSnp[cviZindex].charAt(0) != 'N') && (splitSnp[cviZindex].charAt(0) == baseCan) ) {
							inMorMac = true;
			    		}
			    		
				    	
				    	// Look at Iberian variation
					inIbp = false;
				    	for (int ibp=0; ibp<iberianIndexes.length; ibp++) {
				    		if ( (splitSnp[iberianIndexes[ibp]].charAt(0) != 'N') && (splitSnp[iberianIndexes[ibp]].charAt(0) == baseCan)) {
							inIbp = true;
				    		}
				    	}
				    	// Remove uninformative SNPs
	        			if (!(inIbp && inMorMac)) {
					    	// Do the haplotype game
					    	// If MorMAc SNP	
					    	if ( (baseCan != 'N') && ( (inMorMac) || ( !(inMorMac) && !(inIbp) ) ) ) {
					            	if (memHap[0] == 0) {
                                        startHap[0] = Integer.parseInt(splitSnp[1]);
                        		        varWithin[0] = 0;
                                    }
                	                memHap[0] = memHap[0] + 1;
                                    maskEndtwo.clear(0);
        	                        secondLast.clear(0);
	                                endHap[0] = Integer.parseInt(splitSnp[1]);
					    	} else {
                                    if ( maskEndtwo.get(0) ) {
                                       	if ((memHap[0] >= 2)) {
                                    	    if (secondLast.get(0)) {
                                            	varWithin[0] = varWithin[0] - 1;
                                            }
                                            out.print(chr + "\t" + startHap[0] + "\t" + endHap[0] + "\t" + "MorMac" + "\t" + "MorMac" + "\t" +  memHap[0] + "\t" +  (endHap[0] + 1 - startHap[0]) + "\t" + varWithin[0] + "\n");

                                                                        // End of this haplotype
	                                                         }
                                                                 memHap[0] = 0;
                                                                 secondLast.clear(0);
                                                                 maskEndtwo.clear(0);
                                                                 varWithin[0] = 0;
 						        } else {
                                                        	maskEndtwo.set(0);
                                                               	varWithin[0] = varWithin[0] + 1;
                                                                secondLast.set(0);
                                                       	}
                                               	}
						}
                        // If Iberian SNP        
                        if ( (inIbp) && (baseCan != 'N') ) {
                        	if (memHap[1] == 0) {
                            	startHap[1] = Integer.parseInt(splitSnp[1]);
                                varWithin[1] = 0;
                            }
                            memHap[1] = memHap[1] + 1;
                            maskEndtwo.clear(1);
                            secondLast.clear(1);
                            endHap[1] = Integer.parseInt(splitSnp[1]);
                        } else {
                        	if ( maskEndtwo.get(1) ) {
                            	if ((memHap[1] >= 2)) {
                                	if (secondLast.get(1)) {
                                    	varWithin[1] = varWithin[1] - 1;
                                    }
                                    out.print(chr + "\t" + startHap[1] + "\t" + endHap[1] + "\t" + "Iberian" + "\t" + "Iberian" + "\t" +  memHap[1] + "\t" +  (endHap[1] + 1 - startHap[1]) + "\t" + varWithin[1] + "\n");
                                                                    
                                    // End of this haplotype
                                }
                                memHap[1] = 0;
                                secondLast.clear(1);
                                maskEndtwo.clear(1);
                                varWithin[1] = 0;
                            } else {
                                maskEndtwo.set(1);
                                varWithin[1] = varWithin[1] + 1;
                                secondLast.set(1);
                            }
                        }
					}
			    	rows = rows + 1;
			    	// In each row (SNP in 1001)
		    	out.close();
			}	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Haplo_completeFire_AncestralMigrant haplo_completeFire_AncestralMigrant = new Haplo_completeFire_AncestralMigrant();
		haplo_completeFire_AncestralMigrant.call_Haplo_completeFireConservative(args[0]);
	}
}

