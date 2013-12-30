package cc.explain.parser

import cc.explain.server.core.WordType
import cc.explain.server.model.Language
import cc.explain.server.model.Word
import com.google.common.base.Splitter
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.dao.DataIntegrityViolationException;

def commonDao;

def init(){
    def context = new ClassPathXmlApplicationContext("classpath:spring-dao.xml", "test-spring-dataSource.xml");
    commonDao = context.getBean("commonDao")
}

def parse(){
    def wordsMap = [:].withDefault {[]}
    new File(this.getClass().getResource('/converted_deu.txt').getFile()).eachLine {
        def key = it.split(';')[0]
        def value = it.split(';')[1]
        wordsMap[key].add(value.toLowerCase())
        wordsMap[key].unique()
    }
    println wordsMap['machen']
}

def parseWordFrequency(){
    Splitter splitter = Splitter.onPattern("[(,)]");
    int wordsNumber = 336945;
    int currentLineNumber =1;
    germanWords = [:]

    def file = new File(this.getClass().getResource('/converted_words.txt').getFile())
    file.readLines().reverse().each {
        def frequency = normalize(wordsNumber, currentLineNumber++)
        def word = it
              if(word.contains("(")){
                  Iterable<String> split = splitter.split(word);
                  Iterator<String> iterator = split.iterator();
                  String first = iterator.next();
                  germanWords[first]=frequency;
                  while(iterator.hasNext()){
                      String current = first + iterator.next();
                      if (!iterator.hasNext()){
                          continue;
                      }
                      germanWords[current] = frequency
                  }
              }else if (word.contains(",")) {
                  //skip
              }
              else{
                  germanWords[word.split(" ")[0]]= frequency;
              }

    }
    def index = 0 ;
   germanWords.each{
       key, value ->
           createInDatabase(value, key, Language.de, WordType.undefined)
           println index++
   }
}

private long normalize(def wordsNumber, def currentLineNumber) {
    Math.round((((1000000 / wordsNumber) * currentLineNumber) / 10000))
}

def createInDatabase(freq, v, lang, type) {
    def word = new Word(
        frequency: freq,
        value: v,
        language: lang,
        wordType: type
    )
    try{
        println word.value
        commonDao.create(word)
        commonDao.flush()
    }catch (DataIntegrityViolationException e){
        e.printStackTrace()
    }
}

def parser = new GermanParser()
//parser.parse()
parser.init()
parser.parseWordFrequency()
//parser.sqlTest()