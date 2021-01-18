#!/bin/bash

###
#	We want here a matrix with matches and mism. because we need to know the total number of bases at risk for different classes of mutations
# 	Also we want the ancestor in. So something with not "_snpsOnly.txt" but yes "_plusAncXM.txt" in the name
###


# Red/yellow:
matrix=/srv/netscratch/dep_coupland/grp_fulgione/birds_genomics/pogoniulus/vcf/superMatrix_pogo_2020-11-03.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/others/superMatrix_2020-04-02_redYellowFronted_2.txt_matrix_c3q15.txt
sampleFold=/srv/netscratch/irg/grp_hancock/andrea/birds/final_sample_files_withAlex_2020-04-08
pops=(pogoniulus_kenya_affinis_2020-04-08.txt_simple.txt pogoniulus_sAfrica_extoni_2020-04-08.txt_simple.txt pogoniulus_tanz_affinis_2020-04-08.txt_simple.txt pogoniulus_kenya_chrysoconus_2020-04-08.txt_simple.txt pogoniulus_sAfrica_pusillus_2020-04-08.txt_simple.txt pogoniulus_tanz_extoni_2020-04-08.txt_simple.txt)
outgroup=${sampleFold}/pogoniulus_zetaOutgroupBilineatus_2020-04-08.txt_simple.txt


# Bilineatus:
# matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/others/superMatrix_2020-04-12_PBilineatus.txt_matrix_c3q15.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/others/superMatrix_2020-04-02_PBilineatus_2.txt_matrix_c3q15.txt
# sampleFold=/srv/netscratch/dep_coupland/grp_hancock/andrea/birds/bilineatus_sampleFiles
# pops=(bilineatus-group.txt bilineatus.txt concillator.txt east-leucolaimus.txt fischeri.txt kenya.txt leucolaimus.txt sharpei.txt west-leucolaimus.txt zanzibar.txt)
# outgroup=${sampleFold}/bilin-outgroup.txt

tag0=2020-11-04

len=${#pops[@]}

## Use bash for loop 
for (( i=0; i<$len; i++ ));
do
	# echo "${pops[$i]}"
	for (( j=0; j<$len; j++ ));
	do
		if (( "$j" > "$i"))
		then
			#echo "${pops[$i]}" "${pops[$j]}"
			pop1=${pops[$i]}
			# name1=$(echo ${pop1} | cut -d '/' -f 9 | cut -d '_' -f 1)"_"$(echo ${pop1} | cut -d '/' -f 9 | cut -d '_' -f 2)"-"$(echo ${pop1} | cut -d '/' -f 9 | cut -d '_' -f 3)
			name1=$(echo ${pop1} | cut -d '.' -f 1)
			# echo ${name1}
			pop2=${pops[$j]}
			# name2=$(echo ${pop2} | cut -d '/' -f 9 | cut -d '_' -f 2)"-"$(echo ${pop2} | cut -d '/' -f 9 | cut -d '_' -f 3)
			name2=$(echo ${pop2} | cut -d '.' -f 1)
			# echo ${pop2}
			combo=${name1}"_"${name2}
			tag1=/srv/netscratch/dep_coupland/grp_fulgione/global/bili_${tag0}
			mkdir -p ${tag1}
			tag=${tag1}/${combo}_jsfs_${tag0}
			echo ${tag}
			# echo ${sampleFold}/${pop1}
			bsub -q multicore20 -n 1 -R "span[hosts=1] rusage[mem=26000]" -M 30000 "./sfs_runBatch_pogoniulus.command ${tag} ${sampleFold}/${pop1} ${sampleFold}/${pop2} ${outgroup} ${matrix}"
		fi
	done
done

