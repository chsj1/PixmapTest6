package com.example.groupactiontest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MyGame implements ApplicationListener {
	Stage stage;
	Image image;
	SpriteBatch batch;
	Texture texture;
	TextureRegion region;
	Pixmap pixmap;
	int radiu = 10;//点的半径
	float lastX,lastY;//上一个点的坐标
	float distance = radiu*radiu/2;//距离值.当两个点之间的距离超过这个值的时候,开始画...
//	Rectangle r;//特定区域.如果点在这个区域则进行涂抹,否则不进行涂抹...
//	Polygon polygon;
	
	TextureAtlas atlas;
	TextureRegion bgRegion;//背景图片
	Image bgImage;
	
	@Override
	public void create() {
//		r = new Rectangle(200, 200, 100, 100);
		/**
		 * 绘制不规则图形
		 */
//		polygon = new Polygon(new float[]{
//			50,50,
//			100,50,
//			150,100,
//			200,300
//		});
		atlas = new TextureAtlas(Gdx.files.internal("data/loading.atlas"));
		bgRegion = atlas.findRegion("bg");
		bgImage = new Image(bgRegion);
		bgImage.setSize(800, 480);
		stage = new Stage(800, 480, false);
		batch = new SpriteBatch();
		texture = new Texture(1024, 1024, Format.RGBA8888);
		pixmap = new Pixmap(800, 480, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);// 设置pixmap的颜色
		pixmap.drawLine(50, adjustY(50), 100, adjustY(50));// 画线
//		pixmap.setColor(Color.BLUE);// 设置pixmap的颜色
		pixmap.drawLine(100, adjustY(50), 150, adjustY(100));// 画线
//		pixmap.setColor(Color.BLUE);// 设置pixmap的颜色
		pixmap.drawLine(150, adjustY(100), 200, adjustY(300));// 画线
//		pixmap.setColor(Color.BLUE);// 设置pixmap的颜色
		pixmap.drawLine(200, adjustY(300), 50, adjustY(50));// 画线
//		pixmap.setColor(Color.BLUE);// 设置pixmap的颜色
		pixmap.drawLine(0, 0, 100, 100);// 画线
//		pixmap.setColor(Color.GREEN);
		pixmap.drawLine(100, 100, 200, 0);
//		pixmap.setColor(Color.ORANGE);
		pixmap.drawLine(200, 0, 300, 100);// 起点(x1,y1)、终点(x2,y2)
//		pixmap.setColor(Color.WHITE);
		pixmap.fillCircle(150, 150, 32);// 画实心圆.(x,y)和半径
//		pixmap.setColor(Color.RED);
		pixmap.drawCircle(250, 100, 50);// 画空心圆.(x,y)和半径
//		pixmap.setColor(Color.YELLOW);
		pixmap.drawRectangle(200, 200, 100, 100);// 画空心矩形.起点(x,y),(width,height)
//		pixmap.setColor(Color.MAGENTA);
		pixmap.fillRectangle(0, 160, 100, 100);// 画实心矩形.起点(x,y),(width,height)
		/**
		 * pixmap.getHeight(): 获取pixmap的高度 pixmap.getWidth()： 获取pixmap的宽度
		 * pixmap.getFormat(): 获取pixmap的格式
		 */
		System.out.println("pixmap.getHeight(): " + pixmap.getHeight()
				+ ",pixmap.getWidth( " + pixmap.getWidth()
				+ ", pixmap.getFormat()" + pixmap.getFormat());
		stage.addListener(new InputListener() {
			private int pointer = 0;//用来禁掉多点触碰
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if(pointer != this.pointer){
					return true;
				}
//				if(!pointInRectabgle(r, x, y)){
//					return true;
//				}
				/**
				 * 用来判断这个点是否在不规则图形内..
				 */
//				if(!polygon.contains(x, y)){
//					return true;
//				}
//				System.out.println("--------->你点击了屏幕....");
				pixmap.setColor(Color.BLACK);
				pixmap.fillCircle((int)x, adjustY(y), radiu);
				texture.draw(pixmap, 0, 0);// 在texture中套一个pixmap图层
				/**
				 * 记录第一按下的点...
				 */
				lastX = x;
				lastY = adjustY(y);
				return true;
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
//				if(!pointInRectabgle(r, x, y)){
//					return ;
//				}
//				if(pointer != this.pointer){
//					return ;
//				}
//				if(!polygon.contains(x, y)){
//					return ;
//				}
				/**
				 * 把上一个监听到的点和本次监听到的点之间的距离填满
				 */
				addPoint(x, y, false, false);
				lastX = x;
				lastY = adjustY(y);
				/**
				 * 把监听到的点记录下来
				 */
				pixmap.setColor(Color.BLACK);
				pixmap.fillCircle((int)x, adjustY(y), radiu);
				texture.draw(pixmap, 0, 0);// 在texture中套一个pixmap图层
			}
		});
		texture.draw(pixmap, 0, 0);//在texture中套一个pixmap图层
		region = new TextureRegion(texture, 800, 480);
		image = new Image(region);
		stage.addActor(bgImage);
		stage.addActor(image);
		Gdx.input.setInputProcessor(stage);
	}
	/**
	 * 
	 * @param x 刚刚监听到的点的横坐标
	 * @param y 刚刚监听到的点的纵坐标
	 * @param isNewStartPoint
	 * @param isEnd
	 */
	public void addPoint(float x, float y, boolean isNewStartPoint,boolean isEnd) {
		Pixmap.setBlending(Blending.None);
		y = adjustY(y);
		caculatePoint(lastX, lastY, x, y);
		Pixmap.setBlending(Blending.SourceOver);
	}
	/**
	 * 把(x1,y1) 和 (x2,y2)之间的的点画出来
	 * 这所以有这个方法是因为单纯用touchDrag()的话
	 * 滑动时只能显示两个间隔较远的点.
	 * 以下方法的原理是先计算起点和终点之间的中间点
	 * 然后采用递归的方式不断的把起点和中间点以及中间点和终点之间的点画出来
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	protected void caculatePoint(float x1, float y1, float x2, float y2) {
		float dx = x1 - x2;
		float dy = y1 - y2;
		float delta = dx * dx + dy * dy;
		if (delta >= (distance)) {
			float midx = (x1 + x2) / 2;
			float midy = (y1 + y2) / 2;
			pixmap.fillCircle((int) midx, (int) midy, radiu);
			caculatePoint(x1, y1, midx, midy);
			caculatePoint(x2, y2, midx, midy);
		}
	}
	/**
	 * pixmap中的纵坐标与stage中的纵坐标相互转换
	 * @param y
	 * @return
	 */
	public int adjustY(float y){
		int y1 = (int)y;
		return 480-y1; 
	}
	/**
	 * 判断点(x,y)是否在矩形区域Rectangle中
	 * @param r
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean pointInRectabgle(Rectangle r,float x, float y){
		if(r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y){
			return true;
		}
		return false;
	}
	@Override
	public void dispose() {
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}
	@Override
	public void resize(int arg0, int arg1) {
	}
	@Override
	public void resume() {
	}
}
