#!/bin/bash

for i in {0..250}
do
	for j in {6..9}
	do
		bsub -q multicore40 -n 1 -R "span[hosts=1] rusage[mem=5000]" -M 10000 "./chromoPainter.command ${i} ${j}"
	done
done





