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

public class Simulations_manage {
	public Simulations_manage() {}
	
	public void setMatrixFile(String vcfName, String iterate, String chromosome){
		
		try {
			int[] win = {5000, 10000, 50000, 100000};
			
			for (int winSizeInd =0; winSizeInd<win.length; winSizeInd++) {
				int winSize = win[winSizeInd];
				
				int areSamp = 11;
				int relSamp = 17;
				int[] areInd = new int[areSamp];
				int[] relInd = new int[relSamp];
				int areC = 0;
				int relC = 0;
				
				int[] difAre = new int[areInd.length*(areInd.length-1)/2];
				int[] lenAre = new int[areInd.length*(areInd.length-1)/2];
				int[] difRel = new int[relInd.length*(relInd.length-1)/2];
				int[] lenRel = new int[relInd.length*(relInd.length-1)/2];

				int[][] jsfs = new int[areSamp + 1][relSamp + 1];
				for (int a=0; a<jsfs.length; a++) {
					for (int r=0; r<jsfs[0].length; r++) {
						jsfs[a][r] = 0;
					}
				}
				
				int posMem = 1;
				int chrMem = 1;
				
				Writer writer = new FileWriter(vcfName + "piAreOverPiRel_" + iterate + "_" + winSize + "_sim.txt");
				PrintWriter out = new PrintWriter(writer);
				
				Writer writer2 = new FileWriter(vcfName + "piAre_" + iterate + "_" + winSize + "_sim.txt");
				PrintWriter out2 = new PrintWriter(writer2);
				
				Writer writer3 = new FileWriter(vcfName + "piRel_" + iterate + "_" + winSize + "_sim.txt");
				PrintWriter out3 = new PrintWriter(writer3);
				
				long[] diffGenom = new long[areInd.length*(areInd.length-1)/2];
				for (int a=0; a<areInd.length; a++) {
					diffGenom[a] = 0;
				}
				// Open the file
				
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
								if (Integer.parseInt(splitSnp[h].split("_")[1]) >= areSamp && Integer.parseInt(splitSnp[h].split("_")[1]) < (areSamp+relSamp)) {
									relInd[relC] = h;
									relC = relC + 1;
								}
							}
							System.out.print("Are: " + Arrays.toString(areInd) + "\n");
							System.out.print("Rel: " + Arrays.toString(relInd) + "\n");
						}
					} else {
						//
						// Here, real stuff
						//
						// Get jsfs
						int areSnp = 0;
						int relSnp = 0;
						for (int a=0; a<areInd.length; a++) {
							if (Integer.parseInt(splitSnp[areInd[a]]) == 1) {
								areSnp = areSnp + 1;
							}
						}
						for (int r=0; r<relInd.length; r++) {
							if (Integer.parseInt(splitSnp[relInd[r]]) == 1) {
								relSnp = relSnp + 1;
							}
						}
						jsfs[areSnp][relSnp] = jsfs[areSnp][relSnp] + 1;
						
						
						// Now think about pi in windows
						
						int chr = Integer.parseInt(splitSnp[0]);
						int pos = Integer.parseInt(splitSnp[1]);
						
						if (chrMem == chr) {
							if ((int)pos/winSize > (int)posMem/winSize) {
								// Next window: output the previous

								double pi_are = 0.0;
								for (int p=0; p<difAre.length; p++) {
									pi_are = pi_are + (double)(difAre[p]/(double)winSize);
								}
								pi_are = pi_are/difAre.length;
								
								double pi_rel = 0.0;
								for (int p=0; p<difRel.length; p++) {
									pi_rel = pi_rel + (double)(difRel[p]/(double)winSize);
								}
								pi_rel = pi_rel/difRel.length;
								
								out.print(((double)pi_are/(double)pi_rel) + "\t");
								out2.print(pi_are + "\t");
								out3.print(pi_rel + "\t");
								
								difAre = new int[areInd.length*(areInd.length-1)/2];
								lenAre = new int[areInd.length*(areInd.length-1)/2];
								difRel = new int[relInd.length*(relInd.length-1)/2];
								lenRel = new int[relInd.length*(relInd.length-1)/2];
							}
							// Same window, keep counting
							// Madeira
							int runA = 0;
							for (int are=0; are<areInd.length-1; are++) {
								for (int are2=are+1; are2<areInd.length; are2++) {
									if (splitSnp[areInd[are]].charAt(0) != 'N' && splitSnp[areInd[are2]].charAt(0) != 'N') {
										lenAre[runA] = lenAre[runA] + 1;
										if (splitSnp[areInd[are]].charAt(0) != splitSnp[areInd[are2]].charAt(0)) {
											// System.out.println("ciao!");
											difAre[runA] = difAre[runA] + 1;
											diffGenom[runA] = diffGenom[runA] + 1;
										}
									}
									runA = runA + 1;
								}					
							}
							// Relicts
							int runR = 0;
							for (int rel=0; rel<relInd.length-1; rel++) {
								for (int rel2=rel+1; rel2<relInd.length; rel2++) {
									if (splitSnp[relInd[rel]].charAt(0) != 'N' && splitSnp[relInd[rel2]].charAt(0) != 'N') {
										lenRel[runR] = lenRel[runR] + 1;
										if (splitSnp[relInd[rel]].charAt(0) != splitSnp[relInd[rel2]].charAt(0)) {
											difRel[runR] = difRel[runR] + 1;										
										}
									}
									runR = runR + 1;
								}					
							}
						} else {
							difAre = new int[areInd.length*(areInd.length-1)/2];
							lenAre = new int[areInd.length*(areInd.length-1)/2];
							difRel = new int[relInd.length*(relInd.length-1)/2];
							lenRel = new int[relInd.length*(relInd.length-1)/2];
							
							out.print("\n");
							out2.print("\n");
							out3.print("\n");
							System.out.print(chrMem);
						}
						posMem = pos;
						chrMem = chr;
					}
				}
				out.print("\n");
				out2.print("\n");
				out3.print("\n");
				
				out.close();
				out2.close();
				out3.close();

				Writer writer4 = new FileWriter(vcfName + "jsfs_" + iterate + "_sim.txt");
				PrintWriter out4 = new PrintWriter(writer4);
				
				for (int a=0; a<jsfs.length; a++) {
					for (int r=0; r<jsfs[0].length; r++) {
						out4.print(jsfs[a][r] + "\t");
					}
					out4.print("\n");
				}
				out4.close();

				int[] chrL = {30427671, 19698289, 23459830, 18585056, 26975502};

				Writer writerT = new FileWriter(vcfName + "_" + iterate + "_theta_Ne.txt");
                                PrintWriter outT = new PrintWriter(writerT);
				long sumPi = 0;
				for (int a=0; a<diffGenom.length; a++) {
					sumPi = sumPi + diffGenom[a];
				}
				double theta = (double)(sumPi)/((double)(diffGenom.length)*(double)(chrL[Integer.parseInt(chromosome)]));
				double ne = theta/(4*7.1*0.000000001);
				outT.print(theta + "\t" + ne + "\n");
				outT.close();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Simulations_manage simulations_manage = new Simulations_manage();
		simulations_manage.setMatrixFile(args[0], args[1], args[2]);
	}
}
