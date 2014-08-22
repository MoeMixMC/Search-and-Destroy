package minefuse.utils;

public class MySQLInfo {

	private String address;
	private String database;
	private String username;
	private String password;
	private String port;
	
	public MySQLInfo(String address, String database, String username, String password, String port){
		this.address =   address;
		this.database =  database;
		this.username =  username;
		this.password =  password;
		this.port =      port;
	}

	public MySQLInfo(){
		this.address =   "";
		this.database =  "";
		this.username =  "";
		this.password =  "";
		this.port =      "";
	}
	
	public String getAddress() {
		return address;
	}

	public String getDatabase() {
		return database;
	}

	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}

	public String getPort() {
		return port;
	}
}
