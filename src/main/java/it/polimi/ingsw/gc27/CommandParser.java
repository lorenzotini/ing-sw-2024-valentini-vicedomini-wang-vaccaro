package it.polimi.ingsw.gc27;

public class CommandParser {
    public static Object[] parseCommand(String command) {
        String[] parts = command.split(" ");

        // handle addCard command
        if (parts[0].equalsIgnoreCase("addCard")) {
            if (parts.length != 5) {
                throw new IllegalArgumentException("Invalid command");
            }
            int cardIndex = Integer.parseInt(parts[1]);
            String face = parts[2];
            if (!face.equalsIgnoreCase("front") && !face.equalsIgnoreCase("back")) {
                throw new IllegalArgumentException("Invalid face value");
            }
            int x = Integer.parseInt(parts[3]);
            int y = Integer.parseInt(parts[4]);
            return new Object[]{parts[0], cardIndex, face, x, y};
            //return type:{String, int, String, int, int}
        }
        // handle drawGoldCard commands
        else if (parts[0].equalsIgnoreCase("drawGoldCard")) {
            if (parts.length < 2 || parts.length > 3) {
                throw new IllegalArgumentException("Invalid command");
            }
            String choice = parts[1];
            if (!choice.equalsIgnoreCase("deck") && !choice.equalsIgnoreCase("card")) {
                throw new IllegalArgumentException("Invalid choice value");
            }
            if (choice.equalsIgnoreCase("card") && parts.length != 3) {
                throw new IllegalArgumentException("Index is required when choice is 'card'");
            }
            int index = choice.equalsIgnoreCase("card") ? Integer.parseInt(parts[2]) : -1;
            return new Object[]{parts[0], choice, index};
            //return type:{String, String, int}
        }
        // handle drawResourceCard commands
        else if (parts[0].equalsIgnoreCase("drawResourceCard")) {
            if (parts.length < 2 || parts.length > 3) {
                throw new IllegalArgumentException("Invalid command");
            }
            String choice = parts[1];
            if (!choice.equalsIgnoreCase("deck") && !choice.equalsIgnoreCase("card")) {
                throw new IllegalArgumentException("Invalid choice value");
            }
            if (choice.equalsIgnoreCase("card") && parts.length != 3) {
                throw new IllegalArgumentException("Index is required when choice is 'card'");
            }
            int index = choice.equalsIgnoreCase("card") ? Integer.parseInt(parts[2]) : -1;
            return new Object[]{parts[0], choice, index};
            //return type:{String, String, int}
        }else if (parts[0].equalsIgnoreCase("welcomeplayer")){
            return new Object[]{parts[0]};
        }
        else if(parts[0].equalsIgnoreCase("askstarter")){
            return new Object[]{parts[0]};
        }else{

            throw new IllegalArgumentException("Invalid command, Modify Command Parser");
        }
    }
    public static Object[] parseCommandFromServer(String command){
        String[] parts = command.split(" ");

        if(parts[0].equals("show") ||parts[0].equals("setUsername")){
            return new Object[]{parts[0], command.substring(parts[0].length())};
        }else if (parts[0].equalsIgnoreCase("read")) {
            return new Object[]{parts[0]};
        }
        else if (parts[0].equalsIgnoreCase("runCli")){
            return new Object[]{parts[0]};
        }
        else {
            throw new IllegalArgumentException("Invalid comand, add one");
        }
    }
}