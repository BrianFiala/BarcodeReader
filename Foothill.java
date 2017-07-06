/* name: Brian Fiala
 * date: 5/28/14
 * Class Foothill: instantiates DataMatrix (implements BarcodeIO) and
 * BarcodeImage objects, and tests their methods by calling them to display the
 * image and text data, translating to and from image and text, and tests the
 * clone method during scan operation for inputing new images.
 */

public class Foothill
{
   public static void main(String[] args) throws CloneNotSupportedException
   {
      String[] sImageIn =
         {
         "                                      ",
         "                                      ",
         "                                      ",
         "* * * * * * * * * * * * * * * * *     ",
         "*                                *    ",
         "**** * ****** ** ****** *** ****      ",
         "* ********************************    ",
         "*    *   *  * *  *   *  *   *  *      ",
         "* **    *      *   **    *       *    ",
         "****** ** *** **  ***** * * *         ",
         "* ***  ****    * *  **        ** *    ",
         "* * *   * **   *  *** *   *  * **     ",
         "**********************************    "
         };

      String[] sImageIn_2 =
         {
         "                                          ",
         "                                          ",
         "* * * * * * * * * * * * * * * * * * *     ",
         "*                                    *    ",
         "**** *** **   ***** ****   *********      ",
         "* ************ ************ **********    ",
         "** *      *    *  * * *         * *       ",
         "***   *  *           * **    *      **    ",
         "* ** * *  *   * * * **  *   ***   ***     ",
         "* *           **    *****  *   **   **    ",
         "****  *  * *  * **  ** *   ** *  * *      ",
         "**************************************    "
         };

      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);

      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // create your own message
      dm.readText("this barcode scanning project was a bit more complicated than I anticipated");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
   } // end main
} // end class Foothill -------------------------------------------------------
