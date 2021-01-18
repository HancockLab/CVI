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
import java.text.DecimalFormat;

public class StructuralVariantsCaller {
	public StructuralVariantsCaller() {}
	
	public void setMatrixFile(String fileName){
		
		try {
			File bamFile = new File(fileName);
			Scanner scanFile = new Scanner(bamFile);
			scanFile.nextLine();
			int chr = 0;
			int pos = 0;
			int[] struct = new int[7];
			for (int s=0; s<struct.length; s++) {
				struct[s] = 0;
			}
			String name = fileName.split("/")[fileName.split("/").length-1].split("\\.")[0];
			// System.out.println(name);
			
			while (scanFile.hasNextLine()) {
				String line = scanFile.nextLine();
				String[] splitLine = line.split("\t");
				chr = 0;
				pos = 0;
				
				if (line.charAt(0) != '@') {
					if (splitLine[2].charAt(0) == 'C' && splitLine[2].charAt(1) == 'h' && splitLine[2].charAt(2) == 'r' ) {
						chr = Character.getNumericValue(splitLine[2].charAt(3));
						pos = Integer.parseInt(splitLine[3]);
					}
					// System.out.println(chr + "\t" + pos);
					
					if (splitLine[6].charAt(0) == '=') {
						int pos2 = Integer.parseInt(splitLine[7]);
						// Get transposition
						if ( chr == 5 && pos > 7649200 && pos < 7650200 && pos2 > 14693400 && pos2 < 14694400 ) {
							struct[0] = struct[0] + 1;
							// System.out.println(chr + "\t" + pos + "\t" + pos2);
						}
						// Get inversion
						if ( ( chr == 2 && pos > 13861300 && pos < 13862300 && pos2 > 14026000 && pos2 < 14026500 ) ) {
							struct[1] = struct[1] + 1;
							// System.out.println(chr + "\t" + pos + "\t" + pos2);
						}
						// Get Chr5 var2
						if ( ( chr == 5 && pos > 4095800 && pos < 4096700 && pos2 > 4100700 && pos2 < 4101300 ) ) {
							struct[3] = struct[3] + 1;
						}
						// Get Chr5 var3
						if ( ( chr == 5 && pos > 5627000 && pos < 5628000 && pos2 > 5637500 && pos2 < 5639000 ) ) {
							struct[4] = struct[4] + 1;
						}
						// Get Chr5 var4
						if ( ( chr == 5 && pos > 6630000 && pos < 6631000 && pos2 > 6635000 && pos2 < 6636000 ) ) {
							struct[5] = struct[5] + 1;
						}
						// Get Chr5 var5
						if ( ( chr == 5 && pos > 24200000 && pos < 24205000 && pos2 > 24232000 && pos2 < 24237000 ) ) {
							struct[6] = struct[6] + 1;
						}
					} else {
						// Get the jump on chr5
						if (splitLine[6].charAt(0) == 'C') {
							if (splitLine[6].charAt(1) == 'h' && splitLine[6].charAt(2) == 'r' ) {
								int chr2 = Character.getNumericValue(splitLine[6].charAt(3));
								int pos2 = Integer.parseInt(splitLine[7]);
								if ( ( chr == 5 && chr2 == 4 && pos > 2982900 && pos < 2983400 && pos2 > 15553000 && pos2 < 15555000 ) ) {
									struct[2] = struct[2] + 1;
								}
							}
						}
					}
				}
			}
			Writer writer = new FileWriter(fileName + "_struct.txt");
			PrintWriter out = new PrintWriter(writer);
			out.print(name);
			for (int s=0; s<struct.length; s++) {
				out.print("\t" + struct[s]);
			}
			out.print("\n");
			out.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		StructuralVariantsCaller structuralVariantsCaller = new StructuralVariantsCaller();
		structuralVariantsCaller.setMatrixFile(args[0]);
	}
}
