package cn.yyx.research.ModelHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import cn.yyx.contentassist.parsehelper.ComplexParser;
import cn.yyx.parse.specialparse.ParseRoot;

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
	
	public static void CheckOneSentence(String onesentence) {
		ComplexParser.GetSentence(onesentence);
	}
	
	public static void CheckOneSentenceWeaker(String onesentence) {
		try {
			ParseRoot.ParseOneSentence(onesentence, null, true);
		} catch (Exception | Error e) {
			System.err.println("Parse One Sentence error! serious error, the system will exit. The error parsed setence is :" + onesentence + ".");
			if (e instanceof ParseCancellationException)
			{
				ParseCancellationException pce = (ParseCancellationException)e;
				Throwable pc = pce.getCause();
				if (pc instanceof RecognitionException)
				{
					RecognitionException re = (RecognitionException)pce.getCause();
				    ParserRuleContext context = (ParserRuleContext)re.getCtx();
				    System.err.println(re);
				    System.err.println(context);
				}
			}
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}