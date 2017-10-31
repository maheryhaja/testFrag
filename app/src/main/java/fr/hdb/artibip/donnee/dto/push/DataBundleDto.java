package fr.hdb.artibip.donnee.dto.push;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * .
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataBundleDto {
    /*
        {
            "data": {
                 "idIntervention": 235
            },
            "notification": {
                "title":"D'Clic Elec Intervention",
                "body": "J'arrive dans X minutes",
                "sound": "default"
            },
            "registration_ids": [
                "e45I7P0DGP4:APA91bFNleJzsnmWdf8aqABccyx9DKiGMB_F27e9s4einPb5ejkgLkjgYT2HrU2mwPhrZg7l5j7IBYn-EFnKnMBaJhOWLCTEWLAW8zpyZvYRp9XH02SPGae9aVXqxpBh-_XKBV2qH1Is"
            ]
        }
    */
    @JsonProperty(value ="data")
    private DataDto data;

    @JsonProperty(value ="notification")
    private NotificationDto notification;

    public DataDto getData() {
        return data;
    }

    public void setData(DataDto data) {
        this.data = data;
    }

    public NotificationDto getNotification() {
        return notification;
    }

    public void setNotification(NotificationDto notification) {
        this.notification = notification;
    }
}

