package cn.yyx.research.ModelHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ModelIterator {
	
	File file = null;
	
	public ModelIterator(String filepath) {
		file = new File(filepath);
	}
	
	public void IterateFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String oneline = null;
			int line = 0;
			int allline = 0;
			while ((oneline = br.readLine()) != null)
			{
				allline++;
				oneline = oneline.trim();
				String[] ss = oneline.split("\\s+");
				
				if (ss.length == 4)
				{
					line++;
					 System.out.println("current aline:"+allline + ";line:"+line+";content:"+oneline);
					// System.out.println("one:" + ss[0] + ";two:" + ss[1] + ";three:" + ss[2] + ";four:" + ss[3]);
					
					// AddCount(statis, Integer.parseInt(ss[ss.length-1]), 1);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String processfile = "ModelDirectory/traintest.lm";
		ModelIterator mr = new ModelIterator(processfile);
		mr.IterateFile();
	}
	
}