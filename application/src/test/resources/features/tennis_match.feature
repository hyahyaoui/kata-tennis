Feature: Removing an item from order
  I want to be able to remove an item from a current order.

  Scenario: Tennis game without deuce rule
    Given A tennis game started with the following information
      | firstPlayerName  | player1 |
      | secondPlayerName | player2 |
    When  The following point are won
      | player1   |
      | player1   |
      | player1   |
      | player2   |
      | player2   |
      | player2   |
      | player2   |
    Then Player 'player2' should win the game