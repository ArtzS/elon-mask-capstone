## Here are the step how to implement Cloud in the project:

**(INITIAL PLAN)**
1. Open a new Google Cloud Platform project.
2. Enabling necessary APIs (Compute Engine).
3. Spinning up VM instance, choosing Deep Learning VM from GCP Marketplace.
4. Create a new GCS bucket to store the Deep Learning model.
5. Upload the Deep Learning model to Google Cloud Storage and VM instance.
6. gsutil -m cp -r [LOCAL_OBJECT_LOCATION] gs://’[BUCKET_NAME]’
7. SSH into the VM instance.
8. Download the deep learning model to local VM.
9. gsutil -m cp -r gs://’[BUCKET_NAME]’ .
10. Installing python libraries using pip (virtualenv, Flask, google-cloud-storage)
11. Making a Python Flask API to invoke the Deep Learning model and uploading the resulting video to Google Cloud Storage.
12. https://github.com/ArtzS/elon-mask-capstone/blob/main/Cloud/app.py

**(IMPLEMENTED)**
1. Create a new Google Cloud Platform project.
2. Create Firebase project from Firebase Console.
3. Connecting Firebase project to GCP project.
4. Making Firebase Firestore database for the Android application to connect to.
5. Mode : Native Mode.
6. Preparing Firestore indexes (time, withMask, withoutMask).
