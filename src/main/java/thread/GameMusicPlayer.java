package thread;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

public class GameMusicPlayer extends Thread{
	private List<String> files;
	
	@Override
	public void run(){
		startMusic();
	}
	
	public GameMusicPlayer(){
		files = new ArrayList<String>();
        files.add("src/main/resources/music/bgm0.wav");
        files.add("src/main/resources/music/bgm1.wav");
        files.add("src/main/resources/music/bgm2.wav");
	}
	
	private void startMusic(){

        int i=0;
        byte[] buffer = new byte[4096];
        while(true)
        {
            try
            {
            	File file = new File(files.get(i));
                InputStream stream = Files.newInputStream(file.toPath());
                InputStream bufferedIn = new BufferedInputStream(stream);
                 
                AudioInputStream is = AudioSystem.getAudioInputStream(bufferedIn);
                AudioFormat format = is.getFormat();
                SourceDataLine line = AudioSystem.getSourceDataLine(format);
                line.open(format);
                line.start();
                while (is.available() > 0)
                {
                    int len = is.read(buffer);
                    line.write(buffer, 0, len);
                }
                line.drain(); 
                line.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            i++;
            i = i%3;
        }
	}

}