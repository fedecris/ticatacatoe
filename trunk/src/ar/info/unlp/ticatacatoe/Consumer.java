package ar.info.unlp.ticatacatoe;

import networkdcq.Host;
import networkdcq.NetworkApplicationData;
import networkdcq.NetworkApplicationDataConsumer;
import networkdcq.NetworkStartup;
import networkdcq.discovery.HostDiscovery;
import networkdcq.util.Logger;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

public class Consumer implements NetworkApplicationDataConsumer {

	TicaTacaToeActivity owner = null;
	long myStartingTime = -1;
	
	public Consumer(TicaTacaToeActivity owner) {
		super();
		this.owner = owner;
	}
	
	@Override
	public synchronized void newHost(Host aHost) {
		NetworkStartup.getCommunication().connectToServerHost(aHost);
		owner.runOnUiThread(new Runnable() {
            public void run() {
        		TextView title = (TextView)owner.findViewById(R.id.title);
        		title.setText(R.string.started);           }
		});
		// Order should always be 1) newHost, 2) newData
		try {
			Thread.sleep(HostDiscovery.DISCOVERY_INTERVAL_MS * 2);
		}
		catch (Exception e) { }
		// Send initial data. Who starts?
		Data data = new Data();
		data.action = Data.ACTION_WHO_STARTS;
		data.startingTime = System.nanoTime();
		myStartingTime = data.startingTime;
		NetworkStartup.getCommunication().sendMessage(aHost.getHostIP(), data);
		Logger.i("Who starts?");
	}


	@Override
	public synchronized void byeHost(Host aHost) {
		TicaTacaToeActivity.CURRENT_GAME_STATE = R.string.waiting;
        owner.runOnUiThread(new Runnable() {
            public void run() {
        		TextView title = (TextView)owner.findViewById(R.id.title);
        		title.setText(R.string.waiting);           }
        });
	}

	@Override
	public synchronized void newData(NetworkApplicationData receivedData) {
		Data data = (Data)receivedData;
		Logger.i("New Data! " + data.action);
		// Deciding who starts
		if (TicaTacaToeActivity.CURRENT_GAME_STATE == R.string.waiting && data.action == Data.ACTION_WHO_STARTS ) {
			if (data.startingTime < myStartingTime) {
				TicaTacaToeActivity.CURRENT_GAME_STATE = R.string.other_turn;
				TicaTacaToeActivity.CURRENT_PLAYER_TYPE = R.string.O;
				TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE = R.string.X;
			}
			else {
				TicaTacaToeActivity.CURRENT_GAME_STATE = R.string.your_turn;
				TicaTacaToeActivity.CURRENT_PLAYER_TYPE = R.string.X;
				TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE = R.string.O;
			}
		}
		// Action received!
		else if (data.action == Data.ACTION_SET_CELL ) {
			TicaTacaToeActivity.CURRENT_GAME_STATE = R.string.your_turn;
			ButtonManager manager = new ButtonManager(owner, data.position);
			TicaTacaToeActivity.GAME_BOARD[data.position-1] = TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE;
			
			// Did I loose?
    		if (owner.winCombination(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE)) {
    			TicaTacaToeActivity.CURRENT_GAME_STATE = R.string.you_loose;
    		}
			
	        owner.runOnUiThread(manager);			
		}
		// Update current game state
        owner.runOnUiThread(new Runnable() {
            public void run() {
            	// Current player info
        		TextView title = (TextView)owner.findViewById(R.id.title);
        		title.setText(owner.getString(TicaTacaToeActivity.CURRENT_GAME_STATE));
        		// Title and some more info
        		owner.setTitle(owner.getString(R.string.app_name));
        		if (TicaTacaToeActivity.CURRENT_PLAYER_TYPE == R.string.X || TicaTacaToeActivity.CURRENT_PLAYER_TYPE == R.string.O)
        			owner.setTitle(owner.getString(R.string.app_name) + " Playing with: " + owner.getString(TicaTacaToeActivity.CURRENT_PLAYER_TYPE));
        	}
        });

	}
		
	
	public class ButtonManager extends Thread {
		int position;
		Activity owner;
		
		public ButtonManager(Activity act, int pos) {
			position = pos;
			owner = act;
		}
		
		// Updates the corresponding button
		public void run() {
			if (position == 1)
				((Button)owner.findViewById(R.id.button1)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
			else if (position == 2)
				((Button)owner.findViewById(R.id.button2)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
			else if (position == 3)
				((Button)owner.findViewById(R.id.button3)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
			else if (position == 4)
				((Button)owner.findViewById(R.id.button4)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
			else if (position == 5)
				((Button)owner.findViewById(R.id.button5)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
			else if (position == 6)
				((Button)owner.findViewById(R.id.button6)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
			else if (position == 7)
				((Button)owner.findViewById(R.id.button7)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
			else if (position == 8)
				((Button)owner.findViewById(R.id.button8)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
			else if (position == 9)
				((Button)owner.findViewById(R.id.button9)).setText(TicaTacaToeActivity.CURRENT_OTHER_PLAYER_TYPE);
		}
	}
}


