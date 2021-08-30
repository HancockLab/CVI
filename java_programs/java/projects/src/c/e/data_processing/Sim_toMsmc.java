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

public class Sim_toMsmc {
	public Sim_toMsmc() {}
	
	public void setMatrixFile(String vcfName){
		
		try {
			
			Writer writer2 = new FileWriter(vcfName + ".msmc2hap");
			PrintWriter out2 = new PrintWriter(writer2);
			
			Writer writer3 = new FileWriter(vcfName + ".msmc8hap");
			PrintWriter out3 = new PrintWriter(writer3);
			
			Writer writer4 = new FileWriter(vcfName + ".ccr2hap");
                        PrintWriter out4 = new PrintWriter(writer4);
			
			// Open the file
			File vcfFile = new File(vcfName);
			Scanner scan = new Scanner(vcfFile);
			
			int memPosCcr = 0;
			int memPos2 = 0;
                        int memPos8 = 0;
			while ( scan.hasNextLine() ) {
				String snp = scan.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Mind header
				if (snp.charAt(0) != '#') {
					
                                        char base1 = splitSnp[9].charAt(0);
					
					// For ccr
					//
					Boolean segrega = false;
                                        for (int t=9; t<11; t++) {
                                                if (base1 != splitSnp[t].charAt(0)) {
                                                        segrega = true;
                                                }
                                        }
					for (int t=9+11; t<9+13; t++) {
                                                if (base1 != splitSnp[t].charAt(0)) {
                                                        segrega = true;
                                                }
                                        }
                                        if (segrega) {
                                                out4.print(splitSnp[0] + "\t" + splitSnp[1] + "\t" + (Integer.parseInt(splitSnp[1])-memPosCcr) + "\t");
                                                for (int t=9; t<11; t++) {
                                                        out4.print(splitSnp[t]);
                                                }
						for (int t=9+11; t<9+13; t++) {
                                                        out4.print(splitSnp[t]);
                                                }
                                                out4.print("\n");
                                                memPosCcr = Integer.parseInt(splitSnp[1]);
                                        }
					
					// For 2 haps
					//
                                        segrega = false;
					for (int t=9; t<11; t++) {
						if (base1 != splitSnp[t].charAt(0)) {
							segrega = true;
						}
					}
					if (segrega) {
	                                        out2.print(splitSnp[0] + "\t" + splitSnp[1] + "\t" + (Integer.parseInt(splitSnp[1])-memPos2) + "\t");
	                                        for (int t=9; t<11; t++) {
        	                                        out2.print(splitSnp[t]);
                	                        }
                        	                out2.print("\n");
						memPos2 = Integer.parseInt(splitSnp[1]);
					}
					
					// For 8 haps
					//
                                        segrega = false;
                                        for (int t=9; t<17; t++) {
                                                if (base1 != splitSnp[t].charAt(0)) {
                                                        segrega = true;
                                                }
                                        }
                                        if (segrega) {					
						out3.print(splitSnp[0] + "\t" + splitSnp[1] + "\t" + (Integer.parseInt(splitSnp[1])-memPos8) + "\t");
						for (int t=9; t<17; t++) {
							out3.print(splitSnp[t]);
						}
						out3.print("\n");
						memPos8 = Integer.parseInt(splitSnp[1]);
					}
				}
			}
			out2.close();
			out3.close();
			out4.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Sim_toMsmc sim_toMsmc = new Sim_toMsmc();
		sim_toMsmc.setMatrixFile(args[0]);
	}
}
