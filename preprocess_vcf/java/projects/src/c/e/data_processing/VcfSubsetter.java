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

public class VcfSubsetter {
	
	public VcfSubsetter() {}
	
	public void setSamFile(String vcfName){
		
		try {
			
			// Open the vcf file
			File vcfFile = new File(vcfName);
			Scanner scannerVcf = new Scanner(vcfFile);
			
			// Now write out the nice one
			Writer writer = new FileWriter(vcfFile + "_subset.vcf");
			PrintWriter out = new PrintWriter(writer);
			
			while ( scannerVcf.hasNextLine() ) {
				String snp = scannerVcf.nextLine();
				String[] splitSnp = snp.split("\t");
				
				// Mind the header
				if (snp.charAt(0) == '#') {
					out.print(snp + "\n");
				} else {
					// Then the rest
					if (splitSnp[3].charAt(0) != 'N') {
						
						if (splitSnp[0].equals("Chr4")) {										// (splitSnp[2].equals("Chr4")) {
							if ( (Integer.parseInt(splitSnp[1]) > 11360000) && (Integer.parseInt(splitSnp[1]) < 11410000) ) {
								out.print(snp + "\n");
							}
						} else {
							// if (splitSnp[2].charAt(0) != '*') {
							out.print(snp + "\n");
							//}
						}
					}
				}
			}
			out.close();
			
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		VcfSubsetter vcfSubsetter = new VcfSubsetter();
		vcfSubsetter.setSamFile(args[0]);
	}
}
