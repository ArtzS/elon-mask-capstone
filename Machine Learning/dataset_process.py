import os
import glob

current_dir = os.path.dirname(os.path.abspath(__file__))
print(current_dir)
current_dir = 'data/obj'

test_data_percentage = 15

# Create a train.txt and test.txt
train_file = open('data/train.txt', 'w')
test_file = open('data/test.txt', 'w')

# Put The File Inside train.txt and test.txt
counter = 1
test_index = round(100 / test_data_percentage)
for pathAndFilename in glob.iglob(os.path.join(current_dir, "*.jpg")):
    title, ext = os.path.splitext(os.path.basename(pathAndFilename))

    if counter == test_index:
        counter = 1
        test_file.write("data/obj" + "/" + title + '.jpg' + "\n")
    else:
        train_file.write("data/obj" + "/" + title + '.jpg' + "\n")
        counter = counter + 1
