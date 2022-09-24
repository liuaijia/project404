package model.manager;

import model.loader.ElementLoader;
import model.vo.Player;
import model.vo.SuperElement;

import java.util.List;
import java.util.Map;


/**
 * 元素工厂类
 * &#064;ClassName:  ElementFactory
 * &#064;Description:  用于构建对象进入元素管理器
 * &#064;CreateDate:  2019年4月11日 下午5:07:57
 */
public class ElementFactory {
	private static ElementFactory elementFactory;

	//构造函数
	private ElementFactory() {}
	
	public static ElementFactory getElementFactory() {
		if(elementFactory == null) {
			elementFactory = new ElementFactory();
		}
		return elementFactory;
	}
	
	public SuperElement produceElement(String name) {
		//TODO:写工厂
		Map<String, List<String>> gameInfoMap = 
				ElementLoader.getElementLoader().getGameInfoMap();//获取资源加载器的游戏信息字典

		switch(name) {
		case "playerOne":
			return Player.createPlayer(gameInfoMap.get(name),0);
		case "playerTwo":
			return Player.createPlayer(gameInfoMap.get(name),1);
//		case "bubble":
//			return Bubble.createBubble(gameInfoMap.get(name));
		}
		return null;
	}
	
	
}
