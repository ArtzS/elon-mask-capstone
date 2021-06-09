# Android Documentation

### A. How to duplicate our android app
1. git clone this repository
2. open project [elon-mask-capstone/Android/MaskDetector](https://github.com/ArtzS/elon-mask-capstone/tree/main/Android/MaskDetector) in your Android Studio
3. select `Build tab > Make Project`, and wait for a minute (the `TFLite_Model.tflite` and `coco.txt` should be downloaded automatically). if the download fail then you have to get `TFLite_Model.tflite` and `coco.txt` manually from [here](https://drive.google.com/file/d/10wp5v2aw8lVLLdQ0072JxyAc5BDIqZJ-/view?usp=sharing) and put it in project's `:app` assets folder.
4. run the app.

### B. What's On Our App
1. TFLite Camera
2. Covid-19 Cases Number
3. MaskData Log graph (last 12 hours)
4. MaskData Log graph (select date)
