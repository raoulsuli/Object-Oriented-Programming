package com.tema1.main;

import com.tema1.Players.BasicPlayer;
import com.tema1.Players.BribePlayer;
import com.tema1.Players.GreedyPlayer;
import com.tema1.Players.Player;
import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.goods.LegalGoods;

import java.util.ArrayList;

final class Game {
    private Constants constants = new Constants();

    Game() { }

    private void updateScore(final ArrayList<Player> players) {
        for (Player player : players) {
            if (player.getSoldItems() != null) {
                for (int j = 0; j < player.getSoldItems().size(); j++) {
                    player.addCoins(player.getSoldItems().get(j).getProfit());
                    if (player.getSoldItems().get(j).getType().equals(GoodsType.Illegal)) {
                        int id = player.getSoldItems().get(j).getId();
                        GoodsFactory goodsFactory = GoodsFactory.getInstance();
                        final int ilGood0 = 20;
                        final int ilGood1 = 21;
                        final int ilGood2 = 22;
                        final int ilGood3 = 23;
                        final int ilGood4 = 24;
                        final int cheese = 1;
                        final int chicken = 3;
                        final int bread = 2;
                        final int wine = 7;
                        final int tomato = 4;
                        final int potato = 6;
                        final int three = 3;
                        final int two = 2;
                        final int four = 4;
                        Goods initGood;
                        if (id == ilGood0) {
                            initGood = goodsFactory.getGoodsById(cheese);
                            for (int z = 0; z < three; z++) {
                                player.getSoldItems().add(initGood);
                            }
                        }
                        if (id == ilGood1) {
                            initGood = goodsFactory.getGoodsById(chicken);
                            for (int z = 0; z < two; z++) {
                                player.getSoldItems().add(initGood);
                            }
                        }
                        if (id == ilGood2) {
                            initGood = goodsFactory.getGoodsById(bread);
                            for (int z = 0; z < two; z++) {
                                player.getSoldItems().add(initGood);
                            }
                        }
                        if (id == ilGood3) {
                            initGood = goodsFactory.getGoodsById(wine);
                            for (int z = 0; z < four; z++) {
                                player.getSoldItems().add(initGood);
                            }
                        }
                        if (id == ilGood4) {
                            initGood = goodsFactory.getGoodsById(chicken);
                            player.getSoldItems().add(initGood);
                            initGood = goodsFactory.getGoodsById(tomato);
                            for (int z = 0; z < two; z++) {
                                player.getSoldItems().add(initGood);
                            }
                            initGood = goodsFactory.getGoodsById(potato);
                            for (int z = 0; z < three; z++) {
                                player.getSoldItems().add(initGood);
                            }
                        }
                    }
                }
                for (int i = 0; i < player.getSoldItems().size(); i++) {
                    if (player.getSoldItems().get(i).getType().equals(GoodsType.Illegal)) {
                       player.getSoldItems().remove(i);
                       i--;
                    }
                }
            }
        }
    }
    private void updateBonus(final ArrayList<Player> players) {
        GoodsFactory goodsFactory = GoodsFactory.getInstance();
        int curr;
        int max;
        Player aux;
        final int totalItems = 9;
        for (int i = 0; i <= totalItems; i++) {
            for (int j = 0; j < 2; j++) {
                max = 0;
                aux = null;
                for (Player player : players) {
                    curr = 0;
                    if (player.getSoldItems() != null) {
                        for (int w = 0; w < player.getSoldItems().size(); w++) {
                            if (player.getSoldItems().get(w).getId() == i) {
                                curr++;
                            }
                        }
                    }
                    if (curr > max) {
                        max = curr;
                        aux = player;
                    }
                }
                if (max != 0 && j == 0) {
                    Goods initGood = goodsFactory.getGoodsById(i);
                    aux.addCoins(((LegalGoods) initGood).getKingBonus());
                    for (int k = 0; k < aux.getSoldItems().size(); k++) {
                        if (aux.getSoldItems().get(k).getId() == i) {
                            aux.getSoldItems().remove(k);
                            k--;
                        }
                    }
                } else if (max != 0) {
                    Goods initGood = goodsFactory.getGoodsById(i);
                    aux.addCoins(((LegalGoods) initGood).getQueenBonus());
                    for (int k = 0; k < aux.getSoldItems().size(); k++) {
                        if (aux.getSoldItems().get(k).getId() == i) {
                            aux.getSoldItems().remove(k);
                            k--;
                        }
                    }
                }
            }
        }
    }
    private void printOutput(final ArrayList<Player> players) {
        players.sort(Player::compare);
        for (Player player : players) {
            System.out.println(player.getInitID() + " " + player.
                    getName() + " " + player.getCoins());
        }
    }

    void play(final GameInput gameInput) {
        int nRounds = gameInput.getRounds();
        ArrayList<Player> players = constants.initPlayers(gameInput);
        int nPlayers = gameInput.getPlayerNames().size();
        for (int i = 0; i < nPlayers; i++) {
            players.get(i).setInitID(i);
        }
        Player sheriff;
        for (int i = 1; i <= nRounds; i++) {
            for (int j = 0; j < nPlayers; j++) {
                players.get(j).setSheriff(1);
                sheriff = players.get(j);
                for (int k = 0; k < nPlayers; k++) {
                    if (players.get(k).getName().equals("BASIC") && players
                            .get(k).isSheriff() == 0) {
                        BasicPlayer basicPlayer = (BasicPlayer) players.get(k);
                        basicPlayer.basicPlay(gameInput);
                    }
                    if (players.get(k).getName().equals("GREEDY") && players
                            .get(k).isSheriff() == 0) {
                        GreedyPlayer greedyPlayer = (GreedyPlayer) players.get(k);
                        greedyPlayer.basicPlay(gameInput);
                        if (i % 2 == 0) {
                            greedyPlayer.greedyPlay();
                        }
                    }
                    if (players.get(k).getName().equals("BRIBED") && players
                            .get(k).isSheriff() == 0) {
                        BribePlayer bribePlayer = (BribePlayer) players.get(k);
                        bribePlayer.bribePlay(gameInput);
                    }
                    //TODO COMPLETE FOR BRIBED
                }
                    if (sheriff.getName().equals("BASIC")) {
                        ((BasicPlayer) sheriff).sheriffPlayBasic(players, gameInput);
                    }
                    if (sheriff.getName().equals("GREEDY")) {
                        ((GreedyPlayer) sheriff).greedySheriff(players, gameInput);
                    }
                if (sheriff.getName().equals("BRIBED")) {
                    ((BribePlayer) sheriff).bribeSheriffPlay(players, gameInput);
                }
                //complete for other players^^^^^ bribe sheriff
                    for (Player player:players) {
                        if (player.getSellingBag() != null && !player.getSellingBag().isEmpty()) {
                            player.updateSoldItems();
                        }
                    }
                    players.get(j).setSheriff(0);
            }
        }
        updateScore(players);
        updateBonus(players);
        printOutput(players);
   }
}
