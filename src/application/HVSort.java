package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class HVSort extends Thread{
	private static String m_path;
	private static List<File> m_files=new ArrayList();
	private static boolean m_result=false;
	HVSort(List<File> files,String path){
		m_files=files;
		m_path=path;
	}
	public boolean getResult() {
		return m_result;
	}
	@Override
	public void run() {
		for(File file:m_files) {
			try {
				BufferedImage image=ImageIO.read(file);
				final int width=image.getWidth();
				final int height=image.getHeight();
				if(width>height) {
					new File(m_path+"//wide_images").mkdirs();
					File wideDir=new File(m_path+"//wide_images");
					Files.copy(file.toPath(), (new File(wideDir.getPath()+'\\'+file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}else if(width<height) {
					new File(m_path+"//portrait_images").mkdirs();
					File portraitDir=new File(m_path+"//portrait_images");
					Files.copy(file.toPath(), (new File(portraitDir.getPath()+'\\'+file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}else {
					new File(m_path+"//square_images").mkdirs();
					File squareDir=new File(m_path+"//square_images");
					Files.copy(file.toPath(), (new File(squareDir.getPath()+'\\'+file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
		}
		m_result=true;
	}
	
}
