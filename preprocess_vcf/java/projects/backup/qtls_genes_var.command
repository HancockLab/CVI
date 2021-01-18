#!/bin/bash

echo $1

# scan=${2}
# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Qtl_overlapSelectionScans ${1} ${scan}


# matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-04-23.txt_plusAncXM.txt_snpsOnly.txt
# Because of Ler-0 alone
# 
matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/superMatrix_18-12-27.txt_plusAncXM.txt_snpsOnly.txt
samples=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_19-4-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_19-4-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-4-18.txt
	# capeVerdeos_clean_19-04-23_plusCvi0_no1out.txt
	# santos_clean_19-04-23.txt
samples2=${HOME}/1001plusMor_noCviCan.txt
# mask=${1}
	# ${HOME}/candidates_qtl.txt_mask.txt
# mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/TAIR10_GFF3_genes_transposons.gff_wholeGenome.txt
mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/functionalVariants_qtl.txt_functionalVariants_2020-06-24_mask.txt

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Qtl_toGenes_toVarians_wtIslands ${matrix} ${samples} ${samples2} ${mask}


