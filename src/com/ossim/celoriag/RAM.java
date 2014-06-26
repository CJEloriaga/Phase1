package com.ossim.celoriag;

import java.util.ArrayList;

/*
 * @author Christian Eloriaga
 */

public class RAM 
{
	public ArrayList<Job> jList;
	
	//Constructor for RAM
	public RAM()
	{
		jList = new ArrayList<Job>(0);
	}
	
	//returns ArrayList of Jobs
	public ArrayList<Job> getList()
	{
		return jList;
	}
	
	//Prints out what's in RAM to text file
	public void printRAM() throws Exception
	{
		for(int i = 0; i < jList.size(); ++i)
		{
			Job tempJob = jList.get(i);
			System.out.println("ID: " + tempJob.getID());
			System.out.println("Priority: " + tempJob.getPri());
			System.out.println("Number of Instructions: " + tempJob.getJobs());
			for(int j = 0; j < tempJob.getJobs(); ++j)
			{
				System.out.println(tempJob.iList.get(j));
			}
		}
	}
}
