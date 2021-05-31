package com.tema1.common;

import com.tema1.Players.BasicPlayer;
import com.tema1.Players.BribePlayer;
import com.tema1.Players.GreedyPlayer;
import com.tema1.Players.Player;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

import java.util.ArrayList;

public final class Constants {
    private static final int SIZE = 10;

    public int getItem(final ArrayList<Goods> goods) {
        int count = 0;
        int[] array = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            if (goods.get(i).getType().equals(GoodsType.Legal)) {
                array[goods.get(i).getId()]++;
                count++;
            }
        }
        if (count == 0) {
            goods.sort((g1, g2) -> g2.getProfit() - g1.getProfit());
            return goods.get(0).getId();
        }
        int max = -1;
        count = -1;
        GoodsFactory goodsFactory = GoodsFactory.getInstance();
        for (int i = 0; i < SIZE; i++) {
            if (array[i] > max) {
                max = array[i];
                count = i;
            } else if (array[i] == max) {
                if (goodsFactory.getGoodsById(i).getProfit() > goodsFactory
                        .getGoodsById(count).getProfit()) {
                    max = array[i];
                    count = i;
                } else if (goodsFactory.getGoodsById(i).getProfit() == goodsFactory.
                        getGoodsById(count).getProfit()) {
                    if (goodsFactory.getGoodsById(i).getId() > goodsFactory
                            .getGoodsById(count).getId()) {
                        max = array[i];
                        count = i;
                    }
                }
            }
        }
        return count;
    }
    public ArrayList<Player> initPlayers(final GameInput gameInput) {
        ArrayList<Player> players = new ArrayList<>();
        int nPlayers = gameInput.getPlayerNames().size();
        for (int i = 0; i < nPlayers; i++) {
            if (gameInput.getPlayerNames().get(i).equals("basic")) {
                players.add(i, new BasicPlayer());
            }
            if (gameInput.getPlayerNames().get(i).equals("bribed")) {
                players.add(i, new BribePlayer());
            } else if (gameInput.getPlayerNames().get(i).equals("greedy")) {
                players.add(i, new GreedyPlayer());
            }
        }
        return players;
    }
}
