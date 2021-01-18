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

public class CentromOut_forMsmc {
	
	public CentromOut_forMsmc() {}
	
	public void setFileToSubSet(String file){
		
		try {
			int[] centrom = {15084050, 3616850, 13590100, 3953300, 11705550};
			
			File msmcIn = new File(file);
	    		Scanner scanner = new Scanner(msmcIn);
			
			Writer writer = new FileWriter(file + "_noCentrom.txt");
			PrintWriter out = new PrintWriter(writer);
			Boolean cent = false;
			
			while ( scanner.hasNextLine() ) {
				String snp = scanner.nextLine();
		       		String[] splitSnp = snp.split("\t");
		       		
		       		int c = Integer.parseInt(splitSnp[0]) - 1;
		       		int pos = Integer.parseInt(splitSnp[1]);
		       		Boolean segrega = false;
		       		for (int b=0; b<splitSnp[3].length(); b++) {
		       			if ( splitSnp[3].charAt(0) != splitSnp[3].charAt(b) ) {
		       				segrega = true;
		       			}
		       		}
		       		
		       		//if (segrega) {
			       		if (pos < centrom[c] - 5000000) {
			       			out.print(snp + "\n");
			       		} else {
			       			if ( pos >= centrom[c] - 5000000 && pos <= centrom[c] + 5000000) {
				       			cent = true;
			       			} else {
			       				if (pos > centrom[c] + 5000000) {
			       					if (cent) {
			       						out.print(splitSnp[0] + "\t" + splitSnp[1] + "\t" + "1" + "\t" + splitSnp[3] + "\n");
				       					cent = false;
			       					} else {
			    		       				out.print(snp + "\n");
			       					}
			       				}
			       			}	
					}
		       		//}
			}
			out.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		CentromOut_forMsmc centromOut_forMsmc = new CentromOut_forMsmc();
		centromOut_forMsmc.setFileToSubSet(args[0]);
	}
}
