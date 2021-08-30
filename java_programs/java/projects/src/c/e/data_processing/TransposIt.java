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

public class TransposIt {
	
	private File fileMatrix = null;
	private String lineMatrix=null;
	private BitSet[] mask = null;
	private String snp = null;
	private String[] splitSnp = null;
	private int numLines = 0;


	private TransposIt() {}

	public void setSnpFile(String filename){
		
		
		try {
			
			// Charge the matrix
			// How many lines?
			System.out.println("Counting...");
			
			
			//File folder = new File(filename);
			//File[] fileAll = folder.listFiles();
			//System.out.println(fileMatrix);
			
			
			// for (int f=0; f<fileAll.length; f++) {
				
				// fileMatrix = fileAll[f];
				
			fileMatrix = new File(filename);
				
				
				
				
				
				
				Scanner scannerMatrix = new Scanner(fileMatrix);
				numLines = 0;
				
				while ( scannerMatrix.hasNextLine() ) {
					
					String snp1 = scannerMatrix.nextLine();
		        	numLines = numLines +1;
					/*
					if (numLines == 0) {
						String snp1 = scannerMatrix.nextLine();
			        	String[] splitSnp1 = snp1.split("");
			        	
						System.out.println(splitSnp1.length);
					}
					*/
					// System.out.println("numLines: " + numLines);
				}
				// System.out.println("numLines: " + numLines);
				
				
				mask =new BitSet[numLines];
				
				for (int c=0; c<numLines; c++) {
					mask[c]= new BitSet();
				}
				
				// If you have to put together files with single lines....
				
				/*for (int f = 0; f<numLines; f++) {
					
					fileMatrix = new File(filename + f + ".txt");
					scannerMatrix = new Scanner(fileMatrix);
					
					while ( scannerMatrix.hasNextLine() ) {
						
			        	lineMatrix = scannerMatrix.nextLine();
			        	
			        	for (int i=1; i<lineMatrix.length(); i++) {
			        		if (lineMatrix.charAt(i) == ('2')) {
			        			mask[f].set(i);
			        		}
			        	}
					}
				}
				*/
				
				
				// Charge the matrix again
				System.out.println("Charging...");
				
	        	scannerMatrix = new Scanner(fileMatrix);
	        	int line = 0; 
	        	int rows = 0;
			while ( scannerMatrix.hasNextLine() ) {
				lineMatrix = scannerMatrix.nextLine();		        	
		        	String[] splitSnp1 = lineMatrix.split("");
				rows = splitSnp1.length;

				for (int i=0; i<splitSnp1.length; i++) {
		        		if (splitSnp1[i].charAt(0) == ('1')) {
		        			// System.out.print('2');
		        			mask[line].set(i);
		        		} else {
		        			// System.out.print('0');
		        		}
	        			// System.out.print("/n");
		        	}
		        	// System.out.println(lineMatrix.length());
		        	line = line + 1;
			}
				
				
				
				
				// We have the matrix, let's transposeIt!
				
				System.out.println("Writing...");
				System.out.println("rows: " + rows + "; lines: " + numLines);

				Writer writerAdm = new FileWriter(fileMatrix.getAbsolutePath() + ".transp.geno");
				PrintWriter outAdm = new PrintWriter(writerAdm);
				
				for (int bit =0; bit<rows; bit++) {
					for (int plant=0; plant < numLines; plant++) {
						if (mask[plant].get(bit)) {
							outAdm.print("1");
						}
						if (!mask[plant].get(bit)) {
							outAdm.print("0");
						}
						// if (plant < numLines-1) {
						// 	outAdm.print("\t");
						//}
					}
					outAdm.print("\n");
				}
				outAdm.close();
				
				
			// }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		TransposIt transposIt = new TransposIt();
		transposIt.setSnpFile(args[0]);
	}
}
