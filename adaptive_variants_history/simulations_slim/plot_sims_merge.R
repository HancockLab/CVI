##Script to work on the local cluster to produce the plots and tables 
##Setup and libraries
library(ggplot2)
library(plyr)
library(dplyr)
library(reshape2)

source("~/Rscripts/plot_functions_final_merge.R")
args <- commandArgs(trailingOnly = TRUE);
path <- args[1]
path_plots <- args[2]
plt_name <- as.character(args[3])
setwd(path)


#Read output from all the simulations 
create_grps <- function(ldf_test_file){
   print("Reading LDF")
   ldf <- read.table(ldf_test_file, stringsAsFactors = F, header = T)
   grps <- ldf %>% filter(fixed %in% c("S", "L")) %>% as.data.frame()
   grps$gen <- as.numeric(grps$gen)
   print("plot grps modify")
   ##For plotting
   grps$plt <- rep(paste0(NA), nrow(grps))
   grps$plt2 <- rep(paste0(NA), nrow(grps))
   grps <- grps[, c(1,2,4,5,6,7)]
   colnames(grps) <- c("gen", "frequency", "grp","new_gen", "plt" , "pl2")
   print("read clues inferrence")
   
   #Inferred trajectory from CLUES
   inf_14 <- read.table("20201213_FO_haploid_chr5_FLC_inference_sMAX1_TCO7k_coal_df100_dom0_3bins", stringsAsFactors = F, header = F)
   colnames(inf_14) <- c("gen", "frequency")
   inf_14$gen <- as.numeric(inf_14$gen)
   
   #Modify for plotting
   inf_14$new_gen <- rep(paste0(NA), nrow(inf_14))
   grp_name <- rep(paste0("line14k"), nrow(inf_14)) %>% as.data.frame()
   inf_14_new <- cbind(inf_14, grp_name)
   
   colnames(inf_14_new) <- c("gen", "frequency", "new_gen", "grp")
   inf_14_new$plt <- inf_14_new$frequency
   inf_14_new$frequency <- NA
   inf_14_new$plt2 <- inf_14_new$gen
   inf_14_new$new_gen <- NA
   
   ##Add column to color them based on the dataframe if it's the inferred one or simulated one 
   inf_14_new$color <- "inf"
   grps$color <- "simulated"
   colnames(inf_14_new) <-  c("gen", "frequency", "new_gen", "grp", "plt", "pl2", "color")
   grps_new <- rbind(grps, inf_14_new)
   print("rbinded")
   
   #Combined plots. For this the simulated trajectories have been inverted to backwards in time 
   print("Creating panel")
   # plt_multi(grps_new,  plt_name =  paste0("panel_" , plt_name, ".png"))
   return(grps_new)
}

grps_new_90 <- create_grps("ldf_90.txt")
grps_new_95 <- create_grps("ldf_95.txt")
grps_new_99 <- create_grps("ldf_99.txt")

plt_multi(grps_new_90, grps_new_95, grps_new_99, plt_name = paste0("panel_", plt_name, ".png"))



