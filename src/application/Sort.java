package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sort {
	private static String m_path;
	private  List<File> m_files=new ArrayList();
	
	Sort(List<File> files,String path){
		m_files=files;
		m_path=path;
		System.out.println("From parent");
	}
	public List<File> getFiles(){
		return m_files;
	}
	public String getPath() {
		return m_path;
	}
}
