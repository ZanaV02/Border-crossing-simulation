package Main.Terminals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Main.*;

public class FileWatcher extends Thread
{
    public void run()
    {
        while(true)
        {
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader("terminali.txt"));
                String line = reader.readLine();
                String[] terminalState = line.split("#");
                if("1".equals(terminalState[0]))
                {
                    Main.openedPoliceTerminal1.setVisible(true);
                    Main.closedPoliceTerminal1.setVisible(false);
                    Main.policeTerminal1.setWorking(true);
                }
                else
                {
                    Main.openedPoliceTerminal1.setVisible(false);
                    Main.closedPoliceTerminal1.setVisible(true);
                    Main.policeTerminal1.setWorking(false);
                }
                if("1".equals(terminalState[1]))
                {
                    Main.openedPoliceTerminal2.setVisible(true);
                    Main.closedPoliceTerminal2.setVisible(false);
                    Main.policeTerminal2.setWorking(true);
                }
                else
                {
                    Main.openedPoliceTerminal2.setVisible(false);
                    Main.closedPoliceTerminal2.setVisible(true);
                    Main.policeTerminal2.setWorking(false);
                }
                if("1".equals(terminalState[2]))
                {
                    Main.openedPoliceTerminalTruck.setVisible(true);
                    Main.closedPoliceTerminalTruck.setVisible(false);
                    Main.policeTerminalTruck.setWorking(true);
                }
                else
                {
                    Main.openedPoliceTerminalTruck.setVisible(false);
                    Main.closedPoliceTerminalTruck.setVisible(true);
                    Main.policeTerminalTruck.setWorking(false);
                }
                if("1".equals(terminalState[3]))
                {
                    Main.openedCustomTerminal.setVisible(true);
                    Main.closedCustomTerminal.setVisible(false);
                    Main.customsTerminal.setWorking(true);
                }
                else
                {
                    Main.openedCustomTerminal.setVisible(false);
                    Main.closedCustomTerminal.setVisible(true);
                    Main.customsTerminal.setWorking(false);
                }
                if("1".equals(terminalState[4]))
                {
                    Main.openedCustomTerminalTruck.setVisible(true);
                    Main.closedCustomTerminalTruck.setVisible(false);
                    Main.customsTerminalTruck.setWorking(true);
                }
                else
                {
                    Main.openedCustomTerminalTruck.setVisible(false);
                    Main.closedCustomTerminalTruck.setVisible(true);
                    Main.customsTerminalTruck.setWorking(false);
                }
            }
            catch (IOException e)
            {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }

        }
    }
}
