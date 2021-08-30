## SLiM scripts

SLiM version 3.3.2 was used to simulate different selfing-rates and how these affect the trajectories of the mutation of interest.

The SLiM scripts are split into 6k generations and 1k generations which are stored in the 6k and 1k directories respectively.

Each folder contains the different selfing rates scripts shown as (90, 95 and 99) indicating the selfing rate that it simulates.

To analyze and plot the result of the simulations along with the CLUES inferred trajectory the following scripts were used for each selfing rate.

1. plot_sims.sh
2. plot_functions_final.R
3. plot_sims.R To be used in the directory where the output of the simulations is stored. 
4. 20201213_FO_haploid_chr5_FLC_inference_sMAX1_TCO7k_coal_df100_dom0_3bins -- inferred CLUES trajectory

The main output is the "ldf_*" file along with the plot of the simulated and inferred trajectory. 


For the supplementary figures this script needs to be used within the directory where the  "ldf_*" files are stored of the three different selfing rates and use the following bash script

1. plot_merge.sh
1. plot_functions_final_merge.R
2. plot_sims_merge.R
3. 20201213_FO_haploid_chr5_FLC_inference_sMAX1_TCO7k_coal_df100_dom0_3bins -- inferred CLUES trajectory
 
