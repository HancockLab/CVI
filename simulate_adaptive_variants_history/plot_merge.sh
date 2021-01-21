#!/bin/bash
path=$(pwd)/
script=./plot_sims_merge.R
path_plots=$path
plots=inf_sim_merge



Rscript $script $path $path_plots $plots
