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

public class Matrix_noNsnpsOnly {
	
	public Matrix_noNsnpsOnly() {}
	
	public void setFileToSubSet(String matrixName){
		
		try {
			System.out.println("src/c/e/data_processing/Matrix_snpsOnly.java");
			
		    	// Begin with the big matrix
		    	File matrix = new File(matrixName);
	    		Scanner scannerMatrix = new Scanner(matrix);
			
			// Open also the writing file
			Writer writer = new FileWriter(matrixName + "_noNsnpsOnly.txt");
			PrintWriter out = new PrintWriter(writer);
			
			// Print header
			//if (chromosome.charAt(0) == '1') {
				String head = scannerMatrix.nextLine();
		       		out.println(head);
			//}
			
			// Go with contents
		       	int snps = 0;
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
			       	String[] splitSnp = snp.split("\t");
		       		
		 	      	// Get reference base 
		  		char refBase = splitSnp[2].charAt(0);
				
			       	// Select segregating sites
			       	boolean segrega = false;
			       	boolean n = false;
				for (int sub=3; sub<splitSnp.length; sub++) {
			       		if ( (splitSnp[sub].charAt(0) != 'N') ) {
						if ( (splitSnp[sub].charAt(0) != refBase) ) {
			       				segrega = true;
						}
			       		} else {
						n = true;
					}
			       	}
				
			      	// If it is segregating, output
			       	if (segrega && !n) {
					out.print(snp + "\n");
				      	snps = snps + 1;
	       			}	
			}
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix_noNsnpsOnly matrix_noNsnpsOnly = new Matrix_noNsnpsOnly();
	        matrix_noNsnpsOnly.setFileToSubSet(args[0]);
	}
}
