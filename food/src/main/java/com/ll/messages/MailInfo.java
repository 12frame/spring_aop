package com.ll.messages;

import lombok.Data;

/**
 * @author root
 *
 */
@Data
public class MailInfo {

	private String to;
	private String subject;
	private String body;
}
