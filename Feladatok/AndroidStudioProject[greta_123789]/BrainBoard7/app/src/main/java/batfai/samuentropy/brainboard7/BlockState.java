package batfai.samuentropy.brainboard7;

/**
 * Created by artibarti on 2016.11.05..
 */

public class BlockState
{
    private int x;
    private int y;
    private int resID;
    private int itemType; // 0 - menu; 1 - proc
    private NeuronBox nb;

    public BlockState(int itemType, int x, int y, NeuronBox nb)
    {
        this.resID = resID;
        this.itemType = itemType;
        this.x = x;
        this.y = y;
        this.nb = nb;
    }

    public int getType()
    {
        return itemType;
    }

    public void setType(int t)
    {
        this.itemType = t;
    }

    public int getResID()
    {
        return resID;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public NeuronBox getNeuronBox()
    {
        return nb;
    }
}
