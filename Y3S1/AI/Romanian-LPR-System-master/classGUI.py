import tkinter as tk
import cLogin
import cPanouControl
import cVeziInfo
import cSetari
import cSecuritate

class interfata(tk.Tk):
    def __init__(self):
        tk.Tk.__init__(self)
        tk.Tk.iconbitmap(self,default='GUIPhotos/009.png')
        self.geometry("400x600")
        self.title("NrDeInamtRecunoscatatorul")
        container=tk.Frame(self)
        container.pack(fill="both",expand=True)


        container.grid_rowconfigure(0,weight=1)
        container.grid_columnconfigure(0,weight=1)
        self.algorithmVal=0
        self.securityVal=0
        self.x=None
        self.y=None
        self.assign_ops=None
        self.Label=""  #pentru labelul de sub poza cu masina

        self.frames={}

        for F in (cPanouControl.PanouControl,cVeziInfo.VeziInfo,):
            frame = F(container,self,self.algorithmVal,self.securityVal)

            self.frames[F]= frame

            frame.grid(row=0, column=0,sticky="nsew")

        self.show_frame( cPanouControl.PanouControl )

    def show_frame(self,cont):
 #       if cont==cPanouControl.PanouControl:
        self.geometry("800x430")
        frame=self.frames[cont]
        frame.tkraise()

        menubar = frame.menubar(self )
        self.configure(menu=menubar)


    def SettingsPage(self,answerVal):
        popup = tk.Toplevel()
        popup.grab_set()
        popup.geometry("600x600")
        container = tk.Frame(popup)
        container.pack(fill="both", expand=True)

        container.grid_rowconfigure(0, weight=1)
        container.grid_columnconfigure(0, weight=1)

        popup.frames = {}

        F=cSetari.PanouSetari
        frame = F(container, popup,self, self.algorithmVal)

        popup.frames[F] = frame

        frame.grid(row=0, column=0, sticky="nsew")

        nFrame=popup.frames[cSetari.PanouSetari]
        nFrame.tkraise()

    def SecurityPage(self):
        popup = tk.Toplevel()
        popup.grab_set()
        popup.geometry("600x600")
        container = tk.Frame(popup)
        container.pack(fill="both", expand=True)

        container.grid_rowconfigure(0, weight=1)
        container.grid_columnconfigure(0, weight=1)

        popup.frames = {}

        F=cSecuritate.PanouSecuritate
        frame = F(container, popup, self, self.securityVal)

        popup.frames[F] = frame

        frame.grid(row=0, column=0, sticky="nsew")

        nFrame=popup.frames[cSecuritate.PanouSecuritate]
        nFrame.tkraise()


app=interfata()
app.mainloop()