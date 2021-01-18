#!/bin/python

###
#	To run from terminal as:
# 	python ./scripts/demographic_inference/ccr/scripts/./backup_mainFigCvi_graph_msmc_8hap.py -l "Fogo - Santo Antao" -f ./scripts/demographic_inference/ccr/data/cvi_comparisons.txt -o ./scripts/demographic_inference/ccr/figures/fig1e
###



import sys
import argparse
import numpy as np
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
from scipy import stats
from scipy.interpolate import interp1d
import scipy as sp
# from astropy.stats.funcs import bootstrap
import matplotlib.colors as colors
from fractions import Fraction
import matplotlib.patches as mpatches

import matplotlib.font_manager


# plt.rcParams['font.family'] = 'sans-serif'
# plt.rcParams['font.sans-serif'] = 'Helvetica'



# This works but not for Helvetica
#
plt.rcParams['font.family'] = 'sans-serif'
plt.rcParams['font.sans-serif'] = 'Helvetica'


# import matplotlib.font_manager as font_manager

# font_dirs = ['/System/Library/Fonts']
# font_files = font_manager.findSystemFonts(fontpaths=font_dirs)
# font_list = font_manager.createFontList(font_files)
# font_manager.fontManager.ttflist.extend(font_list)

# plt.rcParams['font.family'] = 'Helvetica'



# plt.rcParams['font.sans-serif'] = 'Helvetica'


# matplotlib.rcParams['font.sans-serif'] = "Helvetica"
# Then, "ALWAYS use sans-serif fonts"
# matplotlib.rcParams['font.family'] = "sans-serif"





matplotlib.rcParams.update({'font.size': 7})



split_res=[]

colors=[]
colors.append('sienna')
colors.append('black')
colors.append('black')
colors.append('grey')
colors.append('grey')
colors.append((0,0,1))
colors.append((0,0,1))
colors.append('violet')
colors.append('violet')
colors.append('deeppink')
colors.append('deeppink')
colors.append('chartreuse')
colors.append('chartreuse')
colors.append('yellow')
colors.append('yellow')
colors.append('silver')
colors.append('silver')
colors.append((1,0,0))
colors.append((1,0,0))
colors.append((0,1,0))
colors.append((0,1,0))

for a in range(1, 16):
 colors.append((float(Fraction((((a)*15)), 255)), float(Fraction((((a)*15)), 255)), float(Fraction((255-((a)*15)), 255)) ))

#print len(colors)
#colors=['#001f3f','#0074D9','#7FDBFF','#39CCCC','#3D9970','#2ECC40','#01FF70','#FFDC00','#FF851B'] 	#,'#FF4136','#85144b','#F012BE','#B10DC9','#111111','#AAAAAA','#DDDDDD']
#colors=['#d10000', "#ff6622","#ffda21","#33dd00","#1133cc","#220066","#330044"]
#colors=["#ff6622","#1133cc","#33dd00", "#1133cc"]

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
mu=7.1e-9
gen=1

print(args.out)

###
#		For CVI alone
x_min = 10
x_max = 10000

###
#               For CVI and Morocco
#x_min = 3000
#x_max = 100000

xnew = np.linspace(x_min, x_max, num=1000, endpoint=True)
y_list = []
plt.figure(figsize=(2.2,1.6)) # 12,7)) # 7,5)) # 12,3))

if not args.means:
 for idx,f in enumerate(file_list):
  f2 = open(f,'r')
  y_list = []
  for line in f2:
   # print(f)
   # try:
   res = open('./scripts/demographic_inference/ccr/results/'+line.strip("\n")+".combined.txt", 'r')
   # except FileNotFoundError:
   #  print("Couldn't find file: "+line.strip("\n")+". Continuing...")
   #  continue	
    
   res.readline()
   x = []
   y = []
   n = 0
   for line in res:
    x.append(float(line.split('\n')[0].split('\t')[1])/(mu*gen))
    
    # For everyone
    y.append(2*float(line.split('\n')[0].split('\t')[4])/(float(line.split('\n')[0].split('\t')[3]) + float(line.split('\n')[0].split('\t')[5]) ))
    
    ###
    # For cvi - mor:
    ###
    #y.append(float(line.split('\n')[0].split('\t')[4])/( float(line.split('\n')[0].split('\t')[5]) ))
    n = n + 1
   # x_rev = x[::-1]
   # y_rev = y[::-1]
   x_rev = x
   y_rev = y
   x_rev=np.asarray(x_rev[5:len(x_rev)])
   y_rev=np.asarray(y_rev[5:len(y_rev)])
   # print(y_rev)
   xnew = np.linspace(x_rev[0], x_rev[len(x_rev)-1], num=1000, endpoint=True)   
   # print(type(xnew))
   # print(type(y_rev))
   # x_rev = np.arange(1000, 2000, 100)
   # y_rev = np.arange(1000, 2000, 100)
   # print(type(y_rev))
   # print(type(x_rev))
   # print(x_rev)
   # print(y_rev)
   spln = interp1d(x_rev, y_rev, kind="cubic",bounds_error=False)
   # xnew = np.arange(1000, 2000, 100)
   # The probkem seems to be that x_rev or y_rev are not arrays. Not Xnew which may not matter
   #
   #
   #
   #
   # print(type(xnew))
   # print(type(np.arange(0, 9, 0.1)))
   # print(spln(np.arange(1000, 2000, 100)))
   y_list.append(spln(xnew))
  y_median=[]
  y_std = []
  for s in range(len(y_list[0])):
   y_temM = []
   for i in range(len(y_list)):
    y_temM.append(y_list[i][s]) 
   # print(y_temM)
   y_median.append(np.median(np.array(y_temM), axis=0))
   y_std.append(np.std(np.array(y_temM),axis=0))
  # print(y_median)
  spln_m = interp1d(xnew, y_median, kind="cubic",bounds_error=False)
  
  #x5 = np.interp(x=0.15, xp=y_5, fp=x_5, left=-300, right=-600)
  #split_res.append(x5)
  
  # if you want standard error instead of standard dev. Otherwise, comment out
  y_std = 1.96*(y_std/np.sqrt(n))
  
  y_medPlSd = np.asarray(y_median) + np.asarray(y_std)
  y_medMinSd = np.asarray(y_median) - np.asarray(y_std)
  
  spln_mP = interp1d(xnew, y_medPlSd, kind="cubic",bounds_error=False)
  spln_mM = interp1d(xnew, y_medMinSd, kind="cubic",bounds_error=False)
 
  # print("xnew") 
  # print(len(xnew))
  x_5 = []
  y_5 = []
  y_5_plus = []
  y_5_minus = []
  for xs in range(1, (len(xnew)-1)):
   if xnew[xs] > 2000 and xnew[xs] < 7000:
    x_5.append(xnew[xs])
    y_5.append(y_median[xs])
    y_5_plus.append(y_medPlSd[xs])
    y_5_minus.append(y_medMinSd[xs])
  # x5 = np.interp(5000, x_5, y_5)
  # print(x_5)
  # print(y_5)
  #print(range(y_5))  

 
  # print(xnew)
  # print(spln_m(xnew))
  if idx in [1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25]:
   plt.plot(xnew[::-1], spln_m(xnew)[::-1], lw=1.0, linestyle='dotted', color="#660000")
  else:
   plt.plot(xnew[::-1], spln_m(xnew)[::-1], lw=1.0, linestyle='solid', color="#660000")
  
  plt.fill_between(xnew[::-1], spln_mP(xnew)[::-1], spln_mM(xnew)[::-1], color="#660000", alpha=0.2)

# print("For table:")
# print(xnew[::-1])
# print(spln_m(xnew)[::-1])
# print(y_std[::-1])
# print("end table")

plt.xlim(8.5e3, 1.9e3)
plt.ylim(0,1.2)
plt.xscale('log')
plt.xlabel("Time ($\mathregular{10^3}$ years ago)")
plt.ylabel("Cross-Coalescence rate")			# ,  fontsize=14)
plt.yticks([0.0, 0.5, 1.0], ["0.0", "0.5", "1.0"])	# , fontsize = 14)
labels=["2", "4", "6", "8"]
plt.xticks([2000, 4000, 6000, 8000], labels)		# , fontsize = 14)


###
#               Paint 0.25, 0.50, 0.75 CCR
###

# w = np.interp(5000, x_5, y_5)
xinterp=x_5
yinterp=y_5
yinterp_plus=y_5_plus
yinterp_min=y_5_minus

toInterp=[0.25, 0.75]
w=np.interp(x=toInterp, fp=xinterp, xp=yinterp, left=-300, right=-600)
print("Interval 0.25 <= ccr <= 0.75:")
print(w)
plt.fill_between(w, 0, 1.2, color='#cc0000', alpha=0.2)


plt.plot(w[1], toInterp[1], marker='o', markersize=6, color="#cc0000")
plt.plot(w[0], toInterp[0], marker='o', markersize=6, color="#cc0000")

toInterp=[0.5]
w=np.interp(x=toInterp, fp=xinterp, xp=yinterp, left=-300, right=-600)
plt.plot(w, toInterp, marker='o', markersize=6, color="#cc0000")
plt.plot([w, w], [0.0, 1.2], color='#cc0000', linestyle='-', linewidth=1)
print("point estimate, ccr=0.5:")
print(w)


###
#       Print CI from different octets
###
print("ci")
print(np.interp(x=toInterp, fp=xinterp, xp=yinterp_plus, left=-300, right=-600))
print(np.interp(x=toInterp, fp=xinterp, xp=yinterp_min, left=-300, right=-600))


###
#               Dadi estimate
###
plt.plot([3685, 3685], [0, 1.2], color='#45818e', linestyle='-', linewidth=1)
plt.fill_between([2608,4762], 0, 1.2, color='#45818e', alpha=0.2)


plt.savefig(args.out +'.pdf', bbox_inches='tight') 
