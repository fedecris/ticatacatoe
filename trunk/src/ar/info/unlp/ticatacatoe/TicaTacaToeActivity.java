package ar.info.unlp.ticatacatoe;

import networkdcq.NetworkStartup;
import networkdcq.util.Logger;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



public class TicaTacaToeActivity extends Activity {

	public static int CURRENT_GAME_STATE  		= R.string.waiting;
	public static int CURRENT_PLAYER_TYPE 		= R.string.empty_cell;
	public static int CURRENT_OTHER_PLAYER_TYPE = R.string.empty_cell;
	
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
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PowerManager powerManager = (PowerManager)getBaseContext().getSystemService(Context.POWER_SERVICE); 
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock"); 
        // Permitir multicast
        WifiManager wm = (WifiManager)getSystemService(Context.WIFI_SERVICE); 
        WifiManager.MulticastLock multicastLock = wm.createMulticastLock("mydebuginfo"); 
        multicastLock.setReferenceCounted(true);
        multicastLock.acquire();


        try {
        	NetworkStartup.configureStartup(new Consumer(this), new Producer());
//        	NetworkStartup.doStartup();
        	NetworkStartup.getCommunication().startService();
        	NetworkStartup.getDiscovery().startDiscovery();

        }
        catch (Exception e) {
        	Logger.e(e.getMessage());
        }

        initButtons();
        cleanBoard();
    }

    protected void cleanBoard() { 
    	((Button)findViewById(R.id.button1)).setText(CURRENT_PLAYER_TYPE);
    	((Button)findViewById(R.id.button2)).setText(CURRENT_PLAYER_TYPE);
    	((Button)findViewById(R.id.button3)).setText(CURRENT_PLAYER_TYPE);
    	((Button)findViewById(R.id.button4)).setText(CURRENT_PLAYER_TYPE);
    	((Button)findViewById(R.id.button5)).setText(CURRENT_PLAYER_TYPE);
    	((Button)findViewById(R.id.button6)).setText(CURRENT_PLAYER_TYPE);
    	((Button)findViewById(R.id.button7)).setText(CURRENT_PLAYER_TYPE);
    	((Button)findViewById(R.id.button8)).setText(CURRENT_PLAYER_TYPE);
    	((Button)findViewById(R.id.button9)).setText(CURRENT_PLAYER_TYPE);
    	for (int i = 0; i < 9; i++)
    		GAME_BOARD[i] = CURRENT_PLAYER_TYPE;
    }
    
    protected void initButtons() {
    	// button1
		((Button)findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button1), 1);
			}
		});
    	// button2
		((Button)findViewById(R.id.button2)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button2), 2);
			}
		});
    	// button3
		((Button)findViewById(R.id.button3)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button3), 3);
			}
		});
    	// button4
		((Button)findViewById(R.id.button4)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button4), 4);
			}
		});
    	// button5
		((Button)findViewById(R.id.button5)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button5), 5);
			}
		});
    	// button6
		((Button)findViewById(R.id.button6)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button6), 6);
			}
		});
    	// button7
		((Button)findViewById(R.id.button7)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button7), 7);
			}
		});
    	// button8
		((Button)findViewById(R.id.button8)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button8), 8);
			}
		});
    	// button9
		((Button)findViewById(R.id.button9)).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
						sendButton((Button)findViewById(R.id.button9), 9);
			}
		});
    }


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
    		NetworkStartup.getCommunication().sendMessage(NetworkStartup.getDiscovery().otherHosts.getValueList().get(0).getHostIP(), data);
    		// now its the other player turn
    		CURRENT_GAME_STATE = R.string.other_turn;
    		
    		// did I win?
    		if (winCombination(CURRENT_PLAYER_TYPE)) {
    			CURRENT_GAME_STATE = R.string.you_win;
    		}
    		
    		TextView title = (TextView)findViewById(R.id.title);
    		title.setText(CURRENT_GAME_STATE);
    	}
    }
    
    
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
}