package c.e.noFiltering;

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

public class MpileupAllExtract {

	private BitSet[] mask = null;
	private BitSet[] maskOr = null;
	private BitSet[] maskAnd = null;
	private BitSet[] maskXor = null;
	private BitSet[] combinedMask = null;
	private String snp1=null;
	private String[] splitSnp1 = null;
	
	private MpileupAllExtract() {}

	public void setSnpFile(String filename){
		
		
		try {
			int accession = Integer.parseInt(filename); 
		
			String whereWe = "/home/CIBIV/andreaf/canaries/rawData/analyses/checkFiltering/allFiltersStrangeWithAll22/";
			
			mask =new BitSet[5];
			maskOr=new BitSet[5];
			maskAnd=new BitSet[5];
			maskXor=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
				maskOr[c]= new BitSet();
				maskAnd[c]= new BitSet();
				maskXor[c]= new BitSet();
			}
			
			
			// Create maskXor of segregating sites, not taking into account N in masks
						
			File folder = new File(whereWe + "snp/");
			File[] listOfSNPFiles = folder.listFiles();
			int numFiles = listOfSNPFiles.length;
			
			System.out.println("numFiles: " + numFiles);
			for (int z=0; z<listOfSNPFiles.length; z++) {
				System.out.println("listOfSNPFiles" + z + " is: " + listOfSNPFiles[z] );
			}
			

			// Now build a mask of segregating sites - maskXor
			
			for (int f=0; f<listOfSNPFiles.length; f++) {
				System.out.println("File numero.... " + f);
				
				for (int c=0; c<5; c++) {
					mask[c]= new BitSet();
				}
				File snpFile = listOfSNPFiles[f];
				
				Scanner scannerCvi = new Scanner(snpFile);
				while ( scannerCvi.hasNextLine() ) { 
					snp1 = scannerCvi.nextLine();
		        	splitSnp1 = snp1.split("\t");
		        	
		        	if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
			        	
		        		int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
			        	int posSNP = Integer.parseInt(splitSnp1[1]);
		        		// if (combinedMask[chr-1].get(posSNP)) {
		        		mask[chr-1].set(posSNP);
		        		// }
			        }
				}
				for (int c=0; c<5; c++) {
					if (f == 0) {
						maskAnd[c].or(mask[c]);
					}
					maskOr[c].or(mask[c]);
					maskAnd[c].and(mask[c]);
				}
			}
			
			// Accumulate new SNPs in maskXor
			int numSnpOr =0;
			int numSnpAnd =0;
			int numSnps =0;
			for (int c=0; c<5; c++) {
				maskXor[c].or(maskOr[c]);
				maskXor[c].xor(maskAnd[c]);
				numSnpOr = numSnpOr + maskOr[c].cardinality();
				numSnpAnd = numSnpAnd + maskAnd[c].cardinality();
				numSnps = numSnps + maskXor[c].cardinality();
				System.out.println("maskXor[c].cardinality()" + maskXor[c].cardinality());
			}
			System.out.println(numSnpOr);
			System.out.println(numSnpAnd);
			System.out.println(numSnps);
			//
			//
			// MaskXor contains all segregating sites
			
			
			
			
			
			
			// Read .all files, only at segregating sites (in maskXor), write a polarized matrix to col-0 -> 0, 1, N
			
			File folderAll = new File(whereWe + "all/");
			File[] listOfMpileupAll = folderAll.listFiles();
			
			for (int z=0; z<listOfMpileupAll.length; z++) {
				System.out.println("listOfMpileupAll" + z + " is: " + listOfMpileupAll[z] );
			}
			
			for (int f=accession; f<accession+1; f++) {
				System.out.println("File numero.... " + f);
				
				Writer writer = new FileWriter(whereWe + "extract/" + f + ".extract");
				PrintWriter out = new PrintWriter(writer);
				
				File snpFile = listOfMpileupAll[f];
				Scanner scannerCvi = new Scanner(snpFile);
				while ( scannerCvi.hasNextLine() ) {
					
					snp1 = scannerCvi.nextLine();
		        	splitSnp1 = snp1.split("\t");
	        		if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
		        	
	        			int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
		        		int posSNP = Integer.parseInt(splitSnp1[1]);
		        		if (maskXor[chr-1].get(posSNP)) {
		        			out.println(snp1);
			        	}
	        		}
				}
				out.print("\n");
				out.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		MpileupAllExtract mpileupAllExtract = new MpileupAllExtract();
		mpileupAllExtract.setSnpFile(args[0]); // 
	}
}
