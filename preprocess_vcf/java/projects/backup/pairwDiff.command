#!/bin/bash

###
#		Call pairwise differences on 1723 samples
###

matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-07_cviMorLyrLer_noHigCov_2020-05-20.txt_plusAncXM.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-29_africa360_all.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_subBamNewIberia.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_alpina_222GOGnoB_19-08-28.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_18-12-20_checkS21-8.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-04-23.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_s1-1_19-04-12.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_18-12-20_worldMadNewIberia.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_cvi0_19-01-07.txt
#bgzip -cd $matrix.b.gz > $matrix

results=/srv/netscratch/dep_coupland/grp_hancock/andrea/pi/pairwDiff_cviPaper_2020-06-10
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/pi/pi_africa360_2019-11-30/
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/pi_newRelSubBam_19-09-07/
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/pi_alpina_19-08-29/
	# /netscratch/dep_coupland/grp_hancock/andrea/pi_checkS21-8/
	# pi_allCvi_19-04-23/
	# pi_s1-1_cviClean_19-04-13/
	# iberianNew_19-04-05/
	# pi_cvi0_19-01-12/
mkdir -p $results

echo "Go 1787 Pairwise!"
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Pairwise_shore ${matrix} ${1} ${results}
echo "Done :)"

echo "Go Pairwise cvi!"
results=/netscratch/dep_coupland/grp_hancock/andrea/piS10-S11_win
	# piCvi_8-10-2018_win
mkdir -p ${results}${1}

matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18_final.txt_plusAncXM.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt
#santos=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
#fogos=/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_11-7-18.txt

s=/home/fulgione/java/projects/S10.txt.id.txt
f=/home/fulgione/java/projects/S11.txt.id.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Pairwise_shore_cvi ${matrix} ${results} ${1} ${s} ${f}

#cp ${results}${1} /srv/biodata/dep_coupland/andrea/

###
#		Single samples pairwise
###

oldNames=/srv/biodata/dep_coupland/grp_hancock/andrea/pi_4-2018/names.txt
newNames=/home/fulgione/java/projects/newSamples_7-18.txt
oldPiMatrix=/srv/biodata/dep_coupland/grp_hancock/andrea/pi_4-2018/matrix.txt
matrix=/netscratch/dep_coupland/grp_hancock/andrea/superMatrix_12-7-18.txt
results=/netscratch/dep_coupland/grp_hancock/andrea/pi_7-2018/
mkdir -p $results

anc=${HOME}/data/cvi_ancestralStates.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_addAncestor ${matrix} ${anc}

matrix2=${matrix}_plusAncestors.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SingleSample_pairwise ${oldNames} ${newNames} ${oldPiMatrix} ${matrix} ${results}


#mv $results /srv/biodata/dep_coupland/grp_hancock/andrea/
echo "Done! :)"


