ESERCIZIO 3

Generare codice intermedio per il seguente frammento di Python:
	
	* costanti intere e booleane
	* le espressioni sono solo espressioni aritmetiche (operatori +,-,*,/,% ; / e` la 
          divisione intera); espressioni booleane (and, or, not); espressioni di confronto 
          (>,<, >=, <=, !=, ==)
	* i comandi sono (a) assegnamento (unico formato possibile "a = exp"), 
          (b) condizionale, (c) invocazione di funzioni, (d) blocchi, ma senza dichiarazioni di 
          funzioni [quindi non ci sono funzioni annidate]
	* ci sono funzioni ricorsive ma non mutuamente ricorsive
	* ATTENZIONE: e` possibile accedere a variabili non locali.

	 
ESEMPI DI CODICI DA VERIFICARE

def prime_num(num, div):
    if (num >=1):
        if (div*div <= num/2):
            if (num % div) == 0:
                return False
            else:
                return prime_num(num,div+1)
        else:
          return True
    else:
        return False
    
print(prime_num(29,2))

=====

num = 29

def prime_numBIS(div):
    if (num >=1):
        if (div*div <= num/2):
            if (num % div) == 0:
                return False
            else:
                return prime_numBIS(div+1)
        else:
          return True
    else:
        return False
    
print(prime_numBIS(2))

=====

def fibonacci(n):
    if ((n == 0) or (n == 1)):
        return 1
    else:
        return (fibonacci(n-1) + fibonacci(n-2))
    
print(fibonacci(6))

=====

def fibonacciIT(n,a,b):
    if ((n == 0) or (n == 1)):
        return a
    else:
        return (fibonacciIT(n-1, a+b, a))
   
print(fibonacciIT(6,1,1))

=====
