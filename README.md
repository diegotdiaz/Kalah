# Kalah
Kalah game implementation for Backbase challenge

This is a REST API implementation for the game, so it could be played using the end points that will be described below.


## Requirements

* JDK 1.8 or later

* Maven 3.0 or later


## Run Instructions

```sh
$ cd Kalah
$ mvn spring-boot:run
```

Note: It's recommended to clean and update your dependencies before running for the first time

```sh
$ mvn clean -U
```

Verify that the application is up and running in port 8080:
```sh
http://127.0.0.1:8080/ecv
```
#### Success Response

	* Code: 200 "Kalah App is Up"

## Game Reference
Kalah game rules can be seen in: https://en.wikipedia.org/wiki/Kalah

## REST API Reference


### Start a new game
Endpoint to start a game with a specified number of seeds per pit

#### URL

/kalah/start/:seeds

#### Method

`POST`

#### URL Params

seeds=[integer] Number of seeds per pit when gam starts

#### Success Response

* #### Code: 201

* #### Request Example: http://127.0.0.1:8080/kalah/start/6

* #### Content: 
  `{
    "id": "c0d98794-0a31-4487-9d01-7a2edd6f6020",
      "score": {
          "boardP1": [6, 6, 6, 6, 6, 6, 0],
          "boardP2": [6, 6, 6, 6, 6, 6, 0]
      },
      "turn": "player_1",
      "status": "ON_GOING"
    }`
    
* **Error Response:**

  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{ error : "Number of seeds must be grater than zero" }`

### Make a move
End point to be use by each player to make a move in the game

#### URL

/kalah/move/id/:id

#### Method

`PUT`

#### URL Params

id=[string] game Id that is returned when the game is started

#### Request Body

{"player" : ":player", pit : ":pit"}

* player=[string] Player who is making the move
  * **Possible values**: "player_1", "player_2"
* pit=[integer] pit number in the player's board (1 - 6)
  * **Possible values**: 1 - 6

* #### Request Body Example

{"player" : "player_1", "pit" : "1"}


#### Success Response

* #### Code: 200

* #### Request Example: http://127.0.0.1:8080/kalah/move/id/c0d98794-0a31-4487-9d01-7a2edd6f6020


* #### Content: 
  `{"id":"c0d98794-0a31-4487-9d01-7a2edd6f6020","score":{"boardP1":[6,0,7,7,7,7,1],"boardP2":[7,6,6,6,6,6,0]},"turn":"player_2","status":"ON_GOING"}`
    
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Game with ID c0d98794-0a31-4487-9d01-7a2edd6f6023 was not found" }`

  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{ error : "It' not player_2's turn" }`
    
  * **Code:** 403 FORBIDDEN <br />
	**Content:** `{ error : "Illegal move, pit 1 does not have any seeds" }`
    
  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{ error : "Illegal move, player_1 may only take seeds from pit 1 to 6" }`
    
  * **Code:** 403 FORBIDDEN <br />
    **Content:** `{ error : "Game is finished" }`
   

### Get Game
End point to retrieve a game with the specified ID

#### URL

/kalah/id/:id

#### Method

`GET`

#### URL Params

id=[integer] Game ID

#### Success Response

* #### Code: 200

* #### Request Example: http://127.0.0.1:8080/kalah/id/c0d98794-0a31-4487-9d01-7a2edd6f6020

* #### Content: 
  `{
    "id": "c0d98794-0a31-4487-9d01-7a2edd6f6020",
      "score": {
          "boardP1": [6, 6, 6, 6, 6, 6, 0],
          "boardP2": [6, 6, 6, 6, 6, 6, 0]
      },
      "turn": "player_1",
      "status": "ON_GOING"
    }`
    
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Game with ID c0d98794-0a31-4487-9d01-7a2edd6f6023 was not found" }`
 
### Delete Game
End point to delete a game with the specified ID from the game store
#### URL

/kalah/id/:id

#### Method

`DELETE`

#### URL Params

id=[integer] Game ID

#### Success Response

* #### Code: 202

* #### Request Example: http://127.0.0.1:8080/kalah/id/c0d98794-0a31-4487-9d01-7a2edd6f6020

* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Game with ID c0d98794-0a31-4487-9d01-7a2edd6f6023 was not found" }`

