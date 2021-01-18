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

public class HetsShoreFile_toVcf {
	
	public HetsShoreFile_toVcf() {}
	
	public void setFileToConvert(String filename){
		
		try {
			// int posThresh = Integer.parseInt(posThreshStr);
			
			// Open hets file
			File hetsFile = new File(filename);
			Scanner scannerVcf = new Scanner(hetsFile);
			
			// Open also the writing file
			Writer writer = new FileWriter(filename + ".vcf");
			PrintWriter out = new PrintWriter(writer);
			
			// Write header
			//
			out.print("##fileformat=VCFv4.0\n");
			out.print("##INFO=<ID=NS,Number=1,Type=Integer,Description=\"Number of Samples With Data\">\n");
			out.print("##INFO=<ID=DP,Number=1,Type=Integer,Description=\"Total Depth\">\n");
			out.print("##FILTER=<ID=q25,Description=\"Quality below 25\">\n");
			out.print("##FORMAT=<ID=GT,Number=1,Type=String,Description=\"Genotype\">\n");
			out.print("##FORMAT=<ID=GQ,Number=1,Type=Integer,Description=\"Genotype Quality\">\n");
			out.print("##FORMAT=<ID=DP,Number=1,Type=Integer,Description=\"Read Depth\">\n");
			out.print("#CHROM\tPOS\tID\tREF\tALT\tQUAL\tFILTER\tINFO\tFORMAT\t");
			// 
			// Missing the sample name at the end of this line
			//
			boolean lastHead = true;

			while ( scannerVcf.hasNextLine() ) {
				String snp1 = scannerVcf.nextLine();
				String[] splitSnp = snp1.split("\t");
		       		
				// Finish header
				//
				String name = splitSnp[0];
				if (lastHead) {
					out.print(name + "\n");
				}

				// Header done. Now contents
				//
				String chr = splitSnp[1];
				String pos = splitSnp[2];
				String id = ".";
				String refBase = splitSnp[3];
				String altBase = null;
				if (splitSnp[3].equals(splitSnp[4])) {
					altBase = splitSnp[7];
				} else {
					altBase = splitSnp[4];
				}
				String qual = "30";
				int dp = Integer.parseInt(splitSnp[5]) + Integer.parseInt(splitSnp[8]);
				
				// Throw a random number
				//
						
    				Random randomGenerator = new Random();
				boolean ran = randomGenerator.nextBoolean();
		    		
				String geno = null;
				if (ran) {
					geno = "1|0:" + qual + ":" + dp;
				} else {
					geno = "0|1:" + qual + ":" + dp;
				}

				out.print(chr + "\t" + pos + "\t" + id + "\t" + refBase + "\t" + altBase + "\t" + qual + "\tPASS\tNS=1;DP=" + dp + "\tGT:GQ:DP\t" + geno + "\n");

				lastHead = false;
       			}
			out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HetsShoreFile_toVcf hetsShoreFile_toVcf = new HetsShoreFile_toVcf();
		hetsShoreFile_toVcf.setFileToConvert(args[0]);
	}
}
