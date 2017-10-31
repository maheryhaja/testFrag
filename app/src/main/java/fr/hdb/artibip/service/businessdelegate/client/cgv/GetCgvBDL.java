package fr.hdb.artibip.service.businessdelegate.client.cgv;

import fr.hdb.artibip.donnee.dto.ws.post.cgv.CgvDto;


public interface GetCgvBDL {
    CgvDto getGcv(String url);
}
