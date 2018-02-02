package fantasy.utility;


import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import fantasy.model.Team;

public class Marshaller {

	private Team unmarshallTeam(String xml){
		try {
			JAXBContext jaxBContext = JAXBContext.newInstance(Team.class);
			Unmarshaller unmarshaller = jaxBContext.createUnmarshaller();
			return (Team) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
		} catch (JAXBException e) {
			System.out.println("[ERROR]\tException is: " + e);
			return null;
		}
	}
	
	private String marshallTeam(Team team){
		String xml = null;
		try { 
			StringWriter stringWriter = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(Team.class);
		    context.createMarshaller().marshal(team, stringWriter);
		    xml = stringWriter.toString();
		} catch (JAXBException e) {
			System.out.println("[ERROR]\tException is: " + e);
		}
		return xml;
	}
}
