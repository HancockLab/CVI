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

public class Diploidize_sample_pairs_forPopSizeAbc {
	
	public Diploidize_sample_pairs_forPopSizeAbc() {}
	
	public void setFileToConvert(String filename){
		
		try {
			//
			// Open combined vcf
			//
			File vcf_comb = new File(filename);
			Scanner scannerVcf = new Scanner(vcf_comb);
			
			// Open also the writing file
			Writer writer = new FileWriter(filename + "_twoHapsToDip.vcf");
			PrintWriter out = new PrintWriter(writer);
			
			int nSample =0;
			String[] splitIDs = null;
			
			int chrMem = 0;
			String snpMem = null;

			while ( scannerVcf.hasNextLine() ) {
				String snp1 = scannerVcf.nextLine();
				String[] splitSnp = snp1.split("\t");
		       		
				// Mind the header now
				if (snp1.charAt(0) == '#') {
					// Print it out
					// out.print(snp1 + "\n");
					
					if (snp1.charAt(1) != '#') {
						splitIDs = snp1.split("\t");
						
						// Count the samples
					    nSample = splitSnp.length - 9;
					    System.out.println("Nsamples: " + nSample);
					    
					    int samples = splitIDs.length - 9;
						System.out.println("We have: " + samples + " samples");
						nSample = samples;
				    }
					
					// Header is over
				} else {

					// mind proper stuff now
					
					// Check reference allele
					//
					
					String[] ref = splitSnp[3].split("");
					Boolean str = true;
					for (int r=0; r<ref.length; r++) {
					        if (ref[r].charAt(0) != 'A' && ref[r].charAt(0) != 'T' && ref[r].charAt(0) != 'C' && ref[r].charAt(0) != 'G') {
							str = false;
						}
					}
					if (!str) {
						// System.out.println(snp1);
					} else {
						// Output now
						//
						Boolean tair = false;
						if (splitSnp[0].charAt(0) == 'C' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'r' ) {
							tair = true;
							out.print(splitSnp[0].charAt(3));
							if (Character.getNumericValue(splitSnp[0].charAt(3)) > chrMem) {
								System.out.println(snpMem);
								chrMem = Character.getNumericValue(splitSnp[0].charAt(3));
							}
						}
						// chloroplast
						if (splitSnp[0].charAt(0) == 'c' && splitSnp[0].charAt(1) == 'h' && splitSnp[0].charAt(2) == 'l' ) {
							tair = true;
							out.print("Pt");
						}
						// mitochondria
						if (splitSnp[0].charAt(0) == 'm' && splitSnp[0].charAt(1) == 'i' && splitSnp[0].charAt(2) == 't' ) {
							tair = true;
							out.print("Mt");
						}
						if (tair) {
							for (int col=1; col<9; col++) {
								out.print("\t" + splitSnp[col]);
							}
							
							// Now combine arbitrary pairs of samples into diploids
							// 
							for (int col=9; col<splitSnp.length; col++) {
								if ( (col & 1) != 0 ) {
									String[] geno1 = splitSnp[col].split(":");
									String[] geno2 = splitSnp[col + 1].split(":");
									if (geno1[0].charAt(0) != '.' && geno2[0].charAt(0) != '.') {
										out.print("\t" + geno1[0] + "|" + geno2[0]);
									} else {
										out.print("\t" + ".|.");
									}
									for (int g=1; g<geno1.length; g++) {
										if (geno1[g].charAt(0) != '.' && geno2[g].charAt(0) != '.') {
											out.print( ":" + (int)(0.5*(Integer.parseInt(geno1[g]) + Integer.parseInt(geno2[g]))) );
										} else {
											out.print( ":" + geno1[g]);
										}
									}
								}
							}
							out.print("\n");
						}
					}
				}
				snpMem = snp1;
       			}
			out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Diploidize_sample_pairs_forPopSizeAbc diploidize_sample_pairs_forPopSizeAbc = new Diploidize_sample_pairs_forPopSizeAbc();
		diploidize_sample_pairs_forPopSizeAbc.setFileToConvert(args[0]);
	}
}
