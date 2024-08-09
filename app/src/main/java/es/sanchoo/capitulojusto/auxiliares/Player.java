package es.sanchoo.capitulojusto.auxiliares;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player implements Comparable<Player> {
    private int score;
    private String name;
    private List<Integer> register;
    private int minScore;
    private int maxScore;


    public Player(String name) {
        this.name = name;
        restartGame();
    }
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public List<Integer> getTurnMin(){
        int firstIndex = register.indexOf(minScore);
        int lastIndex = register.lastIndexOf(minScore);
        List<Integer> list = new ArrayList<>(Arrays.asList(lastIndex));
        while (firstIndex != lastIndex){
            List<Integer> subRegister = register.subList(0, lastIndex);
            lastIndex = subRegister.lastIndexOf(minScore);
            list.add(lastIndex);
        }
        return list;
    }
    public List<Integer> getTurnMax(){
        int firstIndex = register.indexOf(maxScore);
        int lastIndex = register.lastIndexOf(maxScore);
        List<Integer> list = new ArrayList<>(Arrays.asList(lastIndex));
        while (firstIndex != lastIndex){
            List<Integer> subRegister = register.subList(0, lastIndex);
            lastIndex = subRegister.lastIndexOf(maxScore);
            list.add(lastIndex);
        }
        return list;
    }
    public int getMinScore(){
        return minScore;
    }

    public int getMaxScore(){
        return maxScore;
    }

    public String getName() {
        return name;
    }

    public int getScoreAtTurn(int turn) {
        return register.get(turn);
    }

    public void addScore(int score) {
        this.score += score;
        addRegister(score);
    }

    private void addRegister(int score) {
        register.add(score);
        if (score < minScore){
            minScore = score;
        }
        if (score > maxScore){
            maxScore = score;
        }
    }

    public void restartGame() {
        this.score = 0;
        register = new ArrayList<>();
        minScore = Integer.MAX_VALUE;
        maxScore = Integer.MIN_VALUE;
    }

    @Override
    public int compareTo(Player p) {
        return this.score > p.score
                ? 1
                : this.score == p.score
                ? 0
                : -1;
    }
}
