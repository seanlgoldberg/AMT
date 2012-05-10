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
import com.amazonaws.mturk.requester.HIT;
import com.amazonaws.mturk.requester.QualificationType;
import com.amazonaws.mturk.requester.QualificationTypeStatus;

import java.io.*;
import java.util.Scanner;

/**
 * The MTurk Hello World sample application creates a simple HIT via the Mechanical Turk 
 * Java SDK. mturk.properties must be found in the current file path.
 */
public class MTurkHelloWorld2 {

  private RequesterService service;

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
  public MTurkHelloWorld2() {
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
   * 
   */
  public void createHelloWorld() {
    try {

      // The createHIT method is called using a convenience static method of
      // RequesterService.getBasicFreeTextQuestion that generates the QAP for
      // the HIT.
      
    	String question = MTurkHelloWorld2.getXML("testQ.xml");
    	String answerKey = MTurkHelloWorld2.getXML("answer.xml");
    	
    	System.out.println(question);
    	
    	HIT hit = service.createHIT(
              title,
              description,
              reward,
              question,
              numAssignments);
    	
    	
    	QualificationType qual = service.createQualificationType("UFDSR2", "UF, DSR", description);
    	qual.setTest(question);
    	qual.setAnswerKey(answerKey);
    	
    	
      System.out.println("Created HIT: " + hit.getHITId());

      System.out.println("You may see your HIT with HITTypeId '" 
          + hit.getHITTypeId() + "' here: ");
      System.out.println(service.getWebsiteURL() 
          + "/mturk/preview?groupId=" + qual.getQualificationTypeId());

    } catch (ServiceException e) {
      System.err.println(e.getLocalizedMessage());
    }
  }

  /**
   * Main method
   * 
   * @param args
   */
  public static void main(String[] args) {

    MTurkHelloWorld2 app = new MTurkHelloWorld2();

    if (app.hasEnoughFund()) {
      app.createHelloWorld();
      System.out.println("Success.");
    } else {
      System.out.println("You do not have enough funds to create the HIT.");
    }
  }
}
