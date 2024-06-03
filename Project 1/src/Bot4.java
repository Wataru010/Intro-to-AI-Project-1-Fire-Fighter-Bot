import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Bot4 {
    // Bot4 - 
    // A bot of your own design.

    // A* (A star) Search for bot4
    public static ArrayList<Coordinate> Astar_Search(Ship ship){
        ArrayList<Coordinate> path_to_goal = new ArrayList<>();
        CoordinateCompare comparator = new CoordinateCompare();
        PriorityQueue<Coordinate> priority_queue = new PriorityQueue<>(comparator);
        HashMap<String, Integer> distTO = new HashMap<>();
        Coordinate bot_initial_position = ship.getBotCoor();

        priority_queue.add(new Coordinate(bot_initial_position, null, 0, 0+calculate_heuristic(ship, bot_initial_position)));
        distTO.put(String.format("%d%d", bot_initial_position.getRow(), bot_initial_position.getCol()), 0);

        while(priority_queue.peek() != null){
            Coordinate curr = priority_queue.poll();
            if(curr.compare_coor(ship.getButtonCoor())){
                // succeed
                retrive_path(path_to_goal, curr);
                return path_to_goal;
            }

            add_candidate_child(ship, priority_queue, curr, distTO);

        }
        return path_to_goal;
    }

    // Helper method to gather valid child of the parent node
    public static void add_candidate_child(Ship ship_obj, PriorityQueue<Coordinate> priority_queue, Coordinate parent, HashMap<String, Integer> distTO){
        int[][] ship = ship_obj.getShipArray();
        int tempDist = 0;
        // top 
        if(parent.getRow()-1 > -1){
            Coordinate top_cell = new Coordinate(new Coordinate(parent.getRow()-1, parent.getCol()), parent, parent.getLength()+1, 0);
            if((ship[top_cell.getRow()][top_cell.getCol()] == 1 || ship[top_cell.getRow()][top_cell.getCol()] == 3)){

                tempDist = top_cell.getLength();
                int heuristic = calculate_heuristic(ship_obj, top_cell);

                if(distTO.containsKey(String.format("%d%d", top_cell.getRow(), top_cell.getCol()))){
                    if(tempDist < distTO.get(String.format("%d%d", top_cell.getRow(), top_cell.getCol()))){
                        distTO.put(String.format("%d%d", top_cell.getRow(), top_cell.getCol()), tempDist);
                        top_cell.setPriority(tempDist + heuristic);
                        priority_queue.add(top_cell);
                    }
                }else{
                    distTO.put(String.format("%d%d", top_cell.getRow(), top_cell.getCol()), tempDist);
                    top_cell.setPriority(tempDist + heuristic);
                    priority_queue.add(top_cell);
                }

            }
        }

        // bottom
        if(parent.getRow()+1 < ship.length){
            Coordinate bottom_cell = new Coordinate(new Coordinate(parent.getRow()+1, parent.getCol()), parent, parent.getLength()+1, 0);
            if((ship[bottom_cell.getRow()][bottom_cell.getCol()] == 1 || ship[bottom_cell.getRow()][bottom_cell.getCol()] == 3)){

                tempDist = bottom_cell.getLength();
                int heuristic = calculate_heuristic(ship_obj, bottom_cell);

                if(distTO.containsKey(String.format("%d%d", bottom_cell.getRow(), bottom_cell.getCol()))){
                    if(tempDist < distTO.get(String.format("%d%d", bottom_cell.getRow(), bottom_cell.getCol()))){
                        distTO.put(String.format("%d%d", bottom_cell.getRow(), bottom_cell.getCol()), tempDist);
                        bottom_cell.setPriority(tempDist + heuristic);
                        priority_queue.add(bottom_cell);
                    }
                }else{
                    distTO.put(String.format("%d%d", bottom_cell.getRow(), bottom_cell.getCol()), tempDist);
                    bottom_cell.setPriority(tempDist + heuristic);
                    priority_queue.add(bottom_cell);
                }
            }
        }

        // left
        if(parent.getCol()-1 > -1){
            Coordinate left_cell = new Coordinate(new Coordinate(parent.getRow(), parent.getCol()-1), parent, parent.getLength()+1, 0);
            if((ship[left_cell.getRow()][left_cell.getCol()] == 1 || ship[left_cell.getRow()][left_cell.getCol()] == 3)){
                
                tempDist = left_cell.getLength();
                int heuristic = calculate_heuristic(ship_obj, left_cell);

                if(distTO.containsKey(String.format("%d%d", left_cell.getRow(), left_cell.getCol()))){
                    if(tempDist < distTO.get(String.format("%d%d", left_cell.getRow(), left_cell.getCol()))){
                        distTO.put(String.format("%d%d", left_cell.getRow(), left_cell.getCol()), tempDist);
                        left_cell.setPriority(tempDist + heuristic);
                        priority_queue.add(left_cell);
                    }
                }else{
                    distTO.put(String.format("%d%d", left_cell.getRow(), left_cell.getCol()), tempDist);
                    left_cell.setPriority(tempDist + heuristic);
                    priority_queue.add(left_cell);
                }
            }
        }

        // right
        if(parent.getCol()+1 < ship[parent.getRow()].length){
            Coordinate right_cell = new Coordinate(new Coordinate(parent.getRow(), parent.getCol()+1), parent, parent.getLength()+1, 0);
            if((ship[right_cell.getRow()][right_cell.getCol()] == 1 || ship[right_cell.getRow()][right_cell.getCol()] == 3)){
                
                tempDist = right_cell.getLength();
                int heuristic = calculate_heuristic(ship_obj, right_cell);

                if(distTO.containsKey(String.format("%d%d", right_cell.getRow(), right_cell.getCol()))){
                    if(tempDist < distTO.get(String.format("%d%d", right_cell.getRow(), right_cell.getCol()))){
                        distTO.put(String.format("%d%d", right_cell.getRow(), right_cell.getCol()), tempDist);
                        right_cell.setPriority(tempDist + heuristic);
                        priority_queue.add(right_cell);
                    }
                }else{
                    distTO.put(String.format("%d%d", right_cell.getRow(), right_cell.getCol()), tempDist);
                    right_cell.setPriority(tempDist + heuristic);
                    priority_queue.add(right_cell);
                }
            }
        }
    }

    // Helper method to calculate the heuristic for A star Search using DFS for finding a fire cell and use the Manhattan distance to calculate the heuristic
    public static int calculate_heuristic(Ship ship, Coordinate current_cell){
        int[][] ship_layout = ship.getShipArray();
        Stack<Coordinate> stack = new Stack<>();
        // Queue<Coordinate> queue = new LinkedList<>();
        HashMap<String, Coordinate> close_set = new HashMap<>();
        Coordinate init_spot = current_cell;
        // queue.add(current_cell);
        stack.push(current_cell);
        int heuristic = 0;

        while(!stack.isEmpty()){
            Coordinate curr = stack.pop();
            if(ship_layout[curr.getRow()][curr.getCol()] == 2 || ship_layout[curr.getRow()][curr.getCol()] == 4){
                // succeed
                int dist_child2fire = Math.abs(curr.getRow()-init_spot.getRow()) + Math.abs(curr.getCol()-init_spot.getCol());
                int dist_child2button = Math.abs(init_spot.getRow()-ship.getButtonCoor().getRow()) + Math.abs(init_spot.getCol()-ship.getButtonCoor().getCol());   
                // int dist_fire2button = Math.abs(curr.getRow()-ship.getButtonCoor().getRow()) + Math.abs(curr.getCol()-ship.getButtonCoor().getCol());

                if(dist_child2button < dist_child2fire){
                    heuristic = dist_child2button;
                }else if(dist_child2button > dist_child2fire){
                    heuristic = dist_child2button;
                }else{
                    heuristic = (int)(dist_child2button - (dist_child2fire * (ship.getFire().getProbability())));
                }

                break;
            }

            add_candidate_child(ship, stack, curr, close_set);

            close_set.put(String.format("%d%d", curr.getRow(), curr.getCol()), curr);
        }
        
        return heuristic;
    }

    // Helper method to gather valid child of the parent node
    public static void add_candidate_child(Ship ship_obj,  Stack<Coordinate> stack, Coordinate parent, HashMap<String, Coordinate> close_set){
        int[][] ship = ship_obj.getShipArray();
        // top 
        if(parent.getRow()-1 > -1){
            Coordinate top_cell = new Coordinate(new Coordinate(parent.getRow()-1, parent.getCol()), parent, parent.getLength()+1);
            if((ship[top_cell.getRow()][top_cell.getCol()] == 1 
            || ship[top_cell.getRow()][top_cell.getCol()] == 3
            || ship[top_cell.getRow()][top_cell.getCol()] == 2
            || ship[top_cell.getRow()][top_cell.getCol()] == 4
            ) && !close_set.containsKey(String.format("%d%d", top_cell.getRow(), top_cell.getCol())) ){
                stack.push(top_cell);
            }
        }

        // bottom
        if(parent.getRow()+1 < ship.length){
            Coordinate bottom_cell = new Coordinate(new Coordinate(parent.getRow()+1, parent.getCol()), parent, parent.getLength()+1);
            if((ship[bottom_cell.getRow()][bottom_cell.getCol()] == 1 
            || ship[bottom_cell.getRow()][bottom_cell.getCol()] == 3
            || ship[bottom_cell.getRow()][bottom_cell.getCol()] == 2
            || ship[bottom_cell.getRow()][bottom_cell.getCol()] == 4
            ) && !close_set.containsKey(String.format("%d%d", bottom_cell.getRow(), bottom_cell.getCol())) ){
                stack.push(bottom_cell);
            }
        }

        // left
        if(parent.getCol()-1 > -1){
            Coordinate left_cell = new Coordinate(new Coordinate(parent.getRow(), parent.getCol()-1), parent, parent.getLength()+1);
            if((ship[left_cell.getRow()][left_cell.getCol()] == 1 
            || ship[left_cell.getRow()][left_cell.getCol()] == 3
            || ship[left_cell.getRow()][left_cell.getCol()] == 2
            || ship[left_cell.getRow()][left_cell.getCol()] == 4
            ) && !close_set.containsKey(String.format("%d%d", left_cell.getRow(), left_cell.getCol()))){
                stack.push(left_cell);
            }
        }

        // right
        if(parent.getCol()+1 < ship[parent.getRow()].length){
            Coordinate right_cell = new Coordinate(new Coordinate(parent.getRow(), parent.getCol()+1), parent, parent.getLength()+1);
            if((ship[right_cell.getRow()][right_cell.getCol()] == 1 
            || ship[right_cell.getRow()][right_cell.getCol()] == 3
            || ship[right_cell.getRow()][right_cell.getCol()] == 2
            || ship[right_cell.getRow()][right_cell.getCol()] == 4
            ) && !close_set.containsKey(String.format("%d%d", right_cell.getRow(), right_cell.getCol()))){
                stack.push(right_cell);
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

    // Method to run bot4 with a given Ship object, without demostrating the steps
    public static int run_bot4(Ship ship){
        System.out.println("\nTest Run for bot4: ");
        int count = 0;
        Coordinate next_step = null;
        ArrayList<Coordinate> path_to_goal = new ArrayList<>();
        int[][] ship_layout = ship.getShipArray();
        Date date;

        while(true){
            path_to_goal = Astar_Search(ship);
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
                System.out.println("Bot 4 has put out the fire!");
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
                System.out.println("Bot 4 catches on fire!");
                // break;
                return 0;
            }

            if(!status){
                System.out.println("\nTime Stamp: " + (count+1));
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 4 catches on fire!");
                // break;
                return 0;
            }

            count++;
        }
    }

    // Method to run bot4 with a given Ship object, with demostration on the steps
    public static void run_bot4_demo(Ship ship){
        System.out.println("\nTest Run for bot4: ");
        int count = 0;
        Coordinate next_step = null;
        ArrayList<Coordinate> path_to_goal = new ArrayList<>();
        int[][] ship_layout = ship.getShipArray();
        Date date;

        while(true){
            path_to_goal = Astar_Search(ship);
            date = new Date();
            System.out.println("Time Stamp: " + (count+1) + " " + date);

            if(path_to_goal.isEmpty()){
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
                System.out.println("Bot 4 has put out the fire!");
                break;
            }

            boolean status = ship.getFire().spread_fire(ship);
            Ship.print_layout(ship.getShipArray());

            if(Coordinate.compare_coor_with_list(ship.getFire().getListOfFire(), ship.getBotCoor())){
                System.out.println("\nTime Stamp: " + (count+1));
                ship_layout[ship.getBotCoor().getRow()][ship.getBotCoor().getCol()] = 6;
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 4 catches on fire!");
                break;
            }

            if(!status){
                System.out.println("\nTime Stamp: " + (count+1));
                Ship.print_layout(ship.getShipArray());
                System.out.println("Bot 4 catches on fire!");
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
