###
#	Inference of the colonization time
###

We narrowed down colonization time by obtaining an upper bound based on the minimum coalescence time between CVI and Morocco, and a lower bound based on the maximum coalescence time within the CVI clade. 

###

## Folders

### msprime 

We ran coalescence simulations in msprime v.0.4.0 with a split at different times (5-50 kya) between a Morocco-like and CVI-like populations. To account for the confounding effect of purifying selection which reduces the rate of introduction of new mutations locally in the genome, we scaled mutation rate across simulated genomic windows as μscaled=𝛳local/4Ne, where 𝛳local was estimated as 𝛳𝜋 in the Moroccan population within each window and Ne was fixed to the genome-wide average (Ne=𝛳genome/4μ) so that μscaled=μ(𝛳local/𝛳genome). Then we inferred coalescence times between simulated and observed CVI and Moroccan genomes across genomic windows based on the density of mutations. We obtained 95% confidence intervals based on the standard error (SE) estimated with ordinary non-parametric bootstrap for observed data and on the SE across simulations. By fitting the simulated cumulative proportion of genomic windows with different inferred ages to observed data, we obtain a conservative estimate of the upper bound of colonization time. 

### max_coalescence_time_withinCvi.R	

We inferred coalescence times within Cape Verde, across genomic windows (0.1 Mbp, non-overlapping), based on the density of mutations and used the 95th percentile as a lower bound for colonization time. 


