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

public class TajD_perAdmixtureCluster {
	
	public TajD_perAdmixtureCluster() {}
	
	public void setMatrixFile(String matrixName, String clusterString){
		
		try {
			System.out.println("src/c/e/data_processing/TajD.java");			
			
			int cluster = Integer.parseInt(clusterString);
			
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

			int all1001 = 0;
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
                    	
                    	// ids1001[i-2] = Integer.parseInt(splitIDs[i]);
	    				// Count Iberians
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
                    }
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
				}
			}
			
			// From HaplotypeMonsterEater END
						
						

			
			
			
			
			
			
			
			
			
			


			// Load Ids separated by admixture cluster

	    	
	    		// Get in the list of 1135
	    		File genom1001 = new File("/home/lv70590/Andrea/data/1001genomes-accessions.txt");
			Scanner scanner1001 = new Scanner(genom1001); 
			scanner1001.nextLine();			   	
			
			// Count samples
			int the1001 = 0;
			while (scanner1001.hasNextLine()) {
				String snp = scanner1001.nextLine();
				the1001 = the1001 + 1;
			}
			System.out.println("We Have: " + the1001 + " the1001");
			
			scanner1001 = new Scanner(genom1001);
			scanner1001.nextLine();	
			
			int[] thousIds = new int[the1001];
			String[] thousAdmixtures = new String[the1001];
			
			// Count samples
			the1001 = 0;
			while (scanner1001.hasNextLine()) {
				String snp = scanner1001.nextLine();	
				String[] splitSnp = snp.split("\t");
				
				thousIds[the1001] = Integer.parseInt(splitSnp[0]);
				thousAdmixtures[the1001] = splitSnp[12];
				the1001 = the1001 + 1;
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
				groupsThousAndMor[thousIds.length + moroccans] = splitMo[2];
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
	    	// [blu_circle, wht_circle, ylw_circle, purple_circle, red_circle, orange_circle, ltblu_circle, grn_circle, pink_circle, High Atlas, North Middle Atlas, Riff, South Middle Atlas]
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
	    	//for (int p=0; p<idsAdmixtureSorted.length; p++) {
	    	//	System.out.println(Arrays.toString(idsAdmixtureSorted[p]));
	    	//}
	    	
	    	
			// Got ids sorted by admixture cluster
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			// Build IndexesToGet, array of interesting indexes 
	    	// "cluster" is the cluster i Analyse in this run
			
			int howMany = idsAdmixtureSorted[cluster].length;
			System.out.println("howMany: "+ howMany);
			
			int[] IndexesToGet = new int[howMany];
			IndexesToGet = idsAdmixtureSorted[cluster];
			
		












	
			//
			// Now the real game
			// 

        	// Begin to calculate pairwise differences
			// And the number of segregating sites
			// 
        	
			int[] differences = new int[(IndexesToGet.length*(IndexesToGet.length - 1) )/2];
			// int[] length = new int[(IndexesToGet.length*(IndexesToGet.length - 1) )/2];
			int S = 0;
			int chr =0;
			
		    	matrix = new File(matrixName);
		    	scannerMatrix = new Scanner(matrix);
		    	scannerMatrix.nextLine();
	    	
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Just print where we are
    			if (chr != Integer.parseInt(splitSnp[0])) {
    				System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
    			}
    			chr = Integer.parseInt(splitSnp[0]);    			
    			
    			// Get pi and S
    			
    			int pairs = 0;
    			Boolean segrega = false;
    			
    			for (int acc=0; acc<IndexesToGet.length - 1; acc++) {
    				char base1 = splitSnp[IndexesToGet[acc]].charAt(0);
    		    	        for (int acc2=acc + 1; acc2<IndexesToGet.length; acc2++) {
    				    	char base2 = splitSnp[IndexesToGet[acc2]].charAt(0);
    				       	if ( (base1 != 'N') && (base2 != 'N') ) {
    			       			// length[pairs] = length[pairs] + 1;
    		       				if (base1 != base2) {
    			       				differences[pairs] = differences[pairs] + 1;
    				       			segrega = true;
    				       		}
    				       	}
    				       	pairs = pairs + 1;
    			        }
    			}
    			if (segrega) {
    				S = S + 1;
    			}
    			// System.out.println("differences: "+ Arrays.toString(differences));
    			// System.out.println("length: "+ Arrays.toString(length));
   	 		}
			System.out.print("S: " + S + "\n");
			
			// Calculate pi - For tajimas D, using differences and S raw, not per basepair
			
			double pi = 0.0;
			// double piPerBp = 0.0;
			int comp = 0;
			//double[] pairwDiff = new double[IndexesToGet.length];
			
			for (int q=0; q<differences.length; q++) {
				//pairwDiff[q] = (double)(differences[q])/(double)(length[q]);
				if (differences[q] > 0) {
					pi = pi + (double)(differences[q]);
					// piPerBp = piPerBp + (double)(differences[q])/(double)(length[q]);
					comp = comp + 1;
				}
			}
			pi = pi/(double)comp;
			// piPerBp = piPerBp/(double)comp;
			
			System.out.print("pi: " + pi + "\n");
			// System.out.print("piPerBp: " + piPerBp + "\n");
			
			// Done
			
			
			
			// Calculate all the partials for tajD
			
			int n = IndexesToGet.length;

			// Get a1
			Double a1 = 0.0;
			for (int i=1; i<n; i++) {
				a1 = a1 + 1/(double)i;
			}
			System.out.print("a1: " + a1 + "\n");

			// Get a2
			Double a2 = 0.0;
			for (int i=1; i<n; i++) {
				a2 = a2 + 1/Math.pow(i, 2);
			}
			System.out.print("a2: " + a2 + "\n");
			
			// Get b1
			Double b1 = (double)(n + 1)/(double)(3*(n - 1));
			System.out.print("b1: " + b1 + "\n");

			// Get b2
			Double b2 = 2*(double)(Math.pow(n, 2) + n + 3)/(double)(9*n*(n - 1));
			System.out.print("b2: " + b2 + "\n");

			// Get c1
			Double c1 = b1 - (1/a1);
			System.out.print("c1: " + c1 + "\n");

			// Get c2
			Double c2 = b2 - (double)(n+2)/(double)(a1*n) + (double)a2/(double)Math.pow(a1, 2);
			System.out.print("c2: " + c2 + "\n");

			// Get e1
			Double e1 = c1/a1;
			System.out.print("e1: " + e1 + "\n");
			
			// Get e2
			Double e2 = c2/(Math.pow(a1, 2) + a2);
			System.out.print("e2: " + e2 + "\n");
			
			
			
			
			
			// Calculate tajD
			
			Double tajD = (double)( pi - (S/a1) ) / Math.sqrt( (e1*S) + (e2*S*(S-1)) );
			
			
			

			// Write out
			
			Writer writer = new FileWriter("/global/lv70590/Andrea/tajD2/" + cluster + ".txt");
			PrintWriter out = new PrintWriter(writer);
			out.print("Cluster: " + groupUnique[cluster] + "\n");
			out.print("tajD" + "\t" + "pi" + "\t" + "S" + "\t" + "n" + "\t" + "a1" + "\t" + "a2" + "\t" + "b1" + "\t" + "b2" + "\t" + "c1" + "\t" + "c2" + "\t" + "e1" + "\t" + "e2" + "\n");
			out.print(tajD + "\t" + pi + "\t" + S + "\t" + n + "\t" + a1 + "\t" + a2 + "\t" + b1 + "\t" + b2 + "\t" + c1 + "\t" + c2 + "\t" + e1 + "\t" + e2 + "\n");
			out.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TajD_perAdmixtureCluster tajD_perAdmixtureCluster = new TajD_perAdmixtureCluster();
		tajD_perAdmixtureCluster.setMatrixFile(args[0], args[1]);
	}
}
