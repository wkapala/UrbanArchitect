# Urban Architect 2025 🏙️

## Opis Gry
Urban Architect to symulator urbanistyczny, w którym wcielasz się w rolę miejskiego planisty. Twoim zadaniem jest przekształcenie surowego terenu w nowoczesną, dobrze zorganizowaną metropolię. Balansuj potrzeby mieszkańców, środowiska, inwestorów oraz lokalnych społeczności.

## 🎯 Cel Gry
Stwórz stabilną strukturę miejską spełniającą następujące warunki:
- **Zadowolenie mieszkańców** > 75%
- **Równowaga budżetowa** (przychody ≥ wydatki)
- **Zagospodarowanie przestrzeni** ≥ 80% terenów miejskich

Wszystko to przed upływem 24 miesięcy (w trybie sandbox brak limitu czasowego).

## 🎮 Mechanika Gry

### Cykl Rozgrywki
Gra podzielona jest na miesięczne cykle. W każdym miesiącu:
1. **Planowanie** - projektuj strefy i budynki
2. **Zarządzanie** - kontroluj budżet i reaguj na wydarzenia
3. **Analiza** - sprawdzaj raporty i statystyki
4. **Rozwój** - obserwuj jak miasto ewoluuje

### Typy Stref 🗺️
- **[R] Mieszkalna** - domy dla mieszkańców (100 zł)
- **[C] Komercyjna** - sklepy, biura (150 zł)
- **[I] Przemysłowa** - fabryki, magazyny (200 zł)
- **[P] Park** - tereny zielone (50 zł)
- **[#] Droga** - infrastruktura transportowa (20 zł)
- **[S] Specjalna** - budynki użyteczności publicznej

### Budynki Specjalne 🏛️
- **Szkoła** (500 zł) - zwiększa edukację i szczęście
- **Szpital** (800 zł) - zapewnia opiekę zdrowotną
- **Muzeum** (600 zł) - podnosi kulturę i turystykę

Każdy budynek można ulepszać do poziomu 3, zwiększając jego zasięg i efektywność.

### System Wpływów 📊
Każda strefa i budynek wpływa na otoczenie:
- **Pozytywne**: szczęście, edukacja, zdrowie, kultura
- **Negatywne**: zanieczyszczenie, hałas
- **Neutralne**: miejsca pracy, dostępność

Wpływ maleje wraz z odległością od źródła.

### Budżet Miejski 💰
**Przychody:**
- Podatki od stref mieszkalnych, komercyjnych i przemysłowych
- Podatek od mieszkańców
- Dotacje i inwestycje

**Wydatki:**
- Utrzymanie infrastruktury
- Koszty budynków specjalnych
- Konsultacje społeczne

### Reputacja 🏛️
Twoja reputacja (0-100) wpływa na:
- Zainteresowanie inwestorów
- Migracje ludności
- Ściągalność podatków
- Dostępność specjalnych wydarzeń

### Wydarzenia 🎲
Losowe wydarzenia mogą pomóc lub zaszkodzić:
- **Społeczne**: protesty, festiwale
- **Ekonomiczne**: oferty inwestorów, kryzysy
- **Polityczne**: dotacje unijne, wybory
- **Środowiskowe**: katastrofy, inicjatywy ekologiczne

## 🏗️ Scenariusze

### 1. Miasto nad rzeką (Łatwy)
- Naturalna bariera w postaci rzeki
- Mosty jako kluczowe punkty komunikacyjne
- Łatwiejsze planowanie dzielnic

### 2. Teren górzysty (Średni)
- Ograniczona przestrzeń do zabudowy
- Wyzwania komunikacyjne
- Bonusy turystyczne

### 3. Teren poprzemysłowy (Trudny)
- Wysokie początkowe zanieczyszczenie
- Stara infrastruktura do modernizacji
- Niezadowoleni mieszkańcy

### 4. Sandbox
- Brak ograniczeń czasowych
- Pełna swoboda planowania
- Idealne do eksperymentowania

## 📂 Struktura Techniczna

```
main/
  └── UrbanArchitect.java        # Główna klasa startowa

city/                            # Zarządzanie miastem
  ├── CityMap.java              # Mapa miasta (grid)
  ├── CityStats.java            # Statystyki miasta
  ├── GameState.java            # Stan gry (zapis/odczyt)
  └── GameService.java          # Główna logika gry

zones/                           # System stref
  ├── Zone.java                 (interfejs)
  ├── ZoneType.java             (enum)
  └── CityZone.java             # Implementacja strefy

buildings/                       # Budynki specjalne
  ├── Building.java             (interfejs)
  ├── BuildingType.java         (enum)
  ├── AbstractBuilding.java     (abstrakcyjna)
  ├── School.java
  ├── Hospital.java
  └── Museum.java

events/                          # System wydarzeń
  ├── Event.java                (interfejs)
  ├── EventType.java            (enum)
  ├── EventManager.java
  ├── ProtestEvent.java
  ├── InvestorEvent.java
  └── EUGrantEvent.java

budget/                          # System finansowy
  ├── BudgetManager.java
  ├── Transaction.java
  └── TransactionType.java      (enum)

reputation/                      # Reputacja i ranking
  ├── ReputationManager.java
  ├── ScoreEntry.java
  └── ScoreManager.java

utils/                           # Narzędzia pomocnicze
  ├── InputUtils.java           # Obsługa wejścia
  ├── MapRenderer.java          # Renderowanie mapy
  └── ASCIIArt.java            # Grafika ASCII
```

## 🎓 Wykorzystane Koncepcje Programowania

### Programowanie Obiektowe
- **Interfejsy**: `Zone`, `Building`, `Event` - definiują kontrakty
- **Klasy abstrakcyjne**: `AbstractBuilding` - współdzielona logika
- **Hermetyzacja**: wszystkie pola prywatne z getterami/setterami
- **Polimorfizm**: różne typy budynków/wydarzeń z wspólnym interfejsem

### Wzorce Projektowe
- **Strategy Pattern**: różne typy stref i budynków
- **Manager Pattern**: `BudgetManager`, `ReputationManager`, `EventManager`
- **State Pattern**: `GameState` do zapisu/wczytywania
- **Observer Pattern**: strefy reagują na zmiany w otoczeniu

### Java Collections
- `Map<String, Integer>` - przechowywanie wpływów i statystyk
- `List<Building>` - zarządzanie budynkami
- `ArrayList<Event>` - pula wydarzeń
- `HashMap` - agregacja danych budżetowych

### Java I/O
- Serializacja do zapisu stanu gry
- Zapis/odczyt rankingu do pliku tekstowego
- Obsługa błędów I/O

### Inne
- Enumeracje dla typów (bardziej bezpieczne niż stałe)
- LocalDateTime dla znaczników czasu
- Random dla wydarzeń losowych
- Scanner z walidacją dla bezpiecznego wejścia

## 🎮 Instrukcja Gry

### Podstawowe Strategie
1. **Balans stref** - zachowaj proporcje między różnymi typami
2. **Infrastruktura społeczna** - szkoły i szpitale są kluczowe
3. **Zieleń miejska** - parki kompensują negatywne efekty przemysłu
4. **Budżet** - nie wydawaj wszystkiego od razu
5. **Reputacja** - konsultacje społeczne się opłacają

### Wskazówki
- Przemysł generuje dochody ale obniża zadowolenie
- Parki są tanie i bardzo efektywne
- Budynki specjalne mają duży zasięg wpływu
- Wydarzenia mogą zmienić bieg gry
- Dotrzymuj obietnic składanych mieszkańcom

### Częste Błędy
- Zbyt szybka ekspansja = bankructwo
- Ignorowanie szczęścia = exodus mieszkańców
- Brak równowagi = niestabilne miasto
- Zapominanie o infrastrukturze = stagnacja

## 🏆 System Punktacji

Punkty przyznawane za:
- Osiągnięcie celu (1000 pkt)
- Szybkość ukończenia (50 pkt/miesiąc)
- Rozwój miasta (10 pkt/%)
- Szczęście mieszkańców (5 pkt/%)
- Stan budżetu
- Reputacja (10 pkt/poziom)

Mnożniki trudności:
- Teren górzysty: x1.5
- Teren poprzemysłowy: x2.0

## 🚀 Uruchomienie

1. Utwórz projekt Java w IntelliJ IDEA
2. Skopiuj wszystkie pliki do odpowiednich pakietów
3. Uruchom klasę `UrbanArchitect` z pakietu `main`
4. Ciesz się grą!

## 📝 Podsumowanie

Urban Architect to więcej niż gra - to symulator rzeczywistych wyzwań urbanistycznych. Uczy planowania przestrzennego, zarządzania zasobami i podejmowania trudnych decyzji. Każda rozgrywka jest inna dzięki losowym wydarzeniom i różnym strategiom rozwoju.

**Zbuduj miasto swoich marzeń - lub przeżyj jego upadek!** 🏙️
