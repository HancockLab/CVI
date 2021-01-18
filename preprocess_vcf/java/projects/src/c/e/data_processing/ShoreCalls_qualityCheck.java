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

public class ShoreCalls_qualityCheck {

	public ShoreCalls_qualityCheck() {}
	
	public void setMatrixFile(String matrixName){
		
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
						
						
						
			
			
			
			
			
			
			
			

			
			
			
			// Open the big matrix
	    	matrix = new File(matrixName);
	    	scannerMatrix = new Scanner(matrix);

        	// Get IDs and pairNames from Header
			String snp = scannerMatrix.nextLine();
			String[] splitSnp = snp.split("\t");
        	int numAccessions = splitSnp.length-3;
        	
        	int[] IDs = new int[numAccessions];	
        	
			int count =0;
			for (int acc1=3; acc1<splitSnp.length; acc1++) {
        		IDs[count] = Integer.parseInt(splitSnp[acc1]);
        		count = count +1;
        	}
			
			
			

        	
        	// Begin to calculate 
        	
			int[] basesCalled = new int[numAccessions];
			int totalBases = 0;
			int chr =0;
			int pos = 0;
			int posRepeated = 0;
			
			while ( scannerMatrix.hasNextLine() ) {
				snp = scannerMatrix.nextLine();
	        	splitSnp = snp.split("\t");
				
				// Just print where we are
        		if (chr != Integer.parseInt(splitSnp[0])) {
        			System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
        		}
        		chr = Integer.parseInt(splitSnp[0]);
        		
        		// Check how many positions are twice in the matrix
        		if (pos == Integer.parseInt(splitSnp[1])) {
        			posRepeated = posRepeated + 1;
        		}
        		pos = Integer.parseInt(splitSnp[1]);
        		
        		for (int acc1=3; acc1<splitSnp.length; acc1++) {
	        		char base1 = splitSnp[acc1].charAt(0);
	        		
	        		if ( (base1 == 'A') || (base1 == 'T') || (base1 == 'G') || (base1 == 'C') ) {
	        			basesCalled[acc1-3] = basesCalled[acc1-3] + 1;
	        		} else {
	        			if ((base1 != 'N')) {
	        				System.out.println("Problem at: " + snp);
	        			}
	        		}
        		}
        		totalBases = totalBases + 1;
	        }
			// Done
			
			
        	
			
			
			
			
			
			
			// Write out
			String countryHap = null;
			
			Writer writerM = new FileWriter(matrixName + "_qualityCheck.txt");
			PrintWriter outM = new PrintWriter(writerM);
			
			for (int p=0; p<numAccessions; p++) {
				
				// Find country of this sample
	    		for (int co=0; co<ids.length; co++) {
	    			if (ids[co] == IDs[p]) {
	    				countryHap = country[co];
	    			}
	    		}
				outM.print(IDs[p] + "\t" + countryHap + "\t" + (double)basesCalled[p]/(double)totalBases + "\t" + basesCalled[p] + "\t " + totalBases + "\n");
			}
			outM.close();
			
			System.out.println("Done :)");
        	
			
			
			
			
			/*
			for (int id=0; id < IDs.length; id++) {
				if (IDs[id] == 29304) {
					System.out.println("bases called: " + basesCalled[id]);
					System.out.println("totalBases: " + totalBases);					
				}
			}
			*/
			System.out.println("Positions repeated: " + posRepeated);

			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ShoreCalls_qualityCheck shoreCalls_qualityCheck = new ShoreCalls_qualityCheck();
		shoreCalls_qualityCheck.setMatrixFile(args[0]);
	}
}
