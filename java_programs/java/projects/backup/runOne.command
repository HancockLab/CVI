#!/bin/bash

echo $1

RUN=/srv/biodata/dep_coupland/grp_hancock/andrea


vcf=$RUN/superVcf_15-11-17_cviAnd20.vcf.b.gz
vcfCvi3out=$RUN/superVcf_15-11-17_cvi3out.vcf.b.gz
vcfCvi4out=$RUN/superVcf_15-11-17_cvi4out.vcf.b.gz
vcfCvi4outNew=$RUN/superVcf_15-11-17_cvi4outNew.vcf.b.gz
vcfCvi4outNewNoDw=$RUN/vcf/superVcf_15-11-17_cvi4outNew_noDwarfi.vcf.b.gz
vcfCvi=$RUN/superVcf_15-11-17_cvi.vcf.b.gz

vcfCviFinal=$RUN/superVcf_15-11-17_cviFinal.vcf.b.gz

vcfB=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_11-7-18.vcf.b.gz
vcf=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_11-7-18.vcf.b.gz

#cp ${vcfB} ${vcf}
#sleep 30s
#tabix -p vcf ${vcf}

vcf=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-05-15.vcf.b.gz_lyrata.vcf.b.gz_chrOnly.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07.vcf.b.gz
	# /srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_19-05-15.vcf.b.gz_snps.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20.vcf.b.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/superVcf_11-7-18.vcf.b.gz
vcfNew=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-07_cviMorLyrLer.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_2019-11-28_africa360.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_cviClean_11-7-18_plusCvi0.vcf.b.gz
	# /netscratch/dep_coupland/grp_hancock/andrea/superVcf_11-7-18.vcf.b.gz_cleanSantoWg.vcf.b.gz

##
#	~/data/cviOnePerStand.txt
#	${HOME}/data/cviOnePerStand_andRandom20.txt
##
samples=/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_20-05-14_twocCvi0LerMorLyr.txt
	# /home/fulgione/African360_plink.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18_plusCvi0.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt


	
	
	
	
	
###
# vcf=/srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz_ann.vcf.b.gz
	# /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_19-04-18_cvi0cleanSetPlusLastLanes.vcf.b.gz
# bgzip -cd ${vcf}.b.gz > ${vcf}

tabix -p vcf ${vcf}
sleep 30s
bcftools view -S ${samples} --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcf} | bgzip -c > ${vcfNew}
sleep 30s
tabix -p vcf ${vcfNew}
sleep 30s
# bcftools view -v "snps" --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcfNew} | bgzip -c > ${vcfNew}_snps.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcfNew}_snps.vcf.b.gz
# sleep 30s 
bgzip -cd ${vcfNew} > ${vcfNew}.vcf
# echo "num snps"
# cat ${vcf}_snps.vcf.b.gz | wc -l


	
	
	
	
	
	
	
	
	
	
	
	
	
	# bcftools view -S ${samples} --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcf} | bgzip -c > ${vcfNew}
#bcftools view -S ${samples} -v "snps" --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcfCvi} | bgzip -c > ${vcfSanto}
# bcftools view -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcf} | bgzip -c > ${vcf}_chrOnly.vcf.b.gz
# sleep 30s
# tabix -p vcf ${vcf}_chrOnly.vcf.b.gz
# bgzip -cd ${vcfNew} > ${vcfNew}.vcf
 

#bcftools view -S ^$HOME/data/dwarves.txt --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I $vcfCvi4outNew | bgzip -c > $vcfCvi4outNewNoDw
#tabix -p vcf $vcfCvi4outNewNoDw

#bcftools merge $HOME/vcf/*all.srt.vcf.b.gz $vcfCvi3out | bgzip -c > $vcfCvi3out_final
#tabix -p vcf $vcfCvi3out_final

#cd $HOME/analysis/pca
#sleep 30s

##
#	$HOME/analysis/pca/cviAnd20_31-7-18
#	$HOME/analysis/pca/cvi_31-7-18
##
#${HOME}/software/plink64/./plink --vcf ${vcfCvi} --pca --indep-pairwise 50 10 0.1 --geno 0 --out $HOME/analysis/pca/cviAnd20_31-7-18

#bgzip -cd $vcfCvi3out > $vcfCvi3out.vcf



###
#		For Carlos
###

morVcf=/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superVcf_moroccanPaper.vcf.b.gz
madVcf=/srv/biodata/dep_coupland/grp_hancock/andrea/data/are/madeira.vcf
#bgzip -c ${madVcf} > ${madVcf}.b.gz
sleep 30s

newData=/netscratch/dep_coupland/grp_hancock/andrea/vcfShore_afr_18-12-07
vcfNew=/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia.vcf.b.gz
madNet=/netscratch/dep_coupland/grp_hancock/andrea/vcf/madeira.vcf.b.gz
morNet=/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_moroccanPaper.vcf.b.gz

#cp ${madVcf}.b.gz ${madNet}
#cp ${morVcf} ${morNet}
#sleep 30s
#tabix -p vcf ${madNet}
#tabix -p vcf ${morNet}
#sleep 30s
#bcftools view -S ^${HOME}/relictsOut.txt --force-samples -I ${morNet} | bgzip -c > ${morNet}_part.vcf.b.gz
#sleep 30s
#tabix -p vcf ${morNet}_part.vcf.b.gz
#sleep 30s
#bcftools merge ${morNet}_part.vcf.b.gz ${madNet} ${newData}/*all.srt.vcf.b.gz | bgzip -c > ${vcfNew}
sleep 30s
#tabix -p vcf ${vcfNew}

#cp ${vcfNew} /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia.vcf.b.gz
#cp ${vcfNew}.tbi /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia.vcf.b.gz.tbi

# Separate: snp only vcf
vcf=/netscratch/dep_coupland/grp_hancock/andrea/vcf/superVcf_18-12-20_worldMadNewIberia.vcf_dip.vcf.b.gz
#bcftools view -v "snps" -I ${vcf} | bgzip -c > ${vcf}.snp.vcf.b.gz
#sleep 30s
#tabix -p vcf ${vcf}.snp.vcf.b.gz
#sleep 30s 
#md5sum ${vcf}.snp.vcf.b.gz > ${vcf}.snp.vcf.b.gz.md5

sleep 30s
#cp ${vcf}* /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/


#${HOME}/software/plink64/./plink --vcf ${vcfCvi} --pca --indep-pairwise 50 10 0.1 --geno 0 --out $HOME/analysis/pca/cviAnd20_31-7-18





vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_20-3-18.vcf.b.gz
vcfNewPart=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_28-3-18.vcf.b.gz


newData=/srv/biodata/dep_coupland/grp_hancock/andrea/shore
#/netscratch/dep_coupland/grp_hancock/andrea/vcfShore
vcfCvi=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_12-7-18_santo.vcf.gz
vcf=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_22-6-18.vcf.b.gz
	#/netscratch/dep_coupland/grp_hancock/andrea/superVcf_1
final=/srv/biodata/dep_coupland/grp_hancock/VCF

#bcftools view -S ^${HOME}/data/toEliminate_6-18.txt --force-samples -I ${vcf} | bgzip -c > ${vcfNew}_part.vcf.b.gz
#bcftools view -s ^"AHLB" --force-samples -I $vcf | bgzip -c > ${vcfNew}_part.vcf.b.gz

#fileToKeep=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_onePerStand.txt
#bcftools view -S ${fileToKeep} -v "snps" --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I ${vcf} | bgzip -c > ${vcfCvi}
#sleep 1m
#tabix -p vcf ${vcfCvi}
#sleep 1m

#cp ${vcfNew}* ${final}/
#

vcfNew=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_etiop5-7-18.vcf.b.gz
#bcftools view -S ^${HOME}/eliminate.txt -I ${vcf} | bgzip -c > ${vcf}_part.vcf.b.gz
#sleep 1m
#tabix -p vcf ${vcf}_part.vcf.b.gz

#bcftools merge ${newData}/*all.srt.vcf.b.gz | bgzip -c > ${vcfNew}
#bcftools merge ${vcf}_part.vcf.b.gz ${newData}/S*all.srt.vcf.b.gz | bgzip -c > ${vcfNew}
#sleep 1m
#tabix -p vcf ${vcfNew}
#sleep 1m

#cp ${vcfNew}* ${final}/

#bgzip -cd ${vcfNew} > ${vcfNew}.vcf
#cp ${vcfNew}.vcf ${final}/
#rm ${vcfNew}.vcf

###
#		VCF combined to SNP matrix
###
vcf=/netscratch/dep_coupland/grp_hancock/andrea/vcf300shore_8-10-18.vcf.b.gz.vcf
	#vcf=$RUN/superVcf_15-11-17_cvi4outNew_noDwarfi.vcf
	#vcf=${vcfNew}.vcf
	#/netscratch/dep_coupland/grp_hancock/andrea/superVcf_12-7-18.vcf.b.gz.vcf
	#/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_29-4-18.vcf.b.gz.vcf
	#bgzip -cd $vcf.b.gz > $vcf
	#matrix=$RUN/vcf/superMatrix_cvi11-17.txt
matrix=/netscratch/dep_coupland/grp_hancock/andrea/vcf300shoreMatrix_8-10-18.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_etiop5-7-18.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.VcfCombined_to_snpMatrix ${vcf} ${1} ${matrix}

#cat /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr1.txt /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr2.txt /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr3.txt /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr4.txt /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_c5q25_chr5.txt > /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt


matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt
anc=${HOME}/data/cvi_ancestralStates_12-7-18.txt

matrix2=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18_final.txt
#cp ${matrix} ${matrix2}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_snpsOnly ${matrix2}


# scan=xpClr_5percent_tail
scan=xpEhh_5percent_tail
# scan=wihs_5percent_tail
scan=clr_5percent_tail
scan=${2}

# java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Qtl_overlapSelectionScans ${1} ${scan}

#sleep 30s

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_addAncestor ${matrix2}_snpsOnly.txt ${anc}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_addAncestor ${matrix2} ${anc}

echo "Added!"

###
#	 Subset snp Matrix
###

matrix=${matrix2}_snpsOnly.txt_plusAncXM.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt_snpsOnly.txt
samples=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt
	# samples=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
	# samples=/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_11-7-18.txt

tag=santo_clean_11-7-18

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_subsetSnpMatrix ${matrix} ${samples} ${tag}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_subsetNoNSnpMatrix ${matrix} ${samples} ${tag}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_subsetSegregaNoNSnpMatrix ${matrix} ${samples} ${tag}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.TransposIt ${matrix}_santo_clean_11-7-18_genoWihs_polyNoN.txt



###
#		For XP-EHH & XP-CLR
###
santo=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
fogo=/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_11-7-18.txt
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.XpEhh_input ${matrix} ${1} ${santo} ${fogo}

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.TransposIt ${matrix}_xpEhh_chr${1}_hapSanto.txt
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.TransposIt ${matrix}_xpEhh_chr${1}_hapFogo.txt
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Build_recom_rate_infile_chromoPainter ${matrix}_xpEhh_chr${1}_pos.txt

#matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18_final.txt_snpsOnly.txt_plusAncXM.txt_santo_clean_11-7-18_genoWihs.txt
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_noNsnpsOnly ${matrix}

echo "Done step one"
#sleep 30s
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_polySnpsOnly_noSinglets ${matrix}_${tag}.txt





echo "Go sfs!"


#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_addAncestor ${matrix1} ${anc}

#sleep 30s

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_snpsOnly ${matrix1}_plusAncXM.txt








###
#		 SNPs only
###

matrix=/netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_12-7-18.txt
#echo "snp only"
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_snpsOnly $matrix
echo "done"

#scp /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt* /srv/biodata/dep_coupland/grp_hancock/andrea/


###
#		Only snps segregating in cvi
###
#matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18_final.txt_plusAncXM.txt
cvi=/srv/biodata/dep_coupland/grp_hancock/VCF/capeVerdeos_clean_11-7-18.txt
matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18_final.txt_snpsOnly.txt_plusAncXM.txt
santos=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
fogos=/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_11-7-18.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Spatial_getSnps ${matrix} ${santos} ${fogos}



###
#		Get functional masks
###

vcfAnn=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_29-4-18.vcf.b.gz.vcf
#/netscratch/dep_coupland/grp_hancock/andrea/superVcf_15-11-17_afrHerb.vcf.b.gz_ann.vcf
#bgzip -cd /srv/biodata/dep_coupland/grp_hancock/andrea/superVcf_15-11-17_afrHerb.vcf.b.gz_ann.vcf.b.gz > $vcfAnn
results=/netscratch/dep_coupland/grp_hancock/andrea/cviMask_2
mkdir -p $results

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SnpEff_masksFromSuperVcf $vcfAnn $results
#mv $results* /srv/biodata/dep_coupland/grp_hancock/andrea/

###
#		Get JSFS
###

matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_11-17.txt
#bgzip -cd $matrix.b.gz > $matrix
ancFile=/srv/biodata/dep_coupland/grp_hancock/VCF/estSFS_santoAnc_20_ancestralState.txt_mlK.txt
#ancFile=/srv/biodata/dep_coupland/grp_hancock/VCF/estSFS_fogoAnc_10_ancestralState.txt_mlK.txt

#mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/cviMaskintergenicMask.txt
#mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/cviMasksynonimousMask.txt
#mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/cviMasknonSynonimousMask.txt
mask=/srv/biodata/dep_coupland/grp_hancock/andrea/functionalMasks_cvi/TAIR10_GFF3_genes_transposons.gff_interG_superMask_fromGff.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS $matrix $RUN/sfs_haSma
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_getJSFS_morPol $matrix $ancFile $mask 20


###
#		Get input for Mach-admix
###
matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_11-17.txt_snpsOnly.txt
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Mach_input $matrix $1

###
#		Run Mach on the moroccans
###
#/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_11-17.txt_snpsOnly.txt_chr1_mor0_toImpute.txt
c=$1
work=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_11-17.txt_snpsOnly.txt
ped=${work}_chr${c}_mor${2}_toImpute.txt
dat=${work}_chr${c}_santo_datForMach.txt
out=/netscratch/dep_coupland/grp_hancock/andrea/mor${2}_chr${c}_imputed

#$HOME/software/mach/executables/./mach1 -d ${dat} -p ${ped} --rounds 50 --greedy --geno --prefix ${out}




###
#		Run mach-admix
###
c=$1
work=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_11-17.txt_snpsOnly.txt
dir=/srv/biodata/dep_coupland/grp_hancock/andrea/imputazia
#for num in {1..10}
#do
#	ped=${work}_chr${c}_santo_pedForMach_${num}.txt		#_pedForMach.txt
#	dat=${work}_chr${c}_santo_datForMach.txt		#_datForMach.txt
#	hap=${dir}/mor_chr${c}_imputedMorHaps.txt		#_hapsForMach.txt
#	snp=${work}_chr${c}_santo_snpsForMach.txt		#_snpsForMach.txt
#	out=/netscratch/dep_coupland/grp_hancock/andrea/santo_chr${c}_imputedFinal_${num}
	
#	sleep 1m
#	~/software/mach-admix.2.0.203/mach-admix --geno --probs --qc --runMode Integrated -d $dat -p $ped -h $hap -s $snp --prefix $out
#	sleep 1m
#	cp ${out}* /srv/biodata/dep_coupland/grp_hancock/andrea/
#done


###
#		Run mach-admix OLD
###
vc=$1
work=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_11-17.txt_snpsOnly.txt
ped=${work}_chr${c}_santo_pedForMach.txt		#_pedForMach.txt
dat=${work}_chr${c}_santo_datForMach.txt		#_datForMach.txt
hap=${work}_chr${c}_santo_hapsForMach.txt		#_hapsForMach.txt
snp=${work}_chr${c}_santo_snpsForMach.txt		#_snpsForMach.txt
out=/netscratch/dep_coupland/grp_hancock/andrea/santo_chr${c}_impPol

#sleep 1m
#~/software/mach-admix.2.0.203/mach-admix --geno --probs --qc --runMode Integrated -d $dat -p $ped -h $hap -s $snp --prefix $out
#sleep 1m
#cp ${out}* /srv/biodata/dep_coupland/grp_hancock/andrea/

###
#		Get input for EstSFS
###

matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_11-17.txt
matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_11-17.txt_snpsOnly.txt
results=/srv/biodata/dep_coupland/grp_hancock/andrea/estSFS_santoAnc
results=/srv/biodata/dep_coupland/grp_hancock/andrea/estSFS_fogoAnc
#bgzip -cd $matrix.b.gz > $matrix
maxNs=12
maxNs=10
#maxNs=$1

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.EstSfs_inputFromMatrix $matrix $results $maxNs

###
#		Compress
###
#for file in /srv/biodata/dep_coupland/grp_hancock/andrea/data/cvi/*snp.vcf
#do 
#	bgzip -c $file > $file".b.gz"
#done


###
#		snpEff
###
vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/GATK_SNPs_Indels_For_Cape_Verde/CapeVerde_SNPs_Indels_Final_segregating.vcf.gz
vcfAnn=${vcf}_ann.vcf.b.gz
# java -Xmx4g -jar /home/fulgione/software/snpEff/snpEff/snpEff.jar -c /home/fulgione/software/snpEff/snpEff/snpEff.config -v TAIR10.26 ${vcf} | bgzip -c > ${vcfAnn}
#cp ${vcfAnn} /srv/biodata/dep_coupland/grp_hancock/VCF/
# bgzip -cd ${vcfAnn} > /srv/netscratch/dep_coupland/grp_hancock/andrea/vcf/CapeVerde_SNPs_Indels_Final_segregating.vcf.gz.vcf


###
#		Average coverage
##

vcf=$RUN/superVcf_15-11-17_cvi4outNew.vcf
vcf=${1}
#bgzip -cd ${vcf} > ${vcf}.vcf
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.CoverageFromVcf ${vcf}.vcf 0


###
#		Call pairwise differences on 1723 samples
###

matrix=/netscratch/dep_coupland/grp_hancock/andrea/vcf300shoreMatrix_8-10-18.txt
	# /netscratch/dep_coupland/grp_hancock/andrea/superMatrix_etiop5-7-18.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_29-04-18.txt_plusAncestors.txt
#/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_3-18.txt
#bgzip -cd $matrix.b.gz > $matrix
results=/netscratch/dep_coupland/grp_hancock/andrea/pi_300shore_10-18/
	# /netscratch/dep_coupland/grp_hancock/andrea/pi_etiop7-2018/
	# /netscratch/dep_coupland/grp_hancock/andrea/pi_4-2018/
mkdir -p $results

#echo "Go 1787 Pairwise!"
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Pairwise_shore $matrix $1 $results
#echo "Done :)"



#bgzip -cd $vcfJuly.b.gz > $vcfJuly
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.CoverageFromVcf $vcfJuly

