package com.apet2929.test;

import com.apet2929.core.EngineManager;
import com.apet2929.core.ILogic;
import com.apet2929.core.WindowManager;
import com.apet2929.core.utils.Consts;

public class Launcher {

    private static WindowManager window;
    private static TestGame game;


    public static void main(String[] args) {
        window = new WindowManager(Consts.TITLE, 1600, 900, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();

        try{
            engine.start();
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public static WindowManager getWindow(){
        return window;
    }

    public static ILogic getGame() {
        return game;
    }
}
