package model.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ImageIcon;

/**
 * 资源加载器
 * 使用单例设计模式
 *
 */
public class ElementLoader {
	private static ElementLoader elementLoader;
	private Properties properties;
	private Map<String, List<String>> gameInfoMap;//游戏信息字典
	private Map<String, ImageIcon> imageMap;//图片字典
	private Map<String, List<String>> squareTypeMap;//方块类型字典

	//构造函数
	private ElementLoader() {
		properties = new Properties();
		gameInfoMap = new HashMap<>();		
		imageMap = new HashMap<>();
		squareTypeMap = new HashMap<>();
	}

	//单例模式
	public static ElementLoader getElementLoader() {
		if (elementLoader == null) {
			elementLoader = new ElementLoader();
		}
		return elementLoader;
	}

	//读取主配置文件
	public void readGamePro() throws IOException {
		InputStream inputStream = ElementLoader.class.getClassLoader().getResourceAsStream("pro/Game.pro");
		properties.clear();
		properties.load(inputStream);
		for(Object o:properties.keySet()) {
			String info = properties.getProperty(o.toString());
			gameInfoMap.put(o.toString(), infoStringToList(info,","));
		}
	}

	//读取图片
	public void readImagePro() throws IOException{
		InputStream inputStream = 
				ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("imageProPath").get(0));
		properties.clear();
		properties.load(inputStream);
		for(Object o:properties.keySet()) {
			String loc = properties.getProperty(o.toString());
			imageMap.put(o.toString(), new ImageIcon(loc));
		}
	}

	//读取游戏玩家配置
	public void readCharactorsPro() throws IOException {
		InputStream inputStream = 
				ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("charatersPath").get(0));
		properties.clear();
		properties.load(inputStream);
		for(Object o:properties.keySet()) {
			String info = properties.getProperty(o.toString());
			gameInfoMap.put(o.toString(),infoStringToList(info, ","));//放入Map的value中的是已经分割后的配置项
		}
	}

	//获取npc图片列表
	public List<ImageIcon> getNpcImageList(String s){ //s的值为npcA,npcB或npcC 对应相应的npc
		List<ImageIcon> imageList = new ArrayList<>();
		String npc = new String();
		for(int i=0; i<4; i++) {//4张图片
			npc = s + (char)(i+'0');
			imageList.add(imageMap.get(npc));
		}
		return imageList;
	}


	//读取气泡炸弹和爆炸效果配置Bubble.pro
	public void readBubblePro() throws IOException
	{
		InputStream inputStream = 
				ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("bubblePath").get(0));
		properties.clear();
		properties.load(inputStream);
		for(Object o:properties.keySet()) {

			String info = properties.getProperty(o.toString());
			gameInfoMap.put(o.toString(),infoStringToList(info, ","));//����Map��value�е����Ѿ��ָ���������
		}
	}


	//读取方块类型信息
	public void readSquarePro() throws IOException{
		InputStream inputStream = 
				ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get("squareProPath").get(0));
		properties.clear();
		properties.load(inputStream);
		for(Object o:properties.keySet()) {
			String info = properties.getProperty(o.toString());
			squareTypeMap.put(o.toString(),infoStringToList(info, ","));
		}
	}

	//读取特定地图
	public List<List<String>> readMapPro(String mapPro) throws IOException{
		List<List<String>> mapList = new ArrayList<>();
		InputStream inputStream = 
				ElementLoader.class.getClassLoader().getResourceAsStream(gameInfoMap.get(mapPro).get(0));
		properties.clear();
		properties.load(inputStream);
		Set<Object> sortSet = new TreeSet<>(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				try {
					int a = Integer.parseInt(o1.toString());
					int b = Integer.parseInt(o2.toString());
					if(a<b) {
						return 1;
					} else if (a>b) {
						return -1;
					} else {
						return 0;					
					}
				} catch (Exception e) {
					return -1;
				}
			}
		});
		sortSet.addAll(properties.keySet());
		for(Object o:sortSet) {
			String info = properties.getProperty(o.toString());
			if(o.toString().equals("size")) {//��ͼ��С
				gameInfoMap.put("mapSize", infoStringToList(info,","));
			} else {//��ͼ��Ϣ
				mapList.add(infoStringToList(info,","));				
			}
		}
		Collections.reverse(mapList);
		return mapList;
	}


	/**
	 * 将配置项按照指定字符串切割后转为字符串List
	 * @param info 配置项字符串
	 * @param splitString 切割字符串
	 * @return 切割后的字符串List
	 */
	private List<String> infoStringToList(String info,String splitString){
		return Arrays.asList(info.split(splitString));
	}

	public Map<String, List<String>> getGameInfoMap() {
		return gameInfoMap;
	}

	public Map<String, ImageIcon> getImageMap() {
		return imageMap;
	}

	public Map<String, List<String>> getSquareTypeMap() {
		return squareTypeMap;
	}

	//返回窗口w,h,windowSize[0]=w
	public List<Integer> getWindowSize(){
		List<String> data = gameInfoMap.get("windowSize");
		List<Integer> windowSize = new ArrayList<>();
		for(int i=0; i<data.size(); i++)
			windowSize.add(Integer.parseInt(data.get(i)));
		return windowSize;
	}
	
}
