package fr.hdb.artibip.service.applicatif.artisan.temps;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.donnee.dto.ws.response.ListTempsHoraireResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.artisan.temps.TempsBDL;
import fr.hdb.artibip.service.businessdelegate.artisan.temps.TempsBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class TempsSAImpl implements TempsSA {
    @Bean(TempsBDLImpl.class)
    protected TempsBDL tempsBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    /**
     * Récupérer la liste des plages horaires et temps d'intervention
     */
    @Override
    public ListTempsHoraireResponseDto getListTempsHoraire (){
        ListTempsHoraireResponseDto result = tempsBDL.getListTempsHoraire(preferencesSA.getToken());
        if(result != null){
            return result;
        }
        return null;
    }

    /**
     * Conversion String en minute
     * @param strTime
     * @return
     */
    @Override
    public int StringtoMinute(String strTime){
        int heure;
        int minute;
        String splitTime[];
        splitTime = strTime.split(" ");
        if(splitTime.length == 4){
            heure = Integer.valueOf(splitTime[0]);
            minute = Integer.valueOf(splitTime[2]);
        } else {
            if(strTime.indexOf("h") > -1){
                heure = Integer.valueOf(splitTime[0]);
                minute = 0;
            } else {
                heure = 0;
                minute = Integer.valueOf(splitTime[0]);
            }
        }
        minute = minute + ((heure * 60));
        return minute;
    }

    /**
     * Conversion String en minute
     * @param strTime
     * @return
     */
    @Override
    public double StringtoHour(String strTime){
        double hour;
        double minByHour = 60.0;
        int minute = StringtoMinute(strTime);
        hour = minute / minByHour;
        return hour;
    }

    /**
     * Conversion time en String
     * @param time
     * @return
     */
    @Override
    public String TimeToString(int time) {

        String heure=null;
        String minute=null;
        int h = time/60;
        int m= time%60;
        heure=heure.valueOf(h);
        minute=minute.valueOf(m);
        String duree=null;
        if(h>0) {
            if(m>0) {
                duree = heure + " H " + minute + " MIN";
            }else{
                if(h>1) {
                    duree = heure + " HEURES";
                }else{
                    duree = heure + " HEURE";
                }
            }
        }else{
            duree = minute + " MINUTE";
        }
        return duree;
    }
}
