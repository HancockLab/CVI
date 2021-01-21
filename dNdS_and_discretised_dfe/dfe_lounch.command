#!/bin/bash

for inf in ./CVI/scripts/dNdS_and_discretised_dfe/data/input_polyDFE/*_polyDFE.txt
do
	# for model in A C del 
		# classic A C del 
	# do
		# for div in true false
		# do
			./dfe_poly.command ${inf}
			# ./dfe_poly.command ${inf} ${model} ${div}
		# done
	# done
done

