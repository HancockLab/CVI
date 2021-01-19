#!/bin/bash
# undo the gzip, append missing columns, bgzip (with new filename [sample number]), tabix

set -e
set -u

WORK= # path to folder with SNP files
if [[ ${2} = "-o" ]]; then
    SAMPLE_NAME=`basename "${1}"` #need to get sample NUMBER
    SAMPLE_NUMBER=`echo ${SAMPLE_NAME} | cut -d "." -f 1`
fi
if [[ ${2} = "-m" ]]; then
    SAMPLE_NAME=`basename "${1}"` #need to get sample NUMBER
    S=`echo ${SAMPLE_NAME} | cut -d "." -f 2`
    SAMPLE_NUMBER=`echo ${S} | cut -d "_" -f 1`
fi

if [[ ${1} =~ \.gz$ ]]; then
    awk -v SAMPLE="${SAMPLE_NUMBER}" '$1 == "#CHROM" {print $0"\tFORMAT\t"SAMPLE} $1 !~ "#CHROM" {print $0"\tGT\t1"}' <(gzip -dc $1) | bgzip -c > $WORK/${SAMPLE_NUMBER}.vcf.gz
else
    awk -v SAMPLE="${SAMPLE_NUMBER}" '$1 == "#CHROM" {print $0"\tFORMAT\t"SAMPLE} $1 !~ "#CHROM" {print $0"\tGT\t1"}' $1 | bgzip -c > $WORK/${SAMPLE_NUMBER}.vcf.gz
fi

tabix -p vcf $WORK/${SAMPLE_NUMBER}.vcf.gz
