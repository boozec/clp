Python3 Package

* nella cartella src ci sono lexer e parser per Python3 semplificati per il corso 
di CLP (non ci interessa analizzare qualunque programma Python)

* sempre nella cartella src ci sono i file Python3LexerBase.java e 
Python3ParserBase.java. Questi non sono nostri e vanno tenuti nella stessa cartella.

* Attraverso Python3Parser.g4 è possibile generare il parser tramite ANTLR e quindi tutti
i file relativi (Visitor, Listener, etc.)

* Nella cartella src ci sono 2 file che ha fatto Marco Bertoni per creare l'albero sintattico 
di un programma che si trova in progs/test.py (il file Main.java) oppure per creare gli 
alberi sintattici di tutti i programmi nella cartella progs (il file ParseAll.java). Gli 
alberi sintattici sono visualizzati nella cartella trees che verra` creata eseguendo i
due programmi. Abbiamo usato questi codici perche` ANTLR pare sia rotto su questi codici (non
genera alberi sintattici!)

* i programmi della cartella progs sono 870. Li abbiamo presi da un database di programmi
semplici e riusciamo a parsarli tutti. Le sottocartelle dont_care e unparsable_programs contengono programmi con feature che lasciam perdere (classi o lambdas) e programmi che
non riusciamo a parsare (abbiamo semplificato la sintassi per avere alberi piu` semplici: non
abbiam capito perche` non riusciamo ad analizzarli...)

Marco Bertoni, Cosimo Laneve