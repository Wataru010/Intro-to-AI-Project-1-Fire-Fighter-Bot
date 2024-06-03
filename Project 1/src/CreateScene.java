import java.util.ArrayList;

public class CreateScene {
    // Set up button coordinate
    public static Coordinate set_button(int[][] ship, ArrayList<Coordinate> path){
        int pick_button_index = (int)(Math.random()*path.size());
        Coordinate button = path.get(pick_button_index);
        ship[button.getRow()][button.getCol()] = 3;
        path.remove(pick_button_index);
        return button;
    }

    // Set up fire coordinate
    public static Coordinate set_fire(int[][] ship, ArrayList<Coordinate> path, Coordinate button){
        int pick_fire_index = (int)(Math.random()*path.size());
        Coordinate fire = path.get(pick_fire_index);

        while(button.compare_coor(fire)){
            pick_fire_index = (int)(Math.random()*path.size());
            fire = path.get(pick_fire_index);
        }
        
        ship[fire.getRow()][fire.getCol()] = 4;
        path.remove(pick_fire_index);

        return fire;
    }

    // Set up bot coordinate
    public static Coordinate set_bot(int[][] ship, ArrayList<Coordinate> path, Coordinate button, Coordinate fire){
        int pick_bot_index = (int)(Math.random()*path.size());
        Coordinate bot = path.get(pick_bot_index);
        while(bot.compare_coor(button) && bot.compare_coor(fire)){
            pick_bot_index = (int)(Math.random()*path.size());
            bot = path.get(pick_bot_index);
        }

        ship[bot.getRow()][bot.getCol()] = 5;

        return bot;
    }
}