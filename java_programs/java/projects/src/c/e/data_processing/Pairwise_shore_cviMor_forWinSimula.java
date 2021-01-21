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

public class Pairwise_shore_cviMor_forWinSimula {
	
	public Pairwise_shore_cviMor_forWinSimula() {}
	
	public void setMatrixFile(String matrixName, String resultsPart, String wSize, String samplesSanto, String samplesFogo, String winNumber) {
		
		try {
			int win = Integer.parseInt(wSize);
			int winNum = Integer.parseInt(winNumber);
			
			String results = resultsPart + "_" + wSize + "/";
			
			Writer writerLog = new FileWriter(results + "log.txt");
			PrintWriter outLog = new PrintWriter(writerLog);
			outLog.println("src/c/e/data_processing/Pairwise_shore.java");            
			
			// Open IDs in the big matrix
			
			File matrix = new File(matrixName);
			Scanner scannerMatrix = new Scanner(matrix);
			// Header out
			for (int h=0; h<5; h++) {
				scannerMatrix.nextLine();
			}
			String idsUnsplit = scannerMatrix.nextLine();
			String[] IDs = idsUnsplit.split("\t");
			
			
			
			// Get Cvis
			
			File fileNamesToGet = new File(samplesSanto);
			Scanner scanNamesTG = new Scanner(fileNamesToGet);
			
			int samplesTG = 0;
			while (scanNamesTG.hasNextLine()) {
				String name = scanNamesTG.nextLine();
				samplesTG = samplesTG + 1;
			}
			String[] samplesToGet = new String[samplesTG];
			
			samplesTG = 0;
			scanNamesTG = new Scanner(fileNamesToGet);
			while (scanNamesTG.hasNextLine()) {
				String name = scanNamesTG.nextLine();
				String[] splitName = name.split("\t");
				samplesToGet[samplesTG] = splitName[0];
				samplesTG = samplesTG + 1;
			}
			outLog.println(Arrays.toString(samplesToGet));
			outLog.println("We have: " + samplesToGet.length + " samples");
			
			
			
			// Get indexes
			
			int[] index1 = new int[samplesToGet.length];
			for (int s=0; s<samplesToGet.length; s++) {
				Boolean thereIs = false;
				for (int i=0; i<IDs.length; i++) {
					if (samplesToGet[s].equals(IDs[i])) {
						thereIs = true;
						index1[s] = i;
					}
				}
				if (!thereIs) {
					outLog.println("There is no " + samplesToGet[s]);
				}
			}
			for (int p=0; p<index1.length; p++) {
				outLog.print(IDs[index1[p]] + "\t");
			}			
			outLog.print("\n");
			// Got indexes
			
			
			
			
			

			// Get Morocccans
			
			File fileNamesToGet2 = new File(samplesFogo);
			
			Scanner scanNamesTG2 = new Scanner(fileNamesToGet2);

			int samplesTG2 = 0;
			while (scanNamesTG2.hasNextLine()) {
				String name = scanNamesTG2.nextLine();
				samplesTG2 = samplesTG2 + 1;
			}
			String[] samplesToGet2 = new String[samplesTG2];
			
			samplesTG2 = 0;
			scanNamesTG2 = new Scanner(fileNamesToGet2);
			while (scanNamesTG2.hasNextLine()) {
				String name = scanNamesTG2.nextLine();
				String[] splitName = name.split("\t");
				samplesToGet2[samplesTG2] = splitName[0];
				samplesTG2 = samplesTG2 + 1;
			}
			outLog.println(Arrays.toString(samplesToGet2));
			outLog.println("We have: " + samplesToGet2.length + " samples");
			

			// Get indexes
			
			int[] index2 = new int[samplesToGet2.length];
			for (int s=0; s<samplesToGet2.length; s++) {
				Boolean thereIs = false;
				for (int i=0; i<IDs.length; i++) {
					if (samplesToGet2[s].equals(IDs[i])) {
						thereIs = true;
						index2[s] = i;
					}
				}
				if (!thereIs) {
					outLog.println("There is no " + samplesToGet[s]);
				}
			}
			for (int p=0; p<index2.length; p++) {
				outLog.print(IDs[index2[p]] + "\t");
			}
			outLog.print("\n");
			
			
			
			int[] indexTot = new int[index1.length + index2.length];
			int t=0;
			for (int p=0; p<index1.length; p++) {
				indexTot[t] = index1[p];
				t = t + 1;
			}
			for (int p=0; p<index2.length; p++) {
				indexTot[t] = index2[p];
				t = t + 1;
			}
			outLog.println(Arrays.toString(indexTot));
			
			// Got indexes
			
			
			
			
			//
			// Now the real game
			// 
			
        	// Begin to calculate pairwise differences\
			
			// int[] chrL = {30427671, 19698289, 23459830, 18585056, 26975502};
			
			// For Santo
			//
			int[] diffS = new int[(index1.length-1)*(index1.length)/2];
			int[] lenS = new int[(index1.length-1)*(index1.length)/2];
			double[][] piS = new double[winNum][];
			double[][] piSmax = new double[winNum][];
			double[][] tajDs = new double[winNum][];
			
			// ThetaW
			double[][] thetaWS = new double[winNum][];
			int ss = 0;
			int lenSs = 0;
			double as = 0.0;
			for (int i=1; i<index1.length; i++) {
				as = as + 1/(double)i;
			}
			System.out.println(as);
			
			// Taj D
            // Calculate all the partials for tajD

            int n = index1.length;

            // Get a1
            Double a1s = 0.0;
            for (int i=1; i<n; i++) {
                    a1s = a1s + 1/(double)i;
            }
            outLog.print("a1s: " + a1s + "\n");

            // Get a2
            Double a2s = 0.0;
            for (int i=1; i<n; i++) {
                    a2s = a2s + 1/Math.pow(i, 2);
            }
            System.out.print("a2s: " + a2s + "\n");

            // Get b1
            Double b1s = (double)(n + 1)/(double)(3*(n - 1));
            System.out.print("b1s: " + b1s + "\n");

            // Get b2
            Double b2s = 2*(double)(Math.pow(n, 2) + n + 3)/(double)(9*n*(n - 1));
            System.out.print("b2s: " + b2s + "\n");

            // Get c1
            Double c1s = b1s - (1/a1s);
            System.out.print("c1s: " + c1s + "\n");

            // Get c2
            Double c2s = b2s - (double)(n+2)/(double)(a1s*n) + (double)a2s/(double)Math.pow(a1s, 2);
            System.out.print("c2s: " + c2s + "\n");

            // Get e1
            Double e1s = c1s/a1s;
            System.out.print("e1s: " + e1s + "\n");

            // Get e2
            Double e2s = c2s/(Math.pow(a1s, 2) + a2s);
            System.out.print("e2s: " + e2s + "\n");



			
			// For Fogo
			//
			int[] diffF = new int[(index2.length-1)*(index2.length)/2];
			int[] lenF = new int[(index2.length-1)*(index2.length)/2];
			double[][] piF = new double[winNum][];
			double[][] piFmax = new double[winNum][];
			double[][] tajDf = new double[winNum][];
			
			for (int i=0; i<lenF.length; i++) {
				diffF[i] = 0;
				lenF[i] = 0;
			}
			
			// ThetaW
			double[][] thetaWF = new double[winNum][];
			int sf = 0;
			int lenFs = 0;
			double af = 0.0;
			for (int i=1; i<index2.length; i++) {
				af = af + 1/(double)i;
			}
			System.out.println(af);
			
			// For Taj D
            // Calculate all the partials for tajD

            n = index2.length;

            // Get a1
            Double a1f = 0.0;
            for (int i=1; i<n; i++) {
                    a1f = a1f + 1/(double)i;
            }
            System.out.print("a1f: " + a1f + "\n");

            // Get a2
            Double a2f = 0.0;
            for (int i=1; i<n; i++) {
                    a2f = a2f + 1/Math.pow(i, 2);
            }
            System.out.print("a2f: " + a2f + "\n");

            // Get b1
            Double b1f = (double)(n + 1)/(double)(3*(n - 1));
            System.out.print("b1f: " + b1f + "\n");

            // Get b2
            Double b2f = 2*(double)(Math.pow(n, 2) + n + 3)/(double)(9*n*(n - 1));
            System.out.print("b2f: " + b2f + "\n");

            // Get c1
            Double c1f = b1f - (1/a1f);
            System.out.print("c1f: " + c1f + "\n");

            // Get c2
            Double c2f = b2f - (double)(n+2)/(double)(a1f*n) + (double)a2f/(double)Math.pow(a1f, 2);
            System.out.print("c2f: " + c2f + "\n");

            // Get e1
            Double e1f = c1f/a1f;
            System.out.print("e1f: " + e1f + "\n");

            // Get e2
            Double e2f = c2f/(Math.pow(a1f, 2) + a2f);
            System.out.print("e2f: " + e2f + "\n");



			
			// For both
			//
			int[] difT = new int[(indexTot.length - 1)*(indexTot.length)];
			int[] lenT = new int[(indexTot.length - 1)*(indexTot.length)];
			double[][] fst = new double[winNum][];
			
			for (int c=0; c<winNum; c++) {
				int numWin = 1;
				System.out.println(numWin);
				piS[c] = new double[numWin];
				piSmax[c] = new double[numWin];
				piF[c] = new double[numWin];
				piFmax[c] = new double[numWin];
				
				thetaWS[c] = new double[numWin];
				thetaWF[c] = new double[numWin];
				
				tajDs[c] = new double[numWin];
				tajDf[c] = new double[numWin];
				
				fst[c] = new double[numWin];
			}
			
			
			
			
			
			// For between the 2 pops
			//
			int[] diffB = new int[(index1.length)*(index2.length)];
			int[] lenB = new int[(index1.length)*(index2.length)];
			double[][] piBmin = new double[winNum][];
			double[][] piBmax = new double[winNum][];
			double[][] piB = new double[winNum][];

			for (int i=0; i<lenB.length; i++) {
				diffB[i] = 0;
				lenB[i] = 0;
			}

			for (int c=0; c<winNum; c++) {
				int numWin = 1;
				piB[c] = new double[numWin];
				piBmin[c] = new double[numWin];
				piBmax[c] = new double[numWin];
			}
			




		// Go ahead, big game:

			
		int chr = 0;
		int chrMem = 0;
		int posMem = 0;
		int w = 1;	
	    	int l=0;

		// Oldgood thing
		matrix = new File(matrixName);
		scannerMatrix = new Scanner(matrix);
		// Header out
		for (int h=0; h<6; h++) {
			scannerMatrix.nextLine();
		}
			while ( scannerMatrix.hasNextLine() ) {
				String snp = scannerMatrix.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Just print where we are
    			if (chr != Integer.parseInt(splitSnp[0])) {
    				outLog.println("Chr: " + Integer.parseInt(splitSnp[0]));
    				w = 1;
		
				// Reinitialize
				ss = 0;
				sf = 0;
				lenSs = 0;
				lenFs = 0;
    			}
    			chr = Integer.parseInt(splitSnp[0]);
    			int pos = Integer.parseInt(splitSnp[1]);
       		 	//
        		
    		
    			
    			if (chr > chrMem && chr > 1) {
    				// 
    				//  Output, calculate pi
    				//
    				// For Santo
    				//
    				double sum = 0.0;
    				double sumSTajD = 0.0;
				double maxPairwS = -1.0;
				long pairs = 0;
    				for (int p=0; p<diffS.length; p++) {
    					lenS[p] = win;
					double pairw = (double)(diffS[p])/(double)(lenS[p]);
					sum = sum + pairw;
					if (pairw > maxPairwS) {
						maxPairwS = pairw;
					}
    					sumSTajD = sumSTajD + (double)(diffS[p]);
					pairs = pairs + 1;
    					
    					// Reinitialize
    					diffS[p] = 0;
    					lenS[p] = 0;
    				}
    				// System.out.println("Problem at: chr " + chr + " w = "+ w + " piS.length " + piS.length + " piS[chr].length()" + piS[chr-2].length + " " + snp);

				piS[chr-2][w-1] = (double)sum/(double)pairs;
				piSmax[chr-2][w-1] = maxPairwS;

    				// For Fogo
    				//
    				sum = 0.0;
				double sumFTajD = 0.0;
				double maxPairwF = -1.0;
    				pairs = 0;
    				for (int p=0; p<diffF.length; p++) {
					lenF[p] = win;
    					double pairw = (double)(diffF[p])/(double)(lenF[p]);
					sum = sum + pairw;
					if (pairw > maxPairwF) {
						maxPairwF = pairw;
					}
        				sumFTajD = sumFTajD + (double)(diffF[p]);
					pairs = pairs + 1;

    					// Reinitialize
    					diffF[p] = 0;
    					lenF[p] = 0;
    				}
    				piF[chr-2][w-1] = (double)sum/(double)pairs;
				piFmax[chr-2][w-1] = maxPairwF;

				// For Between pops
    				//
				sum =0.0;
				double minPairw = 1000.0;
				double maxPairw = -1.0;
				pairs = 0;
    				for (int p=0; p<diffB.length; p++) {
    					lenB[p] = win;
					double pairw = (double)(diffB[p])/(double)(lenB[p]);
					sum = sum + pairw;
					pairs = pairs + 1;
					if (pairw < minPairw) {
						minPairw = pairw;
					}
    					if (pairw > maxPairw) {
						maxPairw = pairw;
					}

					// Reinitialize
    					diffB[p] = 0;
    					lenB[p] = 0;
    				}
				piBmin[chr-2][w-1] = minPairw;
				piBmax[chr-2][w-1] = maxPairw;
    				piB[chr-2][w-1] = (double)sum/(double)pairs;
				
				// System.out.println(piS[chr-2][w-1] + "\t" + piF[chr-2][w-1] + "\t" + piBmin[chr-2][w-1] + "\t" + piB[chr-2][w-1]);

    				//
    				// Out also theta W
    				//
    				// For Santo
    				thetaWS[chr-2][w-1] = (double)ss / (double)(as*lenSs);
    				//
    				// For Fogo
    				thetaWF[chr-2][w-1] = (double)sf / (double)(af*lenFs);
    				
    				//
                    		// Calculate tajD
    				//
                    		tajDs[chr-2][w-1] = (double)( ( sumSTajD/((double)(diffS.length)) ) - ( (double)ss / (double)(as) ) ) / Math.sqrt( (e1s*ss) + (e2s*ss*(ss-1)) );
                   	 	tajDf[chr-2][w-1] = (double)( ( sumFTajD/((double)(diffF.length)) ) - ( (double)sf / (double)(af) ) ) / Math.sqrt( (e1f*sf) + (e2f*sf*(sf-1)) );
    				
    				//
    				// For Fst
    				//
    				sum = 0.0;
    				pairs = 0;
    				for (int p=0; p<difT.length; p++) {
					lenT[p] = win;
    					sum = sum + (double)(difT[p])/(double)(lenT[p]);
    					pairs = pairs + 1;

    					// Reinitialize
    					difT[p] = 0;
    					lenT[p] = 0;
    				}
    				double averageWt = (double)( piF[chr-2][w-1] + piS[chr-2][w-1] )/2;
    				double piTot = (double)sum/(double)pairs;
    				fst[chr-2][w-1] = (double)(piTot - averageWt)/(double)piTot;
    				if (fst[chr-2][w-1] > 1.0 || fst[chr-2][w-1] < 0.0) {
					// System.out.println("Problem at: " + piTot + "\t" + averageWt + "\t" + piTot + "\t" + piF[chr-2][w-1] + "\t" + piS[chr-2][w-1]);
				}
				
				// Reinitialize
				ss = 0;
				sf = 0;
				lenSs = 0;
				lenFs = 0;

    				w = w + 1;
    			}










	
    			// Calculate, For Santo!
    			//
    			int pair = 0;
    			boolean anyN = false;
    			boolean segrega = false;
    			for (int s1 = 0; s1 < index1.length - 1; s1++) {
    				//
    				// For pi
    				//
    				char base1 = splitSnp[index1[s1]].charAt(0);
    				
				for (int s2 = s1+1; s2 < index1.length; s2++) {
    					//
    	    				char base2 = splitSnp[index1[s2]].charAt(0);
            				if (base1 != 'N' && base2 != 'N') {
            					lenS[pair] = lenS[pair] + 1;
            					if (base1 != base2) {
            						segrega = true;
                					diffS[pair] = diffS[pair] + 1;
            					}
            				} else {
            					anyN = true;
            				}
            				pair = pair + 1;
            			}
    			}
    			
						
    			//
    			// For Watterson's theta
    			if (!anyN) {
    				lenSs = lenSs + 1;
    				if (segrega) {
    					ss = ss + 1;
    				}
    			}
    			
    			// Calculate, For Fogo!
    			//
    			pair = 0;
    			anyN = false;
    			segrega = false;
    			for (int f1 = 0; f1 < index2.length - 1; f1++) {
    				//
    				// For pi
    				//
    				char base1 = splitSnp[index2[f1]].charAt(0);
    				
				for (int f2 = f1+1; f2 < index2.length; f2++) {
	    				//
					char base2 = splitSnp[index2[f2]].charAt(0);
	    				if (base1 != 'N' && base2 != 'N') {
        					lenF[pair] = lenF[pair] + 1;
        					if (base1 != base2) {
        						segrega = true;
            						diffF[pair] = diffF[pair] + 1;
        					}
        				} else {
        					anyN = true;
        				}
        				pair = pair + 1;
        			}
			}
    			//
    			// For Watterson's theta
    			if (!anyN) {
    				lenFs = lenFs + 1;
    				if (segrega) {
    					sf = sf + 1;
    				}
    			}
    			
    			// Calculate, Between populations!
    			//
    			pair = 0;
    			for (int f1 = 0; f1 < index1.length; f1++) {
    				//
    				// For pi
    				//
    				char base1 = splitSnp[index1[f1]].charAt(0);
    				
				for (int f2 = 0; f2 < index2.length; f2++) {
	    				//
					char base2 = splitSnp[index2[f2]].charAt(0);
	    				if (base1 != 'N' && base2 != 'N') {
        					lenB[pair] = lenB[pair] + 1;
        					if (base1 != base2) {
            						diffB[pair] = diffB[pair] + 1;
        					}
        				}
        				pair = pair + 1;
        			}
			}
    			// 



    			// Calculate, pi overall (cvi), for Fst!
    			//
    			pair = 0;
    			for (int s = 0; s < indexTot.length; s++) {
    				char base1 = splitSnp[indexTot[s]].charAt(0);
    				
				for (int s2 = 0; s2 < indexTot.length; s2++) {
    	    				if (s != s2) {
    	    					//
        					char base2 = splitSnp[indexTot[s2]].charAt(0);
        	    				if (base1 != 'N' && base2 != 'N') {
                					lenT[pair] = lenT[pair] + 1;
                					if (base1 != base2) {
                    						difT[pair] = difT[pair] + 1;
                					}
                				}
                				pair = pair + 1;
    	    				}
            			}
    			}
	        	chrMem = chr;
		}



			// Done
			
			
			
			// Write out
			//

			// For between
			//
			Writer writerBmin = new FileWriter(results + "minPiBtw.txt");
			PrintWriter outBmin = new PrintWriter(writerBmin);
			
			Writer writerB = new FileWriter(results + "piBtw.txt");
			PrintWriter outB = new PrintWriter(writerB);
			
			Writer writerBmax = new FileWriter(results + "maxPiBtw.txt");
			PrintWriter outBmax = new PrintWriter(writerBmax);
			
			int[] youngWin = {0, 0, 0, 0, 0};
			int totWin = 0;
			for (int c=0; c<piBmin.length; c++) {
				for (int p=0; p<piBmin[c].length; p++) {
					outBmin.print(piBmin[c][p] + "\t");
					if (piBmin[c][p] < 0.00071) {
						youngWin[0] = youngWin[0] + 1;
					}
					if (piBmin[c][p] < 0.00056) {
                                                youngWin[1] = youngWin[1] + 1;
                                        }
					if (piBmin[c][p] < 0.00042) {
                                                youngWin[2] = youngWin[2] + 1;
                                        }
					if (piBmin[c][p] < 0.00028) {
                                                youngWin[3] = youngWin[3] + 1;
                                        }
					if (piBmin[c][p] < 0.00014) {
                                                youngWin[4] = youngWin[4] + 1;
                                        }
					totWin = totWin + 1;
				}
				outBmin.print("\n");
			}
			outBmin.close();


			Writer writerNyoung = new FileWriter(results + "numYoungWin.txt");
			PrintWriter outYoung = new PrintWriter(writerNyoung);
			for (int print = 0; print<5; print++) {
				outYoung.print(youngWin[print] + "\t");
			}
			outYoung.print(totWin + "\n");
			outYoung.close();


			for (int c=0; c<piBmax.length; c++) {
				for (int p=0; p<piBmax[c].length; p++) {
					outBmax.print(piBmax[c][p] +"\t");
				}
				outBmax.print("\n");
			}
			outBmax.close();

			for (int c=0; c<piB.length; c++) {
				for (int p=0; p<piB[c].length; p++) {
					outB.print(piB[c][p] +"\t");
				}
				outB.print("\n");
			}
			outB.close();



			// For Santo
			//
			Writer writer = new FileWriter(results + "piSanto.txt");
			PrintWriter out = new PrintWriter(writer);
			
			Writer writerSM = new FileWriter(results + "piSantoMax.txt");
			PrintWriter outSM = new PrintWriter(writerSM);

			for (int c=0; c<piS.length; c++) {
				for (int p=0; p<piS[c].length; p++) {
					out.print(piS[c][p]);
					out.print("\t");
				}
				out.print("\n");
			}
			out.close();

			for (int c=0; c<piSmax.length; c++) {
				for (int p=0; p<piSmax[c].length; p++) {
					outSM.print(piSmax[c][p]);
					outSM.print("\t");
				}
				outSM.print("\n");
			}
			outSM.close();
			
			Writer writerW = new FileWriter(results + "thetaWSanto.txt");
			PrintWriter outW = new PrintWriter(writerW);
			
			for (int c=0; c<thetaWS.length; c++) {
				for (int p=0; p<thetaWS[c].length; p++) {
					outW.print(thetaWS[c][p]);
					outW.print("\t");
				}
				outW.print("\n");
			}
			outW.close();
			
			Writer writerT = new FileWriter(results + "tajdSanto.txt");
			PrintWriter outT = new PrintWriter(writerT);
			
			for (int c=0; c<tajDs.length; c++) {
				for (int p=0; p<tajDs[c].length; p++) {
					outT.print(tajDs[c][p]);
					outT.print("\t");
				}
				outT.print("\n");
			}
			outT.close();
			
			//
			// For Fogo
			//
			Writer writer2 = new FileWriter(results + "piFogo.txt");
			PrintWriter out2 = new PrintWriter(writer2);
			
			Writer writerFM = new FileWriter(results + "piFogoMax.txt");
			PrintWriter outFM = new PrintWriter(writerFM);
			
			for (int c=0; c<piF.length; c++) {
				for (int p=0; p<piF[c].length; p++) {
					out2.print(piF[c][p]);
					out2.print("\t");
				}
				out2.print("\n");
			}
			out2.close();
		
			for (int c=0; c<piFmax.length; c++) {
				for (int p=0; p<piFmax[c].length; p++) {
					outFM.print(piFmax[c][p]);
					outFM.print("\t");
				}
				outFM.print("\n");
			}
			outFM.close();
			

			Writer writerW2 = new FileWriter(results + "thetaWFogo.txt");
			PrintWriter outW2 = new PrintWriter(writerW2);
			
			for (int c=0; c<thetaWF.length; c++) {
				for (int p=0; p<thetaWF[c].length; p++) {
					outW2.print(thetaWF[c][p]);
					outW2.print("\t");
				}
				outW2.print("\n");
			}
			outW2.close();
			
			Writer writerfT = new FileWriter(results + "tajdFogo.txt");
			PrintWriter outfT = new PrintWriter(writerfT);
			
			for (int c=0; c<tajDf.length; c++) {
				for (int p=0; p<tajDf[c].length; p++) {
					outfT.print(tajDf[c][p]);
					outfT.print("\t");
				}
				outfT.print("\n");
			}
			outfT.close();
			
			
			
			// Write Fst
			// 
			Writer writerF = new FileWriter(results + "fst.txt");
			PrintWriter outF = new PrintWriter(writerF);
			
			for (int c=0; c<thetaWF.length; c++) {
				for (int p=0; p<fst[c].length; p++) {
					outF.print(fst[c][p]);
					outF.print("\t");
				}
				outF.print("\n");
			}
			outF.close();
			
			outLog.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Pairwise_shore_cviMor_forWinSimula pairwise_shore_cviMor_forWinSimula = new Pairwise_shore_cviMor_forWinSimula();
		pairwise_shore_cviMor_forWinSimula.setMatrixFile(args[0], args[1], args[2], args[3], args[4], args[5]);
	}
}
