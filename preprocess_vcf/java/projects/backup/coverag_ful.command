#!/bin/bash

###
#	Calculate coverage
###
vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_18-12-20.vcf.b.gz_forNjWithinCviPaper_2020-05-28.vcf.b.gz
vcf_net=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20.vcf.b.gz_forNjWithinCviPaper_2020-05-28.vcf.b.gz
# bgzip -cd ${vcf} > ${vcf_net}.vcf

results=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/coverage_cviPaper_2
mkdir -p ${results}

for s in {0..340}
	# /srv/biodata/dep_coupland/grp_hancock/andrea/data/pogo_2020-02-04/*snp.vcf.sort.vcf.b.gz
do
	bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=10000]" -M 20000 "
		# bgzip -cd ${file} > ${file}.vcf
		java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.CoverageFromVcf ${vcf_net}.vcf ${s} ${results}
		# rm ${file}.vcf
	"
done
