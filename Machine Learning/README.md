# Welcome to Machine Learning Path of Elon Mask Application

## Our Dataset is from : https://www.kaggle.com/techzizou/labeled-mask-dataset-yolo-darknet

## Here are steps for creating the model (YoloV4 Face Mask Weights, Tensorflow, Tensorflow Lite, and Pickle) :

** The Detail and Code from These Steps are Shown in Mask-Custom_Training.ipynb and Mask-Detector.ipynb file

1. Go to your main directory of the project
2. Clone the Darknet Repository : https://github.com/AlexeyAB/darknet
3. Enable OPENCV, GPU, CUDNN, CUDNN_HALF, and LIBSO in yolov4-custom.cfg 
4. Extract or move the dataset to data folder in Darknet directory
5. Copy yolov4-custom.cfg from parent directory to cfg folder in Darknet directory
6. Copy obj.names and obj.data files from parent directory to data folder in Darknet directory
7. Copy dataset_process.py from parent directory to Darknet Directory and run it
8. Download the YoloV4 Pre-Trained Weights : https://github.com/AlexeyAB/darknet/releases/download/darknet_yolo_v3_optimal/yolov4.conv.137
9. Train The Mask Detection Model (Custom Object) from obj.data (in darknet/data) and yolov4-custom.cfg (in darknet/data) on the YoloV4 Pre-Trained Weights
10. Convert The yolov4-custom_best.weights that we got from previous step to Tensorflow Model (Code in Mask-Detector.ipynb file step 4)
11. Test to detect mask in a video using detect_video.py file (Code in Mask-Detector.ipynb file step 5)
12. Convert The TF Model (.pb) to Pickle file (Code in Mask-Detector.ipynb file step 6)
13. Convert The TF Model to TF Lite (Code in Mask-Detector.ipynb file step 7)
