package ar.info.unlp.ticatacatoe;

import networkdcq.NetworkApplicationData;

public class Data extends NetworkApplicationData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4493422119694521819L;
	
	/** Starting action */
	public static final int ACTION_WHO_STARTS	= 0;
	/** Set cell action */
	public static final int ACTION_SET_CELL 	= 1;
	/** Restart action */
	public static final int ACTION_RESTART 		= 2;
	
	/** Used to know which starts the game */
	public long startingTime = -1;
	/** Some of the available actions */
	public int action = -1;
	/** 1 to 9 */
	public int position = -1;
	
}
