package batfai.samuentropy.brainboard7;

/**
 * Created by artibarti on 2016.11.05..
 */

public class ScaleAdapter extends android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
{
    private NorbironSurfaceView surfaceView;

    public ScaleAdapter(NorbironSurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    @Override
    public boolean onScale(android.view.ScaleGestureDetector detector) {

        float scaleFactor = surfaceView.getScaleFactor() * detector.getScaleFactor();
        surfaceView.setScaleFactor(Math.max(0.5f, Math.min(scaleFactor, 3.0f)));

        surfaceView.repaint();

        return true;
    }

}
