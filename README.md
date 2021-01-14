# CRUD-framework
Framework CRUD

# Guide to use
```java
  ProxyFramework proxyFramework = new ProxyFramework();
  proxyFramework.connect(<database_type>, <database_url>, <user>, <password>, <database_name>);
  proxyFramework.form(<tabke_name>);

  /*
  Framework version 1.0 with membership features
  */
  FrameworkV1 frameworkV1 = new FrameworkV1(proxyFramework);
  frameworkV1.connect(<database_type>, <database_url>, <user>, <password>, <database_name>);
  frameworkV1.createMembership(); //create if not exists membership_users table in your database
  frameworkV1.form("membership_users");
 ```
 
 # Database types is supported
 - My SQL
 - MS SQL
 
 # Methods
 ## connect
 ```java
    /**
     * connect to database
     * @param _type
     * @param _url
     * @param _user
     * @param _password
     * @param _database
     */
    void connect(String _type, String _url, String _user, String _password, String _database);
 ```
 
 ## form
 ```java
    /**
     * show form of the table typed
     * @param _table
     */
    void form(String _table);
 ```
 
 ## createMembership (v 1.0)
 ```java
    /**
     * create if not exists membership_users table in your database
     */
    public void createMembership();
 ```
 
 ## validateMember (v 1.0)
 ```java
    /**
     * Authenticating user
     * @param _username
     * @param _password
     * @return true: user is a member in membership; false: user is not a member in membership
     */
    public boolean validateMember(String _username, String _password);
 ```
