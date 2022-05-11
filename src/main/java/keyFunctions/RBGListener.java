package keyFunctions;

import java.awt.*;


public class RBGListener{

    static Color getColorAt(int x, int y) throws AWTException {
        return new Robot().getPixelColor(x, y);
    }


    public static int[] getPixelRGB(int x, int y) throws AWTException {
        return new int[]{
                getColorAt(x, y).getRed(),
                getColorAt(x, y).getGreen(),
                getColorAt(x, y).getBlue(),
                getColorAt(x, y).getAlpha()
        };
    }


}
