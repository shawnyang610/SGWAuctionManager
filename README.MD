* Shopgoodwill Offline Manager

** Compile:
- in working directory:javac ProcurementHelper.java
![instruction1](https://user-images.githubusercontent.com/29468957/48114004-5148d980-e22b-11e8-960c-168a7391fce7.png)

** Command-Line Mode:
- java ProcurementHelper –i input.txt –o output.txt
And the program enters command-line mode and starts gathering key words from the input file.
Once it has gotten all the key words, it starts searching for each key word one by one and saves every single items returned from each search into the permanent database. When the program finishes processing all keywords, it will print a list consisting of all the data gathered in this run.
![instruction3](https://user-images.githubusercontent.com/29468957/48114006-5148d980-e22b-11e8-8a67-53465e3fe16a.png)

** GUI Mode:
- To Run in GUI mode: java ProcurementHelper without any flags.
![instruction2](https://user-images.githubusercontent.com/29468957/48114005-5148d980-e22b-11e8-9a1f-50da0dfe19d8.png)

- Online Search: 
Select “Online Search” radio button, type in any key word and select search options of your choice and click the Search button. A list of all the items found will be displayed in main window.
- Offline Search:
Select “Offline Search” radio button and everything is the same as online search, however, it is highly recommended to select “Fuzzy Search” for offline search, this feature will match the key word with anything part of the item’s data. Without Fuzzy search, in order to match, the key word has to be exactly the same as the item’s title.
- Automatic search from inventory file:
By clicking “Open Inv” button, a choosing file dialogue will pop up and prompts you to select an input file. Please reference the demo input.txt file for the formatting of the data.

Once input.txt file is selected, the program will basically do the same thing as command-line. However the program will not automatically save the result to an output file, instead the result will be shown in the main window.
- Reporting:
By clicking the Reporting button, user is prompted to choose an output.txt file. Once a file is chosen, the program will write everything that’s currently shown in the main window to the file.
- Dump Data(commit data):
In GUI mode, all the new search data obtained online will be saved into a hashmap, unlike in command-mode, the data in hashmap will not be automatically saved when the program is closed. In order to save the data so it can be used repeatedly, user needs to click the dump file button. This will save whatever is currently in the hashmap to a permanent storage. 
- Data editing:
Before doing any reporting and dumping, user can use the bottom half of the GUI to delete and edit any data currently in the Hashmap.  
![instruction4](https://user-images.githubusercontent.com/29468957/48114007-5148d980-e22b-11e8-946b-50d7afbc75bb.png)
- Image Feature:
By selecting this option, on top of the basic information, all the subsequent online searches will also obtain the thumbnail images for each returned items.
Use with caution, as searching popular keyword with this option on may take long time to load all images and this can also deplete user’s storage.
![instruction5](https://user-images.githubusercontent.com/29468957/48114008-51e17000-e22b-11e8-8dec-d209dfb5d922.png)
- To Show Image:
First select an item from search result, then click “Show Image” button.
![instruction6](https://user-images.githubusercontent.com/29468957/48114009-51e17000-e22b-11e8-89f3-e2be97a6be71.png)
- offline search what is already in the database.
![instruction7](https://user-images.githubusercontent.com/29468957/48114010-51e17000-e22b-11e8-8c0f-a66552202f33.png)

