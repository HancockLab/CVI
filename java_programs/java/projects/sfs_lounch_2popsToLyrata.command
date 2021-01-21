#!/bin/bash

# Run as ./file_name
# To compute joint site frequency spectra and marginal spectra and spectra in dadi format

###
###
#		This script assumes that you have the ./java/ folder from GitHub in your home directory
# 		If not so, you will need to change some paths (e.g. to the libraries in ./java/lib/)
###
###

cd ./CVI/java_programs/java/projects/

###
#		Get JSFS
###
matrix= "Path to SNP matrix created by matrix_cvi.command "
samplesOutgroup=./CVI/data/lyrata_plink_minus2badQ_2019-11-07.txt
mask=./CVI/data/TAIR10_GFF3_genes_transposons.gff_interG_superMask_fromGff.txt
maskRepeats=./CVI/data/masks_cvi_func/repeatCentromCpg_out_mask.txt
gzip -cd ${mask}.gz > ${mask}
gzip -cd ${maskRepeats}.gz > ${maskRepeats}

#
for samples1 in ./CVI/data/capeVerdeos_clean_final_2021-01-18.txt
	# For Santo Antao Versus Fogo:
	# for samples1 in ./data/santos_clean_2021-01-18.txt
do
	for samples2 in ./CVI/data/morocco.txt
		# For Santo Antao versus Fogo:
		# for samples2 in ./data/fogos_clean_2021-01-18.txt
	do

		local1=$(echo ${samples1} | cut -d "/" -f 4)
		local2=$(echo ${samples2} | cut -d "/" -f 7)
		echo ${local1}
		short1=$(echo ${local1} | cut -d "_" -f 1)
		short2=$(echo ${local2} | cut -d "." -f 1)
		
		results=./results/${short1}-${short2}/
		mkdir -p ${results}
		echo ${results}
		java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS_polLyrata_2pops ${matrix} ${mask} ${results}${short1}-${short2} ${samples1} ${samples2} ${samplesOutgroup}
	done
done
