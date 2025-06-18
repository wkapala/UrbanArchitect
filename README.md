# Dokumentacja projektu nr 6 - Urban Architect


## Spis treści
[Wprowadzenie](#wprowadzenie)

[Podstawy gry](#podstawy-gry)

[Menu główne](#menu-główne)

[Scenariusze](#scenariusze)

[Zasoby i budżet](#zasoby-i-budżet)

[System stref](#system-stref)

[Budynki specjalne](#budynki-specjalne)

[Mechaniki gry](#mechaniki-gry)

[System zapisu](#system-zapisu)

[Szczegółowy opis klas](#szczegółowy-opis-klas)

[Podsumowanie](#podsumowanie)

---

## Autorzy
- Wojciech Kapała
- Bartłomiej Kot

## Wprowadzenie

Urban Architect to konsolowa gra symulacyjna polegające na zarządzaniu miastem. Zadaniem gracza jest przekształcenie surowego terenu w sprawnie działące miasto poprzez budowanie budynków, realizowanie potrzeb mieszkańców i wiele innych zadań. 
Gra jest projektem wykonanym na zaliczenie przedmiotu "Programowanie w języku Java" na kierunku informatyka na Politechnice Krakowskiej

### Wymagania
- Java 17 lub nowsza
- Terminal do uruchomienia gry

### Polecenie uruchamiające
```bash
java main.UrbanArchitect
```


## Podstawy gry

### Cel gry
Stwórz stabilne miasto spełniającą następujące warunki przed upływem limitu czasowego:
- **Zadowolenie mieszkańców** powyzej 75%
- **Dodatni budżet** przychody muszą być większe niż wydatki
- **Zagospodarowanie przestrzeni** ≥ 40% zabudowania terenu

### Warunki przegranej
- Bankructwo (budżet < -5000 zł)
- Bunt mieszkańców (zadowolenie poniżej 20%)
- Przekroczenie limitu czasowego


## Menu główne

Po uruchomieniu gry dostępne są opcje:

### 1. Nowa gra
- Wybór scenariusza i rozpoczęcie rozgrywki

### 2. Wczytaj grę
- Wczytanie zapisanego stanu gry
- Możliwe jest przechowywanie 1 zapisu

### 3. Ranking
- 20 najlepszych wyników
- Statystyki zapisywane i wczytywane z pliku

### 4. Instrukcja
- Wyjaśnienie celu i podstawowej mechaniki

### 5. Wyjście
- Zakończenie programu


## Scenariusze

### 1. Miasto nad rzeką
- **Opis**: Naturalna bariera w postaci rzeki dzieli miasto
- **Budżet startowy**: 3000 zł
- **Limit czasowy**: 30 tur

### 2. Teren górzysty
- **Opis**: Góry w rogach mapy ograniczają przestrzeń
- **Budżet startowy**: 2000 zł
- **Limit czasowy**: 25 tur

### 3. Teren poprzemysłowy
- **Opis**: Stare fabryki wymagają rewitalizacji
- **Budżet startowy**: 1500 zł
- **Limit czasowy**: 20 tur

### 4. Sandbox - tryb kreatywny
- **Opis**: Pusta mapa
- **Budżet startowy**: 5000 zł
- **Limit czasowy**: 999 tur

---

## Zasoby i budżet

### Przychody miesięczne:
- **Podatki mieszkaniowe**: 10 zł/strefę
- **Podatki handlowe**: 20 zł/strefę
- **Podatki przemysłowe**: 30 zł/strefę
- **Podatek od mieszkańców**: 2 zł/osobę

### Wydatki miesięczne:
- **Utrzymanie dróg**: 2 zł/drogę
- **Utrzymanie parków**: 5 zł/park
- **Budynki specjalne**: 50-100 zł/budynek

### Dodatkowe eventy
- **Dotacje unijne**: do 3000 zł (wymaga spełnienia warunków)
- **Inwestycje prywatne**: 2000-2500 zł (mogą obniżyć reputację)
- **Konsultacje społeczne**: -300 zł (+5 reputacji)


## System stref

### Typy stref i ich koszty

| Symbol | Typ | Koszt | Wymaga drogi | 
|--------|-----|-------|--------------|
| R | Mieszkalna | 100 zł | T |
| C | Komercyjna | 150 zł | T |
| I | Przemysłowa | 200 zł | T |
| P | Park | 50 zł | N |
| # | Droga | 20 zł | N |
| S | Specjalna | Zmienny | T |

### System wpływów

Każda strefa wpływa na otoczenie w promieniu do 5 kratek:

#### Strefy mieszkalne:
- **Generują**: populację (50 osób/strefę)
- **Wpływ**: neutralny

#### Strefy komercyjne:
- **Generują**: miejsca pracy (30/strefę), szczęście (+5)
- **Wpływ**: hałas (+3)

#### Strefy przemysłowe:
- **Generują**: miejsca pracy (50/strefę), wysokie przychody
- **Wpływ**: zanieczyszczenie (+8), hałas (+5), szczęście (-10)

#### Parki:
- **Generują**: szczęście (+15)
- **Wpływ**: redukcja zanieczyszczenia (-5), redukcja hałasu (-3)


## Budynki specjalne

#### 1. Szkoła
- **Koszt budowy**: 500 zł
- **Utrzymanie**: 50 zł/miesiąc
- **Zasięg**: 3 kratki (+1 na poziom)
- **Wpływ**:
  - Edukacja: +20
  - Szczęście: +10
  - Bezpieczeństwo: +5
  - Hałas: +2
- **Poziomy**:
  - Poziom 1: Szkoła podstawowa
  - Poziom 2: Szkoła średnia
  - Poziom 3: Kompleks edukacyjny

#### 2. Szpital
- **Koszt budowy**: 800 zł
- **Utrzymanie**: 100 zł/miesiąc
- **Zasięg**: 4 kratki (+1 na poziom)
- **Wpływ**:
  - Zdrowie: +25
  - Szczęście: +15
  - Bezpieczeństwo: +10
  - Hałas: +5
- **Poziomy**:
  - Poziom 1: Przychodnia
  - Poziom 2: Szpital miejski
  - Poziom 3: Centrum medyczne

#### 3. Muzeum
- **Koszt budowy**: 600 zł
- **Utrzymanie**: 60 zł/miesiąc
- **Zasięg**: 5 kratek (+1 na poziom)
- **Wpływ**:
  - Kultura: +30
  - Szczęście: +20
  - Edukacja: +15
  - Turystyka: +10
- **Poziomy**:
  - Poziom 1: Muzeum lokalne
  - Poziom 2: Muzeum miejskie
  - Poziom 3: Muzeum narodowe


## Mechaniki gry

### 1. System szczęścia

#### Czynniki zwiększające szczęście:
- Parki: +15
- Budynki specjalne: +10 do +20
- Niskie podatki: +3 do +5
- Wysokie racje żywności: +5
- Niska cena energii: +5

#### Czynniki zmniejszające szczęście:
- Przemysł: -10
- Wysokie podatki: -5 do -10
- Brak jedzenia: -20
- Brak energii: -10
- Zanieczyszczenie: -2 za punkt
- Hałas: -1 za punkt

### 2. System populacji

#### Wzrost populacji:
- Bazowa populacja: 100
- +50 za każdą strefę mieszkalną
- Migracja zależna od szczęścia:
  - 100-70: +3 osoby/miesiąc
  - 70-50: +1 osoba/miesiąc
  - 50-30: -1 osoba/miesiąc
  - 30-0: -2 osoby/miesiąc


#### Zatrudnienie:
- Strefy komercyjne: 30 miejsc pracy
- Strefy przemysłowe: 50 miejsc pracy
- Bezrobocie > 10% = -10 szczęścia

### 3. System reputacji

#### Poziomy reputacji (0-100):
- **80-100**: 
  - Zainteresowanie inwestorów: 200%
  - Bonus migracji: +20%
  - Ściągalność podatków: 120%
- **60-79**: 
  - Zainteresowanie inwestorów: 150%
  - Bonus migracji: +10%
  - Ściągalność podatków: 110%
- **40-59**: 
  - Zainteresowanie inwestorów: 100%
  - Bonus migracji: 0%
  - Ściągalność podatków: 100%
- **20-39**: 
  - Zainteresowanie inwestorów: 70%
  - Bonus migracji: -10%
  - Ściągalność podatków: 90%
- **0-19**: 
  - Zainteresowanie inwestorów: 50%
  - Bonus migracji: -20%
  - Ściągalność podatków: 70%

### 4. Wymóg dostępu do drogi
- Wszystkie budynki (oprócz parków i dróg) muszą być budowane przy drodze
- "Przy drodze" oznacza bezpośrednio sąsiadujące (góra, dół, lewo, prawo)
- Automatyczne sprawdzanie przed budową

## Wydarzenia losowe

Wydarzenia występują z 30% szansą od 3. miesiąca gry.

### 1. Protest mieszkańców (Społeczne) 👥
- **Warunek**: Zanieczyszczenie > 50
- **Opcje**:
  1. Konsultacje społeczne (-500 zł, +10 reputacji)
  2. Obietnice (-200 zł, +5 reputacji, obietnica do spełnienia)
  3. Ignorowanie (-15 reputacji)

### 2. Oferta inwestora (Ekonomiczne) 
- **Warunek**: Rozwój miasta > 10%
- **Opcje**:
  1. Akceptacja (+2000 zł, -5 reputacji)
  2. Negocjacje (wymaga reputacji > 70, +2500 zł)
  3. Odrzucenie (+5 reputacji)

### 3. Dotacja unijna (Polityczne) 🏛️
- **Warunek**: Zawsze może wystąpić
- **Opcje**:
  1. Złożenie wniosku (wymaga: zanieczyszczenie < 30, min. 3 parki)
     - Sukces: +3000 zł, +15 reputacji
     - Porażka: -5 reputacji
  2. Przygotowanie miasta
  3. Rezygnacja


## System zapisu

### Zapisywanie i wczytywanie gry
Możliwe jest ręczne zapisywanie stanu gry z menu dostępnego w trakcie rozgrywki. Zapis odbywa się do pliku i przechowany może być 1 zapis (ulega automatycznemu nadpisaniu). Wczytywanie gry odbywa się z menu głównego i odtwarza pełny stan rozgrywki.

### System rankingu
Gra oblicza wynik (score) na podstawie posiadanych statystyk (budżet, szczęście, reputracja oraz bonus za ukończenie gry) i zapisuje do listy rankingowej dostępnej w pliku .txt. 

## Szczegółowy opis klas

### 1. Pakiet `main`

#### UrbanArchitect.java
Główna klasa uruchamiająca grę.

Metody:
- `main(String[] args)` - punkt wejścia programu
- `showMainMenu()` - wyświetla menu główne
- `startNewGame()` - rozpoczyna nową grę
- `loadGame()` - wczytuje zapisaną grę
- `showRanking()` - wyświetla ranking
- `showInstructions()` - wyświetla instrukcję

### 2. Pakiet `city`

#### GameService.java
Główny silnik gry zarządzający logiką.

Elementy
- `gameState` - stan gry
- `cityMap` - mapa miasta
- `cityStats` - statystyki
- `budgetManager` - zarządzanie budżetem
- `reputationManager` - system reputacji
- `eventManager` - obsługa wydarzeń

Metody:
- `startGame()` - główna pętla gry
- `playerTurn()` - obsługa tury gracza
- `processEndOfMonth()` - przetwarzanie końca miesiąca
- `checkVictoryConditions()` - sprawdzanie warunków zwycięstwa
- `planZone()` - tryb ciągłego budowania

#### CityMap.java
Reprezentuje mapę miasta jako siatkę 20x15.

Metody:
- `generateScenario(int)` - generuje mapę według scenariusza
- `buildZone(x, y, type)` - buduje strefę
- `buildSpecialBuilding(x, y, building)` - buduje budynek specjalny
- `isNextToRoad(x, y)` - sprawdza dostęp do drogi
- `calculateTotalInfluence(x, y)` - oblicza wpływ na pozycji
- `getDevelopmentPercentage()` - procent zagospodarowania

#### CityStats.java
Przechowuje i oblicza statystyki miasta.

Metody:
- `calculateStats(CityMap)` - przelicza wszystkie statystyki
- `generateReport()` - generuje raport tekstowy

#### GameState.java
Obsługuje zapis i odczyt stanu gry.

**Implementuje**: `Serializable`

Metody:
- `saveToFile()` - zapisuje stan do pliku
- `loadFromFile()` - wczytuje stan z pliku

### 3. Pakiet `zones`

#### Zone.java (interfejs)
Definiuje kontrakt dla wszystkich stref.

Metody:
- `getType()` - zwraca typ strefy
- `calculateInfluence()` - oblicza wpływ
- `canBuild()` - czy można budować

#### ZoneType.java (enum)
Definiuje wszystkie typy stref.

**Wartości**: EMPTY, RESIDENTIAL, COMMERCIAL, INDUSTRIAL, PARK, SPECIAL, ROAD, WATER, MOUNTAIN

#### CityZone.java
Implementacja pojedynczej strefy na mapie.

**Pola**:
- `type` - typ strefy
- `building` - opcjonalny budynek specjalny
- `developmentLevel` - poziom rozwoju (0-5)
- `monthsActive` - liczba miesięcy aktywności

### 4. Pakiet `buildings`

#### Building.java (interfejs)
Kontrakt dla wszystkich budynków.

Metody:
- `getName()` - nazwa budynku
- `getBuildCost()` - koszt budowy
- `getMaintenanceCost()` - koszt utrzymania
- `getInfluence()` - mapa wpływów
- `upgrade()` - ulepszenie budynku

#### AbstractBuilding.java
Abstrakcyjna klasa bazowa dla budynków.

**Implementuje**: wspólną logikę dla wszystkich budynków

#### School.java, Hospital.java, Museum.java
Konkretne implementacje budynków specjalnych.

### 5. Pakiet `events`

#### Event.java (interfejs)
Kontrakt dla wydarzeń.

Metody:
- `getName()` - nazwa wydarzenia
- `getOptions()` - dostępne opcje
- `handleChoice(choice, gameService)` - obsługa wyboru
- `canOccur(gameService)` - warunek wystąpienia

#### EventManager.java
Zarządza pulą wydarzeń i ich losowaniem.

### 6. Pakiet `budget`

#### BudgetManager.java
Zarządza finansami miasta.

Metody:
- `calculateMonthlyBudget()` - oblicza miesięczny budżet
- `canAfford(amount)` - czy stać na wydatek
- `spend(amount, description)` - wydaje pieniądze
- `addIncome(amount, description)` - dodaje przychód

#### Transaction.java
Reprezentuje pojedynczą transakcję.

### 7. Pakiet `reputation`

#### ReputationManager.java
System reputacji miasta.

Metody:
- `modifyReputation(change)` - zmienia reputację
- `getEffects()` - zwraca efekty reputacji

#### ScoreManager.java
Zarządza rankingiem wyników.

### 8. Pakiet `utils`

#### InputUtils.java
obsługa wejścia użytkownika.

Metody:
- `getInt(prompt, min, max)` - pobiera liczbę z zakresu
- `getString(prompt, minLen, maxLen)` - pobiera tekst
- `getYesNo(prompt)` - pobiera tak/nie

#### MapRenderer.java
Renderowanie mapy w konsoli.

Metody:
- `display(CityMap)` - wyświetla mapę
- `displayLegend()` - wyświetla legendę
- `generateProgressBar(percentage)` - generuje pasek postępu



