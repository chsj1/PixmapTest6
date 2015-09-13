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
	int radiu = 10;//��İ뾶
	float lastX,lastY;//��һ���������
	float distance = radiu*radiu/2;//����ֵ.��������֮��ľ��볬�����ֵ��ʱ��,��ʼ��...
//	Rectangle r;//�ض�����.�������������������ͿĨ,���򲻽���ͿĨ...
//	Polygon polygon;
	
	TextureAtlas atlas;
	TextureRegion bgRegion;//����ͼƬ
	Image bgImage;
	
	@Override
	public void create() {
//		r = new Rectangle(200, 200, 100, 100);
		/**
		 * ���Ʋ�����ͼ��
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
		pixmap.setColor(Color.BLACK);// ����pixmap����ɫ
		pixmap.drawLine(50, adjustY(50), 100, adjustY(50));// ����
//		pixmap.setColor(Color.BLUE);// ����pixmap����ɫ
		pixmap.drawLine(100, adjustY(50), 150, adjustY(100));// ����
//		pixmap.setColor(Color.BLUE);// ����pixmap����ɫ
		pixmap.drawLine(150, adjustY(100), 200, adjustY(300));// ����
//		pixmap.setColor(Color.BLUE);// ����pixmap����ɫ
		pixmap.drawLine(200, adjustY(300), 50, adjustY(50));// ����
//		pixmap.setColor(Color.BLUE);// ����pixmap����ɫ
		pixmap.drawLine(0, 0, 100, 100);// ����
//		pixmap.setColor(Color.GREEN);
		pixmap.drawLine(100, 100, 200, 0);
//		pixmap.setColor(Color.ORANGE);
		pixmap.drawLine(200, 0, 300, 100);// ���(x1,y1)���յ�(x2,y2)
//		pixmap.setColor(Color.WHITE);
		pixmap.fillCircle(150, 150, 32);// ��ʵ��Բ.(x,y)�Ͱ뾶
//		pixmap.setColor(Color.RED);
		pixmap.drawCircle(250, 100, 50);// ������Բ.(x,y)�Ͱ뾶
//		pixmap.setColor(Color.YELLOW);
		pixmap.drawRectangle(200, 200, 100, 100);// �����ľ���.���(x,y),(width,height)
//		pixmap.setColor(Color.MAGENTA);
		pixmap.fillRectangle(0, 160, 100, 100);// ��ʵ�ľ���.���(x,y),(width,height)
		/**
		 * pixmap.getHeight(): ��ȡpixmap�ĸ߶� pixmap.getWidth()�� ��ȡpixmap�Ŀ��
		 * pixmap.getFormat(): ��ȡpixmap�ĸ�ʽ
		 */
		System.out.println("pixmap.getHeight(): " + pixmap.getHeight()
				+ ",pixmap.getWidth( " + pixmap.getWidth()
				+ ", pixmap.getFormat()" + pixmap.getFormat());
		stage.addListener(new InputListener() {
			private int pointer = 0;//����������㴥��
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
				 * �����ж�������Ƿ��ڲ�����ͼ����..
				 */
//				if(!polygon.contains(x, y)){
//					return true;
//				}
//				System.out.println("--------->��������Ļ....");
				pixmap.setColor(Color.BLACK);
				pixmap.fillCircle((int)x, adjustY(y), radiu);
				texture.draw(pixmap, 0, 0);// ��texture����һ��pixmapͼ��
				/**
				 * ��¼��һ���µĵ�...
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
				 * ����һ���������ĵ�ͱ��μ������ĵ�֮��ľ�������
				 */
				addPoint(x, y, false, false);
				lastX = x;
				lastY = adjustY(y);
				/**
				 * �Ѽ������ĵ��¼����
				 */
				pixmap.setColor(Color.BLACK);
				pixmap.fillCircle((int)x, adjustY(y), radiu);
				texture.draw(pixmap, 0, 0);// ��texture����һ��pixmapͼ��
			}
		});
		texture.draw(pixmap, 0, 0);//��texture����һ��pixmapͼ��
		region = new TextureRegion(texture, 800, 480);
		image = new Image(region);
		stage.addActor(bgImage);
		stage.addActor(image);
		Gdx.input.setInputProcessor(stage);
	}
	/**
	 * 
	 * @param x �ոռ������ĵ�ĺ�����
	 * @param y �ոռ������ĵ��������
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
	 * ��(x1,y1) �� (x2,y2)֮��ĵĵ㻭����
	 * �������������������Ϊ������touchDrag()�Ļ�
	 * ����ʱֻ����ʾ���������Զ�ĵ�.
	 * ���·�����ԭ�����ȼ��������յ�֮����м��
	 * Ȼ����õݹ�ķ�ʽ���ϵİ������м���Լ��м����յ�֮��ĵ㻭����
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
	 * pixmap�е���������stage�е��������໥ת��
	 * @param y
	 * @return
	 */
	public int adjustY(float y){
		int y1 = (int)y;
		return 480-y1; 
	}
	/**
	 * �жϵ�(x,y)�Ƿ��ھ�������Rectangle��
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
