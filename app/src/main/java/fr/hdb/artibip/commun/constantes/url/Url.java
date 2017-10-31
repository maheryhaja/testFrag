package fr.hdb.artibip.commun.constantes.url;

public interface Url {
    // Both URLS
    //public static final String CONTEXT                  = "https://www.bo-application.fr/";

    public static final String CONTEXT                  = "http://aae.qualif.arkeup.com/web/";

    public static final String APP_DEV                  = "app_dev.php/";
    public static final String DEV_WS                   = "ws-artibip/";
    public static final String URL_LOGIN                = "dclic/ws-connect-user";
    public static final String URL_LOGIN_OUBLI          = "dclic/forgot-pass-user";
    public static final String URL_LOGIN_CHANGE          = "update-password";
    public static final String URL_DECONNEXION          = "ws-deconnect-user";
    public static final String URL_CGV                  = "dclic/list-cgv";
    public static final String URL_CRON                 = "simule-cron-ws";

    // Client URLS
    public static final String URL_LIST_ETABLISSEMENT   = "list-etablissement-client";
    public static final String URL_INSERT_ETABLISSEMNET = "create-etablissement-client";
    public static final String URL_LIST_URGENCE         = "list-type-urgence";
    public static final String URL_CREATE_INTERVENTION  = "create-intervention";
    public static final String URL_LIST_INTERVENTION    = "intervention-client";
    public static final String URL_LIST_DEMANDE_CLIENT  = "intervention-client";
    public static final String URL_UPDATE_CONTACT       = "update-etablissement-contact";

    // Artisan URLS
    public static final String URL_REFUS_INTERVENTION   = "refus-intervention";
    public static final String URL_VALIDATION_INTERVENTION   = "send-msgcl-accept";
    public static final String URL_LIST_DEMANDE_ARTISAN = "intervention-employe";
    public static final String URL_DISTANCE             = "distance-duration";
    public static final String URL_LIST_TEMPS           = "list-temps-horaire-intervention";
    public static final String URL_ABS_INTERVENTION     = "abstract-intervention";
    public static final String URL_FACTURATION          =  "step-facture-intervention";
    public static final String URL_NOTIFICATION         =  "push-notification-client-register";
}
