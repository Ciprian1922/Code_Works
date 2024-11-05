import cv2
import pytesseract
import numpy as np

# Set up webcam
cap = cv2.VideoCapture(0)  # 0 for the primary webcam

# Load the pre-trained Haar cascade for license plate detection
plate_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + "haarcascade_russian_plate_number.xml")

# Set up Tesseract path if needed
# pytesseract.pytesseract.tesseract_cmd = r'path_to_tesseract'

while True:
    # Capture frame-by-frame
    ret, frame = cap.read()
    if not ret:
        break

    # Convert to grayscale for better detection
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    # Detect license plates
    plates = plate_cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5, minSize=(100, 100))

    for (x, y, w, h) in plates:
        # Draw a rectangle around the license plate
        cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 0, 0), 2)

        # Crop the detected plate area
        plate_img = gray[y:y + h, x:x + w]

        # Enhance the cropped image (optional)
        _, plate_img = cv2.threshold(plate_img, 100, 255, cv2.THRESH_BINARY)

        # OCR: Extract text from the cropped license plate
        text = pytesseract.image_to_string(plate_img, config='--psm 8')  # PSM 8 is for single-line text
        print("Detected Plate Text:", text)

        # Display the text on the video frame
        cv2.putText(frame, text.strip(), (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)

    # Display the resulting frame
    cv2.imshow('License Plate Reader', frame)

    # Press 'q' to quit
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# Release the capture and close windows
cap.release()
cv2.destroyAllWindows()
