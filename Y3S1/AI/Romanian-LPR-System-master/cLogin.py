from tkinter import *
import tkinter as tk
import cPanouControl
from tkinter import ttk
from PIL import Image, ImageTk


class LoginFrame(tk.Frame):
    def menubar(self, root):
        menubar = tk.Menu(root)
        return menubar

    def __init__(self,parent,controller):
        tk.Frame.__init__(self, parent)
        def resize_image(event):
            new_width = event.width
            new_height = event.height
            image = copy_of_image.resize((new_width, new_height))
            photo = ImageTk.PhotoImage(image)
            label.config(image=photo)
            label.image = photo  # avoid garbage collection


        image = Image.open('GUIPhotos/015.png')
        copy_of_image = image.copy()
        photo = ImageTk.PhotoImage(image)
        label = ttk.Label(self, image=photo)
        label.bind('<Configure>', resize_image)
        label.pack(fill=BOTH, expand=YES)

        # username
        userImage = PhotoImage(file="GUIPhotos/003.png")
        labelNume = Label(label, image=userImage, bg='white')
        labelNume.place(relx=0.25, rely=0.3, anchor=CENTER, relwidth='0.4', relheight='0.06')
        labelNume.image=userImage

        entryNume = Entry(label)
        entryNume.place(relx=0.75, rely=0.3, anchor=CENTER, relwidth="0.4", relheight='0.06')

        # password
        passwordImage = PhotoImage(file="GUIPhotos/005.png")
        labelPass = Label(label, image=passwordImage, bg='white')
        labelPass.place(relx=0.25, rely=0.5, anchor=CENTER, relwidth='0.4', relheight='0.06')
        labelPass.image=passwordImage

        entryPass = Entry(label)
        entryPass.place(relx=0.75, rely=0.5, anchor=CENTER, relwidth="0.4", relheight='0.06')

        #buton
        buttonImage = PhotoImage(file="GUIPhotos/008.png")
        logButton = Button(label, bg="white", image=buttonImage,
                           command=lambda: controller.show_frame(cPanouControl.PanouControl))
        logButton.place(rely=0.7, relx=0.5, anchor=CENTER, relwidth="0.7", relheight='0.06')
        logButton.image=buttonImage