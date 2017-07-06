/* ----------------------------------------------------------------------------
 * Class DataMatrix: implements BarcodeIO interface. Stores a BarcodeImage
 * object image, a String text, and defines public methods for translation of 
 * text to image, and image to text, a public scan method for inputting a new
 * BarcodeImage, a public readText method for inputting new text, public
 * display methods for the stored text and BarcodeImage, public accessor
 * methods for the signal width and signal height of the BarcodeImage, a public
 * method for debugging that displays raw image data, private helper methods
 * for determining the signal width and height, and private helper methods for
 * converting individual columns into chars and chars into columns, and a
 * private method for clearing the image data.
 */

class DataMatrix implements BarcodeIO
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';

   // stores image un-encoded, passed-in, or created by generateImageFromText()
   private BarcodeImage image;

   // stores text read-in, passed-in, or created by translateImageToText()
   private String text;

   // actualWidth, actualHeight represent signal region for image
   private int actualWidth;
   private int actualHeight;

   // three overloaded constructors
   // default constructor: sets default image and text
   DataMatrix()
   {
      readText(""); // default text

      // default image, sets matching actualWidth and actualHeight
      scan(new BarcodeImage());
   }

   // sets the image but leaves the text at its default value
   DataMatrix(BarcodeImage image)
   {
      if (!scan(image)) // sets image, actualWidth and actualHeight
         return; // emergency return for bad image data. shouldn't happen...
      readText(""); // default text
   }

   // sets the text but leaves the image at its default value
   DataMatrix(String text)
   {
      if (!readText(text)) // sets the text
         return; // emergency return for text too long

      // default image, sets actualWidth/actualHeight to match image, NOT text
      scan(new BarcodeImage());
   }

   // mutator for image data. Also uses helper methods to make actualWidth,
   // actualHeight match image data
   public boolean scan(BarcodeImage image)
   {
      try
      {
         this.image = (BarcodeImage)image.clone();
      }
      catch (CloneNotSupportedException ex)
      {
         System.out.println("Scan failed. Error cloning BarcodeImage image.");
         return false;
      }

      actualWidth = computeSignalWidth();
      actualHeight = computeSignalHeight();
      return true;
   }

   // mutator for text data
   public boolean readText(String text)
   {
      if (text.length() > BarcodeImage.MAX_WIDTH - 2) // if string too long
         return false;
      this.text = text;
      return true;
   }

   // converts text to image (bool array) with borders, sets actualWidth/Height
   public boolean generateImageFromText()
   {
      // check for bad text data. readText filters, so shouldn't ever happen...
      if (text.length() > BarcodeImage.MAX_WIDTH - 2)
         return false; // emergency return

      clearImage(); // blank image, reset actualWidth, actualHeight
      int k;
      char colChar;
      byte charVal;

      // fill in left and right borders
      for (k = BarcodeImage.MAX_HEIGHT - 1;
         k > BarcodeImage.MAX_HEIGHT - 11; k--)
      {
         image.setPixel(k, 0, true); // left
         if (k % 2 == 1)
            image.setPixel(k, text.length() + 1, true); // right
      }

      // fill in top and bottom borders, and columns
      for (k = 1; k < text.length() + 1; k++)
      {
         image.setPixel(BarcodeImage.MAX_HEIGHT - 1, k, true); // bottom
         if (k % 2 == 0)
            image.setPixel(BarcodeImage.MAX_HEIGHT - 10, k, true); // top

         // fill in columns for each char in the String
         colChar = text.charAt(k - 1);
         charVal = (byte)colChar;
         if (!writeCharToCol(k, charVal)) // check if something went wrong
            return false;
      }

      // set actualHeight, actualWidth with new image data
      actualWidth = computeSignalWidth();
      actualHeight = computeSignalHeight();
      return true;
   }

   // converts the image (bool array) into the ASCII text string it represents
   public boolean translateImageToText()
   {
      int col;
      String newText = "";
      for (col = 1; col < actualWidth - 1; col++)
         newText += readCharFromCol(col);

      return readText(newText);
   }

   // prints out the text string to the console.
   public void displayTextToConsole()
   {
      System.out.println(text);
   }

   // displays only the signal portion of the image, with borders
   public void displayImageToConsole()
   {
      int row, col;

      // top row border
      for (col = 0; col < actualWidth + 2; col++)
         System.out.print("-");
      System.out.println();

      // now each row of the signal, adding border chars
      for (row = BarcodeImage.MAX_HEIGHT - actualHeight;
         row < BarcodeImage.MAX_HEIGHT; row++)
      {
         System.out.print("|");
         for (col = 0; col < actualWidth; col++)
            if(image.getPixel(row, col))
               System.out.print("*");
            else
               System.out.print(" ");
         System.out.println("|");
      }

      // bottom row border
      for (col = 0; col < actualWidth + 2; col++)
         System.out.print("-");
      System.out.println("\n");
   }

   // accessor for actualWidth
   public int getActualWidth()
   {
      return actualWidth;
   }

   // accessor for actualHeight
   public int getActualHeight()
   {
      return actualHeight;
   }

   // returns the width of the signal in image (non-blank region)
   // relies on signal region being correctly aligned with left edge of image
   private int computeSignalWidth()
   {
      int width = 0;

      // handles any size image, including blank image condition
      while (image.getPixel(BarcodeImage.MAX_HEIGHT - 1, width))
         width++;
      return width;
   }

   // returns the height of the signal in image (non-blank region)
   // relies on signal region being correctly aligned with bottom edge of image
   private int computeSignalHeight()
   {
      int height = 0;

      // handles any size image, including blank image condition
      while (image.getPixel(BarcodeImage.MAX_HEIGHT - (1 + height), 0))
         height++;
      return height;
   }

   // reads a column from the image, converts the boolean bits into ASCII char
   private char readCharFromCol(int col)
   {
      int row, powVal;
      byte charVal = 0;
      for (row = BarcodeImage.MAX_HEIGHT - 9, powVal = 7;
         row < BarcodeImage.MAX_HEIGHT - 1; row++, powVal--)
      {
         if(image.getPixel(row, col))
         {
            charVal += (byte) Math.pow(2, powVal);
         }
      }

      return (char)charVal;
   }

   // converts int code (ASCII char value) to booleans in image array at col
   private boolean writeCharToCol(int col, int code)
   {
      if (col < 1 || col >= BarcodeImage.MAX_WIDTH || code < 0 || code > 127)
         return false; // return for invalid parameters

      // cannot use actualHeight because not yet set for new image data
      for (int i = 0, row = BarcodeImage.MAX_HEIGHT - 9; i < 8; i++, row++)
      {
         if ((code & (1 << (7 - i))) > 0)
            image.setPixel(row, col, true);
      }
      return true;
   }

   // for debugging: shows the full image data including blank top and right
   public void displayRawImage()
   {
      image.displayToConsole();
   }

   // blanks the image and resets actualWidth and actualHeight in the simplest
   // way possible. Does not modify text data
   private void clearImage()
   {
      scan(new BarcodeImage());
   }
} // end class DataMatrix -----------------------------------------------------
