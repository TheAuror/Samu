package batfai.samuentropy.brainboard7;

/**
 * Created by artibarti on 2016.11.05..
 */

public class NeuronBox implements Cloneable
{

    public int SLOT_SIZE = 120;
    //public int PROCESSOR_SIZE_X = 80;
    //public int PROCESSOR_SIZE_Y = 80;
    public int PROCESSOR_FRAME_SIZE = 20;

    private Sprite[] neurons;

    private android.graphics.Bitmap tiles;
    private android.graphics.Bitmap cover;

    android.graphics.Rect to;

    private int boxWidth;
    private int boxHeight;

    private int x;
    private int y;

    int numberOfNeurons;

    protected static android.graphics.Paint boxPaint = new android.graphics.Paint();
    protected static android.graphics.Paint selectedBoxPaint = new android.graphics.Paint();
    protected int selectedBoxPaintSize = 0;

    protected boolean open = false;
    protected boolean selected = false;

    private int type = 2;



    public NeuronBox(android.graphics.Bitmap tiles, int length, int width, int height,
                     int numberOfNeurons,
                     android.graphics.Bitmap cover, int x, int y) {

        this.x = x * SLOT_SIZE + PROCESSOR_FRAME_SIZE;
        this.y = y * SLOT_SIZE + PROCESSOR_FRAME_SIZE;
        this.tiles = tiles;

        this.cover = cover;
        this.boxWidth = cover.getWidth();
        this.boxHeight = cover.getHeight();

        to = new android.graphics.Rect(0, 0, boxWidth, boxHeight);

        this.numberOfNeurons = numberOfNeurons;

        boxPaint.setColor(android.graphics.Color.argb(0xaf, 0xc4, 0xd9,0xbf));
        boxPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        boxPaint.setAntiAlias(true);
        boxPaint.setStrokeWidth(55);
        boxPaint.setAlpha(170);

        selectedBoxPaint.setColor(android.graphics.Color.argb(0x8f, 0x44, 0xd9, 0xbf));
        selectedBoxPaint.setStyle(android.graphics.Paint.Style.STROKE);
        selectedBoxPaint.setAntiAlias(true);
        selectedBoxPaint.setStrokeWidth(1);

        neurons = new Sprite[numberOfNeurons];
        for (int i = 0; i < neurons.length; ++i) {
            neurons[i] = new Sprite(tiles, length, width, height, boxWidth, boxHeight);
        }
    }

    public Object clone()
    {

        try
        {
            return super.clone();
        }

        catch (CloneNotSupportedException ex)
        {
            java.util.logging.Logger.getLogger(NeuronBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        return null;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean getSelected()
    {
        return selected;
    }

    public void setCover(boolean open)
    {
        this.open = open;
    }

    public boolean getCover()
    {
        return open;
    }

    public int getWidth()
    {
        return boxWidth;
    }

    public int getHeight()
    {
        return boxHeight;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean setXY(float x, float y)
    {
        int oldX = this.x;
        int oldY = this.y;

        this.x = (int) x * SLOT_SIZE + PROCESSOR_FRAME_SIZE;
        this.y = (int) y * SLOT_SIZE + PROCESSOR_FRAME_SIZE;

        if(oldX != this.x || oldY != this.y)
        {
            return true;
        }

        return false;
    }


    public void draw(float shiftx, float shifty, android.graphics.Canvas canvas)
    {
        for (int i = 0; i < neurons.length; ++i)
        {
            neurons[i].draw(x + shiftx, y + shifty, canvas);
        }

        canvas.drawRect(x + shiftx, y + shifty, x + shiftx + boxWidth, y + shifty + boxHeight, boxPaint);

        if (!open)
        {
            to.left = x + (int) shiftx;
            to.top = y + (int) shifty;
            to.right = to.left + boxWidth;
            to.bottom = to.top + boxHeight;
            canvas.drawBitmap(cover, null, to, null);
        }
        if (selected)
        {
            selectedBoxPaintSize = (selectedBoxPaintSize + 3) % 12;
            selectedBoxPaint.setStrokeWidth(1 + selectedBoxPaintSize);

            canvas.drawRect(x + shiftx - selectedBoxPaintSize,
                    y + shifty - selectedBoxPaintSize,
                    x + shiftx + boxWidth + 2 * selectedBoxPaintSize,
                    y + shifty + boxHeight + 2 * selectedBoxPaintSize, selectedBoxPaint);
        }
    }


    public void step()
    {
        for (int i = 0; i < neurons.length; ++i)
        {
            neurons[i].step();
        }
    }

}
