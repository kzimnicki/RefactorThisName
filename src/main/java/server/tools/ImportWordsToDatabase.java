package server.tools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.api.CommonDao;
import server.core.WordType;
import server.core.WordTypeFrequencyContainer;
import server.model.newModel.Word;
import server.model.newModel.WordFamily;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * User: kzimnick
 * Date: 02.06.12
 * Time: 16:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-context.xml",
        "classpath:spring-dao.xml",
        "classpath:spring-dataSource.xml",
        "classpath:spring-security.xml",
        "classpath:spring-tx.xml"
})
public class ImportWordsToDatabase {
    @Autowired
    CommonDao commonDao;

//    @Test
    public void importWords() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("freq.txt")));
        String line = "";
        int x = 0;
        WordTypeFrequencyContainer container = new WordTypeFrequencyContainer();
        WordType buffor = null;
        Word previousWord = new Word();
        WordFamily previousWordfamily = new WordFamily();
        WordFamily wordFamily = new WordFamily();
        String previousLine = "";
        WordType wordType;
        while ((line = br.readLine()) != null) {
            String[] elements = line.split("[\t@]+");
            if(!elements[1].matches("[A-Za-z-]+") || elements[1].startsWith("-") || elements[1].endsWith("-") || elements[1].length() < 2)
            {
//                System.out.println(elements[1]);
                continue;
            }

            Word word = new Word();
            word.setValue(elements[1].toLowerCase());
            word.setFrequency(extractFrequency(line));

            if (line.startsWith("\t@")) {
                if (previousLine.startsWith("\t@")) {
                    wordFamily = previousWordfamily;
                } else {
                    wordFamily = new WordFamily();
                    wordFamily.setRoot(previousWord);
                }
                word.setWordType(buffor);
                if(wordFamily.getRoot().getValue().equalsIgnoreCase(word.getValue())){
                    word = wordFamily.getRoot();
                }
                wordFamily.getFamily().add(word);
            } else {
                wordType = WordType.valueOf(elements[2]);
                word.setWordType(wordType);
                buffor = wordType;
            }
            previousWord = word;
            previousLine = line;
            previousWordfamily = wordFamily;

//            System.out.println(word.getValue());

            try{
                commonDao.saveOrUpdate(word);


            }catch(Exception e){
//                e.printStackTrace();
                Word persistedWord = (Word) commonDao.getFirstByHQL("FROM Word w WHERE w.value = :wordValue", "wordValue", word.getValue());
                word.setId(persistedWord.getId());
//                Thread.sleep(5*1000);
            }


            if(wordFamily.getRoot() != null){
                try{
                commonDao.saveOrUpdate(wordFamily);
                }catch(Exception e){
//                    e.printStackTrace();
                }
                x++;
            }
//            if(x > 10000){
//                System.out.println("KONIEC");
//                break;
//            }
        }
         System.out.println("KONIEC !!!!!");


    }

    private static Integer extractFrequency(String line) {
        return Integer.valueOf(line.split("\t")[6].split("\\.")[1]);
    }


}

