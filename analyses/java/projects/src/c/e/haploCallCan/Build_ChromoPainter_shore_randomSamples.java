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

public class Build_ChromoPainter_shore_randomSamples {
	
	private Build_ChromoPainter_shore_randomSamples() {}

	public void setSnpFile(String matrixName, String accessionStr, String iterateStr, String results){
		
		try {
			int accession = Integer.parseInt(accessionStr);
			int iterate = Integer.parseInt(iterateStr);
			
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
			
			
			
			
			
			
			

			// Load Ids separated by admixture cluster - eliminate moroccans, cvi-0, can-0

	    	
	    	// Get in the list of 1135
	    	File genom1001 = new File("/home/lv70590/Andrea/data/1001genomes-accessions.txt");
			Scanner scanner1001 = new Scanner(genom1001);
			scanner1001.nextLine();			   	
			
			// Count samples
			int the1001 = 0;
			while (scanner1001.hasNextLine()) {
				String snp = scanner1001.nextLine();
				String[] splitSnp = snp.split("\t");
				if ( (Integer.parseInt(splitSnp[0]) != 6911) && (Integer.parseInt(splitSnp[0]) != 9606) && (Integer.parseInt(splitSnp[0]) != 9939) && (Integer.parseInt(splitSnp[0]) != 9940) && (Integer.parseInt(splitSnp[0]) != 7063)) {
					the1001 = the1001 + 1;
				}
			}
			System.out.println("We Have: " + the1001 + " the1001");
			
			scanner1001 = new Scanner(genom1001);
			scanner1001.nextLine();	
			
			int[] thousIds = new int[the1001];
			String[] thousAdmixtures = new String[the1001];
			
			// Get samples
			the1001 = 0;
			while (scanner1001.hasNextLine()) {
				String snp = scanner1001.nextLine();	
				String[] splitSnp = snp.split("\t");
				if ( (Integer.parseInt(splitSnp[0]) != 6911) && (Integer.parseInt(splitSnp[0]) != 9606) && (Integer.parseInt(splitSnp[0]) != 9939) && (Integer.parseInt(splitSnp[0]) != 9940) && (Integer.parseInt(splitSnp[0]) != 7063)) {
					thousIds[the1001] = Integer.parseInt(splitSnp[0]);
					thousAdmixtures[the1001] = splitSnp[10];
					the1001 = the1001 + 1;
				}
			}
			// System.out.println("1135 ids: " + Arrays.toString(thousIds));
			// System.out.println("1135 ids: " + Arrays.toString(thousAdmixtures));
			

			// Now add moroccans
	    		
			File morClustFile = new File("/home/lv70590/Andrea/data/moroccan_samples-Sheet1.tsv");
	    		// File morClustFile = new File("/home/lv70590/Andrea/data/moroccan_samples_plusMadeirans.tsv");
			Scanner scannerMor = new Scanner(morClustFile);
			
			// Count samples
			int moroccans = 0;
			while (scannerMor.hasNextLine()) {
				String line = scannerMor.nextLine();			   	
				if (line.charAt(0) != '#') {
					moroccans = moroccans + 1;
				}
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
				if (mo.charAt(0) != '#') {
					idsThousAndMor[thousIds.length + moroccans] = Integer.parseInt(splitMo[1]);
					groupsThousAndMor[thousIds.length + moroccans] = splitMo[2];
					moroccans = moroccans + 1;
				}
			}
			//System.out.println("ids: " + Arrays.toString(idsThousAndMor));
			//System.out.println("groups: " + Arrays.toString(groupsThousAndMor));

	    	
			
			
			
			
			
						
	    	// Find unique list of admixture clusters
			
	    	String[] groupUnique = {groupsThousAndMor[0]};
    		
	    	for (int c=0; c< groupsThousAndMor.length; c++) {
	    		if (!groupsThousAndMor[c].equals("admixed")) {

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
		System.out.println("We have: " + groupUnique.length + " unique groups ");
		System.out.println("Unique groups: " + Arrays.toString(groupUnique));
	    	// Unique groups: [western_europe, italy_balkan_caucasus, central_europe, asia, south_sweden, north_sweden, germany, spain, relict, High Atlas, North Middle Atlas, Riff, South Middle Atlas, Madeira]
		//String[] groupMem = groupUnique;
	 	//groupUnique = new String[groupUnique.length];
	 	//int runn = 0;
	   	//for (int cM=0; cM< 9; cM++) {
    		//	groupUnique[runn] = groupMem[cM];
    		//	runn = runn + 1;
		//}
		//groupUnique[runn] = groupMem[groupMem.length-2];
	 	//runn = runn + 1;
	  	//for (int cM=9; cM< 12; cM++) {
		//    	groupUnique[runn] = groupMem[cM];
		//    	runn = runn + 1;
	 	//}
                //groupUnique[runn] = groupMem[groupMem.length-1];
                //runn = runn + 1;
	    	// Unique groups: [western_europe, italy_balkan_caucasus, central_europe, asia, south_sweden, north_sweden, germany, spain, relict, South Middle Atlas, High Atlas, North Middle Atlas, Riff]
	    	//System.out.println("We have: " + groupUnique.length + " unique groups ");
	    	//System.out.println("Unique groups: " + Arrays.toString(groupUnique));
				    	
	    	
	    	
	    	
	    	
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
	    	//	System.out.println(idsAdmixtureSorted[p].length);
	    	//}
	    	
	    	
			// Got ids sorted by admixture cluster
			
			
	    	
	    	
			
	    	
	    	
	    	
	    	
			
		// Random samples per cluster
	    	
	    	int repl = 20;
	    	int[][] randomPerCLustIndex = new int[groupUnique.length][];
    		for (int gU=0; gU<groupUnique.length; gU++) {
    		   if (idsAdmixtureSorted[gU].length > repl) {
			randomPerCLustIndex[gU] = new int[repl];
    			
    			// Get random 
    			int[] subSet = new int[repl];
    			int[][] toRun = idsAdmixtureSorted;
        		
    			Random randomGenerator = new Random();
    			for (int boot=0; boot<subSet.length; boot++) {
    				
				subSet[boot] = randomGenerator.nextInt(toRun[gU].length);
    				randomPerCLustIndex[gU][boot] = toRun[gU][subSet[boot]];		// Integer.parseInt(splitIDs[toRun[gU][subSet[boot]]]);
    				
    				int[] toRunMem = new int[toRun[gU].length - 1];
    				int run=0;
    				for (int m=0; m<toRun[gU].length; m++) {
    					if (m != subSet[boot]) {
    						toRunMem[run] = toRun[gU][m];
    						run = run + 1;
    					}
    				}
    				toRun[gU] = toRunMem;
    				// System.out.println(toRun[gU].length);
    			}
		   } else {
			randomPerCLustIndex[gU] = idsAdmixtureSorted[gU];
		   }
		}









		// Change some of them
		// Unique groups: [western_europe, italy_balkan_caucasus, central_europe, asia, south_sweden, north_sweden, germany, spain, relict, South Middle Atlas, High Atlas, North Middle Atlas, Riff, Madeira]
		


		// Eliminate zin from rif

		int[] mem = randomPerCLustIndex[randomPerCLustIndex.length-2];
		randomPerCLustIndex[randomPerCLustIndex.length-2] = new int[mem.length-2];
		int zinn = 0;
		for (int r=0; r<mem.length; r++) {
			if ( (Integer.parseInt(splitIDs[mem[r]]) != 18515)  && (Integer.parseInt(splitIDs[mem[r]]) != 35614)  ) {
				randomPerCLustIndex[randomPerCLustIndex.length-2][zinn] = mem[r];
				zinn = zinn + 1;
			}
		}






		// Change western_europe
/*
                File goodWEur = new File("/home/lv70590/Andrea/data/bestSamplesWestEur.txt");
                Scanner scanNamesGW = new Scanner(goodWEur);
                int samplesGW = 0;
                        while (scanNamesGW.hasNextLine()) {
                                String name = scanNamesGW.nextLine();
                                samplesGW = samplesGW + 1;
                        }
                        int[] wEur = new int[samplesGW];

                        samplesGW = 0;
                        scanNamesGW = new Scanner(goodWEur);
                        while (scanNamesGW.hasNextLine()) {
                                String name = scanNamesGW.nextLine();
                                String[] splitName = name.split("\t");
                                wEur[samplesGW] = Integer.parseInt(splitName[0]);
                                samplesGW = samplesGW + 1;
                        }
                        System.out.println(Arrays.toString(wEur));
                        System.out.println("We have: " + wEur.length + " goodWEur");




                randomPerCLustIndex[0] = new int[wEur.length];
                // Find the index

                for (int n=0; n< wEur.length; n++) {
                        for (int i=3; i<splitIDs.length; i++) {
                                if (Integer.parseInt(splitIDs[i]) == wEur[n]) {
                                        randomPerCLustIndex[0][n]  = i;
                                }
                         }
                }
*/

		

		File goodFile = null;
		Scanner scanGood = null;
		int samplesGood = 0;


                // Change Iberia

                goodFile = new File("/home/lv70590/Andrea/data/bestSamplesIbp.txt");
                scanGood = new Scanner(goodFile);
                samplesGood = 0;
                        while (scanGood.hasNextLine()) {
                                String name = scanGood.nextLine();
                                samplesGood = samplesGood + 1;
                        }
                        int[] goodIberians = new int[samplesGood];

                        samplesGood = 0;
                        scanGood = new Scanner(goodFile);
                        while (scanGood.hasNextLine()) {
                                String name = scanGood.nextLine();
                                String[] splitName = name.split("\t");
                                goodIberians[samplesGood] = Integer.parseInt(splitName[0]);
                                samplesGood = samplesGood + 1;
                        }
                        System.out.println(Arrays.toString(goodIberians));
                        System.out.println("We have: " + goodIberians.length + " goodIberians");




                randomPerCLustIndex[7] = new int[goodIberians.length];

                // Find the index
                for (int n=0; n< goodIberians.length; n++) {
                        for (int i=3; i<splitIDs.length; i++) {
                                if (Integer.parseInt(splitIDs[i]) == goodIberians[n]) {
                                        randomPerCLustIndex[7][n]  = i;
                                }
                         }
                }


                // Change Relicts

                goodFile = new File("/home/lv70590/Andrea/data/bestSamplesRelAndZin.txt");
                scanGood = new Scanner(goodFile);
                samplesGood = 0;
                while (scanGood.hasNextLine()) {
                                String name = scanGood.nextLine();
                                samplesGood = samplesGood + 1;
                        }
                int[] goodRelicts = new int[samplesGood];

                        samplesGood = 0;
                        scanGood = new Scanner(goodFile);
                        while (scanGood.hasNextLine()) {
                                String name = scanGood.nextLine();
                                String[] splitName = name.split("\t");
                                goodRelicts[samplesGood] = Integer.parseInt(splitName[0]);
                                samplesGood = samplesGood + 1;
                        }
                        System.out.println(Arrays.toString(goodRelicts));
                        System.out.println("We have: " + goodRelicts.length + " goodRelicts");




		// Are instead or relicts
		//
		int[] madeiransNoThree = {12763, 12761, 22638, 12908, 12762, 22017, 22022, 22019, 27152, 27153, 27154};
		//
                randomPerCLustIndex[8] = new int[madeiransNoThree.length]; // goodRelicts.length - 1];

                // Find the index
		int did = 0;
                for (int n=0; n< madeiransNoThree.length; n++) {
                        for (int i=3; i<splitIDs.length; i++) {
                                if (Integer.parseInt(splitIDs[i]) == madeiransNoThree[n]) {			// Here to change if it is not relicts we want
                                        randomPerCLustIndex[8][did]  = i;
					did = did + 1;
                                }
                         }
                }




                for (int p=0; p<randomPerCLustIndex.length; p++) {
                        for (int s=0; s<randomPerCLustIndex[p].length; s++) {
                                System.out.print(Integer.parseInt(splitIDs[randomPerCLustIndex[p][s]]) + " ");
                        }
                        System.out.print("\n");
                }





		//System.out.println("Madeirans:");
               	//for (int are=0; are<madeiranIndexes.length; are++) {
                //        System.out.println(splitIDs[madeiranIndexes[are]]);
                //}


	
			
			
			String resultsFolder = results + accession + "/" + iterate + "/";

			
			
			// Get full list of samples to use:
    		// count
    		int samp=0;
    		
		Writer writerD = new FileWriter(resultsFolder + "donor_list_infile.txt");
		PrintWriter outD = new PrintWriter(writerD);
		
		Writer writerN = new FileWriter(resultsFolder + "names.txt");
                PrintWriter outN = new PrintWriter(writerN);

// Just change madeirans to first
int[] order = {8, 0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12};

	
    		for (int p2=1; p2<randomPerCLustIndex.length; p2++) {						// Change to 0 if you dont want to eliminate relicts
int p = order[p2];
    			String[] splitN = groupUnique[p].split(" ");
			String name=null;
			if (splitN.length == 1) {
				name=splitN[0];
			} else {
				if (splitN.length == 2) {
					name=splitN[0] + "_" + splitN[1];
				} else {
					name=splitN[0] + "_" + splitN[1] + "_" + splitN[2];
				}
			}
			outD.print(name + "\t" + randomPerCLustIndex[p].length + "\t" + (double)1/(double)(randomPerCLustIndex.length) + "\t" + 0.0001 +  "\n");
    			samp = samp + randomPerCLustIndex[p].length;
    		}
    		samp = samp + 1; 				// The canarian
    		outD.close();
    		
    		int[] indexesToUse = new int[samp];
    		
    		// Do it
    		samp=0;
    		//for (int p=0; p<randomPerCLustIndex.length; p++) {
for (int p2=1; p2<randomPerCLustIndex.length; p2++) {								// Change to 0 if you dont want to eliminate relicts
int p = order[p2];
        		for (int s=0; s<randomPerCLustIndex[p].length; s++) {
        			indexesToUse[samp] = randomPerCLustIndex[p][s];
        			outN.print(splitIDs[indexesToUse[samp]] + "\n");
				samp = samp + 1;
        		}
    		}
		// If Canaries
		// indexesToUse[samp] = canarianIndexes[accession];
		
		// If Madeira
		indexesToUse[samp] = madeiranIndexes[accession];
		
		// If Cvi-0
		// indexesToUse[samp] = cviZindex;
		
		// If relicts
                //for (int i=3; i<splitIDs.length; i++) {
                //	if (Integer.parseInt(splitIDs[i]) == goodRelicts[accession]) {                        // Here to change if it is not relicts we want
                //        	indexesToUse[samp] =  i;
                //         }
                //}
                 
		outN.print(splitIDs[indexesToUse[samp]] + "\n");
    		System.out.print(splitIDs[indexesToUse[samp]] + "\n");
		samp = samp + 1;

		outN.close();
			
			// Get proper and ordered samples for chromoP
			// Equal number of iberians and Moroccans, plus one canarian at a time
			// Random Iberian and Moroccans, 50
			// Indexes to use
			// int[] indexesToUse = new int[50 + 50 + 1];
			// int run = 0;
			
			/*
			// Get random Moroccans
			int[] subSet = new int[50];
			int rand = 0;
			Random randomGenerator = new Random();
			for (int boot=0; boot<subSet.length; boot++) {
				subSet[boot] = randomGenerator.nextInt(moroccanIndexes.length);
				indexesToUse[run] = moroccanIndexes[subSet[boot]];
				run = run + 1;
			}
			System.out.println(Arrays.toString(subSet));
			
			// Get random Iberians
			subSet = new int[50];
			rand = 0;
			randomGenerator = new Random();
			for (int boot=0; boot<subSet.length; boot++) {
				subSet[boot] = randomGenerator.nextInt(iberianIndexes.length);
				indexesToUse[run] = iberianIndexes[subSet[boot]];
				run = run + 1;
			}
			System.out.println(Arrays.toString(subSet));
			
			// And the one canarian
			indexesToUse[run] = canarianIndexes[accession];
			run = run + 1;
			

			// Write accession names
			Writer writerN = new FileWriter(resultsFolder + accession + ".txt");
			PrintWriter outN = new PrintWriter(writerN);
			
			for (int name=0; name<indexesToUse.length; name++) {
				outN.print(splitIDs[indexesToUse[name]] + "\t");				
			}
			outN.print("\n");
			outN.close();
			*/
			
			
			
			
			
			// Now the real game
			
			scannerMatrix = new Scanner(matrix);
			scannerMatrix.nextLine();
			int chr = 0;
			Writer fineWriter = null;
			PrintWriter outFine = null;
			Writer writerA = null;
			PrintWriter outA = null;
			
			// Open writing file
						
			Writer writer = new FileWriter(resultsFolder + "haplo.txt");
			PrintWriter out = new PrintWriter(writer);
			
			// Positions for ChromoP
			fineWriter = new FileWriter(resultsFolder + "positions.txt");
			outFine = new PrintWriter(fineWriter);
			outFine.print("P ");
			
			// ADM_params
			writerA = new FileWriter(resultsFolder + "ADM_params.map");
			outA = new PrintWriter(writerA);
			
			int snps = 0;
			
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Just print where we are
    			if (chr != Integer.parseInt(splitSnp[0])) {
    				System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
    			}
    			chr = Integer.parseInt(splitSnp[0]);    			
    			
    			// Discard if there are Ns - and keep only segregating sites
    			Boolean Ns = false;
    			Boolean segrega = false;
    			char base1 = splitSnp[indexesToUse[0]].charAt(0);
    			
    			for (int i=0; i<indexesToUse.length; i++) {
    				if (splitSnp[indexesToUse[i]].charAt(0) == 'N') {
    					Ns = true;
    				} else {
    					if (splitSnp[indexesToUse[i]].charAt(0) != base1) {
    						segrega = true;
    					}
    				}
    			}
    			if (!Ns && segrega) {
    				int pos = Integer.parseInt(splitSnp[1]);
    				
        			for (int i=0; i<indexesToUse.length; i++) {
        				if (splitSnp[indexesToUse[i]].charAt(0) == splitSnp[2].charAt(0)) {
        					out.print("0");
        				} else {
        					out.print("1");
        				}
        			}
        			out.print("\n");
        			
        			// Positions
					outFine.print(pos + " ");
					
					// ADM_params
    				String snpName = chr + "_" + pos;
    				outA.print(chr + "\t" + snpName + "\t" + "0" + "\t" + pos + "\n");
    				
    				snps = snps + 1;
    			}
			}
			out.close();
			outA.close();
			
			// Complete writing positions
			outFine.print("\n");
			for (int p = 0; p < snps; p++) {
				outFine.print("S");
			}
			outFine.print("\n");
			outFine.close();
			
			// Write the first lines of chromoP infile
			

			Writer writerC = new FileWriter(resultsFolder + "firstLines.txt");
			PrintWriter outC = new PrintWriter(writerC);
			outC.print(indexesToUse.length - 1 + "\n");
			outC.print(indexesToUse.length + "\n");
			outC.print(snps + "\n");
			outC.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

			
	public static void main(String[] args) {
		Build_ChromoPainter_shore_randomSamples build_ChromoPainter_shore_randomSamples = new Build_ChromoPainter_shore_randomSamples();
		build_ChromoPainter_shore_randomSamples.setSnpFile(args[0], args[1], args[2], args[3]); // args[0]
	}
}
