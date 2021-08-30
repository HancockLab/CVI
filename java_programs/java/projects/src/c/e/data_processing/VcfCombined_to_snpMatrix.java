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

public class VcfCombined_to_snpMatrix {
	
	public VcfCombined_to_snpMatrix() {}
	
	public void setFileToConvert(String filename, String chromosome, String outFile){
		
		try {
			// int posThresh = Integer.parseInt(posThreshStr);
			
			// Open combined vcf
			File vcf_comb = new File(filename);
			Scanner scannerVcf = new Scanner(vcf_comb);
			
			// Open also the writing file
			Writer writer = new FileWriter(outFile + "_c5q25_chr" + chromosome + ".txt");
			PrintWriter out = new PrintWriter(writer);
			
			int checkChr = 0;
			int checkPos=0;	
			int nSample =0;
			Boolean doublePos = false;
			char[] bases = null;
			int fillBases = 0;
			String[] splitIDs = null;
				
			while ( scannerVcf.hasNextLine() ) {
				String snp1 = scannerVcf.nextLine();
			       	String[] splitSnp = snp1.split("\t");
		       	
				// Mind the header now
				if (snp1.charAt(0) == '#') {
					if (snp1.charAt(1) != '#') {
						splitIDs = snp1.split("\t");
						
						// Count the samples
					       	nSample = splitSnp.length - 9;						// - 1: Oua out
					       	System.out.println("Nsamples: " + nSample);
				       		bases = new char[nSample + 1];
			                        for (int bb=0; bb<bases.length; bb++) {
                        			        bases[bb] = 'N';
			                        }
						
				       		// Print out header and samples
				       		if ( (chromosome.charAt(0) == '1' ) ) {
							out.print("chromosome" + "\t" + "position" + "\t" + "ref");
				       			for (int sample=9; sample<splitIDs.length; sample++) {
				       				//
								// To change if plink or normal vcf
								// 
								// Normal:
								//if (Integer.parseInt(splitIDs[sample]) != 22012) {		// Oua out
									out.print( "\t" + splitSnp[sample]);
								//}
								// Plink:
								// out.print( "\t" + splitSnp[9 + sample].split("_")[0]);
				       			}
		       					out.print("\n");
						}
					}
				}
				// Header is over
				
				// mind proper stuff now
				
				// If 1001 vcf:
				// if ( (snp1.charAt(0) != '#') && (splitSnp[0].charAt(0) == chromosome.charAt(0)) ) {
				// 
				// If our vcf:
				// 
				if (splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r' && splitSnp[0].charAt(3) == chromosome.charAt(0) ) { 
				
				// If slim vcf
				// if (splitSnp[0].charAt(0) == chromosome.charAt(0) ) {
				//
				//&& (Integer.parseInt(splitSnp[1]) >= 1000000*posThresh) && (Integer.parseInt(splitSnp[1]) < 1000000*(posThresh + 1)) ) {
					
						// Indels out - ref length == 1
						if (splitSnp[3].length() == 1) {
							// check also alt length == 1
							String[] check = splitSnp[4].split(",");
							boolean inDel=false;
							for (int c=0; c< check.length; c++) {
								if (check[c].length() > 1) {
									inDel = true;
								}
							}
							
							// Also chlorop and mytoc out
							if (!inDel) {
								doublePos = false;
								// Just check doubles problem
								if ( checkChr == Character.getNumericValue(splitSnp[0].charAt(3))) {						// 3 for our vcf
									if ( (checkPos == Integer.parseInt(splitSnp[1])) && (checkPos != 0) ) {
										//
										// Do something about it
										doublePos = true;
									} else {
										// Out if they are not all Ns
										// If it is the first position, bases[] is all Ns as well
										Boolean allN = true;
										for (int n=1; n<bases.length; n++) {
											if (bases[n] != 'N') {
												allN = false;
											}
										}
										if (!allN) {
											out.print(checkChr + "\t" + checkPos);
                                                                               		for (int bb=0; bb<bases.length; bb++) {
                                                                                        	out.print("\t" + bases[bb]);
                                                                            		}
                                                                                	out.print("\n");
										}
										bases = new char[nSample + 1];
										for (int bb=0; bb<bases.length; bb++) {
                        							        bases[bb] = 'N';
							                        }
									}
									checkPos = Integer.parseInt(splitSnp[1]);
								} else {
									checkChr = Character.getNumericValue(splitSnp[0].charAt(3));						//3 for our vcf
									checkPos = Integer.parseInt(splitSnp[1]);	
								}
							
								// For every line, print chromosome and position and Col-0
                    					        // out.print(splitSnp[0].charAt(3) + "\t" + splitSnp[1] + "\t" + splitSnp[3]);
						       		
								// Reference base as bases[0]
								
								bases[0] = splitSnp[3].charAt(0);
								
							       	// For every sample, print a base
							       	int run = 0;
							       	for (int sample=9; sample<splitIDs.length; sample++) {
					       				//if (Integer.parseInt(splitIDs[sample]) != 22012) {              // Oua out
					       					// Is there any call at all?
					    	 	  			//
					     		  			// For plink vcf
					     	  				// if ( (splitSnp[9 + sample].split("/")[0].charAt(0) != '.')) { 
					     	  				//
					     	  				// Normal vcf:
					    	 	  			if ( (splitSnp[sample].split(":")[0].charAt(0) != '.') && (splitSnp[sample].split(":")[1].charAt(0) != '.') && (splitSnp[sample].split(":")[2].charAt(0) != '.') ){
											// If - our Vcf
											// Check coverage and quality
							       				int cov=Integer.parseInt(splitSnp[sample].split(":")[2]);
						       					int qual=Integer.parseInt(splitSnp[sample].split(":")[1]);
						    		   			
						      			 		// Print out something reasonable
						      			 		// (Erase if plink)
						       					if ( (cov >=5) && (qual >= 30) ) {
								       				// Normal vcf:
								       				if (Integer.parseInt(splitSnp[sample].split(":")[0]) == 0) {
								       				// Plink vcf:
								       				// if (Character.getNumericValue(splitSnp[9 + sample].split("/")[0].charAt(0)) == 0) {
								       					bases[run + 1] = splitSnp[3].charAt(0);
						       							//out.print("\t" + splitSnp[3].charAt(0));
									       			} else {
									       				// Normal vcf:
									       				if ( (splitSnp[4].length() == 1) && (Integer.parseInt(splitSnp[sample].split(":")[0]) == 1) ) {
									       				//
									       				// Plink
									       				// if ( (splitSnp[4].length() == 1) && (Character.getNumericValue(splitSnp[9 + sample].split("/")[0].charAt(0)) == 1) ) {
	                                               		         					bases[run + 1] = splitSnp[4].charAt(0);
									       					//out.print("\t" + splitSnp[4].charAt(0));
									       				} else {
														// If normal vcf:
														bases[run + 1] = splitSnp[4].split(",")[Integer.parseInt(splitSnp[sample].split(":")[0]) - 1].charAt(0);
														// If plink:
														// bases[sample + 1] = splitSnp[4].split(",")[Character.getNumericValue(splitSnp[9 + sample].split("/")[0].charAt(0)) - 1].charAt(0);
									   					//out.print("\t" + splitSnp[4].split(",")[Integer.parseInt(splitSnp[9 + sample].split(":")[0]) - 1].charAt(0));
							       						}
							       					}
					       						// Erase if plink:
											} else {
                                                               					if (!doublePos) {
													bases[run + 1] = 'N';
													// out.print("\t" + "N");
												}
						     	  				}
				       						} else {
											if (!doublePos) {
				       								bases[run + 1] = 'N';
				       								//out.print("\t" + "N");
				       							}
										}
										run = run + 1;
									//}
								}
							       	// out.print("\n");
							  	// Next line!
							}
						}	
					}
       				}	
			// }
			// Put out the last position
			Boolean allN = true;
                        for (int n=1; n<bases.length; n++) {
                                if (bases[n] != 'N') {
                                	allN = false;
                        	}
                        }
			if (!allN) {
                                out.print(checkChr + "\t" + checkPos);
                                for (int bb=0; bb<bases.length; bb++) {
                                	out.print("\t" + bases[bb]);
                                }
                        	out.print("\n");
                        }
			out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		VcfCombined_to_snpMatrix vcfCombined_to_snpMatrix = new VcfCombined_to_snpMatrix();
		vcfCombined_to_snpMatrix.setFileToConvert(args[0], args[1], args[2]);
	}
}
