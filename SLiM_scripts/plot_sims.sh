#!/bin/bash
path=$(pwd)/
script=./plot_sims.R
path_plots=$path
rate=95
plots=95_recessive_092_final




Rscript $script $path $path_plots $rate $plots
