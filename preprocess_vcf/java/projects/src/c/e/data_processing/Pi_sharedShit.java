package data_processing;

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

public class Pi_sharedShit {
	public Pi_sharedShit() {}
	
	public void setMatrixFile(String fileName){
		
		try {
			File matrix = new File(fileName);
			Scanner scanM = new Scanner(matrix);
			String head = scanM.nextLine();
			String[] splitHead = head.split("\t");
			System.out.println("We have: " + (splitHead.length-3) + " samples");
			
			int samples = 0;
			
			// Eliminate bad quality samples
			String[] bad = {"none"};		//"44.fastq", "53.fastq", "54.fastq", "55.fastq", "57.fastq", "58.fastq", "59.fastq"};
			
			for (int s=3; s<splitHead.length; s++) {
				Boolean badGuy = false;
				for (int b=0; b<bad.length; b++) {
					if (splitHead[s].equals(bad[b])) {
						badGuy = true;
					}
				}
				if (!badGuy) {
					samples = samples + 1;
				}
			}
			int[] goodSamp = new int[samples];
			samples=0;
			
			for (int s=3; s<splitHead.length; s++) {
				Boolean badGuy = false;
				for (int b=0; b<bad.length; b++) {
					if (splitHead[s].equals(bad[b])) {
						badGuy = true;
					}
				}
				if (!badGuy) {
					goodSamp[samples] = s;
					samples = samples + 1;
				}
			}
			
			
			
			System.out.println("We have: " + samples + " samples");
			
			long[][] diff = new long[goodSamp.length][goodSamp.length];
			long[][] len = new long[goodSamp.length][goodSamp.length];
			for (int s=0; s<goodSamp.length; s++) {
				for (int s2=0; s2<goodSamp.length; s2++) {
					diff[s][s2] = 0;
					len[s][s2] = 0;
				}
			}
			
			while (scanM.hasNextLine()) {
				String snp = scanM.nextLine();
				String[] splitSnp = snp.split("\t");
				
				int chr = Integer.parseInt(splitSnp[0]);
				int pos = Integer.parseInt(splitSnp[1]);
				
        		for (int s1=0; s1<goodSamp.length; s1++) {
	        		char base1 = splitSnp[goodSamp[s1]].charAt(0);
	        		
		        	for (int s2=0; s2<goodSamp.length; s2++) {
		        		char base2 = splitSnp[goodSamp[s2]].charAt(0);
		        		
		        		if ( (base1 != 'N') && (base2 != 'N') ) {
		        			len[s1][s2] = len[s1][s2] + 1;
	        				if (base1 != base2) {
		        				diff[s1][s2] = diff[s1][s2] + 1;
		        			}
		        		}
		        	}
	        	}
			}
			// pi
			double[][] pairwDiff = new double[goodSamp.length][goodSamp.length];
			for (int p=0; p<goodSamp.length; p++) {
				for (int q=0; q<goodSamp.length; q++) {
					pairwDiff[p][q] = (double)(diff[p][q])/(double)(len[p][q]);
				}
			}
			// Write out
			
			Writer writer = new FileWriter(fileName + "_pairwDiffMatrix.txt");
			PrintWriter out = new PrintWriter(writer);			
			
			Writer writer2 = new FileWriter(fileName + "_pairwDiff.txt");
			PrintWriter out2 = new PrintWriter(writer2);

			for (int s1=0; s1<pairwDiff.length; s1++) {
				if (s1 != pairwDiff.length - 1) {
					for (int s2=s1+1; s2<pairwDiff.length; s2++) {
						out2.print(len[s1][s2] + "\t");
					}
				}
			}
			out2.print("\n");
			for (int s1=0; s1<pairwDiff.length; s1++) {
				if (s1 != pairwDiff.length - 1) {
					for (int s2=s1+1; s2<pairwDiff.length; s2++) {
						out2.print(diff[s1][s2] + "\t");
					}
				}
			}
			out2.print("\n");
			for (int s1=0; s1<pairwDiff.length; s1++) {
				for (int s2=0; s2<pairwDiff.length; s2++) {
					out.print(pairwDiff[s1][s2] + "\t");
				}
				out.print("\n");
				if (s1 != pairwDiff.length - 1) {
					for (int s2=s1+1; s2<pairwDiff.length; s2++) {
						out2.print(pairwDiff[s1][s2] + "\t");
					}
				}
			}
			out.close();
			out2.close();
						
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Pi_sharedShit pi_sharedShit = new Pi_sharedShit();
		pi_sharedShit.setMatrixFile("/Volumes/CVI/final/sharedStuff/superVitious3.vcf_c1q17_chr.txt");
	}
}
