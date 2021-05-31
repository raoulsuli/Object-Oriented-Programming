package com.tema1.goods;

public class Goods {
    private final int id;
    private final GoodsType type;
    private final int profit;
    private final int penalty;


    public Goods(final int id, final GoodsType type, final int profit, final int penalty) {
        this.id = id;
        this.type = type;
        this.profit = profit;
        this.penalty = penalty;
    }

    public final int getId() {
        return id;
    }

    public final GoodsType getType() {
        return type;
    }

    public final int getProfit() {
        return profit;
    }

    public final int getPenalty() {
        return penalty;
    }
    public final int compare(final Goods g1) {
        if (g1.getProfit() - this.getProfit() != 0) {
            return g1.getProfit() - this.getProfit();
        } else {
            return g1.getId() - this.getId();
        }
    }
}

