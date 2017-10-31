package fr.hdb.artibip.service.applicatif.date;

import java.util.Date;


public interface DateSA {
    /**
     * Date du jour sans l'année
     * @return
     */
    String toDayWithoutYear();

    /*
   * Conversion de Date en String Traitement date de publication
    */
    String pubDateToString(Date dateToConvert);
}
