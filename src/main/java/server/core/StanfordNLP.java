package server.core;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.*;

import java.io.StringReader;
import java.util.*;

/**
 * User: kzimnick
 * Date: 13.05.12
 * Time: 15:43
 */
public class StanfordNLP {

    public static final String DEPENDENCY_REGEX = "[(),0-9- ]+";
    private LexicalizedParser parser;
    private GrammaticalStructureFactory gsf;

    public StanfordNLP() {
        this.parser = LexicalizedParser.loadModel();
        this.gsf = new PennTreebankLanguagePack().grammaticalStructureFactory();

    }


    public List<List<HasWord>> getSentences(String text) {
        DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(new StringReader(text), DocumentPreprocessor.DocType.Plain);
        List<List<HasWord>> sentences = new LinkedList<List<HasWord>>();
        for (List<HasWord> currentSentence : documentPreprocessor) {
            if (currentSentence.size() > 80) {
                System.err.print(currentSentence.size());
                System.err.println(currentSentence);
                continue;
            }
            currentSentence = filterSentence(currentSentence);
            sentences.add(currentSentence);
        }
        return sentences;
    }

    public Map<String,String> getPhrasalVerbs(List<List<HasWord>> sentences) {
        Map<String,String> phrasalVerbs = new HashMap<String,String>();
        for (List<HasWord> sentence : sentences) {
            Tree tree = parser.parseTree(sentence);
            GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
            Collection tdl = gs.typedDependenciesCollapsed();
            for (Object tpd : tdl) {
                if (((TypedDependency) tpd).reln().toString().equals("prt")) {
                    String dep = tpd.toString();
                    phrasalVerbs.put(dep.split(DEPENDENCY_REGEX)[1], dep.split(DEPENDENCY_REGEX)[2]);
                }
            }
        }
        return phrasalVerbs;
    }

    private List<HasWord> filterSentence(List<HasWord> currentSentence) {
        List<HasWord> filteredSentence = new LinkedList<HasWord>();
        for (HasWord word : currentSentence) {
            if (!word.word().matches("[0-9:>,-]+")) {
                filteredSentence.add(word);
            } else {
                System.err.println(word);
            }
        }
        return filteredSentence;
    }
}
