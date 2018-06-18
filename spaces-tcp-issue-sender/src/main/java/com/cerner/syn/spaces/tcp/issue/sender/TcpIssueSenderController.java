package com.cerner.syn.spaces.tcp.issue.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cerner.syn.spaces.tcp.issue.sender.props.MLLP;
import com.cerner.syn.spaces.tcp.issue.sender.service.Sender;

@Controller
public class TcpIssueSenderController {
	private static final Logger logger = LoggerFactory.getLogger(TcpIssueSenderController.class);
	
	@Autowired
    Sender sender;

	@Autowired
    MLLP mllp;
	
	@RequestMapping(method=RequestMethod.GET)
	public String anyGET(Model model) {
		try {
			sender.trySend(mllp.getStartBytes(), mllp.getPayload(), mllp.getEndBytes());
			model.addAttribute("result", "Success!");
		} catch (Throwable t) 		{
			logger.error("Error Sending!", t);
			model.addAttribute("result", t.toString());
		}

		model.addAttribute("mllp", mllp);
		return "page";
	}
}
