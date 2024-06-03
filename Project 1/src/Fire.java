import java.util.ArrayList;

public class Fire {
    // Fire object instance variable
    private int[][] ship;
    private ArrayList<Coordinate> list_of_fire = new ArrayList<>();
    private double probability;

    // constructor to create a fire object (for initialization)
    public Fire(int[][] ship, Coordinate initial_fire, double probability){
        this.ship = ship;
        this.list_of_fire.add(initial_fire);
        this.probability = probability;
    }

    // overload constructor to create a fire object (for duplicating a Fire object)
    public Fire(int[][] ship, ArrayList<Coordinate> list_of_fire, double probability){
        this.ship = ship;
        this.list_of_fire = list_of_fire;
        this.probability = probability;
    }

    // Getters
    public ArrayList<Coordinate> getListOfFire(){
        return list_of_fire;
    }

    public double getProbability(){
        return probability;
    }

    // Method to spread fire in the ship at a unit of one time stamp
    public boolean spread_fire(Ship ship_obj){
        int size = list_of_fire.size();
        for(int i = 0; i < size; i++){
            // top
            if(list_of_fire.get(i).getRow()-1 > -1){
                Coordinate top_cell = new Coordinate(list_of_fire.get(i).getRow()-1, list_of_fire.get(i).getCol());

                if(ship[top_cell.getRow()][top_cell.getCol()] == 1 || ship[top_cell.getRow()][top_cell.getCol()] == 3 || ship[top_cell.getRow()][top_cell.getCol()] == 5){
                    int K_value = count_fire_neighbor(top_cell);
                    double predict_probability = 1 - Math.pow((1 - probability), K_value);
                    double actual_probability = Math.random();
                    if(actual_probability < predict_probability){
                        if(top_cell.compare_coor(ship_obj.getButtonCoor())){
                            ship[list_of_fire.get(i).getRow()-1][list_of_fire.get(i).getCol()] = 2;
                        }else{
                            ship[list_of_fire.get(i).getRow()-1][list_of_fire.get(i).getCol()] = 4;
                        }
                        list_of_fire.add(top_cell);

                        if(top_cell.compare_coor(ship_obj.getBotCoor())){
                            ship[list_of_fire.get(i).getRow()-1][list_of_fire.get(i).getCol()] = 6;
                            return false;
                        }
                    }
                }
            }

            // bottom
            if(list_of_fire.get(i).getRow()+1 < ship.length){
                Coordinate bottom_cell = new Coordinate(list_of_fire.get(i).getRow()+1, list_of_fire.get(i).getCol());
                
                if(ship[bottom_cell.getRow()][bottom_cell.getCol()] == 1 || ship[bottom_cell.getRow()][bottom_cell.getCol()] == 3 || ship[bottom_cell.getRow()][bottom_cell.getCol()] == 5){
                    int K_value = count_fire_neighbor(bottom_cell);
                    double predict_probability = 1 - Math.pow((1 - probability), K_value);
                    double actual_probability = Math.random();
                    if(actual_probability < predict_probability){
                        if(bottom_cell.compare_coor(ship_obj.getButtonCoor())){
                            ship[list_of_fire.get(i).getRow()+1][list_of_fire.get(i).getCol()] = 2;
                        }else{
                            ship[list_of_fire.get(i).getRow()+1][list_of_fire.get(i).getCol()] = 4;
                        }
                        list_of_fire.add(bottom_cell);

                        if(bottom_cell.compare_coor(ship_obj.getBotCoor())){
                            ship[list_of_fire.get(i).getRow()+1][list_of_fire.get(i).getCol()] = 6;
                            return false;
                        }
                    }
                }
            }

            // left
            if(list_of_fire.get(i).getCol()-1 > -1){
                Coordinate left_cell = new Coordinate(list_of_fire.get(i).getRow(),list_of_fire.get(i).getCol()-1);
                
                if(ship[left_cell.getRow()][left_cell.getCol()] == 1 || ship[left_cell.getRow()][left_cell.getCol()] == 3 || ship[left_cell.getRow()][left_cell.getCol()] == 5){
                    int K_value = count_fire_neighbor(left_cell);
                    double predict_probability = 1 - Math.pow((1 - probability), K_value);
                    double actual_probability = Math.random();
                    if(actual_probability < predict_probability){
                        if(left_cell.compare_coor(ship_obj.getButtonCoor())){
                            ship[list_of_fire.get(i).getRow()][list_of_fire.get(i).getCol()-1] = 2;
                        }else{
                            ship[list_of_fire.get(i).getRow()][list_of_fire.get(i).getCol()-1] = 4;
                        }
                        list_of_fire.add(left_cell);

                        if(left_cell.compare_coor(ship_obj.getBotCoor())){
                            ship[list_of_fire.get(i).getRow()][list_of_fire.get(i).getCol()-1] = 6;
                            return false;
                        }
                    }
                }
            }

            // right
            if(list_of_fire.get(i).getCol()+1 < ship[list_of_fire.get(i).getRow()].length){
                Coordinate right_cell = new Coordinate(list_of_fire.get(i).getRow(),list_of_fire.get(i).getCol()+1);
                
                if(ship[right_cell.getRow()][right_cell.getCol()] == 1 || ship[right_cell.getRow()][right_cell.getCol()] == 3 || ship[right_cell.getRow()][right_cell.getCol()] == 5){
                    int K_value = count_fire_neighbor(right_cell);
                    double predict_probability = 1 - Math.pow((1 - probability), K_value);
                    double actual_probability = Math.random();
                    if(actual_probability < predict_probability){
                        if(right_cell.compare_coor(ship_obj.getButtonCoor())){
                            ship[list_of_fire.get(i).getRow()][list_of_fire.get(i).getCol()+1] = 2;
                        }else{
                            ship[list_of_fire.get(i).getRow()][list_of_fire.get(i).getCol()+1] = 4;
                        }
                        list_of_fire.add(right_cell);

                        if(right_cell.compare_coor(ship_obj.getBotCoor())){
                            ship[list_of_fire.get(i).getRow()][list_of_fire.get(i).getCol()+1] = 6;
                            return false;
                        }
                        
                    }
                }
            }
        }
        return true;
    }

    // Calculate K value
    private int count_fire_neighbor(Coordinate open_cell){
        int count = 0;
        // top
        if(open_cell.getRow()-1 > -1){
            if(ship[open_cell.getRow()-1][open_cell.getCol()] == 4 || ship[open_cell.getRow()-1][open_cell.getCol()] == 2){
                count++;
            }
        }

        // bottom
        if(open_cell.getRow()+1 < ship.length){
            if(ship[open_cell.getRow()+1][open_cell.getCol()] == 4 || ship[open_cell.getRow()+1][open_cell.getCol()] == 2){
                count++;
            }
        }

        // left
        if(open_cell.getCol()-1 > -1){
            if(ship[open_cell.getRow()][open_cell.getCol()-1] == 4 || ship[open_cell.getRow()][open_cell.getCol()-1] == 2){
                count++;
            }
        }

        // right
        if(open_cell.getCol()+1 < ship[open_cell.getRow()].length){
            if(ship[open_cell.getRow()][open_cell.getCol()+1] == 4 || ship[open_cell.getRow()][open_cell.getCol()+1] == 2){
                count++;
            }
        }

        return count;
    }

    // Count adjacent cells that is next to fire cells
    public ArrayList<Coordinate> count_open_cell_adjacent_to_fire(){
        ArrayList<Coordinate> open_cell_adjcent_with_fire = new ArrayList<>();
        for(Coordinate fire_cell : list_of_fire){
             // top
            if(fire_cell.getRow()-1 > -1){
                if(ship[fire_cell.getRow()-1][fire_cell.getCol()] == 1){
                    open_cell_adjcent_with_fire.add(new Coordinate(fire_cell.getRow()-1, fire_cell.getCol()));
                }
            }

            // bottom
            if(fire_cell.getRow()+1 < ship.length){
                if(ship[fire_cell.getRow()+1][fire_cell.getCol()] == 1){
                    open_cell_adjcent_with_fire.add(new Coordinate(fire_cell.getRow()+1, fire_cell.getCol()));
                }
            }

            // left
            if(fire_cell.getCol()-1 > -1){
                if(ship[fire_cell.getRow()][fire_cell.getCol()-1] == 1){
                    open_cell_adjcent_with_fire.add(new Coordinate(fire_cell.getRow(), fire_cell.getCol()-1));
                }
            }

            // right
            if(fire_cell.getCol()+1 < ship[fire_cell.getRow()].length){
                if(ship[fire_cell.getRow()][fire_cell.getCol()+1] == 1){
                    open_cell_adjcent_with_fire.add(new Coordinate(fire_cell.getRow(), fire_cell.getCol()+1));
                }
            }
        }
        return open_cell_adjcent_with_fire;
    }
}
