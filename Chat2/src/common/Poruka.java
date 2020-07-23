package common;

public class Poruka 
{
	boolean privatna = false;
	String name;
	String sadrzajPoruke;
	
	public Poruka() { }
	
	public Poruka(boolean privatna, String name, String sadrzajPoruke) 
	{
		super();
		this.privatna = privatna;
		this.name = name;
		this.sadrzajPoruke = sadrzajPoruke;
	}

	public boolean isPrivatna() {
		return privatna;
	}

	public void setPrivatna(boolean privatna) {
		this.privatna = privatna;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSadrzajPoruke() {
		return sadrzajPoruke;
	}

	public void setSadrzajPoruke(String sadrzajPoruke) {
		this.sadrzajPoruke = sadrzajPoruke;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Util.getXmlFromObject(this);
	}
}
