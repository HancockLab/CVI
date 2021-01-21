##Script to work on the local cluster to produce the plots and tables 
##Setup and libraries
library(ggplot2)
library(plyr)
library(dplyr)
library(reshape2)

source("~/Rscripts/final/plot_functions_final.R")
##TODO Add help function
args <- commandArgs(trailingOnly = TRUE);
path <- args[1]
path_plots <- args[2]
self_rate <- args[3]
plt_name <- as.character(args[4])

setwd(path)
my_files <- list.files(pattern="rec_news*")

ldf <- list()

#Read in and store all the simulations 
ldf_test <- plot_trajectories(ldf,path, self_rate, my_files)

#Plot simulated trajectories 
plt_trajectories(ldf=ldf_test, path=path_plots, plt_name = paste("trajectories", plt_name, ".png", sep = ""))

##Time to loss and fix of a variant
lost = data.frame()
fixed = data.frame()
for (i in 1:length(my_files)){
  count = c()
  print(i) ##files names
  s <- read.delim(paste0(path, my_files[i]), stringsAsFactors = F, header = T, sep = " ")
  lost = rbind(lost, get_gen_number_lost(s$fixed))
  fixed = rbind(fixed, get_gen_number_fixed(s$fixed))
}
##Get rid of the NA ones
lost <- na.omit(lost)

#Percentage of lost ones and fixed ones
tot=200 #Total nummber of simulations
proportion_adapt = (nrow(fixed)/tot)*100

lost_variants = tot - nrow(fixed)
proportion_lost = (lost_variants/tot)*100
#Proportion of variants that adapt and get lost 

variants <- cbind(fixed = proportion_adapt, lost = proportion_lost)
write.table(x = variants, file = paste0("variants", plt_name, ".txt"))

#Simulations where no mutations arose
no_m = get_no_muts(my_files = my_files)
write.table(no_m,  paste0("no_muts", plt_name, ".txt"))

#Fixed distributions
#Plot Distributions
df_fixed = plot_fixed_dist(my_files = my_files, path, path.figs = path_plots,  paste0("fixed_distribution_" , plt_name , ".pdf"))

#Lost distributions
df_lost = plot_lost_dist(my_files = my_files, path, path.figs = path_plots,  paste0("loss_distribution_" , plt_name, ".pdf"))
df_lost = na.omit(df_lost)


# #Get the mean number of generations per simulation to get segregating variant
df_get_seg <- NULL
for (i in 1:length(my_files)){
   s <- read.delim(paste0(path, my_files[i]), stringsAsFactors = F, header = T, sep = " ")
   seg <- get_gen_number_seg(s$fixed)
   rows_of_s = nrow(s)
   if (length(seg) == 0){
     next
   }
   df_get_seg <- rbind(df_get_seg, data.frame(x = seg,  grp = i))
 }

# 
#Mean time to arise and to loss of variants
df_gens <- cbind(arisal_fixed=mean(df_fixed$y), mean_gen_fix=mean(df_fixed$x), arisal_loss=mean(df_lost$y))
write.table(x = df_gens,  paste0("mean_generations", plt_name, ".txt"), row.names = F)

write.table(x = mean(df_get_seg$x), paste0("mean_seg_gen", plt_name, ".txt"), row.names = F)

#Read output from all the simulations 
print("Reading LDF")
ldf <- read.table("ldf_test.txt", stringsAsFactors = F, header = T)
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
plt_multi(grps_new,  plt_name =  paste0("panel_" , plt_name, ".png"))




