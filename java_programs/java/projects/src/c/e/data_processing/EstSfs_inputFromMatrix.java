package data_processing;

import java.io.File;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
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

public class EstSfs_inputFromMatrix {
	
	public EstSfs_inputFromMatrix() {}
	
	public void setFileToSubSet(String matrixName, String outF){
		
		try {
			String mn = "20";
			String outFile = outF + "_" + mn;
			int maxN = Integer.parseInt(mn);
			
			Writer writer = new FileWriter(outFile + "_input_estSfs.txt");
			PrintWriter out = new PrintWriter(writer);
			
			Writer writer1 = new FileWriter(outFile + "_ancestralState.txt");
			PrintWriter out1 = new PrintWriter(writer1);
			out1.print("chr" + "\t" + "pos" + "\t" + "cvi" + "\t" + "ha" + "\t" + "sma" + "\t" + "nma" + "\t" + "rif" + "\n");
			
			Writer writer2 = new FileWriter(outFile + "_pos_estSfs.txt");
			PrintWriter out2 = new PrintWriter(writer2);
			out2.print("chr" + "\t" + "pos" + "\t" + "ha" + "\t" + "sma" + "\t" + "nma" + "\n");
			
			char[] bases = {'A', 'C', 'G', 'T'};
			
			String[][] outIDs = {
					{"22010", "22009", "22003", "18516", "18511", "18513", "35523", "35598", "35595", "35596", "35601", "35602", "35604", "35606", "35613", "35616"}, 
					{"22006", "22004", "22002", "18514", "18512", "18510", "35513", "35594", "35599", "35600", "35625", "35622", "35607", "35608", "35610", "35611", "35615", "35618", "35619", "35621", "35605", "35623", "37470", "37469", "37468"}, 
					{"22011", "22005", "22001", "18509", "22000", "35620", "35624", "35521", "35522", "35593", "35603", "35609", "35612", "37472"},
					{"37471", "35617", "35614", "35512", "22008", "22007", "21999", "18515"}
			};
			
			// Santo:
			String[] focus = {"35519", "35518", "35517", "35516", "27182", "27181", "27180", "27179", "27177", "27175", "27174", "27172", "27170", "27169", "27165", "27163", "27162", "27161", "27160", "27158", "22643", "22642", "22641", "22639", "22637", "22636", "22634", "22633", "22632", "22631", "22630", "22629", "22628", "22627", "22626", "22624", "22623", "22622", "22620", "22619", "22618", "22617", "22616", "22615", "22609", "22013", "20687", "20686", "20685", "20684", "20683", "20682", "20681", "16293", "16292", "16151", "16150", "15675", "15674", "15673", "15672", "15671", "15670", "15669", "15668", "13583", "13582", "13581", "13580", "13578", "13183", "13179", "13178", "13177", "13175", "13173", "13172", "12914", "12912", "12911", "12849", "12766"};
			
			
			// Get indexes
			//
			int[][] outInd = new int[outIDs.length][];
			int[][] outAncSites = new int[outIDs.length][];
			for (int o=0; o<outIDs.length; o++) {
				outInd[o] = new int[outIDs[o].length];
				outAncSites[o] = new int[outIDs[o].length];
				for (int i=0; i<outAncSites[o].length; i++) {
					outAncSites[o][i] = 0;
				}
			}
			int[] focInd = new int[focus.length];
			
			
			
			
			File matrix = new File(matrixName);
	   	 	Scanner scannerMatrix = new Scanner(matrix);
	    	
			String idsUnsplit = scannerMatrix.nextLine();
	    		String[] splitIDs = idsUnsplit.split("\t");
			
			int count = 0;
			for (int o=0; o<outIDs.length; o++) {
				for (int i =0; i<outInd[o].length; i++) {
            	        		for (int s=2; s<splitIDs.length; s++) {
                	        		if (splitIDs[s].equals(outIDs[o][i])) {
                        				outInd[o][i] = s;
                       				}
                    			}
				}
			}
			for (int f =0; f<focInd.length; f++) {
                		for (int s=2; s<splitIDs.length; s++) {
                			if (splitIDs[s].equals(focus[f])) {
                    				focInd[f] = s;
                			}
                		}
			}
			
			
			
			// Open the big matrix
		    	matrix = new File(matrixName);
	    		scannerMatrix = new Scanner(matrix);
			
	       		// Go with contents
	    		int nSites = 0;
	    		int snps = 0;
	    	
	       		scannerMatrix.nextLine();
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
			       	String[] splitSnp = snp.split("\t");
		       	
		       	// If there is any N, out
		       	// Build indexes for non-N accessions
		       	int nonN = 0;
		       	boolean N = false;
		       	boolean mism = false;
		       	char refBase = splitSnp[2].charAt(0);
		       	char cviBase = 'Z';
		       	for (int f=0; f<focInd.length; f++) {
		       		if (splitSnp[focInd[f]].charAt(0) == 'N') {
		       			N = true;
		       		} else {
		       			if (splitSnp[focInd[f]].charAt(0) != refBase) {
		       				cviBase = splitSnp[focInd[f]].charAt(0);
		       				mism = true;
		       			}
		       			nonN = nonN + 1;
		       		}
		       	}
		       	if (mism) {
		       		
		       		// Check all Moroccans for ancestry
		       		for (int o=0; o<outInd.length; o++) {
			       		for (int i=0; i<outInd[o].length; i++) {
			       			if (splitSnp[outInd[o][i]].charAt(0) == cviBase) {
			       				outAncSites[o][i] = outAncSites[o][i] + 1;
			       			}
			       		}
		       		}
		       		
		       		
		       		
		       		// If it is not too many Ns, output
			       	if (nonN >= (focus.length - maxN)) {
			       		snps = snps + 1;
			       		int chr = Integer.parseInt(splitSnp[0]);
			       		int pos = Integer.parseInt(splitSnp[1]);
			       		out1.print(chr + "\t" + pos + "\t");
			       		out2.print(chr + "\t" + pos + "\t");
			       					       		
			       		// Build indexes of nonN accessions
				       	int[] focIndNN = new int[nonN];
				       	nonN = 0;
				       	for (int f=0; f<focInd.length; f++) {
				       		if (splitSnp[focInd[f]].charAt(0) != 'N') {
				       			focIndNN[nonN] = focInd[f];
				       			nonN = nonN + 1;
				       		}
				       	}
				       	// Sorteggia i capeverdi non-N without replacement
				       	// Fisher-Yates algorithm
				       	//
				       	int[] focIndRandN = new int[focus.length - maxN];
				       	Random randomGenerator = new Random();
						for (int boot=0; boot<focIndRandN.length; boot++) {
							int r = randomGenerator.nextInt(focIndNN.length-boot);
							int l = focIndNN[r];
							focIndRandN[focIndRandN.length-1-boot] = l;
							focIndNN[r] = focIndNN[focIndNN.length-1-boot];
							focIndNN[focIndNN.length-1-boot] = l;
						}
				       	
				       	
			       		// Count bases, in order: A, C, G, T.
			       		int[] bas = new int[4];
			       		for (int b=0; b<4; b++) {
			       			bas[b] = 0;
			       		}
			       		for (int f=0; f<focIndRandN.length; f++) {
				       		for (int b=0; b<bases.length; b++) {
				       			if (splitSnp[focIndRandN[f]].charAt(0) == bases[b]) {
					       			bas[b] = bas[b] + 1;
					       		}
				       		}
				       	}
			       		// Got focal bases, out.
			       		out.print(bas[0]);
			       		out1.print(bas[0]);
			       		for (int b=1; b<bas.length; b++) {
			       			out.print("," + bas[b]);
			       			out1.print("," + bas[b]);
			       		}
			       		out.print(" ");
			       		out1.print("\t");
			       		
			       		
			       		// Now get output consensus 
			       		// And output all bases
			       		// 
			       		
			       		for (int o=0; o<outInd.length; o++) {
			       			// Count bases, in order: A, C, G, T.
				       		bas = new int[4];
				       		for (int b=0; b<4; b++) {
				       			bas[b] = 0;
				       		}
				       		for (int i=0; i<outInd[o].length; i++) {
					       		for (int b=0; b<bases.length; b++) {
					       			if (splitSnp[outInd[o][i]].charAt(0) == bases[b]) {
						       			bas[b] = bas[b] + 1;
						       		}
					       		}
					       	}
			       			// Decide for the high frequency
				       		int highInd = -1;
				       		for (int b=0; b<4; b++) {
				       			if (bas[b] > highInd) {
				       				highInd = b;
				       			}
				       		}
				       		// Output for estSfs
				       		// And for our interest
				       		// 
				       		if (highInd == 0) {
			       				if (o<3) {						// 3 outgroups max allowed
			       					out.print("1");
			       				}
			       			} else {
			       				if (o<3) {
			       					out.print("0");
			       				}
			       			}
				       		out1.print(bas[0]);
				       		for (int b=1; b<bas.length; b++) {
				       			out1.print("," + bas[b]);
				       			if (b == highInd) {
				       				if (o<3) {
				       					out.print(",1");
				       				}
				       			} else {
				       				if (o<3) {
				       					out.print(",0");
				       				}
				       			}
				       		}
				       		
				       		// Output also consensus outgroups
				       		out2.print(bases[highInd]);
				       		
				       		// A capo, or keep writing
				       		if (o<2) {
				       			out.print(" ");
				       		} else {
			       				if (o<3) {
			       					out.print("\n");
			       				}
				       		}
				       		if (o<3) {
				       			out1.print("\t");
				       			out2.print("\t");
				       		} else {
			       				out1.print("\n");
				       			out2.print("\n");
				       		}
			       		}
			    	
			       	} else {
			       		nSites = nSites + 1;
			       	}
		       	}
			}
			out.close();
			out1.close();
			out2.close();
			
			Writer writerO = new FileWriter(outFile + "_summary.txt");
			PrintWriter outO = new PrintWriter(writerO);
			
			outO.println("Missing: " + nSites);
			outO.println("Mismatchis: " + snps);
			outO.close();
			
			System.out.println("Missing: " + nSites);
			System.out.println("Mismatchis: " + snps);
			
			

			Writer writerA = new FileWriter(outFile + "_ancestrSingleMor.txt");
			PrintWriter outA = new PrintWriter(writerA);
			
			for (int o=0; o<outInd.length; o++) {
	       		for (int i=0; i<outInd[o].length; i++) {
       				outA.print(outIDs[o][i] + "\t" + outAncSites[o][i] + "\n");
	       		}
       		}
       		
			outA.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		EstSfs_inputFromMatrix estSfs_inputFromMatrix = new EstSfs_inputFromMatrix();
		estSfs_inputFromMatrix.setFileToSubSet(args[0], args[1]);
	}
}
