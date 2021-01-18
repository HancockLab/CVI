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

public class Build_recom_rate_infile_chromoPainter {
	
	private Double[] phisicalMap = null;
	
	private Build_recom_rate_infile_chromoPainter() {}
	

	public void setSnpFile(String filename){
		
		try {
			Boolean xpEhh = false;

			String wereWe = filename;
			File param = new File(wereWe);
			Scanner scannerP = new Scanner(param);
			
			// Count SNps
			int snps =0;
			while ( scannerP.hasNextLine() ) { 
				scannerP.nextLine();
				snps = snps +1;
			}
			//
			
			int[] checkChr = new int[snps];
			int[] positions = new int[snps];
			char[] refs = new char[snps];
			char[] alts = new char[snps];
			
			String[] names = new String[snps];
			phisicalMap = new Double[snps];
			
			snps=0;
			scannerP = new Scanner(param);
			while ( scannerP.hasNextLine() ) { 
				String snp1 = scannerP.nextLine();
	        	String[] splitSnp1 = snp1.split("\t");
	        	
        		checkChr[snps] = Integer.parseInt(splitSnp1[0]);
        		positions[snps] = Integer.parseInt(splitSnp1[3]);
        		names[snps] = splitSnp1[1];
        		phisicalMap[snps] = 0.0;
			
			if (xpEhh) {
				refs[snps] = splitSnp1[4].charAt(0);
				alts[snps] = splitSnp1[5].charAt(0);
			}
        		
	        	snps = snps+1;
			}
			
			
			
			
			
			// Write all genome
			

			Writer writerAll = new FileWriter(wereWe + "_recomRateInfile");
			PrintWriter outAll = new PrintWriter(writerAll);
			
			outAll.print("start.pos recom.rate.perbp"+ "\n");
			// For every chromosome separately
			
			
			
			Writer writerA = new FileWriter(wereWe + "_withPhysicDist.map");
			PrintWriter outA = new PrintWriter(writerA);
			
			Writer writerS = new FileWriter(wereWe + "_forSweepFinder.map");
                        PrintWriter outS = new PrintWriter(writerS);
			
			Writer writerXp = new FileWriter(wereWe + "_forXpEhh.map");
                        PrintWriter outXp = new PrintWriter(writerXp);
			
			Writer writerXpClr = new FileWriter(wereWe + "_forXpClr.map");	
			PrintWriter outXpClr = new PrintWriter(writerXpClr);

			for (int c=0; c<5; c++) {
				System.out.println("Chr: " + c);
				
				
				// charge the map
				
				// File map = new File("/Volumes/MicroTera/analyses/hapmix/results/mapFrom-mapsKHCF.xls-Haldane_crossP10_chr" + c + ".txt");
				// File map = new File("/Volumes/Aner/backupMicrotera/hapmix/results/mapFrom-mapsKHCF.xls-Haldane_crossP10_chr" + c + ".txt");
				// File map = new File("/home/lv70590/Andrea/data/mapFrom-mapsKHCF.xls-Haldane_crossP10_chr" + c + ".txt");
				File map = new File("/home/fulgione/data/mapFrom-mapsKHCF.xls-Haldane_crossP10_chr" + c + ".txt");
				
				int brakes =0;
				Scanner scannerM = new Scanner(map);
				scannerM.nextLine();
				while ( scannerM.hasNextLine() ) { 
					scannerM.nextLine();
					brakes = brakes+1;
				}
				
				int[] brakeChr = new int[brakes];
				int[] brakePos = new int[brakes];
				Double[] brakeDist = new Double[brakes];
				
				brakes = 0;
				scannerM = new Scanner(map);
				scannerM.nextLine();
				
				while ( scannerM.hasNextLine() ) { 
					String snp2 = scannerM.nextLine();
		        		String[] splitSnp2 = snp2.split("\t");
		        	
		        		brakeChr[brakes] = Integer.parseInt(splitSnp2[0].split("-")[0]);
		        		brakePos[brakes] = Integer.parseInt(splitSnp2[0].split("-")[1]);
		        		brakeDist[brakes] = Double.parseDouble(splitSnp2[2]);					// 1: Kosambi 2: Haldane 3: Carter Falconer Distances
		        	
					// System.out.println(snp2);
					// System.out.println(brakePos[brakes]);
					
		        		brakes = brakes+1;
				}
				
				// for (int p=0; p<brakePos.length; p++) {
					// System.out.println(brakeDist[p]);
					// System.out.println(brakePos[p]);
				// }
				// System.out.println(Arrays.toString(brakeDist));
				// System.out.println(Arrays.toString(brakePos));
				// System.out.println(Arrays.toString(brakeChr));
				/*for (int r=1; r<100; r++) {
					System.out.println(positions[positions.length - r]);
				}
				*/
				Double previousDensity = 0.000000018;
				Double density =0.0;
				
				phisicalMap[phisicalMap.length-1] = -9.0;
				
				
				
				
				
				
				for (int snp=1; snp<phisicalMap.length; snp++) {
					
					boolean inTheRightChr = false;
					if (checkChr[snp - 1] == c + 1) {
						inTheRightChr = true;
					}

					
					if (inTheRightChr) {
						
						if (positions[snp] <= brakePos[0]) {
							// System.out.println(1);
							phisicalMap[snp-1] = 0.000000018;
						} else {
							for (int run=1; run<brakePos.length; run++) {
								
								if ( (positions[snp] <= brakePos[run]) && (positions[snp] > brakePos[run-1]) ){
									// System.out.println(2);

									density = (brakeDist[run]-brakeDist[run-1])/(brakePos[run]-brakePos[run-1]);
									
									if ( positions[snp-1] >= brakePos[run-1]) {
										phisicalMap[snp-1] = density;
									} else {
										Double num = density * ( positions[snp] - brakePos[run-1] );
										int denom = ( positions[snp] - brakePos[run-1] );
										
										if (run > 1) {
											if ( positions[snp-1] >= brakePos[run-2] ) {
												num = num + previousDensity * ( brakePos[run-1] - positions[snp-1] );
												denom = denom + ( brakePos[run-1] - positions[snp-1] );
											} else {
												
												int runBack=run-2;
												while ( runBack >= 1 && positions[snp-1] <  brakePos[runBack] ) {
													
													Double densityBack = (brakeDist[runBack+1]-brakeDist[runBack])/(brakePos[runBack+1]-brakePos[runBack]);
													
													num = num + densityBack * (brakePos[runBack+1]-brakePos[runBack]);
													denom = denom + (brakePos[runBack+1]-brakePos[runBack]);
													runBack = runBack-1;
												}
												Double densityBack = 0.000000018;
												if (runBack > 1) {
													densityBack = (brakeDist[runBack]-brakeDist[runBack-1])/(brakePos[runBack]-brakePos[runBack-1]); 
												}
												num = num + densityBack * ( brakePos[runBack] - positions[snp-1] );
												denom = denom + ( brakePos[runBack] - positions[snp-1] );
											}
										}
										phisicalMap[snp-1] = (double)(num) / (double)(denom);
										
									}
									previousDensity = density;
								} else {
									if ( (positions[snp] > brakePos[brakePos.length-1]) && (positions[snp-1] < brakePos[brakePos.length-1]) ) {
										// System.out.println(3);
										
										Double num = previousDensity * ( brakePos[brakePos.length-1] - positions[snp-1] );
										int denom = (  brakePos[brakePos.length-1] - positions[snp-1] );

										num = num + 0.000000018 * ( positions[snp] - brakePos[brakePos.length-1] );
										denom = denom + (positions[snp] - brakePos[brakePos.length-1]);
										
										phisicalMap[snp-1] = (double)(num) / (double)(denom);
										
										
										
									}
									if ( (positions[snp] > brakePos[brakePos.length-1]) && (positions[snp-1] > brakePos[brakePos.length-1]) ) {
										// System.out.println(4);
										phisicalMap[snp-1] = 0.000000018;
									}
								}
							}
						}
						
						if ( (checkChr[snp] > c + 1) ) {
							phisicalMap[snp - 1] = -9.0;
						}
						
						// If we are on the same chromosome
					}
					
				
								
				}
				
				
				/*
				// Scale by (1-F) selfing rate, 95% therefore recomb rate is in nature 1/20 of what is in the lab... 1 recombination event every 20 generations
				for (int s=0; s<phisicalMap.length; s++) {
					if (phisicalMap[s] == 0.0) {
						phisicalMap[s] = 0.000000018;
					}
					if ( (phisicalMap[s] != 0.000000018) && (phisicalMap[s] != -9.0) ) {
						phisicalMap[s] = phisicalMap[s]/(double)(20);
					}
				}
				*/

				// Next chromosome:
				phisicalMap[phisicalMap.length-1] = -9.0;
				
				
				/*
				// Write parameters for rates file
				Writer writerH = new FileWriter(wereWe + "param_popShuffleX10" + c + "_withSelfing.rates");
				PrintWriter outH = new PrintWriter(writerH);
				outH.println(":sites:" + snps);
				for (int pos=0; pos<positions.length; pos++) {
					outH.print(positions[pos]);
					outH.print(" ");
				}
				outH.print("\n");
				for (int pos=0; pos<phisicalMap.length; pos++) {
					if (phisicalMap[pos] != 0.0) {
						outH.print(phisicalMap[pos]);
					} else {
						outH.print("0.000000018");
					}
					outH.print(" ");
				}
				outH.print("\n");
				outH.close();
				
				
				
				// Write parameters for snp files
				Writer writer = new FileWriter(wereWe + "param_popShuffleX10" + c + "_withSelfing.withPhisicalDist.map");
				PrintWriter out = new PrintWriter(writer);
				for (int s=0; s<checkChr.length; s++) {
					out.print(checkChr[s]);
					out.print("\t");
					out.print(names[s]);
					out.print("\t");
					out.print(phisicalMap[s]);
					out.print("\t");
					out.print(positions[s]);
					out.print("\n");
				}
				out.close();
				*/
				
				// Write parameters for chromosome painter recom_rate_infile
				/*
				Writer writerC = new FileWriter(wereWe + "ChromoPainterParam_popShuffleX10" + c + "_withSelfing.recomRateInfile");
				PrintWriter outC = new PrintWriter(writerC);
				outC.println("start.pos recom.rate.perbp");
				*/
				
				
				// System.out.println(Arrays.toString(phisicalMap));
				
			}
			
			outS.print("position" + "\t" + "rate" + "\n");
			double cumulPhysMap = 0.0;
			for (int s=0; s<positions.length; s++) {
				/*outC.print(positions[s]);
				outC.print(" ");
				outC.print(phisicalMap[s]);
				outC.print("\n");*/
				
				outAll.print(positions[s] + " ");
				
				if (phisicalMap[s] == 0.0) {
					phisicalMap[s] = 0.000000018;
				}
				if ( (phisicalMap[s] != 0.000000018) && (phisicalMap[s] != -9.0) ) {
					phisicalMap[s] = phisicalMap[s]*(double)(0.041);
				}
				
				outAll.print(phisicalMap[s] + "\n");
				
				if (s == 0) {
					outS.print(positions[s] + "\t" + "0" + "\n");
					if (xpEhh) {
						outXp.print(checkChr[s] + "_" + positions[s] + " " + positions[s] + " 0 " + refs[s] + " " + alts[s] + "\n");
						outXpClr.print(checkChr[s] + "_" + positions[s] + "\t" + checkChr[s] + "\t0.0000001\t" + positions[s] + "\t" + refs[s] + "\t" + alts[s] + "\n");
					}
				} else {
					outS.print(positions[s] + "\t" + phisicalMap[s - 1]*((double)(positions[s] - positions[s-1])) + "\n");
					if (xpEhh) {
						cumulPhysMap = cumulPhysMap + phisicalMap[s - 1]*((double)(positions[s] - positions[s-1]));
						outXp.print(checkChr[s] + "_" + positions[s] + " " + positions[s] + " " + cumulPhysMap + " " + refs[s] + " " + alts[s] + "\n");
						outXpClr.print(checkChr[s] + "_" + positions[s] + "\t" + checkChr[s] + "\t" + cumulPhysMap + "\t" + positions[s] + "\t" + refs[s] + "\t" + alts[s] + "\n");
					}
				}
				outA.print(checkChr[s] + "\t" + checkChr[s] + ":" + positions[s] + "\t" + phisicalMap[s] + "\t" + positions[s] + "\n");
			}
			// outC.close();
			//
			outS.close();
			outAll.close();
			outA.close();
			outXp.close();
			outXpClr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		Build_recom_rate_infile_chromoPainter build_recom_rate_infile_chromoPainter = new Build_recom_rate_infile_chromoPainter();
		build_recom_rate_infile_chromoPainter.setSnpFile(args[0]);
		// /Volumes/MicroTera/analyses/hapmix/results/ADM_params_0.map"); 
	}
}
