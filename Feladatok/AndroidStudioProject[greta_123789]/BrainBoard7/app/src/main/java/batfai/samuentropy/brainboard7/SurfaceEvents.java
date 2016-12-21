package batfai.samuentropy.brainboard7;

/**
 * Created by artibarti on 2016.11.05..
 */

public class SurfaceEvents implements android.view.SurfaceHolder.Callback
{

    private NorbironSurfaceView surfaceView;
    Thread thread;

    public SurfaceEvents(NorbironSurfaceView surfaceView) {

        this.surfaceView = surfaceView;
    }

    @Override
    public void surfaceDestroyed(android.view.SurfaceHolder holder) {

        surfaceView.stop();
        thread = null;
    }

    @Override

    public void surfaceCreated(android.view.SurfaceHolder holder) {

        thread = new Thread(surfaceView);
        thread.start();
        surfaceView.repaint();

    }

    @Override
    public void surfaceChanged(android.view.SurfaceHolder holder, int format,
                               int width, int height) {

        surfaceView.repaint();

    }

}
