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

public class Build_ChromoPainter_shore_Iberia {
	
	private Build_ChromoPainter_shore_Iberia() {}

	public void setSnpFile(String matrixName, String results){
		
		try {
			
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
	    	
	    	File morClustFile = new File("/home/lv70590/Andrea/data/moroccan_samples_plusMadeirans.tsv");		// /home/lv70590/Andrea/data/moroccan_samples-Sheet1.tsv");
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
		// System.out.println("We have: " + groupUnique.length + " unique groups ");
		// System.out.println("Unique groups: " + Arrays.toString(groupUnique));
	    	// Unique groups: [western_europe, italy_balkan_caucasus, central_europe, asia, south_sweden, north_sweden, germany, spain, relict, High Atlas, North Middle Atlas, Riff, South Middle Atlas, Madeira]
		String[] groupMem = groupUnique;
	 	groupUnique = new String[groupUnique.length];
	 	int runn = 0;
	   	for (int cM=0; cM< 9; cM++) {
    			groupUnique[runn] = groupMem[cM];
    			runn = runn + 1;
		}
		groupUnique[runn] = groupMem[groupMem.length-2];
	 	runn = runn + 1;
	  	for (int cM=9; cM< 12; cM++) {
		    	groupUnique[runn] = groupMem[cM];
		    	runn = runn + 1;
	 	}
                groupUnique[runn] = groupMem[groupMem.length-1];
                runn = runn + 1;
	    	// Unique groups: [western_europe, italy_balkan_caucasus, central_europe, asia, south_sweden, north_sweden, germany, spain, relict, South Middle Atlas, High Atlas, North Middle Atlas, Riff]
	    	System.out.println("We have: " + groupUnique.length + " unique groups ");
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
	    	//	System.out.println(idsAdmixtureSorted[p].length);
	    	//}
	    	
	    	
		// Got ids sorted by admixture cluster
			
			
	    












		// Eliminate low pi - otherwise of course samples coalesce within the group ofr the whole genome
		File fileNamesToGo = new File("/home/lv70590/Andrea/data/lowPi_Weur-Ibp-rel.txt");
		Scanner scanNamesTG = new Scanner(fileNamesToGo);

		int samplesTG = 0;
		while (scanNamesTG.hasNextLine()) {
			String name = scanNamesTG.nextLine();
			samplesTG = samplesTG + 1;
		}
		int[] samplesToGo = new int[samplesTG];
			
		samplesTG = 0;
		scanNamesTG = new Scanner(fileNamesToGo);
		while (scanNamesTG.hasNextLine()) {
			String name = scanNamesTG.nextLine();
			String[] splitName = name.split("\t");
			samplesToGo[samplesTG] = Integer.parseInt(splitName[0]);
			samplesTG = samplesTG + 1;
		}
		System.out.println(Arrays.toString(samplesToGo));
		System.out.println("Eliminate: " + samplesToGo.length + " samples");



	
	    	
			


    	










		// Decide which samples to use
	
		int samp = 0;
		for (int gU=0; gU<groupUnique.length; gU++) {
			if (groupUnique[gU].equals("relict") || groupUnique[gU].equals("spain") || groupUnique[gU].equals("western_europe")) {
				 for (int s=0; s<idsAdmixtureSorted[gU].length; s++) {
					Boolean go = false;
                                        for (int el=0; el<samplesToGo.length; el++) {
                                                if (samplesToGo[el] == Integer.parseInt(splitIDs[idsAdmixtureSorted[gU][s]])) {
                                                        go = true;
						}
					}
					if (!go) {
                                                samp = samp + 1;
                                        }
                                }
			}
		}
		int[] indexesToUse = new int[samp];	
		System.out.println("We will use a total of " + samp + " samples");
		
		// Get full list of samples to use:
    		
		String resultsFolder = results;
		
		Writer writerD = new FileWriter(resultsFolder + "donor_list_infile.txt");
                PrintWriter outD = new PrintWriter(writerD);

		Writer writerN = new FileWriter(resultsFolder + "names.txt");
                PrintWriter outN = new PrintWriter(writerN);
		
		samp = 0;
                for (int gU=0; gU<groupUnique.length; gU++) {
                        if (groupUnique[gU].equals("relict") || groupUnique[gU].equals("spain") || groupUnique[gU].equals("western_europe")) {                                
				int sampPerClust = 0;
				for (int s=0; s<idsAdmixtureSorted[gU].length; s++) {
                                        Boolean go = false;
                                        for (int el=0; el<samplesToGo.length; el++) {
                                                if (samplesToGo[el] == Integer.parseInt(splitIDs[idsAdmixtureSorted[gU][s]])) {
                                                        go = true;
                                                }
                                        }
                                        if (!go) {
						indexesToUse[samp] = idsAdmixtureSorted[gU][s];
						outN.print(splitIDs[indexesToUse[samp]] + "\n");                                        
					        samp = samp + 1;
						sampPerClust = sampPerClust + 1;
                                        }
                                }
				outD.print(groupUnique[gU] + "\t" + sampPerClust + "\t" + (double)1/(double)3 + "\t" + 0.0001 +  "\n");
                        }
                }
                outD.close();
		outN.close();










			
			
			
			
			
			
		


		// Print out the groups you want

	    	Writer writerI = new FileWriter(resultsFolder + "wEurope_Iberia_relicts.txt");
	        PrintWriter outI = new PrintWriter(writerI);
            
	        for (int gU=0; gU<groupUnique.length; gU++) {
                    if (groupUnique[gU].equals("relict") || groupUnique[gU].equals("spain") || groupUnique[gU].equals("western_europe")) {
                            for (int s=0; s<idsAdmixtureSorted[gU].length; s++) {
                            	outI.print(splitIDs[idsAdmixtureSorted[gU][s]] + "\n");
                            }
                    }
            }
            outI.close();




	
			
			
			
			
			
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
			outC.print("0\n");
			outC.print(indexesToUse.length + "\n");
			outC.print(snps + "\n");
			outC.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

			
	public static void main(String[] args) {
		Build_ChromoPainter_shore_Iberia build_ChromoPainter_shore_Iberia = new Build_ChromoPainter_shore_Iberia();
		build_ChromoPainter_shore_Iberia.setSnpFile(args[0], args[1]);
	}
}
