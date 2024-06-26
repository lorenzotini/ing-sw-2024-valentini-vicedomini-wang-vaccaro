
package it.polimi.ingsw.gc27.Model.States;


import it.polimi.ingsw.gc27.Commands.*;
import it.polimi.ingsw.gc27.Controller.ClientTest;
import it.polimi.ingsw.gc27.Controller.GameController;
import it.polimi.ingsw.gc27.Controller.GigaController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DrawingStateTest {


    private GameController gc;

    private ClientTest clientTest, clientTest2;

    public  void initializeGame() {

    }


    @Test
    void drawTest() throws InterruptedException {


        clientTest = new ClientTest();
        clientTest.setNextRead("new");
        clientTest.setNextRead("2");
        clientTest.setNextRead("User1");
        clientTest.setNextRead("BLUE");
        clientTest.setUsername("User1");
        GigaController gigaController = new GigaController();
        gigaController.welcomePlayer(clientTest);

        clientTest2=new ClientTest();
        clientTest2.setNextRead("0");
        clientTest2.setNextRead("User1");
        clientTest2.setNextRead("User2");
        clientTest2.setNextRead("RED");
        clientTest2.setUsername("User2");
        gigaController.welcomePlayer(clientTest2);
        new Thread(()->{
            while(true){
                String s = clientTest.nextShows.poll();
                if(s!=null)
                    System.out.println(clientTest.nextShows.poll());
            }
        }).start();

        gc = gigaController.userToGameController("User1");


        Command command = new AddStarterCommand("User1", true);
        assertEquals("User1", command.getPlayerName());
        gc.addCommand(command);
        command = new AddStarterCommand("User2", true);
        gc.addCommand(command);
        command = new ChooseObjectiveCommand("User1", 1);
        assertEquals("User1", command.getPlayerName());
        gc.addCommand(command);
        command = new ChooseObjectiveCommand("User2", 1);
        gc.addCommand(command);
        command = new SendMessageCommand(gc.getPlayer("User1"),"User2", "LAlalalala");
        gc.addCommand(command);


        for(int i = 1; i<19; i++){
            command = new AddCardCommand("User1",1,false,42+i,42+i);
            assertEquals("User1", command.getPlayerName());
            gc.addCommand(command);
            command = new DrawCardCommand("User1", false, false, 0);
            assertEquals("User1", command.getPlayerName());
            gc.addCommand(command);
            command = new AddCardCommand("User2",1,false,42+i,42+i);
            gc.addCommand(command);
            command = new DrawCardCommand("User2", false, false, 0);
            gc.addCommand(command);
        }
        command = new PingCommand();
        command.execute(gc);
        command.getPlayerName();
        System.out.println(gc.getGame().getMarket().getResourceDeck().size());
        while(!gc.getCommands().isEmpty()){

        }
        assertTrue(gc.getGame().getMarket().getResourceDeck().isEmpty());



    }
    @Test
    void drawCardTest() {


    }
}