package jabberpoint.util;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * A timer for tracking presentation duration.
 * This class provides functionality to track time elapsed during a presentation.
 */
public class PresentationTimer extends JLabel {
    private static final long serialVersionUID = 227L;
    
    private static final int TIMER_DELAY = 1000; // 1 second
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final Font TIMER_FONT = new Font("Monospaced", Font.BOLD, 12);
    
    private long startTime;
    private Timer timer;
    private boolean running;
    private int warningMinutes = 15; // Default warning time in minutes
    private int alertMinutes = 20;   // Default alert time in minutes
    
    /**
     * Creates a new presentation timer.
     */
    public PresentationTimer() {
        super("00:00:00", SwingConstants.CENTER);
        setFont(TIMER_FONT);
        setOpaque(true);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        
        timer = new Timer(TIMER_DELAY, e -> updateTimer());
        running = false;
    }
    
    /**
     * Starts the timer.
     */
    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            timer.start();
            running = true;
        }
    }
    
    /**
     * Stops the timer.
     */
    public void stop() {
        if (running) {
            timer.stop();
            running = false;
        }
    }
    
    /**
     * Resets the timer to 00:00:00.
     */
    public void reset() {
        stop();
        startTime = System.currentTimeMillis();
        setText("00:00:00");
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
    }
    
    /**
     * Returns whether the timer is currently running.
     * 
     * @return true if the timer is running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Sets the warning time in minutes.
     * 
     * @param minutes Minutes after which the timer should display a warning color
     */
    public void setWarningTime(int minutes) {
        this.warningMinutes = minutes;
    }
    
    /**
     * Sets the alert time in minutes.
     * 
     * @param minutes Minutes after which the timer should display an alert color
     */
    public void setAlertTime(int minutes) {
        this.alertMinutes = minutes;
    }
    
    /**
     * Updates the timer display.
     */
    private void updateTimer() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        
        // Format and display the elapsed time
        Date date = new Date(elapsedTime);
        setText(TIME_FORMAT.format(date));
        
        // Change color based on elapsed time
        long elapsedMinutes = elapsedTime / (60 * 1000);
        
        if (elapsedMinutes >= alertMinutes) {
            // Alert color when over the alert time
            setBackground(Color.RED);
            setForeground(Color.WHITE);
        } else if (elapsedMinutes >= warningMinutes) {
            // Warning color when over the warning time
            setBackground(Color.YELLOW);
            setForeground(Color.BLACK);
        }
    }
}
