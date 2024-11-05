from skimage.io import imread
from skimage.filters import threshold_otsu
import matplotlib.pyplot as plt



def getBinaryCarImage(carImagePath):
    car_image = imread(carImagePath, as_grey=True)
    # it should be a 2 dimensional array
    print(car_image.shape)

    # the next line is not compulsory however, a grey scale pixel
    # in skimage ranges between 0 & 1. multiplying it with 255
    # will make it range between 0 & 255 (something we can relate better with

    gray_car_image = car_image * 255
    threshold_value = threshold_otsu(gray_car_image)
    binary_car_image = gray_car_image > threshold_value


    return binary_car_image