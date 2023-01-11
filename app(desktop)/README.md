# Photo Library (Desktop)
Welcome to my desktop app guide. All the features are listed below. Feel free to roam around!

*[shortcut to the source code](./src/main/java/com/example/bro_comm/)*
## Overview
Photo Library gives you the power to create and manage albums directly from images stored on your computer. You can move and copy photos between your albums. You can edit your photos with captions and tags. You can even view your collection with a built-in slideshow. Above all, it supports multiple users! 

This application comes preconfigured with a stock user containing a stock album with five breathtaking stock photos.

<!---
cut frames of gifs
-->

## Implementation
This application was written in java using [JavaFX](https://openjfx.io) and [Scene Builder](https://gluonhq.com/products/scene-builder/). 

## Data Management
Data persistence is achieved through [serialization](https://docs.oracle.com/javase/tutorial/jndi/objects/serial.html). 

## Administration
The administration system allows you to add and delete users. To configure, enter **"admin"** at login.

<!---
![Alt text](./guides/admin.gif?raw=true "Admin")
<img src="./guides/admin.gif" width="800" height="500"/>
-->
![Alt text](./guides/admin.gif?raw=true "Admin")

## Logout
You can logout anytime and ensure all data is saved. You will be brought back to the home/login screen.

<!---
![Alt text](./guides/logout.gif?raw=true "Logout")
<img src="./guides/logout.gif" width="800" height="500"/>
-->
![Alt text](./guides/logout.gif?raw=true "Logout")

## Quit
You can safely quit the app anytime and ensure all data is saved.

<!---
![Alt text](./guides/quit.gif?raw=true "Quit")
<img src="./guides/quit.gif" width="800" height="500"/>
-->
![Alt text](./guides/quit.gif?raw=true "Quit")


## Albums
Logging into a user displays all the user's albums. Here you can select a specific album to display the number of photos in the album as well as the range of modification dates.

You can easily add, delete or rename albums. 

<!---
![Alt text](./guides/album.gif?raw=true "Album")
<img src="./guides/album.gif" width="800" height="500"/>
-->
![Alt text](./guides/album.gif?raw=true "Album")

### Open
Opening an album allows you to select individual photos.

<!---
![Alt text](./guides/open.gif?raw=true "Open")
<img src="./guides/open.gif" width="800" height="500"/>
-->
![Alt text](./guides/open.gif?raw=true "Open")

#### Add
Add a photo to the open album from your computer.
![Alt text](./guides/add.gif?raw=true "Add")

#### Delete
Delete a photo in the open album.
![Alt text](./guides/delete.gif?raw=true "Delete")

#### Copy
Copy a photo from the open album to another album.
![Alt text](./guides/copy.gif?raw=true "Copy")

#### Move
Move a photo from the open album to another album.
![Alt text](./guides/move.gif?raw=true "Move")

### Search
For a fully implemented search, check out the mobile app!

<!---
![Alt text](./guides/search.gif?raw=true "Search")
<img src="./guides/search.gif" width="800" height="500"/>
-->
![Alt text](./guides/search.gif?raw=true "Search")


## Display
After opening an album and selecting a photo, you can display that photo and navigate through the entire album in a slideshow.
![Alt text](./guides/display.gif?raw=true "Display")

### Caption
Add fun captions to your photos!

### Tag
Tag your photos by people and location. Not satisfied? Add your own tags!

# Credits



