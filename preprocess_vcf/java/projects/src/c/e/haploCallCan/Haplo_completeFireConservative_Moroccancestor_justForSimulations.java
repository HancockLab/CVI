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

public class Haplo_completeFireConservative_Moroccancestor_justForSimulations {
	
	public int cviZindex = 0;
	public int colIndex = 0;
	public int totalBp = 0;
	public int totalBpIBP = 0;
	public int mismCviZ = 0;
	public int mismIBP = 0;
	public char[] bases = null;
	public int[] spectrum = null;
	public int snpsGoodForDifferenciatingIbpCvi =0 ;
	public int snpsBadForDifferenciatingIbpCvi =0 ;
	public int snpsGoodIBP =0 ;
	public int snpsBadIBP =0 ;
	public boolean difference = false;
	public Boolean inCVI = false;
	public Boolean inIbp = false;
	public Boolean inMor = false;
	public Boolean inCol = false;
	public int pat = 0;
	public String countryHap = null;
	public boolean longer = false;
	public String country = null;
		
	private Haplo_completeFireConservative_Moroccancestor_justForSimulations() {}
	
	public void call_Haplo_completeFireConservative(String canarian) {
		try {
			
			String whereWe = "/home/CIBIV/andreaf/canaries/rawData/analyses/haplotypeCallsCan/";
			int whichCan = Integer.parseInt(canarian);
			
	    	File matrix = new File(whereWe + "hap_simulations.txt");
	    	Scanner scannerMatrix = new Scanner(matrix);
	    	
	    	String idsUnsplit = scannerMatrix.nextLine();
	    	String[] splitIDs = idsUnsplit.split("\t");
			
	    	
			
			
			int[] iberianIndexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
			int[] moroccanIndexes = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
			int[] canarianIndexes = {36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52};
			int[] allButCanCviIndexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
			
			
			
			// Start the new game
			
	    	//scannerMatrix = new Scanner(matrix);
	    	//scannerMatrix.nextLine();
	    	int rows=0;
	    	
	    	// Just count SNPs... And hope you will do it only once...
	    	//while (scannerMatrix.hasNextLine()) {
			//	scannerMatrix.nextLine();
		    //	rows = rows + 1;
	    	//}
			//System.out.println("rows: " + rows);
			
	    	// Counted! Just for speed, take the number here: How many?
			// int rows = 11530428;
			
			
			
			// Play hard, in the fire
			
	    	for (int can=whichCan; can<whichCan + 1; can++) {
	    		
		    	System.out.println("Focus on can: " + splitIDs[canarianIndexes[can]]);
		    	
				Writer writer = new FileWriter(whereWe + "haploCallsCan_simulating" + can + ".txt");
				
				
				PrintWriter out = new PrintWriter(writer);
				out.print("Focus on can: " + splitIDs[canarianIndexes[can]] + "\n"); // splitIDs[canarianIndexes[can]] + "\n");
				out.print("chromosome" + "\t" + "startPos" + "\t" + "endPos" + "\t" + "ID" + "\t" + "country" + "\t" +  "howManySnps" + "\t" +  "hapLength" + "\t" +  "variationWithinHap" + "\n");
				
				int[] memHap = new int[allButCanCviIndexes.length+1];
				int[] startHap = new int[allButCanCviIndexes.length+1];
				int[] endHap = new int[allButCanCviIndexes.length+1];
				int[] varWithin = new int[allButCanCviIndexes.length+1];
				
	    		for (int i=0; i<allButCanCviIndexes.length+1; i++) {
	    			memHap[i] = 0;
	    			startHap[i] = 0;
	    			endHap[i] = 0;
	    			varWithin[i] = 0;
	    		}
				BitSet maskEndtwo = new BitSet();
				BitSet secondLast = new BitSet();
								
				scannerMatrix = new Scanner(matrix);
		    	scannerMatrix.nextLine();
		    	rows = 0;
		    	System.out.println("Start the game!");
		    	
		    	int chrMemory = 0;
		    	while (scannerMatrix.hasNextLine()) {
			    	// System.out.println(rows);
			    	
					String snp = scannerMatrix.nextLine();
			    	String[] splitSnp = snp.split("\t");
			    	
		    		int chr = Integer.parseInt(splitSnp[0]);
		    		if (chr > chrMemory) {
		    			for (int i=0; i<allButCanCviIndexes.length+1; i++) {
	    					memHap[i] = 0;
	    					startHap[i] = 0;
	    					endHap[i] = 0;
	    					varWithin[i] = 0;
	    				}
						maskEndtwo = new BitSet();
						secondLast = new BitSet();
		    		}
		    		chrMemory = chr;

		    		// System.out.println(can);
		    		
	        		char baseCan = splitSnp[canarianIndexes[can]].charAt(0);
	        		
	        		
	        		
	        		
	        		// if ( (baseCan != 'N') ) {
		    			
	        			// Filter out whatever appears both in Iberia (not only low frequency) and Cvi-0, non informative 'matches'
		    			// Look in Morocco
						inMor = false;
						double howManyMor = 0.0;
						int totM =0;
				    	for (int mor=0; mor<moroccanIndexes.length; mor++) {
				    		if ( (splitSnp[moroccanIndexes[mor]].charAt(0) != 'N') ) {
				    			totM = totM + 1;
				    			if (splitSnp[moroccanIndexes[mor]].charAt(0) == baseCan) {
				    				howManyMor = howManyMor + 1;
				    			}
				    		}
				    	}
				    	if (howManyMor/(double)(totM) > 0.95) {			// 1
							inMor = true;
				    	}
				    				    		
				    	// Look at Iberian variation
						inIbp = false;
						double howMany = 0.0;
						int tot =0;
				    	for (int ibp=0; ibp<iberianIndexes.length; ibp++) {
				    		if ( (splitSnp[iberianIndexes[ibp]].charAt(0) != 'N') ) {
				    			tot = tot + 1;
				    			if (splitSnp[iberianIndexes[ibp]].charAt(0) == baseCan) {
				    				howMany = howMany + 1;
				    			}
				    		}
				    	}
				    	if (howMany/(double)(tot) > 0.75) {			// 1
							inIbp = true;
				    	}
				    	
				    	
	        			
				    	// Remove uninformative SNPs
	        			if (!(inIbp && inMor)) {
	        				
					    	// Do the haplotype game
					    	
					    	Boolean unique = true;
					    	// Boolean isThere = false;
							
					    	for (int b=0; b<allButCanCviIndexes.length; b++) {
					        		char baseBB = splitSnp[allButCanCviIndexes[b]].charAt(0);					        		
					        		// if (baseBB != 'N') {
					        			// isThere = true;
						        		if ( (baseBB == baseCan) && (baseCan != 'N') ) {
						        			unique = false;
						        			if (memHap[b] == 0) {
						        				startHap[b] = Integer.parseInt(splitSnp[1]);
						        				varWithin[b] = 0;
						        			}
					        				memHap[b] = memHap[b] + 1;
			        						maskEndtwo.clear(b);
			        						secondLast.clear(b);
					        				endHap[b] = Integer.parseInt(splitSnp[1]);
					        			} else {
					        				// if (!unique) {
					        					if ( maskEndtwo.get(b) ) {
					        						if ((memHap[b] >= 2)) {
					        					    	
					        					    	
	        						 					if (splitIDs[allButCanCviIndexes[b]].charAt(0) == 'i') {
	        						 						country = "ESP";
	        						 					}
	        						 					
	        						 					if (splitIDs[allButCanCviIndexes[b]].charAt(0) == 'm') {
	        						 						country = "MOR";
	        						 					}
	        						 					
					        				    		if (secondLast.get(b)) {
					        				    			varWithin[b] = varWithin[b] - 1;
					        				    		}
					        				    		out.print(chr + "\t" + startHap[b] + "\t" + endHap[b] + "\t" + splitIDs[allButCanCviIndexes[b]] + "\t" + country + "\t" +  memHap[b] + "\t" +  (endHap[b] + 1 - startHap[b]) + "\t" + varWithin[b] + "\n");
					        				    		
					        				    		// End of this haplotype
					        						}
					        						memHap[b] = 0;
							        				secondLast.clear(b);
							        				maskEndtwo.clear(b);
							        				varWithin[b] = 0;
					        					} else {
					        						maskEndtwo.set(b);
					        						if (baseBB != 'N') {
					        							varWithin[b] = varWithin[b] + 1;
					        							secondLast.set(b);
					        						}
					        					}
					        				// }
					        			}
					        		// }
						    	}
				        		if (unique && (baseCan != 'N') ) {
				        			if (memHap[memHap.length-1] == 0) {
				        				startHap[memHap.length-1] = Integer.parseInt(splitSnp[1]);
				        				varWithin[varWithin.length-1] = 0;
				        			}
				        			memHap[memHap.length-1] = memHap[memHap.length-1] + 1;
	        						maskEndtwo.clear(1000000);
			        				endHap[memHap.length-1] = Integer.parseInt(splitSnp[1]);
				        		} else {
	        						if (memHap[memHap.length-1] > 0) {
	        							if (!maskEndtwo.get(1000000)) {
	        						 		maskEndtwo.set(1000000);
	        						 	} else {
	        						 		if (memHap[memHap.length-1] > 1) { 
	        						 			
        					    				out.print(chr + "\t" + startHap[memHap.length-1] + "\t" + endHap[memHap.length-1] + "\t" + 0 + "\t" + "CANISL" + "\t" +  memHap[memHap.length-1] + "\t" +  (endHap[memHap.length-1] + 1 - startHap[memHap.length-1]) + "\t" + (varWithin[varWithin.length-1]) + "\n");
	        					    		}
        					    			memHap[memHap.length-1] = 0;
				    	    				maskEndtwo.clear(1000000);
				        					startHap[memHap.length-1] = 0;
				        					endHap[memHap.length-1] = 0;
			        						varWithin[varWithin.length-1] = 0;
	        						 	}
			        				}
        						}
        						// Funny??	
						}
					// inside here, if you jump "N"s in The canarian guy
		    		// }
			    	rows = rows + 1;
			    	// In each row (SNP in 1001)

		    	}
		    	out.close();
	    	}
	    	
	    	
	    	
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Haplo_completeFireConservative_Moroccancestor_justForSimulations haplo_completeFireConservative_Moroccancestor_justForSimulations = new Haplo_completeFireConservative_Moroccancestor_justForSimulations();
		haplo_completeFireConservative_Moroccancestor_justForSimulations.call_Haplo_completeFireConservative(args[0]);
	}
}

