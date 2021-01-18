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

public class VcfCombined_to_snpMatrix_outHighCov {
	
	public VcfCombined_to_snpMatrix_outHighCov() {}
	
	public void setFileToConvert(String filename, String chromosome, String outFile){
		
		try {
			// int posThresh = Integer.parseInt(posThreshStr);
			
			// Open combined vcf
			File vcf_comb = new File(filename);
			Scanner scannerVcf = new Scanner(vcf_comb);
		



			////
			//	Run through once to get the coverage vector
			////
			int nSample = 0;
			String[] splitIDs = null;
			long[] sumcov = new long[1];
			long[] lencov = new long[1];
			
			
			while ( scannerVcf.hasNextLine() ) {
				String snp1 = scannerVcf.nextLine();
			       	String[] splitSnp = snp1.split("\t");
		       	
				// Mind the header now
				if (snp1.charAt(0) == '#') {
					if (snp1.charAt(1) != '#') {
						splitIDs = snp1.split("\t");
						// Count the samples
					       	nSample = splitSnp.length - 9;						// - 1: Oua out
					
						sumcov = new long[nSample];
						lencov = new long[nSample];
					}
				}
				// Header is over
				

				// mind proper stuff now
				if (splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r' ) { 
				
					
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
							       	// For every sample, build up average coverage
							       	int run = 0;
							       	for (int sample=9; sample<splitIDs.length; sample++) {
					    	 	  		if ( (splitSnp[sample].split(":")[0].charAt(0) != '.') && (splitSnp[sample].split(":")[1].charAt(0) != '.') && (splitSnp[sample].split(":")[2].charAt(0) != '.') ){
											// If - our Vcf
											// Check coverage and quality
							       				int cov=Integer.parseInt(splitSnp[sample].split(":")[2]);

						    		   			sumcov[sample-9] = sumcov[sample-9] + cov;
											lencov[sample-9] = lencov[sample-9] + 1;
									}
								}
							}
						}	
				}
       			}	
			
			
			







			////
			//		Get vector of average coverages 
			//		To filter SNPs with coverage > 2*average
			Writer writer1 = new FileWriter(outFile + "_c5q25_chr" + chromosome + "_avCov.txt");
			PrintWriter out1 = new PrintWriter(writer1);
		

			double[] avCov = new double[sumcov.length];
			for (int f=0; f<sumcov.length;f++) {
				avCov[f] = (double)sumcov[f]/(double)lencov[f];
				System.out.println(2*avCov[f]);
				out1.print(splitIDs[f+9] + "\t" + avCov[f] + "\t" + sumcov[f] + "\t" + lencov[f] + "\n");
			}
			System.out.println("avCov: \t" + Arrays.toString(avCov));
			out1.close();










/*
                         //              Get vector of average coverages 
			File fileCov = new File("/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-07_cviMorLyrLer_noHigCov.txt_c5q25_chr2_avCov.txt"); 	//outFile + "_c5q25_chr2_avCov.txt");
                        Scanner scannerCov = new Scanner(fileCov);
			int covCount = 0;
			while ( scannerCov.hasNextLine() ) {
                        	String snp1 = scannerCov.nextLine();
                       		String[] splitSnp = snp1.split("\t");
				covCount = covCount + 1;
			}
			
			double[] avCov = new double[covCount];
			covCount = 0;
			scannerCov = new Scanner(fileCov);
			while ( scannerCov.hasNextLine() ) {
				String snp1 = scannerCov.nextLine();
				String[] splitSnp = snp1.split("\t");
				avCov[covCount] = Double.parseDouble(splitSnp[1]);
				covCount = covCount + 1;
			}
			System.out.println("avCov: \t" + Arrays.toString(avCov));


*/























	
			// Open also the writing file
			Writer writer = new FileWriter(outFile + "_c5q25_chr" + chromosome + ".txt");
			PrintWriter out = new PrintWriter(writer);
		









			int checkChr = 0;
			int checkPos=0;	
			nSample =0;
			Boolean doublePos = false;
			char[] bases = null;
			int fillBases = 0;
			splitIDs = null;
			
			System.out.println("Starting the real game");
			
			
			scannerVcf = new Scanner(vcf_comb);

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
				       			System.out.println(Arrays.toString(splitIDs));
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
				
				
				if (splitSnp[0].charAt(0) == chromosome.charAt(0) ) {
				//
					
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
								if ( checkChr == Character.getNumericValue(splitSnp[0].charAt(0))) {						// 3 for our vcf
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
									checkChr = Character.getNumericValue(splitSnp[0].charAt(0));						//3 for our vcf
									checkPos = Integer.parseInt(splitSnp[1]);	
								}
							
								// For every line, print chromosome and position and Col-0
                    					        // out.print(splitSnp[0].charAt(3) + "\t" + splitSnp[1] + "\t" + splitSnp[3]);
						       		
								// Reference base as bases[0]
								
								bases[0] = splitSnp[3].charAt(0);
								
							       	// For every sample, print a base
							       	int run = 0;
							       	for (int sample=9; sample<splitIDs.length; sample++) {
					     	  				// Normal vcf:
					    	 	  			if ( (splitSnp[sample].split(":")[0].charAt(0) != '.') && (splitSnp[sample].split(":")[1].charAt(0) != '.') && (splitSnp[sample].split(":")[2].charAt(0) != '.') ){
											// If - our Vcf
											// Check coverage and quality
							       				int cov=Integer.parseInt(splitSnp[sample].split(":")[2]);
						       					int qual=Integer.parseInt(splitSnp[sample].split(":")[1]);
											
						      			 		// Print out something reasonable
						      			 		// (Erase if plink)
						       					if ( (cov >=5) && (qual >= 30) && (cov < 2*avCov[sample-9]) ) {
								       				// Normal vcf:
								       				if (Character.getNumericValue(splitSnp[sample].split(":")[0].charAt(0)) == 0) {
								       				// Plink vcf:
								       				// if (Character.getNumericValue(splitSnp[9 + sample].split("/")[0].charAt(0)) == 0) {
								       					bases[run + 1] = splitSnp[3].charAt(0);
						       							//out.print("\t" + splitSnp[3].charAt(0));
									       			} else {
									       				// Normal vcf:
									       				if ( (splitSnp[4].length() == 1) && (Character.getNumericValue(splitSnp[sample].split(":")[0].charAt(0)) == 1) ) {
									       				//
									       				// Plink
									       				// if ( (splitSnp[4].length() == 1) && (Character.getNumericValue(splitSnp[9 + sample].split("/")[0].charAt(0)) == 1) ) {
	                                               		         					bases[run + 1] = splitSnp[4].charAt(0);
									       					//out.print("\t" + splitSnp[4].charAt(0));
									       				} else {
														// If normal vcf:
														bases[run + 1] = splitSnp[4].split(",")[Character.getNumericValue(splitSnp[sample].split(":")[0].charAt(0)) - 1].charAt(0);
														// If plink:
														// bases[sample + 1] = splitSnp[4].split(",")[Character.getNumericValue(splitSnp[9 + sample].split("/")[0].charAt(0)) - 1].charAt(0);
									   					//out.print("\t" + splitSnp[4].split(",")[Integer.parseInt(splitSnp[9 + sample].split(":")[0]) - 1].charAt(0));
							       						}
							       					}
					       						// Erase if plink:
											} else {
                                                               					if (!doublePos) {
													bases[run + 1] = 'N';
												}
						     	  				}
				       						} else {
											if (!doublePos) {
				       								bases[run + 1] = 'N';
				       							}
										}
										if (sample == 388) {
											System.out.print(bases[run + 1] + "\n");
										}
										run = run + 1;
								}
							}
						}	
					}
       				}	
			if (bases!=null) {
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
			}
			out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		VcfCombined_to_snpMatrix_outHighCov vcfCombined_to_snpMatrix_outHighCov = new VcfCombined_to_snpMatrix_outHighCov();
		vcfCombined_to_snpMatrix_outHighCov.setFileToConvert(args[0], args[1], args[2]);
	}
}
