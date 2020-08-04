package com.tema1.Players;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.main.GameInput;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final int COINS = 80;
    private static final int ITEMS = 10;
    private int isSheriff;
    private int coins;
    private String name;
    private int goodsDeclaredId;
    private ArrayList<Goods> inventory;
    private ArrayList<Goods> sellingBag;
    private ArrayList<Goods> soldItems;
    private int bribe;
    private int initID;

    Player() {
        this.coins = COINS;
        this.isSheriff = 0;
        this.goodsDeclaredId = -1;
        this.inventory = null;
        this.sellingBag = null;
        this.soldItems = null;
        this.bribe = 0;
        this.initID = -1;
    }

    public final void setInitID(final int initID) {
        this.initID = initID;
    }

    public final int getInitID() {
        return initID;
    }

    public final ArrayList<Goods> getSoldItems() {
        return soldItems;
    }

    final int getBribe() {
        return bribe;
    }

    final void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    final int getGoodsDeclaredId() {
        return goodsDeclaredId;
    }

    final void setGoodsDeclaredId(final int goodsDeclaredId) {
        this.goodsDeclaredId = goodsDeclaredId;
    }

    public final int isSheriff() {
        return isSheriff;
    }

    public final void setSheriff(final int value) {
        isSheriff = value;
    }

    public final int getCoins() {
        return coins;
    }

    public final void addCoins(final int extraCoins) {
        this.coins += extraCoins;
    }

    public final String getName() {
        return name;
    }

    final void setName(final String name) {
        this.name = name;
    }

    final void setSellingBag(final ArrayList<Goods> sellingBag) {
        this.sellingBag = sellingBag;
    }

     final void setInventory(final ArrayList<Goods> inventory) {
        this.inventory = inventory;
    }

    public final ArrayList<Goods> getSellingBag() {
        return sellingBag;
    }

    final ArrayList<Goods> getInventory() {
        return inventory;
    }

    public final int  compare(final Player p1) {
        return p1.getCoins() - this.getCoins();
    }

    private  ArrayList<Goods> createBag(final List<Integer> list) {
        ArrayList<Goods> bag = new ArrayList<>();
        for (int i = 0; i < ITEMS; i++) {
            GoodsFactory goodsFactory = GoodsFactory.getInstance();
            Goods initGood = goodsFactory.getGoodsById(list.get(i));
            Goods good =  new Goods(initGood.getId(), initGood.getType(),
                    initGood.getProfit(), initGood.getPenalty());
            bag.add(good);
        }
        list.subList(0, ITEMS).clear();
        return bag;
    }
    final void initInventory(final GameInput gameInput) {
        this.setInventory(this.createBag(gameInput.getAssetIds()));
    }

    final void checkSellingBag(final Player player, final GameInput gameInput) {
        if (player.getSellingBag() == null || player.isSheriff == 1) {
            return;
        }
        ArrayList<Goods> soldBag = player.getSellingBag();
        int penalty = 0;
        for (int i = 0; i < soldBag.size(); i++) {
            if (soldBag.get(i).getId() != player.getGoodsDeclaredId() || soldBag.get(i).
                    getType().equals(GoodsType.Illegal)) {
                penalty -= soldBag.get(i).getPenalty();
                gameInput.getAssetIds().add(soldBag.get(i).getId());
                soldBag.remove(i);
                i--;
            }
        }
        if (penalty != 0) {
            player.addCoins(penalty);
            this.addCoins(-penalty);
            player.setBribe(0);
        } else {
            if (soldBag.size() != 0) {
                penalty = soldBag.size() * soldBag.get(0).getPenalty();
                player.addCoins(penalty);
                this.addCoins(-penalty);
            }
        }
    }
//
    public final void updateSoldItems() {
        ArrayList<Goods> items = new ArrayList<>();
        if (this.getSoldItems() != null) {
            items.addAll(this.soldItems);
        }
        items.addAll(this.sellingBag);
        this.soldItems = items;
        this.sellingBag.clear();
    }
}
