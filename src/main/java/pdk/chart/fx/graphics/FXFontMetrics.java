package pdk.chart.fx.graphics;

import javafx.geometry.Bounds;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.CharacterIterator;

/**
 * A font metrics implementation for JavaFX.  This uses the JavaFX APIs to
 * get string bounds, which is more exact than relying on the Java2D/AWT
 * measurements.  The remaining font metrics, however, still come from
 * Java2D/AWT.
 */
public class FXFontMetrics extends FontMetrics {

    /**
     * The graphics target.
     */
    private final Graphics2D g2;

    /**
     * Creates a new instance.
     *
     * @param font the font ({@code null} not permitted).
     * @param g2   the graphics target ({@code null} not permitted).
     */
    public FXFontMetrics(Font font, Graphics2D g2) {
        super(font);
        this.g2 = g2;
    }

    @Override
    public Rectangle2D getStringBounds(CharacterIterator ci, int beginIndex,
                                       int limit, Graphics context) {
        char[] arr = new char[limit - beginIndex];
        ci.setIndex(beginIndex);
        for (int idx = 0; idx < arr.length; idx++) {
            arr[idx] = ci.current();
            ci.next();
        }
        return getStringBounds(arr, beginIndex, limit, context);
    }

    @Override
    public Rectangle2D getStringBounds(char[] chars, int beginIndex, int limit,
                                       Graphics context) {
        String str = new String(chars, beginIndex, limit - beginIndex);
        return getStringBounds(str, context);
    }

    @Override
    public Rectangle2D getStringBounds(String str, int beginIndex, int limit,
                                       Graphics context) {
        String substr = str.substring(beginIndex, limit);
        return super.getStringBounds(substr, context);
    }

    @Override
    public Rectangle2D getStringBounds(String str, Graphics context) {
        Text text = new Text(str);
        FontWeight weight = font.isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture = font.isItalic()
                ? FontPosture.ITALIC : FontPosture.REGULAR;
        javafx.scene.text.Font jfxfont = javafx.scene.text.Font.font(
                font.getFamily(), weight, posture, font.getSize());
        text.setFont(jfxfont);
        Bounds b = text.getLayoutBounds();
        return new Rectangle2D.Double(b.getMinX(), b.getMinY(), b.getWidth(),
                b.getHeight());
    }

    @Override
    public int stringWidth(String str) {
        return (int) getStringBounds(str, this.g2).getWidth();
    }

}
