package cc.explain.server.core;

import edu.stanford.nlp.ling.HasWord;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * User: kzimnick
 * Date: 11.05.13
 * Time: 19:10
 */
public class NLPTask implements Callable<List<String>> {
    private List<List<HasWord>> sentences;
    private StanfordNLP stanfordNLP;

    public NLPTask( List<List<HasWord>> sentences){
        this.sentences = sentences;
        this.stanfordNLP = new StanfordNLP();
    }


    public List<String> call() throws Exception {
        System.out.println(Thread.currentThread().getId());
        System.out.println(sentences.size());

        List<String> phrasalVerbs = stanfordNLP.getPhrasalVerbs(sentences);
        return phrasalVerbs;
    }
}
