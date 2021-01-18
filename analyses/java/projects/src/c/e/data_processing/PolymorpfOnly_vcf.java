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

public class PolymorpfOnly_vcf {
	
	public PolymorpfOnly_vcf() {}
	
	public void setFileToSubSet(String vcf){
		
		try {
			
	    	File vcfFile = new File(vcf);
	    	Scanner scanner = new Scanner(vcfFile);

		Writer writer = new FileWriter(vcf + "_polymorphOnly.vcf");
		PrintWriter out = new PrintWriter(writer);
			
	    	while (scanner.hasNextLine()) {
				String snp = scanner.nextLine();
				String[] splitSnp = snp.split("\t");
				
				if (snp.charAt(0) == '#') {
					out.println(snp);
				} else {
					// The real deal
					String[] alt = splitSnp[4].split(",");
					boolean altOne = true;
					for (int a=0; a<alt.length; a++) {
						if (alt[a].length() > 1) {
							altOne = false;
						}
					}
					if (altOne && splitSnp[3].length() == 1) {
						// Check for polymorphisms
						boolean match = false;
						boolean mism = false;
						for (int s=9; s<splitSnp.length; s++) {
							if (splitSnp[s].split(":")[0].charAt(0) != '.' && splitSnp[s].split(":")[1].charAt(0) != '.' && splitSnp[s].split(":")[2].charAt(0) != '.') {
								if (Integer.parseInt(splitSnp[s].split(":")[1]) >= 25 && Integer.parseInt(splitSnp[s].split(":")[2]) >= 3) {
									if (splitSnp[s].split(":")[0].charAt(0) == '0') {
										match = true;
									} else {
										mism = true;
									}
								}
							}
						}
						if (match && mism) {
							out.println(snp);
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
		PolymorpfOnly_vcf polymorpfOnly_vcf = new PolymorpfOnly_vcf();
		polymorpfOnly_vcf.setFileToSubSet(args[0]);
	}
}
