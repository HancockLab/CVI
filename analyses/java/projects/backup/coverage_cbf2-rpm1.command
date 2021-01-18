#!/bin/bash


###
#		VCF combined to SNP matrix
###
vcf=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07_cviMorLyrLer.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-05-15.vcf.b.gz_lyrata.vcf.b.gz_chrOnly.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07_cviMorLyrLer.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-08_cviMorLyr.vcf.b.gz_chrOnly.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_prova.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-08_cviMorLyr.vcf.b.gz_chrOnly.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_pogoniulus_2020-01-11.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-05-15.vcf.b.gz_lyrata.vcf.b.gz_chrOnly.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-28_africa360.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-08_cviMorLyr.vcf.b.gz_chrOnly.vcf.b.gz.vcf 
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-08_cviMorLyr.vcf.b.gz_snps.vcf.b.gz_chrOnly.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-05-15.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia_outOld_subBamNewIberia.vcf.b.gz.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/projects/slimSim/abc/s11/s11s7_vcf518.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz_cvi_plusPartS21-8.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_checkS21-8.vcf
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz.vcf
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_s1-1_19-04-10.vcf
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia_outOld.vcf
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-03-11_worldMadNewIberia.vcf
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_cvi0_19-01-07.vcf
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20.vcf
# bgzip -cd ${vcf}.b.gz > ${vcf}
matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-07_cviMorLyrLer_noHigCov_2020-05-20.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_prova.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-08_wg_cviMorLyr_noHigCov.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_pogoniulus_2020-01-11.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-05-15_withLyrata.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-29_africa360_all.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-07.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-08_wg_cviMorLyr.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_2019-11-08_cviMorLyr.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-10-24_cvi0lerCvis.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_subBamNewIberia.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_vcfProvaSlimFst.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_18-12-20_checkS21-8_plusParts.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_18-12-20_checkS21-8.txt
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_19-04-23.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_s1-1_19-04-12.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_18-12-20_worldMadNewIberia.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/vcf/superMatrix_cvi0_19-01-07.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_18-12-27.txt


# tabix -p vcf ${vcf}
# sleep 30s
samplesToKeep=/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_19-10-24_Cvi0Ler.txt

bcftools view -S ${samplesToKeep} --force-samples -r Chr4:13010000-13029000 -I ${vcf} > ${vcf}_cbf2.vcf
sleep 30s

bcftools view -S ${samplesToKeep} --force-samples -r Chr3:2225000-2229900 -I ${vcf} > ${vcf}_rpm1.vcf
sleep 30s



# tabix -p vcf ${vcf}_cviLer.vcf.b.gz
# sleep 30s 
# bgzip -cd ${vcf}_cviLer.vcf.b.gz > ${vcf}_cviLer.vcf.b.gz.vcf
# echo "num snps"
# cat ${vcf}_snps.vcf.b.gz | wc -l

# sleep 30s
# 
# The normal matriximg
# 
# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.VcfCombined_to_snpMatrix ${vcf} ${1} ${matrix}

###
#	Matrixing but aliminatinging positions with >2*average coverage
###
# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.VcfCombined_to_snpMatrix_outHighCov ${vcf} ${1} ${matrix}

# 
# Matrixing the whole genome, not per chromosome
# 
# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.VcfCombined_to_snpMatrix_alpina_noChr ${vcf} ${matrix}

#cat /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr1.txt /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr2.txt /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr3.txt /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr4.txt /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr5.txt > /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt

###
#	 Subset snp Matrix
###

# matrix=/srv/netscratch/dep_coupland/grp_hancock/andrea/superMatrix_18-12-27.txt_plusAncXM.txt
	# ${matrix2}_snpsOnly.txt_plusAncXM.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt_snpsOnly.txt
# samples=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt
	# samples=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
	# samples=/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_11-7-18.txt

# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_snpsOnly $matrix

tag=santo_clean_11-7-18

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_subsetSnpMatrix ${matrix} ${samples} ${tag}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_subsetNoNSnpMatrix ${matrix} ${samples} ${tag}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_subsetSegregaNoNSnpMatrix ${matrix} ${samples} ${tag}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.TransposIt ${matrix}_santo_clean_11-7-18_genoWihs_polyNoN.txt

