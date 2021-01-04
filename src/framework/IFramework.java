package framework;

public interface IFramework {
    //void membership();

    /**
     * connect to database
     * @param _type
     * @param _url
     * @param _user
     * @param _password
     * @param _database
     */
    void connect(String _type, String _url, String _user, String _password, String _database);

    /**
     * show form of the table typed
     * @param _table
     */
    void form(String _table);

    //void create();

    //void edit();
}