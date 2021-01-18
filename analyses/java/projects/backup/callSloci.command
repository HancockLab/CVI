#!/bin/bash

rawFile=$1
file=$2
workDir=$3
whereWe=$4
BWA=${HOME}/software/bwa-0.7.5a
mkdir -p ${workDir}

#	The usual TAIR10:
REF=${HOME}/ATwithSlociCollection2/atAndSlociColl02.fa


echo $1
# If african 300 samples
# gzip -cd ${1}_R1.fastq.gz > ${SCRATCH_WORK}/2.fastq
# gzip -cd ${1}_R2.fastq.gz > ${SCRATCH_WORK}/1.fastq

# If bam
java -Xmx4g -jar $HOME/software/picard-tools-1.100/SamToFastq.jar I=$rawFile F=$file.1.fastq F2=$file.2.fastq VALIDATION_STRINGENCY=LENIENT


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


# From old callSloci script


# samtools view -h ${1} > ${1}.sam
# sleep 30s
# samtools view -Sbh $file.sam.sortP.sam.duprem.sam > $file.sam.sortP.sam.duprem.sam.bam
# sleep 30s
# samtools mpileup -B -q 25 -f ${REF} ${1} > ${1}.mpileup
# sleep 30s
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SlocusCoverage $file.mpileup


#java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.StructuralVarian    tsCaller $1".sam"
