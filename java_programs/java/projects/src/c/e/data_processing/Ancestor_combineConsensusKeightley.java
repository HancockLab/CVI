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

public class Ancestor_combineConsensusKeightley {
	
	public Ancestor_combineConsensusKeightley() {}
	
	public void setFileToSubSet(String matrixName){
		
		try {
			
			int[] canX = {0, 0};
			int[] morX = {0, 0};
			int[] canOrMorX = {0, 0};
			
			char[] bases = {'A', 'C', 'G', 'T'};
			
			
			File matrix = new File(matrixName);
	    		Scanner scannerMatrix = new Scanner(matrix);

			Writer writer = new FileWriter(matrixName + "_mlK.txt");
			PrintWriter out = new PrintWriter(writer);
			
			// Go with contents
		    	String head = scannerMatrix.nextLine();
		    	out.print(head + "\t-\t" + "XconsMor" + "\t-\n");
	    		int snps = 0;
	    	
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
			       	String[] splitSnp = snp.split("\t");
		       	
			       	// Get Cvi high frequency base
			       	//
			       	String[] cvi = splitSnp[2].split(",");
       				int cviIndex = 0;
       				int max = 0;
       				for (int b=0; b<cvi.length; b++) {
       					if (max < Integer.parseInt(cvi[b])) {
       						cviIndex = b;
       						max = Integer.parseInt(cvi[b]);
       					}
       				}
       				// Get HA high frequency base
		       		//
       				int nonNha = 0;
			       	String[] ha = splitSnp[3].split(",");
       				int haIndex = 0;
     	  			int maxHa = 0;
       				for (int b=0; b<ha.length; b++) {
       					nonNha = nonNha + Integer.parseInt(ha[b]);
       					if (maxHa < Integer.parseInt(ha[b])) {
       						haIndex = b;
       						maxHa = Integer.parseInt(ha[b]);
       					}
       				}
	       			// Check that HA did not have too many Ns - else, consider highest freq. base in mor
       				//
       				if (nonNha < 10) {
       					int[] mor = {0, 0, 0, 0, 0};
       					for (int m=3; m<8; m++) {
       						String[] morS = splitSnp[m].split(",");
   			            		for (int b=0; b<morS.length; b++) {
           						mor[b] = mor[b] + Integer.parseInt(morS[b]);
           					}
       					}
           				haIndex = 0;
        	   			maxHa = 0;
  	         			for (int b=0; b<mor.length; b++) {
           					if (maxHa < mor[b]) {
           						haIndex = b;
           						maxHa = mor[b];
           					}
    		       			}
       				}
       			
       				// Out what was before already in the file
			       	for (int r=0; r<splitSnp.length; r++) {
			       		out.print(splitSnp[r] + "\t-\t");
			       	}
       			
       				////
			       	// 				Output the extra conservative polarization: if the high freq cvi base appears at all in morocco, call it ancestral
		       		////
       			
		       		boolean thereInMor = false;
		       		for (int m=3; m<7; m++) {
		       			String[] mor = splitSnp[m].split(",");
       					if (Integer.parseInt(mor[cviIndex]) > 0) {
       						thereInMor = true;
	       				}
		       		}
		       		if (thereInMor) {
		       			morX[1] = morX[1] + 1;
		       			out.print(bases[cviIndex] + "\t-");
		       		} else {
		       			morX[0] = morX[0] + 1;
		       			out.print(bases[haIndex] + "\t-");
		      	 	}
		       		out.print("\n");
		       		snps = snps + 1;
			}
			out.close();
			
			
	    	System.out.println("Finished! :)");
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Ancestor_combineConsensusKeightley ancestor_combineConsensusKeightley = new Ancestor_combineConsensusKeightley();
		ancestor_combineConsensusKeightley.setFileToSubSet(args[0]);
	}
}
