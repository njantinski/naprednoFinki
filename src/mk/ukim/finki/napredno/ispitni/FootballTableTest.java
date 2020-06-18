package mk.ukim.finki.napredno.ispitni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here

class FootballTable{
    Set<FootballTeam> table;
    Map<String,FootballTeam> teamsATM;


    public FootballTable() {
        teamsATM = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals){
           if(!teamsATM.containsKey(homeTeam)){
               addNewTeam(homeTeam,homeGoals,awayGoals);

           }
           else{
               teamsATM.get(homeTeam).playGame(homeGoals,awayGoals);
           }
           if(!teamsATM.containsKey(awayTeam)){
               addNewTeam(awayTeam,awayGoals,homeGoals);
           }
           else{
               teamsATM.get(awayTeam).playGame(awayGoals,homeGoals);
           }

    }

    private void addNewTeam(String teamName, int goalsMade, int goalsReceived) {
        FootballTeam newTeam = new FootballTeam(teamName);
        newTeam.playGame(goalsMade,goalsReceived);;
        teamsATM.put(teamName,newTeam);
    }

    public void printTable(){
        updateTable();
        int counter = 1;


        for(FootballTeam ft : table){
            System.out.println(String.format("%2d. %-15s%5s%5s%5s%5s%5s",counter, ft.getName(),ft.getGamesPlayed(),
                    ft.getGamesWon(),ft.getGamesDraw(),ft.getGamesLost(),ft.getPoints()));
            counter++;
        }

    }

    private void updateTable() {
        table = new TreeSet<>(Comparator.comparing(FootballTeam::getPoints).thenComparing(FootballTeam::goalDif).thenComparing(FootballTeam::getName).reversed());
        teamsATM.values().stream().forEach(v -> table.add(v));
    }
}

class FootballTeam{
    private String name;
    private int points;
    private int goalsMade;
    private int goalsReceived;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesDraw;
    private int gamesLost;

    public FootballTeam(String name) {
        this.name = name;
        points = 0;
        goalsMade = 0;
        goalsReceived = 0;
        gamesPlayed = 0;
        gamesWon = 0;
        gamesDraw = 0;
        gamesLost = 0;
    }

    public void playGame(int goalsMade, int goalsReceived){
        this.goalsMade += goalsMade;
        this.goalsReceived += goalsReceived;
        gamesPlayed++;
        if (goalsMade > goalsReceived) {
            gamesWon++;
            points+=3;
        }else
        if(goalsMade == goalsReceived){
            gamesDraw++;
            points+=1;
        }else {
            gamesLost++;
        }

    }

    public int getPoints(){
        return points;
    }

    public int getGoalsMade() {
        return goalsMade;
    }

    public int getGoalsReceived() {
        return goalsReceived;
    }

    public String getName() {
        return name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesDraw() {
        return gamesDraw;
    }

    public int goalDif(){
        return goalsMade - goalsReceived;
    }

    public int getGamesLost() {
        return gamesLost;
    }
}