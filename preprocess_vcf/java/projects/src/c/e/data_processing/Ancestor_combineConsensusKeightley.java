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
			
			// Charge Keightley files (3)
			
			
			// File[] kFiles = {
			//		new File("/Volumes/CVI/final/cvi/polariz/outSanto20n_k_pVal.txt"), 
			//		new File("/Volumes/CVI/final/cvi/polariz/outSanto20n_jc_pVal.txt"), 
			//		new File("/Volumes/CVI/final/cvi/polariz/outSanto20n_r6_pVal.txt")
			// };
			// Scanner scannerK = new Scanner(kFiles[0]);
			
	    	// int snps = 0;
	    	// while ( scannerK.hasNextLine() ) {
		//		String snp = scannerK.nextLine();
		       	// header out
		//		if (snp.charAt(0) != '0') {
		//			snps = snps + 1;
		//       	}
		//    }
	    	// System.out.println("Snps: " + snps);
	    	
			/*
	    	int[] n = new int[3];
	    	char[][] ancBases = new char[3][snps];
	    	char[] st = {'A', 'A', 'A', 'A', 'C', 'C', 'C', 'C', 'G', 'G', 'G', 'G', 'T', 'T', 'T', 'T'};
	    	
			for (int f=0; f<kFiles.length; f++) {
				scannerK = new Scanner(kFiles[f]);
		    	snps = 0;
		    	
		    	while ( scannerK.hasNextLine() ) {
					String snp = scannerK.nextLine();
			       	// header out
					if (snp.charAt(0) != '0') {
			       		String[] splitSnp = snp.split(" ");
				    	//System.out.println(Arrays.toString(splitSnp));
			       		
			       		boolean clear = false;
			       		for (int states=3; states<splitSnp.length; states++) {
			       			if (Double.parseDouble(splitSnp[states]) > 0.70) {
			       				ancBases[f][snps] = st[states-3];
			       				clear = true;
			       			}
			       		}
			       		if (!clear) {
			       			if (Double.parseDouble(splitSnp[2]) > 0.95) {
			       				ancBases[f][snps] = 'N';
			       				n[f] = n[f] + 1;
			    		    	// System.out.println(Arrays.toString(splitSnp));
			       			} else {
			       				ancBases[f][snps] = 'N';
			       				n[f] = n[f] + 1;
			       			}
		       			}
			       		snps = snps + 1;
			       	}
			    }
		    	System.out.println("Read file " + f);
		    	System.out.println(ancBases[0][ancBases[0].length-1]);
			}
			*/
			
	    	
			
			
			File matrix = new File(matrixName);
	    	Scanner scannerMatrix = new Scanner(matrix);

			Writer writer = new FileWriter(matrixName + "_mlK.txt");
			PrintWriter out = new PrintWriter(writer);
			
			// Go with contents
	    	String head = scannerMatrix.nextLine();
	    	out.print(head + "\t" + "ml_k2" + "\t" + "ml_jc" + "\t" + "ml_r6" + "\t" + "XconsMor" + "\t" + "XconsCan" + "\t" + "XconsMC" + "\n");
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
       			// Check that HA did not have too many Ns - else, consider highest freq. base in can and mor
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
		       		out.print(splitSnp[r] + "\t");
		       	}
		       	// Out with Keightley
		       	out.print("-" + "\t" + "-" + "\t" + "-" + "\t");
		       	/*
		       	for (int r=0; r<ancBases.length; r++) {
		       		if (ancBases[r][snps] == 'H') {
		       			out.print(bases[cviIndex] + "\t");
		       		} else {
		       			out.print(ancBases[r][snps] + "\t");
			       	}
		       	}
		       	*/
       			
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
		       		out.print(bases[cviIndex] + "\t");
		       	} else {
		       		morX[0] = morX[0] + 1;
		       		out.print(bases[haIndex] + "\t");
		       	}
		       	
		       	
		       	////
		       	//				Same for can
		       	////
		       	
		       	Boolean thereInCan = false;
		       	int m=7;
	       		String[] can = splitSnp[m].split(",");
   				if (Integer.parseInt(can[cviIndex]) > 0) {
   					thereInCan = true;
       			}
	       	
		       	if (thereInCan) {
		       		canX[1] = canX[1] + 1;
		       		out.print(bases[cviIndex] + "\t");
		       	} else {
		       		canX[0] = canX[0] + 1;
		       		out.print(bases[haIndex] + "\t");       	
		       	}
		       	
		       	////
		       	//				Can OR Morocco
		       	////
		       	
		       	if (thereInCan || thereInMor) {
		       		canOrMorX[1] = canOrMorX[1] + 1;
		       		out.print(bases[cviIndex]);
		       	} else {
		       		canOrMorX[0] = canOrMorX[0] + 1;
		       		out.print(bases[haIndex]);
		       	}
		       	
		       	out.print("\n");
		       	snps = snps + 1;
			}
			out.close();
			
			System.out.print("Can: : " + canX[0] + "\t" + canX[1] + "\n");
			System.out.print("Mor: : " + morX[0] + "\t" + morX[1] + "\n");
			System.out.print("Can || Mor: : " + canOrMorX[0] + "\t" + canOrMorX[1] + "\n");
	    	
			
	    	// System.out.println("Ns: " + Arrays.toString(n));
	    	System.out.println("Finished! :)");
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Ancestor_combineConsensusKeightley ancestor_combineConsensusKeightley = new Ancestor_combineConsensusKeightley();
		ancestor_combineConsensusKeightley.setFileToSubSet(args[0]);
		// ancestor_combineConsensusKeightley.setFileToSubSet("/Volumes/CVI/final/cvi/polariz/canWith/estSFS_7-18_ancestralState.txt");
		// ancestor_combineConsensusKeightley.setFileToSubSet("/Volumes/CVI/final/cvi/polariz/canWith/estSFS_6-18_ancestralState.txt");
		// ancestor_combineConsensusKeightley.setFileToSubSet("/Volumes/CVI/final/cvi/polariz/estSFS_santoAnc_20_ancestralState.txt");
		// ancestor_combineConsensusKeightley.setFileToSubSet("/Volumes/CVI/final/cvi/polariz/estSFS_fogoAnc_10_ancestralState.txt");
	}
}
