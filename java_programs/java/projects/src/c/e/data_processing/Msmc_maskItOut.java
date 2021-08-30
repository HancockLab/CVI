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

public class Msmc_maskItOut {
	
	public Msmc_maskItOut() {}
	
	public void setFileToSubSet(String file, String maskName){
		
		try {			
			
			BitSet[] mask=new BitSet[5];
			for (int c=0; c<5; c++) {
				mask[c]= new BitSet();
			}
			
			File fileMask = new File(maskName);
			Scanner scannerMask = new Scanner(fileMask);
        	int chrCVI=0;
        	String lineMask=null;
        	for (int m=0; m<10; m++) {
	        	lineMask = scannerMask.nextLine();
	        	if (m%2 == 1) {
		        	for (int i=0; i<lineMask.length(); i++) {
		        		if (lineMask.charAt(i) == ('1')) {
		        			mask[chrCVI].set(i);
		        		}
		        	}
		        	chrCVI=chrCVI+1;
	        	}
	        }
			
			
			
			File msmcIn = new File(file);
	    	Scanner scanner = new Scanner(msmcIn);

			Writer writer = new FileWriter(file + "_masked.txt");
			PrintWriter out = new PrintWriter(writer);
			int posMem = 1;
			
			while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
		       	String[] splitSnp = snp.split("\t");
		       	
		       	int c = Integer.parseInt(splitSnp[0]) - 1;
		       	int pos = Integer.parseInt(splitSnp[1]);
		       	
		       	if (mask[c].get(pos)) {
		       		// Count the good bases between this and the last good SNP
		       		int goodPos = 0;
		       		for (int p=posMem; p<=pos; p++) {
		       			if (mask[c].get(p)) {
		       				goodPos = goodPos + 1;
		       			}
		       		}
				if (goodPos < 1) {
					goodPos = 1;
				}
		       		out.print(splitSnp[0] + "\t" + splitSnp[1] + "\t" + goodPos + "\t" + splitSnp[3] + "\n");
			       	posMem = pos;
		       	}
			}
			out.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Msmc_maskItOut msmc_maskItOut = new Msmc_maskItOut();
		msmc_maskItOut.setFileToSubSet(args[0], args[1]);
	}
}
