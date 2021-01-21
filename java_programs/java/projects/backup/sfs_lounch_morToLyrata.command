#!/bin/bash

###
#	We want here a matrix with matches and mism. because we need to know the total number of bases at risk for different classes of mutations
# 	Also we want the ancestor in. So something with not "_snpsOnly.txt" but yes "_plusAncXM.txt" in the name
###

matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-05-15_withLyrata.txt
	# For Moroccans polarised to Lyrata:
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-08_wg_cviMorLyr.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-04-23.txt_plusAncXM.txt

tag0=2020-02-21

#pops=(S1S8 S1S8S20 S3 S4 S5 S7 S10 S11 S15 S16 S20)
#pops=(F10 F11 F12 F13 F14 F15 F16 F1F5 F1 F2 F3 F4 F5 F7 F8 F9 Finf)

for file in interg.command # zeroFold.command # cns.command utr.command highImpact.command lowImpact.command modifierImpact.command syn.command nonSyn.command
	# highImpact.command lowImpact.command modifierImpact.command syn.command nonSyn.command # wg.command interg.command fourFold.command zeroFold.command cns.command utr.command
do
	for s in /home/fulgione/highAtlas.txt /home/fulgione/southMiddleAtlas.txt /home/fulgione/northMiddleAtlas.txt
		# /home/fulgione/ids/*subset.txt
		# /home/fulgione/ids/asia*
		# /home/fulgione/riff_noZin.txt 
		# /home/fulgione/southMiddleAtlas.txt /home/fulgione/northMiddleAtlas.txt
		# /home/fulgione/highAtlas.txt /home/fulgione/southMiddleAtlas.txt /home/fulgione/northMiddleAtlas.txt /home/fulgione/riff.txt /home/fulgione/riff_noZin.txt 
	do
		echo $s
		tag1="$(echo ${s} | cut -d '/' -f 4)"
		tag2=$(echo ${tag1} | cut -d '.' -f 1)
		echo $tag2
		for f in 1 # ${!pops[*]}
		do
			#if (( f > s ))
			#then
				lyrata=/srv/biodata/dep_coupland/grp_hancock/VCF/lyrata_plink_minus2badQ_2019-11-07.txt
				mask="$(echo ${file} | cut -d '.' -f 1)"
				tag3=/srv/netscratch/dep_coupland/grp_hancock/andrea/sfs/sfs_1001ToLyrata_subset_fogos_${tag0}
				mkdir -p ${tag3}
				tag=${tag3}/${mask}_${tag2}_toLyrata
					# ${pops[s]}-${pops[f]}
				echo ${tag}
				# Normal:
				# bsub -q multicore20 -n 1 -R "span[hosts=1] rusage[mem=26000]" -M 30000 "./${file} ${matrix} ${tag} ${s} ${lyrata}"

				# 2 pops polarised to lyrata (cvi-mor)
				santos=/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_19-04-23.txt
					#santos=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_19-04-23.txt
				bsub -q multicore20 -n 1 -R "span[hosts=1] rusage[mem=26000]" -M 30000 "./${file} ${matrix} ${tag} ${santos} ${s} ${lyrata}"
			#fi
		done
	done
done
