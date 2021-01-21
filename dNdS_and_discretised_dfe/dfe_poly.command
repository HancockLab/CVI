#!/bin/bash

# input files in:
# ./CVI/scripts/dNdS_and_discretised_dfe/data/input_polyDFE/*_polyDFE.txt 


infile=${1}

polyDfe= # Path to polyDFE-master
${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${infile} > ${infile}_full_C

infile=${1}
model=${2}
div=${3}

####
###
# 	Run
###
######

# Classic
#
if [ "${model}" == "classic" ] && [ "${div}" == "true" ]
then
	echo ${infile} ${model} ${div} "simple"
	${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${infile} > ${infile}_classic_C
	echo ${infile} ${model} ${div} "No Divergence data"
	${polyDfe}/./polyDFE-2.0-linux-64-bit -w -d ${infile} > ${infile}_classic_polyOnly_C
	echo ${infile} ${model} ${div} "Inferring a deleterious DFE using model C"
	${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${infile} -i ${polyDfe}/input/init_model_BandC.txt 2 -v 100 > ${infile}_classic_del_C
	echo ${infile} ${model} ${div} "Model C without nuisance r parameters"
	${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${infile} -i ${polyDfe}/input/init_model_BandC.txt 4 -j > ${infile}_classic_noNuis_C
	echo ${infile} ${model} ${div} "Model C, allow for ancestral error"
	${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${infile} -i ${polyDfe}/input/init_model_BandC.txt 5 > ${infile}_classic_ancError_C
fi


###
#	From chimp data
###




################################################
### Full model A
################################################
if [ "${model}" == "A" ]
then
	if [ "${div}" == "true" ]
	then
		echo "model A"
		echo ${infile} ${model} ${div} "without r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 2 -e > ${inFile}_A_nor_noeps 
		echo ${infile} ${model} ${div} "without r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 3 -e > ${inFile}_A_nor
		echo ${infile} ${model} ${div} "with r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 4 -e > ${inFile}_A_noeps
		echo ${infile} ${model} ${div} "with r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 1 -e > ${inFile}_A
		### to easily enable processing of the results, 
		### output files can be merged into one file
		cat ${inFile}_A_nor_noeps >  ${inFile}_A.txt 
		cat ${inFile}_A_nor       >> ${inFile}_A.txt
		cat ${inFile}_A_noeps     >> ${inFile}_A.txt
		cat ${inFile}_A           >> ${inFile}_A.txt
	else 
		echo ${infile} ${model} ${div} "without r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 2 -e -w > ${inFile}_A_nor_noeps_no_div
		echo ${infile} ${model} ${div} "without r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 3 -e -w > ${inFile}_A_nor_no_div
		echo ${infile} ${model} ${div} "with r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 4 -e -w > ${inFile}_A_noeps_no_div
		echo ${infile} ${model} ${div} "with r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 1 -e -w > ${inFile}_A_no_div
		cat ${inFile}_A_nor_noeps_no_div >  ${inFile}_A_no_div.txt
		cat ${inFile}_A_nor_no_div       >> ${inFile}_A_no_div.txt
		cat ${inFile}_A_noeps_no_div     >> ${inFile}_A_no_div.txt
		cat ${inFile}_A_no_div           >> ${inFile}_A_no_div.txt
	fi
fi



################################################
### Full model C
################################################
if [ "${model}" == "C" ]
then
	if [ "${div}" == "true" ]
	then
		echo "model C"
		echo ${infile} ${model} ${div} "without r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m C -i ${polyDfe}/init_C.txt 2 -e > ${inFile}_C_nor_noeps
		echo ${infile} ${model} ${div} "without r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m C -i ${polyDfe}/init_C.txt 3 -e > ${inFile}_C_nor
		echo ${infile} ${model} ${div} "with r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m C -i ${polyDfe}/init_C.txt 4 -e > ${inFile}_C_noeps
		echo ${infile} ${model} ${div} "with r, with eps_an"
		### this is the default behavior of polyDFE, so we can skip specifying
		### -m C -i init_C.txt 1 -e
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 > ${inFile}_C
		### merge output
		cat ${inFile}_C_nor_noeps >  ${inFile}_C.txt
		cat ${inFile}_C_nor       >> ${inFile}_C.txt
		cat ${inFile}_C_noeps     >> ${inFile}_C.txt
		cat ${inFile}_C           >> ${inFile}_C.txt
	else
		echo ${infile} ${model} ${div} "without r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m C -i ${polyDfe}/init_C.txt 2 -e -w > ${inFile}_C_nor_noeps_no_div
		echo ${infile} ${model} ${div} "without r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m C -i ${polyDfe}/init_C.txt 3 -e -w > ${inFile}_C_nor_no_div
		echo ${infile} ${model} ${div} "with r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m C -i ${polyDfe}/init_C.txt 4 -e -w > ${inFile}_C_noeps_no_div
		echo ${infile} ${model} ${div} "with r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1                         -w > ${inFile}_C_no_div
		cat ${inFile}_C_nor_noeps_no_div >  ${inFile}_C_no_div.txt
		cat ${inFile}_C_nor_no_div       >> ${inFile}_C_no_div.txt
		cat ${inFile}_C_noeps_no_div     >> ${inFile}_C_no_div.txt
		cat ${inFile}_C_no_div           >> ${inFile}_C_no_div.txt
	fi
fi




################################################
### Deleterious model
################################################
if [ "${model}" == "del" ]
then
	if [ "${div}" == "true" ]
	then
		echo "model del"
		echo ${infile} ${model} ${div} "without r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 6 -e > ${inFile}_Del_nor_noeps
		echo ${infile} ${model} ${div} "without r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 7 -e > ${inFile}_Del_nor
		echo ${infile} ${model} ${div} "with r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 8 -e > ${inFile}_Del_noeps
		echo ${infile} ${model} ${div} "with r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 5 -e > ${inFile}_Del
		### merge output
		cat ${inFile}_Del_nor_noeps >  ${inFile}_Del.txt
		cat ${inFile}_Del_nor       >> ${inFile}_Del.txt
		cat ${inFile}_Del_noeps     >> ${inFile}_Del.txt
		cat ${inFile}_Del           >> ${inFile}_Del.txt
	else
		echo ${infile} ${model} ${div} "without r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 6 -e -w > ${inFile}_Del_nor_noeps_no_div
		echo ${infile} ${model} ${div} "without r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 7 -e -w > ${inFile}_Del_nor_no_div
		echo ${infile} ${model} ${div} "with r, without eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 8 -e -w > ${inFile}_Del_noeps_no_div
		echo ${infile} ${model} ${div} "with r, with eps_an"
		${polyDfe}/./polyDFE-2.0-linux-64-bit -d ${inFile} -b ${polyDfe}/params_basinhop.txt 1 -m A -i ${polyDfe}/init_A.txt 5 -e -w > ${inFile}_Del_no_div
		cat ${inFile}_Del_nor_noeps_no_div >  ${inFile}_Del_no_div.txt
		cat ${inFile}_Del_nor_no_div       >> ${inFile}_Del_no_div.txt
		cat ${inFile}_Del_noeps_no_div     >> ${inFile}_Del_no_div.txt
		cat ${inFile}_Del_no_div           >> ${inFile}_Del_no_div.txt
	fi
fi




