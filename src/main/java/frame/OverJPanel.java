package frame;


import main.GameStart;
import model.loader.ElementLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



public class OverJPanel extends JPanel {
	private ImageIcon img;
	private int w;
	private int h;
	private static JButton result = new JButton();
	private static JLabel scoreBoard=new JLabel();

	//构造函数
	public OverJPanel(){
		List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");
		this.img = ElementLoader.getElementLoader().getImageMap().get("gameOver");
		this.w = Integer.parseInt(data.get(0));
		this.h = Integer.parseInt(data.get(1));
		init();
	}

	private void init() {

		this.setLayout(null);
		
		JLabel jLabel = new JLabel(img);
		img.setImage(img.getImage().getScaledInstance(w, h,Image.SCALE_DEFAULT ));
		jLabel.setBounds(0, 0, w, h);
		
		JButton restart = new JButton();
		restart.setIcon(ElementLoader.getElementLoader().getImageMap().get("rect4"));
		restart.setBounds(w/2-90, h-h/4, 180, 60);
		restart.setBorderPainted(false);
		restart.setFocusPainted(false);
		restart.setContentAreaFilled(false);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameStart.changeJPanel("begin");
			}
		});
		
		result.setFont(new Font("Times New Roman", Font.BOLD, 48));
		result.setBounds(w/2-150, h-3*(h/7), 300, 80);
		result.setHorizontalTextPosition(SwingConstants.CENTER);
		result.setVerticalTextPosition(SwingConstants.CENTER);
		result.setBorderPainted(false);
		result.setContentAreaFilled(false);
		result.setEnabled(false);
		result.setForeground(new Color(255, 153, 0));

		//积分输出

		scoreBoard.setFont(new Font("宋体",Font.BOLD, 22));
		scoreBoard.setForeground(Color.RED);
		scoreBoard.setBounds(w/2, 350, 300, 300);
		scoreBoard.setVisible(true);

		this.add(scoreBoard);
		this.add(result);
		this.add(restart);
		this.add(jLabel);

		
		this.setVisible(true);
		this.setOpaque(true);
	}

	public static JButton getResult() {
		return result;
	}
	public static JLabel getScoreBoard() {
		return scoreBoard;
	}
	
}
