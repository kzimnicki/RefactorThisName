package cc.explain.server.subtitle;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class SubtitleUtils {
	
	public static final String PATTERN_CORE = "@@TRANSLATED_TEXT@@";

	private SubtitleUtils(){
		// do nothing
	}

	public static String replaceText(String text, String english, String translated, String pattern){
		String replacePattern = String.format("((?i)\\b%s\\b)", english);
		translated = pattern.replace(PATTERN_CORE, translated);
		String replacement = String.format("$1 %s", translated);
		return text.replaceAll(replacePattern, replacement);
	}

    public static String replacePhrasalVerb(String pattern, String english, String translated){
		return pattern.replace(PATTERN_CORE, String.format("%s = %s",english, translated));
	}
	
	public static boolean findText(String text, String english){
		Pattern compile = Pattern.compile("\\b"+english+"\\b", Pattern.CASE_INSENSITIVE);
		return compile.matcher(text).find();
	}


	
	public static String replaceText(String text, Map<String,String> translations, String pattern){
		for (Entry<String, String> entry : translations.entrySet()) {
			text = replaceText(text, entry.getKey(), entry.getValue(), pattern);
		}
		return text;
	}

    public static String replacePhrasalVerb(String text, Map<String, String> translations, String pattern) {
        return null;
    }

    public static boolean findPhrasalVerb(String text, String phrasalVerb){
       String phrasalVerbPattern = phrasalVerb.replaceAll(" ","(.*?)");
       Pattern compile = Pattern.compile("\\b"+phrasalVerbPattern+"\\b", Pattern.CASE_INSENSITIVE);
	   return compile.matcher(text).find();
    }

}
