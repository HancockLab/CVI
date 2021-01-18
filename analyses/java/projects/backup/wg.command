#!/bin/bash

echo $1

###
#		Get JSFS
###

matrix=${1}

#matrix1=/netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt
#anc=${HOME}/data/cvi_ancestralStates_12-7-18.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_addAncestor ${matrix1} ${anc}

echo "Added!"

#matrix=${matrix1}_plusAncXM.txt
	#/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_29-04-18.txt_plusAncestors.txt

#bgzip -cd $matrix.b.gz > $matrix

#mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/cviMaskintergenicMask.txt
#mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/cviMasksynonimousMask.txt
#mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/cviMasknonSynonimousMask.txt
#mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/TAIR10_GFF3_genes_transposons.gff_interG_superMask_fromGff.txt

## New Masks

#maskFold=/srv/biodata/dep_coupland/grp_hancock/andrea/masks_cvi_func
#mask=$maskFold/fourFoldMask.txt
#mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/TAIR10_GFF3_genes_transposons.gff_UTR_superMask_fromGff.txt

mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/TAIR10_GFF3_genes_transposons.gff_wholeGenome.txt

# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS $matrix $RUN/sfs_haSma

# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS_polLyrata ${matrix} ${mask} ${2} ${3} ${4}

 java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS_morPol ${matrix} ${mask} ${2} ${3} ${4}


# Matrix_getJSFS_morPol
