# CSP Lab MLKIT feature extractor

This project is developed under the Computer Security and Privacy Lab of University of Goettingen. Given a raw dataset of images in the assets folder, the project will print out a list of labels from the MLKIT implementation in the Logger space.

*****
Project Structure:-

1. Assests folder(/app/src/main/assets/): Contains 1000 raw images downloaded from Flickr repository in the directory '/flickrImages/private_1/'.
*****

*****
How to run the Android project:-

1. Clone the repository into local.
2. Import the project in Android Studio.
3. Perform a clean Gradle build in Android Studio(if any complications arise, delete the '.idea' and '.gradle' folder from the project structure and perform a clean build).
4. The project is run for just 1000 images which are located in the assets directory, under /flickrImages/private_1/ folder. The 'MainActivity.java' is run to print out a list of MLKIT image labels in the Logger print space with respect to every image in the folder.
5. Images inside this 'private_1' folder can be replaced with aother set of images and the Main Activity can be run again to print the label list for those images respectively.
6. 'MainActivity.java' has the variable linking the '/flickrImages/private_1/' path, if the folder name is needed to be changed then it is also to be updated in the 'MainActivity.java'.
7. Copy image labels from the Logger print space, paste them in a Notepad file, remove empty spaces and unnecessary log print statements from the whole text(follows a pattern which can be put inside the 'Find and Replace' functionity in Notepad and removed easily), and then save the file in .csv format. The .csv file should have data in each line in the format :- 

100855729,Hand:0.94791704,Skin:0.9214101,Mouth:0.8590267,Nail:0.75333637,Eyelash:0.6909051,.....
103862396,Mouth:0.83657473,Flesh:0.7127041,Beard:0.6380144,Hat:0.5821993,Eyelash:0.5356345,.....
.....

8. Follow steps 4-7 for all available images.
*****

*****
How to process the .csv files into one final .csv file after Step 8 from the above section is completed:-

1. Create the folder structure /features/mlkit/ and create two folders inside - '/raw_csv/' & '/final_csv/'.
2. Put all the .csv files generated from the above section, in the raw_csv folder.
3. Put the 'process_mlkit_csv.ipynb' file in the same branch as the features folder, that is in the root folder.
4. Update the variable value containing name of the .csv file that is to be preprocessed in the ipynb file(2 places, one at top during import, one at bottom during saving of the processed csv file).
5. Run 'combine_csv.ipynb' to combine all the .csv files created till the last step into one whole .csv file. Update the variable names accordingly. Note: The final csv file is created in 'final_csv' folder.
*****

*****
How to process the final csv file from last section into one_hot_encoding csv file for a certain MLKIT label threshold:-

1. Put the 'mlkit_one_hot_enc.ipynb' file in the same branch as the features folder, that is in the root folder.
2. Update variable value in the ipynb file containing the final csv file name.
3. Update the MLKIT confidence level threshold in the 'if statement' and then run the notebook.
*****

*****
How to get the count of the highest occuring labels for each privacy type(public & private):-

1. Put the 'mlkit_one_hot_enc.ipynb' file in the same branch as the features folder, that is in the root folder.
2. Update the variables linking the one_hot_encoding csv file created in the last section, within the ipynb file.
3. Checkout the one_hot_encoding csv file from last section to see till where the data is for private images and from where public images are starting. Note: Checkout the Privacy column at the last.
4. After getting the index value, use it in the processing step (first checkin version has the number - 6834). In this way, the values of private images and public images are separated.
5. Run the notebook. The one_hot_encoding csv file containing the count of labels will be genearted within the final_csv folder.
*****

*****
Jupiter notebook list:-

1. change_column_name.ipynb - To change the column names of a csv file to list of column names.
2. combine_csv.ipynb - To combine multiple csv files into one.
3. mlkit_one_hot_enc_label_count.ipynb - To get the count of top occuring labels of mlkit from an one hot encoded csv file.
4. mlkit_one_hot_enc.ipynb - To turn MLKIT csv file with different confidence levels for different labels into one hot encoded csv file with a provided threshold value for the confidence level. Example:- All labels above 0.5 will be encoded as 1, otherwise 0.
5. process_mlkit_csv.ipynb - To receive MLKIT feature extraction results in text/string format and turn them into csv file.
*****

*****
Limitations, improvements and important notes:-

1. The threshold for confidence level for MLKIT in the MainActivity.java file has been set to 0.1. Giving any value less than it was causing the app to be unresponsive with a batch of 1000 images. The device used as emulator is OnePlus 7T (8GB LPDDR4X RAM, 128 GB UFS 3.0 2-LANE STORAGE, Qualcomm® Snapdragon™ 855 Plus (Octa-core, 7nm, up to 2.96 GHz), Adreno 640 GPU). Even though the confidence values are very low for the labels having less than 0.1 score, THEY CAN BE SIGNIFICANT IN THE FINAL OUTCOME BY A SMALL MARGIN. Thus, a smaller threshold or no threshold confidence level parameter is to be tried out with a better emulator so that the application does not crash and we have values for all 400+ labels for one image.
2. Even though it is mentioned in the website of MLKIT that they have 446 labels, there are actually 430 labels. In their official website(https://developers.google.com/ml-kit/vision/image-labeling/label-map), they have indexing mistakes which has created this confusion.
*****
