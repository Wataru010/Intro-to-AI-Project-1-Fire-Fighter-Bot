Intro to AI Project 1
Author: Sihua Zhou NetID: sz583

TO RUN THE CODE:
    If in the situation you (TA) needs to run the code in this project foler follows the steps belows:

--- Platform Checking:
    ----------------------------------------------------------------------------------------------------------------------------------------------
    Make sure you are at directory that is inside Project 1
    > ls
    > README.md       bin             lib             run.sh          src
    then do the following!
    
    Mac or Linux: 
        If you running this java project on a mac or linux machine, I have the shell script include to compile and run the code at once.
    
    Note: if you just download to your local machine or upload to iLab machine(perferrable way, go easy on your computer), you need to 
        run the following command before using the shell script

        > chmod +x ./run.sh

        that add permission for shell script to run, then

        > ./run.sh args . . .

        arguments will explain after platform checking section.

    Windows:
        Apologizing on not having a batch script for windows to run it, but the follow two commands are sufficent to run the code easily.

        > javac -d bin src/*.java
        > java -cp bin Run args . . .

    ----------------------------------------------------------------------------------------------------------------------------------------------

--- Arguments Explanation:
    ----------------------------------------------------------------------------------------------------------------------------------------------
        In commands I mentioned above, there are symbols like args . . . is showing to run the program with options.

        The args . . . consists with 4 arguments.
        - size of the ship.
        - running for demo or simulation for statistics of the performance.
        - (if in demo mode) number of the bot you want to run 1 - 4 inclusive. Bot 1, 2, 3, 4.
            *Note: don't enter value out of bounds*
        - (if in demo mode) type of probability from 0 - 10 inclusive, reprsenting 0, 0.1, 0.2 ... 1.0 of Q value 
            *Note: don't enter value out of bounds*

        The shell scirpt contain default value if insufficent arguments is provided, but please avoid insufficent arguments when running on
        windows.

        For Mac or Linux (shell script): 
            demo mode: 
            > ./run.sh 50 demo 4 3

            non-demo mode: 
            > ./run.sh 50 s 
        
        For Windows:
            demo mode:
            > java -cp bin Run 50 demo 4 3

            non-demo mode:
            > java -cp bin Run 50 s 0 0
        Note: run program after you compile it.

        Note: if you are not running for demo mode any two integer is sufficent for code to run without error.

    ----------------------------------------------------------------------------------------------------------------------------------------------


    