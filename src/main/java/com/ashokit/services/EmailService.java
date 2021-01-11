package com.ashokit.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ashokit.entity.User;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;

	public Boolean sendMail(String subject, String body, String to) {
		
		Boolean isSent = false;
		
		try {
			
			MimeMessage msg = sender.createMimeMessage();
			
			msg.setSubject(subject);
			
			msg.addRecipient(RecipientType.TO, new InternetAddress(to));
			
			msg.setContent(body, "text/html");
			
			sender.send(msg);
			
			isSent = true;

		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return isSent;
	}

	public String createMailBody(User user) {
		
		String fileName="Unlcok-Account-Email-Body-Template.txt";
		List<String> replacedLines;
		String mailBoday="";
		
		try {
			
			Path path= Paths.get(fileName);
			Stream<String> lines=Files.lines(path);
			replacedLines=lines.map(line->line.replace(" {FNAME}", user.getFirstName())
					  .replace("{LNAME}", user.getLastName())
					  .replace("{TEMP-PWD}", user.getPassword())
					  .replace("{EMAIL}", user.getEmail()))
					  .collect(Collectors.toList());
			mailBoday = String.join("", replacedLines);
			lines.close();
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
				return mailBoday;
	}

}
