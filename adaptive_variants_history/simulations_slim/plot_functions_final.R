library(ggplot2)
library(dplyr)
library(reshape2)
library(plyr)

get_gen_number_fixed <- function(col){
  tmp <- strsplit(paste0(col, collapse = ""), split = "L") %>% unlist() 
    return(nchar(tmp[grepl("F", tmp) == T])-1) 
}

get_gen_number_lost <- function(col){
  tmp <- strsplit(paste0(col, collapse = ""), split = "L") %>% unlist() 
  tmp <- tmp[grepl("F", tmp) == F & grepl("S", tmp) ==T]
  output = c()
  for(n in 1:length(tmp)){
    output = c(output, nchar(tmp[n]))
  }
  return(output)
}

my_theme <- theme_bw() +
  theme(axis.line = element_line(colour = "black"),
        panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(),
        panel.border = element_rect(colour = "black"),
        panel.background = element_blank(),
        axis.text = element_text(size = 7, colour = "black"),
        axis.title = element_text(size = 7, colour = "black"), 
        axis.title.x = element_text(vjust = 3.0),
        legend.position = "none")


plot_trajectories <- function(ldf, path, rate, my_files){
  ldf <- list()
  for(i in my_files){
    f = my_files[i]
    s <- read.delim(paste0(path, i), stringsAsFactors = F, header = T, sep = " ")
    s$grp <- rep(paste0("line", i), nrow(s))
    #Change order of the generations so that it is backwards in time. 
    #Taking as the starting point 6000 instead of 0
    coal_gen = NULL
    max_gen = 6000 
    for (i in 1:nrow(s)){
    new_g <- max_gen - s$gen[i]
    coal_gen <- rbind(coal_gen, new_g)
    }
    s <- cbind(s, coal_gen)
    ldf <- rbind(ldf, s) %>% as.data.frame()
  }
  write.table(ldf, paste("ldf_", rate, ".txt", sep=""), row.names=F, quote=F, sep="\t")
  return(ldf)
}

#Plot trajectories from the simulations
plt_trajectories <- function(ldf, path, plt_name){
  grps <- ldf %>% filter(fixed %in% c("L", "S")) %>% as.data.frame()
  grps$gen <- as.numeric(grps$gen)
  plt <- ggplot(data=grps, 
                aes(x=gen,
                    y = frequency,
                    group=grp,
                    color= fixed)) + 
    geom_line(size=0.5, alpha = 0.2) +
    xlab("Generations") +
    ylab("Frequency") + 
    scale_color_manual(name="Variants", values= c(L = "darkorange", S = "darkgray", F="green")) +
    theme_bw()
  #Save plot
  png(paste(path, plt_name, sep=""), units = "px",  width = 800, height = 400)
  plot(plt)
  dev.off()
  print("saved", str(plt_name))
  return(plt)
}

#Store variants that get lost or fixed
get_variants <- function (my_files) {
  l_variants = c()
  lost = data.frame()
  fixed = data.frame()
  for (i in 1:length(my_files)){
    s <- read.delim(paste0(path, my_files[i]), stringsAsFactors = F, header = T, sep = " ")
    lost = rbind(lost, get_gen_number_lost(s$fixed))
    colnames(lost) <- "lost"
    fixed = rbind(fixed, get_gen_number_fixed(s$fixed))
    colnames(fixed) <- "fixed"
  }
  l_variants <- c(l_variants, lost,  fixed)
  
  return(l_variants)
}


# #Plot distribution of the variants that fix and return fixed variants DF
plot_fixed_dist <- function(my_files, path, plt_name, path.figs){
  df_plot_fixed = NULL
  for (i in 1:length(my_files)){
    count = c()
    s <- read.delim(paste0(path, my_files[i]), stringsAsFactors = F, header = T, sep = " ")
    rows_of_s = nrow(s)
    ##Store the generations to get fixed
    tmp_f = get_gen_number_fixed(s$fixed)
    if (length(tmp_f) == 0){
      next
    }
    index_first_s = rows_of_s - (tmp_f + 1)
    df_plot_fixed <- rbind(df_plot_fixed, data.frame(x = index_first_s, y = tmp_f, grp = i))
  }
  plot_fixed_dist <- ggplot()+
    geom_density(aes(df_plot_fixed$y))+
    xlab( "Time to fix") +
    ggtitle("Fixed variants")+
    geom_vline(aes(xintercept = mean(df_plot_fixed$y),  color = "mean") , linetype = "dashed") +
    scale_color_manual(name="Stats", values= c(mean = "darkorange")) +
    theme_bw()
  pdf(paste(path, plt_name, sep=""), width = 10, height = 10)
  plot(plot_fixed_dist)
  dev.off()
  return(df_plot_fixed)
}




#Plot lost ones and return lost variants DF
plot_lost_dist <- function(my_files, path, plt_name, path.figs){
  df_plot_lost = NULL
  for (i in 1:length(my_files)){
    s <- read.delim(paste0(path, my_files[i]), stringsAsFactors = F, header = T, sep = " ")
    rows_of_s = nrow(s)
    ##Store the generations to get lost 
    tmp_f = get_gen_number_lost(s$fixed)
    if (length(tmp_f) == 0){
      next
    }
    index_first_s = rows_of_s - (tmp_f + 1)
    df_plot_lost <- rbind(df_plot_lost, data.frame(x = index_first_s, y = tmp_f, grp = i))
  }
  df_plot_lost = na.omit(df_plot_lost)
  plot_lost_dist <- ggplot()+ 
    geom_density(aes(df_plot_lost$y))+
    xlab( "Time of loss") + 
    ggtitle("Lost variants")+
    theme_bw()
  pdf(paste(path, plt_name, sep=""), width = 10, height = 10)
  plot(plot_lost_dist)
  dev.off()
  return(df_plot_lost)
}

#No mutations 
get_no_muts <- function(my_files) {
  count_lost = 0
  for(i in my_files){
    # count = count + 1
    f = my_files[i]
    cat("reading ", i, "\n")
    s <- read.delim(paste0(path, i), stringsAsFactors = F, header = T, sep = " ")
    if(all(s$fixed == "L")){
      count_lost = count_lost + 1
      print("count_lost")
    }
    else{
      print("no")
    }
    print(count_lost)
  }
}

#Mean time for a variant to occurr 
get_gen_number_seg <- function(col){
  tmp <- strsplit(paste0(col, collapse = ""), split = "S") %>% unlist()
  # #Store the first one
  return(nchar(tmp[1]) + 1)
}


#Simulated and inferred trajectories plot
plt_multi <- function(grps_new, plt_name){
  plt <- ggplot() +
    geom_line(aes(x=as.numeric(grps_new$new_gen)/1000,
                  y = grps_new$frequency,
                  group=grps_new$grp, 
                  color=grps_new$color), 
                  size=0.5, 
                  alpha = 0.2)+ 
    geom_point(aes(x=as.numeric(grps_new$pl2)/1000,
                   y = as.numeric(grps_new$plt),
                   group=grps_new$grp,
                   color=grps_new$color),
                   size=0.7, 
                   alpha = 0.7) +
    xlab(expression(paste(Time~(10^3 ~"years ago")))) +
    ylab("Allele frequency")  +
    scale_color_manual(name="Variants", values= c(simulated = "grey", inf="#FF7F50"),
                       labels=c("Inferred", "Simulated")) +
    theme(legend.title = element_blank())+ 
    my_theme
  png(paste(path, plt_name, sep=""), units = "in",  width = 5, height = 5, res= 300)
  plot(plt)
  dev.off()
  print("saved", str(plt_name))
}


