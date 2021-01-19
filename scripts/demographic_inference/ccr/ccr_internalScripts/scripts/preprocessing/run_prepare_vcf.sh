#!/bin/bash
# run prepare_vcf.sh on the files we want. Should be able to just do a for loop bc it takes like a second to run this.
# 

set -e
set -u


for file in `find path_to_compressed_snp_files -name "*snp.vcf.b.gz"`; do
    bash scripts/preprocessing/prepare_vcf.sh ${file} -m
done
