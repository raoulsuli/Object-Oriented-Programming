package com.tema1.Players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

import java.util.ArrayList;

public class GreedyPlayer extends BasicPlayer {
    public GreedyPlayer() {
        super();
        this.setName("GREEDY");
    }

    public final void greedyPlay() {
        final int maxSize = 8;
        if (this.getSellingBag().size() < maxSize) {
            this.getInventory().sort(Goods::compare);
            for (int i = 0; i < this.getInventory().size(); i++) {
                if (this.getInventory().get(i).getType().equals(GoodsType.Illegal)) {
                    this.getSellingBag().add(this.getInventory().get(i));
                    this.getInventory().clear();
                    return;
                }
            }
        }
    }
    public final void greedySheriff(final ArrayList<Player> players, final GameInput gameInput) {
        for (Player player : players) {
            if (player.getBribe() != 0) {
                this.addCoins(player.getBribe());
                player.addCoins(-player.getBribe());
                player.setBribe(0);
            } else {
                this.checkSellingBag(player, gameInput);
            }
        }
    }
}
