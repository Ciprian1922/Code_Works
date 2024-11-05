  In this project I implemented a LPR System for Romanian license plates, using 3 different methods:
      1. SVM 
      2. CNN applied on the character 
      3. CNN applied on the full image 
    
    The code in this repository only inludes the detection sources along with the weights and the database used to store the license
    plates. The full code for all three algorithms can be found as follows: 
    
  1. For the SVM I modified Femi Oladeji's algorithm to work with Romanian license plates. His original algorithm can be found here: 
    https://blog.devcenter.co/developing-a-license-plate-recognition-system-with-machine-learning-in-python-787833569ccd. 
     The SVM is fast but not very precise. I only got 87% accuracy on test data set but this is strongly influenced by the training data 
     set I used. 
  2. The CNN applied on the character can be found on my git page, here: 
    https://github.com/badeamihaialexandru/Romanian-LPR-using-CNN
     The CNN is fast and quite precise. The accuracy was 93% on the test data set.
  3. Matthew Earl had a very interesting approach, based on this Google paper, http://static.googleusercontent.com/media/research.google.com/en//pubs/archive/42241.pdf 
  which explains how a CNN is used to extract house numbers from street view images. The main task here was to generate the training images, which 
	is not an easy job because you need quite much processing power. Anyway, despite the fact that the detection is quite slow the accuracy was 
	96%. This method also has the advantage that it does not perform segmentation before detection. Anyway, the detection method proposed in the 
	code on this git page, is not the same as the one in the link above. The images used for testing are the same size as those used for training 
	so there was no reason to parse the image with windows of different sizes. The reason I skipped this step was that it made the recognition too 
	slow. If you want to test the network you can find the weights I generated for romanian number plates after 370k iterations here: https://drive.google.com/open?id=1odzOv0DuIUCzvFg6omTChMtIlt9NP2TG
.	You have to add them in the ctask_weights directory. 
	
	As I was saying, this repository contains a database. In order to administrate it, there is another program which can be found here: https://github.com/badeamihaialexandru/LPR-Admin
	
