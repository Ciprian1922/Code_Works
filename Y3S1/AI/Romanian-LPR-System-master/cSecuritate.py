from tkinter import *
import tkinter as tk

from ConvolutionalIntreg import detect
from PIL import Image, ImageTk
import fonts

class PanouSecuritate(tk.Frame):

    def menubar(self, root,controller):
        menubar = tk.Menu(root, bd=2)

        return menubar
    def show_val(self,var):
        print (var.get())

    def __init__(self,parent,popup,controller,val):
        tk.Frame.__init__(self, parent)

        def exitFunc(answer):
            controller.securityVal = answer
            popup.destroy()

        def resize_image(event):
            new_width = event.width
            new_height = event.height-56 #56=padding_sus+padding_jos+marime_font
            image = copy_of_image.resize((new_width, new_height))
            photo = ImageTk.PhotoImage(image)
            label.config(image=photo)

            label.image = photo  # avoid garbage collection



        image = Image.open('GUIPhotos/021.png')
        copy_of_image = image.copy()
        photo = ImageTk.PhotoImage(image)
        label = tk.Label(self,text="Alegeti un nivel de securitate: ", compound="bottom",
                          pady=20,anchor=NW,font=fonts.VERDANA)
        label.bind('<Configure>', resize_image)
        label.pack(fill=BOTH, expand=YES)

        answer = tk.IntVar(value=val)
        #answer.set(1)  # initialize

        # Modul SLAB
        imageSlab=PhotoImage(file='GUIPhotos/018.png')
        slabCheck = Radiobutton(label, value=0, variable=answer,image=imageSlab,anchor=W,
                               text="Slab",tristatevalue=0,compound=LEFT)
        slabCheck.place(relx=0.05, rely=0.2, relwidth=0.5, relheight=0.05 )
        slabCheck.image=imageSlab
        svmLabelText = StringVar()
        svmLabelText.set("Modul slab de securitate presupune permiterea accesului autovehiculelor ale caror\n"
                         "numere de inmatriculare potrivesc 70% dintr-o intrare existenta in baza de date.\n")



        svmLabel = tk.Label(label, padx=10, anchor=W, justify="left", textvariable=svmLabelText)
        svmLabel.place(relx=0.1, rely=0.27, relwidth=0.82, relheight=0.08)

        # Modul MEDIU
        imageMediu = PhotoImage(file='GUIPhotos/020.png')
        mediuCheck = Radiobutton(label, value=1, variable=answer, image=imageMediu, anchor=W,
                                text="Mediu", tristatevalue=1, compound=LEFT)
        mediuCheck.place(relx=0.05, rely=0.4, relwidth=0.5, relheight=0.05 )
        mediuCheck.image = imageMediu
        mediuText = StringVar()
        mediuText.set("Modul mediu de securitate presupune permiterea accesului autovehiculelor ale caror\n"
                         "numere de inmatriculare potrivesc 85% dintr-o intrare existenta in baza de date,cu\n"
                        "conditia ca accesul acestora sa se realizeze intre orele la care acesta cere accesul\n"
                      "in mod normal.")

        mediuLabel = tk.Label(label, padx=10, anchor=W, justify="left", textvariable=mediuText)
        mediuLabel.place(relx=0.1, rely=0.47, relwidth=0.82, relheight=0.1)

        # Modul SIGUR
        imageSigur = PhotoImage(file='GUIPhotos/019.png')
        sigurCheck = Radiobutton(label, value=2, variable=answer, image=imageSigur, anchor=W,
                                 text="Sigur", tristatevalue=2, compound=LEFT)
        sigurCheck.place(relx=0.05, rely=0.62, relwidth=0.5, relheight=0.05, )
        sigurCheck.image = imageSigur
        sigurText = StringVar()
        sigurText.set("Modul sigur presupune permiterea autovehiculelor, numai daca potrivirea dintre numarul\n"
                      "de inmatriculare si intrarea din baza de date este de 100%\n")

        sigurLabel = tk.Label(label, padx=10, anchor=W, justify="left", textvariable=sigurText)
        sigurLabel.place(relx=0.1, rely=0.67, relwidth=0.82, relheight=0.1)

        sigurCheck.value=answer
        #BUTON


        imageAplica = PhotoImage(file="GUIPhotos/035.png")
        buttonBack = tk.Button(label, relief=RIDGE, image=imageAplica,
                               cursor="hand2",compound=CENTER, anchor=CENTER, command=lambda: exitFunc(answer.get()))
        buttonBack.place(relx=0.45, rely=0.86, width=266, height=30)
        buttonBack.image = imageAplica



