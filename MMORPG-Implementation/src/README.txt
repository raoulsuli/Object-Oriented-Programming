Sulimovici Raoul-Renatto 321CD

Proiectul contine 3 pachete cu cod sursa : Utils, Main, Heroes.

Utils:

Data-> Structura care retine date de tipul String,int,int. Am folosit-o pentru a salva initiala eroului si pozitia initiala a acestuia.

HeroType si LocationType contin enum-uri pentru tipurile de eroi/locatii
-------------------------------
Main:

Main-> Apelurile de metode pentru a rula

GameInputLoader-> Citeste si scrie din fisiere

GameInput-> Salveaza datele din fisiere in structura

Game-> Logica jocului scrisa in metoda play, care este apelata in main. Aici se vor initializa eroii si se va itera prin 2 for-uri de la 0
la numarul de runde si numarul de playeri. Se va verifica daca eroul este in imposibilitate de miscare, si, in caz negativ, se vor face mutarile
corespunzatoare. Dupa aceea se va itera din nou in arraylist-ul de eroi, unde verificam daca eroii sunt in viata si daca se afla pe aceeasi pozitie.
Dupa care se desfasoara lupta efectiva, se aplica damage-ul si se updateaza xp-ul/lvl-ul.
----------------------------------
Heroes:

HeroFactory-> se creeaza eroii
Hero-> o clasa abstracta care contine campurile folositoare eroilor si metode (createHeroes, getLocationType - returneaza tipul de teren pe care se afla eroul, 
addLandMultiplier - verifica ce tip de erou primeste si terenul pe care se afla si adauga bonusul, dealDamage - scade hp-ul victimei, updateXp, updateLevel,
checkDead- verifica daca eroul este ucis si il scoate de pe harta, checkDoT - verifica daca eroul primeste damage over time). 
	Hero contine si 5 metode abstracte : 4 de attack pentru fiecare tip de erou si una isAttacked. Voi folosi aceste metode pentru Double Dispatch.
Clasele Rogue/Wizard/Pyromancer/Knight vor extinde clasa Hero. Acestea vor implementa metodele de attack si isAttacked si isi vor implementa abilitatile.
---------------------------------- 