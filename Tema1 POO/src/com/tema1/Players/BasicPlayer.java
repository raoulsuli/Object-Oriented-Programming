package com.tema1.Players;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.main.GameInput;
import java.util.ArrayList;
import com.tema1.common.Constants;

public class BasicPlayer extends Player {
    private Constants constants = new Constants();

    public BasicPlayer() {
        super();
        this.setName("BASIC");
    }

    public final void basicPlay(final GameInput gameInput) {
        this.initInventory(gameInput);
        ArrayList<Goods> inventory = this.getInventory();
        int sellingID = constants.getItem(inventory);
        ArrayList<Goods> sellingBag = new ArrayList<>();
        final int illegal = 20;
        if (sellingID >= illegal) {
            GoodsFactory goodsFactory =  GoodsFactory.getInstance();
            Goods sellingItem = goodsFactory.getGoodsById(sellingID);
            final int coin = 4;
            if (this.getCoins() < coin) {
                return;
            }
            sellingBag.add(sellingItem);
            this.getInventory().remove(0);
            this.setGoodsDeclaredId(0);
        } else {
            for (Goods goods : inventory) {
                if (goods.getId() == sellingID) {
                    sellingBag.add(goods);
                }
            }
            final int  bagSize = 8;
            if (sellingBag.size() > bagSize) {
                sellingBag.subList(bagSize, sellingBag.size() + 1).clear();
            }
            this.setGoodsDeclaredId(sellingID);
        }
        this.setSellingBag(sellingBag);
        if (this.getName().equals("GREEDY")) {
            return;
        }
        inventory.clear();
    }


    public final void sheriffPlayBasic(final ArrayList<Player> players, final GameInput gameInput) {
        for (Player player : players) {
            final int minCoins = 16;
            if (this.getCoins() < minCoins) {
                return;
            }
            this.checkSellingBag(player, gameInput);
        }
    }
}
