package com.xiaojun.spacewar.level;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LevelHandler extends DefaultHandler {
	
	private WaveList waveList; 
	private Wave tempWave;
	public boolean currentElement = false;  //±êÖ¾Î»
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		this.waveList = new WaveList();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		currentElement = true;

		if (localName.equals("wave")) {
			tempWave = new Wave();
		} else if (localName.equals("enemy")) {
			String attr = attributes.getValue("type");
			tempWave.setType(attr);
		}

	}

//	@Override
//	public void endElement(String uri, String localName, String qName)
//			throws SAXException {
//
//		currentElement = false;
//
//		if (localName.equalsIgnoreCase("enemy"))
//			temp.setCount(Integer.parseInt(currentValue));
//		if (localName.equalsIgnoreCase("wave"))
//			wavesList.addWave(temp);
//
//	}
//
//	@Override
//	public void characters(char[] ch, int start, int length)
//			throws SAXException {
//
//		if (currentElement) {
//			currentValue = new String(ch, start, length);
//			currentElement = false;
//		}
//
//	}
}
