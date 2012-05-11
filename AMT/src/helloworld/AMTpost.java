/*
 * Copyright 2007-2008 Amazon Technologies, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * 
 * http://aws.amazon.com/apache2.0
 * 
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */ 


package helloworld;

import com.amazonaws.mturk.service.axis.RequesterService;

import com.amazonaws.mturk.service.exception.ServiceException;
import com.amazonaws.mturk.util.PropertiesClientConfig;
import com.amazonaws.mturk.requester.*;
import com.amazonaws.mturk.service.axis.*;
import com.amazonaws.mturk.addon.*;

import java.io.*;
import java.util.Scanner;
import java.lang.*;

/**
 * The MTurk Hello World sample application creates a simple HIT via the Mechanical Turk 
 * Java SDK. mturk.properties must be found in the current file path.
 */
public class AMTpost {

  private int NUM_QUESTIONS = 1;	
	
  private RequesterService service;
  private RequesterServiceRaw serviceRaw;

  // Defining the attributes of the HIT to be created
  private String title = "Answer a question";
  private String description = 
    "This is a HIT created by the Mechanical Turk SDK.  Please answer the provided question.";
  private int numAssignments = 1;
  private double reward = 0.05;

  
  //Defining the attributes of the Pre-Qualification
  private String qualTitle = "Answer a Pre-Qualification Question";
  private String qualDescription = "This is a pre-qualification question.";
  
  /**
   * Constructor
   * 
   */
  public AMTpost() {
    service = new RequesterService(new PropertiesClientConfig("../mturk.properties"));
  }

  /**
   * Check if there are enough funds in your account in order to create the HIT
   * on Mechanical Turk
   * 
   * @return true if there are sufficient funds. False if not.
   */
  public boolean hasEnoughFund() {
    double balance = service.getAccountBalance();
    System.out.println("Got account balance: " + RequesterService.formatCurrency(balance));
    return balance > 0;
  }
  
  public static String getXML(String file) {
	  try{
		  FileInputStream fstream = new FileInputStream(file);
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  
		  String Q = "";
		  String Qline;
		  while ((Qline = br.readLine()) != null) {
			  Q += Qline;
		  }
		 in.close();
		 return Q;
	  }
	 catch (Exception e) {
		 System.err.println("Error: " + e.getMessage());
	 }
	 return "Error";
}
	  

  /**
   * Creates the simple HIT.
 * @throws Exception 
   * 
   */
  public void postQuestions() throws Exception {
    try {

        for(int i=1; i<=NUM_QUESTIONS; i++) {
      
        	String question = AMTpost.getXML("Q" + i + ".xml");
        	String answerKey = AMTpost.getXML("answer.xml");
    	
        	//System.out.println(question);
        	//QualificationType qual = service.createQualificationType("UFDSR2", "UF, DSR", qualDescription,
        	//															QualificationTypeStatus.Active,
        	//															(long) 0,
    		//															question,
        	//															answerKey,
        	//															(long) 60*60, false, null);
    	
        	QualificationRequirement qualRec = new QualificationRequirement();
        	//String ID = qual.getQualificationTypeId();
        	String ID = "2RXIXKO2L92HBDCXDRUYRCCF0ESEDD";
        	qualRec.setQualificationTypeId(ID);
        	qualRec.setComparator(Comparator.Exists);
    	
        	QualificationRequirement[] qualRecArray = new QualificationRequirement[1];
        	qualRecArray[0] = qualRec;
    	
        	HIT hit = service.createHIT(
    			null,
                title,
                description,
                null,
                question,
                reward,
                (long) 60*60,
                (long) 60*60*24*15,
                (long) 60*60*24*3,
                1,
                null,
                qualRecArray,
                null);
    	
        	System.out.println("Created HIT: " + hit.getHITId());

        	System.out.println("You may see your HIT with HITTypeId '" 
        			+ hit.getHITTypeId() + "' here: ");
        	System.out.println(service.getWebsiteURL() 
        			+ "/mturk/preview?groupId=" + hit.getHITTypeId());
        }

    } catch (ServiceException e) {
      System.err.println(e.getLocalizedMessage());
    }
  }

  /**
   * Main method
   * 
   * @param args
 * @throws Exception 
   */
  public static void main(String[] args) throws Exception {

    AMTpost app = new AMTpost();

    if (app.hasEnoughFund()) {
      app.postQuestions();
      System.out.println("Success.");
    } else {
      System.out.println("You do not have enough funds to create the HIT.");
    }
  }
}
