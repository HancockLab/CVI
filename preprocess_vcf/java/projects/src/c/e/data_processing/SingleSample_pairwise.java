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

public class SingleSample_pairwise {
	
	public SingleSample_pairwise() {}
	
	public void setMatrixFile(String oldNames, String newNames, String oldPiMatrix, String matrixName, String results){
		
		try {
            //int accessionIndex = Integer.parseInt(accessionString) + 3;
            
			System.out.println("src/c/e/data_processing/SingleSample_pairwise.java");			
            // System.out.println("Running on sample: " + accessionIndex);
            
            
			
			
			
			
            
            // Open names of the samples run previously
            
            File sampleFile = new File(oldNames);
            Scanner scannerS = new Scanner(sampleFile);
            int fil = 0;
            while ( scannerS.hasNextLine() ) {
            	String snp = scannerS.nextLine();
            	fil = fil + 1;
            }
            System.out.println("files: " + fil);
            String[] oldies = new String[fil];
            
            fil= 0;
            scannerS = new Scanner(sampleFile);
            while ( scannerS.hasNextLine() ) {
            	String snp = scannerS.nextLine();
            	String[] splitSnp = snp.split("\t");
            	
            	oldies[fil] = splitSnp[0];
            	fil = fil + 1;
            }
            System.out.println("Old: " + oldies.length);
	    System.out.println(Arrays.toString(oldies));
            

            
            
            
            

            
            // Open names of the new samples 
            
            File sampleFile2 = new File(newNames);
            Scanner scannerS2 = new Scanner(sampleFile2);
            fil = 0;
            while ( scannerS2.hasNextLine() ) {
            	String snp = scannerS2.nextLine();
            	fil = fil + 1;
            }
            System.out.println("files: " + fil);
            String[] newbies = new String[fil];
            
            fil= 0;
            scannerS2 = new Scanner(sampleFile2);
            while ( scannerS2.hasNextLine() ) {
            	String snp = scannerS2.nextLine();
            	String[] splitSnp = snp.split("\t");
            	
            	newbies[fil] = splitSnp[0];
            	fil = fil + 1;
            }
	    System.out.println("New: " + newbies.length);
            System.out.println(Arrays.toString(newbies));
            
            
            
            
            
            
            // Now charge the pi matrix
            
            File piMatrix = new File(oldPiMatrix);
            Scanner scannerP = new Scanner(piMatrix);
            
            fil = 0;
            while ( scannerP.hasNextLine() ) {
            	String snp = scannerP.nextLine();
            	fil = fil + 1;
            }
            System.out.println("rows: " + fil);
            double[][] piM = new double[fil][fil];
            
            scannerP = new Scanner(piMatrix);
            fil = 0;
            while ( scannerP.hasNextLine() ) {
            	String snp = scannerP.nextLine();
            	String[] splitSnp = snp.split("\t");
            	
            	for (int col=0; col<splitSnp.length; col++) {
            		piM[fil][col] = Double.parseDouble(splitSnp[col]);
            	}
            	fil = fil + 1;
            }
            //for (int p=0; p<piM.length; p++) {
            //    System.out.println(Arrays.toString(piM[p]));
            //}
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
	    	
	    	// Begin with the big matrix
	    	File matrix = new File(matrixName);
	    	Scanner scannerMatrix = new Scanner(matrix);
	    	
	    	// Just get ID order in the matrix from first line
		String idsUnsplit = scannerMatrix.nextLine();
	    	String[] splitIDs = idsUnsplit.split("\t");
	    	

            
	        ////
		//		If the new samples overlap the old, keep the new
		//		Also, if the old are not any longer there...
		//		Because they were crappy and eliminated, they are to be killed
		//// 
		
		int oldToKill = 0;
        	int oldSurvivors = 0;
		for (int o=0; o<oldies.length; o++) {
			// Is this old sample in the new matrix?
			Boolean found = false;
			for (int i=3; i<splitIDs.length; i++) {
				if (oldies[o].equals(splitIDs[i])) {
					// Is it also in the list of new samples?
					Boolean duplicate = false;	
					for (int n=0; n<newbies.length; n++) {
						if (newbies[n].equals(oldies[o])) {
							duplicate = true;
						}
					}
					if (duplicate) {
						oldToKill = oldToKill + 1;
					} else {
						oldSurvivors = oldSurvivors + 1;
					}
					found = true;
				}
			}
			if (!found) {
				oldToKill = oldToKill + 1;
			}
		}
		
		// Order samples in the matrix as in names
		
		String[] toKill = new String[oldToKill];
		int[] newbyInd = new int[newbies.length];
		int[] allInd = new int[oldSurvivors + newbies.length];
			
		oldToKill = 0;
		int all = 0;
           	for (int o=0; o<oldies.length; o++) {
			// Is this old sample in the new matrix?
			Boolean found = false;
			for (int i=3; i<splitIDs.length; i++) {
				if (oldies[o].equals(splitIDs[i])) {
					// Is it also in the list of new samples?
					Boolean duplicate = false;	
					for (int n=0; n<newbies.length; n++) {
						if (newbies[n].equals(oldies[o])) {
							duplicate = true;
						}
					}
					if (duplicate) {
						toKill[oldToKill] = oldies[o];
						oldToKill = oldToKill + 1;
					} else {
						allInd[all] = i;
						all = all + 1;
					}
					found = true;
				}
			}
			if (!found) {
				toKill[oldToKill] = oldies[o];
				oldToKill = oldToKill + 1;
			}
		}
        	System.out.println("to kill: " + toKill.length);
		System.out.println(Arrays.toString(toKill));




		for (int o=0; o<newbies.length; o++) {
			Boolean found = false;
			for (int i=3; i<splitIDs.length; i++) {
				if (newbies[o].equals(splitIDs[i])) {
					newbyInd[o] = i;
					allInd[all] = i;
					all = all + 1;
					
					found = true;
				}
			}
			if (!found) {
				System.out.println("Problem in sight at newbies! " + newbies[o]);
			}
		}
		System.out.println("All indexes: " + allInd.length);
		System.out.println("All indexes: " + Arrays.toString(allInd));
			
			
			
			
			
			
			
			
			// Now the real thing: For every newby, calculate a vector of pairw.diff. to add to the matrix
			
			int[][] differences = new int[newbyInd.length][allInd.length];
			int[][] length = new int[newbyInd.length][allInd.length];
			int chr =0;
			
		    	matrix = new File(matrixName);
		    	scannerMatrix = new Scanner(matrix);
		    	scannerMatrix.nextLine();
		    
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Just print where we are
        			if (chr != Integer.parseInt(splitSnp[0])) {
        				System.out.println("Chr: " + Integer.parseInt(splitSnp[0]));
        			}
        			chr = Integer.parseInt(splitSnp[0]);
        			int pos = Integer.parseInt(splitSnp[1]);
	       			//
	        		
        			for (int n=0; n<newbyInd.length; n++) {
            				char base1 = splitSnp[newbyInd[n]].charAt(0);
    	    	    
    		       			for (int acc2=0; acc2<allInd.length; acc2++) {
    		       				char base2 = splitSnp[allInd[acc2]].charAt(0);
    		       				
    		       				if ( (base1 != 'N') && (base2 != 'N') ) {
    		       					length[n][acc2] = length[n][acc2] + 1;
    	        					if (base1 != base2) {
    		       						differences[n][acc2] = differences[n][acc2] + 1;
    		       					}
    		       				}
    	        			}
        			}
		    	}
			// Calculate pi
			
			double[][] pairwDiff = new double[newbyInd.length][allInd.length];
    			for (int n=0; n<newbyInd.length; n++) {
    				for (int q=0; q<allInd.length; q++) {
    					pairwDiff[n][q] = (double)(differences[n][q])/(double)(length[n][q]);
    				}
    			}
			// Done
			
			
			
			
			// Add it to the matrix
			// System.out.println("piM: " + "\t" + piM.length + "\t" + piM[0].length);
			
			double[][] piMnew = new double[allInd.length][allInd.length];				
			
			int row = 0;
			for (int r=0; r<piM.length; r++) {
				Boolean kill = false;
				for (int k=0; k<toKill.length; k++) {
					if (oldies[r].equals(toKill[k])) {
						kill = true;
					}
				}
				if (!kill) {
					int col = 0;
					for (int c=0; c<piM[r].length; c++) {
						Boolean killC = false;
						for (int k=0; k<toKill.length; k++) {
							if (oldies[c].equals(toKill[k])) {
								killC = true;
							}
						}
						if (!killC) {
							piMnew[row][col] = piM[r][c];
							col = col + 1;
						}
					}
					for (int n=0; n<pairwDiff.length; n++) {
						piMnew[row][col] = pairwDiff[n][r];
						col = col + 1;
					}
					row = row + 1;
				}
			}
			for (int n=0; n<pairwDiff.length; n++) {
				for (int c=0; c<pairwDiff[n].length; c++) {
					piMnew[row][c] = pairwDiff[n][c];
				}
				row = row + 1;
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
			// Write out

			Writer writer = new FileWriter(results + "piMatrix.txt");
			PrintWriter out = new PrintWriter(writer);
			for (int r=0; r<piMnew.length; r++) {
				for (int c=0; c<piMnew[0].length; c++) {
					out.print(piMnew[r][c]);
					if (c < piMnew[0].length - 1) {
						out.print("\t");
					}
				}
				out.print("\n");
			}
			out.close();

			// Write accession names
			Writer writerN = new FileWriter(results + "names.txt");
			PrintWriter outN = new PrintWriter(writerN);
	   	   	for (int sub=0; sub<allInd.length; sub++) {
	    	   		outN.print(splitIDs[allInd[sub]] + "\n");
			}
		   	outN.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SingleSample_pairwise singleSample_pairwise = new SingleSample_pairwise();
		singleSample_pairwise.setMatrixFile(args[0], args[1], args[2], args[3], args[4]);
	}
}
