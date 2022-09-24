package model.vo;

import model.loader.ElementLoader;
import model.manager.ElementManager;
import model.manager.GameMap;

import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 * 地图可破坏物体类
 * @ClassName: MapFragility
 * @Description:
 * @CreateDate: 2019年4月13日 下午6:31:49
 */
public class MapFragility extends MapSquare{
	private boolean destoried;
	
	public MapFragility(int i, int j, ImageIcon img, int sx, int sy, int dx, int dy, int scaleX, int scaleY) {
		super(i, j, img, sx, sy, dx, dy, scaleX, scaleY);
		destoried = false;
	}
	
	public static MapFragility createMapFragility(List<String> data,int i, int j) {
		ImageIcon img = ElementLoader.getElementLoader().getImageMap().get(data.get(0));
		int sx = Integer.parseInt(data.get(1));
		int sy = Integer.parseInt(data.get(2));
		int dx = Integer.parseInt(data.get(3));
		int dy = Integer.parseInt(data.get(4));
		int scaleX = Integer.parseInt(data.get(6));
		int scaleY = Integer.parseInt(data.get(7));
		MapFragility mapMapFragility = new MapFragility(i, j, img, sx, sy, dx, dy, scaleX, scaleY);
		return mapMapFragility;
	}
	
	@Override
	public void update() {
		destroy();
	}
	
	@Override
	public void destroy() {
//		判断是否被破坏
		if(!isDestoried()) return;
//		设置地板
		GameMap gameMap = ElementManager.getManager().getGameMap();
		List<Integer> list = GameMap.getIJ(getX(), getY());
		gameMap.setBlockSquareType(list.get(0), list.get(1), GameMap.SquareType.FLOOR);
//		可能生成道具
		Map<String, List<SuperElement>> elmenteMap = ElementManager.getManager().getMap();
		Random rd = new Random();
		int creating = rd.nextInt(4)!=0?1:0;
		if(creating==1){
			List<Integer> locList = GameMap.getIJ(getX(), getY());
			elmenteMap.get("magicBox").add(MagicBox.createMagicBox(locList.get(0),locList.get(1)));
		}
		setDestoried(false);
		setAlive(false);
	}

	public boolean isDestoried() {
		return destoried;
	}

	public void setDestoried(boolean destoried) {
		this.destoried = destoried;
	}
}
