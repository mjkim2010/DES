package hu.mit.bme.mdsd.simulation;

import hu.mit.bme.mdsd.simulation.entities.LevelEntity;
//import hu.mit.bme.mdsd.simulation.entities.TableEntity;
//import hu.mit.bme.mdsd.simulation.entities.WaitressEntity;
import hu.mit.bme.mdsd.simulation.events.SensorEvent;
import desmoj.core.dist.ContDist;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.dist.DiscreteDist;
import desmoj.core.dist.DiscreteDistPoisson;
import desmoj.core.dist.DiscreteDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.statistic.Histogram;
import desmoj.core.statistic.Tally;

public class TankFarmSimulationModel extends Model {

	private static final int WAITRESSES = 1;
	private static final int TABLES = 3;
	//M: arbitrary values for which tank level can change
	private static final int TANK_DELTA_MIN = 0;
	private static final int TANK_DELTA_MAX = 10;
	
	public Queue<LevelEntity> buyersQueue;
	public Queue<LevelEntity> eatersQueue;
	//public Queue<WaitressEntity> idleWaitressQueue;
	//public Queue<TableEntity> idleTableQueue;
	
	private DiscreteDistUniform tankLevelDelta;	  //uniform distribution for modeling tank level change
	private DiscreteDist<?> customerGroupSize;
	private ContDist customerArrivalTime;
	private ContDist buyTime;
	private ContDist eatTime;
	
	public Tally timeSpentByCustomer;
	public Histogram customerGroupSizeHistogram;
	
	public int getTankLevelChange() {
		int value = tankLevelDelta.sample().intValue();
		return value;
	}
	
	public int getCustomerGroupSize(){
		int value = customerGroupSize.sample().intValue();
		return value == 0 ? 1 : value;
	}
	
	public int getCustomerArrivalTime(){
		int value = customerArrivalTime.sample().intValue();
		return value <= 0 ? 0 : value;
	}
	
	public int getBuyTime(){
		return buyTime.sample().intValue();
	}
	
	public int getEatTime(){
		return eatTime.sample().intValue();
	}

	public TankFarmSimulationModel(Model owner, String name,
			boolean showInReport, boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return "This model simulates a tank farm scenario, where there are multiple" +
				"tanks, each with a unique temperature, pressure, and tank level. " +
				"Every hour, a sensor will measure those three attributes for each tank ";
	}

	@Override
	public void doInitialSchedules() {
		SensorEvent event = new SensorEvent(this, "Sensor event", true);
		event.schedule(new TimeInstant(0));
	}

	@Override
	public void init() {
		buyersQueue = new Queue<LevelEntity>(this, "Buyers Queue", true, false);
		eatersQueue = new Queue<LevelEntity>(this, "Eaters Queue", true, false);
		//idleWaitressQueue = new Queue<WaitressEntity>(this, "Idle Waitresses Queue", true, false);
		//idleTableQueue = new Queue<TableEntity>(this, "Idle Tables Queue", true, false);

		/* Minju: we need to set the distributions of our different variables here! */ 
		tankLevelDelta = new DiscreteDistUniform(this, "Change in tank level", TANK_DELTA_MIN, TANK_DELTA_MAX, true, false);
		
		customerGroupSize = new DiscreteDistPoisson(this, "Customer group size", 2, true, false);
		customerArrivalTime = new ContDistNormal(this, "Customer arrival time", 4*60, 1*60, true, false);
		buyTime = new ContDistUniform(this, "Buying time", 1*60, 3*60, true, false);
		eatTime = new ContDistUniform(this, "Eating time", 5*60, 20*60, true, false);
	
		for (int i = 0; i < WAITRESSES; i++){
			//idleWaitressQueue.insert(new WaitressEntity(this, "Waitress", false));
		}
		
		for (int i = 0; i < TABLES; i++){
			//idleTableQueue.insert(new TableEntity(this, "Table", false));
		}
		
		timeSpentByCustomer = new Tally(this, "Time spent by customer", true, false);
		customerGroupSizeHistogram = new Histogram(this, "Histogram", 1, 10, 9, true, false);
	}

}
