Feature: Tennis Game

  Scenario: Tennis game simple case
    Given A tennis set started with the following information
      | firstPlayerName  | player1 |
      | secondPlayerName | player2 |
    And A tennis game with id 'game' started
    When  The following point are won for 'game'
      | player1   |
      | player1   |
      | player2   |
      | player2   |
      | player2   |
      | player2   |
    Then Player 'player2' should win the game 'game'

 Scenario: Tennis game with advantage rule
   Given A tennis set started with the following information
     | firstPlayerName  | player1 |
     | secondPlayerName | player2 |
   And A tennis game with id 'game' started
   When  The following point are won for 'game'
     | player1   |
     | player1   |
     | player1   |
     | player2   |
     | player2   |
     | player2   |
     | player2   |
   Then Second player should have advantage for 'game'

 Scenario: Tennis game with deuce rule
   Given A tennis set started with the following information
     | firstPlayerName  | player1 |
     | secondPlayerName | player2 |
   And A tennis game with id 'game' started
   When  The following point are won for 'game'
     | player1   |
     | player1   |
     | player1   |
     | player2   |
     | player2   |
     | player2   |
     | player2   |
     | player1   |
   Then deuce rule should be applied for 'game'

 Scenario: Player with advantage win when scoring point
   Given A tennis set started with the following information
     | firstPlayerName  | player1 |
     | secondPlayerName | player2 |
   And A tennis game with id 'game' started
   When  The following point are won for 'game'
     | player1   |
     | player1   |
     | player1   |
     | player2   |
     | player2   |
     | player2   |
     | player2   |
     | player2   |
   Then Player 'player2' should win the game 'game'

 Scenario: Advantage move other player and win
   Given A tennis set started with the following information
     | firstPlayerName  | player1 |
     | secondPlayerName | player2 |
   And A tennis game with id 'game' started
   When  The following point are won for 'game'
     | player1   |
     | player1   |
     | player1   |
     | player2   |
     | player2   |
     | player2   |
     | player2   |
     | player1   |
     | player1   |
     | player1   |
   Then Player 'player1' should win the game 'game'


  Scenario: Player will win the set
    Given A tennis set started with the following information
      | firstPlayerName  | player1 |
      | secondPlayerName | player2 |
    When  The following point are won in game for 6 times
      | player2   |
      | player2   |
      | player2   |
      | player2   |
    Then Player 'player2' should win the set
