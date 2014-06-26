package com.ossim.celoriag;

public class PCB 
{
	Job pcbJob;
	Scheduler sts = new Scheduler();
	Boolean isDone, isRunning;
	int timeElapsed, timeWaiting, averageTime, waitTime, currentLine, io;
	
	public PCB(Job j)
	{
		pcbJob = j;
	}
	
	public PCB(){}
	
	public int getWait() { return waitTime; }
	public int getLine() { return currentLine; } 
	public int getIO() { return io; }
	
	public void setWait(int i) { waitTime = i; }
	public void setLine(int i) { currentLine = i; }
	public void setIO(int i) { io = i; }
	
	public void decrementW()
	{
		waitTime = waitTime - 1;
	}
	
	public void decrementIO()
	{
		io = io - 1;
	}
	
	public void save(PCB p)
	{
		
	}
	
	public Job getJob()
	{
		return pcbJob;
	}
	
	public void setJob(Job j)
	{
		pcbJob = j;
	}
}
