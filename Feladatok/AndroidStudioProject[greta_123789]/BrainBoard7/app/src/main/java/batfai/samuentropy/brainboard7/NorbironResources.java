package batfai.samuentropy.brainboard7;

/**
 * Created by artibarti on 2016.11.05..
 */

class NorbironResource
{

    private NorbironSurfaceView surfaceView;
    private android.graphics.Bitmap bitmap;
    private int resID;
    private int x;
    private int y;
    private int posX;
    private int posY;


    public NorbironResource(int x, int y, int resID, NorbironSurfaceView surfaceView)
    {
        this.x = x;
        this.y = y;
        this.resID = resID;
        this.surfaceView = surfaceView;

        this.bitmap = android.graphics.BitmapFactory.decodeResource(surfaceView.getResources(), resID);
        this.bitmap = android.graphics.Bitmap.createScaledBitmap(this.bitmap, x, y, false);
    }

    public android.graphics.Bitmap getBitmap()
    {
        return bitmap;
    }

}


public class NorbironResources
{

    private Integer[] procIDs =
            {
                    R.drawable.randnmproci, R.drawable.gaussnmproci,
                    R.drawable.zeronmproci, R.drawable.unifnmproci,
                    R.drawable.addproci, R.drawable.mulproci,
                    R.drawable.nandironproci, R.drawable.nandironproci2,
                    R.drawable.matyironproci, R.drawable.matyironproci2,
                    R.drawable.gretironproci, R.drawable.gretironproci2,
                    R.drawable.boxproci
            };

    private Integer[] imgIDs =
            {
                    R.drawable.pcb550i, 1, 1,
                    R.drawable.pcb550i_red, 1, 1,
                    R.drawable.brainboard_board, 4, 1,
                    R.drawable.clash, 4, 2
            };

    private Integer[] menuIDs =
            {
                    R.drawable.buildproci, R.drawable.boxinproci
            };

    private Integer[] switchIDs =
            {

            };


    private int BLOCK_SIZE;
    private NorbironSurfaceView surfaceView;
    private java.util.ArrayList<NeuronBox> neuronBoxes;
    private java.util.ArrayList<NeuronBox> menuBoxes;
    private java.util.ArrayList<NeuronBox> switches;
    private java.util.ArrayList<android.graphics.Bitmap> images;
    private int boxin = 0;


    public NorbironResources(int BLOCK_SIZE, NorbironSurfaceView surfaceView)
    {
        this.surfaceView = surfaceView;
        this.BLOCK_SIZE = BLOCK_SIZE;

        neuronBoxes = new java.util.ArrayList<NeuronBox>();
        menuBoxes = new java.util.ArrayList<NeuronBox>();
        images = new java.util.ArrayList<android.graphics.Bitmap>();

        initResources();
    }

    public void initResources()
    {

        android.graphics.Bitmap neuronSprite = android.graphics.BitmapFactory.decodeResource(surfaceView.getResources(), R.drawable.neuronsprite);
        neuronSprite = android.graphics.Bitmap.createScaledBitmap(neuronSprite, 64 * 2 * 14, 62, false);

        for(int i = 0; i<procIDs.length; i++)
        {
            NorbironResource norbironResource = new NorbironResource( (int)(BLOCK_SIZE * 0.6), (int)(BLOCK_SIZE * 0.6) ,procIDs[i], surfaceView);
            neuronBoxes.add(new NeuronBox(neuronSprite, 2 * 14, 64, 62, 10, norbironResource.getBitmap(), 0, 0));
        }
        for(int i = 0; i<imgIDs.length; i+=3)
        {
            NorbironResource norbironResource = new NorbironResource(imgIDs[i+1] * BLOCK_SIZE, imgIDs[i+2] * BLOCK_SIZE, imgIDs[i], surfaceView);
            images.add(norbironResource.getBitmap());
        }
        for(int i = 0; i<menuIDs.length; i++)
        {
            NorbironResource norbironResource = new NorbironResource( (int)(BLOCK_SIZE * 0.6), (int)(BLOCK_SIZE * 0.6), menuIDs[i], surfaceView);
            menuBoxes.add(new NeuronBox(neuronSprite, 2 * 14, 64, 62, 10, norbironResource.getBitmap(), 0, 0));
        }
    }

    public NeuronBox getProc(int i)
    {
        if(i >= neuronBoxes.size())
            return neuronBoxes.get(neuronBoxes.size()-1);
        return neuronBoxes.get(i);
    }

    public NeuronBox getMenu(int i)
    {
        return menuBoxes.get(i);
    }

    public android.graphics.Bitmap getImage(int i)
    {
        return images.get(i);
    }

    public java.util.ArrayList<Integer> getProcResIDs()
    {
        java.util.ArrayList<Integer> ids = new java.util.ArrayList<Integer>();
        for(int i = 0; i<procIDs.length-1; i++)
        {
            ids.add(procIDs[i]);
        }
        for(int i = 0; i<boxin; i++)
        {
            ids.add(procIDs[procIDs.length-1]);
        }
        return ids;
    }

    public void newBox()
    {
        boxin++;
    }
}
