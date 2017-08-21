Feature: Calculate entire credit cost
  Scenario: Check loan without changing conditins during paying, small number of instalments
    Given 20.05.2018 - Kredyt hipoteczny w kwocie 76239 PLN, 9 ratach, 5.22% prowizji i 4.11% WIBORu
    When Testuj:
      | type                                      | date        | value     |
    Then Całkowity koszt kredytu powinien wynieść 79233.39 PLN

  Scenario: Check loan without changing conditins during paying, big number of instalments
    Given 20.05.2018 - Kredyt hipoteczny w kwocie 761239 PLN, 40 ratach, 3.62% prowizji i 8.11% WIBORu
    When Testuj:
      | type                                      | date        | value     |
    Then Całkowity koszt kredytu powinien wynieść 923402.62 PLN

  Scenario: Check loan with paying extra money
    Given 20.05.2018 - Kredyt hipoteczny w kwocie 10000 PLN, 10 ratach, 5% prowizji i 5% WIBORu
    When Testuj:
      | type                                      | date        | value     |
      | Od teraz będę nadpłacać kredyt w kwocie   | 1.09.2018   | 1000      |
    Then Całkowity koszt kredytu powinien wynieść 10362.17 PLN

  Scenario: Check loan with WIBOR decrease
    Given 20.05.2018 - Kredyt hipoteczny w kwocie 10000 PLN, 2 ratach, 5% prowizji i 5% WIBORu
    When Testuj:
      | type                                      | date        | value     |
      | Zmiana WIBOR                              | 11.06.2018  | 0         |
    Then Całkowity koszt kredytu powinien wynieść 10104.25 PLN

  Scenario: Check loan with WIBOR increase
    Given 20.05.2018 - Kredyt hipoteczny w kwocie 10000 PLN, 2 ratach, 5% prowizji i 5% WIBORu
    When Testuj:
      | type                                      | date        | value     |
      | Zmiana WIBOR                              | 11.06.2018  | 10        |
    Then Całkowity koszt kredytu powinien wynieść 10146.09 PLN
