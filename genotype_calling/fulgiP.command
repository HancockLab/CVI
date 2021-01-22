#!/bin/bash

# run as ./fulgiP.command $rawFile $file $workDir $whereWe

cd ./CVI/java_programs/java/projects/

rawFile=$1
file=$2
workDir=$3
whereWe=$4
BWA= # Path to bwa-0.7.5a
mkdir -p ${workDir}

#	The usual TAIR10:
REF= # Path to reference genome
# ${HOME}/ATwithSlociCollection2/atAndSlociColl02.fa

#
gzip -cd "Decompress fastq file 1 into:" > ${file}.1.fastq
gzip -cd "Decompress fastq file 1 into:" > ${file}.2.fastq

# Remove adapters
#
adaptersFile=./data/adapters.txt # Path to adapters file
sleep 30s
"Path to adapterremoval-2.1.2"/build/./AdapterRemoval --file1 ${file}.1.fastq --file2 ${file}.2.fastq --basename ${file} --trimns --trimqualities --adapter-list ${adaptersFile}
#  --qualitymax 42 for illumina X

sleep 30s
rm ${file}.1.fastq
rm ${file}.2.fastq

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

sleep 30s
java -Xmx4g -jar $HOME/software/picard-tools-1.100/MarkDuplicates.jar I=$file.sam.sortP.sam O=$file.sam.sortP.sam.duprem.sam M=pe.dupstat.txt VALIDATION_STRINGENCY=SILENT REMOVE_DUPLICATES=true
sleep 30s
rm $file.sam.sortP.sam

sleep 30s
samtools view -Sbh $file.sam.sortP.sam.duprem.sam > $file.sam.sortP.sam.duprem.sam.bam
sleep 30s
rm $file.sam.sortP.sam.duprem.sam

sleep 30s
samtools view -h -q 25 -f 0x0002 -F 0x0004 -F 0x0008 -b $file.sam.sortP.sam.duprem.sam.bam > $file.sam.sortP.sam.duprem.sam.bam.mq25.bam
sleep 30s

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
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.mpileupBuild_janeiroAllFiltStrandOut $file.mpileup $workDir/



###
#		Call S-loci
###
sleep 30s
java -Xmx4G -classpath ~/java/lib/junit.jar:~/java/lib/jbzip2-0.9.1.jar:~/java/lib/args4j-2.0.12.jar:~/java/lib/commons-compress-1.0.jar:~/java/lib/gson-1.6-javadoc.jar:~/java/lib/gson-1.6-sources.jar:~/java/lib/gson-1.6.jar:bin c.e.data_processing.SlocusCoverage $file.mpileup


###
#	Make order
###

sleep 30s

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
#	The end!
###

