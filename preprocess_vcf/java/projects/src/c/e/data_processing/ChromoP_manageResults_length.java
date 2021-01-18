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

public class ChromoP_manageResults_length {
	public ChromoP_manageResults_length() {}
	
	public void setMatrixFile(String foldName){
		
		try {
			File chromFold = new File(foldName);
			
			for (int can=11; can<14; can++) {
				
				File canFile = new File(chromFold + "/" + can);
				System.out.println(canFile.getName());
				File[] replicateFile = canFile.listFiles();
				int replicates = 101;
				// replicateFile.length;
				
				// System.out.println(replicates + " replicates");
				// System.out.println(Arrays.toString(replicateFile));
				
				File initial = new File(canFile + "/0/results");
				File[] ini = initial.listFiles();
				int numPops = 0;
				for (int i=0; i<ini.length; i++) {
					if (ini[i].getName().split("\\.")[ini[i].getName().split("\\.").length - 2].equals("chunklengths")) {
						
						System.out.println(ini[i].getAbsolutePath());
						Scanner scanI = new Scanner(ini[i]);
						String lineI = scanI.nextLine();
						String[] names = lineI.split(" ");
						
						numPops = names.length;
					}
				}
				
				String[][][] pops = new String[replicates][][];
				Double[][][] len = new Double[replicates][][];				
				
				for (int repl=0; repl < replicates; repl++) {			// replicates
					File neF = new File(canFile + "/" + repl + "/results");
					System.out.print(repl + " ");
					
					File[] resultsFile = neF.listFiles();
					// System.out.println(Arrays.toString(resultsFile));
					
					int propFiles = 0;
					for (int r=0; r<resultsFile.length; r++) {
						String resFileName = resultsFile[r].getName();
						// System.out.println(resFileName);
						if (resFileName.split("\\.")[resFileName.split("\\.").length - 2].equals("chunklengths")) {
							propFiles = propFiles + 1;
						}
					}
					// System.out.println(propFiles);

					pops[repl] = new String[propFiles][];
					len[repl] = new Double[propFiles][];
					for (int f=0; f<propFiles; f++) {
						pops[repl][f] = new String[numPops-1];
						len[repl][f] = new Double[numPops-1];
					}
					// System.out.println(pops.length + " " + pops[0].length + " " + pops[0][0].length);
					
					propFiles = 0;
					for (int r=0; r<resultsFile.length; r++) {
						String resFileName = resultsFile[r].getName();
						// System.out.println(resFileName);

						if (resFileName.split("\\.")[resFileName.split("\\.").length - 2].equals("chunklengths")) {
							// System.out.println(resFileName);
							
							Scanner scan = new Scanner(resultsFile[r]);			
							String lineN = scan.nextLine();
							String[] popStr = lineN.split(" ");
							
							String lineP = scan.nextLine();
							String[] propStr = lineP.split(" ");
							
							for (int run=1; run<propStr.length; run++) {
								pops[repl][propFiles][run-1] = popStr[run];
								len[repl][propFiles][run-1] = Double.parseDouble(propStr[run]);
							}
							propFiles = propFiles + 1;
						}
					}





					propFiles = 0;
					for (int r=0; r<resultsFile.length; r++) {
                                                String resFileName = resultsFile[r].getName();
                                                // System.out.println(resFileName);
                                                if (resFileName.split("\\.")[resFileName.split("\\.").length - 2].equals("chunkcounts")) {
                                                        // System.out.println(resFileName);
                                                        
                                                        Scanner scan = new Scanner(resultsFile[r]);
                                                        String lineN = scan.nextLine();
                                                        String[] popStr = lineN.split(" ");
							
                                                        String lineP = scan.nextLine();
                                                        String[] propStr = lineP.split(" ");

                                                        for (int run=1; run<propStr.length; run++) {
                                                                pops[repl][propFiles][run-1] = popStr[run];
                                                                if (len[repl][propFiles][run-1] > 0) {
									len[repl][propFiles][run-1] = len[repl][propFiles][run-1]/Double.parseDouble(propStr[run]);
                                                        	} else {
									len[repl][propFiles][run-1] = 0.0;
								}
							}
                                                        propFiles = propFiles + 1;
                                                }
                                        }       

				}
                                System.out.print("\n");












				Writer writer = new FileWriter(foldName + can + "_len.txt");
				PrintWriter out = new PrintWriter(writer);
				
				for (int p=0; p<pops[0][0].length; p++) {
					out.print(pops[0][0][p] + "\t");
				}
				out.print("\n");
				for (int r=0; r<replicates; r++) {				//replicates
					// System.out.println(pops[r].length);
					for (int f=0; f<len[r].length; f++) {
						for (int p=0; p<len[r][f].length; p++) {
							out.print(len[r][f][p] + "\t");
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
		ChromoP_manageResults_length chromoP_manageResults_length = new ChromoP_manageResults_length();
		chromoP_manageResults_length.setMatrixFile(args[0]);
	}
}
