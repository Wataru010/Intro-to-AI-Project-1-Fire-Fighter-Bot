// import java.util.concurrent.TimeUnit;
// import java.util.ArrayList;

public class Run {
    public static void main(String[] args){
        // Timers
        long startTime = 0;
        long endTime = 0;
        long totalTime = 0;

        long total_startTime = 0;
        long total_endTime = 0;
        long total_totalTime = 0;

        if(!args[1].equalsIgnoreCase("demo")){
             // Running simulation for all 4 bots and get data back (approx. 4400 times in total)
            total_startTime = System.nanoTime();

            int[][] bot_success_rate = new int[4][11];
            
            int[][] bot1_result = new int[100][11];
            int[][] bot2_result = new int[100][11];
            int[][] bot3_result = new int[100][11];
            int[][] bot4_result = new int[100][11];


            for(int j = 0; j < 100; j++){
                Ship ship = Ship.build_ship(args[0]);
                for(int i = 0; i < 11; i++){
                    startTime = System.nanoTime();
                    System.out.println("\n#"+j+": " + "#"+i+":");
                    ship.reset_ship(i);
                    bot1_result[j][i] = Bot1.run_bot1(ship);
                    endTime = System.nanoTime();
                    totalTime = endTime - startTime;
                    System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
                }

                for(int i = 0; i < 11; i++){
                    startTime = System.nanoTime();
                    System.out.println("\n#"+j+": " + "#"+i+":");
                    ship.reset_ship(i);
                    bot2_result[j][i] = Bot2.run_bot2(ship);
                    endTime = System.nanoTime();
                    totalTime = endTime - startTime;
                    System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
                }

                for(int i = 0; i < 11; i++){
                    startTime = System.nanoTime();
                    System.out.println("\n#"+j+": " + "#"+i+":");
                    ship.reset_ship(i);
                    bot3_result[j][i] = Bot3.run_bot3(ship);
                    endTime = System.nanoTime();
                    totalTime = endTime - startTime;
                    System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
                }

                for(int i = 0; i < 11; i++){
                    startTime = System.nanoTime();
                    System.out.println("\n#"+j+": " + "#"+i+":");
                    ship.reset_ship(i);
                    bot4_result[j][i] = Bot4.run_bot4(ship);
                    endTime = System.nanoTime();
                    totalTime = endTime - startTime;
                    System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
                }
            }

            for(int i = 0; i < 11; i++){
                int count = 0;
                for(int j = 0; j < 100; j++){
                    if(bot1_result[j][i] == 1){
                        count++;
                    }
                }
                bot_success_rate[0][i] = (count);
            }

            for(int i = 0; i < 11; i++){
                int count = 0;
                for(int j = 0; j < 100; j++){
                    if(bot2_result[j][i] == 1){
                        count++;
                    }
                }
                bot_success_rate[1][i] = (count);
            }

            for(int i = 0; i < 11; i++){
                int count = 0;
                for(int j = 0; j < 100; j++){
                    if(bot3_result[j][i] == 1){
                        count++;
                    }
                }
                bot_success_rate[2][i] = (count);
            }

            for(int i = 0; i < 11; i++){
                int count = 0;
                for(int j = 0; j < 100; j++){
                    if(bot4_result[j][i] == 1){
                        count++;
                    }
                }
                bot_success_rate[3][i] = (count);
            }
            
            System.out.println("\nResult: ");

            for(int i = 0; i < bot_success_rate.length; i++){
                System.out.print("Bot " + (i+1)+ ": ");
                for(int j = 0; j < bot_success_rate[i].length; j++){
                    System.out.print(bot_success_rate[i][j] + " ");
                }
                System.out.println();
            }
        }else{
            if(Integer.parseInt(args[2]) == 1){
                // BOT1
                Ship ship = Ship.build_ship(args[0]);
                startTime = System.nanoTime();
                ship.reset_ship(Integer.parseInt(args[3]));
                Bot1.run_bot1_demo(ship);
                endTime = System.nanoTime();
                totalTime = endTime - startTime;
                System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
            }else if(Integer.parseInt(args[2]) == 2){
                // BOT2
                Ship ship = Ship.build_ship(args[0]);
                startTime = System.nanoTime();
                ship.reset_ship(Integer.parseInt(args[3]));
                Bot2.run_bot2_demo(ship);
                endTime = System.nanoTime();
                totalTime = endTime - startTime;
                System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
            }else if(Integer.parseInt(args[2]) == 3){
                // BOT3
                Ship ship = Ship.build_ship(args[0]);
                startTime = System.nanoTime();
                ship.reset_ship(Integer.parseInt(args[3]));
                Bot3.run_bot3_demo(ship);
                endTime = System.nanoTime();
                totalTime = endTime - startTime;
                System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
            }else if(Integer.parseInt(args[2]) == 4){
                // BOT4
                Ship ship = Ship.build_ship(args[0]);
                startTime = System.nanoTime();
                ship.reset_ship(Integer.parseInt(args[3]));
                Bot4.run_bot4_demo(ship);
                endTime = System.nanoTime();
                totalTime = endTime - startTime;
                System.out.printf("Time cost: %.2f seconds\n",totalTime/Math.pow(10, 9));
            }
        }
        total_endTime = System.nanoTime();
        total_totalTime = total_endTime - total_startTime;
        System.out.printf("Time cost: %.2f seconds\n",total_totalTime/Math.pow(10, 9));
    }
}