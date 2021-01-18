package c.e.haploCallCan;

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

public class SnpEff_on_polarizedMatrix {
	
	private SnpEff_on_polarizedMatrix() {}

	public void setSnpFile(String filename){
		
		
		try {
			
			int[] iberianIndexes = null;
			int[] canarianIndexes = null;
			int[] capeVerdeanIndexes = null;
			int[] moroccanIndexes = null;
			int[] madeiranIndexes = null;
			int[] restOfWorldIndexes = null;
			int[] allButCanCviIndexes = null;
			
			String[] splitIDs = null;
			
			int chrMem = 10;
			
			
			
			
			// Get annotation from SNPeff file
			
			BitSet[] intergenic = new BitSet[5];
			BitSet[] nonSynonimous = new BitSet[5];
			BitSet[] synonimous = new BitSet[5];
			
			for (int c=0; c<5; c++) {
				intergenic[c] = new BitSet();
				nonSynonimous[c] = new BitSet();
				synonimous[c] = new BitSet();
			}
			
                        String wereWe = filename;
	
			File folder = new File(wereWe);
			File[] listOfFiles = folder.listFiles();
			
			for (int f=0; f<listOfFiles.length; f++) {
				
				Scanner scanner = new Scanner(listOfFiles[f]);
				
				while ( scanner.hasNextLine() ) {
					String snpAnn = scanner.nextLine();
	        		String[] splitSnpAnn = snpAnn.split("\t");
	        		
					// Do something to the header!
		        	if (snpAnn.charAt(0) == '#') {
			        	if (snpAnn.charAt(1) != '#') {
			        		

			    			
			    			
			    			// 1 get indices right
			    			
			    			
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
			    			
			    			
			    			
			    	    	
			    	    	// check the header!
			    	    	
			    	    	String idsUnsplit = snpAnn;
			    	    	splitIDs = idsUnsplit.split("\t");
			    			
			    	    	int numIberianPlants =0;
			    			int numCanPlants = 0;
			    			int numCviPlants = 0;
			    			int numMorPlants = 0;
			    			int numAllWorld = 0;
			    			int numMadeiraPlants = 0;
			    			int numAllButCanaryCvi = 0;
			    			
			    			int cviZindex = 0;
			    			int colIndex = 0;
			    			
			    			for (int i=9; i<splitIDs.length; i++) {
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
			    			iberianIndexes = new int[numIberianPlants];
			    			canarianIndexes = new int[numCanPlants];
			    			capeVerdeanIndexes = new int[numCviPlants];
			    			moroccanIndexes = new int[numMorPlants];
			    			madeiranIndexes = new int[numMadeiraPlants];
			    			restOfWorldIndexes = new int[numAllWorld];
			    			allButCanCviIndexes = new int[numAllButCanaryCvi];
			    			
			    			numIberianPlants =0;
			    			numCanPlants =0;
			    			numCviPlants = 0;
			    			numMorPlants = 0;
			    			numAllWorld = 0;
			    			numMadeiraPlants = 0;
			    			numAllButCanaryCvi = 0;
			    			
			    			for (int i=9; i<splitIDs.length; i++) {
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
			    			//for (int can=0; can<canarianIndexes.length; can++) {
							//	System.out.print(splitIDs[canarianIndexes[can]] + "\t");
							//}
			    			
			    			// Linked to country. Now we have int Cvi-0, Col-0 index, and int[] for Iberian indexes.
			    			// From HaplotypeMonsterEater END
			    		}
		        	}
					
					
					
					
					
					
					
					
					
					
					
					
					
		        	if (snpAnn.charAt(0) != '#') {
		        		
		        		if ( (splitSnpAnn[0].charAt(0) == 'C') && (splitSnpAnn[0].charAt(1) == 'h') && (splitSnpAnn[0].charAt(2) == 'r') ) {

							// Just print where we are

					    	int chr = Character.getNumericValue(splitSnpAnn[0].charAt(3)) - 1;
					        int pos = Integer.parseInt(splitSnpAnn[1]);
					        
					        if (chrMem != chr) {
			        			if ( (chrMem == 0) || (chrMem == 1) || (chrMem == 2) || (chrMem == 3) || (chrMem == 4) ) {
			        				System.out.println("Intergenic Cardinality: " + intergenic[chrMem].cardinality());
			        			}
			        			System.out.println("Chr: " + chr);
								chrMem = chr;
			        		}
					        
					        
					        // Get a char[] of bases that appear in canaries
					        
							char[] canBases = {'0'};
							Boolean allN = true;
							
							for (int can=0; can<canarianIndexes.length; can++) {
								char canBase = 'N';
								
					       		// Is there any call at all?
					       		if (splitSnpAnn[canarianIndexes[can]].split(":")[0].charAt(0) != '.') {
					       			// System.out.println(splitSnpAnn[canarianIndexes[can]]);
					       			
					       			// Check that cov and qual are there
					       			if ( (splitSnpAnn[canarianIndexes[can]].split(":")[1].charAt(0) != '.') && (splitSnpAnn[canarianIndexes[can]].split(":")[2].charAt(0) != '.') ) {

							       		// Check coverage and quality
							       		int cov=Integer.parseInt(splitSnpAnn[canarianIndexes[can]].split(":")[2]);
							       		int qual=Integer.parseInt(splitSnpAnn[canarianIndexes[can]].split(":")[1]);
							       		
							       		// Retain something reasonable
							       		if ( (cov >=3) && (qual >= 25) ) {
								       		if (Integer.parseInt(splitSnpAnn[canarianIndexes[can]].split(":")[0]) == 0) {
								       			canBase = splitSnpAnn[3].charAt(0);
								       		} else {
								       			if (splitSnpAnn[4].length() == 1) {
									       			canBase = splitSnpAnn[4].charAt(0);
								       			} else {
								       				canBase = splitSnpAnn[4].split(",")[Integer.parseInt(splitSnpAnn[canarianIndexes[can]].split(":")[0]) - 1].charAt(0);
								       			}
								       		}
							       		}
					       			}
					       		}
			        			// System.out.println("char: " + canBase);
								
								if (canBase != 'N') {
									allN = false;
									
					    			// Check if it is in the collection
					    			Boolean thereIs = false;
									for (int chars=0; chars<canBases.length; chars++) {
										if (canBase == canBases[chars]) {
											thereIs = true;
										}
									}
									if (!thereIs) {
										char[] memory = canBases;
										canBases = new char[canBases.length +1];
										for (int c=0; c<memory.length; c++) {
											canBases[c] = memory[c];
										}
										canBases[canBases.length-1] = canBase;
									}
								}
							}
							
							
							
							if (!allN) {

								// Eliminate the first base, it was just for convenience
								
								char[] memory = canBases;
								canBases = new char[canBases.length - 1];
								for (int c=1; c<memory.length; c++) {
									canBases[c - 1] = memory[c];
								}
			        			// System.out.println("char[]: " + Arrays.toString(canBases));
								
						        // Got char[] of canarian bases
						        
						        
			        			// Procede the game
						        String ann = splitSnpAnn[7];
						        
						        
						        String[] annSplitSemicolon = ann.split(";");
				        		for (int semic=0; semic<annSplitSemicolon.length; semic++) {
				        			
				        			if ( (annSplitSemicolon[semic].charAt(0) == 'A' && annSplitSemicolon[semic].charAt(1) == 'N' && annSplitSemicolon[semic].charAt(2) == 'N') ) {
				        				String[] annSplitEffects =  annSplitSemicolon[semic].split("=")[1].split(",");
				        				
						        		for (int eff=0; eff<annSplitEffects.length; eff++) {
						        			String[] annPipeSplit = annSplitEffects[eff].split("\\|");
								        	
								        	// Is this base in canaries?
								        	Boolean inCan = false;
											for (int base=0; base<canBases.length; base++) {
								        		if (annPipeSplit[0].charAt(0) == canBases[base]) {
								        			inCan = true;
								        		}
								        	}
								        	//
								        	
								        	if ( (annPipeSplit[0].length() == 1) && inCan) {
								        		// System.out.println(annPipeSplit[0]);
								        		// System.out.println(annPipeSplit[0]);
								        		
								        		// System.out.println(annPipeSplit[1]);
								        		String[] annAndSplit = annPipeSplit[1].split("&");
								        		// System.out.println(Arrays.toString(annAndSplit));
								        		
									        	for (int a=0; a<annAndSplit.length; a++) {
										        	// System.out.println(annAndSplit[a]);
										        	
									        		if (annAndSplit[a].equals("synonymous_variant") || annAndSplit[a].equals("start_retained_variant") || annAndSplit[a].equals("stop_retained_variant")) {
										        		synonimous[chr].set(pos);
										        	} else {
										        		if (annAndSplit[a].equals("nonsynonymous_variant") || annAndSplit[a].equals("protein_altering_variant") || annAndSplit[a].equals("missense_variant") || annAndSplit[a].equals("conservative_missense_variant") || annAndSplit[a].equals("non_conservative_missense_variant") || annAndSplit[a].equals("start_lost") || annAndSplit[a].equals("stop_gained") || annAndSplit[a].equals("stop_lost")) {
										        			nonSynonimous[chr].set(pos);
										        		} else {
										        			if (annAndSplit[a].equals("intergenic_region") || annAndSplit[a].equals("downstream_gene_variant") || annAndSplit[a].equals("5KB_downstream_variant") || annAndSplit[a].equals("500B_downstream_variant") || annAndSplit[a].equals("downstream_transcript_variant") || annAndSplit[a].equals("upstream_gene_variant") || annAndSplit[a].equals("5KB_upstream_variant") || annAndSplit[a].equals("2KB_upstream_variant") || annAndSplit[a].equals("upstream_transcript_variant") || annAndSplit[a].equals("intron_variant")) {
										        				intergenic[chr].set(pos);
										        			} else {
										        				String[] splitCheck = annAndSplit[a].split("_");
										        				if (annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("5_prime_UTR_variant") || annAndSplit[a].equals("3_prime_UTR_variant") || annAndSplit[a].equals("5_prime_UTR_premature_start_codon_gain_variant") || splitCheck[0].equals("splice") || annAndSplit[a].equals("initiator_codon_variant")) { // || annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("non_coding_exon_variant") || annAndSplit[a].equals("non_coding_exon_variant") || ) {
										        				} else{
										        					// System.out.println(annAndSplit[a]);
										        				}
										        			}
										        		}
										        	}
									        	}
								        	}
							        	}
						        	}
				        		}
							}
		        		}
		        	}
				}
				int syn = 0;
				int nonSyn = 0;
				int interg = 0;
				for (int c=0;c<5; c++) {
					syn = syn + synonimous[c].cardinality();
					nonSyn = nonSyn + nonSynonimous[c].cardinality();
					interg = interg + intergenic[c].cardinality();
				}
				System.out.println("s, n, i: " + syn + " " + nonSyn + " " + interg);
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			// Write out the 3 masks
			
			String wereWeResult = "/home/lv70590/Andrea/analyses/snpEff/";
			
			// Intergenic
			Writer writer = new FileWriter(wereWeResult + "intergenicMask.txt");
			PrintWriter out = new PrintWriter(writer);
			for (int c=0; c<5; c++) {
				out.print(">Chr" + (c+1));
				out.print("\n");
				for (int bit =0; bit< intergenic[c].length(); bit++) {
					if (intergenic[c].get(bit)) {
						out.print("1");
					}
					if (!intergenic[c].get(bit)) {
						out.print("0");
					}
				}
				out.print("\n");
			}
			out.close();
			
			// Sysnonimous
			Writer writerS = new FileWriter(wereWeResult + "synonimousMask.txt");
			PrintWriter outS = new PrintWriter(writerS);
			for (int c=0; c<5; c++) {
				outS.print(">Chr" + (c+1));
				outS.print("\n");
				for (int bit =0; bit< synonimous[c].length(); bit++) {
					if (synonimous[c].get(bit)) {
						outS.print("1");
					}
					if (!synonimous[c].get(bit)) {
						outS.print("0");
					}
				}
				outS.print("\n");
			}
			outS.close();
			
			
			// Non Sysnonimous
			Writer writerN = new FileWriter(wereWeResult + "nonSynonimousMask.txt");
			PrintWriter outN = new PrintWriter(writerN);
			for (int c=0; c<5; c++) {
				outN.print(">Chr" + (c+1));
				outN.print("\n");
				for (int bit =0; bit< nonSynonimous[c].length(); bit++) {
					if (nonSynonimous[c].get(bit)) {
						outN.print("1");
					}
					if (!nonSynonimous[c].get(bit)) {
						outN.print("0");
					}
				}
				outN.print("\n");
			}
			outN.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		SnpEff_on_polarizedMatrix snpEff_on_polarizedMatrix = new SnpEff_on_polarizedMatrix();
		snpEff_on_polarizedMatrix.setSnpFile(args[0]);
	}
}
