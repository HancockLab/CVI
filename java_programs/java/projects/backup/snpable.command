#!/bin/bash

echo $1

BWA=$HOME/software/bwa-0.7.5a
snpable=/home/fulgione/software/seqbility-20091110
ref=/srv/biodata/dep_coupland/grp_hancock/andrea/references/s1-1_19-04-05/S1-1_OnlyChromosomes.fasta
	# $HOME/runningShore/indexFolder_upCase/atAndSlociColl02_upCase.fa
# file=${HOME}/analysis/snpable/at
maskFold=/srv/biodata/dep_coupland/grp_hancock/andrea/references/s1-1_19-04-05

cd ${maskFold}
${snpable}/splitfa ${ref} 150 | split -l 20000000 

for file in xa?
do
	$BWA/bwa aln -R 1000000 -O 3 -E 3 ${ref} ${file} > ${file}.sai 
	$BWA/bwa samse ${ref} ${file}.sai ${file} > ${file}.sam
	gzip -c ${file}.sam > ${file}.sam.gz
done

gzip -dc xa?.sam.gz | ${snpable}/gen_raw_mask.pl > ${maskFold}/s1-1_snpable150.fa

${snpable}/gen_mask -l 150 -r 0.5 ${maskFold}/s1-1_snpable150.fa > ${maskFold}/s1-1_snpable150.fa_50.fa

