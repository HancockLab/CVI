## SLiM scripts

SLiM version 3.3.2 was used to simulate different selfing-rates and how these affect the trajectories of the mutation of interest.

The SLiM scripts are split into 6k generations and 1k generations which are stored in the 6k and 1k directories respectively.

Each folder contains the different selfing rates scripts shown as (90, 95 and 99) indicating the selfing rate that it simulates.

To analyze and plot the result of the simulations along with the comparison of the CLUES inferred trajectory the following scripts were used. For each selfing rate 

1. plot_functions_final_merge.R
2. plot_local_sims.R To be used in the directory where the output of the simulations is stored. 
The main output is the "ldf_*" file along with the simulated and inferred trajectory. 

For the supplementary figures this script needs to be used within the directory where the  "ldf_*" files are stored

1. plot_sims_merge.R