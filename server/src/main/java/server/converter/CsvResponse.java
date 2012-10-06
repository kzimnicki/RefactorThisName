package server.converter;

import java.util.List;

/**
 * User: kzimnick
 * Date: 25.09.12
 * Time: 18:30
 */
public class CsvResponse {
   private final String filename;
   private final List<String> records;

   public CsvResponse(List<String> records, String filename) {
       this.records = records;
       this.filename = filename;
   }
   public String getFilename() {
       return filename;
   }
   public List<String> getRecords() {
       return records;
   }
}