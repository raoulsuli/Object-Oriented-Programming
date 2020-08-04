package com.tema1.Players;


import com.tema1.common.Constants;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

import java.util.ArrayList;

public class BribePlayer extends Player {
    private Constants constants = new Constants();
    public BribePlayer() {
        super();
        this.setName("BRIBED");
    }

    private  void basicStrategy(final ArrayList<Goods> sellingBag) {
        ArrayList<Goods> inventory = this.getInventory();
        int sellingID = constants.getItem(inventory);
        final int illegal = 20;
        if (sellingID >= illegal) {
            final int coin = 4;
            if (this.getCoins() < coin) {
                return;
            }
            sellingBag.add(GoodsFactory.getInstance().getGoodsById(sellingID));
            this.setGoodsDeclaredId(0);
        } else {
            for (Goods goods : inventory) {
                if (goods.getId() == sellingID) {
                    sellingBag.add(goods);
                }
            }
            final int  maxSize = 8;
            if (sellingBag.size() > maxSize) {
                sellingBag.subList(maxSize, sellingBag.size() + 1).clear();
            }
            this.setGoodsDeclaredId(sellingID);
        }
        this.setSellingBag(sellingBag);
        inventory.clear();
    }
    public final void bribePlay(final GameInput gameInput) {
        this.initInventory(gameInput);
        ArrayList<Goods> inventory = this.getInventory();
        ArrayList<Goods> sellingBag = new ArrayList<>();
        inventory.sort(Goods::compare);
        final int maxSize = 8;
        final int lilBribe = 5;
        final int bigBribe = 10;
        int check = 0;
        for (Goods good : inventory) {
            if (good.getType().equals(GoodsType.Illegal) && sellingBag.size() < maxSize) {
                if (good.getPenalty() + check < this.getCoins()) {
                    sellingBag.add(good);
                    check += good.getPenalty();
                }
            }
        }
        if (sellingBag.size() == 0) {
            this.basicStrategy(sellingBag);
            return;
        }
        if (sellingBag.size() <= 2) {
            if (this.getCoins() < lilBribe) { //
                sellingBag.clear();
                this.basicStrategy(sellingBag);
                return;
            }
            this.setBribe(lilBribe);
        } else {
            if (this.getCoins() < bigBribe) { //
                if (this.getCoins() >= lilBribe) {
                    int s = sellingBag.size();
                    while (s > 2) {
                        sellingBag.remove(s - 1);
                        s--;
                    }
                    this.setBribe(lilBribe);
                } else {
                    sellingBag.clear();
                    this.basicStrategy(sellingBag);
                    return;
                }
            } else {
                this.setBribe(bigBribe);
            }
        }
        int money = 0;
        if (sellingBag.size() < maxSize) {
            for (Goods good : sellingBag) {
                money += good.getPenalty();
            }
        }
        int w = 0;
        while (sellingBag.size() < maxSize && w < maxSize) {
            if (inventory.get(w).getType().equals(GoodsType.Legal)) {
                if (this.getCoins() - money - inventory.get(w).getPenalty() > 0) {
                    sellingBag.add(inventory.get(w));
                    money += inventory.get(w).getPenalty();
                }
            }
            w++;
        }
        this.setGoodsDeclaredId(0);
        this.setSellingBag(sellingBag);
        inventory.clear();
    }

    public final void bribeSheriffPlay(final ArrayList<Player> players, final GameInput gameInput) {
        if (this.getCoins() == 0) {
            return;
        }
        final int minCoin = 16;
        if (getCoins() < minCoin) {
            return;
        }
        for (int i = 0; i < players.size(); i++) {
            if (players.size() == 2 && this.getInitID() == players.get(i).getInitID() && i == 0) {
                this.checkSellingBag(players.get(1), gameInput);
            } else if (players.size() == 2 && this.getInitID() == players.get(i)
                    .getInitID() && i == 1) {
                this.checkSellingBag(players.get(0), gameInput);
            } else {
                if (this.getInitID() == players.get(i).getInitID()) {
                    if (i == 0) {
                        this.checkSellingBag(players.get(players.size() - 1), gameInput);
                        this.checkSellingBag(players.get(1), gameInput);
                    } else if (i == players.size() - 1) {
                        this.checkSellingBag(players.get(0), gameInput);
                        this.checkSellingBag(players.get(i - 1), gameInput);
                    } else {
                        this.checkSellingBag(players.get(i - 1), gameInput);
                        this.checkSellingBag(players.get(i + 1), gameInput);
                    }
                }
            }
        }
        for (Player player : players) {
            if (player.getBribe() != 0 && this != player) {
                this.addCoins(player.getBribe());
                player.addCoins(-player.getBribe());
                player.setBribe(0);
            }
        }
    }
}
