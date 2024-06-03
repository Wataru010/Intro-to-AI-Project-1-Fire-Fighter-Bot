import java.util.ArrayList;
// import java.util.concurrent.TimeUnit;

public class Ship implements Cloneable{

    // Ship object instance variable
    private int[][] ship;
    private int[][] og_ship;
    private ArrayList<Coordinate> path;
    private Fire fire;
    private Coordinate initial_fire;
    private Coordinate button;
    private Coordinate og_bot;
    private Coordinate bot;
    private static final double[] probabilities = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};

    // Ship object constructor
    public Ship(int[][] ship, ArrayList<Coordinate> path, Fire fire, Coordinate initial_fire, Coordinate button, Coordinate og_bot, Coordinate bot){
        this.ship = ship;
        this.path = path;
        this.fire = fire;
        this.initial_fire = initial_fire;
        this.button = button;
        this.og_bot = og_bot;
        this.bot = bot;

        this.og_ship = new int[ship.length][ship.length];
        for(int i = 0; i < ship.length; i++){
            for(int j = 0; j < ship[i].length; j++){
                og_ship[i][j] = ship[i][j];
            }
        }
    }

    // Setters and Getters
    public void setBotCoor(Coordinate new_coor){
        bot = new Coordinate(new_coor.getRow(), new_coor.getCol());
    }

    public int[][] getShipArray(){
        return ship;
    }

    public int[][] getOgShip(){
        return og_ship;
    }

    public ArrayList<Coordinate> getPathList(){
        return path;
    }

    public Fire getFire(){
        return fire;
    }

    public Coordinate getInitialFireCoor(){
        return initial_fire;
    }

    public Coordinate getButtonCoor(){
        return button;
    }

    public Coordinate getOgBotCoor(){
        return og_bot;
    }

    public Coordinate getBotCoor(){
        return bot;
    }

    // Method to reset the ship back to original state with new probability
    public void reset_ship(int type_of_probability){
        for(int i = 0; i < ship.length; i++){
            for(int j = 0; j < ship[i].length; j++){
                ship[i][j] = og_ship[i][j];
            }
        }
        this.fire = new Fire(ship, new Coordinate(initial_fire.getRow(), initial_fire.getCol()), probabilities[type_of_probability]);
        this.bot = new Coordinate(og_bot.getRow(), og_bot.getCol());
    }

    // Clone the current ship into a new Ship Obejct
    public static Ship clone(Ship curr_ship){
        int[][] new_ship_layout = new int[curr_ship.getShipArray().length][curr_ship.getShipArray().length];
        for(int i = 0; i < new_ship_layout.length; i++){
            for(int j = 0; j < new_ship_layout[i].length; j++){
                new_ship_layout[i][j] = curr_ship.getShipArray()[i][j];
            }
        }

        ArrayList<Coordinate> new_path = new ArrayList<>();
        for(Coordinate coor : curr_ship.getPathList()){
            new_path.add(coor);
        }

        ArrayList<Coordinate> new_list_of_fire = new ArrayList<>();
        for(Coordinate coor : curr_ship.getFire().getListOfFire()){
            new_list_of_fire.add(coor);
        }
    
        Fire new_fire = new Fire(new_ship_layout, new_list_of_fire, curr_ship.getFire().getProbability());

        Coordinate new_initial_fire = new Coordinate(curr_ship.getInitialFireCoor().getRow(), curr_ship.getInitialFireCoor().getCol());

        Coordinate new_button = new Coordinate(curr_ship.getButtonCoor().getRow(), curr_ship.getButtonCoor().getCol());

        Coordinate new_og_bot = new Coordinate(curr_ship.getOgBotCoor().getRow(), curr_ship.getOgBotCoor().getCol());

        Coordinate new_bot = new Coordinate(curr_ship.getBotCoor().getRow(), curr_ship.getBotCoor().getCol());

        return new Ship(new_ship_layout, new_path, new_fire, new_initial_fire, new_button, new_og_bot, new_bot);
    }
   
    // Initialize a 2d array representation of ship
    public static int[][] createLayout(int size){
        int[][] ship_layout = new int[size][size];

        for(int i = 0; i < ship_layout.length; i++){
            for(int j = 0; j < ship_layout[i].length; j++){
                ship_layout[i][j] = 0;
            }
        }

        return ship_layout;
    }

    // Method to open blocked cells
    public static ArrayList<Coordinate> initial_cells(int[][] ship){
        // colorize
        // String RESET = "\u001B[0m";
        // String BLUE = "\033[0;34m";
        
        // random generate coordinate for open cells
        int row = (int)(Math.random()*ship.length);
        int col = (int)(Math.random()*ship[0].length);
        
        // ArrayList for actually ship path
        ArrayList<Coordinate> path = new ArrayList<>();

        // set the initial coordinate to open cell
        ship[row][col] = 1;
        Coordinate init_coordinate = new Coordinate(row,col);
        path.add(init_coordinate); //add the path coordinate into path list
        // System.out.println(init_coordinate.getRow() + ":" + init_coordinate.getCol());

        // arraylist to store candidate coordinate that can be opened
        ArrayList<Coordinate> candidate = new ArrayList<>();

        // add candidate blocked cell for the initial opened cell
        check_neighbor_add_candidate(ship, init_coordinate, candidate);

        // print current layout
        // System.out.println("--------------------------------------------------------------------------------------");
        // System.out.println("Path Coordinate:");
        // for (Coordinate coordinate : path) {
        //     System.out.print("("+coordinate.getRow() + ":" + coordinate.getCol()+") ");
        // }
        // System.out.println("\n");

        // System.out.println("Candidate Coordinate:");
        // for (Coordinate coordinate : candidate) {
        //     System.out.print("("+coordinate.getRow() + ":" + coordinate.getCol()+") ");
        // }
        // System.out.println("\n");

        // System.out.println();
        // for(int i = 0; i < ship.length; i++){
        //     for(int j = 0; j < ship[i].length; j++){
        //         // System.out.print(ship[i][j] + " ");
        //         if(ship[i][j] == 1){
        //             System.out.print(BLUE + ship[i][j] + " " + RESET);
        //         }else{
        //             System.out.print(ship[i][j] + " ");
        //         }
        //     }
        //     System.out.println();
        // }
        // System.out.println("--------------------------------------------------------------------------------------");

        // exhausting the candidate by keep opening candidate cells
        while(candidate.size() > 0){
            // randomly pick a candidate cell
            if(candidate.size() > 1){
                // pick a candidate to open
                int index = (int)(Math.random()*candidate.size());

                // set the candidate with value 1 on ship and add to candidate list, then update the openable candidate and remove current candidate
                ship[candidate.get(index).getRow()][candidate.get(index).getCol()] = 1;
                path.add(candidate.get(index));
                check_neighbor_add_candidate(ship, candidate.get(index), candidate);
                candidate.remove(index);

                // create a temperory list to hold candidate that needs to be remove after new blocked cell(candidate) is opened
                ArrayList<Integer> rm_list = new ArrayList<>();
                Coordinate[] cc = new Coordinate[candidate.size()]; // carbon copy list for later copy back to candidate list
                
                // add index of candidate that needs to be remove from the candidate list
                for (int i = 0; i < candidate.size(); i++) {
                    cc[i] = candidate.get(i);
                    if(!check_one_open_neighbor(ship, candidate.get(i))){
                        int rm_index = candidate.indexOf(candidate.get(i));
                        // candidate.remove(rm_index);
                        rm_list.add(rm_index);
                    }
                }

                // set removed candidate to null to prevent being copied
                for (int i = 0; i < rm_list.size(); i++) {
                    cc[(int)rm_list.get(i)] = null;
                }

                // erase the currect candidate
                candidate.clear();

                // add back to candidate list with valid candidates from carbon copy list
                for(int i = 0; i < cc.length; i++){
                    if(cc[i] != null){
                        candidate.add(cc[i]);
                    }
                }
            }else{ // if only one candidate left

                // set the candidate with value 1 on ship and add to candidate list, then update the openable candidate and remove current candidate
                ship[candidate.get(0).getRow()][candidate.get(0).getCol()] = 1;
                path.add(candidate.get(0));
                check_neighbor_add_candidate(ship, candidate.get(0), candidate);
                candidate.remove(0);

                ArrayList<Integer> rm_list = new ArrayList<>();

                for (int i = 0; i < candidate.size(); i++) {
                    if(!check_one_open_neighbor(ship, candidate.get(i))){
                        int rm_index = candidate.indexOf(candidate.get(i));
                        // candidate.remove(rm_index);
                        rm_list.add(rm_index);
                    }
                }

                for (int i = 0; i < rm_list.size(); i++) {
                    candidate.remove((int)rm_list.get(i));
                }
            }

            // print current layout
            // System.out.println("--------------------------------------------------------------------------------------");
            // System.out.println("Path Coordinate:");
            // for (Coordinate coordinate : path) {
            //     System.out.print("("+coordinate.getRow() + ":" + coordinate.getCol()+") ");
            // }
            // System.out.println("\n");

            // System.out.println("Candidate Coordinate:");
            // for (Coordinate coordinate : candidate) {
            //     System.out.print("("+coordinate.getRow() + ":" + coordinate.getCol()+") ");
            // }
            // System.out.println("\n");

            // for(int i = 0; i < ship.length; i++){
            //     for(int j = 0; j < ship[i].length; j++){
            //         // System.out.print(ship[i][j] + " ");
            //         if(ship[i][j] == 1){
            //             System.out.print(BLUE + ship[i][j] + " " + RESET);
            //         }else{
            //             System.out.print(ship[i][j] + " ");
            //         }
            //     }
            //     System.out.println();
            // }
            // System.out.println("--------------------------------------------------------------------------------------");

        }
        return path;
    }

    // Helper method to add candidate for exacrly one open neighor
    public static void check_neighbor_add_candidate(int[][] ship, Coordinate coor, ArrayList<Coordinate> candidate_List){
        // top
        if(coor.getRow()-1 > -1){
            Coordinate top_coor = new Coordinate(coor.getRow()-1, coor.getCol());
            if(check_one_open_neighbor(ship, top_coor) && ship[top_coor.getRow()][top_coor.getCol()] != 1){
                candidate_List.add(top_coor);
            }   
        }
        // bottom
        if(coor.getRow()+1 < ship.length){
            Coordinate bottom_coor = new Coordinate(coor.getRow()+1, coor.getCol());
            if(check_one_open_neighbor(ship, bottom_coor) && ship[bottom_coor.getRow()][bottom_coor.getCol()] != 1){
                candidate_List.add(bottom_coor);
            }
        }
        // left
        if(coor.getCol()-1 > -1){
            Coordinate left_coor = new Coordinate(coor.getRow(), coor.getCol()-1);
            if(check_one_open_neighbor(ship, left_coor) && ship[left_coor.getRow()][left_coor.getCol()] != 1){
                candidate_List.add(left_coor);
            }
        }
        // right
        if(coor.getCol()+1 < ship[coor.getRow()].length){
            Coordinate right_coor = new Coordinate(coor.getRow(), coor.getCol()+1);
            if(check_one_open_neighbor(ship, right_coor) && ship[right_coor.getRow()][right_coor.getCol()] != 1){
                candidate_List.add(right_coor);
            }
        }
    }

    // Helper method to calculate open neighbors
    public static boolean check_one_open_neighbor(int[][] ship, Coordinate candidate){
        if(candidate.getRow() < 0 || candidate.getCol() < 0){
            return false;
        }

        int count = 0;
        // top
        if(candidate.getRow()-1 > -1){
            if(ship[candidate.getRow()-1][candidate.getCol()] == 1){
                count++;
            }
        }
        // bottom
        if(candidate.getRow()+1 < ship.length){
            if(ship[candidate.getRow()+1][candidate.getCol()] == 1){
                count++;
            }
        }
        // left
        if(candidate.getCol()-1 > -1){
            if(ship[candidate.getRow()][candidate.getCol()-1] == 1){
                count++;
            }
        }
        // right
        if(candidate.getCol()+1 < ship[candidate.getRow()].length){
            if(ship[candidate.getRow()][candidate.getCol()+1] == 1){
                count++;
            }
        }

        if(count == 1){
            return true;
        }
        
        return false;
    }

    // Method that finds deadend cells
    public static ArrayList<Coordinate> find_deadends(int[][] ship, ArrayList<Coordinate> path_coordinates){
        ArrayList<Coordinate> deadend_list = new ArrayList<>();

        for(Coordinate coor : path_coordinates){
            if(check_one_open_neighbor(ship, coor)){
                deadend_list.add(coor);
            }
        }
        return deadend_list;
    }

    // Method to open blocked cells and eliminate deadend cells 
    public static ArrayList<Coordinate> eliminate_deadends(int[][] ship, ArrayList<Coordinate> deadend_list, ArrayList<Coordinate> path, ArrayList<Coordinate> possible_deadends,ArrayList<Coordinate> actual_deadends, ArrayList<Coordinate> bridge){

        // create an list to hold 
        ArrayList<Coordinate> candidate = new ArrayList<>();

        // possible deadends
        for(Coordinate coor : deadend_list){
            possible_deadends.add(coor);
        }

        // get the fixed size of the original deadend list for reference
        int deadend_list_size = deadend_list.size();

        while(deadend_list.size() > deadend_list_size/2){
            // randomly pick a deadend to eliminate
            int picked_deadend_index = (int)(Math.random()*deadend_list.size());

            // gathering its neighbors that can be opened
            find_deadend_neighbor_candidates(ship, deadend_list.get(picked_deadend_index), candidate);

            // randomly pick a candidate to open
            int pick_candidate_index = (int)(Math.random()*candidate.size());
            Coordinate curr_candidate = candidate.get(pick_candidate_index);

            // open the candidate and add the path
            ship[curr_candidate.getRow()][curr_candidate.getCol()] = 1;
            bridge.add(curr_candidate);
            path.add(curr_candidate);
            
            // four step that get rid of the deadends if they are connected to the open candidate
            // top 
            if(curr_candidate .getRow()-1 > -1){
                Coordinate top_coor = new Coordinate(curr_candidate.getRow()-1, curr_candidate.getCol());
                int index = index_of_coor(deadend_list, top_coor); // not a deadend if index is -1
                if(index != -1){
                    deadend_list.remove(index);
                }
            }

            // bottom
            if(curr_candidate .getRow()+1 < ship.length){
                Coordinate bottom_coor = new Coordinate(curr_candidate .getRow()+1, curr_candidate .getCol());
                int index = index_of_coor(deadend_list, bottom_coor);
                if(index != -1){
                    deadend_list.remove(index);
                }
            }

            // left 
            if(curr_candidate .getCol()-1 > -1){
                Coordinate left_coor = new Coordinate(curr_candidate .getRow(), curr_candidate .getCol()-1);
                int index = index_of_coor(deadend_list, left_coor);
                if(index != -1){
                    deadend_list.remove(index);
                }
            }

            // rigth
            if(curr_candidate .getCol()+1 < ship[curr_candidate .getRow()].length){
                Coordinate right_coor = new Coordinate(curr_candidate .getRow(), curr_candidate .getCol()+1);
                int index = index_of_coor(deadend_list, right_coor);
                if(index != -1){
                    deadend_list.remove(index);
                }
            }

            // clear up the current candidate list for this deadend
            candidate.clear();
        }   

        // actual deadends
        for(Coordinate coor : deadend_list){
            actual_deadends.add(coor);
        }
        return path;
    }

    // find blocked cell that can be opened as the neighor of the deadends
    public static ArrayList<Coordinate> find_deadend_neighbor_candidates(int[][] ship, Coordinate deadend, ArrayList<Coordinate> candidate){
        // top
        if(deadend.getRow()-1 > -1){
            if(ship[deadend.getRow()-1][deadend.getCol()] != 1 && !compare_coor(candidate, new Coordinate(deadend.getRow()-1, deadend.getCol()))){
                candidate.add(new Coordinate(deadend.getRow()-1, deadend.getCol()));
            }
        }

        // bottom
        if(deadend.getRow()+1 < ship.length){
            if(ship[deadend.getRow()+1][deadend.getCol()] != 1 && !compare_coor(candidate, new Coordinate(deadend.getRow()+1, deadend.getCol())) ){
                candidate.add(new Coordinate(deadend.getRow()+1, deadend.getCol()));
            }
        }

        // left
        if(deadend.getCol()-1 > -1){
            if(ship[deadend.getRow()][deadend.getCol()-1] != 1 && !compare_coor(candidate, new Coordinate(deadend.getRow(), deadend.getCol()-1))){
                candidate.add(new Coordinate(deadend.getRow(), deadend.getCol()-1));
            }
        }

        // right
        if(deadend.getCol()+1 < ship[deadend.getRow()].length){
            if(ship[deadend.getRow()][deadend.getCol()+1] != 1 && !compare_coor(candidate, new Coordinate(deadend.getRow(), deadend.getCol()+1))){
                candidate.add(new Coordinate(deadend.getRow(), deadend.getCol()+1));
            }
        }
        return candidate;
    }

    // return the index positon of a coordinate in the arraylist
    public static int index_of_coor(ArrayList<Coordinate> list, Coordinate coor){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getRow() == coor.getRow() && list.get(i).getCol() == coor.getCol()){
                return i;
            }
        }
        return -1;
    }

    // check existence of a Coordinate in a given ArrayList
    public static boolean compare_coor(ArrayList<Coordinate> list, Coordinate coor){
        for(Coordinate obj : list){
            if(obj.getRow() == coor.getRow() && obj.getCol() == coor.getCol()){
                return true;
            }
        }
        return false;
    }

    // check existence of a Coordinate in a given ArrayList
    public static boolean compare_coor(ArrayList<Coordinate> list, int row, int col){
        for(Coordinate coor : list){
            if(coor.getRow() == row && coor.getCol() == col){
                return true;
            }
        }
        return false;
    }

    // build the ship layout with its information print out
    public static Ship build_ship(String ship_size){
        System.out.println("Building the ship . . .");
        // try{
        //     TimeUnit.SECONDS.sleep(1);
        // }catch(Exception error){
        //     System.out.println("Cannot general the ship!");
        //     System.out.println(error);
        //     return null;
        // }
        if(!(Integer.parseInt(ship_size) > 0)){
            System.out.println("Size need to be greater than 0");
            return null;
        }
        long startTime = System.nanoTime();
        int size = Integer.parseInt(ship_size);
        int[][] ship = createLayout(size);

        ArrayList<Coordinate> ship_path = initial_cells(ship);
        ArrayList<Coordinate> deadends = find_deadends(ship, ship_path);
        
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        System.out.println("Possible Deadends: ");
        for(Coordinate coor : deadends){
            System.out.print("("+coor.getRow() + ":" + coor.getCol()+") ");
        }
        System.out.println("\n");

        // colorize ANSI code
        String RESET = "\u001B[0m";
        String BLUE = "\033[0;34m";
        // String RED = "\033[0;31m";
        String YELLOW = "\033[0;33m";
        String GREEN = "\u001B[32m";

        System.out.println("Initial Layout:");
        for(int i = 0; i < ship.length; i++){
            for(int j = 0; j < ship[i].length; j++){
                if(compare_coor(deadends, i, j)){
                    System.out.print(YELLOW + ship[i][j] + " " + RESET);
                }else{
                    if(ship[i][j] == 1){
                        System.out.print(BLUE + ship[i][j] + " " + RESET);
                    }else{
                        System.out.print(ship[i][j] + " ");
                    }
                }
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        ArrayList<Coordinate> possible_deadends = new ArrayList<>();
        ArrayList<Coordinate> actual_deadends = new ArrayList<>();
        ArrayList<Coordinate> actual_bridges = new ArrayList<>();
        ArrayList<Coordinate> final_path = eliminate_deadends(ship, deadends, ship_path, possible_deadends, actual_deadends, actual_bridges);
        int list_size = 0;

        System.out.println("Possible Deadends: ");
        for(Coordinate coor : possible_deadends){
            System.out.print("("+coor.getRow() + ":" + coor.getCol()+") ");
        }
        list_size = possible_deadends.size();
        System.out.println("\npossible deadends size: "+list_size+"\n");

        System.out.println("Actual Deadends: ");
        for(Coordinate coor : actual_deadends){
            System.out.print("("+coor.getRow() + ":" + coor.getCol()+") ");
        }
        list_size = actual_deadends.size();
        System.out.println("\nactual deadends size: "+list_size+"\n");

        System.out.println("Actual Bridges: ");
        for(Coordinate coor : actual_bridges){
            System.out.print("("+coor.getRow() + ":" + coor.getCol()+") ");
        }
        list_size = actual_bridges.size();
        System.out.println("\nactual bridges size: "+list_size+"\n");

        System.out.println("Final Path: ");
        for(Coordinate coor : final_path){
            System.out.print("("+coor.getRow() + ":" + coor.getCol()+") ");
        }
        list_size = final_path.size();
        System.out.println("\nfinal path size: "+list_size+"\n");

        System.out.println("Final Layout:");
        for(int i = 0; i < ship.length; i++){
            for(int j = 0; j < ship[i].length; j++){
                if(compare_coor(actual_deadends, i, j)){
                    System.out.print(YELLOW + ship[i][j] + " " + RESET);
                }else if(compare_coor(actual_bridges, i, j)){
                    System.out.print(GREEN + ship[i][j] + " " + RESET);
                }else{
                    if(ship[i][j] == 1){
                        System.out.print(BLUE + ship[i][j] + " " + RESET);
                    }else{
                        System.out.print(ship[i][j] + " ");
                    }
                }
            }
            System.out.println();
        }

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        
        // print_layout(ship);
        System.out.println();

        Coordinate button = CreateScene.set_button(ship, final_path);
        Coordinate fire = CreateScene.set_fire(ship, final_path, button);
        Coordinate bot = CreateScene.set_bot(ship, final_path, button, fire);
        print_layout(ship);

        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
        // return new Ship(ship, final_path, null, null);
        // 
        return new Ship(ship, final_path, new Fire(ship, fire, probabilities[0]), new Coordinate(fire.getRow(), fire.getCol()), button, new Coordinate(bot.getRow(), bot.getCol()), bot);
    }

    // Print the ship 2d array for visualization
    public static void print_layout(int[][] ship){
        // Number indication:
        // 1 : path
        // 2 : button catches on fire
        // 3 : button
        // 4 : fire
        // 5 : bot
        // 6 : bot catches on fire
        // 7 : bot successfully arrived at the button and put out the fire

        // colorize ANSI code
        String RESET = "\u001B[0m";
        String BLUE = "\033[0;34m";
        String RED = "\033[0;31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\033[0;33m";
        System.out.println("Ship Layout:");
        for(int i = 0; i < ship.length; i++){
            for(int j = 0; j < ship[i].length; j++){
                if(ship[i][j] == 1){
                    System.out.print(BLUE + ship[i][j] + " " + RESET);
                }else if(ship[i][j] == 3 || ship[i][j] == 7){
                    System.out.print(GREEN + ship[i][j] + " " + RESET);
                }else if(ship[i][j] == 4 || ship[i][j] == 2 || ship[i][j] == 6){
                    System.out.print(RED + ship[i][j] + " " + RESET);
                }else if(ship[i][j] == 5){
                    System.out.print(YELLOW + ship[i][j] + " " + RESET);
                }else{
                    System.out.print(ship[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}