/* ----------------------------------------------------------------------------
 * Class BarcodeImage: stores a 2D boolean array representing a barcode image,
 * defines accessor and mutator methods for modifying the array, a method for
 * checking new String[] text data for errors, and implements Cloneable for
 * instantiating a completely fresh copy of a BarcodeImage object
 */

/* ----------------------------------------------------------------------------
 * interface BarcodeIO: defines the method framework for a class that handles
 * BarcodeImage objects. Methods include translation of text to image, and
 * image to text, a scan method for inputing a new BarcodeImage, a readText
 * method for inputing new text, and display methods for the text and
 * BarcodeImage data that must be stored by any implementing class.
 */

 /* ----------------------------------------------------------------------------
  * Class DataMatrix: implements BarcodeIO interface. Stores a BarcodeImage
  * object image, a String text, and defines public methods for translation of
  * text to image, and image to text, a public scan method for inputing a new
  * BarcodeImage, a public readText method for inputing new text, public
  * display methods for the stored text and BarcodeImage, public accessor
  * methods for the signal width and signal height of the BarcodeImage, a public
  * method for debugging that displays raw image data, private helper methods
  * for determining the signal width and height, and private helper methods for
  * converting individual columns into chars and chars into columns, and a
  * private method for clearing the image data.
  */

 /* ----------------------------------------------------------------------------
  * Class Foothill: instantiates DataMatrix (implements BarcodeIO) and
  * BarcodeImage objects, and tests their methods by calling them to display the
  * image and text data, translating to and from image and text, and tests the
  * clone method during scan operation for inputing new images.
  */
