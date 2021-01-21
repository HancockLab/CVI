#!/bin/bash

for inf in /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/cviLongBranch/*
	# /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/wEurope/*
	# /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/ibc/*
	# /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/cEurope/cEurope_04fold_polyDFE.txt
	# /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/asia/*
	# /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/smAtlas_04fold_polyDFE.txt /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/nmAtlas_04fold_polyDFE.txt
	# /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/highAtlas_04fold_polyDFE.txt /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/rif_04fold_polyDFE.txt
	# /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/fogo_onePerStand_polyDFE.txt /srv/netscratch/dep_coupland/grp_hancock/software/polyDFE-master/input/santo_onePerStand_polyDFE.txt	 
do
	# for model in A C del 
		# classic A C del 
	# do
		# for div in true false
		# do
			bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./dfe_poly.command ${inf}"
			# bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "./dfe_poly2.command ${inf} ${model} ${div}"
		# done
	# done
done

