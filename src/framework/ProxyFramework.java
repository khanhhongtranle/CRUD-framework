package framework;

public class ProxyFramework implements IFramework{
    private Framework realFramework = null;

    @Override
    public void connect(String _type, String _url, String _user, String _password, String _database) {
        if (this.realFramework == null){
            this.realFramework = new Framework();
        }
        this.realFramework.connect(_type, _url, _user, _password, _database);
    }

    @Override
    public void form(String _table) {
        if (this.realFramework == null){
            this.realFramework = new Framework();
        }
        this.realFramework.form(_table);
    }
}
