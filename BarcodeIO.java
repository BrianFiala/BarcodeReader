/* ----------------------------------------------------------------------------
 * interface BarcodeIO: defines the method framework for a class that handles
 * BarcodeImage objects. Methods include translation of text to image, and
 * image to text, a scan method for inputting a new BarcodeImage, a readText
 * method for inputing new text, and display methods for the text and
 * BarcodeImage data that must be stored by any implementing class.
 */

interface BarcodeIO
{
   // accepts a BarcodeImage object and stores a copy (a clone or a processed
   // image). Does not do any translation of image data to text
   public boolean scan(BarcodeImage bc);

   // accepts a text String and stores a copy. Does not do any translation
   public boolean readText(String text);

   // produces the corresponding BarcodeImage from the text, and stores it
   // internally. After this method is called, the text and image stored by the
   // implementing class agree with one another.
   public boolean generateImageFromText();

   // produces the companion text from the BarcodeImage, and stores it
   // internally. After this method is called, the text and image stored by the
   // implementing class agree with one another.
   public boolean translateImageToText();

   // prints out the text string to the console.
   public void displayTextToConsole();

   // prints out the image to the console, in a format determined by the
   // implementing class.
   public void displayImageToConsole();
} // end interface BarcodeIO --------------------------------------------------
