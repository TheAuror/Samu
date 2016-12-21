package batfai.samuentropy.brainboard7;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * Created by artibarti on 2016.11.05..
 */

class Vec2d
{
    private int x,y;

    public Vec2d(int x, int y)
    {
        x = this.x;
        y = this.y;
    }

    public int getX() {return this.x;}
    public int getY() {return this.y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
}

public class NorbironMap
{
    private	int n = 10;
    private int m = 10;
    private int[][] map;
    private int BLOCK_SIZE = 120;
    private NorbironSurfaceView surfaceView;
    private NorbironResources norbironResources;
    //private java.util.ArrayList<BlockState> blockStates;
    private java.util.ArrayList<BlockState> blockStates_menus;
    private java.util.ArrayList<BlockState> blockStates_boxes;
    private NeuronBox selectedNode = null;

    public NorbironMap(NorbironSurfaceView surfaceView)
    {
        this.surfaceView = surfaceView;
        initMap();
        norbironResources = new NorbironResources(BLOCK_SIZE, this.surfaceView);
        //blockStates = new java.util.ArrayList<BlockState>();
        blockStates_menus = new java.util.ArrayList<BlockState>();
        blockStates_boxes = new java.util.ArrayList<BlockState>();

        if(blockStates_menus.isEmpty())
        {
            addMenu(0,1,0);
            addMenu(1,2,0);
        }
    }

    public void initMap()
    {
        // 0 - simple
        // 1 - red
        // 2 - board
        // 3 - clash

        this.map = new int[][]
                {
                        { 0, 0, 1, 1, 1, 1, 1, 1, 1, 0 },
                        { 1, 1, 1, 2, -1, -1, -1, 1, 1, 0 },
                        { 0, 0, 1, 1, 1, 1, 1, 1, 0, 0 },
                        { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },
                        { 0, 0, 0, 1, 1, 1, 1, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 3, -1, -1, -1, 0, 0, 0 },
                        { 0, 0, 0, -1, -1, -1, -1, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                };
    }

    public void draw(android.graphics.Canvas canvas, float scaleFactor, float startsx, float startsy)
    {
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawColor(android.graphics.Color.BLACK);

        for(int i = 0; i<10; i++)
        {
            for(int j = 0; j<10; j++)
            {
                if(map[j][i] == -1) continue;
                canvas.drawBitmap(norbironResources.getImage(this.map[j][i]), -startsx + i * BLOCK_SIZE, -startsy + j * BLOCK_SIZE, null);
            }
        }

        for(int i = 0; i<blockStates_menus.size(); i++)
        {
            blockStates_menus.get(i).getNeuronBox().draw(-startsx, -startsy, canvas);
        }
        for(int i = 0; i<blockStates_boxes.size(); i++)
        {
            blockStates_boxes.get(i).getNeuronBox().draw(-startsx, -startsy, canvas);
        }

        canvas.restore();

    }

    public void addMenu(int id, int x, int y)
    {
        BlockState blockState = new BlockState(0, x, y, (NeuronBox)norbironResources.getMenu(id).clone() );
        blockState.getNeuronBox().setXY(x,y);
        blockState.getNeuronBox().setType(id);
        blockState.setType(id);
        blockStates_menus.add(blockState);
    }

    public void addProc(int id, int x, int y)
    {
        BlockState blockState = new BlockState(1, x, y, (NeuronBox)norbironResources.getProc(id).clone() );
        blockState.getNeuronBox().setXY(x,y);
        blockState.setType(id);
        blockStates_boxes.add(blockState);
    }

    public void stepNeurons()
    {
        /*
        for(int i = 0; i<blockStates.size(); i++)
        {
            blockStates.get(i).getNeuronBox().step();
        }
        */
        for(int i = 0; i<blockStates_menus.size(); i++)
        {
            blockStates_menus.get(i).getNeuronBox().step();
        }
        for(int i = 0; i<blockStates_boxes.size(); i++)
        {
            blockStates_boxes.get(i).getNeuronBox().step();
        }

    }

    public NeuronBox getNearestNeuron(float x, float y)
    {
        NeuronBox tmp = null;
        float max = 10000, m;

        for (int i = 0; i<blockStates_boxes.size(); i++)
        {

            if ((m = distance(blockStates_boxes.get(i).getNeuronBox().getX() + blockStates_boxes.get(i).getNeuronBox().getWidth() / 2,
                    blockStates_boxes.get(i).getNeuronBox().getY() + blockStates_boxes.get(i).getNeuronBox().getHeight() / 2,(int)x,(int)y)) < max)
            {
                max = m;
                tmp = blockStates_boxes.get(i).getNeuronBox();
            }
        }
        for (int i = 0; i<blockStates_menus.size(); i++)
        {

            if ((m = distance(blockStates_menus.get(i).getNeuronBox().getX() + blockStates_menus.get(i).getNeuronBox().getWidth() / 2,
                    blockStates_menus.get(i).getNeuronBox().getY() + blockStates_menus.get(i).getNeuronBox().getHeight() / 2,(int)x,(int)y)) < max)
            {
                max = m;
                tmp = blockStates_menus.get(i).getNeuronBox();
            }
        }

        return tmp;

    }

    public float distance(int x1, int y1, int x2, int y2)
    {
        return (float)( (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) );
    }

    public void setSurfaceView(NorbironSurfaceView surfaceView)
    {
        this.surfaceView= surfaceView;
        this.norbironResources = new NorbironResources(BLOCK_SIZE, this.surfaceView);
    }

    public java.util.ArrayList<Integer> getProcResIDs()
    {
        return this.norbironResources.getProcResIDs();
    }

    public void clearMap()
    {
        blockStates_boxes.clear();
    }

    public void clearDatabase(String username)
    {
        String url = "https://brainboard-3fea4.firebaseio.com/Users/" + username;
        Firebase firebase = new Firebase(url);

        firebase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(! (snapshot.getKey().equals("LoggedIn")) )
                    {
                        snapshot.getRef().setValue(null);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void saveMapToServer(String username)
    {
        String url = "https://brainboard-3fea4.firebaseio.com/Users/" + username;
        Firebase firebase = new Firebase(url);
        int posx = 0, posy = 0, type = 0, i = 0;


        for(i = 0; i < blockStates_boxes.size(); i++)
        {
            posx = blockStates_boxes.get(i).getNeuronBox().getX() / BLOCK_SIZE;
            posy = blockStates_boxes.get(i).getNeuronBox().getY() / BLOCK_SIZE;
            type = blockStates_boxes.get(i).getType();

            Firebase child;
            child = firebase.child("node" + String.valueOf(i));

            Firebase childchild;
            childchild = child.child("posx");
            childchild.setValue(String.valueOf(posx));
            childchild = child.child("posy");
            childchild.setValue(String.valueOf(posy));
            childchild = child.child("type");
            childchild.setValue(String.valueOf(type));
        }
    }


    public void initMapFromServer(String username)
    {
        clearMap();
        String url = "https://brainboard-3fea4.firebaseio.com/Users/" + username;
        Firebase firebase = new Firebase(url);
        clearMap();

        firebase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int posx = 0, posy = 0, type = 0, i = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String postKey = postSnapshot.getKey();

                    if(postKey.equals("LoggedIn") || postKey.equals("Password"))
                    {
                        continue;
                    }

                    else
                    {
                        int index = 0;

                        for(DataSnapshot snapShot : postSnapshot.getChildren())
                        {
                            if(index == 0)
                            {
                                String snapValue = snapShot.getValue(String.class);
                                posx = Integer.valueOf(Integer.valueOf(snapValue));
                            }
                            if(index == 1)
                            {
                                String snapValue = snapShot.getValue(String.class);
                                posy = Integer.valueOf(Integer.valueOf(snapValue));
                            }
                            if(index == 2)
                            {
                                String snapValue = snapShot.getValue(String.class);
                                type = Integer.valueOf(Integer.valueOf(snapValue));

                                BlockState blockState = new BlockState(1, posx, posy, (NeuronBox)norbironResources.getProc(type).clone() );
                                blockState.setType(type);
                                blockState.getNeuronBox().setXY(posx,posy);
                                blockStates_boxes.add(blockState);
                            }

                            index++;
                        }
                    }
                }
            }



            @Override
            public void onCancelled(FirebaseError firebaseError)
            {

            }
        });

    }

    public void newBox()
    {
        this.norbironResources.newBox();
    }

    public boolean checkPosition(int x, int y)
    {
        if(x < 0 || x >= n || y < 0 || y >= m)
        {
            return false;
        }

        if(map[y][x] != 0)
        {
            return false;
        }

        else return true;
    }

}


