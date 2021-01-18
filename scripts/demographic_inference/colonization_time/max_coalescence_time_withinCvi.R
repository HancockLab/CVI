# Run as:
# Rscript max_coalescence_time_withinCvi.R



###
#           Maximum coalescence time within Cape Verde
###


###
#         Theta pi between santo and the closest moroccan
###
windows=c("100000") #, "500000", "1000000")
centromeres = c(15084050, 3616850, 13590100, 3953300, 11705550)

winSize="100000"

for (winSize in windows) {
  piSfile <- file("./data/piSanto.txt")
  piSFileStr <- strsplit(readLines(piSfile), "\t")
  close(piSfile)
  piS=vector()
  piSnoCentrom=vector()
  positions=vector()
  mu=7*10^-9
  
  for (c in 1:5) {
    jump = 0
    for (w in 1:20) {
      if (as.double(piSFileStr[[c]][length(piSFileStr[[c]]) - w]) == 0.0) {
        jump = jump + 1
      }
    }
    # print(jump)
    # print(as.double(divFileStr[[c]])[1:(length(divFileStr[[c]]) - jump - 1)])
    piS = append(piS, (as.double(piSFileStr[[c]])[1:(length(piSFileStr[[c]]) - jump - 1)])/(2*mu))
    positions = append(positions, (1:(length(piSFileStr[[c]]) - jump - 1))*as.integer(winSize) + thresh[[c]])
    
    # Get Thresholds among chromosomes
    thresh[[c+1]] = thresh[[c]] + chromosomes[[c]]
    
    # Eliminate centromeres
    for (p in (1:(length(piSFileStr[[c]]) - jump - 1))) {
      # print(p)
      if (p*as.integer(winSize) < centromeres[[c]] - 5000000 || p*as.integer(winSize) > centromeres[[c]] + 5000000 ) {
        piSnoCentrom = append(piSnoCentrom, (as.double(piSFileStr[[c]][[p]]))/(2*mu))
      }
    }
  }
  print(range(piS))
  print(range(piSnoCentrom))
  
  
  piFfile <- file("./data/piFogo.txt")
  piFFileStr <- strsplit(readLines(piFfile), "\t")
  close(piFfile)
  piF=vector()
  piFnoCentrom=vector()
  positions=vector()
  mu=7*10^-9
  
  for (c in 1:5) {
    jump = 0
    for (w in 1:20) {
      if (as.double(piFFileStr[[c]][length(piFFileStr[[c]]) - w]) == 0.0) {
        jump = jump + 1
      }
    }
    # print(jump)
    # print(as.double(divFileStr[[c]])[1:(length(divFileStr[[c]]) - jump - 1)])
    piF = append(piF, (as.double(piFFileStr[[c]])[1:(length(piFFileStr[[c]]) - jump - 1)])/(2*mu))
    positions = append(positions, (1:(length(piFFileStr[[c]]) - jump - 1))*as.integer(winSize) + thresh[[c]])
    
    # Get Thresholds among chromosomes
    thresh[[c+1]] = thresh[[c]] + chromosomes[[c]]
    
    # Eliminate centromeres
    for (p in (1:(length(piFFileStr[[c]]) - jump - 1))) {
      # print(p)
      if (p*as.integer(winSize) < centromeres[[c]] - 5000000 || p*as.integer(winSize) > centromeres[[c]] + 5000000 ) {
        piFnoCentrom = append(piFnoCentrom, (as.double(piFFileStr[[c]][[p]]))/(2*mu))
      }
    }
  }
  print(range(piF))
  print(range(piFnoCentrom))
  
  
  divfile <- file("./data/piBtwSaFo.txt")
  divFileStr <- strsplit(readLines(divfile), "\t")
  close(divfile)
  div=vector()
  divnoCentrom=vector()
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
    # positions = append(positions, (1:(length(piFFileStr[[c]]) - jump - 1))*as.integer(winSize) + thresh[[c]])
    
    # Get Thresholds among chromosomes
    thresh[[c+1]] = thresh[[c]] + chromosomes[[c]]
    
    # Eliminate centromeres
    for (p in (1:(length(divFileStr[[c]]) - jump - 1))) {
      # print(p)
      if (p*as.integer(winSize) < centromeres[[c]] - 5000000 || p*as.integer(winSize) > centromeres[[c]] + 5000000 ) {
        divnoCentrom = append(divnoCentrom, (as.double(divFileStr[[c]][[p]]))/(2*mu))
      }
    }
  }
  
  
  
  # Hist
  # Eliminate centromeres!
  
  pdf("./figures/edf4c.pdf", height=2.5,width=3.5)
  par(ps=7, mfrow=c(1,1), mar=c(2.,2.,0.5,0.)) # , mgp=c(3, -0.2, 3))
  # par(mar=c(5,5,0.5,3))
  x <- seq(0, max(c(na.omit(piSnoCentrom)+1000, na.omit(piFnoCentrom)+1000, na.omit(divnoCentrom)+1000)), 250)
  maxy=0
  trick=hist(as.double(c(as.double(na.omit(piSnoCentrom)))), breaks = x, plot=F)
  if (max(trick$counts) > maxy) {
    maxy = max(trick$counts)
  }
  trick=hist(as.double(c(as.double(na.omit(piFnoCentrom)))), breaks = x, plot=F)
  if (max(trick$counts) > maxy) {
    maxy = max(trick$counts)
  }
  trick=hist(as.double(c(as.double(na.omit(divnoCentrom)))), breaks = x, plot=F)
  if (max(trick$counts) > maxy) {
    maxy = max(trick$counts)
  }
  
  colS = rgb(0/255, 117/255, 220/255, 0.8)
  colF = rgb(255/255, 164/255, 5/255, 0.8)
  colB = rgb(0, 92/255, 49/255, 0.8)
  
  hist(na.omit(piSnoCentrom), breaks = x, col = colS, ylim=c(0.0, maxy), xlim=c(0.0, max(divnoCentrom)+800), main=c(""), xlab=NULL, ylab=NULL, axes=F)
  hist(na.omit(piFnoCentrom), breaks = x, col = colF, axes=F, add=T)
  hist(na.omit(divnoCentrom), breaks = x, col = colB, axes=F, add=T)
  
  quantNinetFive=quantile(c(piSnoCentrom, piFnoCentrom, divnoCentrom), probs=c(0.95))
  print(paste("Attention! 95: ", quantNinetFive))
  
  axis(1, at=c(0, 15000), tick=T, labels=c("", ""), las=1, line=-0.2, lwd.ticks=-1)
  axis(1, at=seq(0, 15000, 5000), tick=F, labels=seq(0, 15, 5), las=1, line=-1.2, lwd.ticks=-1)
  title("", xlab=expression(paste("Time (", "10"^"3", " years ago)")), line=0.7)
  
  # For paper figure
  maxy = 90     # comment out For all windows sizes
  axis(2, at=c(0, maxy), labels=c("", ""), tick=T, las=1, line=-0.5, lwd.ticks=-1)
  axis(2, at=seq(0, maxy, maxy/3), labels=as.integer(seq(0, maxy, maxy/3)), tick=F, las=1, line=-1.2, lwd.ticks=-1)
  title("", ylab=expression("Number of windows"), line=0.6)
  
  
  dev.off()
  
  print(paste("noCentrom", max(c(na.omit(piSnoCentrom), na.omit(piFnoCentrom), na.omit(divnoCentrom))), sep=" "))
}  

quantile(c(piFnoCentrom), probs=c(0.95))
median(piFnoCentrom)
mean(piFnoCentrom)



