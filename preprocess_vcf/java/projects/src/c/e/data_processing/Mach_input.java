package c.e.data_processing;

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

public class Mach_input {
	
	public Mach_input() {}
	
	public void setFileToSubSet(String matrixName, String chrom, String res){
		
		try {
			int chromosome = Integer.parseInt(chrom);
			String results = res + "chr" + chromosome + "_cvi";



			////
			//		Get Focus cvis 
			////

			File focusFile = new File("/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt");
				// /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_29-4-18.txt");
			Scanner scannerF = new Scanner(focusFile);
	    		int cvi = 0;
			while ( scannerF.hasNextLine() ) {
				String snp = scannerF.nextLine();
		       		cvi = cvi + 1;
			}
			System.out.println("Cvis: " + cvi);
			String[] focus = new String[cvi];
			
			cvi= 0;
			scannerF = new Scanner(focusFile);
			while ( scannerF.hasNextLine() ) {
				String snp = scannerF.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				focus[cvi] = splitSnp[0];
				cvi = cvi + 1;
			}
			System.out.println("Cvi: " + Arrays.toString(focus));



			// cvi:
			//String[] focus = {"35515", "35514", "27173", "27171", "27167", "27166", "27164", "27155", "22644", "22640", "22635", "22625", "22621", "22614", "22610", "21230", "21229", "21228", "21226", "21225", "16295", "16294", "13584", "13577", "13576", "13575", "13574", "13573", "13572", "13571", "13187", "13186", "13184", "13182", "13181", "13180", "12915", "12910", "12909", "12768", "12767", "37458", "35519", "35518", "35517", "35516", "27182", "27181", "27180", "27179", "27177", "27176", "27175", "27174", "27172", "27170", "27169", "27165", "27163", "27162", "27161", "27160", "27159", "27158", "22643", "22642", "22641", "22639", "22637", "22636", "22634", "22633", "22632", "22631", "22630", "22629", "22628", "22627", "22626", "22624", "22623", "22622", "22620", "22619", "22618", "22617", "22616", "22615", "22609", "22013", "20687", "20686", "20685", "20684", "20683", "20682", "20681", "16293", "16292", "16152", "16151", "16150", "15675", "15674", "15673", "15672", "15671", "15670", "15669", "15668", "13585", "13583", "13582", "13581", "13580", "13578", "13183", "13179", "13178", "13177", "13175", "13173", "13172", "12914", "12912", "12911", "12849", "12766", "12765"};
			
			// Santo:
			// String[] focus = {"35519", "35518", "35517", "35516", "27182", "27181", "27180", "27179", "27177", "27175", "27174", "27172", "27170", "27169", "27165", "27163", "27162", "27161", "27160", "27158", "22643", "22642", "22641", "22639", "22637", "22636", "22634", "22633", "22632", "22631", "22630", "22629", "22628", "22627", "22626", "22624", "22623", "22622", "22620", "22619", "22618", "22617", "22616", "22615", "22609", "22013", "20687", "20686", "20685", "20684", "20683", "20682", "20681", "16293", "16292", "16151", "16150", "15675", "15674", "15673", "15672", "15671", "15670", "15669", "15668", "13583", "13582", "13581", "13580", "13578", "13183", "13179", "13178", "13177", "13175", "13173", "13172", "12914", "12912", "12911", "12849", "12766"};
			
			// Fogo:
			// String[] focus = {"35515", "35514", "27173", "27171", "27167", "27166", "27164", "27155", "22644", "22640", "22635", "22625", "22621", "22614", "22610", "21230", "21229", "21228", "21226", "21225", "16295", "16294", "13584", "13577", "13576", "13575", "13574", "13573", "13572", "13571", "13187", "13186", "13184", "13182", "13181", "13180", "12915", "12910", "12909", "12768", "12767"};







			////
			//		Get Canaries, just to check
			////

			File sampleFile = new File("/srv/biodata/dep_coupland/grp_hancock/VCF/canarians_noOut.txt");
			Scanner scannerS = new Scanner(sampleFile);
	    		int fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			String[] can = new String[fil];
			////
			//		Get also moroccans
			////	
			File sampleFile2 = new File("/home/fulgione/moroccan_paper_clean_IDs_plink.txt");
			Scanner scannerS2 = new Scanner(sampleFile2);
			while ( scannerS2.hasNextLine() ) {
				String snp = scannerS2.nextLine();
		       		fil = fil + 1;
			}
			System.out.println("files: " + fil);
			String[] mor = new String[fil];
			
			fil= 0;
			scannerS = new Scanner(sampleFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				mor[fil] = splitSnp[0];
				can[fil] = splitSnp[0];
				fil = fil + 1;
			}
			///
			//		Get also moroccans
			////	
			scannerS2 = new Scanner(sampleFile2);
			while ( scannerS2.hasNextLine() ) {
				String snp = scannerS2.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				mor[fil] = splitSnp[0];
				fil = fil + 1;
			}
			System.out.println("can: " + Arrays.toString(can));
                        System.out.println("mor and can: " + Arrays.toString(mor));








			// String[] mor = {"22010", "22009", "22003", "18516", "18511", "18513", "35523", "35598", "35595", "35596", "35601", "35602", "35604", "35606", "35613", "35616", "22011", "22005", "22001", "18509", "22000", "35620", "35624", "37472", "35521", "35522", "35593", "35603", "35609", "35612", "18515", "35614", "22007", "21999", "35617", "22008", "35512", "22006", "22004", "22002", "18514", "18512", "18510", "37468", "37469", "35513", "35594", "35599", "35600", "35625", "35622", "35607", "35608", "35610", "35611", "35615", "35618", "35619", "35621", "35605", "35623"};
			
			
			int[] focusInd = new int[focus.length];			
			int[] morInd = new int[mor.length];			
			
			File matrix = new File(matrixName);
	    	Scanner scannerMatrix = new Scanner(matrix);
	    	
			String idsUnsplit = scannerMatrix.nextLine();
	    	String[] splitIDs = idsUnsplit.split("\t");
	    	
	    	// Indexes for pop1
			for (int f =0; f<focusInd.length; f++) {
                for (int s=2; s<splitIDs.length; s++) {
                    if (splitIDs[s].equals(focus[f])) {
                    	focusInd[f] = s;
                    }
                }
			}
			System.out.println("Focus INdexes: " + Arrays.toString(focusInd));
			
			
	    	// Indexes for mor
			for (int f =0; f<morInd.length; f++) {
	                    for (int s=2; s<splitIDs.length; s++) {
        	            	if (splitIDs[s].equals(mor[f])) {
                	    		morInd[f] = s;
                	    	}
                	    }
			}
			System.out.println("Mor/can indexes: " + Arrays.toString(morInd));
			
			
			Writer writer1 = new FileWriter(results + "_datForMach.txt");
			PrintWriter out1 = new PrintWriter(writer1);
			
			Writer writer3 = new FileWriter(results + "_snpsForMach.txt");
			PrintWriter out3 = new PrintWriter(writer3);
			
			
			
			
			// Open the big matrix
	    	matrix = new File(matrixName);
	    	scannerMatrix = new Scanner(matrix);
			
	       	// Go with contents
	    	
	       	scannerMatrix.nextLine();
	       	int snps = 0;
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	char refBase = splitSnp[2].charAt(0);
		       	char altBase = 'N';
		       	int chr = Integer.parseInt(splitSnp[0]);
	       		int pos = Integer.parseInt(splitSnp[1]);
	       		
	       		if (chr == chromosome) {
					// Create mach input
		       		Boolean mism = false;
				int nonN = 0;
		       		Boolean multAll = false;
		       		for (int f=0; f<focusInd.length; f++) {
		       			if (splitSnp[focusInd[f]].charAt(0) != 'N') {
		       				nonN = nonN + 1;
						if (splitSnp[focusInd[f]].charAt(0) != refBase) {
							mism = true;
		       					if (altBase == 'N') {
		       						altBase = splitSnp[focusInd[f]].charAt(0);
		       					} else {
		       						if (altBase != splitSnp[focusInd[f]].charAt(0)) {
		       							multAll = true;
		       						}
		       					}
						}
		       			}
		       		}
				boolean allMorN = true;
		       		for (int f=0; f<morInd.length; f++) {
		       			if (splitSnp[morInd[f]].charAt(0) != 'N') {
						allMorN = false; 
						if (splitSnp[morInd[f]].charAt(0) != refBase && splitSnp[morInd[f]].charAt(0) != altBase) {
       							multAll = true;
						}
		       			}
		       		}
		       		if (mism && !allMorN && !multAll && nonN > 0.5*(double)(focusInd.length) ) {
			       		// Get the consensus snp in cvi
		       			char[] bases = {'A', 'C', 'G', 'T'};
		       			int[] basInt = {0, 0, 0, 0};
			       		
		       			for (int f=0; f<focusInd.length; f++) {
		       				for (int c=0; c<bases.length; c++) {
		       					if (splitSnp[focusInd[f]].charAt(0) == bases[c]) {
		       						basInt[c] = basInt[c] + 1;
		       					}
		       				}
		       			}
					char[] basesM = {'A', 'C', 'G', 'T'};
		       			int[] basIntM = {0, 0, 0, 0};
			       		
		       			for (int f=0; f<morInd.length; f++) {
		       				for (int c=0; c<bases.length; c++) {
		       					if (splitSnp[morInd[f]].charAt(0) == bases[c]) {
		       						basIntM[c] = basIntM[c] + 1;
		       					}
		       				}
		       			}

			       		//if (snps == 467 || snps == 468 || snps == 469 ) { //(chr == 1 && (pos == 3487274)) {
					//	System.out.println(Arrays.toString(basInt) + " " + Arrays.toString(basIntM));
					//}

					snps = snps + 1;
		       		}
	       		}
			}
			
			
			// Get char of consensus cvis
       		char[] cviC = new char[snps];
       		
       		// Now the donors moroccans
       		char[][] morC = new char[mor.length][snps];
       		
			
			// Open the big matrix
	    	matrix = new File(matrixName);
	    	scannerMatrix = new Scanner(matrix);
			
	       	// Go with contents
	    	
	       	scannerMatrix.nextLine();
	       	snps = 0;
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	char refBase = splitSnp[2].charAt(0);
		       	char altBase = 'N';
		       	int chr = Integer.parseInt(splitSnp[0]);
	       		int pos = Integer.parseInt(splitSnp[1]);
	       		
	       		if (chr == chromosome) {
	       		// Create mach input
	       			Boolean mism = false;
				int nonN = 0;
		       		Boolean multAll = false;
		       		for (int f=0; f<focusInd.length; f++) {
		       			if (splitSnp[focusInd[f]].charAt(0) != 'N') {
						nonN = nonN + 1;
						if (splitSnp[focusInd[f]].charAt(0) != refBase) {	
							mism = true;
		       					if (altBase == 'N') {
		       						altBase = splitSnp[focusInd[f]].charAt(0);
		       					} else {
		       						if (altBase != splitSnp[focusInd[f]].charAt(0)) {
		       							multAll = true;
		       						}
		       					}
						}
		       			}
		       		}
				boolean allMorN = true;
		       		for (int f=0; f<morInd.length; f++) {
		       			if (splitSnp[morInd[f]].charAt(0) != 'N') {
						allMorN = false;
						if (splitSnp[morInd[f]].charAt(0) != refBase && splitSnp[morInd[f]].charAt(0) != altBase) {
       							multAll = true;
						}
		       			}
		       		}
		       		if (mism && !allMorN && !multAll && nonN > 0.5*(double)(focusInd.length) ) {
		       			// Get the consensus snp in cvi
		       			char[] bases = {'A', 'C', 'G', 'T'};
		       			int[] basInt = {0, 0, 0, 0};
			       		
		       			for (int f=0; f<focusInd.length; f++) {
		       				for (int c=0; c<bases.length; c++) {
		       					if (splitSnp[focusInd[f]].charAt(0) == bases[c]) {
		       						basInt[c] = basInt[c] + 1;
		       					}
		       				}
		       				int max=0;
		       				for (int c=0; c<bases.length; c++) {
		       					if (max < basInt[c]) {
		       						cviC[snps] = bases[c];
		       						max = basInt[c];
		       					}
		       				}
		       			}
					
					// Dat file
			       		out1.print("M" + "\t" + chr + "_" + pos + "\n");
			       		out3.print(chr + "_" + pos + "\n");
			       		
			       		// Care of Moroccans
				       	int[] consMor = {0, 0, 0, 0};
			       		for (int f=0; f<morInd.length; f++) {
			       			if (splitSnp[morInd[f]].charAt(0) != 'N') {
			       				morC[f][snps] = splitSnp[morInd[f]].charAt(0);
			       				for (int b=0; b<bases.length; b++) {
			       					if (morC[f][snps] == bases[b]) {
			       						consMor[b] = consMor[b] + 1;
			       					}
			       				}
			       			} else {
			       				morC[f][snps] = '.';
			       			}
			       		}




			       		/*
			       		int max=-1;
			       		int indMax = 0;
			       		for (int b=0; b<bases.length; b++) {
			       			if (consMor[b] > max) {
			       				max = consMor[b];
			       				indMax = b;
			       			}
			       		}
			       		
			       		for (int f=0; f<morInd.length; f++) {
			       			if (morC[f][snps] == '.') {
			       				morC[f][snps] = bases[indMax];
			       			}
			       		}
			       		*/
			       		snps = snps + 1;
			       	}
	       		}
			}
       		out1.close();
       		out3.close();
       		
       		
			
			for (int cut=0;cut<10; cut++) {
				Writer writer = new FileWriter(results + "_pedForMach_" + cut + ".txt");
				PrintWriter out = new PrintWriter(writer);
				
				out.print(cut + cut + cut + "\t" + cut + cut + cut + "\t" + "0" + "\t" + "0" + "\t" + "0" + "\t");
				for (int c=0; c<cviC.length; c++) {
					if (c % 10 == cut) {
			       			out.print("NA NA" + "\t");
					} else {
						out.print(cviC[c] + " " + cviC[c] + "\t");
					}
				}
	   			out.print("\n");
       				out.close();
			}
   			
       		
       		
       		
			// Output separate mor files for imputation within mor pops
       		

			String[] ha = {"22010", "22009", "22003", "18516", "18511", "18513", "35523", "35598", "35595", "35596", "35601", "35602", "35604", "35606", "35613", "35616"};
			String[] sma = {"22006", "22004", "22002", "18514", "18512", "18510", "37468", "37469", "35513", "35594", "35599", "35600", "35625", "35622", "35607", "35608", "35610", "35611", "35615", "35618", "35619", "35621", "35605", "35623"};
			String[] nma = {"22011", "22005", "22001", "18509", "22000", "35620", "35624", "37472", "35521", "35522", "35593", "35603", "35609", "35612"};
			String[] rif = {"18515", "35614", "22007", "21999", "35617", "22008", "35512"};
			
			
			for (int m=0; m<5; m++) {
				String[] mf = null;
				if (m == 0) {
					mf = ha;
				} else {
					if (m == 1) {
						mf = sma;
					} else {
						if (m == 2) {
							mf = nma;
						} else {
							if (m == 3) {
								mf = rif;
							} else {
								if (m == 4) {
									mf = can;
								}
							}
						}
					}
				}
				Writer writer2 = new FileWriter(results + "_mor" + m + "_toImpute.txt");
				PrintWriter out2 = new PrintWriter(writer2);
				
	       			for (int f=0; f<morInd.length; f++) {
	       				boolean in = false;
	       				for (int m2=0; m2<mf.length; m2++) {
	       					if (mor[f].equals(mf[m2])) {
	       						in = true;
	       					}
	       				}
	       				if (in) {
		       				out2.print(mor[f] + "\t" + mor[f] + "\t" + "0" + "\t" + "0" + "\t" + "0" + "\t");
		       				for (int s=0; s<morC[f].length; s++) {
		       					if (morC[f][s] != '.') {
		       						out2.print(morC[f][s] + " " + morC[f][s] + "\t");
		       					} else {
		       						out2.print("NA NA" + "\t");
		       					}
		       				}
		       			out2.print("\n");
	       				}
	       			}
	       		out2.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Mach_input mach_input = new Mach_input();
		mach_input.setFileToSubSet(args[0], args[1], args[2]);
	}
}
