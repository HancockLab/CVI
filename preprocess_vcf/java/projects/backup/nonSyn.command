#!/bin/bash

echo $1

###
#		Get JSFS
###

matrix=${1}
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_29-04-18.txt_plusAncestors.txt
#bgzip -cd $matrix.b.gz > $matrix

mask=/srv/biodata/dep_coupland/grp_hancock/andrea/zeroFourfold_28-7-18/nonSyn_mask.txt
funcDetail=/srv/biodata/dep_coupland/grp_hancock/andrea/zeroFourfold_28-7-18/nonSyn_breakDown.txt

java -Xmx8G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Jsfs_functional ${matrix} ${mask} ${2} ${3} ${funcDetail}


