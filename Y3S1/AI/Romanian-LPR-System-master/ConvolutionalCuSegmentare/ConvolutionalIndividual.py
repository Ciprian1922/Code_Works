import matplotlib.pyplot as plt
import tensorflow as tf
import numpy as np
from PIL import Image, ImageOps
from sklearn.metrics import confusion_matrix
import time
from datetime import timedelta
import math
from mnist import MNIST
from SVC import segmentation
from SVC import preprocess



valori="0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"

# Convolutional Layer 1.
initializat = True
filter_size1 = 5          # Convolution filters are 5 x 5 pixels.
num_filters1 = 16         # There are 16 of these filters.

# Convolutional Layer 2.
filter_size2 = 5          # Convolution filters are 5 x 5 pixels.
num_filters2 = 36         # There are 36 of these filters.

# Fully-connected layer.
fc_size = 128             # Number of neurons in fully-connected layer.


img_size = 40

# Images are stored in one-dimensional arrays of this length.
img_size_flat = img_size * img_size

# Tuple with height and width of images used to reshape arrays.
img_shape = (img_size, img_size)

# Number of colour channels for the images: 1 channel for gray-scale.
num_channels = 1

# Numarul de clase este 36 pentru ca atatea caractere pot aparea in nr de inmatriculare
num_classes = 36


def prelucare_labels(labels):
    L= len(labels)
    lbl_shape = (L, 36)
    new_labels = np.zeros(lbl_shape)
    for i in range(0, L-1):
        new_labels[i][labels[i]] = 1
    return new_labels


def read_training_data(path):
    mndata = MNIST(path)
    print('Incepe citirea datelor de training!\n')
    images, labels = mndata.load_training()
    print('Citire incheiata cu succes!\n')
    print("Size of:")
    print("- Training-set:\t\t{}".format(len(labels)))
    return images, labels


def read_testing_data(path):
    mndata = MNIST(path)
    print('Incepe citirea datelor de test!\n')
    images, labels = mndata.load_testing()
    print('Citire incheiata cu succes!\n')
    print("Size of:")
    print("- Training-set:\t\t{}".format(len(labels)))
    return images, labels

def next_batch(num, data, labels):
    '''
    Return a total of `num` random samples and labels.
    '''
    idx = np.arange(0 , len(data))
    np.random.shuffle(idx)
    idx = idx[:num]
    data_shuffle = [data[ i] for i in idx]
    labels_shuffle = [labels[ i] for i in idx]
    return np.asarray(data_shuffle), np.asarray(labels_shuffle)


def plot_images(images, cls_true, cls_pred=None):
    assert len(images) == len(cls_true) == 9

    # Create figure with 3x3 sub-plots.
    fig, axes = plt.subplots(3, 3)
    fig.subplots_adjust(hspace=0.3, wspace=0.3)

    for i, ax in enumerate(axes.flat):
        # Plot image.
        aux_im = np.array(images[i])
        ax.imshow(aux_im.reshape(img_shape), cmap='binary')

        # Show true and predicted classes.
        if cls_pred is None:
            xlabel = "True: {0}".format(cls_true[i])
        else:
            xlabel = "True: {0}, Pred: {1}".format(cls_true[i], cls_pred[i])

        # Show the classes as the label on the x-axis.
        ax.set_xlabel(xlabel)

        # Remove ticks from the plot.
        ax.set_xticks([])
        ax.set_yticks([])

    # Ensure the plot is shown correctly with multiple plots
    # in a single Notebook cell.
    plt.show()


def new_weights(shape):
    return tf.Variable(tf.truncated_normal(shape, stddev=0.05))


def new_biases(length):
    return tf.Variable(tf.constant(0.05, shape=[length]))


def new_conv_layer(input,  # The previous layer.
                   num_input_channels,  # Num. channels in prev. layer.
                   filter_size,  # Width and height of each filter.
                   num_filters,  # Number of filters.
                   use_pooling=True):  # Use 2x2 max-pooling.

    # Shape of the filter-weights for the convolution.
    # This format is determined by the TensorFlow API.
    shape = [filter_size, filter_size, num_input_channels, num_filters]

    # Create new weights aka. filters with the given shape.
    weights = new_weights(shape=shape)

    # Create new biases, one for each filter.
    biases = new_biases(length=num_filters)

    # Create the TensorFlow operation for convolution.
    # Note the strides are set to 1 in all dimensions.
    # The first and last stride must always be 1,
    # because the first is for the image-number and
    # the last is for the input-channel.
    # But e.g. strides=[1, 2, 2, 1] would mean that the filter
    # is moved 2 pixels across the x- and y-axis of the image.
    # The padding is set to 'SAME' which means the input image
    # is padded with zeroes so the size of the output is the same.
    layer = tf.nn.conv2d(input=input,
                         filter=weights,
                         strides=[1, 1, 1, 1],
                         padding='SAME')

    # Add the biases to the results of the convolution.
    # A bias-value is added to each filter-channel.
    layer += biases

    # Use pooling to down-sample the image resolution?
    if use_pooling:
        # This is 2x2 max-pooling, which means that we
        # consider 2x2 windows and select the largest value
        # in each window. Then we move 2 pixels to the next window.
        layer = tf.nn.max_pool(value=layer,
                               ksize=[1, 2, 2, 1],
                               strides=[1, 2, 2, 1],
                               padding='SAME')

    # Rectified Linear Unit (ReLU).
    # It calculates max(x, 0) for each input pixel x.
    # This adds some non-linearity to the formula and allows us
    # to learn more complicated functions.
    layer = tf.nn.relu(layer)

    # Note that ReLU is normally executed before the pooling,
    # but since relu(max_pool(x)) == max_pool(relu(x)) we can
    # save 75% of the relu-operations by max-pooling first.

    # We return both the resulting layer and the filter-weights
    # because we will plot the weights later.
    return layer, weights


def flatten_layer(layer):
    # Get the shape of the input layer.
    layer_shape = layer.get_shape()

    # The shape of the input layer is assumed to be:
    # layer_shape == [num_images, img_height, img_width, num_channels]

    # The number of features is: img_height * img_width * num_channels
    # We can use a function from TensorFlow to calculate this.
    num_features = layer_shape[1:4].num_elements()

    # Reshape the layer to [num_images, num_features].
    # Note that we just set the size of the second dimension
    # to num_features and the size of the first dimension to -1
    # which means the size in that dimension is calculated
    # so the total size of the tensor is unchanged from the reshaping.
    layer_flat = tf.reshape(layer, [-1, num_features])

    # The shape of the flattened layer is now:
    # [num_images, img_height * img_width * num_channels]

    # Return both the flattened layer and the number of features.
    return layer_flat, num_features


def new_fc_layer(input,  # The previous layer.
                 num_inputs,  # Num. inputs from prev. layer.
                 num_outputs,  # Num. outputs.
                 use_relu=True):  # Use Rectified Linear Unit (ReLU)?

    # Create new weights and biases.
    weights = new_weights(shape=[num_inputs, num_outputs])
    biases = new_biases(length=num_outputs)

    # Calculate the layer as the matrix multiplication of
    # the input and weights, and then add the bias-values.
    layer = tf.matmul(input, weights) + biases

    # Use ReLU?
    if use_relu:
        layer = tf.nn.relu(layer)

    return layer

def test_only_one_pic(path):
    reader = tf.WholeFileReader()
    filename_queue = tf.train.string_input_producer([path])
    key, value = reader.read(filename_queue)
    my_img = tf.image.decode_png(value)
    return my_img

def one_time_test(image_to_test,session,x,y_true,y_pred_cls):
        # Number of images in the test-set.

        # Allocate an array for the predicted classes which
        # will be calculated in batches and filled into this array.
        num=len(image_to_test)
        imagine=np.zeros(shape=(1,img_size*img_size),dtype=np.int)
        eticheta=np.zeros(shape=(1,36),dtype=np.int)
        imagine[0] = image_to_test

        cls_pred = np.zeros(shape=num, dtype=np.int)

        # Now calculate the predicted classes for the batches.
        # We will just iterate through all the batches.
        # There might be a more clever and Pythonic way of doing this.

        # The starting index for the next batch is denoted i.
        label='0'
        eticheta[0]=label
        feed_dict = {x: imagine, y_true:eticheta}


        predicted = session.run(y_pred_cls, feed_dict=feed_dict)
        return valori[np.int(predicted)]

def plot_image(image):
    plt.imshow(image.reshape(img_shape),
               interpolation='nearest',
               cmap='binary')

    plt.show()

def imageprepare(argv):
    """
    This function returns the pixel values.
    The imput is a png file location.
    """
    im = Image.open(argv).convert('L')
    width = float(im.size[0])
    height = float(im.size[1])


    tv = list(im.getdata())  # get pixel values

    # normalize pixels to 0 and 1. 0 is pure white, 1 is pure black. 255 is white

    tva=np.zeros(3136)
   # for i in range(0,len(tva)):
   #    if tv[i]<120:
   #         tva[i]=1
   #     else:
   #         tva[i]=0
    #tva = [(255 - x) * 1.0 / 255.0 for x in tv]
    #tva = [abs(x-1) for x in tva]
    tva=tv

    #print("this is tv")
    #print(tv)
    #print("this is TVA")
    print(tva)
    return tva

def load_image(path):
    '''
    filename_queue = tf.train.string_input_producer(
        [path], num_epochs=1)
    reader = tf.WholeFileReader()
    key, value = reader.read(filename_queue)
    my_img = tf.image.decode_png(value)  # use png or jpg decoder based on your files.

    '''
    img_as_tensor = imageprepare(path)
    return img_as_tensor

def borderize(image,borderSize):
    row, col = image.shape[:2]
    thisShape=(row+2*borderSize,col+2*borderSize)
    bordered_each = np.zeros(thisShape)
    rowInd=borderSize
    colInd=borderSize
    for r in image:
        for c in r:
            bordered_each[rowInd][colInd]=c
            colInd+=1
        colInd=borderSize
        rowInd += 1
    return bordered_each

def buildGraph():
    #building the graph
    x = tf.placeholder(tf.float32, shape=[None, img_size_flat], name='x')

    x_image = tf.reshape(x, [-1, img_size, img_size, num_channels])

    y_true = tf.placeholder(tf.float32, shape=[None, num_classes], name='y_true')

    y_true_cls = tf.argmax(y_true, axis=1)


    layer_conv1, weights_conv1 = \
        new_conv_layer(input=x_image,
                       num_input_channels=num_channels,
                       filter_size=filter_size1,
                       num_filters=num_filters1,
                       use_pooling=True)


    layer_conv2, weights_conv2 = \
        new_conv_layer(input=layer_conv1,
                       num_input_channels=num_filters1,
                       filter_size=filter_size2,
                       num_filters=num_filters2,
                       use_pooling=True)


    layer_flat, num_features = flatten_layer(layer_conv2)


    layer_fc1 = new_fc_layer(input=layer_flat,
                             num_inputs=num_features,
                             num_outputs=fc_size,
                             use_relu=True)


    layer_fc2 = new_fc_layer(input=layer_fc1,
                             num_inputs=fc_size,
                             num_outputs=num_classes,
                             use_relu=False)


    y_pred = tf.nn.softmax(layer_fc2)

    y_pred_cls = tf.argmax(y_pred, axis=1)

    cross_entropy = tf.nn.softmax_cross_entropy_with_logits(logits=layer_fc2,
                                                            labels=y_true)
    cost = tf.reduce_mean(cross_entropy)

    optimizer = tf.train.AdamOptimizer(learning_rate=1e-4).minimize(cost)

    correct_prediction = tf.equal(y_pred_cls, y_true_cls)

    accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))


    saver = tf.train.Saver()


    session = tf.Session()
    # delete the current graph
    tf.reset_default_graph()
    # import the graph from the file
    imported_graph = tf.train.import_meta_graph('ConvolutionalCuSegmentare/my-model.meta')
    saver.restore(session, 'ConvolutionalCuSegmentare/my-model')
    return x,y_true,y_pred_cls,session

def predictConvolutionalIndividual(imagePath,session,x,y_true,y_pred_cls):

    segmentation.segmentate(imagePath)
    numberPlate=[]
    for each in segmentation.characters:
        each=np.floor(255*each)
        row, col = each.shape[:2]
        each=borderize(each,5)
     #   print(row, col)
        each = preprocess.resize(each, (40, 40),anti_aliasing_sigma=True)
        each=np.floor(each)
        each = abs(each - 255)
        each = np.reshape(each,1600)
        numberPlate.append(one_time_test(each,session,x,y_true,y_pred_cls))
   # session.close()

    rightplate_string = ''
    column_list_copy = segmentation.column_list[:]
    segmentation.column_list.sort()

    for each in segmentation.column_list:
        rightplate_string += numberPlate[column_list_copy.index(each)]

    segmentation.characters = []
    segmentation.column_list = []

    return rightplate_string



