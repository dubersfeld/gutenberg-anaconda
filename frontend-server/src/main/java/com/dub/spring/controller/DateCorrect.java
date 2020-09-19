package com.dub.spring.controller;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCorrect {
	
	private static SimpleDateFormat sdf 
							= new SimpleDateFormat("yyyy-MM-dd Z");
	
	public static Date correctDate(Date date) throws ParseException {
		
		StringBuffer buf = new StringBuffer();
		
		sdf.format(date, buf, new FieldPosition(0));
				
		buf.replace(11, buf.length(), "+0000");
						
		return sdf.parse(buf.toString());
	}
}
