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

public class SweepFinder_buildInput {
	
	public SweepFinder_buildInput() {}
	
	public void setFileToSubSet(String matrixName){
		
		try {
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
			
			int cviZindex = 0;
			int colIndex = 0;
			
			for (int i=3; i<splitIDs.length; i++) {
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
			
			for (int i=3; i<splitIDs.length; i++) {
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
						
						
						
			
			
			
			
			
			
			
			// Build IndexesToGet, array of interesting indexes 
			// 
			// rel=["9871", "9947", "9887", "9600", "9554", "9832", "9542", "9944", "9598", "9905", "9555", "9543", "9533", "9869", "9549", "9837", "9879", "35614", "18515"]
			int[] are={27154, 27153, 27152, 22638, 22022, 22019, 22017, 12908, 12763, 12762, 12761};
			
			// int[] are = {27154, 27153, 27152, 22638, 22022, 22019, 22017, 12908, 12763, 12762, 12761};
			//int[] are = {22010, 22009, 22003, 18516, 18511, 18513, 35523, 35598, 35595, 35596, 35601, 35602, 35604, 35606, 35613, 35616};	// Really, HA
			

			// int[] rel = {9871, 9947, 9887, 9600, 9554, 9832, 9542, 9944, 9598, 9905, 9555, 9543, 9533, 9869, 9549, 9837, 9879};

			int[] areRel = are;

			int[] nSample = new int[areRel.length];
			for (int n=0; n<nSample.length; n++) {
				nSample[n] = 0;
			}






int howMany = areRel.length;
			
            int[] IndexesToGet = new int[howMany];
			
            int count = 0;			
			for (int a=0; a<areRel.length; a++) {
                    for (int s=3; s<splitIDs.length; s++) {
                            if ( Integer.parseInt(splitIDs[s]) == areRel[a]) {
                            	IndexesToGet[count] = s;
								count = count + 1;
                            }
                    }
            }
			System.out.println("Got indexes");
			
			
			
			
			int[] areInd = new int[are.length];
			// int[] relInd = new int[rel.length];
			
        		int countA = 0;			
        		// int countR = 0;
			
			for (int a=0; a<are.length; a++) {
                    		for (int s=3; s<splitIDs.length; s++) {
                        		if ( Integer.parseInt(splitIDs[s]) == are[a]) {
                            			areInd[countA] = s;
						countA = countA + 1;
                	        	}
        	        	}
	         	}
			//for (int r=0; r<rel.length; r++) {
                	//	for (int s=3; s<splitIDs.length; s++) {
                	//		if ( Integer.parseInt(splitIDs[s]) == rel[r]) {
                	//			relInd[countR] = s;
			//			countR = countR + 1;
                    	//		}
                	//	}
			//}
			



System.out.println("Got indexes");
			
			
			

			int[] areSfs = new int[areInd.length + 1];
			// int[] relSfs = new int[relInd.length + 1];
			// int[][][] jsfs = new int[areInd.length + 1][relInd.length + 1][pop3Ind.length + 1];
			
			long basesNoN = 0;
			

			// Charge the mask
			//BitSet[] mask = new BitSet[5];
			//for (int c=0; c<5; c++) {
			//	mask[c]= new BitSet();
			//}
			//File fileMask = new File(maskName);
			//Scanner scannerMask = new Scanner(fileMask);
		        //int chrCVI=0;
		        //String lineMask=null;
		        //for (int m=0; m<10; m++) {
			//      	lineMask = scannerMask.nextLine();
			//       	if (m%2 == 1) {
			//       	for (int i=0; i<lineMask.length(); i++) {
			//        		if (lineMask.charAt(i) == ('1')) {
			//        			mask[chrCVI].set(i);
			//        		}
			//        	}
			//        	chrCVI=chrCVI+1;
			//       	}
			//}





		String results = "/global/lv70590/Andrea/analyses/sweepFinder/";
			
			
			// Open the big matrix
	    	matrix = new File(matrixName);
	    	scannerMatrix = new Scanner(matrix);
			
	       	// Go with contents
	       	int snps = 0;
		int chrMem=0;
		Writer writer = null;
                PrintWriter out = null;

		Writer writerA = null;
		PrintWriter outA = null;
	
	       	scannerMatrix.nextLine();
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		  	     	String[] splitSnp = snp.split("\t");
		       		
				int chr = Integer.parseInt(splitSnp[0]);
				
				if (chrMem < chr) {
					if (chrMem != 0) {
						out.close();
						outA.close();
					}
					writer = new FileWriter(results + chr + "_sweepFinder.txt");
		                        out = new PrintWriter(writer);
					out.print("position" + "\t" + "x" + "\t" + "n" + "\t" + "folded" + "\n");

					writerA = new FileWriter(results + chr + "_ADM_params.map");
					outA = new PrintWriter(writerA);
					// outA.print("position" + "\t" + "rate" + "\n");
				}
				chrMem = chr;
				
		       		// If there is any N, out
		       		boolean N = false;
		       		for (int sub=0; sub<IndexesToGet.length; sub++) {
		       			if (splitSnp[IndexesToGet[sub]].charAt(0) == 'N') {
		       				N = true;
						nSample[sub] = nSample[sub] + 1;
		       			}
		      	 	}
		       	
		       	if (!N) {
				basesNoN = basesNoN + 1;
		       		// Get one base as reference
			       	char refBase = splitSnp[2].charAt(0);

			       	// Select segregating sites
			       	boolean segrega = false;
			       	for (int sub=0; sub<IndexesToGet.length; sub++) {
			       		if (splitSnp[IndexesToGet[sub]].charAt(0) != refBase) {
			       			segrega = true;
			       		}
			       	}
				int pos = Integer.parseInt(splitSnp[1]);
				
			       	// If it is segregating, output
			       	if (segrega) { // && mask[chr].get(pos)) {
			       		int areDer = 0;
				       	for (int a=0; a<areInd.length; a++) {
				       		if (splitSnp[areInd[a]].charAt(0) != refBase) {
				       			areDer = areDer + 1;
				       		}
					}
				       	//int relDer = 0;
				       	//for (int r=0; r<relInd.length; r++) {
				        //		if (splitSnp[relInd[r]].charAt(0) != refBase) {
				        //			relDer = relDer + 1;
				       	//	}
					//}

					areSfs[areDer] = areSfs[areDer] + 1;
				       	//relSfs[relDer] = relSfs[relDer] + 1;
				       	//jsfs[areDer][relDer][pop3Der] = jsfs[areDer][relDer][pop3Der] + 1;
				       	
					out.print(pos + "\t" + areDer + "\t" + areInd.length + "\t" + "0" + "\n");
					outA.print(chr + "\t" + chr+"_"+pos + "\t" + "0" + "\t" + pos + "\n");

					snps = snps + 1;
			       	}
		       	}
		}
		out.close();
		outA.close();
			
			System.out.println("snps: " + snps);
                        System.out.println("basesNoN: " + basesNoN);
			System.out.println(Arrays.toString(areRel));
			System.out.println(Arrays.toString(nSample));

			// Open also the writing file
			// String whereWe = "/global/lv70590/Andrea/analyses/jsfs/";
			

			


	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SweepFinder_buildInput sweepFinder_buildInput= new SweepFinder_buildInput();
		sweepFinder_buildInput.setFileToSubSet(args[0]);
	}
}
