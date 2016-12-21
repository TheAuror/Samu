package batfai.samuentropy.brainboard7;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

class MenuAdapter extends android.widget.BaseAdapter
{

    private android.content.Context context;
    java.util.ArrayList<Integer> menuItems = new java.util.ArrayList<Integer>();

    public void setMenuItems(java.util.ArrayList<Integer> menuItems)
    {
        this.menuItems = menuItems;
    }

    public MenuAdapter(android.content.Context context)
    {
        cinit(context);
    }

    public MenuAdapter(android.content.Context context, android.util.AttributeSet attrs)
    {
        cinit(context);
    }

    public MenuAdapter(android.content.Context context,
                       android.util.AttributeSet attrs, int defStyle)
    {
        cinit(context);
    }

    private void cinit(android.content.Context context)
    {
        this.context = context;
    }

    public int getCount()
    {
        return menuItems.size();
    }

    public long getItemId(int position)
    {
        return menuItems.get(position);
    }

    public Object getItem(int position)
    {
        return menuItems.get(position);
    }

    public android.view.View getView(int position, android.view.View oldView, android.view.ViewGroup parent)
    {
        android.widget.ImageView imageView;

        if (oldView != null)
        {
            imageView = (android.widget.ImageView) oldView;
        }
        else
        {
            imageView = new android.widget.ImageView(context);
        }

        imageView.setAdjustViewBounds(true);
        imageView.setImageResource(menuItems.get(position));
        return imageView;
    }
}



public class MainMenuActivity extends android.app.Activity
{
    private java.util.ArrayList<Integer> menuItems;
    private android.widget.RelativeLayout relativeLayout;
    static final int LOGIN_REQUEST = 0;
	private String currentUser = "";
    private Context context;

    @Override
    public void onCreate(android.os.Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        android.content.Intent intent = getIntent();
        this.context = this;
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        if (requestCode == LOGIN_REQUEST)
		{
            if (resultCode == RESULT_OK)
			{
                if (data.hasExtra("username"))
				{
					currentUser = data.getExtras().getString("username");
                }
            }
        }
    }

    public void init()
    {
        menuItems = new java.util.ArrayList<Integer>();
        menuItems.add(R.drawable.main_menu_account);
        menuItems.add(R.drawable.main_menu_brainboard);
        menuItems.add(R.drawable.main_menu_anim);

        android.widget.GridView gridView = (android.widget.GridView) findViewById(R.id.menuitems);
        MenuAdapter menuAdapter = new MenuAdapter(this);
        menuAdapter.setMenuItems(menuItems);
        gridView.setAdapter(menuAdapter);

        gridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
        {
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id)
            {

                if (position == 1)
                {
                    if(!(currentUser.equals("")))
                    {
                        android.content.Intent intent = new android.content.Intent(view.getContext(), NeuronGameActivity.class);
                        intent.putExtra("username", currentUser);
                        startActivity(intent);
                    }
                    else
                    {
                        android.widget.Toast.makeText(context, "U need to log in first", android.widget.Toast.LENGTH_SHORT).show();
                    }
                }
                if (position == 0)
                {
                    android.content.Intent intent = new android.content.Intent(view.getContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST);
                }

                if (position == 2)
                {
                    android.content.Intent intent = new android.content.Intent(view.getContext(), NeuronAnimActivity.class);
                    startActivity(intent);
                }
            }
        });

        relativeLayout = (android.widget.RelativeLayout)findViewById(R.id.bg);
        android.view.ViewTreeObserver vto = relativeLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener()
        {

            @Override
            public void onGlobalLayout()
            {
                relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //startBackgroundAnimation();
            }
        });

    }


    /*
    public void startBackgroundAnimation()
    {
        android.view.animation.Animation rotateLeft		= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_left);
        android.view.animation.Animation rotateRight	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_right);
        android.view.animation.Animation rotateLeft2	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_left_v2);
        android.view.animation.Animation rotateRight2	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_rotate_right_v2);
        android.view.animation.Animation scaleSmaller	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_scale_smaller);
        android.view.animation.Animation scaleBigger	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_scale_bigger);
        android.view.animation.Animation transRightUp	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_translate_rightup);
        android.view.animation.Animation transRightUp2	= android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bg_anim_translate_rightup_v2);

        android.view.animation.AnimationSet iv1set = new android.view.animation.AnimationSet(false);
        iv1set.addAnimation(rotateLeft2);
        iv1set.addAnimation(transRightUp);
        findViewById(R.id.img_a).startAnimation(iv1set);

        android.view.animation.AnimationSet iv2set = new android.view.animation.AnimationSet(false);
        iv2set.addAnimation(rotateRight2);
        iv2set.addAnimation(transRightUp2);
        findViewById(R.id.img_b).startAnimation(iv2set);

        android.view.animation.AnimationSet iv3set = new android.view.animation.AnimationSet(false);
        iv3set.addAnimation(rotateLeft);
        iv3set.addAnimation(transRightUp);
        findViewById(R.id.img_c).startAnimation(iv3set);
    }
    */
}
