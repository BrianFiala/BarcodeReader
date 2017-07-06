/* ----------------------------------------------------------------------------
 * Class BarcodeImage: stores a 2D boolean array representing a barcode image,
 * defines accessor and mutator methods for modifying the array, a method for 
 * checking new String[] text data for errors, and implements Cloneable for 
 * instantiating a completely fresh copy of a BarcodeImage object
 */

class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30; // number of rows
   public static final int MAX_WIDTH = 65; // number of columns
   // the exact internal dimensions of 2D data
   
   // a 2D boolean array that represents the image data
   private boolean[][] image_data;
   
   // default constructor
   // instantiates a 2D array (MAX_HEIGHT x MAX_WIDTH) and stuffs it with 
   // blanks (false).
   BarcodeImage()
   {
      image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
      int row, col;
      for (row = 0; row < image_data.length; row++)
         for (col = 0; col < image_data[row].length; col++)
            image_data[row][col] = false;
   }
   
   // constructor using 1-D array of Strings. Converts to 2D array of booleans
   BarcodeImage(String[] str_data)
   {
      this(); // constructor instantiates image_data array with all blanks
      if (!checkSize(str_data))
         return; // emergency return for bad parameter: str_data
      int row, col, strRow;
      for (row = MAX_HEIGHT - str_data.length, strRow = 0; 
         row < MAX_HEIGHT; row++, strRow++)
         for (col = 0; col < str_data[strRow].length(); col++)
            if (str_data[strRow].charAt(col) != ' ')
               image_data[row][col] = true;
   }
   
   // returns the boolean value in image_data at row, col
   public boolean getPixel(int row, int col)
   {
      // if pixel beyond the bounds of image_data, return false
      if (row < 0 || row >= MAX_HEIGHT || col < 0 || col >= MAX_WIDTH)
         return false;
      return image_data[row][col];
   }
   
   // mutator for image_data at row, col
   public boolean setPixel(int row, int col, boolean value)
   {
      // if pixel beyond the bounds of image_data, return false
      if (row < 0 || row >= MAX_HEIGHT || col < 0 || col >= MAX_WIDTH)
         return false;
      image_data[row][col] = value;
      return true;
   }
   
   // checks incoming String[] data for size and null errors
   private boolean checkSize(String[] data)
   {
      if (data == null)
         return false;
      if (data.length > MAX_HEIGHT)
         return false;
      if (data[0].length() > MAX_WIDTH) // rectangle, so only check 1st String
         return false;
      return true;
   }
   
   // a debugging method. Displays image_data[][] as a series of '*'s and ' 's.
   public void displayToConsole()
   {
      int row, col;
      
      // top row border
      System.out.println();
      for (col = 0; col < MAX_WIDTH + 2; col++)
         System.out.print("-");
      System.out.println();
      
      // now each row from 0 to MAX_WIDTH, adding border chars
      for (row = 0; row < MAX_HEIGHT; row++)
      {
         System.out.print("|");
         for (col = 0; col < MAX_WIDTH; col++)
            if(image_data[row][col])
               System.out.print("*");
            else
               System.out.print(" ");
         System.out.println("|");
      }
      
      // bottom row border
      for (col = 0; col < MAX_WIDTH + 2; col++)
         System.out.print("-");
      System.out.println();
   }
   
   // instantiates a completely fresh clone of the BarcodeImage object
   public Object clone() throws CloneNotSupportedException
   {
      int row, col;
      
      // always do this first - parent will clone its data correctly
      BarcodeImage newBCImg = (BarcodeImage)super.clone();
      
      // now do the private boolean class member image_data[][]
      newBCImg.image_data = new boolean[MAX_HEIGHT][MAX_WIDTH];
      for (row = 0; row < MAX_HEIGHT; row++)
         for (col = 0; col < MAX_WIDTH; col++)
            newBCImg.image_data[row][col] = this.image_data[row][col];
      
      return newBCImg;
   }
} // end class BarcodeImage ---------------------------------------------------
