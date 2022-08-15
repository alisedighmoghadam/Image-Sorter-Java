package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class DimensionalSort extends Sort implements Runnable {
	
	
	DimensionalSort(List<File> files,String path){
		super(files, path);
	}
	
	@Override
	public void run() {
		//List<int[]> dimensions =new ArrayList<int[]>();
		for(File file:super.getFiles()) {
			try {
				BufferedImage image=ImageIO.read(file);
				final int width=image.getWidth();
				final int height=image.getHeight();
				new File(super.getPath()+"//"+width+" X "+height).mkdirs();
				File dir=new File(super.getPath()+"//"+width+" X "+height);
				Files.copy(file.toPath(), (new File(dir.getPath()+'\\'+file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				/*if(width>height) {
					new File(super.getPath()+"//wide_images").mkdirs();
					File wideDir=new File(super.getPath()+"//wide_images");
					Files.copy(file.toPath(), (new File(wideDir.getPath()+'\\'+file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}else if(width<height) {
					
					new File(super.getPath()+"//portrait_images").mkdirs();
					File portraitDir=new File(super.getPath()+"//portrait_images");
					Files.copy(file.toPath(), (new File(portraitDir.getPath()+'\\'+file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}else {
					new File(super.getPath()+"//square_images").mkdirs();
					File squareDir=new File(super.getPath()+"//square_images");
					Files.copy(file.toPath(), (new File(squareDir.getPath()+'\\'+file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}*/
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
		}
			
		
	
	}
}
