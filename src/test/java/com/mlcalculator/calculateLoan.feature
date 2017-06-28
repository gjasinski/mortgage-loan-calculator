Feature: Calculate entire credit cost, you can put here your loan to calculate
  Scenario: You can type here your conditions
    Given 20.05.2018 - Kredyt hipoteczny w kwocie 100000 PLN, 12 ratach, 5% prowizji i 5% WIBORu
    When Oblicz:
      | type                                      | date        | value     |
      | Zmiana WIBOR                              | 1.07.2019   | 1.75      |
      | Od teraz będę nadpłacać kredyt w kwocie   | 1.08.2019   | 3000      |
      | Zmiana WIBOR                              | 20.08.2020  | 2         |
      | Od teraz będę nadpłacać kredyt w kwocie   | 1.04.2020   | 0         |
      | Od teraz będę nadpłacać kredyt w kwocie   | 1.02.2022   | 1200      |
      | Zmiana WIBOR                              | 1.04.2023   | 2.15      |
