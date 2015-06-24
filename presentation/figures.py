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
    plt.plot(x,y,color = '#00274c')
    plt.xlabel(xlab)
    plt.ylabel(ylab)
    plt.title(title, y=1.06)

    
    if save:
        plt.savefig(fName, transparent = True, dpi = 200)
                
    


"""
sns.set_context('poster',font_scale=1.5)
sns.set_style("whitegrid",{"axes.facecolor":"#EEECE1","figure.facecolor":"#EEECE1"
            ,"xtick.color":".2","ytick.color":".2",
            "text.color":".1","axes.labelcolor":".1"})
fig = plt.figure()
ax = fig.add_subplot(111)
ax.plot(2*dat.iloc[:34,0],dat.iloc[:34,1],color = '#002d62',label = 'AC')
ax.plot(3*dat.iloc[:20,0],dat.iloc[:20,2], color = '#9D0E00', label = 'CGG')
ax.plot(6*dat.iloc[:20,0],dat.iloc[:20,3], color = '#ffcb05', label = 'TTAGGG')
ax.set_yscale('log')
leg = ax.legend(title = 'STR')
plt.xlim([1,75])
plt.xlabel('Length of STR Repeats')
plt.ylabel('Number of Occurances in the Genome (log)')
plt.title('Distribution of STR Repeats')
plt.setp(leg.get_title(),fontsize=32)


"""
    