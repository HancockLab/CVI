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

public class Build_ChromoPainter_input {
	
	private Build_ChromoPainter_input() {}

	public void setSnpFile(String matrixName, String accessionStr, String iterateStr, String results, String focalSampleFileName){
		
		try {
			int accession = Integer.parseInt(accessionStr);
			int iterate = Integer.parseInt(iterateStr);
			String resultsFolder = results + accession + "/" + iterate + "/";	
	
			// Open writing file
						
			Writer writerLog = new FileWriter(resultsFolder + "log.txt");
			PrintWriter outLog = new PrintWriter(writerLog);
		

			// Build sample list for Chromopainter, 
			// store in <indexesToUse>
		
		
		
			
		
			////
			//		Get clean samples from files
			//		FOCAL samples
			//		And their indexes in the matrix
			////

			File matrix = new File(matrixName);
	    		Scanner scannerMatrix = new Scanner(matrix);
	    	
			String idsUnsplit = scannerMatrix.nextLine();
		    	String[] splitIDs = idsUnsplit.split("\t");
	    	
			File focalSampleFile = new File(focalSampleFileName);
			Scanner scannerFocus = new Scanner(focalSampleFile);
	    		int filF = 0;
			while ( scannerFocus.hasNextLine() ) {
				String snp = scannerFocus.nextLine();
				String[] splitSnp = snp.split("\t");
				for (int s=2; s<splitIDs.length; s++) {
					if (splitIDs[s].equals(splitSnp[0])) {
						filF = filF + 1;
					}
				}
			}
			System.out.println("Focal samples :" + filF);
			String[] focalIds = new String[filF];
			int[] focalInd = new int[filF];

			filF= 0;
			scannerFocus = new Scanner(focalSampleFile);
			while ( scannerFocus.hasNextLine() ) {
				String snp = scannerFocus.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
				for (int s=2; s<splitIDs.length; s++) {
					if (splitIDs[s].equals(splitSnp[0])) {
						focalIds[filF] = splitSnp[0];
						focalInd[filF] = s;
						filF = filF + 1;
					}
				}
			}
			System.out.println(Arrays.toString(focalIds));
			System.out.println(Arrays.toString(focalInd));
			outLog.println("Focal population: " + focalSampleFile + " " + Arrays.toString(focalIds));




	




		
			////
			//		Get clean samples from files 
			//		DONOR populations
			//		And their indexes in the matrix
			////
			int sampleSize = 20;

			String dirFiles = "/srv/biodata/dep_coupland/grp_fulgione/files/thaliana/";
			String[] sampleFiles = {
				"highAtlas.txt",
				"northMiddleAtlas.txt",
				"southMiddleAtlas.txt",
				"riff_noZin.txt",
				"asia_noEspPor.txt",
				"central_europe_noEspPor.txt",
				"germany_noEspPor.txt",
				"italy_balkan_caucasus_noEspPor.txt",
				"north_sweden_noEspPor.txt",
				"south_sweden_noEspPor.txt",
			   	"western_europe_noEspPor.txt"
				// "relict_noEspPor.txt",
				// "spain_noEspPor.txt",
			};

			String[][] popsIds =  new String[sampleFiles.length][];			
			int[][] popsInd = new int[sampleFiles.length][];
			int[][] popsInd_random = new int[sampleFiles.length][];

			for (int pop=0; pop<sampleFiles.length; pop++) {
				File sampleFile = new File(dirFiles + sampleFiles[pop]);
				Scanner scannerS = new Scanner(sampleFile);
	    			int fil = 0;
				while ( scannerS.hasNextLine() ) {
					String snp = scannerS.nextLine();
					String[] splitSnp = snp.split("\t");
					for (int s=2; s<splitIDs.length; s++) {
						if (splitIDs[s].equals(splitSnp[0])) {
							fil = fil + 1;
						}
					}
				}
				System.out.println("Samples in pop " + sampleFiles[pop] + ": " + fil);
				popsIds[pop] = new String[fil];
				popsInd[pop] = new int[fil];

				fil= 0;
				scannerS = new Scanner(sampleFile);
				while ( scannerS.hasNextLine() ) {
					String snp = scannerS.nextLine();
			       		String[] splitSnp = snp.split("\t");
			       		
					for (int s=2; s<splitIDs.length; s++) {
						if (splitIDs[s].equals(splitSnp[0])) {
							popsIds[pop][fil] = splitSnp[0];
							popsInd[pop][fil] = s;
							fil = fil + 1;
						}
					}
				}
				System.out.println(Arrays.toString(popsIds[pop]));
				System.out.println(Arrays.toString(popsInd[pop]));








				////
				//		Now downsample each population
				//		To same sample sizes
				////

				if (popsIds[pop].length > sampleSize ) {
					popsInd_random[pop] = new int[sampleSize];

    					// Get random 
    					int[] subSet = new int[sampleSize];
		    			int[][] toRun = popsInd;
        				
    					Random randomGenerator = new Random();
		    			for (int boot=0; boot<subSet.length; boot++) {
    						
						subSet[boot] = randomGenerator.nextInt(toRun[pop].length);
		    				popsInd_random[pop][boot] = toRun[pop][subSet[boot]];

    						int[] toRunMem = new int[toRun[pop].length - 1];
    						int run=0;
    						for (int m=0; m<toRun[pop].length; m++) {
    							if (m != subSet[boot]) {
    								toRunMem[run] = toRun[pop][m];
  		  						run = run + 1;
   	 						}
    						}
    						toRun[pop] = toRunMem;
  			  			// System.out.println(toRun[gU].length);
    					}
				} else {
					// Here if sample size is smaller than threshold: 
					// take them all
					popsInd_random[pop] = new int[popsIds[pop].length];
					popsInd_random[pop] = popsInd[pop];
				}
				outLog.println("Pop: " + sampleFiles[pop] + " " + Arrays.toString(popsInd_random[pop]));
				System.out.println("Pop: " + sampleFiles[pop] + " " + Arrays.toString(popsInd_random[pop]));
			}
















	
			////
			// 		Get full list of samples to use:
    			////
    			int samp=0;
		    		
			Writer writerD = new FileWriter(resultsFolder + "donor_list_infile.txt");
			PrintWriter outD = new PrintWriter(writerD);
			
			Writer writerN = new FileWriter(resultsFolder + "names.txt");
                	PrintWriter outN = new PrintWriter(writerN);

	
	    		for (int pop=0; pop<popsInd_random.length; pop++) {						// Change to 0 if you dont want to eliminate relicts
				outD.print(sampleFiles[pop] + "\t" + popsInd_random[pop].length + "\t" + (double)1/(double)(popsInd_random[pop].length) + "\t" + 0.0001 +  "\n");
    				samp = samp + popsInd_random[pop].length;
    			}
 	   		samp = samp + 1; 				// The focus sample
    			outD.close();
    			

			// Build now the total list of samples to use:
			// Donors plus focus sample
			//
    			int[] indexesToUse = new int[samp];
    			
    			samp=0;
			for (int p=0; p<popsInd_random.length; p++) {
        			for (int s=0; s<popsInd_random[p].length; s++) {
        				indexesToUse[samp] = popsInd_random[p][s];
  	      				outN.print(splitIDs[indexesToUse[samp]] + "\n");
					samp = samp + 1;
        			}
    			}
			
			// Add the focal sample
			//
			indexesToUse[samp] = focalInd[accession];
			
			outN.print(splitIDs[indexesToUse[samp]] + "\n");
			samp = samp + 1;
			outN.close();
			




























			
			// Now the real game
			
			scannerMatrix = new Scanner(matrix);
			scannerMatrix.nextLine();
			int chr = 0;
			Writer fineWriter = null;
			PrintWriter outFine = null;
			Writer writerA = null;
			PrintWriter outA = null;
			
			// Open writing file
						
			Writer writer = new FileWriter(resultsFolder + "haplo.txt");
			PrintWriter out = new PrintWriter(writer);
			
			// Positions for ChromoP
			fineWriter = new FileWriter(resultsFolder + "positions.txt");
			outFine = new PrintWriter(fineWriter);
			outFine.print("P ");
			
			// ADM_params
			writerA = new FileWriter(resultsFolder + "ADM_params.map");
			outA = new PrintWriter(writerA);
			
			int snps = 0;
			
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Just print where we are
    			if (chr != Integer.parseInt(splitSnp[0])) {
    				System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
    			}
    			chr = Integer.parseInt(splitSnp[0]);    			
    			
    			// Discard if there are Ns - and keep only segregating sites
    			Boolean Ns = false;
    			Boolean segrega = false;
    			char base1 = splitSnp[indexesToUse[0]].charAt(0);
    			
    			for (int i=0; i<indexesToUse.length; i++) {
    				if (splitSnp[indexesToUse[i]].charAt(0) == 'N') {
    					Ns = true;
    				} else {
    					if (splitSnp[indexesToUse[i]].charAt(0) != base1) {
    						segrega = true;
    					}
    				}
    			}
    			if (!Ns && segrega) {
    				int pos = Integer.parseInt(splitSnp[1]);
    				
        			for (int i=0; i<indexesToUse.length; i++) {
        				if (splitSnp[indexesToUse[i]].charAt(0) == splitSnp[2].charAt(0)) {
        					out.print("0");
        				} else {
        					out.print("1");
        				}
        			}
        			out.print("\n");
        			
        			// Positions
					outFine.print(pos + " ");
					
					// ADM_params
    				String snpName = chr + "_" + pos;
    				outA.print(chr + "\t" + snpName + "\t" + "0" + "\t" + pos + "\n");
    				
    				snps = snps + 1;
    			}
			}
			out.close();
			outA.close();
			
			// Complete writing positions
			outFine.print("\n");
			for (int p = 0; p < snps; p++) {
				outFine.print("S");
			}
			outFine.print("\n");
			outFine.close();
			
			// Write the first lines of chromoP infile
			

			Writer writerC = new FileWriter(resultsFolder + "firstLines.txt");
			PrintWriter outC = new PrintWriter(writerC);
			outC.print(indexesToUse.length - 1 + "\n");
			outC.print(indexesToUse.length + "\n");
			outC.print(snps + "\n");
			outC.close();

			outLog.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

			
	public static void main(String[] args) {
		Build_ChromoPainter_input build_ChromoPainter_input = new Build_ChromoPainter_input();
		build_ChromoPainter_input.setSnpFile(args[0], args[1], args[2], args[3], args[4]);
	}
}
