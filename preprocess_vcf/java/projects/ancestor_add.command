#!/bin/bash

###
# 		In TAIR10 calls:
#		Add ancestor
###
matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-07_cviMorLyrLer_noHigCov_2020-05-20.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-07_cviMorLyrLer_noHigCov.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-04-23.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_cvi0_19-01-07.txt
anc=${HOME}/data/cvi_ancestralStates_12-7-18.txt


###
###
#
# 	For S1-1
#
###
###

matrix=/srv/netscratch/irg/grp_hancock/andrea/vcf/superMatrix_s1-1_2020-09-25_wg_noHigCov.txt
anc=/srv/biodata/irg/grp_hancock/andrea/estSFS_s1-1_2020-09-25_ancestralState.txt_mlK.txt_toIntegrate.txt



###
#	For both 



java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_addAncestor ${matrix} ${anc}
sleep 30s

echo "Added!"

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_snpsOnly ${matrix}_plusAncXM.txt

echo "Snps only!"






