# Test Plan

Version 3 - added Actual Results in the Test Cases section, modified Adequacy Criterion

**Author**: \<Team59 - Fan Wu, Feng Yang, Oran Wallace, Victor Bajenaru>

## 1 Testing Strategy

### 1.1 Overall Strategy

During the development of the Cryptogram Game small unit tests will be conceived as more low level API is added. Initially, a unit test will exist for all major class methods which are self contained and can receive an input and produce an expected output. If a developer creates a new method and deems it suitable for a unit test then a new unit test will be devised. These tests will mainly be conducted with JUnit.

After a reasonable amount of unit tests exist medium sized integration tests will be created. These will consist of two or more unit tests. Unit tests which comprise an integration test may modify class field or pass data directly to the next unit test in the list of tests. A UI testing framework will be used for performing integration tests (Espresso?).

Only a small number of system tests will exist. These will exercise a complete UI workflow which a typical User or Administrator will perform. A UI testing framework will be used for these tests.

Regression testing will be performed by running all tests as often as possible. QA/Test Lead (Oran) will perform these tests as often as possible.

### 1.2 Test Selection

The Category Partition Method will be used to generate test cases. This is a black-box specific test case generation method but it can be applied to both the unit testing level and system. We will employ this method to create test cases for Java class methods (unit tests) and for GUI elements of the game.

As developers add code to the repository test cases will be generated to ensure near 100% statement coverage is achieved (white-box). Methods deemed 'critical' would always have test cases generated with 100% statement coverage.

Additionally test cases will be generated for each use case described in the UseCaseModel.md document.

Automated test case generation and code coverage tools will be used to assist with test case generation.
 

### 1.3 Adequacy Criterion

In order to arrive at an adequete test suite a test will be created for every use case presented in UseCaseModel.md document. Additional tests will be created for error cases within a specific use case. 

### 1.4 Bug Tracking

The "Issues" tool within GitHub will be used to track bugs. Once a bug is identified and it can be reproduced, a new "Issue" will be created within GitHub. When a developer deems the bug "fixed" the Issue will be "closed" within the system.


### 1.5 Technology

JUnit will be used to create small unit tests for classes.
Espresso will be used to perform complicated UI tests within an emulator.

## 2 Test Cases

| Purpose                            | Necessary Steps                                                                | Expected Result | Actual Result |
|------------------------------------|--------------------------------------------------------------------------------|-----------------|---------------|
| Test User login.                   | View Login UI, enter login, submit.                                            |  User Logged in  | Player logged in, confirmed message "Hello Player" exists. |
| Test Admin login.                  | View Login UI, enter login, submit.                                            | Admin Logged in  | Admin logged in, confirmed message "Hello Admin" exists. |
| Test player creation with all valid information provided.           | Admin login, click ‘ADD A NEW PLAYER’ button, Fill out User Info, and  click 'SAVE' button.              | Show up a dialog with message "Successfully Created the Player".         | Pop up a dialog with message "Successfully Created the Player". | 
| Test closing dialog after successfully creating a player. | Create a new player with valid information so the dialog with message 'Successfully Created the Player' shows up. Then click 'OK' on the dialog.| The dialog closes and all three fields on "Add a New Player" screen are cleared up. | The dialog closes and all three fields on 'Add a New Player' screen are cleared up.
| Test player creation with no First Name provided | Log in as Admin, click 'ADD A NEW PLAYER' button, leave 'First Name' field empty, fill the other fields, then click 'SAVE' button. | Error message on 'First Name' field: First name is required. | Error message on 'First Name' field: First name is required.| 
| Test player creation with no Last Name provided. | Log in as Admin, click 'ADD A NEW PLAYER’ button, leave 'Last Name' field empty, fill the other fields, then click 'SAVE' button. | Error message on 'Last Name' field: Last name is required. | Error message on 'Last Name' field: First name is required.| 
| Test player creation with no Username provided. | Log in as Admin, click 'ADD A NEW PLAYER’ button, leave 'Username' field empty, fill the other fields, then click 'SAVE' button. | Error message on 'Username' field: Username is required. | Error message on 'Username' field: Username is required.| 
| Test player creation with invalid Username provided. | Log in as Admin, click 'ADD A NEW PLAYER’ button, fill 'First Name' and 'Last Name' fields, enter an existing username in 'Username' field, then click 'SAVE' button. | Error message on 'Username' field: Username is not unique. | Error message on 'username' field: Username is not unique. | 
| Test player creation with no information provided. | Log in as Admin, click 'ADD A NEW PLAYER’ button, leave all the fields empty, then click 'SAVE' button. | Error message on 'First Name' field: First name is required. Error message on 'Last Name' field: Last name is required.  Error message on 'Username' field: Username is required. | Error message on 'First Name' field: First name is required. Error message on 'Last Name' field: Last name is required.  Error message on 'Username' field: Username is required.
| Test cryptogram creation.          | Admin Login, Fill out Cryptogram Info,  and click 'Create Cryptogram' button.  | Admin login, Cryptogram created, player admin, confirm cryptogram exists. | CryptoAdmin logged in, cryptogram "Test" created, player login, confirmed cryptogram "Test" existed.             |
| Test player stats UI.              | Login as player, view "Player Stats" UI.                                       | Player can view appropriate statistics               | Login as player, win cryptogram "Something to eat 1", change UI to "Player Statistics", confirmed 1 win exists for player.             |
| Test admin stats UI.               | Login as Admin, view "Player Stats" UI.                                        | Admin can view appropriate statistics               | Login as player, win cryptogram "Something to eat 1", logon, login as admin, change UI to "Player Statistics", confirmed 1 win, username and difficulty exists for player.              |
| Test player view cryptogram  list. | Login as player, view "Cryptogram List"  UI.                                  | Player can view appropriate cryptogram list               | After selecting "Cryptograms to Solve", confirmed "Cryptogram List" exists in UI. |
| Test player selecting cryptogram. | Login as player, view "Cryptogram List",  select cryptogram.                   | Player is able to successfully get to the cryptogram game after clicking it in the list               |  After selecting "Cryptograms to Solve", then choosing "Something to eat 1" (preloaded data), confirmed that title exists in the UI. |
| Test playing cryptogram.           | Login as player, view "Cryptogram List",  select cryptogram, play cryptogram.  | Playing the cryptogram is working properly               | Login as player, view "Cryptogram List", select "Something to eat 1", proceed to input 5 incorrect attempts, confirm "Attempts Left" decreased by 5.|
| Test win cryptogram.               | Run "Test playing cryptogram" test. Enter winning sequence.                    | A won cryptogram will not show on the available list, and the statistic will be updated in the statistics screen               | After selecting "Something to eat 1" and entering solution, confirmed "You won" message appearing. |
| Test lose cryptogram.              | Run "Test playing cryptogram" test. Enter losing/wrong sequence.               | A lost cryptogram will not show on the available list, and the statistic will be updated in the statistics screen               | After selecting "Something to eat 1" and entering the wrong solution (20 times), confirmed player is kicked back to "Cryptogram List" UI.|
