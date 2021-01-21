options(scipen=5)
mu <- 7e-9
gen <- 1

HAxNMA<-read.table("results/HAxNMA.combined.txt", header=TRUE)

# set up the plot area

plot(HAxNMA$left_time_boundary/mu*gen, 2 * HAxNMA$lambda_01 / (HAxNMA$lambda_00 + HAxNMA$lambda_11), xlim=c(10000,1000000),ylim=c(0,1), type="n", xlab="Time in years; mu=7e-9, g=1", ylab="Relative Cross-Coalescence Rate")#, log="x")

path = "combined"

file.names <- dir(path, pattern =".txt")
#colors <- rainbow(1:length(file.names))

for(i in 1:length(file.names)){
  file.in <- paste(path,file.names[i],sep="/")
  file <- read.table(file.in, header=TRUE)
  lines(file$left_time_boundary/mu*gen, 2 * file$lambda_01 / (file$lambda_00 + file$lambda_11), type="s")#, col=colors[i])
}