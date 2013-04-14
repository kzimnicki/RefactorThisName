package tools

import org.springframework.test.context.ContextConfiguration

import cc.explain.server.core.CommonDao

import org.hibernate.type.Type

import cc.explain.server.rest.Rest

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * User: kzimnick
 * Date: 07.04.13
 * Time: 14:15
 */
@ContextConfiguration(locations = [
        "classpath:spring-context.xml",
        "classpath:spring-dao.xml",
        "classpath:test-spring-dataSource.xml",
        "classpath:spring-security.xml",
        "classpath:spring-tx.xml"
])
class GoogleWordTranslator {

    private CommonDao commonDao;

    private int MAX_PART_SIZE = 1850;


    def findNotTranslatedWords (int start, int offset) {
        List something = commonDao.executeSQL(String.format("SELECT * FROM Word w LEFT JOIN translation on value = sourceWord WHERE w.id between %d AND %d AND transWord IS NULL",start, offset), new String[0], new Type[0])
        print something
        def wordList = new ArrayList<String>(something.size())
        something.each {x -> wordList.add(x[2]) }
        print wordList
        return wordList
    }

    def Map<String,String> translateWords(List<String> words){
        def request = Rest.get().url("http://translate.googleapis.com/translate_a/t?anno=3&client=tee&format=html&v=1.0&logld=v7&tl=pl&sl=en")
        for (w in words){
            request.addParameter("q",w);
        }
        def response = request.execute()
        for(int i = 0; i<words.size(); i++){
            println words[i]+" - " + response[i]
        }
        println response;
    }

    def save(Map<String, String> translatedWords){
        translatedWords.each { k,v -> println "${k} - ${v}"}
    }

    def "test"(){
        when:
        String a = "";

        def words = findNotTranslatedWords(0,150)
        translateWords(words)

        then:
        true
    }

    def init () {
        def context = new ClassPathXmlApplicationContext("classpath:spring-dao.xml");
        commonDao = context.getBean("commonDao")
    }

    public static void main(String[] args){
        new GoogleWordTranslator().init();
    }
}
