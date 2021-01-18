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

public class DelimiterChange {
	
	private File fileMatrix = null;
	private String lineMatrix=null;
	private BitSet[] mask = null;
	private String snp = null;
	private String[] splitSnp = null;
	private int numLines = 0;


	private DelimiterChange() {}

	public void setSnpFile(String filename){
		
		
		try {
			
			// Charge the matrix
			// How many lines?
			System.out.println("Counting...");
			
			fileMatrix = new File(filename);
			Scanner scannerMatrix = new Scanner(fileMatrix);
			numLines = 0;
			
			while ( scannerMatrix.hasNextLine() ) {
				
				String snp1 = scannerMatrix.nextLine();
		        	numLines = numLines +1;
			}
			// System.out.println("numLines: " + numLines);
				
				
			mask =new BitSet[numLines];
			
			for (int c=0; c<numLines; c++) {
				mask[c]= new BitSet();
			}
				
			// Charge the matrix again
			System.out.println("Charging...");
				
	        	scannerMatrix = new Scanner(fileMatrix);
	        	int line = 0; 
	        	int rows = 0;
			while ( scannerMatrix.hasNextLine() ) {
				lineMatrix = scannerMatrix.nextLine();		        	
		        	String[] splitSnp1 = lineMatrix.split("\t");
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
				
				
				
				
			// We have the matrix, write out again!
			
			System.out.println("Writing...");
			
			Writer writerAdm = new FileWriter(fileMatrix.getAbsolutePath() + ".newDelim.txt");
			PrintWriter outAdm = new PrintWriter(writerAdm);
			
			for (int plant=0; plant < numLines; plant++) {
				for (int bit =0; bit<rows; bit++) {
					if (mask[plant].get(bit)) {
						outAdm.print("1");
					}
					if (!mask[plant].get(bit)) {
						outAdm.print("0");
					}
					if (bit < rows-1) {
						outAdm.print(" ");
					}
				}
				outAdm.print("\n");
			}
			outAdm.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		DelimiterChange delimiterChange = new DelimiterChange();
		delimiterChange.setSnpFile(args[0]);
	}
}
