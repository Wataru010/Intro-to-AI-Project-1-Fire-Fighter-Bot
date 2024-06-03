import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Bot2 {
    // Bot 2 - 
    // At every time step, the bot re-plans the shortest path to the button, avoiding the current fire cells, and then executes the next step in that plan.

    // Breadth First Search for bot2
    public static ArrayList<Coordinate> find_path_BFS(Ship ship){
        Queue<Coordinate> queue = new LinkedList<>();
        HashMap<String, Coordinate> close_set = new HashMap<>();
        ArrayList<Coordinate> path_to_goal = new ArrayList<>();
        Coordinate bot_initial_position = ship.getBotCoor();

        queue.add(new Coordinate(bot_initial_position, null, 0));

        while(queue.peek() != null){
            Coordinate curr = queue.poll();
            if(curr.compare_coor(ship.getButtonCoor())){
                // reached goal note
                retrive_path(path_to_goal, curr);
                return path_to_goal;
            }

            add_candidate_child(ship, queue, curr, close_set);

            String temp = String.format("%d%d", curr.getRow(), curr.getCol());
            close_set.put(temp, curr);
        }
        return path_to_goal;
    }

    // Helper method to gather valid child of the parent node
    public static void add_candidate_child(Ship ship_obj, Queue<Coordinate> queue, Coordinate parent, HashMap<String, Coordinate> close_set){
        int[][] ship = ship_obj.getShipArray();
        // top 
        if(parent.getRow()-1 > -1){
            Coordinate top_cell = new Coordinate(new Coordinate(parent.getRow()-1, parent.getCol()), parent, parent.getLength()+1);
            if((ship[top_cell.getRow()][top_cell.getCol()] == 1 || ship[top_cell.getRow()][top_cell.getCol()] == 3) && !close_set.containsKey(String.format("%d%d", top_cell.getRow(), top_cell.getCol()))){
                queue.add(top_cell);
            }
        }

        // bottom
        if(parent.getRow()+1 < ship.length){
            Coordinate bottom_cell = new Coordinate(new Coordinate(parent.getRow()+1, parent.getCol()), parent, parent.getLength()+1);
            if((ship[bottom_cell.getRow()][bottom_cell.getCol()] == 1 || ship[bottom_cell.getRow()][bottom_cell.getCol()] == 3) && !close_set.containsKey(String.format("%d%d", bottom_cell.getRow(), bottom_cell.getCol()))){
                queue.add(bottom_cell);
            }
        }

        // left
        if(parent.getCol()-1 > -1){
            Coordinate left_cell = new Coordinate(new Coordinate(parent.getRow(), parent.getCol()-1), parent, parent.getLength()+1);
            if((ship[left_cell.getRow()][left_cell.getCol()] == 1 || ship[left_cell.getRow()][left_cell.getCol()] == 3) && !close_set.containsKey(String.format("%d%d", left_cell.getRow(), left_cell.getCol()))){
                queue.add(left_cell);
            }
        }

        // right
        if(parent.getCol()+1 < ship[parent.getRow()].length){
            Coordinate right_cell = new Coordinate(new Coordinate(parent.getRow(), parent.getCol()+1), parent, parent.getLength()+1);
            if((ship[right_cell.getRow()][right_cell.getCol()] == 1 || ship[right_cell.getRow()][right_cell.getCol()] == 3) && !close_set.containsKey(String.format("%d%d", right_cell.getRow(), right_cell.getCol()))){
                queue.add(right_cell);
            }
        }
    }

    // Retrive the path to goal note by back tracking its parent, so on and so forth
    public static void retrive_path(ArrayList<Coordinate> path_to_goal, Coordinate goal_note){
        Coordinate temp = goal_note;
        while(temp.getParent() != null){
            path_to_goal.add(0, new Coordinate(temp.getRow(), temp.getCol()));
            temp = temp.getParent();
        }
    }

    // Method to run bot2 with a given Ship object, without demostrating the steps
    public static int run_bot2(Ship ship){
        System.out.println("\nTest Run for bot2: ");
        int count = 0;
        Coordinate next_step = null;
        ArrayList<Coordinate> path_to_goal = new ArrayList<>();
        int[][] ship_layout = ship.getShipArray();
        Date date;

        while(true){
            path_to_goal = find_path_BFS(ship);
            date = new Date();
            System.out.println("Time Stamp: " + (count+1) + " " + date);

            if(path_to_goal.isEmpty()){
                Ship.print_layout(ship_layout);
                System.out.println("No way to put out the fire!");
                // break;
                return 0;
            }

            // The bot decides which adjacent cell to move to
            next_step = path_to_goal.get(0);
            path_to_goal.remove(0);

            // The bot moves to that cell.
            ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 1;
            ship.setBotCoor(next_step);
            ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 5;

            // If the bot enters the button cell, the button is pressed and the fire is put out - the task is completed.
            if(ship.getBotCoor().compare_coor(ship.getButtonCoor())){
                System.out.println("\nTime Stamp: " + (count+1));
                ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 7;
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 2 has put out the fire!");
                // break;
                return 1;
            }

            // Otherwise, the fire advances.
            boolean status = ship.getFire().spread_fire(ship);

            // If the cell the currently containing the bot catches on fire, the task is failed.
            if(Coordinate.compare_coor_with_list(ship.getFire().getListOfFire(), ship.getBotCoor())){
                System.out.println("\nTime Stamp: " + (count+1));
                ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 6;
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 2 catches on fire!");
                // break;
                return 0;
            }

            if(!status){
                System.out.println("\nTime Stamp: " + (count+1));
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 2 catches on fire!");
                // break;
                return 0;
            }

            count++;
        }
    }

    // Method to run bot2 with a given Ship object, without demostrating the steps
    public static void run_bot2_demo(Ship ship){
        System.out.println("\nTest Run for bot2: ");
        int count = 0;
        Coordinate next_step = null;
        ArrayList<Coordinate> path_to_goal = new ArrayList<>();
        int[][] ship_layout = ship.getShipArray();

        while(true){
            path_to_goal = find_path_BFS(ship);
            System.out.println("Time Stamp: " + (count+1));

            if(path_to_goal.size() == 0){
                Ship.print_layout(ship_layout);
                System.out.println("No way to put out the fire!");
                break;
            }

            next_step = path_to_goal.get(0);
            path_to_goal.remove(0);

            ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 1;
            ship.setBotCoor(next_step);
            ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 5;
            Ship.print_layout(ship.getShipArray());

            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(Exception error){
                System.out.println("Error occur!");
                System.out.println(error);
            }

            if(ship.getBotCoor().compare_coor(ship.getButtonCoor())){
                System.out.println("\nTime Stamp: " + (count+1));
                ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 7;
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 2 has put out the fire!");
                break;
            }

            boolean status = ship.getFire().spread_fire(ship);
            Ship.print_layout(ship.getShipArray());

            if(Coordinate.compare_coor_with_list(ship.getFire().getListOfFire(), ship.getBotCoor())){
                System.out.println("\nTime Stamp: " + (count+1));
                ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 6;
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 2 catches on fire!");
                break;
            }

            if(!status){
                System.out.println("\nTime Stamp: " + (count+1));
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 2 catches on fire!");
                break;
            }

            count++;

            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(Exception error){
                System.out.println("Error occur!");
                System.out.println(error);
            }
        }
    }
}
