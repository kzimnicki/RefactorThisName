package cc.explain.server.core;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
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
    public static final String ILLEGAL_CHARS = "[0-9:>,-]+";
    private LexicalizedParser parser;
    private GrammaticalStructureFactory gsf;

    public StanfordNLP() {
        this.parser = LexicalizedParser.loadModel();
        this.gsf = new PennTreebankLanguagePack().grammaticalStructureFactory();

    }

    public List<List<HasWord>> getSentences(String text) {
        DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor(new StringReader(text), DocumentPreprocessor.DocType.Plain);
        return FluentIterable
                .from(documentPreprocessor)
                .transform(new Function<List<HasWord>, List<HasWord>>() {
                    public List<HasWord> apply(List<HasWord> input) {
                        return filterSentence(input);
                    }
                })
                .filter(new Predicate<List<HasWord>>() {
                    public boolean apply(List<HasWord> input) {
                        return input.size() <= 80;
                    }
                })
                .toList();
    }

    public List<String> getPhrasalVerbs(List<List<HasWord>> sentences) {
        List<String> phrasalVerbs = Lists.newLinkedList();
        for (List<HasWord> sentence : sentences) {
            Tree tree = parser.parseTree(sentence);
            GrammaticalStructure gs = gsf.newGrammaticalStructure(tree);
            Collection tdl = gs.typedDependenciesCollapsed();
            for (Object tpd : tdl) {
                if (((TypedDependency) tpd).reln().toString().equals("prt")) {
                    String dep = tpd.toString();
                    String[] split = dep.split(DEPENDENCY_REGEX); //TODO refactor
                    phrasalVerbs.add((split[1]+" "+split[2]).toLowerCase()); //TODO refactor
                }
            }
        }
        return phrasalVerbs;
    }

    public List<HasWord> filterSentence(List<HasWord> currentSentence) {
        Iterable<HasWord>  filtered = Iterables.filter(currentSentence, new Predicate<HasWord>() {
            public boolean apply(HasWord input) {
                return !input.word().matches(ILLEGAL_CHARS);
            }
        });
        return ImmutableList.copyOf(filtered);
    }
}
