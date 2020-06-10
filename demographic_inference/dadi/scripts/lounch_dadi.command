#!/bin/bash
#

# python simple_unfolded.py 6 exp ./results_cvi_2020-04-21/

sfs=/srv/netscratch/dep_coupland/grp_hancock/andrea/sfs/sfs_interg_2020-06-03_5percN/clean_2020-04-27_sub40_dadiJsfsX.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs/sfs_interg_2020-05-21/clean_2020-04-27_sub40_dadiJsfsX.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs/sfs_interg_2020-05-18/clean_2020-04-27_sub40_dadiJsfsX.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs/sfs_interg_2020-04-27/clean_2020-04-27_sub40_dadiJsfsX.txt 

# results=./results_cvi_interg_jsfs-sub40-2020-04-27_2020-04-28/
results=./results_cvi_interg_jsfs-sub40-2020-04-27_2020-06-03_5percN_folded/
	# ./results_cvi_interg_jsfs-sub40-2020-04-27_2020-05-27_folded/
	# ./results_cvi_interg_jsfs-sub40-2020-04-27_2020-04-28_folded/

mkdir -p ${results}

for r in {0..999}
do
 # 
 # for model in exp pop_split im
 #do
  bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000  "
  	python simple_unfolded.py ${r} 'pop_split' ${results} ${sfs} 
   	python simple_unfolded.py ${r} 'exp' ${results} ${sfs}
  	python simple_unfolded.py ${r} 'im' ${results} ${sfs}
	python simple_unfolded.py ${r} 'bottleneck' ${results} ${sfs}
  "
  # python simple_unfolded.py ${r} ${model} ${results}
  #
 #done
done

