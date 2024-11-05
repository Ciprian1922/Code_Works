import mysql.connector
import datetime

class myDataBase:
    def __init__(self):
        self.mydb = mysql.connector.connect(user='root', password='********',
                                      host='127.0.0.1',
                                      database='mydb')
        self.myCursor = self.mydb.cursor(buffered=True)

    def doQuerry(self, string):

        self.myCursor.execute(string)
        return self.myCursor


    def securitateMica(self,plateNum):
        listaNumere = []
        lungimeNumar = len(plateNum)

        #iau prima parte a numarului apoi pe a doua
        firstPartOfPlate = plateNum[:lungimeNumar - 3]
        lastPartOfPlate = plateNum[lungimeNumar - 3:]


        #execut un querry pentru prima parte a numarului
        self.myCursor.execute("SELECT NumarInmatriculare FROM numereinmat WHERE NumarInmatriculare LIKE \'"
                              + firstPartOfPlate + "%\'")
        answer = self.myCursor.fetchall()
        if answer != []:
            listaNumere +=answer

        #execut un querry pentru a doua parte a numarului
        self.myCursor.execute("SELECT NumarInmatriculare FROM numereinmat WHERE NumarInmatriculare LIKE \'%"
                              + lastPartOfPlate + "\'")
        answer = self.myCursor.fetchall()
        if answer != []:
            listaNumere +=answer


        #verific daca vreunul din numerele potrivite depaseste mai mult de 75%
        for lista in listaNumere:
            if lista != []:
                numMatched = 0
                for litEach, litNum in zip(reversed(lista[0]), reversed(plateNum)):
                    if litEach == litNum:
                        numMatched += 1
                if numMatched / lungimeNumar > 0.75:
                    print("Deschide Bariera")
                    break
                else:
                    print("Accesul nu este permis")

    def levenshteinSecuritateMica(self,plateNum):
        query=" SELECT numere_ID from numereinmat"\
              " WHERE Acces=1 AND levenshtein_ratio(\'"+plateNum+"\',NumarInmatriculare)>75"
        self.myCursor.execute(query)
        answer=self.myCursor.fetchone()
        if answer==None:
            return "False"
        else:
            return answer

    def levenshteinSecuritateMedie(self,plateNum):
        query="SELECT numere_ID from numereinmat"\
             " WHERE Acces=1 AND ((levenshtein_ratio(\'"+plateNum+"\',NumarInmatriculare)=100) OR"\
             " ( levenshtein_ratio(\'"+plateNum+"\',NumarInmatriculare)>75 AND"\
             " (SELECT AVG(HOUR(Ora)) FROM tranzitii INNER JOIN numereinmat ON numereinmat.numere_ID = tranzitii.numereInmat_numere_ID) <= (HOUR(NOW())+1)  AND"\
             " (SELECT AVG(HOUR(Ora)) FROM tranzitii INNER JOIN numereinmat ON numereinmat.numere_ID = tranzitii.numereInmat_numere_ID) >= (HOUR(NOW())-1)))"
        self.myCursor.execute(query)
        answer=self.myCursor.fetchone()
        if answer== None:
            return "False"
        else:
            return answer

    def securitateMedie(self,plateNum):
        oraCurenta=datetime.datetime.now()
        oraCurenta=oraCurenta.hour*3600+oraCurenta.minute*60+oraCurenta.second
        listaNumere = []
        lungimeNumar = len(plateNum)


        # iau prima parte a numarului apoi pe a doua
        firstPartOfPlate = plateNum[:lungimeNumar - 3]
        lastPartOfPlate = plateNum[lungimeNumar - 3:]

        # execut un querry pentru prima parte a numarului
        self.myCursor.execute("SELECT numereinmat.NumarInmatriculare,Ora FROM tranzitii INNER JOIN numereinmat"
                              " ON numereinmat.numere_ID = tranzitii.numereInmat_numere_ID"
                              " WHERE numereinmat.NumarInmatriculare LIKE \'"
                              + firstPartOfPlate + "%\'")
        answer = self.myCursor.fetchall()
        if answer != []:
            listaNumere += answer
        self.myCursor.execute("SELECT AVG(TIME_TO_SEC(Ora)) FROM tranzitii INNER JOIN numereinmat"
                              " ON numereinmat.numere_ID = tranzitii.numereInmat_numere_ID"
                              " WHERE numereinmat.NumarInmatriculare LIKE \'"
                              + firstPartOfPlate + "%\'")
        firstHalfTime=self.myCursor.fetchall()
        # execut un querry pentru a doua parte a numarului
        self.myCursor.execute("SELECT numereinmat.NumarInmatriculare,Ora FROM tranzitii INNER JOIN numereinmat"
                              " ON numereinmat.numere_ID=tranzitii.numereInmat_numere_ID"
                              " WHERE numereinmat.NumarInmatriculare LIKE \'%"
                              + lastPartOfPlate + "\'")
        answer = self.myCursor.fetchall()
        if answer != []:
            listaNumere += answer

        self.myCursor.execute("SELECT AVG(TIME_TO_SEC(Ora)) FROM tranzitii INNER JOIN numereinmat"
                              " ON numereinmat.numere_ID=tranzitii.numereInmat_numere_ID"
                              " WHERE numereinmat.NumarInmatriculare LIKE \'%"
                              + lastPartOfPlate + "\'")
        secondHalfTime=self.myCursor.fetchall()
        mediumTime=(firstHalfTime[0][0]+secondHalfTime[0][0])/2

        # verific daca vreunul din numerele potrivite depaseste mai mult de 75%
        for lista in listaNumere:
            if lista != []:
                if(oraCurenta>mediumTime-1800 and oraCurenta<mediumTime+1800):
                    numMatched = 0
                    for litEach, litNum in zip(reversed(lista[0]), reversed(plateNum)):
                        if litEach == litNum:
                            numMatched += 1
                    if numMatched / lungimeNumar > 0.75:
                        print("Deschide Bariera")
                        break
                    else:
                        print("Accesul nu este permis")

    def securitateRidicata(self,plateNum):
        self.myCursor.execute("SELECT numere_ID FROM numereinmat WHERE Acces=1 AND NumarInmatriculare=\'" +
                              plateNum + "\'")
        answer=self.myCursor.fetchone()
        if answer == None:
            return "False"
        else:
            return answer

    def checkPlateExists(self,plateNum,securityLevel):


        if securityLevel==0:
            self.securitateMica(plateNum)
        if securityLevel==1:
            self.securitateMedie(plateNum)
        if securityLevel==2:
            self.securitateRidicata(plateNum)

    def getInfoLastEntered(self):
        query="SELECT NumarInmatriculare,model,ProducatorMasina,culoare,Nume,Prenume,Functie,Data,Ora"\
        " FROM  tranzitii INNER JOIN numereinmat"\
		" ON tranzitii.numereInmat_numere_ID=numereinmat.numere_ID INNER JOIN culori"\
        " ON numereinmat.culori_culori_ID=culori.culori_ID INNER JOIN persoane"\
        " ON numereinmat.persoane_persoane_ID=persoane.persoane_ID "\
        "ORDER BY tranzitii.Data asc ,Ora asc"
        self.myCursor.execute(query)
        answer =self.myCursor.fetchall()
        return answer

    def getCarIdByNumberPlate(self,numarInmatriculare):
        query="SELECT numereinmat.numere_ID "\
              "FROM numereinmat "\
               "WHERE numereinmat.NumarInmatriculare="+"\'"+numarInmatriculare[0]+"\'"
        self.myCursor.execute(query)
        answer=self.myCursor.fetchone()
        return answer[0]

    def adaugaIntrareInBaza(self,idNumar):
        now=datetime.datetime.now()
        curentDate=str(now.year)+'-'+str(now.month)+'-'+str(now.day)
        curentHour=str(now.hour)+':'+str(now.minute)+':'+str(now.second)
     #   idNumar=self.getCarIdByNumberPlate(numarInmatriculare)
        query="INSERT INTO tranzitii VALUES (NULL ,\'"+curentDate+"\',"+str(idNumar[0])+",\'"+curentHour+"\')";
        self.myCursor.execute(query)
        self.mydb.commit()



    def __del__(self):
        self.mydb.close()


