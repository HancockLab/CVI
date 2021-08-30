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

public class CoverageFromVcf {
	
	public CoverageFromVcf() {}
	
	public void setFileToConvert(String filename, String sampleStr, String results){
		
		try {
			
			// Get index for interesting samples
			String[] samples = new String[1];

			int[] sampleIndexes = new int[samples.length];
			Boolean[] thereIs = new Boolean[samples.length];
			long[] avCov = new long[samples.length];
			long[] nonNpos = new long[samples.length];
			for (int a =0; a<avCov.length; a++) {
				avCov[a] = 0;
				nonNpos[a] = 0;
				thereIs[a] = false;
			}
			// Open combined vcf
			File vcf_comb = new File(filename);
			Scanner scannerVcf = new Scanner(vcf_comb);
			
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
						
						// Get indexes
						/*int ind=0;
						for (int i=9; i<splitSnp.length; i++) {
							for (int run=0; run<samples.length; run++) {
								if (splitSnp[i].equals(samples[run])) {
									thereIs[run] = true;
									ind = ind + 1;
								}
							}
						}
						sampleIndexes = new int[ind];
						ind=0;
                                                for (int i=9; i<splitSnp.length; i++) {
                                                        for (int run=0; run<samples.length; run++) {
                                                                if (splitSnp[i].equals(samples[run])) {
                                                                        sampleIndexes[ind] = i;
                                                                        thereIs[run] = true;
                                                                        ind = ind + 1;
                                                                }
                                                        }
                                                }
						for (int r=0; r<samples.length; r++) {
							if (!thereIs[r]) {
								System.out.println("There is no: " + samples[r]);
							}
						}
						System.out.println(Arrays.toString(sampleIndexes));
						*/
						sampleIndexes[0] = Integer.parseInt(sampleStr) + 9;
                                                samples[0] = splitIDs[sampleIndexes[0]];
					}
				}
				// Header is over
				




				// mind proper stuff now
				
				// If 1001 vcf:
				// if ( (snp1.charAt(0) != '#') && (splitSnp[0].charAt(0) == chromosome.charAt(0)) ) {
				// 
				// If our vcf:
				if (splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r') {
					
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
								
							       	int run = 0;
							       	for (int sample=0; sample<sampleIndexes.length; sample++) {
					       				//if (!splitIDs[sampleIndexes[sample]].equals("22012")) {              // Oua out
					       					// Is there any call at all?
					     	  				// Normal vcf:
					    	 	  			if ( (splitSnp[sampleIndexes[sample]].split(":")[0].charAt(0) != '.') && (splitSnp[sampleIndexes[sample]].split(":")[1].charAt(0) != '.') && (splitSnp[sampleIndexes[sample]].split(":")[2].charAt(0) != '.') ){
											// If - our Vcf
											// Check coverage and quality
							       				int cov=Integer.parseInt(splitSnp[sampleIndexes[sample]].split(":")[2]);
						       					int qual=Integer.parseInt(splitSnp[sampleIndexes[sample]].split(":")[1]);
						    		   			
						      			 		// Print out something reasonable
						      			 		// (Erase if plink)
						       					//if ( (cov >=3) && (qual >= 25) ) {
								       				// Normal vcf:
								       				//
								       				//
								       				avCov[sample] = avCov[sample] + cov;
												nonNpos[sample] = nonNpos[sample] + 1;
												//
								       				//
								       				//
											//}
				       						}
										run = run + 1;
									//}
								}
							}
						}	
					}
       				}	
			



				// Open also the writing file
				Writer writer = new FileWriter(results + "/" + splitIDs[sampleIndexes[0]] + ".txt");
				PrintWriter out = new PrintWriter(writer);
				//out.print("#Sample" + "\t" + "sumCoverage/goldenPathLength(119146348)" + "\t" + "sumCoverage/non 'N' pos" + "\n");
				for (int s=0; s<sampleIndexes.length; s++) {
					out.print(splitIDs[sampleIndexes[s]] + "\t" + (double)(avCov[s])/(double)(119146348) + "\t" + (double)(avCov[s])/(double)(nonNpos[s]) + "\n");
				}
				out.close();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CoverageFromVcf coverageFromVcf = new CoverageFromVcf();
		coverageFromVcf.setFileToConvert(args[0], args[1], args[2]);
	}
}
