package ar.info.unlp.ticatacatoe;

import networkdcq.Host;
import networkdcq.NetworkApplicationData;
import networkdcq.discovery.HostDiscovery;
import networkdcq.util.NetworkSerializable;

public class Data extends NetworkApplicationData implements NetworkSerializable {

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
	
	
	public String networkSerialize() {

		return ( HostDiscovery.thisHost.getHostIP() + NetworkSerializable.VARIABLE_MEMBER_SEPARATOR +
				 HostDiscovery.thisHost.isOnLine() + NetworkSerializable.VARIABLE_MEMBER_SEPARATOR + 
				 HostDiscovery.thisHost.getLastPing() + NetworkSerializable.VARIABLE_MEMBER_SEPARATOR +				
				 startingTime + NetworkSerializable.VARIABLE_MEMBER_SEPARATOR +
				 action + NetworkSerializable.VARIABLE_MEMBER_SEPARATOR +				 
				 position + NetworkSerializable.VARIABLE_END_OF_VARIABLES);
	}

	public Object networkDeserialize(String data) {
		// recupero valores
		String cadenas[] = data.split(""+NetworkSerializable.VARIABLE_MEMBER_SEPARATOR);
			
		// creo la instancia Data y la devuelvo
		Data appData = new Data();
		Host host = new Host(cadenas[0], cadenas[1]=="true");
		appData.sourceHost = host;
		host.setLastPing(Long.parseLong(cadenas[2]));
		appData.startingTime = Long.parseLong(cadenas[3]);
		appData.action = Integer.parseInt(cadenas[4]);
		appData.position = Integer.parseInt(cadenas[5]);
		return appData;
	}
	
}
