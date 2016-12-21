package batfai.samuentropy.brainboard7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

class NodeAdapter extends android.widget.BaseAdapter {

    private android.content.Context context;
    java.util.ArrayList<Integer> nodeIds = new java.util.ArrayList<Integer>();

    public void setNodeIds(java.util.ArrayList<Integer> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public NodeAdapter(android.content.Context context) {

        cinit(context);
    }

    public NodeAdapter(android.content.Context context, android.util.AttributeSet attrs) {

        cinit(context);
    }

    public NodeAdapter(android.content.Context context,
                       android.util.AttributeSet attrs, int defStyle) {

        cinit(context);
    }

    private void cinit(android.content.Context context) {

        this.context = context;
    }

    public int getCount() {
        return nodeIds.size();
    }

    public long getItemId(int position) {
        return nodeIds.get(position);
    }

    public Object getItem(int position) {
        return nodeIds.get(position);
    }

    public android.view.View getView(int position, android.view.View oldView, android.view.ViewGroup parent) {

        android.widget.ImageView imageView;

        if (oldView != null) {
            imageView = (android.widget.ImageView) oldView;
        } else {
            imageView = new android.widget.ImageView(context);
        }

        imageView.setImageResource(nodeIds.get(position));
        return imageView;
    }
}


public class NodeActivity extends android.app.Activity
{

    private java.util.ArrayList<Integer> nodeIds;
    private String username;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nodes);

        android.content.Intent intent = getIntent();
        nodeIds = intent.getIntegerArrayListExtra("nodeIds");
        username = intent.getStringExtra("currentUser");

        android.widget.GridView gridView = (android.widget.GridView) findViewById(R.id.nodelist);

        NodeAdapter nodeAdapter = new NodeAdapter(this);
        nodeAdapter.setNodeIds(nodeIds);
        gridView.setAdapter(nodeAdapter);

        gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(android.widget.AdapterView<?> parent,
                                    android.view.View view,
                                    int position, long id) {

                android.content.Intent intent = new android.content.Intent();

                intent.setClass(NodeActivity.this, NeuronGameActivity.class);
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("selectedNode", position);
                intent.putExtra("username", username);
                startActivity(intent);

                NodeActivity.this.finish();

            }
        });

    }

}
