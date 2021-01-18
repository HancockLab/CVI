#!/bin/bash

echo $1

RUN=/srv/biodata/dep_coupland/grp_hancock/andrea/vcf
HEAD=/srv/biodata/dep_coupland/grp_hancock/andrea/data/are/headMad.txt

###
#               VCF diploid for fucking European Variation Archive
###

#vcf=/srv/biodata/dep_coupland/grp_hancock/andrea/data/are/madeira.vcf
#vcf=/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superVcf_moroccanPaper.vcf
#vcfOld=/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia.vcf
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia.vcf

vcf=/srv/netscratch/irg/grp_hancock/andrea/vcf/superVcf_2019-11-07_cviMorLyrLer.vcf.b.gz_cvi_2.vcf.b.gz
	# /srv/netscratch/irg/grp_hancock/andrea/vcf/superVcf_2019-11-07_cviMorLyrLer.vcf.b.gz
	# /srv/netscratch/irg/grp_hancock/andrea/vcf/cvi_africa_and1001.EVA.vcf.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz_snps.vcf
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia_outOld.vcf

# bcftools view -r "Chr2" -I ${vcf}.b.gz | bgzip -c > ${vcf}.b.gz_chr2.vcf.b.gz
# bgzip -cd ${vcf}.b.gz_chr2.vcf.b.gz > ${vcf}.b.gz_chr2.vcf.b.gz.vcf
#scp $vcf"_dip.vcf_final.vcf" /netscratch/dep_coupland/grp_hancock/andrea/superVcf_moroccanPaper.vcf_dip.vcf_final.vcf

#bcftools view -S^${HOME}/data/iberiansOut.txt --force-samples -I ${vcfOld}.b.gz | bgzip -c > ${vcf}.b.gz
#bgzip -cd ${vcf}.b.gz > ${vcf}

#vcf=$1
# bgzip -cd ${vcf} > ${vcf}.vcf
vcf=/srv/netscratch/irg/grp_hancock/andrea/vcf/superVcf_2019-11-07_cviMorLyrLer.vcf.b.gz_cvi_2.vcf.b.gz.vcf

#/netscratch/dep_coupland/grp_hancock/andrea/superVcf_moroccanPaper.vcf

# vcf=${vcf}.b.gz_chr2.vcf.b.gz.vcf
# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Vcf_hapToDip ${vcf}


echo "Java done!"


#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Diploidize_sample_pairs_forPopSizeAbc ${vcf}.vcf


HEAD=/srv/biodata/irg/grp_hancock/andrea/vcf/head_cvi_release_2020-10-18.txt
	# /srv/biodata/irg/grp_hancock/andrea/vcf/head_cvi_release_2020-10-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/head_cvi_forRelate.txt
	# head_cvi_chr2_forRelate.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/head_newIb_outOld.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/head_newIb.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/head_santo_pairDip.txt

# For Eva and relicts:
#
# cat ${HEAD} ${vcf}_dip.vcf > ${vcf}_dip.vcf.h
# bgzip -c ${vcf}_dip.vcf.h > ${vcf}_dip.vcf.b.gz

# cat ${HEAD} ${vcf}_dip.vcf | bgzip -c > ${vcf}_dip.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcf}_dip.vcf.b.gz

echo "Header done!"

vcfNew=/srv/netscratch/irg/grp_hancock/andrea/vcf/cvi_africa_and1001.EVA_2020-10-20.vcf.gz
vcfAfr1001=/srv/biodata/irg/grp_hancock/andrea/vcf/final/africa_and1001.EVA.vcf.gz
vcfMad=/srv/biodata/irg/grp_hancock/andrea/vcf/final/madeira.vcf.gz
# sleep 30s
# echo "go Merge!"
# bcftools merge ${vcf}_dip.vcf.b.gz ${vcfAfr1001} | bgzip -c > ${vcfNew}

# md5sum ${vcfNew} > ${vcfNew}.md5

echo "Md5 done!"

# For popSizeABC:
#cat ${HEAD} ${vcf}.vcf_twoHapsToDip.vcf > ${vcf}.vcf_twoHapsToDip.vcf.h
#bgzip -c ${vcf}.vcf_twoHapsToDip.vcf.h > ${vcf}.vcf_twoHapsToDip.vcf_h.vcf.b.gz
#tabix -p vcf ${vcf}.vcf_twoHapsToDip.vcf_h.vcf.b.gz

###
#		vcf-validator for EVA
###
#vcf=/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superVcf_moroccanPaper.vcf_dip.vcf
#HEAD=/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/head.txt
#africaVcf=/netscratch/dep_coupland/grp_hancock/andrea/africa_and1001_final.vcf

#cat $HEAD $vcf > $africaVcf

#cp ${vcf}_dip.vcf.h ${vcf}_dip2.vcf

# bgzip -c ${vcf}_dip2.vcf > ${vcf}_dip2.vcf.b.gz
# sleep 30s
tabix -p vcf ${vcfNew}
sleep 30s
bcftools norm --rm-dup 'all' ${vcfNew} | bgzip -c > ${vcfNew}_dip2Norm.vcf.b.gz
sleep 30s
tabix -p vcf ${vcfNew}_dip2Norm.vcf.b.gz
sleep 30s
bgzip -cd ${vcfNew}_dip2Norm.vcf.b.gz > ${vcfNew}_dip2Norm.vcf.b.gz.vcf

#bcftools view -v "snps" -I ${vcf}_dip2Norm.vcf.b.gz | bgzip -c > ${vcf}_dip2Norm_snps.vcf.b.gz

# vcf=/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-03-11_worldMadNewIberia.vcf.b.gz
# vcfSnp=/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-03-11_worldMadNewIberia_snps.vcf.b.gz

# tabix -p vcf ${vcfSnp}
md5sum ${vcfNew}_dip2Norm.vcf.b.gz > ${vcfNew}_dip2Norm.vcf.b.gz.md5
# md5sum ${vcf} > ${vcf}.md5


echo "go Eva!"
# sleep 30s
# bgzip -cd ${vcfNew} > ${vcfNew}.vcf
sleep 30s
${HOME}/software/vcf-validator-develop/./vcf_validator -i ${vcfNew}_dip2Norm.vcf.b.gz.vcf

echo "Eva done!"


#bgzip -c $africaVcf > $africaVcf".gz"

#vcf=/srv/biodata/dep_coupland/grp_hancock/andrea/superVcf_moroccanPaper.vcf_dip.vcf_final.vcf_second.gz

#md5sum $africaVcf".gz" > $africaVcf".gz.md5"

#scp $africaVcf".gz" /srv/biodata/dep_coupland/grp_hancock/andrea/
