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

public class Haplo_callHapFire_conservative {
	
	private int whichCountry = 0;
	private Haplo_callHapFire_conservative() {}
	
	public void haplo_filter_overlaps_conservative(String hapPartialCall) {
		try {
			
			System.out.println("hapPartialCall: " + hapPartialCall);
			
			BitSet[][] haploFreqSpectr = new BitSet[17][5];
			for (int can=0; can<17; can++) {
				for (int c=0; c<5; c++) {
					haploFreqSpectr[can][c] = new BitSet();
				}
			}
			
			
			for (int can=0; can<17; can++) {
				System.out.println("Doing " + can);
				
				File partialCallFile = new File(hapPartialCall + can + ".txt");
				Scanner scannerpartialCall = new Scanner(partialCallFile);
				
				int[] haploCountry = new int[7];
				BitSet[][] hapMask = new BitSet[7][5];
	    		for (int c=0; c<7; c++) {
					for (int chr=0; chr<5; chr++) {
						hapMask[c][chr] = new BitSet();
					}
				}
	    		
	    		int singletonsCan =0;
	    		
				// Build haploMasks
				String name = scannerpartialCall.nextLine();
		    	scannerpartialCall.nextLine();
		    	while (scannerpartialCall.hasNextLine()) {
					String hap = scannerpartialCall.nextLine();
			    	String[] splithap = hap.split("\t");
			    	
		    		int chr = Integer.parseInt(splithap[0]);
		    		int startHap = Integer.parseInt(splithap[1]);
		    		int endHap = Integer.parseInt(splithap[2]);
		    		String country = splithap[4];
		    		
		    		if (country.equals("CANISL")) {
		    			whichCountry = 0;
		    			if (Integer.parseInt(splithap[6]) == 1) {
		    				singletonsCan = singletonsCan + 1;
		    			}
		    		} else {
		    			if (country.equals("CPV")) {
			    			whichCountry = 1;
			    		} else {
			    			if (country.equals("ARE")) {
				    			whichCountry = 2;
				    		} else {
				    			if (country.equals("ESP")) {
					    			whichCountry = 3;
					    		} else {
					    			if (country.equals("POR")) {
						    			whichCountry = 4;
						    		} else {
						    			if (country.equals("MOR")) {
							    			whichCountry = 5;
							    		} else {
							    			whichCountry = 6;
							    		}
						    		}
					    		}
				    		}
			    		}
		    		}
					for (int p = startHap; p < endHap; p++) {
		    			hapMask[whichCountry][chr-1].set(p);
					}
					haploCountry[whichCountry] = haploCountry[whichCountry] + 1;
				}
		    	
		    	
		    	Writer writerH = new FileWriter(hapPartialCall + "SNPProportions.txt"); 
				PrintWriter outH = new PrintWriter(writerH);
			

		    	
    			System.out.println(name);
    			outH.print(name);
    			System.out.println(singletonsCan);
    			outH.print(singletonsCan);
    			
		    	int[] totalPermissive = new int[7];		    	
	        	for (int co=0; co<7; co++) {
	        		totalPermissive[co] = 0;
	        		for (int chr=0; chr<5; chr++) {
	        			totalPermissive[co] = totalPermissive[co] + hapMask[co][chr].cardinality();
	        		}
	        	}
    			System.out.println( "Tot genome permissive: " + Arrays.toString(totalPermissive) );	
    			outH.print("Tot genome permissive: " + Arrays.toString(totalPermissive)  + "\n");	
    			System.out.println( "Tot haps: " + Arrays.toString(haploCountry) );				
    			outH.print( "Tot haps: " + Arrays.toString(haploCountry)  + "\n");	
    			
    			
    			
    			
    			
    			
    			int[] totalStitic = new int[7];	
				BitSet[] stitic = new BitSet[5];
				for (int chr=0; chr<5; chr++) {
					stitic[chr] = new BitSet();
				}
	        	for (int co=0; co<7; co++) {
	        		totalStitic[co] = 0;
	        		for (int chr=0; chr<5; chr++) {
	        			stitic[chr] = new BitSet();
	        			stitic[chr].or(hapMask[co][chr]);
	        			// System.out.println( "Tot genome stitic: " + stitic[chr].cardinality() );	        	
	        			
	    	        	for (int co2=0; co2<7; co2++) {
	    	        		if (co2 != co) {
	    	        			stitic[chr].andNot(hapMask[co2][chr]);
	    	        			// System.out.println( co2 + " " + stitic[chr].cardinality() );	        	
	    	        		}
	    	        	}
	        			totalStitic[co] = totalStitic[co] + stitic[chr].cardinality();
	        		}
	        	}
    			System.out.println( "Tot genome stitic: " + Arrays.toString(totalStitic) );
    			outH.print("Tot genome stitic: " + Arrays.toString(totalStitic) + "\n" );
    			
    			
    			// CAN, CVI, ARE, SPA, POR, MOR, all
    			
    			int[] totalMakeSense = new int[8];	
				BitSet[] notSoStitic = new BitSet[5];
				for (int chr=0; chr<5; chr++) {
					notSoStitic[chr] = new BitSet();
				}
				
				// Macaronesian guys
        		totalMakeSense[0] = 0;
	        	for (int chr=0; chr<5; chr++) {
	        		notSoStitic[chr] = new BitSet();
    	        	for (int co2=0; co2<3; co2++) {
    	        		// CAN, CVI, ARE
    	        		notSoStitic[chr].or(hapMask[co2][chr]);
    	        	}
    	        	for (int co2=3; co2<7; co2++) {
    	        		// SPA, POR, MOR, ALL
    	        		notSoStitic[chr].andNot(hapMask[co2][chr]);
    	        	}
    	        	totalMakeSense[0] = totalMakeSense[0] + notSoStitic[chr].cardinality();
    	        	
    	        	haploFreqSpectr[can][chr].or(notSoStitic[chr]);
	        	}
    			// System.out.println(  "totalMakeSense[0] " + totalMakeSense[0] );	        	
	        	
    			
    			
	        	// Spanish guys
        		totalMakeSense[1] = 0;
	        	for (int chr=0; chr<5; chr++) {
	        		notSoStitic[chr] = new BitSet();
   	        		notSoStitic[chr].or(hapMask[3][chr]);
    	        	for (int co2=0; co2<3; co2++) {
    	        		notSoStitic[chr].andNot(hapMask[co2][chr]);
    	        	}
	        		notSoStitic[chr].andNot(hapMask[4][chr]);
	        		notSoStitic[chr].andNot(hapMask[5][chr]);
    	        	totalMakeSense[1] = totalMakeSense[1] + notSoStitic[chr].cardinality();
	        	}
	        	
	        	// Portuguese guys
        		totalMakeSense[2] = 0;
	        	for (int chr=0; chr<5; chr++) {
	        		notSoStitic[chr] = new BitSet();
   	        		notSoStitic[chr].or(hapMask[4][chr]);
    	        	for (int co2=0; co2<4; co2++) {
    	        		notSoStitic[chr].andNot(hapMask[co2][chr]);
    	        	}
	        		notSoStitic[chr].andNot(hapMask[5][chr]);
    	        	totalMakeSense[2] = totalMakeSense[2] + notSoStitic[chr].cardinality();
	        	}
	        	
    			// CAN, CVI, ARE, SPA, POR, MOR, all
	        	
	        	// Moroccan guys
        		totalMakeSense[3] = 0;
	        	for (int chr=0; chr<5; chr++) {
	        		notSoStitic[chr] = new BitSet();
   	        		notSoStitic[chr].or(hapMask[5][chr]);
    	        	for (int co2=0; co2<5; co2++) {
    	        		notSoStitic[chr].andNot(hapMask[co2][chr]);
    	        	}
    	        	totalMakeSense[3] = totalMakeSense[3] + notSoStitic[chr].cardinality();
	        	}
	        	
	        	// Iberian guys
        		totalMakeSense[4] = 0;
	        	for (int chr=0; chr<5; chr++) {
	        		notSoStitic[chr] = new BitSet();
	        		for (int co2=3; co2<5; co2++) {
    	        		notSoStitic[chr].or(hapMask[co2][chr]);
    	        	}
    	        	for (int co2=0; co2<3; co2++) {
    	        		notSoStitic[chr].andNot(hapMask[co2][chr]);
    	        	}
	        		notSoStitic[chr].andNot(hapMask[5][chr]);
    	        	totalMakeSense[4] = totalMakeSense[4] + notSoStitic[chr].cardinality();
	        	}
	        	
	        	// world only guys
        		totalMakeSense[5] = 0;
	        	for (int chr=0; chr<5; chr++) {
	        		notSoStitic[chr] = new BitSet();
   	        		notSoStitic[chr].or(hapMask[6][chr]);
    	        	for (int co2=0; co2<6; co2++) {
    	        		notSoStitic[chr].andNot(hapMask[co2][chr]);
    	        	}
    	        	totalMakeSense[5] = totalMakeSense[5] + notSoStitic[chr].cardinality();
	        	}
		    	
	        	// Iberian moroccan guys
        		totalMakeSense[6] = 0;
	        	for (int chr=0; chr<5; chr++) {
	        		notSoStitic[chr] = new BitSet();
	        		for (int co2=3; co2<6; co2++) {
    	        		notSoStitic[chr].or(hapMask[co2][chr]);
    	        	}
    	        	for (int co2=0; co2<3; co2++) {
    	        		notSoStitic[chr].andNot(hapMask[co2][chr]);
    	        	}
    	        	totalMakeSense[6] = totalMakeSense[6] + notSoStitic[chr].cardinality();
	        	}
	        	
	        	// NON Macaronesian guys
        		totalMakeSense[7] = 0;
	        	for (int chr=0; chr<5; chr++) {
	        		notSoStitic[chr] = new BitSet();
    	        	for (int co2=3; co2<7; co2++) {
    	        		notSoStitic[chr].or(hapMask[co2][chr]);
    	        	}
    	        	for (int co2=0; co2<3; co2++) {
    	        		notSoStitic[chr].andNot(hapMask[co2][chr]);
    	        	}
    	        	totalMakeSense[7] = totalMakeSense[7] + notSoStitic[chr].cardinality();
	        	}
	        	
	        	
	        	System.out.println( "Tot genome make sense: " + Arrays.toString(totalMakeSense) );	        	
	        	outH.print("Tot genome make sense: " + Arrays.toString(totalMakeSense) + "\n");
	        	
	        	outH.close();

	        	
	        	
	        	
	        	
	        	
	        	
	        	
	        	// Fish out the bastards!!
	        	
	        	// Build haploMasks AGAINNN
	        	
				Writer writerC = new FileWriter(hapPartialCall + can + "bastardsCountry.txt"); 
				PrintWriter outC = new PrintWriter(writerC);
				
				Writer writerI = new FileWriter(hapPartialCall + can + "bastardsIDs.txt"); 
				PrintWriter outI = new PrintWriter(writerI);
				
	        	scannerpartialCall = new Scanner(partialCallFile);
	        	name = scannerpartialCall.nextLine();
	        	outC.print(name + "\n");
	        	outI.print(name + "\n");
				
		    	scannerpartialCall.nextLine();
		    	while (scannerpartialCall.hasNextLine()) {
					String hap = scannerpartialCall.nextLine();
			    	String[] splithap = hap.split("\t");
			    	
		    		int chr = Integer.parseInt(splithap[0]);
		    		int startHap = Integer.parseInt(splithap[1]);
		    		int endHap = Integer.parseInt(splithap[2]);
		    		int ID = Integer.parseInt(splithap[3]);
		    		String country = splithap[4];
		    		
		    		Boolean gotIt = false;
		    		for (int p = startHap; p < endHap; p++) {
		    			if (notSoStitic[chr-1].get(p)) {
		    				gotIt = true;
		    			}
					}
		    		if (gotIt) {
		    			outC.print(country + "\t");
		    			outI.print(ID + "\t");
	    				// System.out.println(country);
	    				// System.out.println(ID);
		    		}
				}
		    	outC.print("\n");
		    	outI.print("\n");
		    	outC.close();
		    	outI.close();
		    }
		    
		    
		    
		    
		    
		    
			
			
			
			// Call the haplo frequency spectrum
			
			Writer writer = new FileWriter(hapPartialCall + "haploFS.txt"); 
			PrintWriter out = new PrintWriter(writer);
			
			Writer writerP = new FileWriter(hapPartialCall + "haploFS_positions.txt"); 
			PrintWriter outP = new PrintWriter(writerP);
			
			for (int chr=0; chr<5; chr++) {
				// Find maximum length
				int max=0;
				for (int can=0; can<17; can++) {
					if (max < haploFreqSpectr[can][chr].length()) {
						max = haploFreqSpectr[can][chr].length();
					}
				}
				// Got max length
				for (int p=0; p<max; p++) {
					// Is there actually something anywhere?!? :)
					boolean interesting = false;
					for (int can=0; can<17; can++) {
						if (haploFreqSpectr[can][chr].get(p)) {
							interesting = true;
						}
					}
					if (interesting) {
						outP.print(chr + "\t" + p + "\t");
						outP.print("\n");
						
						for (int can=0; can<17; can++) {
							if (haploFreqSpectr[can][chr].get(p)) {
								out.print("1");
							} else {
								out.print("0");
							}
						}
						out.print("\n");
					}
				}
			}
			out.close();
			outP.close();
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Haplo_callHapFire_conservative haplo_callHapFire_conservative = new Haplo_callHapFire_conservative();
		// haplo_callHapFire.call_haplo_6sourcesFire("/Volumes/MicroTera/congmao_matrix/canHaploCallsFire_all_can0IDS.txt");
		// haplo_callHapFire.haplo_filter_overlaps("/Volumes/MicroTera/analyses/myCrazyHaplotypeStuff/hapCallsDecember/canHaploCallsFire_varWit_conservative_");
		haplo_callHapFire_conservative.haplo_filter_overlaps_conservative("/home/CIBIV/andreaf/canaries/rawData/analyses/haplotypeCallsCan/canHaploCallsFire_varWit_conservative_");
	}
}
