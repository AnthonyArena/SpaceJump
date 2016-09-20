package jumpyBox;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Box {
	
	private static Box instance = null;
	private Integer x, y;
	private Integer width, height;
	private Integer boxLeft, boxRight, boxUp, boxDown;
	private Integer jumpHeight;
	private Double fallSpeed, acceleration, defaultFallSpeed;
	private boolean alive;
	private boolean jump, fastFall;
	private Rectangle2D shape;
	private Image monster;
	
	public Box() {
		
		x = Main.width / 3;
		y = Main.height / 2;
		
		width = 25;
		height = 25;
		
		boxLeft = x;
		boxRight = x + width;
		boxDown = y + height;
		boxUp = y;
		
		jumpHeight = 2;
		defaultFallSpeed = 0.9;
		fallSpeed = defaultFallSpeed.doubleValue();
		acceleration = 0.005;
				
		alive = true;
		jump = false;
		fastFall = false;
		
		shape = new Rectangle();
		shape.setRect(x, y, width, height);
	}
	
	public void checkGroundCollision() {
		Integer boxDown = this.getY() + this.getHeight();
		Integer ground  = Main.groundPos;
		
		if (boxDown >= ground) {
			setAlive(false);
		}
	}
	
	public void checkPillarCollision(Pillar pillar) {
		if (pillar != null) {
			if (this.boxRight >= pillar.getLeftPillar() && this.boxRight <= pillar.getRightPillar()
					&& this.boxUp <= pillar.getTopGap()) {
				this.setAlive(false);
			}
			if (this.boxRight >= pillar.getLeftPillar() && this.boxRight <= pillar.getRightPillar()
					&& this.boxDown >= pillar.getBottomGap()) {
				this.setAlive(false);
			}
			if (this.boxLeft >= pillar.getLeftPillar() && this.boxLeft <= pillar.getRightPillar()
					&& this.boxUp <= pillar.getTopGap()) {
				this.setAlive(false);
			}
			if (this.boxLeft >= pillar.getLeftPillar() && this.boxLeft <= pillar.getRightPillar()
					&& this.boxDown >= pillar.getBottomGap()) {
				this.setAlive(false);
			}
		}
	}
	
	public void move() {		
		if (getJump()) {
			if (getY() >= 5) {
				y -= jumpHeight;
			} else if (getY() < 5 && getY() > 0) {
				y = 0;
			}
			fallSpeed = defaultFallSpeed.doubleValue();
		} else {
			fallSpeed += fallSpeed * acceleration;
			y += fallSpeed.intValue();
		}
		boxDown = y + height;
		boxUp = y;
	}
	
	public Image getMonster() {
		return monster;
	}

	public void setMonster(Image monster) {
		this.monster = monster;
	}

	public Rectangle2D getShape() {
		return shape;
	}

	public void setShape(Rectangle2D shape) {
		this.shape = shape;
	}

	public Integer getBoxCenter() {
		return (boxLeft + boxRight) / 2;
	}
	
	public Integer getBoxLeft() {
		return boxLeft;
	}

	public void setBoxLeft(Integer boxLeft) {
		this.boxLeft = boxLeft;
	}

	public Integer getBoxRight() {
		return boxRight;
	}

	public void setBoxRight(Integer boxRight) {
		this.boxRight = boxRight;
	}

	public Integer getBoxUp() {
		return boxUp;
	}

	public void setBoxUp(Integer boxUp) {
		this.boxUp = boxUp;
	}

	public Integer getBoxDown() {
		return boxDown;
	}

	public void setBoxDown(Integer boxDown) {
		this.boxDown = boxDown;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean getJump() {
		return jump;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public boolean getFastFall() {
		return fastFall;
	}

	public void setFastFall(boolean fastFall) {
		this.fastFall = fastFall;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public static Box getInstance() {
		if (instance == null) {
			instance = new Box();
		}
		
		return instance;
	}
}
