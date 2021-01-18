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

public class Balancing_selection_stats_onSvs_2 {
	
	public Balancing_selection_stats_onSvs_2() {}
	
	public void setFileToSubSet(String vcf1, String vcf2, String vcf3, String vcf4, String sampleFile){
		
		try {
			// int[] length = {30427671, 19698289, 23459830, 18585056, 26975502};
			
			Writer writer = new FileWriter(sampleFile + "_balSelStats_2020_compareDefenseToGenic.txt");
	    		PrintWriter out = new PrintWriter(writer);
			
			int numReps = 100000;
			double tajD_bootit[] = new double[numReps];
			double pi_bootit[] = new double[numReps];
			double twoPiQ_bootit[] = new double[numReps];
			
			// get clean samples from a file
	double piTD_gw = 0.0;

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
			int[] p1ind = null;
			String[] splitIDs = null;

		
		
		




			////
			//			First, build vectors of all deletions
			////
			//Actually just count them
			
			int numAllDel = 0;

			File vcfZero = new File(vcf1);
			Scanner scannerZ = new Scanner(vcfZero);
			while ( scannerZ.hasNextLine() ) {
				String snp = scannerZ.nextLine();
			       	String[] splitSnp = snp.split("\t");
			       	if (snp.charAt(0) == '#') {
			       		// Header
			       		if (snp.charAt(1) != '#') {
			       			
			       			////
			       			//      Get indexes
			       			////
			       			splitIDs = splitSnp;
			       			
			       			// Indexes for pop1
						int actualSamples = 0;
						for (int i =0; i<pop1.length; i++) {
							Boolean foundIt = false;
							for (int s=2; s<splitIDs.length; s++) {
								if (splitIDs[s].equals(pop1[i])) {
									foundIt = true;
								}
							}
							if (foundIt) {
								actualSamples = actualSamples + 1;
							}
						}
						p1ind = new int[actualSamples];

						actualSamples = 0;
			       			for (int i =0; i<pop1.length; i++) {
			       				Boolean foundIt = false;
			       				for (int s=2; s<splitIDs.length; s++) {
			       					if (splitIDs[s].equals(pop1[i])) {
			       						p1ind[actualSamples] = s;
									actualSamples = actualSamples + 1;
			       						foundIt = true;
			       					}
			       				}
			       				if (!foundIt) {
			       					System.out.println("Sample not found: " + pop1[i]);
			       				}
			       			}
			       			System.out.println(Arrays.toString(p1ind));
					}
				} else {
					// Out of header
		       		
			       	// Is it really a mismatch?
			       	int der = 0;
			       	int match = 0;
			       	for (int i=0; i<p1ind.length; i++) {
			       		if (splitSnp[p1ind[i]].charAt(0) == '1') {
			       			der = der + 1;
			       		} else {
			       			if (splitSnp[p1ind[i]].charAt(0) == '0') {
			       				match = match + 1;
			       			}
			       		}
			       	}
			       	// Segrega?
			       	//
			       	if (der > 0 && match > 0) {
			       		numAllDel = numAllDel + 1;
			       	}
				}
			}
			
			
			////
			//			Now really
			////
			int[] matchVect = new int[numAllDel];
            int[] mismVect = new int[numAllDel];
            
			numAllDel = 0;
			scannerZ = new Scanner(vcfZero);
			while ( scannerZ.hasNextLine() ) {
				String snp = scannerZ.nextLine();
			       	String[] splitSnp = snp.split("\t");
			       	if (snp.charAt(0) == '#') {
			       		// Header
			       		if (snp.charAt(1) != '#') {}
				} else {
					// Out of header
		       		
			       	// Is it really a mismatch?
			       	int der = 0;
			       	int match = 0;
			       	for (int i=0; i<p1ind.length; i++) {
			       		if (splitSnp[p1ind[i]].charAt(0) == '1') {
			       			der = der + 1;
			       		} else {
			       			if (splitSnp[p1ind[i]].charAt(0) == '0') {
			       				match = match + 1;
			       			}
			       		}
			       	}
			       	// Segrega?
			       	//
			       	if (der > 0 && match > 0) {
			       		matchVect[numAllDel] = match;
			       		mismVect[numAllDel] = der;
			       		
			       		numAllDel = numAllDel + 1;
			       	}
				}
			}
			////
			// matchVect and mismVect contain vectors 
			// of numbers of matches and mismatches to randomise 
			
			
			
			
			
			
			
			
			
			
			


			
			////
			//			Open the 3 files one after the other
			////
			
			double pi[] = {0.0, 0.0, 0.0, 0.0};
			double twoPQ[] = {0.0, 0.0, 0.0, 0.0};
			double tajD[] = {0.0, 0.0, 0.0, 0.0};
			
			String[] files = {vcf1, vcf2, vcf3, vcf4};
			
			for (int f=0; f<files.length; f++) {

				// To Boot it
				String localName = files[f].split("/")[files[f].split("/").length - 1];			// Check if you need escape "/"
				// Writer writer2 = new FileWriter(sampleFile + "_" + localName + "_forBootyBooty.txt");
			    	// PrintWriter out2 = new PrintWriter(writer2);
				//
				//
				
		

	
		
			File vcf = new File(files[f]);
		    	Scanner scanner = new Scanner(vcf);
			System.out.println("Go on file: " + files[f]);
				
			while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
			       	String[] splitSnp = snp.split("\t");
			       	if (snp.charAt(0) == '#') {
			       		// Header
			       		if (snp.charAt(1) != '#') {
			       			////
			       			//      Get indexes
			       			////
			       			splitIDs = splitSnp;
			       			
			       			// Indexes for pop1
						int actualSamples = 0;
						for (int i =0; i<pop1.length; i++) {
							Boolean foundIt = false;
							for (int s=2; s<splitIDs.length; s++) {
								if (splitIDs[s].equals(pop1[i])) {
									foundIt = true;
								}
							}
							if (foundIt) {
								actualSamples = actualSamples + 1;
							}
						}
						p1ind = new int[actualSamples];

						actualSamples = 0;
			       			for (int i =0; i<pop1.length; i++) {
			       				Boolean foundIt = false;
			       				for (int s=2; s<splitIDs.length; s++) {
			       					if (splitIDs[s].equals(pop1[i])) {
			       						p1ind[actualSamples] = s;
									actualSamples = actualSamples + 1;
			       						foundIt = true;
			       					}
			       				}
			       				if (!foundIt) {
			       					System.out.println("Sample not found: " + pop1[i]);
			       				}
			       			}
			       			System.out.println(Arrays.toString(p1ind));
					}
				}
			}
		
			
				int[] dif = new int[p1ind.length*(p1ind.length-1)/2];
				int[] len = new int[p1ind.length*(p1ind.length-1)/2];
				
				// For tajD
				int S=0;
				
				double twoPQ_p = 0.0;
				int twoPQ_l = 0;
				piTD_gw = 0.0;	
				
		    	scanner = new Scanner(vcf);
			System.out.println("Go on file: " + files[f]);
				
				while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
			       	String[] splitSnp = snp.split("\t");
			       	if (snp.charAt(0) == '#') {
			       		// Header
			       		if (snp.charAt(1) != '#') {
			       			// read header
			       		}
			       	} else {
			       		
				       	int chr = Integer.parseInt(splitSnp[0]);
				       	int posStart = Integer.parseInt(splitSnp[1]);
				       	int posEnd = Integer.parseInt(splitSnp[7].split(";")[7].split("=")[1]);
				       	
				       	// Is it really a mismatch?
				       	int der = 0;
				       	int match = 0;
				       	Boolean mism = false;
				       	for (int i=0; i<p1ind.length; i++) {
				       		if (splitSnp[p1ind[i]].charAt(0) == '1') {
				       			der = der + 1;
				       			mism = true;
				       		} else {
				       			if (splitSnp[p1ind[i]].charAt(0) == '0') {
				       				match = match + 1;
				       			}
				       		}
				       	}
					// Segrega?
					// //
				       	if (der > 0 && match > 0) {
				       		
				       		// Do the thing
				       		//
				       		// 		Pairw diff
				       		//
				       		int pair = 0;
				       		for (int i=0; i<p1ind.length-1; i++) {
				       			char base1 = splitSnp[p1ind[i]].charAt(0);
						       	for (int i2=i + 1; i2<p1ind.length; i2++) {
		       						//
		       						char base2 = splitSnp[p1ind[i2]].charAt(0);
		       						if (base1 != 'N' && base2 != 'N') {
			       						len[pair] = len[pair] + 1;
			       						if (base1 != base2) {
			       							dif[pair] = dif[pair] + 1;
			       						}
			       					}
		       						pair = pair + 1;
			       				}
				       		}
				       		
				       		// 2pq
				       		double p = der/(der + (double)match);
				       		twoPQ_p = twoPQ_p + 2*p*(1-p);
				       		twoPQ_l = twoPQ_l + 1;
						
						// To check calculations
						double diffPerSnp = (double)(match*der) / (double)( ((match+der)-1)*(match+der)/2);
						piTD_gw = piTD_gw + diffPerSnp;
							
						// To boot it
						// out2.print(chr + "\t" + posStart + "-" + posEnd + "\t" + p1ind.length + "\t" + match + "\t" + der + "\t" + p + "\n");
							
				       		// Taj D
				       		S = S + 1;
				       	}
			       	}
				}
				int numVariants =S;
				////
				// Pairw.diff.
				
				double[] pairwDiff = new double[dif.length];
				for (int q=0; q<dif.length; q++) {
					pairwDiff[q] = (double)(dif[q])/(double)(len[q]);
					
					// This has the problem that the length cannot easily be bootstrapped
					// for confidence intervals
					// pi[f] = pi[f] + pairwDiff[q];
					//
				}
				// pi[f] = pi[f]/dif.length;
				pi[f] = piTD_gw;
				System.out.println(Arrays.toString(pi));
				
				// 2pq
				twoPQ[f] = twoPQ_p/(double)twoPQ_l;
				System.out.println(Arrays.toString(twoPQ));
				
				// Taj D
				double piTD = 0.0;
	       		int comp = 0;
	       		
	       		for (int q=0; q<dif.length; q++) {
	       			// if (dif[q] > 0) {
	       				piTD = piTD + (double)(dif[q]);
	       				comp = comp + 1;
	       			// }
	       		}
	       		piTD = piTD/(double)comp; 
	       		
	       		 
	       		// Calculate all the partials for tajD
	       		
	       		int n = p1ind.length;
	       		
	       		// Get a1
	       		Double a1 = 0.0;
	       		for (int i=1; i<n; i++) {
	       			a1 = a1 + 1/(double)i;
	       		}
	       		System.out.print("a1: " + a1 + "\n");
	       		
	       		// Get a2
	       		Double a2 = 0.0;
	       		for (int i=1; i<n; i++) {
	       			a2 = a2 + 1/Math.pow(i, 2);
	       		}
	       		System.out.print("a2: " + a2 + "\n");
	       		
	       		// Get b1
	       		Double b1 = (double)(n + 1)/(double)(3*(n - 1));
	       		System.out.print("b1: " + b1 + "\n");
	       		
	       		// Get b2
	       		Double b2 = 2*(double)(Math.pow(n, 2) + n + 3)/(double)(9*n*(n - 1));
	       		System.out.print("b2: " + b2 + "\n");
	       		
	       		// Get c1
	       		Double c1 = b1 - (1/a1);
	       		System.out.print("c1: " + c1 + "\n");
		       		 
	       		// Get c2
	       		Double c2 = b2 - (double)(n+2)/(double)(a1*n) + (double)a2/(double)Math.pow(a1, 2);
	       		System.out.print("c2: " + c2 + "\n");
	       		
	       		// Get e1
	       		Double e1 = c1/a1;
	       		System.out.print("e1: " + e1 + "\n");
	       		
	       		// Get e2
	       		Double e2 = c2/(Math.pow(a1, 2) + a2);
			System.out.print("e2: " + e2 + "\n");
			// Calculate tajD
			//
			System.out.print("Partials for tajD: " + piTD + "\t" + S + "\t" + (S/a1) + "\n");
			double tajDAsInBoot = (double)( piTD_gw - (S/a1) ) / Math.sqrt( (e1*S) + (e2*S*(S-1)) );
			System.out.print("TajD as in the boot script: " + tajDAsInBoot + "\t" + piTD_gw + "\t" + (S/a1) + "\n");

			// This one has the problem that piTD is an average over all pairs, not only the pairs that were non missing.
			// This although the number of differences is calculated across the non-missing. So there are fewer differences than it should be:
			//
			// tajD[f] = (double)( piTD - (S/a1) ) / Math.sqrt( (e1*S) + (e2*S*(S-1)) );
			
			tajD[f] = (double)( piTD_gw - (S/a1) ) / Math.sqrt( (e1*S) + (e2*S*(S-1)) );

			// out2.close();
			
			
			
			////
			//			And now, booty booty Boot it many times
			////

			System.out.println("numVariants: " + numVariants);
			for (int rep = 0; rep<numReps; rep++) {
				// System.out.println("rep: " + rep);
				// Boot it
				int[] bootIndexes = new int[numVariants];
				
				// Build random indexes with replacement to randomise stats
				
		       		Random randomGenerator = new Random();
				for (int u=0; u<bootIndexes.length; u++) {
					bootIndexes[u] = randomGenerator.nextInt(matchVect.length);
				}
				// 
				// System.out.println("bootIndex: \t" + Arrays.toString(bootIndexes));

				// Compute stats on the subsample
				//
				double piTD_boot = 0.0;
				double twoPQ_boot = 0.0;
				for (int s=0; s<bootIndexes.length;s++) {
					double diffPerSnp = (double)(matchVect[bootIndexes[s]]*mismVect[bootIndexes[s]]) / (double)( ((matchVect[bootIndexes[s]]+mismVect[bootIndexes[s]])-1)*(matchVect[bootIndexes[s]]+mismVect[bootIndexes[s]])/2);
					piTD_boot = piTD_boot + diffPerSnp;
			       		
					// 2pq
				       	double pBoot = mismVect[bootIndexes[s]]/(matchVect[bootIndexes[s]] + (double)mismVect[bootIndexes[s]]);
				       	twoPQ_boot = twoPQ_boot + 2*pBoot*(1-pBoot);
				       	
	
					// System.out.println("piTD_boot: " + piTD_boot);
				}
				// Calculate tajD
				// System.out.println("Booting it: " + piTD_boot + "\t" + numVariants + "\t" + a1);
				// System.out.println("Booting it: " + (double)( piTD_boot - (numVariants/a1) ) / Math.sqrt( (e1*numVariants) + (e2*numVariants*(numVariants-1)) ));
				
				tajD_bootit[rep] = (double)( piTD_boot - (numVariants/a1) ) / Math.sqrt( (e1*numVariants) + (e2*numVariants*(numVariants-1)) );
				pi_bootit[rep] = piTD_boot;
				twoPiQ_bootit[rep] = twoPQ_boot/(double)bootIndexes.length;
				// System.out.println(pi + "\t" + S + "\t" + S/a1);
			}
			
			out.print("TajD booty booty on file " + f + "\n");
			for (int p=0; p<tajD_bootit.length; p++) {
				out.print("\t" + tajD_bootit[p]);
			}
			out.print("\n");
			
			out.print("pi_bootit  booty booty on file " + f + "\n");
			for (int p=0; p<pi_bootit.length; p++) {
				out.print("\t" + pi_bootit[p]);
			}
			out.print("\n");
			
			
			out.print("twoPiQ_bootit booty booty on file " + f + "\n");
			for (int p=0; p<twoPiQ_bootit.length; p++) {
				out.print("\t" + twoPiQ_bootit[p]);
			}
			out.print("\n");
			
			
			// System.out.println(Arrays.toString(tajD_bootit));
			
			}
		


			out.print("stat\tall\tintergenic\tgenic\tdefence\n");
			
	    	// Pi
	    	out.print("pi");
			for (int p=0; p<pi.length; p++) {
				out.print("\t" + pi[p]);
			}
			out.print("\n");
			
			// 2pq
			out.print("2pq");
			for (int p=0; p<twoPQ.length; p++) {
				out.print("\t" + twoPQ[p]);
			}
			out.print("\n");
			
			// TajjD
			out.print("TajD");
			for (int p=0; p<tajD.length; p++) {
				out.print("\t" + tajD[p]);
			}
			out.print("\n");
			out.close();
	    	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Balancing_selection_stats_onSvs_2 balancing_selection_stats_onSvs_2 = new Balancing_selection_stats_onSvs_2();
		balancing_selection_stats_onSvs_2.setFileToSubSet(args[0], args[1], args[2], args[3], args[4]);
	}
}
