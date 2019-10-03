package by.it.shamuradova.jd02_03;

import java.util.List;

public class Cashier implements Runnable {
    int num;

    private String name;

    private double revenue=0;

    public double getRevenue() {
        return this.revenue;
    }

    Cashier(int num) {
        name = "Кассир № " + num;
        this.num = num;
    }

    @Override
    public void run() {
        System.out.println(this + " открыл кассу");
        while (Dispatcher.marketIsOpened()) {  //пока магазин открыт,или сюда, или в main
            while (QueueBuyers.getCount() > 0) {  //пока в очереди есть покупатель, извлекаем
                Buyer buyer = QueueBuyers.extract();
                if (buyer != null) {
                    System.out.println(this + " начал обслуживать " + buyer);
                    int timeout = Rnd.fromTo(2000, 5000);
                    try {
                        Thread.sleep(timeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (buyer) {
                        Basket basket = buyer.basket;
                        List<Good> listOfProducts = basket.listOfProducts;
                        for (int i = 0; i < listOfProducts.size(); i++) {
                            Good good = listOfProducts.get(i);
                            buyer.check = buyer.check + good.price; // кассир посчитал и вывел чек
                            listOfProducts.remove(i);
                        }
                        this.revenue=this.revenue+buyer.check;
                        System.out.println(this.revenue+ " ВЫРУЧКА!" + this);
                        Shop.balance = Shop.balance + this.revenue;   //выручка магазина
                        System.out.println("СУММА ЧЕКА: " + buyer.check);
                        System.out.println(this + " завершил обслуживание");
                        //Dispatcher.printRevenueTable();
                        buyer.resetWaiting();
                        buyer.notify();
                    }
                }
            }
            //while не сработал, а значит в очереди покупателей нет
            //Dispatcher.cashiers.remove(this);
            //System.out.println("Осталось кассиров " + Dispatcher.cashiers.size());
        }
        System.out.println(this + " закрыл кассу");
    }

    @Override
    public String toString() {
        return name;
    }
}
