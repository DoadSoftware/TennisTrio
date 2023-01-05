package com.tennis.broadcaster;

import java.io.PrintWriter;
import java.util.List;

import com.tennis.containers.Scene;
import com.tennis.model.Match;
import com.tennis.service.TennisService;

public class ATP_2022 extends Scene{
	
	//private String slashOrDash = "-";
	//private String logo_path = "D:\\DOAD_In_House_Everest\\Everest_Sports\\Everest_GBPL\\Logos\\"
	
	public ATP_2022() {
		super();
	}
	
	public Object ProcessGraphicOption(String whatToProcess, Match match, TennisService tennisService,PrintWriter print_writer, List<Scene> scenes, String valueToProcess) {
		
		switch(whatToProcess.toUpperCase()) {
		case "TRIO_ORDER_OF_PLAY":
			System.out.println("in ATP : " + whatToProcess.toUpperCase());
			trioOrderOfPlay(print_writer, valueToProcess, match);
			break;
			
		}
		
		return null;
	}
	
	public void trioOrderOfPlay(PrintWriter print_writer,String value,Match match){
		
	}
	
	public void DoadWriteToTrio(PrintWriter print_writer,String sendCommand) {
		//print_writer.println(sendCommand + char(13) + "/0");
		
	}

}