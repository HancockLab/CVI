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

public class Vcf_selectSegregatingSites {
	
	public Vcf_selectSegregatingSites() {}
	
	public void setFileToConvert(String filename){
		
		try {
			// int posThresh = Integer.parseInt(posThreshStr);
			
			// Open combined vcf
			File vcf_comb = new File(filename);
			Scanner scannerVcf = new Scanner(vcf_comb);
			
			// Open also the writing file
			Writer writer = new FileWriter(filename + "_segregOnly.vcf");
			PrintWriter out = new PrintWriter(writer);
			
			while ( scannerVcf.hasNextLine() ) {
				String snp1 = scannerVcf.nextLine();
			    String[] splitSnp = snp1.split("\t");
		       	
				// Mind the header now
				if (snp1.charAt(0) == '#') {
					// Print it out
					out.print(snp1 + "\n");
					// Header is over
				} else {
					// Check if this position is polymorphic or not
					// 
					boolean[] variants = {false, false};
					for (int col=9; col<splitSnp.length; col++) {
						char geno = splitSnp[col].split(":")[0].charAt(0);
						if (geno == '0') {
							variants[0] = true;
						} else {
							if (geno == '1') {
								variants[1] = true;
							}
						}
					}
					if (variants[0] && variants[1]) {
						out.print(snp1 + "\n");
					}
				}
       		}	
			out.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Vcf_selectSegregatingSites vcf_selectSegregatingSites = new Vcf_selectSegregatingSites();
		vcf_selectSegregatingSites.setFileToConvert(args[0]);
	}
}
