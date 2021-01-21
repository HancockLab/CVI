#!/bin/bash

# Now the proper pipeline :)

# echo $rawFile $file $workDir $whereWe

rawFile=$1
file=$2
workDir=$3
whereWe=$4
BWA=${HOME}/software/bwa-0.7.5a
mkdir -p ${workDir}

#	The usual TAIR10:
REF=${HOME}/ATwithSlociCollection2/atAndSlociColl02.fa

#	The new Cvi-0 assembled by Mehmet:
#REF=/srv/biodata/dep_coupland/grp_hancock/andrea/references/cvi0_2/CVI-0_V1.fasta

# 	The S1-1 assembly:
# REF=/srv/biodata/dep_coupland/grp_hancock/andrea/references/s1-1_19-03-06/S1-1.fasta


#	If it is a .bam file
#
# java -Xmx4g -jar $HOME/software/picard-tools-1.100/SamToFastq.jar I=$rawFile F=$file.1.fastq F2=$file.2.fastq VALIDATION_STRINGENCY=LENIENT	

#	If it is a fastq from refcut or a single end fastq
#
#bgzip -cd $rawFile > $file.1.fastq

#	If it is a fastq from Cologne
#
gzip -cd ${rawFile}_R1_001.fastq.gz > ${file}.1.fastq
gzip -cd ${rawFile}_R2_001.fastq.gz > ${file}.2.fastq

#	If it is one of the chinese or new relicts
# 
#gzip -cd ${rawFile}_1.fastq.gz > ${file}.1.fastq
#gzip -cd ${rawFile}_2.fastq.gz > ${file}.2.fastq

#cp $rawFile"_1.fastq" $file.1.fastq
#cp $rawFile"_2.fastq" $file.2.fastq

#cp $rawFile".fastq" $file.1.fastq

#	If it is a fastq from the 1001 in Cologne
#bzip2 -ckd $fileName > $whereYouWantIt
# gzip -cd ${1}.1.fastq.gz > ${file}.1.fastq
# gzip -cd ${1}.2.fastq.gz > ${file}.2.fastq

#	If it is a single ended bam
# 
#java -Xmx4g -jar /home/CIBIV/andreaf/canaries/picard-tools-1.100/SamToFastq.jar I="$rawFile" F=$file.1.fastq VALIDATION_STRINGENCY=LENIENT 
#scp $rawFile $file.1.fastq

# Remove adapters
#
# Double ended
sleep 30s
$HOME/software/adapterremoval-2.1.2/build/./AdapterRemoval --file1 ${file}.1.fastq --file2 ${file}.2.fastq --basename ${file} --trimns --trimqualities --adapter-list ${HOME}/data/adapters.txt
#  --qualitymax 42 for illumina X something

# Single ended
#$HOME/software/adapterremoval-2.1.2/build/./AdapterRemoval --file1 $file.1.fastq --basename $file --trimns --trimqualities --qualitymax 42 --adapter-list $HOME/data/adapters.txt

sleep 30s
rm ${file}.1.fastq
rm ${file}.2.fastq

# Single ended!
#$BWA/bwa aln $REF $file".truncated" > "$file"_1.sai # &
#$BWA/bwa samse $REF "$file"_1.sai $file".truncated" > "$file".sam

# Double ended!
sleep 30s
${BWA}/bwa aln ${REF} ${file}.pair1.truncated > ${file}_1.sai		# &
${BWA}/bwa aln ${REF} ${file}.pair2.truncated > ${file}_2.sai		# &		# wait
sleep 30s
${BWA}/bwa sampe ${REF} ${file}_1.sai ${file}_2.sai ${file}.pair1.truncated ${file}.pair2.truncated > ${file}.sam
sleep 30s

rm $file*.truncated
rm $file.discarded
rm $file*.sai

sleep 30s
java -Xmx4g -jar $HOME/software/picard-tools-1.100/SortSam.jar I=$file.sam O=$file.sam.sortP.sam VALIDATION_STRINGENCY=SILENT SO=coordinate
sleep 30s
#rm $file.sam
cp $file.sam ${whereWe}/

echo "coped sam..."


sleep 30s
java -Xmx4g -jar $HOME/software/picard-tools-1.100/MarkDuplicates.jar I=$file.sam.sortP.sam O=$file.sam.sortP.sam.duprem.sam M=pe.dupstat.txt VALIDATION_STRINGENCY=SILENT REMOVE_DUPLICATES=true
sleep 30s
rm $file.sam.sortP.sam

sleep 30s
samtools view -Sbh $file.sam.sortP.sam.duprem.sam > $file.sam.sortP.sam.duprem.sam.bam
sleep 30s
rm $file.sam.sortP.sam.duprem.sam

# Double ended!
sleep 30s
samtools view -h -q 25 -f 0x0002 -F 0x0004 -F 0x0008 -b $file.sam.sortP.sam.duprem.sam.bam > $file.sam.sortP.sam.duprem.sam.bam.mq25.bam
sleep 30s
#rm $file.sam.sortP.sam.duprem.sam.bam

# Single ended
#samtools view -h -q 25 -F 0x0004 -b "$file".sam.sortP.sam.duprem.sam.bam > "$file".sam.sortP.sam.duprem.sam.bam.mq25.bam
#rm $file.sam.sortP.sam.duprem.sam.bam

# Keep bam for igv
samtools index ${file}.sam.sortP.sam.duprem.sam.bam ${file}.sam.sortP.sam.duprem.sam.bam.bai

sleep 30s
samtools view -h $file.sam.sortP.sam.duprem.sam.bam.mq25.bam > $file.sam.sortP.sam.duprem.sam.bam.mq25.bam.sam
sleep 30s
rm $file.sam.sortP.sam.duprem.sam.bam.mq25.bam

sleep 30s
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.at.Uniqueness $file.sam.sortP.sam.duprem.sam.bam.mq25.bam.sam
sleep 30s
rm $file.sam.sortP.sam.duprem.sam.bam.mq25.bam.sam

sleep 30s
samtools view -Sbh $file.sam.sortP.sam.duprem.sam.bam.mq25.bam.sam.uniqueMine.sam > $file.sam.sortP.sam.duprem.sam.bam.mq25.bam.sam.uniqueMine.sam.bam
sleep 30s
rm $file.sam.sortP.sam.duprem.sam.bam.mq25.bam.sam.uniqueMine.sam

#Base quality is filtered in the java script after
sleep 30s
samtools mpileup -B -q 25 -f $REF $file.sam.sortP.sam.duprem.sam.bam.mq25.bam.sam.uniqueMine.sam.bam > $file.mpileup
sleep 30s
rm $file.sam.sortP.sam.duprem.sam.bam.mq25.bam.sam.uniqueMine.sam.bam

sleep 30s
###
#		The real SNP calling
###
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.noFiltering.mpileupBuild_janeiroAllFiltStrandOut $file.mpileup $workDir/



###
#		Call S-loci
###
sleep 30s
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SlocusCoverage $file.mpileup


###
#	Make order
###

# rm $file.mpileup.all
sleep 30s
# rm $file.mpileup

rm ${file}.mpileup.all_intermediate2
rm ${file}.mpileup.snp
rm ${file}*settings
mv ${file}* ${whereWe}/
rm -r $workDir

# Now annotate
#java -Xmx4g -jar /home/CIBIV/andreaf/snpEff/snpEff/snpEff.jar -c /home/CIBIV/andreaf/snpEff/snpEff/snpEff.config TAIR10.26 "$file".mpileup.all.snp.vcf > "$file".mpileup.all.snp.vcf.eff.vcf
#for fileToGz in "$file"*; do bgzip -c $fileToGz > "$fileToGz".b.gz; done
#for fileToGz in "$file"*; do bgzip -c $fileToGz > "$fileToGz".b.gz; done

###
#	Finito!
###

