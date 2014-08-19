package com.ossim.celoriag;

import java.util.ArrayList;

public class PCB 
{
	Job pcbJob;
	Scheduler sts = new Scheduler();
	Boolean isDone, isRunning;
	private long timeElapsed, timeWaiting;
	private ArrayList<Integer> avgTime = new ArrayList<Integer>();
	private int waitTime, io;
	private int currentLine = 0;
	private int [] regis = {1,3,5,7,9};
	
	public PCB(Job j)
	{
		pcbJob = j;
	}
	
	public PCB(){}
	
	//methods for getting cycling variables
	public int getWait() { return waitTime; }
	public int getLine() { return currentLine; } 
	public int getIO() { return io; }
	
	//methods for setting cycling variables
	public void setWait(int i) { waitTime = i; }
	public void setLine(int i) { currentLine = i; }
	public void setIO(int i) { io = i; }
	
	//methods for decrementing cycling variables
	public void decrementW() { waitTime = waitTime - 1; }
	public void decrementIO() { io = io - 1; }
	
	//methods for getting/setting job
	public Job getJob() { return pcbJob; }
	public void setJob(Job j) { pcbJob = j; }
	
	//methods for getting/setting registers
	public void setReg(int [] r) { regis = r; }
	public int [] getReg() { return regis; }
	
	//methods for setting times
	public void setRunT(long i) { timeElapsed = i; }
	public void setWaitT(long i) { timeWaiting = i; }
	
	//adding times to wait time AList
	public void averageTime(long i)
	{ 
		avgTime.add((int)System.currentTimeMillis() - (int)timeWaiting); 
	}
	
	//total time spent waiting
	public int totalWait()
	{
		int i = 0;
		for(int j = 0; j < avgTime.size(); ++j)
		{
			i += avgTime.get(i);
		}
		
		return i;
	}
	
	//calculating avg wait time
	public int calcAvgTime()
	{
		int i = 0;
		for(int j = 0; j < avgTime.size(); ++j)
		{
			i += avgTime.get(j);
		}
		
		return i / avgTime.size();
	}
	
	public long calcRunT()
	{
		timeElapsed = System.currentTimeMillis() - timeElapsed;
		return timeElapsed;
	}
}
