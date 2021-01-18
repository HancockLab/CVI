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

public class ErrorsInRealData {
	public ErrorsInRealData() {}
	
	public void setFile(String fileName){
		
		try {
			BitSet[] err = new BitSet[5];
			for (int c=0; c<5; c++) {
				err[c] = new BitSet();
			}
			
			// Open errors file
			File errorFold = new File("/global/lv70590/Andrea/analyses/sharedStuff/newRun/shore/all/");
			File[] errFiles = errorFold.listFiles();
			
			for (int f=0; f<errFiles.length; f++) {
				File errorFile = errFiles[f];
				if (errorFile.getName().split("\\.")[errorFile.getName().split("\\.").length - 1].equals("vcf")) {
					Scanner scanE = new Scanner(errorFile);
					
					while (scanE.hasNextLine()) {
						String line = scanE.nextLine();
						if (line.charAt(0) != '#') {
							String[] splLine = line.split("\t");
							if (splLine[0].charAt(0) == 'C' && splLine[0].charAt(1) == 'h' && splLine[0].charAt(2) == 'r' && splLine[6].equals("PASS") ) {
								int chr = Character.getNumericValue(splLine[0].charAt(3)) - 1;
								int pos = Integer.parseInt(splLine[1]);
								err[chr].set(pos);
							}
						}
					}
				}
			}
			int errors = 0;
			for (int c=0; c<5; c++) {
				errors = errors + err[c].cardinality();
			}
			System.out.println("Errors: " + errors);
			
		
			String results = "/global/lv70590/Andrea/analyses/sharedStuff/errorsInRealData/";

			Writer writer = new FileWriter(results + "errorFreq_pass.txt");
			PrintWriter out = new PrintWriter(writer);
			
			Writer writer2 = new FileWriter(results + "segregErrors_pass.txt");
			PrintWriter out2 = new PrintWriter(writer2);
			
			
			// If SNP matrix:
			/*
			// Open matrix file
			File matFile = new File(fileName);
			Scanner scan = new Scanner(matFile);
			scan.nextLine();
			int chrMem = 0;
			
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] splLine = line.split("\t");
				int chr = Integer.parseInt(splLine[0]) - 1;
				int pos = Integer.parseInt(splLine[1]);
				if (chr > chrMem) {
					System.out.println("Chr" + chr);
					chrMem = chr;
				}
				if (err[chr].get(pos)) {
					int[] freq = new int[3];
					char refBase = splLine[2].charAt(0);
					for (int s=3; s<splLine.length; s++) {
						if (splLine[s].charAt(0) != 'N') {
							freq[0] = freq[0] + 1;
							if (splLine[s].charAt(0) != refBase) {
								freq[1] = freq[1] + 1;
							}
						}
					}
					double frequence = (double)freq[1]/(double)freq[0];
					out.print(frequence +"\t" + chr + "\t" + pos + "\t" + freq[0] + "\t" + freq[1] + "\n" );
					if (frequence < 1.0) {
						out2.print("Chr" + chr + "\t" + pos + "\n");
					}
				}
			}
			*/
			
			
			// If 1001 vcf:
			
			// Open vcf file
			File matFile = new File(fileName);
			Scanner scan = new Scanner(matFile);
			int chrMem = 0;
			
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (line.charAt(0) != '#') {
					String[] splLine = line.split("\t");
					int chr = Integer.parseInt(splLine[0]) - 1;
					int pos = Integer.parseInt(splLine[1]);
					if (chr > chrMem) {
						System.out.println("Chr" + chr);
						chrMem = chr;
					}
					if (err[chr].get(pos)) {
						int[] freq = new int[3];
						char refBase = splLine[3].charAt(0);
						for (int s=9; s<splLine.length; s++) {
							
							// See if there is any reasonable call - from VcfCombined_to_SnpMatrix.java
			    			if ( (splLine[s].split(":")[0].charAt(0) != '.') && (splLine[s].split(":")[1].charAt(0) != '.') && (splLine[s].split(":")[2].charAt(0) != '.') ){
			    				
			    				// Check coverage and quality
			    				int cov=Integer.parseInt(splLine[s].split(":")[2]);
			    				int qual=Integer.parseInt(splLine[s].split(":")[1]);
			    				
			    				if ( (cov >=2) && (qual >= 17) ) {
									freq[0] = freq[0] + 2;
			    					if (Integer.parseInt(splLine[s].split(":")[0].split("\\|")[0]) != 0) {
										freq[1] = freq[1] + 1;
			    					}
			    					if (Integer.parseInt(splLine[s].split(":")[0].split("\\|")[1]) != 0) {
										freq[1] = freq[1] + 1;
			    					}
			    				}
			    			}
						}
						double frequence = (double)freq[1]/(double)freq[0];
						out.print(frequence +"\t" + chr + "\t" + pos + "\t" + freq[0] + "\t" + freq[1] + "\n" );
						if (frequence < 1.0 && frequence > 0.0) {
							out2.print("Chr" + chr + "\t" + pos + "\n");
						}
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
		ErrorsInRealData errorsInRealData = new ErrorsInRealData();
		errorsInRealData.setFile(args[0]);
	}
}
