
# Specyfikacja wymagań

### Symulator obrotu kryptowalutami 
***
- Ewelina Prochownik
- Przemysław Krzempek
- Michał Salamon
###### 2021-03-22
***
**Spis treści**

- [1 Wprowadzenie 2](#_Toc67323117)
- [1.1 Cel projektu 2](#_Toc67323118)
- [1.2 Opis systemu 2](#_Toc67323119)
- [1.3 Słownik pojęć 2](#_Toc67323120)
- [2 Model procesów biznesowych 2](#_Toc67323121)
- [2.1 Obiekty biznesowe 2](#_Toc67323122)
- [2.2 Aktorzy biznesowi 2](#_Toc67323123)
- [3 Wymagania funkcjonalne 2](#_Toc67323124)
- [4 Wymagania niefunkcjonalne 5](#_Toc67323125)


# 1. Wprowadzenie
***
 
### 1.1 Cel projektu

Celem projektu jest śledzenie rynku kryptowalut oraz nauka zarządzania nimi na podstawie realnych notowań.

### 1.2 Opis systemu

W systemie będzie można zobaczyć aktualne notowania poszczególnych kryptowalut wraz z analizą zmiany ich wartości na podstawie określonej ilości czasu. Zarówno środki pieniężne jak i kryptowaluty są tylko wizualną reprezentacją co czyni narzędzie bezpiecznym do nauki ponieważ eliminuje ryzyko realnych strat. Zostaną wyświetlone także listy najwyższych wartości oraz największego lub najmniejszego spadku procentowego. Dla zalogowanych użytkowników udostępniona zostanie dodatkowa funkcja symulacji kupowania i sprzedawania kryptowalut. Dodatkową formą przyciągnięcia i utrzymania użytkowników może być klimat rywalizacji między nimi – w formie rankingów, porównywania wyników ze znajomymi.

### 1.3 Słownik pojęć

- Platforma – Strona internetowa posiadająca opisaną funkcjonalność
- Kryptowaluty – wirtualne waluty których wartość względem siebie odpowiada wartościom ze świata rzeczywistego
- Kurs kryptowaluty – Wartość kryptowaluty w przeliczeniu na daną walutę
- Portfel – Konto użytkownika na którym będą trzymane wirtualne środki i dokonywane będą wszystkie transakcje
- Użytkownik – osoba używająca portalu


# 2. Model procesów biznesowych
***
### 2.1 Obiekty biznesowe

- Kryptowaluta – cyfrowa waluta, która jest tworzona oraz przetrzymywania elektronicznie.
- Waluta - Wymienialne wartości symulujące prawdziwe waluty
- Wirtualny portfel – symulowana ilość doładowanych pieniędzy na koncie. Dzięki niemu można kupować oraz sprzedawać kryptowaluty.

### 2.2 Aktorzy biznesowi

- Użytkownik zalogowany
- Użytkownik niezalogowany

# 3. Wymagania funkcjonalne
***
| **ID** | 1 |
| --- | --- |
| **Nazwa** | Tworzenie konta |
| **Priorytet** | Wysoki |
| **Rola** | Użytkownik niezalogowany |
| **Opis** | Użytkownik może stworzyć nowe konto po podaniu i weryfikacji jego danych w systemie |

| **ID** | 2 |
| --- | --- |
| **Nazwa** | Logowanie użytkownika |
| **Priorytet** | Wysoki |
| **Rola** | Użytkownik niezalogowany |
| **Opis** | Użytkownik może zalogować się do systemu po wpisaniu poprawnych danych |

| **ID** | 3 |
| --- | --- |
| **Nazwa** | Wyświetlanie aktualnych notowań |
| **Priorytet** | Wysoki |
| **Rola** | Wszyscy |
| **Opis** | Użytkownik może wyświetlić listę notowań (także tylko wybranych) kryptowalut wraz z procentową informacją czy i o ile wartość wzrosła lub zmalała |

| **ID** | 4 |
| --- | --- |
| **Nazwa** | Wyświetlanie wykresów notowań |
| **Priorytet** | Wysoki |
| **Rola** | Wszyscy |
| **Opis** | Użytkownik ma możliwość wyświetlenia analizy notowań wybranych kryptowalut na wykresie. |

| **ID** | 5 |
| --- | --- |
| **Nazwa** | Wyświetlanie kryptowalut z najwyższą wartością |
| **Priorytet** | Średni |
| **Rola** | Wszyscy |
| **Opis** | Użytkownik może wyświetlić listę z dziesięcioma kryptowalutami o najwyższej wartości. |

| **ID** | 6 |
| --- | --- |
| **Nazwa** | Wyświetlanie kryptowalut z najwyższym procentowym wzroście w ostatnim czasie |
| **Priorytet** | Średni |
| **Rola** | Wszyscy |
| **Opis** | Użytkownik może wyświetlić listę z dziesięcioma kryptowalutami o najszybszym wzroście wartości w ostatnim czasie. |

| **ID** | 7 |
| --- | --- |
| **Nazwa** | Wyświetlanie kryptowalut z najwyższym procentowym spadku w ostatnim czasie |
| **Priorytet** | Średni |
| **Rola** | Wszyscy |
| **Opis** | Użytkownik może wyświetlić listę z dziesięcioma kryptowalutami o najszybszym spadku wartości w ostatnim czasie. |

| **ID** | 8 |
| --- | --- |
| **Nazwa** | Zarządzanie profilem |
| **Priorytet** | Średni |
| **Rola** | Użytkownik zalogowany |
| **Opis** | Użytkownik ma możliwość zmiany waluty, za którą kupuje kryptowaluty oraz zmiany hasła do konta |

| **ID** | 9 |
| --- | --- |
| **Nazwa** | Resetowanie portfela |
| **Priorytet** | Wysoki |
| **Rola** | Użytkownik zalogowany |
| **Opis** | Użytkownik ma możliwość zresetowania konta do stanu początkowego |

| **ID** | 10 |
| --- | --- |
| **Nazwa** | Symulacja kupowania kryptowalut |
| **Priorytet** | Wysoki |
| **Rola** | Użytkownik zalogowany |
| **Opis** | Użytkownik ma możliwość symulacji kupowania kryptowalut za pieniądze w wirtualnym portfelu |

| **ID** | 11 |
| --- | --- |
| **Nazwa** | Symulacja sprzedawania kryptowalut |
| **Priorytet** | Wysoki |
| **Rola** | Użytkownik zalogowany |
| **Opis** | Użytkownik ma możliwość symulacji sprzedawania kryptowalut za pieniądze w wirtualnym portfelu |

| **ID** | 12 |
| --- | --- |
| **Nazwa** | Sprawdzanie stanu konta |
| **Priorytet** | Wysoki |
| **Rola** | Użytkownik zalogowany |
| **Opis** | Użytkownik może sprawdzić ilość pieniędzy w wirtualnym portfelu |

| **ID** | 13 |
| --- | --- |
| **Nazwa** | Sprawdzanie własnych kryptowalut |
| **Priorytet** | Wysoki |
| **Rola** | Użytkownik zalogowany |
| **Opis** | Użytkownik może wyświetlić listę posiadanych kryptowalut |

| **ID** | 14 |
| --- | --- |
| **Nazwa** | Usuwanie konta |
| **Priorytet** | Wysoki |
| **Rola** | Użytkownik zalogowany |
| **Opis** | Użytkownik może usunąć swoje konto |

| **ID** | 15 |
| --- | --- |
| **Nazwa** | Dostęp do materiałów edukacyjnych |
| **Priorytet** | Niski |
| **Rola** | Wszyscy |
| **Opis** | Użytkownik ma dostęp do materiałów edukacyjnych dotyczących obrotu aktywami oraz inwestowaniem |

# 4. Wymagania niefunkcjonalne
***
| **Nazwa** | Podział odpowiedzialności systemów |
| --- | --- |
| **Priorytet** | Wysoki |
| **Opis** | Platforma musi być podzielona na część frontendową (Angular) i backendową (Java). Komunikacja będzie odbywała się za pomocą REST API. |

| **Nazwa** | Wielojęzyczność systemu |
| --- | --- |
| **Priorytet** | Niski |
| **Opis** | Wsparcie dla języka angielskiego lub innych |

| **Nazwa** | Integralność danych |
| --- | --- |
| **Priorytet** | Wysoki |
| **Opis** | Zabezpieczenie przed nieautoryzowaną zmianą danych przez użycie odpowiednich protokołów zabezpieczających |

| **Nazwa** | Łatwość użycia |
| --- | --- |
| **Priorytet** | Średni |
| **Opis** | Używanie platformy powinno być intuicyjne. Prostota interfejsu. |

| **Nazwa** | Autoryzowanie użytkownika |
| --- | --- |
| **Priorytet** | Wysoki |
| **Opis** | Wykorzystanie tokena JWT do autoryzowania użytkowników |

| **Nazwa** | Spójność wyglądu komponentów aplikacji |
| --- | --- |
| **Priorytet** | Średni |
| **Opis** | Utrzymanie spójnego wyglądu oraz motywu kolorystycznego aplikacji |

| **Nazwa** | Odświeżanie danych w czasie prawie rzeczywistym |
| --- | --- |
| **Priorytet** | Wysoki |
| **Opis** | Odświeżanie danych o aktualnym kursie kryptowalut co jedną minutę |
***
# 5. Model systemu
***
# 6. Schemat bazy danych(?)
***
# 7. Wersje
***
