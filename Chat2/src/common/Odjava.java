package common;

public class Odjava 
{
	String name;
	
	public Odjava() { }
	
	public Odjava(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Util.getXmlFromObject(this);
	}	
}
