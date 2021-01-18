#!/bin/bash

echo $1

###
#		Get JSFS
###

matrix=/srv/netscratch/irg/grp_hancock/andrea/vcf/superMatrix_2019-11-07_cviMorLyrLer_noHigCov_2020-05-20.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-07_cviMorLyrLer_noHigCov.txt_plusAncXM.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-05-15_withLyrata.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/others/superMatrix_2019-11-24_pogoniulus.txt_matrix_c3q15.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_pogoniulus_2020-01-11.txt_c3q15.txt

#
for samples1 in /home/fulgione/highAtlas.txt /home/fulgione/morocco.txt # /home/fulgione/southMiddleAtlas.txt /home/fulgione/northMiddleAtlas.txt /home/fulgione/morocco.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-04-23_final.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_19-04-23_noLastLanes.txt /srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_19-04-23_noLastLanes.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_19-04-23.txt /srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_19-04-23.txt
do
	# /home/fulgione/highAtlas.txt /home/fulgione/southMiddleAtlas.txt /home/fulgione/northMiddleAtlas.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/birds/final_sample_files_withAlex2020/pogoniulus_tanz_extoni_dipFullNames.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/birds/final_sample_files_withAlex2020/pogoniulus_sAfrica_extoni_dipFullNames.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/birds/final_sample_files_withAlex2020/pogoniulus_kenya_chrysoconus_check_dipFullNames.txt
	# Here, old files from before we checked samples with Alex:
	# 
	# /home/fulgione/bird_genomes/pogoniulus_tanz_chrysoconus_dipFullNames.txt
	# ${HOME}/bird_genomes/pogoniulus_sAfrica_chrysoconus_dipFullNames.txt
	# ${HOME}/bird_genomes/pogoniulus_kenya_chrysoconus_check_dipFullNames.txt
	# ${HOME}/bird_genomes/pogoniulus_kenya_chrysoconus_check.txt

# 	
for samples2 in /srv/biodata/irg/grp_hancock/VCF/fogos_clean_19-04-23_noLastLanes.txt # /srv/biodata/irg/grp_hancock/VCF/santos_clean_19-04-23_noLastLanes.txt
do
	# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_19-04-23_noLastLanes.txt /srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_19-04-23_noLastLanes.txt /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-04-23_final.txt
	# /home/fulgione/morocco.txt
	# /home/fulgione/highAtlas.txt /home/fulgione/southMiddleAtlas.txt /home/fulgione/northMiddleAtlas.txt
for samples3 in /srv/biodata/irg/grp_hancock/VCF/santos_clean_19-04-23_noLastLanes.txt # /srv/biodata/irg/grp_hancock/VCF/fogos_clean_19-04-23_noLastLanes.txt

do
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/birds/final_sample_files_withAlex2020/pogoniulus_tanz_affinis_dipFullNames.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/birds/final_sample_files_withAlex2020/pogoniulus_sAfrica_pusillus_dipFullNames.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/birds/final_sample_files_withAlex2020/pogoniulus_kenya_affinis_check_dipFullNames_copy.txt
	# Here, old files
	# 
	# /home/fulgione/bird_genomes/pogoniulus_tanz_pusillus_dipFullNames.txt
	# ${HOME}/bird_genomes/pogoniulus_sAfrica_pusillus_dipFullNames.txt
	# ${HOME}/bird_genomes/pogoniulus_kenya_pusillus_check_dipFullNames.txt
	# ${HOME}/bird_genomes/pogoniulus_kenya_pusillus_check.txt
	samplesOutgroup=/srv/biodata/irg/grp_hancock/VCF/lyrata_plink_minus2badQ_2019-11-07.txt
	# ${HOME}/bird_genomes/pogoniulus_outgroup_dipFullNames.txt
	# ${HOME}/bird_genomes/pogoniulus_outgroup.txt

	local1=$(echo ${samples1} | cut -d "/" -f 4)
	local2=$(echo ${samples2} | cut -d "/" -f 7)
	echo ${local1}
	short1=$(echo ${local1} | cut -d "_" -f 1)
	short2=$(echo ${local2} | cut -d "." -f 1)

	results=/srv/netscratch/irg/grp_hancock/andrea/sfs_mor-fo-sa_2020-09-24/${short1}-${short2}/
	mkdir -p ${results}
	echo ${results}
	mask=/srv/biodata/irg/grp_hancock/andrea/functionalMasks_cvi/TAIR10_GFF3_genes_transposons.gff_interG_superMask_fromGff.txt

# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS_generalAndPogoniulus ${matrix} null ${results}/pogo ${samples1} ${samples2} ${samplesOutgroup}

	bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=26000]" -M 30000 "java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS_polLyrata_3pops ${matrix} ${mask} ${results}${short1}-${short2} ${samples1} ${samples2} ${samples3} ${samplesOutgroup}"
	# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS_polLyrata_3pops ${matrix} ${mask} ${results}${short1}-${short2} ${samples1} ${samples2} ${samples3} ${samplesOutgroup}

done
done
done
