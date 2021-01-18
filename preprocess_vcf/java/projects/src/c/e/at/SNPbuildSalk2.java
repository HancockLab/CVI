package c.e.at;

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

public class SNPbuildSalk2 {
	
	private BitSet[] mask = null;
	private File fileVcf = null;
	String snp1=null;
	private String[] splitSnp1 = null;
	
	public SNPbuildSalk2() {}
	
	public void setSnpFile(String filename){
		
		try {
			mask=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			
			Writer writer = new FileWriter("/home/CIBIV/andreaf/canaries/rawData/CVI_0_gmi-gq_noQD_vcf.snp2");
			PrintWriter out = new PrintWriter(writer);
			
			fileVcf = new File(filename);
			Scanner scanner = new Scanner(fileVcf);
			
			while ( scanner.hasNextLine() ) { 
				
				snp1 = scanner.nextLine();
		        	splitSnp1 = snp1.split("\t");
	        	
	        		if (splitSnp1[0].charAt(0) != '#') {
	        		
	        			if ( splitSnp1[0].charAt(0) == 'C' && splitSnp1[0].charAt(1) == 'h' && splitSnp1[0].charAt(2) == 'r') {
			        	
		        			// int chr = Integer.parseInt(Character.toString(splitSnp1[0].charAt(3)));
			        		// int posSNP = Integer.parseInt(splitSnp1[1]);
			        		char base = splitSnp1[4].charAt(0);
				        	
			        		if (base != '.') {
		        				out.println(snp1);
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
		SNPbuild sNPbuild = new SNPbuild();
		sNPbuild.setSnpFile("/home/CIBIV/andreaf/canaries/rawData/CVI_0_gmi.11015_110_-1.realigned.sort.bam.sam.sam.sortP.sam.duprem.sam.bam.mq20.bam_-gq_noQD.vcf");  
	}
}
