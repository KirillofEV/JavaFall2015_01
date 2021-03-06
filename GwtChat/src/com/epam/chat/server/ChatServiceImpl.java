package com.epam.chat.server;

import java.util.List;

import javax.servlet.ServletException;

import com.epam.chat.client.ChatService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ChatServiceImpl extends RemoteServiceServlet
		implements ChatService {

	private Chat chat = new Chat();

	@Override
	public void init() throws ServletException {
		super.init();
		new Thread(chat).start();
	}
	
	@Override
	public List<String> sendMessage(long uuid, String message) throws IllegalArgumentException {
		List<String> responds = chat.process(uuid, message);
		return responds;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
