#!/bin/bash
#

# python simple_unfolded.py 6 exp ./results_cvi_2020-04-21/

for sfs in /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-06-03/fogos-highAtlas/fogos-highAtlas_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-06-03/fogos-northMiddleAtlas/fogos-northMiddleAtlas_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-06-03/fogos-southMiddleAtlas/fogos-southMiddleAtlas_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-06-03/santos-highAtlas/santos-highAtlas_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-06-03/santos-northMiddleAtlas/santos-northMiddleAtlas_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-06-03/santos-southMiddleAtlas/santos-southMiddleAtlas_dadiJsfs.txt
	
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-05-21/fogos-highAtlas/_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-05-21/santos-highAtlas/_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-05-21/fogos-northMiddleAtlas/_dadiJsfs.txt  /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-05-21/santos-northMiddleAtlas/_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-05-21/fogos-southMiddleAtlas/_dadiJsfs.txt /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs_cvi-mor_2020-05-21/santos-southMiddleAtlas/_dadiJsfs.txt
do
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs/sfs_interg_2020-05-18/clean_2020-04-27_sub40_dadiJsfsX.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/sfs/sfs_interg_2020-04-27/clean_2020-04-27_sub40_dadiJsfsX.txt 

# results=./results_cvi_interg_jsfs-sub40-2020-04-27_2020-04-28/
results=${sfs}_results/
	# ./results_cvi_interg_jsfs-sub40-2020-04-27_2020-04-28_folded/

mkdir -p ${results}

for r in {0..249}
do
 # 
 # for model in exp pop_split im
 #do
  bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000  "
  	python simple_unfolded.py ${r} 'pop_split' ${results} ${sfs} 
   	python simple_unfolded.py ${r} 'exp' ${results} ${sfs}
 	python simple_unfolded.py ${r} 'bottleneck' ${results} ${sfs}
 	python simple_unfolded.py ${r} 'im' ${results} ${sfs}
  "
  # python simple_unfolded.py ${r} ${model} ${results}
  #
 done
done

