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
	
	public void setMatrixFile(String matrixName){
		
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




			System.out.println("src/c/e/data_processing/Pairwise_shore.java");			

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

int allPlants = 0;			
			int cviZindex = 0;
			int colIndex = 0;
			
			for (int i=2; i<splitIDs.length; i++) {
	    		
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
			}

                    System.out.println("We have a total of: " + allPlants + " plants");
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
	
			numIberianPlants =0;
			numCanPlants =0;
			numCviPlants = 0;
			numMorPlants = 0;
			numAllWorld = 0;
			numMadeiraPlants = 0;
			numAllButCanaryCvi = 0;
			allPlants = 0;
			
			for (int i=2; i<splitIDs.length; i++) {
	    		
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
			}
			
			// Linked to country. Now we have int Cvi-0, Col-0 index, and int[] for Iberian indexes.
			// From HaplotypeMonsterEater END
						
						

			
			
			
			
			
			
			// 
			// From Matrix_subSet.java
			//
			
			// Build IndexesToGet, array of interesting indexes 
			
			int howMany = allPlants;	// canarianIndexes.length + moroccanIndexes.length + iberianIndexes.length; // + capeVerdeanIndexes.length + madeiranIndexes.length 
System.out.println("howMany: "+ howMany);
			
			int[] IndexesToGet = new int[howMany];
			IndexesToGet = allIndexes;
			
			/*
			
			String[] IslandIndexes = new String[howMany];
			int count = 0;
		
			// Order samples by island
			String[] fineOrder = {
					"P7-", "P8-", "P9-", "P10-", 
					"P13-1", "P13-5", "P14-", "P15-",
					
					"T1-", "T2-", "T3-", "T4-", "T16-", 
					"T10-", "T12-", "T13-", "T15-", 
					"T101-", "T102", "T103", "T104", "T105", "T106", 
	
					"H1-", "H2-", "H3-", "H4-", "H5-", "H6-", "H7-", "H8-", "H9-", "H10-", "H11-", "H12-", "H13-", "H14-", "H15-", "H16-", "H17-", "H18-", "H19-", "H20-", "H21-", "H22-", "H23-", "H24-", "H25-", "H26-", "H27-", 
					
					"C1-", "C2-", "C3-", "C4-", "C5-", "C6-", "C7-", "C8-", "C9-", "C10-", "C11-", "C12-", "C13-", "C14-", "C15-", "C16-", "C17-", "C18-", "C19-", "C20-", "C21-", "C22-", "C23-", "C24-", "C25-", "C26-", "C27-", 
					
					"G1-", "G102-",  "G103-",  "G104-",  "G105-",  "G106-",  "G107-"					
			};
			
			File idsToPlantsFile = new File("/home/lv70590/Andrea/data/idsToPlants.txt");
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
				// if (!got) {
					// System.out.println("Problem at: " + fineOrder[o]);
				// }
			}

System.out.println("count: "+ count);

		/*
			for (int can=0; can<canarianIndexes.length; can++) {
                                IndexesToGet[count] = canarianIndexes[can];
                                IslandIndexes[count] = "CAN";
                                count = count + 1;
                        }

			for (int ca=0; ca<capeVerdeanIndexes.length; ca++) {
                                IndexesToGet[count] = capeVerdeanIndexes[ca];
                                IslandIndexes[count] = "CVI";
                                count = count + 1;
                        }
			for (int mad=0; mad< madeiranIndexes.length; mad++) {
                                IndexesToGet[count] = madeiranIndexes[mad];
                                IslandIndexes[count] = "ARE";
                                count = count + 1;
                        }
/*
			for (int mo=0; mo< moroccanIndexes.length; mo++) {
				IndexesToGet[count] = moroccanIndexes[mo];
				IslandIndexes[count] = "MOR";
				count = count + 1;
			}
System.out.println("count: "+ count);
			for (int ib=0; ib< iberianIndexes.length; ib++) {
				IslandIndexes[count] = "IBP";
				IndexesToGet[count] = iberianIndexes[ib];
				count = count + 1;
			}
System.out.println("count: "+ count);

			System.out.println("Got indexes");
		System.out.println(Arrays.toString(IndexesToGet));	
			//
			// IndexesToGet, array of interesting indexes 
			//
			
			







*/
			
			//
			// Now the real game
			// 

        	// Begin to calculate pairwise differences
        	
			int[][] differences = new int[IndexesToGet.length][IndexesToGet.length];
			int[][] length = new int[IndexesToGet.length][IndexesToGet.length];
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
        		int pos = Integer.parseInt(splitSnp[1]);
	        	//
        		
        		// if (!mask[chr-1].get(pos)) {

            		for (int acc1=0; acc1<IndexesToGet.length; acc1++) {
    	        		char base1 = splitSnp[IndexesToGet[acc1]].charAt(0);
    	        		
    		        	for (int acc2=0; acc2<IndexesToGet.length; acc2++) {
    		        		char base2 = splitSnp[IndexesToGet[acc2]].charAt(0);
    		        		
    		        		if ( (base1 != 'N') && (base2 != 'N') ) {
    		        			length[acc1][acc2] = length[acc1][acc2] + 1;
    	        				if (base1 != base2) {
    		        				differences[acc1][acc2] = differences[acc1][acc2] + 1;
    		        			}
    		        		}
    		        	}
    	        	}
        		//}
        		
	        }
			// Calculate pi
			
			double[][] pairwDiff = new double[IndexesToGet.length][IndexesToGet.length];
			for (int p=0; p<IndexesToGet.length; p++) {
				for (int q=0; q<IndexesToGet.length; q++) {
					pairwDiff[p][q] = (double)(differences[p][q])/(double)(length[p][q]);
				}
			}
			
			// Done
			
			
			
			

			// Write out
			
			Writer writer = new FileWriter(matrixName + "_pi_shore_caIbMo.txt");
			PrintWriter out = new PrintWriter(writer);
			
	       	// Header, island ids
		//	for (int sub=0; sub<IslandIndexes.length; sub++) {
		//		out.print(IslandIndexes[sub] + "\t");
		//	}
		//	out.print("\n");
	       	// Body
			for (int p=0; p<IndexesToGet.length; p++) {
				for (int q=0; q<IndexesToGet.length; q++) {
					out.print(pairwDiff[p][q]);
					out.print("\t");
				}
				out.print("\n");
			}
			out.close();
			

			// Write accession names

			Writer writerN = new FileWriter(matrixName + "_pi_shore_caIbMo_numIDs.txt");
			PrintWriter outN = new PrintWriter(writerN);
	       	for (int sub=0; sub<IndexesToGet.length; sub++) {
				outN.print(splitIDs[IndexesToGet[sub]] + "\n");
			}
	       	outN.print("\n");
			outN.close();
			
			// And island IDs
			
			Writer writerIsl = new FileWriter(matrixName + "_pi_shore_caIbMo_islIDs.txt");
			PrintWriter outIsl = new PrintWriter(writerIsl);
	       	for (int sub=0; sub<IslandIndexes.length; sub++) {
				outIsl.print(IslandIndexes[sub] + "\n");
			}
	       	outIsl.print("\n");
			outIsl.close();
			
			System.out.println("fatto! :) "); 
		

		// And letter IDs

		Writer writerL = new FileWriter(matrixName + "_pi_shore_caIbMo_letterIDs.txt");
               	PrintWriter outL = new PrintWriter(writerL);
                for (int sub=0; sub<IslandIndexes.length; sub++) {
                                outL.print(IslandIndexes[sub].charAt(0) + "\n");
                        }
                outL.print("\n");
                outL.close();





	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Pairwise_shore pairwise_shore = new Pairwise_shore();
		pairwise_shore.setMatrixFile("/home/lv70590/Andrea/analyses/haplotypeCallsCan/newBies02-16_matrix.txt_clean2.txt");
	}
}
