Futtatás és fordítási kézikönyv:

A program futtatásához szükséges állományok egy szülő mappában helyezkednek el. Ezen a mappán belül a következő elemek vannak:

bin - mappa
tests - mappa
elvart - mappa
JATEK.bat - futtatható állomány
COMPARE_FILES.bat - futtatható állomány
Readme.txt - szöveges állomány

Az eredményes teszteléshez az alábbi lépéseket sorrendben kell végrehajtani. Csak ekkor garantálható, hogy a készített JATEK illetve tesztelő program helyes kimenetet ad.

TESZTEK GENERÁLÁSA BEMENETI FILE ALAPJÁN:

1. Futtassa a JATEK.bat file-t.
2. A felugró konzolon írja be a kívánt tesz-file nevét az alábbi módon:
   Például ha a 3-adik tesztet akarjuk generálni.
	test3
3. Ezt követően a tests mappába a program generál egy szöveges állományt a következő formátumban:
	*bemeneti_név*_out.txt
		A példánál maradva: 
			test3_out.txt
4. Adjon be másik tesztesetet, vagy zárja be a JATEK.bat file-t az “exit” parancs begépelésével (Idézőjelek nélkül).


GENERÁLT TESZTEK ÖSSZEHASONLÍTÁSA PROGRAMMAL:

Most, hogy megvan a generált kimenetünk, össze kell hasonlítani az előre elkészített elvárt kimeneti állományokkal. Erre szolgál a COMPARE_FILES.bat
1. Futtassa a COMPARE_FILES.bat állományt.
2. A felugró konzolon lévő szintaktikát felhasználva adja meg az összehasonlítandó file-ok nevét.
	*testfilenev*_out  *testfilenev*_elvart
		Például a most generált 3-asat hasonlítsuk össze az előre elkészített elvárt kimenettel az alábbi parancs kiadásával:
	test3_out test3_elvart
3. Ha a két file azonos, akkor megjelenik a következő üzenet “Teljes egyezés”
4. Amennyiben valamelyik file eltér a másiktól akkor mindkét file kiírásra kerül az első talált hibánál. Amennyiben ez kijavításra kerül a teszt ismét lefuttatható.
5. Amennyiben kész vagyunk a teszteléssel zárjuk be a programot az “exit” parancsal. (Idézőjelek nélkül)

Mappák: 
	tests - Ebben a mappában tárolódik a bemeneti tesztek (test1.txt, test2.txt …) valamint a tesztek által generált kimeneti file-ok (test1_out.txt, test2_out.txt).
	elvárt - Ebben a mappában az előre elkészített elvárt kimeneti file-ok kerülnek tárolásra. (test1_elvart.txt, test2_elvart.txt).
	bin - Ebben a mappában a program futásához szükséges osztálydeklarációk és implementációjuk található.
