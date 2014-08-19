package com.ossim.celoriag;

import java.util.*;

/*
 * @author Christian Eloriaga
 */

public class Core //extends CPU
{
	//variables
	ArrayList<PCB> wQueue, ioQueue;
	Boolean isActive = false;
	Boolean isWait = false;
	Boolean isIO = false;
	String iString, instruction;
	PCB activePCB;
	Job activeJob;
	int progCount;
	int [] regis = {1,3,5,7,9};
	int [] base = {1,3,5,7,9};
	
	public Core() {}
	
	//run a single cycle
	public void run()
	{
		//temp register variables
		int reg1, reg2, wildcard;

		final Map<Character, Integer> map;
		map = new HashMap<Character, Integer>();  
	    map.put('A', 0);
	    map.put('B', 1);
	    map.put('C', 2);
	    map.put('D', 3);
		
		//create string splitter to read variables from
		//If the job has reached its end, print registers
		if(progCount == activeJob.getList().size())
		{
			System.out.println("Job: " + activeJob.getID());
			System.out.println("Register A: " + regis[0]);
			System.out.println("Register B: " + regis[1]);
			System.out.println("Register C: " + regis[2]);
			System.out.println("Register D: " + regis[3]);
			System.out.println("Accumulator: " + regis[4]);
			System.out.println("Time Elapsed: " + activePCB.calcRunT());
			//System.out.println("Total Wait: " + activePCB.totalWait());
			System.out.println("");
			isActive = false;
			return;
		}
		
		iString = activeJob.iList.get(progCount);
		iString = iString.replaceAll("\\s", "");
		String [] splitter = iString.split(",");
		
		//read instructions into variables
		instruction = splitter[1];
		//System.out.println("Job: " + activeJob.getID());
		//System.out.println("Line Num: " + progCount);
		reg1 = map.get(splitter[2].charAt(0));
		reg2 = map.get(splitter[3].charAt(0));
		wildcard = Integer.parseInt(splitter[4]);
		
		//check for instruction, act accordingly
		if(instruction.equals("add")) { add(reg1, reg2, wildcard); }
		else if(instruction.equals("sub")) { sub(reg1, reg2, wildcard); }
		else if(instruction.equals("mul")) { mul(reg1, reg2, wildcard); }
		else if(instruction.equals("div")) { div(reg1, reg2, wildcard); }
		else if(instruction.equals("rcl")) { rcl(reg1, reg2, wildcard); }
		else if(instruction.equals("sto")) { sto(wildcard); }
		else if(instruction.equals("_wt")) 
		{ 
			isActive = false;
			_wt(activePCB, progCount, wildcard, regis); 
		}
		else if(instruction.equals("_wr")) 
		{
			isActive = false;
			_wr(activePCB, progCount, wildcard, regis);
		}
		else if(instruction.equals("_rd")) 
		{
			isActive = false;
			_rd(activePCB, progCount, wildcard, regis);
		}
		
		progCount++;
	}
	
	//check if Core is active
	public Boolean isActive()
	{
		return isActive;
	}
	
	//fetch PCB from r/w/IO queues
	public void fetch(PCB p)
	{
		isActive = true;
		activePCB = p;
		activeJob = p.getJob();
		if(p.getLine() != 0)
		{
			progCount = p.getLine() + 1;
		}
		else
		{
			progCount = p.getLine();
		}
		regis = p.getReg();
	}
	
	//reset core after finishing
	public void reset()
	{
		regis = base;
	}
	
	//methods for add/sub/mul/div/rcl/sto/_wt/_rd/_wr
	public void add(int r1, int r2, int acc) { regis[4] += regis[r1] + regis[r2]; }
	public void sub(int r1, int r2, int acc) { regis[4] += regis[r1] - regis[r2]; }
	public void mul(int r1, int r2, int acc) { regis[4] += regis[r1] * regis[r2]; }
	public void div(int r1, int r2, int acc) { regis[4] += regis[r2] / regis[r1]; }
	public void rcl(int r1, int r2, int acc) { regis[r1] = regis[4]; }
	public void sto(int wc) { regis[4] = wc; }
	public void _wt(PCB p, int line, int wait, int [] r)
	{
		p.setLine(line);
		p.setWait(wait);
		p.setWaitT(System.currentTimeMillis());
		p.setReg(r);
		//wQueue.add(p);
		isActive = false;
		isWait = true;
	}
	public void _wr(PCB p, int line, int io, int [] r)
	{
		p.setLine(line);
		p.setIO(io);
		p.setReg(r);
		isActive = false;
		isIO = true;
	}
	public void _rd(PCB p, int line, int io, int [] r)
	{
		p.setLine(line);
		p.setIO(io);
		p.setReg(r);
		isActive = false;
		isIO = true;
	}
	
	public PCB retPCB()
	{
		return activePCB;
	}
	
	public Boolean hasWait()
	{
		return isWait;
	}
	
	public void resetWait()
	{
		isWait = false;
	}
	
	public Boolean hasIO()
	{
		return isIO;
	}
	
	public void resetIO()
	{
		isIO = false;
	}
}
