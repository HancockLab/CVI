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

public class CutThatReference {
	public CutThatReference() {}
	
	public void setFile(String fileName, String sampleStr){
		
		try {
			int sample = Integer.parseInt(sampleStr);
			
			// Open reference file
			File refFile = new File(fileName);
			Scanner scanRef = new Scanner(refFile);
			Boolean chrom = false;
			int[] chrL = new int[5];
			for (int c=0; c<5; c++) {
				chrL[c] = 0;
			}
			int chr = 0;
			
			while (scanRef.hasNextLine()) {
				String line = scanRef.nextLine();
				
				if (line.charAt(0) == '>') {
					chrom = false;
					if (line.charAt(1) == 'C' && line.charAt(2) == 'h' && line.charAt(3) == 'r') {
						chrom = true;
						chr = Character.getNumericValue(line.charAt(4)) -1;
						System.out.println(line);
					}
				} else {
					if (chrom) {
						for (int b=0; b<line.length(); b++) {
							chrL[chr] = chrL[chr] + 1;
						}
					}
				}
			}
			System.out.println(Arrays.toString(chrL));
			
			 char[][] ref = new char[5][];
			for (int c=0; c<5; c++) {
				ref[c] = new char[chrL[c]];
			}
			
			
			
			// Now the real thing
			
			scanRef = new Scanner(refFile);
			chrL = new int[5];
			for (int c=0; c<5; c++) {
				chrL[c] = 0;
			}
			
			while (scanRef.hasNextLine()) {
				String line = scanRef.nextLine();
				
				if (line.charAt(0) == '>') {
					chrom = false;
					if (line.charAt(1) == 'C' && line.charAt(2) == 'h' && line.charAt(3) == 'r') {
						chrom = true;
						chr = Character.getNumericValue(line.charAt(4)) -1;
						System.out.println(line);
					}
				} else {
					if (chrom) {
						for (int b=0; b<line.length(); b++) {
							if (line.charAt(b) == 'Y') {
								ref[chr][chrL[chr]] = 'C';
							} else {
								if (line.charAt(b) == 'N') {
									ref[chr][chrL[chr]] = 'A';
								} else {
									if (line.charAt(b) == 'W') {
										ref[chr][chrL[chr]] = 'A';
									} else {
										if (line.charAt(b) == 'M') {
											ref[chr][chrL[chr]] = 'A';
										} else {
											if (line.charAt(b) == 'K') {
												ref[chr][chrL[chr]] = 'G';
											} else {
												if (line.charAt(b) == 'S') {
													ref[chr][chrL[chr]] = 'C';
												} else {
													if (line.charAt(b) == 'R') {
														ref[chr][chrL[chr]] = 'A';
													} else {
														if (line.charAt(b) == 'D') {
															ref[chr][chrL[chr]] = 'A';
														} else {
															ref[chr][chrL[chr]] = line.charAt(b);
														}
													}
												}
											}
										}
									}
								}
							}
							chrL[chr] = chrL[chr] + 1;
						}
					}
				}
			}
			
			// Got the reference
			
        	
        	
			// Check if we know everything about matrix entries...
			char[] bases = {'0'};

	    		for (int c=0; c<5; c++) {
				for (int b=0; b<ref[c].length; b++) {
					Boolean thereIs = false;
					for (int chars=0; chars<bases.length; chars++) {
						if (ref[c][b] == bases[chars]) {
							thereIs = true;
						}
					}
					if (!thereIs) {
						char[] memory = bases;
						bases = new char[bases.length +1];
						for (int m=0; m<memory.length; m++) {
							bases[m] = memory[m];
						}
						bases[bases.length-1] = ref[c][b];
					}
				}
    		}
		System.out.println(Arrays.toString(bases));
	    	//
		// Checked matrix entries,
        	
			
			
			
			// Sample cutting points, for a coverage of 20X
			// (23829270*100)/119146348 = 20
			// 23829270 reads of 100 bases makes 20X coverage
			
			// int samples = 110;
			
			char[][] refMem = ref;
			
			String results = "/global/lv70590/Andrea/analyses/sharedStuff/refCut_mut/";
			
			for (int samp=sample; samp<sample+1; samp++) {
				System.out.println("sample: " + samp);
				
 				
				ref = refMem;
				
				// Introduce mutations
				
				int[][] mut = new int[5][];
				
				for (int c=0; c<5; c++) {
					int num = (int)Math.round(((double)chrL[c]/100000)*465);
					System.out.println(num);
					mut[c] = new int[num];
					
					Random randomGenerator = new Random();
					for (int boot=0; boot<mut[c].length; boot++) {
						mut[c][boot] = randomGenerator.nextInt(chrL[c]);
					}
					// System.out.println(Arrays.toString(startCut));
				}
				
				// Check randomness of mutations
				
				Writer writer = new FileWriter(fileName + "check_mut.txt");
				PrintWriter out = new PrintWriter(writer);
				for (int c=0; c<5; c++) {
					int[] summ = new int[35];
					for (int s=0; s<summ.length; s++) {
						summ[s] = 0;
					}
					for (int p=0; p<mut[c].length; p++) {
						summ[Math.round((mut[c][p])/(1000000))] = summ[Math.round((mut[c][p])/(1000000))] + 1;
					}
					for (int s=0; s<summ.length; s++) {
						out.print(summ[s] + "\t");
					}
					out.print("\n");
				}
				out.close();
	        	
				
				// Mutate reference
				Writer writer3 = new FileWriter(results + samp + "_realMuts.txt");
				PrintWriter out3 = new PrintWriter(writer3);
				
				for (int c=0; c<5; c++) {
					for (int m=0; m<mut[c].length; m++) {
						
						// Decide to what base we mutate
						char[] nucAll = {'A', 'T', 'C', 'G'};
						char[] nuc = new char[3];
						int run=0;
						for (int n=0; n<nucAll.length; n++) {
							if (nucAll[n] != ref[c][mut[c][m]]) {
								nuc[run] = nucAll[n];
								run = run+1;
							}
						}
						// Record before mutation
						out3.print("Chr" + (c+1) + "\t" + mut[c][m] + "\t" + ref[c][mut[c][m]] + "\t");
						
						Random randomGenerator = new Random();
						ref[c][mut[c][m]] = nuc[randomGenerator.nextInt(3)];
						
						// And after
						out3.print(ref[c][mut[c][m]] + "\n");
					}
				}
				out3.close();
				
				
				// Cut that reference
				
				int[][] startCut = new int[5][];
				
				for (int c=0; c<5; c++) {
					int num = Math.round(chrL[c]*25/100);
					System.out.println(num);
					startCut[c] = new int[num];
					
					int rand = 0;
					Random randomGenerator = new Random();
					for (int boot=0; boot<startCut[c].length; boot++) {
						startCut[c][boot] = randomGenerator.nextInt(chrL[c]);
					}
					// System.out.println(Arrays.toString(startCut));
				}
				
				/*
				Writer writer = new FileWriter(fileName + "check_rand.txt");
				PrintWriter out = new PrintWriter(writer);
				for (int c=0; c<5; c++) {
					int[] summ = new int[35];
					for (int s=0; s<summ.length; s++) {
						summ[s] = 0;
					}
					for (int p=0; p<startCut[c].length; p++) {
						summ[Math.round((startCut[c][p])/(1000000))] = summ[Math.round((startCut[c][p])/(1000000))] + 1;
					}
					for (int s=0; s<summ.length; s++) {
						out.print(summ[s] + "\t");
					}
					out.print("\n");
				}
				out.close();
	        		*/
				
				
				
				
				
				// Now write out a fastq
				
				Writer writer2 = new FileWriter(results + samp + "_mut.fastq");
				PrintWriter out2 = new PrintWriter(writer2);
				
				for (int c=0; c<5; c++) {
					for (int p=0; p<startCut[c].length; p++) {
						
						// Add a probability  of 0.5 to have a forward or backwords read
						Random randomGenerator = new Random();
						int forwBack = randomGenerator.nextInt(2);
						
						if (forwBack == 1) {
							// Read length						
							if (startCut[c][p]+100 < ref[c].length && startCut[c][p]-100 > 0) {
								out2.print("@SEQ_ID" + "\n");
								for (int r=0; r<100; r++) {
									out2.print(ref[c][startCut[c][p] + r]);
								}
								out2.print("\n" + "+" + "\n");
								for (int r=0; r<100; r++) {
									out2.print("F");
								}
								out2.print("\n");
							}
						} else {
							// Read length
							if (startCut[c][p]+100 < ref[c].length && startCut[c][p]-100 > 0) {
								out2.print("@SEQ_ID" + "\n");
								for (int r=100; r>0; r--) {
									if (ref[c][startCut[c][p] + r] == 'A') {
										out2.print('T');
									} else {
										if (ref[c][startCut[c][p] + r] == 'T') {
											out2.print('A');
										} else {
											if (ref[c][startCut[c][p] + r] == 'C') {
												out2.print('G');
											} else {
												if (ref[c][startCut[c][p] + r] == 'G') {
													out2.print('C');
												}
											}
										}
									}
								}
								out2.print("\n" + "+" + "\n");
								for (int r=0; r<100; r++) {
									out2.print("F");
								}
								out2.print("\n");
							}
						}
					}
				}
				out2.close();
				// Per sample
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CutThatReference cutThatReference = new CutThatReference();
		cutThatReference.setFile(args[0], args[1]);
	}
}
