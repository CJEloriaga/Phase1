package com.ossim.celoriag;

/*
 * @author Christian Eloriaga
 */

import java.io.*;
import java.util.*;

public class HDD
{	
	ArrayList<Job> jList;
	
	//HDD constructor
	public HDD(ArrayList<Job> jobList)
	{
		jList = jobList;
	}
	
	//secondary constructor
	public HDD(){}
	
	//returns ArrayList of Jobs
	public ArrayList<Job> getList()
	{
		return jList;
	}
	
	//sets HDD to input .txt file
	public void load_to_disk(HDD d) throws IOException, NumberFormatException 
	{
		String bufRead;
		BufferedReader in;
		
		//declares the attributes of the job
		int id = 0;
		int priority, jobSize;
		ArrayList<Job> newJList = new ArrayList<Job>();
		
		in = new BufferedReader(new FileReader("resources/ugradPart1.txt"));
		
		//Method using BufferedReader to parse for items.
		while ((bufRead = in.readLine()) != null) 
		{
			bufRead = bufRead.replaceAll("\\s", "");
			ArrayList<String> job = new ArrayList<String>();
	        if(bufRead.contains("Job"))
	        {
		        String[] splitter = bufRead.split(",");
		        
		        jobSize = Integer.parseInt(splitter[1]);
		        priority = Integer.parseInt(splitter[2]);
		        
		        //read into job instruction list
		        for(int i = 0; i < jobSize; ++i)
		        {
		        	bufRead = in.readLine();
		        	job.add(bufRead);
		        }
		        
		        //add job to job ArrayList
		        Job j = new Job(id, jobSize, priority, job);
		        newJList.add(j);
		        id++;
	        }
	        
	    }
		
		in.close();
		
		d.jList = newJList;
	}
}
