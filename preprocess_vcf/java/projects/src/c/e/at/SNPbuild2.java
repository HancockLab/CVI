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

public class SNPbuild2 {
	
	private BitSet[] mask = null;
	private File fileVcf = null;
	String snp1=null;
	private String[] splitSnp1 = null;
	private String[] variant = null;
	
	public SNPbuild2() {}
	
	public void setSnpFile(String filename){
		
		try {
			mask=new BitSet[5];
			
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			
			Writer writer1 = new FileWriter("/home/CIBIV/andreaf/canaries/rawData/Cvi0_gmi.allVar");
			PrintWriter out1 = new PrintWriter(writer1);
			
			Writer writer2 = new FileWriter("/home/CIBIV/andreaf/canaries/rawData/Cvi0_gmi.snp2");
			PrintWriter out2 = new PrintWriter(writer2);
			
			Writer writer3 = new FileWriter("/home/CIBIV/andreaf/canaries/rawData/Cvi0_gmi.indel");
			PrintWriter out3 = new PrintWriter(writer3);
			
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
		        			out1.println(snp1);
		        			
		        			String var = splitSnp1[4];
		        			variant = var.split("");
		        			
		        			int indel = variant.length;
		        			if (indel == 1) {
		        				out2.println(snp1);
		        			} else {
		        				out3.println(snp1);
		        			}
		        			
			        	}
		        	}
	        	}
	        }
			out1.close();
			out2.close();
			out3.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SNPbuild2 sNPbuild2 = new SNPbuild2();
		sNPbuild2.setSnpFile("/home/CIBIV/andreaf/canaries/rawData/CVI_0_gmi.11015_110_-1.realigned.sort.bam.sam.sam.sortP.sam.duprem.sam.bam.mq20.bam_-gq_noQD.vcf");  
	}
}
