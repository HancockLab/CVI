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

public class Pi_relictVsAre {
	public Pi_relictVsAre() {}
	
	public void setMatrixFile(String fileName){
		
		try {
			int winSize = 10000;
			
			File matrix = new File(fileName);
			Scanner scanM = new Scanner(matrix);
			String head = scanM.nextLine();
			String[] splitHead = head.split("\t");
			int samples = splitHead.length - 3;
			System.out.println("We have: " + samples + " samples");
			
			// Get the samples to use
			int[] relID = {9871, 9947, 9887, 9600, 9554, 9832, 9542, 9944, 9598, 9905, 9555, 9543, 9533, 9869, 9549, 9837, 9879};
			int[] areID = {27154, 27153, 27152, 22638, 22022, 22019, 22017, 12908, 12763, 12762, 12761};
			
			int[] areInd = new int[areID.length]; 
			int[] relInd = new int[relID.length];

			for (int r=0; r<relID.length; r++) {
				for (int i=3; i<splitHead.length; i++) {
					if (relID[r] == Integer.parseInt(splitHead[i])) {
						relInd[r] = i;
					}
				}
			}

			for (int r=0; r<areID.length; r++) {
				for (int i=3; i<splitHead.length; i++) {
					if (areID[r] == Integer.parseInt(splitHead[i])) {
						areInd[r] = i;
					}
				}
			}
			System.out.println(Arrays.toString(areInd));
			System.out.println(Arrays.toString(relInd));
			
			// long[] chrL = {30427671, 19698289, 23459830, 18585056, 26975502};
			
			int[] difAre = new int[areInd.length*(areInd.length-1)/2];
			int[] lenAre = new int[areInd.length*(areInd.length-1)/2];
			int[] difRel = new int[relInd.length*(relInd.length-1)/2];
			int[] lenRel = new int[relInd.length*(relInd.length-1)/2];
			int[] difBetw = new int[relInd.length*areInd.length];
			int[] lenBetw = new int[relInd.length*areInd.length];
			
			int posMem = 1;
			int chrMem = 1;
			
			String results = "/global/lv70590/Andrea/analyses/pi_relVsAre/";
			
			Writer writer = new FileWriter(results + winSize + "_piAreOverPiRel.txt");
			PrintWriter out = new PrintWriter(writer);
			
			Writer writer2 = new FileWriter(results + winSize + "_piAre.txt");
			PrintWriter out2 = new PrintWriter(writer2);
			
			Writer writer3 = new FileWriter(results + winSize + "_piRel.txt");
			PrintWriter out3 = new PrintWriter(writer3);
			
			Writer writer4 = new FileWriter(results + winSize + "_fst.txt");
			PrintWriter out4 = new PrintWriter(writer4);
			
			while (scanM.hasNextLine()) {
				String snp = scanM.nextLine();
				String[] splitSnp = snp.split("\t");
				
				int chr = Integer.parseInt(splitSnp[0]);
				int pos = Integer.parseInt(splitSnp[1]);
				
				if (chrMem == chr) {
					// System.out.println((int)pos/winSize + "\t" + pos);
					if ((int)pos/winSize > (int)posMem/winSize) {
						// Next window: output the previous
						double pi_are = 0.0;
						for (int p=0; p<difAre.length; p++) {
							pi_are = pi_are + (double)(difAre[p]/(double)lenAre[p]);
						}
						
						double pi_rel = 0.0;
						for (int p=0; p<difRel.length; p++) {
							pi_rel = pi_rel + (double)(difRel[p]/(double)lenRel[p]);
						}
						
						double pi_betw = 0.0;
						for (int p=0; p<difBetw.length; p++) {
                                                        pi_betw = pi_betw + (double)(difBetw[p]/(double)lenBetw[p]);
                                                }
						
						//System.out.println((int)pos/winSize + "\t" + pi_are + "\t" + pi_rel + "\t" + pi_are/(double)difAre.length + "\t" + pi_rel/(double)difRel.length);
						out.print((double)((pi_are/(double)difAre.length)/(pi_rel/(double)difRel.length)) + "\t");
						out2.print(pi_are/(double)difAre.length + "\t");
						out3.print(pi_rel/(double)difRel.length + "\t");
						
						double piBAv = pi_betw/(double)difBetw.length;
						double fst = 
						
						out4.print(pi_betw/(double)difBetw.length + "\t");
						
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
									difAre[runA] = difAre[runA] + 1;										
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
			out.close();
			out2.close();
			out3.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Pi_relictVsAre pi_relictVsAre = new Pi_relictVsAre();
		pi_relictVsAre.setMatrixFile(args[0]);
	}
}
