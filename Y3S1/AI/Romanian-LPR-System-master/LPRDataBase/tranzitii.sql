SELECT NumarInmatriculare,model,ProducatorMasina,culoare,Nume,Prenume,Functie,Data,Ora
	FROM  tranzitii INNER JOIN numereinmat 
		ON tranzitii.numereInmat_numere_ID=numereinmat.numere_ID INNER JOIN tipmasina
		ON  numereinmat.tipMasina_tipMasina_ID=tipmasina.tipMasina_ID INNER JOIN modelmasina 
		ON tipmasina.modelMasina_idModel=modelmasina.idModel INNER JOIN producatorimasini 
		ON tipmasina.producatoriMasini_IdProducator= producatorimasini.IdProducator INNER JOIN culori 
        ON numereinmat.culori_culori_ID=culori.culori_ID INNER JOIN persoane
        ON numereinmat.persoane_persoane_ID=persoane.persoane_ID 
        