# Romanian Plate Recognition System
### See it in action: https://bit.ly/3BTuyx5
Plate recognition system designed to recognise Romanian number plates from photos and videos, using a custom in-house trained machine learning model, done in collaboration with Nokia's Innovation Labs.

Project featured on WUT's Faculty of Mathematics & Informatics website: https://www.info.uvt.ro/rezultate-studenti/


## Plate Recognition from photos
- Features:
  - Edge detection 
  - Contour detection
  - Mask creation
  - easyOCR for character recognition 

## Plate Recognition recognition from videos
- Features:
  - Custom in-house trained machine learning model to approximate the number plate's location. The model was trained with more than 1000+ images, assuring an accuracy of 96% rate of succes. The model returns the bounding box of the found license plate that's used for character recogntion
  - Pytesseract used for character recognition with character segmentation and morphological transformations. 
