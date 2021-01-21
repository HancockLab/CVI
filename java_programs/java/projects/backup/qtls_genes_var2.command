#!/bin/bash

echo $1

scan=${2}
big=${3}
outFold=/srv/biodata/dep_coupland/grp_hancock/andrea/qtls_${big}_${scan}_plus50k_2020-05-20
mkdir -p ${outFold}

# String matrixName, String numPh, String scanName, String bigSmall, String outFold
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Qtl_overlapSelectionScans ${1} ${scan} ${big} ${outFold}


