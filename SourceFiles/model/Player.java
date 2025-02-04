package model;

import java.awt.Color;
import java.util.Objects;

public class Player {

    /** Name of this player. */
    private final String name;
    private final char stone;
    private Color color;
    

    /** Create a new player with the given name. */
    public Player(String name, char stone, Color color) {
            this.name = name;
            this.stone = stone;
            this.color = color;
    }
    /** Return the name of this player. */
    public String name() {
        return name;
    }
    /** Return the stone of the player. */
    public char getStone() {
    	return stone;
    }
    public Color getColor() {
    	return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public String getColorName() {
        // This method translates the Color object into a human-readable string.
        if (Color.BLACK.equals(this.color)) {
            return "Black";
        } else if (Color.RED.equals(this.color)) {
            return "Red";
        } else {
            return "Unknown Color";
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Player player = (Player) obj;
        return stone == player.stone && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, stone);
    }
}