import cv2
import sys
import numpy
import glob
import tensorflow as tf
from skimage.transform import resize
from ConvolutionalIntreg import model
from ConvolutionalIntreg import common


def resize_im(im):
    height=im.shape[0]
    width=im.shape[1]
    cropedHeight=height*0.18
    cropedWidth=width*0.25
    cropedIm=im[int(height/2-cropedHeight):int(height/2+cropedHeight), int(width/2-cropedWidth):int(width/2+cropedWidth)]
    resizedCropedIm=resize(cropedIm, (64, 128), anti_aliasing=True)
    cv2.imshow("croped", resizedCropedIm)
    return resizedCropedIm

def vec_to_plate(v):
    return "".join(common.CHARS[i] for i in v)

def recreateModel(trainingParams):
    x, y, params = model.get_training_model()
    assert len(params) == len(trainingParams)
    assign_ops = [w.assign(v) for w, v in zip(params, trainingParams)]
    init = tf.global_variables_initializer()
    with tf.Session() as sess:
        sess.run(init)
   #     sess.run(assign_ops)
    return x,y,params



def loadGraph():
    #citire ponderi
    f = numpy.load("ConvolutionalIntreg/ctask_weights/weights95300.npz")
    initialWeights = [f[n] for n in sorted(f.files, key=lambda s: int(s[4:]))]

    #recreare model
    x, y, params = model.get_training_model()
    y = tf.argmax(tf.reshape(y[:, 1:], [-1, 7, len(common.CHARS)]), 2, name='best')

    #incarcare ponderi
    assert len(params) == len(initialWeights)
    assign_ops = [w.assign(v) for w, v in zip(params, initialWeights)]
    init = tf.initialize_all_variables()
    sess=tf.Session()
    sess.run(init)
    sess.run(assign_ops)

    return sess,x, y


def myDetect(sess,x,y,imagePath):


         im = cv2.imread(imagePath)[:, :, :].astype(numpy.float32)
         im_gray = cv2.cvtColor(im, cv2.COLOR_BGR2GRAY) / 255.
         im_gray=resize_im(im_gray)
         im=numpy.reshape(im_gray, (1, 64, 128))
         feed_dict = {x: im}
         r = sess.run(y, feed_dict=feed_dict)
         return r

