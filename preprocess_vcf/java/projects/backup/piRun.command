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


#bcftools view -S $HOME/capeVerdeos.txt --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I $vcf | bgzip -c > $vcfCvi
#tabix -p vcf $vcfCvi

#bcftools view -S ^$HOME/data/dwarves.txt --force-samples -r "Chr1","Chr2","Chr3","Chr4","Chr5" -I $vcfCvi4outNew | bgzip -c > $vcfCvi4outNewNoDw
#tabix -p vcf $vcfCvi4outNewNoDw

#bcftools merge $HOME/vcf/*all.srt.vcf.b.gz $vcfCvi3out | bgzip -c > $vcfCvi3out_final
#tabix -p vcf $vcfCvi3out_final

#cd $HOME/analysis/pca
#$HOME/software/plink64/./plink --vcf $vcfCviFinal --pca --indep-pairwise 50 10 0.1 --geno 0 --out $HOME/analysis/pca/cviFinal

#bgzip -cd $vcfCvi3out > $vcfCvi3out.vcf



vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_20-3-18.vcf.b.gz
vcfNewPart=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_28-3-18.vcf.b.gz


newData=/netscratch/dep_coupland/grp_hancock/andrea/vcfShore
vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_19-4-18.vcf.b.gz
vcfNew=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_29-4-18.vcf.b.gz
final=/srv/biodata/dep_coupland/grp_hancock/VCF

#bcftools view -S ^${HOME}/data/50out.txt --force-samples -I $vcf | bgzip -c > ${vcfNewPart}_part.vcf.b.gz
#bcftools view -s ^"AHLB" --force-samples -I $vcf | bgzip -c > ${vcfNew}_part.vcf.b.gz
#sleep 1m
#tabix -p vcf ${vcfNew}_part.vcf.b.gz
#sleep 1m

#bcftools merge ${vcf} ${newData}/*all.srt.vcf.b.gz | bgzip -c > ${vcfNew}
#sleep 1m
#tabix -p vcf ${vcfNew}
#sleep 1m

#cp ${vcfNew}* ${final}/
#
#bgzip -cd $vcfNew > ${vcfNew}.vcf
#cp ${vcfNew}.vcf ${final}/
#rm ${vcfNew}.vcf

###
#		VCF combined to SNP matrix
###

#vcf=$RUN/superVcf_15-11-17_cvi4outNew_noDwarfi.vcf
vcf=/srv/biodata/dep_coupland/grp_hancock/VCF/superVcf_29-4-18.vcf.b.gz.vcf
#bgzip -cd $vcf.b.gz > $vcf
#matrix=$RUN/vcf/superMatrix_cvi11-17.txt
matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_29-04-18.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.VcfCombined_to_snpMatrix $vcf $1 $matrix

###
#	 Subset snp Matrix
###

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_subsetSnpMatrix $matrix


###
#		 SNPs only
###

#matrix=$RUN/vcf/superMatrix_3-18.txt
#echo "snp only"
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_snpsOnly $matrix
echo "done"

###
#		Get functional masks
###

vcfAnn=/netscratch/dep_coupland/grp_hancock/andrea/superVcf_15-11-17_afrHerb.vcf.b.gz_ann.vcf
#bgzip -cd /srv/biodata/dep_coupland/grp_hancock/andrea/superVcf_15-11-17_afrHerb.vcf.b.gz_ann.vcf.b.gz > $vcfAnn
results=/netscratch/dep_coupland/grp_hancock/andrea/cviMask

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
vcf=$vcfNew
vcfAnn=${vcfNew}_ann.vcf.b.gz
#java -Xmx4g -jar /home/fulgione/software/snpEff/snpEff/snpEff.jar -c /home/fulgione/software/snpEff/snpEff/snpEff.config -v TAIR10.26 ${vcf} | bgzip -c > ${vcfAnn}
#cp ${vcfAnn} /srv/biodata/dep_coupland/grp_hancock/VCF/


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

matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_29-04-18.txt_plusAncestors.txt
#/srv/biodata/dep_coupland/grp_hancock/andrea/vcf/superMatrix_3-18.txt
#bgzip -cd $matrix.b.gz > $matrix
results=/netscratch/dep_coupland/grp_hancock/andrea/pi_4-2018/
mkdir -p $results

#echo "Go 1787 Pairwise!"
#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Pairwise_shore $matrix $1 $results
#echo "Done :)"

echo "Go Pairwise cvi!"
results=/netscratch/dep_coupland/grp_hancock/andrea/piS10-S11_win
	# piCvi_8-10-2018_win
mkdir -p ${results}${1}

matrix=/srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18_final.txt_plusAncXM.txt
	# /srv/biodata/dep_coupland/grp_hancock/andrea/superMatrix_2-7-18.txt_plusAncXM.txt
#santos=/srv/biodata/dep_coupland/grp_hancock/VCF/santos_clean_11-7-18.txt
#fogos=/srv/biodata/dep_coupland/grp_hancock/VCF/fogos_clean_11-7-18.txt

s=/home/fulgione/java/projects/S10.txt.id.txt
f=/home/fulgione/java/projects/S11.txt.id.txt

java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Pairwise_shore_cvi ${matrix} ${results} ${1} ${s} ${f}

cp ${results}${1} /srv/biodata/dep_coupland/andrea/

###
#		Single samples pairwise
###
oldNames=/srv/biodata/dep_coupland/grp_hancock/andrea/pi_4-2018/names.txt
newNames=/home/fulgione/java/projects/newSamples_7-18.txt
oldPiMatrix=/srv/biodata/dep_coupland/grp_hancock/andrea/pi_4-2018/matrix.txt
matrix=/netscratch/dep_coupland/grp_hancock/andrea/superMatrix_12-7-18.txt
results=/netscratch/dep_coupland/grp_hancock/andrea/pi_7-2018/
mkdir -p $results

anc=${HOME}/data/cvi_ancestralStates.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.Matrix_addAncestor ${matrix} ${anc}

matrix2=${matrix}_plusAncestors.txt

#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SingleSample_pairwise ${oldNames} ${newNames} ${oldPiMatrix} ${matrix} ${results}


#mv $results /srv/biodata/dep_coupland/grp_hancock/andrea/
echo "Done! :)"


