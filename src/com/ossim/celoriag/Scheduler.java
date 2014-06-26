package com.ossim.celoriag;

/*
 * @author Christian Eloriaga
 */

public class Scheduler 
{	
	//private PCB p = new PCB();
	public Scheduler(){}
	
	//orders according to priority
	public void OrderPriority(HDD d, RAM r)
	{
		r.jList.clear();
		Job tempJ, tempK;
		for(int i = 0; i < d.jList.size(); ++i)
		{
			tempJ = d.jList.get(i);
			if(r.jList.isEmpty())
			{
				r.jList.add(d.jList.get(i));
			}
			for(int j = 0; j < r.jList.size(); ++j)
			{
				tempK = r.jList.get(j);
				if(tempJ.getPri() < tempK.getPri())
				{
					r.jList.add(j, tempJ);
					break;
				}
				else if(j == r.jList.size() - 1 && tempJ.getPri() >= tempK.getPri())
				{
					if(tempJ.getID() == tempK.getID())
					{
						break;
					}
					r.jList.add(tempJ);
					break;
				}
			}
		}
	}
	
	//orders according to FiFo
	public void OrderFiFo(HDD d, RAM r)
	{
		Job tempJ;
		r.jList.clear();
		for(int i = 0; i < d.jList.size(); ++i)
		{
			tempJ = d.jList.get(i);
			r.jList.add(tempJ.getID(), d.jList.get(i));
		}
	}
	
	//orders according to SJF
	public void OrderSJF(HDD d, RAM r)
	{
		r.jList.clear();
		Job tempJ, tempK;
		for(int i = 0; i < d.jList.size(); ++i)
		{
			tempJ = d.jList.get(i);
			if(r.jList.isEmpty())
			{
				r.jList.add(d.jList.get(i));
			}
			for(int j = 0; j < r.jList.size(); ++j)
			{
				tempK = r.jList.get(j);
				if(tempJ.getJobs() < tempK.getJobs())
				{
					r.jList.add(j, tempJ);
					break;
				}
				else if(j == r.jList.size() - 1 && tempJ.getJobs() > tempK.getJobs())
				{
					r.jList.add(tempJ);
				}
			}
		}
	}
	
	public void toQueue(RAM r, CPU c)
	{
		for(int i = 0; i < r.jList.size(); ++i)
		{
			Job tempJob = r.jList.get(i);
			PCB p = new PCB(); 
			p.setJob(tempJob);
			c.initPCBQ(p);
		}
	}
}
