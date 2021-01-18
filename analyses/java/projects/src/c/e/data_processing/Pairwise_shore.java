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

public class Pairwise_shore {
	
	public Pairwise_shore() {}
	
	public void setMatrixFile(String matrixName, String accessionString, String results){
		
		try {
                        int accessionIndex = Integer.parseInt(accessionString) + 3;
                        // int accession = Integer.parseInt(accessionString);
/*
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

*/


			System.out.println("src/c/e/data_processing/Pairwise_shore.java");			
                        System.out.println("Running on sample: " + accessionIndex);


/*


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
			
			
*/			
	    	
	    	// Begin with the big matrix
		// From HaplotypeMonsterEater BEGIN
	    	File matrix = new File(matrixName);
	    	Scanner scannerMatrix = new Scanner(matrix);
	    	
	    	// Just get ID order in the matrix from first line
	    	// And count iberians [Spanish + Portuguese]
			String idsUnsplit = scannerMatrix.nextLine();
	    	String[] splitIDs = idsUnsplit.split("\t");
/*			
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
/*
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
*/	    		
/*				Boolean isInEighty = false;
                                for (int eightyId=0; eightyId<eightyIds.length; eightyId++) {
                                        if (eightyIds[eightyId] == Integer.parseInt(splitIDs[i])) {
                                                isInEighty = true;
                                        }
                                }
                                if (!isInEighty) {
*/

/*
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
						
						

		*/	
			
			
			
			
			
			// 
			// From Matrix_subSet.java
			//
			
			// Build IndexesToGet, array of interesting indexes 
			
			int allPlants = splitIDs.length - 3;

			int howMany = allPlants;	// canarianIndexes.length + moroccanIndexes.length + iberianIndexes.length; // + capeVerdeanIndexes.length + madeiranIndexes.length 
			System.out.println("howMany: "+ howMany);
			
			int[] IndexesToGet = new int[howMany];
			
			for (int i=3; i<splitIDs.length; i++) {	
				IndexesToGet[i-3] = i;
			}
		












	
			//
			// Now the real game
			// 

        	// Begin to calculate pairwise differences
        	
			int[] differences = new int[IndexesToGet.length];
			int[] length = new int[IndexesToGet.length];
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
	
    		        	char base1 = splitSnp[accessionIndex].charAt(0);						// IndexesToGet[accession]].charAt(0);
	    	        	
    			       	for (int acc2=0; acc2<IndexesToGet.length; acc2++) {
    			       		char base2 = splitSnp[IndexesToGet[acc2]].charAt(0);
    			       		
    			       		if ( (base1 != 'N') && (base2 != 'N') ) {
    			       			length[acc2] = length[acc2] + 1;
    		        			if (base1 != base2) {
    			       				differences[acc2] = differences[acc2] + 1;
    			       			}
    			       		}
    		        	}
       	 		//} 		
		        }
			// Calculate pi
			
			double[] pairwDiff = new double[IndexesToGet.length];
			for (int q=0; q<IndexesToGet.length; q++) {
				pairwDiff[q] = (double)(differences[q])/(double)(length[q]);
			}
			
			// Done
			
			
			
			// Write out
			
			Writer writer = new FileWriter(results + "pi" + accessionIndex + ".txt");
			PrintWriter out = new PrintWriter(writer);
			for (int q=0; q<IndexesToGet.length; q++) {
				out.print(pairwDiff[q]);
				out.print("\t");
			}
			out.print("\n");
			out.close();



		
                        Writer writer2 = new FileWriter(results + "len" + accessionIndex + ".txt");
                        PrintWriter out2 = new PrintWriter(writer2);
                        for (int q=0; q<IndexesToGet.length; q++) {
                                out2.print(length[q]);
                                out2.print("\t");
                        }
                        out2.print("\n");
                        out2.close();




                        Writer writer3 = new FileWriter(results + "dif" + accessionIndex + ".txt");
                        PrintWriter out3 = new PrintWriter(writer3);
                        for (int q=0; q<IndexesToGet.length; q++) {
                                out3.print(differences[q]);
                                out3.print("\t");
                        }
                        out3.print("\n");
                        out3.close();




	

			// Write accession names
			if (accessionIndex == 5) {
				Writer writerN = new FileWriter(results + "names.txt");
				PrintWriter outN = new PrintWriter(writerN);
	    	   		for (int sub=0; sub<IndexesToGet.length; sub++) {
					outN.print(splitIDs[IndexesToGet[sub]] + "\n");
				}
	       			outN.print("\n");
				outN.close();
			}
/*			
			// And island IDs
			
			Writer writerIsl = new FileWriter("/global/lv70590/Andrea/pi/islIds.txt");
			PrintWriter outIsl = new PrintWriter(writerIsl);
		       	for (int sub=0; sub<IslandIndexes.length; sub++) {
				outIsl.print(IslandIndexes[sub] + "\n");
			}
		       	outIsl.print("\n");
			outIsl.close();
			
			System.out.println("fatto! :) "); 
		

			// And letter IDs
	
			Writer writerL = new FileWriter("/global/lv70590/Andrea/pi/letterId.txt");
       	        	PrintWriter outL = new PrintWriter(writerL);
       		        for (int sub=0; sub<IslandIndexes.length; sub++) {
                                outL.print(IslandIndexes[sub].charAt(0) + "\n");
                        }
             		outL.print("\n");
          		outL.close();
*/




	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Pairwise_shore pairwise_shore = new Pairwise_shore();
		pairwise_shore.setMatrixFile(args[0], args[1], args[2]);
	}
}
