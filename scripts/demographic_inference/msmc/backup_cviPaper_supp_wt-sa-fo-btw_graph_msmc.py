#!/bin/python

# 
# Run as:
# python ./backup_cviPaper_supp_wt-sa-fo-btw_graph_msmc.py -l "Between","SA-Cova","SA-Figueira","SA-Espongeiro","SA-Pico","Fogo-Lava","Fogo-Inferno","Fogo-M.Velha" -f between8_plusS20.txt,./data/s20_clean.txt,./data/s1s8.txt,./data/s11.txt,./data/s10.txt,./data/f10.txt,./data/f12.txt,./data/F_monteVelha_3-4-5-7.txt -o edf3b -m



import sys
import argparse
import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
from matplotlib import collections  as mc
from scipy import stats
from scipy.interpolate import interp1d
import scipy as sp
from fractions import Fraction
#from astropy.stats.funcs import bootstrap

colors=[]

# wt SA, wt Fo, btw
colors.append('#980000')

# wt sa
colors.append('#00BFFF')
colors.append('#ccccff')
colors.append('#0000FF')
colors.append('#1E90FF')

# wt Fo
colors.append('#FF8C00')
colors.append('#FF4500')
colors.append('#FF7F50')



plt.rcParams['font.family'] = 'sans-serif'
plt.rcParams['font.sans-serif'] = 'Helvetica'
matplotlib.rcParams.update({'font.size': 7})



parser = argparse.ArgumentParser(description="A script that plots msmc results. This will plot all lines and color them according to the group provided by the -f argument (and label according to the -l arg). \nTODO: add --means option to plot the means of the groups instead of all the lines.")
parser.add_argument("-l", "--legend", action="store", required=True, help="Labels for the legend. Should be same order as the files. Comma separated. e.g. '-l Within_Morocco,Within_Iberia'. Will automatically replace _ with a space.")
parser.add_argument("-o", "--out", action="store", required=True, help="Output filename")
parser.add_argument("-f", "--files", action="store", required=True, help="List of files containing the comparisons to graph. Comma separated. e.g. '-f w_m.txt,w_i.txt,w_w.txt'")
parser.add_argument("-m", "--means", action="store_true", required=False, default=False, help="Plot means of each group instead of every line.")
parser.add_argument("-k","--kruskal", action="store_true",required=False, default=False, help="Output complete sample array to file for each set of comparisons. For later use in a Kruskal-Wallis Rank Sum test.")

args = parser.parse_args()

file_list = args.files.split(",")
legend=args.legend.split(",")
for idx,l in enumerate(legend):
    legend[idx]=l.replace("_", " ")

num_groups = len(file_list)
plt.figure(figsize=(3.8,2.8))

if not args.means:
    for idx,f in enumerate(file_list):
        f2 = open(f,'r')
        for line in f2:
            try:
                res = open('./results/'+line.strip("\n")+".popsize.txt", 'r')
            except FileNotFoundError:
                print("Couldn't find file: "+line.strip("\n")+". Continuing...")
                continue
            x_temp=res.readline()
            x = x_temp[1:-2].split(',')
            x = [float(t) for t in x]
            x[0] = x[1] / 4.0
            y_temp = res.readline()
            y = y_temp[1:-2].split(',')
            plt.step(x, y, where='post',lw=2.0, color=colors[idx], label=legend[idx],alpha=0.2)
elif args.means:
    for idx,f in enumerate(file_list):
        x_list = []
        y_list = []
        f2 = open(f, 'r')
        length=0
        n = 0
        print(f)
        for line in f2:
            #try:
            res = open('./results/'+line.strip("\n")+".popsize.txt", 'r')
            n = n + 1
            length=length+1
            #except FileNotFoundError:
            #    print("Couldn't find file: "+line.strip("\n")+". Continuing...")
            #    continue
            x_temp=res.readline()
            x = x_temp[1:-2].split(',')
            try:
                x = [float(t) for t in x]
            except ValueError:
                print(x)
                print(res)
                continue
            x[0] = x[1] / 4.0
            y_temp = res.readline()
            y = y_temp[1:-2].split(',')
            x_list.append([float(i) for i in x])
            y_list.append([float(i) for i in y])
        if args.kruskal:
            np.savetxt(f+".x.array.txt",np.array(x_list))
            np.savetxt(f+".y.array.txt",np.array(y_list))
        print(x_list)
	x_mean = np.array(x_list).mean(axis=0)
        y_mean = np.array(y_list).mean(axis=0)
	x_median = np.median(np.array(x_list), axis=0)
        # y_median = np.median(np.array(y_list), axis=0)
	# y_std = np.std(np.array(y_list), axis=0)
	y_median = np.median( (1 / (2 * np.array(y_list))), axis=0)
        y_std = np.std( (1 / (2 * np.array(y_list))), axis=0)
        print("std")
	print(x_median)
	print(y_median)
	# print(np.median(np.array(y_list),axis=0))
	print(y_std)
	print("end std")
	
        y_std = 1.96*(y_std/np.sqrt(28))
        
        x_min = 0 # np.amin(np.array(x_list))
        x_max = 2000000 # np.amax(np.array(x_list))

        ## Median with cubic spline
        x_mult = np.multiply(x_median,(1)) # change gen time to 1.5 by multiplying
        x_rev = x_mult[::-1]
        y_rev = y_median[::-1]
        y_std_rev = y_std[::-1]
        

        spln = interp1d(x_rev, y_rev, kind="cubic",bounds_error=False)
        xnew = np.linspace(x_min, x_max, num=10000, endpoint=True)
        



	if idx in [0, 1, 5]:
		plt.plot(xnew, spln(xnew), lw=1.0, linestyle='solid', color=colors[idx])
	else:
		if idx in [2, 6]:
			plt.plot(xnew, spln(xnew), lw=1.0, linestyle='dashed', color=colors[idx])
		else:
			if idx in [3, 7]:
				plt.plot(xnew, spln(xnew), lw=1.0, linestyle='dashdot', color=colors[idx])
			else:
				if idx in [4, 8]:
					plt.plot(xnew, spln(xnew), lw=1.0, linestyle='dotted', color=colors[idx])
				else:
					plt.plot(xnew, spln(xnew), lw=1.0, linestyle='dashed', color=colors[idx])


# 	plt.plot(xnew, spln(xnew), lw=2.0, color=colors[idx], label=legend[idx])
        
        ###
        #	Output Ne within Madeira
        ###
        
        if f in ['are8_clean_comparisons.txt']:
        	print("Within are")
        	x_sim=np.logspace(np.log10(2900), np.log10(150000), num=15, endpoint=True)
        	y_sim=spln(x_sim)
        	x_sim[0]=10
        	y_sim[0]=50000
        	print(x_sim)
        	print(y_sim)
       
        ###
        #	Output Ne within relicts
        ### 
        
        if f in ['rel_comparisons.txt']:
        	print("Within rel")
        	x_sim2=np.logspace(np.log10(150000), np.log10(2000000), num=10, endpoint=True)
        	y_sim2=spln(x_sim2)
        	print(x_sim2)
        	print(y_sim2)
        
	
        y_revPlusStd = y_rev + y_std_rev
        y_revMinusStd = y_rev - y_std_rev
	print("std2")
	print(x_rev)
	print(y_revPlusStd)
	print(y_revMinusStd)
	print("end std2")
        splnStPlus = interp1d(x_rev, y_revPlusStd, kind="cubic",bounds_error=False)
        splnStMinus = interp1d(x_rev, y_revMinusStd, kind="cubic",bounds_error=False)
	# 
	plt.fill_between(xnew, splnStPlus(xnew), splnStMinus(xnew), color=colors[idx], alpha=0.2)



plt.legend(legend,loc='upper right', frameon=False)
plt.xlim(1e2,10e4) #<- main figure
plt.ylim(0,1.5e-04) # 2.e-04) # 0,0.5e05)
plt.xscale('log')
plt.gca().invert_xaxis()
plt.xlabel("Time ($\mathregular{10^3}$ years ago)")
plt.ylabel("Coalescence rate ($\mathregular{10^{-5}}$)")
labels=["0.1", "1", "10", "100"]
plt.xticks([100, 1000, 10000, 100000], labels) # , fontsize = 14)
plt.yticks([0, 5e-5, 10e-5, 15e-5], ["0", "5", "10", "15"]) # , fontsize = 14)
plt.savefig(args.out+'.pdf', bbox_inches='tight') 



