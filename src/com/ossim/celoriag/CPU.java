package com.ossim.celoriag;

/*
 * @author Christian Eloriaga
 */

import java.util.*;

public class CPU 
{
	private ArrayList<PCB> rQueue = new ArrayList<PCB>(); 
	private ArrayList<PCB> wQueue = new ArrayList<PCB>();
	private ArrayList<PCB> ioQueue = new ArrayList<PCB>();
	private Core c1 = new Core();
	private Core c2 = new Core();
	private Core c3 = new Core();
	private Core c4 = new Core();
	private Core c5 = new Core();
	private Core c6 = new Core();
	private Core c7 = new Core();
	private Core c8 = new Core();
	
	private ArrayList<Core> coreList = new ArrayList<Core>();
	
	
	public CPU() 
	{
		coreList.add(c1);
		coreList.add(c2);
		coreList.add(c3);
		coreList.add(c4);
		coreList.add(c5);
		coreList.add(c6);
		coreList.add(c7);
		coreList.add(c8);
	}
	
	public void stablizeQueues()
	{
		for(int i = 0; i < ioQueue.size(); i++)
		{
			if(ioQueue.contains(ioQueue.get(i)))
			{
				for(int j = i+1; j < ioQueue.size(); j++)
				{
					if(ioQueue.get(j) == ioQueue.get(i))
					{
						ioQueue.remove(j);
						break;
					}
				}
			}
		}
	}
	
	public void execute()
	{
		long runT = System.currentTimeMillis();
		
		for(int i = 0; i < rQueue.size(); ++i)
		{
			rQueue.get(i).setRunT(System.currentTimeMillis());
		}
        //While there are jobs in queue
		while(! rQueue.isEmpty() || ! wQueue.isEmpty() || ! ioQueue.isEmpty()
				|| c1.isActive == true || c2.isActive == true || c3.isActive == true ||
				c4.isActive == true || c5.isActive == true || c6.isActive == true ||
				c7.isActive == true || c8.isActive == true)
		{
			stablizeQueues();
			//decrement counters on wQueue/ioQueue
			for(int i = 0; i < wQueue.size(); ++i) 
			{ 
				if(wQueue.get(i).getWait() > 0)
				{
					wQueue.get(i).decrementW();
				}
			}
			
			//decrement counter on io. If 0, move to wQueue
			for(int i = 0; i < ioQueue.size(); ++i) 
			{ 
				if(ioQueue.get(i).getIO() > 0)
				{
					ioQueue.get(i).decrementIO();
					
					if(ioQueue.get(i).getIO() == 0)
					{
						ioQueue.get(i).setWaitT(System.currentTimeMillis());
						wQueue.add(ioQueue.get(i));
						ioQueue.remove(i);
					}
				}
			}
			
			//wQueue takes priority if possible
			if(! wQueue.isEmpty())
			{
				for(int i = 0; i < wQueue.size(); ++i)
				{
					if(wQueue.get(i).getWait() <= 0)
					{
						for(int j = 0; j < coreList.size()/2; ++j)
						{
							if(coreList.get(j).isActive() == false)
							{
								//wQueue.get(j).averageTime(System.currentTimeMillis());
								coreList.get(j).fetch(wQueue.get(j));
								wQueue.remove(i);
							}
						}
					}
					else 
						wQueue.get(i).decrementW();
				}
			}
			else if(! rQueue.isEmpty())//take the first job in rQueue if necessary
			{
				for(int i = 0; i < coreList.size()/2; ++i)
				{
					if(coreList.get(i).isActive() == false)
					{
						coreList.get(i).fetch(rQueue.get(0));
						rQueue.remove(0);
					}
				}
			}
			
			//execute instructions
			for(int i = 0; i < coreList.size()/2; ++i)
			{
				coreList.get(i).run();
				
				if(coreList.get(i).hasWait() == true)
				{
					if(ioQueue.contains(coreList.get(i).retPCB()))
					{
						ioQueue.remove(coreList.get(i).retPCB());
					}
					wQueue.add(coreList.get(i).retPCB());
					coreList.get(i).resetWait();
				}
				else if(coreList.get(i).hasIO() == true)
				{
					if(wQueue.contains(c1.retPCB()))
					{
						wQueue.remove(c1.retPCB());
					}
					ioQueue.add(c1.retPCB());
					c1.resetIO();
				}
			}
		}
		
		//print out total time to run all programs
		System.out.println("Run Time: " + (System.currentTimeMillis() - runT));

		for(int i = 0; i < coreList.size(); ++i)
		{
			coreList.get(i).reset();
		}
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
	
	//test to check which values are in the wQueue
	public void printWQ()
	{
		for(int i = 0; i < wQueue.size(); ++i)
		{
			Job tempJob = wQueue.get(i).getJob();
			System.out.println("ID: " + tempJob.getID());
			/*System.out.println("Priority: " + tempJob.getPri());
			System.out.println("Number of Instructions: " + tempJob.getJobs());
			for(int j = 0; j < tempJob.getJobs(); ++j)
			{
				System.out.println(tempJob.iList.get(j));
			}*/
		}
	}
}