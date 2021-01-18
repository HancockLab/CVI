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

public class SlocusCoverage {
	
	public SlocusCoverage() {}
	
	public void setFileToConvert(String filename){
		
		try {
			String[] refNames = {"Chr4", "hapC", "hapAC", "hapB", "Ah03_C1", "Ah03_C2", "Ah03_C4", "Ah03_C5", "Ah03_C6", "Ah03_C8", "Ah13_C1", "Ah13_C2", "Ah13_C3", "Ah13_C4", "Ah15_Ca1", "Ah15_Ca2", "Ah15_Ca3", "Ah15_Ca5", "Ah15_Ca6", "Ah15_Ca7", "Ah15_Ca8", "Ah15_Ca9", "Ah15_Cu2", "Ah15_Cu3", "Ah15_Cu4", "Ah20_C2", "Ah20_C3", "Ah20_C4", "Ah32_C1", "Ah32_C3", "Ah32_C4", "CapsellaRubella", "lyrata_S16", "lyrata_S38", "lyrata_S50", "lyrata_Sb", "Ah03C3_embl", "Ah13C5_embl", "Ah15Ca4_embl", "Ah15Cu1_embl", "Ah20C1_embl", "Ah28U1_embl", "Ah28U2_embl", "Ah28Ca1_embl", "Ah28Ca3_embl", "Ah32C2_embl", "Ah32C5_embl", "Ah43C3_embl", "Ah43C4_embl", "Ah43C5_embl", "Ah43C6_embl", "Ah43C7_embl", "Ah43C8_embl", "Al01C1_embl", "Al01C2_embl", "Al14C1_embl", "Al14C2_embl", "Al18Ca1_embl", "Al18Ca2_embl", "Al18Ca3_embl", "Al18Cu1_embl", "Al18Cu2_embl", "Al39C1_embl", "Al39C2_embl", "Al39C3_embl", "Al39C4_embl", "Al39C5_embl", "AhSRK12_EU075133.1", "AhSRK21_EU075142.1", "AhSRK27_EU878012.1", "AkSRKD", "AlSRK02_AY186763.1", "AlSRK07_AY186765.1", "AlSRK15_AY186771.1", "AlSRK23_AF328997.2", "AlSRK35_EU878021.1", "AlSRK45_EU878026.1", "gi|442557142|gb|KC207415.1|", "gi|442557140|gb|KC207414.1|", "gi|197116101|dbj|AB367833.1|", "gi|156637303|gb|EU039782.1|", "gi|156637025|gb|EU039504.1|", "gi|156636941|gb|EU039321.1|", "gi|156636846|gb|EU039226.1|", "gi|156636750|gb|EU039130.1|", "gi|156636655|gb|EU039035.1"};
			
			int[] lengthTot = {3253, 117360, 24696, 56105, 12888, 1223, 3955, 19265, 5565, 925, 46029, 1107, 2137, 13423, 36209, 9297, 6049, 26447, 3026, 6503, 4372, 6828, 2968, 26001, 14428, 28216, 6401, 4409, 24522, 2507, 15146, 72853, 99048, 85472, 115380, 116781, 12754, 25196, 9812, 41660, 42542, 68975, 32554, 63904, 17474, 25079, 47589, 3150, 829, 34958, 5206, 36265, 4856, 50758, 30867, 71234, 23881, 48782, 23297, 18188, 21997, 39655, 12578, 3366, 18739, 32763, 22614, 260, 260, 260, 260, 260, 260, 260, 260, 260, 260, 487, 3987, 697, 417, 557, 277, 487, 487, 417};
			
			long[] avCov = new long[refNames.length];
			long[] len = new long[refNames.length];
			long[] percCov = new long[refNames.length];
			for (int l=0; l<refNames.length; l++) {
				avCov[l] = 0;
				len[l] = 0;
				percCov[l] = 0;
			}
			
			// Open mpileup 
			File mpilFile = new File(filename);
			Scanner scanner = new Scanner(mpilFile);
				
			while ( scanner.hasNextLine() ) {
				String snp1 = scanner.nextLine();
			       	String[] splitSnp = snp1.split("\t");
		       	
				// Mind the header now
				if (snp1.charAt(0) == '#') {
					
				} else {
					// Header is over
					
					if (!splitSnp[0].equals("Chr4") || (splitSnp[0].equals("Chr4") && Integer.parseInt(splitSnp[1]) > 11383884 && Integer.parseInt(splitSnp[1]) < 11387136) ) {		// Chr4:11383884-11387136
						for (int l=0; l<refNames.length; l++) {
							if (splitSnp[0].equals(refNames[l])) {
								avCov[l] = avCov[l] + Integer.parseInt(splitSnp[3]);
								len[l] = Integer.parseInt(splitSnp[1]);
								if (Integer.parseInt(splitSnp[3]) > 0) {
									percCov[l] = percCov[l] + 1;
								}
							}
						}
					}
				}
			}

			// Cut the 'N's in the ref of lz-0 hapC
                        for (int l=0; l<refNames.length; l++) {
				if (refNames[l].charAt(0) == 'g' && refNames[l].charAt(1) == 'i' && refNames[l].charAt(2) == '|') {
					len[l] = len[l] - 1260;
				}
			}




			String numId = filename.split("/")[filename.split("/").length-1].split("\\.")[0];
			System.out.println(numId);


			if (numId.equals("9999")) {
				Writer writer1 = new FileWriter(filename + "_head.txt");
                        	PrintWriter out1 = new PrintWriter(writer1);
                        	out1.print("ID" + "\t");
                        	for (int l=0; l<refNames.length; l++) {
                        	        out1.print(refNames[l] + "\t");
                        	}
                        	out1.print("\n");
                        	out1.close();
			}



                        // Open also the writing file on percentage covered

                        Writer writer3 = new FileWriter(filename + "_percCovered.txt");
                        PrintWriter out3 = new PrintWriter(writer3);
                        out3.print(numId + "\t");
                        for (int l=0; l<refNames.length; l++) {
                                if (percCov[l] != 0) {
                                        out3.print((double)(percCov[l])/(double)(lengthTot[l]) + "\t");
                                } else {
                                        out3.print("0.0" + "\t");
                                }
                        }
                        out3.print("\n");
                        out3.close();




			// Open also the writing file

			Writer writer = new FileWriter(filename + "_cov.txt");
			PrintWriter out = new PrintWriter(writer);
			out.print(numId + "\t");
			for (int l=0; l<refNames.length; l++) {
				if (avCov[l] != 0) {
					out.print((double)(avCov[l])/(double)(len[l]) + "\t");
				} else {
					out.print("0.0" + "\t");
				}
			}
			out.print("\n");
			out.close();
	



			// In case, to check
                        
                        Writer writer2 = new FileWriter(filename + "_covToCheck.txt");
                        PrintWriter out2 = new PrintWriter(writer2);
                        out2.print(numId + "\t" + "avCov[l]" + "\t" + "len[l]" + "\t" + "percCov[l]" + "\t" + "lengthTot[l]" + "\n");
                        for (int l=0; l<refNames.length; l++) {
				if (avCov[l] != 0) {
                                	out2.print(refNames[l] + "\t" + avCov[l] + "\t" + len[l] + "\t" + percCov[l] + "\t" + lengthTot[l] + "\n");
                        	} else {
					out2.print(refNames[l] + "\t" + "0.0" + "\t" + "0.0" + "\t" + "0.0" + "\t" + "0.0" + "\n");
				}
			}
                        out2.close();





		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SlocusCoverage slocusCoverage = new SlocusCoverage();
		slocusCoverage.setFileToConvert(args[0]);
	}
}
