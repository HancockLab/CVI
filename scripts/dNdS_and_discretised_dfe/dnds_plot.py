#!/usr/bin/env python
# coding: utf-8

# In[1]:


###
#    Now the whole thing! Together!
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
# sns.reset_defaults()
# sns.set(rc={'figure.figsize':(20,8), 'axes.labelsize': 
#             40,'font.size':30, 'xtick.labelsize': 20.0, 'ytick.labelsize': 20.0})
sns.set_style('white')

# df_grouped_2 = df_grouped_forSeaborn
# print(df_grouped_2)

# df_grouped_2['dnds'] = df_grouped_forSeaborn['dnds']
# df_grouped_2['dnds'] = np.log10(df_grouped_2['dnds'])


# print(df_grouped_2)
#     Now plot
# 
# This works:

# Old colors:
#
# collors_light=[(0/255,191/255,255/255, 0.3), (255/255,140/255,0/255, 0.3), (218/255,165/255,32/255, 0.3), (160/255,98/255,46/255, 0.3), (50/255,205/255,50/255, 0.3)]
# collors_full=[(0/255,191/255,255/255, 0.9), (255/255,140/255,0/255, 0.9), (218/255,165/255,32/255, 0.9), (160/255,98/255,46/255, 0.9), (50/255,205/255,50/255, 0.9)]

# New colors:
#rgb(240,163,255)

collors_light=[(240/255,163/255,255/255, 0.3), (43/255,206/255,72/255, 0.3), (255/255,164/255,5/255, 0.3), (0/255,92/255,49/255, 0.3)]
collors_full=[(240/255,163/255,255/255, 0.9), (43/255,206/255,72/255, 0.9), (255/255,164/255,5/255, 0.9), (0/255,92/255,49/255, 0.9)]
collors_total=[(240/255,163/255,255/255, 1.), (43/255,206/255,72/255, 1.), (255/255,164/255,5/255, 1.), (0/255,92/255,49/255, 1.)]

# Paper-ready colors
# sa_col = rgb(0,117,220)
# fo_col = rgb(255,164,5)
# cvi_col = rgb(0,92,49)
# mor_col = rgb(43,206,72)

collors_light=[(43/255,206/255,72/255, 0.3), (0,117/255,220/255, 0.3), (255/255,164/255,5/255, 0.3)]
collors_full=[(43/255,206/255,72/255, 0.9), (0,117/255,220/255, 0.9), (255/255,164/255,5/255, 0.9)]
collors_total=[(43/255,206/255,72/255, 1.), (0,117/255,220/255, 1.), (255/255,164/255,5/255, 1.)]

#collors_light=[(0/255,117/255,220/255, 0.3), (255/255,164/255,5/255, 0.3), (0/255,92/255,49/255, 0.3), (43/255,206/255,72/255, 0.3), (240/255,163/255,255/255, 0.3)]
#collors_full=[(0/255,117/255,220/255, 0.9), (255/255,164/255,5/255, 0.9), (0/255,92/255,49/255, 0.9), (43/255,206/255,72/255, 0.9), (240/255,163/255,255/255, 0.9)]
#collors_total=[(0/255,117/255,220/255, 1.), (255/255,164/255,5/255, 1.), (0/255,92/255,49/255, 1.), (43/255,206/255,72/255, 1.), (240/255,163/255,255/255, 1.)]

fig=sns.boxplot(x=df_grouped_forSeaborn_2['Group'], y=df_grouped_forSeaborn_2['dnds'], data=df_grouped_forSeaborn_2, palette=collors_full, showfliers=False, linewidth=0.1) # palette='colorblind'



##
#   new
# sns.boxplot(x="day", y="total_bill", hue="smoker", data=tips, palette="Set1", ax=ax1)
# sns.boxplot(x="day", y="total_bill", hue="smoker", data=tips, palette="Set1", ax=ax2)

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
# print(df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"] == 'mor'])




# dfCvi = pd.DataFrame('na', index=[0, 1], columns=['dnds', 'Group', 'Order'])
# dfCvi['Group'] = ['mor', 'cviLongBranch']
# dfCvi=dfCvi.append(df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"].isin(['sa', 'fo'])])


dfCvi=df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"].isin(['sa', 'fo'])]
print(dfCvi)

# fig=sns.swarmplot(x=df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"] == 'sa']['Order'], y=df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"] == 'sa']['dnds'], data=df_grouped_forSeaborn_2, color="k", size = 4)
fig=sns.swarmplot(x=dfCvi['Group'], y=dfCvi['dnds'], data=dfCvi, color="k", size = 2.)



#get first patchcollection
c0 = fig.get_children()[0]
x,y = np.array(c0.get_offsets()).T
#Add 2 to x values
xnew=x+1
offsets = list(zip(xnew,y))
#set newoffsets
c0.set_offsets(offsets)


#get first patchcollection
c1 = fig.get_children()[1]
x,y = np.array(c1.get_offsets()).T
#Add 2 to x values
xnew=x+1
offsets = list(zip(xnew,y))
#set newoffsets
c1.set_offsets(offsets)



###
#         Now plot on top the actual observed values
###

##
#      Read the actual observed values
##
df_obs=pd.read_csv("./data/morocco-mean_bootDnDs_actualData.txt",
           # delim_whitespace=True,
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')
group=['dnds']
order=[1]
df_obs['Group'] = group 
df_obs['Order'] = order

# print(df_obs)

file_list=[
    # "./data/cviLongBranch_bootDnDs_actualData.txt",     
    "./data/santo_bootDnDs_actualData.txt",
    "./data/fogo_bootDnDs_actualData.txt"]
    
    
    # "./data/eurasia-mean_bootDnDs_actualData.txt"]

    # "./data/mor-ha_bootDnDs_actualData.txt", "./data/mor-sma_bootDnDs_actualData.txt", "./data/mor-nma_bootDnDs_actualData.txt", "./data/mor-rif_bootDnDs_actualData.txt", 
    # "./data/nSweden_bootDnDs_actualData.txt", "./data/wEurope_bootDnDs_actualData.txt", "./data/cEurope_bootDnDs_actualData.txt", "./data/germany_bootDnDs_actualData.txt", "./data/sSweden_bootDnDs_actualData.txt",  "./data/ibc_bootDnDs_actualData.txt", "./data/spain_bootDnDs_actualData.txt", 
    # "./data/relicts_bootDnDs_actualData.txt"]
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
## #fig=sns.swarmplot(x=[0, 1], y=[1.730794,2.233283], size = 8, color="deeppink", marker="D", edgecolor="k")



fig=sns.swarmplot(x='xx', y='yy', data=df_obs_new, size = 5, palette=collors_total, marker="D", edgecolor="black", linewidth=0.5)

###
#
###


# With xlabels
fig.set(title='')
fig.set(xlabel=None)

# X axis
# 
# fig.set_xticks([])
ordered=['Mor', 'SA', 'Fogo']
fig.set_xticklabels(ordered)
# plt.tick_params(labelsize=25)

# Y axis
# 
fig.set_ylabel("dN/dS")





# For all
# 
fig.set_ylim(np.log10([0.15,10.5]))
fig.set_yticks([np.log10(0.2), np.log10(0.5), np.log10(1.), np.log10(2.), np.log10(10.)])
fig.set_yticklabels(['0.2', '0.5', '1.0', '2.0','10.0'])
# fig.set_size_inches(8., 4)

fig = plt.gcf()
fig.set_size_inches(2.3, 1.7)

# plt.figure(figsize=(4,5)) 
plt.savefig("./figures/fig2a.png", format="png",bbox_inches="tight", dpi=900)























# In[40]:


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
df_easy=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
           # delim_whitespace=True,
           header=None,
           names=['sa'],
           skipinitialspace=False,
           sep='\t')
# print(df_easy)



###
# Now append all others

file_list=[
        # "./data/fo_bootDnDs_2.txt",
        # "./data/cviLongBranch_bootDnDs.txt", 
        "./data/mor-ha_bootDnDs.txt", "./data/mor-sma_bootDnDs.txt", "./data/mor-nma_bootDnDs.txt", "./data/mor-rif_bootDnDs.txt", 
        #"./data/asia_bootDnDs.txt", "./data/nSweden_bootDnDs.txt", "./data/wEurope_bootDnDs.txt", "./data/cEurope_bootDnDs.txt", "./data/germany_bootDnDs.txt", "./data/sSweden_bootDnDs.txt",  "./data/ibc_bootDnDs.txt", "./data/spain_bootDnDs.txt", 
        #"./data/relicts_bootDnDs.txt"
          ]
# order_list=[1, 
#             5, 4, 3, 6, 7,
#             3,4,1, 2, 5, 6, 9, 7, 8]

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





# With Mor and Euras grouped:
df_grouped=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
           # delim_whitespace=True,
           header=None,
           names=['sa'],
           skipinitialspace=False,
           sep='\t')

df_grouped_forSeaborn=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
           # delim_whitespace=True,
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')


df_grouped_forSeaborn_halfSwarm=pd.read_csv("./data/cviLongBranch_bootDnDs.txt",
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
# sns.reset_defaults()
# sns.set(rc={'figure.figsize':(20,8), 'axes.labelsize': 
#             40,'font.size':30, 'xtick.labelsize': 20.0, 'ytick.labelsize': 20.0})
sns.set_style('white')


# Paper-ready colors
# sa_col = rgb(0,117,220)
# fo_col = rgb(255,164,5)
# cvi_col = rgb(0,92,49)
# mor_col = rgb(43,206,72)

collors_light=[(0, 92/255, 49/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3)]
collors_full=[(0, 92/255, 49/255, 0.9), (43/255,206/255,72/255, 0.9), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3)]
collors_total=[(0, 92/255, 49/255, 1.), (43/255,206/255,72/255, 1.), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3), (43/255,206/255,72/255, 0.3)]


fig=sns.boxplot(x=df_grouped_forSeaborn_2['Group'], y=df_grouped_forSeaborn_2['dnds'], data=df_grouped_forSeaborn_2, palette=collors_full, showfliers=False, linewidth=0.1) # palette='colorblind'



##
#   new
# sns.boxplot(x="day", y="total_bill", hue="smoker", data=tips, palette="Set1", ax=ax1)
# sns.boxplot(x="day", y="total_bill", hue="smoker", data=tips, palette="Set1", ax=ax2)

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
# print(df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"] == 'mor'])




# dfCvi = pd.DataFrame('na', index=[0, 1], columns=['dnds', 'Group', 'Order'])
# dfCvi['Group'] = ['mor', 'cviLongBranch']
# dfCvi=dfCvi.append(df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"].isin(['sa', 'fo'])])


#dfCvi=df_grouped_forSeaborn_2[df_grouped_forSeaborn_2["Group"].isin(['sa', 'fo'])]

fig=sns.swarmplot(x=df_grouped_forSeaborn_2['Group'], y=df_grouped_forSeaborn_2['dnds'], data=df_grouped_forSeaborn_2, color="k", size = 1.)




###
#         Now plot on top the actual observed values
###

##
#      Read the actual observed values
##
df_obs=pd.read_csv("./data/cviLongBranch_bootDnDs_actualData.txt",
           # delim_whitespace=True,
           header=None,
           names=['dnds'],
           skipinitialspace=False,
           sep='\t')
group=['dnds']
order=[1]
df_obs['Group'] = group 
df_obs['Order'] = order

# print(df_obs)

file_list=[
    # "./data/cviLongBranch_bootDnDs_actualData.txt",     
    # "./data/santo_bootDnDs_actualData.txt",
    # "./data/fogo_bootDnDs_actualData.txt"]
    
    
    # "./data/eurasia-mean_bootDnDs_actualData.txt"]

    "./data/mor-ha_bootDnDs_actualData.txt", "./data/mor-sma_bootDnDs_actualData.txt", "./data/mor-nma_bootDnDs_actualData.txt", "./data/mor-rif_bootDnDs_actualData.txt"
    # "./data/nSweden_bootDnDs_actualData.txt", "./data/wEurope_bootDnDs_actualData.txt", "./data/cEurope_bootDnDs_actualData.txt", "./data/germany_bootDnDs_actualData.txt", "./data/sSweden_bootDnDs_actualData.txt",  "./data/ibc_bootDnDs_actualData.txt", "./data/spain_bootDnDs_actualData.txt", 
    # "./data/relicts_bootDnDs_actualData.txt"
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
## #fig=sns.swarmplot(x=[0, 1], y=[1.730794,2.233283], size = 8, color="deeppink", marker="D", edgecolor="k")



fig=sns.swarmplot(x='xx', y='yy', data=df_obs_new, size = 5, palette=collors_total, marker="D", edgecolor="black", linewidth=0.5)

###
#
###


# With xlabels
fig.set(title='')
fig.set(xlabel=None)

# X axis
# 
# fig.set_xticks([])
ordered=['Cvi_long', 'M_ha', 'M_sma', 'M_nma', 'M_rif']
fig.set_xticklabels(ordered)
plt.tick_params(labelsize=7)

# Y axis
# 
fig.set_ylabel("dN/dS", size=7)





# For all
# 
fig.set_ylim(np.log10([0.235, 0.29]))
fig.set_yticks([np.log10(0.24), np.log10(0.26), np.log10(0.28)])
fig.set_yticklabels(['0.24', '0.26', '0.28'])
# fig.set_size_inches(8., 4)

fig = plt.gcf()
fig.set_size_inches(6.0, 3.)

# plt.figure(figsize=(4,5)) 
plt.savefig("./figures/supplementary_fig3.png", format="png",bbox_inches="tight", dpi=900)

