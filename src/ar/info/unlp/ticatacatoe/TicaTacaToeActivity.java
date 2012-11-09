package ar.info.unlp.ticatacatoe;

import networkdcq.NetworkStartup;
import networkdcq.discovery.HostDiscovery;
import networkdcq.util.Logger;
import android.app.Activity;
import android.content.Context;
import android.graphics.LightingColorFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;



public class TicaTacaToeActivity extends Activity {

	// Current game state 
	public static int CURRENT_GAME_STATE  		= R.string.waiting;
	// Current player type (X o O)	
	public static int CURRENT_PLAYER_TYPE 		= R.string.empty_cell;
	// Current other player type (X o O)	
	public static int CURRENT_OTHER_PLAYER_TYPE = R.string.empty_cell;
	// Who played first in this game
	public static int CURRENT_FIRST = -1;
	// Model
	public static int[] GAME_BOARD = new int[9];
	
	// Keep display ON!!
	protected WakeLock wakeLock; 
	// Support multicast packets!!
	WifiManager.MulticastLock multicastLock = null;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Full screen settings
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PowerManager powerManager = (PowerManager)getBaseContext().getSystemService(Context.POWER_SERVICE); 
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock"); 
        // Permitir multicast
        WifiManager wm = (WifiManager)getSystemService(Context.WIFI_SERVICE); 
        WifiManager.MulticastLock multicastLock = wm.createMulticastLock("mydebuginfo"); 
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();


        try {
        	NetworkStartup.configureStartup(new Consumer(this), null);
        	NetworkStartup.doStartup(true, true, false);
        }
        catch (Exception e) {
        	Logger.e(e.getMessage());
        }

        initButtons();
        cleanBoard();
    }

    @Override
    protected void onPause() {
    	super.onPause();
    	System.exit(0);
    }
    
    /**
     * Reset board
     */
    protected void cleanBoard() { 
    	((Button)findViewById(R.id.button1)).setText(R.string.empty_cell);
    	((Button)findViewById(R.id.button2)).setText(R.string.empty_cell);
    	((Button)findViewById(R.id.button3)).setText(R.string.empty_cell);
    	((Button)findViewById(R.id.button4)).setText(R.string.empty_cell);
    	((Button)findViewById(R.id.button5)).setText(R.string.empty_cell);
    	((Button)findViewById(R.id.button6)).setText(R.string.empty_cell);
    	((Button)findViewById(R.id.button7)).setText(R.string.empty_cell);
    	((Button)findViewById(R.id.button8)).setText(R.string.empty_cell);
    	((Button)findViewById(R.id.button9)).setText(R.string.empty_cell);
    	for (int i = 0; i < 9; i++)
    		GAME_BOARD[i] = R.string.empty_cell;
    }
    
    protected void initButtons() {
        runOnUiThread(new Runnable() {
            public void run() {
		    	((Button)findViewById(R.id.restartButton)).setEnabled(false);
		    	((Button)findViewById(R.id.button1)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	((Button)findViewById(R.id.button2)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	((Button)findViewById(R.id.button3)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	((Button)findViewById(R.id.button4)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	((Button)findViewById(R.id.button5)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	((Button)findViewById(R.id.button6)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	((Button)findViewById(R.id.button7)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	((Button)findViewById(R.id.button8)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	((Button)findViewById(R.id.button9)).getBackground().setColorFilter(new LightingColorFilter(0x00AAAAAA, 0x000000FF));
		    	// button1
				((Button)findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) {  sendButton((Button)findViewById(R.id.button1), 1); }
				});
		    	// button2
				((Button)findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { sendButton((Button)findViewById(R.id.button2), 2); }
				});
		    	// button3
				((Button)findViewById(R.id.button3)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { sendButton((Button)findViewById(R.id.button3), 3); }
				});
		    	// button4
				((Button)findViewById(R.id.button4)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { sendButton((Button)findViewById(R.id.button4), 4); }
				});
		    	// button5
				((Button)findViewById(R.id.button5)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { sendButton((Button)findViewById(R.id.button5), 5); }
				});
		    	// button6
				((Button)findViewById(R.id.button6)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { sendButton((Button)findViewById(R.id.button6), 6); }
				});
		    	// button7
				((Button)findViewById(R.id.button7)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { sendButton((Button)findViewById(R.id.button7), 7); }
				});
		    	// button8
				((Button)findViewById(R.id.button8)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { sendButton((Button)findViewById(R.id.button8), 8); }
				});
		    	// button9
				((Button)findViewById(R.id.button9)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { sendButton((Button)findViewById(R.id.button9), 9); }
				});
		    	// restartButton
				((Button)findViewById(R.id.restartButton)).setOnClickListener(new OnClickListener() {
					public void onClick(View v) { restartAction(true); }
				});
        	}
        });				
    }

    /**
     * Action when the user clicks a button
     */
    protected void sendButton(Button aButton, int number) {
    	// Only if blank and my turn
    	if (CURRENT_GAME_STATE == R.string.your_turn && getString(R.string.empty_cell).equals(aButton.getText())) {
    		// draw button, update model
    		aButton.setText(CURRENT_PLAYER_TYPE);
    		GAME_BOARD[number-1] = CURRENT_PLAYER_TYPE;
    		// notify the other user
    		Data data = new Data();
    		data.action = Data.ACTION_SET_CELL;
    		data.position = number;
    		NetworkStartup.getCommunication().sendMessage(HostDiscovery.otherHosts.getValueList().get(0), data);
    		// now its the other player turn
    		CURRENT_GAME_STATE = R.string.other_turn;
    		
    		// did I win?
    		if (winCombination(CURRENT_PLAYER_TYPE)) {
    			CURRENT_GAME_STATE = R.string.you_win;
    			((Button)findViewById(R.id.restartButton)).setEnabled(true);
    		}
    		// game tied?
    		else if (tieCombination()) {
    			CURRENT_GAME_STATE = R.string.a_tie;
    			((Button)findViewById(R.id.restartButton)).setEnabled(true);
    		}
    		
    		TextView title = (TextView)findViewById(R.id.title);
    		title.setText(CURRENT_GAME_STATE);
    	}
    }
    
    public void restartAction(boolean sendMessage) {
        if (sendMessage) {
    		// notify the other user
    		Data data = new Data();
    		data.action = Data.ACTION_RESTART;
    		NetworkStartup.getCommunication().sendMessage(HostDiscovery.otherHosts.getValueList().get(0), data);
        }
        initButtons();
        cleanBoard();
        if (CURRENT_FIRST == R.string.your_turn) {
        	CURRENT_FIRST = R.string.other_turn;
        	CURRENT_GAME_STATE = R.string.other_turn;
        }
        else {
        	CURRENT_FIRST = R.string.your_turn;
        	CURRENT_GAME_STATE = R.string.your_turn;
        }
    	// Current player info
        runOnUiThread(new Runnable() {
            public void run() {
            	TextView title = (TextView)findViewById(R.id.title);
        		title.setText(getString(TicaTacaToeActivity.CURRENT_GAME_STATE));
        	}
        });
    }
    
    
    /**
     * Check for the possible win combinations
     */
    public boolean winCombination(int playerType) {
    	return 	(GAME_BOARD[0] == playerType && GAME_BOARD[1] == playerType && GAME_BOARD[2] == playerType) ||
    			(GAME_BOARD[3] == playerType && GAME_BOARD[4] == playerType && GAME_BOARD[5] == playerType) ||
    			(GAME_BOARD[6] == playerType && GAME_BOARD[7] == playerType && GAME_BOARD[8] == playerType) ||
    			(GAME_BOARD[0] == playerType && GAME_BOARD[3] == playerType && GAME_BOARD[6] == playerType) ||
    			(GAME_BOARD[1] == playerType && GAME_BOARD[4] == playerType && GAME_BOARD[7] == playerType) ||
    			(GAME_BOARD[2] == playerType && GAME_BOARD[5] == playerType && GAME_BOARD[8] == playerType) ||
    			(GAME_BOARD[0] == playerType && GAME_BOARD[4] == playerType && GAME_BOARD[8] == playerType) ||
    			(GAME_BOARD[2] == playerType && GAME_BOARD[4] == playerType && GAME_BOARD[6] == playerType);
    }
    
    public boolean tieCombination() {
    	for (int i=0; i<9; i++)
    		if (GAME_BOARD[i] == R.string.empty_cell)
    			return false;
    	return true;
    }
}