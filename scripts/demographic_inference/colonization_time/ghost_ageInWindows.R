# Run as:
# Rscript ghost_ageInWindows.R


###
#       Here for paper figure on Colonization t 10 kya
#       and windows age within CVI
###




######
### 
#           Now with windows scaled to diversity in HA
#           Simple split and mass migration ghost into HA
#           GREAT STUFF HERE!  
### 
######
pdf("./figures/edf3c.pdf", height=3,width=3.)
par(ps=7, mfrow=c(1,1), mar=c(2.,2.5,0.5,0.)) # , mgp=c(3, -0.2, 3))
plot(x=0, y=0, type="n", xlab=NULL, ylab=NULL, xlim=c(10000, 50000), axes=F, ylim=c(0.0, 0.26))

axis(1, at=c(10000, 50000), tick=T, labels=c("", ""), las=1, line=-0.2, lwd.ticks=-1)
axis(1, at=seq(10000, 50000, 10000), tick=F, labels=c("<10", "<20", "<30", "<40", "<50"), las=1, line=-1.2, lwd.ticks=-1)
title("", xlab=expression(paste("Window age (", "10"^"3", " years ago)")), line=0.7)

axis(2, at=c(0, 0.25), labels=c("", ""), tick=T, las=1, line=-0.2, lwd.ticks=-1)
axis(2, at=seq(0.0, 0.20, 0.10), labels=seq(0.0, 0.2, 0.1), tick=F, las=1, line=-0.8, lwd.ticks=-1)
title("", ylab=expression('Cumulative proportion of windows'), line=1.2)

winAge=seq(50000, 10000, -10000)


#install.packages("viridis")
library(viridis)
mainCol=viridis_pal(alpha = 1, begin = 0, end = 1, direction = 1, option = "inferno")(15)[c(6, 7, 8, 10, 12, 14)]
shadeCol=viridis_pal(alpha = 0.5, begin = 0, end = 1, direction = 1, option = "inferno")(15)[c(6, 7, 8, 10, 12, 14)]


winAgeK=c(seq(50, 10, -10), 5)
for (s in 1:length(winAgeK)) {
  simAge = winAgeK[[s]]
  sim <- read.table(paste("./results/", simAge, "k.txt", sep=""), header=F)
  sim=as.data.frame(sim[,1:5]/sim[,6])
  colnames(sim) = c("50k", "40k", "30k", "20k", "10k")
  str(sim)
  
  meanSim=apply(sim, 2, mean)
  sdSim=apply(sim, 2, sd)
  countSim=apply(sim, 2, function(x) length(x[!is.na(x)]))
  errSim = (1.96*sdSim)/(sqrt(countSim))
  
  #Plot data points
  points(winAge, as.double(meanSim), col=mainCol[[s]])
  arrowY.start <- as.double(meanSim + errSim)
  arrowY.end <- as.double(meanSim - errSim)
  arrows(winAge, arrowY.start, winAge, arrowY.end, angle=90, code=3, length=0.03, col=mainCol[[s]]) 
  
  func = splinefun(x=winAge, y=as.double(meanSim), method="fmm",  ties = mean)
  func2 = splinefun(x=winAge, y=as.double(meanSim + errSim), method="fmm",  ties = mean)
  func3 = splinefun(x=winAge, y=as.double(meanSim - errSim), method="fmm",  ties = mean)
  
  # Plot the spline
  x=seq(50000, 10000, -1000)
  polygon(c(x, rev(x)), c(func2(x), rev(func3(x))), col =shadeCol[[s]] , border = NA)
  lines(x=x, y=func2(x), col=mainCol[[s]], lwd=1)
  lines(x=x, y=func3(x), col=mainCol[[s]], lwd=1)
  # print(max(as.double(meanSim + errSim)))
}

# Observed, obtained from
# 
# bootstrapping with 1000 replications 
# results <- boot(data=divMinRawNoCentrom, statistic=winYoungAge, R=1000)
# plot(results)
# get 95% confidence interval 
# boot.ci(results, type="bca")

observed_scaledToPi=c(267, 210, 149, 79, 18)
obs = observed_scaledToPi/1429
seObs= c(0.010391707, 0.009875042, 0.008364101, 0.005956213, 0.002909825)

#Plot data points
points(winAge, as.double(obs), col=rainbow(8)[[8]])
arrowY.start <- as.double(obs + seObs)
arrowY.end <- as.double(obs - seObs)
arrows(winAge, arrowY.start, winAge, arrowY.end, angle=90, code=3, length=0.03, col=rainbow(8)[[8]])

func = splinefun(x=winAge, y=as.double(obs), method="fmm",  ties = mean)
func2 = splinefun(x=winAge, y=as.double(obs + seObs), method="fmm",  ties = mean)
func3 = splinefun(x=winAge, y=as.double(obs - seObs), method="fmm",  ties = mean)

x=seq(50000, 10000, -1000)
lines(x=x, y=func(x), col=rgb(0, 0, 0), lwd=1)
polygon(c(x, rev(x)), c(func2(x), rev(func3(x))), col =rgb(0, 0, 0, 0.5) , border = NA)
lines(x=x, y=func2(x), col=rgb(0, 0, 0), lwd=1)
lines(x=x, y=func3(x), col=rgb(0, 0, 0), lwd=1)


legend("topleft", legend =c(expression('Observed'), expression('T'[split]*' = 5 kya'), expression('T'[split]*' = 10 kya'), expression('T'[split]*' = 20 kya'), expression('T'[split]*' = 30 kya'), expression('T'[split]*' = 40 kya'), expression('T'[split]*' = 50 kya')),
       col = c(rgb(0, 0, 0), shadeCol[6:1]), lty=1, lwd=3, cex = 1, bty="n", ncol=1, y.intersp=0.6)		#border= c(eur.col, afr.col)

# Code up above! :
#
#ORDINARY NONPARAMETRIC BOOTSTRAP
#Call:
#  boot(data = divMinRawNoCentrom, statistic = winYoungAge, R = 1000)
#Bootstrap Statistics :
#  original        bias    std. error
# t1* 0.18684395 -1.469559e-05 0.010391707
# t2* 0.14695591 -2.715185e-04 0.009875042
# t3* 0.10426872 -5.115465e-04 0.008364101
# t4* 0.05528341 -2.246326e-04 0.005956213
# t5* 0.01259622  8.327502e-05 0.002909825



dev.off()



###
###
#
#	Here, to get observed values: minimum divergence in windows
#	Scaled to diversity in Morocco
#	Bootstrapped for CI
#
###
###


winSize = "50000"
for (winSize in windows) {
  divFile <- file(paste("./data/", winSize, "/minPiBtwCviMor.txt", sep=""))
  divFileStr <- strsplit(readLines(divFile), "\t")
  close(divFile)
  div=vector()
  divMinRaw = vector()
  divMinRawNoCentrom = vector()
  positions=vector()
  mu=7*10^-9
  
  for (c in 1:5) {
    jump = 0
    for (w in 1:20) {
      if (as.double(divFileStr[[c]][length(divFileStr[[c]]) - w]) == 0.0) {
        jump = jump + 1
      }
    }
    # print(jump)
    # print(as.double(divFileStr[[c]])[1:(length(divFileStr[[c]]) - jump - 1)])
    div = append(div, (as.double(divFileStr[[c]])[1:(length(divFileStr[[c]]) - jump - 1)])/(2*mu))
    divMinRaw = append(divMinRaw, (as.double(divFileStr[[c]])[1:(length(divFileStr[[c]]) - jump - 1)]))
    positions = append(positions, (1:(length(divFileStr[[c]]) - jump - 1))*as.integer(winSize) + thresh[[c]])
    
    # Get Thresholds among chromosomes
    thresh[[c+1]] = thresh[[c]] + chromosomes[[c]]
    
    # Eliminate centromeres
    for (p in (1:(length(divFileStr[[c]]) - jump - 1))) {
      # print(p)
      if (p*as.integer(winSize) < centromeres[[c]] - 5000000 || p*as.integer(winSize) > centromeres[[c]] + 5000000 ) {
        divMinRawNoCentrom = append(divMinRawNoCentrom, (as.double(divFileStr[[c]][[p]])) )
      }
    }
  }
  mean(divMinRaw)
  print(range(div))
  print(range(divMinRaw))
  print(range(na.omit(divMinRawNoCentrom)))
  
  
  
  
  
  piFile <- file(paste("./data/", winSize, "/piMorocco.txt", sep=""))
  piFileStr <- strsplit(readLines(piFile), "\t")
  close(piFile)
  piM=vector()
  piMRaw = vector()
  piMRawNoCentrom = vector()
  
  for (c in 1:5) {
    jump = 0
    for (w in 1:20) {
      if (as.double(piFileStr[[c]][length(piFileStr[[c]]) - w]) == 0.0) {
        jump = jump + 1
      }
    }
    # print(jump)
    # print(as.double(divFileStr[[c]])[1:(length(divFileStr[[c]]) - jump - 1)])
    piM = append(piM, (as.double(piFileStr[[c]])[1:(length(piFileStr[[c]]) - jump - 1)])/(2*mu))
    piMRaw = append(piMRaw, (as.double(piFileStr[[c]])[1:(length(piFileStr[[c]]) - jump - 1)]))
    
    # Eliminate centromeres
    for (p in (1:(length(piFileStr[[c]]) - jump - 1))) {
      # print(p)
      if (p*as.integer(winSize) < centromeres[[c]] - 5000000 || p*as.integer(winSize) > centromeres[[c]] + 5000000 ) {
        piMRawNoCentrom = append(piMRawNoCentrom, (as.double(piFileStr[[c]][[p]])) )
      }
    }
  }
  
  ###
  #         To scale simulations
  ###
  # theta = 4 Ne mu
  ne = (mean(na.omit(piMRawNoCentrom))) / (4*mu)
  ne = (median(na.omit(piMRawNoCentrom))) / (4*mu)
  simulaMut = (na.omit(piMRawNoCentrom)) / (4*ne)
  
  
  # Eliminate missing data in either
  for (p in 1:length(piMRawNoCentrom)) {
    if (is.na(piMRawNoCentrom[[p]]) ) {
      divMinRawNoCentrom[[p]] = NA
    }
  }
  youngW=c(0, 0, 0, 0, 0)
  allW=0
  for (p in 1:length(divMinRawNoCentrom)) {
    if (is.na(divMinRawNoCentrom[[p]]) ) {
      piMRawNoCentrom[[p]] = NA
    } else {
      # 50 k
      if (divMinRawNoCentrom[[p]] < 7.1e-04) {
        youngW[[1]] = youngW[[1]] + 1
      }
      # 40 k
      if (divMinRawNoCentrom[[p]] < 0.00056) {
        youngW[[2]] = youngW[[2]] + 1
      }
      # 30 k
      if (divMinRawNoCentrom[[p]] < 0.00042) {
        youngW[[3]] = youngW[[3]] + 1
      }
      # 20 k
      if (divMinRawNoCentrom[[p]] < 0.00028) {
        youngW[[4]] = youngW[[4]] + 1
      }
      # 10 k
      if (divMinRawNoCentrom[[p]] < 0.00014) {
        youngW[[5]] = youngW[[5]] + 1
      }
      allW = allW + 1
    }
  }
  youngW # 267 210 149  79  18
  allW   # 1429
  (youngW/allW)*2380
  
  
  
  ###
  #         Now bootstrap windows for errors
  ###
  
  library(boot)
  winYoungAge <- function(divMinRawNoCentrom, indices) {
    winAgeBoot = divMinRawNoCentrom[indices]
    # Count windows younger than so and so
    youngW=c(0, 0, 0, 0, 0)
    allW = 0
    # Across subsampled windows
    for (p in 1:length(winAgeBoot)) {
      if (!is.na(winAgeBoot[[p]]) ) {
        # 50 k
        if (winAgeBoot[[p]] < 7.1e-04) {
          youngW[[1]] = youngW[[1]] + 1
        }
        # 40 k
        if (winAgeBoot[[p]] < 0.00056) {
          youngW[[2]] = youngW[[2]] + 1
        }
        # 30 k
        if (winAgeBoot[[p]] < 0.00042) {
          youngW[[3]] = youngW[[3]] + 1
        }
        # 20 k
        if (winAgeBoot[[p]] < 0.00028) {
          youngW[[4]] = youngW[[4]] + 1
        }
        # 10 k
        if (winAgeBoot[[p]] < 0.00014) {
          youngW[[5]] = youngW[[5]] + 1
        }
        allW = allW + 1
      }
    }
    return(youngW/allW)
  } 
  
  # Scale mean divergence
  divScaledMean = divMean/piM
  # Scale minimum divergence
  divScaledMin = div/piM
  
  
  
  ###
  #         Now, age of div minus age of Mor
  ###
  
  ageWin = divMean - median(na.omit(piM))
  
  # bootstrapping with 1000 replications 
  results <- boot(data=divMinRawNoCentrom, statistic=winYoungAge, R=1000)
  # get 95% confidence interval 
  boot.ci(results, type="bca")
}
results




