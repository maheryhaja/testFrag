package fr.hdb.artibip.service.applicatif.preferences;

public interface PreferencesSA {

    String getLogin();
    void setLogin(String login);

    String getToken();
    void setToken(String token);

    String getRole();
    void setRole(String userType);

    String getEmail();
    void setEmail(String email);

    void setId(int id);
    int getId();

    String getUid();
    void setUid(String uid);

    int getTypeUser();
    void setTypeUser(int type);

    int getIdDemandeStatus();
    void setIdDemandeStatus(int idDemandeStatus);

    String getPassword();
    void setPassword(String pass);

    void clearAll();
}
