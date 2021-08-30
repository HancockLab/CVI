#!/bin/bash
# undo the gzip, append missing columns, bgzip (with new filename [sample number]), tabix


set -e
set -u
# If you want to add a mask for centromeres and repeats:
mask=./data/repeatCentromCpg_out_mask.txt.gz

cd ${HOME}/java/projects/
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.MasksCombineTwo ${1} ${mask}

WORK= # Path to mask directory
if [[ ${2} = "-o" ]]; then
    SAMPLE_NAME=`basename "${1}"` #need to get sample NUMBER
    SAMPLE_NUMBER=`echo ${SAMPLE_NAME} | cut -d "." -f 1`
fi
if [[ ${2} = "-m" ]]; then
    SAMPLE_NAME=`basename "${1}"` #need to get sample NUMBER
    S=`echo ${SAMPLE_NAME} | cut -d "." -f 2`
    SAMPLE_NUMBER=`echo ${S} | cut -d "_" -f 1`
    newName=`echo ${S} | cut -d "_" -f 1``echo ${S} | cut -d "_" -f 2`
fi

ln -sf ${1} ${WORK}/${newName}.txt.gz

ln -sf ${1}_noRepCent.txt $WORK/${newName}_noRC.txt.gz
