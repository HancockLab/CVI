#!/bin/bash

###
#	We want here a matrix with matches and mism. because we need to know the total number of bases at risk for different classes of mutations
# 	Also we want the ancestor in. So something with not "_snpsOnly.txt" but yes "_plusAncXM.txt" in the name
###

matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18_final.txt_plusAncXM.txt
	#
	# /srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt
	# _snpsOnly.txt_plusAncXM.txt
	
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_snpsOnly.txt_plusAncXM.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt_snpsOnly.txt 
	# _0_ is whith this: /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_29-04-18.txt_plusAncestors.txt_snpsOnly.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt

tag0=12-11-18
	# 8-10-18_withCpGCentr

# pops=(S1S8 S1S8S20 S3 S4 S5 S7 S10 S11 S15 S16 S20)
pops=(F10 F11 F12 F13 F14 F15 F16 F1F5 F1 F2 F3 F4 F5 F7 F8 F9 Finf)

for file in interg.command # zeroFold.command fourFold.command # wg.command interg.command cns.command utr.command
	# highImpact.command lowImpact.command modifierImpact.command syn.command nonSyn.command # wg.command interg.command fourFold.command zeroFold.command cns.command utr.command
do
	for s in ${!pops[*]}
	do
		echo $s
		sant=/home/fulgione/java/projects/${pops[s]}.txt.id.txt
		# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
		# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18_nos1s8s20s11-57.txt
		# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_onePerStand.txt
		# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
		# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_onePerStand.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s1-8.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s7.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s5.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s3.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s4.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s11.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s10.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s15.txt /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_s16.txt 
		 
		#tag1="$(echo ${sant} | cut -d '/' -f 7)"
		#tag=${tag0}_"$(echo ${tag1} | cut -d '_' -f 4)"
		#echo $tag
		#fog=/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_11-7-18.txt
			# /srv/biodata/dep_coupland/grp_hancock/VCF/s1s8.txt
			# /srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_onePerStand.txt
		for f in ${!pops[*]}
		do
			if (( f > s ))
			then
				fog=/home/fulgione/java/projects/${pops[f]}.txt.id.txt
				mask="$(echo ${file} | cut -d '.' -f 1)"
				tag1=/netscratch/dep_coupland/grp_hancock/andrea/sfs/sfs_${mask}_${tag0}
				mkdir -p ${tag1}
				tag=${tag1}/${pops[s]}-${pops[f]}
				echo ${tag}
				bsub -q multicore20 -n 1 -R "span[hosts=1] rusage[mem=26000]" -M 30000 "./${file} ${matrix} ${tag} ${sant} ${fog}"
			fi
		done
	done
done


###
#		QTLs
###

#sant=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_onePerStand.txt
#for mask in /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/2:germ_fr_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/3:germ_lfr_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/5:DSDS50_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/angle_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CA_contFR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CA_HIR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CA_hrlyFR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CA_hrlyR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CA_LFR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CA_VLFR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CLN_LD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CLN_LDV_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CLN_SD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/CR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/delta13_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/EOD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/EOD_response_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/FT_LD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/FT_LDV_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/FT_SD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/germ_red_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/germ_vlfr_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/HGI_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/HL_contFR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/HL_dark_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/HL_hrlyFR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/HL_hrlyR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/HL_LFR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/HL_VLFR_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/Lcl_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/Rhf_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/RLN_LD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/RLN_LDV_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/RLN_SD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/Root_length_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/TLN_LD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/TLN_LDV_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/TLN_SD_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/VGI_mask.txt /srv/biodata/dep_coupland/grp_hancock/andrea/qtl_masks/WL_mask.txt
#do
#	tag1="$(echo ${sant} | cut -d '/' -f 7)"
#	tag=${tag0}_"$(echo ${tag1} | cut -d '_' -f 3)"
#	echo $tag
#	bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=26000]" -M 30000 "./qtls.command ${matrix} ${tag} ${sant} ${mask}"
#done


