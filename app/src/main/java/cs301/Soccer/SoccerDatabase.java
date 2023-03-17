package cs301.Soccer;

import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private Hashtable<String, SoccerPlayer> database = new Hashtable<>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {

        String key = firstName + " ## " + lastName;
        SoccerPlayer player = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);

        if(database.containsKey(key) == true) {
            return false;
        }
        else {
            database.put(key,player);
            return true;
        }
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {

        String key = firstName + " ## " + lastName;

        if(database.containsKey(key) == true) {
            database.remove(key);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {

        String key = firstName + " ## " + lastName;

        if(database.containsKey(key) == true) {
            return database.get(key);
        }
        else {
            return null;
        }
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {

        String key = firstName + " ## " + lastName;

        if(database.containsKey(key) == true) {
            database.get(key).bumpGoals();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        String key = firstName + " ## " + lastName;

        if(database.containsKey(key) == true) {
            database.get(key).bumpYellowCards();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        String key = firstName + " ## " + lastName;

        if(database.containsKey(key) == true) {
            database.get(key).bumpRedCards();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {

        int i = 0;

        if(teamName == null) {
            return database.size();
        }
        else {
            for(SoccerPlayer player : database.values()) {
                if(player.getTeamName().equals(teamName)) {
                    i++;
                }
            }
        }
        return i;
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        int count = 0;
        for(String name : database.keySet()) {
            if(teamName == null) {
                if(count == idx) {
                    return database.get(name);
                }
                count++;
            }
            else {
                if(database.get(name).getTeamName().equals(teamName)) {
                    if(count == idx) {
                        return database.get(name);
                    }
                    count++;
                }
            }
        }
        return null;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        Scanner scan = null;
        try {
            scan = new Scanner(file);
            while(scan.hasNextLine()) {

                String firstName = scan.nextLine();
                String lastName = scan.nextLine();
                int uniformNum = Integer.parseInt(scan.nextLine());
                String teamName = scan.nextLine();
                int goalsScored = Integer.parseInt(scan.nextLine());
                int redCards = Integer.parseInt(scan.nextLine());
                int yellowCards = Integer.parseInt(scan.nextLine());

                SoccerPlayer player = new SoccerPlayer(firstName, lastName, uniformNum, teamName);
                for (int i = 0; i < goalsScored; ++i) {
                    player.bumpGoals();
                }
                for (int i = 0; i < yellowCards; ++i) {
                    player.bumpYellowCards();
                }
                for (int i = 0; i < redCards; ++i) {
                    player.bumpRedCards();
                }

                String key = firstName + " ## " + lastName;

                database.put(key, player);
            }
        }
        catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(file);
            for(SoccerPlayer player : database.values()) {
                pw.println(logString(player.getFirstName()));
                pw.println(logString(player.getLastName()));
                pw.println(logString(Integer.toString(player.getUniform())));
                pw.println(logString(player.getTeamName()));
                pw.println(logString(Integer.toString(player.getGoals())));
                pw.println(logString(Integer.toString(player.getRedCards())));
                pw.println(logString(Integer.toString(player.getYellowCards())));
            }
            pw.close();
            return true;
        }
        catch(FileNotFoundException e) {
            return false;
        }
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        return new HashSet<String>();
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if(database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
