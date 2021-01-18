#!/bin/bash

echo $1

vcfJuly=/biodata/dep_coupland/grp_hancock/africa_arabidopsis/fullset5/data/superVcf_29-7-16.vcf

bgzip -cd $vcfJuly.b.gz > $vcfJuly

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.CoverageFromVcf $vcfJuly


