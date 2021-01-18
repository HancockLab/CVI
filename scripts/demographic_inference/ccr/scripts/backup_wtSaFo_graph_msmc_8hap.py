#!/bin/ython


###
#	To run from terminal as:
#	python ./scripts/demographic_inference/ccr/scripts/./backup_wtSaFo_graph_msmc_8hap.py -l "S_cova-S1S8","S_cova-S_pico","S_cova-S_espong","S1S8-S_pico","S1S8-S_espong","S_pico-S_espong","Fogo - Santo Antao" -f ./scripts/demographic_inference/ccr/data/wtSanto_2020-05-14_s20-s1s8_comparisons.txt,./scripts/demographic_inference/ccr/data/wtSanto_2020-05-14_s20-s10_comparisons.txt,./scripts/demographic_inference/ccr/data/wtSanto_2020-05-14_s20-s11_comparisons.txt,./scripts/demographic_inference/ccr/data/wtSanto_2020-05-14_s1s8-s10_comparisons.txt,./scripts/demographic_inference/ccr/data/wtSanto_2020-05-14_s1s8-s11_comparisons.txt,./scripts/demographic_inference/ccr/data/wtSanto_2020-05-14_s11-s10_comparisons.txt,./scripts/demographic_inference/ccr/data/cvi_comparisons.txt -o edf5b
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
from astropy.stats.funcs import bootstrap
import matplotlib.colors as colors
from fractions import Fraction
import matplotlib.patches as mpatches

plt.rcParams['font.family'] = 'sans-serif'
plt.rcParams['font.sans-serif'] = 'Helvetica'



matplotlib.rcParams.update({'font.size': 7})


split_res=[]

colors=[]
# "S_cova-S1S8","S_cova-S_pico","S_cova-S_espong","S1S8-S_pico","S1S8-S_espong","S_pico-S_espong","Fogo - Santo Antao"

colors.append('#00BFFF')
colors.append('#00BFFF')
colors.append('#00BFFF')
colors.append('#ccccff')
colors.append('#ccccff')
colors.append('#0000FF')

colors.append('#980000')




# Wt santo:
# colors.append('sienna')
# colors.append('deeppink')
# colors.append('silver')
# colors.append('#660000')

# # Wtboth islands
# colors.append('#a4c2f4')
colors.append('#1155cc')
colors.append('#1155cc')
colors.append('#1155cc')

# colors.append('#073763')
# colors.append('#660000')

colors.append('#ff9900')
colors.append('#ff9900')

colors.append('#cc4125')

colors.append('#980000')


colors.append('#660000')

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

###
#		For CVI alone
x_min = 10
x_max = 20000

###
#               For CVI and Morocco
#x_min = 3000
#x_max = 100000


plt.figure(figsize=(3.5,2.4))

xnew = np.linspace(x_min, x_max, num=1000, endpoint=True)
y_list = []
# plt.figure(figsize=(7,5)) # 12,7)) # 7,5)) # 12,3))

if not args.means:
 for idx,f in enumerate(file_list):
  f2 = open(f,'r')
  y_list = []
  for line in f2:
   print(f)
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
    #y.append(2*float(line.split('\n')[0].split('\t')[4])/(float(line.split('\n')[0].split('\t')[3]) + float(line.split('\n')[0].split('\t')[5]) ))
    
    ###
    # For cvi - mor:
    ###
    
    y.append(float(line.split('\n')[0].split('\t')[4])/( float(line.split('\n')[0].split('\t')[5]) ))
    n = n + 1
   x_rev = x[::-1]
   y_rev = y[::-1]
   x_rev=x_rev[5:len(x_rev)]
   y_rev=y_rev[5:len(y_rev)]
   print(len(x_rev))
   xnew = np.linspace(x_rev[0], x_rev[len(x_rev)-1], num=1000, endpoint=True)
   spln = interp1d(x_rev, y_rev, kind="cubic",bounds_error=False)
   #print(spln(xnew))
   y_list.append(spln(xnew))
  y_median=[]
  y_std = []
  for s in range(len(y_list[0])):
   y_temM = []
   for i in range(len(y_list)):
    y_temM.append(y_list[i][s]) 
   y_median.append(np.median(np.array(y_temM), axis=0))
   y_std.append(np.std(np.array(y_temM),axis=0))
  spln_m = interp1d(xnew, y_median, kind="cubic",bounds_error=False)
  
  # print("xnew") 
  # print(len(xnew))
  x_5 = []
  y_5 = []
  for xs in range(1, (len(xnew)-1)):
   if xnew[xs] > 2000 and xnew[xs] < 7000:
    x_5.append(xnew[xs])
    y_5.append(y_median[xs])
  x5 = np.interp(5000, x_5, y_5)
  # print(x_5)
  # print(y_5)
  #print(range(y_5))  

  #x5 = np.interp(x=0.15, xp=y_5, fp=x_5, left=-300, right=-600)
  #split_res.append(x5)
  
  # if you want standard error instead of standard dev. Otherwise, comment out
  y_std = 1.96*(y_std/np.sqrt(n))
  
  y_medPlSd = np.asarray(y_median) + np.asarray(y_std)
  y_medMinSd = np.asarray(y_median) - np.asarray(y_std)
  
  spln_mP = interp1d(xnew, y_medPlSd, kind="cubic",bounds_error=False)
  spln_mM = interp1d(xnew, y_medMinSd, kind="cubic",bounds_error=False)
  
  #print idx 
  # For different lines:
  #
  if idx in [1,4]:
    plt.plot(xnew, spln_m(xnew), lw=1.0, linestyle='dashed', color=colors[idx])
  else:
    if idx in [2]:
      plt.plot(xnew, spln_m(xnew), lw=1.0, linestyle='dotted', color=colors[idx])
    else:
      plt.plot(xnew, spln_m(xnew), lw=1.0, linestyle='solid', color=colors[idx])
  
  # For the same line
  #
  # plt.plot(xnew, spln_m(xnew), lw=1.0, linestyle='solid', color=colors[idx])
  
  #print(xnew)
  #print(spln_m(xnew))
  #if idx != 13:
  
  
  # TO UNCOMMENT!!!
  
  plt.fill_between(xnew, spln_mP(xnew), spln_mM(xnew), color=colors[idx], alpha=0.2)
  
  #else:
   #print f
   #plt.fill_between(xnew, spln_mP(xnew), spln_mM(xnew), color=colors[idx], alpha=0.5)

###
#	For cvi alone:
###

 plt.xscale('log')
 plt.xlim(1.9e3, 10e3) # 7.5
 plt.ylim(0,1.2)

 #labels=["2", "4", "6"]
 #plt.xticks([2000, 4000, 6000], labels, fontsize = 14)
 
 labels=["2", "4", "6", "8"]
 plt.xticks([2000, 4000, 6000, 8000], labels) # , fontsize = 14)
###
#	For cvi and morocco
###

 # plt.xlim(0e3, 100e3)
 # plt.ylim(0,1.05)
 
 #labels=["25", "50", "75"]
 #plt.xticks([25000, 50000, 75000], labels, fontsize = 14)





 plt.xlabel("Time ($\mathregular{10^3}$ years ago)")
 plt.ylabel("Cross-Coalescence rate") # ,  fontsize=14)
 
 plt.yticks([0.0, 0.5, 1.0], ["0.0", "0.5", "1.0"]) # , fontsize = 14)
 plt.gca().invert_xaxis()
 
 
 ###
 #      The legend
 ###
 morL = mpatches.Patch(color=('sienna'), label='Madeira - Morocco Atlas', linestyle='solid')
 weuL = mpatches.Patch(color=('mediumblue'), label='Madeira - Eurasian non-relicts', linestyle='solid')
 
 #leg = plt.legend(legend, loc='lower left', frameon=False, ncol=1)
 #for legobj in leg.legendHandles:
 # legobj.set_linewidth(4.0)
 
 
 #plt.fill_between([0,3e4], 0, 3e5, color='grey', alpha=0.2)
 
#  plt.fill_between([1e3,2e3], 0, 3e5, color='grey', alpha=0.2)

#plt.plot([30000, 2000000], [1, 1], color='black', linestyle='-', linewidth=1)
#plt.plot([30000, 2000000], [0.75, 0.75], color='black', linestyle='-', linewidth=1)

#plt.plot([36648.6475, 36648.6475], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([48580.7945, 48580.7945], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([64397.8361, 64397.8361], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([85364.6248, 85364.6248], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([113157.827, 113157.827], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([1.50000000e+05, 1.50000000e+05], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([200025.07964745, 200025.07964745], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([266733.54991978, 266733.54991978], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([355689.33044901, 355689.33044901], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([474311.91101873, 474311.91101873], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([632495.18519503, 632495.18519503], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([843432.66530175, 843432.66530175], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([1124717.90702827, 1124717.90702827], [0, 2], color='black', linestyle='-', linewidth=1)
#plt.plot([1499811.92622827, 1499811.92622827], [0, 2], color='black', linestyle='-', linewidth=1)

###
#		For Cvi with Morocco
###
#plt.plot([45000, 60000], [0, 1.2], color='b', linestyle='-', linewidth=1)
#plt.fill_between([47500,60500], 0, 1.2, color='b', alpha=0.2)


# For cvi:
plt.plot([1000, 20000], [0.5, 0.5], color='black', linestyle='-', linewidth=0.5)
# plt.plot([8000, 8000], [0.0, 1.0], color='black', linestyle='-', linewidth=1)
# plt.plot([7000, 7000], [0.0, 1.0], color='black', linestyle='-', linewidth=1)

#######
####		Split time to Morocco:
#######

###
#		CCR estimate
###
#plt.plot([5000, 5000], [0, 1.2], color='b', linestyle='-', linewidth=1)
#plt.fill_between([4820,5250], 0, 1.2, color='b', alpha=0.2)

###
#		Paint 0.25, 0.50, 0.75 CCR
###

# w = np.interp(5000, x_5, y_5)
xinterp=x_5[::-1]
yinterp=y_5[::-1]
toInterp=[0.25, 0.75]
w=np.interp(x=toInterp, fp=xinterp, xp=yinterp, left=-300, right=-600)
# plt.stem(w, toInterp, linefmt='k:', c='orange', basefmt=" ")
# plt.fill_between(w, 0, 1.2, color='#cc0000', alpha=0.2)
# plt.plot(tointerp, w, 'or')

# plt.plot([w[0], w[0]], [0.0, 1.2], color='black', linestyle=':', linewidth=0.5)
# plt.plot([w[1], w[1]], [0.0, 1.2], color='black', linestyle=':', linewidth=0.5)
# plt.plot(w[1], toInterp[1], marker='o', markersize=6, color="#cc0000")
# plt.plot(w[0], toInterp[0], marker='o', markersize=6, color="#cc0000")

toInterp=[0.5]
w=np.interp(x=toInterp, fp=xinterp, xp=yinterp, left=-300, right=-600)
# plt.stem(w, toInterp, linefmt='k-', c='orange', basefmt=" ")
# plt.stem(w, [1.2], linefmt='k-', c='orange', basefmt=" ")
# plt.stem(w, toInterp, basefmt=" ")
# plt.plot(w, toInterp, marker='o', markersize=6, color="#cc0000")

# plt.plot([w, w], [0.0, 1.2], color='#cc0000', linestyle='-', linewidth=1)

###
#		Dadi estimate
###
# old
#plt.plot([3500, 3500], [0, 1.2], color='darkgoldenrod', linestyle='-', linewidth=1)
#plt.fill_between([3000,4000], 0, 1.2, color='darkgoldenrod', alpha=0.2)

# new:
# plt.plot([4382, 4382], [0, 1.2], color='#45818e', linestyle='-', linewidth=1)
# plt.fill_between([3549,4953], 0, 1.2, color='#45818e', alpha=0.2)
# plt.plot([3549, 3549], [0.0, 1.2], color='b', linestyle=':', linewidth=0.5)
# plt.plot([4953, 4953], [0.0, 1.2], color='b', linestyle=':', linewidth=0.5)











# For cvi-mor
#
#plt.plot([10, 100000], [0.75, 0.75], color='black', linestyle='-', linewidth=1)
#plt.fill_between([3000,4000], 0, 1.2, color='darkgoldenrod', alpha=0.2)

#plt.plot([5000, 5000], [0.0, 1.25], color='black', linestyle='-', linewidth=1)

#f3 = open(args.out+'_075.txt', 'w+')

#_" + str(sys.argv[1]) + ".txt", 'w+')
#for w in split_res:
# print(w)
# s = str(w)
# f3.write(s+"\t")
#f3.write('\n')
#f3.close()

plt.savefig(args.out+'.pdf', bbox_inches='tight') 

