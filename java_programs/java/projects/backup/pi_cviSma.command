#!/bin/bash


###
#		Call minimum pairwise differences between cvi and mor
###

#matrix=/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_cvi0_19-01-07.txt

matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-07.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_18-12-27.txt_plusAncXM.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_18-12-27.txt_plusAncXM.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_29-04-18.txt_plusAncestors.txt
results=/srv/netscratch/dep_coupland/grp_hancock/andrea/pi/pi_cviSma_2020-01-08
	# /netscratch/dep_coupland/grp_hancock/andrea/pi_cviSma_19-02-04
mkdir -p ${results}_${1}

echo "Go Pairwise cvi!"

s=/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-04-23.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt
f=/home/fulgione/southMiddleAtlas.txt

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Pairwise_shore_cviMor ${matrix} ${results} ${1} ${s} ${f}
sleep 30s

cp -r ${results}_${1} /srv/biodata/dep_coupland/grp_hancock/andrea/

