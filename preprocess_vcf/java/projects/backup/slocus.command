#!/bin/bash

# Now the proper pipeline :)

# echo $rawFile $file $workDir $whereWe

rawFile=$1
file=$2
workDir=$3
whereWe=$4
BWA=${HOME}/software/bwa-0.7.5a

REF=${HOME}/ATwithSlociCollection2/atAndSlociColl02.fa
mkdir -p ${workDir}



for file in /srv/biodata/dep_coupland/grp_hancock/andrea/data/afr_30-10-18/*mpileup.all
do
	java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SlocusCoverage ${file}
done


