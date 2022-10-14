package model.vo;

import java.awt.Graphics;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import model.manager.ElementManager;
import model.manager.MoveTypeEnum;

/**
 * 角色类
 * @ClassName: Character
 * @Description:为玩家和电脑的父类，保存玩家与电脑共有的属性和方法
 * @CreateDate: 2019年4月18日 下午5:27:20
 */
public class Character extends SuperElement{
	public final static int INIT_SPEED = 4; //初始移动速度

	protected boolean dead;//记录是否存活
	protected MoveTypeEnum moveType;
	protected int speed;//移动速度
	protected int speedItemCount;//生效中的加速卡数量
	protected int changeDirectionCount;//生效中的方向改变卡数量
	protected int stopitemCount;//生效中的其他玩家停止卡数量
	protected int bubblePower;//炮弹威力
	protected int bubbleNum;//记录玩家已经放了多少个炸弹
	protected int bubbleLargest;//玩家最多可以放多少个炸弹，初始值为3
	public int score;
	protected int heathPoint;//玩家生命值
	protected boolean isUnstoppable;//玩家是否获得无敌
	protected int unstoppableCount;//无敌卡数量
	protected boolean isShowing;//是否要展示元素


	public Character(int x, int y, int w, int h) {
		super(x, y, w, h);
		moveType = MoveTypeEnum.STOP;
		speedItemCount = 0;
		changeDirectionCount=0;
		stopitemCount=0;
		bubblePower = 1;
		bubbleNum = 0;
		bubbleLargest = 1;
		heathPoint = 1;
		isUnstoppable = false;
		unstoppableCount = 0;
		isShowing = true;
		speed = INIT_SPEED;
		score = 0;
		dead = false;
	}

	public void setHealthPoint(int change) {
		if(change<0)
		{
			if(isUnstoppable)return;
			setUnstoppable(3);//生命值减少时，无敌一段时间
		}
		heathPoint += change;
//		生命值为0，死亡
		if(heathPoint<=0)
		{
			setDead(true);
			setAlive(false);
			setX(-100);
			setY(-100);
		}
	}



	//	改变一段时间的移动速度,传入速度需要提升的倍数和持续的时间（秒）
	public void changeSpeed(double times,int lastTime) {
		speed = (int)(speed*times);
		Timer timer = new Timer(true);
		speedItemCount++;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				speedItemCount--;
				if(speedItemCount==0) {
					speed = INIT_SPEED;					
				}
			}
		};
		timer.schedule(task,lastTime*1000);
	}

	//	改变一段时间的方向，方向相反
	public void changeDirection(int lastTime) {
		speed = -speed;
		Timer timer = new Timer(true);
		changeDirectionCount++;
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				changeDirectionCount--;
				if(changeDirectionCount==0) {
					speed = INIT_SPEED;		

				}
			}
		};
		timer.schedule(task,lastTime*1000);
	}

	//	使其他玩家静止一段时间
	public void setOtherStop(int lastTime) {
//		除了自己以外的玩家
		List<SuperElement> playerList = ElementManager.getManager().getElementList("player");
		for (int i = 0; i < playerList.size(); i++) {
			Player player = (Player) playerList.get(i);
			if(player!=this)
			{
				player.setSpeed(0);
				setSpeedToInital(lastTime,player);
			}
		}
		List<SuperElement> NPCList = ElementManager.getManager().getElementList("npc");
		for (int i = 0; i < NPCList.size(); i++) {
			Npc npc = (Npc) NPCList.get(i);
			if(npc!=this)
			{
				npc.setSpeed(0);
				setSpeedToInital(lastTime,npc);
			}
			
		}
	}

	//	一段时间后恢复初始速度
//	lastTime 持续时间，character 用于设置得到该效果次数
	public void setSpeedToInital(int lastTime,final Character character)
	{
		Timer timer = new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				character.stopitemCount = character.stopitemCount-1;
				if(speedItemCount==0) {
					character.speed = INIT_SPEED;					
				}
			}
		};
		timer.schedule(task,lastTime*1000);
	}

	//	无敌一段时间，不会被炸死
//	lastTime 持续时间
	public void setUnstoppable(int lastTime)
	{
		isUnstoppable = true;
		unstoppableCount++;
		unstoppableChangeImg(lastTime);
		Timer timer = new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				unstoppableCount--;
				if(unstoppableCount==0) isUnstoppable = false;	
			}
		};
		timer.schedule(task,lastTime*1000);
	}
	//	图片闪烁
	public void unstoppableChangeImg(final int lastTime) {
		Timer timer = new Timer();
		final int times = lastTime*1000/100;//次数
		TimerTask task1 = new TimerTask() {// 图片消失
			int count = 0;
			@Override
			public void run() {
				isShowing = false;
				count++;
				if(count == times/5) {
					isShowing = true;//重置为可以显示
					this.cancel();
				}
			}
		};
		TimerTask task2 =  new TimerTask() {//图片出现
			int count = 0;
			@Override
			public void run() {
				isShowing = true;
				count++;
				if(count == times) this.cancel();
			}
		};
		timer.scheduleAtFixedRate(task1, 0, 500);//0延迟，每500ms调用一次
		timer.scheduleAtFixedRate(task2, 0, 100);//0延迟，每100ms调用一次
	}


	public void bubbleAddPower() {
		bubblePower++;
	}
	@Override
	public void showElement(Graphics g) {}

	@Override
	public void move() {}

	@Override
	public void destroy() {}
	

	
	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public MoveTypeEnum getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveTypeEnum moveType) {
		this.moveType = moveType;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeedItemCount() {
		return speedItemCount;
	}

	public void setSpeedItemCount(int speedItemCount) {
		this.speedItemCount = speedItemCount;
	}

	public int getBubblePower() {
		return bubblePower;
	}

	public void setBubblePower(int bubblePower) {
		this.bubblePower = bubblePower;
	}

	public int getBubbleNum() {
		return bubbleNum;
	}

	public void setBubbleNum(int bubbleNum) {
		this.bubbleNum = bubbleNum;
	}

	public int getBubbleLargest() {
		return bubbleLargest;
	}

	public void setBubbleLargest(int bubbleLargest) {
		this.bubbleLargest = bubbleLargest;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

	public int getChangeDirectionCount() {
		return changeDirectionCount;
	}

	public void setChangeDirectionCount(int changeDirectionCount) {
		this.changeDirectionCount = changeDirectionCount;
	}

	public int getStopitemCount() {
		return stopitemCount;
	}

	public void setStopitemCount(int stopitemCount) {
		this.stopitemCount = stopitemCount;
	}

	public int getHeathPoint() {
		return heathPoint;
	}

	public boolean isisUnstoppable() {
		return isUnstoppable;
	}

	public void setisUnstoppable(boolean unstoppable) {
		this.isUnstoppable = unstoppable;
	}
}
