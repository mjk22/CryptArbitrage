package com.example.alexander.cryptarbitrage2;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alexander on 1/7/2018.
 */

public class HomePage extends Activity implements View.OnClickListener {
    static ArrayList<Exchange> listOfExchanges;
    static Exchange bitfinex;
    static Exchange bittrex;
    static Exchange binance;
    static Exchange hitBTC;
    static Exchange bitZ;
    static Exchange poloniex;
    static Exchange bitStamp;
    static Exchange OKEX;
    static Exchange GDAX;

    static DownloadTask taskBitfinex;
    static DownloadTask taskBittrex;
    static DownloadTask taskBinance;
    static DownloadTask taskHitBTC;
    static DownloadTask taskBitZ;
    static DownloadTask taskPoloniex;
    static DownloadTask taskBitStamp;
    static DownloadTask taskOKEX;
    static DownloadTask taskGDAX;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        System.out.println("ON CREATE");


        //If this is the first time visiting the homepage
        if(!MainActivity.isCreatedHomepage) {
            listOfExchanges = new ArrayList<>();
            bitfinex = new Exchange("Bitfinex", "ask", "bid", false);
            bittrex = new Exchange("Bittrex", "Ask", "Bid", true);
            binance = new Exchange("Binance", "price", "price", false);
            hitBTC = new Exchange("HitBTC", "ask", "bid", false);
            bitZ = new Exchange("Bit-Z","sell","buy",false);
            poloniex = new Exchange("Poloniex","lowestAsk", "highestBid",false);
            bitStamp = new Exchange("BitStamp","bid","ask",false);
            OKEX = new Exchange("OKEX","sell","buy",false);
            GDAX = new Exchange("GDAX","ask","bid",false);

            initialzeTasks();
        }


        //set up click listeners for buttons on home page
        View viewCurrentOpprotunities = findViewById(R.id.view_current_opprotunities);
        viewCurrentOpprotunities.setOnClickListener(this);

        View modifyExchanges = findViewById(R.id.modify_exchanges);
        modifyExchanges.setOnClickListener(this);

        View modifyCryptocurrencies = findViewById(R.id.modify_cryptocurrencies);
        modifyCryptocurrencies.setOnClickListener(this);


        MainActivity.isCreatedHomepage = true;
    }
    @Override
    public void onStart(){
        super.onStart();
System.out.println(MainActivity.isCreatedCryptocurrencies + " Crypto " + MainActivity.isCreatedExchanges + "Exchanges");
        if(MainActivity.isCreatedExchanges  && MainActivity.isCreatedCryptocurrencies) {
            for(Exchange exchange: listOfExchanges){
                getAsksAndBids(exchange);
                System.out.println(exchange.getName());
            }
        }
    }


    //Creates an Array of URLs and calls downloadtask.execute()
    public void getAsksAndBids(Exchange e){
        String [] APIs = new String [e.getCoins().size()*3];
        DownloadTask task = null;

        System.out.println(e.getCoins().size());
        for(int i = 0; i < e.getCoins().size(); i+=1){
            switch (e.getName()){
                case "Bitfinex":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("USD");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3*i+2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                    task = HomePage.taskBitfinex;
                    break;
                case "Bittrex":
                    APIs[3*i] = "USDT-".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i + 1] = "BTC-".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i + 2] = "ETH-".concat(e.getCoins().get(i).getAbbreviation());
                    task = HomePage.taskBittrex;
                    break;
                case "Binance":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("USDT");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                    task = HomePage.taskBinance;
                    break;
                case "HitBTC":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("USD");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                    task = HomePage.taskHitBTC;
                    break;
                case "Bit-Z":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("_usdt");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("_btc");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("_eth");
                    task = HomePage.taskBitZ;
                    break;
                case "Poloniex":
                    APIs[3*i] = "USDT_".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i + 1] = "BTC_".concat(e.getCoins().get(i).getAbbreviation());
                    APIs[3*i + 2] = "ETH_".concat(e.getCoins().get(i).getAbbreviation());
                    task = HomePage.taskPoloniex;
                    break;
                case "BitStamp":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("USD");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("BTC");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("ETH");
                    task = HomePage.taskBitStamp;
                    break;
                case "OKEX":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("_usdt");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("_btc");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("_eth");
                    task = HomePage.taskOKEX;
                    break;
                case "GDAX":
                    APIs[3*i] = e.getCoins().get(i).getAbbreviation().concat("-usd/ticker");
                    APIs[3*i + 1] = e.getCoins().get(i).getAbbreviation().concat("-btc/ticker");
                    APIs[3*i + 2] = e.getCoins().get(i).getAbbreviation().concat("-eth/ticker");
                    task = HomePage.taskGDAX;
                    break;
            }

        }
        if(task == null) {
            System.out.println("TRIED TO USE NULL DOWNLOAD TASK");
            return;
        }
        else {
            task.execute(APIs);
        }

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.view_current_opprotunities:
                Intent j = new Intent(this, ViewCryptoOpprotunities.class);
                startActivity(j);

                break;
            case R.id.modify_exchanges:
                Intent i = new Intent(this,Exchanges.class);
                startActivity(i);
                break;

            case R.id.modify_cryptocurrencies:
                Intent k = new Intent(this, Cryptocurrencies.class);
                startActivity(k);
                break;
        }
    }

    public void initialzeTasks(){
        taskBitfinex = new DownloadTask(null,"https://api.bitfinex.com/v1/pubticker/", bitfinex );
        taskBittrex = new DownloadTask("MarketName",
                "https://bittrex.com/api/v1.1/public/getmarketsummaries", bittrex);
        taskBinance = new DownloadTask("symbol", "https://www.binance.com/api/v1/ticker/allPrices",
                binance);
        taskHitBTC = new DownloadTask("symbol","https://api.hitbtc.com/api/2/public/ticker", hitBTC);
        taskBitZ = new DownloadTask("","https://www.bit-z.com/api_v1/tickerall", bitZ);
        taskPoloniex = new DownloadTask("","https://poloniex.com/public?command=returnTicker",poloniex);
        taskBitStamp = new DownloadTask(null, "https://www.bitstamp.net/api/v2/ticker/",bitStamp);
        taskOKEX = new DownloadTask("ticker","https://www.okex.com/api/v1/ticker.do?symbol=", OKEX);
        taskGDAX = new DownloadTask(null,"https://api.gdax.com/products/", GDAX);
    }

    @Override
    //after OnPause and OnStart, basically every time
    public void onResume(){
        super.onResume();
    }
}
