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

public class Matrix_getJSFS {
	
	public Matrix_getJSFS() {}
	
	public void setFileToSubSet(String matrixName, String maskName){
		
		try {
			// From HaplotypeMonsterEater
			// File with 2 lines: tab separated int IDS for 1001 plus macaronesian plants, 
			// and in the second line, same order, country code for the same plant
			File idsCountryFile = new File("/home/fulgione/data/idsCountry_11-2017.txt");
			Scanner scannerIdsCountry = new Scanner(idsCountryFile);
			
			// How many plants in our sample?
			int plants = scannerIdsCountry.nextLine().split("\t").length;	
			
			String[] ids = new String[plants];
			String[] country = new String[plants];
			
			scannerIdsCountry = new Scanner(idsCountryFile);
			String idsPlants = scannerIdsCountry.nextLine();
	    	String[] splitIds = idsPlants.split("\t");
	    	for (int p=0; p< splitIds.length; p++) {
	    		ids[p] = splitIds[p];
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
	    			if (ids[co].equals(splitIDs[i])) {
	    				if ( ids[co].equals("6911") ) {
	    					cviZindex = i;
	    				}
		    			if ( ids[co].equals("6909") ) {
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
	    			if (ids[co].equals(splitIDs[i])) {
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
			
			// 27154, 27152 twins of other 2
			// int[] are = {27153, 22638, 22022, 22019, 22017, 12908, 12763, 12762, 12761};
			// 35609 eliminated for many Ns
			//int[] rel = {9517, 9578, 9590, 9826, 9847, 9854, 9880, 9883, 9887, 9949, 8337, 9569, 9585, 9599, 9827, 9851, 9862, 9881, 9885, 9888}; // , 8351, 9571, 9588, 9823, 9828, 9853, 9875, 9882, 9886, 9941};		// Really, W Eur
			// int[] rel = {9871, 9887, 9600, 9554, 9832, 9542, 9598, 9555, 9543, 9533, 9869, 9549, 9837, 9879, 9545};
						
			// int[] pop3 = {6931, 9610, 9125, 18696, 9629, 7183, 8424, 14312, 1890, 8354, 9133, 9640, 14313, 9639, 9134, 9745, 14318, 9612, 9611, 7323}; // really, asia

			// ha
			// String[] pop1 = {"35616", "35613", "35606", "35604", "35602", "35601", "35598", "35596", "35595", "35523", "22010", "22009", "22003", "18516", "18513", "18511"};
			// nma
			String[] pop1 = {"37472", "35624", "35620", "35612", "35609", "35603", "35593", "35522", "35521", "22011", "22005", "22001", "22000", "18509"};
			// sma
			String[] pop2 = {"37471", "37470", "37469", "37468", "37467", "35625", "35623", "35622", "35621", "35619", "35618", "35615", "35611", "35610", "35608", "35607", "35605", "35600", "35599", "35594", "35513", "22006", "22004", "22002", "18514", "18512", "18510"};
				// "35515", "35514", "27173", "27171", "27167", "27166", "27164", "27155", "22644", "22640", "22635", "22625", "22621", "22614", "22610", "21230", "21229", "21228", "21226", "21225", "16295", "16294", "13584", "13577", "13576", "13575", "13574", "13573", "13572", "13571", "13187", "13186", "13184", "13182", "13181", "13180", "12915", "12910", "12909", "12768", "12767"};
			// Fogo


			String[] bothPop = new String[pop1.length + pop2.length]; // + pop3.length];
			int fill = 0;
			for (int a=0; a<pop1.length; a++) {
				bothPop[fill] = pop1[a];
				fill = fill + 1;
			}
			for (int a=0; a<pop2.length; a++) {
                                bothPop[fill] = pop2[a];
                                fill = fill + 1;
                        }
			//for (int a=0; a<pop3.length; a++) {
                        //        areRel[fill] = pop3[a];
                        //        fill = fill + 1;
                        //}




			int[] nSample = new int[bothPop.length];
			for (int n=0; n<nSample.length; n++) {
				nSample[n] = 0;
			}






int howMany = bothPop.length;
			
            int[] IndexesToGet = new int[howMany];
			
            int count = 0;			
			for (int a=0; a<bothPop.length; a++) {
                    	for (int s=3; s<splitIDs.length; s++) {
                            if (splitIDs[s].equals(bothPop[a])) {
                            	IndexesToGet[count] = s;
				count = count + 1;
                            }
                    }
            }
			System.out.println("Got indexes");
			System.out.println("All ind: " + Arrays.toString(IndexesToGet));
			
			
			
			int[] ind1 = new int[pop1.length];
			int[] ind2 = new int[pop2.length];
			// int[] pop3Ind = new int[pop3.length];
			
        		int count1 = 0;			
        		int count2 = 0;
			// int countP3 = 0;
			
			for (int a=0; a<pop1.length; a++) {
                    		for (int s=3; s<splitIDs.length; s++) {
                        		if (splitIDs[s].equals(pop1[a])) {
                            			ind1[count1] = s;
						count1 = count1 + 1;
                	        	}
        	        	}
	         	}
			for (int r=0; r<pop2.length; r++) {
                		for (int s=3; s<splitIDs.length; s++) {
                			if (splitIDs[s].equals(pop2[r])) {
                				ind2[count2] = s;
						count2 = count2 + 1;
                    			}
                		}
			}
                        //for (int p3=0; p3<pop3.length; p3++) {
                        //        for (int s=3; s<splitIDs.length; s++) {
                        //                if ( Integer.parseInt(splitIDs[s]) == pop3[p3]) {
                        //                        pop3Ind[countP3] = s;
                        //                      countP3 = countP3 + 1;
                        //                }
                        //        }
                        //}
			

System.out.println("Pop1: " + Arrays.toString(ind1));
System.out.println("Pop2: " + Arrays.toString(ind2));





System.out.println("Got indexes");
			
			
			
			
			

			int[] sfs1 = new int[ind1.length + 1];
			int[] sfs2 = new int[ind2.length + 1];
			//int[] pop3Sfs = new int[pop3Ind.length + 1];
			int[][] jsfs = new int[ind1.length + 1][ind2.length + 1];
			
			int[] sfs1_mor = new int[ind1.length + 1];
			int[] sfs2_mor = new int[ind2.length + 1];
			
			int[][] jsfs_mor = new int[ind1.length + 1][ind2.length + 1];

			long basesNoN = 0;
			

			// Charge the mask
			BitSet[] mask = new BitSet[5];
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			/*File fileMask = new File(maskName);
			Scanner scannerMask = new Scanner(fileMask);
		        int chrCVI=0;
		        String lineMask=null;
		        for (int m=0; m<10; m++) {
			       	lineMask = scannerMask.nextLine();
			       	if (m%2 == 1) {
			        	for (int i=0; i<lineMask.length(); i++) {
			        		if (lineMask.charAt(i) == ('1')) {
			        			mask[chrCVI].set(i);
			        		}
			        	}
			        	chrCVI=chrCVI+1;
			       	}
			}
			*/


			// For whole genome:
			//
			for (int c=0; c<5; c++) {
				for (int p=0; p<30000000; p++) {
					mask[c].set(p);
				}
			}




		Writer writerM = new FileWriter(maskName + "_intergenicSnps.txt");
                PrintWriter outM = new PrintWriter(writerM);	
			
		Writer writerA = new FileWriter(maskName + "_intergenicAll.txt");
                PrintWriter outA = new PrintWriter(writerA);
			
		// Open the big matrix
	    	matrix = new File(matrixName);
	    	scannerMatrix = new Scanner(matrix);
			
	       	// Go with contents
	       	int snps = 0;
		int segr = 0;
		int inter = 0;
	       	scannerMatrix.nextLine();
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		  	     	String[] splitSnp = snp.split("\t");

		       		int chr = Integer.parseInt(splitSnp[0]) - 1;
                                int pos = Integer.parseInt(splitSnp[1]);
				char refBase = splitSnp[2].charAt(0);
				
				if (mask[chr].get(pos)) {
					inter = inter + 1;
					outA.print(chr + "\t" + pos + "\t");
					for (int a=0; a<ind1.length; a++) {
                                                outA.print(splitSnp[ind1[a]].charAt(0) + "\t");
                                        }

					for (int r=0; r<ind2.length; r++) {
                                                outA.print(splitSnp[ind2[r]].charAt(0) + "\t");
                                        }
					outA.print("\n");
				}
				
		       		// If there is any N, out
		       		boolean N = false;
				boolean mism = false;
				boolean match = false;
		       		for (int sub=0; sub<IndexesToGet.length; sub++) {
		       			if (splitSnp[IndexesToGet[sub]].charAt(0) == 'N') {
		       				N = true;
						nSample[sub] = nSample[sub] + 1;
		       			}
					if (splitSnp[IndexesToGet[sub]].charAt(0) != refBase && splitSnp[IndexesToGet[sub]].charAt(0) != 'N') {
						mism = true;
					}
					if (splitSnp[IndexesToGet[sub]].charAt(0) == refBase && splitSnp[IndexesToGet[sub]].charAt(0) != 'N') {
						match = true;
					}
		      	 	}
		       		if (mism && match && mask[chr].get(pos)) {
					segr = segr + 1;
					//System.out.println(inter + "\t" + segr + "\n");
				}
		       	if (!N) {
		       		// Get one base as reference
			       	// char refBase = splitSnp[2].charAt(0);

			       	// Select segregating sites
			       	boolean segrega = false;
			       	for (int sub=0; sub<IndexesToGet.length; sub++) {
			       		if (splitSnp[IndexesToGet[sub]].charAt(0) != refBase) {
			       			segrega = true;
			       		}
			       	}
			       	//int chr = Integer.parseInt(splitSnp[0]) - 1;
				//int pos = Integer.parseInt(splitSnp[1]);
			
				if (mask[chr].get(pos)) {
					basesNoN = basesNoN + 1;
				}	
			       	// If it is segregating, output
			       	if (segrega && mask[chr].get(pos)) {
			       		
					outM.print(chr + "\t" + pos + "\t");
					
					int der1 = 0;
				       	for (int a=0; a<ind1.length; a++) {
				       		outM.print(splitSnp[ind1[a]].charAt(0) + "\t");
						if (splitSnp[ind1[a]].charAt(0) != refBase) {
				       			der1 = der1 + 1;
				       		}
					}
				       	int der2 = 0;
				       	for (int r=0; r<ind2.length; r++) {
						outM.print(splitSnp[ind2[r]].charAt(0) + "\t");
				       		if (splitSnp[ind2[r]].charAt(0) != refBase) {
				       			der2 = der2 + 1;
				       		}
					}
					outM.print("\n");
				       	// int pop3Der = 0;
                                        //for (int p3=0; p3<pop3Ind.length; p3++) {
                                        //        if (splitSnp[pop3Ind[p3]].charAt(0) != refBase) {
                                        //                pop3Der = pop3Der + 1;
                                        //        }
                                        //}

					sfs1[der1] = sfs1[der1] + 1;
				       	sfs2[der2] = sfs2[der2] + 1;
					//pop3Sfs[pop3Der] = pop3Sfs[pop3Der] + 1;
				       	jsfs[der1][der2] = jsfs[der1][der2] + 1;
				       	
					// Check morocco for polarization
					Boolean inMor = false;
					for (int m=0; m<moroccanIndexes.length; m++) {
						if (splitSnp[moroccanIndexes[m]].charAt(0) != refBase) {
							inMor = true;
						}
					}
					if (!inMor) {
						sfs1_mor[der1] = sfs1_mor[der1] + 1;
						sfs2_mor[der2] = sfs2_mor[der2] + 1;
						jsfs_mor[der1][der2] = jsfs_mor[der1][der2] + 1;
					}
					snps = snps + 1;
			       	}	
		       	}
			}
			System.out.println("intergenic: " + inter);
			System.out.println("snps: " + snps);
			System.out.println("segr: " + segr);
                        System.out.println("basesNoN: " + basesNoN);
			System.out.println("bothPop: " + Arrays.toString(bothPop));
			System.out.println("nSample: " + Arrays.toString(nSample));

			outM.close();
			outA.close();

			// Open also the writing file
			// String whereWe = "/global/lv70590/Andrea/analyses/jsfs/";
			
                        Writer writerB = new FileWriter(maskName + "_numBases");
                        PrintWriter outB = new PrintWriter(writerB);
			outB.print(basesNoN);
			outB.close();

			Writer writer8 = new FileWriter(maskName + "_jsfs.txt");
			PrintWriter out8 = new PrintWriter(writer8);
			
			for (int r=0; r<jsfs.length; r++) {
				for (int c=0; c<jsfs[r].length; c++) {
					out8.print(jsfs[r][c] + "\t");
				}
				out8.print("\n");
			}
			out8.close();
			
			Writer writer82 = new FileWriter(maskName + "_jsfs_morPol.txt");
			PrintWriter out82 = new PrintWriter(writer82);
			
			for (int r=0; r<jsfs_mor.length; r++) {
				for (int c=0; c<jsfs_mor[r].length; c++) {
					out82.print(jsfs_mor[r][c] + "\t");
				}
				out82.print("\n");
			}
			out82.close();

			Writer writer2 = new FileWriter(maskName + "_sfs1.txt");
			PrintWriter out2 = new PrintWriter(writer2);
			for (int r=0; r<sfs1.length; r++) {
				out2.print(sfs1[r] + "\t");
			}
			out2.print("\n");
			out2.close();

			Writer writer3 = new FileWriter(maskName + "_sfs2.txt");
			PrintWriter out3 = new PrintWriter(writer3);
			for (int r=0; r<sfs2.length; r++) {
				out3.print(sfs2[r] + "\t");
			}
			out3.print("\n");
			out3.close();

			Writer writer2b = new FileWriter(maskName + "_sfs1_mor.txt");
			PrintWriter out2b = new PrintWriter(writer2b);
			for (int r=0; r<sfs1_mor.length; r++) {
				out2b.print(sfs1_mor[r] + "\t");
			}
			out2b.print("\n");
			out2b.close();

			Writer writer3b = new FileWriter(maskName + "_sfs2_mor.txt");
			PrintWriter out3b = new PrintWriter(writer3b);
			for (int r=0; r<sfs2_mor.length; r++) {
				out3b.print(sfs2_mor[r] + "\t");
			}
			out3b.print("\n");
			out3b.close();
			


			Writer writer = new FileWriter(maskName + "_pop12_dadiSpectrum2.txt");
			PrintWriter out = new PrintWriter(writer);
			
			out.println((ind1.length + 1) + " " + (ind2.length + 1) + " unfolded");
			
			for (int p1 =0; p1<ind1.length + 1; p1++) {
				for (int p2 =0; p2<ind2.length + 1; p2++) {
					//for (int p3 =0; p3<pop3Ind.length + 1; p3++) {
						out.print(jsfs[p1][p2] + " ");
					//}
				}
			}
			out.print("\n");
			
			for (int p1 =0; p1<ind1.length + 1; p1++) {
				for (int p2 =0; p2<ind2.length + 1; p2++) {
					//for (int p3 =0; p3<pop3Ind.length + 1; p3++) {
						out.print("0 ");
					//}
				}
			}
			out.print("\n");
			out.close();
			


	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix_getJSFS matrix_getJSFS = new Matrix_getJSFS();
		matrix_getJSFS.setFileToSubSet(args[0], args[1]);
	}
}
