package com.tennis.controller;

import java.io.File;
import java.io.FileFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tennis.broadcaster.ATP_2022;
import com.tennis.containers.Scene;
import com.tennis.model.*;
import com.tennis.service.TennisService;
import com.tennis.util.TennisFunctions;
import com.tennis.util.TennisUtil;

import net.sf.json.JSONObject;

@Controller
public class IndexController 
{
	@Autowired
	TennisService tennisService;
	
	public static String expiry_date = "2023-12-31";
	public static String current_date = "";
	public static String error_message = "";
	public static ATP_2022 this_ATP_2022;
	public static Match session_match = new Match();
	public static EventFile session_event = new EventFile();
	public static Configurations session_Configurations = new Configurations();
	
	List<Scene> session_selected_scenes = new ArrayList<Scene>();
	public static String session_selected_broadcaster;
	public static Socket session_socket;
	public static PrintWriter print_writer;
	
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model) throws JAXBException, IOException 
	{
		if(current_date == null || current_date.isEmpty()) {
			current_date = TennisFunctions.getOnlineCurrentDate();
		}
		model.addAttribute("session_viz_scenes", new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.SCENES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".via") && pathname.isFile();
		    }
		}));
		model.addAttribute("match_files", new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".xml") && pathname.isFile();
		    }
		}));
		
		
		if(new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CONFIGURATIONS_DIRECTORY + TennisUtil.OUTPUT_XML).exists()) {
			session_Configurations = (Configurations)JAXBContext.newInstance(
					Configurations.class).createUnmarshaller().unmarshal(
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CONFIGURATIONS_DIRECTORY 
					+ TennisUtil.OUTPUT_XML));
		} else {
			session_Configurations = new Configurations();
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CONFIGURATIONS_DIRECTORY + 
					TennisUtil.OUTPUT_XML));
		}
		
		model.addAttribute("session_Configurations",session_Configurations);
		
		return "initialise";
	
	}

	@RequestMapping(value = {"/output"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String outputPage(ModelMap model,
			@RequestParam(value = "select_broadcaster", required = false, defaultValue = "") String select_broadcaster,
			@RequestParam(value = "selectedMatch", required = false, defaultValue = "") String selectmatch,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") String vizPortNumber,
			@RequestParam(value = "vizScene", required = false, defaultValue = "") String vizScene) 
					throws UnknownHostException, IOException, JAXBException, IllegalAccessException, InvocationTargetException, ParseException 
	{
		if(current_date == null || current_date.isEmpty()) {
			
			model.addAttribute("error_message","You must be connected to the internet online");
			return "error";
		
		} else if(new SimpleDateFormat("yyyy-MM-dd").parse(expiry_date).before(new SimpleDateFormat("yyyy-MM-dd").parse(current_date))) {
			
			model.addAttribute("error_message","This software has expired");
			return "error";
			
		}else {
			this_ATP_2022 = new ATP_2022();
			session_Configurations.setBroadcaster(select_broadcaster);
			session_Configurations.setVizscene(vizScene);
			session_Configurations.setIpAddress(vizIPAddresss);
			if(!vizPortNumber.trim().isEmpty()) {
				session_Configurations.setPortNumber(Integer.valueOf(vizPortNumber));
				session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
				print_writer = new PrintWriter(session_socket.getOutputStream(), true);
			}
			
			session_selected_broadcaster = select_broadcaster;
			
			switch (session_selected_broadcaster.toUpperCase()) {
			case TennisUtil.ATP_2022:
				//session_selected_scenes.add(new Scene("/Default/ScoreBug-Single","FRONT_LAYER")); // Front layer
				session_selected_scenes.add(new Scene("","MIDDLE_LAYER"));
				break;
			}
			
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(TennisUtil.TENNIS_DIRECTORY + TennisUtil.CONFIGURATIONS_DIRECTORY + TennisUtil.OUTPUT_XML));
			
			model.addAttribute("session_match", session_match);
			model.addAttribute("session_selected_broadcaster", session_selected_broadcaster);
			model.addAttribute("session_port", vizPortNumber);
			model.addAttribute("session_selected_ip", vizIPAddresss);
			
			return "output";
		}
	}

	@RequestMapping(value = {"/processTennisProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processBadmintonProcedures(
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess)  
					throws IOException, IllegalAccessException, InvocationTargetException, JAXBException, InterruptedException, ParseException 
	{
		switch (whatToProcess.toUpperCase()) {
		
		default:
			switch (session_selected_broadcaster) {
			case TennisUtil.ATP_2022:
				System.out.println("index controller : " + whatToProcess.toUpperCase());
				System.out.println("index controller : " + valueToProcess.toUpperCase());
				this_ATP_2022.ProcessGraphicOption(whatToProcess, session_match, tennisService, print_writer, session_selected_scenes, valueToProcess);
				break;
			}
			return JSONObject.fromObject(session_match).toString();
		}
	}
}