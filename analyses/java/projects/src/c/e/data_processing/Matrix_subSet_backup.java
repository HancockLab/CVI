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

public class Matrix_subSet {
	
	public Matrix_subSet() {}
	
	public void setFileToSubSet(String matrixName){
		
		try {
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
						
						
						
			
			
			
		// Choose the subset of samples
		

		// Iberians selected as the closest to can based on pi
		//
		int[] iberiansIDs = {};
		int howMany = canarianIndexes.length + iberiansIDs.length; 			//  + moroccanIndexes.length + iberianIndexes.length;

		int[] IndexesToGet = new int[howMany];
		String[] IslandIndexes = new String[howMany];
		
		int count = 0;
// Order samples by island
		String[] fineOrder = {
			"P7-", "P8-", "P9-", "P10-", 
			"P13-1", "P13-5", "P14-", "P15-",
			"T1-", "T2-", "T3-", "T4-", "T16-", 
			"T10-", "T12-", "T13-", "T15-", 
			"H1-", "H2-", "H3-", "H4-", "H5-", "H6-", "H7-", "H8-", "H9-", "H10-", "H11-", "H12-", "H13-", "H14-", "H15-", "H16-", "H17-", "H18-", "H19-", "H20-", "H21-", "H22-", "H23-", "H24-", "H25-", "H26-", "H27-", 
			"C1-", "C2-", "C3-", "C4-", "C5-", "C6-", "C7-", "C8-", "C9-", "C10-", "C11-", "C12-", "C13-", "C14-", "C15-", "C16-", "C17-", "C18-", "C19-", "C20-", "C21-", "C22-", "C23-", "C24-", "C25-", "C26-", "C27-", 
 			"G1-", "G102-",  "G103-",  "G104-",  "G105-",  "G106-",  "G107-"					
		};

		File idsToPlantsFile = new File("/Volumes/wDinoDisk/final/files/idsToPlants.txt");
		for (int o=0; o<fineOrder.length; o++) {
			Scanner scannerIdsPlants = new Scanner(idsToPlantsFile);
			boolean got = false;
			while ( scannerIdsPlants.hasNextLine() ) {
				String snp = scannerIdsPlants.nextLine();
			    	String[] splitsnp = snp.split("\t");
				
				// Mind header
				if (splitsnp[0].charAt(0) != '#'){
					// Is that the right sample?
					boolean thatSit = true;
					// Get minimum length between splitsnp[1] and fineOrder[o] for comparing them;
					int min = fineOrder[o].length();
					if (splitsnp[1].length() < fineOrder[o].length()) {
						min = splitsnp[1].length();
					}
					for (int check=0; check<min; check++) {
						if (splitsnp[1].charAt(check) != fineOrder[o].charAt(check)) {
							thatSit = false;
						}
					}
					if (thatSit) {
						IslandIndexes[count] = splitsnp[1];
						for (int i=2; i<splitIDs.length; i++) {
							if (Integer.parseInt(splitIDs[i]) == Integer.parseInt(splitsnp[0])) {
								IndexesToGet[count] = i;
								
								got = true;
								count = count + 1;
							}
						}
					}
				}
			}
		}
		
		// If subset of iberians are in:
		//
            	// Iberians selected as the closest to can based on pi

		for (int ib=0; ib<iberiansIDs.length; ib++) {
         		for (int allI=0; allI<iberianIndexes.length; allI++) {
                        	if ( Integer.parseInt(splitIDs[iberianIndexes[allI]]) == iberiansIDs[ib]) {
                            		IndexesToGet[count] = allI;
					count = count + 1;
                        	}
                	}
            	}
		System.out.println("Got indexes");
			



		// Open the big matrix
	    	matrix = new File(matrixName);
	    	scannerMatrix = new Scanner(matrix);
			
		// Open also the writing file
		Writer writer = new FileWriter(matrixName + "_subMatrix.txt");
		PrintWriter out = new PrintWriter(writer);

		Writer writerH = new FileWriter(matrixName + "_subMatrix_head.txt");
                PrintWriter outH = new PrintWriter(writerH);			
		// Print header
	       	for (int sub=0; sub<IndexesToGet.length; sub++) {
			outH.print(splitIDs[IndexesToGet[sub]] + "\t");
		}
	       	outH.print("\n");
		outH.close();
			
	       	// Go with contents
		while ( scannerMatrix.hasNextLine() ) {
			String snp = scannerMatrix.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	// If there is any N, out
		       	boolean N = false;
		       	for (int sub=0; sub<IndexesToGet.length; sub++) {
		       		if (splitSnp[IndexesToGet[sub]].charAt(0) == 'N') {
		       			N = true;
		       		}
		       	}
		       	
		       	if (!N) {
		       		// Get one base as reference
			       	char firstBase = splitSnp[IndexesToGet[0]].charAt(0);

			       	// Select segregating sites
			       	boolean segrega = false;
			       	for (int sub=0; sub<IndexesToGet.length; sub++) {
			       		if (splitSnp[IndexesToGet[sub]].charAt(0) != firstBase) {
			       			segrega = true;
			       		}
			       	}
			       	
			       	// If it is segregating, output
			       	if (segrega) {
				       	for (int sub=0; sub<IndexesToGet.length; sub++) {
				       		
						// Distinguish matches to mismatches
				       		if (splitSnp[IndexesToGet[sub]] == splitSnp[colIndex]) {
							out.print("0" + "\t");
						} else {
							out.print("1" + "\t");
						}
					}
				       	out.print("\n");		       		
			       	}	
		       	}
		}
		out.close();
			
			
			
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public static void main(String[] args) {
	Matrix_subSet matrix_subSet = new Matrix_subSet();
	matrix_subSet.setFileToSubSet("/home/lv70590/Andrea/analyses/haplotypeCallsCan/ourAnIBP_withCol0_matrix.txt");
}
}
