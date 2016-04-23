package cn.yyx.research.ModelHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.parsehelper.ComplexParser;

public class ModelChecker {

	public static void CheckWillBePutModel(String key, ArrayList<String> predict) {
		// most important checking.
		String[] ks = key.split(" ");
		List<String> ptests = new LinkedList<String>();
		ptests.addAll(Arrays.asList(ks));
		ptests.addAll(predict);
		Iterator<String> itr = ptests.iterator();
		while (itr.hasNext()) {
			String ke = itr.next();
			// Sentence sete = null;
			ComplexParser.GetSentence(ke);
			System.out.println("One Sentence Successfully Parsed:" + ke + ";");
		}
	}

}