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

public class Transposition_age {
	
	public Transposition_age() {}
	
	public void setMatrixFile(String matrixName){
		
		try {
			System.out.println("src/c/e/data_processing/Transposition_age.java");

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
	 	
		System.out.println("ids.length: " + ids.length);
                System.out.println("country.length: " + country.length);

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

			int all1001 = 0;
			int allPlants = 0;		
	
			int cviZindex = 0;
			int colIndex = 0;
		

System.out.println("Total, there are: " + (splitIDs.length-3) + " samples");
	
			for (int i=3; i<splitIDs.length; i++) {
/*	    		
                                // Check if it is a 80genome
				Boolean isInEighty = false;
                                for (int eightyId=0; eightyId<eightyIds.length; eightyId++) {
                                        if (eightyIds[eightyId] == Integer.parseInt(splitIDs[i])) {
                                                isInEighty = true;
                                        }
                                }
                                if (!isInEighty) {
*/

                                        Boolean thereIs = false;


					// ids1001[i-2] = Integer.parseInt(splitIDs[i]);
	    				// Count Iberians
	    				for (int co=0; co<ids.length; co++) {
						if (ids[co] == Integer.parseInt(splitIDs[i])) {
	    						thereIs = true;
							allPlants = allPlants + 1;
							if ( ids[co] == 6911 ) {
	    							cviZindex = i;
	  		  				}
		    					if ( ids[co] == 6909 ) {
		    						colIndex = i;
	    						}
	  		  				if ( country[co].equals("ESP") || country[co].equals("POR") ) {
	    							all1001 = all1001 + 1;
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
												all1001 = all1001 + 1;
						    						numAllWorld = numAllWorld + 1;
					    							numAllButCanaryCvi = numAllButCanaryCvi + 1;
									    		}
				    						}
		    							}
		    						}
	    						}
	    					}
	    				}
					if (!thereIs) {
						System.out.println("problem for: " + splitIDs[i]);
					}	    		
//	}
			}

                System.out.println("We have a total of: " + allPlants + " plants");
		System.out.println("In the 1001: " + all1001 + " plants");
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
			int[] thousOneIndexes = new int[all1001];	
			
			numIberianPlants =0;
			numCanPlants =0;
			numCviPlants = 0;
			numMorPlants = 0;
			numAllWorld = 0;
			numMadeiraPlants = 0;
			numAllButCanaryCvi = 0;
			allPlants = 0;
			all1001 = 0;
			
			for (int i=3; i<splitIDs.length; i++) {
	    		
/*				Boolean isInEighty = false;
                                for (int eightyId=0; eightyId<eightyIds.length; eightyId++) {
                                        if (eightyIds[eightyId] == Integer.parseInt(splitIDs[i])) {
                                                isInEighty = true;
                                        }
                                }
                                if (!isInEighty) {
*/


					for (int co=0; co<ids.length; co++) {
			    			if (ids[co] == Integer.parseInt(splitIDs[i])) {
	    		                                allIndexes[allPlants] = i;
                	                                allPlants = allPlants + 1;
	
							if ( country[co].equals("ESP") || country[co].equals("POR") ) {
	    							thousOneIndexes[all1001] = i;
								all1001 = all1001 + 1;
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
                        				                                        thousOneIndexes[all1001] = i;
				                                                                all1001 = all1001 + 1;

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
//				}
			}
			
			// Linked to country. Now we have int Cvi-0, Col-0 index, and int[] for Iberian indexes.
			// From HaplotypeMonsterEater END
						
						

			
			
			
			
			
			
			// 
			// From Matrix_subSet.java
			//
			
			// Build IndexesToGet, array of interesting indexes 
			




			int[] relT = {9869, 9574, 9832, 9550, 27154};
			int[] relNt = {9871, 9879, 9887, 9554, 9555, 9598, 9600, 9837, 9533, 9542, 9543, 9545, 9549};
				
			int[] relTind = new int[relT.length];
			int[] relNtInd = new int[relNt.length];
			
                        for (int i=3; i<splitIDs.length; i++) {
				for (int r=0; r<relT.length; r++) {
					if (Integer.parseInt(splitIDs[i]) == relT[r]) {
						relTind[r] = i;
					}
				}
			}
		        for (int i=3; i<splitIDs.length; i++) {
		                for (int r=0; r<relNt.length; r++) {
                                        if (Integer.parseInt(splitIDs[i]) == relNt[r]) {
                                                relNtInd[r] = i;
                                        }
                                }
                        }
			System.out.println(Arrays.toString(relTind));
			System.out.println(Arrays.toString(relNtInd));
			











	
			//
			// Now the real game
			// 

        	// Begin to calculate pairwise differences
        	
			long[] diffT = new long[relTind.length*(relTind.length/2)];
			long[] lenT = new long[relTind.length*(relTind.length/2)];
			
			long[] diffNt = new long[relNtInd.length*(relNtInd.length/2)];
                        long[] lenNt = new long[relNtInd.length*(relNtInd.length/2)];
			
			long[] diffB = new long[relNtInd.length*relTind.length];
			long[] lenB = new long[relNtInd.length*relTind.length];
			
			int chr =0;
			
		    	matrix = new File(matrixName);
		    	scannerMatrix = new Scanner(matrix);
		    	scannerMatrix.nextLine();
	    	
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
				String[] splitSnp = snp.split("\t");
				// System.out.println(splitSnp.length);
				
				// Just print where we are
        			if (chr != Integer.parseInt(splitSnp[0])) {
        				System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
        			}
        			chr = Integer.parseInt(splitSnp[0]);
        			int pos = Integer.parseInt(splitSnp[1]);
	       		 	//
	        		
       		 		// if (!mask[chr-1].get(pos)) {
				
				if (chr == 5 && pos > 7700000 && pos < 14000000) { // pos > 14693000 && pos < 14694000) { // pos > 7700000 && pos < 14000000) {
				
				// Go transposition
				//
				int tCount = 0;
	    	        	for (int acc1=0; acc1<relTind.length-1; acc1++) {
    			       		char base1 = splitSnp[relTind[acc1]].charAt(0);
					for (int acc2=acc1+1; acc2<relTind.length; acc2++) {
    			       			char base2 = splitSnp[relTind[acc2]].charAt(0);
    			       		
    			       			if ( (base1 != 'N') && (base2 != 'N') ) {
    			       				lenT[tCount] = lenT[tCount] + 1;
    		        				if (base1 != base2) {
    			       					diffT[tCount] = diffT[tCount] + 1;
    			       				}
						}
						tCount = tCount + 1;
    			       		}
    		        	}
				// Go un-transposition!
				//
				int nCount = 0;
                                for (int acc1=0; acc1<relNtInd.length-1; acc1++) {
                                        char base1 = splitSnp[relNtInd[acc1]].charAt(0);
                                        for (int acc2=acc1+1; acc2<relNtInd.length; acc2++) {
                                                char base2 = splitSnp[relNtInd[acc2]].charAt(0);

                                                if ( (base1 != 'N') && (base2 != 'N') ) {
                                                        lenNt[nCount] = lenNt[nCount] + 1;
                                                        if (base1 != base2) {
                                                                diffNt[nCount] = diffNt[nCount] + 1;
                                                        }
                                                }
                                                nCount = nCount + 1;
                                        }
                                }
				// Between
				int bCount = 0;
				for (int acc1=0; acc1<relTind.length; acc1++) {
                                        char base1 = splitSnp[relTind[acc1]].charAt(0);
                                        for (int acc2=0; acc2<relNtInd.length; acc2++) {
                                                char base2 = splitSnp[relNtInd[acc2]].charAt(0);

                                                if ( (base1 != 'N') && (base2 != 'N') ) {
                                                        lenB[bCount] = lenB[bCount] + 1;
                                                        if (base1 != base2) {
                                                                diffB[bCount] = diffB[bCount] + 1;
                                                        }
                                                }
                                                bCount = bCount + 1;
                                        }
                                }


       	 		} 		
		        }
			// Calculate pi
			
			double[] pairwDiffT = new double[lenT.length];
			double piSum = 0.0;
			for (int q=0; q<lenT.length; q++) {
				pairwDiffT[q] = (double)(diffT[q])/(double)(lenT[q]);
				piSum = piSum + pairwDiffT[q];
			}
			double piT = piSum/(double)lenT.length;

			double[] pairwDiffNt = new double[lenNt.length];
                        double piSum2 = 0.0;
                        for (int q=0; q<lenNt.length; q++) {
                                pairwDiffNt[q] = (double)(diffNt[q])/(double)(lenNt[q]);
                                piSum2 = piSum2 + pairwDiffNt[q];
                        }
                        double piNt = piSum2/(double)lenNt.length;

			double[] pairwDiffB = new double[lenB.length];
			double piSum3 = 0.0;
			int non0 = 0;
                        for (int q=0; q<lenB.length; q++) {
                                pairwDiffB[q] = (double)(diffB[q])/(double)(lenB[q]);
				piSum3 = piSum3 + pairwDiffB[q];
				non0 = non0 + 1;
			}
                        double piB = piSum3/(double)non0;
			// Done
			
			
			
			String results = "/global/lv70590/Andrea/";	

			// Write out
			
			Writer writer = new FileWriter(results + "transposAge_bigOne.txt");
			PrintWriter out = new PrintWriter(writer);
                        out.print("Between" + "\t" + piB + "\n");
			out.print("Transp" + "\t" + piT + "\n");
                        out.print("Non-transp" + "\t" + piNt + "\n");

                        out.print("Between" + "\n");
                        for (int q=0; q<diffB.length; q++) {
                                out.print(diffB[q] + "\t" + lenB[q] + "\t" + (double)diffB[q]/(double)lenB[q] + "\n");
                        }
			out.print("Transp" + "\n");
			for (int q=0; q<diffT.length; q++) {
				out.print(diffT[q] + "\t" + lenT[q] + "\t" + (double)diffT[q]/(double)lenT[q] + "\n");
			}
			out.print("un-transp" + "\n"); 
			for (int q=0; q<diffNt.length; q++) {
				out.print("un-transp");
                                out.print(diffNt[q] + "\t" + lenNt[q] + "\t" + (double)diffNt[q]/(double)lenNt[q] + "\n");
			}
			out.close();



	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Transposition_age transposition_age = new Transposition_age();
		transposition_age.setMatrixFile(args[0]);
	}
}
