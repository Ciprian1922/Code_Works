from skimage import measure
from skimage.measure import regionprops
import matplotlib.pyplot as plt
import matplotlib.patches as patches
from SVC import localization

def getPlateLikeObj(carImagePath):
    # this gets all the connected regions and groups them together
    binary_car_image=localization.getBinaryCarImage(carImagePath)
    label_image = measure.label(binary_car_image)

    # getting the maximum width, height and minimum width and height that a license plate can be
    plate_dimensions = (0.1*label_image.shape[0], 0.5*label_image.shape[0], 0.5*label_image.shape[1], 0.9*label_image.shape[1])
    square_plate_dimensions=(0.15*label_image.shape[0], 0.3*label_image.shape[0], 0.38*label_image.shape[1], 0.5*label_image.shape[1])
    min_height, max_height, min_width, max_width = plate_dimensions
    square_min_height, square_max_height, square_min_width, square_max_width=square_plate_dimensions
    plate_objects_cordinates = []
    plate_like_objects = []
    plate_type_of_objects= []
    #ax1.imshow(localization.gray_car_image, cmap="gray");

    # regionprops creates a list of properties of all the labelled regions
    for region in regionprops(label_image):
        if region.area < 50:
            #if the region is so small then it's likely not a license plate
            continue

        # the bounding box coordinates
        min_row, min_col, max_row, max_col = region.bbox
        region_height = max_row - min_row
        region_width = max_col - min_col
        # ensuring that the region identified satisfies the condition of a typical license plate
        if region_height >= min_height and region_height <= max_height and region_width >= min_width and region_width <= max_width and region_width > region_height :
            plate_like_objects.append(binary_car_image[min_row:max_row,
                                      min_col:max_col])
            plate_type_of_objects.append(0)
            plate_objects_cordinates.append((min_row, min_col,
                                                  max_row, max_col))
            rectBorder = patches.Rectangle((min_col, min_row), max_col-min_col, max_row-min_row, edgecolor="red", linewidth=2, fill=False)
        else :
            if region_height >= square_min_height and region_height <= square_max_height and region_width >= square_min_width and region_width <= square_max_width and region_width > region_height:
                plate_like_objects.append(binary_car_image[min_row:max_row,
                                          min_col:max_col])
                plate_type_of_objects.append(1)
                plate_objects_cordinates.append((min_row, min_col,
                                                 max_row, max_col))
                rectBorder = patches.Rectangle((min_col, min_row), max_col - min_col, max_row - min_row, edgecolor="blue",
                                               linewidth=2, fill=False)




        # let's draw a red rectangle over those regions

    plt.show()
    return binary_car_image,plate_like_objects,plate_type_of_objects