package com.ossim.celoriag;

/*
 * @author Christian Eloriaga
 */

public class Driver 
{
	public static void main (String [] args) throws Exception
	{
		//initialize drives and scheduler then load data to the disk
		HDD nHDD = new HDD();
		RAM nRAM = new RAM();
		CPU nCPU = new CPU();
		Scheduler schedule = new Scheduler();
		
		nHDD.load_to_disk(nHDD);
		
		//fifo scheduling, load to RAM, print
		System.out.println("FiFo");
		schedule.OrderFiFo(nHDD, nRAM);
		//nRAM.printRAM();
		
		//rQ takes in RAM and executes jobs on CPU
		schedule.toQueue(nRAM, nCPU);
		nCPU.execute();
		//nCPU.printRQ();
		System.out.println();
		
		//sjf scheduling, load to RAM, print
		System.out.println("SJF");
		schedule.OrderSJF(nHDD, nRAM);
		//nRAM.printRAM();
		System.out.println();
		
		//priority scheduling, load to RAM, print
		System.out.println("Priority");
		schedule.OrderPriority(nHDD, nRAM);
		//nRAM.printRAM();
		System.out.println();
		
	}
}
