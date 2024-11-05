import numpy as np
from skimage.transform import resize
from skimage import measure
from skimage.measure import regionprops
import matplotlib.patches as patches
from skimage.filters import threshold_otsu
import matplotlib.pyplot as plt
from SVC import cca2

# on the image I'm using, the headlamps were categorized as a license plate
# because their shapes were similar
# for now I'll just use the plate_like_objects[2] since I know that's the
# license plate. We'll fix this later

characters = []
column_list = [] #sunt globale pentru ca le apelez si in cca2


def validate_plate( candidates,plate_type_of_objects):
    index = 0
    plate_type=0
    highest_average = 0
    license_plate = []
    for each_candidate in candidates:
        height, width = each_candidate.shape


        total_white_pixels = 0
        for column in range(width):
            total_white_pixels += sum(each_candidate[:, column])

        average = float(total_white_pixels) / width
        if average >= highest_average:
            license_plate = each_candidate
            highest_average=average;
            plate_type=plate_type_of_objects[index]
        index+=1

    return plate_type,license_plate

def calculate_plate(local_license_plate,img_num,license_type,license_plate,first_half,second_half):
    character_dimensions = (0.50*local_license_plate.shape[0], 0.90*local_license_plate.shape[0], 0.03*local_license_plate.shape[1], 0.15*local_license_plate.shape[1])
    square_character_dimensions=(0.60*local_license_plate.shape[0], 0.95*local_license_plate.shape[0], 0.08*local_license_plate.shape[1], 0.22*local_license_plate.shape[1])
    min_height, max_height, min_width, max_width = character_dimensions
    square_min_height, square_max_height, square_min_width, square_max_width=square_character_dimensions;

    counter = 0


    for regions in regionprops(local_license_plate):
        y0, x0, y1, x1 = regions.bbox
        region_height = y1 - y0
        region_width = x1 - x0
        if(license_type==0) :
            if (region_height > min_height and region_height < max_height and region_width > min_width and region_width < max_width):
                    roi = license_plate[y0:y1, x0:x1]

                    # draw a red bordered rectangle over the character.
                    rect_border = patches.Rectangle((x0, y0), x1 - x0, y1 - y0, edgecolor="red",
                                                   linewidth=2, fill=False)

                    # resize the characters to 20X20 and then append each character into the characters list
                    resized_char = resize(roi, (20, 20))
                    characters.append(resized_char)

                    # this is just to keep track of the arrangement of the characters
                    column_list.append(x0)


        else:
            if (region_height > square_min_height and region_height < square_max_height and region_width > square_min_width and region_width < square_max_width):

                    if(img_num==1):
                         roi = first_half[y0:y1, x0:x1]
                    else:
                         roi=second_half[y0:y1,x0:x1]

                    rect_border = patches.Rectangle((x0, y0), x1 - x0, y1 - y0, edgecolor="green",
                                                      linewidth=2, fill=False)


                    # resize the characters to 20X20 and then append each character into the characters list
                    resized_char = resize(roi, (20, 20))
                    characters.append(resized_char)

                    # this is just to keep track of the arrangement of the characters
                    if(img_num==1):
                        column_list.append(x0)
                    else:
                        column_list.append(x0+local_license_plate.shape[1])

    #end of function !!!


def split_plate(license_plate):
    print('dimensiune: ',license_plate.shape)
    half=license_plate.shape[0]/2
    half=int(half)
    print('jumatate: ' ,half)
    first_half=np.zeros(shape=(half,license_plate.shape[1]),dtype=bool)
    second_half = np.zeros(shape=(half,license_plate.shape[1]),dtype=bool)
    end=license_plate.shape[0]
    for i in range(0,half):
        first_half[i][:]=license_plate[i][:]

    for i in range(0,half):
        second_half[i][:]=license_plate[i+half][:]



    return first_half,second_half

def segmentate(carImagePath):
    blackWhiteImage,plate_like_objects,plate_type_of_objects =cca2.getPlateLikeObj(carImagePath)
    # The invert was done so as to convert the black pixel to white pixel and vice versa
    #license_plate = np.invert(cca2.plate_like_objects[1])
    if len(plate_like_objects)==0 :
        print("Nu au fost detectate placute in imagine !")
    else:
        license_type,license_plate=validate_plate(plate_like_objects,plate_type_of_objects)

        if license_type==2:
            first_half, second_half=split_plate(license_plate)

            first_half = np.invert(first_half)
            second_half= np.invert(second_half)

            labelled_first_half=measure.label(first_half)
            labelled_second_half=measure.label(second_half)


            calculate_plate(labelled_first_half, 1,license_type,license_plate,first_half,second_half)
            calculate_plate(labelled_second_half,2,license_type,license_plate,first_half,second_half)
            plt.show()

        # the next two lines is based on the assumptions that the width of
        # a license plate should be between 5% and 15% of the license plate,
        # and height should be between 35% and 60%
        # this will eliminate some



        if license_type==0:

            license_plate = np.invert(license_plate)

            labelled_plate = measure.label(license_plate)

            calculate_plate(labelled_plate,0,license_type,license_plate,None,None)


        return blackWhiteImage
    #print('The list of numbers : ')
    #print(cca2.plate_type_of_objects)
