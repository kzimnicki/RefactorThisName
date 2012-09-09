package server.tools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import server.core.CommonDao;
import server.core.WordType;
import server.model.newModel.RootWord;
import server.model.newModel.Word;
import server.model.newModel.WordRelation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    @Test
    public void importWords() throws IOException, InterruptedException {
        BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("files/freq.txt")));
//           BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("testFreq.txt")));
        String line = "";
        int x = 0;
        WordType buffor = null;
        Word previousWord = new Word();
        RootWord previousRootWord = new RootWord();
        RootWord rootWord = new RootWord();
        WordRelation wordRelation = new WordRelation();
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
            wordRelation = new WordRelation();

            if (line.startsWith("\t@")) {
//                if (previousLine.startsWith("\t@")) {
//                    wordFamily = previousWordfamily;
//                } else {
//                    wordFamily = new WordFamily();
//                    wordFamily.setRoot(previousWord);
//                }
                word.setWordType(buffor);
                rootWord = previousRootWord;
                wordRelation.setRootWord(rootWord);
                wordRelation.setWord(word);
//                if(wordFamily.getRoot().getValue().equalsIgnoreCase(word.getValue())){
//                    word = wordFamily.getRoot();
//                }
//                wordFamily.getFamily().add(word);
            } else {
                rootWord = new RootWord();
                rootWord.setRootWord(word);

                wordType = WordType.valueOf(elements[2]);
                word.setWordType(wordType);

                buffor = wordType;

                wordRelation.setRootWord(rootWord);
                wordRelation.setWord(word);
            }
            previousRootWord = rootWord;
            previousWord = word;

//            System.out.println(word.getValue());

//            try{
            System.out.println(word);

            if(word.getFrequency() > 0 && !word.getWordType().equals(WordType.NoP) ){

                try{
                    commonDao.saveOrUpdate(wordRelation.getWord());
                    commonDao.saveOrUpdate(wordRelation.getRootWord());
                    commonDao.merge(wordRelation);
                }catch(Exception e){
                    e.printStackTrace();
//                    Word persistedWord = getWordFromDB(word.getValue(), word.getWordType());
//                    wordRelation.setWord(persistedWord);
                }


            }



//

//            if(wordFamily.getRoot() != null){
//                try{
//                commonDao.saveOrUpdate(wordFamily);
//                }catch(Exception e){
////                    e.printStackTrace();
//                }
//                x++;
//            }
//            if(x > 10000){
//                System.out.println("KONIEC");
//                break;
//            }
        }
         System.out.println("KONIEC !!!!!");


    }

    private Word getWordFromDB(String wordValue, WordType wordType) {
        String[] params = {"wordValue"}; //TODO wordType
        Object[]  values = {wordValue};
        return (Word) commonDao.getFirstByHQL("FROM Word w WHERE w.value = :wordValue", params, values);
    }

    private static Integer extractFrequency(String line) {
        return Integer.valueOf(line.split("\t")[6].split("\\.")[1]);
    }


}

