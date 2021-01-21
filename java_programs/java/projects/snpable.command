#!/bin/bash

echo $1

BWA= # Path to bwa-0.7.5a
snpable= # Path to seqbility-20091110
ref= # Path to the reference genome
maskFold= # Path to reference older 

cd ${maskFold}
${snpable}/splitfa ${ref} 150 | split -l 20000000 

for file in xa?
do
	${BWA}/bwa aln -R 1000000 -O 3 -E 3 ${ref} ${file} > ${file}.sai 
	${BWA}/bwa samse ${ref} ${file}.sai ${file} > ${file}.sam
	gzip -c ${file}.sam > ${file}.sam.gz
done

gzip -dc xa?.sam.gz | ${snpable}/gen_raw_mask.pl > ${maskFold}/ref.fa

${snpable}/gen_mask -l 150 -r 0.5 ${maskFold}/ref.fa > ${maskFold}/ref150.fa_50.fa

