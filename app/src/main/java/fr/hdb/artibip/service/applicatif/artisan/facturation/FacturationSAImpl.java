package fr.hdb.artibip.service.applicatif.artisan.facturation;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.donnee.dto.ws.artisan.facturation.FacturationDto;
import fr.hdb.artibip.donnee.dto.ws.response.ResponseDto;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSA;
import fr.hdb.artibip.service.applicatif.preferences.PreferencesSAImpl;
import fr.hdb.artibip.service.businessdelegate.artisan.facturation.FacturationBDL;
import fr.hdb.artibip.service.businessdelegate.artisan.facturation.FacturationBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class FacturationSAImpl implements FacturationSA {

    @Bean(FacturationBDLImpl.class)
    protected FacturationBDL facturationBDL;

    @Bean(PreferencesSAImpl.class)
    protected PreferencesSA preferencesSA;

    /**
     * Ajout des éléments pour la facturation
     */
    @Override
    public ResponseDto sendFacturation(FacturationDto facturationDto){
        ResponseDto result = facturationBDL.sendFacturation(facturationDto,preferencesSA.getToken());
        if(result != null){
            return result;
        }
        return null;
    }
}
