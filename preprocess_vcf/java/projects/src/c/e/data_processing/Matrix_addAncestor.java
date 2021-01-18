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

public class Matrix_addAncestor {
	
	public Matrix_addAncestor() {}
	
	public void setFileToSubSet(String matrixName, String ancFileName){
		
		try {
			
			// Read in ancestral states
			
			BitSet[] ancMask = new BitSet[5];
			for (int c=0; c<5; c++) {
				ancMask[c] = new BitSet();
			}
			
			int snps = 0;
			
			File ancFile = new File(ancFileName);
	    	Scanner scannerAnc = new Scanner(ancFile);
	    	scannerAnc.nextLine();
			while ( scannerAnc.hasNextLine() ) {
				String snp = scannerAnc.nextLine();
		       	snps = snps + 1;
			}
			System.out.println("Snps: " + snps);
			
			int[] chrA = new int[snps];
			int[] posA = new int[snps];
			char[] ancMl = new char[snps];
			char[] ancX = new char[snps];
			char[] ancMach = new char[snps];
			
			snps = 0;
			scannerAnc = new Scanner(ancFile);
	    		scannerAnc.nextLine();
			while ( scannerAnc.hasNextLine() ) {
				String snp = scannerAnc.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       	
		       	chrA[snps] = Integer.parseInt(splitSnp[0]);
		       	posA[snps] = Integer.parseInt(splitSnp[1]);
		       	
		       	ancMask[chrA[snps] - 1].set(posA[snps]);
		       	
		       	ancMl[snps] = splitSnp[2].charAt(0);
		       	ancX[snps] = splitSnp[3].charAt(0);
		       	ancMach[snps] = splitSnp[4].charAt(0);
		       	
		       	snps = snps + 1;
			}
			// System.out.println(Arrays.toString(ancX));
			
			// Ancestor in
			
			
			

			// Open the big matrix
	    	
			File matrix = new File(matrixName);
	    	Scanner scannerMatrix = new Scanner(matrix);
	    	
			Writer writer = new FileWriter(matrixName + "_plusAncXM.txt");
			PrintWriter out = new PrintWriter(writer);
	    	
	       	String head = scannerMatrix.nextLine();
	       	String[] splitHead = head.split("\t");
	       	// Print head
	       	// 
	       	out.print(splitHead[0] + "\t" + splitHead[1] + "\t" + splitHead[2] + "\t" + "ancMl" + "\t" + "ancX" + "\t" + "ancMach");
	       	for (int h=3; h<splitHead.length; h++) {
	       		out.print("\t" + splitHead[h]);
	       	}
       		out.print("\n");	       	
			
       		while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	out.print(splitSnp[0] + "\t" + splitSnp[1] + "\t" + splitSnp[2]);
		       	
		       	int chr = Integer.parseInt(splitSnp[0]) - 1;
		       	int pos = Integer.parseInt(splitSnp[1]);
		       	
		       	if (ancMask[chr].get(pos)) {
		       		//
		       		// find the ancestral base
		       		for (int a=0; a<chrA.length; a++) {
		       			if (chrA[a] == chr +1 && posA[a] == pos) {
		       				//
		       				// Out ancestral bases
		       				
		       				if (ancMl[a] == 'A' || ancMl[a] == 'C' || ancMl[a] == 'G' || ancMl[a] == 'T') {
		       					out.print("\t" + ancMl[a]);
		       				} else {
		       					out.print("\t" + "N");
		       				}
		       				if (ancX[a] == 'A' || ancX[a] == 'C' || ancX[a] == 'G' || ancX[a] == 'T') {
		       					out.print("\t" + ancX[a]);
		       				} else {
		       					out.print("\t" + "N");
		       				}
		       				if (ancMach[a] == 'A' || ancMach[a] == 'C' || ancMach[a] == 'G' || ancMach[a] == 'T') {
		       					out.print("\t" + ancMach[a]);
		       				} else {
		       					out.print("\t" + "N");
		       				}
		       			}
		       		}
		       	} else {
		       		for (int a=0; a<3; a++) {
		       			// Ancestor is reference base
		       			out.print("\t" + splitSnp[2]);
		       		}
		       	}
		       	// Output all samples
		       	for (int s=3; s<splitSnp.length; s++) {
		       		out.print("\t" + splitSnp[s]);
		       	}
		       	out.print("\n");
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Matrix_addAncestor matrix_addAncestor = new Matrix_addAncestor();
		matrix_addAncestor.setFileToSubSet(args[0], args[1]);
	}
}
