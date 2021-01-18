package c.e.data_processing;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
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

public class Simulations_manage_areOnly {
	public Simulations_manage_areOnly() {}
	
	public void setMatrixFile(String vcfName, String chromosome){
		
		try {
				int areSamp = 11;
				int[] areInd = new int[areSamp];
				int areC = 0;
				
				long[] diffGenom = new long[areInd.length*(areInd.length-1)/2];
				for (int a=0; a<areInd.length; a++) {
					diffGenom[a] = 0;
				}
				
				Writer writer = new FileWriter(vcfName + "_sweepFinder.txt");
                                PrintWriter out = new PrintWriter(writer);
                                // out.print("position" + "\t" + "x" + "\t" + "n" + "\t" + "folded" + "\n");
                                
				Writer writerA = new FileWriter(vcfName + "_ADM_params.map");
                                PrintWriter outA = new PrintWriter(writerA);

	
				// Open the file
				int S=0;
				File vcfFile = new File(vcfName);
				Scanner scan = new Scanner(vcfFile);
				
				while ( scan.hasNextLine() ) {
					String snp = scan.nextLine();
					String[] splitSnp = snp.split("\t");
					// Mind header
					if (snp.charAt(0) == '#') {
						if (snp.charAt(1) != '#') {
							// Get indexes
							for (int h=9; h<splitSnp.length; h++) {
								if (Integer.parseInt(splitSnp[h].split("_")[1]) < areSamp) {
									areInd[areC] = h;
									areC = areC + 1;
								}
							}
							System.out.print("Are: " + Arrays.toString(areInd) + "\n");
						}
					} else {
						//
						// Here, real stuff
						//
						// Get allele frequency
						int areSnp = 0;
						for (int a=0; a<areInd.length; a++) {
							if (Integer.parseInt(splitSnp[areInd[a]]) == 1) {
								areSnp = areSnp + 1;
							}
						}
						if (areSnp > 0) {
							S = S+1;
						}
						
						int pos = Integer.parseInt(splitSnp[1]);
						
						out.print(pos + "\t" + areSnp + "\t" + areInd.length + "\t" + "0" + "\n");
        	                                outA.print((Integer.parseInt(chromosome)+1) + "\t" + (Integer.parseInt(chromosome)+1)+"_"+pos + "\t" + "0" + "\t" + pos + "\n");
	
						// Get theta pi
						//
						int runA = 0;
						for (int are=0; are<areInd.length-1; are++) {
							for (int are2=are+1; are2<areInd.length; are2++) {
								if (splitSnp[areInd[are]].charAt(0) != splitSnp[areInd[are2]].charAt(0)) {
									diffGenom[runA] = diffGenom[runA] + 1;
								}
								runA = runA + 1;
							}					
						}
					}
				}
				out.close();
				outA.close();

				int[] chrL = {30427671, 19698289, 23459830, 18585056, 26975502};

				Writer writerT = new FileWriter(vcfName + "_theta_S_Ne.txt");
                                PrintWriter outT = new PrintWriter(writerT);
				long sumPi = 0;
				for (int a=0; a<diffGenom.length; a++) {
					sumPi = sumPi + diffGenom[a];
				}
				double theta = (double)(sumPi)/((double)(diffGenom.length)*(double)(chrL[Integer.parseInt(chromosome)]));
				double ne = theta/(4*7.1*0.000000001);
				outT.print(theta + "\t" + ((double)S/(double)(chrL[Integer.parseInt(chromosome)])) + "\t" + ne + "\n");
				outT.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Simulations_manage_areOnly simulations_manage_areOnly = new Simulations_manage_areOnly();
		simulations_manage_areOnly.setMatrixFile(args[0], args[1]);
	}
}
