#!/bin/bash
# run symlink_masks.sh on the files we want. Should be able to just do a for loop bc it takes like a second to run this.
# 

set -e
set -u


for file in `find path_to_mask_folder -name "*mpileup.mask.txt.b.gz"`; do
    bash scripts/preprocessing/symlink_masks.sh ${file} -m
done

