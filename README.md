*********************************************************************************************
	  __          __       ___                       __                         
	 /\ \        /\ \__  /'___`\                    /\ \__         __           
	 \_\ \    ___\ \ ,_\/\_\ /\ \    ___ ___      __\ \ ,_\  _ __ /\_\    ___   
	 /'_` \  / __`\ \ \/\/_/// /__ /' __` __`\  /'__`\ \ \/ /\`'__\/\ \  /'___\ 
	/\ \L\ \/\ \L\ \ \ \_  // /_\ \/\ \/\ \/\ \/\  __/\ \ \_\ \ \/ \ \ \/\ \__/ 
	\ \___,_\ \____/\ \__\/\______/\ \_\ \_\ \_\ \____\\ \__\\ \_\  \ \_\ \____\
	 \/__,_ /\/___/  \/__/\/_____/  \/_/\/_/\/_/\/____/ \/__/ \/_/   \/_/\/____/

*********************************************************************************************
Traduttore di file .dot in file .metric<br>

Lo scopo del traduttore è generare dei file testuali che contengono le stesse informazioni contenute in un file .dot, in un formato di più facile comprensione per l’utente. <br>

L’algoritmo sviluppato analizza i file DOT generati dal tool Doxygen e genera cinque file ".metric". <br>

Accetta in input due parametri, sotto forma di stringhe: <br>
• path relativo alla directory di input <br>
• path relativo alla directory di output <br>

Vengono generati cinque file: <br>
• "chiamate.metric", contenente per ogni metodo la lista di chiamate dirette e indirette ad esso relative <br>
• "coppie.metric", composto da una coppia "chiamante –> chiamato" per ogni chiamata diretta <br>
• "trees.metric", contenete un albero delle chiamate per ogni singolo metodo <br>
• "global_tree.metric", contenete un albero generale, privo di ridondanze, con tutte le chiamate dirette e indirette dei metodi <br>
• "report.metric" il riepilogo dei dati ottenuti dall’analisi effettuata <br>
