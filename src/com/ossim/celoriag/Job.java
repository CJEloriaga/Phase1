package com.ossim.celoriag;

/*
 * @author Christian Eloriaga
 */

import java.util.List;

public class Job 
{
	int jobID, priority, numInst;
	List<String> iList;
	
	public Job() {}
	
	//Job object constructor
	public Job(int jobNum, int totalJobs, int pri, List<String> jobList)
	{
		jobID = jobNum;
		priority = pri;
		numInst = totalJobs;
		iList = jobList;
	}
	
	//returns job ID
	public int getID()
	{
		return jobID;
	}
	
	//returns Priority
	public int getPri()
	{
		return priority;
	}
	
	//returns number of jobs
	public int getJobs()
	{
		return numInst;
	}
	
	//returns list of actions
	public List<String> getList()
	{
		return iList;
	}
	
	public void setStartTime()
	{
		
	}
	
	public void setTerminateTime()
	{
		
	}
}
