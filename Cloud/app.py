from flask import Flask, render_template, request, send_from_directory, jsonify
import os
from google.cloud import storage

client = storage.Client()
bucket = client.get_bucket('elon-mask-video-bucket')

UPLOAD_FOLDER = "/home/c0050503/Mask Detector YOLOV4/video test"
MY_IP = os.system('curl ifconfig.me')

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route("/", methods=['GET','POST'])
def home():
	user_input = request.form.get('video')
	os.chdir('/home/c0050503/Mask Detector YOLOV4/detection')
	os.system('python /home/c0050503/Mask\ Detector\ YOLOV4/detection/detect_video.py --weights /home/c0050503/Mask\ Detector\ YOLOV4/detection/TFmodel --size 416 --model yolov4 --video /home/c0050503/Mask\ Detector\ YOLOV4/video\ test/Wearing\ a\ face\ mask\ in\ scared\ of\ coronavirus\ in\ the\ Philippines.mp4 --output /home/c0050503/Mask\ Detector\ YOLOV4/result.mp4 --count')

	return render_template('index.html')




if __name__ == "__main__":
	app.run(host="0.0.0.0", port=int(os.environ.get("PORT", 8081)), debug=True)

	os.chdir('..')
	blob = bucket.blob('result.mp4')
	blob.upload_from_filename('result.mp4')
