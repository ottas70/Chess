/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.chess;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author ottovodvarka
 */
public class ChessTimer implements Runnable {

    private final Game game;
    private final Player player;
    private final Label timerLabel;
    
    Timer timer = new Timer();

    public ChessTimer(Game game, Player player, Label timerLabel) {
        this.game = game;
        this.player = player;
        this.timerLabel = timerLabel;
    }

    @Override
    public void run() {        
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (game.getPlayerOnMove() == player) {
                    player.getTime().decrease();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timerLabel.setText(player.getTime().toString());
                        }
                    });
                    if (player.getTime().getSeconds() == 0) {
                        timer.cancel();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                game.setOutOfTime(true);
                            }
                        });
                    }
                }
            }
        }, 0, 1000);
    }
    
    public void stop(){
        timer.cancel();
    }

}
