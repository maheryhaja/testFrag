package fr.hdb.artibip.service.applicatif.client.cgv;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import fr.hdb.artibip.commun.constantes.url.Url;
import fr.hdb.artibip.donnee.dto.ws.post.cgv.CgvDto;
import fr.hdb.artibip.service.businessdelegate.client.cgv.GetCgvBDL;
import fr.hdb.artibip.service.businessdelegate.client.cgv.GetCgvBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class CgvSAImpl implements CgvSA {

    @Bean(GetCgvBDLImpl.class)
    protected GetCgvBDL cgvBDL;

    @Override
    public CgvDto getCgv() {
        String url = Url.CONTEXT + Url.URL_CGV;
        try {
            CgvDto cgv = cgvBDL.getGcv(url);
            return cgv;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
