package jumpyBox;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Pillar {
	
	private Integer x, dy, dyPos;
	private Integer width, length;
	private Integer bottomGap, topGap, leftPillar, rightPillar;
	private Integer speed;
	private Rectangle2D bottomShape, topShape;
	private boolean visible;
	
	public Pillar() {
		x = Main.width;
		dy = Main.height / 6;
		
		width = 60;
		length = Main.groundPos;
		
		Random rand = new Random();
		dyPos = 25 + rand.nextInt(length - 50 - dy);
		
		bottomGap = dyPos + dy;
		topGap = dyPos;
		leftPillar = x;
		rightPillar = x + width;
		
		speed = 1;
		
		visible = true;
		
		bottomShape = new Rectangle();
		topShape = new Rectangle();
		bottomShape.setRect(x, bottomGap, width, Main.groundPos - bottomGap);
		topShape.setRect(x, 0, width, topGap);
	}

	public void move() {
		x -= speed;
		bottomGap = dyPos + dy;
		topGap = dyPos;
		leftPillar = x;
		rightPillar = x + width;
		bottomShape.setRect(x, bottomGap, width, Main.groundPos - bottomGap);
		topShape.setRect(x, 0, width, topGap);
	}

	public boolean isVisible() {
		if (rightPillar <= 0) {
			setVisible(false);
		}
		return visible;
	}
	
	public Rectangle2D getBottomShape() {
		return bottomShape;
	}

	public void setBottomShape(Rectangle2D bottomShape) {
		this.bottomShape = bottomShape;
	}

	public Rectangle2D getTopShape() {
		return topShape;
	}

	public void setTopShape(Rectangle2D topShape) {
		this.topShape = topShape;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Integer getLeftPillar() {
		return leftPillar;
	}

	public void setLeftPillar(Integer leftPillar) {
		this.leftPillar = leftPillar;
	}

	public Integer getRightPillar() {
		return rightPillar;
	}

	public void setRightPillar(Integer rightPillar) {
		this.rightPillar = rightPillar;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getBottomGap() {
		return bottomGap;
	}

	public void setBottomGap(Integer bottomGap) {
		this.bottomGap = bottomGap;
	}

	public Integer getTopGap() {
		return topGap;
	}

	public void setTopGap(Integer topGap) {
		this.topGap = topGap;
	}
	
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getDy() {
		return dy;
	}

	public void setDy(Integer dy) {
		this.dy = dy;
	}

	public Integer getDyPos() {
		return dyPos;
	}

	public void setDyPos(Integer dyPos) {
		this.dyPos = dyPos;
	}
}
