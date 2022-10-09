package frame;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import main.GameController;
import main.GameStart;
import model.loader.ElementLoader;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.swing.*;


public class BeginJPanel extends JPanel{
	private ImageIcon img;
	private int w;
	private int h;

	//构造函数
	public BeginJPanel(){
		List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");
		this.img = ElementLoader.getElementLoader().getImageMap().get("beginBackground");
		this.w = Integer.parseInt(data.get(0));
		this.h = Integer.parseInt(data.get(1));
		init();
	}

	private void init() {

		this.setLayout(null);
		
		JLabel jLabel = new JLabel(img);
		img.setImage(img.getImage().getScaledInstance(w, h,Image.SCALE_DEFAULT ));
		jLabel.setBounds(0, 0, w, h);
		
		ImageIcon img2 = new ImageIcon("src/main/resources/img/bg/introduce.png");
		final JLabel jLabel2 = new JLabel(img2);
		jLabel2.setBounds(w/2, h/6, 600, 800);
		jLabel2.setVisible(false);

		//排行榜图片
		ImageIcon img3 = new ImageIcon("src/main/resources/img/bg/rect5.png");
		final JLabel jLabel3 = new JLabel(img3);
		jLabel3.setBounds(w/6, h/9, 600, 800);
		jLabel3.setVisible(false);
		
		JButton onePlayerButton = new JButton();
		onePlayerButton.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect1"));
		onePlayerButton.setBounds(w/6, h/3, 180, 60);
		onePlayerButton.setBorderPainted(false);
		onePlayerButton.setFocusPainted(false);
		onePlayerButton.setContentAreaFilled(false);
		onePlayerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameController.setTwoPlayer(false);
				GameStart.startNewGame();
			}
		});
		
		JButton twoPlayerButton = new JButton();
		twoPlayerButton.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect2"));
		twoPlayerButton.setBounds(w/6, h/2, 180, 60);
		twoPlayerButton.setBorderPainted(false);
		twoPlayerButton.setFocusPainted(false);
		twoPlayerButton.setContentAreaFilled(false);
		twoPlayerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameController.setTwoPlayer(true);
				GameStart.startNewGame();
			}
		});

		JButton magicBoxButton = new JButton();
		magicBoxButton.setIcon(new ImageIcon("src/main/resources/img/bg/rect3.png"));

		magicBoxButton.setBounds(w/6, h-h/3, 180, 60);
		magicBoxButton.setBorderPainted(false);
		magicBoxButton.setFocusPainted(false);
		magicBoxButton.setContentAreaFilled(false);
		magicBoxButton.addActionListener(arg0 -> {
			 // TODO 自动生成的方法存根
			if(!jLabel2.isVisible())
				jLabel2.setVisible(true);
			else {
				jLabel2.setVisible(false);
			}
		});

		//排行榜
		//排行榜,前十名，此处写死，后续从数据库取出

		String name = "<html>" +
				"1050760349" +
				"<br>" +
				"1876428372" +
				"<br>" +
				"859823942" +
				"<br>" +
				"329984721" +
				"<br>" +
				"780192834" +
				"<br>" +
				"1982648372" +
				"<br>" +
				"5291039233" +
				"<br>" +
				"2874628324" +
				"<br>" +
				"4299855721" +
				"<br>" +
				"10984233265" +
				"</html>";

		final JLabel no1=new JLabel(name);
		no1.setFont(new Font("宋体",Font.BOLD, 22));
		no1.setForeground(Color.WHITE);
		no1.setBounds(w/2, 350, 300, 300);
		no1.setVisible(false);

		JButton magicBoxButton2 = new JButton();
		magicBoxButton2.setIcon(new ImageIcon("src/main/resources/img/bg/rect6.png"));
		magicBoxButton2.setBounds(w/6, h-h/6, 180, 60);
		magicBoxButton2.setBorderPainted(false);
		magicBoxButton2.setFocusPainted(false);
		magicBoxButton2.setContentAreaFilled(false);
		magicBoxButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				if(!jLabel3.isVisible()){
					jLabel3.setVisible(true);
				    no1.setVisible(true);
					RankThread rankThread = new RankThread();
					new Thread(rankThread).start();
				}
				else {
					jLabel3.setVisible(false);
					no1.setVisible(false);

				}
			}
		});

		this.add(onePlayerButton);
		this.add(twoPlayerButton);
		this.add(magicBoxButton);
		this.add(magicBoxButton2);
		this.add(no1);

		this.add(jLabel3);
		this.add(jLabel2);
		this.add(jLabel);
		
		
		this.setVisible(true);
		this.setOpaque(true);
	}

	static class RankThread implements Runnable{

		@Override
		public void run() {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url("http://localhost:8002/getRank")
					.build();
			client.newCall(request).enqueue(new Callback() {
				@Override
				public void onFailure(@NotNull Call call, @NotNull IOException e) {
					JOptionPane.showMessageDialog(null, "network error");
				}

				@Override
				public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
					String responseData = Objects.requireNonNull(response.body()).string();
					if (!responseData.isEmpty()){
						JSONObject jsonObject = JSON.parseObject(responseData);
						System.out.println(jsonObject);
					}
				}
			});

		}
	}
}
