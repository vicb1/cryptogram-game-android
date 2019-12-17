# cryptogram-game-android

This was a project developed for the following class where software architecture and testing was covered, and this was the final project.
    - [Course description](https://www.omscs.gatech.edu/cs-6300-software-development-process)
    - [Udacity lectures](https://classroom.udacity.com/courses/ud805)

See this root folder for documents used to plan this project.  Additionally, see below for instructions on how to run the Android game and the user manual on how to use it.

## How to launch Android app:
1. run Android Virtual Device in Android Studio
2. drag and drop the .apk file in the root directory onto the simulator screen
3. look for the app Crypto6300 in the listing of all apps

## How to export .apk file after making any changes:
1. run command From menu Build-> Build Bundles/APKs->Build APK
2. You will find APK file in folder app/build/outputs/apk/debug

# Cryptogram Game User Manual

## 1. Description
Cryptogram is a game where a common word or phrase is encrypted and the player wins a game by decoding the encrypted phrase. 

## 2. How to for Admin
### __2.1 Log In__
- Launch the app and enter the admin username "CryptoAdmin". 

  <img src="./images/Screenshots/login_admin.png" height="500" />

- Click "LOG IN" button to log in and navigate to the admin home screen.
- There are three options: 

    - View all the players' game statistics.
    - Add a new player into the game.
    - Add a new cryptogram game.
    
    Click one button to navigate to the corresponding screen.

  <img src="./images/Screenshots/home_admin.png" height="500" />

### __2.2 Player Statistics__
- Click "PLAYER STATISTICS" on the admin home screen to view the table of player statistics. This table displays all the players' first name, number of won, number of lost, username and difficulty category in descending order of number of cryptograms won.
- Click "BACK" button on the navigation bar to navigate back to the admin home screen.

  <img src="./images/Screenshots/statistics_admin.png" height="500" />

### __2.2 Add a New Player__
- Click "ADD A NEW PLAYER" on the admin home screen to navigate to the form for new player creation.
- Fill the new player information: first name, last name, username and difficulty category.

  <img src="./images/Screenshots/create_player_fill_info.png" height="500" />

- Must provide a non-empty text for each input, otherwise error messages will display. Easy difficulty category is selected by default for a new player.

  <img src="./images/Screenshots/create_player_fn_required.png" height="500" /> <img src="./images/Screenshots/create_player_ln_required.png" height="500" /> <img src="./images/Screenshots/create_player_un_required.png" height="500" />

- If an username is already used by an existing player, an error message will display. Must provide an unique username for the new player.

  <img src="./images/Screenshots/create_player_un_unique.png" height="500" />

- After confirming all the entered information is valid, click "SAVE" to create the new player. A dialog with a confirmation message will show up indicating the success of adding a new player.

  <img src="./images/Screenshots/create_player_success.png" height="500" />

- Click "OK" to close the confirmation dialog. All the inputs are reset for adding another new player.
- Click "BACK" button on the navigation bar to navigate back to the admin home screen.

### __2.2 Add a New Cryptogram__
- Click "ADD A NEW CRYPTOGRAM" on the admin home screen to navigate to the form for new cryptogram creation.
- Fill the new cryptogram information: name, answer and tries allowed per difficulty.
- Tries per difficulty must satisfy the following: Easy > Normal > Hard

  <img src="./images/Screenshots/create_cryptogram_fill_info.png" height="500" />

- Must provide a non-empty text for each input, otherwise error messages will display.

  <img src="./images/Screenshots/create_cryptogram_name_required.png" height="500" /> <img src="./images/Screenshots/create_cryptogram_answer_required.png" height="500" /> <img src="./images/Screenshots/create_cryptogram_easy_required.png" height="500" />  <img src="./images/Screenshots/create_cryptogram_normal_required.png" height="500" /> <img src="./images/Screenshots/create_cryptogram_hard_required.png" height="500" />

- If a cryptogram name already exists, an error message will display. Must provide an unique name for the new cryptogram.

  <img src="./images/Screenshots/create_cryptogram_name_unique.png" height="500" />

- After confirming all the entered information is valid, click "SAVE" to create the new cryptogram. A dialog with a confirmation message will show up indication the success of adding a new cryptogram.

  <img src="./images/Screenshots/create_cryptogram_success.png" height="500" />

- Click "OK" to close the confirmation dialog. All the inputs are reset for adding another new cryptogram.
- Click "BACK" button on the navigation bar to navigate back to the admin home screen.

## 3. How to for Player
### __3.1 Log In__
- Launch the app and enter the player username, e.g. "player1". 

  <img src="./images/Screenshots/login_player.png" height="500" />

- An error message will display if the player doesn't exist.

  <img src="./images/Screenshots/login_fail.png" height="500" />

- Click "LOG IN" button to log in and navigate to the player home screen.
- There are two options: 

    - View all the unsolved cryptograms.
    - View all the players' game statistics.
    
    Click one button to navigate to the corresponding screen.

  <img src="./images/Screenshots/home_player.png" height="500" />

### __3.2 Unsolved Cryptogram List__
- Click "CRYPTOGRAM TO SOLVE" on the player home screen to view the list of unsolved cryptograms.
- The cryptograms with "UNSTARTED" are the ones that the player has not started.
- The cryptograms with "INPROGRESS" are the ones that the player has started but has not won or lost.
- The cryptograms that have been completed (either won or lost) are not shown on this list.

  <img src="./images/Screenshots/unsolved_cryptogram_list.png" height="500" />

### __3.3 Solve a Cryptogram__
- Click any "UNSTARTED" or "INPROGRESS" cryptogram to open the screen for solving the cryptogram.
- If the cryptogram has been started before and is in progress, the previous answer entered by the player will display. Otherwise, the answer input will be empty.
- The word or phrase is encrypted randomly using an one-to-one substitution cipher. e.g. All the letter "u"s in the encrypted phrase stand for all the letter "a"s in the answer.
- Click "SAVE AND QUIT" to temporarily save the answer without submitting it and navigate back to the unsolved cryptogram list. The player can reopen this cryptogram later to continue working on it.

  <img src="./images/Screenshots/solve_cryptogram.png" height="500" />

- There is an "Attempts Left" for each cryptogram for each player. It is originally the same as the tries allowed per difficulty when the cryptogram is created. e.g Attempts Left is 10 for the cryptogram "Something to eat 4 when the player opens it and has not made any attempt submissions. 
- Click "SUBMIT" to submit the answer and check if the submitted answer is correct. If the answer is wrong, a dialog with a message shows up.
- Click "OK" to try again if there are still attempts left. The attempts left decreases by 1 whenever a wrong answer is submitted.

  <img src="./images/Screenshots/solve_cryptogram_attempt.png" height="500" /> <img src="./images/Screenshots/solve_cryptogram_wrong.png" height="500" /> <img src="./images/Screenshots/solve_cryptogram_attempt_decrement.png" height="500" /> 

- If a correct answer is submitted, a dialog with "You Won" message shows up.
- If a wrong answer is submitted and no more attempts left, a dialog with "You Lost" message shows up.
- Click "OK" to navigate back to the unsolved cryptogram list to choose another cryptogram.

  <img src="./images/Screenshots/solve_cryptogram_won.png" height="500" /> <img src="./images/Screenshots/solve_cryptogram_lost.png" height="500" /> 

### __3.4 Player Statistics__

- Click "PLAYER STATISTICS" on the player home screen to view the table of player statistics. This table displays all the players' first name, number of won, number of lost in descending order of number of cryptograms won.
- Click "BACK" button on the navigation bar to navigate back to the admin home screen.

  <img src="./images/Screenshots/statistics_player.png" height="500" />

