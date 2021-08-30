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

public class ChromoP_manageResults {
	public ChromoP_manageResults() {}
	
	public void setMatrixFile(String foldName){
		
		try {
			File chromFold = new File(foldName);
			int iter = 1000;
			
			for (int can=0; can<251; can++) {
				
				File canFile = new File(chromFold + "/" + can);
				System.out.println(canFile.getName());
				File[] replicateFile = canFile.listFiles();
				int replicates = 5;
				// replicateFile.length;
				
				// System.out.println(replicates + " replicates");
				// System.out.println(Arrays.toString(replicateFile));
				





				int fileL = 0;
				for (int repl=0; repl < replicates; repl++) {                   // replicates
                                        File neF = new File(canFile + "/" + repl + "/results");
                                        System.out.print(repl + " ");

                                        File[] resultsFile = neF.listFiles();

					// Get likelyhood
                                        for (int r=0; r<resultsFile.length; r++) {
						if (resultsFile[r].length() != 0 && resultsFile[r].exists() && resultsFile[r].getName().split("\\.")[resultsFile[r].getName().split("\\.").length - 2].equals("EMprobs")) {
							Scanner scanL = new Scanner(resultsFile[r]);
                                        	        String like = null;
							int countLines = 0;
                                                	while ( scanL.hasNextLine() ) {
                                                        	like = scanL.nextLine();
								countLines = countLines + 1;
                                                	}
							if (countLines != 0) {
                                                		String[] splitL = like.split(" ");
								if (Integer.parseInt(splitL[0]) == iter) {
									fileL = fileL + 1;
								}
							}
						}
					}
				}
				double[] likelH = new double[fileL];
				fileL = 0;
				for (int repl=0; repl < replicates; repl++) {                   // replicates
                                        File neF = new File(canFile + "/" + repl + "/results");
                                        System.out.print(repl + " ");

                                        File[] resultsFile = neF.listFiles();

                                        // Get likelyhood
                                        for (int r=0; r<resultsFile.length; r++) {
						if (resultsFile[r].length() != 0 && resultsFile[r].exists() && resultsFile[r].getName().split("\\.")[resultsFile[r].getName().split("\\.").length - 2].equals("EMprobs")) {
						
                                        	        System.out.println(resultsFile[r].getAbsolutePath());
                                        	        Scanner scanL = new Scanner(resultsFile[r]);
							String like = null;
							int countLines = 0;
							while ( scanL.hasNextLine() ) {
								like = scanL.nextLine();
								countLines = countLines + 1;
							}
							if (countLines != 0) {
								String[] splitL = like.split(" ");
								if (Integer.parseInt(splitL[0]) == iter) {
									likelH[fileL] = Double.parseDouble(splitL[1]);
									fileL = fileL + 1;
                                   	     			}
							}
						}
                                	}
				}
				System.out.println(Arrays.toString(likelH));



























				String[][][] pops = new String[replicates][][];
				Double[][][] props = new Double[replicates][][];				
				int numPops = 0;

				for (int repl=0; repl < replicates; repl++) {			// replicates
					File neF = new File(canFile + "/" + repl + "/results");
					System.out.print(repl + " ");
					
					File[] resultsFile = neF.listFiles();
					// System.out.println(Arrays.toString(resultsFile));
					
					int propFiles = 0;
					for (int r=0; r<resultsFile.length; r++) {
						String resFileName = resultsFile[r].getName();
						// System.out.println(resFileName);
						if (resultsFile[r].length() != 0 && resultsFile[r].exists() && resFileName.split("\\.")[resFileName.split("\\.").length - 2].equals("prop")) {
							
							Scanner scanL = new Scanner(resultsFile[r]);
                                                        String like = null;
                                                        int l=0;
							while ( scanL.hasNextLine() ) {
                                                                like = scanL.nextLine();
								l = l+1;
                                                        }
                                                        if (l > 1) {
								propFiles = propFiles + 1;
							}
							numPops = like.split(" ").length;
						}
					}
					System.out.println("propFlies: " + propFiles);

					pops[repl] = new String[propFiles][];
					props[repl] = new Double[propFiles][];
					for (int f=0; f<propFiles; f++) {
						pops[repl][f] = new String[numPops-1];
						props[repl][f] = new Double[numPops-1];
					}
					// System.out.println(pops.length + " " + pops[0].length + " " + pops[0][0].length);
					
					propFiles = 0;
					for (int r=0; r<resultsFile.length; r++) {
						String resFileName = resultsFile[r].getName();
						// System.out.println(resFileName);

						if (resultsFile[r].length() != 0 && resultsFile[r].exists() && resFileName.split("\\.")[resFileName.split("\\.").length - 2].equals("prop")) {
							// System.out.println(resFileName);
							Scanner scanL = new Scanner(resultsFile[r]);
                                                        String like = null;
                                                        int l=0;
                                                        while ( scanL.hasNextLine() ) {
                                                                like = scanL.nextLine();
                                                                l = l+1;
                                                        }
                                                        if (l > 1) {
								Scanner scan = new Scanner(resultsFile[r]);							
								String lineN = scan.nextLine();
								String[] popStr = lineN.split(" ");
								
								String lineP = scan.nextLine();
								String[] propStr = lineP.split(" ");
								
								for (int run=1; run<propStr.length; run++) {
									pops[repl][propFiles][run-1] = popStr[run];
									props[repl][propFiles][run-1] = Double.parseDouble(propStr[run]);
								}
								propFiles = propFiles + 1;
							}
						}
					}
				}
                                System.out.print("\n");


				Writer writer = new FileWriter(foldName + can + "_propIterate.txt");
				PrintWriter out = new PrintWriter(writer);
				// System.out.println(Arrays.toString(pops[0][0]));

				boolean searchNonEmpty = false;
				for (int pp=0; pp<pops.length; pp++) {
					if (pops[pp] != null &&  pops[pp].length != 0) {
						if (pops[pp][0] != null && pops[pp][0].length != 0) {
							for (int p=0; p<pops[pp][0].length; p++) {
								if (!searchNonEmpty) {
									out.print(pops[pp][0][p] + "\t");
								}
							}
							searchNonEmpty = true;
						}
					}
				}
				out.print("\n");
				for (int r=0; r<replicates; r++) {				//replicates
					// System.out.println(pops[r].length);
					for (int f=0; f<props[r].length; f++) {
						for (int p=0; p<props[r][f].length; p++) {
							out.print(props[r][f][p] + "\t");
						}
						out.print("\n");
					}
				}
				out.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ChromoP_manageResults chromoP_manageResults = new ChromoP_manageResults();
		chromoP_manageResults.setMatrixFile(args[0]);
	}
}
