package com.ossim.celoriag;

import java.util.*;

public class CPU 
{
	private int [] regis = {1,3,5,7,9};
	private ArrayList<PCB> rQueue = new ArrayList<PCB>(); 
	private ArrayList<PCB> wQueue = new ArrayList<PCB>();
	private ArrayList<PCB> ioQueue = new ArrayList<PCB>();
	
	public CPU(){}
	
	public void execute()
	{
		//basic local variables
		PCB tempPCB;
		Job tempJob = new Job();
		int reg1, reg2, wildcard;
		int lineNum = 0;
		String inst, qString;
		qString = "";
		
		//HashMap to convert characters to integers
		final Map<Character, Integer> map;
		map = new HashMap<Character, Integer>();  
        map.put('A', 0);
        map.put('B', 1);
        map.put('C', 2);
        map.put('D', 3);
		
        //While there are jobs in queue
		while(! rQueue.isEmpty())
		{
			/*
			//decrement wait time for wait queue
			for(int i = 0; i < wQueue.size(); ++i)
			{
				wQueue.get(i).decrementW();
			}
			
			//decrement IO time for IO;
			for(int i = 0; i < ioQueue.size(); ++i)
			{
				ioQueue.get(i).decrementIO();
			}
			
			//wQueue takes priority if possible
			if(! wQueue.isEmpty())
			{
				tempPCB = wQueue.get(0);
				if(tempPCB.getWait() == 0)
				{
					tempJob = tempPCB.getJob();
					lineNum = tempPCB.getLine();
				}
			}
			*/
			//else // take the first job in rQueue
			//{
				tempPCB = rQueue.get(0);
				rQueue.remove(0);
				tempJob = tempPCB.getJob();
			//}
			
			//execute instructions
			for(int i = lineNum; i < tempJob.getList().size(); ++i)
			{
				qString = tempJob.iList.get(0);
				qString = qString.replaceAll("\\s", "");
				String [] splitter = qString.split(",");
				
				//if LineNum doesn't already exist, initialize to 0
				if(lineNum == 0);
				{
					lineNum = Integer.parseInt(splitter[0]);
				}
				
				//read in from splitter into variables for instructions/regs
				inst = splitter[1];
				System.out.println(inst);
				reg1 = map.get(splitter[2].charAt(0));
				reg2 = map.get(splitter[3].charAt(0));
				wildcard = Integer.parseInt(splitter[4]);
				
				//check for instruction, act accordingly
				if(inst == "add") { add(reg1, reg2, wildcard); }
				else if(inst == "sub") { sub(reg1, reg2, wildcard); }
				else if(inst == "mul") { mul(reg1, reg2, wildcard); }
				else if(inst == "div") { div(reg1, reg2, wildcard); }
				else if(inst == "rcl") { rcl(reg1, reg2, wildcard); }
				
				/*else if(inst == "_wt") 
				{ 
					_wt(tempPCB, lineNum, wildcard); 
					break;
				}
				
				else if(inst == "_wr") 
				{
					_wr(tempPCB, lineNum, wildcard);
					break;
				}
				else if(inst == "_rd") 
				{
					_rd(tempPCB, lineNum, wildcard);
					break;
				}
				else if(inst == "sto") 
				{
					sto(reg1, reg2);
					break;
				}*/
				
				if(lineNum == tempJob.getList().size())
				{
					System.out.println("Job: " + tempJob.getID());
					System.out.println("Register A: " + regis[0]);
					System.out.println("Register B: " + regis[1]);
					System.out.println("Register C: " + regis[2]);
					System.out.println("Register D: " + regis[3]);
					System.out.println("Accumulator: " + regis[4]);
				}
				++lineNum;
			}
		}
	}
	
	//methods for add/sub/mul/div/rcl/sto/_wt/_rd/_wr
	public void add(int r1, int r2, int acc) { regis[4] += regis[r1] + regis[r2]; }
	public void sub(int r1, int r2, int acc) { regis[4] += regis[r1] - regis[r2]; }
	public void mul(int r1, int r2, int acc) { regis[4] += regis[r1] * regis[r2]; }
	public void div(int r1, int r2, int acc) { regis[4] += regis[r1] / regis[r2]; }
	public void rcl(int r1, int r2, int acc) { regis[4] += regis[r1] << regis[r2]; }
	
	public void _wt(PCB p, int line, int wait)
	{
		p.setLine(line);
		p.setWait(wait);
		wQueue.add(p);
	}
	
	public void _wr(PCB p, int line, int io)
	{
		p.setLine(line);
		p.setIO(io);
		ioQueue.add(p);
	}
	
	public void _rd(PCB p, int line, int io)
	{
		p.setLine(line);
		p.setIO(io);
		ioQueue.add(p);
	}
	
	public void sto(int r1, int r2)
	{
		
	}
	
	//initialize the rQueue with all jobs in RAM
	public void initPCBQ(PCB p)
	{
		rQueue.add(p);
	}
	
	//test to check which values are in the rQueue
	public void printRQ()
	{
		for(int i = 0; i < rQueue.size(); ++i)
		{
			PCB tempPCB = rQueue.get(i);
			Job tempJob = tempPCB.getJob();
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