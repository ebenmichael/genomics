# -*- coding: utf-8 -*-
"""
Figures for the poster
"""

import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

def plotVals(csvFile,xlab,ylab,title,fName = None,save = False, log = False):
    dat = pd.read_csv(csvFile)
    x = dat.iloc[:,0]
    y = dat.iloc[:,1]
    sns.set_context('poster',font_scale=1.5)
    sns.set_style("whitegrid",{"axes.facecolor":"#EEECE1","figure.facecolor":"#EEECE1"
                ,"xtick.color":".2","ytick.color":".2",
                "text.color":".1","axes.labelcolor":".1"})
    plt.figure(figsize=(9, 12))      
    plt.plot(x,y,color = '#00274c')
    plt.xlabel(xlab)
    plt.ylabel(ylab)
    plt.title(title)

    
    if save:
        plt.savefig(fName, transparent = True, dpi = 200)
                
    


"""
sns.set_context('poster',font_scale=1.5)
sns.set_style("whitegrid",{"axes.facecolor":"#EEECE1","figure.facecolor":"#EEECE1"
            ,"xtick.color":".2","ytick.color":".2",
            "text.color":".1","axes.labelcolor":".1"})
fig = plt.figure()
ax = fig.add_subplot(111)
ax.plot(dat.iloc[:,0],dat.iloc[:,1],color = '#002d62',label = 'AC')
ax.plot(dat.iloc[:,0],dat.iloc[:,2], color = '#9D0E00', label = 'CGG')
ax.plot(dat.iloc[:,0],dat.iloc[:,3], color = '#ffcb05', label = 'TTAGGG')
ax.set_yscale('log')
leg = ax.legend(title = 'STR')
plt.xlabel('Number of STR Repeats')
plt.ylabel('Number of Occurances in the Genome (log)')
plt.title('Distribution of STR Repeats')
plt.setp(leg.get_title(),fontsize=32)

"""
    