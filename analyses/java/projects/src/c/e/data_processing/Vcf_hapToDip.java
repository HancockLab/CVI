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

public class Vcf_hapToDip {
	
	public Vcf_hapToDip() {}
	
	public void setFileToConvert(String filename, String headerTF){
		
		try {
			boolean header = true;
			if (headerTF.equals("false")) {
				header = false;
			}
			// int posThresh = Integer.parseInt(posThreshStr);
			
			// Open combined vcf
			File vcf_comb = new File(filename);
			Scanner scannerVcf = new Scanner(vcf_comb);
			
			// Open also the writing file
			Writer writer = new FileWriter(filename + "_dip.vcf");
			PrintWriter out = new PrintWriter(writer);
			
			int checkChr = 0;
			int checkPos=0;	
			int nSample =0;
			Boolean doublePos = false;
			char[] bases = null;
			int fillBases = 0;
			String[] splitIDs = null;
			int[] goodSamp = null;
			
			int chrMem = 0;
			String snpMem = null;

			while ( scannerVcf.hasNextLine() ) {
				String snp1 = scannerVcf.nextLine();
				String[] splitSnp = snp1.split("\t");
		       		
				// Mind the header now
				if (snp1.charAt(0) == '#') {
					// Print it out
					if (header) {
						out.print(snp1 + "\n");
					}
					
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
							for (int col=9; col<splitSnp.length; col++) {
								String[] geno = splitSnp[col].split(":");
								out.print("\t" + geno[0] + "|" + geno[0]);
								for (int g=1; g<geno.length; g++) {
									out.print( ":" + geno[g]);
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
		Vcf_hapToDip vcf_hapToDip = new Vcf_hapToDip();
		vcf_hapToDip.setFileToConvert(args[0], args[1]);
	}
}
