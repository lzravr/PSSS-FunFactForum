package common;

public class Prijava 
{
	String name;
	String password;
	
	public Prijava(){}

	public Prijava(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() 
	{
		return common.Util.getXmlFromObject(this);
	}
}
