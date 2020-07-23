package common;

public class StatusPrijave 
{
	boolean prijaveJeUspesna = false;
	String poruka;
	
	public StatusPrijave() { }

	public StatusPrijave(boolean prijaveJeUspesna, String poruka) 
	{
		super();
		this.prijaveJeUspesna = prijaveJeUspesna;
		this.poruka = poruka;
	}
	
	public StatusPrijave(boolean prijaveJeUspesna) 
	{
		super();
		this.prijaveJeUspesna = prijaveJeUspesna;
	}

	public boolean isPrijaveJeUspesna() {
		return prijaveJeUspesna;
	}

	public void setPrijaveJeUspesna(boolean prijaveJeUspesna) {
		this.prijaveJeUspesna = prijaveJeUspesna;
	}

	public String getPoruka() {
		return poruka;
	}

	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}
	
	@Override
	public String toString() 
	{
		return common.Util.getXmlFromObject(this);
	}
}
