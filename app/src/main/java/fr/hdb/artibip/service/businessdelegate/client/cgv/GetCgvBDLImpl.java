package fr.hdb.artibip.service.businessdelegate.client.cgv;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpMethod;
import fr.hdb.artibip.donnee.dto.ws.post.cgv.CgvDto;
import fr.hdb.artibip.service.businessdelegate.AbstractCommunBDLImpl;


@EBean(scope = EBean.Scope.Singleton)
public class GetCgvBDLImpl extends AbstractCommunBDLImpl implements GetCgvBDL {

    @Override
    public CgvDto getGcv(String url) {
        try {
            String response = sendMessageForEntity(null, url, HttpMethod.POST, String.class,
                    null, null, null, getHeader(), getConverter()).getBody();
            return sendMessageForEntity(null, url, HttpMethod.POST, CgvDto.class,
                    null, null, null, getHeader(), getConverter()).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
