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
import java.util.Collections;

public class Uniqueness {
	
	private File fileSam = null;
	String snp1=null;
	private String[] splitSnp1 = null;
	
	public Uniqueness() {}
	
	public void setSnpFile(String filename){
		
		try {
			
			fileSam = new File(filename);
			Scanner scanner = new Scanner(fileSam);
			
			Writer writer = new FileWriter(filename + ".uniqueMine.sam");
			PrintWriter out = new PrintWriter(writer);
			
			while ( scanner.hasNextLine() ) { 
				
				snp1 = scanner.nextLine();
				splitSnp1 = snp1.split("\t");
	        		// char beginn = splitSnp1[0].split("")[1].charAt(0);
	        		char beginn = splitSnp1[0].charAt(0);
					
	        		if (beginn == '@') {
	    				out.println(snp1);
	        		} else {
	        			Boolean unics = false;
					for (int chunk=0; chunk<splitSnp1.length; chunk++) {
	        				if (splitSnp1[chunk].equals("XT:A:U")) {
	        					unics = true;
	        				}
	  		      		}
					if (unics) {
						out.println(snp1);
					}
	        		}
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Uniqueness uniqueness = new Uniqueness();
		uniqueness.setSnpFile(args[0]);
	}
}
