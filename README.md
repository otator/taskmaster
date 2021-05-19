# taskmaster

Task master is an android application that lets you add tasks and save them

<img src="screenshot/Screenshot_3.jpg" width ="250px" height="400px" style="margin: 50px 0 50px 0; border-radius: 5px">


### day1
* home page with title, image and two buttons that when they clicked new activiites will be opened.

* Add Task activity with two input fileds and a button for save(not saving yet), and a counter for all tasks

* All tasks activity that has an image and title


### day2

* update home page to show three buttons with hard coded task titles, when any button of them is clicked it will open details activity.

* new activity added *details activity* that has two texts, one for description which is *lorem ipsum* for now, and a tiltle that comes from the clicked button in the home page.

* Setting activity added as well, this activity has an input text and save button to store the user input to the shared preferences and get it back in the home page(once the save button clicked, a toast message will be displayed and return to the home page)

### day3

* add Task class that has title, description and state(in progress, new, assigned, complete).
* add custom adapter to show hard coded tasks dynamically in the home page.
* add listener for the task views to show the task details in the details activity.

### day4

* add the ability for the user to add tasks.
* implement local database to save the user tasks
* add the functionality to display the number of tasks in the add task activity

<div style="display:flex; justify-content: space-around">
<img src="screenshot/Screenshot_4.jpg" width ="250px" height="400px" style="margin: 50px 0 50px 0; border-radius: 5px">
<img src="screenshot/Screenshot_5.jpg" width ="250px" height="400px" style="margin: 50px 0 50px 0; border-radius: 5px">

</div>