package cc.explain.parser

import cc.explain.server.api.TextDAO
import cc.explain.server.core.WordType
import cc.explain.server.model.Language
import cc.explain.server.model.RootWord
import cc.explain.server.model.Word
import cc.explain.server.model.WordRelation
import com.google.common.base.Splitter
import com.google.common.collect.ImmutableList
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.dao.DataIntegrityViolationException

import javax.validation.ConstraintViolationException

def commonDao;
def textDAO;

def init(){
    def context = new ClassPathXmlApplicationContext("classpath:spring-dao.xml", "test-spring-dataSource.xml");
    commonDao = context.getBean("commonDao")
    textDAO =new TextDAO();
    textDAO.setCommonDao(commonDao)
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

def createWordFamilyPlural(){
    def gradleScript = new File('/home/kz/Downloads/dewiktionary-20140104-pages-articles-multistream.xml')
    def parseEnable = false
    def rootWord = false
    def rootWordValue = null
    gradleScript.eachLine {
        rootWord = false

        if(it.matches('^\\|Nominativ Singular=(der|die|das) .*$')){
            parseEnable = true
            rootWord = true
        }

        if(it.startsWith('}}') || it.startsWith('|Bild')){
            parseEnable = false
        }

        if(parseEnable && rootWord){
            rootWordValue = parseWikiLine(it)

        } else if(parseEnable){
            def family = parseWikiLine(it)
            List<Word> words = textDAO.findWord([rootWordValue], cc.explain.server.model.Language.de)
            def persistedWord = words.size() > 0 ? words.get(0) : null;
            if(rootWordValue != null && family != null && persistedWord != null ){
                List<RootWord> rootWords = textDAO.findRootWord([persistedWord]);
                RootWord persistedRootWord =  rootWords.size() > 0 ? rootWords.get(0) : null;
                if(persistedRootWord == null){
                   persistedRootWord = new RootWord();
                   persistedRootWord.setRootWord(persistedWord)
                   commonDao.saveOrUpdate(persistedRootWord)
                }

                words = textDAO.findWord([family], cc.explain.server.model.Language.de)
                persistedWord = words.size() > 0 ? words.get(0) : null;



                WordRelation wordRelation = new WordRelation();
                println persistedRootWord.rootWord.id//zlego roota przypisuje!
                wordRelation.setRootWord(persistedRootWord)
                wordRelation.setWord(persistedWord)
                try{
                    commonDao.saveOrUpdate(wordRelation)
                }catch(Exception e){
                    e.printStackTrace()
                    //ignore
                }
            }
        }
    }
}


def parseWikiLine(def line) {
    def split = line.split('=')
    def second = split.length == 2 ? split[1] : '—'
    if(second.startsWith('—') || StringUtils.isBlank(second))
        return null

    def word = second.split(' ')[second.split(' ').length - 1]

    if( word.matches('[A-zäöüßÄÖÜßé-]+') )   {
       return word
    }
    return null
}

def parser = new GermanParser()
//parser.parse()
parser.init()
parser.createWordFamilyPlural()
//parser.parseWordFrequency()
//parser.sqlTest()