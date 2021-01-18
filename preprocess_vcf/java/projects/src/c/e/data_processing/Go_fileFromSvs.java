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

public class Go_fileFromSvs {
	
	public Go_fileFromSvs() {}
	
	public void setFileToSubSet(String matrixName, String sampleFile){
		
		try {
			
			// get clean samples from a file

			File sFile = new File(sampleFile);
			Scanner scannerS = new Scanner(sFile);
	    		int fil = 0;
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		fil = fil + 1;
			}
			fil = fil -1;
			System.out.println("files: " + fil);
			String[] pop1 = new String[fil];

			fil= 0;
			scannerS = new Scanner(sFile);
			while ( scannerS.hasNextLine() ) {
				String snp = scannerS.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		if (fil < pop1.length) {
					pop1[fil] = splitSnp[0];
					fil = fil + 1;
				}
			}
			System.out.println(Arrays.toString(pop1));
			int[] p1ind = new int[pop1.length];
			String[] splitIDs = null;



			BitSet[] mask =new BitSet[5];
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			
			File qtlFile= new File(matrixName);
		    	Scanner scanner = new Scanner(qtlFile);
			
			while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       	
		       	if (snp.charAt(0) == '#') {
				// Header
				if (snp.charAt(1) != '#') {
					// read header

					////
					//	Get indexes
					////
			    		splitIDs = splitSnp;
		    			
	    				// Indexes for pop1
					for (int f =0; f<p1ind.length; f++) {
	            				Boolean foundIt = false;
						for (int s=2; s<splitIDs.length; s++) {
                					if (splitIDs[s].equals(pop1[f])) {
                    						p1ind[f] = s;
								foundIt = true;
                 		   			}
                				}
						if (!foundIt) {
							System.out.println("Sample not found: " + pop1[f]);
						}
					}
					System.out.println(Arrays.toString(p1ind));
				}
			} else {

			       	int chr = Integer.parseInt(splitSnp[0]);
			       	int posStart = Integer.parseInt(splitSnp[1]);
			       	int posEnd = Integer.parseInt(splitSnp[7].split(";")[7].split("=")[1]);
			     	
				// Is it really a mismatch?
				Boolean mism = false;
			       	for (int i=0; i<p1ind.length; i++) {
					if (splitSnp[p1ind[i]].charAt(0) == '1') {
						mism = true;
					}
				}
				if (mism) {
			       		for (int p=posStart; p<=posEnd; p++) {
			       			mask[chr - 1].set(p);
			       		}
				}
		       	}
			}
			
			// Out
			
			int[] length = {30427671, 19698289, 23459830, 18585056, 26975502};
			
	    		Writer writer = new FileWriter(sampleFile + "_forGo.txt");
			PrintWriter out = new PrintWriter(writer);

	    		Writer writerWg = new FileWriter(sampleFile + "_forGoWg.txt");
			PrintWriter outWg = new PrintWriter(writerWg);
			
			int card = 0;
			for (int c=0; c<5; c++) {
				card = card + mask[c].cardinality();
				for (int p=0; p<=mask[c].length(); p++) {
		       		if (mask[c].get(p)) {
		       			out.print("Chr" + (c+1) + "\t" + p + "\n"); 
		       		}
		       	}
				for (int p=0; p<=length[c]; p++) {
					outWg.print("Chr" + (c+1) + "\t" + p + "\n");
				}
			}
			System.out.println("Card: " + card);
	    		out.close();
	    		outWg.close();
	    	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Go_fileFromSvs go_fileFromSvs = new Go_fileFromSvs();
		go_fileFromSvs.setFileToSubSet(args[0], args[1]);
	}
}
