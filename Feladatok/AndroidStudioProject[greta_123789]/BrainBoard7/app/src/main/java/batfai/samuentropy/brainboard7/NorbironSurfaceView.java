package batfai.samuentropy.brainboard7;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by artibarti on 2016.11.05..
 */


public class NorbironSurfaceView extends android.view.SurfaceView implements Runnable
{
    public int SLOT_SIZE = 120;
    private NorbironMap norbironMap = new NorbironMap(this);
    private static java.util.ArrayList<BlockState> savedStates = new java.util.ArrayList<BlockState>();
	private String currentUser;

    private float startsx = 0;
    private float startsy = 0;
    private float width = 2048;
    private float height = 2048;

    protected float swidth;
    protected float sheight;
    protected float fromsx;
    protected float fromsy;

    private android.view.SurfaceHolder surfaceHolder;
    private android.view.ScaleGestureDetector scaleGestureDetector;
    private float scaleFactor = 0.3f;

    private static boolean inited;
    private boolean running = true;
    private android.content.Context context;
    protected NeuronBox selNb = null;

    public void setScaleFactor(float scaleFactor)
    {
        this.scaleFactor = scaleFactor;
    }
    public float getScaleFactor()
    {
        return scaleFactor;
    }

    public NorbironSurfaceView(android.content.Context context, String user)
    {
        super(context);
        currentUser = user;
        norbironMap.clearMap();
        cinit(context);
    }
    public NorbironSurfaceView(android.content.Context context)
    {
        super(context);
        cinit(context);
    }
    public NorbironSurfaceView(android.content.Context context, android.util.AttributeSet attrs)
    {
        super(context, attrs);
        cinit(context);
    }
    public NorbironSurfaceView(android.content.Context context, android.util.AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        cinit(context);
    }

    @Override
    protected void onSizeChanged(int newx, int newy, int x, int y)
    {

        super.onSizeChanged(newx, newy, x, y);

        width = newx;
        height = newy;
        swidth = width / 2 - SLOT_SIZE / 2;
        sheight = height / 2 - SLOT_SIZE / 2;

    }

    public void initMenuNodes()
    {
        if (!inited)
        {
            norbironMap.addMenu(0,4,3);
            norbironMap.addMenu(1,5,3);
            inited = true;
        }
    }

    private void cinit(android.content.Context context)
    {
        this.context = context;
        norbironMap.setSurfaceView(this);

        android.content.Intent intent = ((NeuronGameActivity) context).getIntent();
        android.os.Bundle bundle = intent.getExtras();
        norbironMap.clearMap();
        norbironMap.initMapFromServer(currentUser);

        if (bundle != null)
        {
            int i = bundle.getInt("selectedNode");
            norbironMap.addProc(i, 0,3);
        }

        norbironMap.saveMapToServer(currentUser);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceEvents(this));
        scaleGestureDetector = new android.view.ScaleGestureDetector(context, new ScaleAdapter(this));
    }

    @Override
    public void onDraw(android.graphics.Canvas canvas)
    {

        if (surfaceHolder.getSurface().isValid())
        {
            norbironMap.draw(canvas, scaleFactor, startsx, startsy);
        }
    }

    public void repaint()
    {
        android.graphics.Canvas canvas = null;
        try
        {
            canvas = surfaceHolder.lockCanvas();
            if (canvas != null)
            {
                onDraw(canvas);
            }
        }
        finally
        {
            if (canvas != null)
            {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void newNode()
    {
        norbironMap.saveMapToServer(currentUser);
        android.content.Intent intent = new android.content.Intent(context, NodeActivity.class);
        intent.putIntegerArrayListExtra("nodeIds", norbironMap.getProcResIDs());
        intent.putExtra("currentUser", currentUser);
        context.startActivity(intent);
    }

    public void newBox()
    {
        norbironMap.clearMap();
        norbironMap.clearDatabase(currentUser);
        norbironMap.newBox();
        android.widget.Toast.makeText(context, "Table cleared", android.widget.Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event)
    {
        scaleGestureDetector.onTouchEvent(event);

        float x = event.getX() / scaleFactor;
        float y = event.getY() / scaleFactor;

        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN)
        {
            fromsx = x;
            fromsy = y;

            NeuronBox nb2 = norbironMap.getNearestNeuron(x + startsx, y + startsy);

            if(nb2 != null)
            {
                if (nb2.getType() == 0)
                {
                    newNode();
                }
                else if (nb2.getType() == 1)
                {
                    newBox();
                }
                else
                {
                    nb2.setCover(!nb2.getCover());
                    nb2.setSelected(!nb2.getSelected());
                    selNb = nb2;
                }
            }
            else
            {
                selNb = null;
            }
        }

        else if (event.getAction() == android.view.MotionEvent.ACTION_POINTER_DOWN)
        {
            selNb = null;
        }

        else if (event.getAction() == android.view.MotionEvent.ACTION_CANCEL)
        {

        }

        else if (event.getAction() == android.view.MotionEvent.ACTION_MOVE)
        {

            if (selNb != null)
            {

                int nx,ny;

                nx = (int)(x + startsx) / SLOT_SIZE;
                ny = (int)(y + startsy) / SLOT_SIZE;

                /*
                if (norbironMap.checkPosition(nx, ny) == true)
                {
                    selNb.setXY(nx,ny);
                    norbironMap.saveMapToServer(currentUser);
                }
                */

                selNb.setXY(nx,ny);
                norbironMap.saveMapToServer(currentUser);

                fromsx = x;
                fromsy = y;

            }

            else if (Math.abs(fromsx - x) + Math.abs(fromsy - y) > 25)
            {
                startsx += (fromsx - x);
                startsy += (fromsy - y);

                fromsx = x;
                fromsy = y;
            }

            repaint();
        }

        else if (event.getAction() == android.view.MotionEvent.ACTION_UP)
        {
            if (selNb != null)
            {
                int nx,ny;

                nx = (int)(x + startsx) / SLOT_SIZE;
                ny = (int)(y + startsy) / SLOT_SIZE;


                /*
                if (norbironMap.checkPosition(nx, ny) == true)
                {
                    selNb.setXY(nx,ny);
                }
                */

                selNb.setXY(nx,ny);


                fromsx = x;
                fromsy = y;

                selNb = null;
            }
        }
        return true;
    }


    public void stop()
    {
        running = false;
    }

    @Override
    public void run()
    {
        long now = System.currentTimeMillis(), newnow;
        running = true;
        while (running)
        {
            if ((newnow = System.currentTimeMillis()) - now > 100)
            {
                norbironMap.stepNeurons();
                repaint();
                now = newnow;
            }
        }
    }

}
