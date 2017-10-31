package fr.hdb.artibip.service.applicatif.date;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@EBean(scope = EBean.Scope.Singleton)
public class DateSAImpl implements DateSA{
    private SimpleDateFormat sdfDate;
    private SimpleDateFormat sdfDayMonth;
    private SimpleDateFormat sdfDayMonthMin;
    private SimpleDateFormat sdfHoursMinute;

    @AfterInject
    protected void afterInject(){
        sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        sdfDayMonth  = new SimpleDateFormat("dd/MM", Locale.FRANCE);
        sdfDayMonthMin  = new SimpleDateFormat("dd/MM HH:mm", Locale.FRANCE);
        sdfHoursMinute = new SimpleDateFormat("HH:mm", Locale.FRANCE);
    }
    /**
     * Date du jour sans l'année
     * @return
     */
    @Override
    public String toDayWithoutYear(){
        String strDayMonth;
        //Recupration date du jour
        strDayMonth = sdfDayMonth.format(new Date());
        return strDayMonth;
    }

    /*
    * Conversion de Date en String Traitement date de publication
     */
    public String pubDateToString(Date dateToConvert) {
        String dateStr;
        String strToDay;
        try {
            dateStr = sdfDayMonth.format(dateToConvert);
            strToDay = sdfDayMonth.format(new Date());
            if(dateStr.equalsIgnoreCase(strToDay)){
                dateStr = sdfHoursMinute.format(dateToConvert);
            } else {
                dateStr = sdfDayMonthMin.format(dateToConvert);
            }
            return dateStr;
        } catch (Exception e) {
            return "KO";
        }

    }
}
