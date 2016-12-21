/*
 * NorbironSurfaceView.java
 *
 * Norbiron Game
 * This is a case study for creating sprites for SamuEntropy/Brainboard.
 *
 * Copyright (C) 2016, Dr. Bátfai Norbert
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Ez a program szabad szoftver; terjeszthető illetve módosítható a
 * Free Software Foundation által kiadott GNU General Public License
 * dokumentumában leírtak; akár a licenc 3-as, akár (tetszőleges) későbbi
 * változata szerint.
 *
 * Ez a program abban a reményben kerül közreadásra, hogy hasznos lesz,
 * de minden egyéb GARANCIA NÉLKÜL, az ELADHATÓSÁGRA vagy VALAMELY CÉLRA
 * VALÓ ALKALMAZHATÓSÁGRA való származtatott garanciát is beleértve.
 * További részleteket a GNU General Public License tartalmaz.
 *
 * A felhasználónak a programmal együtt meg kell kapnia a GNU General
 * Public License egy példányát; ha mégsem kapta meg, akkor
 * tekintse meg a <http://www.gnu.org/licenses/> oldalon.
 *
 * Version history:
 *
 * 0.0.1, 2013.szept.29.
 */
package batfai.samuentropy.brainboard3;

/**
 *
 * @author nbatfai
 */

public class Box
{
	private int x,y,size;
	private int startx, starty;	
	private android.graphics.Paint paint;
	private android.graphics.Color color;	
    private android.graphics.Bitmap neuronSprite;
	private Sprite sprite;
    private android.content.Context context;

	public Box(int x_start, int y_start, int size ,android.content.Context current)
	{
		this.x = x_start;
		this.y = y_start;
		this.startx = x_start;
		this.starty = y_start;
		this.size = size;
		paint = new android.graphics.Paint();
		color = new android.graphics.Color();
		this.context = current;
		initSprite();
	}

	public void initSprite()
	{
		int resId = R.drawable.neuronsprite;
        neuronSprite = android.graphics.BitmapFactory.decodeResource(context.getResources(), resId);
        neuronSprite = android.graphics.Bitmap.createScaledBitmap(neuronSprite, 128 * 7, 123, false);
        sprite = new Sprite(neuronSprite, 7, 128, 123, x,y, x+size, y+size, size);

	}

	public void Draw(android.graphics.Canvas canvas)
	{
		paint.setStyle(android.graphics.Paint.Style.STROKE);
		paint.setColor(color.BLACK);
        paint.setStrokeWidth(7);
        canvas.drawRect(x, y, x + size, y + size, paint);
		this.sprite.draw(canvas);
	}
	

	public Sprite getSprite() {return this.sprite;}

	public int getX() {return this.x;}
	public int getY() {return this.y;}

	public int getstartX() {return this.startx;}
	public int getstartY() {return this.starty;}
	
    public void setXY(float x, float y)
	{
        this.x = (int)x;
        this.y = (int)y;
		//this.sprite.setXY(x,y);
    }

}

