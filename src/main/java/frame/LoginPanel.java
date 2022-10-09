package frame;


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
        login.addActionListener(arg0 -> {
            if (!jt.getText().isEmpty()||!pswd.getText().isEmpty()){
                LoginThread loginThread = new LoginThread(jt.getText(),pswd.getText());
                new Thread(loginThread).start();
            }


        });
        //直接游戏
        JButton gaming = new JButton();
        gaming.setIcon(ElementLoader.getElementLoader().getImageMap().get("gameButton"));
        gaming.setBounds(600, h/2, 180, 60);
        gaming.setBorderPainted(false);
        gaming.setFocusPainted(false);
        gaming.setContentAreaFilled(false);
        gaming.addActionListener(arg0 -> {
            //直接开始游戏，没有登录，不计入排行
            GameStart.changeJPanel("begin");
        });

        this.add(gaming);
        this.add(login);
        this.add(jLabel);


        this.setVisible(true);
        this.setOpaque(true);
    }

static class LoginThread implements Runnable{
    private final String username;
    private final String password;

    LoginThread(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8002/login")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                JOptionPane.showMessageDialog(null, "network error");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                if ("1".equals(responseData)){
                    //登陆成功
                    GameStart.changeJPanel("begin");
                    //设置GameFrame的静态变量usrID，全局共享
                    GameFrame.uID = username;
                }
                if ("0".equals(responseData)){
                    JOptionPane.showMessageDialog(null, "error userID or password");
                }
            }
        });

    }
}
}
