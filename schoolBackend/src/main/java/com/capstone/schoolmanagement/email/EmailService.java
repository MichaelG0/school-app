package com.capstone.schoolmanagement.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;

	@Async
	public void send(String to, String body) {
		try {
			MimeMessage mimeMsg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, "utf-8");
			helper.setFrom("mars_uni@gmail.com");
			helper.setTo(to);
			helper.setSubject("Enrol to our course");
			helper.setText(body, true);
			mailSender.send(mimeMsg);
		} catch (MessagingException e) {
			log.error("Email delivery failed", e);
			throw new IllegalStateException("Email delivery failed");
		}
	}
}
