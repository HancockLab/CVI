######## UNLESS SPECIFIED, RUN THE SCRIPT ON R
######## whenever you find <...>, you should load the data mentioned from Supplementary Tables

# set working directory
setwd("")

# load libraries
library(ggplot2)
library(gdata)
library(chromPlot)
library(dplyr)
library(gmodels)
library(metaseqR)
library(caret)
library(leaflet)
library(ggpubr)


####################################################
##################### Extra  #######################
####################################################

# colours and shapes
fogo_col <- "#FFA405"
sa_col <- "#0075DC"
mor_col <-"#2BCE48"
fogo_shape <- 17
sa_shape <- 16
mor_shape <- 18
S_Fig= #B0E0E6
S_Cova= #00BFFF
S_Pico= #1E90FF
S_Espong= #0000FF
F_MonteVelha= #FF7F50
F_Lava= #FF8C00
F_Inferno= #FF4500

# statistical comparisons between pops
my_comparisons <- list( c("SA", "Fogo"), c("Fogo", "Mor"), c("SA", "Mor"))

# extra functions:
## n in boxplot
stat_box_data <- function(y, lower_limit = 10) {
  return( 
    data.frame(y = lower_limit,
               label = paste('n =', length(y))
    )
  )
}

## normalization
normalize <- function(x) {
  return ((x - min(x)) / (max(x) - min(x)))
}

## theme
my_theme <- theme_bw() +
  theme(axis.line = element_line(colour = "black"), 
        panel.grid.major = element_blank(),
        panel.grid.minor = element_blank(),
        panel.border = element_rect(colour = "black"),
        panel.background = element_blank(), 
        axis.text = element_text(size = 7, colour = "black"), 
        axis.title = element_text(size = 7, colour = "black"), 
        legend.position = "none")
        
        
#############################################
################### Maps ####################
#############################################     
## Fig 1A
allIsl=read.table("allCoordinates_MorSAFogo.txt", header = T)

icons <- iconList(
  circle <- makeIcon(
    iconUrl = "blueCircle.png",
    iconWidth = 20, iconHeight = 20),
  diamond <- makeIcon(
    iconUrl = "greenDiamond.png",
    iconWidth = 20, iconHeight = 20),
  triangle <- makeIcon(
    iconUrl = "orangeTriangle_transp.png",
    iconWidth = 30, iconHeight = 30)
)

######## plot everything at once
leaflet(allIsl) %>%
  addProviderTiles("GeoportailFrance.orthos") %>%
  addMarkers(lng = ~ Long, lat = ~ Lat, icon = ~ icons[as.numeric(Island)]) %>%
  addScaleBar(position = "bottomright", options = scaleBarOptions(metric = T, imperial = F)) 

######## one by one
sa=read.table("SantoAntao_sites.txt", header = T)
head(sa)
fogo=read.table("Fogo_sites.txt", header = T)
head(fogo)
mor=read.table("mor_sites.txt", header = T)

triangle <- makeIcon(
  iconUrl = "orangeTriangle_transp.png",
  iconWidth = 30, iconHeight = 30)

circle <- makeIcon(
  iconUrl = "blueCircle.png",
  iconWidth = 20, iconHeight = 20)

diamond <- makeIcon(
  iconUrl = "greenDiamond.png",
  iconWidth = 20, iconHeight = 20)

m <- leaflet() 
m <- addMarkers(m, lat=allIsl$Lat, lng=allIsl$Long, label=allIsl$Pop, icon = ~icons[allIsl$Island])
m <- addProviderTiles(m, providers$GeoportailFrance.orthos) 
m <- addScaleBar(m, position = "bottomright", options = scaleBarOptions(metric = T, imperial = F))
m

mapshot(m, file = "map.pdf")



### S-locus map
world <- map_data("world")
world <- world[world$long > -30 & world$long < -20 & world$lat > 10 & world$lat < 20,]
p <- ggplot(world, aes(long, lat)) +
  geom_map(map=world, aes(map_id=region), fill=NA, color="black") +
  coord_quickmap() + xlim(-25.1,-25.01) + ylim(17.09,17.13) + theme(plot.background = element_rect(fill = "transparent"), axis.text.x=element_blank(), axis.text.y=element_blank(), panel.background = element_rect(fill = "transparent"), panel.grid.minor = element_blank(), panel.grid.major = element_blank(), axis.line.x = element_line(color="black", size = 0.5), axis.line.y = element_line(color="black", size = 0.5)) + xlab("Longitude") + ylab("Latitude")

#load S-locus info (from "African genomes illuminate the early history and transition to selfing in Arabidopsis thaliana") 
d=read.table("Slocus_map.txt", header = T)

s = p + geom_scatterpie(aes(x=long-20, y=lat, group=pop, r=radius),
                        data=d, cols=c(4,5, 6, 7),sorted_by_radius = T , alpha=.8) + labs(fill = "S-locus alleles") + 
  theme(axis.title.x = element_text(size=16), axis.title.y = element_text(size=16), legend.position = "none") +
  scale_fill_manual(values =c("#0072B2", "#F0E442", "#D55E00", "#CC79A7"))#+
geom_scatterpie_legend(d$radius, x=-25.03, y=17.12, n=4, labeller=function(x) 10*x/0.005)
s



#############################################
#################### PCA ####################
#############################################
# in terminal
# prepare vcf file with vcftools
#vcftools --vcf <vcf file released> --keep <text file with IDs from SA individuals from Table S1> --recode --out SA.vcf
#vcftools --vcf SA.vcf --remove-indv 3968_AL --remove-indv 6911 --plink --out SA_no3968_AL_no6911

# run pca in plink
#plink1.9 --file SA_no3968_AL_no6911 --pca --out SA_no3968_AL_no6911

# manually edit eigen file by adding pops and clusters

# plot Fig. S7A
eigenvec=read.table("SA_no3968_AL_no6911.eigenvec", header=F)
head(eigenvec)
pca=ggplot(eigenvec, aes(x=eigenvec$V4, y=eigenvec$V5, 
                         group=eigenvec$V3, color=eigenvec$V2, shape = eigenvec$V2)) + 
  geom_point()+ 
  #geom_text(aes(label=V3),hjust=0, vjust=0, size=2.5, check_overlap = T) +
  my_theme +
  scale_color_manual(values = c("#00BFFF", "#0000FF", "#B0E0E6", "#1E90FF")) +
  scale_shape_manual(values =c(18, 19, 17, 15)) +
  xlab("PC1 (4.47%)") + ylab("PC2 (4.05%)")
pca

ggsave(plot = pca, filename = "./PCA_shore_no3968_AL_no6911.pdf", width = 8, height = 6, units = "cm")


################################
####### Climate data ###########
################################

###### from the field site (S16)
s16=read.table(<data in Table S12>, header = T)

s16$Date <- as.Date(s16$Date, format = "%d.%m.%Y")
s16$newTime = paste(s16$Date, s16$Time)
s16$newTime2=as.POSIXct(s16$newTime, format="%Y-%m-%d %H:%M:%S")
s16$newTime2 <- as.Date(s16$newTime2)

# plot Fig. S9A
s16_plot = ggplot() + 
  geom_line(data=s16, aes(x=newTime2, y=Temp.C., color = "Temperature (??C)")) + 
  geom_line(data = s16, aes(x=newTime2, y=Humid..., color="Humidity (%)")) +
  geom_line(data = s16, aes(x=newTime2, y=Precip.Counts.*0.2, color = "Precipitation (mm)")) +
  my_theme + theme(axis.text.x = element_text(angle = 30, hjust = 1)) +
  xlab("Date") + ylab("Value") +
  scale_color_manual(values=c("#00BA38", "#619CFF", "#F8766D")) +
  scale_x_date(date_labels = "%b %y", breaks = "2 month")

ggsave(s16_plot, filename = "./S16_plot.pdf", width = 15, height = 9, units = "cm")


###### simulated in the chamber 
chamber=read.table(<data in Table S13>, header = T)  
head(chamber)
chamber$Day <- as.Date(chamber$Day, format = "%d.%m.%Y")
chamber$newTime = paste(chamber$Day, chamber$Time)
chamber$newTime2=as.POSIXct(chamber$newTime, format="%Y-%m-%d %H:%M:%S")

lims <- as.POSIXct(strptime(c("0000-01-01 05:00","0000-03-10 00:00"), format = "%Y-%m-%d %H:%M"))   

labels <- seq(from = as.POSIXct(strptime("0000-01-01 05:00", format = "%Y-%m-%d %H:%M")), 
              to = as.POSIXct(strptime("0000-03-10 00:00", format = "%Y-%m-%d %H:%M")), by = 7*24*60*60)    

# plot Fig. S9B
chamber_plot = ggplot(chamber[chamber$Day < "0000-03-10",]) + 
  geom_line(aes(x=newTime2, y=Temp, color = "Temperature (??C)")) + 
  geom_line(aes(x=newTime2, y=Humidity, color="Humidity (%)")) +
  geom_vline(xintercept = chamber[chamber$Water == "1", "newTime2"], colour = "#619CFF") +
  my_theme + theme(axis.text.x = element_text(angle = 30, hjust = 1)) +
  xlab("Date") + ylab("Value") +
  scale_color_manual(values=c("#00BA38",  "#F8766D" )) +
  scale_x_datetime(labels = date_format("%j"), limits = lims, breaks = labels)

ggsave(chamber_plot, filename = "./chamber_plot.pdf", width = 15, height = 9, units = "cm")


####################################################
######### CVI simulated Experiment  ################
####################################################

# load data (bolting and fitness per replicate):
bronson=read.table(<data in Table S15>, header=T)
head(bronson)
summary(bronson)
bronson <- bronson[order(-as.numeric(factor(bronson$Island))),]
bronson$Island <- factor(bronson$Island , levels=c("SA", "Fogo", "Mor", "Control", "Mutants"))


# test chamber units effects
summary(aov(BoltingDays ~ Island + ID + ChamberUnit + TrayColumn + TrayRow, bronson))  ### no effect
summary(aov(BoltingDays ~ Island  * ChamberUnit, bronson))

# correlation between flowering time traits
#Morocco
correl_mor=cor(na.omit(bronson[bronson$Island == "Mor",c("BoltingDays", "LeavesAtBolting", "FloweringDays", "DaysTo3cm")]), use ="p", method = "spearman")
#SA
correl_sa=cor(na.omit(bronson[bronson$Island == "SA",c("BoltingDays", "LeavesAtBolting", "FloweringDays", "DaysTo3cm")]), use ="p", method = "spearman")
#Fogo
correl_fogo=cor(na.omit(bronson[bronson$Island == "Fogo",c("BoltingDays", "LeavesAtBolting", "FloweringDays", "DaysTo3cm")]), use ="p", method = "spearman")

# get median per genotype
all_med=aggregate(bronson[, c("BoltingDays", "LeavesAtBolting", "FloweringDays", "DaysTo3cm", "TotalSeedNumber")], 
                  list(bronson$ID, bronson$Island, bronson$Pop, bronson$CRY2, bronson$MPK12, bronson$FtsZ, bronson$FRI, bronson$ANAC, bronson$FLS2,  bronson$GI, bronson$FLC), median, na.rm = T)
colnames(all_med) <- c("ID", "Island", "Pop", "CRY2", "MPK12", "FtsZ", "FRI", "ANAC", "FLS2",  "GI", "FLC", "BoltingDays", "LeavesAtBolting", "FloweringDays", "DaysTo3cm", "TotalSeedNumber")

#exclude Col-0 and mutants
all_nocol=bronson[bronson$Island == "SA" | bronson$Island == "Fogo" | bronson$Island == "Mor",]
all_nocol_med=aggregate(all_nocol[, c("BoltingDays", "LeavesAtBolting", "FloweringDays", "DaysTo3cm", "TotalSeedNumber")], list(all_nocol$ID, all_nocol$Island), median, na.rm = T)
colnames(all_nocol_med) <- c("ID", "Island", "BoltingDays", "LeavesAtBolting", "FloweringDays", "DaysTo3cm", "TotalSeedNumber")
head(all_nocol_med)


####################################################
####### CVI simulated Experiment: Fitness  #########
####################################################
# add 1 to seeds = 0 to log
bronson$TotalSeedNumber2 = bronson$TotalSeedNumber + 1
all_nocol_med$TotalSeedNumber2 = all_nocol_med$TotalSeedNumber + 1

### Fig 2C
fit_cvi=ggplot(all_nocol_med, aes(x= Island, y=log(TotalSeedNumber2), colour = Island, shape = Island)) + 
  geom_hline(yintercept = 0, size = 0.2) +
  geom_boxplot(outlier.shape = NA, lwd = 0.2) + 
  #stat_summary(fun.data = stat_box_data, geom = "text", size = 1.5) +
  geom_jitter(aes(x=Island, y=log(TotalSeedNumber2), colour = Island, shape = Island), alpha = 0.5, size = 0.5) +
  scale_colour_manual(values=c(sa_col, fogo_col, mor_col)) +
  scale_x_discrete(limits = c("Mor", "SA", "Fogo")) +
  scale_shape_manual(values = c(sa_shape, fogo_shape, mor_shape)) +
  stat_compare_means(comparisons = my_comparisons, size = 2, label.y = c(9, 11, 10), method = "wilcox.test") +
  ylim(-0.5, 12) + my_theme +
  xlab('') + ylab(expression(log[e]~(Seed_number)))
fit_cvi

ggsave(plot = fit_cvi, width = 3.9, height = 4.7, filename = "./fit_cvi.pdf", units = "cm")

# check differences
mean(na.omit(all_nocol[all_nocol$Island == "SA", "TotalSeedNumber"])) / mean(na.omit(all_nocol[all_nocol$Island == "Mor", "TotalSeedNumber"]))
mean(na.omit(all_nocol[all_nocol$Island == "Fogo", "TotalSeedNumber"])) / mean(na.omit(all_nocol[all_nocol$Island == "Mor", "TotalSeedNumber"]))

# test differences
wilcox.test(all_nocol[!all_nocol$Island == "Mor", "TotalSeedNumber"], all_nocol[all_nocol$Island == "Mor", "TotalSeedNumber"])


##################################################
########### Fitness under Mor conditions #########
##################################################
mor=read.table(<data in Table S16>, header = T)
head(mor)
mor = mor[order(-as.numeric(factor(mor$Island))),]
mor$Island = factor(mor$Island , levels=c("SA", "Fogo", "Mor"))
mor_med=aggregate(mor[, c("weight")], list(mor$ID, mor$Island), median, na.rm = T)
colnames(mor_med) <- c("ID", "Island", "weight")
head(mor_med)

## plot S10
fit_mor=ggplot(mor_med, aes(x= Island, y=weight, colour = Island, shape = Island)) + 
  geom_boxplot(outlier.shape = NA, lwd = 1) + 
  stat_summary(fun.data = stat_box_data, geom = "text", size = 1) +
  geom_jitter(aes(x=Island, y=weight, colour = Island, shape = Island), alpha = 0.5, size = 0.5) +
  scale_colour_manual(values=c(sa_col, fogo_col, mor_col)) +
  scale_x_discrete(limits = c("Mor", "SA", "Fogo")) +
  scale_shape_manual(values = c(sa_shape, fogo_shape, mor_shape)) +
  stat_compare_means(mapping = aes(x=Island, y=weight, group=Island), data = mor, 
                     comparisons = my_comparisons, method = "wilcox.test", size = 1) +
  xlab('') + ylab("Total seed weight (g)") +
  my_theme
fit_mor

ggsave(plot = fit_mor, width = 4, height = 4, filename = "./fit_mor.pdf", units = "cm")


wilcox.test(mor[mor$Island == "SA", "weight"], mor[mor$Island == "Mor", "weight"])
wilcox.test(mor[mor$Island == "Fogo", "weight"], mor[mor$Island == "Mor", "weight"])
wilcox.test(mor[mor$Island == "SA" | mor$Island == "Fogo", "weight"], mor[mor$Island == "Mor", "weight"])


################################
############ QTLS ##############
################################

####Fig 2D
qtl_subset=read.table("QTLsubset.txt", header = T)
map = read.table("QTLmap.txt", header = T)
band=read.table("QTLbands.txt", header = T)

chromPlot(segment = qtl_subset, noHist = T, bands = band, legChrom = 4, colSegments = grey.colors(7), figCols = 5)



#############################################
############ Model for fitness ##############
#############################################

sa = aggregate(bronson[bronson$Island == "SA", "TotalSeedNumber"], 
               by = bronson[bronson$Island == "SA", c("ID", "CRY2", "MPK12", "FtsZ", "FRI", "ANAC", "FLS2",  "GI", "SeqID")], FUN = median)
head(sa)

# Set up repeated k-fold cross-validation
train.control.more <- trainControl(method = "boot", number = 1000)

# Train the model
step.model <- train(x ~ CRY2 + MPK12 + FtsZ + FRI + ANAC + FLS2 + GI, data = sa,
                    method = "leapSeq", 
                    tuneGrid = data.frame(nvmax = 1:7),
                    trControl = train.control.more
)

step.model$results
#nvmax: the number of variable in the model. For example nvmax = 2, specify the best 2-variables model
#RMSE and MAE are two different metrics measuring the prediction error of each model. The lower the RMSE and MAE, the better the model.
#Rsquared indicates the correlation between the observed outcome values and the values predicted by the model. The higher the R squared, the better the model.
step.model$finalModel
summary(step.model)
step.model$results
summary(step.model$finalModel)
coef(step.model$finalModel, 2)
s0=step.model$results$Rsquared[as.numeric(step.model$bestTune)]


################### PERMUTATIONS
### in terminal 
### need vcftools, plink

# subset VCF file with all CVs to get SAs only
#vcftools --vcf SA.vcf --plink --out SA

# load .ped file
ped = read.table(<load ped file created by vcftools, command above>)
counts = as.data.frame(apply(ped, 2, function(x) length(unique(x))))
counts$drop = ifelse(counts[,1] == '1', 'drop', 'keep')
keep = rownames(counts[counts$drop == 'keep',])
ped_clean= subset(ped, select = colnames(ped) %in% keep)
head(ped_clean)
colnames(ped_clean)[colnames(ped_clean) == 'V1'] <- 'SeqID'

# ATTENTION: this will create 2000 files (!!!) in your working directory
ab=c(1:2000)
for (i in 1:2000){
  random1=cbind(ped_clean$SeqID, sample(ped_clean, 7))
  colnames(random1)[1] <- "SeqID"
  total <- merge(unique(random1),unique(sa[c("SeqID","x")]),by="SeqID")
  train.control.b <- trainControl(method = "boot", number = 1000)
  f1 <- as.formula(paste("x~", paste(names(total)[2:8],collapse="+")))
  step.model <- train(f1, data = total,
                      method = "leapSeq", 
                      tuneGrid = data.frame(nvmax = 1:7),
                      trControl = train.control.b
  )
  x=step.model$results$Rsquared[as.numeric(step.model$bestTune)]
  write.table(x, paste(ab[i], '.txt'), col.names = F, row.names = F)
}


newFileNames2 = list.files(pattern = "*.txt*")
tables <- lapply(newFileNames2, read.table)
head(tables)
str(tables)
combined.df <- do.call("rbind", tables)
head(combined.df)

hist(unlist(combined.df))
abline(v=s0)
pval = (1+sum(unlist(combined.df) >= s0))/(nrow(combined.df)+1)
pval



####################################################
### CVI simulated Experiment: Flowering time  #####
####################################################
### Fig 3A
bolt_cvi=ggplot(all_nocol_med, aes(x= Island, y=BoltingDays, colour = Island, shape = Island)) + 
  geom_boxplot(outlier.shape = NA, lwd = 0.2) + 
  stat_summary(fun.data = stat_box_data, geom = "text", size = 1.5) +
  geom_jitter(aes(x=Island, y=BoltingDays, colour = Island, shape = Island), alpha = 0.5, size = 0.5) +
  xlab('') + ylab("Days to bolting") +
  scale_colour_manual(values=c(sa_col, fogo_col, mor_col)) +
  scale_x_discrete(limits = c("Mor", "SA", "Fogo")) +
  scale_y_continuous(breaks = c(25, 45, 65), limits = c(15, 90)) +
  scale_shape_manual(values = c(sa_shape, fogo_shape, mor_shape)) +
  stat_compare_means(comparisons = my_comparisons,size = 2, label.y = c(69, 85, 76), method = "wilcox.test") +
  my_theme
bolt_cvi

ggsave(filename = "./bolt_cvi.pdf", plot = bolt_cvi, width = 4, height = 4, units = "cm")

# test differences
wilcox.test(all_nocol[!all_nocol$Island == "Mor", "BoltingDays"], all_nocol[all_nocol$Island == "Mor", "BoltingDays"])



#######################################################
############### Phenotype correlations ################
#######################################################

# plot Fig 3B
cor=ggplot(all_nocol_med, aes(x=BoltingDays, y=TotalSeedNumber/1000, colour=Island, shape=Island)) + 
  geom_hline(yintercept = 0, size = 0.2) +
  geom_point(size = 0.5) + 
  xlab('Days to bolting') + ylab(bquote('Seed number ('~x10^3*')')) +
  scale_colour_manual(values=c(sa_col, fogo_col, mor_col)) +
  scale_shape_manual(values = c(sa_shape, fogo_shape, mor_shape)) +
  scale_x_continuous(breaks = c(25, 35, 45, 55, 65)) +
  my_theme
cor

ggsave(filename = "./corr.pdf", plot = cor, width = 8, height = 4, units = "cm")


# correlation fitness - flowering time
cor.test(all_nocol$BoltingDays, all_nocol$TotalSeedNumber,  method = "spearman")

cor.test(all_nocol[all_nocol$Island == "SA", "BoltingDays"], all_nocol[all_nocol$Island == "SA", "TotalSeedNumber"],  method = "spearman")
cor.test(all_nocol[all_nocol$Island == "Fogo", "BoltingDays"], all_nocol[all_nocol$Island == "Fogo", "TotalSeedNumber"],  method = "spearman")
cor.test(all_nocol[all_nocol$Island == "Mor", "BoltingDays"], all_nocol[all_nocol$Island == "Mor", "TotalSeedNumber"],  method = "spearman")


## calculate means and CI
all_nocol_means=aggregate(all_nocol[c("BoltingDays", "TotalSeedNumber")], by = all_nocol[c("Island")], FUN = ci, na.rm = T)
## calculate sd
#all_nocol_sd=aggregate(all_nocol[, c("BoltingDays", "TotalSeedNumber")], by = list(all_nocol$Island), FUN = sd, na.rm = T)
##calculate variance
#all_nocol_var=aggregate(all_nocol[, c("BoltingDays", "TotalSeedNumber")], by = list(all_nocol$Island), FUN = var, na.rm = T)

#plot Fig 3B
mean=ggplot(data=all_nocol_means, aes(x=all_nocol_means$BoltingDays[,1], y=all_nocol_means$TotalSeedNumber[, 1]/100, 
                                      colour = all_nocol_means$Island, shape = all_nocol_means$Island)) + 
  geom_point(size=4) + 
  xlab('DtB') + ylab("SN (x100)") + 
  scale_colour_manual(values=c(sa_col, fogo_col, mor_col))+
  scale_shape_manual(values = c(sa_shape, fogo_shape, mor_shape)) +
  #geom_hline(yintercept = 0) +
  geom_pointrange(aes(ymin = all_nocol_means$TotalSeedNumber[, 2]/100 , ymax = all_nocol_means$TotalSeedNumber[, 3]/100), color= "black", size = 0.2) + 
  geom_errorbarh(aes(xmin = all_nocol_means$BoltingDays[, 2], xmax = all_nocol_means$BoltingDays[, 3]), height = 0, color = "black", size = 0.2) +
  xlim(25, 70) + my_theme +
  scale_y_continuous(expand = c(0, 0), breaks = c(0, 2, 4), limits = c(0, 4.5)) +
  scale_x_continuous(expand = c(0, 0), breaks = c(25, 45, 65), limits = c(20, 70))
mean

ggsave(plot = mean, filename = "./meanPop.pdf", width = 2.5, height = 2, units = "cm")




####################################################
################### F2 pops  #######################
####################################################

# load data for all 3 F2 pops
f2s=read.table(<data in Table S22>, header = T)
head(f2s)
summary(f2s)

#median per parent 
f9=median(f2s[f2s$ind == "F9-2", "bolt"])
cvi=median(f2s[f2s$ind == "CVI-0", "bolt"])
s5=median(f2s[f2s$ind == "S5-10", "bolt"])
f13=median(f2s[f2s$ind == "F13-8", "bolt"])
s15=median(f2s[f2s$ind == "S15-3", "bolt"])
f3=median(f2s[f2s$ind == "F3-2", "bolt"])

# plot 3C
f2=ggplot(f2s) + 
  geom_density(data = f2s[f2s$pop == "F9CVI",], aes(x=bolt, group = pop, fill = pop), alpha = 0.8, position = "identity", size = 0.5) +
  geom_density(data = f2s[f2s$pop == "S5F13",], aes(x=bolt, group = pop, fill = pop), alpha = 0.8, position = "identity", linetype = "dotted", size = 0.5) +
  geom_density(data = f2s[f2s$pop == "S15F3",], aes(x=bolt, group = pop, fill = pop), alpha = 0.8, position = "identity", linetype = "longdash", size = 0.5) +
  scale_fill_manual(values = c("gray40", "gray60", "gray80")) +
  geom_vline(aes(xintercept=cvi, colour = "CVI-0", linetype = "CVI-0"), size = 0.5) +
  geom_vline(aes(xintercept=s5, colour = "S5-10", linetype = "S5-10"), size = 0.5) +
  geom_vline(aes(xintercept=f9, colour = "F9-2", linetype = "F9-2"), size = 0.5) +
  geom_vline(aes(xintercept=f13, colour = "F13-8", linetype = "F13-8"), size = 0.5) +
  geom_vline(aes(xintercept=s15, colour = "S15-3", linetype = "S15-3"), size = 0.5) +
  geom_vline(aes(xintercept=f3, colour = "F3-2", linetype = "F3-2"), size = 0.5) +
  scale_color_manual(name = "Parents", values = c("CVI-0" = sa_col, "S5-10" = sa_col, "F9-2" = fogo_col, "F13-8" = fogo_col, "S15-3" = sa_col, "F3-2" = fogo_col)) +
  scale_linetype_manual(name = "Parents", values = c("CVI-0" = "solid", "S5-10" = "dotted", "F9-2" = "solid", "F13-8" = "dotted", "S15-3" = "longdash", "F3-2" = "longdash"))+
  my_theme +
  xlab("Days to bolting") + ylab("Proportion of F2 individuals")
f2

ggsave(plot = f2, filename = "./f2s.pdf", width = 4, height = 4, units = "cm")

# test transgressive segregation
DunnettTest(list(f2s[f2s$pop == "S5F13" & f2s$who == "F2", 4],f2s[f2s$pop == "S5F13" & f2s$who == "parent", 4]))
DunnettTest(list(f2s[f2s$pop == "S15F3" & f2s$who == "F2", 4],f2s[f2s$pop == "S15F3" & f2s$who == "parent", 4]))
DunnettTest(list(f2s[f2s$pop == "F9CVI" & f2s$who == "F2", 4],f2s[f2s$pop == "F9CVI" & f2s$who == "parent", 4]))

#fisher combined p-value
fisher.method(matrix(c(0.0365, 0.00000000047, 0.00015), ncol = 3))
#p=c(0.0365, 0.00000000047, 0.00015)
#X = -2*(log(0.0365, base = exp(1)) + log(0.00000000047, base = exp(1)) + log(0.00015, base = exp(1)))
#pchisq(X, df=6, lower.tail=FALSE)



##########################################
############ Santo Antao #################
##########################################
### in terminal 
### need vcftools, plink, GEMMA

# create input for GEMMA; this generates .bim, .bed and .fam files
#p-link --file SA --make-bed --out SA

# change .fam file: replace 6th column (-9) on with bolting time median per accession
# match each SeqID to plantID
# order here is important!!

#GEMMA: creates centered relatedness matrix (.cXX.txt extemnsion) in folder "output"
#'/...path.../gemma' -bfile SA -gk 1 -o SA

#GEMMA: association - creates association file (.assoc.txt extension) in folder "output"
#'/...path.../gemma' -bfile SA -k ./output/SA.cXX.txt -lmm 4 -o SA_bolting_CVI

# load data from GEMMA output --- .assoc file
sa_bolt=read.table(<load .assoc file created by GEMMA>, header = T)
head(sa_bolt)

# plot Manhattan
bolt <- sa_bolt %>% 
  
  # Compute chromosome size
  group_by(chr) %>% 
  summarise(chr_len=max(ps)) %>% 
  
  # Calculate cumulative position of each chromosome
  mutate(tot=cumsum(chr_len)-chr_len) %>%
  
  # Add this info to the initial dataset
  left_join(sa_bolt, ., by=c("chr"="chr")) %>%
  
  # Add a cumulative position of each SNP
  arrange(chr, ps) %>%
  mutate(BPcum=ps+tot) 

axisdf = bolt %>% group_by(chr) %>% summarize(end=(max(BPcum)))
chromosomes = c(29920000/2, 
                49110000 -(49110000-29920000)/2, 
                72060000 -(72060000-49110000)/2, 
                90140000-(90140000-72060000)/2, 
                116610000-(116610000-90140000)/2)

# plot Fig 3D
sa_bolting = ggplot(bolt, aes(x=BPcum, y=-log10(p_lrt))) +
  
  # Show all points
  geom_vline(xintercept = c(axisdf$end), colour = "gray") +
  geom_point(data = bolt, aes(x=BPcum, y=-log10(p_lrt), color="black"), alpha=0.8, size = 1) +
  scale_color_manual(values = c(sa_col, sa_col, sa_col, sa_col, sa_col)) +
  geom_hline(yintercept = -log(0.05/length(bolt)), size = 0.5, linetype= 2)+
  
  # custom X axis:
  scale_x_continuous(label = axisdf$chr, breaks= chromosomes, expand = c(0,0) ) +
  scale_y_continuous(expand = c(0, 0), breaks = c(0, 40), limits = c(0, 40)) +     # remove space between plot area and x axis
  
  my_theme+
  xlab("") + ylab(expression(-log[10](P)))
sa_bolting

ggsave(filename = "./manhattan_SA.pdf", plot = sa_bolting, width = 16, height = 3, units = "cm")



###########################################
################## FRI ####################
###########################################

# FRI effect on both phenotypes, mutants included 
#calculate means and CI per pop and allele
ci_fri=aggregate(bronson[, c("BoltingDays", "TotalSeedNumber")], list(bronson$Island, bronson$Pop, bronson$FRI, bronson$FLC), ci, na.rm = T)
colnames(ci_fri) <- c("Island", "Pop", "FRI", "FLC", "BoltingDays", "TotalSeedNumber")
fri_sub=ci_fri[ci_fri$Island == "SA"| ci_fri$Pop == "Col-0" | ci_fri$Pop == "FRI" & !ci_fri$FLC == "Der",]
fri_natpop=all_med[all_med$Island == "SA" & !all_med$FRI == "NA"| all_med$Pop == "Col-0" | all_med$Pop == "FRI" & all_med$FLC == "Anc" & all_med$FRI == "Anc",]

# plot Fig 3E
fri=ggplot() +
  geom_point(data=fri_natpop, aes(x=BoltingDays, y=TotalSeedNumber/100, fill = FRI, shape = Island, colour = FRI), size = 1, alpha = 0.5) +
  geom_point(data = fri_sub, aes(y=TotalSeedNumber[, 1]/100, x=BoltingDays[, 1], shape = fri_sub$Island, fill = fri_sub$FRI), size = 4) +
  scale_shape_manual(values = c(21, 22, 22)) +
  scale_fill_manual(values = c("gray", sa_col, "#00BFFF", sa_col)) + 
  scale_colour_manual(values = c("gray", sa_col, "#00BFFF", sa_col)) + 
  geom_pointrange(data = fri_sub, aes(y=TotalSeedNumber[, 1]/100, ymin = TotalSeedNumber[, 2]/100 , ymax = TotalSeedNumber[, 3]/100,
                                      x=BoltingDays[, 1], xmin = BoltingDays[, 2], xmax = BoltingDays[, 3]), size = 0.1) + 
  geom_errorbarh(data = fri_sub, aes(y=TotalSeedNumber[, 1]/100, ymin = TotalSeedNumber[, 2]/100 , ymax = TotalSeedNumber[, 3]/100,
                                     x=BoltingDays[, 1], xmin = BoltingDays[, 2], xmax = BoltingDays[, 3], height = 0), size = 0.1) +
  
  my_theme + 
  xlab("Days to bolting") + ylab("Seed number (x100)") + ylim(0, 11)
fri

ggsave(filename = "./fri_effect.pdf", plot = fri, width = 4, height = 3.3, units = "cm")


# test of differences
#nat pop
wilcox.test(all_med[all_med$Island == "SA" & all_med$FRI == "Der", "BoltingDays"], all_med[all_med$Island == "SA" & all_med$FRI == "Anc", "BoltingDays"])
mean(all_med[all_med$Island == "SA" & all_med$FRI == "Der", "BoltingDays"]) - mean(all_med[all_med$Island == "SA" & all_med$FRI == "Anc", "BoltingDays"])

wilcox.test(all_med[all_med$Island == "SA" & all_med$FRI == "Der", "TotalSeedNumber"], all_med[all_med$Island == "SA" & all_med$FRI == "Anc", "TotalSeedNumber"])
mean(all_med[all_med$Island == "SA" & all_med$FRI == "Der", "TotalSeedNumber"]) - mean(all_med[all_med$Island == "SA" & all_med$FRI == "Anc", "TotalSeedNumber"])
mean(all_med[all_med$Island == "SA" & all_med$FRI == "Der", "TotalSeedNumber"]) / mean(all_med[all_med$Island == "SA" & all_med$FRI == "Anc", "TotalSeedNumber"])

#mutants and Col-0
wilcox.test(bronson[bronson$Pop == "Col-0", "BoltingDays"], bronson[bronson$Island == "Mutants" & bronson$Pop == "FRI" & bronson$FLC == "Anc", "BoltingDays"], conf.int = T)
mean(bronson[bronson$Pop == "Col-0", "BoltingDays"]) - mean(bronson[bronson$Island == "Mutants" & bronson$Pop == "FRI" & bronson$FLC == "Anc", "BoltingDays"])

wilcox.test(bronson[bronson$Pop == "Col-0", "TotalSeedNumber"], bronson[bronson$Island == "Mutants" & bronson$Pop == "FRI" & bronson$FLC == "Anc", "TotalSeedNumber"])
mean(bronson[bronson$Pop == "Col-0", "TotalSeedNumber"]) - mean(bronson[bronson$Island == "Mutants" & bronson$Pop == "FRI" & bronson$FLC == "Anc", "TotalSeedNumber"])

########### FRI geographical distribution Fig. S11A
world <- map_data("world")
world <- world[world$long > -30 & world$long < -20 & world$lat > 10 & world$lat < 20,]
p <- ggplot(world, aes(long, lat)) +
  geom_map(map=world, aes(map_id=region), fill=NA, color="black") +
  coord_quickmap() + xlim(-25.1,-25.01) + ylim(17.09,17.13) + theme(plot.background = element_rect(fill = "transparent"), axis.text.x=element_blank(), axis.text.y=element_blank(), panel.background = element_rect(fill = "transparent"), panel.grid.minor = element_blank(), panel.grid.major = element_blank(), axis.line.x = element_line(color="black", size = 0.5), axis.line.y = element_line(color="black", size = 0.5)) + xlab("Longitude") + ylab("Latitude")

#load FRI info
g=read.table("FRI_map.txt", header = T)

f = p + geom_scatterpie(aes(x=long-20, y=lat, group=pop, r=radius),
                        data=g, cols=c(4,5, 6, 7),sorted_by_radius = T , alpha=.8) + labs(fill = "FRI alleles") + 
  theme(axis.title.x = element_text(size=16), axis.title.y = element_text(size=16), legend.position = "none") +
  scale_fill_manual(values =c("#0072B2", "#F0E442", "#D55E00", "#CC79A7"))#+
geom_scatterpie_legend(g$radius, x=-25.03, y=17.12, n=4, labeller=function(x) 10*x/0.005)
f



##########################################
################ BSLMM ###################
##########################################
### in terminal 
### need vcftools, plink, GEMMA
### using the same files as for GWAS

#GEMMA: BSLMM will create five files: .bv, .gamma, .hyp, .param, .log
#run 10 times
#'/...path.../gemma' -bfile SA -k ./output/SA.cXX.txt -bslmm 1 -s 10000000 -w 2500000 -o SA_bolting_CVI_bslmm_1

### in R, follow: http://romainvilloutreix.alwaysdata.net/romainvilloutreix/wp-content/uploads/2017/01/gwas_gemma-2017-01-17.pdf



##########################################
################# Fogo ###################
##########################################
### in terminal 
### need vcftools, plink, GEMMA

# subset VCF file with all CVs to get Fogos only
#vcftools --vcf <vcf file released> --keep <text file with IDs from Fogo individuals from Table S1> --recode --out Fogo

# create input for plink; this generates .ped and .map files
#vcftools --vcf Fogo.vcf --plink --out Fogo

# create input for GEMMA; this generates .bim, .bed and .fam files
#p-link --file Fogo --make-bed --out Fogo

# change .fam file: replace 6th column (-9) on with bolting time median per accession
# match each SeqID to plantID
# order here is important!!

#GEMMA: creates centered relatedness matrix (.cXX.txt extemnsion) in folder "output"
#'/...path.../gemma' -bfile Fogo -gk 1 -o Fogo

#GEMMA: association - creates association file (.assoc.txt extension) in folder "output"
#'/...path.../gemma' -bfile Fogo -k ./output/Fogo.cXX.txt -lmm 4 -o Fogo_bolting_CVI


#load data from GEMMA output
fogo_bolt=read.table(<load assoc file created by GEMMA>, header = T)
head(fogo_bolt)

# plot Manhattan
bolt <- fogo_bolt %>% 
  
  # Compute chromosome size
  group_by(chr) %>% 
  summarise(chr_len=max(ps)) %>% 
  
  # Calculate cumulative position of each chromosome
  mutate(tot=cumsum(chr_len)-chr_len) %>%
  
  # Add this info to the initial dataset
  left_join(fogo_bolt, ., by=c("chr"="chr")) %>%
  
  # Add a cumulative position of each SNP
  arrange(chr, ps) %>%
  mutate(BPcum=ps+tot) 

# plot Fig. S12
fogo_bolting = ggplot(bolt, aes(x=BPcum, y=-log10(p_lrt))) +
  
  # Show all points
  geom_vline(xintercept = c(axisdf$end), colour = "gray") +
  geom_point( aes(color=as.factor(chr)), alpha=0.8) +
  scale_color_manual(values = c(fogo_col, fogo_col, fogo_col, fogo_col, fogo_col)) +
  geom_hline(yintercept = -log(0.05/length(bolt)), size = 1, linetype= 2)+
  
  # custom X axis:
  scale_x_continuous(label = axisdf$chr, breaks= chromosomes, expand = c(0,0) ) +
  scale_y_continuous(expand = c(0, 0), breaks = c(0, 7), limits = c(0, 7)) +     # remove space between plot area and x axis
  
  my_theme+
  xlab("") + ylab(expression(-log[10](P)))
fogo_bolting

ggsave(filename = "./manhattan_Fogo.pdf", plot = fogo_bolting, width = 15, height = 5, units = "cm")


##########################################
################# BSA ###################
##########################################

### run script for BSA 

#load data
bsa=read.table(<load text file with values per windows created by BSA script>, header = T)
bsa$Chrom = gsub("chr","",bsa$Chrom) 
head(bsa)

bsa2 <- bsa %>% 
  
  # Compute chromosome size
  group_by(Chrom) %>% 
  summarise(chr_len=max(Window)) %>% 
  
  # Calculate cumulative position of each chromosome
  mutate(tot=cumsum(chr_len)-chr_len) %>%
  
  # Add this info to the initial dataset
  left_join(bsa, ., by=c("Chrom"="Chrom")) %>%
  
  # Add a cumulative position of each SNP
  arrange(Chrom, Window) %>%
  mutate( BPcum=Window+tot)

head(bsa2)
bsa2$sdToPlot_up = bsa2$X21226_Freq_Median + bsa2$X21226_STD
bsa2$sdToPlot_up <- ifelse(bsa2$sdToPlot_up > 1, 1, bsa2$sdToPlot_up)
bsa2$sdToPlot_down = bsa2$X21226_Freq_Median - bsa2$X21226_STD
bsa2$sdToPlot_down <- ifelse(bsa2$sdToPlot_down < 0, 0, bsa2$sdToPlot_down)
head(bsa2)

# plot Fig 3G
bsa_plot = ggplot(bsa2, aes(x=BPcum, y=X21226_Freq_Median)) +
  
  # Show all points
  geom_vline(xintercept = c(axisdf$end), colour = "gray") +
  geom_hline(yintercept = 0.5) +
  geom_ribbon(aes(ymin = sdToPlot_down, ymax = sdToPlot_up), alpha = 0.4, fill = fogo_col) +
  geom_line(aes(color=as.factor(Chrom)), size = 0.5) +
  scale_color_manual(values = c(fogo_col, fogo_col, fogo_col, fogo_col, fogo_col)) +

  # custom X axis:
  scale_x_continuous(label = axisdf$chr, breaks= chromosomes, expand = c(0,0) ) +
  scale_y_continuous(expand = c(0, 0), breaks = c(0,1)) +     # remove space between plot area and x axis
  
  my_theme +
  xlab("Chromosome") + ylab("Allele frequency") 
bsa_plot

ggsave(filename = "./bsa_Fogo.pdf", plot = bsa_plot, width = 7.3, height = 3.3, units = "cm")

#######################################
################## FLC ################
#######################################

#### qPCR
pcr=read.table(<data in Table S29>, header = T)
head(pcr)

# plot Fig S13A
qpcr = ggplot(pcr, aes(x=factor(Accession, level = c('FRI+FLC+', 'FRI+FLC-',"Col-0", "Cvi-0", "S1-1", "S15-3", "S5-10","F10-1-3", "F13-8", "F3-2", "F9-2","Ait-14", "Ait-9", "Arb-0", "Elh-46",  "Set-0", "Set-6")), 
                       y=Ratio, colour = Island)) +
  geom_boxplot(outlier.shape = NA, lwd = 0.5) + geom_jitter(aes(shape = Island)) +
  my_theme +
  ylab(expression(paste(italic("FLC"), "expression relative to FRI+FLC+"))) + xlab("")+
  rotate_x_text(angle = 45) +
  scale_colour_manual(values = c("gray84", "black", "gray44", fogo_col, mor_col, sa_col)) +
  scale_shape_manual(values = c(0, 7, 12, fogo_shape, mor_shape, sa_shape))

ggsave(filename = "./qPCR.pdf", plot = qpcr, width = 13, height = 9, units = "cm")

#### Complementation test
comp=read.table(<data in Table S30>, header = T)
head(comp)
summary(comp)

comps=list(c("F10-1-3", "F1---FRIflcxF10-1-3"), c("F9-2", "F1---FRIflcxF9-2"), c("F3-2", "F1---FRIflcxF3-2"), c("F13-8", "F1---FRIflcxF13-8"),
           c("FRIflc", "F1---FRIflcxF10-1-3"), c("FRIflc", "F1---FRIflcxF9-2"), c("FRIflc", "F1---FRIflcxF3-2"), c("FRIflc", "F1---FRIflcxF13-8"))

comp$cross = paste(comp$Pop, comp$FRI.FLC)
comp$name = comp$Pop
comp$name = ifelse(comp$Accessions == "FRIflc", "FRI+FLC-", comp$name)
comp$name = ifelse(comp$Accessions == "FRIFLC", "FRI+FLC+", comp$name)
comp$name = ifelse(comp$Accessions == "Col-0", "Col-0", comp$name)
comp$name = ifelse(comp$Pop == "Fogo", "Fogo", comp$name)
comp$name = ifelse(comp$Pop == "F1", "F1", comp$name)

comparisons = list(c("FRI+FLC-", "F1"), c("FRI+FLC-", "Fogo"), c("FRI+FLC-", "FRI+FLC+"))

# plot Fig S13B
compl_flc=ggplot(comp[!comp$Accessions == "Col-0",], aes(x=factor(name, level = c('FRI+FLC+', 'FRI+FLC-', 'F1', 'Fogo')), y=bolting, colour = name)) +
  geom_boxplot(outlier.shape = NA, lwd = 0.5) + geom_jitter(aes(shape = Accessions)) +
  my_theme +
  ylab("Days to bolting") + xlab("")+
  rotate_x_text(angle = 45) +
  scale_colour_manual(values = c("burlywood1", "black", "gray44",fogo_col)) +
  scale_shape_manual(values = c(0,1,2,5,15,16,17,18, 7,12))+
  stat_compare_means(mapping = aes(x=name, y=bolting), data = comp[!comp$Accessions == "Col-0",], 
                     method = "wilcox.test", comparisons = comparisons, label.y = c(35, 38, 54), size = 2)
compl_flc

ggsave(filename = "./complementation.pdf", plot = compl_flc, width = 13, height = 9, units = "cm")

wilcox.test(comp[comp$cross == "F1 Anc-Der", "bolting"], comp[comp$cross == "Col-0 Anc-Der", "bolting"])
wilcox.test(comp[comp$cross == "F1 Anc-Der", "bolting"], comp[comp$cross == "Col-0 Anc-Anc", "bolting"])


### effects on phenotypes
# subset Fogo and calculate CI, and means
flc_natpop=all_med[all_med$Island == "Fogo"| all_med$Pop == "FRI",]
flc_sub=ci_fri[ci_fri$Island == "Fogo" | ci_fri$Pop == "FRI",]
#colnames(flc_sub) [1:4] <- c("Island", "Pop", "FRI", "FLC")

# plot Fig 3H
flc=ggplot() +
  geom_point(data=flc_natpop, aes(x=BoltingDays, y=TotalSeedNumber/100, colour = FLC, shape = Island, fill = FLC), size = 1, alpha = 0.5) +
  geom_point(data = flc_sub, aes(y=TotalSeedNumber[, 1]/100, x=BoltingDays[, 1], shape = flc_sub$Island, fill = flc_sub$FLC), size = 4) +
  scale_shape_manual(values = c(24, 22, 22)) +
  scale_colour_manual(values = c("gray", fogo_col, "#FFD700", fogo_col)) + 
  scale_fill_manual(values = c("gray", fogo_col, "#FFD700", fogo_col)) + 
  geom_pointrange(data = flc_sub, aes(y=TotalSeedNumber[, 1]/100, ymin = TotalSeedNumber[, 2]/100 , ymax = TotalSeedNumber[, 3]/100,
                                      x=BoltingDays[, 1], xmin = BoltingDays[, 2], xmax = BoltingDays[, 3], shape = Island), size = 0.1) + 
  geom_errorbarh(data = flc_sub, aes(y=TotalSeedNumber[, 1]/100, ymin = TotalSeedNumber[, 2]/100 , ymax = TotalSeedNumber[, 3]/100,
                                     x=BoltingDays[, 1], xmin = BoltingDays[, 2], xmax = BoltingDays[, 3], shape = Island), height = 0, size = 0.1) +
  my_theme +
  xlab("Days to bolting") + ylab("Seed number (x100)") +
  scale_x_continuous(breaks = c(20, 40, 60))
flc

ggsave(filename = "./flc_effect.pdf", plot = flc, width = 3.8, height = 3.2, units = "cm")

# test of differences
#mutants and Col-0
wilcox.test(bronson[bronson$Pop == "FRI" & bronson$FLC == "Anc", "BoltingDays"], bronson[bronson$Pop == "FRI" & bronson$FLC == "Der", "BoltingDays"])
mean(bronson[bronson$Pop == "FRI" & bronson$FLC == "Anc", "BoltingDays"]) - mean(bronson[bronson$Pop == "FRI" & bronson$FLC == "Der", "BoltingDays"])

wilcox.test(bronson[bronson$Pop == "FRI" & bronson$FLC == "Anc", "TotalSeedNumber"], bronson[bronson$Pop == "FRI" & bronson$FLC == "Der", "TotalSeedNumber"])
