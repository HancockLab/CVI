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

public class Jsfs_fromVcf_simulaSlim {
	
	public Jsfs_fromVcf_simulaSlim() {}
	
	public void setMatrixFile(String matrixName, String resultsPart, String samplesSanto, String samplesFogo) {
		
		try {
			////
			// 	Colonisers in ${vcf}_colonisers.vcf
			////
			//
			// Get vector of positions segregating in the colonisers
			BitSet[] mask = new BitSet[1];
			for (int c=0; c<1; c++) {
				mask[c]= new BitSet();
			}
								
			File matrix_col = new File(matrixName + "_colonisers.vcf");
			Scanner scannerMatrix_col = new Scanner(matrix_col);
			while (scannerMatrix_col.hasNextLine()) {
	 	        	String snp = scannerMatrix_col.nextLine();
	 			String[] splitSnp = snp.split("\t");
				if (snp.charAt(0) != '#') {
					int chr = Integer.parseInt(splitSnp[0]);
					int pos = Integer.parseInt(splitSnp[1]);
					
					mask[chr-1].set(pos);
				}
			}








			double sumFst = 0.0;
			int countsFst = 0;

			String results = resultsPart + "_";
			
			Writer writerLog = new FileWriter(results + "log.txt");
			PrintWriter outLog = new PrintWriter(writerLog);
			outLog.println("src/c/e/data_processing/Pairwise_shore.java");            
			
			// Open IDs in the big matrix
			
			File matrix = new File(matrixName);
			Scanner scannerMatrix = new Scanner(matrix);
			// Header out
			for (int h=0; h<13; h++) {
				scannerMatrix.nextLine();
			}
			String idsUnsplit = scannerMatrix.nextLine();
			String[] IDs = idsUnsplit.split("\t");
			
			
			
			// Get Santos
			
			File fileNamesToGet = new File(samplesSanto);
			Scanner scanNamesTG = new Scanner(fileNamesToGet);
			
			int samplesTG = 0;
			while (scanNamesTG.hasNextLine()) {
				String name = scanNamesTG.nextLine();
				samplesTG = samplesTG + 1;
			}
			String[] samplesToGet = new String[samplesTG];
			
			samplesTG = 0;
			scanNamesTG = new Scanner(fileNamesToGet);
			while (scanNamesTG.hasNextLine()) {
				String name = scanNamesTG.nextLine();
				String[] splitName = name.split("\t");
				samplesToGet[samplesTG] = splitName[0];
				samplesTG = samplesTG + 1;
			}
			outLog.println(Arrays.toString(samplesToGet));
			outLog.println("We have: " + samplesToGet.length + " samples");
			
			
			
			// Get indexes
			
			int[] index1 = new int[samplesToGet.length];
			for (int s=0; s<samplesToGet.length; s++) {
				Boolean thereIs = false;
				for (int i=0; i<IDs.length; i++) {
					if (samplesToGet[s].equals(IDs[i])) {
						thereIs = true;
						index1[s] = i;
					}
				}
				if (!thereIs) {
					outLog.println("There is no " + samplesToGet[s]);
				}
			}
			for (int p=0; p<index1.length; p++) {
				outLog.print(IDs[index1[p]] + "\t");
			}			
			outLog.print("\n");
			// Got indexes
			
			
			
			
			

			// Get Fogoes
			
			File fileNamesToGet2 = new File(samplesFogo);
			
			Scanner scanNamesTG2 = new Scanner(fileNamesToGet2);

			int samplesTG2 = 0;
			while (scanNamesTG2.hasNextLine()) {
				String name = scanNamesTG2.nextLine();
				samplesTG2 = samplesTG2 + 1;
			}
			String[] samplesToGet2 = new String[samplesTG2];
			
			samplesTG2 = 0;
			scanNamesTG2 = new Scanner(fileNamesToGet2);
			while (scanNamesTG2.hasNextLine()) {
				String name = scanNamesTG2.nextLine();
				String[] splitName = name.split("\t");
				samplesToGet2[samplesTG2] = splitName[0];
				samplesTG2 = samplesTG2 + 1;
			}
			outLog.println(Arrays.toString(samplesToGet2));
			outLog.println("We have: " + samplesToGet2.length + " samples");
			

			// Get indexes
			
			int[] index2 = new int[samplesToGet2.length];
			for (int s=0; s<samplesToGet2.length; s++) {
				Boolean thereIs = false;
				for (int i=0; i<IDs.length; i++) {
					if (samplesToGet2[s].equals(IDs[i])) {
						thereIs = true;
						index2[s] = i;
					}
				}
				if (!thereIs) {
					outLog.println("There is no " + samplesToGet[s]);
				}
			}
			for (int p=0; p<index2.length; p++) {
				outLog.print(IDs[index2[p]] + "\t");
			}
			outLog.print("\n");
			
			
			// Got indexes
			
			
			
			
			//
			// Now the real game
			// 
			
			// int[] chrL = {30427671, 19698289, 23459830, 18585056, 26975502};
			
			int[][] jsfs = new int[index1.length + 1][index2.length + 1];
			for (int r=0; r<jsfs.length; r++) {
				for (int c=0; c<jsfs[r].length; c++) {
					jsfs[r][c] = 0;
				}
			}
			
			
			
		// Go ahead, big game:

			
		int chr = 0;
		int chrMem = 0;
		int posMem = 0;

		// Oldgood thing
		matrix = new File(matrixName);
		scannerMatrix = new Scanner(matrix);
		// Header out
		
		int fixFsegS = 0;
		int fixSsegF = 0;
		int fixFnoS = 0;
		int snps = 0;
		int snpsF = 0;
		int fixDer = 0;
		int sharedPolym = 0;
		int sharedWithColonisers = 0;
		
		while ( scannerMatrix.hasNextLine() ) {
			
			String snp = scannerMatrix.nextLine();
			if (snp.charAt(0) != '#') {
				String[] splitSnp = snp.split("\t");
				
				// Just print where we are
    				if (chr != Integer.parseInt(splitSnp[0])) {
    					outLog.println("Chr: " + Integer.parseInt(splitSnp[0]));
		
    				}
    				chr = Integer.parseInt(splitSnp[0]);
    				int pos = Integer.parseInt(splitSnp[1]);
       		 		//
				// Pop1
				int der1 = 0;
        			for (int s1=0; s1<index1.length; s1++) {
					if (splitSnp[index1[s1]].charAt(0) == '1') {
						der1 = der1 + 1;
					}
				}
				// Pop2
				int der2 = 0;
        			for (int s2=0; s2<index2.length; s2++) {
					if (splitSnp[index2[s2]].charAt(0) == '1') {
						der2 = der2 + 1;
					}
				}
				jsfs[der1][der2] = jsfs[der1][der2] + 1;

				////
				//	Build your statistic
				////
				if (der1 == (jsfs.length-1) && (der2 > 0 && der2 < (jsfs[1].length-1) ) ) {
					fixSsegF = fixSsegF + 1;
				}
				if (der2 == (jsfs[1].length-1) && (der1 > 0 && der1 < (jsfs.length-1) ) ) {
					fixFsegS = fixFsegS + 1;
				}
				if (der2 == (jsfs[1].length-1) && der1 == 0) {
					fixFnoS = fixFnoS + 1;
				}
				if (der1 == (jsfs.length-1) && der2 == (jsfs[1].length-1)) {
					fixDer = fixDer + 1;
				}
				if ( (der1 > 0 && der1 < (jsfs.length-1) ) && (der2 > 0 && der2 < (jsfs[1].length-1) ) ) {
					sharedPolym = sharedPolym + 1;
				}
				////
				// 	Find how many SNPs entered the islands with the colonisers
				// 	and are still segregating round
				////
				if (mask[chr-1].get(pos) && (der1 != 0 || der2 != 0) && (der1 < (jsfs.length-1) || der2 < (jsfs[1].length-1) ) ) {
					sharedWithColonisers = sharedWithColonisers + 1;
				}
				snps = snps + 1;
			
				if (der2 != 0) {
					snpsF = snpsF + 1;
				}

				// Do Fst another way: for Slim
				double p1 = (double)der1/(double)index1.length;
				double p2 = (double)der2/(double)index2.length;
				double meanP = 0.5*(p1 + p2);

				double ht = 2*meanP*(1-meanP);
				double hw = p1*(1-p1) + p2*(1-p2);

				double fst = 1 - (hw/ht);

				if (!Double.isNaN(fst)) {
					sumFst = sumFst + fst;
					countsFst = countsFst + 1;
				}
			}
			chrMem = chr;
		}



			// Done
			
			
			
			// Write out
			//
			Writer writer = new FileWriter(results + "jsfs_slim.txt");
			PrintWriter out = new PrintWriter(writer);
			
			for (int r=0; r<jsfs.length; r++) {
				for (int c=0; c<jsfs[r].length; c++) {
					out.print(jsfs[r][c] + "\t");
				}
				out.print("\n");
			}
			out.close();
			
			snps = snps - fixDer;

			Writer writer2 = new FileWriter(results + "jsfs_fixSegrega.txt");
			PrintWriter out2 = new PrintWriter(writer2);
			double stats = (double)((double)fixFsegS/(double)snps) - (double)fixSsegF/(double)snps;
			out2.print(fixSsegF + "\t" + fixFsegS + "\t" + snps + "\t" + (double)fixSsegF/(double)snps + "\t" + (double)fixFsegS/(double)snps + "\t" + stats + "\t" + fixFnoS + "\t" + (double)fixFnoS/(double)snps + "\t" + (double)fixFnoS/(double)snpsF + "\t" + (double)sharedPolym/(double)snps + "\t" + (double)sharedWithColonisers/( (double)snps + (double)mask[0].cardinality() ) + "\t" + (double)sharedWithColonisers/(double)snps + "\t" + sharedWithColonisers + "\t" + mask[0].cardinality() + "\n"); 
			out2.close();

			double meanFst = (double)sumFst/(double)countsFst;
			outLog.print("sumFst: " + sumFst + " countsFst: " + countsFst + " meanFst: " + meanFst + "\n");
			outLog.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Jsfs_fromVcf_simulaSlim jsfs_fromVcf_simulaSlim = new Jsfs_fromVcf_simulaSlim();
		jsfs_fromVcf_simulaSlim.setMatrixFile(args[0], args[1], args[2], args[3]);
	}
}
