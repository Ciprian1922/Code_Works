from tkinter import *
import tkinter as tk
from tkinter import ttk
from PIL import Image, ImageTk
import fonts
from threading import Thread
import threading
import cVeziInfo
import cLPRDataBase
import mysql.connector
from time import sleep
from SVC import prediction
from ConvolutionalCuSegmentare import ConvolutionalIndividual
from ConvolutionalIntreg import detect

NUM_PHOTOS=62

class PanouControl(tk.Frame):

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
        x, y_true, y_pred_cls, session = ConvolutionalIndividual.buildGraph()

        #create DB Object
        mydb= cLPRDataBase.myDataBase()

        def labelImageResize(LastRecognized):
            newWidth = 200
            newHeight = int((newWidth / LastRecognized.size[0]) * LastRecognized.size[1])
            resizedLastRecognized = LastRecognized.resize((newWidth, newHeight))
            LastRecognizedTk = ImageTk.PhotoImage(resizedLastRecognized)
            return LastRecognizedTk


        def veziInfo():
            controller.frames[cVeziInfo.VeziInfo].externCall()
            controller.show_frame(cVeziInfo.VeziInfo)


        def resize_image(event):
            new_width = event.width
            new_height = event.height
            image = copy_of_image.resize((new_width, new_height))
            photo = ImageTk.PhotoImage(image)
            label.config(image=photo)
            label.image = photo  # avoid garbage collection
            LastNumber.place(y=new_height*0.27+200)

        tk.Frame.__init__(self,parent)



        image = Image.open('GUIPhotos/026.png')
        copy_of_image = image.copy()
        photo = ImageTk.PhotoImage(image)
        label = ttk.Label(self, image=photo)
        label.bind('<Configure>', resize_image)
        label.pack(fill=BOTH, expand=YES)

        def getCameraImage():
            '''generarea de imagini se face atata timp cat utilizatorul lasa sa ruleze un anumit algoritm.
            Cand acesta apa pe opreste recunoastere, nu va mai rula threadul, deci nu se vor mai genera imagini,
             iar daca nu se vor mai genera imagini, nu va mai rula nici functia de recunoastere, ea bazandu-se pe
             un for pe imaginile generate de aceasta functie.'''
            picNum = -1
            t = threading.currentThread()
            while getattr(t, "run", True):
                dirName="PozeCamera/"
                picNum=(picNum+1)% NUM_PHOTOS
                yield dirName+"car"+str(picNum) +".jpg"

        def cnnIndividualCall(imagePath):
            numberPlate=ConvolutionalIndividual.predictConvolutionalIndividual(imagePath,session,x,y_true,y_pred_cls)
            return numberPlate

        def cnnIntregCall(imagePath):
            numberPlate=detect.myDetect(controller.assign_ops, controller.x, controller.y, imagePath)
            return numberPlate

        def plateLastProcess( plateNum):
            # daca numarul e de bucuresti nu merge sa iau al 3 -lea caracter . de aceea fac procesarea
            # incepand cu finalul
            tupleCifre = ['0', '1', '2']
            tupleLitere = ['O', 'I', 'Z']
            lungime = len(plateNum)
            digitsPlace = lungime - 5
            digitsPlace = [digitsPlace, digitsPlace + 1]
            listPlate = list(plateNum)
            for i in range(0, lungime):
                if i in digitsPlace:
                    if plateNum[i] in tupleLitere:
                        indexCif = tupleLitere.index(plateNum[i])
                        listPlate[i] = tupleCifre[indexCif]
                else:
                    if plateNum[i] in tupleCifre:
                        indexLit = tupleCifre.index(plateNum[i])
                        listPlate[i] = tupleLitere[indexLit]
            plateNum = "".join(listPlate)
            return plateNum



        def threadFunctionButonPornire():

            securitySw={0:mydb.levenshteinSecuritateMica, 1:mydb.levenshteinSecuritateMedie,
                        2:mydb.securitateRidicata}

            switch={0:prediction.mainSVCfunc, 1: cnnIndividualCall, 2: cnnIntregCall}
            f=open('rezultateSVM','a')
            for picturePath in getCameraImage():
                licensePlateNum = switch[controller.algorithmVal](picturePath)
                LastRecognizedImage = Image.open(picturePath)
                LastRecognizedImageTk = labelImageResize(LastRecognizedImage)
                if licensePlateNum=='':
                    licensePlateNum='Niciun numar'
                    labelStare.config(text="Nu a fost detectata o masina")

                else:
                    licensePlateNum= plateLastProcess(licensePlateNum)
                    placutaDinBaza = securitySw[controller.securityVal](licensePlateNum)
                    if placutaDinBaza is not "False":
                        mydb.adaugaIntrareInBaza(placutaDinBaza)
                        labelStare.config(text="Deschidere bariera!")
                        print("Deschid bariera!")
                    else :
                        labelStare.config(text="Vehicul neidentificat la poarta")
                ###delete ####
                f.write(licensePlateNum)
                f.write("\n")
                ###delete ####

                LastNumber.config(text=licensePlateNum)
                labelImagineNumar.config(image=LastRecognizedImageTk)
                labelImagineNumar.image = LastRecognizedImageTk

               # sleep(1)
            print("Exiting thread...")


        def incepeRecunoastere():
            imageOpresteRecunoastere = PhotoImage(file="GUIPhotos/032.png")
            butonDeschideBariera.config(image=imageOpresteRecunoastere)
            butonDeschideBariera.image = imageOpresteRecunoastere
            butonDeschideBariera.config(command=lambda: opresteRecunoastere(thread))
            thread = Thread(target=threadFunctionButonPornire)
            thread.start()


        def opresteRecunoastere(thread):
            imageOpresteRecunoastere=PhotoImage(file="GUIPhotos/033.png")
            butonDeschideBariera.config(image=imageOpresteRecunoastere)
            butonDeschideBariera.image=imageOpresteRecunoastere
            thread.run = False
            butonDeschideBariera.config(command=lambda:incepeRecunoastere())


        #buton deschidere bariera

        imageDeschide=PhotoImage(file="GUIPhotos/033.png")
        butonDeschideBariera=tk.Button(self,relief=RIDGE,image=imageDeschide,
                                       cursor="hand2",compound=CENTER,anchor=CENTER, command=lambda:incepeRecunoastere())
        butonDeschideBariera.place(relx=0.1,rely=0.27,height=30,width=266)
        butonDeschideBariera.image=imageDeschide


        #buton vezi informatii
        imageInfo=PhotoImage(file="GUIPhotos/034.png")
        butonVeziInformatii = tk.Button(self,relief=RIDGE,image=imageInfo,anchor=CENTER,compound=CENTER,
                                        cursor="hand2",command=lambda: veziInfo())
        butonVeziInformatii.place(relx=0.1, rely=0.47, height=30, width=266)
        butonVeziInformatii.image=imageInfo

        #label cu stare curenta
        labelStare = tk.Label(self, text="LPR functioneaza normal", anchor=CENTER,
                                        bg="white")
        labelStare.place(relx=0.1, rely=0.66, height=30, width=266)


        #Afisarea ultimului numar recunoscut

        LastRecognized = Image.open("GUIPhotos/010.png")
        LastRecognizedTk=labelImageResize(LastRecognized)
        labelImagineNumar=tk.Label(self,image=LastRecognizedTk,bg="white")
        labelImagineNumar.place(relx=0.7,rely=0.27,width=200,height=200)
        labelImagineNumar.image=LastRecognizedTk

        LastNumber=tk.Label(self,text="BT 83 KNQ",relief=FLAT,font=fonts.TIMES_NEW_ROMAN)
        LastNumber.place(relx=0.7,width=200,height=20)



