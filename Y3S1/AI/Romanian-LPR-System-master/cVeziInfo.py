import tkinter as tk
from tkinter import *
from tkinter import ttk
import cLPRDataBase
import cPanouControl
NUM_PHOTOS=4

class VeziInfo(tk.Frame):

    def menubar(self, root):
        menubar = tk.Menu(root, bd=2)
        pageMenu = tk.Menu(menubar, tearoff=0)
        menubar.add_cascade(label="Meniu", menu=pageMenu)
        pageMenu.add_command(label="Selectare algoritm",command=lambda: root.SettingsPage(0))
        pageMenu.add_command(label="Securitate",command=lambda:root.SecurityPage() )
        pageMenu.add_separator()
        pageMenu.add_cascade(label="Exit", command=quit)
        return menubar

    def __init__(self,parent,controller,settingsVal,securityVal):
        '''Ar trebui mutata pe butonul de selectare tip de algoritm'''
        tk.Frame.__init__(self,parent)



        scrollbar = Scrollbar(self)
        scrollbar.pack(side=RIGHT, fill=Y,pady=(0,85))

        self.vedereCopac=ttk.Treeview(self,height=15,yscrollcommand = scrollbar.set,
                                      columns=("producator","model","culoare","nume",
                                                              "prenume","functie","data","ora"));
        self.vedereCopac.pack(fill='x')
        scrollbar.config(command=self.vedereCopac.yview)
        self.vedereCopac.heading('#0',text="Numar inmatriculare",anchor='w')
        self.vedereCopac.heading("producator",text="Producator",anchor='w')
        self.vedereCopac.heading("model",text="Model",anchor='w')
        self.vedereCopac.heading("culoare",text="Culoare",anchor='w')
        self.vedereCopac.heading("nume", text="Nume", anchor='w')
        self.vedereCopac.heading("prenume", text="Prenume", anchor='w')
        self.vedereCopac.heading("functie", text="Functie", anchor='w')
        self.vedereCopac.heading("data", text="Data", anchor='w')
        self.vedereCopac.heading("ora", text="Ora", anchor='w')
        self.vedereCopac.column("#0",width=70)
        self.vedereCopac.column("producator",width=30)
        self.vedereCopac.column("model",width=40)
        self.vedereCopac.column("culoare", width=40)
        self.vedereCopac.column("nume", width=60)
        self.vedereCopac.column("prenume", width=40)
        self.vedereCopac.column("functie",width=60)
        self.vedereCopac.column("data",width=40)
        self.vedereCopac.column("ora",width=50)




        def goBack():
            controller.show_frame(cPanouControl.PanouControl)

        imageInfo = PhotoImage(file="GUIPhotos/036.png")
        butonVeziInformatii = tk.Button(self,relief=RIDGE,image=imageInfo,anchor=CENTER,compound=CENTER,
                                        cursor="hand2",command=lambda: goBack())
        butonVeziInformatii.place(relx=0.5,rely=0.9,anchor="center", height=30, width=266)
        butonVeziInformatii.image = imageInfo

    def showInfo(self,valoriPentruTabel):
        for line in valoriPentruTabel:
            self.vedereCopac.insert('', '0', text=line[0], values=(line[2], line[1], line[3], line[4], line[5],
                                                                   line[6], line[7], line[8]))

    def externCall(self):
        dbObj = cLPRDataBase.myDataBase()
        valoriPentruTabel = dbObj.getInfoLastEntered()
        self.showInfo(valoriPentruTabel)

