package tools

import org.springframework.test.context.ContextConfiguration

import cc.explain.server.core.CommonDao

import org.hibernate.type.Type

import cc.explain.server.rest.Rest

import org.springframework.context.support.ClassPathXmlApplicationContext
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.ListeningExecutorService
import com.google.common.util.concurrent.MoreExecutors
import com.google.common.util.concurrent.ListenableFuture
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import com.google.common.base.Charsets
import java.nio.charset.Charset;

class GoogleWordTranslator {

    private CommonDao commonDao;

    def synchronized findNotTranslatedWords (int start, int offset) {
        println " >>> $start - $offset "
        List something = commonDao.executeSQL(String.format(" SELECT * FROM Word w WHERE w.value NOT IN ( SELECT sourceWord FROM Translation) AND w.id between %d AND %d",start, offset), new String[0], new Type[0])
        def wordList = new ArrayList<String>(something.size())
        something.each {x -> wordList.add(x[2]) }
        return wordList
    }

    def synchronized save(Map<String, String> translatedWords){
        println ">>> save "
        translatedWords.each { key, value -> save(key, value)}
    }

    def save(String key, String value){
        try{
            commonDao.executeSQL(String.format("INSERT INTO TRANSLATION(sourceLang, sourceWord, transLang, transWord) VALUES('en','%s','pl','%s')", key, value.toLowerCase()))
        }catch (MySQLIntegrityConstraintViolationException e){
            println "Exception" + e.getMessage()
        }


    }

    def init () {
        println ">>> init"
        def context = new ClassPathXmlApplicationContext("classpath:spring-dao.xml", "test-spring-dataSource.xml");
        commonDao = context.getBean("commonDao")
    }

    public static void main(String[] args){
        ExpandoMetaClass.disableGlobally()  //for debug purpose
        def translator = new GoogleWordTranslator()
        translator.init();

        ExecutorService service = Executors.newFixedThreadPool(10);
        for(int i = 0; i<1160; i++){
            service.submit(new Translator(i*200, i*200+200, translator));
        }
        service.shutdown();
    }
}