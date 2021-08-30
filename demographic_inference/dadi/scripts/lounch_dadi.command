#!/bin/bash
#

# python simple_unfolded.py 6 exp ./results_cvi_2020-04-21/

sfs= # Path to *_dadiJsfsX.txt file produced by ./CVI/java_scripts/java/projects/sfs_lounch_2popsToLyrata.command 
results= # path to results folder

mkdir -p ${results}

for r in {0..999}
do
  	python simple_unfolded.py ${r} 'pop_split' ${results} ${sfs} 
   	python simple_unfolded.py ${r} 'exp' ${results} ${sfs}
  	python simple_unfolded.py ${r} 'im' ${results} ${sfs}
	python simple_unfolded.py ${r} 'bottleneck' ${results} ${sfs}
done


## To launch uncertainty analyses:
# python uncert_dadi.py ${sfs} ${results}


## To launch dadi on three populations mind that you need to input the 3-pops Jsfs
# python simple_unfolded.py ${r} 'threePops_1' ${results} ${sfs}

