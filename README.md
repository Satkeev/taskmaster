
#### SOCCER app.

-Click to <Settings> and enter your User name 
-Then click <Update Username>
-Click to <Add task> 
-User can fill out fields <My Task>, <do something> and <Status Task> for tracking tasks
-Click to <Add Task> go to back to <Soccer> home page.
-Click to <All tasks> and User can see three tasks
-Click to buttons <Soccer stadiums>, <Soccer tickets>, <Soccer schedule> where User can see all these information

http://github/Satkeev

Testing by Espresso
<Run> -> <Record Espresso Test> start for UI app testing
<Add Assertion> for choise what you testing
Choise on emulator button or text or other for testing
<Save Assertion> 
Create a test file
Run test file and check test results
An Android app for tracking tasks!

Changelog  
10/19/20  
initialized project  
main tasks page added  
add task page added  
task list page added  
10/20/20  
created task details page  
created settings page  
added task buttons with intents to task details  
task list title dynamic to username in settings  
10/21/20 - task class created - set up recycler view for task list - made task list   clickable to view details  
10/22/20  
added Room dependencies and linked Task as an Entity  
edited task list to refer to database  
replaced hard coded task inserts with reference to add form  
adding tests for task and username changes  
10/26/20  
adding tests for task views and main user paths    
updated screenshots  
10/27/20  
connected to DynamoDB using Amplify  
converted task add to save to DynamoDB instead of Room  
main page task list now updated from DynamoDB  
10/28/20    
added subscription and handler to keep task list up to date with cloud  
created state and team models with relations to the task model  
replaced submitted popup with toast on add task form  
added hard coded radio button team selection to add task form  
updated task save to match relations on DynamoDB  
removed connection to local Room storage  
task list now displays tasks of user's team  
11/02/20  
added Cognito user authentication plugin  
created activities for signup, user confirmation, and login  
linked new activites to user authentication operations  
added logout button  
adjusted main page to display signed in username  
11/03/20  
added AWS S3 files for use S3 storage, and upload, retrieve files with  
images  
11/04/20  
linked to Firebase for push notificationsnn
attached Pinpoint push notifications through Firebase  
11/05/20  
added Amplify Analytics plugin  
created event tracking App Start  
added event tracking user button clicks  
linked Pinpoint stream to Kinesis  
11/09/20  
made image shares receivable to add task form
shared images can now be saved with new task and viewed with task details  
11/10/20   
made location on main page and save address  

![img1](assets/Screenshot 2020-10-19 205009.png)
![img2](assets/Screenshot 2020-10-19 225023.png)
![img3](assets/Screenshot 2020-10-20 224855.png)
![img4](assets/Screenshot 2020-10-20 224932.png)
![img5](assets/Screenshot 2020-10-20 225005.png)
![img6](assets/Screenshot 2020-10-21 213401.png)
![img7](assets/Screenshot 2020-10-22 211509.png)
![img8](assets/Screenshot 2020-10-22 211509.png)
![img9](assets/Screenshot 2020-10-22 211551.png)
![img10](assets/Screenshot 2020-10-22 211551.png)
![img11](assets/Screenshot 2020-10-22 211551.png)
![img11](assets/Screenshot 2020-10-22 211727.png)
![img11](assets/Screenshot 2020-10-22 211906.png)
![img11](assets/Screenshot 2020-10-26 191758.png)
![img11](assets/Screenshot 2020-10-26 191913.png)
![img11](assets/Screenshot 2020-10-26 195020.png)
![img11](assets/Screenshot 2020-10-28 001429.png)
![img11](assets/Screenshot 2020-10-28 001531.png)
![img11](assets/Screenshot 2020-10-28 235334.png)
![img11](assets/Screenshot 2020-10-28 235404.png)
![img11](assets/Screenshot 2020-10-28 235457.png)
![img11](assets/Screenshot 2020-10-28 235552.png)
![img11](assets/Screenshot 2020-10-28 235718.png)
![img11](assets/Screenshot 2020-10-29 205258.png)
![img11](assets/Screenshot 2020-10-29 205336.png)
![img11](assets/Screenshot 2020-10-29 205415.png)
![img11](assets/Screenshot 2020-11-03 000947.png)
![img11](assets/Screenshot 2020-11-03 001013.png)
![img11](assets/Screenshot 2020-11-03 001118.png)
![img11](assets/Screenshot 2020-11-03 001225.png)
![img11](assets/Screenshot 2020-11-03 001410.png)
![img11](assets/Screenshot 2020-11-04 000340.png)
![img11](assets/Screenshot 2020-11-04 223156.png)
![img11](assets/Screenshot 2020-11-04 223226.png)
![img11](assets/Screenshot 2020-11-05 195156.png)
![img11](assets/Screenshot 2020-11-05 195650.png)
![img11](assets/Screenshot 2020-11-09 233324.png)
![img11](assets/Screenshot 2020-11-09 233501.png)
![img11](assets/Screenshot 2020-11-09 233606.png)
![img11](assets/Screenshot 2020-11-09 233853.png)
![img11](assets/Screenshot 2020-11-09 234008.png)
![img12](assets/Screenshot 2020-11-10 232129.png)
![img13](assets/Screenshot 2020-11-10 232223.png)
![img14](assets/Screenshot 2020-11-10 232247.png)
![img15](assets/Screenshot 2020-11-10 232342.png)





