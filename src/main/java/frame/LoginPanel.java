package frame;


import main.GameStart;
import model.loader.ElementLoader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.*;



public class LoginPanel extends JPanel {
    private ImageIcon img;
    private int w;
    private int h;

    //构造函数
    public LoginPanel(){
        List<String> data = ElementLoader.getElementLoader().getGameInfoMap().get("windowSize");
        this.img = ElementLoader.getElementLoader().getImageMap().get("login");
        this.w = Integer.parseInt(data.get(0));
        this.h = Integer.parseInt(data.get(1));
        init();
    }


    private void init() {

        this.setLayout(null);

        JLabel jLabel = new JLabel(img);
        img.setImage(img.getImage().getScaledInstance(w, h,Image.SCALE_DEFAULT ));
        jLabel.setBounds(0, 0, w, h);

        //登录文本框

        JTextField jt=new JTextField();//设置一个文本框20像素长度
        jt.setColumns(200);//设置文本框
        //jt.setText("请输入账号");//设置文本
        jt.setText("");
        jt.setFont(new Font("黑体",Font.PLAIN,20));//设置字体格式
        this.add(jt);
        jt.setBounds(605, 723, 150, 30);
        JTextField pswd=new JTextField();//设置一个文本框20像素长度
        pswd.setColumns(200);//设置文本框
        //pswd.setText("请输入密码");//设置文本
        pswd.setText("");
        pswd.setFont(new Font("黑体",Font.PLAIN,20));//设置字体格式
        this.add(pswd);
        pswd.setBounds(605, 760, 150, 30);

        //登录按钮
        JButton login = new JButton();
        login.setIcon(ElementLoader.getElementLoader().getImageMap().get("logButton"));
        login.setBounds(400, h/2, 180, 60);
        login.setBorderPainted(false);
        login.setFocusPainted(false);
        login.setContentAreaFilled(false);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //后续在该处决定点击事件，用户名输入成功或错误，先写死
                //System.out.println(jt.getText());
                //System.out.println(pswd.getText());
                //System.out.println(jt.getText()=="10000");
                if(jt.getText().equals("10000") && pswd.getText().equals("123456"))
                {
                    //登陆成功
                    GameStart.changeJPanel("begin");
                    //设置GameFrame的静态变量usrID，全局共享
                    GameFrame.uID = jt.getText();
                }
                else{
                    //登陆不成功
                    JOptionPane.showMessageDialog(null, "error userID or password");
                }

            }
        });
        //直接游戏
        JButton gaming = new JButton();
        gaming.setIcon(ElementLoader.getElementLoader().getImageMap().get("gameButton"));
        gaming.setBounds(600, h/2, 180, 60);
        gaming.setBorderPainted(false);
        gaming.setFocusPainted(false);
        gaming.setContentAreaFilled(false);
        gaming.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //直接开始游戏，没有登录，不计入排行
                GameStart.changeJPanel("begin");
            }
        });

        this.add(gaming);
        this.add(login);
        this.add(jLabel);


        this.setVisible(true);
        this.setOpaque(true);
    }


}
