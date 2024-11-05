from tkinter import *
import tkinter as tk
from tkinter import ttk
from PIL import Image, ImageTk
from ConvolutionalIntreg import detect
import fonts

class PanouSetari(tk.Frame):

    def menubar(self, root,controller):
        menubar = tk.Menu(root, bd=2)

        return menubar
    def show_val(self,var):
        print (var.get())

    def __init__(self,parent,popup,controller,val):

        tk.Frame.__init__(self, parent)
        def exitFunc(answer):
            controller.algorithmVal=answer
            if answer==2:
                controller.Label="Incarcare date training..."
                controller.assign_ops,controller.x, controller.y=detect.loadGraph()

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
        label = tk.Label(self, text="Alegeti un algoritm: ", compound="bottom",
                         pady=20, anchor=NW, font=fonts.VERDANA)
        label.bind('<Configure>', resize_image)
        label.pack(fill=BOTH, expand=YES)

        answer = tk.IntVar(value=val)

        # SVM
        imageSlab = PhotoImage(file='GUIPhotos/024.png')
        slabCheck = Radiobutton(label, value=0, variable=answer, image=imageSlab, anchor=W,
                                text="Support Vector Machine", tristatevalue=0, compound=LEFT,padx=10)
        slabCheck.place(relx=0.05, rely=0.2, relwidth=0.5, relheight=0.05)
        slabCheck.image = imageSlab
        svmLabelText = StringVar()
        svmLabelText.set("Acest algoritm se bazeaza pe gasirea unui numar de inmatriculare in cadrul imaginii, apoi \n"
                        "se cauta caracterele in cadrul placutei. In final se calculeaza un vector corespunzator \n"
                        "acestor caractere si se compara cu vectorii obtinuti prin antrenare. Recomandat pentru \n"
                         "calculatoare cu putere de procesare mica. Procesarea este rapida dar precizia este slaba.")
        svmLabel = tk.Label(label, padx=10, anchor=W, justify="left", textvariable=svmLabelText)
        svmLabel.place(relx=0.1, rely=0.27, relwidth=0.82, relheight=0.1)

        # Modul CNN pe caracter
        imageMediu = PhotoImage(file='GUIPhotos/023.png')
        mediuCheck = Radiobutton(label, value=1, variable=answer, image=imageMediu, anchor=W, padx=10,
                                 text="Retea convolutionala pe caracter", tristatevalue=1, compound=LEFT)
        mediuCheck.place(relx=0.05, rely=0.4, relwidth=0.5, relheight=0.05)
        mediuCheck.image = imageMediu
        mediuText = StringVar()
        mediuText.set("Metoda CNN individual presupune aplicarea segmentarii pentru obtinerea imaginilor\n"
                         "cu caractere. Acestea sunt folosite apoi ca input pentru o retea convolutionala care\n"
                         "are ca output probabilitatea ca o imagine sa reprezinte un anumit caracter. Probabilitatea\n"
                          "cea mai mare va reprezenta predictia retelei.Procesarea si precizia sunt medii.")
        mediuLabel = tk.Label(label, padx=10, anchor=W, justify="left", textvariable=mediuText)
        mediuLabel.place(relx=0.1, rely=0.47, relwidth=0.82, relheight=0.1)

        # Modul SIGUR
        imageSigur = PhotoImage(file='GUIPhotos/022.png')
        sigurCheck = Radiobutton(label, value=2, variable=answer, image=imageSigur, anchor=W,padx=10,
                                 text="Retea convolutionala pe intreaga imagine", tristatevalue=2,
                                 compound=LEFT)
        sigurCheck.place(relx=0.05, rely=0.62, relwidth=0.5, relheight=0.05, )
        sigurCheck.image = imageSigur
        sigurText = StringVar()
        sigurText.set("Metoda de recunoastere ce foloseste o retea convolutionala aplicata pe intreaga\n"
                          "imagine primeste ca input o poza si are mai multe iesiri care determina probabilitatea\n"
                          "existentei placutei in cadrul imaginii dar si probabilitatile pentru fiecare caracter\n"
                          "in cazul in care placuta exista. Procesarea necesita multe resurse insa precizia este \n"
                          "foarte buna.")

        sigurLabel = tk.Label(label, padx=10, anchor=W, justify="left", textvariable=sigurText)
        sigurLabel.place(relx=0.1, rely=0.69, relwidth=0.82, relheight=0.12)

        sigurCheck.variable = answer

        imageAplica = PhotoImage(file="GUIPhotos/035.png")
        buttonBack=tk.Button(label, relief= RIDGE, image=imageAplica,
                             cursor="hand2",compound=CENTER, anchor=CENTER,command=lambda:exitFunc(answer.get()))
        buttonBack.place(relx=0.45,rely=0.86,width=266,height=30)
        buttonBack.image=imageAplica

