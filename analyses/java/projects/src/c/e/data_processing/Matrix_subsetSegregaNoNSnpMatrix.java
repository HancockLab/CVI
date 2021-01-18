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

public class Matrix_subsetSegregaNoNSnpMatrix {
	public Matrix_subsetSegregaNoNSnpMatrix() {}
	
	public void setMatrixFile(String matrixName, String sampleFile, String tag){
		
		try {
			Writer writer = new FileWriter(matrixName + "_" + tag + "_polyNoN.txt");
			PrintWriter out = new PrintWriter(writer);
			
			Writer writerW = new FileWriter(matrixName + "_" + tag + "_genoWihs_polyNoN.txt");
			PrintWriter outW = new PrintWriter(writerW);
			
			Writer writerP = new FileWriter(matrixName + "_" + tag + "_posWihs_polyNoN.txt");
			PrintWriter outP = new PrintWriter(writerP);
			
			Writer writerN = new FileWriter(matrixName + "_" + tag + "_namesWihs_polyNoN.txt");
			PrintWriter outN = new PrintWriter(writerN);
			
			File fileNames = new File(sampleFile);
			Scanner scanNames = new Scanner(fileNames);
			
			int samples = 0;
			while (scanNames.hasNextLine()) {
				String name = scanNames.nextLine();
				samples = samples + 1;
			}
			String[] namesIn = new String[samples];
			
			samples = 0;
			scanNames = new Scanner(fileNames);
			while (scanNames.hasNextLine()) {
				String name = scanNames.nextLine();
				String[] splitName = name.split("\t");
				namesIn[samples] = splitName[0];
				samples = samples + 1;
			}
			System.out.println(namesIn.length);
			System.out.println(Arrays.toString(namesIn));
			
			
			
			// Open the big matrix
	    		File matrix = new File(matrixName);
	    		Scanner scannerMatrix = new Scanner(matrix);
	    	
			// Get header
			//
	    	   	String head = scannerMatrix.nextLine();
	       		String[] splitIDs = head.split("\t");
	       		System.out.println(splitIDs.length);
			System.out.println(Arrays.toString(splitIDs));
			
			
			int[] indexesToGet = new int[namesIn.length];
		       	int run = 0;
	       		
			for (int n=0; n<namesIn.length; n++) {
				for (int h=3; h<splitIDs.length; h++) {
	       				if (splitIDs[h].equals(namesIn[n])) {
	       					indexesToGet[run] = h;
	       					run = run + 1;
	       				}
	      	 		}
	       		}
			System.out.println(Arrays.toString(indexesToGet));
	       	
	       	
	       		// Go with contents
			// Open
			//
	    		matrix = new File(matrixName);
	    		scannerMatrix = new Scanner(matrix);
	    		int row = 0;
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				boolean n = false;
				boolean segrega = false;
				for (int i=0; i<indexesToGet.length; i++) {
					if (splitSnp[indexesToGet[i]].charAt(0) == 'N') {
						n = true;
					}
					if (splitSnp[indexesToGet[i]].charAt(0) != splitSnp[indexesToGet[0]].charAt(0)) {
						segrega = true;
					}
				}

				if (!n && segrega) {
			       		out.print(splitSnp[0] + "\t" + splitSnp[1] + "\t" + splitSnp[2]); 
			       		if (row > 0) {
						outP.print(splitSnp[0] + "\t" + splitSnp[1] + "\n");
					}
					for (int i=0; i<indexesToGet.length; i++) {
		       				out.print("\t" + splitSnp[indexesToGet[i]]);
						if (row > 0) {
							// But header, print out genotypes
							if (splitSnp[indexesToGet[i]].charAt(0) == splitSnp[2].charAt(0) ) {
								outW.print("0");
								// outW.print(splitSnp[indexesToGet[i]]);
							} else {
								outW.print("1");
							}
		       					if (i < indexesToGet.length-1) {
								outW.print("\t");
							}
						} else {
							// Print out sample names
							outN.print(splitSnp[indexesToGet[i]] + "\n");
						}
					}
		       			out.print("\n");
					if (row > 0) {
						outW.print("\n");
					}
				}
				row = row + 1;
				
			}
			out.close();
			outW.close();
			outP.close();
			outN.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix_subsetSegregaNoNSnpMatrix matrix_subsetSegregaNoNSnpMatrix = new Matrix_subsetSegregaNoNSnpMatrix();
		matrix_subsetSegregaNoNSnpMatrix.setMatrixFile(args[0], args[1], args[2]);
	}
}
