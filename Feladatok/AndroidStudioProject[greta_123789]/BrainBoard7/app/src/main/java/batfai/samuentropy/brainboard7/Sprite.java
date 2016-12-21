package batfai.samuentropy.brainboard7;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;

/**
 * Created by artibarti on 2016.11.05..
 */

public class Sprite
{

    private android.graphics.Bitmap tiles;
    private int length;
    private int width;
    private int height;
    private int boxWidth;
    private int boxHeight;
    private int tileIndex = 0;
    private int x;
    private int y;
    private android.graphics.Rect from;
    private android.graphics.Rect to;
    static java.util.Random random = new java.util.Random();

    public Sprite(android.graphics.Bitmap tiles, int length, int width, int height, int boxWidth, int boxHeight)
    {
        this.tiles = tiles;
        this.length = length;
        this.width = width;
        this.height = height;
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        from = new android.graphics.Rect(0, 0, width, height);
        to = new android.graphics.Rect(0, 0, width, height);

        java.util.Random r = new java.util.Random();

        tileIndex = random.nextInt(length) + 1;

        x = (random.nextInt(100) + 1) * 3;
        y = (random.nextInt(200) + 1) * 3;

    }

    public void step() {

        nextTile();

        int dx = 2 * random.nextInt(2) - 1;
        int dy = 2 * random.nextInt(2) - 1;

        x = Math.abs((x + dx) % (boxWidth-width));
        y = Math.abs((y + dy) % (boxHeight-height));

        setXY(x, y);
    }

    public void nextTile()
    {
        tileIndex = (tileIndex + 1) % length;
    }

    public void setXY(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw(float shiftx, float shifty, android.graphics.Canvas canvas)
    {

        int x = this.x + (int) shiftx;
        int y = this.y + (int) shifty;

        from.left = tileIndex * width;
        from.right = tileIndex * width + width;
        to.left = x+10;
        to.top = y+10;
        to.right = x + width-10;
        to.bottom = y + height-10;

        canvas.drawBitmap(tiles, from, to, null);

    }

}
