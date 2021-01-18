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

public class VcfCombined_to_snpMatrix_generalDiploid {
	
	public VcfCombined_to_snpMatrix_generalDiploid() {}
	
	public void setFileToConvert(String filename, String outFile){
		
		try {
			// int posThresh = Integer.parseInt(posThreshStr);
			
			// Open combined vcf
			File vcf_comb = new File(filename);
			Scanner scannerVcf = new Scanner(vcf_comb);
			
			// Open also the writing file
			Writer writer = new FileWriter(outFile + "_matrix_c3q15.txt");
			PrintWriter out = new PrintWriter(writer);
			
			String checkChr = "scaf_01_1";
			int checkPos=0;	
			int nSample =0;
			Boolean doublePos = false;
			char[][] bases = new char[2][];
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
				       		for (int b=0; b<2; b++) {
							bases[b] = new char[nSample + 1];
							 for (int bb=0; bb<bases[b].length; bb++) {
                        			        	bases[b][bb] = 'N';
			                        	}
						}
						
				       		// Print out header and samples
				       		// if ( (chromosome.charAt(0) == '1' ) ) {
							out.print("chromosome" + "\t" + "position" + "\t" + "ref");
				       			for (int sample=9; sample<splitIDs.length; sample++) {
				       				//
								// To change if plink or normal vcf
								// 
								// Normal:
								for (int dpl=0; dpl<2; dpl++) {
									out.print( "\t" + splitSnp[sample] + "_" + dpl);
								}
								//}
								// Plink:
								// out.print( "\t" + splitSnp[9 + sample].split("_")[0]);
				       			}
		       					out.print("\n");
						// }
					}
				} else {
				// Header is over
				
				// mind proper stuff now
				
				// If 1001 vcf:
				// if ( (snp1.charAt(0) != '#') && (splitSnp[0].charAt(0) == chromosome.charAt(0)) ) {
				// 
				// If our vcf:
				// 
				// if (splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r' && splitSnp[0].charAt(3) == chromosome.charAt(0) ) { 
				
				// If slim vcf
				// if (splitSnp[0].charAt(0) == chromosome.charAt(0) ) {
				//
				//&& (Integer.parseInt(splitSnp[1]) >= 1000000*posThresh) && (Integer.parseInt(splitSnp[1]) < 1000000*(posThresh + 1)) ) {
				//
				// If general vcf, for example RAD seq on birds
				// if () {
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
							if (checkChr.equals("24") && (checkPos == 39 ||  checkPos == 40 || checkPos == 41) ) {
								System.out.print(inDel + "\t" + Integer.parseInt(splitSnp[1]) + "\tns And\t");
								for (int b=0; b<bases.length; b++) {
									for (int n=1; n<bases[b].length; n++) {
										if (bases[b][n] != 'N') {
											System.out.print(bases[b][n]+ "\t");
										}
									}
								}
								System.out.print("\n");
							}
							// Also chlorop and mytoc out
							if (!inDel) {
								doublePos = false;
								// Just check doubles problem
								if ( checkChr.equals(splitSnp[0])) {						// 3 for our vcf
									if ( (checkPos == Integer.parseInt(splitSnp[1])) && (checkPos != 0) ) {
										//
										// Do something about it
										doublePos = true;
									} else {
										// Out if they are not all Ns
										// If it is the first position, bases[] is all Ns as well
										Boolean allN = true;
										for (int b=0; b<bases.length; b++) {
											for (int n=1; n<bases[b].length; n++) {
												if (bases[b][n] != 'N') {
													allN = false;
												}
											}
										}
										if (!allN) {
											out.print(checkChr + "\t" + checkPos);
                                                                               		for (int bb=0; bb<bases[0].length; bb++) {
                                                                                        	for (int b=0; b<bases.length; b++) {
													out.print("\t" + bases[b][bb]);
												}
                                                                            		}
                                                                                	out.print("\n");
										}
										for (int b=0; b<bases.length; b++) {
											bases[b] = new char[nSample + 1];
											for (int bb=0; bb<bases[b].length; bb++) {
                        							        	bases[b][bb] = 'N';
											}
							                        }
									}
									checkPos = Integer.parseInt(splitSnp[1]);
								} else {
									checkChr = splitSnp[0];						//3 for our vcf
									checkPos = Integer.parseInt(splitSnp[1]);	
								}
							
								// For every line, print chromosome and position and Col-0
                    					        // out.print(splitSnp[0].charAt(3) + "\t" + splitSnp[1] + "\t" + splitSnp[3]);
						       		
								// Reference base as bases[0]
								
								bases[0][0] = splitSnp[3].charAt(0);
								bases[1][0] = splitSnp[3].charAt(0);

							       	// For every sample, print a base
							       	int run = 0;
								//
								if (splitSnp[0].equals("24") && (splitSnp[1].equals("40") || splitSnp[1].equals("41")) ) {
									System.out.print("Actual " + splitSnp[0] + "\t" + splitSnp[1] + "\t");
								}

							       	for (int sample=9; sample<splitIDs.length; sample++) {
									
					       				//if (Integer.parseInt(splitIDs[sample]) != 22012) {              // Oua out
					       					// Is there any call at all?
					    	 	  			//
					     		  			// For plink vcf
					     	  				// if ( (splitSnp[9 + sample].split("/")[0].charAt(0) != '.')) { 
					     	  				//
										// Treat separately matches to the ref
										// if () {
										//
										//
					     	  				// Normal vcf:
										boolean empty = false;
										if (!splitSnp[8].equals("DP")) {
											String[] geno = splitSnp[sample].split(":");
											if (geno[0].charAt(0) == '.' || geno[1].charAt(0) == '.' || geno[2].charAt(0) == '.') {
												empty = true;
											}
										}
									if (!empty) {
											// If - our Vcf
											// Check coverage and quality
							       				// 
											// For Certhia brachidactila
											// int qual=Integer.parseInt(splitSnp[sample].split(":")[3]);
											// int cov=Integer.parseInt(splitSnp[sample].split(":")[1]);
						       					// 
											// For Pogoniulus
											// System.out.println(snp1);
											// int cov=Integer.parseInt(splitSnp[sample].split(":")[3]);
											// int qual=Integer.parseInt(splitSnp[sample].split(":")[2]);
						    		   			// 
											// Pogoniulus, my stacks calls
											//
											// Deal first with reference calls
											//
										if (splitSnp[8].equals("DP")) {
											if (splitSnp[sample].matches("^-?\\d+$")) {
												int cov=Integer.parseInt(splitSnp[sample]);
												if (cov >= 5) {
													for (int b=0; b<bases.length; b++) {
														bases[b][run + 1] = splitSnp[3].charAt(0);
													}
												} else {
													for (int b=0; b<bases.length; b++) {
														 bases[b][run + 1] = 'N';
													}
												}
											} else {
												for (int b=0; b<bases.length; b++) {
													bases[b][run + 1] = 'N';
												}
											}
										} else {
											//	FulgiPype:
											int cov=Integer.parseInt(splitSnp[sample].split(":")[2]);
											int qual=Integer.parseInt(splitSnp[sample].split(":")[1]);
											//
											//	Stacks:
											// int cov=Integer.parseInt(splitSnp[sample].split(":")[4]);
											// int qual=Integer.parseInt(splitSnp[sample].split(":")[3]);
						    		   			//
											if (splitSnp[0].equals("24") && (splitSnp[1].equals("40") || splitSnp[1].equals("41")) ) {
												System.out.println(cov + "\t" + qual + "\t");
											}
											// Pogoniulus my calls: quality and depth already filtered
											// int cov=10;
											// int qual=40;

						      			 		// Print out something reasonable
						       					if ( (cov >= 5) && (qual >= 25) ) {
								       				if (splitSnp[0].equals("24") && (splitSnp[1].equals("40") || splitSnp[1].equals("41")) ) {
													 System.out.println("pass\n");
												}
												if (splitSnp[sample].split(":")[0].equals("0|0") || splitSnp[sample].split(":")[0].equals("0/0")) {
								       					for (int b=0; b<bases.length; b++) {
														bases[b][run + 1] = splitSnp[3].charAt(0);
													}
									       			} else {
													// if (splitSnp[sample].split(":")[0].equals("1")) {
													if ( splitSnp[sample].split(":")[0].equals("1|0") || splitSnp[sample].split(":")[0].equals("0|1") || splitSnp[sample].split(":")[0].equals("1/0") || splitSnp[sample].split(":")[0].equals("0/1") ) {
														bases[0][run + 1] = splitSnp[3].charAt(0);
														bases[1][run + 1] = splitSnp[4].charAt(0);
													} else {
														// if (splitSnp[sample].split(":")[0].equals("2")) {
														if (splitSnp[sample].split(":")[0].equals("1/1") || splitSnp[sample].split(":")[0].equals("1|1") ) {
															for (int b=0; b<bases.length; b++) {
																bases[b][run + 1] = splitSnp[4].charAt(0);
															}
														}
													}
							       					}
											} else {
												if (splitSnp[0].equals("24") && (splitSnp[1].equals("40") || splitSnp[1].equals("41")) ) {
													 System.out.println("notpassed\n");
												}

                                                               					if (!doublePos) {
													for (int b=0; b<bases.length; b++) {
														bases[b][run + 1] = 'N';
													}
												}
						     	  				}
										}
				       						} else {
											if (!doublePos) {
												for (int b=0; b<bases.length; b++) {
				       									bases[b][run + 1] = 'N';
												}
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
       				// }	
			// }
			}
			// Put out the last position
			Boolean allN = true;
                        for (int b=0; b<bases.length; b++) {
				for (int n=1; n<bases.length; n++) {
                                	if (bases[b][n] != 'N') {
                                		allN = false;
					}
                        	}
                        }
			if (!allN) {
                                out.print(checkChr + "\t" + checkPos);
                                for (int bb=0; bb<bases[0].length; bb++) {
                                	for (int b=0; b<bases.length; b++) {
						out.print("\t" + bases[b][bb]);
					}
                                }
                        	out.print("\n");
                        }
			out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		VcfCombined_to_snpMatrix_generalDiploid vcfCombined_to_snpMatrix_generalDiploid = new VcfCombined_to_snpMatrix_generalDiploid();
		vcfCombined_to_snpMatrix_generalDiploid.setFileToConvert(args[0], args[1]);
	}
}
