#!/usr/bin/env python
# coding: utf-8


###
#    Plot main figure for  dN/dS
###

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns

plt.rcParams['font.family'] = 'sans-serif'
plt.rcParams['font.sans-serif'] = 'Helvetica'
plt.rcParams.update({'font.size': 7})



df=pd.read_csv("./data/sa_bootDnDs.txt",
           # delim_whitespace=True,
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')
group=['sa' for i in range(df.shape[0])]
order=[0
       for i in range(df.shape[0])]
df['Group'] = group 
df['Order'] = order


# With group names:
df_easy=pd.read_csv("./data/sa_bootDnDs.txt",
           # delim_whitespace=True,
           header=None,
           names=['sa'],
           skipinitialspace=False,
           sep='\t')
# print(df_easy)



###
# Now append all others

file_list=["./data/fo_bootDnDs.txt",
           "./data/mor-ha_bootDnDs.txt", "./data/mor-sma_bootDnDs.txt", "./data/mor-nma_bootDnDs.txt", "./data/mor-rif_bootDnDs.txt"]

order_list=[1, 
             2, 3, 4, 5, 6, 7, 9, 8, 10, 11 , 12, 13, 14, 15]



for file in range(len(file_list)):
    # print(file_list[file])
    name=file_list[file].split("/")[len(file_list[file].split("/"))-1].split("_")[0]
    df_2=pd.read_csv(file_list[file],
        header=None,
        names=['dnds'], # [name],
        skipinitialspace=False,
        sep='\t')
    print(name)
    group=[name for i in range(df_2.shape[0])]
    order=[order_list[file] for i in range(df_2.shape[0])]
    
    df_easy = pd.concat([df_easy, df_2], axis=1, sort=False)
    df_easy.rename(columns={"dnds": name})
    
    df_2['Group'] = group
    df_2['Order'] = order
    
    df=df.append(df_2)
    
# print(df_easy)



#
# print(df)


cvi_df  = df[(df['Group'] == 'sa') | (df['Group'] == 'fo')]
nonCvi_df  = df[(df['Group'] != 'sa') & (df['Group'] != 'fo')]

# print(nonCvi_df)












file_list=[#"./data/cviLongBranch_bootDnDs.txt", 
           "./data/sa_bootDnDs_2.txt",
           "./data/fo_bootDnDs_2.txt"]
           # "./data/eurasia_bootDnDs.txt"]


# With Mor and Euras grouped:
df_grouped=pd.read_csv("./data/sa_bootDnDs_2.txt",
           # delim_whitespace=True,
           header=None,
           names=['sa'],
           skipinitialspace=False,
           sep='\t')

df_grouped_forSeaborn=pd.read_csv("./data/mor-all_bootDnDs.txt",
           # delim_whitespace=True,
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')


df_grouped_forSeaborn_halfSwarm=pd.read_csv("./data/sa_bootDnDs_2.txt",
           # delim_whitespace=True,
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')


group=['mor' for i in range(df_grouped_forSeaborn.shape[0])]
order=[order_list[file] for i in range(df_grouped_forSeaborn.shape[0])]
df_grouped_forSeaborn['Group'] = group
df_grouped_forSeaborn['Order'] = order
df_grouped_forSeaborn_halfSwarm['Group'] = ['sa' for i in range(df_grouped_forSeaborn_halfSwarm.shape[0])]
df_grouped_forSeaborn_halfSwarm['Order'] = [3 for i in range(df_grouped_forSeaborn_halfSwarm.shape[0])]



# file_list=["./data/fo_bootDnDs_2.txt"]

for file in range(len(file_list)):
    # print(file_list[file])
    name=file_list[file].split("/")[len(file_list[file].split("/"))-1].split("_")[0]
    df_2=pd.read_csv(file_list[file],
        header=None,
        names=['dnds'], # [name],
        skipinitialspace=False,
        sep='\t')
    # print(name)
    group=[name for i in range(df_2.shape[0])]
    order=[4 for i in range(df_2.shape[0])]
    
    df_2['Group'] = group
    df_2['Order'] = order
    
    df_grouped_forSeaborn_halfSwarm=df_grouped_forSeaborn_halfSwarm.append(df_2)




print("df_grouped_forSeaborn_halfSwarm")
print(df_grouped_forSeaborn_halfSwarm)




for file in range(len(file_list)):
    # print(file_list[file])
    name=file_list[file].split("/")[len(file_list[file].split("/"))-1].split("_")[0]
    df_2=pd.read_csv(file_list[file],
        header=None,
        names=['dnds'], # [name],
        skipinitialspace=False,
        sep='\t')
    # print(name)
    group=[name for i in range(df_2.shape[0])]
    order=[order_list[file] for i in range(df_2.shape[0])]
    
    df_2['Group'] = group
    df_2['Order'] = order
    
    df_grouped_forSeaborn=df_grouped_forSeaborn.append(df_2)



df_grouped_forSeaborn_2 = df_grouped_forSeaborn
df_grouped_forSeaborn_2['dnds'] = np.log10(df_grouped_forSeaborn_2['dnds'])









###
#    FINAL: the main figure here
#    
###

plt.clf()
plt.figure(figsize=(18,6))
sns.set_style('white')

collors_light=[(240/255,163/255,255/255, 0.3), (43/255,206/255,72/255, 0.3), (255/255,164/255,5/255, 0.3), (0/255,92/255,49/255, 0.3)]
collors_full=[(240/255,163/255,255/255, 0.9), (43/255,206/255,72/255, 0.9), (255/255,164/255,5/255, 0.9), (0/255,92/255,49/255, 0.9)]
collors_total=[(240/255,163/255,255/255, 1.), (43/255,206/255,72/255, 1.), (255/255,164/255,5/255, 1.), (0/255,92/255,49/255, 1.)]

collors_light=[(43/255,206/255,72/255, 0.3), (0,117/255,220/255, 0.3), (255/255,164/255,5/255, 0.3)]
collors_full=[(43/255,206/255,72/255, 0.9), (0,117/255,220/255, 0.9), (255/255,164/255,5/255, 0.9)]
collors_total=[(43/255,206/255,72/255, 1.), (0,117/255,220/255, 1.), (255/255,164/255,5/255, 1.)]

fig=sns.boxplot(x=df_grouped_forSeaborn_2['Group'], y=df_grouped_forSeaborn_2['dnds'], data=df_grouped_forSeaborn_2, palette=collors_full, showfliers=False, linewidth=0.1) # palette='colorblind'



print(len(fig.lines))
for i,artist in enumerate(fig.artists):
    # Set the linecolor on the artist to the facecolor, and set the facecolor to None
    col = artist.get_facecolor()
    artist.set_edgecolor(col)
    artist.set_facecolor(collors_light[i])

    # Each box has 6 associated Line2D objects (to make the whiskers, fliers, etc.)
    # Loop over them here, and use the same colour as above
    for j in range(i*5,i*5+5):
        line = fig.lines[j]
        line.set_color(collors_full[i])
        line.set_mfc(collors_full[i])
        line.set_mec(collors_full[i])
        
print("checked")




dfCvi=df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"].isin(['sa', 'fo'])]
fig=sns.swarmplot(x=dfCvi['Group'], y=dfCvi['dnds'], data=dfCvi, color="k", size = 2.)



c0 = fig.get_children()[0]
x,y = np.array(c0.get_offsets()).T
xnew=x+1
offsets = list(zip(xnew,y))
c0.set_offsets(offsets)


c1 = fig.get_children()[1]
x,y = np.array(c1.get_offsets()).T
xnew=x+1
offsets = list(zip(xnew,y))
c1.set_offsets(offsets)



###
#         Now plot on top the actual observed values
###

##
#      Read the actual observed values
##
df_obs=pd.read_csv("./data/morocco-mean_bootDnDs_actualData.txt",
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')
group=['dnds']
order=[1]
df_obs['Group'] = group 
df_obs['Order'] = order

file_list=[
    "./data/santo_bootDnDs_actualData.txt",
    "./data/fogo_bootDnDs_actualData.txt"]
    
order_list=[2, 3, 4]

for file in range(len(file_list)):
    name=file_list[file].split("/")[len(file_list[file].split("/"))-1].split("_")[0]
    df_obs2=pd.read_csv(file_list[file],
        header=None,
        names=['dnds'], # [name],
        skipinitialspace=False,
        sep='\t')
    df_obs2['Group'] = 'dnds'
    df_obs2['Order'] = order_list[file]
    
    df_obs=df_obs.append(df_obs2)
    
print(df_obs)

#     Now plot
df_obs_new = pd.DataFrame(dict(xx=df_obs['Order'], yy=np.log10(df_obs['dnds'])))
fig=sns.swarmplot(x='xx', y='yy', data=df_obs_new, size = 5, palette=collors_total, marker="D", edgecolor="black", linewidth=0.5)

###
#
###


# With xlabels
fig.set(title='')
fig.set(xlabel=None)

# X axis
# 
ordered=['Mor', 'SA', 'Fogo']
fig.set_xticklabels(ordered)

# Y axis
# 
fig.set_ylabel("dN/dS")


fig.set_ylim(np.log10([0.15,10.5]))
fig.set_yticks([np.log10(0.2), np.log10(0.5), np.log10(1.), np.log10(2.), np.log10(10.)])
fig.set_yticklabels(['0.2', '0.5', '1.0', '2.0','10.0'])

fig = plt.gcf()
fig.set_size_inches(2.3, 1.7)

plt.savefig("./figures/fig2a.png", format="png",bbox_inches="tight", dpi=900)
























###
#    Supp figure, mor and CVI long branch
###

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns

plt.rcParams['font.family'] = 'sans-serif'
plt.rcParams['font.sans-serif'] = 'Helvetica'
plt.rcParams.update({'font.size': 7})



df=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')
group=['sa' for i in range(df.shape[0])]
order=[0
       for i in range(df.shape[0])]
df['Group'] = group 
df['Order'] = order


# With group names:
df_easy=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
           # delim_whitespace=True,
           header=None,
           names=['sa'],
           skipinitialspace=False,
           sep='\t')



###
# Now append all others

file_list=[
        "./data/mor-ha_bootDnDs.txt", "./data/mor-sma_bootDnDs.txt", "./data/mor-nma_bootDnDs.txt", "./data/mor-rif_bootDnDs.txt", 
          ]

order_list=[1, 
             2, 3, 4, 5, 6, 7, 9, 8, 10, 11 , 12, 13, 14, 15]


for file in range(len(file_list)):
    name=file_list[file].split("/")[len(file_list[file].split("/"))-1].split("_")[0]
    df_2=pd.read_csv(file_list[file],
        header=None,
        names=['dnds'], # [name],
        skipinitialspace=False,
        sep='\t')
    print(name)
    group=[name for i in range(df_2.shape[0])]
    order=[order_list[file] for i in range(df_2.shape[0])]
    
    df_easy = pd.concat([df_easy, df_2], axis=1, sort=False)
    df_easy.rename(columns={"dnds": name})
    
    df_2['Group'] = group
    df_2['Order'] = order
    
    df=df.append(df_2)




cvi_df  = df[(df['Group'] == 'sa') | (df['Group'] == 'fo')]
nonCvi_df  = df[(df['Group'] != 'sa') & (df['Group'] != 'fo')]


# With Mor and Euras grouped:
df_grouped=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
           header=None,
           names=['sa'],
           skipinitialspace=False,
           sep='\t')

df_grouped_forSeaborn=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')


df_grouped_forSeaborn_halfSwarm=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')


group=['mor' for i in range(df_grouped_forSeaborn.shape[0])]
order=[order_list[file] for i in range(df_grouped_forSeaborn.shape[0])]
df_grouped_forSeaborn['Group'] = group
df_grouped_forSeaborn['Order'] = order
df_grouped_forSeaborn_halfSwarm['Group'] = ['sa' for i in range(df_grouped_forSeaborn_halfSwarm.shape[0])]
df_grouped_forSeaborn_halfSwarm['Order'] = [3 for i in range(df_grouped_forSeaborn_halfSwarm.shape[0])]




for file in range(len(file_list)):
    name=file_list[file].split("/")[len(file_list[file].split("/"))-1].split("_")[0]
    df_2=pd.read_csv(file_list[file],
        header=None,
        names=['dnds'], # [name],
        skipinitialspace=False,
        sep='\t')
    group=[name for i in range(df_2.shape[0])]
    order=[4 for i in range(df_2.shape[0])]
    
    df_2['Group'] = group
    df_2['Order'] = order
    
    df_grouped_forSeaborn_halfSwarm=df_grouped_forSeaborn_halfSwarm.append(df_2)




print("df_grouped_forSeaborn_halfSwarm")
print(df_grouped_forSeaborn_halfSwarm)




for file in range(len(file_list)):
    name=file_list[file].split("/")[len(file_list[file].split("/"))-1].split("_")[0]
    df_2=pd.read_csv(file_list[file],
        header=None,
        names=['dnds'], # [name],
        skipinitialspace=False,
        sep='\t')
    group=[name for i in range(df_2.shape[0])]
    order=[order_list[file] for i in range(df_2.shape[0])]
    
    df_2['Group'] = group
    df_2['Order'] = order
    
    df_grouped_forSeaborn=df_grouped_forSeaborn.append(df_2)



df_grouped_forSeaborn_2 = df_grouped_forSeaborn
df_grouped_forSeaborn_2['dnds'] = np.log10(df_grouped_forSeaborn_2['dnds'])









plt.clf()
plt.figure(figsize=(18,6))
sns.set_style('white')


collors_light=[(0, 92/255, 49/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3)]
collors_full=[(0, 92/255, 49/255, 0.9), (43/255,206/255,72/255, 0.9), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3)]
collors_total=[(0, 92/255, 49/255, 1.), (43/255,206/255,72/255, 1.), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3)]

fig=sns.boxplot(x=df_grouped_forSeaborn_2['Group'], y=df_grouped_forSeaborn_2['dnds'], data=df_grouped_forSeaborn_2, palette=collors_full, showfliers=False, linewidth=0.1) # palette='colorblind'

print(len(fig.lines))
for i,artist in enumerate(fig.artists):
    col = artist.get_facecolor()
    artist.set_edgecolor(col)
    artist.set_facecolor(collors_light[i])

    for j in range(i*5,i*5+5):
        line = fig.lines[j]
        line.set_color(collors_full[i])
        line.set_mfc(collors_full[i])
        line.set_mec(collors_full[i])




        
        
print("checked")

fig=sns.swarmplot(x=df_grouped_forSeaborn_2['Group'], y=df_grouped_forSeaborn_2['dnds'], data=df_grouped_forSeaborn_2, color="k", size = 1.)




###
#         Now plot on top the actual observed values
###

##
#      Read the actual observed values
##
df_obs=pd.read_csv("./data/cviLongBranch_bootDnDs_actualData.txt",
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')
group=['dnds']
order=[1]
df_obs['Group'] = group 
df_obs['Order'] = order

file_list=[
    "./data/mor-ha_bootDnDs_actualData.txt", "./data/mor-sma_bootDnDs_actualData.txt", "./data/mor-nma_bootDnDs_actualData.txt", "./data/mor-rif_bootDnDs_actualData.txt"
]
order_list=[2, 3, 4, 5, 6]

for file in range(len(file_list)):
    name=file_list[file].split("/")[len(file_list[file].split("/"))-1].split("_")[0]
    df_obs2=pd.read_csv(file_list[file],
        header=None,
        names=['dnds'], # [name],
        skipinitialspace=False,
        sep='\t')
    df_obs2['Group'] = 'dnds'
    df_obs2['Order'] = order_list[file]
    
    df_obs=df_obs.append(df_obs2)
    
print(df_obs)

#     Now plot
df_obs_new = pd.DataFrame(dict(xx=df_obs['Order'], yy=np.log10(df_obs['dnds'])))
fig=sns.swarmplot(x='xx', y='yy', data=df_obs_new, size = 5, palette=collors_total, marker="D", edgecolor="black", linewidth=0.5)

fig.set(title='')
fig.set(xlabel=None)

# X axis
# 
ordered=['Cvi_long', 'M_ha', 'M_sma', 'M_nma', 'M_rif']
fig.set_xticklabels(ordered)
plt.tick_params(labelsize=7)

# Y axis
# 
fig.set_ylabel("dN/dS", size=7)

fig.set_ylim(np.log10([0.235, 0.29]))
fig.set_yticks([np.log10(0.24), np.log10(0.26), np.log10(0.28)])
fig.set_yticklabels(['0.24', '0.26', '0.28'])

fig = plt.gcf()
fig.set_size_inches(6.0, 3.)

plt.savefig("./figures/supplementary_fig3.png", format="png",bbox_inches="tight", dpi=900)

